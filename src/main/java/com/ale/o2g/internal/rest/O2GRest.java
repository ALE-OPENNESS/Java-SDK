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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.O2GException;
import com.ale.o2g.internal.services.IO2G;
import com.ale.o2g.internal.types.RoxeRestApiDescriptor;

/**
 *
 */
public class O2GRest extends AbstractRESTService implements IO2G {

	final static Logger logger = LoggerFactory.getLogger(O2GRest.class);

	public O2GRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}

	@Override
	public RoxeRestApiDescriptor get() throws O2GException {
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();
        
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		
		try {
			HttpResponse<String> httpResponse = response.get();
			if (isSucceeded(httpResponse.statusCode())) {
				return gson.fromJson(httpResponse.body(), RoxeRestApiDescriptor.class);
			}
			else {
				String msgError = String.format("Get request to %s failed; Response status = %s", uri.toString(), httpResponse.statusCode());
				logger.error(msgError);
				throw new O2GException(msgError);
			}
		}
		catch (InterruptedException | ExecutionException e) {
			// Error when trying to access httpResponse
			logger.error("Unable to read http response", e);
			throw new O2GException(e);
		}
	}
}
