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

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.management.O2GAttributeModel;
import com.ale.o2g.test.AbstractJsonTest;

class ModelAttributeTest extends AbstractJsonTest {

    @Test
    void testBasicProperties() {
        ModelAttribute attr = new ModelAttribute(
                "Attr1",
                true,
                ModelAttribute.Type.OctetString,
                true,
                Arrays.asList("value-1", "value-2"),
                new OctetStringLength(1, 10),
                "default",
                true,
                "context");

        // Name and type
        assertEquals("Attr1", attr.getName());
        assertEquals(ModelAttribute.Type.OctetString, attr.getType());

        // Mandatory and multi-value
        assertTrue(attr.isMandatory());
        assertTrue(attr.isMultiValue());

        // Allowed values
        assertContains(Arrays.asList("value-1", "value-2"), attr.getAllowedValues());

        // Collection should be unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> attr.getAllowedValues().add("val3"));

        // Octet string length
        assertEquals(1, attr.getOctetStringLength().getMin());
        assertEquals(10, attr.getOctetStringLength().getMax());

        // Default value, filtering, usedWhen
        assertEquals("default", attr.getDefaultValue());
        assertTrue(attr.isFiltering());
        assertEquals("context", attr.getUsedWhen());
    }

    @Test
    void testEmptyAllowedValues() {
        ModelAttribute attr = new ModelAttribute(
                "Attr2",
                false,
                ModelAttribute.Type.Boolean,
                false,
                null,
                null,
                null,
                false,
                null);

        // Should return empty list instead of null
        assertNotNull(attr.getAllowedValues());
        assertTrue(attr.getAllowedValues().isEmpty());
    }

    @Test
    void testDeserializationFull() {
        // JSON with all fields
        String json = """
                {
                	"name": "DirectoryNumber",
                	"mandatory": true,
                	"typeValue": "OctetString",
                	"lengthValue": "20",
                	"filtering": true
                }
                """;

        O2GAttributeModel o2gAttrModel = gson.fromJson(json, O2GAttributeModel.class);
        ModelAttribute modelAttr = o2gAttrModel.toAttributeModel();
        
        assertEquals("DirectoryNumber", modelAttr.getName());
        assertEquals(ModelAttribute.Type.OctetString, modelAttr.getType());
        assertTrue(modelAttr.isMandatory());
        assertTrue(modelAttr.isFiltering());
        assertFalse(modelAttr.isMultiValue());
        assertTrue(modelAttr.getAllowedValues().isEmpty());
        assertEquals(20, modelAttr.getOctetStringLength().getMax());
        assertNull(modelAttr.getDefaultValue());
        assertNull(modelAttr.getUsedWhen());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        O2GAttributeModel o2gAttrModel = gson.fromJson(json, O2GAttributeModel.class);
        ModelAttribute modelAttr = o2gAttrModel.toAttributeModel();
        
        assertNull(modelAttr.getName());
        assertNull(modelAttr.getType());
        assertFalse(modelAttr.isMandatory());
        assertFalse(modelAttr.isFiltering());
        assertFalse(modelAttr.isMultiValue());
        assertTrue(modelAttr.getAllowedValues().isEmpty());
        assertNull(modelAttr.getOctetStringLength());
        assertNull(modelAttr.getDefaultValue());
        assertNull(modelAttr.getUsedWhen());
    }
}