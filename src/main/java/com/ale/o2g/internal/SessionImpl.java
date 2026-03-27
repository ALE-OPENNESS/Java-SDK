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

import java.net.URI;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.AnalyticsService;
import com.ale.o2g.CallCenterAgentService;
import com.ale.o2g.CallCenterManagementService;
import com.ale.o2g.CallCenterPilotService;
import com.ale.o2g.CallCenterRealtimeService;
import com.ale.o2g.CallCenterStatisticsService;
import com.ale.o2g.CommunicationLogService;
import com.ale.o2g.DirectoryService;
import com.ale.o2g.EventSummaryService;
import com.ale.o2g.MaintenanceService;
import com.ale.o2g.ManagementService;
import com.ale.o2g.MessagingService;
import com.ale.o2g.O2GException;
import com.ale.o2g.RoutingService;
import com.ale.o2g.Session;
import com.ale.o2g.SessionMonitoringPolicy;
import com.ale.o2g.Subscription;
import com.ale.o2g.TelephonyService;
import com.ale.o2g.UserManagementService;
import com.ale.o2g.UsersService;
import com.ale.o2g.internal.events.ChunkEventing;
import com.ale.o2g.internal.events.SubscriptionImpl;
import com.ale.o2g.internal.services.ISessions;
import com.ale.o2g.internal.services.ISubscriptions;
import com.ale.o2g.internal.types.SessionInfo;
import com.ale.o2g.internal.types.SubscriptionResult;
import com.ale.o2g.types.Account;

public class SessionImpl implements Session {    
    
    class ImplAccount implements Account {

        private String loginName;
        private String o2gLoginName;
        private boolean isGoingToExpire;
        
        protected ImplAccount(String loginName, String o2gLoginName, boolean isGoingToExpire) {
            this.loginName = loginName;
            
            if (this.o2gLoginName != null) {
                this.o2gLoginName = o2gLoginName;
            }
            else {
                this.o2gLoginName = loginName;
            }
            this.isGoingToExpire = isGoingToExpire;
        }

        @Override
        public String getLoginName() {
            return loginName;
        }

        @Override
        public String getO2GUserLoginName() {
            if (o2gLoginName == null) {
                return loginName;
            }
            else {
                return o2gLoginName;
            }
        }

        @Override
        public boolean isGoingToExpire() {
            return isGoingToExpire;
        }
        
    };
    
    
	final static Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	
	private SessionInfo info;
	private final ServiceFactory serviceFactory;
    private ChunkEventing chunkEventing = null;
    private KeepAlive keepAlive = null;
    private SessionMonitoringHandler sessionMonitoringHandler = null;

    private String subscriptionId = null;

	private Account account = null;
	private String loginName;

	public SessionImpl(ServiceFactory serviceFactory, SessionInfo sessionInfo, final String loginName, final String o2gLoginName, final boolean expire, SessionMonitoringPolicy sessionMonitoringPolicy) {
		
		this.serviceFactory = serviceFactory;
		this.info = sessionInfo;
		this.sessionMonitoringHandler = new SessionMonitoringHandler(sessionMonitoringPolicy, this);

		this.loginName = loginName;
		
		this.account = new ImplAccount(loginName, o2gLoginName, expire);
		
		startKeepAlive();
	}

    @Override
    public String getLoginName() {
        return loginName;
    }


    @Override
    public Account getAccount() {
        return account;
    }

    private void startKeepAlive() {
        keepAlive = new KeepAlive(info.getTimeToLive(), serviceFactory.getSessionsService(), sessionMonitoringHandler);
        keepAlive.start();
    }
	
	@Override
	public UsersService getUsersService() {
		return this.serviceFactory.getUsersService();
	}
	
	@Override
	public TelephonyService getTelephonyService() {
		return this.serviceFactory.getTelephonyService();
	}

	@Override
	public RoutingService getRoutingService() {
		return this.serviceFactory.getRoutingService();
	}

	@Override
	public EventSummaryService getEventSummaryService() {
		return this.serviceFactory.getEventSummaryService();
	}

    @Override
    public MessagingService getMessagingService() {
        return this.serviceFactory.getMessagingService();
    }

    @Override
	public MaintenanceService getMaintenanceService() {
		return this.serviceFactory.getMaintenanceService();
	}

	@Override
	public DirectoryService getDirectoryService() {
		return this.serviceFactory.getDirectoryService();
	}

    @Override
    public CommunicationLogService getCommunicationLogService() {
        return this.serviceFactory.getCommunicationLogService();
    }

    @Override
    public AnalyticsService getAnalyticsService() {
        return this.serviceFactory.getAnalyticsService();
    }
	
    @Override
    public CallCenterAgentService getCallCenterAgentService() {
        return this.serviceFactory.getCallCenterAgentService();
    }

    @Override
    public ManagementService getManagementService() {
        return this.serviceFactory.getManagementService();
    }

    /*
    @Override
    public RsiService getRsiService() {
        return this.serviceFactory.getRsiService();
    }
    */


    @Override
    public CallCenterRealtimeService getCallCenterRealtimeService() {
        return this.serviceFactory.getCallCenterRealtimeService();
    }

    @Override
    public UserManagementService getUserManagementService() {
        return this.serviceFactory.getUserManagementService();
    }

    @Override
    public CallCenterPilotService getCallCenterPilotService() {
        return this.serviceFactory.getCallCenterPilotService();
    }

    @Override
    public CallCenterManagementService getCallCenterManagementService() {
        return this.serviceFactory.getCallCenterManagementService();
    }

    @Override
    public CallCenterStatisticsService getCallCenterStatisticsService() {        
       return this.serviceFactory.getCallCenterStatisticsService();
    }
    
    /*
    @Override
    public RecordingService getRecordingService() {
        return this.serviceFactory.getRecordingService();
    }
    */


    @Override
	public void listenEvents(Subscription subscription) throws O2GException {
		Objects.requireNonNull(subscription);
		
		startEventing((SubscriptionImpl)subscription);
	}

	@Override
	public void close() {
		if (subscriptionId != null) {
            stopEventing();
        }
		
        if (keepAlive != null) {
            keepAlive.stop();
        }

        // Close the session
        try {
            ISessions sessionService = serviceFactory.getSessionsService();
            sessionService.close();
        }
        catch (Exception e) {
            logger.error("Error while closing session on server", e);
        }
        
        serviceFactory.shutdown();

        logger.info("Session is closed.");
	}

	@Override
	public boolean isAdmin() {
		return this.info.isAdmin();
	}

	/*
	 * Start the eventing 
	 */
    private void startEventing(SubscriptionImpl subscription) throws O2GException {
    	
    	try {
            ISubscriptions subscriptionsService = serviceFactory.getSubscriptionsService();
            SubscriptionResult subscriptionResult = subscriptionsService.create(subscription);
    		
	        if ((subscriptionResult != null) && subscriptionResult.isAccepted()) {
	        	
	            subscriptionId = subscriptionResult.getId();
	
	            logger.trace("Subscription has been accepted.");
	
	            URI chunkUri;
	            if (serviceFactory.getAccessMode() == ServiceFactory.AccessMode.Private) {
	                chunkUri = URI.create(subscriptionResult.getPrivatePollingUrl());
	            }
	            else {
	                chunkUri = URI.create(subscriptionResult.getPublicPollingUrl());
	            }
	
	            chunkEventing = new ChunkEventing(chunkUri, subscription.getListeners(), sessionMonitoringHandler);
	            chunkEventing.start();
	
	            // set the listener reference to sessionFactory
	            this.serviceFactory.setEventListeners(subscription.getListeners());
	            
	            logger.info("Eventing is started.");
	        }
	        else {
	            logger.error("Subscription has been refused. Fix the subscription request.");
	            if (subscriptionResult != null) {
	                throw new O2GException("Subscription Refused : " + subscriptionResult.getMessage());
	            }
	            else {
                    throw new O2GException("Subscription Refused");
	            }
	        }
    	}
    	catch (Exception e) {
    		throw new O2GException(e);
    	}
    }
    
    private void stopEventing() {
        
        if (subscriptionId != null) {
            chunkEventing.stop();

            try {
                ISubscriptions subscriptionsService = serviceFactory.getSubscriptionsService();
                subscriptionsService.delete(subscriptionId);
                logger.trace("Subscription has been deleted");
            }
            catch (Exception e) {
                logger.error("Error while deleting subscription", e);
            }
            
            // Subscription is cancelled
            logger.info("Eventing is stopped.");
        }
    }
}
