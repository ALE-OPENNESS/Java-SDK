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
import com.ale.o2g.types.telephony.device.Capabilities;

public class OnCallModifiedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "callRef": "call123",
            "previousCallRef": "call122",
            "replacedByCallRef": "call124",
            "cause": "BUSY",
            "callData": {
                "deviceCall": true,
                "anonymous": false,
                "callUUID": "uuid-456",
                "state": "HELD",
                "recordState": "PAUSED"
            },
            "modifiedLegs": [
                { "deviceId": "leg1", "state": "ACTIVE" }
            ],
            "addedLegs": [
                { "deviceId": "leg2", "state": "RINGING_OUTGOING" }
            ],
            "removedLegs": [
                { "deviceId": "leg3", "state": "HELD" }
            ],
            "modifiedParticipants": [
                { "participantId": "user1", "state": "ACTIVE" }
            ],
            "addedParticipants": [
                { "participantId": "user2", "state": "RINGING_INCOMING" }
            ],
            "removedParticipantIds": ["user3"],
            "deviceCapabilities": [
                { "deviceId": "Phone1", "makeCall": true },
                { "deviceId": "Phone2", "makeCall": false }
            ]
        }
        """;

        OnCallModifiedEvent event = gson.fromJson(json, OnCallModifiedEvent.class);

        // Basic fields
        assertEquals("oxe1000", event.getLoginName());
        assertEquals("call123", event.getCallRef());
        assertEquals("call122", event.getPreviousCallRef());
        assertEquals("call124", event.getReplacedByCallRef());
        assertEquals(Cause.BUSY, event.getCause());

        // CallData
        CallData data = event.getCallData();
        assertNotNull(data);
        assertTrue(data.isDeviceCall());
        assertFalse(data.isAnonymous());
        assertEquals("uuid-456", data.getCallUUID());
        assertEquals(MediaState.HELD, data.getState());
        assertEquals(RecordState.PAUSED, data.getRecordState());

        // Legs
        assertNotNull(event.getModifiedLegs());
        assertContainsStrict(List.of("leg1"), event.getModifiedLegs(), Leg::getDeviceId);

        assertNotNull(event.getAddedLegs());
        assertContainsStrict(List.of("leg2"), event.getAddedLegs(), Leg::getDeviceId);

        assertNotNull(event.getRemovedLegs());
        assertContainsStrict(List.of("leg3"), event.getRemovedLegs(), Leg::getDeviceId);

        // Participants
        assertNotNull(event.getModifiedParticipants());
        assertContainsStrict(List.of("user1"), event.getModifiedParticipants(), Participant::getId);

        assertNotNull(event.getAddedParticipants());
        assertContainsStrict(List.of("user2"), event.getAddedParticipants(), Participant::getId);

        assertNotNull(event.getRemovedParticipantIds());
        assertContainsStrict(List.of("user3"), event.getRemovedParticipantIds(), s -> s);

        // Device capabilities
        assertNotNull(event.getDeviceCapabilities());
        assertContainsStrict(List.of("Phone1", "Phone2"), event.getDeviceCapabilities(), Capabilities::getDeviceId);
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnCallModifiedEvent event = gson.fromJson(json, OnCallModifiedEvent.class);

        assertNull(event.getLoginName());
        assertNull(event.getCallRef());
        assertNull(event.getPreviousCallRef());
        assertNull(event.getReplacedByCallRef());
        assertNull(event.getCause());
        assertNull(event.getCallData());
        assertNull(event.getModifiedLegs());
        assertNull(event.getAddedLegs());
        assertNull(event.getRemovedLegs());
        assertNull(event.getModifiedParticipants());
        assertNull(event.getAddedParticipants());
        assertNull(event.getRemovedParticipantIds());
        assertNull(event.getDeviceCapabilities());
    }
}