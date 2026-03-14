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

package com.ale.o2g.events.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.events.management.OnInternalPbxObjectEvent;
import com.ale.o2g.test.AbstractJsonTest;

public class OnPbxObjectInstanceDeletedEventTest extends AbstractJsonTest {

    @Test
    void testFullEvent() {
        String json = """
        {
            "eventName": "PBX_OBJECT_DELETED",
            "objectName": "Subscriber_Key",
            "objectId": "2",
            "nodeId": "1",
            "father": {
                "objectName": "Subscriber",
                "objectId": "35001"
            }
        }
        """;

        OnPbxObjectInstanceDeletedEvent event = (OnPbxObjectInstanceDeletedEvent) OnInternalPbxObjectEvent.adaptDeleted(gson.fromJson(json, OnInternalPbxObjectEvent.class));

        // Node ID
        assertEquals(1, event.getNodeId());

        // Object
        assertNotNull(event.getObject());
        assertEquals("Subscriber_Key", event.getObject().getObjectName());
        assertEquals("2", event.getObject().getObjectId());

        // Father
        assertNotNull(event.getFather());
        assertEquals("Subscriber", event.getFather().getObjectName());
        assertEquals("35001", event.getFather().getObjectId());
    }

    @Test
    void testEmptyEvent() {
        OnPbxObjectInstanceDeletedEvent event = gson.fromJson("{}", OnPbxObjectInstanceDeletedEvent.class);

        assertEquals(0, event.getNodeId()); // default int value
        assertNull(event.getObject());
        assertNull(event.getFather());
    }
}