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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.ccm.Pilot;
import com.ale.o2g.types.ccm.calendar.Calendar;
import com.ale.o2g.types.ccm.calendar.Transition;
import com.ale.o2g.types.ccm.calendar.Transition.PilotOperatingMode;
import com.ale.o2g.types.ccm.calendar.Transition.Time;

class CallCenterManagementRestTest extends AbstractRestServiceTest<CallCenterManagementRest> {

    protected CallCenterManagementRestTest() {
        super(CallCenterManagementRest.class, "https://o2g/rest/api/callcenter/management");
    }

    // --------------------------------------------------
    // Pilots
    // --------------------------------------------------

    @Test
    void testGetPilots() throws Exception {
        defineResponse(200, """
        		{
        			"pilotList":[
        				{
        					"number": "15000",
        					"name": "Pilote-15000"
        				}
        			]
        		}
        		""");
        
        Collection<Pilot> result = service.getPilots(1);
        assertCalledWith(GET, "/1/pilots");
        assertNotNull(result);
        assertEquals("15000", result.iterator().next().getNumber());
    }

    @Test
    void testGetPilot() throws Exception {
        defineResponse(200, "{}");
        
        service.getPilot(1, "33000");
        
        assertCalledWith(GET, "/1/pilots/33000");
    }

    // --------------------------------------------------
    // Calendar
    // --------------------------------------------------

    @Test
    void testGetCalendar() throws Exception {
        defineResponse(200, "{}");
        
        Calendar result = service.getCalendar(1, "33000");
        
        assertCalledWith(GET, "/1/pilots/33000/calendar");
        assertNotNull(result);
    }

    @Test
    void testGetExceptionCalendar() throws Exception {
        defineResponse(200, "{}");
        
        service.getExceptionCalendar(1, "33000");
        
        assertCalledWith(GET, "/1/pilots/33000/calendar/exception");
    }

    @Test
    void testGetNormalCalendar() throws Exception {
        defineResponse(200, "{}");
        
        service.getNormalCalendar(1, "33000");
        
        assertCalledWith(GET, "/1/pilots/33000/calendar/normal");
    }

    // --------------------------------------------------
    // Exception Transitions
    // --------------------------------------------------

    @Test
    void testAddExceptionTransition() throws Exception {
        defineResponse(200, "");
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(2026, java.util.Calendar.JULY, 14);
        
        boolean result = service.addExceptionTransition(
        		1, 
        		"33000", 
        		cal.getTime(), 
        		new Transition(2, PilotOperatingMode.FORWARD, new Time(8, 0)));
        
        assertCalledWith(POST, "/1/pilots/33000/calendar/exception/20260714/transitions", "{"
        		+ "\"time\":\"08:00\","
        		+ "\"ruleNumber\":2,"
        		+ "\"mode\":\"forward\""
        		+ "}");
        assertTrue(result);
    }

<<<<<<< HEAD

    @Test
    void testDeleteExceptionTransition() throws Exception {
        defineResponse(200, "");
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(2026, java.util.Calendar.JULY, 14);
        
        boolean result = service.deleteExceptionTransition(1, "33000", cal.getTime(), 0);
        
        assertCalledWith(DELETE, "/1/pilots/33000/calendar/exception/20260714/transitions/1");
        assertTrue(result);
    }

    @Test
    void testSetExceptionTransition() throws Exception {
        defineResponse(200, "");
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(2026, java.util.Calendar.JULY, 14);
        

        boolean result = service.setExceptionTransition(
        		1, 
        		"33000", 
        		cal.getTime(), 
        		0, 
        		new Transition(3, PilotOperatingMode.NORMAL, new Time(6, 30)));
        
        assertCalledWith(PUT, "/1/pilots/33000/calendar/exception/20260714/transitions/1", "{"
        		+ "\"time\":\"06:30\","
        		+ "\"ruleNumber\":3,"
        		+ "\"mode\":\"normal\""
        		+ "}");
        assertTrue(result);
    }


=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
    // --------------------------------------------------
    // Normal Transitions
    // --------------------------------------------------

    @Test
    void testAddNormalTransition() throws Exception {
    	
        defineResponse(200, "");
        
        boolean result = service.addNormalTransition(
        		1, 
        		"33000", 
        		DayOfWeek.MONDAY,
        		new Transition(2, PilotOperatingMode.CLOSED, new Time(18, 30)));

        assertCalledWith(POST, "/1/pilots/33000/calendar/normal/monday/transitions", "{"
        		+ "\"time\":\"18:30\","
        		+ "\"ruleNumber\":2,"
        		+ "\"mode\":\"closed\""
        		+ "}");
      
        assertTrue(result);
    }

    @Test
    void testDeleteNormalTransition() throws Exception {
        defineResponse(200, "");
        
        boolean result = service.deleteNormalTransition(1, "33000", DayOfWeek.TUESDAY, 0);
        
        assertCalledWith(DELETE, "/1/pilots/33000/calendar/normal/tuesday/transitions/1");
        assertTrue(result);
    }

    @Test
    void testSetNormalTransition() throws Exception {
        defineResponse(200, "");
        
        boolean result = service.setNormalTransition(
        		1, 
        		"33000", 
        		DayOfWeek.WEDNESDAY, 
        		0, 
        		new Transition(3, PilotOperatingMode.NORMAL, new Time(6, 30)));
        
        assertCalledWith(PUT, "/1/pilots/33000/calendar/normal/wednesday/transitions/1", "{"
        		+ "\"time\":\"06:30\","
        		+ "\"ruleNumber\":3,"
        		+ "\"mode\":\"normal\""
        		+ "}");
        assertTrue(result);
    }

    // --------------------------------------------------
    // Open/Close Pilot
    // --------------------------------------------------

    @Test
    void testOpenPilot() throws Exception {
        defineResponse(200, "");
        boolean result = service.openPilot(1, "pilot1");
        assertCalledWith(POST, "/1/pilots/pilot1/open");
        assertTrue(result);
    }

    @Test
    void testClosePilot() throws Exception {
        defineResponse(200, "");
        boolean result = service.closePilot(1, "pilot1");
        assertCalledWith(POST, "/1/pilots/pilot1/close");
        assertTrue(result);
    }
}