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
package com.ale.o2g.types.ccstats.scheduled;

import java.time.LocalDateTime;
import java.util.Collection;

import com.ale.o2g.types.ccstats.Context;
import com.ale.o2g.types.ccstats.Format;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a scheduled report in the system.
 * <p>
 * Instances of this interface are created by {@code CallCenterStatisticsService.createScheduledReport(...)}. A scheduled
 * report defines a report to be generated periodically or once, along with its
 * format, recipients, observation period, recurrence, and execution state.
 * @see com.ale.o2g.CallCenterStatisticsService#createScheduledReport(Context, String, String, ReportObservationPeriod, Format, String[])
 * @see com.ale.o2g.CallCenterStatisticsService#createScheduledReport(Context, String, String, ReportObservationPeriod, Recurrence, Format, String[])
 * @since 2.7.4
 */
public interface ScheduledReport {

    /**
     * Enum representing the execution state of a scheduled report.
     */
    public static enum State {

        /** Report has not yet been executed. */
        @SerializedName("Not_executed")
        NOT_EXECUTED,

        /** Report has been executed successfully. */
        @SerializedName("Executed")
        EXECUTED,

        /** Execution failed during data retrieval. */
        @SerializedName("Failed_On_Get_Data")
        FAILED_ON_GET_DATA,

        /** Execution failed while sending the report via email. */
        @SerializedName("Failed_On_Send_Mail")
        FAILED_ON_SEND_MAIL,

        /** Report execution is currently in progress. */
        @SerializedName("In_progress")
        IN_PROGRESS,

        /** Scheduled report has expired and will no longer execute. */
        @SerializedName("Expired")
        EXPIRED
    }

    /**
     * Returns the unique identifier of the scheduled report.
     * 
     * @return the report ID
     */
    String getId();

    /**
     * Returns the description of the scheduled report.
     * 
     * @return the report description
     */
    String getDescription();

    
    /**
     * Sets the description of the scheduled report.
     *
     * @param description the report description to set
     */
    void setDescription(String description);
    
    /**
     * Returns the observation period used by this scheduled report.
     * 
     * @return the observation period
     */
    ReportObservationPeriod getObservationPeriod();

    /**
     * Sets the observation period for this scheduled report.
     *
     * @param observationPeriod the observation period to set
     */
    void setObservationPeriod(ReportObservationPeriod observationPeriod);
    
    
    /**
     * Returns the recurrence pattern for this report.
     * 
     * @return the recurrence, or null if the report is executed only once
     */
    Recurrence getRecurrence();
    
    /**
     * Sets the recurrence pattern for this report.
     *
     * @param recurrence the recurrence to set
     */
    void setRecurrence(Recurrence recurrence);

    /**
     * Indicates whether this report is executed only once.
     * 
     * @return true if the report is a one-time report, false if recurring
     */
    boolean isOnce();

    /**
     * Returns the output format of the report.
     * 
     * @return the report format
     */
    Format getFormat();

    /**
     * Sets the output format of the report.
     *
     * @param format the format to set
     */    
    void setFormat(Format format);
    
    /**
     * Returns the collection of recipients who will receive the report.
     * <p>
     * Each recipient is represented as an email address.
     *
     * @return a collection of email addresses of the report recipients
     */
    Collection<String> getRecipients();
    
    /**
     * Sets the recipients of the scheduled report.
     *
     * @param recipients an array of email addresses
     */
    void setRecipients(String[] recipients);
    

    /**
     * Returns the current state of the scheduled report.
     * 
     * @return the report execution state
     */
    State getState();

    /**
     * Indicates whether the scheduled report is currently enabled.
     * 
     * @return true if the report is enabled, false otherwise
     */
    boolean isEnable();

    /**
     * Returns the date and time when the report was last executed.
     * 
     * @return the last execution date, or null if never executed
     */
    LocalDateTime getLastExecutionDate();

    /**
     * Returns the context used to generate this scheduled report.
     *
     * @return the report execution context
     */
    Context getContext();
}
