/*
* Copyright 2024 ALE International
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
package com.ale.o2g.events.ccp;

import java.util.EventListener;

/**
 * {@code CallCenterPilotEventListener} defines the interface for an object that
 * listens to call center pilot notifications. The class that is interested in
 * processing call center pilot events implements this interface(and all the
 * methods it contains) or extends the {@code CallCenterPilotEventAdapter}
 * (overriding only the methods of interest). The listener object created from
 * that class is then registered with the session, using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a call center pilot event occurs the
 * relevant method in the listener object is invoked.
 * 
 * @since 2.7
 * 
 */
public interface CallCenterPilotEventListener extends EventListener {

    /**
     * Invoked when the a new call arrives on a CCD pilot.
     * 
     * @param e the associated event object
     */
    void onPilotCallCreated(OnPilotCallCreatedEvent e);

    /**
     * Invoked when a CCD call is routed in a Queue.
     * 
     * @param e the associated event object
     */
    void onPilotCallQueued(OnPilotCallQueuedEvent e);

    /**
     * Invoked when a CCD call has been removed from the queue. Either because is
     * has been distributed, or abandonned, or is rerouted in case of queue
     * overflow.
     * 
     * @param e the associated event object
     */
    void onPilotCallRemoved(OnPilotCallRemovedEvent e);
}
