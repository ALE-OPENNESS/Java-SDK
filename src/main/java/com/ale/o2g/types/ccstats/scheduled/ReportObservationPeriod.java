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
package com.ale.o2g.types.ccstats.scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ale.o2g.internal.util.AssertUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Represents an observation period used in a scheduled statistic request.
 * <p>
 * This class allows specifying the time range over which statistics should be
 * collected or analyzed. Observation periods can be standard predefined periods
 * (current day, week, month, last N days/weeks, last month) or a custom date
 * range.
 * <p>
 * Instances are created using static factory methods for clarity and safety.
 * 
 * @since 2.7.4
 */
public final class ReportObservationPeriod {

    /**
     * Defines the type of observation period.
     */
    public static enum PeriodType {

        /** The current day. */
        @SerializedName("currentDay")
        CURRENT_DAY,

        /** The current week. */
        @SerializedName("currentWeek")
        CURRENT_WEEK,

        /** The current month. */
        @SerializedName("currentMonth")
        CURRENT_MONTH,

        /** The last N days. */
        @SerializedName("lastDays")
        LAST_DAYS,

        /** The last N weeks. */
        @SerializedName("lastWeeks")
        LAST_WEEKS,

        /** The last month. */
        @SerializedName("lastMonth")
        LAST_MONTHS,

        /** A custom range defined by a start date and end date. */
        @SerializedName("fromDateToDate")
        FROM_DATE_TO_DATE
    }

    private PeriodType periodType;
    private int lastNb;
    private String beginDate;
    private String endDate;

    /**
     * Creates an observation period for the current day.
     * 
     * @return a ReportObservationPeriod representing the current day
     */
    public static ReportObservationPeriod onCurrentDay() {
        return new ReportObservationPeriod(PeriodType.CURRENT_DAY);
    }

    /**
     * Creates an observation period for the current week.
     * 
     * @return a ReportObservationPeriod representing the current week
     */
    public static ReportObservationPeriod onCurrentWeek() {
        return new ReportObservationPeriod(PeriodType.CURRENT_WEEK);
    }

    /**
     * Creates an observation period for the current month.
     * 
     * @return a ReportObservationPeriod representing the current month
     */
    public static ReportObservationPeriod onCurrentMonth() {
        return new ReportObservationPeriod(PeriodType.CURRENT_MONTH);
    }

    /**
     * Creates an observation period for the last N days.
     * 
     * @param nbDays number of days (must be 1-31)
     * @return a ReportObservationPeriod representing the last N days
     */
    public static ReportObservationPeriod onLastDays(int nbDays) {
        return new ReportObservationPeriod(PeriodType.LAST_DAYS, AssertUtil.requireRange(nbDays, 1, 31, "nbDays"));
    }

    /**
     * Creates an observation period for the last N weeks.
     * 
     * @param nbWeeks number of weeks (must be 1-4)
     * @return a ReportObservationPeriod representing the last N weeks
     */
    public static ReportObservationPeriod onLastWeeks(int nbWeeks) {
        return new ReportObservationPeriod(PeriodType.LAST_WEEKS, AssertUtil.requireRange(nbWeeks, 1, 4, "nbWeeks"));
    }

    /**
     * Creates an observation period for the last month.
     * 
     * @return a ReportObservationPeriod representing the last month
     */
    public static ReportObservationPeriod onLastMonth() {
        return new ReportObservationPeriod(PeriodType.LAST_MONTHS, 1);
    }

    /**
     * Creates a custom observation period starting from a given date for a number
     * of days.
     * 
     * @param from   the start date (must not be null and must be in the past)
     * @param nbDays number of days (must be 1-31)
     * @return a ReportObservationPeriod representing the custom range
     * @throws IllegalArgumentException if 'from' is in the future
     */
    public static ReportObservationPeriod fromDate(LocalDateTime from, int nbDays) {

        if (AssertUtil.requireNotNull(from, "from").isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("'from' must be in the past");
        }

        LocalDateTime to = AssertUtil.requireNotNull(from, "from")
                .plusDays(AssertUtil.requireRange(nbDays, 1, 31, "nbDays"));

        return new ReportObservationPeriod(PeriodType.FROM_DATE_TO_DATE, -1, from, to);
    }

    /**
     * Returns the type of the observation period.
     * 
     * @return the period type
     */
    public final PeriodType getPeriodType() {
        return periodType;
    }

    /**
     * Returns the number of units (days or weeks) for last-period types.
     * 
     * @return the number of units, or -1 for other types
     */
    public final int getLastUnits() {
        return lastNb;
    }

    /**
     * Returns the start date of the observation period.
     * <p>
     * Only valid if the period type is {@link PeriodType#FROM_DATE_TO_DATE}.
     *
     * @return the begin date as {@link LocalDateTime}, or {@code null} if not
     *         applicable
     */
    public final LocalDateTime getBeginDate() {
        if (beginDate == null) {
            return null;
        }
        else {
            return LocalDateTime.parse(beginDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    /**
     * Returns the end date of the observation period.
     * <p>
     * Only valid if the period type is {@link PeriodType#FROM_DATE_TO_DATE}.
     *
     * @return the end date as {@link LocalDateTime}, or {@code null} if not
     *         applicable
     */
    public final LocalDateTime getEndDate() {
        if (endDate == null) {
            return null;
        }
        else {
            return LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    private ReportObservationPeriod(PeriodType periodType) {
        this(periodType, -1);
    }

    private ReportObservationPeriod(PeriodType periodType, int lastNb) {
        this(periodType, lastNb, null, null);
    }

    private ReportObservationPeriod(PeriodType periodType, int lastNb, LocalDateTime from, LocalDateTime to) {
        this.periodType = periodType;
        this.lastNb = lastNb;

        if (from != null) {
            beginDate = from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        if (to != null) {
            endDate = to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}
