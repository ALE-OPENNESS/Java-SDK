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
package com.ale.o2g.types.routing;

/**
 * {@code Forward} represents a forward the user has activated.
 */
public class Forward {

    /**
     * {@code Condition} represents the possible condition a user can associate to a
     * forward.
     */
    public static enum Condition {

        /**
         * Incoming calls are immediately forwarded on the target.
         */
        IMMEDIATE,

        /**
         * Incoming calls are forwarded on the target if the user is busy.
         */
        BUSY,

        /**
         * Incoming calls are forwarded on the target if the user does not answer the
         * call.
         */
        NO_ANSWER,

        /**
         * Incoming calls are forwarded on the target if the user is busy or if the user
         * does not answer the call.
         */
        BUSY_NO_ANSWER
    }

    protected Forward(Destination destination, Condition condition, String number) {
        this.destination = destination;
        this.condition = condition;
        this.number = number;
    }

    private Destination destination;
    private Condition condition;
    private String number;

    /**
     * Returns this forward destination.
     * 
     * @return the destination
     */
    public final Destination getDestination() {
        return destination;
    }

    /**
     * Returns this forward associated condition.
     * 
     * @return the condition
     */
    public final Condition getCondition() {
        return condition;
    }

    /**
     * Returns the nuber destination of this forward.
     * 
     * @return the number or {@code null} if the forward destination is not a
     *         number.
     */
    public final String getNumber() {
        return number;
    }

}
