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

import com.google.gson.annotations.SerializedName;

/**
 * {@code ComRecord} class represents a communication record, that is an history
 * call ticket stored by the O2G server for each conversation.
 */
public class ComRecord {

    @SerializedName(value = "recordId")
    private long id;

    @SerializedName(value = "comRef")
    private String callRef;
    private boolean acknowledged;
    private Collection<ComRecordParticipant> participants;
    private Date begin;
    private Date end;

    /**
     * Returns this communication record identifier.
     * 
     * @return the unique identifier for this communication record.
     */
    public final long getId() {
        return id;
    }

    /**
     * Returns the reference of the call that has created this communication record.
     * 
     * @return the callRef the call reference?
     * @see com.ale.o2g.types.telephony.Call#getCallRef() Call.getCallRef
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Returns whether this communication record has been acknowledged. This
     * parameter can only be {@code true} for a missed incoming call.
     * 
     * @return {@code true} if the call has been acknowledged; {@code false}
     *         otherwise.
     */
    public final boolean isAcknowledged() {
        return acknowledged;
    }

    /**
     * Returns the participant to the call.
     * 
     * @return the participants.
     */
    public final Collection<ComRecordParticipant> getParticipants() {
        return participants;
    }

    /**
     * Returns the start date of this call.
     * 
     * @return the begin date.
     */
    public final Date getBegin() {
        return begin;
    }

    /**
     * Returns the end date of this call.
     * 
     * @return the end date.
     */
    public final Date getEnd() {
        return end;
    }

    protected ComRecord() {
    }

}
