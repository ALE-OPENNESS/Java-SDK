/*
* Copyright 2025 ALE International
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import com.ale.o2g.internal.util.FormatUtil;
import com.ale.o2g.types.ccstats.Context;
import com.ale.o2g.types.ccstats.Format;
import com.ale.o2g.types.ccstats.scheduled.Recurrence;
import com.ale.o2g.types.ccstats.scheduled.ReportObservationPeriod;
import com.ale.o2g.types.ccstats.scheduled.ScheduledReport;

/**
 *
 */
public class ScheduledReportImpl implements ScheduledReport {

    private String name;
    private String description;
    private ReportObservationPeriod obsPeriod;
    private ReportFrequency frequency;
    private Collection<String> recipients;
    private Format fileType;
    private State state;
    private String lastExecDate;
    private boolean enable;
    
    private transient Context context;
    
        
    public ScheduledReportImpl(Context context, String name, String description, ReportObservationPeriod obsPeriod,
            Recurrence frequency, Format format, String[] recipients) {
        
        this.context = context;
        this.name = name;
        this.description = description;
        this.obsPeriod = obsPeriod;
        
        if (frequency != null) {
            this.frequency = ReportFrequency.from(frequency);
        }
        else {
            this.frequency = ReportFrequency.asOnce();   
        }
            
        this.fileType = format;
        this.recipients = Arrays.asList(recipients);
        this.state = State.NOT_EXECUTED;
        this.enable = false;
        this.lastExecDate = null;
    }
    
    
    public void setContext(Context context) {
        this.context = context;
    }
    
    @Override
    public Context getContext() {
        return this.context;
    }
    

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public ReportObservationPeriod getObservationPeriod() {
        return this.obsPeriod;
    }
    
    @Override
    public Recurrence getRecurrence() {
        return this.frequency.asRecurrence();
    }

    @Override
    public boolean isOnce() {
        return this.frequency.isOnce();
    }

    @Override
    public Collection<String> getRecipients() {
        return this.recipients;
    }

    @Override
    public Format getFormat() {
        return this.fileType;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public boolean isEnable() {
        return this.enable;
    }

    @Override
    public LocalDateTime getLastExecutionDate() {
        return FormatUtil.asLocalDateTime(lastExecDate);
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public void setObservationPeriod(ReportObservationPeriod observationPeriod) {
        this.obsPeriod = observationPeriod;
    }


    @Override
    public void setRecurrence(Recurrence recurrence) {
        if (recurrence != null) {
            this.frequency = ReportFrequency.from(recurrence);
        }
        else {
            this.frequency = ReportFrequency.asOnce();   
        }
    }


    @Override
    public void setFormat(Format format) {
        this.fileType = format;
    }


    @Override
    public void setRecipients(String[] recipients) {
        this.recipients = Arrays.asList(recipients);
    }

}
