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
 * {@code Calendar} class represents a calendar associated to a CCD pilot.
 * <p>
 * A calendar is composed of two parts, the normal calendar and the exceptional
 * calendar.
 * <h2>Normal calendar:</h2>The normal calendar define the pilot behavior
 * depending on a time slot. It is possible to define ten time slots per day and
 * to configure all the days in a week, for a total of up to 70 time slots per
 * week.
 * <h2>Exception calendar:</h2>The exceptional calendar defines special days
 * that don't follow the configuration of the normal calendar. Exceptional day
 * off for exemple. If a exceptional day is configured, it replaces the
 * configuration of the normal calendar. It is possible to define ten time slots
 * per exceptional day.
 * <h2>Transition</h2>A transition define a time slot.It is define by the start
 * time of the time slot. This start time is the hour:minut since the begining
 * of the day (00:00). A time slot defines the CCD pilot rule to apply and the
 * operating mode.
 */
public class Calendar {

    private NormalCalendar normalDays;
    private ExceptionCalendar exceptionDays;

    /**
     * Return the normal calendar. The normal calendar is a set of transitions per
     * days of week
     * 
     * @return the normalDays
     */
    public final NormalCalendar getNormalDays() {
        return normalDays;
    }

    /**
     * Return the exceptional calendar. It's a collection of days with the
     * associated transitions.
     * 
     * @return the exceptionDays
     */
    public final ExceptionCalendar getExceptionDays() {
        return exceptionDays;
    }

    protected Calendar(NormalCalendar normalDays, ExceptionCalendar exceptionDays) {
        this.normalDays = normalDays;
        this.exceptionDays = exceptionDays;
    }
}
