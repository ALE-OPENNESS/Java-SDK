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
 * Provides types and utilities for handling Call Center real-time (RTI) information
 * in the CCD (Call Center Distribution) system.
 * <p>
 * This package contains classes and enums used by the {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}
 * to define which objects and attributes should be monitored in real-time, and to process
 * real-time events related to CCD objects.
 * <p>
 * The main concepts in this package are:
 * <ul>
 *     <li>{@link com.ale.o2g.types.ccrt.AgentAttributes AgentAttributes}, {@link com.ale.o2g.types.ccrt.QueueAttributes QueueAttributes}, {@link com.ale.o2g.types.ccrt.PilotAttributes PilotAttributes}, 
 *         {@link com.ale.o2g.types.ccrt.AgentProcessingGroupAttributes AgentProcessingGroupAttributes}, {@link com.ale.o2g.types.ccrt.OtherProcessingGroupAttributes OtherProcessingGroupAttributes}: 
 *         Enums representing the possible attributes for each type of CCD object. These
 *         attributes can be selected to control which data is reported in real-time events.</li>
 *     <li>{@link com.ale.o2g.types.ccrt.RtiObjectIdentifier RtiObjectIdentifier}: Represents a single CCD object, providing its number,
 *         name, and unique key.</li>
 *     <li>{@link com.ale.o2g.types.ccrt.RtiObjects RtiObjects}: Groups collections of {@link com.ale.o2g.types.ccrt.RtiObjectIdentifier RtiObjectIdentifier} for all
 *         CCD object types (agents, pilots, queues, processing groups). Can be used to create
 *         a {@link com.ale.o2g.types.ccrt.Filter AbstractFilter} containing all these objects.</li>
 *     <li>{@link com.ale.o2g.types.ccrt.Filter AbstractFilter}: Defines which CCD objects and their attributes should be included
 *         in real-time eventing. Filters can be applied when creating a {@link com.ale.o2g.types.ccrt.Context}.</li>
 *     <li>{@link com.ale.o2g.types.ccrt.Context}: Represents a subscription to CCD real-time events, specifying
 *         the observation period, notification frequency, and the {@link com.ale.o2g.types.ccrt.Filter AbstractFilter} defining
 *         which objects and attributes are monitored.</li>
 * </ul>
 * <p>
 * Typical workflow:
 * <ol>
 *     <li>Create a {@link com.ale.o2g.types.ccrt.Filter AbstractFilter} to select which CCD objects and attributes to monitor.</li>
 *     <li>Use the filter to create a {@link com.ale.o2g.types.ccrt.Context Context} defining the observation period and notification frequency.</li>
 *     <li>Pass the context to the {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService} to start monitoring real-time events.</li>
 *     <li>Receive and handle events through a listener implementing {@link com.ale.o2g.internal.events.ccstats.CallCenterStatisticsEventListener CallCenterStatisticsEventListener}.</li>
 * </ol>
 * <p>
 * This package is part of the CCD real-time eventing API and is intended to provide
 * a type-safe, structured way to select, filter, and process real-time information from
 * the call center system.
 * 
 * @since 2.7.4
 */
package com.ale.o2g.types.ccrt;
