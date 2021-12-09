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
import java.util.List;

import com.ale.o2g.types.analytics.CallType;
import com.ale.o2g.types.analytics.Charging;
import com.ale.o2g.types.analytics.TelFacility;

/**
 *
 */
public class O2GCharging {
    
    class TelFacilities {
        private List<TelFacility> facilities;
    }

    
    private String caller;
    private String name;
    private String called;
    private String initialDialledNumber;
    private int callNumber;
    private int chargingUnits;
    private float cost;
    private String startDate;
    private int duration;
    private CallType callType;
    private int effectiveCallDuration;
    private int actingExtensionNumberNode;
    private TelFacilities internalFacilities;
    private TelFacilities externalFacilities;

    public Charging toCharging() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

        Date startTimestamp;
        try {
            startTimestamp = formatter.parse(startDate);
        }
        catch (ParseException e) {
            startTimestamp = null;
        }
        
        return new Charging(
                caller,
                name,
                called,
                initialDialledNumber,
                callNumber,
                chargingUnits,
                cost,
                startTimestamp,
                duration, 
                callType, 
                effectiveCallDuration, 
                actingExtensionNumberNode, 
                internalFacilities.facilities,
                externalFacilities.facilities) {};
    }
}
