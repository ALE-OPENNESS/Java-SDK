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

package com.ale.o2g.events.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.ale.o2g.test.ExtendAssert.assertContains;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.users.Device;
import com.ale.o2g.types.users.User;

public class UserEventsTest extends AbstractJsonTest {

    @Test
    void testOnUserCreatedEvent() {
        String json = """
        {
            "user": {
                "loginName": "oxe1000",
                "firstName": "John",
                "lastName": "Doe",
                "companyPhone": "12345",
                "nodeId": "42",
                "externalLogin": "oxe1000_ext",
                "devices": [
                    { "id": "Phone1", "type": "DESKPHONE" },
                    { "id": "Phone2", "type": "DECT" }
                ],
                "voicemail": null
            }
        }
        """;

        OnUserCreatedEvent event = gson.fromJson(json, OnUserCreatedEvent.class);
        assertNotNull(event.getUser());

        User user = event.getUser();
        assertEquals("oxe1000", user.getLoginName());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("12345", user.getCompanyPhone());
        assertEquals(42, user.getNodeId());
        assertEquals("oxe1000_ext", user.getExternalLogin());
        assertNull(user.getVoicemail());
        assertNotNull(user.getDevices());
        assertEquals(2, user.getDevices().size());
        assertContains(List.of("Phone1", "Phone2"), user.getDevices(), Device::getId);
    }

    @Test
    void testOnUserInfoChangedEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "user": {
                "loginName": "oxe1000",
                "firstName": "John",
                "lastName": "Doe"
            }
        }
        """;

        OnUserInfoChangedEvent event = gson.fromJson(json, OnUserInfoChangedEvent.class);
        assertEquals("oxe1000", event.getLoginName());
        assertNotNull(event.getUser());
        assertEquals("oxe1000", event.getUser().getLoginName());
        assertEquals("John", event.getUser().getFirstName());
        assertEquals("Doe", event.getUser().getLastName());
        assertTrue(event.getUser().getDevices().isEmpty());
    }

    @Test
    void testOnUserDeletedEvent() {
        String json = """
        {
            "loginName": "oxe1000"
        }
        """;

        OnUserDeletedEvent event = gson.fromJson(json, OnUserDeletedEvent.class);
        assertEquals("oxe1000", event.getLoginName());
    }

    @Test
    void testEmptyEvents() {
        // Minimal JSON: all fields missing
        OnUserCreatedEvent created = gson.fromJson("{}", OnUserCreatedEvent.class);
        assertNull(created.getUser());

        OnUserInfoChangedEvent changed = gson.fromJson("{}", OnUserInfoChangedEvent.class);
        assertNull(changed.getUser());
        assertNull(changed.getLoginName());

        OnUserDeletedEvent deleted = gson.fromJson("{}", OnUserDeletedEvent.class);
        assertNull(deleted.getLoginName());
    }
}