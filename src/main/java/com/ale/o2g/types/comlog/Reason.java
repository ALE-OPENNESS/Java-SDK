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

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code Reason} enum defines the why reason the communication has been released, established or rerouted.
 */
@JsonEnumDeserializerFallback(value = "UNKNOWN")
public enum Reason {
    
    /**
     * The call was abandonned because there was no Available trunk.
     */
    ALL_TRUNK_BUSY,
    
    /**
     * The call was refused because the dialed number is not valid.
     */
    INVALID_NUMBER,
    
    /**
     * The call was canceled by the caller.
     */
    ABANDONED,
    
    /**
     * The call failed because the called party is busy.
     */
    BUSY,
    
    /**
     * The call was set to be a conference.
     */
    CONFERENCED,
    
    /**
     * he call was picked up.
     */
    PICKUP,
    
    /**
     * The call was forwarded to another destination.
     */
    FORWARDED,
    
    /**
     * The call was redirected to another destination.
     */
    REDIRECTED,
    
    /**
     * The call was released since redirection to another destination fails.
     */
    RELEASED_ON_REDIRECT,
    
    /**
     * The call was transferred.
     */
    TRANSFERRED,
    
    /**
     * The call was released since transfer to another destination fails.
     */
    RELEASED_ON_TRANSFER,
    
    /**
     * The call ended on voicemail.
     */
    VOICEMAIL,
    
    /**
     * The call normally ended.
     */
    NORMAL,
    
    /**
     * The reason is unknown.
     */
    UNKNOWN
}
