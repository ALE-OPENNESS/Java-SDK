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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.Subscription;
import com.ale.o2g.internal.services.ISubscriptions;
import com.ale.o2g.internal.types.SubscriptionResult;
import com.ale.o2g.internal.util.HttpClientWrapper;
import com.ale.o2g.internal.util.URIBuilder;

/**
 * 
 *
 */
public class SubscriptionsRest  extends AbstractRESTService implements ISubscriptions {

<<<<<<< HEAD
	final static Logger logger = LoggerFactory.getLogger(SubscriptionsRest.class);

=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
	public SubscriptionsRest(HttpClientWrapper httpClient, URI uri) {
		super(httpClient, uri);
	}

	
	@Override
	public SubscriptionResult create(Subscription subscription) {
		String json = gson.toJson(subscription);
		
    	if (logger.isDebugEnabled()) {
    		logger.debug("create() called with: subscription={}", json);
    	}

		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json))
				.build();
        
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, SubscriptionResult.class);
	}


	@Override
	public boolean delete(String subscriptionId) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("delete() called with: subscriptionId={}", subscriptionId);
    	}

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URIBuilder.appendPath(uri, subscriptionId))
				.DELETE()
				.build();
        
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

}
