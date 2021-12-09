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
package com.ale.o2g.internal.types.analytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ale.o2g.types.analytics.Incident;

/**
 *
 */
public class O2GIncident {
    
    private String date;
    private String hour;
    private int severity;
    private String value;
    private String type;
    private int nbOccurs;
    private String node;
    private boolean main;
    private String rack;
    private String board;
    private String equipement;
    private String termination;

    public Incident toIncident() {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy-HH:mm:ss");

        Date timestamp;
        try {
            timestamp = formatter.parse(String.format("%s-%s", date, hour));
        }
        catch (ParseException e) {
            timestamp = null;
        }
        
        return new Incident(
                Integer.parseInt(value),
                timestamp,
                severity,
                type,
                nbOccurs,
                Integer.parseInt(node),
                main,
                rack,
                board,
                equipement,
                termination) {};
    }
}
