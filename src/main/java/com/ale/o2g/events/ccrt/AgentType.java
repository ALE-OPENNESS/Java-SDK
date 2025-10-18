/*
* Copyright 2025 ALE International
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
package com.ale.o2g.events.ccrt;

import com.google.gson.annotations.SerializedName;

/**
 * {@code AgentType} represents the possible types of an agent.
 * <p>
 * An agent can be either a {@link #NORMAL normal} human agent or an {@link #IVR IVR}
 * (Interactive Voice Responder) agent.
 *
 * @see com.ale.o2g.events.ccrt.OnPGAgentRtiChangedEvent
 * @since 2.7.4
 */
public enum AgentType {
    
    /** Normal human agent. */
    @SerializedName(value = "Normal")
    NORMAL,

    /** IVR (Interactive Voice Responder) agent. */
    IVR
}
