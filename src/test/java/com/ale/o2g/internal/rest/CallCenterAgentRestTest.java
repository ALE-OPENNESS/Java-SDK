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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    
}
