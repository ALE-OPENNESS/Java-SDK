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
 * {@code Transition} represents a transition in a CCD pilot calendar.
 */
public class Transition {

    /**
     * {@code Time} represents the starting time of a transition in a pilot
     * calendar.
     * <p>
     * Time is in 24 hours format.
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
     * {@code PilotOperatingMode} represents the possible operating mode of a CCD
     * pilot.
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
     * Return the pilot rule number associated to this transition.
     * 
     * @return the ruleNumber
     */
    public final int getRuleNumber() {
        return ruleNumber;
    }

    /**
     * Return the mode in which the pilot is operating during this transition.
     * 
     * @return the mode
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
     * Return the start time of this transition in the day.
     * 
     * @return the time
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
