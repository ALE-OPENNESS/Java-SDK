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

package com.ale.o2g.types.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.common.PartyInfo.Type.MainType;

/**
 * 
 */
class PartyInfoTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                	"id": {
                		"loginName": "oxe1000",
                		"phoneNumber": "12000",
                		"multiLine": true
                	},
                	"firstName": "john",
                	"lastName": "doe",
                	"displayName": "john-doe",
                	"type": {
                		"main": "USER",
                		"subType": "ALE300"
                	}
                }
                """;

        // Deserialize JSON into OperatorConfiguration
        PartyInfo pi = gson.fromJson(json, PartyInfo.class);
        
        assertEquals("oxe1000", pi.getId().getLoginName());
        assertEquals("12000", pi.getId().getPhoneNumber());
        assertTrue(pi.getId().isMultiLine());
        
        assertEquals("john", pi.getFirstName());
        assertEquals("doe", pi.getLastName());
        assertEquals("john-doe", pi.getDisplayName());
        
        assertEquals(MainType.USER, pi.getType().getMain());
        assertEquals("ALE300", pi.getType().getSubType());
    }

    @Test
    void testDeserializationMin() {
        String json = """
                {
                	"id": {
                		"loginName": "oxe1000"
                	}
                }
                """;

        // Deserialize JSON into OperatorConfiguration
        PartyInfo pi = gson.fromJson(json, PartyInfo.class);
        
        assertEquals("oxe1000", pi.getId().getLoginName());
        assertNull(pi.getId().getPhoneNumber());
        assertFalse(pi.getId().isMultiLine());
        
        assertNull(pi.getFirstName());
        assertNull(pi.getLastName());
        assertNull(pi.getDisplayName());
        
        assertNull(pi.getType());
    }

}
