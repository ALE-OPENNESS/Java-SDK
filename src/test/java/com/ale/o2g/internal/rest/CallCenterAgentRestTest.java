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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.cca.IntrusionMode;
import com.ale.o2g.types.cca.OperatorConfiguration;
import com.ale.o2g.types.cca.OperatorState;
import com.ale.o2g.types.cca.WithdrawReason;

/**
 * 
 */
class CallCenterAgentRestTest extends AbstractRestServiceTest<CallCenterAgentRest> {


	protected CallCenterAgentRestTest() {
		super(CallCenterAgentRest.class, "https://o2g/rest/api/ccag");
	}

	@Test
	void testGetOperatorConfiguration_returnsOperatorConfiguration() throws Exception {

		// Define the response
		defineResponse(200, "{"
				+ "\"type\":\"AGENT\","
				+ "\"proacd\":\"12000\""
				+ "}");

		// Call the method
		OperatorConfiguration result = service.getOperatorConfiguration("oxe1000");

		// Verify Called URI
		assertCalledWith(GET, "/config?loginName=oxe1000");
		
		// Assert the result
		assertNotNull(result);
		assertEquals(OperatorConfiguration.Type.AGENT, result.getType());
		assertEquals("12000", result.getProacd());
	}

    @Test
    void testGetOperatorState_returnsOperatorState() throws Exception {
        defineResponse(200, "{"
                + "\"mainState\":\"LOG_ON\","
                + "\"subState\":\"READY\","
                + "\"proAcdDeviceNumber\":\"12000\","
                + "\"pgNumber\":\"13000\","
                + "\"withdrawReason\":1,"
                + "\"withdrawn\":true"
                + "}");

        OperatorState result = service.getOperatorState("oxe1000");

        assertCalledWith(GET, "/state?loginName=oxe1000");
        
        assertNotNull(result);
        assertEquals(OperatorState.OperatorMainState.LOG_ON, result.getMainState());
        assertEquals(OperatorState.OperatorDynamicState.READY, result.getSubState());
        assertEquals("12000", result.getProAcdDeviceNumber());
        assertEquals("13000", result.getPgNumber());
        assertTrue(result.isWithdrawn());
        assertEquals(1, result.getWithdrawReason());
    }

    @Test
    void testLogonOperator_returnsTrue() throws Exception {
        defineResponse(200, "");

        boolean result = service.logonOperator("12000", "15000", true, "oxe1000");

        assertCalledWith(POST, "/logon?loginName=oxe1000", "{"
        		+ "\"proAcdDeviceNumber\":\"12000\","
        		+ "\"pgGroupNumber\":\"15000\","
        		+ "\"headset\":true"   		
        		+ "}");
        
        assertTrue(result);
    }

    
    @Test
    void testLogoffOperator_returnsTrue() throws Exception {
        defineResponse(200, "{\"success\":true}");

        boolean result = service.logoffOperator("oxe1000");
        assertCalledWith(POST, "/logoff?loginName=oxe1000");
        assertTrue(result);
    }

    @Test
    void testEnterAgentGroup() throws Exception {
        defineResponse(200, "");
        
        boolean enterResult = service.enterAgentGroup("15000", "oxe1000");
        
        assertCalledWith(POST, "/enterPG?loginName=oxe1000", "{"
        		+ "\"pgGroupNumber\":\"15000\""
        		+ "}");
        
        assertTrue(enterResult);
    }

    @Test
    void testExitAgentGroup() throws Exception {

    	// define response for getOperator
    	HttpResponse<String> resp1 = mockResponse(200, "{"
    		+ "\"mainState\": \"LOG_ON\","
    		+ "\"pgNumber\":\"15000\""
    		+ "}"
    	);
    	
    	// define response for exit
    	HttpResponse<String> resp2 = mockResponse(200, "");
        
        defineResponses(List.of(resp1, resp2));

        boolean exitResult = service.exitAgentGroup("oxe1000");
        
        assertCalledWith(1, POST, "/exitPG?loginName=oxe1000", "{"
        		+ "\"pgGroupNumber\":\"15000\""
        		+ "}"
        		);

        assertTrue(exitResult);
        
    }
    
    @Test
    void testSetReady() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setReady("oxe1000"));
        
        assertCalledWith(POST, "/ready?loginName=oxe1000");
    }

    @Test
    void testSetPause() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setPause("oxe1000"));
        assertCalledWith(POST, "/pause?loginName=oxe1000");
    }

    @Test
    void testWrapup() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setWrapup("oxe1000"));
        assertCalledWith(POST, "/wrapUp?loginName=oxe1000");
    }
    
    @Test
    void testSetWithdraw() throws Exception {
    	
        defineResponse(200, "");
        
        WithdrawReason reason = gson.fromJson("{\"index\": 1, \"label\": \"Lunch\"}", WithdrawReason.class);

        assertTrue(service.setWithdraw(reason, "oxe1000"));
        
        assertCalledWith(POST, "/withdraw?loginName=oxe1000", "{"
        		+ "\"reasonIndex\":1"
        		+ "}"
        		);
    }
        
    @Test
    void testRequestPermanentListening() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestPermanentListening("12000", "oxe1000"));
        
        assertCalledWith(POST, "/permanentListening?loginName=oxe1000", "{"
        		+ "\"agentNumber\":\"12000\""
        		+ "}"
        		);
    }

    @Test
    void testCancelPermanentListening() throws Exception {
        defineResponse(200, "");

        assertTrue(service.cancelPermanentListening("oxe1000"));
        
        assertCalledWith(DELETE, "/permanentListening?loginName=oxe1000");
    }

    @Test
    void testIntrusion() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestIntrusion("12000", IntrusionMode.NORMAL, "oxe1000"));
        assertCalledWith(POST, "/intrusion?loginName=oxe1000", "{"
        		+ "\"agentNumber\":\"12000\","
        		+ "\"mode\":\"NORMAL\""
        		+ "}"
        		);
    }

    @Test
    void testChangeIntrusionMode() throws Exception {
        defineResponse(200, "");

        assertTrue(service.changeIntrusionMode(IntrusionMode.RESTRICTED, "oxe1000"));
        assertCalledWith(PUT, "/intrusion?loginName=oxe1000", "{"
        		+ "\"mode\":\"RESTRICTED\""
        		+ "}"
        		);
    }

    @Test
    void testRequestSupervisorHelp() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestSupervisorHelp("oxe1000"));
        assertCalledWith(POST, "/supervisorHelp?loginName=oxe1000");
    }

    @Test
    void testRejectSupervisorHelp() throws Exception {
        defineResponse(200, "");

        assertTrue(service.rejectAgentHelpRequest("12001", "oxe1000"));
        assertCalledWith(DELETE, "/intrusion?loginName=oxe1000");
    }


    @Test
    void testGetWithdrawReasons_returnsList() throws Exception {
        defineResponse(200, "{ \"reasons\": ["
                + "{\"index\":1,\"label\":\"Lunch\"},"
                + "{\"index\":2,\"label\":\"Meeting\"}"
                + "]}");

        List<WithdrawReason> reasons = service.getWithdrawReasons("15000", "oxe1000");

        assertCalledWith(GET, "/withdrawReasons?pgNumber=15000&loginName=oxe1000");
        assertNotNull(reasons);
        assertEquals(2, reasons.size());
        assertEquals("Lunch", reasons.get(0).getLabel());
        assertEquals(2, reasons.get(1).getIndex());
    }

    @Test
    void testActivateSkills() throws Exception {
        defineResponse(200, "");

        assertTrue(service.activateSkills(Arrays.asList(101, 102), "oxe1000"));
        assertCalledWith(POST, "/config/skills/activate?loginName=oxe1000", "{"
        		+ "\"skills\":[101,102]"
        		+ "}");
    }

    @Test
    void testDeactivateSkills() throws Exception {
        defineResponse(200, "");

        assertTrue(service.deactivateSkills(Arrays.asList(101, 102), "oxe1000"));
        assertCalledWith(POST, "/config/skills/deactivate?loginName=oxe1000", "{"
        		+ "\"skills\":[101,102]"
        		+ "}");
    }
    
 // ── getOperatorConfiguration ──────────────────────────────────────────────────

    @Test
    void testGetOperatorConfiguration_NoLoginName() throws Exception {
        defineResponse(200, "{"
                + "\"type\":\"SUPERVISOR\","
                + "\"proacd\":\"13000\""
                + "}");

        OperatorConfiguration result = service.getOperatorConfiguration(null);

        assertCalledWith(GET, "/config");
        assertNotNull(result);
        assertEquals(OperatorConfiguration.Type.SUPERVISOR, result.getType());
    }

    // ── getOperatorState ──────────────────────────────────────────────────────────

    @Test
    void testGetOperatorState_NoLoginName() throws Exception {
        defineResponse(200, "{"
                + "\"mainState\":\"LOG_ON\","
                + "\"subState\":\"READY\""
                + "}");

        OperatorState result = service.getOperatorState();

        assertCalledWith(GET, "/state");
        assertNotNull(result);
        assertEquals(OperatorState.OperatorMainState.LOG_ON, result.getMainState());
    }

    // ── logonOperator ─────────────────────────────────────────────────────────────

    @Test
    void testLogonOperator_NoLoginName() throws Exception {
        defineResponse(200, "");

        boolean result = service.logonOperator("12000", "15000", true);

        assertCalledWith(POST, "/logon", "{"
                + "\"proAcdDeviceNumber\":\"12000\","
                + "\"pgGroupNumber\":\"15000\","
                + "\"headset\":true"
                + "}");
        assertTrue(result);
    }

    @Test
    void testLogonOperator_NoHeadset() throws Exception {
        defineResponse(200, "");

        boolean result = service.logonOperator("12000", "15000", false, "oxe1000");

        assertCalledWith(POST, "/logon?loginName=oxe1000", "{"
                + "\"proAcdDeviceNumber\":\"12000\","
                + "\"pgGroupNumber\":\"15000\","
                + "\"headset\":false"
                + "}");
        assertTrue(result);
    }

    // ── logoffOperator ────────────────────────────────────────────────────────────

    @Test
    void testLogoffOperator_NoLoginName() throws Exception {
        defineResponse(200, "");

        boolean result = service.logoffOperator();

        assertCalledWith(POST, "/logoff");
        assertTrue(result);
    }

    // ── enterAgentGroup ───────────────────────────────────────────────────────────

    @Test
    void testEnterAgentGroup_NoLoginName() throws Exception {
        defineResponse(200, "");

        boolean result = service.enterAgentGroup("15000");

        assertCalledWith(POST, "/enterPG", "{"
                + "\"pgGroupNumber\":\"15000\""
                + "}");
        assertTrue(result);
    }

    // ── exitAgentGroup ────────────────────────────────────────────────────────────

    @Test
    void testExitAgentGroup_NoLoginName() throws Exception {
        HttpResponse<String> resp1 = mockResponse(200, "{"
                + "\"mainState\": \"LOG_ON\","
                + "\"pgNumber\":\"15000\""
                + "}");

        HttpResponse<String> resp2 = mockResponse(200, "");

        defineResponses(List.of(resp1, resp2));

        boolean result = service.exitAgentGroup();

        assertCalledWith(1, POST, "/exitPG", "{"
                + "\"pgGroupNumber\":\"15000\""
                + "}");
        assertTrue(result);
    }

    @Test
    void testExitAgentGroup_NotInGroup_ReturnsFalse() throws Exception {
        // Agent has no pgNumber — not in a group
        defineResponse(200, "{"
                + "\"mainState\": \"LOG_ON\""
                + "}");

        boolean result = service.exitAgentGroup("oxe1000");

        assertFalse(result);
    }

    // ── setWrapup ─────────────────────────────────────────────────────────────────

    @Test
    void testSetWrapup_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setWrapup());

        assertCalledWith(POST, "/wrapUp");
    }

    // ── setReady ──────────────────────────────────────────────────────────────────

    @Test
    void testSetReady_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setReady());

        assertCalledWith(POST, "/ready");
    }

    // ── setPause ──────────────────────────────────────────────────────────────────

    @Test
    void testSetPause_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.setPause());

        assertCalledWith(POST, "/pause");
    }

    // ── setWithdraw ───────────────────────────────────────────────────────────────

    @Test
    void testSetWithdraw_NoLoginName() throws Exception {
        defineResponse(200, "");

        WithdrawReason reason = gson.fromJson("{\"index\": 2, \"label\": \"Meeting\"}", WithdrawReason.class);

        assertTrue(service.setWithdraw(reason));

        assertCalledWith(POST, "/withdraw", "{"
                + "\"reasonIndex\":2"
                + "}");
    }

    // ── requestPermanentListening ─────────────────────────────────────────────────

    @Test
    void testRequestPermanentListening_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestPermanentListening("12000"));

        assertCalledWith(POST, "/permanentListening", "{"
                + "\"agentNumber\":\"12000\""
                + "}");
    }

    // ── requestIntrusion ──────────────────────────────────────────────────────────

    @Test
    void testRequestIntrusion_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestIntrusion("12000", IntrusionMode.NORMAL));

        assertCalledWith(POST, "/intrusion", "{"
                + "\"agentNumber\":\"12000\","
                + "\"mode\":\"NORMAL\""
                + "}");
    }

    // ── changeIntrusionMode ───────────────────────────────────────────────────────

    @Test
    void testChangeIntrusionMode_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.changeIntrusionMode(IntrusionMode.RESTRICTED));

        assertCalledWith(PUT, "/intrusion", "{"
                + "\"mode\":\"RESTRICTED\""
                + "}");
    }

    // ── requestSupervisorHelp ─────────────────────────────────────────────────────

    @Test
    void testRequestSupervisorHelp_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestSupervisorHelp());

        assertCalledWith(POST, "/supervisorHelp");
    }

    // ── rejectAgentHelpRequest ────────────────────────────────────────────────────

    @Test
    void testRejectAgentHelpRequest_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.rejectAgentHelpRequest("12001"));

        assertCalledWith(DELETE, "/intrusion");
    }

    // ── cancelSupervisorHelpRequest ───────────────────────────────────────────────

    @Test
    void testCancelSupervisorHelpRequest_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.cancelSupervisorHelpRequest("supervisor1", "oxe1000"));

        assertCalledWith(DELETE, "/intrusion?loginName=oxe1000");
    }

    @Test
    void testCancelSupervisorHelpRequest_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.cancelSupervisorHelpRequest("supervisor1"));

        assertCalledWith(DELETE, "/intrusion");
    }

    // ── requestSnaphot ────────────────────────────────────────────────────────────

    @Test
    void testRequestSnapshot_WithLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestSnaphot("oxe1000"));

        assertCalledWith(POST, "/state/snapshot?loginName=oxe1000");
    }

    @Test
    void testRequestSnapshot_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.requestSnaphot());

        assertCalledWith(POST, "/state/snapshot");
    }

    // ── getWithdrawReasons ────────────────────────────────────────────────────────

    @Test
    void testGetWithdrawReasons_NoLoginName() throws Exception {
        defineResponse(200, "{ \"reasons\": ["
                + "{\"index\":1,\"label\":\"Lunch\"}"
                + "]}");

        List<WithdrawReason> reasons = service.getWithdrawReasons("15000");

        assertCalledWith(GET, "/withdrawReasons?pgNumber=15000");
        assertNotNull(reasons);
        assertEquals(1, reasons.size());
        assertEquals("Lunch", reasons.get(0).getLabel());
    }

    @Test
    void testGetWithdrawReasons_ReturnsNullOnError() throws Exception {
        defineResponse(404, "");

        List<WithdrawReason> reasons = service.getWithdrawReasons("15000");

        assertNull(reasons);
    }

    // ── activateSkills ────────────────────────────────────────────────────────────

    @Test
    void testActivateSkills_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.activateSkills(Arrays.asList(101, 102)));

        assertCalledWith(POST, "/config/skills/activate", "{"
                + "\"skills\":[101,102]"
                + "}");
    }

    // ── deactivateSkills ──────────────────────────────────────────────────────────

    @Test
    void testDeactivateSkills_NoLoginName() throws Exception {
        defineResponse(200, "");

        assertTrue(service.deactivateSkills(Arrays.asList(101, 102)));

        assertCalledWith(POST, "/config/skills/deactivate", "{"
                + "\"skills\":[101,102]"
                + "}");
    }
}
