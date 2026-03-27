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

package com.ale.o2g.types.ccstats.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

public class PilotAbandonedCallsStatisticsRowTest extends AbstractJsonTest {

    @Test
    public void testFullCoverage() {

        String json = """
        {
          "date": "2026-03-08T10:15",
          "queueName": "SupportQueue",
          "pilotName": "SalesPilot",
          "pilotNumber": "60141",
          "callerNumber": "0033123456789",
          "waitingTime": 45,
          "abandonedOnGreetingVG": 1,
          "abandonedOn1stWaitingVG": 1,
          "abandonedOn2ndWaitingVG": 0,
          "abandonedOn3rdWaitingVG": 0,
          "abandonedOn4thWaitingVG": 0,
          "abandonedOn5hWaitingVG": 0,
          "abandonedOn6thWaitingVG": 1,
          "abandonedOnRinging": 0,
          "abandonedOnGeneralFwdVG": 1,
          "abandonedOnBlockedVG": 0,
          "abandonedOnDirectCallWaiting": 1
        }
        """;

        PilotAbandonedCallsStatisticsRow row = gson.fromJson(json, PilotAbandonedCallsStatisticsRow.class);

        assertNotNull(row);

        // Identity
        assertEquals(LocalDateTime.of(2026, 3, 8, 10, 15), row.getDate());
        assertEquals("SupportQueue", row.getQueueName());
        assertEquals("SalesPilot", row.getPilotName());
        assertEquals("60141", row.getPilotNumber());
        assertEquals("0033123456789", row.getCallerNumber());

        // Waiting time
        assertEquals(45, row.getWaitingTime());

        // Boolean flags — true cases
        assertTrue(row.isAbandonedOnGreetingVG());
        assertTrue(row.isAbandonedOn1stWaitingVG());
        assertTrue(row.isAbandonedOn6thWaitingVG());
        assertTrue(row.isAbandonedOnGeneralFwdVG());
        assertTrue(row.isAbandonedOnDirectCallWaiting());

        // Boolean flags — false cases (value is 0)
        assertFalse(row.isAbandonedOn2ndWaitingVG());
        assertFalse(row.isAbandonedOn3rdWaitingVG());
        assertFalse(row.isAbandonedOn4thWaitingVG());
        assertFalse(row.isAbandonedOn5hWaitingVG());
        assertFalse(row.isAbandonedOnRinging());
        assertFalse(row.isAbandonedOnBlockedVG());
    }

    @Test
    public void testAbsentFlagsDefaultToFalse() {

        String json = """
        {
          "pilotName": "MinimalPilot",
          "pilotNumber": "60142"
        }
        """;

        PilotAbandonedCallsStatisticsRow row = gson.fromJson(json, PilotAbandonedCallsStatisticsRow.class);

        assertNotNull(row);

        // Identity fields — present
        assertEquals("MinimalPilot", row.getPilotName());
        assertEquals("60142", row.getPilotNumber());

        // Optional fields — absent
        assertNull(row.getQueueName());
        assertNull(row.getCallerNumber());
        assertNull(row.getWaitingTime());
        assertNull(row.getDate());

        // All boolean flags — absent, must default to false
        assertFalse(row.isAbandonedOnGreetingVG());
        assertFalse(row.isAbandonedOn1stWaitingVG());
        assertFalse(row.isAbandonedOn2ndWaitingVG());
        assertFalse(row.isAbandonedOn3rdWaitingVG());
        assertFalse(row.isAbandonedOn4thWaitingVG());
        assertFalse(row.isAbandonedOn5hWaitingVG());
        assertFalse(row.isAbandonedOn6thWaitingVG());
        assertFalse(row.isAbandonedOnRinging());
        assertFalse(row.isAbandonedOnGeneralFwdVG());
        assertFalse(row.isAbandonedOnBlockedVG());
        assertFalse(row.isAbandonedOnDirectCallWaiting());
    }

    @Test
    public void testEachFlagCanBeSetIndependently() {

        String json = """
        {
          "pilotName": "TestPilot",
          "pilotNumber": "60143",
          "abandonedOnRinging": 1,
          "abandonedOnBlockedVG": 1
        }
        """;

        PilotAbandonedCallsStatisticsRow row = gson.fromJson(json, PilotAbandonedCallsStatisticsRow.class);

        assertNotNull(row);

        // Only these two flags are set
        assertTrue(row.isAbandonedOnRinging());
        assertTrue(row.isAbandonedOnBlockedVG());

        // All others default to false
        assertFalse(row.isAbandonedOnGreetingVG());
        assertFalse(row.isAbandonedOn1stWaitingVG());
        assertFalse(row.isAbandonedOn2ndWaitingVG());
        assertFalse(row.isAbandonedOn3rdWaitingVG());
        assertFalse(row.isAbandonedOn4thWaitingVG());
        assertFalse(row.isAbandonedOn5hWaitingVG());
        assertFalse(row.isAbandonedOn6thWaitingVG());
        assertFalse(row.isAbandonedOnGeneralFwdVG());
        assertFalse(row.isAbandonedOnDirectCallWaiting());
    }
}