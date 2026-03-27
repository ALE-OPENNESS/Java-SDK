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

package com.ale.o2g.events.telephony;

import static com.ale.o2g.test.ExtendAssert.assertContainsStrict;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.telephony.device.DeviceState;
import com.ale.o2g.types.telephony.device.OperationalState;

public class OnDeviceStateModifiedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "loginName": "oxe1000",
            "deviceStates": [
                { "deviceId": "Phone1", "state": "IN_SERVICE" },
                { "deviceId": "Phone2", "state": "OUT_OF_SERVICE" },
                { "deviceId": "Phone3", "state": "UNKNOWN" }
            ]
        }
        """;

        OnDeviceStateModifiedEvent event = gson.fromJson(json, OnDeviceStateModifiedEvent.class);

        // Basic fields
        assertEquals("oxe1000", event.getLoginName());

        // Device states
        assertNotNull(event.getStates());
        assertEquals(3, event.getStates().size());
        assertContainsStrict(List.of("Phone1", "Phone2", "Phone3"), event.getStates(), DeviceState::getDeviceId);

        // Check individual states
        for (DeviceState state : event.getStates()) {
            switch (state.getDeviceId()) {
                case "Phone1" -> assertEquals(OperationalState.IN_SERVICE, state.getState());
                case "Phone2" -> assertEquals(OperationalState.OUT_OF_SERVICE, state.getState());
                case "Phone3" -> assertEquals(OperationalState.UNKNOWN, state.getState());
                default -> fail("Unexpected deviceId: " + state.getDeviceId());
            }
        }
    }

    @Test
    void testEmptyEvent() {
        String json = "{}";

        OnDeviceStateModifiedEvent event = gson.fromJson(json, OnDeviceStateModifiedEvent.class);

        assertNull(event.getLoginName());
        assertNull(event.getStates());
    }
}