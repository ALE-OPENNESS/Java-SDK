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

package com.ale.o2g.types.ccm.calendar;

import static com.ale.o2g.test.ExtendAssert.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.ccm.O2GCalendar;
import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.ccm.calendar.Transition.Time;

public class CalendarTest extends AbstractJsonTest {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Test
    void testFullCalendarWithTransitions() throws Exception {
        String json = """
        {
          "normalDays": {
            "calendar": [
              {
                "day": "monday",
                "list": [
                  { "number": 1, "transition": { "time": "08:30", "ruleNumber": 101, "mode": "normal" } },
                  { "number": 2, "transition": { "time": "12:15", "ruleNumber": 102, "mode": "closed" } }
                ]
              },
              {
                "day": "tuesday",
                "list": [
                  { "number": 1, "transition": { "time": "09:00", "ruleNumber": 103, "mode": "forward" } }
                ]
              }
            ]
          },
          "exceptionDays": {
            "calendar": [
              {
                "date": "20260315",
                "list": [
                  { "number": 1, "transition": { "time": "10:00", "ruleNumber": 201, "mode": "normal" } }
                ]
              }
            ]
          }
        }
        """;

        // Deserialize DTO and convert to API object
        O2GCalendar o2gCalendar = gson.fromJson(json, O2GCalendar.class);
        Calendar calendar = o2gCalendar.toCalendar();

        // ---- Normal Calendar ----
        NormalCalendar normal = calendar.getNormalDays();
        assertNotNull(normal);
        assertContains(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), normal.getDays());
        
        // Check MONDAY transitions
        List<Transition> mondayTransitions = normal.getTransitions(DayOfWeek.MONDAY);
        assertEquals(2, mondayTransitions.size());

        Transition t1 = mondayTransitions.get(0);
        assertEquals(101, t1.getRuleNumber());
        assertEquals(8, t1.getTime().getHour());
        assertEquals(30, t1.getTime().getMinute());
        assertEquals(Transition.PilotOperatingMode.NORMAL, t1.getMode());

        Transition t2 = mondayTransitions.get(1);
        assertEquals(102, t2.getRuleNumber());
        assertEquals(12, t2.getTime().getHour());
        assertEquals(15, t2.getTime().getMinute());
        assertEquals(Transition.PilotOperatingMode.CLOSED, t2.getMode());

        // Check TUESDAY transitions
        List<Transition> tuesdayTransitions = normal.getTransitions(DayOfWeek.TUESDAY);
        assertEquals(1, tuesdayTransitions.size());
        Transition t3 = tuesdayTransitions.get(0);
        assertEquals(103, t3.getRuleNumber());
        assertEquals(9, t3.getTime().getHour());
        assertEquals(0, t3.getTime().getMinute());
        assertEquals(Transition.PilotOperatingMode.FORWARD, t3.getMode());

        // ---- Exception Calendar ----
        ExceptionCalendar exc = calendar.getExceptionDays();
        assertNotNull(exc);

        Date excDate = sdf.parse("20260315");
        List<Transition> excTransitions = exc.getTransitions(excDate);
        assertEquals(1, excTransitions.size());
        Transition et = excTransitions.get(0);
        assertEquals(201, et.getRuleNumber());
        assertEquals(10, et.getTime().getHour());
        assertEquals(0, et.getTime().getMinute());
        assertEquals(Transition.PilotOperatingMode.NORMAL, et.getMode());
    }

    @Test
    void testCalendarWithEmptyDays() {
        // Calendar with no normal or exception days
        Calendar calendar = new Calendar(null, null);
        assertNull(calendar.getNormalDays());
        assertNull(calendar.getExceptionDays());
    }

    @Test
    void testTransitionObject() {
        // Directly create a transition and check getters
        Transition.Time time = new Transition.Time(14, 45);
        Transition transition = new Transition(301, Transition.PilotOperatingMode.CLOSED, time);

        assertEquals(301, transition.getRuleNumber());
        assertEquals(Transition.PilotOperatingMode.CLOSED, transition.getMode());

        Time t = transition.getTime();
        assertEquals(14, t.getHour());
        assertEquals(45, t.getMinute());
        assertEquals("14:45", t.toString());
    }
}