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
package com.ale.o2g.types.ccm.calendar;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code NormalCalendar} class represents the normal calendar associated to a
 * CCD pilot.
 * <p>
 * The normal calendar define the pilot behavior depending on a time slot. It is
 * possible to define ten time slots per day and to configure all the days in a
 * week, for a total of up to 70 time slots per week.
 */
public class NormalCalendar extends AbstractCalendar<DayOfWeek> {

    /**
     * Get the set of days of week in this calendar.
     * 
     * @return a set of days of week
     */
    public Set<DayOfWeek> getDays() {
        return transitions.keySet();
    }

    /**
     * Get the transition at the specified index for the specified day of week.
     * 
     * @param day   the day of week
     * @param index the transition index
     * @return The transition
     */
    public Transition getTransitionAt(DayOfWeek day, int index) {
        return super._getItemAt(day, index);
    }

    /**
     * Return the list of transition for the specified day.
     * 
     * @param day the dat
     * @return A list of transition
     */
    public List<Transition> getTransitions(DayOfWeek day) {
        return super._getTransitions(day);
    }

    protected NormalCalendar(Map<DayOfWeek, List<Transition>> transitions) {
        super(transitions);
    }
}
