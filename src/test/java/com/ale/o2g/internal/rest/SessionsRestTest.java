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

import org.junit.jupiter.api.Test;

import com.ale.o2g.SupervisedAccount;
import com.ale.o2g.internal.types.SessionInfo;
import com.ale.o2g.test.AbstractRestServiceTest;

/**
 * 
 */
class SessionsRestTest extends AbstractRestServiceTest<SessionsRest> {

    protected SessionsRestTest() {
        super(SessionsRest.class, "https://o2g/rest/api/sessions");
    }

    @Test
    void testOpenSessionWithSupervisedAccount() throws Exception {

        // Define the response
        defineResponse(200, "{"
                + "\"privateBaseUrl\":\"http://o2g/rest/api\","
                + "\"admin\":\"false\""
                + "}");

        // Call the method
        SessionInfo result = service.open("MyApplication", SupervisedAccount.withLoginName("oxe1000"));

        // Verify called URI and payload
        assertCalledWith(POST, "/", "{"
                + "\"applicationName\":\"MyApplication\","
                + "\"supervisedAccount\":{"
                	+ "\"type\":\"LOGIN_NAME\","
                    + "\"id\":\"oxe1000\""
                + "}"
            + "}"
        );

        // Assert result
        assertNotNull(result);
        assertEquals("http://o2g/rest/api", result.getPrivateBaseUrl());
        assertFalse(result.isAdmin());
    }

    @Test
    void testOpenSessionWithoutSupervisedAccount() throws Exception {

        // Define the response
        defineResponse(200, "{"
                + "\"privateBaseUrl\":\"http://o2g/rest/api\","
                + "\"admin\":\"false\""
                + "}");

        // Call the method
        SessionInfo result = service.open("MyApplication", null);

        // Verify called URI and payload
        assertCalledWith(POST, "/", "{"
                + "\"applicationName\":\"MyApplication\""
            + "}"
        );

        // Assert result
        assertNotNull(result);
        assertEquals("http://o2g/rest/api", result.getPrivateBaseUrl());
        assertFalse(result.isAdmin());
    }
    
    @Test
    void testCloseSession() throws Exception {

        // Define the response
        defineResponse(200, "");

        // Call the method
        boolean result = service.close();

        // Verify called URI
        assertCalledWith(DELETE, "/");

        // Assert result
        assertTrue(result);
    }

    @Test
    void testGetSession() throws Exception {

        // Define the response
        defineResponse(200, "{"
                + "\"privateBaseUrl\":\"http://o2g/rest/api\","
                + "\"admin\":\"false\""
                + "}");

        // Call the method
        SessionInfo result = service.get();

        // Verify called URI
        assertCalledWith(GET, "/");

        // Assert result
        assertNotNull(result);
        assertEquals("http://o2g/rest/api", result.getPrivateBaseUrl());
        assertFalse(result.isAdmin());
    }

    @Test
    void testSendKeepAlive() throws Exception {

        // Define the response
        defineResponse(200, "");

        // Call the method
        boolean result = service.sendKeepAlive();

        // Verify called URI
        assertCalledWith(POST, "/keepalive");

        // Assert result
        assertTrue(result);
    }
}