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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

public class PilotStatisticsRowTest extends AbstractJsonTest {

    @Test
    public void testPilotStatisticsRowFullCoverage() {

        String json = """
        {
          "date": "2026-03-08",
          "queueName": "QueueA",
          "pilotName": "SalesPilot",
          "pilotNumber": "101",
          "nbCallsOpen": 10,
          "nbCallsBlocked": 2,
          "nbCallsForward": 3,
          "nbCallsByTransfer": 1,
          "nbCallsByMutualAid": 0,
          "maxNbSimultCalls": 5,
          "nbOverflowInQueue": 1,
          "nbOverflowInRinging": 0,
          "nbCallsWOQueuing": 7,
          "nbCallsAfterQueuing": 3,
          "nbCallsSentInMutualAidQueue": 2,
          "nbCallsRedirectedOutACDArea": 1,
          "nbCallsDissuaded": 0,
          "nbCallsDissuadedAfterTryingMutualAid": 0,
          "nbCallsVGTypePG": 4,
          "nbCallsSentToPG": 2,
          "nbCallsRejectedLackOfRes": 1,
          "nbCallsServedByAgent": 8,
          "nbCallsServedInTime": 7,
          "nbCallsServedTooQuick": 0,
          "nbCallsWithoutTransCode": 3,
          "nbCallsWithTransCode": 5,
          "nbCallsRedistrib": 1,
          "nbCallsBeforeTS1": 2,
          "percentCallsBeforeTS1": "25.0",
          "nbCallsBeforeTS2": 3,
          "percentCallsBeforeTS2": "37.5",
          "nbCallsBeforeTS3": 1,
          "percentCallsBeforeTS3": "12.5",
          "nbCallsBeforeTS4": 2,
          "percentCallsBeforeTS4": "25.0",
          "nbCallsAfterTS4": 0,
          "percentCallsAfterTS4": "0.0",
          "nbAbandonsOnGreetingsVG": 1,
          "nbAbandonsOn1WaitingVG": 0,
          "nbAbandonsOn2WaitingVG": 0,
          "nbAbandonsOn3WaitingVG": 0,
          "nbAbandonsOn4WaitingVG": 0,
          "nbAbandonsOn5WaitingVG": 0,
          "nbAbandonsOn6WaitingVG": 0,
          "nbAbandonsOnRinging": 1,
          "nbAbandonsOnGenFwdVG": 0,
          "nbAbandonsOnBlockedVG": 0,
          "nbAbandonsOnAgentBusy": 0,
          "nbAbandons": 2,
          "nbAbandonsBeforeTS1": 1,
          "percentAbandonsBeforeTS1": "50.0",
          "nbAbandonsBeforeTS2": 0,
          "percentAbandonsBeforeTS2": "0.0",
          "nbAbandonsBeforeTS3": 1,
          "percentAbandonsBeforeTS3": "50.0",
          "nbAbandonsBeforeTS4": 0,
          "percentAbandonsBeforeTS4": "0.0",
          "nbAbandonsAfterTS4": 0,
          "percentAbandonsAfterTS4": "0.0",
          "callProcTDur": "00:10:00",
          "callProcADur": "00:01:15",
          "greetingListenTDur": "00:02:00",
          "greetingListenADur": "00:00:30",
          "beforeQueuingTDur": "00:01:00",
          "waitServedCallsTDur": "00:05:00",
          "waitServedCallsADur": "00:00:45",
          "waitAbandonnedCallsTDur": "00:01:30",
          "waitAbandonnedCallsADur": "00:00:15",
          "ringingTDur": "00:03:00",
          "ringingADur": "00:00:20",
          "convTDur": "00:08:00",
          "convADur": "00:01:00",
          "holdCallsTDur": "00:02:30",
          "holdCallsADur": "00:00:40",
          "wrapupTDur": "00:01:20",
          "wrapupADur": "00:00:20",
          "longestWaitingDur": "00:05:30",
          "serviceLevel": "90.0",
          "efficiency": "95.0",
          "inServiceState": "85.0",
          "genFwdState": "5.0",
          "blockedState": "10.0",
          "dnbTotReceivedCalls": 15,
          "dnbCallsOpen": 10,
          "dnbCallsBlocked": 2,
          "dnbCallsForward": 3,
          "dnbDirectRoute": 7,
          "dnbIndirectRoute": 8,
          "dnbTotServedCalls": 12,
          "defficiency": "80.0",
          "dnbCallsWOQueuing": 6,
          "dnbCallsAfterQueuing": 6,
          "dnbCallsBeforeTS1": 2,
          "dnbCallsBeforeTS2": 3,
          "dnbCallsBeforeTS3": 1,
          "dnbCallsBeforeTS4": 2,
          "dnbCallsAfterTS4": 0,
          "dwaitServedCallsADur": "00:00:40",
          "dnbAbandons": 2,
          "dnbAbandonsOnGreetingsVG": 1,
          "dnbAbandonsOnWaitingVG": 1,
          "dnbAbandonsBeforeTS1": 1,
          "dnbAbandonsBeforeTS2": 0,
          "dnbAbandonsBeforeTS3": 1,
          "dnbAbandonsBeforeTS4": 0,
          "dnbAbandonsAfterTS4": 0,
          "dnbCallsRejectedLackOfRes": 1,
          "dnbCallsDissuaded": 0,
          "dnbTotCallsRedirected": 2,
          "dnbCallsRedirectedOutACDArea": 1,
          "dnbCallsSentInMutualAidQueue": 1,
          "dnbCallsSentToPG": 1,
          "dserviceLevel": "90.0",
          "defficiency2": "95.0",
          "dcallProcADur": "00:01:10",
          "dconvADur": "00:01:05",
          "dwrapupADur": "00:00:15",
          "dholdCallsADur": "00:00:35",
          "dringingADur": "00:00:18",
          "dwaitingADur": "00:00:50",
          "dlongestWaitingDur": "00:05:15"
        }
        """;

        PilotStatisticsRow row = gson.fromJson(json, PilotStatisticsRow.class);

        assertNotNull(row);

        // Identity
//        assertEquals(LocalDateTime.of(2026, 3, 8, 0, 0), row.getDate());
        assertEquals("QueueA", row.getQueueName());
        assertEquals("SalesPilot", row.getPilotName());
        assertEquals("101", row.getPilotNumber());

        // Counters
        assertEquals(10, row.getNbCallsOpen());
        assertEquals(2, row.getNbCallsBlocked());
        assertEquals(3, row.getNbCallsForward());
        assertEquals(1, row.getNbCallsByTransfer());
        assertEquals(0, row.getNbCallsByMutualAid());
        assertEquals(5, row.getMaxNbSimultCalls());
        assertEquals(1, row.getNbOverflowInQueue());
        assertEquals(0, row.getNbOverflowInRinging());

        // Percentages
        assertEquals(25.0, row.getPercentCallsBeforeTS1());
        assertEquals(37.5, row.getPercentCallsBeforeTS2());
        assertEquals(12.5, row.getPercentCallsBeforeTS3());
        assertEquals(25.0, row.getPercentCallsBeforeTS4());
        assertEquals(0.0, row.getPercentCallsAfterTS4());

        assertEquals(50.0, row.getPercentAbandonsBeforeTS1());
        assertEquals(0.0, row.getPercentAbandonsBeforeTS2());
        assertEquals(50.0, row.getPercentAbandonsBeforeTS3());
        assertEquals(0.0, row.getPercentAbandonsBeforeTS4());
        assertEquals(0.0, row.getPercentAbandonsAfterTS4());

        assertEquals(90.0, row.getServiceLevel());
        assertEquals(95.0, row.getEfficiency());
        assertEquals(85.0, row.getInServiceState());
        assertEquals(5.0, row.getGenFwdState());
        assertEquals(10.0, row.getBlockedState());

        // Durations
        assertEquals(Duration.ofMinutes(10), row.getCallProcTDur());
        assertEquals(Duration.ofMinutes(1).plusSeconds(15), row.getCallProcADur());
        assertEquals(Duration.ofMinutes(2), row.getGreetingListenTDur());
        assertEquals(Duration.ofSeconds(30), row.getGreetingListenADur());
        assertEquals(Duration.ofMinutes(1), row.getBeforeQueuingTDur());
        assertEquals(Duration.ofMinutes(5), row.getWaitServedCallsTDur());
        assertEquals(Duration.ofSeconds(45), row.getWaitServedCallsADur());
        assertEquals(Duration.ofMinutes(1).plusSeconds(30), row.getWaitAbandonnedCallsTDur());
        assertEquals(Duration.ofSeconds(15), row.getWaitAbandonnedCallsADur());
        assertEquals(Duration.ofMinutes(3), row.getRingingTDur());
        assertEquals(Duration.ofSeconds(20), row.getRingingADur());
        assertEquals(Duration.ofMinutes(8), row.getConvTDur());
        assertEquals(Duration.ofMinutes(1), row.getConvADur());
        assertEquals(Duration.ofMinutes(2).plusSeconds(30), row.getHoldCallsTDur());
        assertEquals(Duration.ofSeconds(40), row.getHoldCallsADur());
        assertEquals(Duration.ofMinutes(1).plusSeconds(20), row.getWrapupTDur());
        assertEquals(Duration.ofSeconds(20), row.getWrapupADur());
        assertEquals(Duration.ofMinutes(5).plusSeconds(30), row.getLongestWaitingDur());

        // D-prefixed fields
        assertEquals(15, row.getDnbTotReceivedCalls());
        assertEquals(Duration.ofSeconds(40), row.getDwaitServedCallsADur());
        assertEquals(Duration.ofMinutes(1).plusSeconds(10), row.getDcallProcADur());
        assertEquals(Duration.ofMinutes(1).plusSeconds(5), row.getDconvADur());
        assertEquals(Duration.ofSeconds(15), row.getDwrapupADur());
        assertEquals(Duration.ofSeconds(35), row.getDholdCallsADur());
        assertEquals(Duration.ofSeconds(18), row.getDringingADur());
        assertEquals(Duration.ofSeconds(50), row.getDwaitingADur());
        assertEquals(Duration.ofMinutes(5).plusSeconds(15), row.getDlongestWaitingDur());
    }
}