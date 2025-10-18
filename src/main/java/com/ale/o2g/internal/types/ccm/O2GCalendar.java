/*
* Copyright 2021 ALE International
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
package com.ale.o2g.internal.types.ccm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ale.o2g.types.ccm.calendar.Calendar;
import com.ale.o2g.types.ccm.calendar.ExceptionCalendar;
import com.ale.o2g.types.ccm.calendar.NormalCalendar;
import com.ale.o2g.types.ccm.calendar.Transition;
import com.ale.o2g.types.ccm.calendar.Transition.PilotOperatingMode;
import com.ale.o2g.types.ccm.calendar.Transition.Time;

/**
 *
 */
public class O2GCalendar {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    
    public static class PilotTransition {
        private String time;
        private int ruleNumber;
        private O2GPilotOperatingMode mode;
        
        private PilotOperatingMode getPilotOperatingMode() {
            return PilotOperatingMode.valueOf(mode.name().toUpperCase());
        }
        
        Transition toTransition() {
            
            Time transitionTime = null;
            if (time != null) {
                String[] values = time.split(":");
                transitionTime = new Time(Integer.parseInt(values[0]), Integer.parseInt(values[1])) {};
            }
            
            return new Transition(ruleNumber, getPilotOperatingMode(), transitionTime);
        }
        
        public PilotTransition(Transition transition) {
            this.time = transition.getTime().toString();
            this.mode = O2GPilotOperatingMode.valueOf(transition.getMode().name().toLowerCase());
            this.ruleNumber = transition.getRuleNumber();
        }
    }
    
    static class PilotTransitions {
        private int number;
        private PilotTransition transition;
        
        public int getTransitionIndex() {
            return this.number;
        }
        
        public Transition toTransition() {
            return transition.toTransition();
        }
    }
    
    static class NormalTransitions {
        private O2GDayOfWeek day;
        private Collection<PilotTransitions> list;
        
        public DayOfWeek getDayOfWeek() {
            return DayOfWeek.valueOf(day.name().toUpperCase());
        }
        
        public List<Transition> toDayTransitions() {
            
            List<Transition> transitions =  new ArrayList<Transition>();
            if (list != null) {
                list.forEach(t -> transitions.add(t.getTransitionIndex()-1, t.toTransition()));
            }
            
            return transitions;
        }
    }
    
    public static class O2GNormalCalendar {
        private Collection<NormalTransitions> calendar;
        
        public NormalCalendar toNormalCalendar() {
            
            Map<DayOfWeek, List<Transition>> dayTransitions = new HashMap<DayOfWeek, List<Transition>>();
            
            if (calendar != null) {
                calendar.forEach(t -> dayTransitions.put(t.getDayOfWeek(), t.toDayTransitions()));
            }
            
            return new NormalCalendar(dayTransitions) {};
        }
        
    }

    public static class ExceptionTransitions {
        private String date;
        private Collection<PilotTransitions> list;
        
        public Date getDate() {
            try {
                return formatter.parse(date);
            }
            catch (ParseException e) {
                throw new RuntimeException("Invalid date format in calendar");
            }
        }
        
        public List<Transition> toDayTransitions() {
            
            List<Transition> transitions =  new ArrayList<Transition>();
            if (list != null) {
                list.forEach(t -> transitions.add(t.getTransitionIndex()-1, t.toTransition()));
            }
            
            return transitions;
        }
    }
    
    public static class O2GExceptionCalendar {
        private Collection<ExceptionTransitions> calendar;

        public ExceptionCalendar toExceptionCalendar() {
            
            Map<Date, List<Transition>> dayTransitions = new HashMap<Date, List<Transition>>();
            
            if (calendar != null) {
                calendar.forEach(t -> dayTransitions.put(t.getDate(), t.toDayTransitions()));
            }
            
            return new ExceptionCalendar(dayTransitions) {};
        }
}

    
    O2GNormalCalendar normalDays;
    O2GExceptionCalendar exceptionDays;
    
    public Calendar toCalendar() {
        return new Calendar(
                normalDays.toNormalCalendar(),
                exceptionDays.toExceptionCalendar()) {};
    }
}
