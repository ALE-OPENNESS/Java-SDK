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

package com.ale.o2g.events.comlog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

public class OnComRecordsDeletedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "recordIds": [201, 202, 203]
        }
        """;

        OnComRecordsDeletedEvent event = gson.fromJson(json, OnComRecordsDeletedEvent.class);

        // Basic field
        assertEquals("oxe1000", event.getLoginName());

        // Record IDs
        assertNotNull(event.getRecordIds());
        assertEquals(3, event.getRecordIds().size());
        assertTrue(event.getRecordIds().containsAll(List.of(201L, 202L, 203L)));
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnComRecordsDeletedEvent event = gson.fromJson(json, OnComRecordsDeletedEvent.class);

        assertNull(event.getLoginName());
        assertNull(event.getRecordIds());
    }
}