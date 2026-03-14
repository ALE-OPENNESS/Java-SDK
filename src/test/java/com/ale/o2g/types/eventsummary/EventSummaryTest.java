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

package com.ale.o2g.types.eventsummary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

/**
 * 
 */
public class EventSummaryTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON with all fields
        String json = """
                {
                    "missedCallsNb": 1,
                    "voiceMessagesNb": 2,
                    "callBackRequestsNb": 3,
                    "faxNb": 4,
                    "newTextNb": 5,
                    "oldTextNb": 6,
                    "eventWaiting": true
                }
                """;

        EventSummary es = gson.fromJson(json, EventSummary.class);

        assertEquals(1, es.getMissedCallsNb());
        assertEquals(2, es.getVoiceMessagesNb());
        assertEquals(3, es.getCallBackRequestsNb());
        assertEquals(4, es.getFaxNb());
        assertEquals(5, es.getNewTextNb());
        assertEquals(6, es.getOldTextNb());
        assertTrue(es.isEventWaiting());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        EventSummary es = gson.fromJson(json, EventSummary.class);

        assertEquals(0, es.getMissedCallsNb());
        assertEquals(0, es.getVoiceMessagesNb());
        assertEquals(0, es.getCallBackRequestsNb());
        assertEquals(0, es.getFaxNb());
        assertEquals(0, es.getNewTextNb());
        assertEquals(0, es.getOldTextNb());
        assertFalse(es.isEventWaiting());
    }
}