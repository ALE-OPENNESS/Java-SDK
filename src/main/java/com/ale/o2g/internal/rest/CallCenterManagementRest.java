/*
* Copyright 2024 ALE International
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.CallCenterManagementService;
import com.ale.o2g.internal.types.ccm.O2GCalendar;
import com.ale.o2g.internal.types.ccm.O2GCalendar.O2GExceptionCalendar;
import com.ale.o2g.internal.types.ccm.O2GCalendar.O2GNormalCalendar;
import com.ale.o2g.internal.types.ccm.O2GPilot;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.ccm.Pilot;
import com.ale.o2g.types.ccm.calendar.Calendar;
import com.ale.o2g.types.ccm.calendar.DayOfWeek;
import com.ale.o2g.types.ccm.calendar.ExceptionCalendar;
import com.ale.o2g.types.ccm.calendar.NormalCalendar;
import com.ale.o2g.types.ccm.calendar.Transition;

public class CallCenterManagementRest extends AbstractRESTService implements CallCenterManagementService {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    private static class PilotList {
        private Collection<O2GPilot> pilotList;
    }    
    
	public CallCenterManagementRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}

    @Override
    public Collection<Pilot> getPilots(int nodeId) {

        URI uriGet = URIBuilder.appendPath(uri, String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), "pilots");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        PilotList pilotList = getResult(response, PilotList.class);
        if (pilotList == null) {
            return null;
        }
        else {
            Collection<Pilot> pilots = new ArrayList<Pilot>();
            pilotList.pilotList.forEach(p -> pilots.add(p.toPilot()));

            return pilots;
        }        
    }

    @Override
    public Pilot getPilot(int nodeId, String pilotNumber) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber")
                );
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GPilot o2gPilot = getResult(response, O2GPilot.class);
        return o2gPilot.toPilot();
    }

    @Override
    public Calendar getCalendar(int nodeId, String pilotNumber) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "calendar");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GCalendar calendar = getResult(response, O2GCalendar.class);
        return calendar.toCalendar();
    }

    @Override
    public ExceptionCalendar getExceptionCalendar(int nodeId, String pilotNumber) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "calendar", "exception");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GExceptionCalendar calendar = getResult(response, O2GExceptionCalendar.class);
        return calendar.toExceptionCalendar();
    }

    @Override
    public boolean addExceptionTransition(int nodeId, String pilotNumber, Date date, Transition transition) {
        
        URI uriPost = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "calendar", "exception",
                dateFormat.format(AssertUtil.requireNotNull(date, "date")),
                "transitions");
        

        String json = gson.toJson(new O2GCalendar.PilotTransition(transition));

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deleteExceptionTransition(int nodeId, String pilotNumber, Date date, int transitionIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setExceptionTransition(int nodeId, String pilotNumber, Date date, int transitionIndex,
            Transition transition) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public NormalCalendar getNormalCalendar(int nodeId, String pilotNumber) {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "calendar", "exception");
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GNormalCalendar calendar = getResult(response, O2GNormalCalendar.class);
        return calendar.toNormalCalendar();
    }

    @Override
    public boolean addNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, Transition transition) {
        
        URI uriPost = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "calendar", "normal",
                day.name().toLowerCase(),
                "transitions");

        String json = gson.toJson(new O2GCalendar.PilotTransition(transition));

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deleteNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, int transitionIndex) {

        URI uriDelete = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "calendar", "normal",
                day.name().toLowerCase(),
                "transitions",
                String.valueOf(AssertUtil.requirePositive(transitionIndex, "transitionIndex")+1));

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean setNormalTransition(int nodeId, String pilotNumber, DayOfWeek day, int transitionIndex,
            Transition transition) {
        
        URI uriPut = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "calendar", "normal",
                day.name().toLowerCase(),
                "transitions",
                String.valueOf(AssertUtil.requirePositive(transitionIndex, "transitionIndex")+1));

        String json = gson.toJson(new O2GCalendar.PilotTransition(transition));

        HttpRequest request = HttpUtil.PUT(uriPut, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean openPilot(int nodeId, String pilotNumber) {
        
        URI uriPost = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "open");

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean closePilot(int nodeId, String pilotNumber) {
        
        URI uriPost = URIBuilder.appendPath(
                uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "pilots",
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"),
                "close");

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

}
