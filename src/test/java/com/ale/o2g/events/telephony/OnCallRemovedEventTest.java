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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.telephony.call.Cause;
import com.ale.o2g.types.telephony.device.Capabilities;

public class OnCallRemovedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "callRef": "call123",
            "cause": "ABANDONED",
            "newDestination": "5551234",
            "deviceCapabilities": [
                { "deviceId": "Phone1", "makeCall": true },
                { "deviceId": "Phone2", "makeCall": false }
            ]
        }
        """;

        OnCallRemovedEvent event = gson.fromJson(json, OnCallRemovedEvent.class);

        // Basic fields
        assertEquals("oxe1000", event.getLoginName());
        assertEquals("call123", event.getCallRef());
        assertEquals(Cause.ABANDONED, event.getCause());
        assertEquals("5551234", event.getNewDestination());

        // Device capabilities
        assertNotNull(event.getDeviceCapabilities());
        assertContainsStrict(List.of("Phone1", "Phone2"), event.getDeviceCapabilities(), Capabilities::getDeviceId);
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnCallRemovedEvent event = gson.fromJson(json, OnCallRemovedEvent.class);

        assertNull(event.getLoginName());
        assertNull(event.getCallRef());
        assertNull(event.getCause());
        assertNull(event.getNewDestination());
        assertNull(event.getDeviceCapabilities());
    }
}