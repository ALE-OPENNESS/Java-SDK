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

package com.ale.o2g.events.ccrt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.common.ServiceState;

public class OnPGOtherRtiChangedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "name": "ForwardPG",
            "number": "2001",
            "type": "Forward",
            "state": "Opened",
            "nbOfACDCalls": 5,
            "incomingTraffic": 12,
            "afeKey": 4321
        }
        """;

        OnPGOtherRtiChangedEvent event = gson.fromJson(json, OnPGOtherRtiChangedEvent.class);

        assertEquals("ForwardPG", event.getName());
        assertEquals("2001", event.getNumber());
        assertEquals(OtherProcessingGroupType.FORWARD, event.getType());
        assertEquals(ServiceState.OPENED, event.getState());
        assertEquals(5, event.getNbOfACDCalls());
        assertEquals(12, event.getIncomingTraffic());
        assertEquals(4321, event.getKey());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnPGOtherRtiChangedEvent event = gson.fromJson(json, OnPGOtherRtiChangedEvent.class);

        assertNull(event.getName());
        assertNull(event.getNumber());
        assertNull(event.getType());
        assertNull(event.getState());
        assertEquals(0, event.getNbOfACDCalls());
        assertEquals(0, event.getIncomingTraffic());
        assertEquals(0, event.getKey());
    }
}