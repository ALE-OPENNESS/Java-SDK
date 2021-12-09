/*
* Copyright 2021 ALE International
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.O2GAuthenticationException;
import com.ale.o2g.ServiceEndPoint;
import com.ale.o2g.Session;
import com.ale.o2g.internal.services.IAuthentication;
import com.ale.o2g.internal.services.ISessions;
import com.ale.o2g.internal.types.AuthenticateResult;
import com.ale.o2g.internal.types.SessionInfo;
import com.ale.o2g.types.Credential;
import com.ale.o2g.types.ServerInfo;

/**
 *
 */
public class ServiceEndPointImpl implements ServiceEndPoint {

	final static Logger logger = LoggerFactory.getLogger(ServiceEndPointImpl.class);

	private ServiceFactory serviceFactory;
	private ServerInfo serverInfo;

	public ServiceEndPointImpl(ServiceFactory serviceFactory, ServerInfo serverInfo) {
		this.serviceFactory = serviceFactory;
		this.serverInfo = serverInfo;
	}

	/**
	 * Open a session on this O2GServer for the specified credential and the
	 * specified applicationName.
	 * 
	 * @param credential      The credential associated to the user or administrator
	 *                        the session is open for.
	 * @param applicationName The applicationName is an identifier for logging and
	 *                        statistic purpose.
	 * @return The create session.
	 * @throws O2GAuthenticationException
	 * @throws ServiceInvocationException
	 */
	@Override
	public Session openSession(Credential credential, String applicationName)
			throws O2GAuthenticationException {

		if (logger.isTraceEnabled()) {
			logger.trace("OpenSession -> Authenticate user {}", credential.getLogin());
		}

		// Authenticate the user or the administrator
		IAuthentication authenticationService = serviceFactory.getAuthenticationService();
		AuthenticateResult authenticateResult = authenticationService.authenticate(credential);
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication done.");
		}

		serviceFactory.setSessionsUri(authenticateResult.getPrivateUrl(), authenticateResult.getPublicUrl());

		// Now open the session and retrieve other services
		if (logger.isTraceEnabled()) {
			logger.trace("OpenSession -> OpenSession {}", applicationName);
		}

		ISessions sessionsService = serviceFactory.getSessionsService();
		SessionInfo sessionInfo = sessionsService.open(applicationName);
		serviceFactory.setServices(sessionInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("Session opened: TimeToLive = {}", sessionInfo.getTimeToLive());
		}

		// Create the session
		Session session = new SessionImpl(serviceFactory, sessionInfo, credential.getLogin());

		// OK, create the session
		return session;
	}
}
