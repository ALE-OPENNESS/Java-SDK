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

class AcdDataTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON string with all fields
        String json = """
                {
                    "callInfo": {
                        "queueWaitingTime": 10,
                        "globalWaitingTime": 20,
                        "agentGroup": "31000",
                        "local": true
                    },
                    "queueData": {
                        "waitingTime": 15,
                        "saturated": false
                    },
                    "pilotNumber": "22000",
                    "rsiNumber": "5000",
                    "supervisedTransfer": true,
                    "pilotTransferInfo": {
                        "transferPossible": true,
                        "pilotStatus": "GENERAL_FORWARDING"
                    }
                }
                """;

        // Deserialize JSON into AcdData
        AcdData acdData = gson.fromJson(json, AcdData.class);

        // Verify callInfo
        assertNotNull(acdData.getCallInfo());
        assertEquals(10, acdData.getCallInfo().getQueueWaitingTime());
        assertEquals(20, acdData.getCallInfo().getGlobalWaitingTime());
        assertEquals("31000", acdData.getCallInfo().getAgentGroup());
        assertTrue(acdData.getCallInfo().isLocal());

        // Verify queueData
        assertNotNull(acdData.getQueueData());
        assertEquals(15, acdData.getQueueData().getWaitingTime());
        assertFalse(acdData.getQueueData().isSaturated());

        // Verify pilot info
        assertEquals("22000", acdData.getPilotNumber());
        assertEquals("5000", acdData.getRsiNumber());
        assertTrue(acdData.isSupervisedTransfer());

        // Verify pilotTransferInfo
        assertNotNull(acdData.getPilotTransferInfo());
        assertTrue(acdData.getPilotTransferInfo().isTransferPossible());
        assertNotNull(acdData.getPilotTransferInfo().getPilotState());
        assertEquals(PilotStatus.GENERAL_FORWARDING, acdData.getPilotTransferInfo().getPilotState());
    }

    @Test
    void testDeserializationMinimal() {
        // Empty JSON
        String json = "{}";

        AcdData acdData = gson.fromJson(json, AcdData.class);

        assertNull(acdData.getCallInfo());
        assertNull(acdData.getQueueData());
        assertNull(acdData.getPilotNumber());
        assertNull(acdData.getRsiNumber());
        assertFalse(acdData.isSupervisedTransfer());
        assertNull(acdData.getPilotTransferInfo());
    }
}