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

/**
 * {@code Cause} lists the different call causes.
 */
public enum Cause {

    /**
     * Caller in a two-party call has disconnected before the call was answered.
     */
    ABANDONED, 

    /**
     * The call is receiving the network congestion tone.
     */
    ALL_TRUNK_BUSY, 

    /**
     * The call is receiving the busy tone.
     */
    BUSY, 

    /**
     * One party in a two-party call has disconnected after the call was answered.
     */
    CLEARED, 

    /**
     * One party has left the conference call.
     */
    PARTICIPANT_LEFT, 

    /**
     * This is a multi-party call.
     */
    CONFERENCED, 

    /**
     * The call is receiving the invalid number tone.
     */
    INVALID_NUMBER, 

    /**
     * The destination cannot be reached.
     */
    DESTINATION_NOT_OBTAINABLE,

    /**
     * The call has been forwarded.
     */
    FORWARDED, 

    /**
     * The call has been picked up.
     */
    PICKED_UP, 

    /**
     * The call has been redirected.
     */
    REDIRECTED, 

    /**
     * This is a transferred call.
     */
    TRANSFERRED, 

    /**
     * Unknown cause.
     */
    UNKNOWN, 

    /**
     * Picked up tandem.
     */
    PICKED_UP_TANDEM, 

    /**
     * The call is a call back.
     */
    CALL_BACK, 

    /**
     * The call is recall (e.g. on HELD call indicates that device rings back).
     */
    RECALL, 

    /**
     * CCD context: call distribution
     */
    DISTRIBUTED,

    /**
     * CCD context: supervisor is listening the agent conversation
     */
    SUPERVISOR_LISTENING, 

    /**
     * CCD context: supervisor is fully intruded in the agent conversation
     */
    SUPERVISOR_INTRUSION, 

    /**
     * CCD context: supervisor can speak to the agent
     */
    SUPERVISOR_RESTRICT_INTRUSION, 

    /**
     * CCD context: No available agent
     */
    NO_AVAILABLE_AGENT, 

    /**
     * Physical phone set device is off the hook
     */
    LOCKOUT
}
