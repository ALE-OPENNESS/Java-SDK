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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.ccm.O2GPilotRule;
import com.ale.o2g.test.AbstractJsonTest;

/**
 * 
 */
public class PilotRuleTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON with all fields
        String json = """
                {
                    "ruleNumber": "11",
                    "name": "Welcome-morning",
                    "active": true
                }
                """;

        O2GPilotRule o2gP = gson.fromJson(json, O2GPilotRule.class);
        PilotRule pilotRule = o2gP.toPilotRule();

        assertEquals(11, pilotRule.getNumber());
        assertEquals("Welcome-morning", pilotRule.getName());
        assertTrue(pilotRule.isActive());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";


        O2GPilotRule o2gP = gson.fromJson(json, O2GPilotRule.class);
        PilotRule pilotRule = o2gP.toPilotRule();

        assertEquals(-1, pilotRule.getNumber());
        assertNull(pilotRule.getName());
        assertFalse(pilotRule.isActive());
    }
}