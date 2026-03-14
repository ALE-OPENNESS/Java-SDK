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

package com.ale.o2g.internal.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.routing.DndState;
import com.ale.o2g.types.routing.Forward;
import com.ale.o2g.types.routing.Overflow;
import com.ale.o2g.types.routing.RoutingCapabilities;
import com.ale.o2g.types.routing.RoutingState;

/**
 * 
 */
class RoutingRestTest extends AbstractRestServiceTest<RoutingRest> {

    protected RoutingRestTest() {
        super(RoutingRest.class, "https://o2g/rest/api/routing");
    }

    @Test
    void testGetCapabilities() throws Exception {

        defineResponse(200, "{\"presentationRoute\": true}");
        
        RoutingCapabilities result = service.getCapabilities();

        assertCalledWith(GET, "/");
        assertNotNull(result);
        assertTrue(result.canManageRemoteExtension());
    }

    @Test
    void testGetCapabilitiesWithLogin() throws Exception {

        defineResponse(200, "{\"presentationRoute\": true}");

        RoutingCapabilities result = service.getCapabilities("oxe1000");

        assertCalledWith(GET, "?loginName=oxe1000");
        assertNotNull(result);
        assertTrue(result.canManageRemoteExtension());
    }

    @Test
    void testSetRemoteExtensionActivation() throws Exception {

        defineResponse(200, "");

        boolean result = service.setRemoteExtensionActivation(true);

        assertCalledWith(POST, "/", "{"
        		+ "\"presentationRoutes\":["
        			+ "{"
        				+ "\"destinations\":["
        					+ "{"
        						+ "\"type\":\"MOBILE\","
        						+ "\"selected\":true"
    						+ "}"
						+ "]"
					+ "}"
				+ "]"
			+ "}"
        );

        assertTrue(result);
    }

    @Test
    void testGetDndState() throws Exception {

        defineResponse(200, "{ \"active\": true }");

        DndState result = service.getDndState();

        assertCalledWith(GET, "/dnd");
        assertNotNull(result);
    }

    @Test
    void testActivateDnd() throws Exception {

        defineResponse(200, "");

        boolean result = service.activateDnd();

        assertCalledWith(POST, "/dnd");
        assertTrue(result);
    }

    @Test
    void testCancelDnd() throws Exception {

        defineResponse(200, "");

        boolean result = service.cancelDnd();

        assertCalledWith(DELETE, "/dnd");
        assertTrue(result);
    }

    @Test
    void testForwardOnNumber() throws Exception {

        defineResponse(200, "");

        boolean result = service.forwardOnNumber("1234", Forward.Condition.BUSY_NO_ANSWER);

        assertCalledWith(POST, "/forwardroute", "{"
                + "\"forwardRoute\":{"
                	+ "\"forwardType\":\"BUSY_NO_ANSWER\","
                	+ "\"destinations\":["
                		+ "{"
                			+ "\"type\":\"NUMBER\","
                			+ "\"number\":\"1234\""
                		+ "}"
                	+ "]"
                + "}"
            + "}"
        );

        assertTrue(result);
    }

    @Test
    void testCancelForward() throws Exception {

        defineResponse(200, "");

        boolean result = service.cancelForward();

        assertCalledWith(DELETE, "/forwardroute");
        assertTrue(result);
    }

    @Test
    void testOverflowOnVoiceMail() throws Exception {

        defineResponse(200, "");

        boolean result = service.overflowOnVoiceMail(Overflow.Condition.NO_ANSWER);

        assertCalledWith(POST, "/overflowroute", "{"
        		+ "\"overflowRoutes\":["
        			+ "{"
        				+ "\"overflowType\":\"NO_ANSWER\","
        				+ "\"destinations\":["
        					+ "{"
        						+ "\"type\":\"VOICEMAIL\""
        					+ "}"
        				+ "]"
        			+ "}"
        		+ "]"
        	+ "}"
        );

        assertTrue(result);
    }

    @Test
    void testCancelOverflow() throws Exception {

        defineResponse(200, "");

        boolean result = service.cancelOverflow();

        assertCalledWith(DELETE, "/overflowroute");
        assertTrue(result);
    }

    @Test
    void testGetRoutingState() throws Exception {

        defineResponse(200, "{ \"remoteExtensionActive\": true }");

        RoutingState result = service.getRoutingState();

        assertCalledWith(GET, "/state");
        assertNotNull(result);
    }

    @Test
    void testRequestSnapshot() throws Exception {

        defineResponse(200, "");

        boolean result = service.requestSnapshot();

        assertCalledWith(POST, "/state/snapshot");
        assertTrue(result);
    }
}