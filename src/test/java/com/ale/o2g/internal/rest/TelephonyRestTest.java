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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.telephony.Call;
import com.ale.o2g.types.telephony.Callback;
import com.ale.o2g.types.telephony.HuntingGroupStatus;
import com.ale.o2g.types.telephony.HuntingGroups;
import com.ale.o2g.types.telephony.MiniMessage;
import com.ale.o2g.types.telephony.RecordingAction;
import com.ale.o2g.types.telephony.call.CorrelatorData;
import com.ale.o2g.types.telephony.call.Leg;
import com.ale.o2g.types.telephony.call.Participant;
import com.ale.o2g.types.telephony.call.acd.PilotInfo;
import com.ale.o2g.types.telephony.call.acd.PilotTransferQueryParameters;
import com.ale.o2g.types.telephony.device.DeviceState;


class TelephonyRestFullTest extends AbstractRestServiceTest<TelephonyRest> {

    protected TelephonyRestFullTest() {
        super(TelephonyRest.class, "https://o2g/rest/api");
    }

    // --- Basic calls ---
    @Test void testBasicMakeCall() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.basicMakeCall("12000", "12005"));
        
        assertCalledWith(POST, "/basicCall",
                "{\"deviceId\":\"12000\",\"callee\":\"12005\",\"autoAnswer\":true}");
    }

    @Test void testBasicAnswerCall() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.basicAnswerCall("12000"));
        
        assertCalledWith(POST, "/basicCall/answer", "{\"deviceId\":\"12000\"}");
    }

    @Test void testBasicDropMe() throws Exception {
    	
        defineResponse(200, "");
        
        assertTrue(service.basicDropMe());
        
        assertCalledWith(POST, "/basicCall/dropme");
    }

    @Test void testBasicDropMeWithLoginName() throws Exception {
    	
        defineResponse(200, "");
        
        assertTrue(service.basicDropMe("oxe1000"));
        
        assertCalledWith(POST, "/basicCall/dropme?loginName=oxe1000");
    }

    // --- Call retrieval ---
    @Test void testGetCalls() throws Exception {
        defineResponse(200, "{ \"calls\": [{\"callRef\":\"ccccc1\"},{\"callRef\":\"aaaa2\"}] }");
        
        Collection<Call> calls = service.getCalls();
        
        assertEquals(2, calls.size());
        assertCalledWith(GET, "/calls");
    }

    @Test void testGetCallByRef() throws Exception {
    	
        defineResponse(200, "{ \"callRef\":\"ccccc1\" }");
        
        Call call = service.getCall("ccccc1");
        
        assertCalledWith(GET, "/calls/ccccc1");
        
        assertEquals("ccccc1", call.getCallRef());        
    }

    // --- Make call variants ---
    @Test void testMakeCallFull() throws Exception {
        defineResponse(200, "");
       
        assertTrue(service.makeCall("12000", "12300", true, true, new CorrelatorData("Marketing"), "1234"));
        assertCalledWith(POST, "/calls",
                "{"
                + "\"deviceId\":\"12000\","
                + "\"callee\":\"12300\","
                + "\"autoAnswer\":true," 
                + "\"inhibitProgressTone\":true,"
                + "\"hexaBinaryAssociatedData\":\"Marketing\","
                + "\"callingNumber\":\"1234\""
                + "}"
                );
    }

    @Test void testMakePrivateCall() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.makePrivateCall("12000", "12300", true, "pin", "secret"));
        
        assertCalledWith(POST, "/calls",
                "{"
                + "\"deviceId\":\"12000\","
                + "\"callee\":\"12300\","
                + "\"autoAnswer\":true," 
                + "\"inhibitProgressTone\":false,"
                + "\"pin\":\"pin\"," 
                + "\"secretCode\":\"secret\""
                + "}"
        		);
    }

    @Test void testMakeBusinessCall() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.makeBusinessCall("12000", "callee1", true, "bizCode"));
        
        assertCalledWith(POST, "/calls",
                "{"
                + "\"deviceId\":\"12000\","
                + "\"callee\":\"callee1\","
                + "\"autoAnswer\":true," 
                + "\"inhibitProgressTone\":false,"
                + "\"businessCode\":\"bizCode\""
                + "}"
                );
    }

    // --- Call actions ---
    @Test void testAlternate() throws Exception {
    	
        defineResponse(200, "");
        
        assertTrue(service.alternate("abcdef12345", "12000"));
        
        assertCalledWith(POST,"/calls/abcdef12345/alternate","{\"deviceId\":\"12000\"}");
    }

    @Test void testAnswer() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.answer("abcdef12345","12000"));
        
        assertCalledWith(POST,"/calls/abcdef12345/answer","{\"deviceId\":\"12000\"}");
    }

    @Test void testAttachData() throws Exception {
    	
        defineResponse(200, "");
        
        CorrelatorData data = new CorrelatorData("abc".getBytes());
        
        assertTrue(service.attachData("abcdef1234", "12000", data));
        
        assertCalledWith(POST,"/calls/abcdef1234/attachdata",
                "{"
                + "\"deviceId\":\"12000\","
                + "\"hexaBinaryAssociatedData\":\"616263\""
                + "}"
                );
    }

    @Test void testBlindTransfer() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.blindTransfer("abcdef12345", "12010"));
        
        assertCalledWith(POST, "/calls/abcdef12345/blindtransfer", "{"
        		+ "\"transferTo\":\"12010\","
        		+ "\"anonymous\":false"
        		+ "}"
        		);
    }

    @Test void testCallbackRequest() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.callback("abcdef12345"));
        
        assertCalledWith(POST, "/calls/abcdef12345/callback");
    }

    @Test void testGetLegs() throws Exception {
    	
        defineResponse(200, "{"
        		+ "\"legs\":["
        			+ "{"
        				+ "\"legId\":\"l1\""
        			+ "},"
        			+ "{"
        				+ "\"legId\":\"l2\""
        			+ "}"
        		+ "]"
        	+ "}"
        );
        
        Collection<Leg> legs = service.getLegs("abcdef12345");
        assertCalledWith(GET,"/calls/abcdef12345/deviceLegs");
        
        assertEquals(2, legs.size());
    }

    @Test void testGetParticipants() throws Exception {
        defineResponse(200, "{ \"participants\":[{\"participantId\":\"p1\"},{\"participantId\":\"p2\"}]}");
        
        Collection<Participant> participants = service.getParticipants("abcdef12345");
        
        assertCalledWith(GET, "/calls/abcdef12345/participants");
        assertEquals(2, participants.size());
    }

    @Test void testDropParticipant() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.dropParticipant("abcdef12345", "p1"));
        assertCalledWith(DELETE,"/calls/abcdef12345/participants/p1");
    }

    @Test void testReconnect() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.reconnect("abcdef12345", "12000", "call2"));
        
        assertCalledWith(POST,"/calls/abcdef12345/reconnect", "{"
        		+ "\"deviceId\":\"12000\","
        		+ "\"enquiryCallRef\":\"call2\""
        		+ "}"
        		);
    }

    @Test void testDoRecordAction() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.doRecordAction("abcdef12345",RecordingAction.START));
        
        assertCalledWith(POST,"/calls/abcdef12345/recording?action=start");
    }

    @Test void testRedirect() throws Exception {
    	
        defineResponse(200, "");
        
        assertTrue(service.redirect("abcdef12345", "12010"));
        
        assertCalledWith(POST, "/calls/abcdef12345/redirect", "{"
        		+ "\"redirectTo\":\"12010\","
        		+ "\"anonymous\":false"
        		+ "}"
        		);
    }

    @Test void testRetrieve() throws Exception {
    	
        defineResponse(200, "");
        
        assertTrue(service.retrieve("abcdef12345", "12000"));
        
        assertCalledWith(POST,"/calls/abcdef12345/retrieve", "{\"deviceId\":\"12000\"}");
    }

    @Test void testSendDtmf() throws Exception {
    	
        defineResponse(200, "");
        
        assertTrue(service.sendDtmf("abcdef12345", "12000", "123"));
        
        assertCalledWith(POST,"/calls/abcdef12345/sendDtmf","{"
        		+ "\"deviceId\":\"12000\","
        		+ "\"number\":\"123\""
        		+ "}"
        		);
    }

    @Test void testSendAccountInfo() throws Exception {
        
    	defineResponse(200, "");
        
        assertTrue(service.sendAccountInfo("abcdef12345", "12000", "acc123"));
        
        assertCalledWith(POST,"/calls/abcdef12345/sendaccountinfo", "{"
        		+ "\"deviceId\":\"12000\","
        		+ "\"accountInfo\":\"acc123\""
        		+ "}"
        		);
    }

    @Test void testTransfer() throws Exception {
    	
        defineResponse(200, "");
        
        assertTrue(service.transfer("abcdef12345", "call2"));
        
        assertCalledWith(POST,"/calls/abcdef12345/transfer", "{\"heldCallRef\":\"call2\"}");
    }

    @Test void testDeskSharingLogOn() throws Exception {
        defineResponse(200, "");

        assertTrue(service.deskSharingLogOn("10000"));
        
        assertCalledWith(POST,"/deskSharing", "{\"dssDeviceNumber\":\"10000\"}");
    }

    @Test void testDeskSharingLogOff() throws Exception {
        defineResponse(200, "");

        assertTrue(service.deskSharingLogOff());
        assertCalledWith(DELETE, "/deskSharing");
    }

    @Test void testDevicesState() throws Exception {
        defineResponse(200, "{ "
        		+ "\"deviceStates\":["
        			+ "{"
        				+ "\"deviceId\":\"d1\""
        			+ "}"
        		+ "]"
        	+ "}"
        );
        
        Collection<DeviceState> devices = service.getDevicesState();
        assertCalledWith(GET,"/devices");

        assertEquals(1, devices.size());        
    }

    @Test void testPickUp() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.pickUp("12000", "abcdef12345", "12003"));
        
        assertCalledWith(POST,"/devices/12000/pickup", "{"
        		+ "\"otherCallRef\":\"abcdef12345\","
        		+ "\"otherPhoneNumber\":\"12003\","
        		+ "\"autoAnswer\":false}"
        		);
    }

    @Test void testIntrusion() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.intrusion("10000"));
        assertCalledWith(POST, "/devices/10000/intrusion");        
    }

    @Test void testToggle() throws Exception {
        defineResponse(200, "");

        assertTrue(service.toggleInterphony("10000"));
        assertCalledWith(POST, "/devices/10000/ithmicro");
    }

    @Test void testUnPark() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.unPark("call2", "12000"));
        
        assertCalledWith(POST, "/devices/12000/unpark", "{\"heldCallRef\":\"call2\"}");
    }

    @Test void testHuntingGroupLogOn() throws Exception {    	
    	defineResponse(200, "");    	

    	assertTrue(service.huntingGroupLogOn("oxe1000"));
        
        assertCalledWith(POST, "/huntingGroupLogOn?loginName=oxe1000");
    }
    
    @Test void testHuntingGroupLogOff() throws Exception {
    	defineResponse(200, "");    	

    	assertTrue(service.huntingGroupLogOff("oxe1000"));
        
        assertCalledWith(DELETE, "/huntingGroupLogOn?loginName=oxe1000");
    }
    
    @Test void testAddMeToHuntingGroup() throws Exception {    	
    	defineResponse(200, "");    	

    	assertTrue(service.addMeToHuntingGroup("6500", "oxe1000"));
        
        assertCalledWith(POST, "/huntingGroupMember/6500?loginName=oxe1000");
    }       
    
    @Test void testRemoveMeFromHuntingGroup() throws Exception {    	
    	defineResponse(200, "");    	

    	assertTrue(service.removeMeFromHuntingGroup("6500", "oxe1000"));
        
        assertCalledWith(DELETE, "/huntingGroupMember/6500?loginName=oxe1000");
    }       
    
    @Test void testHuntingGroupStatus() throws Exception {
    	
        defineResponse(200, "{\"logon\": true }");
        
        HuntingGroupStatus result = service.getHuntingGroupStatus();        
        assertCalledWith(GET, "/huntingGroupLogOn");
        
        assertTrue(result.isLoggedOn());
    }

    @Test void testQueryHuntingGroups() throws Exception {
        defineResponse(200, "{"
        		+ "\"hgList\": [ \"52000\", \"52001\" ],"
        		+ "\"currentHg\": \"52001\""
        		+ "}"
        		);
        
        HuntingGroups hgs = service.queryHuntingGroups();
        
        assertCalledWith(GET, "/huntingGroups");
        assertNotNull(hgs);
        assertEquals(2, hgs.getHuntingGroups().size());
        assertEquals("52001", hgs.getCurrentHuntingGroup());
    }

    @Test void testCallbacks() throws Exception {
        defineResponse(200,"{ \"callbacks\":["
        		+ "{"
	        		+ "\"callbackId\" : \"cb1000\","
	        		+ "\"partyInfo\": {"
	        			+ "\"id\": {"
	        				+ "\"phoneNumber\": \"12000\""
	        			+ "}"
	        		+ "}"
        		+ "}"
        		+ "]"
        		+ "}");
        
        Collection<Callback> callbacks = service.getCallbacks();
        assertCalledWith(GET, "/incomingCallbacks");
        
        assertNotNull(callbacks);
        assertEquals(1, callbacks.size());
    }

    @Test void testGetMiniMessages() throws Exception {
        defineResponse(200, "{"
        		+ "\"sender\":\"9800\","
        		+ "\"date\":\"2026-03-01T14:32:15Z\","
        		+ "\"message\":\"The message to this user\""
        		+ "}");
        
        MiniMessage message = service.getMiniMessage();
        
        assertCalledWith(GET, "/miniMessages");
        assertNotNull(message);
        assertEquals("9800", message.getSender());
        assertUTCDateEquals("2026-03-01T14:32:15Z", message.getDate());
    }

    @Test void testSendMiniMessages() throws Exception {
    	
        defineResponse(200, "{ \"messages\":[] }");
        
        assertTrue(service.sendMiniMessage("9800", "hello"));
        
        assertCalledWith(POST, "/miniMessages", "{\"recipient\":\"9800\",\"message\":\"hello\"}");
    }

    @Test void testRequestSnapshot() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.requestSnapshot());
        
        assertCalledWith(POST,"/state/snapshot");
    }

    @Test void testDeleteCallback() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.deleteCallback("cb1"));        
        assertCalledWith(DELETE,"/incomingCallbacks/cb1");
    }

    @Test void testRelease() throws Exception {
        defineResponse(200, "");
        
        assertTrue(service.release("abcdef12345"));
        
        assertCalledWith(DELETE, "/calls/abcdef12345");
    }

    @Test void testGetPilotInfoSimple() throws Exception {
        defineResponse(200," { \"number\": \"23000\" }");

        PilotInfo info = service.getPilotInfo(1, "23000");

        assertEquals("23000", info.getPilotNumber());

        assertCalledWith(POST, "/pilots/1/23000/transferInfo");
    }

    @Test void testGetPilotInfoWithTransferParam() throws Exception {
        defineResponse(200," { \"number\": \"23000\" }");
        
        PilotTransferQueryParameters param = new PilotTransferQueryParameters();
        param.setAgentNumber("1200");
        
        PilotInfo info = service.getPilotInfo(1, "23000", param);
        
        assertEquals("23000", info.getPilotNumber());
        
        assertCalledWith(POST, "/pilots/1/23000/transferInfo",
                "{"
                + "\"agentNumber\":\"1200\""
                + "}"
                );
    }
}

