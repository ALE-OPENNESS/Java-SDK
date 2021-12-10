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
 * Send when an existing call has been modified. Modification of a call can be
 * triggered for various reason: changes on legs, on participants, changes on
 * states, ...
 */
public class OnCallModifiedEvent extends O2GEvent {
    private String loginName;
    private String callRef;
    private Cause cause;
    private String previousCallRef;
    private String replacedByCallRef;
    private CallData callData;
    private Collection<Leg> modifiedLegs;
    private Collection<Leg> addedLegs;
    private Collection<Leg> removedLegs;
    private Collection<Participant> modifiedParticipants;
    private Collection<Participant> addedParticipants;
    private Collection<String> removedParticipantIds;
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
     * Returns this call reference.
     * 
     * @return the call reference.
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Returns the event cause.
     * 
     * @return the cause
     */
    public final Cause getCause() {
        return cause;
    }

    /**
     * Returns the previous call reference. When not {@code null}, this call
     * reference indicates that the {@code callRef} replaces
     * {@code previousCallRef}. This also indicates that {@code previousCallRef} has
     * been removed (call removed event is not generated).
     * 
     * @return the previous call reference.
     */
    public final String getPreviousCallRef() {
        return previousCallRef;
    }

    /**
     * Returns the call reference that has been replaced by the new {@code callRef}.
     * This call reference appears when a call is released.
     * 
     * @return the replaced call reference.
     */
    public final String getReplacedByCallRef() {
        return replacedByCallRef;
    }

    /**
     * Returns the call associated data.
     * 
     * @return the call data
     */
    public final CallData getCallData() {
        return callData;
    }

    /**
     * Returns a collection of modified legs.
     * 
     * @return the modified legs or {@code null} if there is no change.
     */
    public final Collection<Leg> getModifiedLegs() {
        return modifiedLegs;
    }

    /**
     * Returns the added legs.
     * 
     * @return the added legs or {@code null} if there is no new leg.
     */
    public final Collection<Leg> getAddedLegs() {
        return addedLegs;
    }

    /**
     * Returns the removed legs.
     * 
     * @return the removed legs or {@code null} if there is no removed leg.
     */
    public final Collection<Leg> getRemovedLegs() {
        return removedLegs;
    }

    /**
     * Returns the modified participants.
     * 
     * @return the modified participants or {@code null} if there is no change.
     */
    public final Collection<Participant> getModifiedParticipants() {
        return modifiedParticipants;
    }

    /**
     * Returns the added participants
     * 
     * @return the added participants or {@code null} if there is no new
     *         participant.
     */
    public final Collection<Participant> getAddedParticipants() {
        return addedParticipants;
    }

    /**
     * Returns the removed participant identifiers.
     * 
     * @return the removed participant identifier or {@code null} if there is no
     *         removed participant.
     */
    public final Collection<String> getRemovedParticipantIds() {
        return removedParticipantIds;
    }

    /**
     * Returns the device capabilities.
     * 
     * @return the device capabilities.
     */
    public final Collection<Capabilities> getDeviceCapabilities() {
        return deviceCapabilities;
    }

    protected OnCallModifiedEvent() {
    }
}
