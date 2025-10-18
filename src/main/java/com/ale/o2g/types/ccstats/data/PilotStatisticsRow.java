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

import com.ale.o2g.internal.util.FormatUtil;
import com.ale.o2g.types.ccrt.PilotAttributes;

/**
 * Represents a single row of statistical counters for a CCD pilot.
 * <p>
 * Each {@code PilotStatisticsRow} instance contains metrics corresponding to the 
 * attributes defined in {@link PilotAttributes}. These metrics include counts, 
 * durations, and averages related to call handling, wrap-up, transactions, pauses, 
 * and on-hold periods.
 * <p>
 * <b>Counts</b> indicate the number of calls received, served, transferred, or 
 * currently in wrap-up. <b>Durations</b> are expressed in {@code hh:mm:ss} format 
 * and cover call processing, conversation, wrap-up, transaction phase, pause, 
 * and on-hold times.
 * <p>
 * This class inherits from {@link StatisticsRow}, which provides generic access 
 * to its metrics via the associated attribute enum, allowing dynamic retrieval such as:
 * <pre><code>
 * Duration duration = pilotStatRow.get(PilotAttributes.convTDur).asDuration();
 * </code></pre>
 *
 * @since 2.7.4
 */
public class PilotStatisticsRow extends StatisticsRow<PilotAttributes> {
    
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
    private String queueName;
    private String pilotName;
    private String pilotNumber;
    private Integer nbCallsOpen;
    private Integer nbCallsBlocked;
    private Integer nbCallsForward;
    private Integer nbCallsByTransfer;
    private Integer nbCallsByMutualAid;
    private Integer maxNbSimultCalls;
    private Integer nbOverflowInQueue;
    private Integer nbOverflowInRinging;
    private Integer nbCallsWOQueuing;
    private Integer nbCallsAfterQueuing;

    private Integer nbCallsSentInMutualAidQueue;
    private Integer nbCallsRedirectedOutACDArea;
    private Integer nbCallsDissuaded;
    private Integer nbCallsDissuadedAfterTryingMutualAid;
    private Integer nbCallsVGTypePG;
    private Integer nbCallsSentToPG;
    private Integer nbCallsRejectedLackOfRes;
    private Integer nbCallsServedByAgent;
    private Integer nbCallsServedInTime;
    private Integer nbCallsServedTooQuick;
    private Integer nbCallsWithoutTransCode;
    private Integer nbCallsWithTransCode;
    private Integer nbCallsRedistrib;
    private Integer nbCallsBeforeTS1;
    private String percentCallsBeforeTS1;
    private Integer nbCallsBeforeTS2;
    private String percentCallsBeforeTS2;
    private Integer nbCallsBeforeTS3;
    private String percentCallsBeforeTS3;
    private Integer nbCallsBeforeTS4;
    private String percentCallsBeforeTS4;
    private Integer nbCallsAfterTS4;
    private String percentCallsAfterTS4;
    private Integer nbAbandonsOnGreetingsVG;
    private Integer nbAbandonsOn1WaitingVG;
    private Integer nbAbandonsOn2WaitingVG;
    private Integer nbAbandonsOn3WaitingVG;
    private Integer nbAbandonsOn4WaitingVG;
    private Integer nbAbandonsOn5WaitingVG;
    private Integer nbAbandonsOn6WaitingVG;
    private Integer nbAbandonsOnRinging;
    
    private Integer nbAbandonsOnGenFwdVG;
    private Integer nbAbandonsOnBlockedVG;
    private Integer nbAbandonsOnAgentBusy;
    private Integer nbAbandons;
    private Integer nbAbandonsBeforeTS1;
    private String percentAbandonsBeforeTS1;
    private Integer nbAbandonsBeforeTS2;
    private String percentAbandonsBeforeTS2;
    private Integer nbAbandonsBeforeTS3;
    private String percentAbandonsBeforeTS3;
    private Integer nbAbandonsBeforeTS4;
    private String percentAbandonsBeforeTS4;
    private Integer nbAbandonsAfterTS4;
    private String percentAbandonsAfterTS4;
    private String callProcTDur;
    private String callProcADur;
    private String greetingListenTDur;
    private String greetingListenADur;
    private String beforeQueuingTDur;
    private String waitServedCallsTDur;
    private String waitServedCallsADur;
    private String waitAbandonnedCallsTDur;
    private String waitAbandonnedCallsADur;
    private String ringingTDur;
    private String ringingADur;
    private String convTDur;
    private String convADur;
    private String holdCallsTDur;
    private String holdCallsADur;
    private String wrapupTDur;
    private String wrapupADur;
    private String longestWaitingDur;
    private String serviceLevel;
    private String efficiency;

    private String inServiceState;
    private String genFwdState;
    private String blockedState;
    private Integer dnbTotReceivedCalls;
    private Integer dnbCallsOpen;
    private Integer dnbCallsBlocked;
    private Integer dnbCallsForward;
    private Integer dnbDirectRoute;
    private Integer dnbIndirectRoute;
    private Integer dnbTotServedCalls;
    private String defficiency;
    private Integer dnbCallsWOQueuing;
    private Integer dnbCallsAfterQueuing;
    private Integer dnbCallsBeforeTS1;
    private Integer dnbCallsBeforeTS2;
    private Integer dnbCallsBeforeTS3;
    private Integer dnbCallsBeforeTS4;
    private Integer dnbCallsAfterTS4;
    private String dwaitServedCallsADur;
    private Integer dnbAbandons;
    private Integer dnbAbandonsOnGreetingsVG;
    private Integer dnbAbandonsOnWaitingVG;
    private Integer dnbAbandonsBeforeTS1;
    private Integer dnbAbandonsBeforeTS2;
    private Integer dnbAbandonsBeforeTS3;
    private Integer dnbAbandonsBeforeTS4;
    private Integer dnbAbandonsAfterTS4;
    private Integer dnbCallsRejectedLackOfRes;
    private Integer dnbCallsDissuaded;
    private Integer dnbTotCallsRedirected;
    private Integer dnbCallsRedirectedOutACDArea;
    private Integer dnbCallsSentInMutualAidQueue;
    private Integer dnbCallsSentToPG;
    private String dserviceLevel;
    private String defficiency2;
    private String dcallProcADur;
    private String dconvADur;
    private String dwrapupADur;
    private String dholdCallsADur;
    private String dringingADur;
    private String dwaitingADur;
    private String dlongestWaitingDur;
    
    
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
     * Queue's name.
     * 
     * @return the name of the queue
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * Pilot's name.
     * 
     * @return the name of the pilot
     */
    public String getPilotName() {
        return pilotName;
    }

    /**
     * Pilot's number.
     * 
     * @return the pilot's phone number
     */
    public String getPilotNumber() {
        return pilotNumber;
    }

    /**
     * Number of calls received in open state (PC001).
     * 
     * @return the number of calls received in open state
     */
    public Integer getNbCallsOpen() {
        return nbCallsOpen;
    }

    /**
     * Number of calls received in blocked state (PC002).
     * 
     * @return the number of calls received in blocked state
     */
    public Integer getNbCallsBlocked() {
        return nbCallsBlocked;
    }

    /**
     * Number of calls received in general forwarding state (PC003).
     * 
     * @return the number of calls received in forwarding state
     */
    public Integer getNbCallsForward() {
        return nbCallsForward;
    }

    /**
     * Number of calls received by transfer (PC004).
     * 
     * @return the number of calls received via transfer
     */
    public Integer getNbCallsByTransfer() {
        return nbCallsByTransfer;
    }

    /**
     * Number of calls received by mutual aid (PC005).
     * 
     * @return the number of calls received via mutual aid
     */
    public Integer getNbCallsByMutualAid() {
        return nbCallsByMutualAid;
    }

    /**
     * Maximum number of simultaneous calls (PC006).
     * 
     * @return the maximum number of simultaneous calls
     */
    public Integer getMaxNbSimultCalls() {
        return maxNbSimultCalls;
    }

    /**
     * Timing overflow while calls were in queue (PC007).
     * 
     * @return the number of timing overflows while calls were waiting in the queue
     */
    public Integer getNbOverflowInQueue() {
        return nbOverflowInQueue;
    }

    /**
     * Timing overflow while calls were ringing the agent (PC008).
     * 
     * @return the number of timing overflows while calls were ringing the agent
     */
    public Integer getNbOverflowInRinging() {
        return nbOverflowInRinging;
    }

    /**
     * Number of calls served without queuing (PC009).
     * 
     * @return the number of calls served immediately without queuing
     */
    public Integer getNbCallsWOQueuing() {
        return nbCallsWOQueuing;
    }

    /**
     * Number of calls served after queuing (PC010).
     * 
     * @return the number of calls served after waiting in queue
     */
    public Integer getNbCallsAfterQueuing() {
        return nbCallsAfterQueuing;
    }

    /**
     * Number of calls sent in mutual aid queue (PC011).
     * 
     * @return the number of calls redirected to the mutual aid queue
     */
    public Integer getNbCallsSentInMutualAidQueue() {
        return nbCallsSentInMutualAidQueue;
    }

    /**
     * Number of calls redirected outside ACD area (PC012).
     * 
     * @return the number of calls redirected outside the ACD area
     */
    public Integer getNbCallsRedirectedOutACDArea() {
        return nbCallsRedirectedOutACDArea;
    }

    /**
     * Number of calls dissuaded (PC013).
     * 
     * @return the number of calls that were dissuaded
     */
    public Integer getNbCallsDissuaded() {
        return nbCallsDissuaded;
    }

    /**
     * Number of calls dissuaded after trying mutual aid (PC014).
     * 
     * @return the number of calls dissuaded after attempting mutual aid
     */
    public Integer getNbCallsDissuadedAfterTryingMutualAid() {
        return nbCallsDissuadedAfterTryingMutualAid;
    }

    /**
     * Number of calls processed by VG type PG (PC015).
     * 
     * @return the number of calls processed by VG type PG
     */
    public Integer getNbCallsVGTypePG() {
        return nbCallsVGTypePG;
    }

    /**
     * Number of calls sent to remote PG (PC016).
     * 
     * @return the number of calls sent to a remote PG
     */
    public Integer getNbCallsSentToPG() {
        return nbCallsSentToPG;
    }

    /**
     * Number of calls rejected due to lack of resources (PC017).
     * 
     * @return the number of calls rejected due to insufficient resources
     */
    public Integer getNbCallsRejectedLackOfRes() {
        return nbCallsRejectedLackOfRes;
    }

    /**
     * Number of calls served by agent (PC018).
     * 
     * @return the number of calls served by an agent
     */
    public Integer getNbCallsServedByAgent() {
        return nbCallsServedByAgent;
    }

    /**
     * Number of calls served in time (PC019).
     * 
     * @return the number of calls served within the expected time
     */
    public Integer getNbCallsServedInTime() {
        return nbCallsServedInTime;
    }

    /**
     * Number of calls served too quickly (PC020).
     * 
     * @return the number of calls served too quickly
     */
    public Integer getNbCallsServedTooQuick() {
        return nbCallsServedTooQuick;
    }

    /**
     * Number of calls without transaction code (PC021).
     * 
     * @return the number of calls without a transaction code
     */
    public Integer getNbCallsWithoutTransCode() {
        return nbCallsWithoutTransCode;
    }

    /**
     * Number of calls with transaction code (PC022).
     * 
     * @return the number of calls with a transaction code
     */
    public Integer getNbCallsWithTransCode() {
        return nbCallsWithTransCode;
    }

    /**
     * Number of calls redistributed (PC023).
     * 
     * @return the number of calls that were redistributed
     */
    public Integer getNbCallsRedistrib() {
        return nbCallsRedistrib;
    }

    /**
     * Number of calls served before threshold 1 (e.g., 5 sec) (PC024).
     * 
     * @return the number of calls served before the first time threshold
     */
    public Integer getNbCallsBeforeTS1() {
        return nbCallsBeforeTS1;
    }

    /**
     * Percentage of calls served before threshold 1 (e.g., 5 sec) (PC025).
     * 
     * @return the percentage of calls served before threshold 1
     */
    public Double getPercentCallsBeforeTS1() {
        return FormatUtil.asDouble(percentCallsBeforeTS1);
    }

    /**
     * Number of calls served before threshold 2 (e.g., 15 sec) (PC026).
     * 
     * @return the number of calls served before threshold 2
     */
    public Integer getNbCallsBeforeTS2() {
        return nbCallsBeforeTS2;
    }

    /**
     * Percentage of calls served before threshold 2 (e.g., 15 sec) (PC027).
     * 
     * @return the percentage of calls served before threshold 2
     */
    public Double getPercentCallsBeforeTS2() {
        return FormatUtil.asDouble(percentCallsBeforeTS2);
    }

    /**
     * Number of calls served before threshold 3 (e.g., 30 sec) (PC028).
     * 
     * @return the number of calls served before threshold 3
     */
    public Integer getNbCallsBeforeTS3() {
        return nbCallsBeforeTS3;
    }

    /**
     * Percentage of calls served before threshold 3 (e.g., 30 sec) (PC029).
     * 
     * @return the percentage of calls served before threshold 3
     */
    public Double getPercentCallsBeforeTS3() {
        return FormatUtil.asDouble(percentCallsBeforeTS3);
    }

    /**
     * Number of calls served before threshold 4 (e.g., 60 sec) (PC030).
     * 
     * @return the number of calls served before threshold 4
     */
    public Integer getNbCallsBeforeTS4() {
        return nbCallsBeforeTS4;
    }

    /**
     * Percentage of calls served before threshold 4 (e.g., 60 sec) (PC031).
     * 
     * @return the percentage of calls served before threshold 4
     */
    public Double getPercentCallsBeforeTS4() {
        return FormatUtil.asDouble(percentCallsBeforeTS4);
    }

    /**
     * Number of calls served after threshold 4 (e.g., 60 sec) (PC032).
     * 
     * @return the number of calls served after threshold 4
     */
    public Integer getNbCallsAfterTS4() {
        return nbCallsAfterTS4;
    }

    /**
     * Percentage of calls served after threshold 4 (e.g., 60 sec) (PC033).
     * 
     * @return the percentage of calls served after threshold 4
     */
    public Double getPercentCallsAfterTS4() {
        return FormatUtil.asDouble(percentCallsAfterTS4);
    }

    /**
     * Number of abandons on greeting voice guide (PC034).
     * 
     * @return the number of calls abandoned on greeting voice guide
     */
    public Integer getNbAbandonsOnGreetingsVG() {
        return nbAbandonsOnGreetingsVG;
    }

    /**
     * Number of abandons on first waiting voice guide (PC035).
     * 
     * @return the number of calls abandoned on first waiting voice guide
     */
    public Integer getNbAbandonsOn1WaitingVG() {
        return nbAbandonsOn1WaitingVG;
    }

    /**
     * Number of abandons on second waiting voice guide (PC036).
     * 
     * @return the number of calls abandoned on second waiting voice guide
     */
    public Integer getNbAbandonsOn2WaitingVG() {
        return nbAbandonsOn2WaitingVG;
    }

    /**
     * Number of abandons on third waiting voice guide (PC037).
     * 
     * @return the number of calls abandoned on third waiting voice guide
     */
    public Integer getNbAbandonsOn3WaitingVG() {
        return nbAbandonsOn3WaitingVG;
    }

    /**
     * Number of abandons on fourth waiting voice guide (PC038).
     *
     * @return the number of calls abandoned on fourth waiting voice guide
     */
    public Integer getNbAbandonsOn4WaitingVG() {
        return nbAbandonsOn4WaitingVG;
    }

    /**
     * Number of abandons on fifth waiting voice guide (PC039).
     *
     * @return the number of calls abandoned on fifth waiting voice guide
     */
    public Integer getNbAbandonsOn5WaitingVG() {
        return nbAbandonsOn5WaitingVG;
    }

    /**
     * Number of abandons on sixth waiting voice guide (PC040).
     *
     * @return the number of calls abandoned on sixth waiting voice guide
     */
    public Integer getNbAbandonsOn6WaitingVG() {
        return nbAbandonsOn6WaitingVG;
    }

    /**
     * Number of abandons on ringing (PC041).
     *
     * @return the number of calls abandoned while ringing
     */
    public Integer getNbAbandonsOnRinging() {
        return nbAbandonsOnRinging;
    }

    /**
     * Number of abandons on general forwarding voice guide (PC042).
     *
     * @return the number of calls abandoned on general forwarding voice guide
     */
    public Integer getNbAbandonsOnGenFwdVG() {
        return nbAbandonsOnGenFwdVG;
    }

    /**
     * Number of abandons on blocked voice guide (PC043).
     *
     * @return the number of calls abandoned on blocked voice guide
     */
    public Integer getNbAbandonsOnBlockedVG() {
        return nbAbandonsOnBlockedVG;
    }

    /**
     * Number of abandons of direct calls while waiting on agent busy (PC044).
     *
     * @return the number of calls abandoned due to agent being busy
     */
    public Integer getNbAbandonsOnAgentBusy() {
        return nbAbandonsOnAgentBusy;
    }

    /**
     * Overall number of abandons (PC045).
     *
     * @return the total number of call abandons
     */
    public Integer getNbAbandons() {
        return nbAbandons;
    }

    /**
     * Number of abandons before threshold 1 (e.g., 5s) (PC046).
     *
     * @return the number of call abandons before threshold 1
     */
    public Integer getNbAbandonsBeforeTS1() {
        return nbAbandonsBeforeTS1;
    }

    /**
     * Percentage of abandons before threshold 1 (e.g., 5s) (PC047).
     *
     * @return the percentage of call abandons before threshold 1
     */
    public Double getPercentAbandonsBeforeTS1() {
        return FormatUtil.asDouble(percentAbandonsBeforeTS1);
    }

    /**
     * Number of abandons before threshold 2 (e.g., 15s) (PC048).
     *
     * @return the number of call abandons before threshold 2
     */
    public Integer getNbAbandonsBeforeTS2() {
        return nbAbandonsBeforeTS2;
    }

    /**
     * Percentage of abandons before threshold 2 (e.g., 15s) (PC049).
     *
     * @return the percentage of call abandons before threshold 2
     */
    public Double getPercentAbandonsBeforeTS2() {
        return FormatUtil.asDouble(percentAbandonsBeforeTS2);
    }

    /**
     * Number of abandons before threshold 3 (e.g., 30s) (PC050).
     *
     * @return the number of call abandons before threshold 3
     */
    public Integer getNbAbandonsBeforeTS3() {
        return nbAbandonsBeforeTS3;
    }

    /**
     * Percentage of abandons before threshold 3 (e.g., 30s) (PC051).
     *
     * @return the percentage of call abandons before threshold 3
     */
    public Double getPercentAbandonsBeforeTS3() {
        return FormatUtil.asDouble(percentAbandonsBeforeTS3);
    }

    /**
     * Number of abandons before threshold 4 (e.g., 60s) (PC052).
     *
     * @return the number of call abandons before threshold 4
     */
    public Integer getNbAbandonsBeforeTS4() {
        return nbAbandonsBeforeTS4;
    }

    /**
     * Percentage of abandons before threshold 4 (e.g., 60s) (PC053).
     *
     * @return the percentage of call abandons before threshold 4
     */
    public Double getPercentAbandonsBeforeTS4() {
        return FormatUtil.asDouble(percentAbandonsBeforeTS4);
    }

    /**
     * Number of abandons after threshold 4 (e.g., 60s) (PC054).
     *
     * @return the number of call abandons after threshold 4
     */
    public Integer getNbAbandonsAfterTS4() {
        return nbAbandonsAfterTS4;
    }

    /**
     * Percentage of abandons after threshold 4 (e.g., 60s) (PC055).
     *
     * @return the percentage of call abandons after threshold 4
     */
    public Double getPercentAbandonsAfterTS4() {
        return FormatUtil.asDouble(percentAbandonsAfterTS4);
    }

    /**
     * Total duration of call processing (PC056).
     *
     * @return the total duration of call processing
     */
    public Duration getCallProcTDur() {
        return FormatUtil.asDuration(callProcTDur);
    }

    /**
     * Average duration of call processing (PC057).
     *
     * @return the average duration of call processing
     */
    public Duration getCallProcADur() {
        return FormatUtil.asDuration(callProcADur);
    }

    /**
     * Total duration of greeting guide listening (PC058).
     *
     * @return the total duration spent listening to greeting guide
     */
    public Duration getGreetingListenTDur() {
        return FormatUtil.asDuration(greetingListenTDur);
    }

    /**
     * Average duration of greeting guide listening (PC059).
     *
     * @return the average duration spent listening to greeting guide
     */
    public Duration getGreetingListenADur() {
        return FormatUtil.asDuration(greetingListenADur);
    }

    /**
     * Total time before queuing (PC060).
     *
     * @return the total duration before calls are queued
     */
    public Duration getBeforeQueuingTDur() {
        return FormatUtil.asDuration(beforeQueuingTDur);
    }

    /**
     * Total waiting duration of served calls (PC061).
     *
     * @return the total waiting duration of served calls
     */
    public Duration getWaitServedCallsTDur() {
        return FormatUtil.asDuration(waitServedCallsTDur);
    }

    /**
     * Average waiting duration of served calls (PC062).
     *
     * @return the average waiting duration of served calls
     */
    public Duration getWaitServedCallsADur() {
        return FormatUtil.asDuration(waitServedCallsADur);
    }

    /**
     * Total waiting duration of abandoned calls (PC063).
     *
     * @return the total waiting duration of abandoned calls
     */
    public Duration getWaitAbandonnedCallsTDur() {
        return FormatUtil.asDuration(waitAbandonnedCallsTDur);
    }

    /**
     * Average waiting duration of abandoned calls (PC064).
     *
     * @return the average waiting duration of abandoned calls
     */
    public Duration getWaitAbandonnedCallsADur() {
        return FormatUtil.asDuration(waitAbandonnedCallsADur);
    }

    /**
     * Total duration of ringing (PC065).
     *
     * @return the total ringing duration
     */
    public Duration getRingingTDur() {
        return FormatUtil.asDuration(ringingTDur);
    }

    /**
     * Average duration of ringing (PC066).
     *
     * @return the average ringing duration
     */
    public Duration getRingingADur() {
        return FormatUtil.asDuration(ringingADur);
    }

    /**
     * Total duration of conversation (PC067).
     *
     * @return the total conversation duration
     */
    public Duration getConvTDur() {
        return FormatUtil.asDuration(convTDur);
    }

    /**
     * Average duration of conversation (PC068).
     *
     * @return the average conversation duration
     */
    public Duration getConvADur() {
        return FormatUtil.asDuration(convADur);
    }

    /**
     * Total duration of hold calls (PC069).
     *
     * @return the total duration of calls on hold
     */
    public Duration getHoldCallsTDur() {
        return FormatUtil.asDuration(holdCallsTDur);
    }

    /**
     * Average duration of hold calls (PC070).
     *
     * @return the average duration of calls on hold
     */
    public Duration getHoldCallsADur() {
        return FormatUtil.asDuration(holdCallsADur);
    }

    /**
     * Total duration of wrap-up (PC071).
     *
     * @return the total wrap-up duration
     */
    public Duration getWrapupTDur() {
        return FormatUtil.asDuration(wrapupTDur);
    }

    /**
     * Average duration of wrap-up (PC072).
     *
     * @return the average wrap-up duration
     */
    public Duration getWrapupADur() {
        return FormatUtil.asDuration(wrapupADur);
    }

    /**
     * Longest waiting time (PC073).
     *
     * @return the longest waiting duration for a call
     */
    public Duration getLongestWaitingDur() {
        return FormatUtil.asDuration(longestWaitingDur);
    }

    /**
     * Service level (PC077).
     *
     * @return the service level
     */
    public Double getServiceLevel() {
        return FormatUtil.asDouble(serviceLevel);
    }

    /**
     * Efficiency (PC078).
     *
     * @return the efficiency of the queue or pilot
     */
    public Double getEfficiency() {
        return FormatUtil.asDouble(efficiency);
    }

    /**
     * In-service state (pilot state percent) (PC079).
     *
     * @return the in-service state as a percentage
     */
    public Double getInServiceState() {
        return FormatUtil.asDouble(inServiceState);
    }

    /**
     * General forwarding state (pilot state percent) (PC080).
     *
     * @return the general forwarding state as a percentage
     */
    public Double getGenFwdState() {
        return FormatUtil.asDouble(genFwdState);
    }

    /**
     * Blocked state (pilot state percent) (PC081).
     *
     * @return the blocked state as a percentage
     */
    public Double getBlockedState() {
        return FormatUtil.asDouble(blockedState);
    }

    /**
     * Total number of received calls (PC082).
     *
     * @return the total number of received calls
     */
    public Integer getDnbTotReceivedCalls() {
        return dnbTotReceivedCalls;
    }

    /**
     * Number of received calls in pilot open (PC083).
     *
     * @return the number of received calls in open state
     */
    public Integer getDnbCallsOpen() {
        return dnbCallsOpen;
    }

    /**
     * Number of received calls in pilot blocked (PC084).
     *
     * @return the number of received calls in blocked state
     */
    public Integer getDnbCallsBlocked() {
        return dnbCallsBlocked;
    }

    /**
     * Number of received calls in pilot general forwarding (PC085).
     *
     * @return the number of received calls in general forwarding state
     */
    public Integer getDnbCallsForward() {
        return dnbCallsForward;
    }

    /**
     * Number of received calls in direct routing (PC086).
     *
     * @return the number of received calls routed directly
     */
    public Integer getDnbDirectRoute() {
        return dnbDirectRoute;
    }

    /**
     * Number of received calls in indirect routing (PC087).
     *
     * @return the number of received calls routed indirectly
     */
    public Integer getDnbIndirectRoute() {
        return dnbIndirectRoute;
    }

    /**
     * Total number of served calls (PC088).
     *
     * @return the total number of served calls
     */
    public Integer getDnbTotServedCalls() {
        return dnbTotServedCalls;
    }

    /**
     * ACD served calls efficiency (PC089).
     *
     * @return the efficiency of ACD served calls
     */
    public String getDefficiency() {
        return defficiency;
    }

    /**
     * Number of ACD served calls without queuing (PC090).
     *
     * @return the number of ACD served calls without queuing
     */
    public Integer getDnbCallsWOQueuing() {
        return dnbCallsWOQueuing;
    }

    /**
     * Number of ACD served calls after queuing (PC091).
     *
     * @return the number of ACD served calls after queuing
     */
    public Integer getDnbCallsAfterQueuing() {
        return dnbCallsAfterQueuing;
    }

    /**
     * Number of ACD served calls before threshold 1 (PC092).
     *
     * @return the number of ACD served calls before threshold 1
     */
    public Integer getDnbCallsBeforeTS1() {
        return dnbCallsBeforeTS1;
    }

    /**
     * Number of ACD served calls before threshold 2 (PC093).
     *
     * @return the number of ACD served calls before threshold 2
     */
    public Integer getDnbCallsBeforeTS2() {
        return dnbCallsBeforeTS2;
    }

    /**
     * Number of ACD served calls before threshold 3 (PC094).
     *
     * @return the number of ACD served calls before threshold 3
     */
    public Integer getDnbCallsBeforeTS3() {
        return dnbCallsBeforeTS3;
    }

    /**
     * Number of ACD served calls before threshold 4 (PC095).
     *
     * @return the number of ACD served calls before threshold 4
     */
    public Integer getDnbCallsBeforeTS4() {
        return dnbCallsBeforeTS4;
    }

    /**
     * Number of ACD served calls after threshold 4 (PC096).
     *
     * @return the number of ACD served calls after threshold 4
     */
    public Integer getDnbCallsAfterTS4() {
        return dnbCallsAfterTS4;
    }

    /**
     * Average waiting time for ACD served calls (PC097).
     *
     * @return the average waiting duration of served calls
     */
    public Duration getDwaitServedCallsADur() {
        return FormatUtil.asDuration(dwaitServedCallsADur);
    }

    /**
     * Total number of abandons (PC098).
     *
     * @return the total number of abandoned calls
     */
    public Integer getDnbAbandons() {
        return dnbAbandons;
    }

    /**
     * Number of abandons on greetings voice guide (PC099).
     *
     * @return the number of abandons during greeting voice guide
     */
    public Integer getDnbAbandonsOnGreetingsVG() {
        return dnbAbandonsOnGreetingsVG;
    }

    /**
     * Number of abandons on waiting voice guide (PC100).
     *
     * @return the number of abandons during waiting voice guide
     */
    public Integer getDnbAbandonsOnWaitingVG() {
        return dnbAbandonsOnWaitingVG;
    }

    /**
     * Number of abandons before threshold 1 (PC101).
     *
     * @return the number of abandons before threshold 1
     */
    public Integer getDnbAbandonsBeforeTS1() {
        return dnbAbandonsBeforeTS1;
    }

    /**
     * Number of abandons before threshold 2 (PC102).
     *
     * @return the number of abandons before threshold 2
     */
    public Integer getDnbAbandonsBeforeTS2() {
        return dnbAbandonsBeforeTS2;
    }

    /**
     * Number of abandons before threshold 3 (PC103).
     *
     * @return the number of abandons before threshold 3
     */
    public Integer getDnbAbandonsBeforeTS3() {
        return dnbAbandonsBeforeTS3;
    }

    /**
     * Number of abandons before threshold 4 (PC104).
     *
     * @return the number of abandons before threshold 4
     */
    public Integer getDnbAbandonsBeforeTS4() {
        return dnbAbandonsBeforeTS4;
    }

    /**
     * Number of abandons after threshold 4 (PC105).
     *
     * @return the number of abandons after threshold 4
     */
    public Integer getDnbAbandonsAfterTS4() {
        return dnbAbandonsAfterTS4;
    }

    /**
     * Number of calls rejected due to lack of resources (PC106).
     *
     * @return the number of calls rejected due to insufficient resources
     */
    public Integer getDnbCallsRejectedLackOfRes() {
        return dnbCallsRejectedLackOfRes;
    }

    /**
     * Number of calls dissuaded (PC107).
     *
     * @return the number of dissuaded calls
     */
    public Integer getDnbCallsDissuaded() {
        return dnbCallsDissuaded;
    }

    /**
     * Total number of redirected calls (PC108).
     *
     * @return the total number of redirected calls
     */
    public Integer getDnbTotCallsRedirected() {
        return dnbTotCallsRedirected;
    }

    /**
     * Number of calls redirected outside ACD area (PC109).
     *
     * @return the number of calls redirected outside the ACD area
     */
    public Integer getDnbCallsRedirectedOutACDArea() {
        return dnbCallsRedirectedOutACDArea;
    }

    /**
     * Number of calls sent in mutual aid queue (PC110).
     *
     * @return the number of calls sent to mutual aid queue
     */
    public Integer getDnbCallsSentInMutualAidQueue() {
        return dnbCallsSentInMutualAidQueue;
    }

    /**
     * Number of calls sent to remote PG (PC111).
     *
     * @return the number of calls sent to a remote PG
     */
    public Integer getDnbCallsSentToPG() {
        return dnbCallsSentToPG;
    }

    /**
     * Service level (PC112).
     *
     * @return the service level
     */
    public String getDserviceLevel() {
        return dserviceLevel;
    }

    /**
     * Efficiency (PC113).
     *
     * @return the efficiency
     */
    public String getDefficiency2() {
        return defficiency2;
    }

    /**
     * Average duration of call processing (PC114).
     *
     * @return the average duration of call processing
     */
    public Duration getDcallProcADur() {
        return FormatUtil.asDuration(dcallProcADur);
    }

    /**
     * Average duration of conversation (PC115).
     *
     * @return the average duration of conversation
     */
    public Duration getDconvADur() {
        return FormatUtil.asDuration(dconvADur);
    }

    /**
     * Average duration of wrap-up (PC116).
     *
     * @return the average wrap-up duration
     */
    public Duration getDwrapupADur() {
        return FormatUtil.asDuration(dwrapupADur);
    }

    /**
     * Average duration of hold calls (PC117).
     *
     * @return the average duration of calls on hold
     */
    public Duration getDholdCallsADur() {
        return FormatUtil.asDuration(dholdCallsADur);
    }

    /**
     * Average duration of ringing (PC118).
     *
     * @return the average ringing duration
     */
    public Duration getDringingADur() {
        return FormatUtil.asDuration(dringingADur);
    }

    /**
     * Average duration of waiting (PC119).
     *
     * @return the average waiting duration
     */
    public Duration getDwaitingADur() {
        return FormatUtil.asDuration(dwaitingADur);
    }

    /**
     * Longest waiting duration (PC120).
     *
     * @return the longest waiting duration
     */
    public Duration getDlongestWaitingDur() {
        return FormatUtil.asDuration(dlongestWaitingDur);
    }

    protected PilotStatisticsRow() {
        
    }
}
