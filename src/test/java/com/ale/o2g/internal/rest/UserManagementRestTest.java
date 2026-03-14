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
import com.ale.o2g.types.users.User;

/**
 * 
 */
class UserManagementRestTest extends AbstractRestServiceTest<UserManagementRest> {

	protected UserManagementRestTest() {
        super(UserManagementRest.class, "https://o2g/rest/api/userManagement");
    }

	@Test
    void testGetLogins_withNodeIds_returnsCollection() throws Exception {
        defineResponse(200, "{ \"loginNames\": [\"oxe1000\", \"oxe1001\"] }");

        Collection<String> result = service.getLogins(new int[]{1, 2});

        assertCalledWith(GET, "?nodeIds=1%3B2");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("oxe1000"));
        assertTrue(result.contains("oxe1001"));
    }

    @Test
    void testGetLogin_withDeviceNumber_returnsLogin() throws Exception {
    	defineResponse(200, "{ \"loginNames\": [\"oxe1000\"] }");

        String login = service.getLogin("1001");

        assertCalledWith(GET, "?deviceNumber=1001");
        assertEquals("oxe1000", login);
    }

    @Test
    void testGetLogin_withNoLogin_returnsNull() throws Exception {
        defineResponse(200, "{ \"loginNames\": [] }");

        String login = service.getLogin("1001");

        assertCalledWith(GET, "?deviceNumber=1001");
        assertNull(login);
    }

    @Test
    void testGetUser_returnsUser() throws Exception {
        defineResponse(200, "{"
        		+ "\"companyPhone\": \"12000\","
        		+ "\"loginName\": \"oxe12000\","
        		+ "\"lastName\": \"Doe\""
        		+ "}"
        );

        User user = service.getUser("12000");

        assertCalledWith(GET, "/12000");
        assertNotNull(user);
        assertEquals("oxe12000", user.getLoginName());
        assertEquals("Doe", user.getLastName());
        assertEquals("12000", user.getCompanyPhone());
    }

    @Test
    void testCreateUsers_withDeviceNumbers_returnsUsers() throws Exception {
        defineResponse(200, "{ \"users\": ["
                + "{\"loginName\":\"oxe12000\", \"companyPhone\":\"12000\"},"
                + "{\"loginName\":\"oxe12001\", \"companyPhone\":\"12001\"}"
                + "]}");

        Collection<User> users = service.createUsers(100, new String[]{"12000", "12001"});

        assertCalledWith(POST, "/", "{"
                + "\"nodeId\":\"100\","
                + "\"deviceNumbers\":[\"12000\",\"12001\"],"
                + "\"all\":false"
                + "}");
        
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void testCreateUsers_withoutDeviceNumbers_returnsUsers() throws Exception {
        defineResponse(200, "{ \"users\": ["
                + "{\"loginName\":\"oxe12000\", \"companyPhone\":\"12000\"},"
                + "{\"loginName\":\"oxe12001\", \"companyPhone\":\"12001\"}"
                + "]}");

        Collection<User> users = service.createUsers(101);

        assertCalledWith(POST, "/", "{"
                + "\"nodeId\":\"101\","
                + "\"all\":true"
                + "}");
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser_returnsTrue() throws Exception {
        defineResponse(200, "");

        boolean success = service.deleteUser("oxe12000");

        assertCalledWith(DELETE, "/oxe12000");
        assertTrue(success);
    }
}
