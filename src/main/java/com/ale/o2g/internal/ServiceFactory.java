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
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.AnalyticsService;
import com.ale.o2g.CallCenterAgentService;
import com.ale.o2g.CallCenterManagementService;
import com.ale.o2g.CallCenterPilotService;
import com.ale.o2g.CommunicationLogService;
import com.ale.o2g.DirectoryService;
import com.ale.o2g.EventSummaryService;
import com.ale.o2g.MaintenanceService;
import com.ale.o2g.ManagementService;
import com.ale.o2g.MessagingService;
import com.ale.o2g.O2GException;
import com.ale.o2g.RoutingService;
import com.ale.o2g.TelephonyService;
import com.ale.o2g.UsersService;
import com.ale.o2g.internal.rest.AnalyticsRest;
import com.ale.o2g.internal.rest.AuthenticationRest;
import com.ale.o2g.internal.rest.CallCenterAgentRest;
import com.ale.o2g.internal.rest.CallCenterManagementRest;
import com.ale.o2g.internal.rest.CallCenterPilotRest;
import com.ale.o2g.internal.rest.CommunicationLogRest;
import com.ale.o2g.internal.rest.DirectoryRest;
import com.ale.o2g.internal.rest.EventSummaryRest;
import com.ale.o2g.internal.rest.MaintenanceRest;
import com.ale.o2g.internal.rest.ManagementRest;
import com.ale.o2g.internal.rest.MessagingRest;
import com.ale.o2g.internal.rest.O2GRest;
import com.ale.o2g.internal.rest.RoutingRest;
import com.ale.o2g.internal.rest.SessionsRest;
import com.ale.o2g.internal.rest.SubscriptionsRest;
import com.ale.o2g.internal.rest.TelephonyRest;
import com.ale.o2g.internal.rest.UsersRest;
import com.ale.o2g.internal.services.IAuthentication;
import com.ale.o2g.internal.services.IO2G;
import com.ale.o2g.internal.services.IService;
import com.ale.o2g.internal.services.ISessions;
import com.ale.o2g.internal.services.ISubscriptions;
import com.ale.o2g.internal.types.RoxeRestApiDescriptor;
import com.ale.o2g.internal.types.Service;
import com.ale.o2g.internal.types.SessionInfo;
import com.ale.o2g.types.Host;
import com.ale.o2g.types.ServerInfo;
import com.ale.o2g.util.HttpClientBuilder;

/**
 *
 */
public class ServiceFactory {

	final static Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

	static enum AccessMode {
		Public, Private
	}

	private HttpClient httpClient = null;

	private final Map<Service, URI> servicesUri = new HashMap<Service, URI>();
	private final Map<Service, IService> services = new HashMap<Service, IService>();

	private String apiVersion;
	private AccessMode accessMode;
	private ExecutorService executorService;

	public ServiceFactory(String apiVersion) throws O2GException {
		this.apiVersion = apiVersion;

		try {
		    executorService = Executors.newCachedThreadPool();
			httpClient = HttpClientBuilder.getInstance().build(executorService);
		}
		catch (Exception e) {
			throw new O2GException(e);
		}
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public AccessMode getAccessMode() {
		return accessMode;
	}

	private static void throwUnableToConnect(Host host, Exception e) throws O2GException {
		if ((host.getPrivateAddress() != null) && (host.getPublicAddress() != null)) {
			throw new O2GException(String.format("Unable to bootstrap on O2G [%s, %s]", host.getPrivateAddress(),
					host.getPublicAddress()), e);
		}
		else if (host.getPrivateAddress() != null) {
			throw new O2GException(String.format("Unable to bootstrap on O2G [%s]", host.getPrivateAddress()), e);
		}
		else {
			throw new O2GException(String.format("Unable to bootstrap on O2G [%s]", host.getPublicAddress()), e);
		}
	}

	public ServerInfo bootstrap(Host host) throws O2GException {

		if (logger.isDebugEnabled()) {
			logger.debug("bootstrap : start");
		}

		RoxeRestApiDescriptor descriptor = null;

		// Check connection using private URL if it exist
		if (host.getPrivateAddress() != null) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("bootstrap => Test for private address: {}", host.getPrivateAddress());
				}

				// Initialize the server Uri
				setO2GServiceUri(host.getPrivateAddress());

				// Get the server information
				descriptor = getO2GService().get();
				accessMode = AccessMode.Private;

				if (logger.isDebugEnabled()) {
					logger.debug("bootstrap done: private access");
				}
			}
			catch (Exception e) {
			    // Catch exception in case of getO2GService can be returned
				logger.debug("Unable to bootstrap on {}", host.getPrivateAddress());
				if (host.getPublicAddress() == null) {
					// In case there is no public address, bootstrap definitively failed !
					throwUnableToConnect(host, e);
				}
			}
		}

		if (descriptor == null) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("bootstrap => Test for public address: {}", host.getPublicAddress());
				}

				// Initialize the server Uri
				setO2GServiceUri(host.getPublicAddress());

				// Get the server information
				descriptor = getO2GService().get();
				accessMode = AccessMode.Public;

				if (logger.isDebugEnabled()) {
					logger.debug("bootstrap done: public access");
				}
			}
			catch (Exception e) {
                // Catch exception in case of getO2GService can be returned
				logger.debug("Unable to bootstrap on {address} ", host.getPublicAddress());
				throwUnableToConnect(host, e);
			}
		}

		// Here we should have a valid descriptor
		// Check the requested API version
		RoxeRestApiDescriptor.Version version;
		if (apiVersion != null) {
			version = descriptor.get(apiVersion);
			if (version == null) {
				// We have a problem...
				throw new O2GException("The requested API version [" + apiVersion + "] is not supported.");
			}
		}
		else {
			version = descriptor.getCurrent();
			apiVersion = version.getId();
		}

		// And initialize the authentication URL received
		if (accessMode == AccessMode.Private) {
			servicesUri.put(Service.Authentication, URI.create(version.getInternalUrl()));
		}
		else {
			servicesUri.put(Service.Authentication, URI.create(version.getPublicUrl()));
		}

		return descriptor.getServerInfo();
	}

	private void setO2GServiceUri(String address) {

		if (servicesUri.containsKey(Service.O2G)) {
			servicesUri.remove(Service.O2G);
			services.remove(Service.O2G);
		}

		servicesUri.put(Service.O2G, URI.create("https://" + address + "/api/rest"));
	}

	public IO2G getO2GService() {
		return getOrCreate(Service.O2G, O2GRest.class);
	}

	public IAuthentication getAuthenticationService() {
		return getOrCreate(Service.Authentication, AuthenticationRest.class);
	}

	public ISessions getSessionsService() {
		return getOrCreate(Service.Sessions, SessionsRest.class);
	}

	public UsersService getUsersService() {
		return getOrCreate(Service.Users, UsersRest.class);
	}

	public ISubscriptions getSubscriptionsService() {
		return getOrCreate(Service.Subscriptions, SubscriptionsRest.class);
	}

	public TelephonyService getTelephonyService() {
		return getOrCreate(Service.Telephony, TelephonyRest.class);
	}

	public RoutingService getRoutingService() {
		return getOrCreate(Service.Routing, RoutingRest.class);
	}

    public MessagingService getMessagingService() {
        return getOrCreate(Service.Messaging, MessagingRest.class);
    }

    public MaintenanceService getMaintenanceService() {
		return getOrCreate(Service.Maintenance, MaintenanceRest.class);
	}

	public EventSummaryService getEventSummaryService() {
		return getOrCreate(Service.EventSummary, EventSummaryRest.class);
	}

	public DirectoryService getDirectoryService() {
		return getOrCreate(Service.Directory, DirectoryRest.class);
	}

    public CommunicationLogService getCommunicationLogService() {
        return getOrCreate(Service.CommunicationLog, CommunicationLogRest.class);
    }

    public AnalyticsService getAnalyticsService() {
        return getOrCreate(Service.Analytics, AnalyticsRest.class);
    }

    public CallCenterAgentService getCallCenterAgentService() {
        return getOrCreate(Service.CallCenterAgent, CallCenterAgentRest.class);
    }

    public ManagementService getManagementService() {
        return getOrCreate(Service.Management, ManagementRest.class);
    }
    
    /*
    public RsiService getRsiService() {
        return getOrCreate(Service.Rsi, RsiRest.class);
    }
    */

    public CallCenterPilotService getCallCenterPilotService() {
        return getOrCreate(Service.CallCenterPilot, CallCenterPilotRest.class);
    }

    public CallCenterManagementService getCallCenterManagementService() {
        return getOrCreate(Service.CallCenterManagement, CallCenterManagementRest.class);
    }

	@SuppressWarnings("unchecked")
	private <T extends IService> T getOrCreate(Service serviceName, Class<T> restClass) {

		IService service = services.get(serviceName);
		if (service == null) {

			try {
				service = restClass.getConstructor(HttpClient.class, URI.class).newInstance(httpClient,
						servicesUri.get(serviceName));
				services.put(serviceName, service);
			}
			catch (Exception e) {
				service = null;
			}
		}

		return (T) service;
	}

	public void setSessionsUri(String privateUrl, String publicUrl) {
		if (accessMode == AccessMode.Private) {
			servicesUri.put(Service.Sessions, URI.create(privateUrl));
		}
		else {
			servicesUri.put(Service.Sessions, URI.create(publicUrl));
		}
	}

	public void setServices(SessionInfo sessionInfo) {

		String baseUrl;

		// get the right URL
		if (accessMode == AccessMode.Private) {
			baseUrl = sessionInfo.getPrivateBaseUrl();
		}
		else {
			baseUrl = sessionInfo.getPublicBaseUrl();
		}

		for (SessionInfo.Service service : sessionInfo.getServices()) {
			Service serviceName = Service.get(service.getServiceName());

			if (service.getRelativeUrl().startsWith("/telephony")) {
				// Patch check the principle
				serviceName = Service.Telephony;
			}

			if (!servicesUri.containsKey(serviceName)) {
				if (service.getRelativeUrl().startsWith("/telephony")) {
					servicesUri.put(serviceName, URI.create(baseUrl + "/telephony"));
				}
				else {
					servicesUri.put(serviceName, URI.create(baseUrl + service.getRelativeUrl()));
				}
			}
		}

		// Add maintenance service
		servicesUri.put(Service.Maintenance, URI.create(baseUrl + "/system"));
	}
	
	public void shutdown() {
	    executorService.shutdown();
	}
}
