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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.management.O2GPbxAttribute;
import com.ale.o2g.test.AbstractJsonTest;

class PbxAttributeTest extends AbstractJsonTest {

    @Test
    void testCreateStringAttribute() {
        PbxAttribute attr = PbxAttribute.create("AttrName", "value");

        assertEquals("AttrName", attr.getName());
        assertEquals("value", attr.asString());
    }

    @Test
    void testCreateBooleanAttribute() {
        PbxAttribute attr = PbxAttribute.create("AttrName", true);

        assertEquals("AttrName", attr.getName());
        assertTrue(attr.asBool());
    }

    @Test
    void testCreateIntegerAttribute() {
        PbxAttribute attr = PbxAttribute.create("AttrName", 42);

        assertEquals("AttrName", attr.getName());
        assertEquals(42, attr.asInt());
    }

    @Test
    void testCreateSetOfStrings() {
        List<String> values = Arrays.asList("A", "B", "C");

        PbxAttribute attr = PbxAttribute.createSetOfStrings("AttrName", values);

        assertEquals("AttrName", attr.getName());
        assertEquals(values, attr.values);
    }

    @Test
    void testCreateSequenceAttribute() {
        PbxAttributeMap map = PbxAttributeMap.create(Arrays.asList(
                PbxAttribute.create("Param1", 1),
                PbxAttribute.create("Param2", true)
        ));

        PbxAttribute attr = PbxAttribute.create("sequence", map);

        assertEquals("sequence", attr.getName());
        assertEquals(map, attr.asAttributeMap());
    }

    @Test
    void testCreateSetOfSequences() {
        PbxAttributeMap map1 = PbxAttributeMap.create(Arrays.asList(
                PbxAttribute.create("Skill_nb", 21)
        ));

        PbxAttributeMap map2 = PbxAttributeMap.create(Arrays.asList(
                PbxAttribute.create("Skill_nb", 22)
        ));

        PbxAttribute attr = PbxAttribute.createSetOfSequences(
                "skillSet",
                Arrays.asList(map1, map2)
        );

        assertEquals("skillSet", attr.getName());
        assertEquals(2, attr.asListOfMaps().size());
        assertEquals(map1, attr.getAt(0));
        assertEquals(map2, attr.getAt(1));
    }

    @Test
    void testSetters() {
        PbxAttribute attr = PbxAttribute.create("AttrName", "initial");

        attr.set(true);
        assertTrue(attr.asBool());

        attr.set(10);
        assertEquals(10, attr.asInt());

        attr.set("updated");
        assertEquals("updated", attr.asString());
    }

    @Test
    void testGetAtThrowsExceptionWhenNotSequenceSet() {
        PbxAttribute attr = PbxAttribute.create("AttrName", "value");

        assertThrows(AttributeFormatException.class, () -> attr.getAt(0));
    }

    @Test
    void testAddSequenceAttribute() {
        PbxAttribute sequence = PbxAttribute.create("sequence", (PbxAttributeMap) null);

        sequence.addSequenceAttribute(PbxAttribute.create("Param1", 5));
        sequence.addSequenceAttribute(PbxAttribute.create("Param2", true));

        assertNotNull(sequence.asAttributeMap());
    }


    @Test
    void testDeserializationSimple() {
        // JSON with all fields
        String json = """
                {
                	"name": "DirectoryNumber",
                	"value": ["35000"]
                }
                """;

        O2GPbxAttribute o2gPbxAttr = gson.fromJson(json, O2GPbxAttribute.class);
        PbxAttribute attr = o2gPbxAttr.toPbxAttribute();
        
        assertEquals("DirectoryNumber", attr.getName());
        assertEquals("35000", attr.asString());        
    }


    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        O2GPbxAttribute o2gPbxAttr = gson.fromJson(json, O2GPbxAttribute.class);
        PbxAttribute attr = o2gPbxAttr.toPbxAttribute();

        assertNull(attr.getName());
    }
}



