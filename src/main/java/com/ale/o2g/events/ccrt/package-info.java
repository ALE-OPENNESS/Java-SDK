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

/**
 * Provides classes and interfaces for receiving and handling call center real-time (RTI) events.
 * <p>
 * This package contains event classes and listener interfaces used by the 
 * {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService} to notify 
 * applications of changes to CCD objects, such as agents, pilots, queues, and processing groups, 
 * according to a {@link com.ale.o2g.types.ccrt.Context Context}.
 * <p>
 * The main entry point is the 
 * {@link com.ale.o2g.events.ccrt.CallCenterRealtimeEventListener CallCenterStatisticsEventListener} 
 * interface, which applications implement to receive notifications. For convenience, applications 
 * can extend the 
 * {@link com.ale.o2g.events.ccrt.CallCenterRealtimeEventAdapter CallCenterRealtimeEventAdapter}, 
 * which provides empty implementations for all listener methods.
 * <p>
 * Each type of RTI notification is represented by a dedicated event class, including:
 * <ul>
 *   <li>{@link com.ale.o2g.events.ccrt.OnAgentRtiChangedEvent OnAcdStatsProgressEvent} - changes for individual agents</li>
 *   <li>{@link com.ale.o2g.events.ccrt.OnPilotRtiChangedEvent OnPilotRtiChangedEvent} - changes for pilots</li>
 *   <li>{@link com.ale.o2g.events.ccrt.OnQueueRtiChangedEvent OnQueueRtiChangedEvent} - changes for queues</li>
 *   <li>{@link com.ale.o2g.events.ccrt.OnPGAgentRtiChangedEvent OnPGAgentRtiChangedEvent} - changes for agent processing groups</li>
 *   <li>{@link com.ale.o2g.events.ccrt.OnPGOtherRtiChangedEvent OnPGOtherRtiChangedEvent} - changes for other processing groups</li>
 * </ul>
 * <p>
 * Typical workflow:
 * <ol>
 *     <li>Create a {@link com.ale.o2g.types.ccrt.Filter AbstractFilter} and a 
 *         {@link com.ale.o2g.types.ccrt.Context Context} to define which CCD objects and attributes 
 *         to monitor and the notification frequency.</li>
 *     <li>Pass the context to the {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService} 
 *         to start receiving events.</li>
 *     <li>Handle notifications through an implementation of 
 *         {@link com.ale.o2g.events.ccrt.CallCenterRealtimeEventListener CallCenterStatisticsEventListener}.</li>
 * </ol>
 * <p>
 * This package works closely with {@link com.ale.o2g.types.ccrt}, which provides the data types, 
 * filters, and context objects required to configure and interpret RTI events.
 *
 * @since 2.7.4
 */
package com.ale.o2g.events.ccrt;
