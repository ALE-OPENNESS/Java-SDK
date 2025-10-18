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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ale.o2g.internal.util.FormatUtil;
import com.ale.o2g.types.ccstats.AgentAttributes;

/**
 * Represents a single row of statistical counters for a CCD (Call Center Distribution) agent.
 * <p>
 * Each {@code AgentStatisticsRow} instance contains metrics corresponding to the 
 * attributes defined in {@link AgentAttributes}. These metrics include counts, 
 * durations, and averages related to call handling, wrap-up, transactions, pauses, 
 * and on-hold periods.
 * <p>
 * <b>Counts</b> indicate the number of calls received, served, transferred, or 
 * currently in wrap-up. <b>Durations</b> are expressed in {@code hh:mm:ss} format 
 * and cover call processing, conversation, wrap-up, transaction phase, pause, 
 * and on-hold times.
 * <p>
 * For statistics broken down by pilot, see {@link AgentByPilotStatisticsRow}.
 * <p>
 * This class inherits from {@link StatisticsRow}, which provides generic access 
 * to its metrics via the associated attribute enum, allowing dynamic retrieval such as:
 * <pre><code>
 * Duration duration = agentStatRow.get(AgentAttributes.convTDur).asDuration();
 * </code></pre>
 *
 * @since 2.7.4
 */
public class AgentStatisticsRow extends StatisticsRow<AgentAttributes> {

    private static final DateTimeFormatter DATE_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
    private String login;
    private String operator;
    private String firstName;
    private String lastName;
    private String number;
    private String group;

    private Integer nbRotating;
    private Integer nbPickedUp;
    private Integer nbPickup;
    private Integer nbLocalOutNonAcd;
    private Integer nbExtOutNonacd;
    private Integer nbRingAcd;
    private Integer nbHelp;
    private Integer nbLocInNonacd;
    private Integer nbExtInNonacdDirect;
    private Integer nbExtInNonacdTransferred;
    private Integer nbServedWOCode;
    private Integer nbServedWCode;
    private Integer nbAcdQuickServed;
    private Integer nbExtinNonacdServed;
    private Integer nbExtinNonacdQuickServed;
    private Integer nbOutAcd;
    private Integer nbOutAcdAnswered;
    private Integer nbOnWrapup;

    private String ringAcdServedTDur;
    private String ringAcdServedADur;
    private String ringInNonAcdExtServedTDur;
    private String ringInNonAcdExtServedADur;
    private String ringAcdTDur;
    private String ringAcdADur;
    private String ringInNonAcdExtTDur;
    private String ringInNonAcdExtADur;
    private String ringTDur;
    private String ringADur;
    private String convAcdTDur;
    private String convAcdADur;
    private String wrapupAcdTDur;
    private String convLocOutNonacdTDur;
    private String convLocOutNonacdADur;
    private String convExtOutTDur;
    private String convExtOutADur;
    private String convLocInNonacdTDur;
    private String convLocInNonacdADur;
    private String convExtInNonacdTDur;
    private String convExtInNonacdADur;
    private String outAcdCommTDur;
    private String outAcdCommADur;
    private String outAcdConvTDur;
    private String outAcdConvADur;
    private String outAcdTransactTDur;
    private String outAcdTransactADur;
    private String outAcdWrapupTDur;
    private String outAcdWrapupADur;
    private String outAcdPauseTDur;
    private String outAcdPauseADur;
    private String wrapUpIdleTDur;
    private String callOnWrapupTDur;
    private String busyOnWrapupTDur;
    private String busyTDur;
    private String loggedOutPerTime;
    private String notAssignedPerTime;
    private String assignedPerTime;
    private String withdrawPerTime;
    private String withdrawPerTimeCause1;
    private String withdrawPerTimeCause2;
    private String withdrawPerTimeCause3;
    private String withdrawPerTimeCause4;
    private String withdrawPerTimeCause5;
    private String withdrawPerTimeCause6;
    private String withdrawPerTimeCause7;
    private String withdrawPerTimeCause8;
    private String withdrawPerTimeCause9;

    private Integer nbPilots;
    private Integer nbAcdServedCalls;
    private Integer nbAcdInServedCalls;
    private Integer nbInCallsReceivedByPilot;
    private Integer nbAcdOutServedCalls;
    private Integer nbTotNonServedCalls;
    private Integer nbInNonServedCalls;
    private Integer nbPickedupCalls;
    private Integer nbRefusedCalls;
    private Integer nbAcdOutNonServedCalls;
    private Integer nbTotNonAcdreceivedCalls;
    private Integer nbInNonAcdCalls;
    private Integer nbOutNonAcdCalls;

    private String assignedNotWithdrawDur;
    private String withdrawDur;
    private String manuWrapupDur;
    private String unreachableDur;
    private String nonAcdWorkTDur;
    private String nonAcdWorkADur;
    private String acdWorkTDur;
    private String acdWorkADur;
    private String acdWorkInTDur;
    private String acdWorkInADur;
    private String acdWorkInConvTDur;
    private String acdWorkInConvADur;
    private String acdWorkInRingTDur;
    private String acdWorkInRingADur;
    private String acdWorkInWrapupTDur;
    private String acdWorkInWrapupADur;
    private String acdWorkOutTDur;
    private String acdWorkOutADur;
    private String acdWorkOutConvTDur;
    private String acdWorkOutConvADur;
    private String acdWorkOutWrapupTDur;
    private String acdWorkOutWrapupADur;
    private String acdInConvTDur;
    private String acdInConvADur;
    private String acdOutConvTDur;
    private String acdOutConvADur;

    private List<AgentByPilotStatisticsRow> pilotAgentStatsRows;

    /**
     * Returns the timestamp associated with this statistics row.
     * <p>
     * The returned value represents the date and time at which the statistics were
     * collected, as a {@link LocalDateTime}.
     *
     * @return the {@link LocalDateTime} representing when this statistics entry was recorded
     */
    public final LocalDateTime getDate() {
        return LocalDateTime.parse(date, DATE_FORMATER);
    }

    /**
     * Returns the login name of the agent associated with this statistics row.
     *
     * @return the agent's login name
     */
    public final String getLogin() {
        return login;
    }

    /**
     * Returns the operator of the agent.
     *
     * @return the operator as a {@link String}
     */
    public final String getOperator() {
        return operator;
    }

    /**
     * Returns the first name of the agent.
     *
     * @return the agent's first name
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the agent.
     *
     * @return the agent's last name
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * Returns the directory number associated with the agent.
     *
     * @return the agent's directory number
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Returns the directory number of the group the agent is logged in.
     *
     * @return the agent's group directory number
     */
    public final String getGroup() {
        return group;
    }

    /**
     * Returns the number of rotating time-outs that occurred for this agent.
     * <p>
     * A rotating time-out typically indicates that an incoming call was offered to the
     * agent but not answered within the timeout period, and then rotated to another agent.
     *
     * @return the count of rotating time-outs
     */
    public final Integer getNbRotating() {
        return nbRotating;
    }

    /**
     * Returns the number of calls that were picked up by the agent.
     *
     * @return the number of calls picked up by the agent
     */
    public final Integer getNbPickedUp() {
        return nbPickedUp;
    }

    /**
     * Returns the number of pickup actions performed by the agent.
     * <p>
     * This represents calls the agent retrieved from ringing groups or colleagues
     * using the call pickup feature.
     *
     * @return the number of pickup actions performed
     */
    public final Integer getNbPickup() {
        return nbPickup;
    }

    /**
     * Returns the number of internal outgoing calls
     * that were not handled via the ACD system.
     *
     * @return the count of internal outgoing non-ACD calls
     */
    public final Integer getNbLocalOutNonAcd() {
        return nbLocalOutNonAcd;
    }

    /**
     * Returns the number of external outgoing calls
     * that were not handled via the ACD system.
     *
     * @return the count of external outgoing non-ACD calls
     */
    public final Integer getNbExtOutNonacd() {
        return nbExtOutNonacd;
    }

    /**
     * Returns the number of ACD calls that rang the agent.
     * <p>
     * This counts all calls offered to the agent via the Automatic Call Distribution system,
     * regardless of whether the agent answered.
     *
     * @return the number of ACD calls that rang the agent
     */
    public final Integer getNbRingAcd() {
        return nbRingAcd;
    }

    /**
     * Returns the number of help requests made by the agent during calls.
     *
     * @return the count of help requests
     */
    public final Integer getNbHelp() {
        return nbHelp;
    }

    /**
     * Returns the number of internal (within the organization) incoming calls
     * that were not handled via the ACD system.
     *
     * @return the count of internal non-ACD incoming calls
     */
    public final Integer getNbLocInNonacd() {
        return nbLocInNonacd;
    }

    /**
     * Returns the number of direct external (outside the organization) incoming calls
     * that were not handled via the ACD system.
     *
     * @return the count of direct external non-ACD incoming calls
     */
    public final Integer getNbExtInNonacdDirect() {
        return nbExtInNonacdDirect;
    }

    /**
     * Returns the number of transferred external (outside the organization) incoming calls
     * that were not handled via the ACD system.
     *
     * @return the count of transferred external non-ACD incoming calls
     */
    public final Integer getNbExtInNonacdTransferred() {
        return nbExtInNonacdTransferred;
    }

    /**
     * Returns the number of ACD calls that were served without a transaction code.
     *
     * @return the count of ACD calls served without a transaction code
     */
    public final Integer getNbServedWithoutCode() {
        return nbServedWOCode;
    }

    /**
     * Returns the number of ACD calls that were served with a transaction code.
     *
     * @return the count of ACD calls served with a transaction code
     */
    public final Integer getNbServedWithCode() {
        return nbServedWCode;
    }

    /**
     * Returns the number of ACD calls that were served too quickly.
     * <p>
     * A "quickly served" call is one answered or handled below the minimum expected
     * service time threshold.
     *
     * @return the count of ACD calls served too quickly
     */
    public final Integer getNbAcdQuickServed() {
        return nbAcdQuickServed;
    }

    /**
     * Returns the number of external (outside the organization) non-ACD incoming calls
     * that were successfully served by the agent.
     *
     * @return the count of non-ACD external incoming calls served
     */
    public final Integer getNbExtinNonacdServed() {
        return nbExtinNonacdServed;
    }

    /**
     * Returns the number of external non-ACD incoming calls that were served too quickly.
     * <p>
     * A "quickly served" call is one answered or handled below the expected service time threshold.
     *
     * @return the count of external non-ACD incoming calls served too quickly
     */
    public final Integer getNbExtinNonacdQuickServed() {
        return nbExtinNonacdQuickServed;
    }

    /**
     * Returns the number of outgoing calls handled via the ACD system.
     *
     * @return the count of outgoing ACD calls
     */
    public final Integer getNbOutAcd() {
        return nbOutAcd;
    }

    /**
     * Returns the number of outgoing ACD calls that were successfully answered.
     *
     * @return the count of outgoing ACD calls answered
     */
    public final Integer getNbOutAcdAnswered() {
        return nbOutAcdAnswered;
    }

    /**
     * Returns the number of calls currently in wrap-up.
     * <p>
     * Wrap-up refers to the post-call work period after finishing a call, during which
     * the agent completes administrative or reporting tasks related to the call.
     *
     * @return the count of calls on wrap-up
     */
    public final Integer getNbOnWrapup() {
        return nbOnWrapup;
    }

    /**
     * Returns the total duration of ringing for ACD calls that were served.
     * <p>
     * The returned {@link Duration} represents the cumulative ringing time
     * for all served ACD calls.
     *
     * @return the total ringing duration for served ACD calls
     */
    public final Duration getRingAcdServedTDur() {
        return FormatUtil.asDuration(ringAcdServedTDur);
    }

    /**
     * Returns the average duration of ringing for ACD calls that were served.
     * <p>
     * The returned {@link Duration} represents the average ringing time
     * across all served ACD calls.
     *
     * @return the average ringing duration for served ACD calls
     */
    public final Duration getRingAcdServedADur() {
        return FormatUtil.asDuration(ringAcdServedADur);
    }

    /**
     * Returns the total duration of ringing for non-ACD external incoming calls that were served.
     * <p>
     * The returned {@link Duration} represents the cumulative ringing time
     * for all served non-ACD external incoming calls.
     *
     * @return the total ringing duration for served non-ACD external incoming calls
     */
    public final Duration getRingInNonAcdExtServedTDur() {
        return FormatUtil.asDuration(ringInNonAcdExtServedTDur);
    }

    /**
     * Returns the average duration of ringing for non-ACD external incoming calls that were served.
     * <p>
     * The returned {@link Duration} represents the average ringing time
     * across all served non-ACD external incoming calls.
     *
     * @return the average ringing duration for served non-ACD external incoming calls
     */
    public final Duration getRingInNonAcdExtServedADur() {
        return FormatUtil.asDuration(ringInNonAcdExtServedADur);
    }

    /**
     * Returns the total duration of ringing for all ACD calls, regardless of whether they were served.
     * <p>
     * The returned {@link Duration} represents the cumulative ringing time
     * for all ACD calls.
     *
     * @return the total ringing duration for all ACD calls
     */
    public final Duration getRingAcdTDur() {
        return FormatUtil.asDuration(ringAcdTDur);
    }
    /**
     * Returns the average duration of ringing for ACD calls.
     * <p>
     * The returned {@link Duration} represents the average ringing time
     * across all ACD calls.
     *
     * @return the average ringing duration for ACD calls
     */
    public final Duration getRingAcdADur() {
        return FormatUtil.asDuration(ringAcdADur);
    }

    /**
     * Returns the total duration of ringing for non-ACD external incoming calls.
     * <p>
     * The returned {@link Duration} represents the cumulative ringing time
     * for all non-ACD external incoming calls.
     *
     * @return the total ringing duration for non-ACD external incoming calls
     */
    public final Duration getRingInNonAcdExtTDur() {
        return FormatUtil.asDuration(ringInNonAcdExtTDur);
    }

    /**
     * Returns the average duration of ringing for non-ACD external incoming calls.
     * <p>
     * The returned {@link Duration} represents the average ringing time
     * across all non-ACD external incoming calls.
     *
     * @return the average ringing duration for non-ACD external incoming calls
     */
    public final Duration getRingInNonAcdExtADur() {
        return FormatUtil.asDuration(ringInNonAcdExtADur);
    }

    /**
     * Returns the total duration of ringing for all calls.
     * <p>
     * The returned {@link Duration} represents the cumulative ringing time
     * across all call types (ACD and non-ACD).
     *
     * @return the total ringing duration for all calls
     */
    public final Duration getRingTDur() {
        return FormatUtil.asDuration(ringTDur);
    }

    /**
     * Returns the average duration of ringing for all calls.
     * <p>
     * The returned {@link Duration} represents the average ringing time
     * across all call types (ACD and non-ACD).
     *
     * @return the average ringing duration for all calls
     */
    public final Duration getRingADur() {
        return FormatUtil.asDuration(ringADur);
    }

    /**
     * Returns the total duration of conversation for ACD calls.
     * <p>
     * The returned {@link Duration} represents the cumulative time spent
     * actively conversing with callers in all ACD calls.
     *
     * @return the total conversation duration for ACD calls
     */
    public final Duration getConvAcdTDur() {
        return FormatUtil.asDuration(convAcdTDur);
    }

    /**
     * Returns the average duration of conversation for ACD calls.
     * <p>
     * The returned {@link Duration} represents the average time spent actively
     * conversing with callers in all ACD calls.
     *
     * @return the average conversation duration for ACD calls
     */
    public final Duration getConvAcdADur() {
        return FormatUtil.asDuration(convAcdADur);
    }

    /**
     * Returns the total duration of wrap-up for all calls served.
     * <p>
     * Wrap-up refers to the post-call work period after finishing a call,
     * during which the agent completes administrative or reporting tasks.
     *
     * @return the total wrap-up duration for all served calls
     */
    public final Duration getWrapupAcdTDur() {
        return FormatUtil.asDuration(wrapupAcdTDur);
    }

    /**
     * Returns the total duration of conversation for internal (within the organization)
     * outgoing non-ACD calls.
     *
     * @return the total conversation duration for internal outgoing non-ACD calls
     */
    public final Duration getConvLocOutNonacdTDur() {
        return FormatUtil.asDuration(convLocOutNonacdTDur);
    }

    /**
     * Returns the average duration of conversation for internal outgoing non-ACD calls.
     *
     * @return the average conversation duration for internal outgoing non-ACD calls
     */
    public final Duration getConvLocOutNonacdADur() {
        return FormatUtil.asDuration(convLocOutNonacdADur);
    }

    /**
     * Returns the total duration of conversation for external (outside the organization)
     * outgoing calls.
     *
     * @return the total conversation duration for external outgoing calls
     */
    public final Duration getConvExtOutTDur() {
        return FormatUtil.asDuration(convExtOutTDur);
    }

    /**
     * Returns the average duration of conversation for external outgoing calls.
     *
     * @return the average conversation duration for external outgoing calls
     */
    public final Duration getConvExtOutADur() {
        return FormatUtil.asDuration(convExtOutADur);
    }

    /**
     * Returns the total duration of conversation for internal non-ACD incoming calls.
     *
     * @return the total conversation duration for internal non-ACD incoming calls
     */
    public final Duration getConvLocInNonacdTDur() {
        return FormatUtil.asDuration(convLocInNonacdTDur);
    }

    /**
     * Returns the average duration of conversation for internal non-ACD incoming calls.
     * <p>
     * The returned {@link Duration} represents the average conversation time
     * across all internal non-ACD incoming calls.
     *
     * @return the average conversation duration for internal non-ACD incoming calls
     */
    public final Duration getConvLocInNonacdADur() {
        return FormatUtil.asDuration(convLocInNonacdADur);
    }

    /**
     * Returns the total duration of conversation for non-ACD external incoming calls.
     * <p>
     * The returned {@link Duration} represents the cumulative conversation time
     * across all non-ACD external incoming calls.
     *
     * @return the total conversation duration for non-ACD external incoming calls
     */
    public final Duration getConvExtInNonacdTDur() {
        return FormatUtil.asDuration(convExtInNonacdTDur);
    }

    /**
     * Returns the average duration of conversation for non-ACD external incoming calls.
     * <p>
     * The returned {@link Duration} represents the average conversation time
     * across all non-ACD external incoming calls.
     *
     * @return the average conversation duration for non-ACD external incoming calls
     */
    public final Duration getConvExtInNonacdADur() {
        return FormatUtil.asDuration(convExtInNonacdADur);
    }

    /**
     * Returns the total duration of outgoing ACD call processing.
     * <p>
     * The returned {@link Duration} represents the cumulative time spent
     * processing outgoing ACD calls, including both ringing and conversation.
     *
     * @return the total duration of outgoing ACD call processing
     */
    public final Duration getOutAcdCommTDur() {
        return FormatUtil.asDuration(outAcdCommTDur);
    }

    /**
     * Returns the average duration of outgoing ACD call processing.
     * <p>
     * The returned {@link Duration} represents the average time spent
     * processing each outgoing ACD call.
     *
     * @return the average duration of outgoing ACD call processing
     */
    public final Duration getOutAcdCommADur() {
        return FormatUtil.asDuration(outAcdCommADur);
    }

    /**
     * Returns the total duration of conversation for outgoing ACD calls.
     * <p>
     * The returned {@link Duration} represents the cumulative conversation time
     * for all outgoing ACD calls.
     *
     * @return the total conversation duration for outgoing ACD calls
     */
    public final Duration getOutAcdConvTDur() {
        return FormatUtil.asDuration(outAcdConvTDur);
    }

    /**
     * Returns the average duration of conversation for outgoing ACD calls.
     * <p>
     * The returned {@link Duration} represents the average conversation time
     * for outgoing ACD calls.
     *
     * @return the average conversation duration for outgoing ACD calls
     */
    public final Duration getOutAcdConvADur() {
        return FormatUtil.asDuration(outAcdConvADur);
    }

    /**
     * Returns the total duration of the transaction phase for outgoing ACD calls.
     * <p>
     * The returned {@link Duration} represents the cumulative time spent in the
     * transaction phase of all outgoing ACD calls.
     *
     * @return the total transaction duration for outgoing ACD calls
     */
    public final Duration getOutAcdTransactTDur() {
        return FormatUtil.asDuration(outAcdTransactTDur);
    }

    /**
     * Returns the average duration of the transaction phase for outgoing ACD calls.
     * <p>
     * The returned {@link Duration} represents the average time spent in the
     * transaction phase per outgoing ACD call.
     *
     * @return the average transaction duration for outgoing ACD calls
     */
    public final Duration getOutAcdTransactADur() {
        return FormatUtil.asDuration(outAcdTransactADur);
    }

    /**
     * Returns the total duration of wrap-up for outgoing ACD calls.
     * <p>
     * Wrap-up refers to the post-call work period after finishing a call,
     * during which the agent completes administrative or reporting tasks.
     *
     * @return the total wrap-up duration for outgoing ACD calls
     */
    public final Duration getOutAcdWrapupTDur() {
        return FormatUtil.asDuration(outAcdWrapupTDur);
    }

    /**
     * Returns the average duration of wrap-up for outgoing ACD calls.
     *
     * @return the average wrap-up duration for outgoing ACD calls
     */
    public final Duration getOutAcdWrapupADur() {
        return FormatUtil.asDuration(outAcdWrapupADur);
    }

    /**
     * Returns the total duration of pause phase during outgoing ACD calls.
     *
     * @return the total pause duration for outgoing ACD calls
     */
    public final Duration getOutAcdPauseTDur() {
        return FormatUtil.asDuration(outAcdPauseTDur);
    }

    /**
     * Returns the average duration of pause phase during outgoing ACD calls.
     *
     * @return the average pause duration for outgoing ACD calls
     */
    public final Duration getOutAcdPauseADur() {
        return FormatUtil.asDuration(outAcdPauseADur);
    }

    /**
     * Returns the total idle duration during wrap-up time.
     * <p>
     * This represents the cumulative time agents were idle while on wrap-up.
     *
     * @return the total idle duration during wrap-up
     */
    public final Duration getWrapUpIdleTDur() {
        return FormatUtil.asDuration(wrapUpIdleTDur);
    }

    /**
     * Returns the total duration of calls currently on wrap-up.
     *
     * @return the cumulative duration of calls on wrap-up
     */
    public final Duration getCallOnWrapupTDur() {
        return FormatUtil.asDuration(callOnWrapupTDur);
    }

    /**
     * Returns the total busy duration during wrap-up.
     * <p>
     * This represents the cumulative time agents were actively busy
     * while in wrap-up status after finishing calls.
     *
     * @return the total busy duration during wrap-up
     */
    public final Duration getBusyOnWrapupTDur() {
        return FormatUtil.asDuration(busyOnWrapupTDur);
    }

    /**
     * Returns the total busy duration outside wrap-up.
     * <p>
     * This represents the cumulative time agents were actively busy
     * while not in wrap-up status.
     *
     * @return the total busy duration outside wrap-up
     */
    public final Duration getBusyTDur() {
        return FormatUtil.asDuration(busyTDur);
    }

    /**
     * Returns the percentage of time the agent was logged out.
     *
     * @return the logged-out time percentage for the agent
     */
    public final double getLoggedOutPerTime() {
        return FormatUtil.asDouble(loggedOutPerTime);
    }

    /**
     * Returns the percentage of time the agent was not assigned.
     *
     * @return the not-assigned time percentage for the agent
     */
    public final double getNotAssignedPerTime() {
        return FormatUtil.asDouble(notAssignedPerTime);
    }

    /**
     * Returns the percentage of time the agent was assigned.
     *
     * @return the assigned time percentage for the agent
     */
    public final double getAssignedPerTime() {
        return FormatUtil.asDouble(assignedPerTime);
    }

    /**
     * Returns the percentage of time the agent was withdrawn.
     *
     * @return the withdrawn time percentage for the agent
     */
    public final double getWithdrawPerTime() {
        return FormatUtil.asDouble(withdrawPerTime);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 1.
     *
     * @return the withdrawn time percentage for cause 1
     */
    public final double getWithdrawPerTimeCause1() {
        return FormatUtil.asDouble(withdrawPerTimeCause1);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 2.
     *
     * @return the withdrawn time percentage for cause 2
     */
    public final double getWithdrawPerTimeCause2() {
        return FormatUtil.asDouble(withdrawPerTimeCause2);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 3.
     *
     * @return the withdrawn time percentage for cause 3
     */
    public final double getWithdrawPerTimeCause3() {
        return FormatUtil.asDouble(withdrawPerTimeCause3);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 4.
     *
     * @return the withdrawn time percentage for cause 4
     */
    public final double getWithdrawPerTimeCause4() {
        return FormatUtil.asDouble(withdrawPerTimeCause4);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 5.
     *
     * @return the withdrawn time percentage for cause 5
     */
    public final double getWithdrawPerTimeCause5() {
        return FormatUtil.asDouble(withdrawPerTimeCause5);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 6.
     *
     * @return the withdrawn time percentage for cause 6
     */
    public final double getWithdrawPerTimeCause6() {
        return FormatUtil.asDouble(withdrawPerTimeCause6);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 7.
     *
     * @return the withdrawn time percentage for cause 7
     */
    public final double getWithdrawPerTimeCause7() {
        return FormatUtil.asDouble(withdrawPerTimeCause7);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 8.
     *
     * @return the withdrawn time percentage for cause 8
     */
    public final double getWithdrawPerTimeCause8() {
        return FormatUtil.asDouble(withdrawPerTimeCause8);
    }

    /**
     * Returns the percentage of withdrawn time due to cause 9.
     *
     * @return the withdrawn time percentage for cause 9
     */
    public final double getWithdrawPerTimeCause9() {
        return FormatUtil.asDouble(withdrawPerTimeCause9);
    }

    /**
     * Returns the number of pilots assigned to the agent.
     *
     * @return the number of pilots assigned to the agent
     */
    public final Integer getNbPilots() {
        return nbPilots;
    }

    /**
     * Returns the total number of ACD calls served by the agent.
     *
     * @return the total number of ACD served calls
     */
    public final Integer getNbAcdServedCalls() {
        return nbAcdServedCalls;
    }

    /**
     * Returns the number of incoming ACD calls served by the agent.
     *
     * @return the number of ACD incoming served calls
     */
    public final Integer getNbAcdInServedCalls() {
        return nbAcdInServedCalls;
    }

    /**
     * Returns the number of incoming calls received by pilots.
     *
     * @return the number of incoming calls handled by pilots
     */
    public final Integer getNbInCallsReceivedByPilot() {
        return nbInCallsReceivedByPilot;
    }

    /**
     * Returns the number of outgoing ACD calls served by the agent.
     *
     * @return the number of ACD outgoing served calls
     */
    public final Integer getNbAcdOutServedCalls() {
        return nbAcdOutServedCalls;
    }

    /**
     * Returns the total number of calls that were not served.
     *
     * @return the total number of non-served calls
     */
    public final Integer getNbTotNonServedCalls() {
        return nbTotNonServedCalls;
    }

    /**
     * Returns the number of incoming calls that were not served.
     *
     * @return the number of incoming non-served calls
     */
    public final Integer getNbInNonServedCalls() {
        return nbInNonServedCalls;
    }

    /**
     * Returns the number of calls that were picked up by the agent.
     *
     * @return the number of picked-up calls
     */
    public final Integer getNbPickedupCalls() {
        return nbPickedupCalls;
    }

    /**
     * Returns the number of calls that were refused by the agent.
     *
     * @return the number of refused calls
     */
    public final Integer getNbRefusedCalls() {
        return nbRefusedCalls;
    }

    /**
     * Returns the number of outgoing ACD calls that were not served.
     *
     * @return the number of outgoing ACD non-served calls
     */
    public final Integer getNbAcdOutNonServedCalls() {
        return nbAcdOutNonServedCalls;
    }

    /**
     * Returns the total number of non-ACD calls received by the agent.
     *
     * @return the total number of non-ACD received calls
     */
    public final Integer getNbTotNonAcdreceivedCalls() {
        return nbTotNonAcdreceivedCalls;
    }

    /**
     * Returns the number of incoming non-ACD calls received by the agent.
     *
     * @return the number of incoming non-ACD calls
     */
    public final Integer getNbInNonAcdCalls() {
        return nbInNonAcdCalls;
    }

    /**
     * Returns the number of outgoing non-ACD calls made by the agent.
     *
     * @return the number of outgoing non-ACD calls
     */
    public final Integer getNbOutNonAcdCalls() {
        return nbOutNonAcdCalls;
    }

    /**
     * Returns the total duration during which the agent was assigned but not withdrawn.
     *
     * @return the duration of assigned-but-not-withdrawn time
     */
    public final Duration getAssignedNotWithdrawDur() {
        return FormatUtil.asDuration(assignedNotWithdrawDur);
    }

    /**
     * Returns the total duration during which the agent was withdrawn.
     *
     * @return the total withdrawn duration
     */
    public final Duration getWithdrawDur() {
        return FormatUtil.asDuration(withdrawDur);
    }

    /**
     * Returns the total duration of manual wrap-up work performed by the agent.
     *
     * @return the total manual wrap-up duration
     */
    public final Duration getManuWrapupDur() {
        return FormatUtil.asDuration(manuWrapupDur);
    }

    /**
     * Returns the total duration during which the agent was unreachable.
     *
     * @return the total unreachable duration
     */
    public final Duration getUnreachableDur() {
        return FormatUtil.asDuration(unreachableDur);
    }

    /**
     * Returns the total duration of non-ACD work performed by the agent.
     *
     * @return the total non-ACD work duration
     */
    public final Duration getNonAcdWorkTDur() {
        return FormatUtil.asDuration(nonAcdWorkTDur);
    }

    /**
     * Returns the average duration of non-ACD work performed by the agent.
     *
     * @return the average non-ACD work duration
     */
    public final Duration getNonAcdWorkADur() {
        return FormatUtil.asDuration(nonAcdWorkADur);
    }

    /**
     * Returns the total duration of ACD work performed by the agent.
     *
     * @return the total ACD work duration
     */
    public final Duration getAcdWorkTDur() {
        return FormatUtil.asDuration(acdWorkTDur);
    }

    /**
     * Returns the average duration of ACD work performed by the agent.
     *
     * @return the average ACD work duration
     */
    public final Duration getAcdWorkADur() {
        return FormatUtil.asDuration(acdWorkADur);
    }

    /**
     * Returns the total duration of incoming ACD work.
     *
     * @return the total duration of incoming ACD work
     */
    public final Duration getAcdWorkInTDur() {
        return FormatUtil.asDuration(acdWorkInTDur);
    }

    /**
     * Returns the average duration of incoming ACD work.
     *
     * @return the average duration of incoming ACD work
     */
    public final Duration getAcdWorkInADur() {
        return FormatUtil.asDuration(acdWorkInADur);
    }

    /**
     * Returns the total duration of conversations during incoming ACD work.
     *
     * @return the total conversation duration during incoming ACD work
     */
    public final Duration getAcdWorkInConvTDur() {
        return FormatUtil.asDuration(acdWorkInConvTDur);
    }

    /**
     * Returns the average duration of conversations during incoming ACD work.
     *
     * @return the average conversation duration during incoming ACD work
     */
    public final Duration getAcdWorkInConvADur() {
        return FormatUtil.asDuration(acdWorkInConvADur);
    }

    /**
     * Returns the total duration of ringing during incoming ACD work.
     *
     * @return the total ringing duration during incoming ACD work
     */
    public final Duration getAcdWorkInRingTDur() {
        return FormatUtil.asDuration(acdWorkInRingTDur);
    }

    /**
     * Returns the average duration of ringing during incoming ACD work.
     *
     * @return the average ringing duration during incoming ACD work
     */
    public final Duration getAcdWorkInRingADur() {
        return FormatUtil.asDuration(acdWorkInRingADur);
    }

    /**
     * Returns the total duration of wrap-up during incoming ACD work.
     *
     * @return the total wrap-up duration during incoming ACD work
     */
    public final Duration getAcdWorkInWrapupTDur() {
        return FormatUtil.asDuration(acdWorkInWrapupTDur);
    }

    /**
     * Returns the average duration of wrap-up during incoming ACD work.
     *
     * @return the average wrap-up duration during incoming ACD work
     */
    public final Duration getAcdWorkInWrapupADur() {
        return FormatUtil.asDuration(acdWorkInWrapupADur);
    }

    /**
     * Returns the total duration of outgoing ACD work.
     *
     * @return the total duration of outgoing ACD work
     */
    public final Duration getAcdWorkOutTDur() {
        return FormatUtil.asDuration(acdWorkOutTDur);
    }

    /**
     * Returns the average duration of outgoing ACD work.
     *
     * @return the average duration of outgoing ACD work
     */
    public final Duration getAcdWorkOutADur() {
        return FormatUtil.asDuration(acdWorkOutADur);
    }

    /**
     * Returns the total duration of conversations during outgoing ACD work.
     *
     * @return the total conversation duration during outgoing ACD work
     */
    public final Duration getAcdWorkOutConvTDur() {
        return FormatUtil.asDuration(acdWorkOutConvTDur);
    }

    /**
     * Returns the average duration of conversations during outgoing ACD work.
     *
     * @return the average conversation duration during outgoing ACD work
     */
    public final Duration getAcdWorkOutConvADur() {
        return FormatUtil.asDuration(acdWorkOutConvADur);
    }

    /**
     * Returns the total duration of wrap-up during outgoing ACD work.
     *
     * @return the total wrap-up duration during outgoing ACD work
     */
    public final Duration getAcdWorkOutWrapupTDur() {
        return FormatUtil.asDuration(acdWorkOutWrapupTDur);
    }

    /**
     * Returns the average duration of wrap-up during outgoing ACD work.
     *
     * @return the average wrap-up duration during outgoing ACD work
     */
    public final Duration getAcdWorkOutWrapupADur() {
        return FormatUtil.asDuration(acdWorkOutWrapupADur);
    }

    /**
     * Returns the total duration of conversations for incoming ACD calls.
     *
     * @return the total conversation duration for incoming ACD calls
     */
    public final Duration getAcdInConvTDur() {
        return FormatUtil.asDuration(acdInConvTDur);
    }

    /**
     * Returns the average duration of conversations for incoming ACD calls.
     *
     * @return the average conversation duration for incoming ACD calls
     */
    public final Duration getAcdInConvADur() {
        return FormatUtil.asDuration(acdInConvADur);
    }

    /**
     * Returns the total duration of conversations for outgoing ACD calls.
     *
     * @return the total conversation duration for outgoing ACD calls
     */
    public final Duration getAcdOutConvTDur() {
        return FormatUtil.asDuration(acdOutConvTDur);
    }

    /**
     * Returns the average duration of conversations for outgoing ACD calls.
     *
     * @return the average conversation duration for outgoing ACD calls
     */
    public final Duration getAcdOutConvADur() {
        return FormatUtil.asDuration(acdOutConvADur);
    }

    /**
     * Returns the list of statistics broken down by pilot.
     *
     * @return a list of {@link AgentByPilotStatisticsRow} representing statistics for each pilot
     */
    public final List<AgentByPilotStatisticsRow> getByPilotRows() {
        return pilotAgentStatsRows;
    }
}
