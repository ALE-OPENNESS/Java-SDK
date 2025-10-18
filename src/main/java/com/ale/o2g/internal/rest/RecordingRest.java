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

import com.ale.o2g.RecordingService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.recording.RecordedDevice;
import com.ale.o2g.types.recording.RecordingStartType;
import com.ale.o2g.types.recording.RecordingStatus;

/**
 *
 */
public class RecordingRest extends AbstractRESTService implements RecordingService {

    private static record RecordingRequest(String callRef) {}
    private static record StartRecordingRequest(String callRef, RecordingStartType StartType) {}
    
    private static class RecordingDevices {
        private Collection<String> recordingDevices;
    }
    
    
	public RecordingRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}

    @Override
    public Collection<String> getRecordedDevices() {

        URI uriGet = URIBuilder.appendPath(uri, "devices");

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        RecordingDevices devices = getResult(response, RecordingDevices.class);
        if (devices == null) {
            return null;
        }
        else {
            return devices.recordingDevices;
        }
    }

    @Override
    public RecordedDevice getRecordedDeviceInfo(String deviceId, String loginName) {

        URI uriGet = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"));

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, RecordedDevice.class);
    }

    @Override
    public RecordedDevice startRecording(String deviceId, String callRef, RecordingStartType startType) {
        return this.startRecording(deviceId, callRef, startType, null);
    }

    @Override
    public RecordedDevice startRecording(String deviceId, String callRef, RecordingStartType startType,
            String loginName) {

        URI uriPost = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"), "start");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new StartRecordingRequest(
                AssertUtil.requireNotEmpty(callRef, "callRef"),
                startType));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);        
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getResult(response, RecordedDevice.class);
    }

    @Override
    public RecordedDevice pauseRecording(String deviceId, String callRef) {
        return pauseRecording(deviceId, callRef, null);
    }

    @Override
    public RecordedDevice pauseRecording(String deviceId, String callRef, String loginName) {

        URI uriPost = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"), "pause");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new RecordingRequest(AssertUtil.requireNotEmpty(callRef, "callRef")));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);        
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getResult(response, RecordedDevice.class);
    }

    @Override
    public RecordedDevice resumeRecording(String deviceId, String callRef) {
        return resumeRecording(deviceId, callRef, null);
    }

    @Override
    public RecordedDevice resumeRecording(String deviceId, String callRef, String loginName) {
        URI uriPost = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"), "resume");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new RecordingRequest(AssertUtil.requireNotEmpty(callRef, "callRef")));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);        
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getResult(response, RecordedDevice.class);
    }

    @Override
    public RecordingStatus getRecordingStatus() {

        URI uriGet = URIBuilder.appendPath(uri, "status");

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, RecordingStatus.class);
    }
}
