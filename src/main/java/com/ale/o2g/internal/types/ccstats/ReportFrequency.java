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
package com.ale.o2g.internal.types.ccstats;

import java.time.DayOfWeek;
import java.util.Arrays;

import com.ale.o2g.types.ccstats.scheduled.Recurrence;

/**
 *
 */
public final class ReportFrequency {

    private static enum Type {
        once,
        daily,
        weekly,
        monthly        
    }    
    
    private Type periodicity;
    private String[] daysInWeek;
    private int dayInMonth;
    
    
    protected ReportFrequency() {
        periodicity = Type.once;
        daysInWeek = null;
        dayInMonth = -1;
    }
    
    protected ReportFrequency(Recurrence recurrence) {
        
        if (recurrence.isDaily()) periodicity = Type.daily;
        else if (recurrence.isWeekly()) periodicity = Type.weekly;
        else periodicity = Type.monthly;        
        
        if (recurrence.getDayInWeek() != null) {
            this.daysInWeek = recurrence.getDayInWeek()
                    .stream()
                    .map(day -> day.name().toLowerCase()) 
                    .toArray(String[]::new);
        }
        else {
            this.daysInWeek = null;   
        }
        
        if (recurrence.getDayInMonth() > 0) {
            dayInMonth = recurrence.getDayInMonth();
        }
        else {
            dayInMonth = -1;
        }
    }
    
    
    public static ReportFrequency from(Recurrence recurrence) {
        return new ReportFrequency(recurrence);
    }
    
    
    public static ReportFrequency asOnce() {
        return new ReportFrequency();
    }
    
    
    public boolean isOnce() {
        return (periodicity == Type.once);
    }
    
    public Recurrence asRecurrence() {

        if (periodicity == Type.daily) {
            return Recurrence.daily();
        }
        else if (periodicity == Type.weekly) {
            return Recurrence.weekly(Arrays.stream(daysInWeek)
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .toArray(DayOfWeek[]::new));
        }
        else if (periodicity == Type.monthly) {
            return Recurrence.monthly(dayInMonth);
        }
        else {
            return null;
        }
    }
}
