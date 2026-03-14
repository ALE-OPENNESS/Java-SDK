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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;

class CallCenterPilotRestTest extends AbstractRestServiceTest<CallCenterPilotRest> {

    protected CallCenterPilotRestTest() {
        super(CallCenterPilotRest.class, "https://o2g/rest/api/callcenterpilot");
    }

    // --------------------------------------------------
    // Monitor Start
    // --------------------------------------------------

    @Test
    void testMonitorStart() throws Exception {

        defineResponse(200, "");

        boolean result = service.monitorStart(1, "12000");

        assertCalledWith(POST, "/12000");
        assertTrue(result);
    }

    @Test
    void testMonitorStop() throws Exception {

        defineResponse(200, "");

        boolean result = service.monitorStop("12000");

        assertCalledWith(DELETE, "/12000");
        assertTrue(result);
    }
}