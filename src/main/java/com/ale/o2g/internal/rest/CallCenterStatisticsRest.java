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
package com.ale.o2g.internal.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.ale.o2g.CallCenterStatisticsService;
import com.ale.o2g.O2GRuntimeException;
import com.ale.o2g.events.ccstats.ProgressCallback;
import com.ale.o2g.events.ccstats.ProgressStep;
import com.ale.o2g.internal.events.ccstats.CallCenterStatisticsEventListener;
import com.ale.o2g.internal.events.ccstats.O2GProgressStep;
import com.ale.o2g.internal.events.ccstats.OnAcdStatsProgressEvent;
import com.ale.o2g.internal.types.ccstats.AgentFilterImpl;
import com.ale.o2g.internal.types.ccstats.ContextImpl;
import com.ale.o2g.internal.types.ccstats.PilotFilterImpl;
import com.ale.o2g.internal.types.ccstats.ReportFrequency;
import com.ale.o2g.internal.types.ccstats.RequesterImpl;
import com.ale.o2g.internal.types.ccstats.ScheduledReportImpl;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.CompressionUtil;
import com.ale.o2g.internal.util.FileUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.ccstats.AgentFilter;
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
 *
 */
public class CallCenterStatisticsRest extends AbstractRESTService implements CallCenterStatisticsService, CallCenterStatisticsEventListener {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");        
    
    // A helper class to retrieve statistics files
    private static class StatAsyncRequest {
        
        private Path directory;
        private ProgressCallback progressCallback;
        private CompletableFuture<Path> promise;
        
        protected StatAsyncRequest(Path directory, ProgressCallback progressCallback) {
            this.directory = directory;
            this.progressCallback = progressCallback;
            this.promise = new CompletableFuture<Path>();
        }

        public CompletableFuture<Path> getPromise() {
            return this.promise;
        }

        public void reportProgress(ProgressStep step, int nbObjects, int processedObject) {
            if (this.progressCallback != null) {
                
                int progress;
                if (nbObjects > 0) {
                    progress = (int)(100.0 * (float)processedObject / (float)nbObjects);
                }
                else {
                    progress = 0;
                }
                
                this.progressCallback.onProgress(step, progress);
            }
        }

        public Path getDirectory() {
            return this.directory;
        }
    }
        
    
    private static class StatsContexts {
        private Collection<ContextImpl> contexts;
    }

    private static class ScheduledReports {
        private Collection<ScheduledReportImpl> schedules;
    }

    
    private static record StatsSchedule(String name, String description, ReportObservationPeriod obsPeriod, ReportFrequency frequency, Format fileType, String[] recipients) {
        
    }
    
    
    private static record StatsFilter(AgentFilterImpl agentFilter, PilotFilterImpl pilotFilter) {
    }

    private static record Supervised(String number) {
    }

    private static record StatsScope(RequesterImpl supervisor, Supervised[] agents) {
    }

    private static record StatsContext(String supervisorId, String label, String description, StatsFilter filter) {
    }

    private static record RespId(String id) {
    }

    
    // Use to manage async file downloading
    private StatAsyncRequest currentAsyncRequest = null;    
    private final AtomicBoolean running = new AtomicBoolean(false);    
    
    // Constructor
    public CallCenterStatisticsRest(HttpClient httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public Requester createRequester(String id, Language language, ZoneOffset timezone, String[] agents) {
        
        URI uriPost = URIBuilder.appendPath(uri, "scope");

        Supervised[] supervised = Arrays.stream(AssertUtil.requireNotEmpty(agents, "agents")).map(Supervised::new)
                .toArray(Supervised[]::new);

        RequesterImpl requester = new RequesterImpl(id, language, timezone);
        
        StatsScope statsScope = new StatsScope(AssertUtil.requireNotNull(requester, "requester"), supervised);

        String json = gson.toJson(statsScope);

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        if (isSucceeded(response)) {
            return requester;
        }
        else {
            return null;
        }            
    }
    
    @Override
    public Requester createRequester(String id, Language language, String[] agents) {
        return this.createRequester(id, language, ZonedDateTime.now(ZoneId.systemDefault()).getOffset(), agents);
    }
    
    @Override
    public boolean deleteRequester(Requester requester) {
        
        String encoded = URLEncoder.encode(AssertUtil.requireNotNull(requester, "requester").getId(), StandardCharsets.UTF_8);
        URI uriDelete = URIBuilder.appendPath(uri, "scope", encoded);

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
         
        return isSucceeded(response);
    }

    @Override
    public Requester getRequester(String requesterId) {
        
        URI uriGet = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotEmpty(requesterId, "requesterId"), StandardCharsets.UTF_8));

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, RequesterImpl.class);
    }

    @Override
    public Context createContext(Requester requester, String label, String description, Filter filter) {

        URI uriPost = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(requester, "requester").getId(), StandardCharsets.UTF_8),
                "ctx");

        AssertUtil.requireNotNull(filter, "filter");

        StatsFilter statsFilter = null;
        if (filter instanceof AgentFilter) {
            statsFilter = new StatsFilter((AgentFilterImpl) filter, null);
        }
        else {
            statsFilter = new StatsFilter(null, (PilotFilterImpl) filter);
        }

        StatsContext statContext = new StatsContext(requester.getId(), label, description, statsFilter);

        String json = gson.toJson(statContext);

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        RespId respId = getResult(response, RespId.class);
        if (respId == null) {
            return null;
        }
        else {
            ContextImpl context = new ContextImpl(respId.id, requester.getId());
            context.setDescription(description);
            context.setLabel(label);
            context.setFilter(filter);
            
            return context;
        }
    }

    @Override
    public Collection<Context> getContexts(Requester requester) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(requester, "requester").getId(), StandardCharsets.UTF_8),
                "ctx");

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        StatsContexts contexts = getResult(response, StatsContexts.class);
        if ((contexts == null) || (contexts.contexts == null)) {
            return null;
        }
        else {
            return contexts.contexts.stream()
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean deleteContexts(Requester requester) {

        URI uriDelete = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(requester, "requester").getId(), StandardCharsets.UTF_8),
                "ctx");

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public Context getContext(Requester requester, String contextId) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(requester, "requester").getId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotEmpty(contextId, "contextId"));

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, ContextImpl.class);
    }

    @Override
    public boolean deleteContext(Context context) {
        
        URI uriDelete = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId());

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean updateContext(Context context) {

        URI uriPut = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId());

        Filter filter = context.getFilter();
        
        StatsFilter statsFilter = null;
        if (filter instanceof AgentFilter) {
            statsFilter = new StatsFilter((AgentFilterImpl) filter, null);
        }
        else {
            statsFilter = new StatsFilter(null, (PilotFilterImpl) filter);
        }

        StatsContext statContext = new StatsContext(context.getRequesterId(), context.getLabel(), context.getDescription(), statsFilter);

        String json = gson.toJson(statContext);

        HttpRequest request = HttpUtil.PUT(uriPut, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public StatisticsData getData(Context context, DateRange range) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "days/data"
                );

        AssertUtil.requireNotNull(range, "range");
        uriGet = URIBuilder.appendQuery(uriGet, "begindate", range.getFrom().format(DATE_FORMATTER));
        uriGet = URIBuilder.appendQuery(uriGet, "enddate", range.getTo().format(DATE_FORMATTER));
        uriGet = URIBuilder.appendQuery(uriGet, "format", "json");

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());            
        return getResult(response, StatisticsData.class);
    }

    
    private Path saveInDirectory(CompletableFuture<HttpResponse<byte[]>> response, Path directory) throws IOException {
        byte[] compressed = asByteArray(response);
        
        Map<String, byte[]> zipContent = CompressionUtil.unzip(compressed);
         
        // Ensure directory exist
        Files.createDirectories(directory);
        
        String fileName = zipContent.keySet().iterator().next();
        Path filePath = FileUtil.withTimestamp(directory, fileName);
        
        Files.write(filePath, zipContent.get(fileName));
        
        return filePath;
    }
    
    
    // Convenient method to adapt slot type
    private String getSlotType(TimeInterval timeInterval) {
        
        if (timeInterval == TimeInterval.HOUR) {
            return "anHour";
        }
        else if (timeInterval == TimeInterval.HALF_HOUR) {
            return "halfAnHour";
        }
        else {
            return "aQuarterOfAnHour";
        }
    }
    
    
    @Override
    public StatisticsData getData(Context context, LocalDate date, TimeInterval timeInterval) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "oneday/data"
                );

        
        uriGet = URIBuilder.appendQuery(uriGet, "date", AssertUtil.requireNotNull(date, "date").atStartOfDay().format(DATE_FORMATTER));
        if (timeInterval != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "slotType", getSlotType(timeInterval));            
        }
        uriGet = URIBuilder.appendQuery(uriGet, "format", "json");

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());            
        return getResult(response, StatisticsData.class);
    }

    @Override
    public StatisticsData getData(Context context, LocalDate date) {
        return this.getData(context, date, null);
    }

    @Override
    public StatisticsData getData(Context context) {
        return this.getData(context, LocalDate.now(), null);
    }


    @Override
    public boolean cancelRequest(Context context) {

        URI uriDelete = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "data/request");

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public CompletableFuture<Path> getFileData(Context context, DateRange range, Format format, Path directory,
            ProgressCallback progressCallback) {

        try {
            // Control the access
            if (!running.compareAndSet(false, true)) {
                throw new IllegalStateException("A statistic request is already in progress");
            }
            
            // There is a unique request in progress, we can go
            currentAsyncRequest = new StatAsyncRequest(
                    AssertUtil.requireNotNull(directory, "directory"), 
                    progressCallback);
            
            URI uriGet = URIBuilder.appendPath(
                    uri, 
                    "scope", 
                    URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                    "ctx",
                    AssertUtil.requireNotNull(context, "context").getId(),
                    "days/data"
                    );
    
            AssertUtil.requireNotNull(range, "range");
            uriGet = URIBuilder.appendQuery(uriGet, "begindate", range.getFrom().format(DATE_FORMATTER));
            uriGet = URIBuilder.appendQuery(uriGet, "enddate", range.getTo().format(DATE_FORMATTER));
            
            if (format == Format.CSV) {
                uriGet = URIBuilder.appendQuery(uriGet, "format", "csv");
            }
            else if (format == Format.EXCEL) {
                uriGet = URIBuilder.appendQuery(uriGet, "format", "xls");
            }
            uriGet = URIBuilder.appendQuery(uriGet, "async", "true");
            
            HttpRequest request = HttpUtil.GET(uriGet);
            CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

            CompletableFuture<Path> promise = currentAsyncRequest.getPromise();
            if (!isSucceeded(response)) {
                promise.completeExceptionally(new O2GRuntimeException("Error while getting statistics"));
            }
            
            return promise;
        }
        catch (Exception e) {
            CompletableFuture<Path> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalStateException("A statistic request is already in progress"));
            return failed;
        }
    }
    
    @Override
    public CompletableFuture<Path> getFileData(Context context, LocalDate date, TimeInterval timeInterval,
            Format format, Path directory, ProgressCallback progressCallback) {
        
        try {
            // Control the access
            if (!running.compareAndSet(false, true)) {
                throw new IllegalStateException("A statistic request is already in progress");
            }
            
            // There is a unique request in progress, we can go
            currentAsyncRequest = new StatAsyncRequest(
                    AssertUtil.requireNotNull(directory, "directory"), 
                    progressCallback);
    
            // First step is to send the request
            URI uriGet = URIBuilder.appendPath(
                    uri, 
                    "scope", 
                    URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                    "ctx",
                    AssertUtil.requireNotNull(context, "context").getId(),
                    "oneday/data"
                    );
            
            uriGet = URIBuilder.appendQuery(uriGet, "date", AssertUtil.requireNotNull(date, "date").atStartOfDay().format(DATE_FORMATTER));
            if (timeInterval != null) {
                uriGet = URIBuilder.appendQuery(uriGet, "slotType", getSlotType(timeInterval));            
            }
    
            if (format == Format.CSV) {
                uriGet = URIBuilder.appendQuery(uriGet, "format", "csv");
            }
            else if (format == Format.EXCEL) {
                uriGet = URIBuilder.appendQuery(uriGet, "format", "xls");
            }
            uriGet = URIBuilder.appendQuery(uriGet, "async", "true");
    
            HttpRequest request = HttpUtil.GET(uriGet);
            CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

            CompletableFuture<Path> promise = currentAsyncRequest.getPromise();
            if (!isSucceeded(response)) {
                promise.completeExceptionally(new O2GRuntimeException("Error while getting statistics"));
            }
            
            return promise;
        }
        catch (Exception e) {
            CompletableFuture<Path> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalStateException("A statistic request is already in progress"));
            return failed;
        }
    }

    @Override
    public CompletableFuture<Path> getFileData(Context context, LocalDate date, Format format, Path directory,
            ProgressCallback progressCallback) {        
        return this.getFileData(context, date, null, format, directory, progressCallback);
    }    
    
    @Override
    public CompletableFuture<Path> getFileData(Context context, Format format, Path directory,
            ProgressCallback progressCallback) {
        return this.getFileData(context, LocalDate.now(), null, format, directory, progressCallback);
    }    
    
    private void downloadFile(String fileUri, Path directory, CompletableFuture<Path> promise) {
        
        URI uriGet = URIBuilder.appendPath(URIBuilder.getBaseUri(uri), fileUri);
        HttpRequest request = HttpUtil.GET(uriGet);
        
        try {
            Path filePath = saveInDirectory(httpClient.sendAsync(request, BodyHandlers.ofByteArray()), directory);
            promise.complete(filePath);
        }
        catch (IOException e) {
            // Error, exception in promise
            promise.completeExceptionally(e);
        }
    }
    
    
    @Override
    public void onAcdStatsProgress(OnAcdStatsProgressEvent e) {
        
        O2GProgressStep step = e.getStep();
        if (step == O2GProgressStep.COLLECT) {
            if (currentAsyncRequest != null) {
                currentAsyncRequest.reportProgress(ProgressStep.COLLECTING, e.getNbTotObjects(), e.getNbProcessedObjects());
            }        
        }
        else if (step == O2GProgressStep.PROCESSED) {
            if (currentAsyncRequest != null) {
                // Force a 100% value
                currentAsyncRequest.reportProgress(ProgressStep.PROCESSED, 1, 1);
            }                    
        }        
        else if (step == O2GProgressStep.FORMATED) {
            if (currentAsyncRequest != null) {
                // Force a 100% value
                currentAsyncRequest.reportProgress(ProgressStep.FORMATED, 1, 1);
                
                downloadFile(e.getFullResPath(), currentAsyncRequest.getDirectory(), currentAsyncRequest.getPromise());
                
                running.set(false);
            }        
        }
        else if (step == O2GProgressStep.CANCELLED) {

            if (currentAsyncRequest != null) {
                currentAsyncRequest.getPromise().cancel(true);
                
                currentAsyncRequest = null;
            }
                        
            running.set(false);
        }
        else if (step == O2GProgressStep.ERROR) {

            if (currentAsyncRequest != null) {
                currentAsyncRequest.getPromise().completeExceptionally(new O2GRuntimeException("Error while getting statistics"));
                
                currentAsyncRequest = null;
            }

            running.set(false);
        }
    }

    @Override
    public ScheduledReport createScheduledReport(
                Context context, 
                String id, 
                String description,
                ReportObservationPeriod observationPeriod, 
                Recurrence recurrence, 
                Format format,
                String[] recipients) {
        

        URI uriPost = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "schedule");

        if ((recipients == null) || (recipients.length < 1)) {
            throw new IllegalArgumentException("'recipients' must be non null and not empty");
        }
        
        StatsSchedule statSchedule = new StatsSchedule(
                AssertUtil.requireNotEmpty(id, "id"),
                description,
                AssertUtil.requireNotNull(observationPeriod, "observationPeriod"),
                ReportFrequency.from(AssertUtil.requireNotNull(recurrence, "recurrence")),
                AssertUtil.requireNotNull(format, "format"),
                recipients
                );

        String json = gson.toJson(statSchedule);

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        RespId respId = getResult(response, RespId.class);
        if (respId == null) {
            return null;
        }
        else {
            return new ScheduledReportImpl(context, respId.id, description, observationPeriod, recurrence, format, recipients);
        }
    }


    @Override
    public ScheduledReport createScheduledReport(Context context, String id, String description,
            ReportObservationPeriod observationPeriod, Format format, String[] recipients) {
        
        URI uriPost = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "schedule");

        if ((recipients == null) || (recipients.length < 1)) {
            throw new IllegalArgumentException("'recipients' must be non null and not empty");
        }
        
        StatsSchedule statSchedule = new StatsSchedule(
                AssertUtil.requireNotEmpty(id, "id"),
                description,
                AssertUtil.requireNotNull(observationPeriod, "observationPeriod"),
                ReportFrequency.asOnce(),
                AssertUtil.requireNotNull(format, "format"),
                recipients
                );

        String json = gson.toJson(statSchedule);

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        RespId respId = getResult(response, RespId.class);
        if (respId == null) {
            return null;
        }
        else {
            return new ScheduledReportImpl(context, respId.id, description, observationPeriod, null, format, recipients);
        }
    }    

    @Override
    public boolean deleteScheduledReport(ScheduledReport report) {

        Context context = AssertUtil.requireNotNull(report, "report").getContext();
        
        URI uriDelete = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "schedule",
                URLEncoder.encode(report.getId(), StandardCharsets.UTF_8));

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }
    
    @Override
    public Collection<ScheduledReport> getScheduledReports(Context context) {
        
        URI uriGet = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "schedule");

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        ScheduledReports schedules = getResult(response, ScheduledReports.class);
        if ((schedules == null) || (schedules.schedules == null)) {
            return null;
        }
        else {
            schedules.schedules.forEach(report -> report.setContext(context));

            return schedules.schedules.stream()
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean setScheduledReportEnabled(ScheduledReport report, boolean enabled) {

        Context context = AssertUtil.requireNotNull(report, "report").getContext();
        
        URI uriPost = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "schedule",
                URLEncoder.encode(report.getId(), StandardCharsets.UTF_8),
                "enable");
        
        uriPost = URIBuilder.appendQuery(uriPost, "enable", String.valueOf(enabled));

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public ScheduledReport getScheduledReport(Context context, String scheduleReportId) {
        URI uriGet = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "schedule",
                URLEncoder.encode(AssertUtil.requireNotEmpty(scheduleReportId, "scheduleReportId"), StandardCharsets.UTF_8));
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        ScheduledReportImpl report = getResult(response, ScheduledReportImpl.class);
        if (report == null) {
            return null;
        }
        
        report.setContext(context);
        return report;
    }

    @Override
    public boolean updateScheduledReport(ScheduledReport report) {
        Context context = AssertUtil.requireNotNull(report, "report").getContext();
        
        URI uriPut = URIBuilder.appendPath(
                uri, 
                "scope", 
                URLEncoder.encode(AssertUtil.requireNotNull(context, "context").getRequesterId(), StandardCharsets.UTF_8),
                "ctx",
                AssertUtil.requireNotNull(context, "context").getId(),
                "schedule",
                URLEncoder.encode(report.getId(), StandardCharsets.UTF_8));

        ReportFrequency frequency;
        if (report.isOnce()) {
            frequency = ReportFrequency.asOnce();
        }
        else {
            frequency = ReportFrequency.from(report.getRecurrence());
        }
        
        Collection<String> recipients = report.getRecipients();
        if ((recipients == null) || (recipients.size() < 1)) {
            throw new IllegalArgumentException("'recipients' must be non null and not empty");
        }
        
        StatsSchedule statSchedule = new StatsSchedule(
                report.getId(),
                report.getDescription(),
                report.getObservationPeriod(),
                frequency,
                report.getFormat(),
                recipients.toArray(new String[0])
                );
        
        String json = gson.toJson(statSchedule);
        
        HttpRequest request = HttpUtil.PUT(uriPut, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }
}
