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
package com.ale.o2g.types.ccstats.data;

import java.time.LocalDateTime;
import java.util.List;

import com.ale.o2g.internal.util.FormatUtil;

/**
 * Represents statistical data retrieved from the Call Center Statistics service.
 * <p>
 * A {@code StatisticsData} object encapsulates the statistics collected for a requester
 * over a defined observation period. It includes  results for both agents and pilots,
 * grouped by observation time slots and periods.
 *
 * <p>
 * The StatisticsData is returned by getData(...) methods of 
 * {@link com.ale.o2g.CallCenterStatisticsService CallCenterStatisticsService}.
 * It provides convenient access to:
 * <ul>
 *   <li>The identifier of the requester.</li>
 *   <li>Statistics for agents, accessible through {@link #getAgentsStats()}.</li>
 *   <li>Statistics for pilots, accessible through {@link #getPilotsStats()}.</li>
 * </ul>
 *
 * <p><b>Typical usage:</b></p>
 * <pre><code>
 * StatisticsData data = statService.getJsonData(context, dateRange);
 *
 * for (StatisticsData.ObjectStatistics&lt;AgentStatisticsRow&gt; slot : data.getAgentsStats()) {
 *     LocalDateTime timeSlot = slot.getTimeSlot();
 *     for (AgentStatisticsRow row : slot.getRows()) {
 *         Integer answeredCalls = row.get(AgentAttributes.nbCallsServed).asInteger();
 *     }
 * }
 * </code></pre>
 *
 * @since 2.7.4
 */
public final class StatisticsData {

    /**
     * Represents the statistical results for a specific observation period and time slot.
     * <p>
     * Each {@code ObjectStatistics} instance groups the data rows collected for a given
     * {@link SelectedPeriod} and an optional time slot (e.g., 15-minute or hourly interval).
     *
     * <p>The class is generic and can represent either agent-level or pilot-level statistics:
     * <ul>
     *   <li>{@code ObjectStatistics<AgentStatisticsRow>} for agent statistics.</li>
     *   <li>{@code ObjectStatistics<PilotStatisticsRow>} for pilot statistics.</li>
     * </ul>
     *
     * @param <T> the type of the statistics row, typically {@link AgentStatisticsRow} or {@link PilotStatisticsRow}
     */
    public static class ObjectStatistics<T> {

        private String timeSlot;
        private SelectedPeriod selectedPeriod;
        private List<T> rows;

        /**
         * Returns the start date and time of this time slot.
         * <p>
         * The time slot represents the period during which the statistics were aggregated
         * (e.g., 2025-09-02T10:00 for a 15-minute interval starting at 10:00).
         *
         * @return the {@link LocalDateTime} representing the time slot start
         */
        public final LocalDateTime getTimeSlot() {
            return FormatUtil.asLocalDateTime(timeSlot);
        }

        /**
         * Returns the selected period during which these statistics were collected.
         *
         * @return the {@link SelectedPeriod} defining the observation range
         */
        public final SelectedPeriod getSelectedPeriod() {
            return selectedPeriod;
        }

        /**
         * Returns the list of statistics rows collected for this time slot and period.
         *
         * @return a list of rows containing detailed statistics data
         */
        public final List<T> getRows() {
            return rows;
        }
        
        protected ObjectStatistics() {
            
        }
    }

    private String supervisor;
    private List<ObjectStatistics<AgentStatisticsRow>> agentsStats;
    private List<ObjectStatistics<PilotStatisticsRow>> pilotsStats;

    /**
     * Returns the identifier of the requester (typically the supervisor) who initiated the query.
     *
     * @return the requester's unique identifier
     */
    public final String getRequesterId() {
        return supervisor;
    }

    /**
     * Returns the list of statistical results related to agents.
     * <p>
     * Each element represents aggregated data for a specific time slot or observation period.
     *
     * @return a list of {@code ObjectStatistics<AgentStatisticsRow>} objects
     */
    public final List<ObjectStatistics<AgentStatisticsRow>> getAgentsStats() {
        return agentsStats;
    }

    /**
     * Returns the list of statistical results related to pilots.
     * <p>
     * Each element represents aggregated data for a specific time slot or observation period.
     *
     * @return a list of {@code ObjectStatistics<PilotStatisticsRow>} objects
     */
    public final List<ObjectStatistics<PilotStatisticsRow>> getPilotsStats() {
        return pilotsStats;
    }
    
    protected StatisticsData() {
        
    }
}
