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

package com.ale.o2g.types.telephony.call;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class CallDataTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
            {
        		"initialCalled": {
			    	"id": {
			    		"loginName": "oxe1000",
			    		"phoneNumber": "12000"
			    	},
			        "firstName": "John",
			        "lastName": "Doe",
			        "displayName": "John Doe",
			        "type": {
			        	"main": "USER",
			        	"subType" : "ALE300"
			        }
        		},
        		"lastRedirecting": {
			    	"id": {
			    		"loginName": "oxe1001",
			    		"phoneNumber": "12001"
			    	},
			        "firstName": "Alice",
			        "lastName": "Smith",
			        "displayName": "Alice Smith Doe",
			        "type": {
			        	"main": "USER",
			        	"subType" : "ALE400"
			        }
        		},
        		"deviceCall": true,
        		"callUUID": "CALL-1234",
                "state": "ACTIVE",
                "recordState" : "RECORDING",
                "tags": [
                    {"name":"tag1","value":"val1","visibilities":["all"]},
                    {"name":"tag2","value":"val2","visibilities":["private"]}
                ],
                "capabilities": {
        			"addDevice": true,
        			"transfer": true,
        			"terminate": true
                },
                "associateData": "Marketing",
                "accountInfo": "acct-01",
                "anonymous": false,
                "acdCallData": {
                    "supervisedTransfer": true,
                    "pilotNumber": "15000"
                },
                "trunkIdentification": {
        		    "networkTimeslot": 10
        		}	    
            }
        """;

        CallData callData = gson.fromJson(json, CallData.class);

        // Verify basic fields
        assertEquals(MediaState.ACTIVE, callData.getState());
        assertEquals("CALL-1234", callData.getCallUUID());
        assertTrue(callData.isDeviceCall());
        assertFalse(callData.isAnonymous());
        assertEquals("acct-01", callData.getAccountInfo());
        assertEquals(RecordState.RECORDING, callData.getRecordState());

        assertNotNull(callData.getCapabilities());
        assertTrue(callData.getCapabilities().canAddDevice());
        assertTrue(callData.getCapabilities().canTransfer());
        assertTrue(callData.getCapabilities().canTerminate());
        
        // Verify initial initialCalled
        assertNotNull(callData.getInitialCalled());
        assertEquals("12000", callData.getInitialCalled().getId().getPhoneNumber());

        // Verify lastRedirecting initialCalled
        assertNotNull(callData.getLastRedirecting());
        assertEquals("12001", callData.getLastRedirecting().getId().getPhoneNumber());

        // Verify CorrelatorData from associateData
        assertNotNull(callData.getCorrelatorData());
        assertEquals("Marketing", callData.getCorrelatorData().asString());

        // Verify tags
        assertNotNull(callData.getTags());
        assertEquals(2, callData.getTags().size());

        // Verify ACD call data
        assertNotNull(callData.getAcdCallData());
        assertTrue(callData.getAcdCallData().isSupervisedTransfer());
        assertEquals("15000", callData.getAcdCallData().getPilotNumber());
        
        assertNotNull(callData.getTrunkIdentification());
    }

    @Test
    void testDeserializationMin() {
        // Minimal JSON
        String json = "{}";

        CallData callData = gson.fromJson(json, CallData.class);

        // All fields should be null/default
        assertNull(callData.getInitialCalled());
        assertNull(callData.getLastRedirecting());
        assertNull(callData.getState());
        assertNull(callData.getRecordState());
        assertNull(callData.getCallUUID());
        assertFalse(callData.isDeviceCall());
        assertFalse(callData.isAnonymous());
        assertNull(callData.getAccountInfo());
        assertNotNull(callData.getTags());
        assertTrue(callData.getTags().isEmpty());
        assertNull(callData.getCapabilities());
        assertNull(callData.getAcdCallData());
        assertNull(callData.getCorrelatorData());
        assertNull(callData.getTrunkIdentification());
    }
}