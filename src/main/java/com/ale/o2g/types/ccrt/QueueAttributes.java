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

/**
 * {@code QueueAttributes} represents the possible real-time attributes 
 * for a CCD queue.
 * <p>
 * The {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService} can report real-time events
 * for each of these attributes.
 */
public enum QueueAttributes {

    /**
     * The service state of the queue (e.g., Open, Blocked).
     */
    State,

    /**
     * The number of agents currently involved in distributing calls 
     * in the waiting queue.
     */
    NbOfAgentsInDistribution,

    /**
     * The number of incoming calls to the queue within the last minute.
     */
    IncomingTraffic,

    /**
     * The number of outgoing calls from the queue within the last minute.
     */
    OutgoingTraffic,

    /**
     * The number of calls currently waiting in the queue.
     */
    NbOfWaitingCalls,

    /**
     * The current waiting time of calls in the queue (in seconds).
     */
    CurrentWaitingTime,

    /**
     * The expected waiting time for calls in the queue (in seconds).
     */
    ExpectedWaitingTime,

    /**
     * The filling rate of the queue.
     */
    FillingRate,

    /**
     * The longest waiting time among all queues in a super waiting queue.
     */
    LongestWaitingTimeInList,

    /**
     * Represents all attributes.
     */
    ALL
}
