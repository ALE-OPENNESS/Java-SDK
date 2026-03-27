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

package com.ale.o2g.types.telephony.call.acd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class PilotTransferInfoTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
                {
                    "transferPossible": true,
                    "pilotStatus": "OPEN"
                }
                """;

        PilotTransferInfo info = gson.fromJson(json, PilotTransferInfo.class);

        assertTrue(info.isTransferPossible());
        assertEquals(PilotStatus.OPEN, info.getPilotState());
    }

    @Test
    void testDeserializationTransferNotPossible() {
        String json = """
                {
                    "transferPossible": false,
                    "pilotStatus": "BLOCKED"
                }
                """;

        PilotTransferInfo info = gson.fromJson(json, PilotTransferInfo.class);

        assertFalse(info.isTransferPossible());
        assertEquals(PilotStatus.BLOCKED, info.getPilotState());
    }

    @Test
    void testDeserializationMin() {
        String json = "{}";

        PilotTransferInfo info = gson.fromJson(json, PilotTransferInfo.class);

        // primitive boolean defaults to false
        assertFalse(info.isTransferPossible());
        assertNull(info.getPilotState());
    }

}