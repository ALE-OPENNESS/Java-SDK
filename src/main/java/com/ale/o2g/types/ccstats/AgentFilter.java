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

import java.util.Set;

/**
 * Filter for selecting agents in Call Center StatisticsData reports.
 * <p>
 * An {@code AgentFilter} allows specifying which statistics to collect for
 * agents and, optionally, statistics broken down by pilots.
 * <p>
 * Instances should be obtained via the {@link Filter#createAgentFilter()}
 * factory method. SDK users do not need to implement this interface directly.
 * 
 * @since 2.7.4
 */
public interface AgentFilter extends Filter {

    /**
     * Returns the set of directory numbers associated with this filter.
     * <p>
     * These numbers identify the agents whose statistics should be collected.
     *
     * @return a {@link Set} of agent directory numbers; never {@code null}, but may
     *         be empty if no numbers have been added
     */
    Set<String> getNumbers();

    /**
     * Adds one or more agent directory numbers to this filter.
     * <p>
     * Once added, the corresponding agents will be included in the scope of
     * statistical reports.
     *
     * @param numbers an array of directory numbers to add; must not be
     *                {@code null}, though it may be empty
     */
    void addNumbers(String[] numbers);

    /**
     * Returns the set of statistics attributes to collect for agents.
     * 
     * @return a set of {@link AgentAttributes}
     */
    Set<AgentAttributes> getAgentAttributes();

    /**
     * Returns the set of statistics attributes to collect for agents by pilot.
     * 
     * @return a set of {@link AgentByPilotAttributes}
     */
    Set<AgentByPilotAttributes> getByPilotAttributes();

    /**
     * Sets the statistics attributes for agents in this filter. Any existing
     * attributes may be replaced or supplemented depending on the implementation.
     * 
     * @param attributes the agent attributes to include
     */
    void setAgentAttributes(AgentAttributes... attributes);

    /**
     * Sets the statistics attributes for agents broken down by pilot in this
     * filter. Any existing attributes may be replaced or supplemented depending on
     * the implementation.
     * 
     * @param attributes the agent-by-pilot attributes to include
     */
    void setAgentByPilotAttributes(AgentByPilotAttributes... attributes);
}
