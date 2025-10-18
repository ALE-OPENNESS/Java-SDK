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

import java.time.LocalDate;

import com.ale.o2g.internal.util.FormatUtil;
import com.ale.o2g.types.ccstats.TimeInterval;

/**
 * Defines a time range and granularity for retrieving statistical data.
 * <p>
 * A {@code SelectedPeriod} specifies both the observation duration and the temporal resolution
 * (slot size) used when aggregating statistics. It is used when querying data or generating
 * detailed or multi-day reports.
 *
 * <p>The selected period includes:
 * <ul>
 *   <li>The type of observation period ({@link DataObservationPeriod}), such as a single day or several consecutive days.</li>
 *   <li>The time slot granularity ({@link TimeInterval}) used for grouping data within the period (e.g., 15-minute or hourly intervals).</li>
 *   <li>The start and end boundaries of the observation period.</li>
 * </ul>
 *
 * <p>
 * This class is typically used as part of a query or report configuration to define
 * the exact time frame and aggregation level for statistical computations.
 *
 * @since 2.7.4
 */
public class SelectedPeriod {
    
    private DataObservationPeriod periodType;
    private TimeInterval slotType;
    private String beginDate;
    private String endDate;
    
    /**
     * Returns the start date of the selected period as a {@link LocalDate}.
     *
     * @return the beginning date of the observation period
     */
    public LocalDate getBeginDate() {
        return FormatUtil.asLocalDate(beginDate);
    }

    /**
     * Returns the end date of the selected period as a {@link LocalDate}.
     *
     * @return the ending date of the observation period
     */
    public LocalDate getEndDate() {
        return FormatUtil.asLocalDate(endDate);
    }

    /**
     * Returns the observation period type, defining whether statistics are
     * collected for a single day or multiple consecutive days.
     *
     * @return the observation period type
     */
    public DataObservationPeriod getObservationPeriod() {
        return this.periodType;
    }

    /**
     * Returns the time slot granularity used for aggregating statistics within
     * the selected period (e.g., 15-minute, 30-minute, or hourly).
     *
     * @return the time interval (slot size) for data aggregation
     */
    public TimeInterval getTimeInterval() {
        return this.slotType;
    }
    
    protected SelectedPeriod() {
        
    }
}
