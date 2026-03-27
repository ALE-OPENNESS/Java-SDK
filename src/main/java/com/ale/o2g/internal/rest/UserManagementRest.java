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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
<<<<<<< HEAD
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

import com.ale.o2g.UserManagementService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpClientWrapper;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.users.User;

public class UserManagementRest extends AbstractRESTService implements UserManagementService {

<<<<<<< HEAD
	final static Logger logger = LoggerFactory.getLogger(UserManagementRest.class);

=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
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
	
	public UserManagementRest(HttpClientWrapper httpClient, URI uri) {
		super(httpClient, uri);
	}


	@Override
    public Collection<String> getLogins(int[] nodeIds) {
<<<<<<< HEAD
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("getLogins() called with: nodeIds={}", Arrays.toString(nodeIds));
    	}
    	
	    URI uriGet = uri;
	    if (nodeIds != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "nodeIds", Arrays.stream(nodeIds)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(";")));
=======

	    URI uriGet = uri;
	    if (nodeIds != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "nodeIds", MakeNodeQuery(nodeIds));
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return unmodifiableOrEmpty(getOptionalResult(response, LoginsResponse.class)
                .map(LoginsResponse::getLoginNames)
                .orElse(null));
	}


    @Override
    public String getLogin(String deviceNumber) {
<<<<<<< HEAD

    	if (logger.isDebugEnabled()) {
    		logger.debug("getLogin() called with: deviceNumber={}", deviceNumber);
    	}
    	
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
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

<<<<<<< HEAD

    @Override
    public User getUser(String loginName) {
    
    	if (logger.isDebugEnabled()) {
    		logger.debug("getUser() called with: loginName={}", loginName);
    	}
    	
=======
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
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        URI uriGet = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"));

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, User.class);
    }

    @Override
    public Collection<User> createUsers(int nodeId, String[] deviceNumbers) {
<<<<<<< HEAD
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("createUsers() called with: nodeId={}, deviceNumbers={}", nodeId, Arrays.toString(deviceNumbers));
    	}

    	URI uriPost = uri;
=======
        URI uriPost = uri;
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
                
        String json = gson.toJson(new UserCreateRequest(
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")),
                Arrays.asList(AssertUtil.requireNotNull(deviceNumbers, "deviceNumbers")),
                false));
        
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
                
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return unmodifiableOrEmpty(getOptionalResult(response, Users.class)
                .map(Users::getUsers)
                .orElse(null));        
    }

    @Override
    public Collection<User> createUsers(int nodeId) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("createUsers() called with: nodeId={}", nodeId);
    	}

    	URI uriPost = uri;
=======
        URI uriPost = uri;
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        
        String json = gson.toJson(new UserCreateRequest(
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")),
                null, true));
        
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
                
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return unmodifiableOrEmpty(getOptionalResult(response, Users.class)
                .map(Users::getUsers)
                .orElse(null));        
    }

    @Override
    public boolean deleteUser(String loginName) {
<<<<<<< HEAD

    	if (logger.isDebugEnabled()) {
    		logger.debug("deleteUser() called with: loginName={}", loginName);
    	}

    	URI uriDelete = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"));
=======
        URI uriDelete = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(loginName, "loginName"));
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }
}
