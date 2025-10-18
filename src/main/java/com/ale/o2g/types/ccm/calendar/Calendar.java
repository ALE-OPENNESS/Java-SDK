/*
* Copyright 2024 ALE International
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
package com.ale.o2g.types.ccm.calendar;

/**
 * {@code Calendar} represents a calendar associated with a CCD pilot.
 * <p>
 * A calendar consists of two components: the normal calendar and the exceptional calendar.
 * 
 * <h2>Normal calendar</h2>
 * The normal calendar defines the pilot's behavior for each time slot. Up to ten time slots
 * can be defined per day, and all days of the week can be configured, for a maximum of
 * 70 time slots per week.
 * 
 * <h2>Exceptional calendar</h2>
 * The exceptional calendar defines special days that do not follow the normal calendar
 * configuration, such as holidays or other exceptions. If an exceptional day is configured,
 * it overrides the corresponding day in the normal calendar. Each exceptional day can have
 * up to ten time slots.
 * 
 * <h2>Transition</h2>
 * A transition represents a time slot and is defined by its start time (hour and minute
 * since the beginning of the day, 00:00). Each time slot specifies the CCD pilot rule to apply
 * and the operating mode during that slot.
 * 
 * <p>
 * Typically, a {@code Calendar} object is retrieved via the {@link com.ale.o2g.CallCenterManagementService#getCalendar(int, String) CallCenterManagementService#getCalendar(int, String)}
 * or {@link com.ale.o2g.CallCenterManagementService#getNormalCalendar(int, String) CallCenterManagementService#getNormalCalendar(int, String)}
 * and {@link com.ale.o2g.CallCenterManagementService#getExceptionCalendar(int, String) CallCenterManagementService#getExceptionCalendar(int, String)} methods.
 */
public class Calendar {

    private NormalCalendar normalDays;
    private ExceptionCalendar exceptionDays;

    /**
     * Returns the normal calendar, which is a set of transitions organized by days of the week.
     * 
     * @return the normal calendar
     */
    public final NormalCalendar getNormalDays() {
        return normalDays;
    }

    /**
     * Returns the exceptional calendar, which is a collection of specific dates
     * with their associated transitions.
     * 
     * @return the exceptional calendar
     */
    public final ExceptionCalendar getExceptionDays() {
        return exceptionDays;
    }

    /**
     * Constructs a new calendar with the specified normal and exceptional calendars.
     * 
     * @param normalDays the normal calendar
     * @param exceptionDays the exceptional calendar
     */
    protected Calendar(NormalCalendar normalDays, ExceptionCalendar exceptionDays) {
        this.normalDays = normalDays;
        this.exceptionDays = exceptionDays;
    }
}
