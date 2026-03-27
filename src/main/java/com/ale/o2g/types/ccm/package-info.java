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
 * Provides types and classes for managing CCD pilots and their behavior.
 * <p>
 * This package contains core types to represent CCD pilots, their associated
 * rules, and calendar schedules that define pilot behavior over time.
 * <p>
 * Key classes include:
 * <ul>
 *   <li>{@link Pilot} - Represents a CCD pilot, including its number, name,
 *       service state, waiting time, queue saturation, and transfer capabilities.</li>
 *   <li>{@link PilotRule} - Represents a single CCD pilot rule, including
 *       its identifier, name, and activation status.</li>
 *   <li>{@link PilotRuleSet} - Represents a set of CCD pilot rules, allowing
 *       retrieval and inspection of rules by number.</li>
 * </ul>
 * <p>
 * The {@code com.ale.o2g.types.ccm.calendar} subpackage provides classes to
 * define both normal and exceptional calendars ({@link com.ale.o2g.types.ccm.calendar.Calendar Calendar}),
 * and transitions ({@link com.ale.o2g.types.ccm.calendar.Transition Transition}) that specify
 * pilot operating modes and rules for each time slot.
 */
package com.ale.o2g.types.ccm;
