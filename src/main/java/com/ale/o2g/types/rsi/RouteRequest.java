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
 * {@code RouteRequest} represents a routing request done from a RSI point to the listening application.
 * @hidden
 */
public class RouteRequest {

    private String routeCrid;
    private String caller;
    private String callingName;
    private RoutingCallerType callerType;
    private String called;
    private String routedCallRef;
    private int nbOverflows;
    private RoutingReason reason;
    
    
    /**
     * Returns the routing session identifier.
     * @return the routing session identifier.
     */
    public final String getRouteCrid() {
        return routeCrid;
    }
    
    /**
     * Returns the caller's extension number.
     * @return the caller number.
     */
    public final String getCaller() {
        return caller;
    }
    
    
    /**
     * Returns the caller's name.
     * @return the caller's name.
     */
    public final String getCallingName() {
        return callingName;
    }
    
    
    /**
     * Returns the caller type, whether the caller is internal, or external.
     * @return the caller type.
     */
    public final RoutingCallerType getCallerType() {
        return callerType;
    }
    
    
    /**
     * Returns the called number
     * @return the called number
     */
    public final String getCalled() {
        return called;
    }
    
    
    /**
     * Returns the call reference of the call for which the route request is made.
     * @return the routed call reference.
     */
    public final String getRoutedCallRef() {
        return routedCallRef;
    }
    
    
    /**
     * Returns the maximum number of route requests that can be done before considering an error. 
     * @return the nbOverflows the max number of route requests.
     */
    public final int getNbOverflows() {
        return nbOverflows;
    }
    
    
    /**
     * Returns the reason of this route request.
     * @return the reason
     */
    public final RoutingReason getReason() {
        return reason;
    }
    
    protected RouteRequest() {
        
    }
}
