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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.routing.Destination;
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

 // ── setRemoteExtensionActivation ──────────────────────────────────────────────

    @Test
    void testSetRemoteExtensionActivation_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setRemoteExtensionActivation(true, "oxe1000"));

        assertRequest()
            .method(POST)
            .uri("?loginName=oxe1000")
            .jsonBody(json -> {
                json.assertValue("$.presentationRoutes[0].destinations[0].type", "MOBILE");
                json.assertValue("$.presentationRoutes[0].destinations[0].selected", true);
            });
    }

    @Test
    void testSetRemoteExtensionActivation_Deactivate() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setRemoteExtensionActivation(false));

        assertRequest()
            .method(POST)
            .uri("/")
            .jsonBody(json -> {
                json.assertValue("$.presentationRoutes[0].destinations[0].type", "MOBILE");
                json.assertValue("$.presentationRoutes[0].destinations[0].selected", false);
            });
    }

    // ── getDndState ───────────────────────────────────────────────────────────────

    @Test
    void testGetDndState_WithLoginName() throws Exception {
        defineResponse(200, "{ \"active\": false }");

        DndState result = service.getDndState("oxe1000");

        assertRequest().method(GET).uri("/dnd?loginName=oxe1000");
        assertNotNull(result);
    }

    // ── activateDnd ───────────────────────────────────────────────────────────────

    @Test
    void testActivateDnd_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.activateDnd("oxe1000"));

        assertRequest().method(POST).uri("/dnd?loginName=oxe1000");
    }

    // ── cancelDnd ─────────────────────────────────────────────────────────────────

    @Test
    void testCancelDnd_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.cancelDnd("oxe1000"));

        assertRequest().method(DELETE).uri("/dnd?loginName=oxe1000");
    }

    // ── getForward ────────────────────────────────────────────────────────────────

    @Test
    void testGetForward() throws Exception {
        defineResponse(200, "{"
                + "\"forwardType\":\"BUSY\","
                + "\"destinations\":[{\"type\":\"VOICEMAIL\"}]"
                + "}");

        Forward result = service.getForward();

        assertRequest().method(GET).uri("/forwardroute");
        assertNotNull(result);
    }

    @Test
    void testGetForward_WithLoginName() throws Exception {
        defineResponse(200, "{"
                + "\"forwardType\":\"BUSY\","
                + "\"destinations\":[{\"type\":\"VOICEMAIL\"}]"
                + "}");

        Forward result = service.getForward("oxe1000");

        assertRequest().method(GET).uri("/forwardroute?loginName=oxe1000");
        assertNotNull(result);
    }

    @Test
    void testGetForward_ReturnsNoneWhenNull() throws Exception {
        defineResponse(404, "");

        Forward result = service.getForward();

        assertRequest().method(GET).uri("/forwardroute");
        assertNotNull(result);
        assertEquals(Destination.NONE, result.getDestination());
    }

    // ── cancelForward ─────────────────────────────────────────────────────────────

    @Test
    void testCancelForward_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.cancelForward("oxe1000"));

        assertRequest().method(DELETE).uri("/forwardroute?loginName=oxe1000");
    }

    // ── forwardOnVoiceMail ────────────────────────────────────────────────────────

    @Test
    void testForwardOnVoiceMail() throws Exception {
        defineResponse(200, "");

        assertTrue(service.forwardOnVoiceMail(Forward.Condition.BUSY));

        assertRequest()
            .method(POST)
            .uri("/forwardroute")
            .jsonBody(json -> {
                json.assertValue("$.forwardRoute.forwardType", "BUSY");
                json.assertValue("$.forwardRoute.destinations[0].type", "VOICEMAIL");
            });
    }

    @Test
    void testForwardOnVoiceMail_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.forwardOnVoiceMail(Forward.Condition.IMMEDIATE, "oxe1000"));

        assertRequest()
            .method(POST)
            .uri("/forwardroute?loginName=oxe1000")
            .jsonBody(json -> {
                json.assertValue("$.forwardRoute.destinations[0].type", "VOICEMAIL");
            });
    }

    // ── forwardOnNumber ───────────────────────────────────────────────────────────

    @Test
    void testForwardOnNumber_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.forwardOnNumber("1234", Forward.Condition.BUSY_NO_ANSWER, "oxe1000"));

        assertRequest()
            .method(POST)
            .uri("/forwardroute?loginName=oxe1000")
            .jsonBody(json -> {
                json.assertValue("$.forwardRoute.forwardType", "BUSY_NO_ANSWER");
                json.assertValue("$.forwardRoute.destinations[0].type", "NUMBER");
                json.assertValue("$.forwardRoute.destinations[0].number", "1234");
            });
    }

    @Test
    void testForwardOnNumber_Immediate() throws Exception {
        defineResponse(200, "");

        assertTrue(service.forwardOnNumber("5678", Forward.Condition.IMMEDIATE));

        assertRequest()
            .method(POST)
            .uri("/forwardroute")
            .jsonBody(json -> {
                json.assertValue("$.forwardRoute.destinations[0].type", "NUMBER");
                json.assertValue("$.forwardRoute.destinations[0].number", "5678");
            });
    }

    // ── cancelOverflow ────────────────────────────────────────────────────────────

    @Test
    void testCancelOverflow_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.cancelOverflow("oxe1000"));

        assertRequest().method(DELETE).uri("/overflowroute?loginName=oxe1000");
    }

    // ── getOverflow ───────────────────────────────────────────────────────────────

    @Test
    void testGetOverflow() throws Exception {
        defineResponse(200, "{"
                + "\"overflowType\":\"BUSY\","
                + "\"destinations\":[{\"type\":\"VOICEMAIL\"}]"
                + "}");

        Overflow result = service.getOverflow();

        assertRequest().method(GET).uri("/overflowroute");
        assertNotNull(result);
    }

    @Test
    void testGetOverflow_WithLoginName() throws Exception {
        defineResponse(200, "{"
                + "\"overflowType\":\"BUSY\","
                + "\"destinations\":[{\"type\":\"VOICEMAIL\"}]"
                + "}");

        Overflow result = service.getOverflow("oxe1000");

        assertRequest().method(GET).uri("/overflowroute?loginName=oxe1000");
        assertNotNull(result);
    }

    @Test
    void testGetOverflow_ReturnsNoneWhenNull() throws Exception {
        defineResponse(404, "");

        Overflow result = service.getOverflow();

        assertRequest().method(GET).uri("/overflowroute");
        assertNotNull(result);
        assertEquals(Destination.NONE, result.getDestination());
    }

    // ── overflowOnVoiceMail ───────────────────────────────────────────────────────

    @Test
    void testOverflowOnVoiceMail_Busy() throws Exception {
        defineResponse(200, "");

        assertTrue(service.overflowOnVoiceMail(Overflow.Condition.BUSY));

        assertRequest()
            .method(POST)
            .uri("/overflowroute")
            .jsonBody(json -> {
                json.assertValue("$.overflowRoutes[0].overflowType", "BUSY");
                json.assertValue("$.overflowRoutes[0].destinations[0].type", "VOICEMAIL");
            });
    }

    @Test
    void testOverflowOnVoiceMail_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.overflowOnVoiceMail(Overflow.Condition.BUSY, "oxe1000"));

        assertRequest()
            .method(POST)
            .uri("/overflowroute?loginName=oxe1000")
            .jsonBody(json -> {
                json.assertValue("$.overflowRoutes[0].overflowType", "BUSY");
                json.assertValue("$.overflowRoutes[0].destinations[0].type", "VOICEMAIL");
            });
    }

    // ── getRoutingState ───────────────────────────────────────────────────────────

    @Test
    void testGetRoutingState_WithLoginName() throws Exception {
        defineResponse(200, "{ \"remoteExtensionActive\": true }");

        RoutingState result = service.getRoutingState("oxe1000");

        assertRequest().method(GET).uri("/state?loginName=oxe1000");
        assertNotNull(result);
    }

    @Test
    void testGetRoutingState_ReturnsDefaultWhenNull() throws Exception {
        defineResponse(404, "");

        RoutingState result = service.getRoutingState();

        assertRequest().method(GET).uri("/state");
        assertNotNull(result);
        assertFalse(result.isRemoteExtensionActivated());
        assertEquals(Destination.NONE, result.getForward().getDestination());
        assertEquals(Destination.NONE, result.getOverflow().getDestination());
        assertFalse(result.getDndState().isActivated());
    }

    // ── requestSnapshot ───────────────────────────────────────────────────────────

    @Test
    void testRequestSnapshot_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestSnapshot("oxe1000"));

        assertRequest().method(POST).uri("/state/snapshot?loginName=oxe1000");
    }
}