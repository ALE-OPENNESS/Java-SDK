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

import java.nio.file.Path;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.messaging.MailBox;
import com.ale.o2g.types.messaging.MailBoxInfo;
import com.ale.o2g.types.messaging.VoiceMessage;



class MessagingRestTest extends AbstractRestServiceTest<MessagingRest> {

    protected MessagingRestTest() {
        super(MessagingRest.class, "https://o2g/rest/api/messaging");
    }

    @Test
    void testGetMailBoxes() throws Exception {

        defineResponse(200, "{ "
        		+ "\"mailboxes\": [{"
        			+ "\"id\": \"VM4645_M\""
        		+ "}] "
        	+ "}"
        );

        Collection<MailBox> result = service.getMailBoxes();

        assertCalledWith(GET, "/");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("VM4645_M", result.iterator().next().getId());
    }

    @Test
    void testGetMailBoxesWithLogin() throws Exception {

        defineResponse(200, "{ \"mailboxes\": [] }");

        Collection<MailBox> result = service.getMailBoxes("oxe1000");

        assertCalledWith(GET, "?loginName=oxe1000");
        assertNotNull(result);
    }

    @Test
    void testGetMailBoxInfoWithPassword() throws Exception {

        defineResponse(200, "{\"totalVoiceMsg\":2}");

        MailBoxInfo result = service.getMailBoxInfo("VM4645_M", "1234");

        assertCalledWith(POST, "/VM4645_M", "{\"password\":\"1234\"}");

        assertNotNull(result);
        assertEquals(2, result.getTotalVoiceMsg());
    }

    @Test
    void testGetMailBoxInfoWithUserPassword() throws Exception {

        defineResponse(200, "{\"totalVoiceMsg\":2}");

        MailBoxInfo result = service.getMailBoxInfo("VM4645_M", null);

        assertCalledWith(POST, "/VM4645_M?withUserPwd");
        assertNotNull(result);
    }

    @Test
    void testGetVoiceMessages() throws Exception {

        defineResponse(200, "{ \"voicemails\": [] }");

        Collection<VoiceMessage> result = service.getVoiceMessages("VM4645_M", true, 5, 10, "oxe1000");

        assertCalledWith(GET,
                "/VM4645_M/voicemails?loginName=oxe1000&offset=5&limit=10&newOnly=true");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteVoiceMessages() throws Exception {

        defineResponse(200, "");

        boolean result = service.deleteVoiceMessages("VM4645_M", new String[] {"v1", "v2"}, "oxe1000");

        assertCalledWith(DELETE, "/VM4645_M/voicemails?loginName=oxe1000&msgIds=v1%3Bv2");

        assertTrue(result);
    }

    @Test
    void testDeleteVoiceMessage() throws Exception {

        defineResponse(200, "");

        boolean result = service.deleteVoiceMessage("VM4645_M", "v1");

        assertCalledWith(DELETE, "/VM4645_M/voicemails/v1");

        assertTrue(result);
    }

    @Test
    void testDownloadVoiceMessageWithPath() throws Exception {

        defineResponse(200, "");

        // Inject a mock downloader that just returns a fake path
        Path fakePath = Path.of("mockedFile.wav");
        service.fileDownloader = (wavPath, response) -> fakePath;
        
        Path result = service.downloadVoiceMessage("VM4645_M", "v1", "C://tmp/message.wav");

        assertCalledWith(GET, "/VM4645_M/voicemails/v1");
        assertEquals(fakePath, result);
    }
}