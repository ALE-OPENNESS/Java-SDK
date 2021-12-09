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
package com.ale.o2g.internal.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ale.o2g.AnalyticsService;
import com.ale.o2g.internal.types.analytics.O2GCharging;
import com.ale.o2g.internal.types.analytics.O2GChargingFile;
import com.ale.o2g.internal.types.analytics.O2GIncident;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.analytics.ChargingFile;
import com.ale.o2g.types.analytics.ChargingResult;
import com.ale.o2g.types.analytics.Incident;
import com.ale.o2g.types.analytics.TimeRange;

/**
 *
 */
public class AnalyticsRest extends AbstractRESTService implements AnalyticsService {

    class O2GIncidents {
        private Collection<O2GIncident> incidents;

        public Collection<Incident> toIncidents() {
            if (incidents == null) {
                return null;
            }
            else {
                return incidents.stream().map(inc -> inc.toIncident()).collect(Collectors.toList());
            }
        }
    }
    
    class O2GChargingResult {
        public Collection<O2GCharging> chargings;
        public String fromDate;
        public String toDate;
        public int nbChargingFiles;
        public int totalTicketNb;
        public int valuableTicketNb;

        public ChargingResult toChargingResult() {
            TimeRange range = null;
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            
            if ((fromDate != null) && (toDate != null)) {
                try {
                    range = new TimeRange(formatter.parse(fromDate), formatter.parse(toDate));
                }
                catch (ParseException e) {
                    range = null;
                }
            }

            return new ChargingResult(
                    chargings.stream().map(c -> c.toCharging()).collect(Collectors.toList()),
                    range,
                    nbChargingFiles,
                    totalTicketNb,
                    valuableTicketNb) {};
        } 
    }

    
    class ChargingFileList {
        private Collection<O2GChargingFile> files;

        public Collection<ChargingFile> toChargingFiles() {
            
            return files.stream().map(f -> new ChargingFile(f.getName(), f.getTimeStamp()) {
                
            }).collect(Collectors.toList());
        }
    }
    
    
    public AnalyticsRest(HttpClient httpClient, URI uri) {
        super(httpClient, uri);
    }


    @Override
    public Collection<Incident> getIncidents(int nodeId, int last) {

        URI uriGet = URIBuilder.appendPath(uri, "incidents");
        uriGet = URIBuilder.appendQuery(uriGet, "nodeId", String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")));

        if (last > 0) {
            uriGet = URIBuilder.appendQuery(uriGet, "last", String.valueOf(last));
        }
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GIncidents o2gIncidents = getResult(response, O2GIncidents.class);
        if (o2gIncidents == null) {
            return null;
        }
        else {
            return o2gIncidents.toIncidents();
        }
    }

    @Override
    public Collection<Incident> getIncidents(int nodeId) {
        return this.getIncidents(nodeId, 0);
    }


    @Override
    public Collection<ChargingFile> getChargingFiles(int nodeId) {
        return this.getChargingFiles(nodeId, null);
    }


    @Override
    public Collection<ChargingFile> getChargingFiles(int nodeId, TimeRange filter) {
        URI uriGet = URIBuilder.appendPath(uri, "charging", "files");
        uriGet = URIBuilder.appendQuery(uriGet, "nodeId", String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")));

        if (filter != null) {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            
            uriGet = URIBuilder.appendQuery(uriGet, "fromDate", df.format(filter.getFrom()));
            uriGet = URIBuilder.appendQuery(uriGet, "toDate", df.format(filter.getTo()));
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        ChargingFileList chargingFiles = getResult(response, ChargingFileList.class);
        if (chargingFiles == null) {
            return null;
        }
        else {
            return chargingFiles.toChargingFiles();
        }
    }


    @Override
    public ChargingResult getChargings(int nodeId, TimeRange filter, Integer topResults, boolean all) {
        
        URI uriGet = URIBuilder.appendPath(uri, "charging");
        uriGet = URIBuilder.appendQuery(uriGet, "nodeId", String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")));
        
        if (filter != null) {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            
            uriGet = URIBuilder.appendQuery(uriGet, "fromDate", df.format(filter.getFrom()));
            uriGet = URIBuilder.appendQuery(uriGet, "toDate", df.format(filter.getTo()));            
        }
        
        if (topResults != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "top", String.valueOf(topResults));
        }

        if (all) {
            uriGet = URIBuilder.appendQuery(uriGet, "all", "true");
        }
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GChargingResult chargingResult = getResult(response, O2GChargingResult.class);
        if (chargingResult == null) {
            return null;
        }
        else {
            return chargingResult.toChargingResult();
        }
    }


    @Override
    public ChargingResult getChargings(int nodeId, Collection<ChargingFile> files, Integer topResults, boolean all) {
        URI uriGet = URIBuilder.appendPath(uri, "charging");
        uriGet = URIBuilder.appendQuery(uriGet, "nodeId", String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")));
        
        if (topResults != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "top", String.valueOf(topResults));
        }

        if (all) {
            uriGet = URIBuilder.appendQuery(uriGet, "all", "true");
        }
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GChargingResult chargingResult = getResult(response, O2GChargingResult.class);
        if (chargingResult == null) {
            return null;
        }
        else {
            return chargingResult.toChargingResult();
        }
    }


    @Override
    public ChargingResult getChargings(int nodeId, Collection<ChargingFile> files, boolean all) {
        return this.getChargings(nodeId, files, null, all);
    }


    @Override
    public ChargingResult getChargings(int nodeId, TimeRange filter, boolean all) {
        return this.getChargings(nodeId, filter, null, all);
    }
    
}
