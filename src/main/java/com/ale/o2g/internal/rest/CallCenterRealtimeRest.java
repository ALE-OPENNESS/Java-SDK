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
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.CallCenterRealtimeService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.ccrt.Context;
import com.ale.o2g.types.ccrt.RtiObjectIdentifier;
import com.ale.o2g.types.ccrt.RtiObjects;

/**
 *
 */
public class CallCenterRealtimeRest extends AbstractRESTService implements CallCenterRealtimeService {
    
    /**
     * @param httpClient
     * @param uri
     */
    public CallCenterRealtimeRest(HttpClient httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public RtiObjects getRtiObjects() {

        HttpRequest request = HttpUtil.GET(uri);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, RtiObjects.class);
    }

    @Override
    public boolean setContext(Context context) {

        URI uriPost = URIBuilder.appendPath(uri, "ctx");

        String json = gson.toJson(AssertUtil.requireNotNull(context, "context"));

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public Context getContext() {

        URI uriGet = URIBuilder.appendPath(uri, "ctx");

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getResult(response, Context.class);
    }

    @Override
    public boolean deleteContext() {

        URI uriDelete = URIBuilder.appendPath(uri, "ctx");

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public Collection<RtiObjectIdentifier> getAgents() {

        URI uriGet = URIBuilder.appendPath(uri, "agents");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RtiObjects objects = getResult(response, RtiObjects.class);
        if (objects == null) {
            return null;
        }
        else {
            return objects.getAgents();
        }
    }

    @Override
    public Collection<RtiObjectIdentifier> getPilots() {

        URI uriGet = URIBuilder.appendPath(uri, "pilots");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RtiObjects objects = getResult(response, RtiObjects.class);
        if (objects == null) {
            return null;
        }
        else {
            return objects.getPilots();
        }
    }

    @Override
    public Collection<RtiObjectIdentifier> getQueues() {

        URI uriGet = URIBuilder.appendPath(uri, "queues");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RtiObjects objects = getResult(response, RtiObjects.class);
        if (objects == null) {
            return null;
        }
        else {
            return objects.getQueues();
        }
    }

    @Override
    public Collection<RtiObjectIdentifier> getAgentProcessingGroups() {

        URI uriGet = URIBuilder.appendPath(uri, "pgAgents");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RtiObjects objects = getResult(response, RtiObjects.class);
        if (objects == null) {
            return null;
        }
        else {
            return objects.getAgentProcessingGroups();
        }
    }

    @Override
    public Collection<RtiObjectIdentifier> getOtherProcessingGroups() {

        URI uriGet = URIBuilder.appendPath(uri, "pgOthers");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RtiObjects objects = getResult(response, RtiObjects.class);
        if (objects == null) {
            return null;
        }
        else {
            return objects.getOtherProcessingGroups();
        }
    }

    @Override
    public boolean start() {

        URI uriPost = URIBuilder.appendPath(uri, "snapshot");

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

}
