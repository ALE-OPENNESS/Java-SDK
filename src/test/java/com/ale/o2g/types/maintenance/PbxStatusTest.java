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

package com.ale.o2g.types.maintenance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.maintenance.PbxStatus.CTILinkState;

/**
 * 
 */
public class PbxStatusTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                    "name": "O2G-Test",
                    "nodeId": 2,
                    "mainAddress": {
                    	"fqdn": "oxe.main.com",
                    	"ip": "10.1.20.129"
                    },
                    "secondaryAddress": {
                    	"fqdn": "oxe.standy.com",
                    	"ip": "10.1.20.119"
                    }, 
                    "version": "R101.1", 
                    "connected": true,
                    "loaded": true,
                    "ctiLinkState": "CONNECTED_MAIN", 
                    "secured": true,
                    "monitoredUserNumber": 20,
                    "lmsConnectionStatus": true
                }
                """;

        PbxStatus status = gson.fromJson(json, PbxStatus.class);

        assertEquals("O2G-Test", status.getName());
        assertEquals(2, status.getNodeId());
        assertEquals("10.1.20.129", status.getMainAddress().getIp());
        assertEquals("10.1.20.119", status.getSecondaryAddress().getIp());
        assertEquals("R101.1", status.getVersion());
        assertTrue(status.isConnected());
        assertTrue(status.isLoaded());
        assertTrue(status.isSecured());
        assertEquals(CTILinkState.CONNECTED_MAIN, status.getCtiLinkState());
        assertEquals(20, status.getMonitoredUserNumber());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        PbxStatus status = gson.fromJson(json, PbxStatus.class);

        assertNull(status.getName());
        assertEquals(-1, status.getNodeId());
        assertNull(status.getMainAddress());
        assertNull(status.getSecondaryAddress());
        assertNull(status.getVersion());
        assertFalse(status.isConnected());
        assertFalse(status.isLoaded());
        assertFalse(status.isSecured());
        assertNull(status.getCtiLinkState());
        assertEquals(0, status.getMonitoredUserNumber());
    }
}