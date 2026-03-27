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

package com.ale.o2g.types.telephony;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.telephony.O2GTelephonicState;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.telephony.device.DeviceState;
import com.ale.o2g.types.telephony.device.OperationalState;
import com.ale.o2g.types.telephony.user.UserState;

class TelephonicStateTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        String json = """
            {
                "calls": [
                    {
                        "callRef": "aaaaa11111",
                        "callData": {
        		            "deviceCall": true
                        }
                    }    
                ],
                "deviceCapabilities": [
                    {
                        "deviceId": "1001",
                        "makeCall": true,
                        "makePrivateCall": true
                    }
                ],
                "userState": "BUSY",
                "deviceStates": {
        			"deviceStates": [{
    					"deviceId": "2000",
    					"state": "IN_SERVICE"
    				}, {
    					"deviceId": "2001",
    					"state": "OUT_OF_SERVICE"
    				}]
                }
            }
        """;

        O2GTelephonicState o2gState = gson.fromJson(json, O2GTelephonicState.class);
        TelephonicState state = o2gState.toTelephonicState();

        // Calls
        assertNotNull(state.getCalls());
        assertEquals(1, state.getCalls().size());
        assertEquals("aaaaa11111", state.getCalls().iterator().next().getCallRef());

        // Device capabilities
        assertNotNull(state.getDeviceCapabilities());
        assertEquals(1, state.getDeviceCapabilities().size());

        // User state
        assertEquals(UserState.BUSY, state.getUserState());
        
        assertNotNull(state.getDevicesState());
        assertEquals(2, state.getDevicesState().size());
        
        DeviceState ds = state.getDevicesState().iterator().next();
        
        assertEquals("2000", ds.getDeviceId());
        assertEquals(OperationalState.IN_SERVICE, ds.getState());
    }

    @Test
    void testDeserializationMin() {
        String json = "{}";

        O2GTelephonicState o2gState = gson.fromJson(json, O2GTelephonicState.class);
        TelephonicState state = o2gState.toTelephonicState();

        assertNotNull(state.getCalls());
        assertTrue(state.getCalls().isEmpty());

        assertNotNull(state.getDeviceCapabilities());
        assertTrue(state.getDeviceCapabilities().isEmpty());

        // userState defaults to UNKNOWN if null
        assertEquals(UserState.UNKNOWN, state.getUserState());

        assertNotNull(state.getDevicesState());
        assertTrue(state.getDevicesState().isEmpty());
    }
}