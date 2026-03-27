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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.events.cca.OnInternalSkillChanged;
import com.ale.o2g.test.AbstractJsonTest;

public class OnAgentSkillChangedEventTest extends AbstractJsonTest {

    @Test
    void testEventDeserialization() {
        String json = """
        {
          "name": "SkillChangedEvent",
          "loginName": "agent007",
          "skills": {
            "skills": [
              {
                "number": 1,
                "name": "Sales",
                "domain": 100,
                "level": 5,
                "active": true
              },
              {
                "number": 2,
                "name": "Support",
                "domain": 200,
                "level": 3,
                "active": false
              }
            ]
          }
        }
        """;

        // Deserialize the JSON
        OnAgentSkillChangedEvent event = (OnAgentSkillChangedEvent) OnInternalSkillChanged.adaptSkillChanged(gson.fromJson(json, OnInternalSkillChanged.class));

        assertEquals("agent007", event.getLoginName());

        assertNotNull(event.getSkills());
        assertEquals(2, event.getSkills().size());

        // Check skills by number
        assertNotNull(event.getSkills().get(1));
        assertEquals(1, event.getSkills().get(1).getNumber());
        assertEquals(5, event.getSkills().get(1).getLevel());
        assertTrue(event.getSkills().get(1).isActive());
        assertEquals(100, event.getSkills().get(1).getDomain());
        assertEquals("Sales", event.getSkills().get(1).getName());

        assertNotNull(event.getSkills().get(2));
        assertEquals(2, event.getSkills().get(2).getNumber());
        assertEquals(3, event.getSkills().get(2).getLevel());
        assertFalse(event.getSkills().get(2).isActive());
        assertEquals(200, event.getSkills().get(2).getDomain());
        assertEquals("Support", event.getSkills().get(2).getName());

        // Check skill lookup by domain and name
        assertEquals(1, event.getSkills().get(100, "Sales").getNumber());
        assertEquals(2, event.getSkills().get(200, "Support").getNumber());
    }

    @Test
    void testEmptySkills() {
        String json = "{}";

        OnAgentSkillChangedEvent event = (OnAgentSkillChangedEvent) OnInternalSkillChanged.adaptSkillChanged(gson.fromJson(json, OnInternalSkillChanged.class));

        assertNull(event.getLoginName());
        assertNotNull(event.getSkills());
        assertTrue(event.getSkills().isEmpty());
    }
}