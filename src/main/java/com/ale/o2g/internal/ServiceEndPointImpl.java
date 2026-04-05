/*
* Copyright 2022 ALE International
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this 
* software and associated documentation files (the "Software"), to deal in the Software 
* without restriction, including without limitation the rights to use, copy, modify, merge, 
* publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons 
* to whom the Software is furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or 
* substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
* BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
* DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.ale.o2g.internal;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.O2GAuthenticationException;
import com.ale.o2g.ServiceEndPoint;
import com.ale.o2g.Session;
import com.ale.o2g.SessionMonitoringPolicy;
import com.ale.o2g.SessionMonitoringPolicy.Behavior;
import com.ale.o2g.Subscription;
import com.ale.o2g.SupervisedAccount;
import com.ale.o2g.internal.services.IAuthentication;
import com.ale.o2g.internal.services.ISessions;
import com.ale.o2g.internal.types.AuthenticateResult;
import com.ale.o2g.internal.types.SessionInfo;
import com.ale.o2g.types.Credential;
import com.ale.o2g.types.Host;
import com.ale.o2g.types.O2GServers;

/**
 *
 */
public class ServiceEndPointImpl implements ServiceEndPoint {

    final static Logger logger = LoggerFactory.getLogger(ServiceEndPointImpl.class);

    // Backoff delays in seconds: 2, 4, 8, 16, capped at 30
    private static final long[] BACKOFF_DELAYS_SECONDS = { 2, 4, 8, 16, 30 };

    private static long getBackoffDelay(int attempt) {
        return BACKOFF_DELAYS_SECONDS[Math.min(attempt, BACKOFF_DELAYS_SECONDS.length - 1)];
    }

    // Server configuration
    private final O2GServers servers;
    private final String apiVersion;
    private Host currentHost;
    private boolean usingSecondary = false;

    // Current service factory — replaced on each recovery
    private ServiceFactory serviceFactory;

    // Monitoring policy
    private SessionMonitoringPolicy sessionMonitoringPolicy = new DefaultSessionMonitoringPolicy();

    // Stored credentials for recovery
    private Credential credential;
    private String applicationName;
    private SupervisedAccount supervisedAccount;

    // Stored subscription for re-subscription after recovery
    private Subscription subscription;

    // Current active session — replaced on each recovery
    private volatile SessionImpl currentSession;

    // Guard against concurrent recovery attempts
    private final AtomicBoolean recovering = new AtomicBoolean(false);

    public ServiceEndPointImpl(O2GServers servers, String apiVersion) {
        this.servers = servers;
        this.apiVersion = apiVersion;
        this.currentHost = servers.getPrimaryHost();
    }

    @Override
    public void setSessionMonitoringPolicy(SessionMonitoringPolicy sessionMonitoringPolicy) {
        this.sessionMonitoringPolicy = sessionMonitoringPolicy;
    }

    @Override
    public Session openSession(Credential credential, String applicationName)
            throws O2GAuthenticationException {
        return openSession(credential, applicationName, null);
    }

    @Override
    public Session openSession(
            Credential credential,
            String applicationName,
            SupervisedAccount supervisedAccount) throws O2GAuthenticationException {

        // Store credentials for recovery
        this.credential = credential;
        this.applicationName = applicationName;
        this.supervisedAccount = supervisedAccount;

        // Retry initial connection until server is reachable or policy aborts
        int attempt = 0;
        while (true) {
            try {
                currentSession = doOpenSession();
                return currentSession;
            } 
            catch (Exception e) {
                checkFailover();

                Behavior behavior = sessionMonitoringPolicy.getBehaviorOnConnectFailure(e);
                if (behavior.isAbort()) {
                    logger.error("Initial connection aborted by monitoring policy.");
                    throw new O2GAuthenticationException(e);
                }

                long delay = behavior.getPeriod() > 0
                        ? behavior.getUnit().toSeconds(behavior.getPeriod())
                        : 5;
                logger.error(
                        "Initial connection attempt {} failed. Retrying in {}s...",
                        attempt + 1, delay);
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } 
                catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new O2GAuthenticationException(new InterruptedException("Connection interrupted."));
                }
                attempt++;
            }
        }
    }

    // ── Recovery ─────────────────────────────────────────────────────────────

    /**
     * Called by {@link SessionMonitoringHandler} when either the keep-alive
     * or the chunk stream signals the session is definitively lost.
     * Runs recovery on a dedicated daemon thread.
     */
    private void onSessionLost() {
        if (!recovering.compareAndSet(false, true)) {
            logger.debug("Session lost signal ignored — recovery already in progress.");
            return;
        }

        Thread recoveryThread = new Thread(() -> {

            logger.error("Session lost. Starting recovery...");

            int attempt = 0;
            boolean recovered = false;

            while (!recovered) {
                checkFailover();

                long delay = getBackoffDelay(attempt);
                logger.debug("Recovery attempt {} in {}s...", attempt + 1, delay);
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                try {
                    SessionImpl session = doOpenSession();
                    currentSession = session;

                    // Re-subscribe if the application had subscribed
                    if (subscription != null) {
                        try {
                            session.listenEvents(subscription);
                        } catch (Exception e) {
                            logger.error("Recovery: re-subscription failed: {}",
                                    e.getMessage());
                            // Session is valid even if re-subscription fails —
                            // application will be notified via onSessionRecovered
                            // and can re-subscribe manually if needed.
                        }
                    }

                    recovered = true;
                    recovering.set(false);
                    logger.info("Recovery successful.");
                    sessionMonitoringPolicy.onSessionRecovered();

                } catch (Exception e) {
                    logger.error("Recovery attempt {} failed: {}",
                            attempt + 1, e.getMessage());
                    attempt++;
                }
            }

        }, "O2G-Recovery");

        recoveryThread.setDaemon(true);
        recoveryThread.start();
    }

    // ── Failover ─────────────────────────────────────────────────────────────

    /**
     * Switches to the secondary server if the primary has failed and geographic
     * HA is configured. Once switched to secondary, stays there permanently.
     */
    private void checkFailover() {
        if (!servers.hasSecondary()) return;
        if (usingSecondary) return;  // already on secondary — stay there permanently

        logger.warn("Primary server unreachable. Switching to secondary permanently.");
        currentHost = servers.getSecondaryHost();
        usingSecondary = true;
    }

    // ── Internal session open ─────────────────────────────────────────────────

    /**
     * Opens a new session on {@link #currentHost} using stored credentials.
     * Wires the session lost callback and subscription callback.
     */
    private SessionImpl doOpenSession() throws Exception {

        if (logger.isTraceEnabled()) {
            logger.trace("doOpenSession -> bootstrap on {}", currentHost);
        }

        // Bootstrap on current host
        serviceFactory = new ServiceFactory(apiVersion);
        serviceFactory.bootstrap(currentHost);

        // Authenticate
        if (logger.isTraceEnabled()) {
            logger.trace("doOpenSession -> authenticate user {}", credential.getLogin());
        }

        IAuthentication authenticationService = serviceFactory.getAuthenticationService();
        AuthenticateResult authenticateResult = authenticationService.authenticate(credential);

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication done.");
        }

        serviceFactory.setSessionsUri(
                authenticateResult.getPrivateUrl(),
                authenticateResult.getPublicUrl());

        // Open session
        if (logger.isTraceEnabled()) {
            logger.trace("doOpenSession -> open session {}", applicationName);
        }

        ISessions sessionsService = serviceFactory.getSessionsService();
        SessionInfo sessionInfo = sessionsService.open(applicationName, supervisedAccount);
        serviceFactory.setServices(sessionInfo);

        if (logger.isDebugEnabled()) {
            logger.debug("Session opened: TimeToLive = {}", sessionInfo.getTimeToLive());
        }

        // Create session with both callbacks:
        // - onSessionLost: signals ServiceEndPointImpl to start recovery
        // - onSubscriptionCallback: stores subscription for re-subscription after recovery
        SessionImpl session = new SessionImpl(
                serviceFactory,
                sessionInfo,
                credential.getLogin(),
                authenticateResult.getLoginName(),
                authenticateResult.isExpired(),
                sessionMonitoringPolicy,
                this::onSessionLost,
                sub -> this.subscription = sub);

        return session;
    }
}
