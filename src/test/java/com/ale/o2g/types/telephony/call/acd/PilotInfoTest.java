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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class PilotInfoTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
                {
                    "number": "3001",
                    "waitingTime": 25,
                    "saturation": true,
                    "supervisedTransfer": true,
                    "pilotTransferInfo": {
                        "transferPossible": true,
                        "pilotStatus": "OPEN"
                    }
                }
                """;

        PilotInfo info = gson.fromJson(json, PilotInfo.class);

        assertEquals("3001", info.getPilotNumber());
        assertEquals(25, info.getWaitingTime());
        assertTrue(info.isSaturated());
        assertTrue(info.isSupervisedTransfer());

        assertNotNull(info.getPilotTransferInfo());
        assertTrue(info.getPilotTransferInfo().isTransferPossible());
        assertEquals(PilotStatus.OPEN, info.getPilotTransferInfo().getPilotState());
    }

    @Test
    void testDeserializationNoTransferInfo() {
        String json = """
                {
                    "number": "3001",
                    "waitingTime": 10,
                    "saturation": false,
                    "supervisedTransfer": false
                }
                """;

        PilotInfo info = gson.fromJson(json, PilotInfo.class);

        assertEquals("3001", info.getPilotNumber());
        assertEquals(10, info.getWaitingTime());
        assertFalse(info.isSaturated());
        assertFalse(info.isSupervisedTransfer());
        assertNull(info.getPilotTransferInfo());
    }

    @Test
    void testDeserializationMin() {
        String json = "{}";

        PilotInfo info = gson.fromJson(json, PilotInfo.class);

        assertNull(info.getPilotNumber());
        assertEquals(0, info.getWaitingTime());
        assertFalse(info.isSaturated());
        assertFalse(info.isSupervisedTransfer());
        assertNull(info.getPilotTransferInfo());
    }

}