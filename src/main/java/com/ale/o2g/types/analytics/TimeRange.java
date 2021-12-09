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
package com.ale.o2g.types.analytics;

import java.util.Calendar;
import java.util.Date;

/**
 * {@code TimeRange} represents an interval between two dates. It is used to filter analytics requests
 */
public class TimeRange {

    private Date from;
    private Date to;

    /**
     * Construct a new TimeRange, with the specified "from" date and "to" date.
     * @param from the begining of the range
     * @param to the end of the range
     */
    public TimeRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Construct a new TimeRange with the specified "from" date and a specified number of days.
     * @param from the begining of the range
     * @param days the number of days in this range
     */
    public TimeRange(Date from, int days) {
        this.from = from;

        Calendar c = Calendar.getInstance(); 
        c.setTime(from); 
        c.add(Calendar.DATE, 1);        
        
        this.to = c.getTime();
    }

    /**
     * Returns the "from" date.
     * @return the from
     */
    public final Date getFrom() {
        return from;
    }

    
    /**
     * Return the "to" date.
     * @return the to
     */
    public final Date getTo() {
        return to;
    }

    
}
