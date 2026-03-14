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

package com.ale.o2g.types.telephony.call;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class TagTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON with all fields
        String json = """
                {
                    "name": "Priority",
                    "value": "High",
                    "visibilities": ["AGENT", "SUPERVISOR"]
                }
                """;

        Tag tag = gson.fromJson(json, Tag.class);

        assertEquals("Priority", tag.getName());
        assertEquals("High", tag.getValue());

        Collection<String> expectedVisibilities = Arrays.asList("AGENT", "SUPERVISOR");
        assertNotNull(tag.getVisibilities());
        assertEquals(expectedVisibilities.size(), tag.getVisibilities().size());
        assertTrue(tag.getVisibilities().containsAll(expectedVisibilities));
    }

    @Test
    void testDeserializationMinimal() {
        // Empty JSON
        String json = """
                {
                    "name": "Priority"
                }
                """;

        Tag tag = gson.fromJson(json, Tag.class);

        assertEquals("Priority", tag.getName());
        assertNull(tag.getValue());
        assertNotNull(tag.getVisibilities());
        assertTrue(tag.getVisibilities().isEmpty());
    }
}