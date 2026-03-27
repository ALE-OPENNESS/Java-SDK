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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.telephony.TelephonicState;
import com.ale.o2g.types.telephony.device.Capabilities;
import com.ale.o2g.types.telephony.user.UserState;

public class OnTelephonyStateEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "state": {
                "userState": "BUSY",
                "calls": [
                    { "callRef": "call123", "callData": null, "legs": [], "participants": [] }
                ],
                "deviceCapabilities": [
                    { "deviceId": "Phone1", "makeCall": true, "makeBusinessCall": false, "makePrivateCall": true, "unParkCall": false }
                ]
            }
        }
        """;

        OnTelephonyStateEvent event = gson.fromJson(json, OnTelephonyStateEvent.class);

        // Basic login check
        assertEquals("oxe1000", event.getLoginName());

        // State check
        TelephonicState state = event.getState();
        assertNotNull(state);
        assertEquals(UserState.BUSY, state.getUserState());

        // Calls
        assertNotNull(state.getCalls());
        assertEquals(1, state.getCalls().size());
        assertEquals("call123", state.getCalls().iterator().next().getCallRef());

        // Device capabilities
        assertNotNull(state.getDeviceCapabilities());
        assertEquals(1, state.getDeviceCapabilities().size());
        Capabilities cap = state.getDeviceCapabilities().iterator().next();
        assertEquals("Phone1", cap.getDeviceId());
        assertTrue(cap.canMakeCall());
        assertFalse(cap.canMakeBusinessCall());
        assertTrue(cap.canMakePrivateCall());
        assertFalse(cap.canUnParkCall());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnTelephonyStateEvent event = gson.fromJson(json, OnTelephonyStateEvent.class);

        assertNull(event.getLoginName());

        TelephonicState state = event.getState();
        assertNull(state);
    }
}