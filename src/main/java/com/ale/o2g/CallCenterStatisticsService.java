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
package com.ale.o2g;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.events.ccstats.ProgressCallback;
import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.ccstats.Context;
import com.ale.o2g.types.ccstats.Filter;
import com.ale.o2g.types.ccstats.Format;
import com.ale.o2g.types.ccstats.Language;
import com.ale.o2g.types.ccstats.Requester;
import com.ale.o2g.types.ccstats.TimeInterval;
import com.ale.o2g.types.ccstats.data.StatisticsData;
import com.ale.o2g.types.ccstats.scheduled.Recurrence;
import com.ale.o2g.types.ccstats.scheduled.ReportObservationPeriod;
import com.ale.o2g.types.ccstats.scheduled.ScheduledReport;
import com.ale.o2g.types.common.DateRange;

/**
 * Provides access to historical ACD statistics and reporting for CCD agents and pilots.
 * <p>
 * This service supports two modes of data retrieval:
 * <ul>
 *   <li><b>Immediate reports</b> — statistics retrieved on demand, returned as in-memory data
 *       or exported as CSV or Excel files.</li>
 *   <li><b>Scheduled reports</b> — recurring statistics delivered as ZIP file attachments via
 *       email to predefined recipients.</li>
 * </ul>
 *
 * <h2>Requesters and Contexts</h2>
 * <p>
 * Statistics are accessed through a two-level hierarchy:
 * <ul>
 *   <li>A {@link Requester} defines the scope of agents whose data can be accessed
 *       (e.g., a team leader's requester covers all agents in the team).</li>
 *   <li>A {@link Context} defines the filter criteria (pilots, agents, queues) for which
 *       statistics are collected.</li>
 * </ul>
 *
 * <h2>Retrieving Statistics</h2>
 * <p>
 * The typical usage sequence is:
 * <ol>
 *   <li>Create a requester with {@link #createRequester}, specifying the agents in scope.</li>
 *   <li>Create a context with {@link #createContext}, specifying the filter criteria.</li>
 *   <li>Retrieve data with {@link #getData(Context, LocalDate, TimeInterval)} for a single day,
 *       or {@link #getData(Context, DateRange)} for a date range.</li>
 *   <li>Delete the context and requester when done.</li>
 * </ol>
 *
 * <pre>{@code
 * // Get the CallCenterStatisticsService from the opened session
 * CallCenterStatisticsService statService = session.getCallCenterStatisticsService();
 *
 * // Create a requester scoped to a set of agents
 * Requester requester = statService.createRequester(
 *         "John Doe",
 *         Language.EN,
 *         new String[] { "60114", "60115", "60116", "60117", "60118", "60119" });
 *
 * // Create a filter with all agent attributes
 * AgentFilter filter = Filter.createAgentFilter();
 * filter.setAgentAttributes(AgentAttributes.ALL);
 *
 * // Create a statistics context
 * Context context = statService.createContext(requester, "AgentStatContext", "Agent Statistics", filter);
 *
 * // Retrieve in-memory statistics for a date range
 * LocalDate startDate = LocalDate.of(2025, 10, 1);
 * DateRange range = new DateRange(startDate, startDate.plusDays(4));
 * StatisticsData statistics = statService.getData(context, range);
 *
 * // Or schedule a recurring report
 * ScheduledReport report = statService.createScheduledReport(
 *         context,
 *         "ScheduledReport",
 *         "Agent Weekly Report",
 *         ReportObservationPeriod.onCurrentWeek(),
 *         Recurrence.weekly(DayOfWeek.MONDAY),
 *         Format.CSV,
 *         new String[] { "john.doe@mycompany.com" });
 * }</pre>
 *
 * <h2>Report Types</h2>
 * <p>
 * Two types of reports are available:
 * <ul>
 *   <li><b>Single-day detailed reports</b>
 *     <ul>
 *       <li>Provide statistics in time slots (15-minute or 30-minute intervals).</li>
 *       <li>Data spans from 00:00 until the last completed interval of the day.</li>
 *     </ul>
 *   </li>
 *   <li><b>Multi-day reports</b>
 *     <ul>
 *       <li>Provide aggregated statistics (one row per agent or pilot per day).</li>
 *       <li>Can cover up to 31 consecutive days within the last 12 months.</li>
 *       <li>May span month boundaries (e.g., from 15/04/2024 to 14/05/2024).</li>
 *       <li>Data spans from 00:00 of the first day to 24:00 of the last day.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Scheduling</h2>
 * <p>
 * Scheduled reports apply only to multi-day data:
 * <ul>
 *   <li>Each schedule is linked to a specific context.</li>
 *   <li>There is no limitation on the number of scheduled reports per context.</li>
 *   <li>Reports are delivered as ZIP file attachments via email.</li>
 * </ul>
 *
 * <h3>Scheduling Rules</h3>
 * <p>
 * Scheduled reports must comply with specific rules depending on the
 * {@link ReportObservationPeriod.PeriodType}:
 * <ul>
 *   <li><b>Current Day:</b> Recurrence can only be <b>daily</b>.
 *       Each run collects data from the previous day.</li>
 *   <li><b>Current Week:</b> Recurrence can only be <b>weekly</b>.
 *       Example: recurrence on Monday and Thursday collects Monday's data on Tuesday,
 *       and data from Monday to Wednesday on Thursday.</li>
 *   <li><b>Current Month:</b> Recurrence can only be <b>monthly</b>.
 *       Example: on the 12th of each month, collects data from the 1st 00:00 to the 11th 24:00.</li>
 *   <li><b>Last N Days:</b> Recurrence can be <b>daily</b>, <b>weekly</b>, or <b>monthly</b>.</li>
 *   <li><b>Last N Weeks:</b> Recurrence can only be <b>weekly</b>.</li>
 *   <li><b>Last Month:</b> Recurrence can only be <b>monthly</b>.</li>
 *   <li><b>From Date to Date:</b> Recurrence is not allowed; the report can only be scheduled once.</li>
 *   <li><b>One-time reports:</b> Any observation period can be used for a single scheduled execution.</li>
 * </ul>
 *
 * <h2>Using Asynchronous File Download Methods</h2>
 * <p>
 * Before invoking any {@code getFileData} overload, subscribe to
 * <b>CallCenterStatistics events</b> so that progress notifications and completion
 * callbacks are received:
 * <pre><code>
 * Subscription subscription = Subscription.newBuilder()
 *       .addCallCenterStatisticsEventListener()
 *       .build();
 * session.listenEvents(subscription);
 * </code></pre>
 * <p>
 * This subscription only needs to be established once per session and remains valid for all
 * subsequent asynchronous operations.
 * <p>
 * Using this service requires a <b>CONTACTCENTER_SERVICE</b> license in CAPEX mode, or a
 * <b>40 api-tel-f</b> subscription in OPEX mode (Purple On Demand).
 *
 * @since 2.7.4
 */
public interface CallCenterStatisticsService extends IService {

    /**
     * Creates a new {@code Requester} with the specified identifier, language, and time zone,
     * and establishes the statistics scope defining which agents' data the requester is
     * authorized to access.
     * <p>
     * The agent scope determines which agents' statistics can be retrieved by the requester.
     * Once created, the requester can query individual or aggregated statistics for those agents
     * through the reporting services.
     *
     * @param id       the unique identifier of the requester (e.g., a supervisor ID)
     * @param language the requester's preferred {@link Language}
     * @param timezone the requester's time zone as a {@link ZoneOffset}
     * @param agents   an array of agent directory numbers that define the scope of accessible statistics
     * @return the newly created {@code Requester} instance
     * @see #deleteRequester(Requester)
     */
    Requester createRequester(String id, Language language, ZoneOffset timezone, String[] agents);

    /**
     * Creates a new {@code Requester} with the specified identifier and language,
     * using the system's default time zone offset, and establishes the statistics scope
     * defining which agents' data the requester is authorized to access.
     * <p>
     * The agent scope determines which agents' statistics can be retrieved by the requester.
     * Once created, the requester can query individual or aggregated statistics for those agents
     * through the reporting services.
     *
     * @param id       the unique identifier of the requester (e.g., a supervisor ID)
     * @param language the requester's preferred {@link Language}
     * @param agents   an array of agent directory numbers that define the scope of accessible statistics
     * @return the newly created {@code Requester} instance
     * @see #deleteRequester(Requester)
     */
    Requester createRequester(String id, Language language, String[] agents);

    
    /**
     * Removes the specified requester and all its associated contexts.
     * <p>
     * After this call, the requester no longer has access to any agent statistics defined
     * under its scope.
     *
     * @param requester the requester to remove
     * @return {@code true} if the requester was successfully removed; {@code false} otherwise
     */
    boolean deleteRequester(Requester requester);

    /**
     * Retrieves the requester associated with the specified identifier.
     * <p>
     * The returned requester represents the scope of agents for which statistics can be
     * accessed.
     *
     * @param id the unique identifier of the requester
     * @return the {@link Requester} object corresponding to the ID, or {@code null} if no
     *         matching requester is found
     */
    Requester getRequester(String id);


    /**
     * Creates a new statistics context with the specified label, description, and filter
     * for the given requester.
     * <p>
     * A context defines the filter criteria (pilots, agents, queues) for which call-center
     * statistics are collected and analyzed.
     *
     * @param requester   the requester for whom the context is created
     * @param label       a short label identifying this context
     * @param description a detailed description of the context
     * @param filter      the filter defining the selection criteria for the context
     * @return the created {@link Context} if successful; {@code null} otherwise
     */
    Context createContext(Requester requester, String label, String description, Filter filter);


    /**
     * Retrieves all statistics contexts created for the specified requester.
     *
     * @param requester the requester whose contexts are retrieved
     * @return a {@link Collection} of {@link Context} objects if successful; {@code null} if
     *         there is an error or if no contexts exist for this requester
     * @see #createContext(Requester, String, String, Filter)
     */
    Collection<Context> getContexts(Requester requester);


    /**
     * Retrieves a statistics context by its identifier for the specified requester.
     *
     * @param requester the requester who owns the context
     * @param contextId the unique identifier of the context
     * @return the {@link Context} if found; {@code null} if no context exists with the specified
     *         identifier or if an error occurred
     */
    Context getContext(Requester requester, String contextId);

    /**
     * Deletes all statistics contexts associated with the specified requester.
     *
     * @param requester the requester whose contexts should be deleted
     * @return {@code true} if all contexts were successfully deleted; {@code false} if an error
     *         occurred or no contexts were deleted
     * @see #createContext(Requester, String, String, Filter)
     */
    boolean deleteContexts(Requester requester);

    /**
     * Deletes the specified statistics context.
     *
     * @param context the context to delete
     * @return {@code true} if the context was successfully deleted; {@code false} if an error
     *         occurred or the context could not be deleted
     * @see #createContext(Requester, String, String, Filter)
     */
    boolean deleteContext(Context context);


    /**
     * Persists any changes made to the specified statistics context.
     * <p>
     * Fields that can be updated include the label, description, and filter.
     *
     * @param context the context with updated parameters to apply
     * @return {@code true} if the context was successfully updated; {@code false} if an error
     *         occurred or the update could not be applied
     * @see #createContext(Requester, String, String, Filter)
     */
    boolean updateContext(Context context);
    
    /**
     * Returns aggregated statistics for a range of days.
     * <p>
     * Multi-day reports provide one row of aggregated data per agent or pilot per day.
     * The range can cover up to 31 consecutive days within the last 12 months and may span
     * month boundaries.
     *
     * @param context the context defining the scope and filters for the statistics
     * @param range   the date range over which to collect statistics
     * @return a {@link StatisticsData} object containing the aggregated data, or {@code null}
     *         if the data could not be retrieved
     */
    StatisticsData getData(Context context, DateRange range);
    
    /**
     * Returns statistics for a single day with the specified time slot granularity.
     * <p>
     * Statistics are provided in time slots according to {@link TimeInterval}, spanning from
     * 00:00 until the last completed interval of the specified day.
     *
     * @param context      the context defining the scope and filters for the statistics
     * @param date         the day for which to collect statistics
     * @param timeInterval the time slot interval for reporting (e.g., 15 or 30 minutes)
     * @return a {@link StatisticsData} object containing the data, or {@code null} if the data
     *         could not be retrieved
     */
    StatisticsData getData(Context context, LocalDate date, TimeInterval timeInterval);
    
    /**
     * Returns statistics for a single day using the default 15-minute time slot granularity.
     * <p>
     * Statistics span from 00:00 until the last completed 15-minute interval of the specified
     * day.
     *
     * @param context the context defining the scope and filters for the statistics
     * @param date    the day for which to collect statistics
     * @return a {@link StatisticsData} object containing the data, or {@code null} if the data
     *         could not be retrieved
     */
    StatisticsData getData(Context context, LocalDate date);

    /**
     * Returns statistics for the current day using the default 15-minute time slot granularity.
     * <p>
     * Statistics span from 00:00 until the last completed 15-minute interval of the current day.
     *
     * @param context the context defining the scope and filters for the statistics
     * @return a {@link StatisticsData} object containing the data, or {@code null} if the data
     *         could not be retrieved
     */
    StatisticsData getData(Context context);

    /**
     * Asynchronously downloads statistics for a single day as a report file.
     * <p>
     * Statistics are reported in time slots defined by {@link TimeInterval} (e.g., 15-minute or
     * 30-minute intervals), spanning from 00:00 until the last completed interval of the
     * specified day. The generated report file is saved in the specified directory in the
     * format indicated by {@link Format}.
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the ongoing
     * operation. The future will:
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file on success.</li>
     *   <li>Complete exceptionally with an {@link IllegalStateException} if another request is
     *       already in progress.</li>
     *   <li>Complete exceptionally with a
     *       {@link java.util.concurrent.CancellationException CancellationException} if the
     *       operation is cancelled.</li>
     *   <li>Complete exceptionally with other exceptions on I/O or service errors.</li>
     * </ul>
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active at a time.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * CompletableFuture<Path> future = statService.getFileData(
     *     context,
     *     LocalDate.of(2025, 10, 5),
     *     TimeInterval.HOUR,
     *     Format.EXCEL,
     *     Paths.get("downloads"),
     *     (percent, message) -> System.out.println(percent + "% - " + message)
     * );
     *
     * future.whenComplete((path, ex) -> {
     *     if (future.isCancelled()) {
     *         System.out.println("Download cancelled!");
     *     } else if (ex != null) {
     *         System.err.println("Download failed: " + ex.getMessage());
     *     } else {
     *         System.out.println("Download complete! File saved at: " + path);
     *     }
     * });
     * }</pre>
     *
     * <p><b>Note:</b> This method requires that CallCenterStatistics event subscriptions be
     * active. See the {@link CallCenterStatisticsService class documentation} for details and
     * setup example.
     *
     * @param context          the context defining the scope and filters for the statistics
     * @param date             the date for which to generate the report
     * @param timeInterval     the length of each reporting time slot within the day
     * @param format           the output format for the report file
     * @param directory        the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress; may be {@code null} if
     *                         progress updates are not needed
     * @return a {@link CompletableFuture} that completes with the {@link Path} to the generated
     *         report file, or exceptionally if an error occurs or the operation is cancelled
     * @see #cancelRequest(Context)
     */
    CompletableFuture<Path> getFileData(
            Context context,
            LocalDate date,
            TimeInterval timeInterval,
            Format format,
            Path directory,
            ProgressCallback progressCallback);
    
    /**
     * Asynchronously downloads statistics for a single day as a report file, using the default
     * 15-minute time slot granularity.
     * <p>
     * Statistics span from 00:00 until the last completed 15-minute interval of the specified
     * day. The generated report file is saved in the specified directory in the format indicated
     * by {@link Format}.
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the ongoing
     * operation. The future will:
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file on success.</li>
     *   <li>Complete exceptionally with an {@link IllegalStateException} if another request is
     *       already in progress.</li>
     *   <li>Complete exceptionally with a
     *       {@link java.util.concurrent.CancellationException CancellationException} if the
     *       operation is cancelled.</li>
     *   <li>Complete exceptionally with other exceptions on I/O or service errors.</li>
     * </ul>
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active at a time.
     *
     * <p><b>Example:</b></p>
     * <pre><code>
     * CompletableFuture&lt;Path&gt; future = statService.getFileData(
     *     context,
     *     LocalDate.of(2025, 10, 5),
     *     Format.CSV,
     *     Paths.get("reports"),
     *     (percent, message) -&gt; System.out.println(percent + "% - " + message)
     * );
     *
     * future.whenComplete((path, ex) -&gt; {
     *     if (future.isCancelled()) {
     *         System.out.println("Report generation cancelled!");
     *     } else if (ex != null) {
     *         System.err.println("Report generation failed: " + ex.getMessage());
     *     } else {
     *         System.out.println("Report complete! File saved at: " + path);
     *     }
     * });
     * </code></pre>
     *
     * <p><b>Note:</b> This method requires that CallCenterStatistics event subscriptions be
     * active. See the {@link CallCenterStatisticsService class documentation} for details and
     * setup example.
     *
     * @param context          the context defining the scope and filters for the statistics
     * @param date             the date for which to generate the report
     * @param format           the output format for the report file
     * @param directory        the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress; may be {@code null} if
     *                         progress updates are not needed
     * @return a {@link CompletableFuture} that completes with the {@link Path} to the generated
     *         report file, or exceptionally if an error occurs or the operation is cancelled
     * @see #cancelRequest(Context)
     */
    CompletableFuture<Path> getFileData(
            Context context,
            LocalDate date,
            Format format,
            Path directory,
            ProgressCallback progressCallback);
    
    
    /**
     * Asynchronously downloads statistics for the current day as a report file, using the
     * default 15-minute time slot granularity.
     * <p>
     * Statistics span from 00:00 until the last completed 15-minute interval of the current day.
     * The generated report file is saved in the specified directory in the format indicated by
     * {@link Format}.
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the ongoing
     * operation. The future will:
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file on success.</li>
     *   <li>Complete exceptionally with an {@link IllegalStateException} if another request is
     *       already in progress.</li>
     *   <li>Complete exceptionally with a
     *       {@link java.util.concurrent.CancellationException CancellationException} if the
     *       operation is cancelled.</li>
     *   <li>Complete exceptionally with other exceptions on I/O or service errors.</li>
     * </ul>
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active at a time.
     *
     * <p><b>Example:</b></p>
     * <pre><code>
     * CompletableFuture&lt;Path&gt; future = statService.getFileData(
     *     context,
     *     Format.EXCEL,
     *     Paths.get("downloads"),
     *     (percent, message) -&gt; System.out.println(percent + "% - " + message)
     * );
     *
     * future.whenComplete((path, ex) -&gt; {
     *     if (future.isCancelled()) {
     *         System.out.println("Download cancelled!");
     *     } else if (ex != null) {
     *         System.err.println("Download failed: " + ex.getMessage());
     *     } else {
     *         System.out.println("Download complete! File saved at: " + path);
     *     }
     * });
     * </code></pre>
     *
     * <p><b>Note:</b> This method requires that CallCenterStatistics event subscriptions be
     * active. See the {@link CallCenterStatisticsService class documentation} for details and
     * setup example.
     *
     * @param context          the context defining the scope and filters for the statistics
     * @param format           the output format for the report file
     * @param directory        the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress; may be {@code null} if
     *                         progress updates are not needed
     * @return a {@link CompletableFuture} that completes with the {@link Path} to the generated
     *         report file, or exceptionally if an error occurs or the operation is cancelled
     * @see #cancelRequest(Context)
     */
    CompletableFuture<Path> getFileData(
            Context context,
            Format format,
            Path directory,
            ProgressCallback progressCallback);
    
    /**
     * Asynchronously downloads statistics for a range of days as a report file.
     * <p>
     * Multi-day reports provide one row of aggregated data per agent or pilot per day. The range
     * can cover up to 31 consecutive days within the last 12 months and may span month
     * boundaries. The generated report file is saved in the specified directory in the format
     * indicated by {@link Format}.
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the ongoing
     * operation. The future will:
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file on success.</li>
     *   <li>Complete exceptionally with an {@link IllegalStateException} if another request is
     *       already in progress.</li>
     *   <li>Complete exceptionally with a
     *       {@link java.util.concurrent.CancellationException CancellationException} if the
     *       operation is cancelled.</li>
     *   <li>Complete exceptionally with other exceptions on I/O or service errors.</li>
     * </ul>
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active at a time.
     *
     * <p><b>Example:</b></p>
     * <pre><code>
     * CompletableFuture&lt;Path&gt; future = statService.getFileData(
     *     context,
     *     new DateRange(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 30)),
     *     Format.CSV,
     *     Paths.get("reports"),
     *     (percent, message) -&gt; System.out.println(percent + "% - " + message)
     * );
     *
     * future.whenComplete((path, ex) -&gt; {
     *     if (future.isCancelled()) {
     *         System.out.println("Report generation cancelled!");
     *     }
     *     else if (ex != null) {
     *         System.err.println("Report generation failed: " + ex.getMessage());
     *     } 
     *     else {
     *         System.out.println("Report complete! File saved at: " + path);
     *     }
     * });
     * </code></pre>
     * 
     * <p><b>Note:</b> This method requires that CallCenterStatistics event 
     * subscriptions be active. See {@link CallCenterStatisticsService class documentation}
     * for details and example setup.     
     *
     * @param context          the context defining the scope and filters for the statistics
     * @param range            the date range over which to collect statistics
     * @param format           the output format for the report file
     * @param directory        the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress; may be {@code null} if
     *                         progress updates are not needed
     * @return a {@link CompletableFuture} that completes with the {@link Path} to the generated
     *         report file, or exceptionally if an error occurs or the operation is cancelled
     * @see #cancelRequest(Context)
     */
    CompletableFuture<Path> getFileData(
            Context context,
            DateRange range,
            Format format,
            Path directory,
            ProgressCallback progressCallback);
    
    /**
     * Attempts to cancel an ongoing asynchronous statistics report generation for the specified
     * context.
     * <p>
     * Cancellation may succeed only if the server-side process has not already completed. The
     * method returns immediately and does not block until the process is fully terminated.
     *
     * @param context the {@link Context} identifying the report generation process to cancel
     * @return {@code true} if a running request was found and cancellation was successfully
     *         requested; {@code false} if there was no running process for the specified context
     *         or if it could not be cancelled
     */
    boolean cancelRequest(Context context);

    /**
     * Creates a new recurring scheduled report with the specified configuration.
     * <p>
     * The report is generated repeatedly according to the given {@code recurrence} pattern and
     * {@code observationPeriod}, formatted in the specified output format, and sent as a ZIP file
     * attachment to the provided recipients.
     *
     * @param context           the context defining which data and counters to include
     * @param id                a unique identifier for the scheduled report
     * @param description       a human-readable description of the report
     * @param observationPeriod the observation period over which statistics are collected
     * @param recurrence        the recurrence pattern for generating the report
     * @param format            the output format of the report
     * @param recipients        an array of email addresses to receive the report
     * @return the newly created {@link ScheduledReport}, or {@code null} on failure
     * @see #createScheduledReport(Context, String, String, ReportObservationPeriod, Format, String[])
     * @see #deleteScheduledReport(ScheduledReport)
     * @see #setScheduledReportEnabled(ScheduledReport, boolean)
     */
    ScheduledReport createScheduledReport(
            Context context,
            String id,
            String description,
            ReportObservationPeriod observationPeriod,
            Recurrence recurrence,
            Format format,
            String[] recipients);

    /**
     * Creates a new one-time scheduled report with the specified configuration.
     * <p>
     * Unlike the recurring overload, this report is generated only once for the specified
     * {@code observationPeriod} and is no longer active afterwards.
     *
     * @param context           the context defining which data and counters to include
     * @param id                a unique identifier for the scheduled report
     * @param description       a human-readable description of the report
     * @param observationPeriod the observation period over which statistics are collected
     * @param format            the output format of the report
     * @param recipients        an array of email addresses to receive the report
     * @return the newly created {@link ScheduledReport}, or {@code null} on failure
     * @see #createScheduledReport(Context, String, String, ReportObservationPeriod, Recurrence, Format, String[])
     * @see #deleteScheduledReport(ScheduledReport)
     */
    ScheduledReport createScheduledReport(
            Context context,
            String id,
            String description,
            ReportObservationPeriod observationPeriod,
            Format format,
            String[] recipients);    
    
    /**
     * Returns all scheduled reports associated with the specified context.
     *
     * @param context the context whose reports are retrieved
     * @return a collection of {@link ScheduledReport} objects, or {@code null} on failure
     */
    Collection<ScheduledReport> getScheduledReports(Context context);
    
    /**
     * Deletes the specified scheduled report.
     *
     * @param report the scheduled report to delete
     * @return {@code true} if the report was successfully deleted; {@code false} otherwise
     */
    boolean deleteScheduledReport(ScheduledReport report);

    /**
     * Enables or disables the specified scheduled report.
     *
     * @param report   the scheduled report to update
     * @param enabled  {@code true} to enable the report; {@code false} to disable it
     * @return {@code true} if the report state was successfully updated; {@code false} otherwise
     */
    boolean setScheduledReportEnabled(ScheduledReport report, boolean enabled);
    
    
    /**
     * Returns the scheduled report with the specified identifier.
     *
     * @param context          the statistics context that owns the report
     * @param scheduleReportId the unique identifier of the scheduled report to retrieve
     * @return the {@link ScheduledReport} corresponding to the specified ID, or {@code null} if
     *         no report exists with that ID
     */
    ScheduledReport getScheduledReport(Context context, String scheduleReportId);
    
    
    /**
     * Persists any changes made to the specified scheduled report.
     * <p>
     * Fields that can be updated include the description, observation period, recurrence
     * pattern, output format, and recipient list.
     *
     * @param report the {@link ScheduledReport} instance containing the updated fields
     * @return {@code true} if the update was successful; {@code false} if the report does not
     *         exist or the update could not be applied
     */
    boolean updateScheduledReport(ScheduledReport report);
}
