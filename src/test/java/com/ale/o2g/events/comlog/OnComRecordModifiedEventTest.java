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

public class OnComRecordModifiedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "record": {
                "recordId": 54321,
                "comRef": "call456",
                "acknowledged": false,
                "holdDuration": 2000,
                "transferredBy": "user3",
                "associatedData": "correlator-abc",
                "beginDate": "2026-03-08T11:00:00.000Z",
                "endDate": "2026-03-08T11:30:00.000Z",
                "convDate": "2026-03-08T11:05:00.000Z",
                "participants": [
                    {
                        "role": "CALLEE",
                        "answered": false,
                        "anonymous": true,
                        "leg": "leg2",
                        "identity": {
                            "id": { "loginName": "user4", "phoneNumber": "1002", "multiLine": true },
                            "firstName": "Jane",
                            "lastName": "Smith",
                            "displayName": "Jane Smith",
                            "type": { "main": "USER", "subType": "pbx" }
                        }
                    }
                ]
            }
        }
        """;

        OnComRecordModifiedEvent event = gson.fromJson(json, OnComRecordModifiedEvent.class);

        // Basic field
        assertEquals("oxe1000", event.getLoginName());

        // ComRecord
        ComRecord record = event.getRecord();
        assertNotNull(record);
        assertEquals(54321, record.getId());
        assertEquals("call456", record.getCallRef());
        assertFalse(record.isAcknowledged());
        assertEquals(2000, record.getHoldDuration());
        assertEquals("user3", record.getTransferredBy());

        // CorrelatorData
        assertNotNull(record.getCorrelatorData());
        assertEquals("correlator-abc", record.getCorrelatorData().asString());

        // Dates
        assertNotNull(record.getBegin());
        assertNotNull(record.getEnd());
        assertNotNull(record.getAnswered());

        // Participants
        assertNotNull(record.getParticipants());
        assertEquals(1, record.getParticipants().size());

        ComRecordParticipant p = record.getParticipants().iterator().next();
        assertEquals(Role.CALLEE, p.getRole());
        assertEquals(Boolean.FALSE, p.getAnswered());
        assertTrue(p.isAnonymous());
        assertEquals("leg2", p.getLeg());

        // Participant identity
        PartyInfo identity = p.getIdentity();
        assertNotNull(identity);
        assertEquals("Jane", identity.getFirstName());
        assertEquals("Smith", identity.getLastName());
        assertEquals("Jane Smith", identity.getDisplayName());
        assertNotNull(identity.getId());
        assertEquals("user4", identity.getId().getLoginName());
        assertEquals("1002", identity.getId().getPhoneNumber());
        assertTrue(identity.getId().isMultiLine());
        assertNotNull(identity.getType());
        assertEquals(PartyInfo.Type.MainType.USER, identity.getType().getMain());
        assertEquals("pbx", identity.getType().getSubType());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnComRecordModifiedEvent event = gson.fromJson(json, OnComRecordModifiedEvent.class);

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

        OnComRecordModifiedEvent event = gson.fromJson(json, OnComRecordModifiedEvent.class);
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