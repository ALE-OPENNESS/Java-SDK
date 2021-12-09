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
package com.ale.o2g.events.routing;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.routing.RoutingState;

/**
 * An event which indicates that a change has been done on a user routing
 * configuration. The event is passed to every {@code RoutingEventListener}
 * object that registered to receive such events using the session's
 * {@code listenEvents} method.
 * 
 * @see RoutingEventListener
 * @see com.ale.o2g.Subscription Subscription
 */
public class OnRoutingStateChangedEvent extends O2GEvent {
	private String loginName;
	private RoutingState routingState;

	protected OnRoutingStateChangedEvent(String eventName, String loginName, RoutingState routingState) {
		super(eventName);
		this.loginName = loginName;
		this.routingState = routingState;
	}

	/**
	 * Return the user login name for which this event is raised.
	 * 
	 * @return the user loginName
	 */
	public final String getLoginName() {
		return loginName;
	}

	/**
	 * Return the routing state associated to this event
	 * 
	 * @return the {@link RoutingState RoutingState}
	 */
	public final RoutingState getRoutingState() {
		return routingState;
	}

}
