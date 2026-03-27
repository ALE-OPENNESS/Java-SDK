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
package com.ale.o2g;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Date;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.ccm.Pilot;
import com.ale.o2g.types.ccm.calendar.Calendar;
import com.ale.o2g.types.ccm.calendar.ExceptionCalendar;
import com.ale.o2g.types.ccm.calendar.NormalCalendar;
import com.ale.o2g.types.ccm.calendar.Transition;

/**
 * The {@code CallCenterManagementService} provides operations to configure 
 * and manage CCD (Call Center Distribution) pilots and their associated calendars.
 * <p>
 * Usage of this service requires a <b>CONTACTCENTER_SERVICE</b> license in CAPEX mode, 
 * or 40 api-tel-f subscriptions in OPEX mode (Purple On Demand).
 * <p>
 * Each CCD pilot has two types of calendars:
 * <ul>
 *   <li><b>NormalCalendar:</b> Defines standard pilot behavior for each day of the week.
 *       Each day can have up to 10 transitions (time slots).</li>
 *   <li><b>ExceptionCalendar:</b> Defines special days or overrides that do not follow 
 *       the normal calendar (e.g., holidays, exceptional days off). Each exceptional 
 *       day can also have up to 10 transitions.</li>
 * </ul>
 * <p>
 * A {@link Transition} represents a time slot in a calendar, including the start time, 
 * the pilot rule to apply, and the pilot operating mode.
 */
public interface CallCenterManagementService extends IService {

    /**
     * Returns all CCD pilots configured on the specified node.
     * 
     * @param nodeId the OmniPCX Enterprise node identifier
     * @return a collection of {@link Pilot} objects, or {@code null} if no pilots are configured
     */
    Collection<Pilot> getPilots(int nodeId);

    /**
     * Returns the CCD pilot with the specified directory number.
     * 
     * @param nodeId      the OmniPCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @return the {@link Pilot}, or {@code null} if not found
     */
    Pilot getPilot(int nodeId, String pilotNumber);

    /**
     * Returns the calendar associated with the specified CCD pilot.
     * 
     * @param nodeId      the OmniPCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @return the {@link Calendar} of the pilot, or {@code null} if not found
     */
    Calendar getCalendar(int nodeId, String pilotNumber);

    /**
     * Returns the exceptional calendar for the specified CCD pilot.
     * 
     * @param nodeId      the OmniPCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @return the {@link ExceptionCalendar}, or {@code null} if not found
     */
    ExceptionCalendar getExceptionCalendar(int nodeId, String pilotNumber);

    /**
     * Adds a new transition to the exceptional calendar of the specified CCD pilot.
     * Up to 10 transitions can be defined per exceptional day.
     * 
     * @param nodeId      the OmniPCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @param date        the exceptional day
     * @param transition  the {@link Transition} to add
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean addExceptionTransition(int nodeId, String pilotNumber, Date date, Transition transition);

    /**
     * Deletes a transition from the exceptional calendar of the specified CCD pilot.
     * The transition is identified by its zero-based index in the list of transitions for the day.
     * 
     * @param nodeId          the OmniPCX Enterprise node identifier
     * @param pilotNumber     the pilot directory number
     * @param date            the exceptional day
     * @param transitionIndex the zero-based index of the transition to remove
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean deleteExceptionTransition(int nodeId, String pilotNumber, Date date, int transitionIndex);

    /**
     * Modifies a transition in the exceptional calendar of the specified CCD pilot.
     * The transition is identified by its zero-based index in the list of transitions for the day.
     * 
     * @param nodeId          the OmniPCX Enterprise node identifier
     * @param pilotNumber     the pilot directory number
     * @param date            the exceptional day
     * @param transitionIndex the zero-based index of the transition to modify
     * @param transition      the new {@link Transition} value
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean setExceptionTransition(int nodeId, String pilotNumber, Date date, int transitionIndex,
            Transition transition);

    /**
     * Returns the normal calendar for the specified CCD pilot.
     * 
     * @param nodeId      the OmniPCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @return the {@link NormalCalendar}, or {@code null} if not found
     */
    NormalCalendar getNormalCalendar(int nodeId, String pilotNumber);

    /**
     * Adds a new transition to the normal calendar of the specified CCD pilot.
     * Up to 10 transitions can be defined per day.
     * 
     * @param nodeId      the OmniPCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @param day         the {@link DayOfWeek} to which the transition applies
     * @param transition  the {@link Transition} to add
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean addNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, Transition transition);

    /**
     * Deletes a transition from the normal calendar of the specified CCD pilot.
     * The transition is identified by its zero-based index in the list of transitions for the day.
     * 
     * @param nodeId          the OmniPCX Enterprise node identifier
     * @param pilotNumber     the pilot directory number
     * @param day             the {@link DayOfWeek} from which to remove the transition
     * @param transitionIndex the zero-based index of the transition to remove
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean deleteNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, int transitionIndex);

    /**
     * Modifies a transition in the normal calendar of the specified CCD pilot.
     * The transition is identified by its zero-based index in the list of transitions for the day.
     * 
     * @param nodeId          the OmniPCX Enterprise node identifier
     * @param pilotNumber     the pilot directory number
     * @param day             the {@link DayOfWeek} of the transition
     * @param transitionIndex the zero-based index of the transition to modify
     * @param transition      the new {@link Transition} value
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean setNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, int transitionIndex,
            Transition transition);

    /**
     * Opens the specified CCD pilot.
     * 
     * @param nodeId      the OmniPCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean openPilot(int nodeId, String pilotNumber);

    /**
     * Closes the specified CCD pilot.
     * 
     * @param nodeId      the PCX Enterprise node identifier
     * @param pilotNumber the pilot directory number
     * @return {@code true} if successful; {@code false} otherwise
     */
    boolean closePilot(int nodeId, String pilotNumber);
}
