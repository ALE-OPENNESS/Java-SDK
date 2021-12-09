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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.RsiService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.rsi.AdditionalDigitCollectionCriteria;
import com.ale.o2g.types.rsi.RouteSession;
import com.ale.o2g.types.rsi.RsiPoint;
import com.ale.o2g.types.rsi.Tones;

/**
 *
 */
public class RsiRest extends AbstractRESTService implements RsiService {
    
    static class O2GAdditionalDigitCollectionCriteria {
        @SuppressWarnings("unused")
        private String abortDigits;
        @SuppressWarnings("unused")
        private String ignoreDigits;
        @SuppressWarnings("unused")
        private String backspaceDigits;
        @SuppressWarnings("unused")
        private String termDigits;
        @SuppressWarnings("unused")
        private String resetDigit;
        @SuppressWarnings("unused")
        private Integer startTimeout;
        @SuppressWarnings("unused")
        private Integer digitTimeout;
        
        static O2GAdditionalDigitCollectionCriteria from (AdditionalDigitCollectionCriteria adcc) {
            
            O2GAdditionalDigitCollectionCriteria o2gAdcc = new O2GAdditionalDigitCollectionCriteria();
            o2gAdcc.abortDigits = (adcc.getAbortDigits().length() > 0) ? adcc.getAbortDigits() : null;
            o2gAdcc.ignoreDigits = (adcc.getIgnoreDigits().length() > 0) ? adcc.getIgnoreDigits() : null;
            o2gAdcc.backspaceDigits = (adcc.getBackspaceDigits().length() > 0) ? adcc.getBackspaceDigits() : null;
            o2gAdcc.termDigits = (adcc.getTermDigits().length() > 0) ? adcc.getTermDigits() : null;
            o2gAdcc.resetDigit = (adcc.getResetDigits().length() > 0) ? adcc.getResetDigits() : null;
            
            o2gAdcc.startTimeout = (adcc.getStartTimeout() > 0) ? adcc.getStartTimeout() : null;
            o2gAdcc.digitTimeout = (adcc.getDigitTimeout() > 0) ? adcc.getDigitTimeout() : null;
            
            return o2gAdcc;
        }
    }
    
    
    static record StartDataCollectionRequest(String callRef, Integer numChars, Character flushChar, Integer timeout, O2GAdditionalDigitCollectionCriteria additionalCriteria) {}
    static record PlayToneRequest(String callRef, Tones type, int duration) {}
    static record CallRefRequest(String callRef) {}
    static record PlayVoiceGuideRequest(String callRef, int guideNumber, Integer duration) {}
    static record RouteEnd(String routeCrid) {}
    static record RouteSelect(String routeCrid, String selectedRoute, String callingLine, String associatedData, Boolean routeToVoiceMail) {}
    
    static class RsiPointList
    {
        public Collection<RsiPoint> rsiPoints;
    }
    static class RouteSessionList
    {
        public Collection<RouteSession> routeSessions;
    }
    
    
    public RsiRest(HttpClient httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public Collection<RsiPoint> getRsiPoints() {

        HttpRequest request = HttpUtil.GET(uri);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RsiPointList rsiPoints = getResult(response, RsiPointList.class);
        if (rsiPoints == null) {
            return null;
        }
        else {
            return rsiPoints.rsiPoints;
        }
    }

    @Override
    public boolean enableRsiPoint(String rsiNumber) {

        HttpRequest request = HttpUtil.POST(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "enable"));
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public boolean disableRsiPoint(String rsiNumber) {

        HttpRequest request = HttpUtil.POST(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "disable"));
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public String startCollectDigit(String rsiNumber, String callRef, Integer nbChars, Character flushChar,
            Integer timeout, AdditionalDigitCollectionCriteria additionalCriteria) {
        
        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "collectDigits");
        
        String json = gson.toJson(new StartDataCollectionRequest(
                AssertUtil.requireNotEmpty(callRef, "callRef"),
                nbChars,
                flushChar,
                timeout,
                O2GAdditionalDigitCollectionCriteria.from(additionalCriteria)));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        HttpResponse<String> httpResponse = getResponse(response);
        if (isSucceeded(httpResponse.statusCode())) {
            
            Optional<String> location = httpResponse.headers().firstValue("Location");
            if (location.isPresent()) {
                return location.get();
            }
        }
        
        return null;
    }

    @Override
    public boolean stopCollectDigit(String rsiNumber, String callCrid) {
        URI uriDelete = URIBuilder.appendPath(
                uri, 
                AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), 
                "collectDigits",
                AssertUtil.requireNotEmpty(callCrid, "callCrid"));
        
        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public boolean playTone(String rsiNumber, String callRef, Tones tone, int duration) {
        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "playTone");
        
        String json = gson.toJson(new PlayToneRequest(
                AssertUtil.requireNotEmpty(callRef, "callRef"),
                tone,
                duration));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public boolean cancelTone(String rsiNumber, String callRef) {
        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "stopTone");
        
        String json = gson.toJson(new CallRefRequest(AssertUtil.requireNotEmpty(callRef, "callRef")));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public boolean playVoiceGuide(String rsiNumber, String callRef, int guideNumber, Integer duration) {
        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "playVoiceGuide");
        
        String json = gson.toJson(new PlayVoiceGuideRequest(
                AssertUtil.requireNotEmpty(callRef, "callRef"),
                guideNumber,
                duration));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public boolean routeEnd(String rsiNumber, String routeCrid) {
        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "routeEnd");
        
        String json = gson.toJson(new RouteEnd(AssertUtil.requireNotEmpty(routeCrid, "routeCrid")));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public boolean routeSelect(String rsiNumber, String routeCrid, String selectedRoute, String callingLine,
            String associatedData, Boolean routeToVoiceMail) {
        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "routeSelect");
        
        String json = gson.toJson(new RouteSelect(
                AssertUtil.requireNotEmpty(routeCrid, "routeCrid"),
                AssertUtil.requireNotEmpty(selectedRoute, "selectedRoute"),
                callingLine,
                associatedData,
                routeToVoiceMail));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return isSucceeded(response);
    }

    @Override
    public Collection<RouteSession> getRouteSessions(String rsiNumber) {

        URI uriGet = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), "routeSessions");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RouteSessionList routeSessions = getResult(response, RouteSessionList.class);
        if (routeSessions == null) {
            return null;
        }
        else {
            return routeSessions.routeSessions;
        }
    }

    @Override
    public RouteSession getRouteSession(String rsiNumber, String routeCrid) {

        URI uriGet = URIBuilder.appendPath(uri, 
                AssertUtil.requireNotEmpty(rsiNumber, "rsiNumber"), 
                "routeSessions",
                AssertUtil.requireNotEmpty(routeCrid, "routeCrid"));
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, RouteSession.class);
    }

}
