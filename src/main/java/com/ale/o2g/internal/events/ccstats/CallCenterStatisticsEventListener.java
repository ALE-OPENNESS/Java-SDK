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
package com.ale.o2g.internal.events.ccstats;

import java.util.EventListener;

/**
 * Defines the interface for receiving call center real-time notifications.
 * <p>
 * A class interested in processing call center real-time events can either:
 * <ul>
 *   <li>Implement this interface and all its methods, or</li>
 *   <li>Extend the {@code CallCenterRealtimeEventAdapter} class, overriding only
 *       the methods of interest.</li>
 * </ul>
 * The listener object created from that class must then be registered with the
 * session using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a call center real-time event occurs, the
 * relevant method in the listener object is invoked.
 * <p>
 * The {@linkplain com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}
 * is used to define a {@linkplain com.ale.o2g.types.ccrt.Context Context}.
 * This context is unique for an administrator session and specifies the CCD
 * objects that are included in real-time notifications.
 * <p>
 * Call center real-time notifications are delivered periodically at the interval
 * defined in the {@code Context}. Implementations must be <b>thread-safe</b>, as events
 * may be delivered from multiple threads depending on service configuration.
 * <p>
 * Typical usage:
 * <ol>
 *     <li>Create a class implementing {@code CallCenterStatisticsEventListener} or extend 
 *         {@link CallCenterRealtimeEventAdapter}.</li>
 *     <li>Instantiate your concrete listener class.</li>
 *     <li>Register the listener with a {@link com.ale.o2g.Subscription} in a session 
 *         using {@link com.ale.o2g.Session#listenEvents(Subscription)}.</li>
 *     <li>Create a {@link com.ale.o2g.types.ccrt.Context} specifying which real-time 
 *         information should be sent and received.</li>
 *     <li>Start monitoring via {@link com.ale.o2g.CallCenterRealtimeService}.</li>
 * </ol>
 * <p>
 * Example implementation:
 * <pre><code>
 * public class MyRealtimeListener implements CallCenterStatisticsEventListener {
 *     {@literal @}Override
 *     public void onAgentRtiChanged(OnAcdStatsProgressEvent e) {
 *         // Process agent event
 *     }
 *
 *     {@literal @}Override
 *     public void onPilotRtiChanged(OnPilotRtiChangedEvent e) {
 *         // Process pilot event
 *     }
 *
 *     // Implement other methods as needed
 * }
 * </code></pre>
 * <p>
 * After registration, this listener will be notified asynchronously whenever one
 * or more attributes of monitored CCD objects change. Each event contains only the
 * attributes that have changed since the previous notification.
 *
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see com.ale.o2g.types.ccrt.Context
 * @see com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * @see com.ale.o2g.CallCenterRealtimeEventAdapter
 * @since 2.7.4
 */
public interface CallCenterStatisticsEventListener extends EventListener {

    void onAcdStatsProgress(OnAcdStatsProgressEvent e);
}
