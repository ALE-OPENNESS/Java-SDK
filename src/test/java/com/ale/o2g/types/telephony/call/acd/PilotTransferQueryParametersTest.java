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

class PilotTransferQueryParametersTest {

    @Test
    void testDefaultState() {
        PilotTransferQueryParameters params = new PilotTransferQueryParameters();

        assertNull(params.getAgentNumber());
        assertNull(params.getPriorityTransfer());
        assertNull(params.getSupervisedTransfer());
        assertNull(params.getCallProfile());

        assertFalse(params.hasAgentNumber());
        assertFalse(params.hasPriorityTransferCriteria());
        assertFalse(params.hasSupervisedTransferCriteria());
        assertFalse(params.hasCallProfile());
    }

    @Test
    void testAgentNumber() {
        PilotTransferQueryParameters params = new PilotTransferQueryParameters()
                .setAgentNumber("1234");

        assertEquals("1234", params.getAgentNumber());
        assertTrue(params.hasAgentNumber());
    }

    @Test
    void testEmptyAgentNumber() {
        PilotTransferQueryParameters params = new PilotTransferQueryParameters()
                .setAgentNumber("");

        assertEquals("", params.getAgentNumber());
        assertFalse(params.hasAgentNumber());
    }

    @Test
    void testPriorityTransferCriteria() {
        PilotTransferQueryParameters params = new PilotTransferQueryParameters()
                .setPriorityTransfer(true);

        assertTrue(params.getPriorityTransfer());
        assertTrue(params.hasPriorityTransferCriteria());
    }

    @Test
    void testSupervisedTransferCriteria() {
        PilotTransferQueryParameters params = new PilotTransferQueryParameters()
                .setSupervisedTransfer(false);

        assertFalse(params.getSupervisedTransfer());
        assertTrue(params.hasSupervisedTransferCriteria());
    }

    @Test
    void testCallProfile() {
        CallProfile profile = new CallProfile();

        PilotTransferQueryParameters params = new PilotTransferQueryParameters()
                .setCallProfile(profile);

        assertEquals(profile, params.getCallProfile());
        assertTrue(params.hasCallProfile());
    }

    @Test
    void testFluentChaining() {
        CallProfile profile = new CallProfile();

        PilotTransferQueryParameters params = new PilotTransferQueryParameters()
                .setAgentNumber("1001")
                .setPriorityTransfer(true)
                .setSupervisedTransfer(false)
                .setCallProfile(profile);

        assertEquals("1001", params.getAgentNumber());
        assertTrue(params.getPriorityTransfer());
        assertFalse(params.getSupervisedTransfer());
        assertEquals(profile, params.getCallProfile());

        assertTrue(params.hasAgentNumber());
        assertTrue(params.hasPriorityTransferCriteria());
        assertTrue(params.hasSupervisedTransferCriteria());
        assertTrue(params.hasCallProfile());
    }
}