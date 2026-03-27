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
 * Provides data model classes used for representing Call Center statistics.
 * <p>
 * The classes in this package define the structure of the statistical data returned by
 * the {@link com.ale.o2g.CallCenterStatisticsService CallCenterStatisticsService}
 * when retrieving results through its query methods such as
 * {@link com.ale.o2g.CallCenterStatisticsService#getData(com.ale.o2g.types.ccstats.Context, com.ale.o2g.types.ccstats.DateRange) CallCenterStatisticsService.getData(Context, DateRange)}.
 *
 * <h2>Overview</h2>
 * <ul>
 *   <li>{@link StatisticsData} - the root object representing a complete
 *       statistics report, including agent and pilot data.</li>
 *   <li>{@link AgentStatisticsRow} - detailed statistics for a single agent.</li>
 *   <li>{@link AgentByPilotStatisticsRow} - statistics grouped by pilot for each agent.</li>
 *   <li>{@link PilotStatisticsRow} - aggregated statistics for pilots.</li>
 *   <li>{@link SelectedPeriod} - defines the observation range and granularity of data collection.</li>
 *   <li>{@link DataObservationPeriod} - enumerates the types of observation periods (single day, multiple days, etc.).</li>
 *   <li>{@link StatisticsRow} - abstract base class providing type-safe dynamic access to counters using the related attribute enum.</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre><code>
 * StatisticsData data = statService.getData(context, dateRange);
 *
 * for (StatisticsData.ObjectStatistics&lt;AgentStatisticsRow&gt; slot : data.getAgentsStats()) {
 *     LocalDateTime timeSlot = slot.getTimeSlot();
 *     for (AgentStatisticsRow row : slot.getRows()) {
 *         // Access counters dynamically using the attribute enum
 *         Integer answeredCalls = row.get(AgentAttributes.nbCallsServed).asInteger();
 *         Duration avgCallDuration = row.get(AgentAttributes.callProcADur).asDuration();
 *
 *         System.out.println(row.getAgentId() + " - Answered calls: " + answeredCalls 
 *                            + ", Avg call duration: " + avgCallDuration);
 *     }
 * }
 * </code></pre>
 *
 * <p>
 * This approach avoids the need for multiple getters for each counter and
 * provides a consistent way to access all statistics fields dynamically
 * while preserving type safety through the enum-based API.
 *
 * @since 2.7.4
 */
package com.ale.o2g.types.ccstats.data;
