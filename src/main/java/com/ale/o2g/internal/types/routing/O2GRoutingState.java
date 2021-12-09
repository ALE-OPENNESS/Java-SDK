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
package com.ale.o2g.internal.types.routing;

import java.util.List;

import com.ale.o2g.types.routing.Destination;
import com.ale.o2g.types.routing.DndState;
import com.ale.o2g.types.routing.Forward;
import com.ale.o2g.types.routing.Overflow;
import com.ale.o2g.types.routing.RoutingState;

/**
 *
 */
public class O2GRoutingState {

	private List<PresentationRoute> presentationRoutes;
	private List<ForwardRoute> forwardRoutes;
	private List<OverflowRoute> overflowRoutes;
	private DndState dndState;

	public RoutingState toRoutingState() {

		Forward forward = null;
		if ((forwardRoutes != null) && (forwardRoutes.size() > 0)) {
			forward = forwardRoutes.get(0).toForward();
		}
		else {
			forward = new Forward(Destination.NONE, null, null) {};
		}

		Overflow overflow = null;
		if ((overflowRoutes != null) && (overflowRoutes.size() > 0)) {
			overflow = overflowRoutes.get(0).toOverflow();
		}
		else {
			overflow = new Overflow(Destination.NONE, null) {};
		}

		return new RoutingState(getRemoteExtensionActivation(), forward, overflow, dndState) {};
	}

	private boolean getRemoteExtensionActivation() {

		if ((presentationRoutes == null) || (presentationRoutes.size() == 0)) {
			return false;
		}

		return presentationRoutes.stream().filter(p -> p.isMobileRouteActivate()).findFirst().isPresent();
	}
}
