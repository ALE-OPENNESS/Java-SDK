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
 * {@code PilotAttributes} represents the possible real-time attributes 
 * for a CCD pilot.
 * <p>
 * The {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService} can report real-time events
 * for each of these attributes.
 */
public enum PilotAttributes {

    /**
     * The service state of the pilot (e.g., Open, Blocked).
     */
    State,

    /**
     * The service level of the pilot.
     */
    ServiceLevel,

    /**
     * The name of the current routing rule applied to the pilot.
     */
    CurrentRuleName,

    /**
     * The number of calls currently waiting for the pilot.
     */
    NbOfWaitingCalls,

    /**
     * The number of calls rerouted for mutual aid.
     */
    NbOfMutualAidCalls,

    /**
     * The number of calls currently in conversation.
     */
    NbOfCallsInConversation,

    /**
     * The number of calls being processed in a remote processing group.
     */
    NbOfCallsInRemotePG,

    /**
     * The average waiting time (in seconds) before answering calls.
     */
    AverageWaitingTime,

    /**
     * The number of calls currently in progress.
     */
    NbOfRunningCalls,

    /**
     * The number of ACD calls currently ringing for the pilot.
     */
    NbOfRingingACDCalls,

    /**
     * The number of calls that were dissuaded.
     */
    NbOfDissuadedCalls,

    /**
     * The number of calls in general forwarding for the pilot.
     */
    NbOfCallsInGeneralForwarding,

    /**
     * The efficiency of the pilot (average, best, worst).
     */
    Efficiency,

    /**
     * The number of incoming calls received by the pilot within the last minute.
     */
    IncomingTraffic,

    /**
     * Represents all attributes.
     */
    ALL
}
