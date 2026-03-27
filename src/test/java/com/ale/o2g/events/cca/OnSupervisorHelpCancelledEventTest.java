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

package com.ale.o2g.events.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

public class OnSupervisorHelpCancelledEventTest extends AbstractJsonTest {

    @Test
    void testEventDeserialization() {
        String json = """
        {
            "eventName": "SupervisorHelpCancelled",
            "loginName": "supervisor01",
            "agentNumber": "agent123"
        }
        """;

        OnSupervisorHelpCancelledEvent event = gson.fromJson(json, OnSupervisorHelpCancelledEvent.class);

        // Validate login name
        assertEquals("supervisor01", event.getLoginName());

        // Validate agent/party number
        assertEquals("agent123", event.getPartyNumber());

    }

    @Test
    void testEmptyValues() {
        String json = """
        {
            "eventName": "SupervisorHelpCancelled",
            "loginName": "",
            "agentNumber": ""
        }
        """;

        OnSupervisorHelpCancelledEvent event = gson.fromJson(json, OnSupervisorHelpCancelledEvent.class);

        assertEquals("", event.getLoginName());
        assertEquals("", event.getPartyNumber());
    }
}