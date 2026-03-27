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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CorrelatorDataTest {

    @Test
    void testStringConstructor() {
        String text = "test-correlator";
        CorrelatorData cd = new CorrelatorData(text);

        // Verify string conversion
        assertEquals(text, cd.asString());

        // Verify byte array conversion
        assertArrayEquals(text.getBytes(), cd.asByteArray());
    }

    @Test
    void testByteArrayConstructor() {
        byte[] bytes = new byte[] { 1, 2, 3, 4 };
        CorrelatorData cd = new CorrelatorData(bytes);

        // Verify byte array
        assertArrayEquals(bytes, cd.asByteArray());

        // Verify string conversion matches new String(bytes)
        assertEquals(new String(bytes), cd.asString());
    }

    @Test
    void testEmptyString() {
        CorrelatorData cd = new CorrelatorData("");
        assertEquals("", cd.asString());
        assertArrayEquals(new byte[0], cd.asByteArray());
    }

    @Test
    void testEmptyByteArray() {
        CorrelatorData cd = new CorrelatorData(new byte[0]);
        assertEquals("", cd.asString());
        assertArrayEquals(new byte[0], cd.asByteArray());
    }
}