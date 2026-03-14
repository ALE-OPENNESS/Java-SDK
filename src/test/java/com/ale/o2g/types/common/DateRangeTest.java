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

package com.ale.o2g.types.common;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class DateRangeTest {

    @Test
    void testCreation() {
        LocalDateTime from = LocalDateTime.of(2026, 3, 7, 10, 0);
        LocalDateTime to = LocalDateTime.of(2026, 3, 7, 12, 0);

        DateRange range = new DateRange(from, to);

        assertEquals(from, range.getFrom());
        assertEquals(to, range.getTo());
    }

    @Test
    void testNullValuesAllowed() {
        DateRange range = new DateRange(null, null);

        assertNull(range.getFrom());
        assertNull(range.getTo());
    }

    @Test
    void testFromAfterTo() {
        LocalDateTime from = LocalDateTime.of(2026, 3, 7, 15, 0);
        LocalDateTime to = LocalDateTime.of(2026, 3, 7, 10, 0);

        DateRange range = new DateRange(from, to);

        // Class does not validate ordering, so values should be stored as-is
        assertEquals(from, range.getFrom());
        assertEquals(to, range.getTo());
    }

    @Test
    void testSameFromAndTo() {
        LocalDateTime date = LocalDateTime.of(2026, 3, 7, 10, 0);

        DateRange range = new DateRange(date, date);

        assertEquals(date, range.getFrom());
        assertEquals(date, range.getTo());
    }
}