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
package com.ale.o2g.events.ccrt;

import java.time.LocalDateTime;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.internal.util.FormatUtil;

/**
 * Event delivered by {@link CallCenterRealtimeEventListener#onAgentRtiChanged(OnAgentRtiChangedEvent) onAgentRtiChanged(OnAcdStatsProgressEvent)}
 * whenever the attributes of one or more CCD agents change.
 * <p>
 * This event contains only the attributes that have changed since the previous notification.
 * It is sent periodically according to the {@link com.ale.o2g.types.ccrt.Context Context} 
 * configuration in {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}.
 * <p>
 * Typical usage:
 * <pre><code>
 * {@literal @}Override
 * public void onAgentRtiChanged(OnAcdStatsProgressEvent e) {
 *     int nbServerACDCalls = e.getNbOfServedACDCalls();
 *     // ...
 * }
 * </code></pre>
 * 
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see com.ale.o2g.types.ccrt.Context
 * @see CallCenterRealtimeEventListener#onAgentRtiChanged(OnAgentRtiChangedEvent)
 * @since 2.7.4
 */
public class OnAgentRtiChangedEvent extends O2GEvent {

    private String name;
    private String firstName;
    private String number;
    private AgentType type;
    private String logonDate;

    private AgentServiceState serviceState;
    private String serviceStateDate;

    private AgentPhoneState phoneState;
    private String phoneStateDate;

    private String pilotName;
    private String queueName;
    private int nbOfWithdrawals;
    private int withdrawalsTotalDuration;
    private int nbOfPrivateCalls;
    private int privateCallsTotalDuration;
    private int nbOfServedACDCalls;
    private int nbOfOutgoingACDCalls;
    private int nbOfRefusedACDCalls;
    private int nbOfInterceptedACDCalls;
    private int nbOfTransferedACDCalls;
    private String currentPG;
    private String associatedSet;
    private int withdrawReason;
    private int afeKey;

    /**
     * Returns the agent's directory number.
     *
     * @return the agent directory number
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the agent first name.
     * 
     * @return the agent firstname
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Returns the agent directory number.
     * 
     * @return the agent directory number
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Returns the agent type.
     * 
     * @return the agent type
     */
    public final AgentType getType() {
        return type;
    }

    /**
     * Returns the date and time when the agent logged on.
     *
     * @return the logon date as a {@link LocalDateTime}, or {@code null} if 
     *         the value could not be parsed
     */
    public final LocalDateTime getLogonDate() {
        return FormatUtil.asLocalDateTime(logonDate);
    }

    /**
     * Returns the agent's current service state.
     *
     * @return the agent service state
     * @see #getServiceStateDate()
     */
    public final AgentServiceState getServiceState() {
        return serviceState;
    }

    /**
     * Returns the date and time when the agent's service state last changed.
     *
     * @return the service state change date as a {@link LocalDateTime}, or 
     *         {@code null} if the value could not be parsed
     * @see #getServiceState()
     */
    public final LocalDateTime getServiceStateDate() {
        return FormatUtil.asLocalDateTime(serviceStateDate);
    }

    /**
     * Returns the agent's current phone state.
     *
     * @return the phone state
     * @see #getPhoneStateDate()
     */
    public final AgentPhoneState getPhoneState() {
        return phoneState;
    }

    /**
     * Returns the date and time when the agent's phone state last changed.
     *
     * @return the phone state change date as a {@link LocalDateTime}, or 
     *         {@code null} if the value could not be parsed
     * @see #getPhoneState()
     */
    public final LocalDateTime getPhoneStateDate() {
        return FormatUtil.asLocalDateTime(phoneStateDate);
    }

    /**
     * Returns the name of the pilot that distributed the call.
     * 
     * @return the pilot name
     */
    public final String getPilotName() {
        return pilotName;
    }

    /**
     * Returns the name of the queue that distributed the call.
     * 
     * @return the queue name
     */
    public final String getQueueName() {
        return queueName;
    }

    /**
     * Returns the number of times the agent has entered the withdrawal state.
     *
     * @return the number of withdrawals
     * @see #getWithdrawalsTotalDuration()
     */
    public final int getNbOfWithdrawals() {
        return nbOfWithdrawals;
    }

    /**
     * Returns the total number of seconds the agent remained in the withdrawal
     * state.
     *
     * @return the total duration in seconds that the agent was in withdrawal
     * @see #getNbOfWithdrawals()
     */
    public final int getWithdrawalsTotalDuration() {
        return withdrawalsTotalDuration;
    }

    /**
     * Returns the number of private calls this agent has handled.
     *
     * @return the number of private calls
     * @see #getPrivateCallsTotalDuration()
     */
    public final int getNbOfPrivateCalls() {
        return nbOfPrivateCalls;
    }

    /**
     * Returns the total number of seconds the agent spent on private calls.
     *
     * @return the total duration in seconds of private calls
     * @see #getNbOfPrivateCalls()
     */
    public final int getPrivateCallsTotalDuration() {
        return privateCallsTotalDuration;
    }

    /**
     * Returns the number of ACD calls this agent has handled.
     *
     * @return the number of ACD calls handled by the agent
     */
    public final int getNbOfServedACDCalls() {
        return nbOfServedACDCalls;
    }

    /**
     * Returns the number of outgoing ACD calls placed by this agent.
     *
     * @return the number of outgoing ACD calls
     * @see #getNbOfServedACDCalls()
     */
    public final int getNbOfOutgoingACDCalls() {
        return nbOfOutgoingACDCalls;
    }

    /**
     * Returns the number of ACD calls this agent has refused.
     *
     * @return the number of refused ACD calls
     * @see #getNbOfServedACDCalls()
     */
    public final int getNbOfRefusedACDCalls() {
        return nbOfRefusedACDCalls;
    }

    /**
     * Returns the number of ACD calls this agent has intercepted (picked up).
     *
     * @return the number of intercepted ACD calls
     * @see #getNbOfServedACDCalls()
     */
    public final int getNbOfInterceptedACDCalls() {
        return nbOfInterceptedACDCalls;
    }

    /**
     * Returns the number of ACD calls this agent has transferred.
     *
     * @return the number of transferred ACD calls
     * @see #getNbOfServedACDCalls()
     */
    public final int getNbOfTransferedACDCalls() {
        return nbOfTransferedACDCalls;
    }

    /**
     * Returns the processing group in which the agent is currently logged in.
     *
     * @return the current processing group
     */
    public final String getCurrentProcessingGroup() {
        return currentPG;
    }

    /**
     * Returns the agent associated set.
     * 
     * @return the associated set
     */
    public final String getAssociatedSet() {
        return associatedSet;
    }

    /**
     * Returns the withdraw reason.
     * 
     * @return the withdraw reason
     */
    public final int getWithdrawReason() {
        return withdrawReason;
    }

    /**
     * Returns the object CCD key.
     * 
     * @return the ccd key
     */
    public final int getKey() {
        return afeKey;
    }

    protected OnAgentRtiChangedEvent() {
    }
}
