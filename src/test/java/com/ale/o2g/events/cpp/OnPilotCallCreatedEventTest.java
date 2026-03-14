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

package com.ale.o2g.events.cpp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.events.ccp.OnPilotCallCreatedEvent;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.ccp.CallDataPilot;
import com.ale.o2g.types.telephony.TrunkIdentification;
import com.ale.o2g.types.telephony.call.Cause;

public class OnPilotCallCreatedEventTest extends AbstractJsonTest {

    @Test
    void testFullEventDeserialization() {
        // Sample JSON simulating the event
        String json = """
        {
            "pilot": "1001",
            "caller": "5551234",
            "callRef": "CALL-REF-001",
            "cause": "DISTRIBUTED",
            "callData": {
                "initialCalled": "2001",
                "anonymous": false,
                "associatedData": "sampleData",
                "networkRerouted": true,
                "trunkIdentification": {
                    "networkTimeslot": 5,
                    "trunkNeqt": [101, 102]
                }
            }
        }
        """;

        // Use gson from AbstractJsonTest
        OnPilotCallCreatedEvent event = gson.fromJson(json, OnPilotCallCreatedEvent.class);

        assertEquals("1001", event.getPilot());
        assertEquals("5551234", event.getCaller());
        assertEquals("CALL-REF-001", event.getCallRef());
        assertEquals(Cause.DISTRIBUTED, event.getCause());

        CallDataPilot callData = event.getCallData();
        assertNotNull(callData);
        assertEquals("2001", callData.getInitialCalled());
        assertFalse(callData.isAnonymous());
        assertTrue(callData.isNetworkRerouted());

        // Test associatedData mapping to CorrelatorData
        assertNotNull(callData.getCorrelatorData());
        assertEquals("sampleData", callData.getCorrelatorData().asString());

        // Test TrunkIdentification
        TrunkIdentification trunk = callData.getTrunkIdentification();
        assertNotNull(trunk);
        assertEquals(5, trunk.getNetworkTimeslot());
        assertEquals(List.of(101, 102), trunk.getTrunkNeqts().stream().toList());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";
        OnPilotCallCreatedEvent event = gson.fromJson(json, OnPilotCallCreatedEvent.class);

        assertNull(event.getPilot());
        assertNull(event.getCaller());
        assertNull(event.getCallRef());
        assertNull(event.getCause());
        assertNull(event.getCallData());
    }
}