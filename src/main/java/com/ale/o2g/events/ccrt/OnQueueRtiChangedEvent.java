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
 * Event delivered by {@link CallCenterRealtimeEventListener#onQueueRtiChanged(OnQueueRtiChangedEvent) onQueueRtiChanged(OnQueueRtiChangedEvent)}
 * whenever the attributes of one or more CCD queues change.
 * <p>
 * This event contains only the attributes that have changed since the previous notification.
 * It is sent periodically according to the {@link com.ale.o2g.types.ccrt.Context Context} 
 * configuration in {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}.
 * <p>
 * Typical usage:
 * <pre><code>
 * {@literal @}Override
 * public void onQueueRtiChanged(OnQueueRtiChangedEvent e) {
 *     int incomingTraffic = e.getIncomingTraffic();
 *     // ...
 * }
 * </code></pre>
 * 
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see com.ale.o2g.types.ccrt.Context
 * @see CallCenterRealtimeEventListener#onQueueRtiChanged(OnQueueRtiChangedEvent)
 * @since 2.7.4
 */
public class OnQueueRtiChangedEvent extends O2GEvent {

    private String name;
    private String number;
    private QueueType type;
    private ServiceState state;
    private int nbOfAgentsInDistribution;
    private int incomingTraffic;
    private int outgoingTraffic;
    private int nbOfWaitingCalls;
    private int currentWaitingTime;
    private int fillingRate;
    private int expectedWaitingTime;
    private int longestWaitingTimeInList;
    private int afeKey;
    /** 
     * Returns the name of the queue.
     * 
     * @return the queue name
     */
    public String getName() {
        return name;
    }

    /** 
     * Returns the directory number of the queue.
     * 
     * @return the queue number
     */
    public String getNumber() {
        return number;
    }

    /** 
     * Returns the type of the queue.
     * 
     * @return the {@link QueueType} of the queue
     */
    public QueueType getType() {
        return type;
    }

    /** 
     * Returns the current service state of the queue.
     * 
     * @return the {@link ServiceState} of the queue
     */
    public ServiceState getState() {
        return state;
    }

    /** 
     * Returns the number of agents in the distribution of the waiting queue.
     * 
     * @return the number of agents currently distributing calls in the queue
     */
    public int getNbOfAgentsInDistribution() {
        return nbOfAgentsInDistribution;
    }

    /** 
     * Returns the number of incoming calls within the last minute.
     * 
     * @return the count of incoming calls in the last minute
     */
    public int getIncomingTraffic() {
        return incomingTraffic;
    }

    /** 
     * Returns the number of outgoing calls within the last minute.
     * 
     * @return the count of outgoing calls in the last minute
     */
    public int getOutgoingTraffic() {
        return outgoingTraffic;
    }

    /** 
     * Returns the number of waiting calls in the queue.
     * 
     * @return the number of calls currently waiting in the queue
     */
    public int getNbOfWaitingCalls() {
        return nbOfWaitingCalls;
    }

    /** 
     * Returns the current waiting time in the queue.
     * 
     * @return the current waiting time (in seconds) for calls in the queue
     */
    public int getCurrentWaitingTime() {
        return currentWaitingTime;
    }

    /** 
     * Returns the filling rate of the queue.
     * 
     * @return the queue filling rate as a percentage (0-100)
     */
    public int getFillingRate() {
        return fillingRate;
    }

    /** 
     * Returns the expected waiting time in the queue.
     * 
     * @return the expected waiting time (in seconds) for incoming calls
     */
    public int getExpectedWaitingTime() {
        return expectedWaitingTime;
    }

    /** 
     * Returns the longest waiting time among the queues in a super waiting queue.
     * 
     * @return the longest waiting time (in seconds) across the super queue
     */
    public int getLongestWaitingTimeInList() {
        return longestWaitingTimeInList;
    }

    /** 
     * Returns the CCD key of this queue object.
     * 
     * @return the unique key identifier for the queue
     */
    public final int getKey() {
        return afeKey;
    }

    protected OnQueueRtiChangedEvent() {
    }
}
