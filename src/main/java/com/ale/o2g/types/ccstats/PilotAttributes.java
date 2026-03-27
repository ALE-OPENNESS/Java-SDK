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
package com.ale.o2g.types.ccstats;

/**
 * {@code PilotAttributes} enumerates the possible attributes for a Call Center
 * CCD pilot.
 * <p>
 * Each attribute corresponds to a statistic that can be reported by
 * {@link com.ale.o2g.CallCenterStatisticsService CallCenterStatisticsService}. These attributes allow fine-grained
 * analysis of pilot performance, call handling, waiting times, and service
 * levels.
 * <p>
 * The special attribute {@link #ALL} includes all available attributes and is
 * mainly intended for testing or bulk retrieval purposes.
 * 
 * @since 2.7.4
 */
public enum PilotAttributes {

    /**
     * Number of calls received in the open state.
     * <p>
     * An open state call is one that is offered to the pilot without any
     * restrictions or routing blocks.
     */
    nbCallsOpen,

    /**
     * Number of calls received in the blocked state.
     * <p>
     * A blocked state call is one that could not be delivered immediately due to
     * pilot availability or system constraints.
     */
    nbCallsBlocked,

    /**
     * Number of calls received in the general forwarding state.
     * <p>
     * This represents calls that were routed to the pilot via general forwarding
     * rules.
     */
    nbCallsForward,

    /**
     * Number of calls received by transfer.
     * <p>
     * These are calls transferred to this pilot.
     */
    nbCallsByTransfer,

    /**
     * Number of calls received via mutual aid.
     */
    nbCallsByMutualAid,

    /**
     * Maximum number of simultaneous calls.
     * <p>
     * Represents the peak number of concurrent calls handled by the pilot at any
     * given moment.
     */
    maxNbSimultCalls,

    /**
     * Number of overflows while calls were in queue.
     * <p>
     * An overflow occurs when a call waits too long in the queue and exceeds the
     * defined threshold.
     */
    nbOverflowInQueue,

    /**
     * Number of overflows while calls were ringing the agent.
     * <p>
     * Represents calls that exceeded the maximum allowed ringing time without being
     * answered.
     */
    nbOverflowInRinging,

    /**
     * Number of calls served without queuing.
     * <p>
     * These calls were handled immediately by the pilot without waiting in a queue.
     */
    nbCallsWOQueuing,

    /**
     * Number of calls served after queuing.
     * <p>
     * Calls that were first placed in a queue before being answered by the pilot.
     */
    nbCallsAfterQueuing,

    /**
     * Number of calls sent to the mutual aid queue.
     * <p>
     * Calls redirected to assist other agents or teams when demand exceeds
     * capacity.
     */
    nbCallsSentInMutualAidQueue,

    /**
     * Number of calls redirected outside the ACD area.
     * <p>
     * Calls that could not be handled by the local ACD system and were routed
     * externally.
     */
    nbCallsRedirectedOutACDArea,

    /**
     * Number of calls dissuaded before reaching an agent.
     * <p>
     * Calls abandoned by the caller or rejected by the system before the pilot
     * could answer.
     */
    nbCallsDissuaded,

    /**
     * Number of calls dissuaded after attempting mutual aid.
     * <p>
     * Calls that were initially redirected for mutual aid but eventually abandoned.
     */
    nbCallsDissuadedAfterTryingMutualAid,

    /**
     * Number of calls processed by VG type PG.
     * <p>
     * Calls handled via the voice guide (VG) type PG processing.
     */
    nbCallsVGTypePG,

    /**
     * Number of calls sent to a remote PG.
     * <p>
     * Calls forwarded to a remote pilot group (PG) for handling.
     */
    nbCallsSentToPG,

    /** Number of calls rejected due to lack of resources. */
    nbCallsRejectedLackOfRes,

    /** Number of calls served by the agent. */
    nbCallsServedByAgent,

    /** Number of calls served within the expected time. */
    nbCallsServedInTime,

    /** Number of calls served too quickly. */
    nbCallsServedTooQuick,

    /** Number of calls without a transaction code. */
    nbCallsWithoutTransCode,

    /** Number of calls with a transaction code. */
    nbCallsWithTransCode,

    /** Number of calls redistributed to other agents or queues. */
    nbCallsRedistrib,

    /** Number of calls served before threshold 1 (e.g., 5 seconds). */
    nbCallsBeforeTS1,

    /** Percentage of calls served before threshold 1 (e.g., 5 seconds). */
    percentCallsBeforeTS1,

    /** Number of calls served before threshold 2 (e.g., 15 seconds). */
    nbCallsBeforeTS2,

    /** Percentage of calls served before threshold 2 (e.g., 15 seconds). */
    percentCallsBeforeTS2,

    /** Number of calls served before threshold 3 (e.g., 30 seconds). */
    nbCallsBeforeTS3,

    /** Percentage of calls served before threshold 3 (e.g., 30 seconds). */
    percentCallsBeforeTS3,

    /** Number of calls served before threshold 4 (e.g., 60 seconds). */
    nbCallsBeforeTS4,

    /** Percentage of calls served before threshold 4 (e.g., 60 seconds). */
    percentCallsBeforeTS4,

    /** Number of calls served after threshold 4 (e.g., 60 seconds). */
    nbCallsAfterTS4,

    /** Percentage of calls served after threshold 4 (e.g., 60 seconds). */
    percentCallsAfterTS4,

    /** Number of abandons during the greeting voice guide. */
    nbAbandonsOnGreetingsVG,

    /** Number of abandons during the first waiting voice guide. */
    nbAbandonsOn1WaitingVG,

    /** Number of abandons during the second waiting voice guide. */
    nbAbandonsOn2WaitingVG,

    /** Number of abandons during the third waiting voice guide. */
    nbAbandonsOn3WaitingVG,

    /** Number of abandons during the fourth waiting voice guide. */
    nbAbandonsOn4WaitingVG,

    /** Number of abandons during the fifth waiting voice guide. */
    nbAbandonsOn5WaitingVG,

    /** Number of abandons during the sixth waiting voice guide. */
    nbAbandonsOn6WaitingVG,

    /** Number of abandons while the call was ringing. */
    nbAbandonsOnRinging,

    /** Number of abandons on general forwarding voice guide. */
    nbAbandonsOnGenFwdVG,

    /** Number of abandons on blocked voice guide. */
    nbAbandonsOnBlockedVG,

    /** Number of direct call abandons while the agent was busy. */
    nbAbandonsOnAgentBusy,

    /** Total number of abandons. */
    nbAbandons,

    /** Number of abandons before threshold 1 (e.g., 5 seconds). */
    nbAbandonsBeforeTS1,

    /** Percentage of abandons before threshold 1 (e.g., 5 seconds). */
    percentAbandonsBeforeTS1,

    /** Number of abandons before threshold 2 (e.g., 15 seconds). */
    nbAbandonsBeforeTS2,

    /** Percentage of abandons before threshold 2 (e.g., 15 seconds). */
    percentAbandonsBeforeTS2,

    /** Number of abandons before threshold 3 (e.g., 30 seconds). */
    nbAbandonsBeforeTS3,

    /** Percentage of abandons before threshold 3 (e.g., 30 seconds). */
    percentAbandonsBeforeTS3,

    /** Number of abandons before threshold 4 (e.g., 60 seconds). */
    nbAbandonsBeforeTS4,

    /** Percentage of abandons before threshold 4 (e.g., 60 seconds). */
    percentAbandonsBeforeTS4,

    /** Number of abandons after threshold 4 (e.g., 60 seconds). */
    nbAbandonsAfterTS4,

    /** Percentage of abandons after threshold 4 (e.g., 60 seconds). */
    percentAbandonsAfterTS4,

    /** Total duration of call processing. */
    callProcTDur,

    /** Average duration of call processing. */
    callProcADur,

    /** Total duration of greeting guide listening. */
    greetingListenTDur,

    /** Average duration of greeting guide listening. */
    greetingListenADur,

    /** Total time before queuing. */
    beforeQueuingTDur,

    /** Total waiting duration of served calls. */
    waitServedCallsTDur,

    /** Average waiting duration of served calls. */
    waitServedCallsADur,

    /** Total waiting duration of abandoned calls. */
    waitAbandonnedCallsTDur,

    /** Average waiting duration of abandoned calls. */
    waitAbandonnedCallsADur,

    /** Total duration of ringing. */
    ringingTDur,

    /** Average duration of ringing. */
    ringingADur,

    /** Total conversation duration. */
    convTDur,

    /** Average conversation duration. */
    convADur,

    /** Total duration of hold calls. */
    holdCallsTDur,

    /** Average duration of hold calls. */
    holdCallsADur,

    /** Total duration of wrap-up. */
    wrapupTDur,

    /** Average duration of wrap-up. */
    wrapupADur,

    /** Longest waiting time observed. */
    longestWaitingDur,

    /** Service level achieved. */
    serviceLevel,

    /** Efficiency metric. */
    efficiency,

    /** Percent time in in-service state. */
    inServiceState,

    /** Percent time in general forwarding state. */
    genFwdState,

    /** Percent time in blocked state. */
    blockedState,

    /** Total number of received calls. */
    dnbTotReceivedCalls,

    /** Number of received calls in pilot open state. */
    dnbCallsOpen,

    /** Number of received calls in pilot blocked state. */
    dnbCallsBlocked,

    /** Number of received calls in general forwarding state. */
    dnbCallsForward,

    /** Number of received calls via direct routing. */
    dnbDirectRoute,

    /** Number of received calls via indirect routing. */
    dnbIndirectRoute,

    /** Total number of served calls. */
    dnbTotServedCalls,

    /** ACD served calls efficiency. */
    defficiency,

    /** Number of ACD served calls without queuing. */
    dnbCallsWOQueuing,

    /** Number of ACD served calls after queuing. */
    dnbCallsAfterQueuing,

    /** Number of ACD served calls before threshold 1 (e.g., 5s). */
    dnbCallsBeforeTS1,

    /** Number of ACD served calls before threshold 2 (e.g., 15s). */
    dnbCallsBeforeTS2,

    /** Number of ACD served calls before threshold 3 (e.g., 30s). */
    dnbCallsBeforeTS3,

    /** Number of ACD served calls before threshold 4 (e.g., 60s). */
    dnbCallsBeforeTS4,

    /** Number of ACD served calls after threshold 4 (e.g., 60s). */
    dnbCallsAfterTS4,

    /** Average waiting duration of ACD served calls. */
    dwaitServedCallsADur,

    /** Total number of abandons. */
    dnbAbandons,

    /** Number of abandons on greeting VG. */
    dnbAbandonsOnGreetingsVG,

    /** Number of abandons on waiting VG. */
    dnbAbandonsOnWaitingVG,

    /** Number of abandons before threshold 1 (e.g., 5s). */
    dnbAbandonsBeforeTS1,

    /** Number of abandons before threshold 2 (e.g., 15s). */
    dnbAbandonsBeforeTS2,

    /** Number of abandons before threshold 3 (e.g., 30s). */
    dnbAbandonsBeforeTS3,

    /** Number of abandons before threshold 4 (e.g., 60s). */
    dnbAbandonsBeforeTS4,

    /** Number of abandons after threshold 4 (e.g., 60s). */
    dnbAbandonsAfterTS4,

    /** Number of calls rejected due to lack of resources. */
    dnbCallsRejectedLackOfRes,

    /** Number of calls dissuaded. */
    dnbCallsDissuaded,

    /** Total number of redirected calls. */
    dnbTotCallsRedirected,

    /** Number of calls redirected outside the ACD area. */
    dnbCallsRedirectedOutACDArea,

    /** Number of calls sent to mutual aid queue. */
    dnbCallsSentInMutualAidQueue,

    /** Number of calls sent to remote PG. */
    dnbCallsSentToPG,

    /** Service level achieved. */
    dserviceLevel,

    /** Efficiency metric. */
    defficiency2,

    /** Average call processing duration. */
    dcallProcADur,

    /** Average conversation duration. */
    dconvADur,

    /** Average wrap-up duration. */
    dwrapupADur,

    /** Average hold calls duration. */
    dholdCallsADur,

    /** Average ringing duration. */
    dringingADur,

    /** Average waiting duration. */
    dwaitingADur,

    /** Longest waiting duration observed. */
    dlongestWaitingDur,

    /** All attributes included (for testing purposes). */
    ALL
}
