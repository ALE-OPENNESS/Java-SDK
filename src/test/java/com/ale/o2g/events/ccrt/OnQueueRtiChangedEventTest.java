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

public class OnQueueRtiChangedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "name": "SupportQueue",
            "number": "1001",
            "type": "Normal",
            "state": "Opened",
            "nbOfAgentsInDistribution": 5,
            "incomingTraffic": 12,
            "outgoingTraffic": 3,
            "nbOfWaitingCalls": 7,
            "currentWaitingTime": 45,
            "fillingRate": 80,
            "expectedWaitingTime": 60,
            "longestWaitingTimeInList": 120,
            "afeKey": 999
        }
        """;

        OnQueueRtiChangedEvent event = gson.fromJson(json, OnQueueRtiChangedEvent.class);

        // Basic fields
        assertEquals("SupportQueue", event.getName());
        assertEquals("1001", event.getNumber());
        assertEquals(QueueType.NORMAL, event.getType());
        assertEquals(ServiceState.OPENED, event.getState());

        // Numeric fields
        assertEquals(5, event.getNbOfAgentsInDistribution());
        assertEquals(12, event.getIncomingTraffic());
        assertEquals(3, event.getOutgoingTraffic());
        assertEquals(7, event.getNbOfWaitingCalls());
        assertEquals(45, event.getCurrentWaitingTime());
        assertEquals(80, event.getFillingRate());
        assertEquals(60, event.getExpectedWaitingTime());
        assertEquals(120, event.getLongestWaitingTimeInList());
        assertEquals(999, event.getKey());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnQueueRtiChangedEvent event = gson.fromJson(json, OnQueueRtiChangedEvent.class);

        // Fields should default to null or 0
        assertNull(event.getName());
        assertNull(event.getNumber());
        assertNull(event.getType());
        assertNull(event.getState());
        assertEquals(0, event.getNbOfAgentsInDistribution());
        assertEquals(0, event.getIncomingTraffic());
        assertEquals(0, event.getOutgoingTraffic());
        assertEquals(0, event.getNbOfWaitingCalls());
        assertEquals(0, event.getCurrentWaitingTime());
        assertEquals(0, event.getFillingRate());
        assertEquals(0, event.getExpectedWaitingTime());
        assertEquals(0, event.getLongestWaitingTimeInList());
        assertEquals(0, event.getKey());
    }
}