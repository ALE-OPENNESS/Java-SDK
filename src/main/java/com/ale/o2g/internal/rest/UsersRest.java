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
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.UsersService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpClientWrapper;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.users.Preferences;
import com.ale.o2g.types.users.SupportedLanguages;
import com.ale.o2g.types.users.User;

public class UsersRest extends AbstractRESTService implements UsersService {

	final static Logger logger = LoggerFactory.getLogger(UsersRest.class);
	
	static class LoginsResponse {
        private Collection<String> loginNames;

		public Collection<String> getLoginNames() {
			return loginNames;
		}
    }
	
	static record ChangePasswordRequest(String oldPassword, String newPassword) {}
	
	public UsersRest(HttpClientWrapper httpClient, URI uri) {
		super(httpClient, uri);
	}

    @Override
    public Collection<String> getLogins(int[] nodeIds, boolean onlyACD) {
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("getLogins() called with: nodeIds={}, onlyACD={}",
    	        Arrays.toString(nodeIds), onlyACD);
    	}
    	
        URI uriGet = URI.create(uri.toString().replace("/users", "/logins"));
        if (nodeIds != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "nodeIds", Arrays.stream(nodeIds)
        	        .mapToObj(Integer::toString)
        	        .collect(Collectors.joining(";")));
        }

        if (onlyACD) {
            uriGet = URIBuilder.appendQuery(uriGet, "onlyACD");
        }
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return unmodifiableOrEmpty(getOptionalResult(response, LoginsResponse.class)
                .map(LoginsResponse::getLoginNames)
                .orElse(null));
    }
	
	
	@Override
	public Collection<String> getLogins(String[] nodeIds, boolean onlyACD) {
		
		int[] intNodeIds = Arrays.stream(nodeIds)
		        .mapToInt(Integer::parseInt)
		        .toArray();
		    return getLogins(intNodeIds, onlyACD);
	}

	@Override
	public User getByLoginName(String loginName) {

    	if (logger.isDebugEnabled()) {
    		logger.debug("getByLoginName() called with: loginName={}", loginName);
    	}

    	HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName")));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, User.class);
	}

	@Override
	public User getByCompanyPhone(String companyPhone) {

    	if (logger.isDebugEnabled()) {
    		logger.debug("getByCompanyPhone() called with: companyPhone={}", companyPhone);
    	}

		HttpRequest request = HttpUtil.GET(URIBuilder.appendQuery(uri, "companyPhone", AssertUtil.requireNotEmpty(companyPhone, "companyPhone")));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, User.class);
	}

	@Override
	public Preferences getPreferences(String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("getPreferences() called with: loginName={}", loginName);
    	}

		HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"), "preferences"));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, Preferences.class);
	}

	@Override
	public boolean changePassword(String loginName, String oldPassword, String newPassword) {

    	if (logger.isDebugEnabled()) {
    		logger.debug("changePassword() called with: loginName={}", loginName);
    	}

		String json = gson.toJson(new ChangePasswordRequest(oldPassword, newPassword));
		
		HttpRequest request = HttpUtil.PUT(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"), "password"), json); 
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public SupportedLanguages getSupportedLanguages(String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("getSupportedLanguages() called with: loginName={}", loginName);
    	}

    	HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, loginName, "preferences/supportedLanguages"));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		
		return getResult(response, SupportedLanguages.class);
	}
}
