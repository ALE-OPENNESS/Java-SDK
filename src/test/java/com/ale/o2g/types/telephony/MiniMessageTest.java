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

import static com.ale.o2g.test.ExtendAssert.assertEqualsUtc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class MiniMessageTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
            {
                "sender": "12000",
                "date": "2026-03-07T15:30:00Z",
                "message": "Hello world!"
            }
        """;

        MiniMessage miniMessage = gson.fromJson(json, MiniMessage.class);

        assertEquals("12000", miniMessage.getSender());
        assertEqualsUtc("2026-03-07T15:30:00Z", miniMessage.getDate());
        assertEquals("Hello world!", miniMessage.getMessage());
    }

    @Test
    void testDeserializationMin() {
        String json = "{}";

        MiniMessage miniMessage = gson.fromJson(json, MiniMessage.class);

        assertNull(miniMessage.getSender());
        assertNull(miniMessage.getDate());
        assertNull(miniMessage.getMessage());
    }
}