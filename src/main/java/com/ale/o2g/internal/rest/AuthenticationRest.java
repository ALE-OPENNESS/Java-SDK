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
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.O2GAuthenticationException;
import com.ale.o2g.internal.services.IAuthentication;
import com.ale.o2g.internal.types.AuthenticateResult;
import com.ale.o2g.types.Credential;

public class AuthenticationRest extends AbstractRESTService implements IAuthentication {

	public AuthenticationRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}

	private static String buildChallenge(Credential credential) {
	    return "Basic " + Base64.getEncoder().encodeToString((credential.getLogin() + ":" + credential.getPassword()).getBytes());
	}
	
	@Override
	public AuthenticateResult authenticate(Credential credential) throws O2GAuthenticationException {
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.header("Authorization", buildChallenge(credential))
				.build();
		
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		
		try {
			if (isSucceeded(response.get().statusCode())) {
				return gson.fromJson(response.get().body(), AuthenticateResult.class);
			}
			else {
				throw new O2GAuthenticationException();
			}
		}
		catch (Exception e) {
			throw new O2GAuthenticationException(e);
		}
	}

}
