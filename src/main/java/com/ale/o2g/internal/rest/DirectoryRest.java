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

import com.ale.o2g.DirectoryService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.directory.Criteria;
import com.ale.o2g.types.directory.SearchResult;

/**
 *
 */
public class DirectoryRest extends AbstractRESTService implements DirectoryService {

	private static record SearchRequest(Integer limit, Criteria filter) {}
	
	public DirectoryRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}

	private boolean search(Criteria filter, Integer limit, String loginName) {

		URI uriPost = URIBuilder.appendPath(uri, "search");
        if (loginName != null) {
        	uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

		String json = gson.toJson(new SearchRequest(limit, AssertUtil.requireNotNull(filter, "filter")));

		HttpRequest request = HttpUtil.POST(uriPost, json);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean search(Criteria filter, int limit, String loginName) {
		return this.search(filter, limit, loginName);
	}

	@Override
	public boolean search(Criteria filter, int limit) {
		return this.search(filter, limit, null);
	}

	@Override
	public boolean search(Criteria filter) {
		return this.search(filter, null, null);
	}

	@Override
	public boolean cancel(String loginName) {

		URI uriDelete = URIBuilder.appendPath(uri, "search");
        if (loginName != null) {
        	uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.DELETE(uriDelete);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean cancel() {
		return this.cancel(null);
	}

	@Override
	public SearchResult getResults(String loginName) {

		URI uriGet = URIBuilder.appendPath(uri, "search");
        if (loginName != null) {
        	uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.GET(uriGet);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, SearchResult.class);
	}

	@Override
	public SearchResult getResults() {
		return this.getResults(null);
	}
}
