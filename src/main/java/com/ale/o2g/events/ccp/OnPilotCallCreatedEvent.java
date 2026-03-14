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
import com.ale.o2g.types.ccp.CallDataPilot;
import com.ale.o2g.types.telephony.call.Cause;

/**
 * Event triggered when a new call arrives on a CCD pilot.
 * <p>
 * This event contains information about the pilot handling the call, 
 * the caller, the call reference, and additional call-related data.
 * </p>
 *
 * @since 2.7
 */
public class OnPilotCallCreatedEvent extends O2GEvent {

    private String pilot;
    private String caller;
    private String callRef;
    private Cause cause;
    private CallDataPilot callData;
    
    /**
     * Returns the pilot number distributing the call.
     *
     * @return the pilot number
     */
    public final String getPilot() {
        return pilot;
    }

    /**
     * Returns the caller's phone number.
     *
     * @return the caller's number
     */
    public final String getCaller() {
        return caller;
    }

    /**
     * Returns the unique reference identifier for this call.
     *
     * @return the call reference
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Returns the cause of this event.
     *
     * @return the event cause
     */
    public final Cause getCause() {
        return cause;
    }

    /**
     * Returns additional data associated with this call.
     *
     * @return the call data
     * @since 2.7.2
     */
    public final CallDataPilot getCallData() {
        return callData;
    }
    
    /**
     * Protected constructor to prevent direct instantiation.
     */
    protected OnPilotCallCreatedEvent() {
    }    
}