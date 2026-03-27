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
 * {@code Role} defines the possible roles a participant can have in a communication record.
 * <p>
 * Each participant in a call or communication session is assigned a role describing 
 * their function in the interaction. This information is used to identify whether 
 * a participant is the initiator, receiver, or has an unknown role.
 * 
 * <p>Example usage:
 * <pre>{@code 
 * Role role = participant.getRole();
 * if (role == Role.CALLER) {
 *     System.out.println("This participant initiated the call.");
 * }
 * }</pre>
 */
@JsonEnumDeserializerFallback(value = "UNKNOWN")
public enum Role {

    /**
     * The participant is the caller (initiating the call).
     */
    CALLER,

    /**
     * The participant is the callee (receiving the call).
     */
    CALLEE,

    /**
     * The participant's role is unknown or not specified.
     */
    UNKNOWN
}
