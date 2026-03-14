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

package com.ale.o2g.internal.rest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.analytics.ChargingFile;
import com.ale.o2g.types.analytics.ChargingResult;
import com.ale.o2g.types.analytics.Incident;
import com.ale.o2g.types.common.DateRange;


class AnalyticsRestTest extends AbstractRestServiceTest<AnalyticsRest> {

    protected AnalyticsRestTest() {
        super(AnalyticsRest.class, "https://o2g/rest/api/analytics");
    }

    @Test
    void testGetIncidents() throws Exception {
        defineResponse(200, """
        		{
	        		"incidents": [
	        			{
	        				"date":"03/03/26",
	        				"hour":"10:24:32",
	        				"severity": 1,
	        				"value":"2321",
	        				"type":"Error",
	        				"nbOccurs":1,
	        				"node": "2"
	        			}
	        		]
        		}
        		""");

        Collection<Incident> incidents = service.getIncidents(1, 5);

        assertCalledWith(GET, "/incidents?nodeId=1&last=5");
        assertNotNull(incidents);
        assertEquals(1, incidents.size());
        assertEquals(2321, incidents.iterator().next().getId());
    }

    @Test
    void testGetIncidentsWithoutLast() throws Exception {
    	
        defineResponse(200, "{ \"incidents\": [] }");

        Collection<Incident> incidents = service.getIncidents(2);

        assertCalledWith(GET, "/incidents?nodeId=2");
        assertNotNull(incidents);
        assertTrue(incidents.isEmpty());
    }

    @Test
    void testGetChargingFiles() throws Exception {
        defineResponse(200, """
        		{ 
        			"files": [ 
        				{
        					"name": "file1.csv", 
        					"date": "03/04/26",
        					"time": "10:30" 
        				} 
        			] 
        		}
        		""");

        Collection<ChargingFile> files = service.getChargingFiles(1);

        assertCalledWith(GET, "/charging/files?nodeId=1");
        
        assertNotNull(files);
        
        assertEquals(1, files.size());
        
        assertEquals("file1.csv", files.iterator().next().getName());
    }

    @Test
    void testGetChargingFilesWithDateRange() throws Exception {
    	
        defineResponse(200, """
        		{ 
        			"files": [ 
        				{
        					"name": "file1.csv", 
        					"date": "03/04/26",
        					"time": "10:30" 
        				} 
        			] 
        		}
        		""");

        DateRange filter = new DateRange(
                LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 31, 23, 59)
        );

        Collection<ChargingFile> files = service.getChargingFiles(1, filter);

        assertCalledWith(GET, "/charging/files?nodeId=1&fromDate=20230701&toDate=20230731");
        assertNotNull(files);
        assertEquals(1, files.size());
        assertEquals("file1.csv", files.iterator().next().getName());
    }

    @Test
    void testGetChargingsWithDateRange() throws Exception {
        defineResponse(200, """
        		{
        			"chargings": [
        				{
	        				"caller": "31000",
	        				"name": "John Doe",
	        				"called": "12000"
	        			}
        			],
        			"fromDate": "20260309",
        			"toDate": "20260310",
        			"nbChargingFiles": 3
        		}
        		""");

        DateRange filter = new DateRange(
                LocalDateTime.of(2023, 7, 1, 0, 0),
                LocalDateTime.of(2023, 7, 31, 23, 59)
        );

        ChargingResult result = service.getChargings(1, filter, true);

        assertCalledWith(GET, "/charging?nodeId=1&fromDate=20230701&toDate=20230731&all=true");
        assertNotNull(result);
        assertEquals(1, result.getChargings().size());
        assertEquals(3, result.getChargingFileCount());
        assertEquals(0, result.getTotalTicketCount());
    }

    @Test
    void testGetChargingsWithFiles() throws Exception {
        defineResponse(200, """
        		{
        			"chargings": [
        				{
	        				"caller": "31000",
	        				"name": "John Doe",
	        				"called": "12000"
	        			}
        			],
        			"fromDate": "20260309",
        			"toDate": "20260310",
        			"nbChargingFiles": 3
        		}
        		""");

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(2026, java.util.Calendar.JULY, 14);
        
        ChargingFile file = new ChargingFile("file1.csv", cal.getTime()) {};
        ChargingResult result = service.getChargings(1, List.of(file), true);

        assertCalledWith(GET, "/charging?nodeId=1&all=true");

        assertNotNull(result);
        assertEquals(1, result.getChargings().size());
        assertEquals(3, result.getChargingFileCount());
        assertEquals(0, result.getTotalTicketCount());
    }
}