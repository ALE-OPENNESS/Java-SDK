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
package com.ale.o2g.events;

/**
 * {@code EventPackage} represents a set of notification grouped by domain and
 * that can be used to subscribe event in O2G server.
 * 
 * @see com.ale.o2g.Subscription Subscription.
 *
 */
public class EventPackage {

    private String value;

    private EventPackage(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * The telephony event package.
     */
    public final static EventPackage TELEPHONY = new EventPackage("telephony");
    
    /**
     * The event summary event package.
     */
    public final static EventPackage EVENT_SUMMARY = new EventPackage("eventSummary");
    
    /**
     * The call log event package.
     */
    public final static EventPackage CALLLOG = new EventPackage("unifiedComLog");
    
    /**
     * The user event package.
     */
    public final static EventPackage USER = new EventPackage("user");
    
    /**
     * The users event package.
     */
    public final static EventPackage USERS = new EventPackage("userManagement");
    
    /**
     * The routing event package.
     */
    public final static EventPackage ROUTING = new EventPackage("routingManagement");
    
    /**
     * The call center agent event package.
     */
    public final static EventPackage AGENT = new EventPackage("agent");
    
    /**
     * The call center pilot event package.
     */
    public final static EventPackage PILOT = new EventPackage("pilot");
    
    /**
     * The rsi event package.
     */
//    public final static EventPackage RSI = new EventPackage("rsi");
    
    /**
     * The maintenance event package.
     */
    public final static EventPackage MAINTENANCE = new EventPackage("system");
    
    /**
     * The management event package.
     */
    public final static EventPackage MANAGEMENT = new EventPackage("pbxManagement");
}
