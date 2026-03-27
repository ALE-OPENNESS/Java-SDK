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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.routing.O2GRoutingState;
import com.ale.o2g.test.AbstractJsonTest;

class RoutingStateTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
                {
                	"presentationRoutes": [{
                		"destinations": [{
                			"type": "MOBILE",
                			"selected": true
                		}]
                	}],
                	"forwardRoutes": [{
                		"forwardType": "NO_ANSWER",
                		"destinations" : [{
                			"type": "NUMBER",
                			"number": "23000",
                			"selected": true
                		}]
                	}],
                	"overflowRoutes": [{
                		"overflowType": "BUSY_NO_ANSWER",
                		"destinations" : [{
                			"type": "VOICEMAIL",
                			"selected": true
                		}]
                	}],
                    "dndState": {
                    	"activate": true
                    }
                }
                """;

        O2GRoutingState o2gRoutingState = gson.fromJson(json, O2GRoutingState.class);
        RoutingState routingState = o2gRoutingState.toRoutingState();

        assertNotNull(routingState.getForward());
        assertEquals(Forward.Condition.NO_ANSWER, routingState.getForward().getCondition());
        assertEquals(Destination.NUMBER, routingState.getForward().getDestination());
        assertEquals("23000", routingState.getForward().getNumber());
        
        assertNotNull(routingState.getOverflow());
        assertEquals(Overflow.Condition.BUSY_NO_ANSWER, routingState.getOverflow().getCondition());
        assertEquals(Destination.VOICEMAIL, routingState.getOverflow().getDestination());

        assertNotNull(routingState.getDndState());
        assertTrue(routingState.getDndState().isActivated());
        
        assertTrue(routingState.isRemoteExtensionActivated());
    }

    @Test
    void testDeserializationMinimal() {
        String json = "{}";

        O2GRoutingState o2gRoutingState = gson.fromJson(json, O2GRoutingState.class);
        RoutingState routingState = o2gRoutingState.toRoutingState();

        assertFalse(routingState.isRemoteExtensionActivated());
        assertNotNull(routingState.getForward());
        assertEquals(Destination.NONE, routingState.getForward().getDestination());
        
        assertNotNull(routingState.getOverflow());
        assertEquals(Destination.NONE, routingState.getOverflow().getDestination());

        assertNotNull(routingState.getDndState());
        assertFalse(routingState.getDndState().isActivated());
    }
}