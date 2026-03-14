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

package com.ale.o2g.events.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.cca.OperatorState;

public class OnAgentStateChangedEventTest extends AbstractJsonTest {

    @Test
    void testEventDeserialization() {
        String json = """
        {
            "eventName": "AgentStateChanged",
            "loginName": "agent007",
            "state": {
                "mainState": "LOG_ON",
                "subState": "READY",
                "proAcdDeviceNumber": "1001",
                "pgNumber": "PG01",
                "withdrawReason": 2,
                "withdrawn": true
            }
        }
        """;

        OnAgentStateChangedEvent event = gson.fromJson(json, OnAgentStateChangedEvent.class);

        // Validate login name
        assertEquals("agent007", event.getLoginName());

        // Validate operator state
        OperatorState state = event.getState();
        assertNotNull(state);
        assertEquals(OperatorState.OperatorMainState.LOG_ON, state.getMainState());
        assertEquals(OperatorState.OperatorDynamicState.READY, state.getSubState());
        assertEquals("1001", state.getProAcdDeviceNumber());
        assertEquals("PG01", state.getPgNumber());
        assertEquals(2, state.getWithdrawReason());
        assertTrue(state.isWithdrawn());
    }

    @Test
    void testEmptyState() {
        String json = """
        {
            "eventName": "AgentStateChanged",
            "loginName": "agent007",
            "state": {}
        }
        """;

        OnAgentStateChangedEvent event = gson.fromJson(json, OnAgentStateChangedEvent.class);

        assertEquals("agent007", event.getLoginName());
        assertNotNull(event.getState());
        assertNull(event.getState().getMainState());
        assertNull(event.getState().getSubState());
        assertNull(event.getState().getProAcdDeviceNumber());
        assertNull(event.getState().getPgNumber());
        assertNull(event.getState().getWithdrawReason());
        assertFalse(event.getState().isWithdrawn());
    }
}