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

class InfoTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
                {
                    "queueWaitingTime": 15,
                    "globalWaitingTime": 40,
                    "agentGroup": "12000",
                    "local": true
                }
                """;

        Info info = gson.fromJson(json, Info.class);

        assertEquals(15, info.getQueueWaitingTime());
        assertEquals(40, info.getGlobalWaitingTime());
        assertEquals("12000", info.getAgentGroup());
        assertTrue(info.isLocal());
    }

    @Test
    void testDeserializationLocalFalse() {
        String json = """
                {
                    "queueWaitingTime": 5,
                    "globalWaitingTime": 10,
                    "agentGroup": "12000"
                }
                """;

        Info info = gson.fromJson(json, Info.class);

        assertEquals(5, info.getQueueWaitingTime());
        assertEquals(10, info.getGlobalWaitingTime());
        assertEquals("12000", info.getAgentGroup());
        assertFalse(info.isLocal());
    }

    @Test
    void testDeserializationMin() {
        String json = "{}";

        Info info = gson.fromJson(json, Info.class);

        assertEquals(0, info.getQueueWaitingTime()); // default int
        assertEquals(0, info.getGlobalWaitingTime()); // default int
        assertNull(info.getAgentGroup());
        assertFalse(info.isLocal()); // default boolean
    }
}