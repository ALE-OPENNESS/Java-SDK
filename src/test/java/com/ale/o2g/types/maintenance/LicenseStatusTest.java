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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.maintenance.LicenseStatus.LicenseType;

/**
 * 
 */
public class LicenseStatusTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                    "type": "FLEXLM",
                    "context": "PROD",
                    "currentServer": "12.98.12.2",
                    "status": "NORMAL",
                    "statusMessage": "Normal",
                    "lics": [{
	                    "name": "Advanced Telephony",
	                    "total": 1000,
	                    "currentUsed": 200,
	                    "expiration": "12/30/2026"
                    }]
                }
                """;

        LicenseStatus ls = gson.fromJson(json, LicenseStatus.class);

        assertEquals(LicenseType.FLEXLM, ls.getType());
        assertEquals("PROD", ls.getContext());
        assertEquals("12.98.12.2", ls.getCurrentServer());
        assertEquals("Normal", ls.getStatusMessage());
        assertEquals("NORMAL", ls.getStatus());
        assertEquals(1, ls.getLics().size());
        assertEquals("Advanced Telephony", ls.getLics().iterator().next().getName());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        LicenseStatus ls = gson.fromJson(json, LicenseStatus.class);

        assertNull(ls.getType());
        assertNull(ls.getContext());
        assertNull(ls.getCurrentServer());
        assertNull(ls.getStatusMessage());
        assertNull(ls.getStatus());
        assertTrue(ls.getLics().isEmpty());
    }
}