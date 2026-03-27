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
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.routing.ForwardRoute;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.routing.Forward.Condition;

/**
 * 
 */
public class ForwardTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
                {
            		"forwardType": "NO_ANSWER",
            		"destinations" : [{
            			"type": "NUMBER",
            			"number": "23000",
            			"selected": true
            		}]
                }
                """;

        ForwardRoute forwardRoute = gson.fromJson(json, ForwardRoute.class);
        Forward forward = forwardRoute.toForward();

        assertEquals(Forward.Condition.NO_ANSWER, forward.getCondition());
        assertEquals(Destination.NUMBER, forward.getDestination());
        assertEquals("23000", forward.getNumber());
    }

    @Test
    void testCreateForwardOnVM() {
    	
    	ForwardRoute forwardRoute = ForwardRoute.createForwardOnVoiceMail(Condition.IMMEDIATE);
        Forward forward = forwardRoute.toForward();

        assertEquals(Condition.IMMEDIATE, forward.getCondition());
        assertEquals(Destination.VOICEMAIL, forward.getDestination());
        assertNull(forward.getNumber());
    }
    
    @Test
    void testCreateForwardOnNumber() {
    	
    	ForwardRoute forwardRoute = ForwardRoute.createForwardOnNumber("21000", Condition.BUSY_NO_ANSWER);
        Forward forward = forwardRoute.toForward();

        assertEquals(Condition.BUSY_NO_ANSWER, forward.getCondition());
        assertEquals(Destination.NUMBER, forward.getDestination());
        assertEquals("21000", forward.getNumber());
    }
    
    
    @Test
    void testDeserializationMinimal() {
        String json = "{}";

        ForwardRoute forwardRoute = gson.fromJson(json, ForwardRoute.class);
        Forward forward = forwardRoute.toForward();

        assertNotNull(forward);
        assertEquals(Destination.NONE, forward.getDestination());
    }
}