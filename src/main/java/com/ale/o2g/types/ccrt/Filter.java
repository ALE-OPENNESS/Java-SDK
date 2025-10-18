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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.SerializedName;

/**
 * The {@code AbstractFilter} class specifies which CCD objects and their attributes
 * should be included in real-time eventing.
 * <p>
 * For each category of CCD objects (agents, pilots, queues, agent processing groups,
 * other processing groups), this class maintains a filter that defines:
 * <ul>
 *     <li>The specific objects to monitor (directory numbers)</li>
 *     <li>The attributes of each object to include in the real-time notifications</li>
 * </ul>
 * <p>
 * Typical usage:
 * <pre><code>
 * AbstractFilter filter = new AbstractFilter();
 * 
 * // Monitor specific agents with selected attributes
 * filter.setAgentNumbers(new String[] { "60119", "60120" });
 * filter.setAgentAttributes(AgentAttributes.PrivateCallsTotalDuration, AgentAttributes.LogonDate);
 * 
 * // Monitor queues with selected attributes
 * filter.setQueueNumbers(new String[] { "1001", "1002" });
 * filter.setQueueAttributes(QueueAttributes.WaitingCalls, QueueAttributes.AverageWaitTime);
 * 
 * // Use the filter to create a real-time context
 * Context context = new Context(30, 5, filter);
 * CallCenterRealtimeService rtiService = session.getCallCenterRealtimeService();
 * rtiService.setContext(context);
 * rtiService.start();
 * </code></pre>
 * 
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see RtiObjects
 * @since 2.7.4
 */
public class Filter {

    private static abstract class AbstractRtiFilter<T> {

        private Set<String> numbers = new HashSet<String>();
        private Set<T> attributes  = new HashSet<T>();
                
        public void addNumbers(String[] numbers) {
            Collections.addAll(this.numbers, numbers);
        }
        
        public void addAttributes(T[] attributes) {
            Collections.addAll(this.attributes, attributes);
        }
    }    
    
    
    static private class AgentRtiFilter extends AbstractRtiFilter<AgentAttributes> {
    }

    static private class PilotRtiFilter extends AbstractRtiFilter<PilotAttributes> {
    }

    static private class QueueRtiFilter extends AbstractRtiFilter<QueueAttributes> {
    }

    static private class AgentProcessingGroupRtiFilter extends AbstractRtiFilter<AgentProcessingGroupAttributes> {
    }

    static private class OtherProcessingGroupRtiFilter extends AbstractRtiFilter<OtherProcessingGroupAttributes> {
    }

    private AgentRtiFilter agentFilter;
    private PilotRtiFilter pilotFilter;
    private QueueRtiFilter queueFilter;

    @SerializedName(value = "teamFilter")
    private AgentProcessingGroupRtiFilter agentProcessingGroupFilter;

    @SerializedName(value = "otherPgFilter")
    private OtherProcessingGroupRtiFilter otherProcessingGroupFilter;

    /**
     * Constructs a new empty RTI filter. No objects or attributes are included initially.
     */
    public Filter() {
        this.agentFilter = null;
        this.pilotFilter = null;
        this.queueFilter = null;
        this.agentProcessingGroupFilter = null;
        this.otherProcessingGroupFilter = null;
    }

    private AgentRtiFilter getAgentFilter() {
        if (this.agentFilter == null) {
            this.agentFilter = new AgentRtiFilter();
        }

        return this.agentFilter;
    }

    private PilotRtiFilter getPilotFilter() {
        if (this.pilotFilter == null) {
            this.pilotFilter = new PilotRtiFilter();
        }

        return this.pilotFilter;
    }

    private QueueRtiFilter getQueueFilter() {
        if (this.queueFilter == null) {
            this.queueFilter = new QueueRtiFilter();
        }

        return this.queueFilter;
    }

    private AgentProcessingGroupRtiFilter getAgentProcessingGroupFilter() {
        if (this.agentProcessingGroupFilter == null) {
            this.agentProcessingGroupFilter = new AgentProcessingGroupRtiFilter();
        }

        return this.agentProcessingGroupFilter;
    }

    private OtherProcessingGroupRtiFilter getOtherProcessingGroupFilter() {
        if (this.otherProcessingGroupFilter == null) {
            this.otherProcessingGroupFilter = new OtherProcessingGroupRtiFilter();
        }

        return this.otherProcessingGroupFilter;
    }

 // --- Agent methods ---

    /**
     * Sets the attributes of agents to include in real-time eventing.
     * <p>
     * Only the specified attributes of the selected agents will be included
     * in the real-time notifications.
     *
     * @param attributes the agent attributes to monitor
     */
    public void setAgentAttributes(AgentAttributes... attributes) {
        getAgentFilter().addAttributes(attributes);
    }

    /**
     * Sets the directory numbers of agents to include in real-time eventing.
     * <p>
     * Only events related to these agents will be sent.
     *
     * @param numbers the agent directory numbers to monitor
     */
    public void setAgentNumbers(String[] numbers) {
        getAgentFilter().addNumbers(numbers);
    }
    

    // --- Pilot methods ---

    /**
     * Sets the attributes of pilots to include in real-time eventing.
     * <p>
     * Only the specified attributes of the selected pilots will be included
     * in the real-time notifications.
     *
     * @param attributes the pilot attributes to monitor
     */
    public void setPilotAttributes(PilotAttributes... attributes) {
        getPilotFilter().addAttributes(attributes);
    }

    /**
     * Sets the directory numbers of pilots to include in real-time eventing.
     * <p>
     * Only events related to these pilots will be sent.
     *
     * @param numbers the pilot directory numbers to monitor
     */
    public void setPilotNumbers(String[] numbers) {
        getPilotFilter().addNumbers(numbers);
    }

    // --- Queue methods ---

    /**
     * Sets the attributes of queues to include in real-time eventing.
     * <p>
     * Only the specified attributes of the selected queues will be included
     * in the real-time notifications.
     *
     * @param attributes the queue attributes to monitor
     */
    public void setQueueAttributes(QueueAttributes... attributes) {
        getQueueFilter().addAttributes(attributes);
    }

    /**
     * Sets the directory numbers of queues to include in real-time eventing.
     * <p>
     * Only events related to these queues will be sent.
     *
     * @param numbers the queue directory numbers to monitor
     */
    public void setQueueNumbers(String[] numbers) {
        getQueueFilter().addNumbers(numbers);
    }

    // --- Agent Processing Group methods ---

    /**
     * Sets the attributes of agent processing groups to include in real-time eventing.
     * <p>
     * Only the specified attributes of the selected processing groups will be included
     * in the real-time notifications.
     *
     * @param attributes the agent processing group attributes to monitor
     */
    public void setAgentProcessingGroupAttributes(AgentProcessingGroupAttributes... attributes) {
        getAgentProcessingGroupFilter().addAttributes(attributes);
    }

    /**
     * Sets the directory numbers of agent processing groups to include in real-time eventing.
     * <p>
     * Only events related to these processing groups will be sent.
     *
     * @param numbers the agent processing group directory numbers to monitor
     */
    public void setAgentProcessingGroupNumbers(String[] numbers) {
        getAgentProcessingGroupFilter().addNumbers(numbers);
    }

    // --- Other Processing Group methods ---

    /**
     * Sets the attributes of other processing groups to include in real-time eventing.
     * <p>
     * Only the specified attributes of the selected processing groups will be included
     * in the real-time notifications.
     *
     * @param attributes the other processing group attributes to monitor
     */
    public void setOtherProcessingGroupAttributes(OtherProcessingGroupAttributes... attributes) {
        getOtherProcessingGroupFilter().addAttributes(attributes);
    }

    /**
     * Sets the directory numbers of other processing groups to include in real-time eventing.
     * <p>
     * Only events related to these processing groups will be sent.
     *
     * @param numbers the other processing group directory numbers to monitor
     */
    public void setOtherProcessingGroupNumbers(String... numbers) {
        getOtherProcessingGroupFilter().addNumbers(numbers);
    }
}
