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
package com.ale.o2g.types.telephony.call.acd;

/**
 * {@code Info} represents the acd information for this call
 */
public class Info {
    private int queueWaitingTime;
    private int globalWaitingTime;
    private String agentGroup;
    private boolean local;
    
    
    /**
     * Returns the waiting time in a queue from which the call has been distributed.
     * @return the estimated queue waiting time.
     */
    public final int getQueueWaitingTime() {
        return queueWaitingTime;
    }
    
    /**
     * Returns the global waiting time in the CCD.
     * @return the estimated global waiting time.
     */
    public final int getGlobalWaitingTime() {
        return globalWaitingTime;
    }
    
    /**
     * Returns the agent group the agent who answer the call is logged in.
     * @return the agent group.
     */
    public final String getAgentGroup() {
        return agentGroup;
    }
    
    /**
     * Return whether it's a local acd call
     * @return {@code true} if it is a local acd call; {@code false} otherwise.
     */
    public final boolean isLocal() {
        return local;
    }

    protected Info() {
    }
    
    
}
