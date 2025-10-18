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
package com.ale.o2g.types.ccrt;

/**
 * {@code AgentAttributes} represents the possible real-time attributes 
 * for a CCD agent.
 * <p>
 * The {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService} can report real-time events
 * for each of these attributes.
 */
public enum AgentAttributes {

    /**
     * The associated pro-acd set of the agent.
     */
    AssociatedSet,

    /**
     * The current processing group of the agent.
     */
    CurrentPG,

    /**
     * The phone state of the agent (e.g., ringing, talking, idle).
     */
    PhoneState,

    /**
     * The UTC logon date of the agent.
     */
    LogonDate,

    /**
     * The total duration (in seconds) of all private calls.
     */
    PrivateCallsTotalDuration,

    /**
     * The UTC date when the agent entered the current communication.
     */
    ComDate,

    /**
     * The duration (in seconds) of the current communication.
     */
    ComDuration,

    /**
     * The number of private calls handled by the agent.
     */
    NBOfPrivateCalls,

    /**
     * The number of answered ACD calls.
     */
    NbOfServedACDCalls,

    /**
     * The number of non-answered ACD calls.
     */
    NbOfRefusedACDCalls,

    /**
     * The number of transferred ACD calls.
     */
    NbOfTransferredACDCalls,

    /**
     * The number of outgoing ACD calls.
     */
    NbOfOutgoingACDCalls,

    /**
     * The number of picked-up ACD calls (intercepted calls).
     */
    NbOfInterceptedACDCalls,

    /**
     * The service state of the agent (e.g., loggedIn, assigned, available).
     */
    ServiceState,

    /**
     * The number of withdrawals taken by the agent.
     */
    NbOfWithdrawals,

    /**
     * The total duration of withdrawals taken by the agent.
     */
    WithdrawalsTotalDuration,

    /**
     * The reason for the last withdrawal.
     */
    WithdrawReason,

    /**
     * The name of the pilot associated with the agent.
     */
    PilotName,

    /**
     * The name of the queue associated with the agent.
     */
    QueueName,

    /**
     * Represents all attributes.
     */
    ALL
}
