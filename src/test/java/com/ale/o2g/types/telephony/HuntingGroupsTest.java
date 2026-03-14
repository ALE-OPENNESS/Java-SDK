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

package com.ale.o2g.types.telephony;

import static com.ale.o2g.test.ExtendAssert.assertContains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class HuntingGroupsTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
            {
                "hgList": ["10001", "10002", "10003"],
                "currentHg": "10002"
            }
        """;

        HuntingGroups hg = gson.fromJson(json, HuntingGroups.class);

        assertContains(Arrays.asList("10001", "10002", "10003"), hg.getHuntingGroups());
        assertEquals("10002", hg.getCurrentHuntingGroup());
    }

    @Test
    void testDeserializationMin() {
        String json = "{}";

        HuntingGroups hg = gson.fromJson(json, HuntingGroups.class);

        assertNotNull(hg.getHuntingGroups());
        assertTrue(hg.getHuntingGroups().isEmpty());
        assertNull(hg.getCurrentHuntingGroup());
    }
}