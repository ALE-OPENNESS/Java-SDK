/*
* Copyright 2025 ALE International
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
package com.ale.o2g.types.ccstats.data;

import java.time.LocalDateTime;

import com.ale.o2g.types.ccstats.PilotAbandonedCallsAttributes;

/**
 * Represents a single row of abandoned call statistics for a CCD pilot.
 * <p>
 * Each row corresponds to one abandoned call event and provides the caller's
 * waiting time, the pilot and queue involved, and a set of boolean flags
 * indicating at which point in the call flow the caller hung up.
 * <p>
 * Boolean flag fields follow the O2G convention: {@code true} when the value
 * is {@code 1}, absent (treated as {@code false}) when the field is not present
 * in the response.
 * <p>
 * Rows are returned as part of a {@code StatisticsData} object via
 * {@code CallCenterStatisticsService.getDayData} or
 * {@code CallCenterStatisticsService.getDaysData}.
 *
 * @since 2.7.4
 */
public class PilotAbandonedCallsStatisticsRow extends StatisticsRow<PilotAbandonedCallsAttributes> {

    private String date;
    @SuppressWarnings("unused")
    private String year;
    @SuppressWarnings("unused")
    private String month;
    @SuppressWarnings("unused")
    private String day;
    @SuppressWarnings("unused")
    private String hour;
    @SuppressWarnings("unused")
    private String minute;
    private String queueName;
    private Integer waitingTime;
    private String pilotName;
    private String pilotNumber;
    private String callerNumber;

    private Integer abandonedOnGreetingVG;
    private Integer abandonedOn1stWaitingVG;
    private Integer abandonedOn2ndWaitingVG;
    private Integer abandonedOn3rdWaitingVG;
    private Integer abandonedOn4thWaitingVG;
    private Integer abandonedOn5hWaitingVG;
    private Integer abandonedOn6thWaitingVG;
    private Integer abandonedOnRinging;
    private Integer abandonedOnGeneralFwdVG;
    private Integer abandonedOnBlockedVG;
    private Integer abandonedOnDirectCallWaiting;

    
    private boolean booleanValue(Integer value) {
    	if (value == null) return false;
    	if (value == 0) return false;
    	return true;
    }
    
    /**
     * Returns the timestamp associated with this statistics row.
     * <p>
     * The returned value represents the date and time at which the statistics were
     * collected, as a {@link LocalDateTime}.
     *
     * @return the {@link LocalDateTime} representing when this statistics entry was
     *         recorded
     */
    public final LocalDateTime getDate() {
        return asLocalDateTime(date);
    }

    /**
     * Returns the name of the queue associated with this abandoned call.
     *
     * @return the queue name, or {@code null} if not set
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * Returns the name of the pilot on which the call was abandoned.
     *
     * @return the pilot name, or {@code null} if not set
     */
    public String getPilotName() {
        return pilotName;
    }

    /**
     * Returns the phone number of the pilot on which the call was abandoned.
     *
     * @return the pilot number, or {@code null} if not set
     */
    public String getPilotNumber() {
        return pilotNumber;
    }

    /**
     * Returns the phone number of the caller who abandoned the call.
     *
     * @return the caller's phone number, or {@code null} if not set
     */
    public String getCallerNumber() {
        return callerNumber;
    }

    /**
     * Returns the total time in seconds the caller waited before hanging up.
     *
     * @return the waiting time in seconds, or {@code null} if not set
     */
    public Integer getWaitingTime() {
        return waitingTime;
    }

    /**
     * Returns whether the caller abandoned during the greeting voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the greeting voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOnGreetingVG() {
        return booleanValue(this.abandonedOnGreetingVG);
    }

    /**
     * Returns whether the caller abandoned during the 1st waiting voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the 1st waiting voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOn1stWaitingVG() {
        return booleanValue(this.abandonedOn1stWaitingVG);
    }

    /**
     * Returns whether the caller abandoned during the 2nd waiting voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the 2nd waiting voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOn2ndWaitingVG() {
        return booleanValue(this.abandonedOn2ndWaitingVG);
    }

    /**
     * Returns whether the caller abandoned during the 3rd waiting voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the 3rd waiting voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOn3rdWaitingVG() {
        return booleanValue(this.abandonedOn3rdWaitingVG);
    }

    /**
     * Returns whether the caller abandoned during the 4th waiting voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the 4th waiting voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOn4thWaitingVG() {
        return booleanValue(this.abandonedOn4thWaitingVG);
    }

    /**
     * Returns whether the caller abandoned during the 5th waiting voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the 5th waiting voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOn5hWaitingVG() {
        return booleanValue(this.abandonedOn5hWaitingVG);
    }

    /**
     * Returns whether the caller abandoned during the 6th waiting voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the 6th waiting voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOn6thWaitingVG() {
        return booleanValue(this.abandonedOn6thWaitingVG);
    }

    /**
     * Returns whether the caller abandoned while ringing an agent.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on ringing;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOnRinging() {
        return booleanValue(this.abandonedOnRinging);
    }

    /**
     * Returns whether the caller abandoned during the general forwarding voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the general forwarding voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOnGeneralFwdVG() {
        return booleanValue(this.abandonedOnGeneralFwdVG);
    }

    /**
     * Returns whether the caller abandoned during the blocked voice guide.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on the blocked voice guide;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOnBlockedVG() {
        return booleanValue(this.abandonedOnBlockedVG);
    }

    /**
     * Returns whether the caller abandoned while waiting on a direct call to a busy agent.
     * <p>
     * Returns {@code false} when the field is absent from the response.
     *
     * @return {@code true} if the caller abandoned on direct call waiting;
     *         {@code false} otherwise
     */
    public boolean isAbandonedOnDirectCallWaiting() {
        return booleanValue(this.abandonedOnDirectCallWaiting );
    }

    protected PilotAbandonedCallsStatisticsRow() {
    }
}