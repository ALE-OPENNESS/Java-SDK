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

package com.ale.o2g.events.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.events.routing.OnInternalRoutingStateChangedEvent;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.routing.Destination;
import com.ale.o2g.types.routing.Forward;
import com.ale.o2g.types.routing.Overflow;

/**
 * 
 */
public class OnRoutingStateChangedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "routingState": {
                "presentationRoutes": [
                    { "destinations": [ { "type": "MOBILE", "selected": true } ] }
                ],
                "forwardRoutes": [
                    { "forwardType": "BUSY", "destinations": [ { "type": "NUMBER", "number": "5551234" } ] }
                ],
                "overflowRoutes": [
                    { "overflowType": "NO_ANSWER", "destinations": [ { "type": "VOICEMAIL" } ] }
                ],
                "dndState": { "activate": true }
            }
        }
        """;

        OnRoutingStateChangedEvent event = (OnRoutingStateChangedEvent)
            OnInternalRoutingStateChangedEvent.adapt(gson.fromJson(json, OnInternalRoutingStateChangedEvent.class));

        // Basic field
        assertEquals("oxe1000", event.getLoginName());

        // Remote extension
        assertTrue(event.getRoutingState().isRemoteExtensionActivated());

        // Forward
        assertEquals(Destination.NUMBER, event.getRoutingState().getForward().getDestination());
        assertEquals(Forward.Condition.BUSY, event.getRoutingState().getForward().getCondition());
        assertEquals("5551234", event.getRoutingState().getForward().getNumber());

        // Overflow
        assertEquals(Destination.VOICEMAIL, event.getRoutingState().getOverflow().getDestination());
        assertEquals(Overflow.Condition.NO_ANSWER, event.getRoutingState().getOverflow().getCondition());

        // DND
        assertTrue(event.getRoutingState().getDndState().isActivated());
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnRoutingStateChangedEvent event = (OnRoutingStateChangedEvent)
            OnInternalRoutingStateChangedEvent.adapt(gson.fromJson(json, OnInternalRoutingStateChangedEvent.class));

        assertNull(event.getLoginName());
        assertNull(event.getRoutingState());
    }
}
