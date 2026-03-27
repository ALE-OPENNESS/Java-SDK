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
package com.ale.o2g.events.ccrt;

import com.google.gson.annotations.SerializedName;

/**
 * {@code AgentPhoneState} represents the possible telephonic states of a CCD agent.
 * <p>
 * These states are reported by the 
 * {@link com.ale.o2g.internal.events.ccstats.OnAcdStatsProgressEvent OnAcdStatsProgressEvent} and related real-time events.
 *
 * @see com.ale.o2g.internal.events.ccstats.OnAcdStatsProgressEvent
 * @since 2.7.4
 */
public enum AgentPhoneState {

    /** Idle (agent is free). */
    @SerializedName(value = "Idle")
    IDLE,

    /** Only for analog devices: device is off-hook. */
    @SerializedName(value = "LineLockout")
    LINE_LOCKOUT,

    /** Out of service. */
    @SerializedName(value = "OutOfOrder")
    OUT_OF_ORDER,

    /** Ringing (incoming ACD call). */
    @SerializedName(value = "AcdRinging")
    ACD_RINGING,

    /** Call in progress (ACD call). */
    @SerializedName(value = "AcdConversation")
    ACD_TALKING,

    /** In call or on hold while establishing another connection (double call). */
    @SerializedName(value = "AcdConsultation")
    ACD_CONSULTATION,

    /** Request for help (supervisor support). */
    @SerializedName(value = "Help")
    HELP,

    /** In conference call. */
    @SerializedName(value = "AcdConference")
    ACD_CONFERENCE,

    /** Dialing transaction code. */
    @SerializedName(value = "TransactionOnDial")
    TRANSACTION_ON_DIAL,

    /** In pause state. */
    @SerializedName(value = "Pause")
    PAUSE,

    /** In wrap-up state after a call. */
    @SerializedName(value = "WrapUp")
    WRAP_UP,

    /** Requester-only: discreet listening. */
    @SerializedName(value = "SupervisorDiscreteListening")
    SUPERVISOR_DISCRETE_LISTENING,

    /** Agent is monitored during discreet listening. */
    @SerializedName(value = "AgentDiscreteListening")
    AGENT_DISCRETE_LISTENING,

    /** Recording call in progress. */
    @SerializedName(value = "Recording")
    RECORDING,

    /** Agent has logged off. */
    @SerializedName(value = "LoggedOut")
    LOGGED_OUT,

    /** Agent or correspondent has placed the call on hold. */
    @SerializedName(value = "Held")
    HELD,

    /** Dialing a call. */
    @SerializedName(value = "Dialing")
    DIALING,

    /** Ringing state (local visual/melodic). */
    @SerializedName(value = "PrivateRinging")
    PRIVATE_RINGING,

    /** Local call in progress. */
    @SerializedName(value = "PrivateLocalConversation")
    PRIVATE_LOCAL_CONVERSATION,

    /** External call in progress. */
    @SerializedName(value = "PrivateExternalConversation")
    PRIVATE_EXTERNAL_CONVERSATION,

    /** Call established or on hold, with a second call in establishment or hold. */
    @SerializedName(value = "PrivateConsultation")
    PRIVATE_CONSULTATION,

    /** In a conference call. */
    @SerializedName(value = "PrivateConference")
    PRIVATE_CONFERENCE,

    /** Busy tone. */
    @SerializedName(value = "BusyTone")
    BUSY_TONE,

    /** Reserved by the attendant. */
    @SerializedName(value = "Reserved")
    RESERVED,

    /** Outgoing ACD call. */
    @SerializedName(value = "AcdOutgoingConversation")
    ACD_OUTGOING_CONVERSATION,

    /** Supervisor in "listening on agent". */
    @SerializedName(value = "ContinuousSupervision")
    CONTINUOUS_SUPERVISION,

    /** Fake call that does not block the GT (IVR). */
    @SerializedName(value = "Unavailable")
    UNAVAILABLE,

    /** Unknown telephonic state. */
    @SerializedName(value = "unknown")
    UNKNOWN
}
