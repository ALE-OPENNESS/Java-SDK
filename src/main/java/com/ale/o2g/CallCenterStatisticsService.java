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
 * Service for retrieving statistics about CCD agents and pilots.
 * <p>
 * This service is intended for administrators and provides two modes of data retrieval:
 * <ul>
 *   <li><b>Immediate reports</b> - statistics retrieved on-demand. They can be returned as data or exported as CSV or Excel files.</li>
 *   <li><b>Scheduled reports</b> - recurring statistics sent as email attachments to predefined recipients.</li>
 * </ul>
 *
 * <h2>Requesters</h2>
 * <p>
 * Data is retrieved by a {@code Requester}. A requester:
 * <ul>
 *   <li>Has a unique identifier.</li>
 *   <li>Is associated with a scope of agents (e.g., a team leader's requester covers all agents in the team).</li>
 *   <li>Defines a <i>context</i> describing which CCD objects and counters to include in reports.</li>
 * </ul>
 *
 * <h2>Retrieving Statistics</h2>
 * <p>
 * Retrieving statistics requires creating a requester, defining a statistical context,
 * and then querying data for that context. The following example illustrates the process:
 * </p>
 *
 * <pre>{@code  
 * // Get the CallCenterStatisticsService from the opened session
 * CallCenterStatisticsService statService = session.getCallCenterStatisticsService();
 *
 * // Create a requester
 * Requester requester = statService.createRequester(
 *         "John Doe",
 *         Language.EN,
 *         new String[] { "60118", "60119", "60117", "60116", "60115", "60114" });
 *
 * // Create a filter with all agent attributes
 * AgentFilter filter = Filter.createAgentFilter();
 * filter.setAgentAttributes(AgentAttributes.ALL);
 *
 * // Create a statistic context
 * Context context = statService.createContext(requester, "AgentStatContext", "Agent Statistics", filter);
 *
 * // Retrieve statistics as JSON
 * LocalDateTime startDate = LocalDateTime.of(2025, 10, 1, 0, 0);
 * DateRange range = new DateRange(startDate, startDate.plusDays(4));
 * StatisticsData statistics = statService.getJsonData(context, range);
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
 *       <li>May cross months (e.g., from 15/04/2024 to 14/05/2024).</li>
 *       <li>Data spans from 00:00 of the first day to 24:00 of the last day.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Scheduling</h2>
 * <p>
 * Scheduling applies only to multi-day reports:
 * <ul>
 *   <li>Each schedule is linked to a specific report.</li>
 *   <li>There is no limitation on the number of schedules per report.</li>
 *   <li>Reports are delivered as ZIP file attachments via email.</li>
 * </ul>
 *
 * <h3>Scheduling Rules</h3>
 * <p>
 * Scheduled reports must comply with specific rules depending on the {@link ReportObservationPeriod.PeriodType}:
 * </p>
 * <ul>
 *   <li><b>Current Day:</b> Recurrence can only be <b>daily</b>. 
 *       Each run collects data from the previous day.</li>
 *   <li><b>Current Week:</b> Recurrence can only be <b>weekly</b>.
 *       Example: recurrence on Monday and Thursday:
 *       <ul>
 *         <li>Every Tuesday, collect Monday's data.</li>
 *         <li>Every Thursday, collect data from Monday to Wednesday.</li>
 *       </ul>
 *   </li>
 *   <li><b>Current Month:</b> Recurrence can only be <b>monthly</b>.
 *       Example: on the 12th of each month, collect data from the 1st 00:00 to the 11th 24:00.</li>
 *   <li><b>Last N Days:</b> Recurrence can be <b>daily</b>, <b>weekly</b>, or <b>monthly</b>:
 *       <ul>
 *         <li>Daily: every day, collect the N previous days.</li>
 *         <li>Weekly: every selected day, collect the N previous days.</li>
 *         <li>Monthly: on the selected day of each month, collect the N previous days.</li>
 *       </ul>
 *   </li>
 *   <li><b>Last N Weeks:</b> Recurrence can only be <b>weekly</b>.
 *       Example: for recurrence on Monday and Thursday, every Tuesday and Thursday collect data from the N past weeks (Monday 00:00 to Sunday 24:00).</li>
 *   <li><b>Last Month:</b> Recurrence can only be <b>monthly</b>.
 *       Example: on the 12th of each month, collect data for the previous month (from the 1st 00:00 to the last day 24:00).</li>
 *   <li><b>From Date to Date:</b> Recurrence is not allowed; such reports can only be scheduled once.</li>
 *   <li><b>One-time reports:</b> Any observation period can be used for a single scheduled execution.</li>
 * </ul>
 * 
 * <h2>Using Asynchronous Statistics Query Methods</h2>
 * <p>
 * To use asynchronous methods, it is necessary to subscribe to <b>CallCenterStatistics events</b> 
 * before invoking them. Without this subscription, progress notifications and completion callbacks 
 * will not be received.
 * </p>
 * <p>
 * The asynchronous methods include:
 * <ul>
 *   <li>{@link #getFileData(Context, Format, Path, ProgressCallback)}</li>
 *   <li>{@link #getFileData(Context, DateRange, Format, Path, ProgressCallback)}</li>
 *   <li>{@link #getFileData(Context, LocalDate, Format, Path, ProgressCallback)}</li>
 *   <li>{@link #getFileData(Context, LocalDate, TimeInterval, Format, Path, ProgressCallback)}</li>
 * </ul>
 * <p><b>Example of subscription:</b>
 * <pre><code>
 * Subscription subscription = Subscription.newBuilder()
 *       .addCallCenterStatisticsEventListener()
 *       .build();
 * session.listenEvents(subscription);
 * </code></pre>
 * 
 * <p>
 * This subscription only needs to be established once per session, and remains
 * valid for all subsequent asynchronous operations.
 * </p>
 * <p>
 * Using this service requires a <b>CONTACTCENTER_SERVICE</b> license in CAPEX mode,
 * or a 40 api-tel-f subscription in OPEX mode (Purple On Demand).
 * </p>
 *
 * @since 2.7.4
 */
public interface CallCenterStatisticsService extends IService {

    /**
     * Creates a new {@code Requester} with the specified identifier, language, and time zone,
     * and establishes the statistics scope defining which agents' data the requester is authorized to access.
     * <p>
     * This method determines the set of agents whose statistics can be retrieved by the specified requester.
     * Once the scope is created, the requester can query individual or aggregated statistics for those agents
     * through the reporting services.
     *
     * @param id        the unique identifier of the requester (e.g., supervisor ID);
     *                  must not be {@code null} or empty
     * @param language  the requester's preferred {@link Language}; must not be {@code null}
     * @param timezone  the requester's time zone as a {@link ZoneOffset}; must not be {@code null}
     * @param agents    an array of agent identifiers that define the scope of accessible statistics;
     *                  must not be {@code null} or empty
     * @return the newly created {@code Requester} instance
     *
     */
    Requester createRequester(String id, Language language, ZoneOffset timezone, String[] agents);

    /**
     * Creates a new {@code Requester} with the specified identifier and language,
     * using the system's default time zone offset, and establishes the statistics scope
     * defining which agents' data the requester is authorized to access.
     * <p>
     * This method determines the set of agents whose statistics can be retrieved by the specified requester.
     * Once the scope is created, the requester can query individual or aggregated statistics
     * for those agents through the reporting services.
     *
     * @param id        the unique identifier of the requester (e.g., supervisor ID);
     *                  must not be {@code null} or empty
     * @param language  the requester's preferred {@link Language}; must not be {@code null}
     * @param agents    an array of agent identifiers that define the scope of accessible statistics;
     *                  must not be {@code null} or empty
     * @return the newly created {@code Requester} instance
     *
     */
    Requester createRequester(String id, Language language, String[] agents);

    
    /**
     * Removes the statistics scope associated with a given requester.
     * <p>
     * After calling this method, the specified requester will no longer have access
     * to any agent statistics defined under their scope.
     *
     * @param requester the requester whose statistics scope should be removed
     * @return {@code true} if the scope was successfully removed; {@code false} otherwise
     */
    boolean deleteRequester(Requester requester);

    /**
     * Retrieves the requester associated with the specified identifier.
     * <p>
     * The returned requester represents the scope of agents for which statistics
     * can be accessed. If no requester exists with the given ID, this method
     * returns {@code null}.
     *
     * @param id the unique identifier of the requester
     * @return the {@link Requester} object corresponding to the ID, or {@code null}
     *         if no matching requester is found
     */
    Requester getRequester(String id);


    /**
     * Creates a new statistical context with the specified label, description, and filter
     * for the given requester.
     * <p>
     * A context defines a scope for which call center statistics can be collected and analyzed
     * according to the specified filter criteria.
     *
     * @param requester  the requester for whom the context is being created
     * @param label      a short label identifying this context
     * @param description a detailed description of the context
     * @param filter     the filter defining the selection criteria for the context
     * @return the created {@link Context} if successful; {@code null} otherwise
     */
    Context createContext(Requester requester, String label, String description, Filter filter);


    /**
     * Retrieves the statistical contexts created for the specified requester.
     * <p>
     * Each context defines a scope for collecting and analyzing call center statistics
     * according to its associated filter criteria.
     *
     * @param requester the requester whose contexts are being retrieved
     * @return a {@link Collection} of {@link Context} objects if successful; {@code null} 
     *         if there is an error or if no contexts exist for this requester
     * @see #createContext(Requester, String, String, Filter)
     */
    Collection<Context> getContexts(Requester requester);


    /**
     * Retrieves a statistical context by its identifier for the specified requester.
     * <p>
     * A context defines a scope for collecting and analyzing call center statistics
     * according to its associated filter criteria.
     *
     * @param requester the requester who owns the context
     * @param contextId the unique identifier of the context
     * @return the {@link Context} if found and retrieval is successful; {@code null} 
     *         if there is an error or if no context exists with the specified identifier
     */
    Context getContext(Requester requester, String contextId);

    /**
     * Deletes all statistical contexts associated with the specified requester.
     * <p>
     * A context defines a scope for collecting and analyzing call center statistics 
     * according to its associated filter criteria.
     *
     * @param requester the requester whose contexts should be deleted
     * @return {@code true} if all contexts were successfully deleted; {@code false} 
     *         if an error occurred or no contexts were deleted
     * @see #createContext(Requester, String, String, Filter)
     */
    boolean deleteContexts(Requester requester);

    /**
     * Deletes the specified statistical context.
     * <p>
     * A context defines a scope for collecting and analyzing call center statistics
     * according to its associated filter criteria.
     *
     * @param context the context to be deleted
     * @return {@code true} if the context was successfully deleted; {@code false} 
     *         if an error occurred or the context could not be deleted
     * @see #createContext(Requester, String, String, Filter)
     */
    boolean deleteContext(Context context);


    /**
     * Updates the specified statistical context.
     * <p>
     * A context defines a scope for collecting and analyzing call center statistics.
     * Any changes to the context's label, description, or filter will be applied
     * when this method is called.
     *
     * @param context the context with updated parameters to be applied
     * @return {@code true} if the context was successfully updated; {@code false} 
     *         if an error occurred or the update could not be applied
     * @see #createContext(Requester, String, String, Filter)
     */
    boolean updateContext(Context context);
    
    /**
     * Retrieves statistical data for the specified context.
     * <p>
     * This method generates a multi-day report corresponding to the time range
     * defined by the provided {@link DateRange}. It allows reporting and analysis
     * of call center metrics across multiple days within the selected period.
     * <p>
     * The returned {@link StatisticsData} object contains the aggregated data, suitable for further processing or integration.
     *
     * @param context the context defining the scope and filters for the statistics
     * @param range the date range over which to collect statistics
     * @return a {@link StatisticsData} object containing the data, or
     *         {@code null} if the data could not be retrieved
     */
    StatisticsData getData(Context context, DateRange range);
    
    /**
     * Retrieves statistical data for the specified context for a single day.
     * <p>
     * The statistics are provided in time slots according to the {@link TimeInterval} parameter.
     *
     * @param context the context defining the scope and filters for the statistics
     * @param date the specific day for which to collect statistics
     * @param timeInterval the time slot interval for reporting (e.g., 15 or 30 minutes)
     * @return a {@link StatisticsData} object containing the data, or
     *         {@code null} if the data could not be retrieved
     */
    StatisticsData getData(Context context, LocalDate date, TimeInterval timeInterval);
    
    /**
     * Retrieves statistical data for the specified context for a single day.
     * <p>
     * The statistics are reported using default 15-minute intervals (quarter-hour slots) 
     * spanning from 00:00 until the last completed interval of the day.
     *
     * @param context the context defining the scope and filters for the statistics
     * @param date the specific day for which to collect statistics
     * @return a {@link StatisticsData} object containing the data, or
     *         {@code null} if the data could not be retrieved
     */
    StatisticsData getData(Context context, LocalDate date);

    /**
     * Retrieves statistical data for the specified context for the current day.
     * <p>
     * The statistics are reported using default 15-minute intervals (quarter-hour slots) 
     * spanning from 00:00 until the last completed interval of the current day.
     *
     * @param context the context defining the scope and filters for the statistics
     * @return a {@link StatisticsData} object containing the data, or
     *         {@code null} if the data could not be retrieved
     */
    StatisticsData getData(Context context);

    /**
     * Asynchronously retrieves statistical data for the specified context for a single day 
     * and stores it as a report file in the given directory.
     * <p>
     * The statistics are reported in time slots defined by the {@link TimeInterval} 
     * parameter (e.g., 15-minute or 30-minute intervals), spanning from 00:00 until the 
     * last completed interval of the specified day.
     * <p>
     * The output file format is determined by the {@link Format} parameter (e.g., CSV, XLS),
     * and the generated report file is saved in the specified directory.
     *
     * <p>
     * Progress updates during the report generation are reported to the provided 
     * {@link ProgressCallback} instance.
     * </p>
     *
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active 
     * at a time. Any attempt to start another request while one is already in progress 
     * will result in the returned {@link CompletableFuture} being completed exceptionally 
     * with an {@link IllegalStateException}.
     * </p>
     *
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the 
     * ongoing operation. The future will:
     * </p>
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file when the 
     *       operation succeeds.</li>
     *   <li>Complete exceptionally if an error occurs before or during processing, including 
     *       I/O errors, service errors, or {@link IllegalStateException} if another request 
     *       is already in progress.</li>
     *   <li>Complete exceptionally with a {@link java.util.concurrent.CancellationException CancellationException} 
     *       if the operation is cancelled.</li>
     * </ul>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * CompletableFuture<Path> future = fileService.getFileData(
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
     *     }
     *     else if (ex != null) {
     *         System.err.println("Download failed: " + ex.getMessage());
     *     } 
     *     else {
     *         System.out.println("Download complete! File saved at: " + path);
     *     }
     * });
     * }</pre>
     * 
     * <p><b>Note:</b> This method requires that CallCenterStatistics event 
     * subscriptions be active. See {@link CallCenterStatisticsService class documentation}
     * for details and example setup.     
     *
     * @param context the context defining the scope and filters for the statistics
     * @param date the date for which to generate the report
     * @param timeInterval the length of each reporting interval (slot) within the day
     * @param format the output format for the report file
     * @param directory the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress of the operation; may be 
     *        {@code null} if progress updates are not needed
     * @return a {@link CompletableFuture} representing the asynchronous operation. The 
     *         future completes with the {@link Path} to the generated report file, or 
     *         exceptionally if an error occurs or the operation is cancelled
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
     * Asynchronously retrieves statistical data for the specified context for a single day 
     * and stores it as a report file in the given directory.
     * <p>
     * By default, the statistics are reported in 15-minute intervals (quarter-hour slots),
     * spanning from 00:00 until the last completed interval of the specified day.
     * <p>
     * The output file format is determined by the {@link Format} parameter (e.g., CSV, XLS),
     * and the generated report file is saved in the specified directory.
     * <p>
     * Progress updates during the report generation are reported to the provided 
     * {@link ProgressCallback} instance.
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active 
     * at a time. Any attempt to start another request while one is already in progress 
     * will result in the returned {@link CompletableFuture} being completed exceptionally 
     * with an {@link IllegalStateException}.
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the 
     * ongoing operation. The future will:
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file when the 
     *       operation succeeds.</li>
     *   <li>Complete exceptionally if an error occurs before or during processing, including 
     *       I/O errors, service errors, or {@link IllegalStateException} if another request 
     *       is already in progress.</li>
     *   <li>Complete exceptionally with a {@link java.util.concurrent.CancellationException CancellationException} 
     *       if the operation is cancelled.</li>
     * </ul>
     *
     * <p><b>Example:</b></p>
     * <pre><code>
     * CompletableFuture&lt;Path&gt; future = fileService.getFileData(
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
     *
     * @param context the context defining the scope and filters for the statistics
     * @param date the date for which to generate the report
     * @param format the output format for the report file
     * @param directory the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress of the operation; may be 
     *        {@code null} if progress updates are not needed
     * @return a {@link CompletableFuture} representing the asynchronous operation. The 
     *         future completes with the {@link Path} to the generated report file, or 
     *         exceptionally if an error occurs, the operation is cancelled, or another 
     *         request is already in progress
     * @see #cancelRequest(Context)
     */
    CompletableFuture<Path> getFileData(
            Context context,
            LocalDate date,
            Format format,
            Path directory,
            ProgressCallback progressCallback);
    
    
    /**
     * Asynchronously retrieves statistical data for the specified context for the current day 
     * and stores it as a report file in the given directory.
     * <p>
     * By default, the statistics are reported in 15-minute intervals (quarter-hour slots),
     * spanning from 00:00 until the last completed interval of the current day.
     * <p>
     * The output file format is determined by the {@link Format} parameter (e.g., CSV, XLS),
     * and the generated report file is saved in the specified directory.
     * <p>
     * Progress updates during the report generation are reported to the provided 
     * {@link ProgressCallback} instance.
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active 
     * at a time. Any attempt to start another request while one is already in progress 
     * will result in the returned {@link CompletableFuture} being completed exceptionally 
     * with an {@link IllegalStateException}.
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the 
     * ongoing operation. The future will:
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file when the 
     *       operation succeeds.</li>
     *   <li>Complete exceptionally if an error occurs before or during processing, including 
     *       I/O errors, service errors, or {@link IllegalStateException} if another request 
     *       is already in progress.</li>
     *   <li>Complete exceptionally with a {@link java.util.concurrent.CancellationException CancellationException} 
     *       if the operation is cancelled.</li>
     * </ul>
     *
     * <p><b>Example:</b></p>
     * <pre><code>
     * CompletableFuture&lt;Path&gt; future = fileService.getFileData(
     *     context,
     *     Format.EXCEL,
     *     Paths.get("downloads"),
     *     (percent, message) -&gt; System.out.println(percent + "% - " + message)
     * );
     *
     * future.whenComplete((path, ex) -&gt; {
     *     if (future.isCancelled()) {
     *         System.out.println("Download cancelled!");
     *     } 
     *     else if (ex != null) {
     *         System.err.println("Download failed: " + ex.getMessage());
     *     } 
     *     else {
     *         System.out.println("Download complete! File saved at: " + path);
     *     }
     * });
     * </code></pre>
     * 
     * <p><b>Note:</b> This method requires that CallCenterStatistics event 
     * subscriptions be active. See {@link CallCenterStatisticsService class documentation}
     * for details and example setup.     
     *
     * @param context the context defining the scope and filters for the statistics
     * @param format the output format for the report file
     * @param directory the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress of the operation; may be 
     *        {@code null} if progress updates are not needed
     * @return a {@link CompletableFuture} representing the asynchronous operation. The 
     *         future completes with the {@link Path} to the generated report file, or 
     *         exceptionally if an error occurs or the operation is cancelled
     * @see #cancelRequest(Context)
     */
    CompletableFuture<Path> getFileData(
            Context context,
            Format format,
            Path directory,
            ProgressCallback progressCallback);
    
    /**
     * Asynchronously retrieves statistical data for the specified context and stores it as a 
     * report file in the given directory.
     * <p>
     * This method generates a multi-day report corresponding to the period defined by the 
     * provided {@link DateRange}, and filtered according to the specified {@link Context}. 
     * The output file format is determined by the {@link Format} parameter (e.g., CSV, XLS).
     * <p>
     * The generated report file is saved in the specified directory. Progress updates during 
     * report generation are reported to the provided {@link ProgressCallback} instance.
     * <p>
     * <b>Concurrency limitation:</b> Only one report generation request can be active 
     * at a time. Any attempt to start another request while one is already in progress 
     * will result in the returned {@link CompletableFuture} being completed exceptionally 
     * with an {@link IllegalStateException}.
     * <p>
     * The method returns immediately with a {@link CompletableFuture} representing the 
     * ongoing operation. The future will:
     * <ul>
     *   <li>Complete normally with the {@link Path} to the created report file when the 
     *       operation succeeds.</li>
     *   <li>Complete exceptionally if an error occurs before or during processing, including 
     *       I/O errors, service errors, or {@link IllegalStateException} if another request 
     *       is already in progress.</li>
     *   <li>Complete exceptionally with a {@link java.util.concurrent.CancellationException CancellationException} 
     *       if the operation is cancelled.</li>
     * </ul>
     *
     * <p><b>Example:</b></p>
     * <pre><code>
     * CompletableFuture&lt;Path&gt; future = fileService.getFileData(
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
     * @param context the context defining the scope and filters for the statistics
     * @param range the date range over which to collect statistics
     * @param format the output format for the report file
     * @param directory the directory in which to save the generated report file
     * @param progressCallback a callback invoked to report progress of the operation; may be 
     *        {@code null} if progress updates are not needed
     * @return a {@link CompletableFuture} representing the asynchronous operation. The 
     *         future completes with the {@link Path} to the generated report file, or 
     *         exceptionally if an error occurs, the operation is cancelled, or another 
     *         request is already in progress
     * @see #cancelRequest(Context)
     */
    CompletableFuture<Path> getFileData(
            Context context,
            DateRange range,
            Format format,
            Path directory,
            ProgressCallback progressCallback);
    
    /**
     * Attempts to cancel an ongoing statistics report generation for the specified context.
     * <p>
     * If a report generation process for the given {@link Context} is currently running,
     * this method will attempt to stop it. Cancellation may succeed only if the process
     * has not already completed.
     * <p>
     * The method returns immediately and does not block until the process is fully terminated.
     *
     * @param context the {@link Context} identifying the report generation process to cancel
     * @return {@code true} if a running report generation was found and successfully
     *         requested to be cancelled, {@code false} if there was no running process
     *         for the specified context or if it could not be cancelled
     */
    boolean cancelRequest(Context context);

    /**
     * Creates a new scheduled report with the specified configuration.
     * <p>
     * The scheduled report will be generated according to the given recurrence and observation period,
     * formatted in the specified output format, and sent to the provided recipients.
     *
     * @param context the context defining which data and counters to include in the report
     * @param id a unique identifier for the scheduled report
     * @param description a human-readable description of the report
     * @param observationPeriod the period over which statistics are collected
     * @param recurrence the recurrence pattern for generating the report; may be null for one-time reports
     * @param format the output format of the report
     * @param recipients an array of email addresses to receive the report
     * @return a {@link ScheduledReport} representing the newly created scheduled report
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
     * Creates a one-time scheduled report with the specified configuration.
     * <p>
     * The report will be generated once for the given observation period,
     * formatted in the specified output format, and sent to the provided recipients.
     *
     * @param context the context defining which data and counters to include in the report
     * @param id a unique identifier for the scheduled report
     * @param description a human-readable description of the report
     * @param observationPeriod the period over which statistics are collected
     * @param format the output format of the report
     * @param recipients an array of email addresses to receive the report
     * @return a {@link ScheduledReport} representing the newly created scheduled report
     */
    ScheduledReport createScheduledReport(
            Context context, 
            String id, 
            String description, 
            ReportObservationPeriod observationPeriod, 
            Format format,
            String[] recipients);    
    
    /**
     * Returns all scheduled reports associated with the given context.
     *
     * @param context the context defining which reports to retrieve
     * @return a collection of {@link ScheduledReport} objects for the specified context;
     *         may be empty if no reports exist
     */
    Collection<ScheduledReport> getScheduledReports(Context context);
    
    /**
     * Deletes the specified scheduled report.
     *
     * @param report the scheduled report to delete
     * @return {@code true} if the report was successfully deleted, {@code false} otherwise
     */
    boolean deleteScheduledReport(ScheduledReport report);

    /**
     * Enables or disables the specified scheduled report.
     *
     * @param report the scheduled report to update
     * @param enabled {@code true} to enable the report, {@code false} to disable it
     * @return {@code true} if the report state was successfully updated, {@code false} otherwise
     */
    boolean setScheduledReportEnabled(ScheduledReport report, boolean enabled);
    
    
    /**
     * Retrieves a previously created scheduled report by its unique identifier.
     * <p>
     * This method requires a valid {@link Context} to perform the retrieval operation.
     * The {@code scheduleReportId} must correspond to an existing scheduled report.
     *
     * @param context the session context used to access the service
     * @param scheduleReportId the unique identifier of the scheduled report to retrieve
     * @return the {@link ScheduledReport} corresponding to the specified ID, 
     *         or {@code null} if no report exists with that ID
     */
    ScheduledReport getScheduledReport(Context context, String scheduleReportId);
    
    
    /**
     * Updates the configuration of an existing scheduled report.
     * <p>
     * This method persists any changes made to the provided {@link ScheduledReport} instance,
     * including its description, observation period, recurrence, format, and recipients.
     * <p>
     * Implementations should ensure that the report exists and that the updates are valid.
     *
     * @param report the {@link ScheduledReport} instance containing the updated information
     * @return {@code true} if the update was successful; {@code false} if the report does not exist
     *         or the update could not be applied
     */    
    boolean updateScheduledReport(ScheduledReport report);
}
