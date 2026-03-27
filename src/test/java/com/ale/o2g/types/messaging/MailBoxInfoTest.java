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

package com.ale.o2g.types.messaging;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class MailBoxInfoTest extends AbstractJsonTest {


    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                	"totalVoiceMsg": 3,
                	"newVoiceMsg": 2,
                	"storageUsage": 30
                }
                """;

        MailBoxInfo mbi = gson.fromJson(json, MailBoxInfo.class);
        
        assertEquals(3, mbi.getTotalVoiceMsg());
        assertEquals(2, mbi.getNewVoiceMsg());
        assertEquals(30, mbi.getStorageUsage());
    }

    @Test
    void testDeserializationMin() {
        String json = """
                {
                }
                """;

        MailBoxInfo mbi = gson.fromJson(json, MailBoxInfo.class);
        
        assertEquals(0, mbi.getTotalVoiceMsg());
        assertEquals(0, mbi.getNewVoiceMsg());
        assertEquals(0, mbi.getStorageUsage());
    }
}
