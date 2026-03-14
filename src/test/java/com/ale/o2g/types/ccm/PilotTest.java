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

package com.ale.o2g.types.ccm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.ccm.O2GPilot;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.common.ServiceState;

public class PilotTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
            {
                "number": "31000",
                "name": "Reception",
                "state": "Opened",
                "waitingTime": 5,
                "saturation": false,
                "rules": {
                    "ruleList": [
                        { "ruleNumber": "11", "name": "Morning", "active": true },
                        { "ruleNumber": "12", "name": "Evening", "active": false }
                    ]
                },
                "possibleTransfer": true,
                "supervisedTransfer": false
            }
            """;

        O2GPilot o2gPilot = gson.fromJson(json, O2GPilot.class);
        Pilot pilot = o2gPilot.toPilot();

        assertEquals("31000", pilot.getNumber());
        assertEquals("Reception", pilot.getName());
        assertEquals(ServiceState.OPENED, pilot.getState());
        assertEquals(5, pilot.getWaitingTime());
        assertFalse(pilot.isSaturation());
        assertTrue(pilot.isPossibleTransfer());
        assertFalse(pilot.isSupervisedTransfer());

        PilotRuleSet rules = pilot.getRules();
        assertNotNull(rules);
        assertEquals(2, rules.size());

        PilotRule rule11 = rules.get(11);
        assertNotNull(rule11);
        assertEquals("Morning", rule11.getName());
        assertTrue(rule11.isActive());

        PilotRule rule12 = rules.get(12);
        assertNotNull(rule12);
        assertEquals("Evening", rule12.getName());
        assertFalse(rule12.isActive());
    }

    @Test
    void testDeserializationMinimal() {
        String json = "{}";

        O2GPilot o2gPilot = gson.fromJson(json, O2GPilot.class);
        Pilot pilot = o2gPilot.toPilot();

        assertNull(pilot.getNumber());
        assertNull(pilot.getName());
        assertNull(pilot.getState());
        assertEquals(0, pilot.getWaitingTime());
        assertFalse(pilot.isSaturation());
        assertFalse(pilot.isPossibleTransfer());
        assertFalse(pilot.isSupervisedTransfer());

        PilotRuleSet rules = pilot.getRules();
        assertNotNull(rules);
        assertEquals(0, rules.size());
    }

    @Test
    void testDeserializationNullRules() {
        String json = """
            {
                "number": "32000",
                "rules": null
            }
            """;

        O2GPilot o2gPilot = gson.fromJson(json, O2GPilot.class);
        Pilot pilot = o2gPilot.toPilot();

        assertEquals("32000", pilot.getNumber());
        PilotRuleSet rules = pilot.getRules();
        assertNotNull(rules);
        assertEquals(0, rules.size());
    }

    @Test
    void testDeserializationEmptyRuleList() {
        String json = """
            {
                "number": "33000",
                "rules": { "ruleList": [] }
            }
            """;

        O2GPilot o2gPilot = gson.fromJson(json, O2GPilot.class);
        Pilot pilot = o2gPilot.toPilot();

        assertEquals("33000", pilot.getNumber());
        PilotRuleSet rules = pilot.getRules();
        assertNotNull(rules);
        assertEquals(0, rules.size());
    }
}