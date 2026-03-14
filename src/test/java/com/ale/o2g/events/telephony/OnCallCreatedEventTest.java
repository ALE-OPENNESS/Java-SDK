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

package com.ale.o2g.events.telephony;

import static com.ale.o2g.test.ExtendAssert.assertContainsStrict;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.telephony.call.CallData;
import com.ale.o2g.types.telephony.call.Cause;
import com.ale.o2g.types.telephony.call.Leg;
import com.ale.o2g.types.telephony.call.MediaState;
import com.ale.o2g.types.telephony.call.Participant;
import com.ale.o2g.types.telephony.call.RecordState;

public class OnCallCreatedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "callRef": "call123",
            "cause": "ABANDONED",
            "initiator": "user1",
            "callData": {
                "deviceCall": true,
                "anonymous": false,
                "callUUID": "uuid-123",
                "state": "ACTIVE",
                "recordState": "RECORDING"
            },
            "legs": [
                { "deviceId": "leg1", "state": "ACTIVE" },
                { "deviceId": "leg2", "state": "HELD" }
            ],
            "participants": [
                { "participantId": "user1", "state": "ACTIVE" },
                { "participantId": "user2", "state": "RINGING_INCOMING" }
            ],
            "deviceCapabilities": [
                { "deviceId": "Phone1", "makeCall": true },
                { "deviceId": "Phone2", "makeCall": false }
            ]
        }
        """;

        OnCallCreatedEvent event = gson.fromJson(json, OnCallCreatedEvent.class);

        // Basic fields
        assertEquals("oxe1000", event.getLoginName());
        assertEquals("call123", event.getCallRef());
        assertEquals(Cause.ABANDONED, event.getCause());
        assertEquals("user1", event.getInitiator());

        // CallData
        CallData data = event.getCallData();
        assertNotNull(data);
        assertTrue(data.isDeviceCall());
        assertFalse(data.isAnonymous());
        assertEquals("uuid-123", data.getCallUUID());
        assertEquals(MediaState.ACTIVE, data.getState());
        assertEquals(RecordState.RECORDING, data.getRecordState());

        // Legs
        assertNotNull(event.getLegs());
        assertEquals(2, event.getLegs().size());
        assertContainsStrict(List.of("leg1", "leg2"), event.getLegs(), Leg::getDeviceId);

        // Participants
        assertNotNull(event.getParticipants());
        assertEquals(2, event.getParticipants().size());
        assertContainsStrict(List.of("user1", "user2"), event.getParticipants(), Participant::getId);

        // Device capabilities
        assertNotNull(event.getDeviceCapabilities());
        assertEquals(2, event.getDeviceCapabilities().size());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnCallCreatedEvent event = gson.fromJson(json, OnCallCreatedEvent.class);

        assertNull(event.getLoginName());
        assertNull(event.getCallRef());
        assertNull(event.getCause());
        assertNull(event.getCallData());
        assertNull(event.getInitiator());
        assertNull(event.getLegs());
        assertNull(event.getParticipants());
        assertNull(event.getDeviceCapabilities());
    }
}