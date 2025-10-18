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

/**
 * Represents a transition in a CCD pilot calendar.
 * <p>
 * A transition defines the change of state for a CCD pilot at a specific time of day.
 * Each transition is characterized by:
 * <ul>
 *   <li>a {@link Time} indicating when the transition occurs,</li>
 *   <li>a {@link PilotOperatingMode} specifying the new operating mode for the pilot,</li>
 *   <li>a rule number identifying the CCD pilot rule being activated.</li>
 * </ul>
 * Transitions are used to build daily or weekly calendars that control
 * the behavior of CCD pilots.
 */
public class Transition {

    /**
     * Represents the starting time of a transition in a CCD pilot calendar.
     * <p>
     * Time is expressed in 24-hour format (hour and minute).
     * For example, 08:30 represents 8:30 AM, and 17:45 represents 5:45 PM.
     */
    public static class Time {
        private int hour;
        private int minute;

        /**
         * Construct a new Time with the specified hour and minute.
         * 
         * @param hour   the hours
         * @param minute the minutes
         */
        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        /**
         * Get the hours part of the time.
         * 
         * @return the hour
         */
        public final int getHour() {
            return hour;
        }

        /**
         * Get the minutes part of the time.
         * 
         * @return the minute
         */
        public final int getMinute() {
            return minute;
        }

        @Override
        public String toString() {
            return String.format("%02d:%02d", hour, minute);
        }

    }

    /**
     * Defines the possible operating modes of a CCD pilot during a transition.
     */
    public static enum PilotOperatingMode {
        /**
         * The pilot operates in normal (opened) mode.
         */
        NORMAL,

        /**
         * The pilot operates in closed mode.
         */
        CLOSED,

        /**
         * The pilot operates in forward mode.
         */
        FORWARD
    }

    private int ruleNumber;
    private PilotOperatingMode mode;
    private Time time;

    /**
     * Returns the CCD pilot rule number associated with this transition.
     *
     * @return the rule number
     */
    public final int getRuleNumber() {
        return ruleNumber;
    }

    /**
     * Returns the operating mode in which the pilot functions
     * after this transition takes effect.
     *
     * @return the operating mode
     */
    public final PilotOperatingMode getMode() {
        return mode;
    }

    /**
     * Set the pilot operating mode for this transition.
     * 
     * @param mode the mode to set
     */
    public final void setMode(PilotOperatingMode mode) {
        this.mode = mode;
    }

    /**
     * Returns the start time of this transition within the day.
     *
     * @return the transition time
     */
    public final Time getTime() {
        return time;
    }

    /**
     * Construct a new transition with the specified pilot rule, operating mode and
     * starting time.
     * 
     * @param ruleNumber the pilot rule number
     * @param mode       the operating mode
     * @param time       the starting time for this transition
     */
    public Transition(int ruleNumber, PilotOperatingMode mode, Time time) {
        this.ruleNumber = ruleNumber;
        this.mode = mode;
        this.time = time;
    }

}
