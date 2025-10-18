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
 * Event delivered by {@link CallCenterRealtimeEventListener#onPilotRtiChanged(OnPilotRtiChangedEvent) onPilotRtiChanged(OnPilotRtiChangedEvent)}
 * whenever the attributes of one or more CCD pilots change.
 * <p>
 * This event contains only the attributes that have changed since the previous notification.
 * It is sent periodically according to the {@link com.ale.o2g.types.ccrt.Context Context} 
 * configuration in {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}.
 * <p>
 * Typical usage:
 * <pre><code>
 * {@literal @}Override
 * public void onPilotRtiChanged(OnPilotRtiChangedEvent e) {
 *     int efficiency = e.getEfficiency();
 *     // ...
 * }
 * </code></pre>
 * 
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see com.ale.o2g.types.ccrt.Context
 * @see CallCenterRealtimeEventListener#onPilotRtiChanged(OnPilotRtiChangedEvent)
 * @since 2.7.4
 */
public class OnPilotRtiChangedEvent extends O2GEvent {

    private String name;
    private String number;
    private ServiceState state;
    private int nbOfRunningCalls;
    private int serviceLevel;
    private int efficiency;
    private int nbOfWaitingCalls;
    private int nbOfRingingACDCalls;
    private int nbOfMutualAidCalls;
    private int nbOfDissuadedCalls;
    private int nbOfCallsInConversation;
    private int nbOfCallsInGeneralForwarding;
    private int nbOfCallsInRemotePG;
    private int incomingTraffic;
    private int averageWaitingTime;
    private int worstServiceLevelInList;
    private int worstEfficiencyInList;
    private int bestServiceLevelInList;
    private int bestEfficiencyInList;
    private int afeKey;

    /** 
     * Returns the name of the pilot.
     * 
     * @return the pilot's name
     */
    public String getName() {
        return name;
    }

    /** 
     * Returns the directory number of the pilot.
     * 
     * @return the pilot's directory number
     */
    public String getNumber() {
        return number;
    }

    /** 
     * Returns the current service state of the pilot.
     * 
     * @return the {@link ServiceState} of the pilot
     */
    public ServiceState getState() {
        return state;
    }

    /** 
     * Returns the number of calls currently in progress for the pilot.
     * 
     * @return the number of running calls
     */
    public int getNbOfRunningCalls() {
        return nbOfRunningCalls;
    }

    /** 
     * Returns the service level of the pilot.
     * 
     * @return the service level as an integer percentage
     */
    public int getServiceLevel() {
        return serviceLevel;
    }

    /** 
     * Returns the efficiency indicator of the pilot.
     * 
     * @return the efficiency as an integer percentage
     */
    public int getEfficiency() {
        return efficiency;
    }

    /** 
     * Returns the number of waiting calls for the pilot.
     * 
     * @return the number of calls currently waiting
     */
    public int getNbOfWaitingCalls() {
        return nbOfWaitingCalls;
    }

    /** 
     * Returns the number of ringing ACD calls for the pilot.
     * 
     * @return the number of calls currently ringing
     */
    public int getNbOfRingingACDCalls() {
        return nbOfRingingACDCalls;
    }

    /** 
     * Returns the number of calls rerouted for mutual aid for the pilot.
     * 
     * @return the number of mutual aid calls
     */
    public int getNbOfMutualAidCalls() {
        return nbOfMutualAidCalls;
    }

    /** 
     * Returns the number of dissuaded calls for the pilot.
     * 
     * @return the number of dissuaded calls
     */
    public int getNbOfDissuadedCalls() {
        return nbOfDissuadedCalls;
    }

    /** 
     * Returns the number of calls currently in conversation for the pilot.
     * 
     * @return the number of active conversations
     */
    public int getNbOfCallsInConversation() {
        return nbOfCallsInConversation;
    }

    /** 
     * Returns the number of calls in general forwarding for the pilot.
     * 
     * @return the number of calls in general forwarding
     */
    public int getNbOfCallsInGeneralForwarding() {
        return nbOfCallsInGeneralForwarding;
    }

    /** 
     * Returns the number of calls being processed in a remote processing group for the pilot.
     * 
     * @return the number of calls in a remote processing group
     */
    public int getNbOfCallsInRemoteProcessingGroup() {
        return nbOfCallsInRemotePG;
    }

    /** 
     * Returns the number of incoming calls within the last minute for the pilot.
     * 
     * @return the count of incoming calls in the last minute
     */
    public int getIncomingTraffic() {
        return incomingTraffic;
    }

    /** 
     * Returns the average waiting time before answering for the pilot.
     * 
     * @return the average waiting time in seconds
     */
    public int getAverageWaitingTime() {
        return averageWaitingTime;
    }

    /** 
     * Returns the worst service level among the pilots in a super pilot group.
     * 
     * @return the worst service level in the group
     */
    public int getWorstServiceLevelInList() {
        return worstServiceLevelInList;
    }

    /** 
     * Returns the worst efficiency among the pilots in a super pilot group.
     * 
     * @return the worst efficiency in the group
     */
    public int getWorstEfficiencyInList() {
        return worstEfficiencyInList;
    }

    /** 
     * Returns the best service level among the pilots in a super pilot group.
     * 
     * @return the best service level in the group
     */
    public int getBestServiceLevelInList() {
        return bestServiceLevelInList;
    }

    /** 
     * Returns the best efficiency among the pilots in a super pilot group.
     * 
     * @return the best efficiency in the group
     */
    public int getBestEfficiencyInList() {
        return bestEfficiencyInList;
    }

    /** 
     * Returns the CCD key of this pilot object.
     * 
     * @return the unique key identifier for the pilot
     */
    public final int getKey() {
        return afeKey;
    }

    protected OnPilotRtiChangedEvent() {
    }
}
