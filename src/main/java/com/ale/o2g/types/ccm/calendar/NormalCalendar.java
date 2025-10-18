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

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * {@code NormalCalendar} represents the normal calendar associated with a CCD pilot.
 * <p>
 * The normal calendar defines the pilot's behavior based on time slots. Up to ten time slots
 * can be configured per day across all days of the week, for a maximum of 70 time slots per week.
 * <p>
 * Typically, a {@code NormalCalendar} object is retrieved or modified via the
 * {@link com.ale.o2g.CallCenterManagementService#getNormalCalendar(int, String) CallCenterManagementService#getNormalCalendar(int, String)},
 * {@link com.ale.o2g.CallCenterManagementService#addNormalTransition(int, String, DayOfWeek, Transition) CallCenterManagementService#addNormalTransition(int, String, DayOfWeek, Transition)},
 * {@link com.ale.o2g.CallCenterManagementService#setNormalTransition(int, String, DayOfWeek, int, Transition) CallCenterManagementService#setNormalTransition(int, String, DayOfWeek, int, Transition)},
 * and {@link com.ale.o2g.CallCenterManagementService#deleteNormalTransition(int, String, DayOfWeek, int) CallCenterManagementService#deleteNormalTransition(int, String, DayOfWeek, int)}
 * methods.
 */
public class NormalCalendar extends AbstractCalendar<DayOfWeek> {

    /**
     * Returns the set of days of the week defined in this calendar.
     * 
     * @return the set of days of the week
     */
    public Set<DayOfWeek> getDays() {
        return transitions.keySet();
    }

    /**
     * Returns the transition at the specified index for the given day of the week.
     * 
     * @param day   the day of the week
     * @param index the transition index
     * @return the transition
     */
    public Transition getTransitionAt(DayOfWeek day, int index) {
        return super._getItemAt(day, index);
    }

    /**
     * Returns the list of transitions for the given day.
     * 
     * @param day the day of the week
     * @return the list of transitions
     */
    public List<Transition> getTransitions(DayOfWeek day) {
        return super._getTransitions(day);
    }

    /**
     * Creates a new normal calendar with the specified transitions.
     * 
     * @param transitions the mapping of days of the week to their transitions
     */
    protected NormalCalendar(Map<DayOfWeek, List<Transition>> transitions) {
        super(transitions);
    }
}
