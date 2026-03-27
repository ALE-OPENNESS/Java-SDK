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

/**
 * Provides classes and types to define and manage CCD pilot calendars.
 * <p>
 * This package contains classes to represent both normal and exceptional
 * calendars, time transitions, and the operating modes of CCD pilots.
 * Calendars define the behavior of a pilot based on time slots, allowing
 * different rules and modes to be applied throughout the week or on special dates.
 * <p>
 * Key classes include:
 * <ul>
 *   <li>{@link NormalCalendar} - Represents the standard weekly schedule with up to ten time slots per day.</li>
 *   <li>{@link ExceptionCalendar} - Represents special days that override the normal calendar (e.g., holidays or exceptional days off).</li>
 *   <li>{@link Transition} - Defines a single time slot in a calendar, including the start time, pilot operating mode, and associated rule.</li>
 *   <li>{@link Calendar} - Combines normal and exceptional calendars into a single object for managing pilot behavior.</li>
 * </ul>
 * <p>
 * A time slot is defined by a {@link com.ale.o2g.types.ccm.calendar.Transition.Time Transition.Time} object representing the start time, and a {@link com.ale.o2g.types.ccm.calendar.Transition.PilotOperatingMode Transition.PilotOperatingMode} indicating how the CCD pilot operates during that slot.
 * Exceptional days, if defined, replace the normal calendar configuration for that day.
 */
package com.ale.o2g.types.ccm.calendar;
