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

import static com.ale.o2g.test.ExtendAssert.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

/**
 * Unit tests for {@link AgentGroups}.
 */
class AgentGroupsTest extends AbstractJsonTest {


    @Test
    void testDeserialization() {
        // Prepare JSON string
        String json = """
                {
                    "preferred": "1001",
                    "groups": ["1001", "1002", "1003"]
                }
                """;

        // Deserialize JSON into AgentGroups
        AgentGroups agentGroups = gson.fromJson(json, AgentGroups.class);

        // Verify preferred group
        assertEquals("1001", agentGroups.getPreferred());

        // Verify groups collection
        assertContains(Arrays.asList("1001", "1002", "1003"), agentGroups.getGroups());

        // Verify returned collection is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> agentGroups.getGroups().add("1004"));
    }

    @Test
    void testEmptyGroups() {
        // JSON with no groups defined
        String json = """
                {
                }
                """;

        AgentGroups agentGroups = gson.fromJson(json, AgentGroups.class);

        // Preferred should be set
        assertNull(agentGroups.getPreferred());

        // Groups should be empty, but never null
        assertNotNull(agentGroups.getGroups());
        assertTrue(agentGroups.getGroups().isEmpty());
    }

}