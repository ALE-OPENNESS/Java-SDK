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
package com.ale.o2g.events.telephony;

import java.util.EventListener;

/**
 * {@code TelephonyEventListener} defines the interface for an object that
 * listens to telephony notifications. The class that is interested in
 * processing telephony events implements this interface (and all the methods it
 * contains) or extends the {@code TelephonyEventAdapter} (overriding only the
 * methods of interest). The listener object created from that class is then
 * registered with the session, using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a telephony event occurs the relevant
 * method in the listener object is invoked.
 */
public interface TelephonyEventListener extends EventListener {

    /**
     * Invoked when a call has been removed (hang up, transfer...).
     * 
     * @param e the related event object.
     */
    void onCallRemoved(OnCallRemovedEvent e);

    /**
     * Invoked when the users's state has been modified (Free, Busy, ...).
     * 
     * @param e the related event object.
     */
    void onUserStateModified(OnUserStateModifiedEvent e);

    /**
     * Invoked when an existing call has been modified. Modification of a call can
     * be triggered for various reason: changes on legs, on participants, changes on
     * states, ...
     * 
     * @param e the related event object.
     */
    void onCallModified(OnCallModifiedEvent e);

    /**
     * Invoked when a new call has been created.
     * 
     * @param e the related event object.
     */
    void onCallCreated(OnCallCreatedEvent e);

    /**
     * Invoked when the telephonic state of a user has changed. it can be a change
     * on the user's calls or on teh user's devices.
     * 
     * @param e the related event object.
     */
    void onTelephonyState(OnTelephonyStateEvent e);

    /**
     * Invoked when a device state has changed.
     * 
     * @param e the related event object.
     */
    void onDeviceStateModified(OnDeviceStateModifiedEvent e);

    /**
     * when a user dynamic state has changed. For exemple the user is logged in a
     * hunting group.
     * 
     * @param e the related event object.
     */
    void onDynamicStateChanged(OnDynamicStateChangedEvent e);
}
