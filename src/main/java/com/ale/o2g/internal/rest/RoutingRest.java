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
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.RoutingService;
import com.ale.o2g.internal.types.routing.ForwardRoute;
import com.ale.o2g.internal.types.routing.O2GRoutingState;
import com.ale.o2g.internal.types.routing.OverflowRoute;
import com.ale.o2g.internal.types.routing.PresentationRoute;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpClientWrapper;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.routing.Destination;
import com.ale.o2g.types.routing.DndState;
import com.ale.o2g.types.routing.Forward;
import com.ale.o2g.types.routing.Forward.Condition;
import com.ale.o2g.types.routing.Overflow;
import com.ale.o2g.types.routing.RoutingCapabilities;
import com.ale.o2g.types.routing.RoutingState;

/**
 *
 */
public class RoutingRest extends AbstractRESTService implements RoutingService {
	final static Logger logger = LoggerFactory.getLogger(RoutingRest.class);

	private static class SetRouteRequest {
		private Collection<PresentationRoute> presentationRoutes = new ArrayList<PresentationRoute>();
		
		public void addPresentationRoute(PresentationRoute p) {
			presentationRoutes.add(p);
		}
	}
	
	private static class SetOverflowRouteRequest {
		private Collection<OverflowRoute> overflowRoutes = new ArrayList<OverflowRoute>();
		
		public void addRoute(OverflowRoute p) {
			overflowRoutes.add(p);
		}
	}
	
	private static record SetForwardRouteRequest(ForwardRoute forwardRoute) {}
	
	public RoutingRest(HttpClientWrapper httpClient, URI uri) {
		super(httpClient, uri);
	}

	@Override
	public RoutingCapabilities getCapabilities(String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCapabilities() called with: loginName={}", loginName);
		}

		URI uriGet = uri;
        if (loginName != null) {
        	uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.GET(uriGet);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return getResult(response, RoutingCapabilities.class);
	}

	@Override
	public RoutingCapabilities getCapabilities() {
		return this.getCapabilities(null);
	}

	@Override
	public boolean setRemoteExtensionActivation(boolean active, String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("setRemoteExtensionActivation() called with: active={}, loginName={}", active, loginName);
		}

		URI uriPost = uri;
        if (loginName != null) {
        	uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        SetRouteRequest setRouteRequest = new SetRouteRequest();
        setRouteRequest.addPresentationRoute(PresentationRoute.createMobileRouteActivation(active));

        String json = gson.toJson(setRouteRequest);
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
        
		HttpRequest request = HttpUtil.POST(uriPost, json);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean setRemoteExtensionActivation(boolean active) {
		return this.setRemoteExtensionActivation(active, null);
	}

	@Override
	public DndState getDndState(String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDndState() called with: loginName={}", loginName);
		}

		URI uriGet = URIBuilder.appendPath(uri, "dnd");
        if (loginName != null) {
        	uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.GET(uriGet);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		
		return getResult(response, DndState.class);
	}

	@Override
	public DndState getDndState() {
		return this.getDndState(null);
	}

	@Override
	public boolean activateDnd(String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("activateDnd() called with: loginName={}", loginName);
		}

		URI uriPost = URIBuilder.appendPath(uri, "dnd");
        if (loginName != null) {
        	uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.POST(uriPost);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean activateDnd() {
		return this.activateDnd(null);
	}

	@Override
	public boolean cancelDnd(String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("cancelDnd() called with: loginName={}", loginName);
		}

		URI uriDelete = URIBuilder.appendPath(uri, "dnd");
        if (loginName != null) {
        	uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.DELETE(uriDelete);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean cancelDnd() {
		return this.cancelDnd(null);
	}

	@Override
	public Forward getForward(String loginName) {

		if (logger.isDebugEnabled()) {
			logger.debug("getForward() called with: loginName={}", loginName);
		}

		URI uriGet = URIBuilder.appendPath(uri, "forwardroute");
        if (loginName != null) {
        	uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.GET(uriGet);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		ForwardRoute forwardRoute = getResult(response, ForwardRoute.class);
		if (forwardRoute == null) {
			return new Forward(Destination.NONE, null, null) {};
		}
		else {
			return forwardRoute.toForward();
		}
	}

	@Override
	public Forward getForward() {
		return this.getForward(null);
	}

	@Override
	public boolean cancelForward(String loginName) {

		if (logger.isDebugEnabled()) {
			logger.debug("cancelForward() called with: loginName={}", loginName);
		}

		URI uriDelete = URIBuilder.appendPath(uri, "forwardroute");
        if (loginName != null) {
        	uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.DELETE(uriDelete);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean cancelForward() {
		return this.cancelForward(null);
	}

	@Override
	public boolean forwardOnVoiceMail(Condition condition, String loginName) {

		if (logger.isDebugEnabled()) {
			logger.debug("forwardOnVoiceMail() called with: condition={}, loginName={}", condition, loginName);
		}

		URI uriPost = URIBuilder.appendPath(uri, "forwardroute");
        if (loginName != null) {
        	uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new SetForwardRouteRequest(ForwardRoute.createForwardOnVoiceMail(condition)));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

		HttpRequest request = HttpUtil.POST(uriPost, json);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean forwardOnVoiceMail(Condition condition) {
		return this.forwardOnVoiceMail(condition, null);
	}

	@Override
	public boolean forwardOnNumber(String number, Condition condition, String loginName) {

		if (logger.isDebugEnabled()) {
			logger.debug("forwardOnNumber() called with: number={}, condition={}, loginName={}", number, condition, loginName);
		}

		URI uriPost = URIBuilder.appendPath(uri, "forwardroute");
        if (loginName != null) {
        	uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new SetForwardRouteRequest(
        		ForwardRoute.createForwardOnNumber(
        				AssertUtil.requireNotEmpty(number, "number"), 
        				condition)));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

		HttpRequest request = HttpUtil.POST(uriPost, json);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean forwardOnNumber(String number, Condition condition) {
		return this.forwardOnNumber(number, condition, null);
	}

	@Override
	public boolean cancelOverflow(String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("cancelOverflow() called with: loginName={}", loginName);
		}

		URI uriDelete = URIBuilder.appendPath(uri, "overflowroute");
        if (loginName != null) {
        	uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.DELETE(uriDelete);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean cancelOverflow() {
		return this.cancelOverflow(null);
	}

	@Override
	public Overflow getOverflow(String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOverflow() called with: loginName={}", loginName);
		}

		URI uriGet = URIBuilder.appendPath(uri, "overflowroute");
        if (loginName != null) {
        	uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.GET(uriGet);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		OverflowRoute overflowRoute = getResult(response, OverflowRoute.class);
		if (overflowRoute == null) {
			return new Overflow(Destination.NONE, null) {};
		}
		else {
			return overflowRoute.toOverflow();
		}
	}

	@Override
	public Overflow getOverflow() {
		return this.getOverflow(null);
	}

	@Override
	public boolean overflowOnVoiceMail(Overflow.Condition condition, String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("overflowOnVoiceMail() called with: condition={}, loginName={}", condition, loginName);
		}

		URI uriPost = URIBuilder.appendPath(uri, "overflowroute");
        if (loginName != null) {
        	uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        SetOverflowRouteRequest setOverflowRouteRequest = new SetOverflowRouteRequest();
        setOverflowRouteRequest.addRoute(OverflowRoute.createOverflowOnVoiceMail(condition));
        
        String json = gson.toJson(setOverflowRouteRequest);
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
        
		HttpRequest request = HttpUtil.POST(uriPost, json);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean overflowOnVoiceMail(Overflow.Condition condition) {
		return this.overflowOnVoiceMail(condition, null);
	}

	@Override
	public RoutingState getRoutingState(String loginName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getRoutingState() called with: loginName={}", loginName);
		}

		URI uriGet = URIBuilder.appendPath(uri, "state");
        if (loginName != null) {
        	uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.GET(uriGet);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		O2GRoutingState routingState = getResult(response, O2GRoutingState.class);
		if (routingState == null) {
			return new RoutingState(
					false,
					new Forward(Destination.NONE, null, null) {},
					new Overflow(Destination.NONE, null) {},
					new DndState(false) {}
					) {};
		}
		else {
			return routingState.toRoutingState();
		}
	}

	@Override
	public RoutingState getRoutingState() {
		return this.getRoutingState(null);
	}

	@Override
	public boolean requestSnapshot(String loginName) {

		if (logger.isDebugEnabled()) {
			logger.debug("requestSnapshot() called with: loginName={}", loginName);
		}

		URI uriPost = URIBuilder.appendPath(uri, "state/snapshot");
        if (loginName != null) {
        	uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

		HttpRequest request = HttpUtil.POST(uriPost);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

	@Override
	public boolean requestSnapshot() {
		return this.requestSnapshot(null);
	}
}
