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
package com.ale.o2g.types.comlog;

import java.util.Collection;
import java.util.Date;

import com.ale.o2g.types.telephony.call.CorrelatorData;
import com.google.gson.annotations.SerializedName;

/**
 * {@code ComRecord} represents a communication record, which is a call history
 * entry stored by the O2G server for each conversation.
 * <p>
 * Each record contains information about the call, including its unique
 * identifier, reference, participants, timestamps, and acknowledgment status.
 * <p>
 * A {@code ComRecord} may represent an incoming or outgoing call, and includes
 * details such as the start and end times, as well as the conversation time
 * when the call was answered.
 * 
 */
public class ComRecord {

    @SerializedName(value = "recordId")
    private long id;

    @SerializedName(value = "comRef")
    private String callRef;

    private boolean acknowledged;
    private Collection<ComRecordParticipant> participants;
    private Date beginDate;
    private Date endDate;
    private Date convDate;
    private long holdDuration;
    private String transferredBy;
    private String associatedData;

    /**
     * Return the duration this call has been on hold.
     * 
     * @return the hold duration
     * @since 2.7.4
     */
    public final long getHoldDuration() {
        return holdDuration;
    }

    /**
     * Returns the number of the userwho performed the transfer for this call, if
     * the call has been transferred.
     * <p>
     * This value may be {@code null} if the call has not been transferred.
     * </p>
     *
     * @return the number of the user who transferred the call, or {@code null} if
     *         no transfer occurred
     * @since 2.7.4
     */
    public final String getTransferredBy() {
        return transferredBy;
    }

    
    /**
     * Returns the correlator data associated with this call, if present.
     * <p>
     * This method may return {@code null} if no correlator data is associated
     * with the call.
     * </p>
     *
     * @return the correlator data associated with this call,
     *         or {@code null} if none is present
     * @since 2.7.4
     */
    public final CorrelatorData getCorrelatorData() {
        
        if (this.associatedData == null) {
            return null;
        }
        else {
            return new CorrelatorData(associatedData);
        }
    }

    /**
     * Returns the unique identifier of this communication record.
     * 
     * @return the record ID.
     */
    public final long getId() {
        return id;
    }

    /**
     * Returns the reference of the call that created this communication record.
     * 
     * @return the call reference.
     * @see com.ale.o2g.types.telephony.Call#getCallRef()
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Indicates whether this communication record has been acknowledged.
     * <p>
     * Only missed incoming calls can have this flag set to {@code true}.
     * 
     * @return {@code true} if the call has been acknowledged; {@code false}
     *         otherwise.
     */
    public final boolean isAcknowledged() {
        return acknowledged;
    }

    /**
     * Returns the collection of participants in this communication record.
     * 
     * @return a collection of {@link ComRecordParticipant} objects representing all
     *         participants in the call.
     */
    public final Collection<ComRecordParticipant> getParticipants() {
        return participants;
    }

    /**
     * Returns the start date and time of this call.
     * 
     * @return the call start date.
     */
    public final Date getBegin() {
        return beginDate;
    }

    /**
     * Returns the end date and time of this call.
     * 
     * @return the call end date.
     */
    public final Date getEnd() {
        return endDate;
    }

    /**
     * Returns the date and time when the call was answered.
     * <p>
     * For missed calls, this value may be {@code null}.
     * 
     * @return the conversation date.
     * @since 2.6
     */
    public final Date getAnswered() {
        return convDate;
    }

    /**
     * Protected constructor for deserialization purposes.
     */
    protected ComRecord() {
    }
}
