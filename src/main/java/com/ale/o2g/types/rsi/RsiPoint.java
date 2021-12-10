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
 * {@code RsiPoint} represents a RSI point. When a call is receive by a RSI
 * routing point, a {@linkplain com.ale.o2g.events.rsi.OnRouteRequestEvent
 * OnRouteRequestEvent} is send to the application.
 */
public class RsiPoint {

    private String number;
    private String name;
    private int node;
    private boolean registered;

    /**
     * Returns this rsi point extension number.
     * 
     * @return the rsi point extension number.
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Returns the name of this rsi point.
     * 
     * @return the rsi point name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the OmniPcxEnterprise node on which this RSI point is configured.
     * 
     * @return the OmniPcxEnterprise node.
     */
    public final int getNode() {
        return node;
    }

    /**
     * Returns whether this rsi point is registered.
     * 
     * @return {@code true} if the rsi point is regsitered; {@code false} otherwise;
     */
    public final boolean isRegistered() {
        return registered;
    }

    protected RsiPoint() {
    }
}
