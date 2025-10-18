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
package com.ale.o2g.types.ccstats.scheduled;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.ale.o2g.internal.util.AssertUtil;

/**
 * Represents a recurrence schedule for a scheduled statistic report.
 * <p>
 * This class allows specifying how often a scheduled report should be
 * generated: daily, weekly, or monthly. Instances are created using static
 * factory methods for clarity and safety.
 * <p>
 * <b>Examples:</b>
 * 
 * <pre>
 * Recurrence daily = Recurrence.daily();
 * Recurrence weekly = Recurrence.weekly(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
 * Recurrence monthly = Recurrence.monthly(15); // on the 15th day of each month
 * </pre>
 * 
 * @since 2.7.4
 */
public final class Recurrence {

    private static enum Type {
        DAILY, WEEKLY, MONTHLY
    }

    private Type type;
    private Set<DayOfWeek> daysInWeek;
    private int dayInMonth;

    /**
     * Creates a recurrence that occurs every day.
     * 
     * @return a daily recurrence
     */
    public static Recurrence daily() {
        return new Recurrence(Type.DAILY, null, -1);
    }

    /**
     * Creates a recurrence that occurs weekly on specific days.
     * 
     * @param days array of {@link DayOfWeek} representing the days of the week on
     *             which the recurrence should occur; must not be null, empty, or
     *             contain duplicates
     * @return a weekly recurrence
     * @throws IllegalArgumentException if the array is null, empty, or contains
     *                                  duplicates
     */
    public static Recurrence weekly(DayOfWeek... days) {
        return new Recurrence(Type.WEEKLY, days, -1);
    }

    /**
     * Creates a recurrence that occurs monthly on a specific day.
     * 
     * @param day the day of the month (1-31)
     * @return a monthly recurrence
     * @throws IllegalArgumentException if the day is not in the range 1-31
     */
    public static Recurrence monthly(int day) {
        return new Recurrence(Type.MONTHLY, null, AssertUtil.requireRange(day, 1, 31, "day"));
    }

    private Recurrence(Type type, DayOfWeek[] daysOfWeek, int dayInMonth) {
        this.type = type;

        if ((daysOfWeek == null) || (daysOfWeek.length == 0)) {
            throw new IllegalArgumentException("Days array cannot be null or empty");
        }

        Set<DayOfWeek> daySet = new HashSet<>(Arrays.asList(daysOfWeek));
        if (daySet.size() != daysOfWeek.length) {
            throw new IllegalArgumentException("Duplicate DayOfWeek values are not allowed");
        }

        this.daysInWeek = Set.copyOf(daySet); // unmodifiable set
        this.dayInMonth = dayInMonth;
    }

    /**
     * Checks if the recurrence is daily.
     * 
     * @return true if this recurrence is daily, false otherwise
     */
    public boolean isDaily() {
        return (type == Type.DAILY);
    }

    /**
     * Checks if the recurrence is weekly.
     * 
     * @return true if this recurrence is weekly, false otherwise
     */
    public boolean isWeekly() {
        return (type == Type.WEEKLY);
    }

    /**
     * Checks if the recurrence is monthly.
     * 
     * @return true if this recurrence is monthly, false otherwise
     */
    public boolean isMonthly() {
        return (type == Type.MONTHLY);
    }

    /**
     * Returns the set of days in the week for weekly recurrence.
     * <p>
     * Only valid if {@link #isWeekly()} returns true; otherwise, returns null. The
     * returned set is unmodifiable.
     *
     * @return an unmodifiable set of {@link DayOfWeek}, or null if not weekly
     */
    public final Set<DayOfWeek> getDayInWeek() {
        return daysInWeek;
    }

    /**
     * Returns the day of the month for monthly recurrence.
     * <p>
     * Only valid if {@link #isMonthly()} returns true; otherwise, returns -1.
     *
     * @return the day of the month (1-31) or -1 if not monthly
     */
    public final int getDayInMonth() {
        return dayInMonth;
    }
}
