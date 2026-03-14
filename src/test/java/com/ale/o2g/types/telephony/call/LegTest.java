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

package com.ale.o2g.types.telephony.call;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class LegTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON representing a full Leg object
        String json = """
                {
                    "deviceId": "12000",
                    "state": "RINGING_INCOMING",
                    "ringingRemote": true,
                    "capabilities": {
                        "answer": true,
                        "drop": true,
                        "hold": true,
                        "reconnect": true,
                        "sendDtmf": true
                    }
                }
                """;

        Leg leg = gson.fromJson(json, Leg.class);

        // Basic fields
        assertEquals("12000", leg.getDeviceId());
        assertTrue(leg.isRingingRemote());

        // MediaState
        assertEquals(MediaState.RINGING_INCOMING, leg.getState());

        // Capabilities
        assertNotNull(leg.getCapabilities());
        assertTrue(leg.getCapabilities().canAnswer());
        assertTrue(leg.getCapabilities().canDrop());
        assertTrue(leg.getCapabilities().canHold());
        assertFalse(leg.getCapabilities().canRetrieve());
        assertTrue(leg.getCapabilities().canReconnect());
        assertFalse(leg.getCapabilities().canMute());
        assertTrue(leg.getCapabilities().canSendDtmf());
        assertFalse(leg.getCapabilities().canSwitchDevice());
    }

    @Test
    void testDeserializationMinimal() {
        // Minimal JSON (empty)
        String json = "{}";

        Leg leg = gson.fromJson(json, Leg.class);

        assertNull(leg.getDeviceId());
        assertNull(leg.getState());
        assertFalse(leg.isRingingRemote());
        assertNull(leg.getCapabilities());
    }
}