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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.management.O2GObjectModel;
import com.ale.o2g.test.AbstractJsonTest;

class ModelTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON with all fields
        String json = """
                {
                    "objectName": "Subscriber",
                    "attributes": [ {
                    	"name": "DirectoryNumber",
                    	"mandatory": true,
                    	"typeValue": "OctetString"
                    }],
                    "objects": [{
                    	"objectName": "ProgKey",
                    	"attributes": [ {
	                    	"name": "number",
	                    	"typeValue": "OctetString"
                    	}]
                    }],
                    "create": true,
                    "delete": true,
                    "set": true,
                    "get": true,
                    "otherActions": ["FORCE_DELETE"]
                }
                """;

        O2GObjectModel o2gModel = gson.fromJson(json, O2GObjectModel.class);
        Model model = O2GObjectModel.build(o2gModel);
        
        assertEquals("Subscriber", model.getName());
        assertTrue(model.canCreate());
        assertTrue(model.canDelete());
        assertTrue(model.canGet());
        assertTrue(model.canSet());
        
        ModelAttribute attr = model.getAttribute("DirectoryNumber"); 
        assertNotNull(attr);
        assertEquals(ModelAttribute.Type.OctetString, attr.getType());
        assertNotNull(model.getChildModel("ProgKey"));
        
        assertContains(List.of("FORCE_DELETE"), model.getOtherActions());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        O2GObjectModel o2gModel = gson.fromJson(json, O2GObjectModel.class);
        Model model = O2GObjectModel.build(o2gModel);

        assertNull(model.getName());
        assertFalse(model.canCreate());
        assertFalse(model.canDelete());
        assertFalse(model.canGet());
        assertFalse(model.canSet());
        
        assertNotNull(model.getAttributes());
        assertTrue(model.getAttributes().isEmpty());

        assertNotNull(model.getChildModels());
        assertTrue(model.getChildModels().isEmpty());
        
        assertTrue(model.getOtherActions().isEmpty());
    }
}
