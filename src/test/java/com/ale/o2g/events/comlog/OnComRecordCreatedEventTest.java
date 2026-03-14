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

package com.ale.o2g.events.comlog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.comlog.ComRecord;
import com.ale.o2g.types.comlog.ComRecordParticipant;
import com.ale.o2g.types.comlog.Role;
import com.ale.o2g.types.common.PartyInfo;

public class OnComRecordCreatedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "record": {
                "recordId": 12345,
                "comRef": "call123",
                "acknowledged": true,
                "holdDuration": 5000,
                "transferredBy": "user2",
                "associatedData": "correlator-xyz",
                "beginDate": "2026-03-08T10:15:30.000Z",
                "endDate": "2026-03-08T10:45:30.000Z",
                "convDate": "2026-03-08T10:20:00.000Z",
                "participants": [
                    {
                        "role": "CALLER",
                        "answered": true,
                        "anonymous": false,
                        "leg": "leg1",
                        "identity": {
                            "id": { "loginName": "user1", "phoneNumber": "1001", "multiLine": false },
                            "firstName": "John",
                            "lastName": "Doe",
                            "displayName": "John Doe",
                            "type": { "main": "USER", "subType": "pbx" }
                        }
                    }
                ]
            }
        }
        """;

        OnComRecordCreatedEvent event = gson.fromJson(json, OnComRecordCreatedEvent.class);

        // Basic field
        assertEquals("oxe1000", event.getLoginName());

        // ComRecord
        ComRecord record = event.getRecord();
        assertNotNull(record);
        assertEquals(12345, record.getId());
        assertEquals("call123", record.getCallRef());
        assertTrue(record.isAcknowledged());
        assertEquals(5000, record.getHoldDuration());
        assertEquals("user2", record.getTransferredBy());

        // CorrelatorData
        assertNotNull(record.getCorrelatorData());
        assertEquals("correlator-xyz", record.getCorrelatorData().asString());

        // Dates
        assertNotNull(record.getBegin());
        assertNotNull(record.getEnd());
        assertNotNull(record.getAnswered());

        // Participants
        assertNotNull(record.getParticipants());
        assertEquals(1, record.getParticipants().size());

        ComRecordParticipant p = record.getParticipants().iterator().next();
        assertEquals(Role.CALLER, p.getRole());
        assertEquals(Boolean.TRUE, p.getAnswered());
        assertFalse(p.isAnonymous());
        assertEquals("leg1", p.getLeg());

        // Participant identity
        PartyInfo identity = p.getIdentity();
        assertNotNull(identity);
        assertEquals("John", identity.getFirstName());
        assertEquals("Doe", identity.getLastName());
        assertEquals("John Doe", identity.getDisplayName());
        assertNotNull(identity.getId());
        assertEquals("user1", identity.getId().getLoginName());
        assertEquals("1001", identity.getId().getPhoneNumber());
        assertFalse(identity.getId().isMultiLine());
        assertNotNull(identity.getType());
        assertEquals(PartyInfo.Type.MainType.USER, identity.getType().getMain());
        assertEquals("pbx", identity.getType().getSubType());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnComRecordCreatedEvent event = gson.fromJson(json, OnComRecordCreatedEvent.class);

        assertNull(event.getLoginName());
        assertNull(event.getRecord());
    }

    @Test
    void testRecordWithNullFields() {
        String json = """
        {
            "loginName": "oxe1001",
            "record": {
                "recordId": 0,
                "comRef": null,
                "acknowledged": false,
                "participants": null,
                "associatedData": null
            }
        }
        """;

        OnComRecordCreatedEvent event = gson.fromJson(json, OnComRecordCreatedEvent.class);
        ComRecord record = event.getRecord();

        assertEquals("oxe1001", event.getLoginName());
        assertNotNull(record);
        assertEquals(0, record.getId());
        assertNull(record.getCallRef());
        assertFalse(record.isAcknowledged());
        assertTrue(record.getParticipants().isEmpty());
        assertNull(record.getCorrelatorData());
    }
}