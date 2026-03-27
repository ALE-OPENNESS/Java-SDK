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

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.AuthenticateResult;
import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.Credential;

/**
 * 
 */
public class AuthenticationRestTest extends AbstractRestServiceTest<AuthenticationRest> {

    protected AuthenticationRestTest() {
        super(AuthenticationRest.class, "https://o2g/rest/api/authenticate");
    }

    @Test
    void authenticate() throws Exception {
        defineResponse(200, """
        		{
	        		"credential": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpbnRlcm5",
	        		"publicUrl": "https://public-o2g-url",
	        		"internalUrl": "https://private-o2g-url",
	        		"loginName": "john.doe",
	        		"expired": true
        		}
        		""");

        AuthenticateResult result = service.authenticate(new Credential("JohnDoe", "John#Doe;"));
        assertRequest().method(GET).uri("/").header("Authorization", "Basic Sm9obkRvZTpKb2huI0RvZTs=");
        
        assertNotNull(result);
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpbnRlcm5", result.getCredential());
        assertEquals("https://public-o2g-url", result.getPublicUrl());
        assertEquals("https://private-o2g-url", result.getPrivateUrl());
        assertEquals("john.doe", result.getLoginName());
        assertTrue(result.isExpired());
    }
}