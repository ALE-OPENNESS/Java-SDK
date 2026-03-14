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

import static com.ale.o2g.test.ExtendAssert.assertEqualsUtc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

public class AgentStatisticsRowTest extends AbstractJsonTest {

    @Test
    public void testAgentStatisticsRowFullCoverage() {

        String json = """
        {
          "date": "2026-03-02T12:30",
          "login": "agent001",
          "operator": "OP1",
          "firstName": "John",
          "lastName": "Doe",
          "number": "1001",
          "group": "GroupA",

          "nbRotating": 3,
          "nbPickedUp": 5,
          "nbPickup": 2,
          "nbLocalOutNonAcd": 1,
          "nbExtOutNonacd": 4,
          "nbRingAcd": 7,

          "ringAcdServedTDur": "00:02:00",
          "ringAcdServedADur": "00:00:30",
          "ringInNonAcdExtServedTDur": "00:01:00",
          "ringInNonAcdExtServedADur": "00:00:20",

          "ringAcdTDur": "00:05:00",
          "ringAcdADur": "00:00:40",
          "ringInNonAcdExtTDur": "00:01:30",
          "ringInNonAcdExtADur": "00:00:25",

          "ringTDur": "00:06:40",
          "ringADur": "00:00:35",

          "convAcdTDur": "00:08:20",
          "convAcdADur": "00:01:00",
          "wrapupAcdTDur": "00:01:40",

          "convLocOutNonacdTDur": "00:01:20",
          "convLocOutNonacdADur": "00:00:20",

          "convExtOutTDur": "00:01:10",
          "convExtOutADur": "00:00:15",

          "convLocInNonacdTDur": "00:01:00",
          "convLocInNonacdADur": "00:00:18",

          "convExtInNonacdTDur": "00:00:50",
          "convExtInNonacdADur": "00:00:17",

          "outAcdCommTDur": "00:00:40",
          "outAcdCommADur": "00:00:10",

          "outAcdConvTDur": "00:00:30",
          "outAcdConvADur": "00:00:09",

          "outAcdTransactTDur": "00:00:20",
          "outAcdTransactADur": "00:00:06",

          "outAcdWrapupTDur": "00:00:15",
          "outAcdWrapupADur": "00:00:05",

          "outAcdPauseTDur": "00:00:12",
          "outAcdPauseADur": "00:00:04",

          "wrapUpIdleTDur": "00:05:00",
          "callOnWrapupTDur": "00:03:20",
          "busyOnWrapupTDur": "00:01:40",
          "busyTDur": "00:13:20",

          "loggedOutPerTime": 12.5,
          "notAssignedPerTime": 20.0,
          "assignedPerTime": 60.0,
          "withdrawPerTime": 7.5,

          "pilotAgentStatsRows": [
            {
              "pilotNumber": "100",
              "pilotName": "Sales",

              "nbCallsReceived": 4,
              "nbCallsTransfIn": 1,
              "nbCallsServed": 3,
              "nbCallsServedTooQuickly": 0,
              "nbCallsWithEnquiry": 1,
              "nbCallsWithHelp": 0,
              "nbCallsTransf": 1,
              "nbCallsTransfToAgent": 2,
              "nbCallsInWrapup": 1,

              "maxCallProcDur": "00:01:00",
              "maxConvDur": "00:00:50",
              "maxWrapupDur": "00:00:30",

              "callProcTDur": "00:01:00",
              "callProcADur": "00:00:20",
              "convTDur": "00:00:50",
              "convADur": "00:00:15",
              "wrapupTDur": "00:00:30",
              "wrapupADur": "00:00:10",

              "convInWrapupTDur": "00:00:15",
              "busyTimeInWrapupTDur": "00:00:10",

              "onHoldTDur": "00:00:05",
              "onHoldADur": "00:00:02",

              "transTDur": "00:00:10",
              "transADur": "00:00:03",

              "pauseTDur": "00:00:05",
              "pauseADur": "00:00:02"
            }
          ]
        }
        """;

        AgentStatisticsRow row = gson.fromJson(json, AgentStatisticsRow.class);

        assertNotNull(row);

        assertEqualsUtc("2026-03-02T12:30", row.getDate());
        
        // identity        
        assertEquals("agent001", row.getLogin());
        assertEquals("OP1", row.getOperator());
        assertEquals("John", row.getFirstName());
        assertEquals("Doe", row.getLastName());
        assertEquals("1001", row.getNumber());
        assertEquals("GroupA", row.getGroup());

        // counters
        assertEquals(3, row.getNbRotating());
        assertEquals(5, row.getNbPickedUp());
        assertEquals(2, row.getNbPickup());

        // durations
        assertEquals(Duration.ofMinutes(2), row.getRingAcdServedTDur());
        assertEquals(Duration.ofSeconds(30), row.getRingAcdServedADur());
        assertEquals(Duration.ofMinutes(5), row.getRingAcdTDur());
        assertEquals(Duration.ofMinutes(8).plusSeconds(20), row.getConvAcdTDur());
        assertEquals(Duration.ofMinutes(13).plusSeconds(20), row.getBusyTDur());

        // percentages
        assertEquals(12.5, row.getLoggedOutPerTime());
        assertEquals(20.0, row.getNotAssignedPerTime());
        assertEquals(60.0, row.getAssignedPerTime());
        assertEquals(7.5, row.getWithdrawPerTime());

        // pilot rows
        List<AgentByPilotStatisticsRow> pilots = row.getByPilotRows();
        assertEquals(1, pilots.size());

        AgentByPilotStatisticsRow pilot = pilots.get(0);

        assertEquals("100", pilot.getPilotNumber());
        assertEquals("Sales", pilot.getPilotName());

        assertEquals(4, pilot.getNbCallsReceived());
        assertEquals(1, pilot.getNbCallsTransfIn());
        assertEquals(3, pilot.getNbCallsServed());

        assertEquals(Duration.ofMinutes(1), pilot.getMaxCallProcDur());
        assertEquals(Duration.ofSeconds(50), pilot.getMaxConvDur());
        assertEquals(Duration.ofSeconds(30), pilot.getMaxWrapupDur());
    }

    @Test
    public void testAgentStatisticsSimpleDate() {

        String json = """
        {
          "date": "2026-03-02",
          "login": "agent001",
          "operator": "OP1",
          "firstName": "John",
          "lastName": "Doe",
          "number": "1001",
          "group": "GroupA",

          "nbRotating": 3
        }
        """;

        AgentStatisticsRow row = gson.fromJson(json, AgentStatisticsRow.class);

        assertNotNull(row);

        assertEqualsUtc("2026-03-02", row.getDate());
        
        // identity        
        assertEquals("agent001", row.getLogin());
        assertEquals("OP1", row.getOperator());
        assertEquals("John", row.getFirstName());
        assertEquals("Doe", row.getLastName());
        assertEquals("1001", row.getNumber());
        assertEquals("GroupA", row.getGroup());

        // counters
        assertEquals(3, row.getNbRotating());
    }
}