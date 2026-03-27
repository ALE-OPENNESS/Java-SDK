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

package com.ale.o2g.types.ccstats.data;

import static com.ale.o2g.test.ExtendAssert.assertEqualsUtc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.ccstats.TimeInterval;

/**
 * 
 */
public class SelectedPeriodTest extends AbstractJsonTest {
	
    @Test
    void testDeserializationFull() {
        // Prepare JSON with all fields
        String json = """
                {
                    "periodType": "oneDay",
                    "slotType": "aQuarterOfAnHour",
                    "beginDate": "2026-02-10",
                    "endDate": "2026-02-13"
                }
                """;

        SelectedPeriod period = gson.fromJson(json, SelectedPeriod.class);

        assertEquals(DataObservationPeriod.ONE_DAY, period.getObservationPeriod());
        assertEquals(TimeInterval.QUARTER_HOUR, period.getTimeInterval());
        assertEqualsUtc("2026-02-10", period.getBeginDate());
        assertEqualsUtc("2026-02-13", period.getEndDate());
    }

    @Test
    void testDeserializationMin() {
        // Prepare JSON with all fields
        String json = """
                {
                }
                """;

        SelectedPeriod period = gson.fromJson(json, SelectedPeriod.class);

        assertNull(period.getObservationPeriod());
        assertNull(period.getTimeInterval());
        assertNull(period.getBeginDate());
        assertNull(period.getEndDate());
    }

}
