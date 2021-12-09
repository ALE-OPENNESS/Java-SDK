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
 * {@code MaintenanceEventListener} defines the interface for an object that
 * listens to maintenance (system) notifications. The class that is interested
 * in processing maintenance events implements this interface(and all the
 * methods it contains) or extends the {@code MaintenanceAdpater} (overriding
 * only the methods of interest). The listener object created from that class is
 * then registered with the session, using the {@code listenEvents} method. When
 * a maintenance event occurs by a change in the connection state with the PBX
 * or a change on the license status, the relevant method in the listener object
 * is invoked.
 * 
 * @see MaintenanceEventAdapter
 */
public interface MaintenanceEventListener extends EventListener {

	/**
	 * Invoked when the CTI link is down.
	 * @param e the event to be processed
	 */
	void onCtiLinkDown(OnCtiLinkDownEvent e);

	/**
	 * Invoked when the CTI link is reestablished.
	 * @param e the event to be processed
	 */
	void onCtiLinkUp(OnCtiLinkUpEvent e);

	/**
	 * Invoked when datas are fully loaded from an OmniPcx Enterprise.
	 * @param e the event to be processed
	 */
	void onPbxLoaded(OnPbxLoadedEvent e);
}
