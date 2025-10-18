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
package com.ale.o2g.types.ccrt;

import java.util.Collection;

/**
 * Represents a collection of CCD objects on which real-time information is available.
 * <p>
 * This class groups CCD objects by type, including agents, pilots, queues, and processing groups
 * (both agent and other). Each object is represented by an {@link RtiObjectIdentifier RtiObjectIdentifier}.
 * <p>
 * Instances of this class are typically returned by the {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}
 * to provide the list of available CCD objects for which real-time data can be retrieved.
 * <p>
 * A {@link Filter} can be created from an {@code RtiObjects} instance using
 * {@link #createFilter()} to subscribe only to the selected objects in real-time event monitoring.
 *
 * @see RtiObjectIdentifier
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see Filter
 * @since 2.7.4
 */
public class RtiObjects {

    private Collection<RtiObjectIdentifier> agents;
    private Collection<RtiObjectIdentifier> pilots;
    private Collection<RtiObjectIdentifier> queues;
    private Collection<RtiObjectIdentifier> pgAgents;
    private Collection<RtiObjectIdentifier> pgOthers;

    /**
     * Returns the collection of CCD agents.
     *
     * @return the agents
     */
    public final Collection<RtiObjectIdentifier> getAgents() {
        return agents;
    }

    /**
     * Returns the collection of CCD pilots.
     *
     * @return the pilots
     */
    public final Collection<RtiObjectIdentifier> getPilots() {
        return pilots;
    }

    /**
     * Returns the collection of CCD queues.
     *
     * @return the queues
     */
    public final Collection<RtiObjectIdentifier> getQueues() {
        return queues;
    }

    /**
     * Returns the collection of agent processing groups.
     *
     * @return the agent processing groups
     */
    public final Collection<RtiObjectIdentifier> getAgentProcessingGroups() {
        return pgAgents;
    }

    /**
     * Returns the collection of other (non-agent) processing groups.
     *
     * @return the other processing groups
     */
    public final Collection<RtiObjectIdentifier> getOtherProcessingGroups() {
        return pgOthers;
    }

    /**
     * Protected default constructor for internal use.
     * Instances are typically created and populated by the real-time service.
     */
    protected RtiObjects() {
    }

    /**
     * Creates a {@link Filter} object initialized with all CCD objects contained in this instance.
     * <p>
     * The resulting filter can be used to create a real-time context for event monitoring
     * with {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}. It includes all agents, pilots,
     * queues, and processing groups present in this {@code RtiObjects} instance.
     * <p>
     * Example usage:
     * <pre><code>
     * CallCenterRealtimeService rtiService = session.getCallCenterRealtimeService();
     * 
     * RtiObjects rtiObjects = rtiService.getRtiObjects();
     * AbstractFilter filter = rtiObjects.createFilter();
     * 
     * // Use the filter to create a real-time context
     * Context context = new Context(30, 5, filter);
     * rtiService.setContext(context);
     * rtiService.start();
     * </code></pre>
     *
     * @return a {@code AbstractFilter} containing all objects from this {@code RtiObjects} instance
     */
    public Filter createFilter() {
        Filter filter = new Filter();

        if ((agents != null) && (!agents.isEmpty())) {
            filter.setAgentNumbers(agents.stream().map(RtiObjectIdentifier::getNumber).toArray(String[]::new));
            filter.setAgentAttributes(AgentAttributes.ALL);
        }

        if ((pilots != null) && (!pilots.isEmpty())) {
            filter.setPilotNumbers(pilots.stream().map(RtiObjectIdentifier::getNumber).toArray(String[]::new));
            filter.setPilotAttributes(PilotAttributes.ALL);
        }

        if ((queues != null) && (!queues.isEmpty())) {
            filter.setQueueNumbers(queues.stream().map(RtiObjectIdentifier::getNumber).toArray(String[]::new));
            filter.setQueueAttributes(QueueAttributes.ALL);
        }

        if ((pgAgents != null) && (!pgAgents.isEmpty())) {
            filter.setAgentProcessingGroupNumbers(pgAgents.stream().map(RtiObjectIdentifier::getNumber).toArray(String[]::new));
            filter.setAgentProcessingGroupAttributes(AgentProcessingGroupAttributes.ALL);
        }

        if ((pgOthers != null) && (!pgOthers.isEmpty())) {
            filter.setOtherProcessingGroupNumbers(pgOthers.stream().map(RtiObjectIdentifier::getNumber).toArray(String[]::new));
            filter.setOtherProcessingGroupAttributes(OtherProcessingGroupAttributes.ALL);
        }

        return filter;
    }
}
