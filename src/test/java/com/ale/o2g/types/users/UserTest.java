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

package com.ale.o2g.types.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.users.Voicemail.Type;

class UserTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON with all fields
        String json = """
                {
                    "companyPhone": "1001",
                    "firstName": "Alice",
                    "lastName": "Smith",
                    "loginName": "asmith",
                    "voicemail": {
                        "number": "33000",
                        "type": "VM_4645"
                    },
                    "devices": [
                        { "type": "DESKPHONE", "id": "13000", "subType": "IPPhone" },
                        { "type": "MOBILE", "id": "13001", "subType": "Smartphone" }
                    ],
                    "nodeId": "2",
                    "externalLogin": "alice.smith@example.com"
                }
                """;

        User user = gson.fromJson(json, User.class);

        assertEquals("1001", user.getCompanyPhone());
        assertEquals("Alice", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("asmith", user.getLoginName());

        assertNotNull(user.getVoicemail());
        assertEquals("33000", user.getVoicemail().getNumber());
        assertEquals(Type.VM_4645, user.getVoicemail().getType());

        Collection<Device> devices = user.getDevices();
        assertNotNull(devices);
        assertEquals(2, devices.size());

        // Verify first device
        Device d1 = devices.iterator().next();
        assertNotNull(d1.getType());
        assertNotNull(d1.getId());
        assertNotNull(d1.getSubType());

        assertEquals(2, user.getNodeId());
        assertEquals("alice.smith@example.com", user.getExternalLogin());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        User user = gson.fromJson(json, User.class);

        assertNull(user.getCompanyPhone());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getLoginName());
        assertNull(user.getVoicemail());
        assertNotNull(user.getDevices());
        assertTrue(user.getDevices().isEmpty());
        assertEquals(-1, user.getNodeId());
        assertNull(user.getExternalLogin());
    }
}