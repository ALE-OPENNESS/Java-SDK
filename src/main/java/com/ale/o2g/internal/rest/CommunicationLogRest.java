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
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ale.o2g.CommunicationLogService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.comlog.ComRecord;
import com.ale.o2g.types.comlog.Option;
import com.ale.o2g.types.comlog.Page;
import com.ale.o2g.types.comlog.QueryFilter;
import com.ale.o2g.types.comlog.QueryResult;
import com.ale.o2g.types.comlog.Role;

/**
 *
 */
public class CommunicationLogRest extends AbstractRESTService implements CommunicationLogService {

    private static record UpdateComRecordsRequest(Collection<Long> recordIds) {}    

    static class O2GQueryResult {      
        private Collection<ComRecord> records;
        private Integer offset;
        private Integer limit;
        private Integer count;
        
        public QueryResult toQueryResult() {
            int offsetValue = 0;
            int limitValue = 0;
            
            if (offset != null) offsetValue = offset;
            if (limit != null) limitValue = limit;
            
            return new QueryResult(records, new Page(offsetValue, limitValue), count) {};
        }
    }
    
    
    public CommunicationLogRest(HttpClient httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public QueryResult getComRecords(QueryFilter filter, Page page, boolean optimized, String loginName) {

        URI uriGet = uri;
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        if (filter != null) {
            
            if (filter.getAfter() != null)
            {
                uriGet = URIBuilder.appendQuery(uriGet, "afterDate", filter.getAfter().toInstant().toString());
            }
            
            if (filter.getBefore() != null)
            {
                uriGet = URIBuilder.appendQuery(uriGet, "beforeDate", filter.getBefore().toInstant().toString());
            }
            
            EnumSet<Option> options = filter.getOptions();
            if (options != null)
            {
                if (options.contains(Option.UNACKNOWLEDGED))
                {
                    uriGet = URIBuilder.appendQuery(uriGet, "unacknowledged", "true");
                }

                if (options.contains(Option.UNANSWERED))
                {
                    uriGet = URIBuilder.appendQuery(uriGet, "unanswered", "true");
                }
            }

            if (filter.getRole() != null)
            {
                if (filter.getRole() == Role.CALLEE)
                {
                    uriGet = URIBuilder.appendQuery(uriGet, "role", "CALLEE"); 
                }
                else if (filter.getRole() == Role.CALLER)
                {
                    uriGet = URIBuilder.appendQuery(uriGet, "role", "CALLER");
                }
            }

            if (filter.getCallRef() != null)
            {
                uriGet = URIBuilder.appendQuery(uriGet, "comRef", filter.getCallRef());
            }

            if (filter.getRemotePartyId() != null)
            {
                uriGet = URIBuilder.appendQuery(uriGet, "remotePartyId", filter.getRemotePartyId());
            }
        }

        if (page != null)
        {
            if (AssertUtil.requirePositive(page.getOffset(), "page.Offset") > 0)
            {
                uriGet = URIBuilder.appendQuery(uriGet, "offset", String.valueOf(page.getOffset()));
            }

            if (AssertUtil.requirePositive(page.getLength(), "page.Length") > 0)
            {
                uriGet = URIBuilder.appendQuery(uriGet, "limit", String.valueOf(page.getLength()));
            }
        }

        if (optimized)
        {
            uriGet = URIBuilder.appendQuery(uriGet, "optimized", "true");
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GQueryResult o2gQueryResult = getResult(response, O2GQueryResult.class);
        if (o2gQueryResult == null) {
            return null;
        }
        else {
            return o2gQueryResult.toQueryResult();
        }
   }

    @Override
    public QueryResult getComRecords(QueryFilter filter, Page page, boolean optimized) {
        return this.getComRecords(filter, page, optimized, null);
    }

    @Override
    public QueryResult getComRecords(QueryFilter filter, Page page) {
        return this.getComRecords(filter, page, false);
    }

    @Override
    public QueryResult getComRecords(QueryFilter filter) {
        return this.getComRecords(filter, null);
    }

    @Override
    public ComRecord getComRecord(long recordId, String loginName) {
        URI uriGet = URIBuilder.appendPath(uri, String.valueOf(recordId));
        if (loginName != null)
        {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName); 
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, ComRecord.class);
    }

    @Override
    public ComRecord getComRecord(long recordId) {
        return this.getComRecord(recordId, null);
    }

    @Override
    public boolean deleteComRecord(long recordId, String loginName) {

        URI uriDelete = URIBuilder.appendPath(uri, String.valueOf(recordId));
        if (loginName != null)
        {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName); 
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deleteComRecord(long recordId) {
        return this.deleteComRecord(recordId, null);
    }

    @Override
    public boolean deleteComRecords(QueryFilter filter, String loginName) {

        URI uriDelete = uri;
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        if (filter != null) {
            
            if (filter.getAfter() != null)
            {
                uriDelete = URIBuilder.appendQuery(uriDelete, "afterDate", filter.getAfter().toInstant().toString());
            }
            
            if (filter.getBefore() != null)
            {
                uriDelete = URIBuilder.appendQuery(uriDelete, "beforeDate", filter.getBefore().toInstant().toString());
            }
            
            EnumSet<Option> options = filter.getOptions();
            if (options != null)
            {
                if (options.contains(Option.UNACKNOWLEDGED))
                {
                    uriDelete = URIBuilder.appendQuery(uriDelete, "unacknowledged", "true");
                }

                if (options.contains(Option.UNANSWERED))
                {
                    uriDelete = URIBuilder.appendQuery(uriDelete, "unanswered", "true");
                }
            }

            if (filter.getRole() != null)
            {
                if (filter.getRole() == Role.CALLEE)
                {
                    uriDelete = URIBuilder.appendQuery(uriDelete, "role", "CALLEE"); 
                }
                else if (filter.getRole() == Role.CALLER)
                {
                    uriDelete = URIBuilder.appendQuery(uriDelete, "role", "CALLER");
                }
            }

            if (filter.getCallRef() != null)
            {
                uriDelete = URIBuilder.appendQuery(uriDelete, "comRef", filter.getCallRef());
            }

            if (filter.getRemotePartyId() != null)
            {
                uriDelete = URIBuilder.appendQuery(uriDelete, "remotePartyId", filter.getRemotePartyId());
            }
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return isSucceeded(response);
    }

    @Override
    public boolean deleteComRecords(QueryFilter filter) {
        return this.deleteComRecords(filter, null);
    }

    @Override
    public boolean deleteComRecords(Collection<Long> recordIds, String loginName) {
        
        String sRecordIds = recordIds.stream().map((s)->String.valueOf(s)).collect(Collectors.joining(","));
        
        URI uriDelete = URIBuilder.appendQuery(uri, "recordIdList", sRecordIds);
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return isSucceeded(response);
    }

    @Override
    public boolean deleteComRecords(Collection<Long> recordIds) {
        return this.deleteComRecords(recordIds, null);
    }

    private boolean ackOrUnAckComRecords(boolean ack, Collection<Long> recordIds, String loginName) {
        URI uriPut = URIBuilder.appendQuery(uri, "acknowledge", String.valueOf(ack));
        if (loginName != null) {
            uriPut = URIBuilder.appendQuery(uriPut, "loginName", loginName);
        }

        String json = gson.toJson(new UpdateComRecordsRequest(recordIds));

        HttpRequest request = HttpUtil.PUT(uriPut, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }
    
    
    @Override
    public boolean acknowledgeComRecords(Collection<Long> recordIds, String loginName) {
        return this.ackOrUnAckComRecords(true, recordIds, loginName);
    }

    @Override
    public boolean acknowledgeComRecords(Collection<Long> recordIds) {
        return this.acknowledgeComRecords(recordIds, null);
    }

    @Override
    public boolean acknowledgeComRecord(long recordId, String loginName) {
        
        return this.acknowledgeComRecords(Arrays.asList(recordId), loginName);
    }

    @Override
    public boolean acknowledgeComRecord(long recordId) {
        return this.acknowledgeComRecord(recordId, null);
    }

    @Override
    public boolean unacknowledgeComRecords(Collection<Long> recordIds, String loginName) {
        return this.ackOrUnAckComRecords(false, recordIds, loginName);
    }

    @Override
    public boolean unacknowledgeComRecords(Collection<Long> recordIds) {
        return this.unacknowledgeComRecords(recordIds, null);
    }

    @Override
    public boolean unacknowledgeComRecord(long recordId, String loginName) {
        return this.unacknowledgeComRecords(Arrays.asList(recordId), loginName);
    }

    @Override
    public boolean unacknowledgeComRecord(long recordId) {
        return this.unacknowledgeComRecord(recordId, null);
    }

}
