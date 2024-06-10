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
package com.ale.o2g.types.ccm;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code Pilot} class represents a CCD Pilot.
 */
public class Pilot {

    /**
     * {@code ServiceState} represents the possible service state of a CCD Pilot.
     */
    @JsonEnumDeserializerFallback(value = "Unknown")
    public static enum ServiceState {
        Closed, Opened, Blocked, Unknown
    }

    private String number;
    private String name;
    private ServiceState state;
    private int waitingTime;
    private boolean saturation;
    private PilotRuleSet rules;
    private boolean possibleTransfer;
    private boolean supervisedTransfer;

    /**
     * Return this pilot directory number.
     * 
     * @return the String representing the pilot number
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Return this pilot name.
     * 
     * @return the String representing the pilot name
     */
    public final String getName() {
        return name;
    }

    /**
     * Return this pilot service state. A pilot can be opened, closed, or blocked.
     * 
     * @return the service state
     */
    public final ServiceState getState() {
        return state;
    }

    /**
     * Return the maximum expected waiting time for a call on this pilot..
     *
     * @return the expected waiting Time
     */
    public final int getWaitingTime() {
        return waitingTime;
    }

    /**
     * Return wheater the queues behind this pilot are saturated.
     * 
     * @return {@code true} in case of saturation; {@code false} otherwise.
     */
    public final boolean isSaturation() {
        return saturation;
    }

    /**
     * Returns the pilot rules.
     * 
     * @return the rules
     */
    public final PilotRuleSet getRules() {
        return rules;
    }

    /**
     * Return wheater transfering a call on this pilot is possible.
     * 
     * @return {@code true} if the transfer is possible; {@code false} otherwise.
     */
    public final boolean isPossibleTransfer() {
        return possibleTransfer;
    }

    /**
     * Return wheater supervized transfer is possible on this pilot.
     * 
     * @return {@code true} if the supervized transfer is possible; {@code false}
     *         otherwise.
     */
    public final boolean isSupervisedTransfer() {
        return supervisedTransfer;
    }

    protected Pilot(String number, String name, ServiceState state, int waitingTime, boolean saturation,
            PilotRuleSet rules, boolean possibleTransfer, boolean supervisedTransfer) {
        this.number = number;
        this.name = name;
        this.state = state;
        this.waitingTime = waitingTime;
        this.saturation = saturation;
        this.rules = rules;
        this.possibleTransfer = possibleTransfer;
        this.supervisedTransfer = supervisedTransfer;
    }

}
