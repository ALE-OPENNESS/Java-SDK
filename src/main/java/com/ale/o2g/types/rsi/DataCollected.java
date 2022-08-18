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
package com.ale.o2g.types.rsi;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code DataCollected} represents a collection of tones. DTMF tones are
 * generated by parties on a call by pressing the dialpad digits 0 through 9 and
 * * and # during the call.
 */
public class DataCollected {

    /**
     * {@code CollectionCause} represents the reason the digits collection ended.
     */
    @JsonEnumDeserializerFallback(value = "SF_TERMINATED")
    public static enum CollectionCause {

        /**
         * A specific tone specified in the retrieval criteria is received.
         */
        FLUSCHAR_RECEIVED,

        /**
         * The number of tones specified in the retrieval criteria is received.
         */
        CHAR_COUNT_REACHED,

        /**
         * The timeout specified in the retrieval criteria has elapsed.
         */
        TIMEOUT,

        /**
         * An error occurs.
         */
        SF_TERMINATED
    }

    private String collCrid;
    private String digits;
    private CollectionCause cause;

    /**
     * Returns the digit collection identifier.
     * 
     * @return the digit collection identifier.
     */
    public final String getCollCrid() {
        return collCrid;
    }

    /**
     * Returns the collected digits.
     * 
     * @return the digits.
     */
    public final String getDigits() {
        return digits;
    }

    /**
     * Returns the cause of the digit collection termination.
     * 
     * @return the reason of the digit collection termination.
     */
    public final CollectionCause getCause() {
        return cause;
    }

    protected DataCollected() {
    }

}
