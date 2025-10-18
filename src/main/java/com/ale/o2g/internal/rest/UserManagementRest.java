/*
* Copyright 2025 ALE International
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.UserManagementService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.users.User;

public class UserManagementRest extends AbstractRESTService implements UserManagementService {

	static class LoginsResponse {
        private Collection<String> loginNames;

		public Collection<String> getLoginNames() {
			return loginNames;
		}
    }

    static class Users {
        private Collection<User> users;

        public Collection<User> getUsers() {
            return users;
        }
    }
	
    private static record UserCreateRequest(String nodeId, Collection<String> deviceNumbers, boolean all) {}
	
	public UserManagementRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}


	@Override
    public Collection<String> getLogins(int[] nodeIds) {

	    URI uriGet = uri;
	    if (nodeIds != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "nodeIds", MakeNodeQuery(nodeIds));
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getOptionalResult(response, LoginsResponse.class)
                .map(LoginsResponse::getLoginNames)
                .orElse(null);
	}


    @Override
    public String getLogin(String deviceNumber) {
        URI uriGet = URIBuilder.appendQuery(uri, "deviceNumber", AssertUtil.requireNotEmpty(deviceNumber, "deviceNumber"));

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        LoginsResponse logins = getResult(response, LoginsResponse.class);
        if ((logins == null) || (logins.loginNames == null) || logins.loginNames.isEmpty()) {
            return null;
        }
        else {
            return (new ArrayList<>(logins.loginNames)).get(0);
        }
    }

    private String MakeNodeQuery(int[] nodeIds) {
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
    public User getUser(String loginName) {
        URI uriGet = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"));

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, User.class);
    }

    @Override
    public Collection<User> createUsers(int nodeId, String[] deviceNumbers) {
        URI uriPost = uri;
                
        String json = gson.toJson(new UserCreateRequest(
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")),
                Arrays.asList(AssertUtil.requireNotNull(deviceNumbers, "deviceNumbers")),
                false));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getOptionalResult(response, Users.class)
                .map(Users::getUsers)
                .orElse(null);        
    }

    @Override
    public Collection<User> createUsers(int nodeId) {
        URI uriPost = uri;
        
        String json = gson.toJson(new UserCreateRequest(
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")),
                null, true));
        
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getOptionalResult(response, Users.class)
                .map(Users::getUsers)
                .orElse(null);        
    }

    @Override
    public boolean deleteUser(String loginName) {
        URI uriDelete = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"));

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }
}
