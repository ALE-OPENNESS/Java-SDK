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

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

public class OnAgentRtiChangedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "name": "1001",
            "firstName": "Alice",
            "number": "1001",
            "type": "Normal",
            "logonDate": "2026-03-08T09:30:00",
            "serviceState": "LoggedIn",
            "serviceStateDate": "2026-03-08T09:35:00",
            "phoneState": "Idle",
            "phoneStateDate": "2026-03-08T09:40:00",
            "pilotName": "SupportPilot",
            "queueName": "SupportQueue",
            "nbOfWithdrawals": 2,
            "withdrawalsTotalDuration": 120,
            "nbOfPrivateCalls": 3,
            "privateCallsTotalDuration": 180,
            "nbOfServedACDCalls": 5,
            "nbOfOutgoingACDCalls": 1,
            "nbOfRefusedACDCalls": 0,
            "nbOfInterceptedACDCalls": 1,
            "nbOfTransferedACDCalls": 2,
            "currentPG": "PG1",
            "associatedSet": "SetA",
            "withdrawReason": 1,
            "afeKey": 1234
        }
        """;

        OnAgentRtiChangedEvent event = gson.fromJson(json, OnAgentRtiChangedEvent.class);

        assertEquals("1001", event.getName());
        assertEquals("Alice", event.getFirstName());
        assertEquals("1001", event.getNumber());
        assertEquals(AgentType.NORMAL, event.getType());
        assertEquals(LocalDateTime.parse("2026-03-08T09:30:00"), event.getLogonDate());
        assertEquals(AgentServiceState.LOGGED_IN, event.getServiceState());
        assertEquals(LocalDateTime.parse("2026-03-08T09:35:00"), event.getServiceStateDate());
        assertEquals(AgentPhoneState.IDLE, event.getPhoneState());
        assertEquals(LocalDateTime.parse("2026-03-08T09:40:00"), event.getPhoneStateDate());
        assertEquals("SupportPilot", event.getPilotName());
        assertEquals("SupportQueue", event.getQueueName());
        assertEquals(2, event.getNbOfWithdrawals());
        assertEquals(120, event.getWithdrawalsTotalDuration());
        assertEquals(3, event.getNbOfPrivateCalls());
        assertEquals(180, event.getPrivateCallsTotalDuration());
        assertEquals(5, event.getNbOfServedACDCalls());
        assertEquals(1, event.getNbOfOutgoingACDCalls());
        assertEquals(0, event.getNbOfRefusedACDCalls());
        assertEquals(1, event.getNbOfInterceptedACDCalls());
        assertEquals(2, event.getNbOfTransferedACDCalls());
        assertEquals("PG1", event.getCurrentProcessingGroup());
        assertEquals("SetA", event.getAssociatedSet());
        assertEquals(1, event.getWithdrawReason());
        assertEquals(1234, event.getKey());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnAgentRtiChangedEvent event = gson.fromJson(json, OnAgentRtiChangedEvent.class);

        assertNull(event.getName());
        assertNull(event.getFirstName());
        assertNull(event.getNumber());
        assertNull(event.getType());
        assertNull(event.getLogonDate());
        assertNull(event.getServiceState());
        assertNull(event.getServiceStateDate());
        assertNull(event.getPhoneState());
        assertNull(event.getPhoneStateDate());
        assertNull(event.getPilotName());
        assertNull(event.getQueueName());
        assertEquals(0, event.getNbOfWithdrawals());
        assertEquals(0, event.getWithdrawalsTotalDuration());
        assertEquals(0, event.getNbOfPrivateCalls());
        assertEquals(0, event.getPrivateCallsTotalDuration());
        assertEquals(0, event.getNbOfServedACDCalls());
        assertEquals(0, event.getNbOfOutgoingACDCalls());
        assertEquals(0, event.getNbOfRefusedACDCalls());
        assertEquals(0, event.getNbOfInterceptedACDCalls());
        assertEquals(0, event.getNbOfTransferedACDCalls());
        assertNull(event.getCurrentProcessingGroup());
        assertNull(event.getAssociatedSet());
        assertEquals(0, event.getWithdrawReason());
        assertEquals(0, event.getKey());
    }
}