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
package com.ale.o2g.events.telephony;

import java.util.Collection;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.telephony.call.CallData;
import com.ale.o2g.types.telephony.call.Cause;
import com.ale.o2g.types.telephony.call.Leg;
import com.ale.o2g.types.telephony.call.Participant;
import com.ale.o2g.types.telephony.device.Capabilities;

/**
 * Send when a new call has been created.
 *
 */
public class OnCallCreatedEvent extends O2GEvent {

    private String loginName;
    private String callRef;
    private Cause cause;
    private CallData callData;
    private String initiator;
    private Collection<Leg> legs;
    private Collection<Participant> participants;
    private Collection<Capabilities> deviceCapabilities;

    /**
     * Returns the user's login name.
     * 
     * @return the login name.
     */
    public final String getLoginName() {
        return loginName;
    }

    /**
     * Returns this new call reference.
     * 
     * @return the call reference.
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Returns the event cause.
     * 
     * @return the cause.
     */
    public final Cause getCause() {
        return cause;
    }

    /**
     * Returns the associated call data.
     * 
     * @return the call data
     */
    public final CallData getCallData() {
        return callData;
    }

    /**
     * Returns this call initiator id. The initiator is in teh participants
     * collection.
     * 
     * @return the initiator id.
     */
    public final String getInitiator() {
        return initiator;
    }

    /**
     * Returns the legs associated to this call.
     * 
     * @return the legs.
     */
    public final Collection<Leg> getLegs() {
        return legs;
    }

    /**
     * Returns the participants associated to this call.
     * 
     * @return the participants.
     */
    public final Collection<Participant> getParticipants() {
        return participants;
    }

    /**
     * Returns the device capabilities.
     * 
     * @return the deviceCapabilities.
     */
    public final Collection<Capabilities> getDeviceCapabilities() {
        return deviceCapabilities;
    }

}
