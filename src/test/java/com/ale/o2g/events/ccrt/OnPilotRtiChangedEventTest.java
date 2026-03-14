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

public class OnPilotRtiChangedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "name": "SupportPilot",
            "number": "2001",
            "state": "Opened",
            "nbOfRunningCalls": 10,
            "serviceLevel": 85,
            "efficiency": 90,
            "nbOfWaitingCalls": 5,
            "nbOfRingingACDCalls": 2,
            "nbOfMutualAidCalls": 1,
            "nbOfDissuadedCalls": 0,
            "nbOfCallsInConversation": 7,
            "nbOfCallsInGeneralForwarding": 1,
            "nbOfCallsInRemotePG": 2,
            "incomingTraffic": 12,
            "averageWaitingTime": 30,
            "worstServiceLevelInList": 70,
            "worstEfficiencyInList": 65,
            "bestServiceLevelInList": 95,
            "bestEfficiencyInList": 98,
            "afeKey": 555
        }
        """;

        OnPilotRtiChangedEvent event = gson.fromJson(json, OnPilotRtiChangedEvent.class);

        assertEquals("SupportPilot", event.getName());
        assertEquals("2001", event.getNumber());
        assertEquals(ServiceState.OPENED, event.getState());
        assertEquals(10, event.getNbOfRunningCalls());
        assertEquals(85, event.getServiceLevel());
        assertEquals(90, event.getEfficiency());
        assertEquals(5, event.getNbOfWaitingCalls());
        assertEquals(2, event.getNbOfRingingACDCalls());
        assertEquals(1, event.getNbOfMutualAidCalls());
        assertEquals(0, event.getNbOfDissuadedCalls());
        assertEquals(7, event.getNbOfCallsInConversation());
        assertEquals(1, event.getNbOfCallsInGeneralForwarding());
        assertEquals(2, event.getNbOfCallsInRemoteProcessingGroup());
        assertEquals(12, event.getIncomingTraffic());
        assertEquals(30, event.getAverageWaitingTime());
        assertEquals(70, event.getWorstServiceLevelInList());
        assertEquals(65, event.getWorstEfficiencyInList());
        assertEquals(95, event.getBestServiceLevelInList());
        assertEquals(98, event.getBestEfficiencyInList());
        assertEquals(555, event.getKey());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnPilotRtiChangedEvent event = gson.fromJson(json, OnPilotRtiChangedEvent.class);

        assertNull(event.getName());
        assertNull(event.getNumber());
        assertNull(event.getState());
        assertEquals(0, event.getNbOfRunningCalls());
        assertEquals(0, event.getServiceLevel());
        assertEquals(0, event.getEfficiency());
        assertEquals(0, event.getNbOfWaitingCalls());
        assertEquals(0, event.getNbOfRingingACDCalls());
        assertEquals(0, event.getNbOfMutualAidCalls());
        assertEquals(0, event.getNbOfDissuadedCalls());
        assertEquals(0, event.getNbOfCallsInConversation());
        assertEquals(0, event.getNbOfCallsInGeneralForwarding());
        assertEquals(0, event.getNbOfCallsInRemoteProcessingGroup());
        assertEquals(0, event.getIncomingTraffic());
        assertEquals(0, event.getAverageWaitingTime());
        assertEquals(0, event.getWorstServiceLevelInList());
        assertEquals(0, event.getWorstEfficiencyInList());
        assertEquals(0, event.getBestServiceLevelInList());
        assertEquals(0, event.getBestEfficiencyInList());
        assertEquals(0, event.getKey());
    }
}