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

package com.ale.o2g.types.cca;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class AgentSkillTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                    "number": 101,
                    "level": 5,
                    "active": true,
                    "domain": 2,
                    "name": "Customer Support",
                    "abvName": "CS"
                }
                """;

        // Deserialize JSON into AgentSkill
        AgentSkill skill = gson.fromJson(json, AgentSkill.class);

        // Verify all fields
        assertEquals(101, skill.getNumber());
        assertEquals(5, skill.getLevel());
        assertTrue(skill.isActive());
        assertEquals(2, skill.getDomain());
        assertEquals("Customer Support", skill.getName());
        assertEquals("CS", skill.getAbvName());
    }

    @Test
    void testDeserializationPartial() {
        // Prepare JSON with only required fields
        String json = """
                {
                    "number": 102,
                    "level": 3,
                    "active": false
                }
                """;

        AgentSkill skill = gson.fromJson(json, AgentSkill.class);

        // Verify fields
        assertEquals(102, skill.getNumber());
        assertEquals(3, skill.getLevel());
        assertFalse(skill.isActive());

        // Optional fields should be null
        assertNull(skill.getDomain());
        assertNull(skill.getName());
        assertNull(skill.getAbvName());
    }

    @Test
    void testDeserializationEmpty() {
        // Edge case: empty JSON
        String json = "{}";

        AgentSkill skill = gson.fromJson(json, AgentSkill.class);

        // Number and level default to 0, active defaults to false
        assertEquals(0, skill.getNumber());
        assertEquals(0, skill.getLevel());
        assertFalse(skill.isActive());

        // Optional fields null
        assertNull(skill.getDomain());
        assertNull(skill.getName());
        assertNull(skill.getAbvName());
    }
}