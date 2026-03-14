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

package com.ale.o2g.types.maintenance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

/**
 * 
 */
public class LicenseTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                    "name": "Advanced Telephony",
                    "total": 1000,
                    "currentUsed": 200,
                    "expiration": "12/30/2026"
                }
                """;

        License lic = gson.fromJson(json, License.class);

        assertEquals("Advanced Telephony", lic.getName());
        assertEquals(1000, lic.getTotal());
        assertEquals(200, lic.getCurrentUsed());
        assertEquals("12/30/2026", lic.getExpiration());
    }


    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        License lic = gson.fromJson(json, License.class);

        assertNull(lic.getName());
        assertEquals(0, lic.getTotal());
        assertEquals(0, lic.getCurrentUsed());
        assertNull(lic.getExpiration());
    }
}