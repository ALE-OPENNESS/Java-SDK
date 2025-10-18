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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code ExceptionCalendar} represents the exceptional calendar associated with a CCD pilot.
 * <p>
 * The exceptional calendar defines special days that do not follow the configuration of the
 * normal calendar, such as holidays or other exceptions. Each exceptional day can have up to
 * ten time slots.
 * <p>
 * Typically, an {@code ExceptionCalendar} object is retrieved or modified via the
 * {@link com.ale.o2g.CallCenterManagementService#getExceptionCalendar(int, String) CallCenterManagementService#getExceptionCalendar(int, String)},
 * {@link com.ale.o2g.CallCenterManagementService#addExceptionTransition(int, String, Date, Transition) CallCenterManagementService#addExceptionTransition(int, String, Date, Transition)},
 * {@link com.ale.o2g.CallCenterManagementService#setExceptionTransition(int, String, Date, int, Transition) CallCenterManagementService#setExceptionTransition(int, String, Date, int, Transition)},
 * and {@link com.ale.o2g.CallCenterManagementService#deleteExceptionTransition(int, String, Date, int) CallCenterManagementService#deleteExceptionTransition(int, String, Date, int)}
 * methods.
 */
public class ExceptionCalendar extends AbstractCalendar<Date> {

    /**
     * Get the set of exceptional dates in this calendar.
     * 
     * @return a set of date
     */
    public Set<Date> getExceptionDates() {
        return transitions.keySet();
    }

    /**
     * Get the transition at the specified index for the specified date.
     * 
     * @param date  the date of the exceptional day
     * @param index the transition index
     * @return The transition
     */
    public Transition getTransitionAt(Date date, int index) {
        return super._getItemAt(date, index);
    }

    /**
     * Return the list of transition for the specified date.
     * 
     * @param date the date
     * @return A list of transition
     */
    public List<Transition> getTransitions(Date date) {
        return super._getTransitions(date);
    }

    protected ExceptionCalendar(Map<Date, List<Transition>> transitions) {
        super(transitions);
    }
}
