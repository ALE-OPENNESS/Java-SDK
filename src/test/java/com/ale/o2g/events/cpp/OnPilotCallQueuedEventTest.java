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

package com.ale.o2g.events.cpp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.events.ccp.OnPilotCallQueuedEvent;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.telephony.call.Cause;

public class OnPilotCallQueuedEventTest extends AbstractJsonTest {

    @Test
    void testFullEventDeserialization() {
        String json = """
        {
            "pilot": "1001",
            "caller": "5551234",
            "callRef": "CALL-REF-002",
            "cause": "ACD_ENTER_DISTRIBUTION",
            "queue": "QUEUE-01",
            "numberOfQueued": 5
        }
        """;

        OnPilotCallQueuedEvent event = gson.fromJson(json, OnPilotCallQueuedEvent.class);

        assertEquals("1001", event.getPilot());
        assertEquals("5551234", event.getCaller());
        assertEquals("CALL-REF-002", event.getCallRef());
        assertEquals(Cause.ACD_ENTER_DISTRIBUTION, event.getCause());
        assertEquals("QUEUE-01", event.getQueue());
        assertEquals(5, event.getCallNumber());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";
        OnPilotCallQueuedEvent event = gson.fromJson(json, OnPilotCallQueuedEvent.class);

        assertNull(event.getPilot());
        assertNull(event.getCaller());
        assertNull(event.getCallRef());
        assertNull(event.getCause());
        assertNull(event.getQueue());
        assertEquals(0, event.getCallNumber()); // default int value
    }
}