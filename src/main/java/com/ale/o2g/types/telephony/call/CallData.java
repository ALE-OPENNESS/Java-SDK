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
package com.ale.o2g.types.telephony.call;

import java.util.Collection;

import com.ale.o2g.internal.util.HexaString;
import com.ale.o2g.types.common.PartyInfo;
import com.ale.o2g.types.telephony.Call;
import com.ale.o2g.types.telephony.call.acd.AcdData;

/**
 * {@code CallData} represents the data associated to a call.
 */
public class CallData {
    private PartyInfo initialCalled;
    private boolean deviceCall;
    private boolean anonymous;
    private String callUUID;
    private MediaState state;
    private RecordState recordState;
    private Collection<Tag> tags;
    private Call.Capabilities capabilities;
    private String associateData;
    private String hexaBinaryAssociatedData;
    private String accountInfo;
    private AcdData acdCallData;

    /**
     * Returns the state of this call.
     * 
     * @return the call state
     */
    public final MediaState getState() {
        return state;
    }

    /**
     * Returns the initial party called.
     * 
     * @return the initial called.
     */
    public final PartyInfo getInitialCalled() {
        return initialCalled;
    }

    /**
     * Returns whether it is a device call.
     * 
     * @return {@code true} if it is a device call; {@code false} otherwise.
     */
    public final boolean isDeviceCall() {
        return deviceCall;
    }

    /**
     * Returns whether this call is anonymous.
     * 
     * @return {@code true} if the call is anonymous; {@code false} otherwise.
     */
    public final boolean isAnonymous() {
        return anonymous;
    }

    /**
     * Returns this call UUID.
     * 
     * @return the call UUID.
     */
    public final String getCallUUID() {
        return callUUID;
    }

    /**
     * Returns the record state of this call.
     * 
     * @return the record state.
     */
    public final RecordState getRecordState() {
        return recordState;
    }

    /**
     * Returns this call tags.
     * 
     * @return the tags
     */
    public final Collection<Tag> getTags() {
        return tags;
    }

    /**
     * Returns the call capabilities.
     * 
     * @return the capabilities.
     */
    public final Call.Capabilities getCapabilities() {
        return capabilities;
    }

    /**
     * Return the attached corelator data.
     * @return the correlator data or {@code null} if there is no attached data.
     */
    public final CorrelatorData getCorrelatorData() {
        if (associateData != null) {
            return new CorrelatorData(associateData);
        }
        else if (hexaBinaryAssociatedData != null) {
            return new CorrelatorData(HexaString.toByteArray(hexaBinaryAssociatedData));
        }
        else {
            return null;
        }
    }
    
    
    /**
     * Return the attached corelator data.
     * @return the correlator data or {@code null} if there is no attached data.
     */
    public final CorrelatorData getCorrelatorData() {
        if (associateData != null) {
            return new CorrelatorData(associateData);
        }
        else if (hexaBinaryAssociatedData != null) {
            return new CorrelatorData(HexaString.toByteArray(hexaBinaryAssociatedData));
        }
        else {
            return null;
        }
    }
    
    
    /**
     * Return this call account info.
     * 
     * @return the account info.
     */
    public final String getAccountInfo() {
        return accountInfo;
    }

    /**
     * Returns this call associated acd data.
     * 
     * @return the acd call data or {@code null} if it is not an acd call.
     */
    public final AcdData getAcdCallData() {
        return acdCallData;
    }

    protected CallData() {
    }

}
