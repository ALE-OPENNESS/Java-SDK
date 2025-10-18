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

import com.ale.o2g.types.Account;

/**
 * The {@code Session} object represents an opened session on the O2G server.
 * The session gives access to the different services.
 * 
 */
public interface Session {

    /**
     * Get the login name of the user who has opened this session.
     * 
     * @return the user login name
     * @deprecated Use {@link #getAccount()} instead.
     */
    @Deprecated
    String getLoginName();
    
    /**
     * Get the account corresponding to the O2G user who has opened the session.
     * @return the user account.
     */
    Account getAccount();
    
    /**
     * Returns whether this session is opened by an administrator.
     * 
     * @return {@code true} if the session is opened by an administrator;
     *         {@code false} otherwise.
     */
    boolean isAdmin();

    /**
     * Returns the UsersService.
     * 
     * @return the {@link UsersService UsersService } object.
     */
    UsersService getUsersService();

    /**
     * Returns the MaintenanceService.
     * 
     * @return the {@link MaintenanceService MaintenanceService } object.
     */
    MaintenanceService getMaintenanceService();

    /**
     * Returns the ManagementService.
     * 
     * @return the {@link ManagementService ManagementService } object.
     */
    ManagementService getManagementService();

    /**
     * Returns the DirectoryService.
     * 
     * @return the {@link DirectoryService DirectoryService } object.
     */
    DirectoryService getDirectoryService();

    /**
     * Returns the TelephonyService.
     * 
     * @return the {@link TelephonyService TelephonyService } object.
     */
    TelephonyService getTelephonyService();

    /**
     * Returns the EventSummaryService.
     * 
     * @return the {@link EventSummaryService EventSummaryService } object.
     */
    EventSummaryService getEventSummaryService();

    /**
     * Returns the MessagingService.
     * 
     * @return the {@link MessagingService MessagingService } object.
     */
    MessagingService getMessagingService();

    /**
     * Returns the CommunicationLogService.
     * 
     * @return the {@link CommunicationLogService CommunicationLogService } object.
     */
    CommunicationLogService getCommunicationLogService();

    /**
     * Returns the AnalyticsService.
     * 
     * @return the {@link AnalyticsService AnalyticsService } object.
     */
    AnalyticsService getAnalyticsService();

    /**
     * Returns the RoutingService.
     * 
     * @return the {@link RoutingService RoutingService } object.
     */
    RoutingService getRoutingService();

    /**
     * Returns the CallCenterAgentService.
     * 
     * @return the {@link CallCenterAgentService CallCenterAgentService } object.
     */
    CallCenterAgentService getCallCenterAgentService();

    /**
     * Returns the RsiService.
     * 
     * @return the {@link RsiService RsiService } object.
     */
//    RsiService getRsiService();
    
    
    /**
     * Returns the CallCenterRealtimeService.
     * @return the {@link CallCenterRealtimeService CallCenterRealtimeService } object.
     */
    CallCenterRealtimeService getCallCenterRealtimeService();
    
    /**
     * Returns the CallCenterPilotService.
     * 
     * @return the {@link CallCenterPilotService CallCenterPilotService } object.
     */
    CallCenterPilotService getCallCenterPilotService();
    
    /**
     * Returns the CallCenterManagementService.
     * 
     * @return the {@link CallCenterManagementService CallCenterManagementService } object.
     */
    CallCenterManagementService getCallCenterManagementService();
    
    
    /**
     * Returns the UserManagementService
     * @return the {@link UserManagementService UserManagementService } object.
     */
    UserManagementService getUserManagementService();
    
    
    /**
     * Returns the RecordingService
     * @return the {@link RecordingService RecordingService } object.
     */
//    RecordingService getRecordingService();
    
    
    /**
     * Returns the CallCenterStatisticsService
     * @return the {@link CallCenterStatisticsService CallCenterStatisticsService } object.
     */
    CallCenterStatisticsService getCallCenterStatisticsService();
    
    /**
     * Listen to event notification from the O2G server.
     * <p>
     * The application needs to subscribe to the desired event.
     * 
     * @param subscription The {@link Subscription} Subscription object.
     * @throws O2GException - When the subscription fail.
     */
    void listenEvents(Subscription subscription) throws O2GException;

    /**
     * Close an open session. This method closes the session on server, stop the
     * keep alive and the eventing if any. If the communication with the server is
     * broken, the session is closed locally and the ressources are released.
     */
    void close();
}
