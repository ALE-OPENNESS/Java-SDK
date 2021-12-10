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
package com.ale.o2g.types.rsi;

/**
 * {@code RouteSession} represents a route request session between a RSI point
 * and an application.
 * <p>
 * A route session is initiated by a RSI point by sending a
 * {@linkplain com.ale.o2g.events.rsi.OnRouteRequestEvent OnRouteRequestEvent}.
 * The application selects a route and answer the request by calling
 * {@linkplain com.ale.o2g.RsiService#routeSelect RsiService.routeSelect}
 */
public class RouteSession {

    private String routeCrid;
    private String caller;
    private String called;
    private String routedCallRef;

    /**
     * Returns this Route session unique idnetifier.
     * 
     * @return the route crid, a unique route session identifier.
     */
    public final String getRouteCrid() {
        return routeCrid;
    }

    /**
     * Returns the call extension number.
     * 
     * @return the caller number.
     */
    public final String getCaller() {
        return caller;
    }

    /**
     * Returns the called number.
     * 
     * @return the called number.
     */
    public final String getCalled() {
        return called;
    }

    /**
     * Returns the routed call reference.
     * 
     * @return the routed call reference.
     */
    public final String getRoutedCallRef() {
        return routedCallRef;
    }

    protected RouteSession() {
        
    }
}
