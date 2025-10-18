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

import java.util.EventListener;

/**
 * Defines a listener for call center agent events.
 * <p>
 * Applications interested in processing call center agent events can implement
 * this interface or extend the {@link com.ale.o2g.events.cca.CallCenterAgentEventAdapter
 * CallCenterAgentEventAdapter}, which provides default (empty) implementations
 * for all methods. This allows overriding only the methods of interest.
 * <p>
 * Once implemented, the listener should be registered with the session using
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription) Session.listenEvents}.
 * When a relevant agent event occurs, the corresponding method on this listener
 * will be invoked.
 * @see com.ale.o2g.CallCenterAgentService
 * @see com.ale.o2g.events.cca.CallCenterAgentEventAdapter
 * 
 */
public interface CallCenterAgentEventListener extends EventListener {

    /**
     * Invoked when the state of a CCD agent changes.
     *
     * @param e the event object containing the updated agent state
     */
    void onAgentStateChanged(OnAgentStateChangedEvent e);

    /**
     * Invoked when a supervisor help request is cancelled by the agent or 
     * rejected by the supervisor.
     *
     * @param e the event object containing the cancellation details
     */
    void onSupervisorHelpCancelled(OnSupervisorHelpCancelledEvent e);

    /**
     * Invoked when an agent requests supervisor assistance.
     *
     * @param e the event object containing the help request details
     */
    void onSupervisorHelpRequested(OnSupervisorHelpRequestedEvent e);
    
    /**
     * Invoked when an agent activates or deactivates one of their skills.
     *
     * @param e the event object containing the updated skill information
     */
    void onAgentSkillChanged(OnAgentSkillChangedEvent e);
}
