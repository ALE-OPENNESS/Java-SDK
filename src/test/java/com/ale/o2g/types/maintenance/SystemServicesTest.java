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

/**
 * 
 */
public class SystemServicesTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                    "services": [
                    	{
                    		"name": "tomcat",
                    		"status": "Started",
                    		"mode": "Active"
                    	},
                    	{
                    		"name": "nginx",
                    		"status": "Started",
                    		"mode": "Active"
                    	}
                    ],
                    "globalIPAdress": "10.2.3.121",
                    "drbd": "Running"
                }
                """;

        SystemServices services = gson.fromJson(json, SystemServices.class);

        assertEquals("10.2.3.121", services.getGlobalIPAdress());
        assertEquals("Running", services.getDrbdStatus());
        assertEquals(2, services.getServices().size());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        SystemServices services = gson.fromJson(json, SystemServices.class);

        assertNull(services.getGlobalIPAdress());
        assertNull(services.getDrbdStatus());
        assertTrue(services.getServices().isEmpty());
    }
}