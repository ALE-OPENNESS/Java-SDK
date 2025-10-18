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
import com.ale.o2g.types.cca.OperatorState;

/**
 * Event delivered when the state of a CCD operator (agent or supervisor) changes.
 * <p>
 * This event contains the operator's login name and their updated state. It is typically
 * received by classes implementing {@link CallCenterAgentEventListener} or extending
 * {@link CallCenterAgentEventAdapter}.
 * <p>
 * Typical usage:
 * <pre><code>
 * {@literal @}Override
 * public void onAgentStateChanged(OnAgentStateChangedEvent e) {
 *     OperatorState state = e.getState();
 *     // handle the operator's new state
 * }
 * </code></pre>
 *
 * @see CallCenterAgentEventListener#onAgentStateChanged(OnAgentStateChangedEvent)
 * @see CallCenterAgentEventAdapter
 * @see OperatorState
 */
public class OnAgentStateChangedEvent extends O2GEvent {

    private String loginName;
    private OperatorState state;

    /**
     * Returns the login name of the operator whose state has changed.
     * 
     * @return the operator's login name
     */
    public final String getLoginName() {
        return loginName;
    }

    /**
     * Returns the current state of the operator.
     * 
     * @return the operator's state
     */
    public final OperatorState getState() {
        return state;
    }

    protected OnAgentStateChangedEvent() {
    }

}
