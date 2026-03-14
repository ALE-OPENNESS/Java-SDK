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

package com.ale.o2g.types.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.routing.OverflowRoute;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.routing.Overflow.Condition;

/**
 * 
 */
public class OverflowTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
                {
            		"overflowType": "BUSY_NO_ANSWER",
            		"destinations" : [{
            			"type": "VOICEMAIL",
            			"selected": true
            		}]
                }
                """;

        OverflowRoute overflowRoute = gson.fromJson(json, OverflowRoute.class);
        Overflow overflow = overflowRoute.toOverflow();

        assertNotNull(overflow);
        assertEquals(Overflow.Condition.BUSY_NO_ANSWER, overflow.getCondition());
        assertEquals(Destination.VOICEMAIL, overflow.getDestination());
    }

    @Test
    void testCreateOverflowOnVM() {
    	
    	OverflowRoute overflowRoute = OverflowRoute.createOverflowOnVoiceMail(Condition.BUSY);
        Overflow overflow = overflowRoute.toOverflow();

        assertNotNull(overflow);
        assertEquals(Overflow.Condition.BUSY, overflow.getCondition());
        assertEquals(Destination.VOICEMAIL, overflow.getDestination());
    }
    
    @Test
    void testDeserializationMinimal() {
        String json = "{}";

        OverflowRoute overflowRoute = gson.fromJson(json, OverflowRoute.class);
        Overflow overflow = overflowRoute.toOverflow();

        assertNotNull(overflow);
        assertEquals(Destination.NONE, overflow.getDestination());
    }
}