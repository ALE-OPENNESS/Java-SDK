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

package com.ale.o2g.types.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

/**
 * 
 */
class OperatorStateTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                	"mainState": "LOG_ON",
                	"subState": "OUT_OF_PG",
                	"proAcdDeviceNumber": "12300",
                	"pgNumber": "12000",
                	"withdrawReason": 2,
                	"withdrawn": true
                }
                """;

        // Deserialize JSON into OperatorConfiguration
        OperatorState state = gson.fromJson(json, OperatorState.class);
        
        // Verify all fields
        assertEquals(OperatorState.OperatorMainState.LOG_ON, state.getMainState());
        assertEquals(OperatorState.OperatorDynamicState.OUT_OF_PG, state.getSubState());
        assertEquals("12300", state.getProAcdDeviceNumber());
        assertEquals("12000", state.getPgNumber());
        
        assertEquals(2, state.getWithdrawReason());
        assertTrue(state.isWithdrawn());
    }

    @Test
    void testDeserializationMin() {
        // Prepare JSON with all fields
        String json = """
                {
                	"mainState": "LOG_OFF"
                }
                """;

        // Deserialize JSON into OperatorConfiguration
        OperatorState state = gson.fromJson(json, OperatorState.class);
        
        // Verify all fields
        assertEquals(OperatorState.OperatorMainState.LOG_OFF, state.getMainState());
        assertNull(state.getSubState());
        assertNull(state.getProAcdDeviceNumber());
        assertNull(state.getPgNumber());
        
        assertNull(state.getWithdrawReason());
        assertFalse(state.isWithdrawn());
    }


}
