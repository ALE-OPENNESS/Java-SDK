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
package com.ale.o2g.events.rsi;

import java.util.EventListener;

/**
 * {@code RsiEventListener} defines the interface for an object that listens to
 * Rsi notifications. The class that is interested in processing rsi events
 * implements this interface(and all the methods it contains) or extends the
 * {@code RsiEventAdapter} (overriding only the methods of interest). The
 * listener object created from that class is then registered with the session,
 * using the
 * {@linkplain com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 * Session.listenEvents} method. When a rsi event occurs the relevant method in
 * the listener object is invoked.
 * @hidden
 */
public interface RsiEventListener extends EventListener {

    /**
     * Invoked when a data collection has ended.
     * 
     * @param e the associated event object
     */
    void onDigitCollected(OnDigitCollectedEvent e);

    /**
     * Invoked by te rsi point to close a route session.
     * 
     * @param e the associated event object
     */
    void onRouteEnd(OnRouteEndEvent e);

    /**
     * Invoked when the rsi point request a route to the application.
     * 
     * @param e the associated event object containing the routeRequest
     */
    void onRouteRequest(OnRouteRequestEvent e);

    /**
     * Invoked when a tone generation is started.
     * 
     * @param e the associated event object
     */
    void onToneGeneratedStart(OnToneGeneratedStartEvent e);

    /**
     * Invoked when a tone generation is stopped.
     * 
     * @param e the associated event object
     */
    void onToneGeneratedStop(OnToneGeneratedStopEvent e);
}
