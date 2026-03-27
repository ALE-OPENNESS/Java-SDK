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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
import com.ale.o2g.CallCenterPilotService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpClientWrapper;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;

public class CallCenterPilotRest extends AbstractRESTService implements CallCenterPilotService {

<<<<<<< HEAD
	final static Logger logger = LoggerFactory.getLogger(CallCenterPilotRest.class);

=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
	public CallCenterPilotRest(HttpClientWrapper httpClient, URI uri) {
		super(httpClient, uri);
	}

    @Override
    public boolean monitorStart(int nodeId, String pilotNumber) {
    	return this.monitorStart(pilotNumber);
    }

    @Override
    public boolean monitorStart(String pilotNumber) {
<<<<<<< HEAD
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("monitorStart() called with: pilotNumber={}", pilotNumber);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        
        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"));
        
        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }
    
    @Override
    public boolean monitorStop(int nodeId, String pilotNumber) {
    	return this.monitorStop(pilotNumber);
    }

    @Override
    public boolean monitorStop(String pilotNumber) {
<<<<<<< HEAD
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("monitorStop() called with: pilotNumber={}", pilotNumber);
    	}

    	URI uriDelete = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"));
=======
        
        URI uriDelete = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"));
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        
        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }
}
