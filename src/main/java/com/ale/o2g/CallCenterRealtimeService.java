/*
* Copyright 2025 ALE International
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

import java.util.Collection;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.ccrt.Context;
import com.ale.o2g.types.ccrt.RtiObjectIdentifier;
import com.ale.o2g.types.ccrt.RtiObjects;

/**
 * Provides real-time information about CCD objects 
 * from an OXE system in the form of events.
 * <p>
 * This service is available only to administrators and delivers the same level of 
 * information as the legacy RTI interface available in the CCS.
 * <p>
 * The CCD objects that can be monitored include:
 * <ul>
 *     <li>CCD agents</li>
 *     <li>CCD pilots</li>
 *     <li>CCD waiting queues</li>
 *     <li>CCD processing groups associated with agents</li>
 *     <li>CCD processing groups not associated with agents (e.g., forward, guide)</li>
 * </ul>
 * <p>
 * Typical usage sequence:
 * <ol>
 *     <li>Create a class that implements the {@link com.ale.o2g.events.ccrt.CallCenterRealtimeEventListener CallCenterStatisticsEventListener} 
 *         interface to handle real-time events.</li>
 *     <li>Instantiate your concrete listener class.</li>
 *     <li>Create a {@link com.ale.o2g.Subscription Subscription} object and attach the listener instance.</li>
 *     <li>Create a {@link com.ale.o2g.types.ccrt.Context Context} specifying which real-time information will be sent 
 *         and received by the listener.</li>
 *     <li>Start the monitoring.</li>
 * </ol>
 * <p>
 * Example code:
 * <pre><code>
 * // Instantiate an event listener
 * CallCenterStatisticsEventListener myListener = new MyRealtimeListener();
 * 
 * // Attach the listener to a Subscription and register it with the session
 * Subscription subscription = Subscription.newBuilder()
 *         .addCallCenterRealtimeEventListener(myListener)
 *         .build();
 * session.listenEvents(subscription);
 * 
 * // Create a Filter and use it to build a Context
 * Filter filter = new Filter();
 * filter.setAgentAttributes(
 *      AgentAttributes.PrivateCallsTotalDuration,
 *      AgentAttributes.AssociatedSet,
 *      AgentAttributes.LogonDate
 * );
 * filter.setAgentNumbers(new String[] { "60119", "60120" } );
 *       
 * Context context = new Context(30, 5, filter);
 *
 * // Set the created context
 * CallCenterRealtimeService rti = session.getCallCenterRealtimeService();
 * rti.setContext(context);
 * 
 * // Start the eventing
 * rti.start(); 
 * </code></pre>
 * <p>
 * After initialization, the application is notified of events whenever one or more 
 * attributes change. Each event contains only the attributes that have changed 
 * since the previous notification.
 * <p>
 * Access to this service requires a valid license:
 * <ul>
 *     <li><b>CONTACTCENTER_SERVICE</b> in CAPEX mode, or</li>
 *     <li>40 api-tel-f subscriptions in OPEX mode (Purple On Demand)</li>
 * </ul>
 * 
 * @since 2.7.4
 */
public interface CallCenterRealtimeService extends IService {

    /**
     * Returns all CCD objects that currently provide real-time information.
     * <p>
     * The returned {@link com.ale.o2g.types.ccrt.RtiObjects RtiObjects} contains collections
     * of agents, pilots, queues, and processing groups that can be monitored.
     *
     * @return the {@code RtiObjects} containing the CCD objects with real-time
     *         information, or {@code null} if no objects are available or an error occurs
     */
    RtiObjects getRtiObjects();
    
    /**
     * Returns all CCD agents that provide real-time information.
     *
     * @return a collection of {@link com.ale.o2g.types.ccrt.RtiObjectIdentifier} for agents,
     *         or an empty collection if none exist
     */
    Collection<RtiObjectIdentifier> getAgents();
    
    /**
     * Returns all CCD pilots that provide real-time information.
     *
     * @return a collection of {@link com.ale.o2g.types.ccrt.RtiObjectIdentifier} for pilots,
     *         or an empty collection if none exist
     */
    Collection<RtiObjectIdentifier> getPilots();
    
    /**
     * Returns all CCD queues that provide real-time information.
     *
     * @return a collection of {@link com.ale.o2g.types.ccrt.RtiObjectIdentifier} for queues,
     *         or an empty collection if none exist
     */
    Collection<RtiObjectIdentifier> getQueues();
    
    /**
     * Returns all CCD agent processing groups that provide real-time information.
     *
     * @return a collection of {@link com.ale.o2g.types.ccrt.RtiObjectIdentifier} for agent processing groups,
     *         or an empty collection if none exist
     */
    Collection<RtiObjectIdentifier> getAgentProcessingGroups();
    
    /**
     * Returns all CCD processing groups (other than agents) that provide real-time information.
     *
     * @return a collection of {@link com.ale.o2g.types.ccrt.RtiObjectIdentifier} for other processing groups,
     *         or an empty collection if none exist
     */
    Collection<RtiObjectIdentifier> getOtherProcessingGroups();

    /**
     * Associates or updates the monitoring {@link com.ale.o2g.types.ccrt.Context Context} 
     * for this administrator (session). If no context exists, a new one is created.
     * <p>
     * The context defines which objects and attributes are monitored and the 
     * notification frequency for RTI events.
     *
     * @param context the {@code Context} to associate with this administrator
     * @return {@code true} if the update was successful; {@code false} otherwise
     */
    boolean setContext(Context context);

    /**
     * Deletes the monitoring {@link com.ale.o2g.types.ccrt.Context Context} associated 
     * with this administrator (session), stopping any RTI event notifications.
     *
     * @return {@code true} if the deletion was successful; {@code false} otherwise
     */
    boolean deleteContext();

    /**
     * Returns the monitoring {@link com.ale.o2g.types.ccrt.Context Context} associated 
     * with this administrator (session).
     *
     * @return the {@code Context} associated with this administrator, or {@code null} if none exists
     */
    Context getContext();
    
    /**
     * Starts the monitoring of CCD objects according to the associated context.
     * <p>
     * After calling this method, RTI events will be sent to any registered listeners.
     *
     * @return {@code true} if the monitoring started successfully; {@code false} otherwise
     */
    boolean start();
}
