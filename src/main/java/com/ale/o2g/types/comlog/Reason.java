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
 * {@code Reason} enumerates the possible reasons why a communication was
 * released, established, or rerouted.
 * <p>
 * Each reason represents the cause of call termination, the outcome of the
 * communication, or the final handling of the call.
 * <p>
 * This enum is commonly used in call logs to indicate
 * why a call ended or was redirected.
 *
 * <p>Example usage:
 * <pre>{@code
 * Reason reason = callRecord.getReason();
 * if (reason == Reason.ABANDONED) {
 *     System.out.println("The caller abandoned the call before it was answered.");
 * }
 * }</pre>
 */
@JsonEnumDeserializerFallback(value = "UNKNOWN")
public enum Reason {

    /** All trunks were busy, preventing call completion. */
    ALL_TRUNK_BUSY,

    /** The call was refused because the dialed number was invalid. */
    INVALID_NUMBER,

    /** The call was canceled by the caller before being answered. */
    ABANDONED,

    /** The call failed because the called party was busy. */
    BUSY,

    /** The call was set up as part of a conference. */
    CONFERENCED,

    /** The call was successfully picked up by the callee. */
    PICKUP,

    /** The call was forwarded to another destination. */
    FORWARDED,

    /** The call was redirected to a different destination. */
    REDIRECTED,

    /** The call was released because redirection to another destination failed. */
    RELEASED_ON_REDIRECT,

    /** The call was successfully transferred to another destination. */
    TRANSFERRED,

    /** The call was released because transfer to another destination failed. */
    RELEASED_ON_TRANSFER,

    /** The call ended and was sent to voicemail. */
    VOICEMAIL,

    /** The call ended normally without any special handling. */
    NORMAL,

    /** The reason for call termination is unknown or unspecified. */
    UNKNOWN
}
