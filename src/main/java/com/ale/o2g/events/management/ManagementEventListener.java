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
package com.ale.o2g.events.management;

import java.util.EventListener;

/**
 * {@code ManagementEventListener} defines the interface for an object that
 * listens to Management notifications. The class that is interested in
 * processing management events implements this interface(and all the methods it
 * contains) or extends the {@code ManagementEventAdapter} (overriding only the
 * methods of interest). The listener object created from that class is then
 * registered with the session, using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a management event occurs the relevant
 * method in the listener object is invoked.
 *
 */
public interface ManagementEventListener extends EventListener {

    /**
     * Invoked when an object has been created.
     * 
     * @param e the associated event object
     */
    void pbxObjectInstanceCreated(OnPbxObjectInstanceCreatedEvent e);

    /**
     * Invoked when an object has been modified.
     * 
     * @param e the associated event object
     */
    void pbxObjectInstanceModified(OnPbxObjectInstanceModifiedEvent e);

    /**
     * Invoked when an object has been deleted.
     * 
     * @param e the associated event object
     */
    void pbxObjectInstanceDeleted(OnPbxObjectInstanceDeletedEvent e);
}
