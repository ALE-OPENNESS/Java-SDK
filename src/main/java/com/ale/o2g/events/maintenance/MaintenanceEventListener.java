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
package com.ale.o2g.events.maintenance;

import java.util.EventListener;

/**
 * {@code MaintenanceEventListener} defines the interface for objects that
 * listen to maintenance-related system notifications from the PBX.
 * <p>
 * Classes interested in processing maintenance events should either:
 * <ul>
 *     <li>Implement this interface (overriding all methods), or</li>
 *     <li>Extend {@link MaintenanceEventAdapter} and override only the
 *         methods of interest.</li>
 * </ul>
 * <p>
 * A listener instance created from such a class should be registered with
 * the session using the {@code listenEvents} method. When a maintenance
 * event occurs - such as a change in the connection state with the PBX or
 * a license status change - the corresponding method in the listener is invoked.
 *
 * @see MaintenanceEventAdapter
 */
public interface MaintenanceEventListener extends EventListener {

    /**
     * Invoked when the CTI link is down.
     *
     * @param e the event containing details about the CTI link down
     */
    void onCtiLinkDown(OnCtiLinkDownEvent e);

    /**
     * Invoked when the CTI link is reestablished.
     *
     * @param e the event containing details about the CTI link restoration
     */
    void onCtiLinkUp(OnCtiLinkUpEvent e);

    /**
     * Invoked when the PBX link (CMIS) is down.
     *
     * @param e the event containing details about the PBX link down
     * @since 2.7.4
     */
    void onPbxLinkDown(OnPbxLinkDownEvent e);

    /**
     * Invoked when the PBX link (CMIS) is reestablished.
     *
     * @param e the event containing details about the PBX link restoration
     * @since 2.7.4
     */
    void onPbxLinkUp(OnPbxLinkUpEvent e);

    /**
     * Invoked when all data have been fully loaded from an OmniPCX Enterprise system.
     *
     * @param e the event containing details about the completed data load
     */
    void onPbxLoaded(OnPbxLoadedEvent e);
}
