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
package com.ale.o2g.events.cca;

import com.ale.o2g.events.O2GEvent;

/**
 * Event delivered when a supervisor help request is cancelled or rejected.
 * <p>
 * This event occurs in two scenarios:
 * <ul>
 *     <li>When an agent cancels a previously requested assistance from a supervisor.</li>
 *     <li>When a supervisor rejects a help request from an agent.</li>
 * </ul>
 * <p>
 * The event is received by both the agent and the supervisor involved in the request.
 * It contains the login name of the recipient and the number of the other party.
 * <p>
 * Typical usage:
 * <pre><code>
 * {@literal @}Override
 * public void onSupervisorHelpCancelled(OnSupervisorHelpCancelledEvent e) {
 *     String otherPartyNumber = e.getOtherNumber();
 *     // handle cancellation or rejection
 * }
 * </code></pre>
 *
 * @see CallCenterAgentEventListener#onSupervisorHelpCancelled(OnSupervisorHelpCancelledEvent)
 */
public class OnSupervisorHelpCancelledEvent extends O2GEvent {

    private String loginName;
    private String agentNumber;

    /**
     * Returns the login name of the operator who receives this event.
     * 
     * @return the operator's login name
     */
    public final String getLoginName() {
        return loginName;
    }

    /**
     * Returns the number of the other party involved in the help request.
     * <p>
     * If the event is received by the supervisor, this returns the agent's number.
     * If the event is received by the agent, this returns the supervisor's number.
     * 
     * @return the other party's number
     * @deprecated Use {@link #getPartyNumber()} instead.
     */
    @Deprecated
    public final String getOtherNumber() {
        return agentNumber;
    }

    /**
     * Returns the number of the other party involved in the help request.
     * <p>
     * If the event is received by the supervisor, this returns the agent's number.
     * If the event is received by the agent, this returns the supervisor's number.
     * 
     * @return the other party's number
     */
    public final String getPartyNumber() {
        return agentNumber;
    }

    protected OnSupervisorHelpCancelledEvent() {
    }
    
    
}
