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
package com.ale.o2g.internal.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 */
public class FormatUtil {

    /**
     * Create a Duration form a string formated as hh:mm:ss
     * @param sDuration
     * @return
     */
    public static Duration asDuration(String timeStr) {
         
        if (timeStr == null || !timeStr.matches("\\d+:\\d{2}:\\d{2}")) {
            return null;
        }

        String[] parts = timeStr.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);

        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }
    
    public static Double asDouble(String value) {
        if (value == null) {
            return null;
        }
        
        return Double.valueOf(value);
    }
    
    public static LocalDate asLocalDate(String value) {
        if (value == null) {
            return null;
        }
        
        try {
            return LocalDate.parse(value);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public static LocalDateTime asLocalDateTime(String value) {
        if (value == null) {
            return null;
        }
        
        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }
}
