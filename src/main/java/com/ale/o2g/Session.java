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
package com.ale.o2g;

/**
 * The {@code Session} object represents an opened session on the O2G server. The session gives access to the different services.
 * 
 */
public interface Session {

	/**
	 * Get the login name of the user who has opened this session.
	 * @return the user login name
	 */
    String getLoginName();

    /**
     * Returns whether this session is opened by an administrator.
     * @return {@code true} if the session is opened by an administrator; {@code false} otherwise.
     */
	boolean isAdmin();
    
	/**
	 * Return the UsersService.
	 * @return the {@link UsersService UsersService } object.
	 */
    UsersService getUsersService();
    
    /**
	 * Return the MaintenanceService.
	 * @return the {@link MaintenanceService MaintenanceService } object.
     */
    MaintenanceService getMaintenanceService();
    
    /**
     * Return the ManagementService.
     * @return the {@link ManagementService ManagementService } object.
     */
    ManagementService getManagementService();
    
    /**
	 * Return the DirectoryService.
	 * @return the {@link DirectoryService DirectoryService } object.
     */
    DirectoryService getDirectoryService();

    /**
	 * Return the TelephonyService.
	 * @return the {@link TelephonyService TelephonyService } object.
     */
    TelephonyService getTelephonyService();
    
    /**
	 * Return the EventSummaryService.
	 * @return the {@link EventSummaryService EventSummaryService } object.
     */
    EventSummaryService getEventSummaryService();
        
    /**
     * Return the MessagingService.
     * @return the {@link MessagingService MessagingService } object.
     */
    MessagingService getMessagingService();
    
    /**
     * Return the CommunicationLogService.
     * @return the {@link CommunicationLogService CommunicationLogService } object.
     */
    CommunicationLogService getCommunicationLogService();
        
    /**
     * Return the AnalyticsService.
     * @return the {@link AnalyticsService AnalyticsService } object.
     */
    AnalyticsService getAnalyticsService();
    
    /**
	 * Return the RoutingService.
	 * @return the {@link RoutingService RoutingService } object.
     */
    RoutingService getRoutingService();
    
    /**
     * Return the CallCenterAgentService.
     * @return the {@link CallCenterAgentService CallCenterAgentService } object.
     */
    CallCenterAgentService getCallCenterAgentService();
    
    /**
     * Return the RsiService.
     * @return the {@link RsiService RsiService } object.
     */
    RsiService getRsiService();


    /**
     * Listen to event notification from the O2G server.
     * <p>
     * The application needs to subscribe to the desired event.
     * @param subscription The {@link Subscription} Subscription object.
     * @throws O2GException - When the subscription fail.
     */
    void listenEvents(Subscription subscription) throws O2GException;

    /**
     * Close an open session.
     * @throws O2GException in case of error (if the session is not opened).
     */
    void close() throws O2GException;
}
