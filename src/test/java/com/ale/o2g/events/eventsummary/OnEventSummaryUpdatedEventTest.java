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

package com.ale.o2g.events.eventsummary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.eventsummary.EventSummary;

public class OnEventSummaryUpdatedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "eventSummary": {
                "missedCallsNb": 5,
                "voiceMessagesNb": 3,
                "callBackRequestsNb": 2,
                "faxNb": 1,
                "newTextNb": 4,
                "oldTextNb": 6,
                "eventWaiting": true
            }
        }
        """;

        OnEventSummaryUpdatedEvent event = gson.fromJson(json, OnEventSummaryUpdatedEvent.class);

        // Basic fields
        assertEquals("oxe1000", event.getLoginName());

        // Event summary
        EventSummary summary = event.getEventSummary();
        assertNotNull(summary);
        assertEquals(5, summary.getMissedCallsNb());
        assertEquals(3, summary.getVoiceMessagesNb());
        assertEquals(2, summary.getCallBackRequestsNb());
        assertEquals(1, summary.getFaxNb());
        assertEquals(4, summary.getNewTextNb());
        assertEquals(6, summary.getOldTextNb());
        assertTrue(summary.isEventWaiting());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnEventSummaryUpdatedEvent event = gson.fromJson(json, OnEventSummaryUpdatedEvent.class);

        // loginName is missing
        assertNull(event.getLoginName());

        // Event summary is missing
        assertNull(event.getEventSummary());
    }

    @Test
    void testEventSummaryWithNullFields() {
        String json = """
        {
            "loginName": "oxe1001",
            "eventSummary": {
                "missedCallsNb": null,
                "voiceMessagesNb": null,
                "callBackRequestsNb": null,
                "faxNb": null,
                "newTextNb": null,
                "oldTextNb": null,
                "eventWaiting": false
            }
        }
        """;

        OnEventSummaryUpdatedEvent event = gson.fromJson(json, OnEventSummaryUpdatedEvent.class);
        EventSummary summary = event.getEventSummary();

        assertEquals("oxe1001", event.getLoginName());
        assertNotNull(summary);

        // Null Integer fields should return 0 via getNotNullInt
        assertEquals(0, summary.getMissedCallsNb());
        assertEquals(0, summary.getVoiceMessagesNb());
        assertEquals(0, summary.getCallBackRequestsNb());
        assertEquals(0, summary.getFaxNb());
        assertEquals(0, summary.getNewTextNb());
        assertEquals(0, summary.getOldTextNb());

        // eventWaiting
        assertFalse(summary.isEventWaiting());
    }
}