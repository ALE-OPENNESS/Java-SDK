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

package com.ale.o2g.types.management;

import static com.ale.o2g.test.ExtendAssert.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.management.O2GPbxObject;
import com.ale.o2g.test.AbstractJsonTest;

class PbxObjectTest extends AbstractJsonTest {

    @Test
    void testBasicProperties() {

        Map<String, PbxAttribute> attrs = new HashMap<>();
        attrs.put("Attr1", PbxAttribute.create("Attr1", "value1"));

        Collection<String> subObjects = Arrays.asList("Sub1", "Sub2");

        PbxObject obj = new PbxObject(
                attrs,
                "TestObject",
                "123",
                subObjects,
                true,
                false
        );

        assertEquals("TestObject", obj.getName());
        assertEquals("123", obj.getId());

        assertTrue(obj.canBeDeleted());
        assertFalse(obj.canBeSet());
    }

    @Test
    void testGetAttribute() {

        Map<String, PbxAttribute> attrs = new HashMap<>();
        PbxAttribute attr = PbxAttribute.create("Attr1", 10);
        attrs.put("Attr1", attr);

        PbxObject obj = new PbxObject(attrs, "Obj", "1", null, false, true);

        assertEquals(attr, obj.getAttribute("Attr1"));
        assertNull(obj.getAttribute("Unknown"));
    }

    @Test
    void testGetObjectNames() {

        Collection<String> names = Arrays.asList("Child1", "Child2");

        PbxObject obj = new PbxObject(
                new HashMap<>(),
                "Obj",
                "1",
                names,
                false,
                false
        );

        Collection<String> result = obj.getObjectNames();

        assertEquals(2, result.size());
        assertTrue(result.contains("Child1"));
        assertTrue(result.contains("Child2"));

        assertThrows(UnsupportedOperationException.class,
                () -> result.add("Another"));
    }

    @Test
    void testGetObjectNamesWhenNull() {

        PbxObject obj = new PbxObject(
                new HashMap<>(),
                "Obj",
                "1",
                null,
                false,
                false
        );

        Collection<String> result = obj.getObjectNames();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeserializationFull() {
        // JSON with all fields
        String json = """
                {
                	"objectName": "Subscriber",
                	"objectId": "35000",
                	"attributes": [
                		{
                			"name": "Directory_Number",
                			"value": ["13000"]
                		},
                		{
                			"name": "Station_Type",
                			"value": ["ALE300"]
                		}                		
                	],
                	"objectNames": [ "ProgKey", "TCS-IP" ],
                	"create": true,
                	"set": true,
                	"get": true,
                	"delete": true,
                	"otherActions": [ "FORCE_DELETE" ]
                }
                """;

        O2GPbxObject o2gPbxObject = gson.fromJson(json, O2GPbxObject.class);
        PbxObject obj = O2GPbxObject.build(o2gPbxObject);
        
        assertEquals("Subscriber", obj.getName());
        assertEquals("35000", obj.getId());
        
        assertTrue(obj.canBeDeleted());
        assertTrue(obj.canBeSet());
        
        assertEquals(2, obj.getAttributes().size());
        assertEquals("ALE300", obj.getAttribute("Station_Type").asString());
        
        assertContains(List.of("ProgKey", "TCS-IP"), obj.getObjectNames());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        O2GPbxObject o2gPbxObject = gson.fromJson(json, O2GPbxObject.class);
        PbxObject obj = O2GPbxObject.build(o2gPbxObject);
        
        assertNull(obj.getName());
        assertNull(obj.getId());
        
        assertFalse(obj.canBeDeleted());
        assertFalse(obj.canBeSet());
        
        assertTrue(obj.getAttributes().isEmpty());        
        assertTrue(obj.getObjectNames().isEmpty());
    }

}