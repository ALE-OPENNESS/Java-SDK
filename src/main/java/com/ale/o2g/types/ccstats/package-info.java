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
 * Provides the core types and utility classes for working with the
 * {@link com.ale.o2g.CallCenterStatisticsService CallCenterStatisticsService}.
 * <p>
 * This package defines the primary data structures, filters, and configuration
 * objects used to query, filter, and format call center (CCD) statistics for
 * agents and pilots. It forms the foundation for building contexts and report
 * definitions used by the statistics service.
 *
 * <h2>Main Components</h2>
 * <ul>
 *   <li>{@link Requester} - represents the entity requesting statistics,
 *       typically associated with a supervisor or administrator.</li>
 *   <li>{@link Context} - defines the scope of a statistics query,
 *       including filters and attributes.</li>
 *   <li>{@link Filter} and its subclasses
 *       ({@link AgentFilter}, {@link com.ale.o2g.types.ccstats.PilotFilter PilotFilter}) -
 *       specify which agents or pilots to include in a query.</li>
 *   <li>{@link AgentAttributes},
 *       {@link PilotAttributes}, and
 *       {@link AgentByPilotAttributes} - define the available
 *       statistical counters and metrics.</li>
 *   <li>{@link Format} - enumerates supported output formats
 *       (e.g., CSV, Excel).</li>
 *   <li>{@link TimeInterval} - represents the granularity
 *       of time slots (e.g., 15 or 30 minutes).</li>
 *   <li>{@link Language} - defines the language to use for
 *       localized report generation.</li>
 * </ul>
 *
 * <h2>Subpackages</h2>
 * <ul>
 *   <li>{@link com.ale.o2g.types.ccstats.data} - contains data models used when retrieving
 *       statistics in JSON format.</li>
 *   <li>{@link com.ale.o2g.types.ccstats.scheduled} - defines types related to scheduled
 *       reports, including recurrence and observation period handling.</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre><code>
 * CallCenterStatisticsService statsService = session.getCallCenterStatisticsService();
 *
 * Requester requester = statsService.createRequester(
 *         "John Doe", Language.EN, new String[] { "60118", "60119" });
 *
 * AgentFilter filter = Filter.createAgentFilter();
 * filter.setAgentAttributes(AgentAttribute.ALL);
 *
 * Context context = statsService.createContext(requester, "AgentStats", "Daily agent statistics", filter);
 * </code></pre>
 *
 * <p>
 * Together, these classes and subpackages provide the complete API surface for defining,
 * retrieving, and scheduling CCD statistics reports.
 *
 * @since 2.7.4
 */
package com.ale.o2g.types.ccstats;
