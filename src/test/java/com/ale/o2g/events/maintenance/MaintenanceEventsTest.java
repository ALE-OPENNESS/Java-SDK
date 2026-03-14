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

package com.ale.o2g.events.maintenance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.events.maintenance.OnInternalNodeIdEvent;
import com.ale.o2g.test.AbstractJsonTest;

public class MaintenanceEventsTest extends AbstractJsonTest {

    @Test
    void testAdaptPnxLinkUp() {
        String json = """
        {
            "eventName": "PBX_LINK_UP",
            "nodeId": "100"
        }
        """;

        OnPbxLinkUpEvent event = (OnPbxLinkUpEvent) OnInternalNodeIdEvent.adaptPbxLinkUp(gson.fromJson(json, OnInternalNodeIdEvent.class));

        assertEquals("PBX_LINK_UP", event.getName());
        assertEquals(100, event.getNodeId());
    }

    @Test
    void testAdaptPbxLinkDown() {
        String json = """
        {
            "eventName": "PBX_LINK_DOWN",
            "nodeId": "101"
        }
        """;

        OnPbxLinkDownEvent event = (OnPbxLinkDownEvent) OnInternalNodeIdEvent.adaptPbxLinkDown(gson.fromJson(json, OnInternalNodeIdEvent.class));

        assertEquals("PBX_LINK_DOWN", event.getName());
        assertEquals(101, event.getNodeId());
    }


	@Test
    void testAdaptLinkUp() {
        String json = """
        {
            "eventName": "CTI_LINK_UP",
            "nodeId": "100"
        }
        """;

        OnCtiLinkUpEvent event = (OnCtiLinkUpEvent) OnInternalNodeIdEvent.adaptCtiLinkUp(gson.fromJson(json, OnInternalNodeIdEvent.class));

        assertEquals("CTI_LINK_UP", event.getName());
        assertEquals(100, event.getNodeId());
    }

    @Test
    void testAdaptLinkDown() {
        String json = """
        {
            "eventName": "CTI_LINK_DOWN",
            "nodeId": "101"
        }
        """;

        OnCtiLinkDownEvent event = (OnCtiLinkDownEvent) OnInternalNodeIdEvent.adaptCtiLinkDown(gson.fromJson(json, OnInternalNodeIdEvent.class));

        assertEquals("CTI_LINK_DOWN", event.getName());
        assertEquals(101, event.getNodeId());
    }

    @Test
    void testAdaptPbxLoaded() {
        String json = """
        {
            "eventName": "PBX_LOADED",
            "nodeId": "200"
        }
        """;

        OnPbxLoadedEvent event = (OnPbxLoadedEvent) OnInternalNodeIdEvent.adaptPbxLoaded(gson.fromJson(json, OnInternalNodeIdEvent.class));

        assertEquals("PBX_LOADED", event.getName());
        assertEquals(200, event.getNodeId());
    }

    @Test
    void testEmptyOnPbxLoadedEvent() {
        String json = "{}";

        OnPbxLoadedEvent event = (OnPbxLoadedEvent) OnInternalNodeIdEvent.adaptPbxLoaded(gson.fromJson(json, OnInternalNodeIdEvent.class));
        assertNull(event.getName());
        assertEquals(-1, event.getNodeId());
    }
    
    @Test
    void testEmptyOnCtiLinkUpEvent() {
        String json = "{}";

        OnCtiLinkUpEvent event = (OnCtiLinkUpEvent) OnInternalNodeIdEvent.adaptCtiLinkUp(gson.fromJson(json, OnInternalNodeIdEvent.class));
        assertNull(event.getName());
        assertEquals(-1, event.getNodeId());
    }

    @Test
    void testEmptyOnCtiLinkDownEvent() {
        String json = "{}";

        OnCtiLinkDownEvent event = (OnCtiLinkDownEvent) OnInternalNodeIdEvent.adaptCtiLinkDown(gson.fromJson(json, OnInternalNodeIdEvent.class));
        assertNull(event.getName());
        assertEquals(-1, event.getNodeId());
    }
    
    @Test
    void testEmptyOnPbxLinkUpEvent() {
        String json = "{}";

        OnPbxLinkUpEvent event = (OnPbxLinkUpEvent) OnInternalNodeIdEvent.adaptPbxLinkUp(gson.fromJson(json, OnInternalNodeIdEvent.class));
        assertNull(event.getName());
        assertEquals(-1, event.getNodeId());
    }
    
    @Test
    void testEmptyOnPbxLinkDownEvent() {
        String json = "{}";

        OnPbxLinkDownEvent event = (OnPbxLinkDownEvent) OnInternalNodeIdEvent.adaptPbxLinkDown(gson.fromJson(json, OnInternalNodeIdEvent.class));
        assertNull(event.getName());
        assertEquals(-1, event.getNodeId());
   }
}