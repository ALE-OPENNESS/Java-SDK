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
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.TelephonyService;
import com.ale.o2g.internal.types.telephony.O2GTelephonicState;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HexaString;
import com.ale.o2g.internal.util.HttpClientWrapper;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.telephony.Call;
import com.ale.o2g.types.telephony.Callback;
import com.ale.o2g.types.telephony.HuntingGroupStatus;
import com.ale.o2g.types.telephony.HuntingGroups;
import com.ale.o2g.types.telephony.MiniMessage;
import com.ale.o2g.types.telephony.RecordingAction;
import com.ale.o2g.types.telephony.TelephonicState;
import com.ale.o2g.types.telephony.call.CorrelatorData;
import com.ale.o2g.types.telephony.call.Leg;
import com.ale.o2g.types.telephony.call.Participant;
import com.ale.o2g.types.telephony.call.acd.CallProfile;
import com.ale.o2g.types.telephony.call.acd.PilotInfo;
import com.ale.o2g.types.telephony.call.acd.PilotTransferQueryParameters;
import com.ale.o2g.types.telephony.device.DeviceState;

public class TelephonyRest extends AbstractRESTService implements TelephonyService {
    final static Logger logger = LoggerFactory.getLogger(TelephonyRest.class);

    private static class CallList {
        private Collection<Call> calls;
    }

    private static class LegList {
        private Collection<Leg> legs;
    }

    private static class ParticipantList {
        private Collection<Participant> participants;
    }

    private static class DeviceStateList {
        private Collection<DeviceState> deviceStates;
    }

    private static class CallbackList {
        private Collection<Callback> callbacks;
    }

    private static class SendAssociatedDataRequest {
        @SuppressWarnings("unused")
        private String deviceId;
        @SuppressWarnings("unused")
        private String associatedData;
        @SuppressWarnings("unused")
        private String hexaBinaryAssociatedData;

        public SendAssociatedDataRequest(String deviceId, CorrelatorData correlatorData) {
            this.deviceId = deviceId;
            this.associatedData = null;

            byte[] byteValue = correlatorData.asByteArray();
            if (byteValue == null) {
                this.hexaBinaryAssociatedData = null;
            } else {
                this.hexaBinaryAssociatedData = HexaString.toHexaString(byteValue);
            }
        }
    }

    private static record MakeBasicCallRequest(String deviceId, String callee, boolean autoAnswer) {}
    private static record DeviceIdRequest(String deviceId) {}
    private static record MakeCallRequest(String deviceId, String callee, boolean autoAnswer,
            boolean inhibitProgressTone, String hexaBinaryAssociatedData, String pin, String secretCode,
            String businessCode, String callingNumber) {}
    private static record BlindTransferRequest(String transferTo, boolean anonymous) {}
    private static record HeldCallRequest(String heldCallRef) {}
    private static record ParkRequest(String parkTo) {}
    private static record ReconnectRequest(String deviceId, String enquiryCallRef) {}
    private static record RedirectRequest(String redirectTo, boolean anonymous) {}
    private static record SendDtmfRequest(String deviceId, String number) {}
    private static record SendAccountInfoRequest(String deviceId, String accountInfo) {}
    private static record DSLogOnRequest(String dssDeviceNumber) {}
    private static record PickupRequest(String otherCallRef, String otherPhoneNumber, boolean autoAnswer) {}
    private static record MiniMessageRequest(String recipient, String message) {}
    private static record CallbackRequest(String callee) {}
    private static record ACRSkills(Collection<CallProfile.Skill> skills) {}
    private static record PilotQueryParam(String agentNumber, ACRSkills skills, Boolean priorityTransfer,
            Boolean supervisedTransfer) {}

    public TelephonyRest(HttpClientWrapper httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public boolean basicMakeCall(String deviceId, String callee, boolean autoAnswer) {
        if (logger.isDebugEnabled()) {
            logger.debug("basicMakeCall() called with: deviceId={}, callee={}, autoAnswer={}", deviceId, callee, autoAnswer);
        }

        String json = gson.toJson(new MakeBasicCallRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(callee, "callee"), autoAnswer));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(URIBuilder.appendPath(uri, "basicCall"), json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean basicMakeCall(String deviceId, String callee) {
        if (logger.isDebugEnabled()) {
            logger.debug("basicMakeCall() called with: deviceId={}, callee={}", deviceId, callee);
        }

        return this.basicMakeCall(deviceId, callee, true);
    }

    @Override
    public boolean basicAnswerCall(String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("basicAnswerCall() called with: deviceId={}", deviceId);
        }

        String json = gson.toJson(new DeviceIdRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId")));

        HttpRequest request = HttpUtil.POST(URIBuilder.appendPath(uri, "basicCall/answer"), json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean basicDropMe(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("basicDropMe() called with: loginName={}", loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "basicCall/dropme");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean basicDropMe() {
        if (logger.isDebugEnabled()) {
            logger.debug("basicDropMe()");
        }

        return this.basicDropMe(null);
    }

    @Override
    public Collection<Call> getCalls(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getCalls() called with: loginName={}", loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "calls");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        CallList calls = getResult(response, CallList.class);
        if (calls == null) {
            return null;
        } else {
            return unmodifiableOrEmpty(calls.calls);
        }
    }

    @Override
    public Collection<Call> getCalls() {
        if (logger.isDebugEnabled()) {
            logger.debug("getCalls()");
        }

        return this.getCalls(null);
    }

    @Override
    public Call getCall(String callRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getCall() called with: callRef={}, loginName={}", callRef, loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"));
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, Call.class);
    }

    @Override
    public Call getCall(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("getCall() called with: callRef={}", callRef);
        }

        return this.getCall(callRef, null);
    }

    @Override
    public boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            CorrelatorData correlatorData, String callingNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeCall() called with: deviceId={}, callee={}, autoAnswer={}, inhibitProgressTone={}, correlatorData={}, callingNumber={}, loginName={}",
                    deviceId, callee, autoAnswer, inhibitProgressTone, correlatorData, callingNumber, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String hexaBinaryAssociatedData = null;
        if (correlatorData != null) {
            hexaBinaryAssociatedData = correlatorData.asString();
        }

        String json = gson.toJson(new MakeCallRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(callee, "callee"), autoAnswer, inhibitProgressTone, hexaBinaryAssociatedData,
                null, null, null, callingNumber));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            CorrelatorData correlatorData, String callingNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeCall() called with: deviceId={}, callee={}, autoAnswer={}, inhibitProgressTone={}, correlatorData={}, callingNumber={}",
                    deviceId, callee, autoAnswer, inhibitProgressTone, correlatorData, callingNumber);
        }

        return this.makeCall(deviceId, callee, autoAnswer, inhibitProgressTone, correlatorData, callingNumber, null);
    }

    @Override
    @Deprecated
    public boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            String associatedData, String callingNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeCall() called with: deviceId={}, callee={}, autoAnswer={}, inhibitProgressTone={}, associatedData={}, callingNumber={}, loginName={}",
                    deviceId, callee, autoAnswer, inhibitProgressTone, associatedData, callingNumber, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new MakeCallRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(callee, "callee"), autoAnswer, inhibitProgressTone, associatedData, null,
                null, null, callingNumber));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    @Deprecated
    public boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            String associatedData, String callingNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeCall() called with: deviceId={}, callee={}, autoAnswer={}, inhibitProgressTone={}, associatedData={}, callingNumber={}",
                    deviceId, callee, autoAnswer, inhibitProgressTone, associatedData, callingNumber);
        }

        return this.makeCall(deviceId, callee, autoAnswer, inhibitProgressTone, associatedData, callingNumber, null);
    }

    @Override
    public boolean makeCall(String deviceId, String callee, boolean autoAnswer, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeCall() called with: deviceId={}, callee={}, autoAnswer={}, loginName={}",
                    deviceId, callee, autoAnswer, loginName);
        }

        return this.makeCall(deviceId, callee, autoAnswer, false, (CorrelatorData) null, null, loginName);
    }

    @Override
    public boolean makeCall(String deviceId, String callee, boolean autoAnswer) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeCall() called with: deviceId={}, callee={}, autoAnswer={}", deviceId, callee, autoAnswer);
        }

        return this.makeCall(deviceId, callee, autoAnswer, null);
    }

    @Override
    public boolean makePrivateCall(String deviceId, String callee, boolean autoAnswer, String pin, String secretCode,
            String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("makePrivateCall() called with: deviceId={}, callee={}, autoAnswer={}, pin=***, secretCode=***, loginName={}",
                    deviceId, callee, autoAnswer, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new MakeCallRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(callee, "callee"), autoAnswer, false, null, pin, secretCode, null, null));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean makePrivateCall(String deviceId, String callee, boolean autoAnswer, String pin, String secretCode) {
        if (logger.isDebugEnabled()) {
            logger.debug("makePrivateCall() called with: deviceId={}, callee={}, autoAnswer={}, pin=***, secretCode=***",
                    deviceId, callee, autoAnswer);
        }

        return this.makePrivateCall(deviceId, callee, autoAnswer, pin, secretCode, null);
    }

    @Override
    public boolean makeBusinessCall(String deviceId, String callee, boolean autoAnswer, String businessCode,
            String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeBusinessCall() called with: deviceId={}, callee={}, autoAnswer={}, businessCode={}, loginName={}",
                    deviceId, callee, autoAnswer, businessCode, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new MakeCallRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(callee, "callee"), autoAnswer, false, null, null, null,
                AssertUtil.requireNotEmpty(businessCode, "businessCode"), null));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean makeBusinessCall(String deviceId, String callee, boolean autoAnswer, String businessCode) {
        if (logger.isDebugEnabled()) {
            logger.debug("makeBusinessCall() called with: deviceId={}, callee={}, autoAnswer={}, businessCode={}",
                    deviceId, callee, autoAnswer, businessCode);
        }

        return this.makeBusinessCall(deviceId, callee, autoAnswer, businessCode, null);
    }

    @Override
    public boolean alternate(String callRef, String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("alternate() called with: callRef={}, deviceId={}", callRef, deviceId);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "alternate");

        String json = gson.toJson(new DeviceIdRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean answer(String callRef, String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("answer() called with: callRef={}, deviceId={}", callRef, deviceId);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "answer");

        String json = gson.toJson(new DeviceIdRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean attachData(String callRef, String deviceId, CorrelatorData correlatorData) {
        if (logger.isDebugEnabled()) {
            logger.debug("attachData() called with: callRef={}, deviceId={}, correlatorData={}", callRef, deviceId, correlatorData);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "attachdata");

        String json = gson.toJson(
                new SendAssociatedDataRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"), correlatorData));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean blindTransfer(String callRef, String transferTo, boolean anonymous, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("blindTransfer() called with: callRef={}, transferTo={}, anonymous={}, loginName={}",
                    callRef, transferTo, anonymous, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "blindtransfer");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new BlindTransferRequest(AssertUtil.requireNotEmpty(transferTo, "transferTo"), anonymous));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean blindTransfer(String callRef, String transferTo, boolean anonymous) {
        if (logger.isDebugEnabled()) {
            logger.debug("blindTransfer() called with: callRef={}, transferTo={}, anonymous={}", callRef, transferTo, anonymous);
        }

        return this.blindTransfer(callRef, transferTo, anonymous, null);
    }

    @Override
    public boolean blindTransfer(String callRef, String transferTo) {
        if (logger.isDebugEnabled()) {
            logger.debug("blindTransfer() called with: callRef={}, transferTo={}", callRef, transferTo);
        }

        return this.blindTransfer(callRef, transferTo, false);
    }

    @Override
    public boolean callback(String callRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("callback() called with: callRef={}, loginName={}", callRef, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "callback");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean callback(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("callback() called with: callRef={}", callRef);
        }

        return this.callback(callRef, null);
    }

    @Override
    public Collection<Leg> getLegs(String callRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getLegs() called with: callRef={}, loginName={}", callRef, loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "deviceLegs");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        LegList legs = getResult(response, LegList.class);
        if (legs == null) {
            return null;
        } else {
            return unmodifiableOrEmpty(legs.legs);
        }
    }

    @Override
    public Collection<Leg> getLegs(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("getLegs() called with: callRef={}", callRef);
        }

        return this.getLegs(callRef, null);
    }

    @Override
    public Leg getLeg(String callRef, String legId, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getLeg() called with: callRef={}, legId={}, loginName={}", callRef, legId, loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "deviceLegs",
                AssertUtil.requireNotEmpty(legId, "legId"));

        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, Leg.class);
    }

    @Override
    public Leg getLeg(String callRef, String legId) {
        if (logger.isDebugEnabled()) {
            logger.debug("getLeg() called with: callRef={}, legId={}", callRef, legId);
        }

        return this.getLeg(callRef, legId, null);
    }

    @Override
    public boolean dropme(String callRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("dropme() called with: callRef={}, loginName={}", callRef, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "dropme");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean dropme(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("dropme() called with: callRef={}", callRef);
        }

        return this.dropme(callRef, null);
    }

    @Override
    public boolean hold(String callRef, String deviceId, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("hold() called with: callRef={}, deviceId={}, loginName={}", callRef, deviceId, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "hold");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new DeviceIdRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean hold(String callRef, String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("hold() called with: callRef={}, deviceId={}", callRef, deviceId);
        }

        return this.hold(callRef, deviceId, null);
    }

    @Override
    public boolean merge(String callRef, String heldCallRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("merge() called with: callRef={}, heldCallRef={}, loginName={}", callRef, heldCallRef, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "merge");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new HeldCallRequest(AssertUtil.requireNotEmpty(heldCallRef, "heldCallRef")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean merge(String callRef, String heldCallRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("merge() called with: callRef={}, heldCallRef={}", callRef, heldCallRef);
        }

        return this.merge(callRef, heldCallRef, null);
    }

    @Override
    public boolean overflowToVoiceMail(String callRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("overflowToVoiceMail() called with: callRef={}, loginName={}", callRef, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "overflowToVoiceMail");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean overflowToVoiceMail(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("overflowToVoiceMail() called with: callRef={}", callRef);
        }

        return this.overflowToVoiceMail(callRef, null);
    }

    @Override
    public TelephonicState getState(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getState() called with: loginName={}", loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "state");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        O2GTelephonicState o2gTelephonicState = getResult(response, O2GTelephonicState.class);
        if (o2gTelephonicState != null) {
            return o2gTelephonicState.toTelephonicState();
        } else {
            return null;
        }
    }

    @Override
    public TelephonicState getState() {
        if (logger.isDebugEnabled()) {
            logger.debug("getState()");
        }

        return this.getState(null);
    }

    @Override
    public boolean park(String callRef, String parkTo, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("park() called with: callRef={}, parkTo={}, loginName={}", callRef, parkTo, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "park");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new ParkRequest(parkTo));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean park(String callRef, String parkTo) {
        if (logger.isDebugEnabled()) {
            logger.debug("park() called with: callRef={}, parkTo={}", callRef, parkTo);
        }

        return this.park(callRef, parkTo, null);
    }

    @Override
    public boolean park(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("park() called with: callRef={}", callRef);
        }

        return this.park(callRef, null, null);
    }

    @Override
    public Collection<Participant> getParticipants(String callRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getParticipants() called with: callRef={}, loginName={}", callRef, loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "participants");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        ParticipantList participants = getResult(response, ParticipantList.class);
        if (participants == null) {
            return null;
        } else {
            return unmodifiableOrEmpty(participants.participants);
        }
    }

    @Override
    public Collection<Participant> getParticipants(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("getParticipants() called with: callRef={}", callRef);
        }

        return this.getParticipants(callRef, null);
    }

    @Override
    public Participant getParticipant(String callRef, String participantId, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getParticipant() called with: callRef={}, participantId={}, loginName={}", callRef, participantId, loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "participants",
                AssertUtil.requireNotEmpty(participantId, "participantId"));
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, Participant.class);
    }

    @Override
    public Participant getParticipant(String callRef, String participantId) {
        if (logger.isDebugEnabled()) {
            logger.debug("getParticipant() called with: callRef={}, participantId={}", callRef, participantId);
        }

        return this.getParticipant(callRef, participantId, null);
    }

    @Override
    public boolean dropParticipant(String callRef, String participantId, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("dropParticipant() called with: callRef={}, participantId={}, loginName={}", callRef, participantId, loginName);
        }

        URI uriDelete = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"),
                "participants", AssertUtil.requireNotEmpty(participantId, "participantId"));
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean dropParticipant(String callRef, String participantId) {
        if (logger.isDebugEnabled()) {
            logger.debug("dropParticipant() called with: callRef={}, participantId={}", callRef, participantId);
        }

        return this.dropParticipant(callRef, participantId, null);
    }

    @Override
    public boolean reconnect(String callRef, String deviceId, String enquiryCallRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("reconnect() called with: callRef={}, deviceId={}, enquiryCallRef={}, loginName={}",
                    callRef, deviceId, enquiryCallRef, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "reconnect");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new ReconnectRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(enquiryCallRef, "enquiryCallRef")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean reconnect(String callRef, String deviceId, String enquiryCallRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("reconnect() called with: callRef={}, deviceId={}, enquiryCallRef={}", callRef, deviceId, enquiryCallRef);
        }

        return this.reconnect(callRef, deviceId, enquiryCallRef, null);
    }

    @Override
    public boolean doRecordAction(String callRef, RecordingAction action, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("doRecordAction() called with: callRef={}, action={}, loginName={}", callRef, action, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "recording");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        uriPost = URIBuilder.appendQuery(uriPost, "action", action.toString().toLowerCase());

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean doRecordAction(String callRef, RecordingAction action) {
        if (logger.isDebugEnabled()) {
            logger.debug("doRecordAction() called with: callRef={}, action={}", callRef, action);
        }

        return this.doRecordAction(callRef, action, null);
    }

    @Override
    public boolean redirect(String callRef, String redirectTo, boolean anonymous, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("redirect() called with: callRef={}, redirectTo={}, anonymous={}, loginName={}",
                    callRef, redirectTo, anonymous, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "redirect");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new RedirectRequest(AssertUtil.requireNotEmpty(redirectTo, "transferTo"), anonymous));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean redirect(String callRef, String redirectTo, boolean anonymous) {
        if (logger.isDebugEnabled()) {
            logger.debug("redirect() called with: callRef={}, redirectTo={}, anonymous={}", callRef, redirectTo, anonymous);
        }

        return this.redirect(callRef, redirectTo, anonymous, null);
    }

    @Override
    public boolean redirect(String callRef, String redirectTo) {
        if (logger.isDebugEnabled()) {
            logger.debug("redirect() called with: callRef={}, redirectTo={}", callRef, redirectTo);
        }

        return this.redirect(callRef, redirectTo, false);
    }

    @Override
    public boolean retrieve(String callRef, String deviceId, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("retrieve() called with: callRef={}, deviceId={}, loginName={}", callRef, deviceId, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "retrieve");

        String json = gson.toJson(new DeviceIdRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean retrieve(String callRef, String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("retrieve() called with: callRef={}, deviceId={}", callRef, deviceId);
        }

        return this.retrieve(callRef, deviceId, null);
    }

    @Override
    public boolean sendDtmf(String callRef, String deviceId, String number) {
        if (logger.isDebugEnabled()) {
            logger.debug("sendDtmf() called with: callRef={}, deviceId={}, number={}", callRef, deviceId, number);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "sendDtmf");

        String json = gson.toJson(new SendDtmfRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(number, "number")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean sendAccountInfo(String callRef, String deviceId, String accountInfo) {
        if (logger.isDebugEnabled()) {
            logger.debug("sendAccountInfo() called with: callRef={}, deviceId={}, accountInfo={}", callRef, deviceId, accountInfo);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "sendaccountinfo");

        String json = gson.toJson(new SendAccountInfoRequest(AssertUtil.requireNotEmpty(deviceId, "deviceId"),
                AssertUtil.requireNotEmpty(accountInfo, "accountInfo")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean transfer(String callRef, String heldCallRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("transfer() called with: callRef={}, heldCallRef={}, loginName={}", callRef, heldCallRef, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"), "transfer");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new HeldCallRequest(AssertUtil.requireNotEmpty(heldCallRef, "heldCallRef")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean transfer(String callRef, String heldCallRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("transfer() called with: callRef={}, heldCallRef={}", callRef, heldCallRef);
        }

        return this.transfer(callRef, heldCallRef, null);
    }

    @Override
    public boolean deskSharingLogOn(String dssDeviceNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("deskSharingLogOn() called with: dssDeviceNumber={}, loginName={}", dssDeviceNumber, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "deskSharing");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new DSLogOnRequest(AssertUtil.requireNotEmpty(dssDeviceNumber, "dssDeviceNumber")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deskSharingLogOn(String dssDeviceNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("deskSharingLogOn() called with: dssDeviceNumber={}", dssDeviceNumber);
        }

        return this.deskSharingLogOn(dssDeviceNumber, null);
    }

    @Override
    public boolean deskSharingLogOff(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("deskSharingLogOff() called with: loginName={}", loginName);
        }

        URI uriDelete = URIBuilder.appendPath(uri, "deskSharing");
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deskSharingLogOff() {
        if (logger.isDebugEnabled()) {
            logger.debug("deskSharingLogOff()");
        }

        return this.deskSharingLogOff(null);
    }

    @Override
    public Collection<DeviceState> getDevicesState(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getDevicesState() called with: loginName={}", loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "devices");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        DeviceStateList deviceStates = getResult(response, DeviceStateList.class);
        if (deviceStates == null) {
            return null;
        } else {
            return unmodifiableOrEmpty(deviceStates.deviceStates);
        }
    }

    @Override
    public Collection<DeviceState> getDevicesState() {
        if (logger.isDebugEnabled()) {
            logger.debug("getDevicesState()");
        }

        return this.getDevicesState(null);
    }

    @Override
    public DeviceState getDeviceState(String deviceId, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getDeviceState() called with: deviceId={}, loginName={}", deviceId, loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"));
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, DeviceState.class);
    }

    @Override
    public DeviceState getDeviceState(String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("getDeviceState() called with: deviceId={}", deviceId);
        }

        return this.getDeviceState(deviceId, null);
    }

    @Override
    public boolean pickUp(String deviceId, String otherCallRef, String otherPhoneNumber, boolean autoAnswer) {
        if (logger.isDebugEnabled()) {
            logger.debug("pickUp() called with: deviceId={}, otherCallRef={}, otherPhoneNumber={}, autoAnswer={}",
                    deviceId, otherCallRef, otherPhoneNumber, autoAnswer);
        }

        URI uriPost = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"), "pickup");

        String json = gson.toJson(new PickupRequest(AssertUtil.requireNotEmpty(otherCallRef, "otherCallRef"),
                AssertUtil.requireNotEmpty(otherPhoneNumber, "otherPhoneNumber"), autoAnswer));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean pickUp(String deviceId, String otherCallRef, String otherPhoneNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("pickUp() called with: deviceId={}, otherCallRef={}, otherPhoneNumber={}",
                    deviceId, otherCallRef, otherPhoneNumber);
        }

        return this.pickUp(deviceId, otherCallRef, otherPhoneNumber, false);
    }

    @Override
    public boolean intrusion(String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("intrusion() called with: deviceId={}", deviceId);
        }
        
        URI uriPost = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"),
				"intrusion");
		HttpRequest request = HttpUtil.POST(uriPost);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

    @Override
    public boolean toggleInterphony(String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("toggleInterphony() called with: deviceId={}", deviceId);
        }

        URI uriPost = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"), "ithmicro");

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean unPark(String heldCallRef, String deviceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("unPark() called with: heldCallRef={}, deviceId={}", heldCallRef, deviceId);
        }

        URI uriPost = URIBuilder.appendPath(uri, "devices", AssertUtil.requireNotEmpty(deviceId, "deviceId"), "unpark");

        String json = gson.toJson(new HeldCallRequest(AssertUtil.requireNotEmpty(heldCallRef, "heldCallRef")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public HuntingGroupStatus getHuntingGroupStatus(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getHuntingGroupStatus() called with: loginName={}", loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "huntingGroupLogOn");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, HuntingGroupStatus.class);
    }

    @Override
    public HuntingGroupStatus getHuntingGroupStatus() {
        if (logger.isDebugEnabled()) {
            logger.debug("getHuntingGroupStatus()");
        }

        return this.getHuntingGroupStatus(null);
    }

    @Override
    public boolean huntingGroupLogOn(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("huntingGroupLogOn() called with: loginName={}", loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "huntingGroupLogOn");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean huntingGroupLogOn() {
        if (logger.isDebugEnabled()) {
            logger.debug("huntingGroupLogOn()");
        }

        return this.huntingGroupLogOn(null);
    }

    @Override
    public boolean huntingGroupLogOff(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("huntingGroupLogOff() called with: loginName={}", loginName);
        }

        URI uriDelete = URIBuilder.appendPath(uri, "huntingGroupLogOn");
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean huntingGroupLogOff() {
        if (logger.isDebugEnabled()) {
            logger.debug("huntingGroupLogOff()");
        }

        return this.huntingGroupLogOff(null);
    }

    @Override
    public boolean addMeToHuntingGroup(String hgNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("addMeToHuntingGroup() called with: hgNumber={}, loginName={}", hgNumber, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "huntingGroupMember", AssertUtil.requireNotEmpty(hgNumber, "hgNumber"));
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean addMeToHuntingGroup(String hgNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("addMeToHuntingGroup() called with: hgNumber={}", hgNumber);
        }

        return this.addMeToHuntingGroup(hgNumber, null);
    }

    @Override
    public boolean addHuntingGroupMember(String hgNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("addHuntingGroupMember() called with: hgNumber={}, loginName={}", hgNumber, loginName);
        }

        return this.addMeToHuntingGroup(hgNumber, loginName);
    }

    @Override
    public boolean addHuntingGroupMember(String hgNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("addHuntingGroupMember() called with: hgNumber={}", hgNumber);
        }

        return this.addMeToHuntingGroup(hgNumber, null);
    }

    @Override
    public boolean removeMeFromHuntingGroup(String hgNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("removeMeFromHuntingGroup() called with: hgNumber={}, loginName={}", hgNumber, loginName);
        }

        URI uriDelete = URIBuilder.appendPath(uri, "huntingGroupMember", AssertUtil.requireNotEmpty(hgNumber, "hgNumber"));
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean removeMeFromHuntingGroup(String hgNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("removeMeFromHuntingGroup() called with: hgNumber={}", hgNumber);
        }

        return this.removeMeFromHuntingGroup(hgNumber, null);
    }

    @Override
    public boolean deleteHuntingGroupMember(String hgNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteHuntingGroupMember() called with: hgNumber={}, loginName={}", hgNumber, loginName);
        }

        return this.removeMeFromHuntingGroup(hgNumber, loginName);
    }

    @Override
    public boolean deleteHuntingGroupMember(String hgNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteHuntingGroupMember() called with: hgNumber={}", hgNumber);
        }

        return this.removeMeFromHuntingGroup(hgNumber, null);
    }

    @Override
    public HuntingGroups queryHuntingGroups(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("queryHuntingGroups() called with: loginName={}", loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "huntingGroups");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, HuntingGroups.class);
    }

    @Override
    public HuntingGroups queryHuntingGroups() {
        if (logger.isDebugEnabled()) {
            logger.debug("queryHuntingGroups()");
        }

        return this.queryHuntingGroups(null);
    }

    @Override
    public Collection<Callback> getCallbacks(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getCallbacks() called with: loginName={}", loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "incomingCallbacks");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        CallbackList callbacks = getResult(response, CallbackList.class);
        if (callbacks == null) {
            return null;
        } else {
            return unmodifiableOrEmpty(callbacks.callbacks);
        }
    }

    @Override
    public Collection<Callback> getCallbacks() {
        if (logger.isDebugEnabled()) {
            logger.debug("getCallbacks()");
        }

        return this.getCallbacks(null);
    }

    @Override
    public boolean deleteCallbacks(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteCallbacks() called with: loginName={}", loginName);
        }

        URI uriDelete = URIBuilder.appendPath(uri, "incomingCallbacks");
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deleteCallbacks() {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteCallbacks()");
        }

        return this.deleteCallbacks(null);
    }

    @Override
    public MiniMessage getMiniMessage(String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getMiniMessage() called with: loginName={}", loginName);
        }

        URI uriGet = URIBuilder.appendPath(uri, "miniMessages");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, MiniMessage.class);
    }

    @Override
    public MiniMessage getMiniMessage() {
        if (logger.isDebugEnabled()) {
            logger.debug("getMiniMessage()");
        }

        return this.getMiniMessage(null);
    }

    @Override
    public boolean sendMiniMessage(String recipient, String message, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("sendMiniMessage() called with: recipient={}, message={}, loginName={}", recipient, message, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "miniMessages");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new MiniMessageRequest(AssertUtil.requireNotEmpty(recipient, "recipient"),
                AssertUtil.requireNotEmpty(message, "message")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean sendMiniMessage(String recipient, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug("sendMiniMessage() called with: recipient={}, message={}", recipient, message);
        }

        return this.sendMiniMessage(recipient, message, null);
    }

    @Override
    public boolean requestCallback(String callee, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("requestCallback() called with: callee={}, loginName={}", callee, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "outgoingCallbacks");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new CallbackRequest(AssertUtil.requireNotEmpty(callee, "callee")));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean requestCallback(String callee) {
        if (logger.isDebugEnabled()) {
            logger.debug("requestCallback() called with: callee={}", callee);
        }

        return this.requestCallback(callee, null);
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
        if (logger.isDebugEnabled()) {
            logger.debug("requestSnapshot()");
        }

        return this.requestSnapshot(null);
    }

    @Override
    public boolean deleteCallback(String callbackId, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteCallback() called with: callbackId={}, loginName={}", callbackId, loginName);
        }

		URI uriDelete = URIBuilder.appendPath(uri, "incomingCallbacks",
				AssertUtil.requireNotEmpty(callbackId, "callbackId"));
		if (loginName != null) {
			uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
		}

		HttpRequest request = HttpUtil.DELETE(uriDelete);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

    @Override
    public boolean deleteCallback(String callbackId) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteCallback() called with: callbackId={}", callbackId);
        }

        return this.deleteCallback(callbackId, null);
    }

    @Override
    public boolean release(String callRef, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("release() called with: callRef={}, loginName={}", callRef, loginName);
        }

		URI uriDelete = URIBuilder.appendPath(uri, "calls", AssertUtil.requireNotEmpty(callRef, "callRef"));
		if (loginName != null) {
			uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
		}

		HttpRequest request = HttpUtil.DELETE(uriDelete);
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		return isSucceeded(response);
	}

    @Override
    public boolean release(String callRef) {
        if (logger.isDebugEnabled()) {
            logger.debug("release() called with: callRef={}", callRef);
        }

        return this.release(callRef, null);
    }

    @Override
    public PilotInfo getPilotInfo(int nodeId, String pilotNumber, String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getPilotInfo() called with: nodeId={}, pilotNumber={}, loginName={}", nodeId, pilotNumber, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "pilots", String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")),
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"), "transferInfo");

        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getResult(response, PilotInfo.class);
    }

    @Override
    public PilotInfo getPilotInfo(int nodeId, String pilotNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("getPilotInfo() called with: nodeId={}, pilotNumber={}", nodeId, pilotNumber);
        }

        return this.getPilotInfo(nodeId, pilotNumber, (String) null);
    }

    @Override
    public PilotInfo getPilotInfo(int nodeId, String pilotNumber, PilotTransferQueryParameters pilotTransferQueryParam,
            String loginName) {
        if (logger.isDebugEnabled()) {
            logger.debug("getPilotInfo() called with: nodeId={}, pilotNumber={}, pilotTransferQueryParam={}, loginName={}",
                    nodeId, pilotNumber, pilotTransferQueryParam, loginName);
        }

        URI uriPost = URIBuilder.appendPath(uri, "pilots", String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")),
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"), "transferInfo");

        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        AssertUtil.requireNotNull(pilotTransferQueryParam, "pilotTransferQueryParam");
        ACRSkills skills = null;
        if (pilotTransferQueryParam.getCallProfile() != null) {
            skills = new ACRSkills(pilotTransferQueryParam.getCallProfile().getSkills());
        }

        String json = gson.toJson(new PilotQueryParam(pilotTransferQueryParam.getAgentNumber(), skills,
                pilotTransferQueryParam.getPriorityTransfer(), pilotTransferQueryParam.getSupervisedTransfer()));

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getResult(response, PilotInfo.class);
    }

    @Override
    public PilotInfo getPilotInfo(int nodeId, String pilotNumber,
            PilotTransferQueryParameters pilotTransferQueryParam) {
        if (logger.isDebugEnabled()) {
            logger.debug("getPilotInfo() called with: nodeId={}, pilotNumber={}, pilotTransferQueryParam={}",
                    nodeId, pilotNumber, pilotTransferQueryParam);
        }

        URI uriPost = URIBuilder.appendPath(uri, "pilots", String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")),
                AssertUtil.requireNotEmpty(pilotNumber, "pilotNumber"), "transferInfo");

        AssertUtil.requireNotNull(pilotTransferQueryParam, "pilotTransferQueryParam");
        ACRSkills skills = null;
        if (pilotTransferQueryParam.getCallProfile() != null) {
            skills = new ACRSkills(pilotTransferQueryParam.getCallProfile().getSkills());
        }

        String json = gson.toJson(new PilotQueryParam(pilotTransferQueryParam.getAgentNumber(), skills,
                pilotTransferQueryParam.getPriorityTransfer(), pilotTransferQueryParam.getSupervisedTransfer()));
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return getResult(response, PilotInfo.class);
    }
}
