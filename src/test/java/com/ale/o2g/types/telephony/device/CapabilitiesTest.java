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

package com.ale.o2g.types.telephony.device;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class CapabilitiesTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
            {
                "deviceId": "12000",
                "makeCall": true,
                "makeBusinessCall": true,
                "makePrivateCall": true,
                "unParkCall": true
            }
        """;

        Capabilities capabilities = gson.fromJson(json, Capabilities.class);

        assertEquals("12000", capabilities.getDeviceId());
        assertTrue(capabilities.canMakeCall());
        assertTrue(capabilities.canMakeBusinessCall());
        assertTrue(capabilities.canMakePrivateCall());
        assertTrue(capabilities.canUnParkCall());
    }

    @Test
    void testDeserializationMin() {
        String json = "{}";

        Capabilities capabilities = gson.fromJson(json, Capabilities.class);

        assertNull(capabilities.getDeviceId());
        assertFalse(capabilities.canMakeCall());
        assertFalse(capabilities.canMakeBusinessCall());
        assertFalse(capabilities.canMakePrivateCall());
        assertFalse(capabilities.canUnParkCall());
    }
}