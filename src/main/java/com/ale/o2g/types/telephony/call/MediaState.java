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

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code MediaState} represents a media state.
 */
@JsonEnumDeserializerFallback(value = "UNKNOWN")
public enum MediaState {
    
    /**
     * Unknown media state. O2G server is unable to provide the information.
     */
    UNKNOWN,
    
    /**
     * The OFF_HOOK state is used when the device is busy for other reasons than a call; typically during service activation.
     */
    OFF_HOOK, 
    
    /**
     * Idle state, no activity on the media.
     */
    IDLE, 
    
    /**
     * The call is releasing.
     */
    RELEASING, 
    
    /**
     * A make call is in progress.
     */
    DIALING, 
    
    /**
     * The call has been placed on hold.
     */
    HELD, 
    
    /**
     * An incoming call is ringing.
     */
    RINGING_INCOMING, 
    
    /**
     * An outgoing call is in progress and the other party is ringing.
     */
    RINGING_OUTGOING, 
    
    /**
     * The call is active.
     */
    ACTIVE
}
