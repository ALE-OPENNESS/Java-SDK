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

import com.ale.o2g.types.common.ServiceState;

/**
 * {@code Pilot} represents a CCD pilot, which is a single entry point into the
 * call center distribution system (CCDistribution).
 * <p>
 * A pilot is used to organize and manage call distribution for a specific type
 * of service offered by a company. It connects three key objects within the system:
 * pilots, queues, and resources (agents or agent groups), allowing calls to be
 * prioritized and routed according to configured rules.
 * <p>
 * Each pilot has a service state, may experience queue saturation, and can support
 * call transfers under specific conditions. The behavior of a pilot is defined
 * by its associated {@link PilotRuleSet}.
 */
public class Pilot {

    private String number;
    private String name;
    private ServiceState state;
    private int waitingTime;
    private boolean saturation;
    private PilotRuleSet rules;
    private boolean possibleTransfer;
    private boolean supervisedTransfer;

    /**
     * Returns the directory number of this pilot.
     * 
     * @return the pilot's directory number
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Returns the name of this pilot.
     * 
     * @return the pilot name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the current service state of the pilot.
     * 
     * @return the {@link ServiceState} of the pilot
     */
    public final ServiceState getState() {
        return state;
    }

    /**
     * Returns the expected maximum waiting time for a call on this pilot.
     * <p>
     * Unit is seconds.
     * 
     * @return the waiting time in seconds
     */
    public final int getWaitingTime() {
        return waitingTime;
    }

    /**
     * Indicates whether the call queues for this pilot are currently saturated.
     * 
     * @return {@code true} if queues are saturated; {@code false} otherwise
     */
    public final boolean isSaturation() {
        return saturation;
    }

    /**
     * Returns the set of rules associated with this pilot.
     * 
     * @return a {@link PilotRuleSet} containing the pilot rules
     */
    public final PilotRuleSet getRules() {
        return rules;
    }

    /**
     * Indicates whether transferring a call to this pilot is possible.
     * 
     * @return {@code true} if transfer is possible; {@code false} otherwise
     */
    public final boolean isPossibleTransfer() {
        return possibleTransfer;
    }

    /**
     * Indicates whether supervised transfer is allowed for this pilot.
     * 
     * @return {@code true} if supervised transfer is possible; {@code false} otherwise
     */
    public final boolean isSupervisedTransfer() {
        return supervisedTransfer;
    }

    /**
     * Constructs a new {@code Pilot} with the specified attributes.
     * <p>
     * Typically used internally when creating pilot objects from the call center 
     * management system.
     * 
     * @param number             the pilot's directory number
     * @param name               the pilot's name
     * @param state              the current service state
     * @param waitingTime        the expected maximum waiting time (seconds)
     * @param saturation         {@code true} if call queues are saturated
     * @param rules              the set of rules associated with the pilot
     * @param possibleTransfer   {@code true} if call transfer is possible
     * @param supervisedTransfer {@code true} if supervised transfer is possible
     */
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
