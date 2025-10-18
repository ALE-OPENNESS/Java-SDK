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

import java.time.Duration;

import com.ale.o2g.internal.util.FormatUtil;
import com.ale.o2g.types.ccstats.AgentByPilotAttributes;

/**
 * Represents a single row of statistical counters for a CCD agent in relation
 * to a specific CCD pilot.
 * <p>
 * Each {@code AgentByPilotStatisticsRow} instance contains metrics corresponding
 * to the attributes defined in {@link AgentByPilotAttributes}. These metrics
 * include counts, durations, and averages related to call handling, wrap-up,
 * transactions, pauses, and on-hold periods.
 * <p>
 * <b>Counts</b> indicate the number of calls received, served, transferred, 
 * or currently in wrap-up. <b>Durations</b> are expressed in {@code hh:mm:ss} 
 * format and cover call processing, conversation, wrap-up, transaction phase, 
 * pause, and on-hold times.
 * <p>
 * This class inherits from {@link StatisticsRow}, which provides generic 
 * access to its metrics via the associated attribute enum, allowing dynamic
 * retrieval such as:
 * <pre><code>
 * Duration duration = agentByPilotStatRow.get(AgentByPilotAttributes.convTDur).asDuration();
 * </code></pre>
 *
 * @since 2.7.4
 */
public class AgentByPilotStatisticsRow extends StatisticsRow<AgentByPilotAttributes> {

    private String pilotNumber;
    private String pilotName;

    private Integer nbCallsReceived;
    private Integer nbCallsTransfIn;
    private Integer nbCallsServed;
    private Integer nbCallsServedTooQuickly;
    private Integer nbCallsWithEnquiry;
    private Integer nbCallsWithHelp;
    private Integer nbCallsTransf;
    private Integer nbCallsTransfToAgent;
    private Integer nbCallsInWrapup;

    private String maxCallProcDur;
    private String maxConvDur;
    private String maxWrapupDur;

    private String callProcTDur;
    private String callProcADur;
    private String convTDur;
    private String convADur;
    private String wrapupTDur;
    private String wrapupADur;
    private String convInWrapupTDur;
    private String busyTimeInWrapupTDur;

    private String onHoldTDur;
    private String onHoldADur;

    private String transTDur;
    private String transADur;

    private String pauseTDur;
    private String pauseADur;


    /**
     * Returns the pilot's unique identifier or number.
     *
     * @return the pilot's number
     */
    public final String getPilotNumber() {
        return pilotNumber;
    }

    /**
     * Returns the pilot's display name.
     *
     * @return the pilot's name
     */
    public final String getPilotName() {
        return pilotName;
    }

    /**
     * Returns the total number of calls received by this pilot.
     *
     * @return the number of received calls
     */
    public final Integer getNbCallsReceived() {
        return nbCallsReceived;
    }

    /**
     * Returns the number of calls received by transfer.
     *
     * @return the number of transferred-in calls
     */
    public final Integer getNbCallsTransfIn() {
        return nbCallsTransfIn;
    }

    /**
     * Returns the total number of calls served by the pilot.
     *
     * @return the number of served calls
     */
    public final Integer getNbCallsServed() {
        return nbCallsServed;
    }

    /**
     * Returns the number of calls served too quickly.
     *
     * @return the number of calls served too quickly
     */
    public final Integer getNbCallsServedTooQuickly() {
        return nbCallsServedTooQuickly;
    }

    /**
     * Returns the number of calls that included an enquiry.
     *
     * @return the number of calls with enquiry
     */
    public final Integer getNbCallsWithEnquiry() {
        return nbCallsWithEnquiry;
    }

    /**
     * Returns the number of calls where help was requested.
     *
     * @return the number of calls with help requested
     */
    public final Integer getNbCallsWithHelp() {
        return nbCallsWithHelp;
    }

    /**
     * Returns the number of calls transferred from this pilot to others.
     *
     * @return the number of calls transferred from the agent
     */
    public final Integer getNbCallsTransf() {
        return nbCallsTransf;
    }

    /**
     * Returns the number of calls transferred to this pilot.
     *
     * @return the number of calls transferred to the agent
     */
    public final Integer getNbCallsTransfToAgent() {
        return nbCallsTransfToAgent;
    }

    /**
     * Returns the number of calls currently in wrap-up state.
     *
     * @return the number of calls in wrap-up
     */
    public final Integer getNbCallsInWrapup() {
        return nbCallsInWrapup;
    }

    /**
     * Returns the maximum call processing duration.
     *
     * @return the maximum duration of call processing 
     */
    public final Duration getMaxCallProcDur() {
        return FormatUtil.asDuration(maxCallProcDur);
    }

    /**
     * Returns the maximum conversation duration.
     *
     * @return the maximum duration of conversation 
     */
    public final Duration getMaxConvDur() {
        return FormatUtil.asDuration(maxConvDur);
    }

    /**
     * Returns the maximum wrap-up duration.
     *
     * @return the maximum duration of wrap-up 
     */
    public final Duration getMaxWrapupDur() {
        return FormatUtil.asDuration(maxWrapupDur);
    }

    /**
     * Returns the total call processing duration.
     *
     * @return total duration of call processing 
     */
    public final Duration getCallProcTDur() {
        return FormatUtil.asDuration(callProcTDur);
    }

    /**
     * Returns the average call processing duration.
     *
     * @return average duration of call processing 
     */
    public final Duration getCallProcADur() {
        return FormatUtil.asDuration(callProcADur);
    }

    /**
     * Returns the total conversation duration.
     *
     * @return total duration of conversation 
     */
    public final Duration getConvTDur() {
        return FormatUtil.asDuration(convTDur);
    }

    /**
     * Returns the average conversation duration.
     *
     * @return average duration of conversation 
     */
    public final Duration getConvADur() {
        return FormatUtil.asDuration(convADur);
    }

    /**
     * Returns the total wrap-up duration.
     *
     * @return total duration of wrap-up 
     */
    public final Duration getWrapupTDur() {
        return FormatUtil.asDuration(wrapupTDur);
    }

    /**
     * Returns the average wrap-up duration.
     *
     * @return average duration of wrap-up 
     */
    public final Duration getWrapupADur() {
        return FormatUtil.asDuration(wrapupADur);
    }

    /**
     * Returns the total conversation duration during wrap-up.
     *
     * @return total duration of conversation in wrap-up 
     */
    public final Duration getConvInWrapupTDur() {
        return FormatUtil.asDuration(convInWrapupTDur);
    }

    /**
     * Returns the total busy time during wrap-up.
     *
     * @return total busy duration in wrap-up 
     */
    public final Duration getBusyTimeInWrapupTDur() {
        return FormatUtil.asDuration(busyTimeInWrapupTDur);
    }

    /**
     * Returns the total duration of calls on hold.
     *
     * @return total duration of hold calls 
     */
    public final Duration getOnHoldTDur() {
        return FormatUtil.asDuration(onHoldTDur);
    }

    /**
     * Returns the average duration of calls on hold.
     *
     * @return average duration of hold calls 
     */
    public final Duration getOnHoldADur() {
        return FormatUtil.asDuration(onHoldADur);
    }

    /**
     * Returns the total duration of the transaction phase.
     *
     * @return total duration of transactions 
     */
    public final Duration getTransTDur() {
        return FormatUtil.asDuration(transTDur);
    }

    /**
     * Returns the average duration of the transaction phase.
     *
     * @return average duration of transactions 
     */
    public final Duration getTransADur() {
        return FormatUtil.asDuration(transADur);
    }

    /**
     * Returns the total duration of pause.
     *
     * @return total duration of pause 
     */
    public final Duration getPauseTDur() {
        return FormatUtil.asDuration(pauseTDur);
    }

    /**
     * Returns the average duration of pause.
     *
     * @return average duration of pause 
     */
    public final Duration getPauseADur() {
        return FormatUtil.asDuration(pauseADur);
    }
}
