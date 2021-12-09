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

/**
 * An abstract adapter class for receiving maintenance events. The methods in
 * this class are empty. This class exists as convenience for creating listener
 * objects.
 * <p>
 * Extend this class to create a {@code MaintenanceEvent} listener and override
 * the methods for the events of interest. (If you implement the
 * MaintenanceEventListener interface, you have to define all of the methods in
 * it. This abstract class defines null methods for them all, so you can only
 * have to define methods for events you care about.)
 * 
 * <p>
 * Create a listener object using the extended class and then register it with
 * the session, using the {@code listenEvents} method. When a maintenance event
 * occurs by a change in the connection state with the PBX or a change on the
 * license status, the relevant method in the listener object is invoked.
 * @see MaintenanceEventListener
 */
public abstract class MaintenanceEventAdapter implements MaintenanceEventListener {

	@Override
	public void onCtiLinkDown(OnCtiLinkDownEvent ev) {
	}

	@Override
	public void onCtiLinkUp(OnCtiLinkUpEvent ev) {
	}

	@Override
	public void onPbxLoaded(OnPbxLoadedEvent ev) {
	}

}
