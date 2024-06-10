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

import java.util.Collection;
import java.util.Date;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.ccm.Pilot;
import com.ale.o2g.types.ccm.calendar.Calendar;
import com.ale.o2g.types.ccm.calendar.DayOfWeek;
import com.ale.o2g.types.ccm.calendar.ExceptionCalendar;
import com.ale.o2g.types.ccm.calendar.NormalCalendar;
import com.ale.o2g.types.ccm.calendar.Transition;

/**
 * The {@code CallCenterManagementService} allows a user configure CCD Pilot
 * distribution mode.
 * <p>
 * Using this service requires having a <b>CONTACTCENTER_SERVICE</b> license in
 * CAPEX mode, or 40 api-tel-f subscription in OPEX mode (Purple On Demand).
 */
public interface CallCenterManagementService extends IService {

    /**
     * Return the CCD Pilot configured on the specified node.
     * 
     * @param nodeId the PCX Enterprise node id
     * @return the collection of Pilots or null if there is no CCD pilot configured
     *         on this node.
     */
    Collection<Pilot> getPilots(int nodeId);

    /**
     * Return the CCD pilot with the specified directory number.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @return the pilot or null if there is no pilot with this directory number on
     *         this node.
     */
    Pilot getPilot(int nodeId, String pilotNumber);

    /**
     * Return the calendar associated to the specified CCD pilot.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @return the pilot associated calendar
     */
    Calendar getCalendar(int nodeId, String pilotNumber);

    /**
     * Return the exceptional calendar associated to the specified CCD pilot.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @return the pilot associated exceptional calendar
     */
    ExceptionCalendar getExceptionCalendar(int nodeId, String pilotNumber);

    /**
     * Add a new transition to the specified CCD pilot exceptional calendar.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @param date        the date of the exceptional day to which this transition
     *                    applies. It is possible to create up to ten transitions
     *                    per day.
     * @param transition  the transition to add
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean addExceptionTransition(int nodeId, String pilotNumber, Date date, Transition transition);

    /**
     * Delete the specified transition from the CCD pilot exceptional calendar. The
     * transition is defined by its index in the list of transition for the
     * specified date.
     * 
     * @param nodeId          the PCX Enterprise node id
     * @param pilotNumber     the pilot directory number
     * @param date            the date from which the transition will be removed
     * @param transitionIndex the index of the transition to remove in the list of
     *                        transition for the specified date
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteExceptionTransition(int nodeId, String pilotNumber, Date date, int transitionIndex);

    /**
     * Modify the specified transition in the CCD pilot exceptional calendar. The
     * transition is defined by its index in the list of transition for the
     * specified date.
     * 
     * @param nodeId          the PCX Enterprise node id
     * @param pilotNumber     the pilot directory number
     * @param date            the date in which the transition modification applies
     * @param transitionIndex the index of the transition to modify in the list of
     *                        transition for the specified date
     * @param transition      the new transition value
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setExceptionTransition(int nodeId, String pilotNumber, Date date, int transitionIndex,
            Transition transition);

    /**
     * Return the normal calendar associated to the specified CCD pilot.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @return the pilot associated normal calendar
     */
    NormalCalendar getNormalCalendar(int nodeId, String pilotNumber);

    /**
     * Add a new transition to the specified CCD pilot normal calendar.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @param day         the day to which this transition applies. It is possible
     *                    to create up to ten transitions per day.
     * @param transition  the transition to add
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean addNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, Transition transition);

    /**
     * Delete the specified transition from the CCD pilot normal calendar. The
     * transition is defined by its index in the list of transition for the
     * specified day.
     * 
     * @param nodeId          the PCX Enterprise node id
     * @param pilotNumber     the pilot directory number
     * @param day             the day from which the transition will be removed
     * @param transitionIndex the index of the transition to remove in the list of
     *                        transition for the specified day
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, int transitionIndex);

    /**
     * Modify the specified transition in the CCD pilot normal calendar. The
     * transition is defined by its index in the list of transition for the
     * specified day.
     * 
     * @param nodeId          the PCX Enterprise node id
     * @param pilotNumber     the pilot directory number
     * @param day             the day in which the transition modification applies
     * @param transitionIndex the index of the transition to modify in the list of
     *                        transition for the specified day
     * @param transition      the new transition value
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, int transitionIndex,
            Transition transition);

    /**
     * Open the specified CCD pilot.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean openPilot(int nodeId, String pilotNumber);

    /**
     * Close the specified CCD pilot.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot directory number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean closePilot(int nodeId, String pilotNumber);
}
