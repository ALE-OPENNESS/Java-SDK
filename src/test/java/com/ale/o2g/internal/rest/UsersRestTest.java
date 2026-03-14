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

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.users.Preferences;
import com.ale.o2g.types.users.SupportedLanguages;
import com.ale.o2g.types.users.User;

/**
 * 
 */
class UsersRestTest extends AbstractRestServiceTest<UsersRest> {

	protected UsersRestTest() {
		super(UsersRest.class, "https://o2g/rest/api/users");
	}

	@Test
    void testGetLogins_withIntNodeIds_returnsCollection() throws Exception {
        defineResponse(200, "{ \"loginNames\": [\"user1\", \"user2\"] }");

        Collection<String> result = service.getLogins(new int[]{1, 2}, true);

        assertCalledWith(GET, "https://o2g/rest/api/logins?nodeIds=1%3B2&onlyACD");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("user1"));
        assertTrue(result.contains("user2"));
    }

    @Test
    void testGetLogins_withStringNodeIds_returnsCollection() throws Exception {
        defineResponse(200, "{ \"loginNames\": [\"userA\", \"userB\"] }");

        Collection<String> result = service.getLogins(new String[]{"2", "3"}, false);

        assertCalledWith(GET, "https://o2g/rest/api/logins?nodeIds=2%3B3");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("userA"));
        assertTrue(result.contains("userB"));
    }

    @Test
    void testGetByLoginName_returnsUser() throws Exception {
        defineResponse(200, "{ \"companyPhone\": \"12000\", \"loginName\": \"oxe12000\" }");

        User result = service.getByLoginName("user1");

        assertCalledWith(GET, "/user1");
        assertNotNull(result);
        assertEquals("oxe12000", result.getLoginName());
        assertEquals("12000", result.getCompanyPhone());
    }

    @Test
    void testGetByCompanyPhone_returnsUser() throws Exception {
    	defineResponse(200, "{ \"companyPhone\": \"12000\", \"loginName\": \"oxe12000\" }");

        User result = service.getByCompanyPhone("12000");

        assertCalledWith(GET, "?companyPhone=12000");
        assertNotNull(result);
        assertEquals("oxe12000", result.getLoginName());
        assertEquals("12000", result.getCompanyPhone());
    }

    @Test
    void testGetPreferences_returnsPreferences() throws Exception {
        defineResponse(200, "{ \"guiLanguage\": \"fr\" }");

        Preferences result = service.getPreferences("oxe12000");

        assertCalledWith(GET, "/oxe12000/preferences");
        assertNotNull(result);
        assertEquals("fr", result.getGuiLanguage());
        assertNull(result.getOxeLanguage());
    }

    @Test
    void testChangePassword_returnsTrue() throws Exception {
        defineResponse(200, "");

        boolean result = service.changePassword("oxe12000", "oldPass", "newPass");

        assertCalledWith(PUT, "/oxe12000/password", "{"
                + "\"oldPassword\":\"oldPass\","
                + "\"newPassword\":\"newPass\""
                + "}");
        
        assertTrue(result);
    }

    @Test
    void testGetSupportedLanguages_returnsLanguages() throws Exception {
        defineResponse(200, "{ \"supportedLanguages\": [\"en\", \"fr\"] }");

        SupportedLanguages result = service.getSupportedLanguages("oxe12000");

        assertCalledWith(GET, "/oxe12000/preferences/supportedLanguages");
        assertNotNull(result);
        
        assertEquals(2, result.getSupportedLanguages().size());
        assertTrue(result.getSupportedLanguages().containsAll(Arrays.asList("en", "fr")));
        assertNull(result.getSupportedGuiLanguages());
    }
}