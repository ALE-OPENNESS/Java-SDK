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
 * {@code ExceptionCalendar} class represents the exceptional calendar
 * associated to a CCD pilot.
 * <p>
 * The exceptional calendar defines special days that don't follow the
 * configuration of the normal calendar. Exceptional day off for exemple. It is
 * possible to define ten time slots per exceptional day.
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
