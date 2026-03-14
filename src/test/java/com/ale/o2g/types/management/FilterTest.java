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

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class FilterTest extends AbstractJsonTest {

    @Test
    void testCreateWithOperationEquals() {
        Filter filter = Filter.create("Attr", Filter.Operation.Equals, "Value");
        assertEquals("Attr==Value", filter.toString());
    }

    @Test
    void testCreateWithOperationNotEquals() {
        Filter filter = Filter.create("Attr", Filter.Operation.NotEquals, "Value");
        assertEquals("Attr=!Value", filter.toString());
    }

    @Test
    void testCreateWithOperationStartsWith() {
        Filter filter = Filter.create("Attr", Filter.Operation.StartsWith, "Value");
        assertEquals("Attr==Value*", filter.toString());
    }

    @Test
    void testCreateWithOperationEndsWith() {
        Filter filter = Filter.create("Attr", Filter.Operation.EndsWith, "Value");
        assertEquals("Attr==*Value", filter.toString());
    }

    @Test
    void testCreateWithOperationGreaterThanOrEquals() {
        Filter filter = Filter.create("Attr", Filter.Operation.GreatherThanOrEquals, "10");
        assertEquals("Attr=ge=10", filter.toString());
    }

    @Test
    void testCreateWithOperationLessThanOrEquals() {
        Filter filter = Filter.create("Attr", Filter.Operation.LessThanOrEquals, "20");
        assertEquals("Attr=le=20", filter.toString());
    }

    @Test
    void testAndCombination() {
        Filter f1 = Filter.create("A", Filter.Operation.Equals, "1");
        Filter f2 = Filter.create("B", Filter.Operation.Equals, "2");
        Filter f3 = Filter.create("C", Filter.Operation.Equals, "3");

        Filter combined = Filter.and(f1, f2, f3);
        assertEquals("A==1 and B==2 and C==3", combined.toString());
    }

    @Test
    void testOrCombination() {
        Filter f1 = Filter.create("X", Filter.Operation.Equals, "foo");
        Filter f2 = Filter.create("Y", Filter.Operation.Equals, "bar");

        Filter combined = Filter.or(f1, f2);
        assertEquals("X==foo or Y==bar", combined.toString());
    }

    @Test
    void testCreateFromPbxAttribute() {
        PbxAttribute attr = PbxAttribute.create("AttrName", "value");
        Filter filter = Filter.create(attr, Filter.Operation.Equals, "value");
        assertEquals("AttrName==value", filter.toString());
    }
}

