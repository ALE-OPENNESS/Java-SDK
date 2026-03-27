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

import static com.ale.o2g.test.ExtendAssert.assertContainsStrict;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.cca.O2GAgentConfig;
import com.ale.o2g.test.AbstractJsonTest;


class OperatorConfigurationTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                	"type": "AGENT",
                	"proacd": "12000",
                	"processingGroups": {
                		"preferred": "31000"
                	},
                	"skills": {
                		"skills": [
                			{ "number":1, "level":2, "active": true },
                			{ "number":3, "level":1, "active": true },
                			{ "number":5, "level":3, "active": true }                			
                		]
                	},
                	"selfAssign": true,
                	"headset": true,
                	"help": true,
                	"multiline": true
                }
                """;

        // Deserialize JSON into OperatorConfiguration
        O2GAgentConfig o2gConfig = gson.fromJson(json, O2GAgentConfig.class);
        OperatorConfiguration config = o2gConfig.toOperatorConfiguration();
        
        // Verify all fields
        assertEquals(OperatorConfiguration.Type.AGENT, config.getType());
        assertEquals("12000", config.getProacd());
        assertEquals("31000", config.getGroups().getPreferred());
        assertNotNull(config.getSkills());
        assertContainsStrict(Arrays.asList(1, 3, 5), config.getSkills().getSkillNumbers());
        assertTrue(config.isSelfAssignable());
        assertTrue(config.canRequestHelp());
        assertTrue(config.isHeadsetEnabled());
        assertTrue(config.isMultiline());
    }

    @Test
    void testDeserializationMin() {
        // Prepare JSON with all fields
        String json = """
                {
                	"type": "SUPERVISOR"
                }
                """;

        O2GAgentConfig o2gConfig = gson.fromJson(json, O2GAgentConfig.class);
        OperatorConfiguration config = o2gConfig.toOperatorConfiguration();

        // Verify all fields
        assertEquals(OperatorConfiguration.Type.SUPERVISOR, config.getType());
        assertNull(config.getProacd());
        
        assertNull(config.getGroups());
        
        assertNotNull(config.getSkills());
        assertTrue(config.getSkills().isEmpty());
        
        assertFalse(config.isSelfAssignable());
        assertFalse(config.canRequestHelp());
        assertFalse(config.isHeadsetEnabled());
        assertFalse(config.isMultiline());
    }

}