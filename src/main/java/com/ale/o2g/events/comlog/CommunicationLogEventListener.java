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
package com.ale.o2g.events.comlog;

import java.util.EventListener;

/**
 * {@code CommunicationLogEventListener} defines the interface for an object
 * that listens to communication log notifications. 
 * <p>
 * A class interested in processing communication log events can either:
 * <ul>
 *   <li>implement this interface (and all of its methods), or</li>
 *   <li>extend the {@link com.ale.o2g.events.comlog.CommunicationLogEventAdapter}
 *       class, overriding only the methods of interest.</li>
 * </ul>
 * <p>
 * The listener object created from that class is then registered with the
 * session, using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a communication log event occurs, the
 * corresponding method in the listener object is invoked.
 *
 * @see com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * @see com.ale.o2g.events.comlog.CommunicationLogEventAdapter
 */
public interface CommunicationLogEventListener extends EventListener {

    /**
     * Called when a new communication log entry has been created.
     *
     * @param e the event object containing details about the created record
     */
    void onComRecordCreated(OnComRecordCreatedEvent e);

    /**
     * Called when one or more communication log records have been modified.
     *
     * @param e the event object containing details about the modified records
     */
    void onComRecordModified(OnComRecordModifiedEvent e);

    /**
     * Called when one or more communication log records have been deleted.
     *
     * @param e the event object containing details about the deleted records
     */
    void onComRecordsDeleted(OnComRecordsDeletedEvent e);

    /**
     * Called when one or more unanswered communication log records have been acknowledged.
     *
     * @param e the event object containing details about the acknowledged records
     */
    void onComRecordsAck(OnComRecordsAckEvent e);

    /**
     * Called when one or more unanswered communication log records have been unacknowledged.
     *
     * @param e the event object containing details about the unacknowledged records
     */
    void onComRecordsUnAck(OnComRecordsUnAckEvent e);
}
