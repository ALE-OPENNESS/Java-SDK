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
 * {@code CallCenterAgentEventListener} defines the interface for an object that
 * listens to call center agent notifications. The class that is interested in
 * processing call center agent events implements this interface(and all the
 * methods it contains) or extends the {@code CallCenterAgentEventAdapter}
 * (overriding only the methods of interest). The listener object created from
 * that class is then registered with the session, using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a call center agent event occurs the relevant
 * method in the listener object is invoked.
 * 
 */
public interface CallCenterAgentEventListener extends EventListener {

    /**
     * Invoked when the state of a CCD agent is changed.
     * 
     * @param e the associated event object
     */
    void onAgentStateChanged(OnAgentStateChangedEvent e);

    /**
     * Invoked when agent has requested the assistance of his supervisor and when
     * the request is canceled by the agent or when the request is rejected by the
     * supervisor.
     * 
     * @param e the associated event object
     */
    void onSupervisorHelpCancelled(OnSupervisorHelpCancelledEvent e);

    /**
     * Invoked when an agent requests the assistance of his supervisor.
     * 
     * @param e the associated event object
     */
    void onSupervisorHelpRequested(OnSupervisorHelpRequestedEvent e);
    
    
    /**
     * Invoked when an agent activate or deactivate skills.
     * 
     * @param e the associated event object
     */
    void onAgentSkillChanged(OnAgentSkillChangedEvent e);
}
