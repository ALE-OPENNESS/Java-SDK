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
package com.ale.o2g.events.users;

import java.util.EventListener;

/**
 * {@code UsersEventListener} defines the interface for an object that listens
 * to users notifications. The class that is interested in processing users
 * events implements this interface (and all the methods it contains) or extends
 * the {@code UsersEventAdapter} (overriding only the methods of interest). The
 * listener object created from that class is then registered with the session,
 * using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a users event occurs the relevant method
 * in the listener object is invoked.
 */
public interface UsersEventListener extends EventListener {

    /**
     * Invoked when a user has been created.
     * 
     * @param e the related event object
     */
    void onUserCreated(OnUserCreatedEvent e);

    /**
     * Invoked when a user has been deleted.
     * 
     * @param e the related event object
     */
    void onUserDeleted(OnUserDeletedEvent e);

    /**
     * Invoked when a user has been modified.
     * 
     * @param e the related event object
     */
    void onUserInfoChanged(OnUserInfoChangedEvent e);
}
