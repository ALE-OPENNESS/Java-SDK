/*
* Copyright 2026 ALE International
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
package com.ale.o2g.types.telephony.call.acd;

import com.google.gson.annotations.SerializedName;

/**
 * {@code PilotTransferQueryParameters} describes the set of criteria
 * used to query a CCD Pilot for call transfer possibilities.
 * <p>
 * Each criterion is optional. If a field is not set (null), it will not
 * be used as a filtering condition in the query.
 * <p>
 * Typical usage:
 * <pre>{@code
 * PilotTransferQueryParameters params = new PilotTransferQueryParameters()
 *      .setAgentNumber("1234")
 *      .setPriorityTransfer(true)
 *      .setCallProfile(profile);
 * }</pre>
 * @since 2.7.4
 */
public class PilotTransferQueryParameters {

    private String agentNumber;
    private Boolean priorityTransfer = null;
    private Boolean supervisedTransfer = null;

    @SerializedName("skills")
    private CallProfile callProfile = null;

    /**
     * Constructs an empty {@code PilotTransferQueryParameters} object. All criteria
     * are initially unset.
     */
    public PilotTransferQueryParameters() {
    }

    /**
     * Returns the agent number criterion.
     *
     * @return the agent number, or {@code null} if not set
     */
    public String getAgentNumber() {
        return this.agentNumber;
    }

    /**
     * Sets the agent number criterion.
     *
     * @param agentNumber the agent number to filter by
     * @return this instance for fluent chaining
     */
    public PilotTransferQueryParameters setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
        return this;
    }

    /**
     * Returns the priority transfer criterion.
     *
     * @return {@code true} if only priority transfers, {@code false} if only
     *         non-priority transfers, {@code null} if this criterion is not used
     */
    public Boolean getPriorityTransfer() {
        return this.priorityTransfer;
    }

    /**
     * Sets the priority transfer criterion.
     *
     * @param priorityTransfer {@code true} for priority only, {@code false} for
     *                         non-priority only, {@code null} to ignore
     * @return this instance for fluent chaining
     */
    public PilotTransferQueryParameters setPriorityTransfer(Boolean priorityTransfer) {
        this.priorityTransfer = priorityTransfer;
        return this;
    }

    /**
     * Returns the supervised transfer criterion.
     *
     * @return {@code true} if only supervised transfers, {@code false} if only
     *         unsupervised transfers, {@code null} if this criterion is not used
     */
    public Boolean getSupervisedTransfer() {
        return this.supervisedTransfer;
    }

    /**
     * Sets the supervised transfer criterion.
     *
     * @param supervisedTransfer {@code true} for supervised only, {@code false} for
     *                           unsupervised only, {@code null} to ignore
     * @return this instance for fluent chaining
     */
    public PilotTransferQueryParameters setSupervisedTransfer(Boolean supervisedTransfer) {
        this.supervisedTransfer = supervisedTransfer;
        return this;
    }

    /**
     * Returns the call profile criterion.
     *
     * @return the {@link CallProfile} to match, or {@code null} if not set
     */
    public CallProfile getCallProfile() {
        return this.callProfile;
    }

    /**
     * Sets the call profile criterion.
     *
     * @param callProfile the call profile to match
     * @return this instance for fluent chaining
     */
    public PilotTransferQueryParameters setCallProfile(CallProfile callProfile) {
        this.callProfile = callProfile;
        return this;
    }

    /**
     * Returns {@code true} if an agent number has been set.
     *
     * @return {@code true} if agent number is not null or empty
     */
    public boolean hasAgentNumber() {
        return (this.agentNumber != null) && !this.agentNumber.isEmpty();
    }

    /**
     * Returns {@code true} if the priority transfer criterion is used.
     *
     * @return {@code true} if priorityTransfer is not null
     */
    public boolean hasPriorityTransferCriteria() {
        return this.priorityTransfer != null;
    }

    /**
     * Returns {@code true} if the supervised transfer criterion is used.
     *
     * @return {@code true} if supervisedTransfer is not null
     */
    public boolean hasSupervisedTransferCriteria() {
        return this.supervisedTransfer != null;
    }

    /**
     * Returns {@code true} if a call profile criterion has been set.
     *
     * @return {@code true} if callProfile is not null
     */
    public boolean hasCallProfile() {
        return this.callProfile != null;
    }
}
