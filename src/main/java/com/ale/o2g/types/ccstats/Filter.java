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
package com.ale.o2g.types.ccstats;

import com.ale.o2g.internal.types.ccstats.AgentFilterImpl;
import com.ale.o2g.internal.types.ccstats.PilotFilterImpl;

/**
 * Base interface for filters used in the Call Center StatisticsData SDK.
 * <p>
 * A filter defines criteria for selecting objects (such as agents or pilots) to
 * include in statistical reports.
 * <p>
 * This interface also serves as a factory for creating concrete filter
 * instances. Users can obtain pre-built filters without directly instantiating
 * the underlying classes:
 * 
 * <pre>
 * {
 *     &#64;code
 *     AgentFilterImpl agentFilter = Filter.createAgentFilter();
 *     PilotFilterImpl pilotFilter = Filter.createPilotFilter();
 * }
 * </pre>
 * 
 * @since 2.7.4
 */
public interface Filter {

    /**
     * Creates a new filter for selecting agents.
     * 
     * @return a new {@link AgentFilterImpl} instance
     */
    static AgentFilter createAgentFilter() {
        return new AgentFilterImpl();
    }

    /**
     * Creates a new filter for selecting pilots.
     * 
     * @return a new {@link PilotFilterImpl} instance
     */
    static PilotFilter createPilotFilter() {
        return new PilotFilterImpl();
    }
}
