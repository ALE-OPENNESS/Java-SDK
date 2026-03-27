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

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.common.ServiceState;

/**
 * Event delivered by {@link CallCenterRealtimeEventListener#onPGAgenRtiChanged(OnPGAgentRtiChangedEvent) 
 * onPGAgentRtiChanged(OnPGAgentRtiChangedEvent)} whenever the attributes of one or more CCD agent 
 * processing groups change.
 * <p>
 * This event contains only the attributes that have changed since the previous notification.
 * It is sent periodically according to the {@link com.ale.o2g.types.ccrt.Context Context} configuration in 
 * {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}.
 * <p>
 * Typical usage:
 * <pre><code>
 * {@literal @}Override
 * public void onPGAgentRtiChanged(OnPGAgentRtiChangedEvent e) {
 *     int nbWithdrawnAgents = e.getNbOfWithdrawnAgents();
 *     // ...
 * }
 * </code></pre>
 * 
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see com.ale.o2g.types.ccrt.Context
 * @see CallCenterRealtimeEventListener#onPGAgenRtiChanged(OnPGAgentRtiChangedEvent)
 * @since 2.7.4
 */
public class OnPGAgentRtiChangedEvent extends O2GEvent {

    private String name;

    private String number;
    private AgentProcessingGroupType type;
    private ServiceState state;
    private int nbOfWithdrawnAgents;
    private int nbOfAgentsInPrivateCall;
    private int nbOfAgentsInACDCall;
    private int nbOfAgentsInACDRinging;
    private int nbOfAgentsInACDConv;
    private int nbOfAgentsInWrapupAndTransaction;
    private int nbOfAgentsInPause;
    private int nbOfBusyAgents;
    private int nbOfLoggedOnAgents;
    private int nbOfFreeAgents;
    private int nbOfIdleAgents;
    private int nbOfLoggedOnAndNotWithdrawnAgents;
    private int incomingTraffic;
    private int consolidatedPilotsServiceLevel;
    private int consolidatedPilotsEfficiency;
    private int consolidatedQueuesWaitingTime;
    private int consolidatedQueuesNbOfWaitingCalls;
    private int consolidatedQueuesEWT;
    private int pilotsWorstServiceLevel;
    private int pilotsWorstEfficiency;
    private int pilotsBestServiceLevel;
    private int pilotsBestEfficiency;
    private int queuesLongestWaitingTime;
    private int afeKey;

    /**
     * Returns the name of the agent processing group.
     * 
     * @return the name of the agent processing group
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number identifying the agent processing group.
     * 
     * @return the number identifying the agent processing group
     */
    public String getNumber() {
        return number;
    }

    /**
     * Returns the type of the agent processing group.
     * 
     * @return the type of the agent processing group
     */
    public AgentProcessingGroupType getType() {
        return type;
    }

    /**
     * Returns the current service state of the agent processing group.
     * 
     * @return the current service state of the agent processing group
     */
    public ServiceState getState() {
        return state;
    }

    /**
     * Returns the number of withdrawn agents.
     * 
     * @return the number of withdrawn agents
     */
    public int getNbOfWithdrawnAgents() {
        return nbOfWithdrawnAgents;
    }

    /**
     * Returns the number of agents currently in private call.
     * 
     * @return the number of agents in private call
     */
    public int getNbOfAgentsInPrivateCall() {
        return nbOfAgentsInPrivateCall;
    }

    /**
     * Returns the number of agents currently in ACD call.
     * 
     * @return the number of agents in ACD call
     */
    public int getNbOfAgentsInACDCall() {
        return nbOfAgentsInACDCall;
    }

    /**
     * Returns the number of agents currently in ACD ringing.
     * 
     * @return the number of agents in ACD ringing
     */
    public int getNbOfAgentsInACDRinging() {
        return nbOfAgentsInACDRinging;
    }

    /**
     * Returns the number of agents currently in ACD conversation.
     * 
     * @return the number of agents in ACD conversation
     */
    public int getNbOfAgentsInACDConv() {
        return nbOfAgentsInACDConv;
    }

    /**
     * Returns the number of agents in wrap-up or transaction state.
     * 
     * @return the number of agents in wrap-up and transaction
     */
    public int getNbOfAgentsInWrapupAndTransaction() {
        return nbOfAgentsInWrapupAndTransaction;
    }

    /**
     * Returns the number of agents currently in pause.
     * 
     * @return the number of agents in pause
     */
    public int getNbOfAgentsInPause() {
        return nbOfAgentsInPause;
    }

    /**
     * Returns the number of busy agents (ACD or in private call).
     * 
     * @return the number of busy agents
     */
    public int getNbOfBusyAgents() {
        return nbOfBusyAgents;
    }

    /**
     * Returns the number of agents logged on (total).
     * 
     * @return the number of logged-on agents
     */
    public int getNbOfLoggedOnAgents() {
        return nbOfLoggedOnAgents;
    }

    /**
     * Returns the number of free agents (withdrawn or not).
     * 
     * @return the number of free agents
     */
    public int getNbOfFreeAgents() {
        return nbOfFreeAgents;
    }

    /**
     * Returns the number of idle agents, excluding withdrawn agents.
     * 
     * @return the number of idle agents
     */
    public int getNbOfIdleAgents() {
        return nbOfIdleAgents;
    }

    /**
     * Returns the number of agents logged on who are not withdrawn or free.
     * 
     * @return the number of logged-on and not withdrawn agents
     */
    public int getNbOfLoggedOnAndNotWithdrawnAgents() {
        return nbOfLoggedOnAndNotWithdrawnAgents;
    }

    /**
     * Returns the number of incoming calls during the last minute.
     * 
     * @return the number of incoming calls during the last minute
     */
    public int getIncomingTraffic() {
        return incomingTraffic;
    }

    /**
     * Returns the service level on all pilots possibly serving this processing
     * group.
     * 
     * @return the consolidated pilots service level
     */
    public int getConsolidatedPilotsServiceLevel() {
        return consolidatedPilotsServiceLevel;
    }

    /**
     * Returns the efficiency on all pilots possibly serving this processing group.
     * 
     * @return the consolidated pilots efficiency
     */
    public int getConsolidatedPilotsEfficiency() {
        return consolidatedPilotsEfficiency;
    }

    /**
     * Returns the current waiting time on queues possibly serving this processing
     * group.
     * 
     * @return the consolidated queues waiting time
     */
    public int getConsolidatedQueuesWaitingTime() {
        return consolidatedQueuesWaitingTime;
    }

    /**
     * Returns the number of waiting calls in queues possibly serving this
     * processing group.
     * 
     * @return the consolidated number of waiting calls
     */
    public int getConsolidatedQueuesNbOfWaitingCalls() {
        return consolidatedQueuesNbOfWaitingCalls;
    }

    /**
     * Returns the expected waiting time on queues possibly serving this processing
     * group.
     * 
     * @return the consolidated expected waiting time
     */
    public int getConsolidatedQueuesEWT() {
        return consolidatedQueuesEWT;
    }

    /**
     * Returns the worst service level on all pilots possibly serving this
     * processing group.
     * 
     * @return the worst pilots service level
     */
    public int getPilotsWorstServiceLevel() {
        return pilotsWorstServiceLevel;
    }

    /**
     * Returns the worst efficiency on all pilots possibly serving this processing
     * group.
     * 
     * @return the worst pilots efficiency
     */
    public int getPilotsWorstEfficiency() {
        return pilotsWorstEfficiency;
    }

    /**
     * Returns the best service level on all pilots possibly serving this processing
     * group.
     * 
     * @return the best pilots service level
     */
    public int getPilotsBestServiceLevel() {
        return pilotsBestServiceLevel;
    }

    /**
     * Returns the best efficiency on all pilots possibly serving this processing
     * group.
     * 
     * @return the best pilots efficiency
     */
    public int getPilotsBestEfficiency() {
        return pilotsBestEfficiency;
    }

    /**
     * Returns the longest current waiting time in queues possibly serving this
     * processing group.
     * 
     * @return the queues' longest waiting time
     */
    public int getQueuesLongestWaitingTime() {
        return queuesLongestWaitingTime;
    }


    /**
     * Returns the object CCD key.
     * 
     * @return the ccd key
     */
    public final int getKey() {
        return afeKey;
    }

    
    protected OnPGAgentRtiChangedEvent() {
    }
}
