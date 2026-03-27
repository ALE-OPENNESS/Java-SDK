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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class PbxAttributeMapTest extends AbstractJsonTest {

    @Test
    void testCreateEmptyMap() {
        PbxAttributeMap map = PbxAttributeMap.create();

        assertNotNull(map);
        assertTrue(map.getNames().isEmpty());
    }

    @Test
    void testAddAttributeAndGetAttribute() {
        PbxAttributeMap map = PbxAttributeMap.create();

        PbxAttribute attr = PbxAttribute.create("Attr1", "value1");
        map.add(attr);

        assertEquals(attr, map.getAttribute("Attr1"));
        assertEquals(1, map.getNames().size());
        assertTrue(map.getNames().contains("Attr1"));
    }

    @Test
    void testAddChaining() {
        PbxAttributeMap map = PbxAttributeMap.create()
                .add(PbxAttribute.create("Param1", 1))
                .add(PbxAttribute.create("Param2", true));

        assertEquals(2, map.getNames().size());
        assertEquals(1, map.getAttribute("Param1").asInt());
        assertTrue(map.getAttribute("Param2").asBool());
    }

    @Test
    void testCreateWithList() {
        List<PbxAttribute> attrs = Arrays.asList(
                PbxAttribute.create("A", 10),
                PbxAttribute.create("B", false)
        );

        PbxAttributeMap map = PbxAttributeMap.create(attrs);

        assertEquals(2, map.getNames().size());
        assertEquals(10, map.getAttribute("A").asInt());
        assertFalse(map.getAttribute("B").asBool());
    }

    @Test
    void testGetAttributeReturnsNullIfMissing() {
        PbxAttributeMap map = PbxAttributeMap.create();

        assertNull(map.getAttribute("unknown"));
    }

    @Test
    void testIterator() {
        PbxAttributeMap map = PbxAttributeMap.create()
                .add(PbxAttribute.create("A", 1))
                .add(PbxAttribute.create("B", 2));

        Iterator<PbxAttribute> iterator = map.iterator();

        assertTrue(iterator.hasNext());
        int count = 0;
        while (iterator.hasNext()) {
            PbxAttribute attr = iterator.next();
            assertNotNull(attr.getName());
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    void testGetNamesReflectsAttributes() {
        PbxAttributeMap map = PbxAttributeMap.create()
                .add(PbxAttribute.create("X", "value"))
                .add(PbxAttribute.create("Y", "value"));

        assertTrue(map.getNames().contains("X"));
        assertTrue(map.getNames().contains("Y"));
        assertEquals(2, map.getNames().size());
    }
}