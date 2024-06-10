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
package com.ale.o2g.events.ccp;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.telephony.call.Cause;

/**
 * This event is raised when a CCD call has been removed from the queue. Either
 * because is has been distributed, or abandonned, or is rerouted in case of
 * queue overflow.
 * 
 * @since 2.7
 */
public class OnPilotCallRemovedEvent extends O2GEvent {

    private String pilot;
    private String callRef;
    private Cause cause;
    private String releasingDevice;
    private String newDestination;

    /**
     * Return the pilot which distribute the call
     * 
     * @return the pilot number
     */
    public final String getPilot() {
        return pilot;
    }

    /**
     * Returns this call reference
     * 
     * @return the call reference
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Returns the event cause
     * 
     * @return the cause
     */
    public final Cause getCause() {
        return cause;
    }

    /**
     * Return the device that release the CCD call
     * 
     * @return the releasingDevice
     */
    public final String getReleasingDevice() {
        return releasingDevice;
    }

    /**
     * Return the new destination.it can be an agent if the call is distributed or a
     * queu overflow destination if the call overflows.
     * 
     * @return the new destination
     */
    public final String getNewDestination() {
        return newDestination;
    }

    protected OnPilotCallRemovedEvent() {
    }
}
