/*
* Copyright 2026 ALE International
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.common.ServiceState;

public class OnPGAgentRtiChangedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "name": "SupportPG",
            "number": "3001",
            "type": "Agent",
            "state": "Opened",
            "nbOfWithdrawnAgents": 2,
            "nbOfAgentsInPrivateCall": 3,
            "nbOfAgentsInACDCall": 4,
            "nbOfAgentsInACDRinging": 1,
            "nbOfAgentsInACDConv": 5,
            "nbOfAgentsInWrapupAndTransaction": 2,
            "nbOfAgentsInPause": 1,
            "nbOfBusyAgents": 6,
            "nbOfLoggedOnAgents": 10,
            "nbOfFreeAgents": 4,
            "nbOfIdleAgents": 3,
            "nbOfLoggedOnAndNotWithdrawnAgents": 8,
            "incomingTraffic": 15,
            "consolidatedPilotsServiceLevel": 85,
            "consolidatedPilotsEfficiency": 90,
            "consolidatedQueuesWaitingTime": 20,
            "consolidatedQueuesNbOfWaitingCalls": 5,
            "consolidatedQueuesEWT": 25,
            "pilotsWorstServiceLevel": 70,
            "pilotsWorstEfficiency": 65,
            "pilotsBestServiceLevel": 95,
            "pilotsBestEfficiency": 98,
            "queuesLongestWaitingTime": 40,
            "afeKey": 777
        }
        """;

        OnPGAgentRtiChangedEvent event = gson.fromJson(json, OnPGAgentRtiChangedEvent.class);

        assertEquals("SupportPG", event.getName());
        assertEquals("3001", event.getNumber());
        assertEquals(AgentProcessingGroupType.AGENT, event.getType());
        assertEquals(ServiceState.OPENED, event.getState());
        assertEquals(2, event.getNbOfWithdrawnAgents());
        assertEquals(3, event.getNbOfAgentsInPrivateCall());
        assertEquals(4, event.getNbOfAgentsInACDCall());
        assertEquals(1, event.getNbOfAgentsInACDRinging());
        assertEquals(5, event.getNbOfAgentsInACDConv());
        assertEquals(2, event.getNbOfAgentsInWrapupAndTransaction());
        assertEquals(1, event.getNbOfAgentsInPause());
        assertEquals(6, event.getNbOfBusyAgents());
        assertEquals(10, event.getNbOfLoggedOnAgents());
        assertEquals(4, event.getNbOfFreeAgents());
        assertEquals(3, event.getNbOfIdleAgents());
        assertEquals(8, event.getNbOfLoggedOnAndNotWithdrawnAgents());
        assertEquals(15, event.getIncomingTraffic());
        assertEquals(85, event.getConsolidatedPilotsServiceLevel());
        assertEquals(90, event.getConsolidatedPilotsEfficiency());
        assertEquals(20, event.getConsolidatedQueuesWaitingTime());
        assertEquals(5, event.getConsolidatedQueuesNbOfWaitingCalls());
        assertEquals(25, event.getConsolidatedQueuesEWT());
        assertEquals(70, event.getPilotsWorstServiceLevel());
        assertEquals(65, event.getPilotsWorstEfficiency());
        assertEquals(95, event.getPilotsBestServiceLevel());
        assertEquals(98, event.getPilotsBestEfficiency());
        assertEquals(40, event.getQueuesLongestWaitingTime());
        assertEquals(777, event.getKey());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnPGAgentRtiChangedEvent event = gson.fromJson(json, OnPGAgentRtiChangedEvent.class);

        assertNull(event.getName());
        assertNull(event.getNumber());
        assertNull(event.getType());
        assertNull(event.getState());
        assertEquals(0, event.getNbOfWithdrawnAgents());
        assertEquals(0, event.getNbOfAgentsInPrivateCall());
        assertEquals(0, event.getNbOfAgentsInACDCall());
        assertEquals(0, event.getNbOfAgentsInACDRinging());
        assertEquals(0, event.getNbOfAgentsInACDConv());
        assertEquals(0, event.getNbOfAgentsInWrapupAndTransaction());
        assertEquals(0, event.getNbOfAgentsInPause());
        assertEquals(0, event.getNbOfBusyAgents());
        assertEquals(0, event.getNbOfLoggedOnAgents());
        assertEquals(0, event.getNbOfFreeAgents());
        assertEquals(0, event.getNbOfIdleAgents());
        assertEquals(0, event.getNbOfLoggedOnAndNotWithdrawnAgents());
        assertEquals(0, event.getIncomingTraffic());
        assertEquals(0, event.getConsolidatedPilotsServiceLevel());
        assertEquals(0, event.getConsolidatedPilotsEfficiency());
        assertEquals(0, event.getConsolidatedQueuesWaitingTime());
        assertEquals(0, event.getConsolidatedQueuesNbOfWaitingCalls());
        assertEquals(0, event.getConsolidatedQueuesEWT());
        assertEquals(0, event.getPilotsWorstServiceLevel());
        assertEquals(0, event.getPilotsWorstEfficiency());
        assertEquals(0, event.getPilotsBestServiceLevel());
        assertEquals(0, event.getPilotsBestEfficiency());
        assertEquals(0, event.getQueuesLongestWaitingTime());
        assertEquals(0, event.getKey());
    }
}