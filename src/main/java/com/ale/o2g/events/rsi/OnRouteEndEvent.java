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

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.rsi.RoutingReason;

/**
 * This event is sent from a Routing point to close a route session (routing crid is no longer valid).
 * @hidden
 */
public class OnRouteEndEvent extends O2GEvent {

    private String rsiPoint;
    private String routeCrid;
    private String routedCallRef;
    private RoutingReason reason;
    
    /**
     * Returns the RSI point extension number.
     * @return the rsiPoint
     */
    public final String getRsiPoint() {
        return rsiPoint;
    }
    
    
    /**
     * Returns the routing session identifier.
     * @return the routing session unique identifier.
     * @see com.ale.o2g.types.rsi.RouteRequest RouteRequest
     */
    public final String getRouteCrid() {
        return routeCrid;
    }
    
    /**
     * Returns the routed call reference.
     * @return the routed call reference.
     */
    public final String getRoutedCallRef() {
        return routedCallRef;
    }
    
    /**
     * Returns the routing reason.
     * @return the reason.
     */
    public final RoutingReason getReason() {
        return reason;
    }


    protected OnRouteEndEvent() {
    }
}
