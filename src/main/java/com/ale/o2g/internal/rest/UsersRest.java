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

import com.ale.o2g.UsersService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.users.Preferences;
import com.ale.o2g.types.users.SupportedLanguages;
import com.ale.o2g.types.users.User;

public class UsersRest extends AbstractRESTService implements UsersService {

	static class LoginsResponse {
        private Collection<String> loginNames;

		public Collection<String> getLoginNames() {
			return loginNames;
		}
    }
	
	static record ChangePasswordRequest(String oldPassword, String newPassword) {}
	
	public UsersRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}

	@Override
	public Collection<String> getLogins(String[] nodeIds, boolean onlyACD) {
		
		URI uriGet = URI.create(uri.toString().replace("/users", "/logins"));
        if (nodeIds != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "nodeIds", MakeNodeQuery(nodeIds));
        }

        if (onlyACD) {
            uriGet = URIBuilder.appendQuery(uriGet, "onlyACD");
        }
        
		HttpRequest request = HttpUtil.GET(uriGet);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getOptionalResult(response, LoginsResponse.class)
				.map(LoginsResponse::getLoginNames)
				.orElse(null);
	}

    private String MakeNodeQuery(String[] nodeIds) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodeIds.length; i++) {
            if (i > 0) {
                sb.append(';');
            }
            sb.append(nodeIds[i]);
        }

        return sb.toString();
    }

	@Override
	public User getByLoginName(String loginName) {

		HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName")));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, User.class);
	}

	@Override
	public User getByCompanyPhone(String companyPhone) {

		HttpRequest request = HttpUtil.GET(URIBuilder.appendQuery(uri, "companyPhone", AssertUtil.requireNotEmpty(companyPhone, "companyPhone")));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, User.class);
	}

	@Override
	public Preferences getPreferences(String loginName) {
		HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"), "preferences"));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, Preferences.class);
	}

	@Override
	public boolean changePassword(String loginName, String oldPassword, String newPassword) {
		
		String json = gson.toJson(new ChangePasswordRequest(oldPassword, newPassword));
		
		HttpRequest request = HttpUtil.PUT(URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"), "password"), json); 
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public SupportedLanguages getSupportedLanguages(String loginName) {
		HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, loginName, "preferences/supportedLanguages"));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, SupportedLanguages.class);
	}
}
