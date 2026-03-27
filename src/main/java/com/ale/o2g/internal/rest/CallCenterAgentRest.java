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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.CallCenterAgentService;
import com.ale.o2g.internal.types.cca.O2GAgentConfig;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpClientWrapper;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.RestErrorInfo;
import com.ale.o2g.types.cca.IntrusionMode;
import com.ale.o2g.types.cca.OperatorConfiguration;
import com.ale.o2g.types.cca.OperatorState;
import com.ale.o2g.types.cca.WithdrawReason;

/**
 *
 */
public class CallCenterAgentRest extends AbstractRESTService implements CallCenterAgentService {

<<<<<<< HEAD
	final static Logger logger = LoggerFactory.getLogger(CallCenterAgentService.class);
	
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
    private static record LogOnAgentRequest(String proAcdDeviceNumber, String pgGroupNumber, boolean headset) {
    }

    private static record PgRequest(String pgGroupNumber) {
    }

    private static record WithdrawAgentRequest(int reasonIndex) {
    }

    private static record PermanentListeningRequest(String agentNumber) {
    }

    private static record IntrusionRequest(String agentNumber, IntrusionMode mode) {
    }

    private static record ChangeIntrusionModeRequest(IntrusionMode mode) {
    }

    private static record AgentSkillActivation(List<Integer> skills) {
    }

    static class WithdrawReasons {
        private List<WithdrawReason> reasons;

        public final List<WithdrawReason> getReasons() {
            return reasons;
        }
    }

    /**
     * @param httpClient
     * @param uri
     */
    public CallCenterAgentRest(HttpClientWrapper httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public OperatorConfiguration getOperatorConfiguration(String loginName) {
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("getOperatorConfiguration() called with: loginName={}", loginName);
    	}

    	URI uriGet = URIBuilder.appendPath(uri, "config");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        O2GAgentConfig agentConfig = getResult(response, O2GAgentConfig.class);
        if (agentConfig == null) {
            return null;
        }
        else {
            return agentConfig.toOperatorConfiguration();
        }
    }

    @Override
    public OperatorState getOperatorState(String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("getOperatorState() called with: loginName={}", loginName);
    	}

        URI uriGet = URIBuilder.appendPath(uri, "state");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, OperatorState.class);
    }

    @Override
    public OperatorState getOperatorState() {
        return this.getOperatorState(null);
    }

    @Override
    public boolean logonOperator(String proAcdNumber, String pgNumber, boolean headset, String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("logonOperator() called with: proAcdNumber={}, pgNumber={}, headset={}, loginName={}", 
    				proAcdNumber, pgNumber, headset, loginName);
    	}

        URI uriPost = URIBuilder.appendPath(uri, "logon");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(
                new LogOnAgentRequest(AssertUtil.requireNotEmpty(proAcdNumber, "proAcdNumber"), pgNumber, headset));
<<<<<<< HEAD
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean logonOperator(String proAcdNumber, String pgNumber, boolean headset) {
        return this.logonOperator(proAcdNumber, pgNumber, headset, null);
    }

    @Override
    public boolean logoffOperator(String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("logoffOperator() called with: loginName={}", loginName);
    	}
    	
        URI uriPost = URIBuilder.appendPath(uri, "logoff");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean logoffOperator() {
        return this.logoffOperator(null);
    }

    @Override
    public boolean enterAgentGroup(String pgNumber, String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("enterAgentGroup() called with: pgNumber={}, loginName={}", pgNumber, loginName);
    	}
    	
        URI uriPost = URIBuilder.appendPath(uri, "enterPG");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }
<<<<<<< HEAD
=======

        String json = gson.toJson(new PgRequest(AssertUtil.requireNotEmpty(pgNumber, "pgNumber")));
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        String json = gson.toJson(new PgRequest(AssertUtil.requireNotEmpty(pgNumber, "pgNumber")));
    	if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
    	
        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean enterAgentGroup(String pgNumber) {
        return this.enterAgentGroup(pgNumber, null);
    }

    @Override
    public boolean exitAgentGroup(String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("exitAgentGroup() called with: loginName={}", loginName);
    	}
    	
=======

>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        // First get the operator state to get the processing group
        OperatorState operatorState = this.getOperatorState(loginName);
        
        if (operatorState.getPgNumber() == null) {
            // The supervisor is NOT in a group return an error

            lastError = Optional.ofNullable(new RestErrorInfo("Requester is not in a group", false) {
            });
            return false;
        }
        else {
            URI uriPost = URIBuilder.appendPath(uri, "exitPG");
            if (loginName != null) {
                uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
            }

            String json = gson
                    .toJson(new PgRequest(AssertUtil.requireNotEmpty(operatorState.getPgNumber(), "pgNumber")));
<<<<<<< HEAD

            if (logger.isDebugEnabled()) {
        		logger.debug("Request=: {}", json);
        	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

            HttpRequest request = HttpUtil.POST(uriPost, json);
            CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
            return isSucceeded(response);
        }
    }

    @Override
    public boolean exitAgentGroup() {
        return this.exitAgentGroup(null);
    }

    private boolean doAgentAction(String action, String loginName) {
        URI uriPost = URIBuilder.appendPath(uri, action);
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean setWrapup(String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("setWrapup() called with: loginName={}", loginName);
    	}

    	return this.doAgentAction("wrapUp", loginName);
    }

    @Override
    public boolean setWrapup() {
        return this.setWrapup(null);
    }

    @Override
    public boolean setReady(String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("setReady() called with: loginName={}", loginName);
    	}

        return this.doAgentAction("ready", loginName);
    }

    @Override
    public boolean setReady() {
        return this.setReady(null);
    }

    @Override
    public boolean setPause(String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("setPause() called with: loginName={}", loginName);
    	}

        return this.doAgentAction("pause", loginName);
    }

    @Override
    public boolean setPause() {
        return this.setPause(null);
    }

    @Override
    public boolean setWithdraw(WithdrawReason reason, String loginName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("setWithdraw() called with: reason={}, loginName={}", reason, loginName);
    	}

        URI uriPost = URIBuilder.appendPath(uri, "withdraw");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new WithdrawAgentRequest(AssertUtil.requireNotNull(reason.getIndex(), "reason")));
<<<<<<< HEAD

        if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean setWithdraw(WithdrawReason reason) {
        return this.setWithdraw(reason, null);
    }

    @Override
    public boolean requestPermanentListening(String agentNumber, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("requestPermanentListening() called with: agentNumber={}, loginName={}", agentNumber, loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        URI uriPost = URIBuilder.appendPath(uri, "permanentListening");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson
                .toJson(new PermanentListeningRequest(AssertUtil.requireNotEmpty(agentNumber, "agentNumber")));
<<<<<<< HEAD

        if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean requestPermanentListening(String agentNumber) {
        return this.requestPermanentListening(agentNumber, null);
    }

    @Override
    public boolean requestIntrusion(String agentNumber, IntrusionMode intrusionMode, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("requestIntrusion() called with: agentNumber={}, intrusionMode={}, loginName={}", agentNumber, intrusionMode, loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        URI uriPost = URIBuilder.appendPath(uri, "intrusion");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson
                .toJson(new IntrusionRequest(AssertUtil.requireNotEmpty(agentNumber, "agentNumber"), intrusionMode));
<<<<<<< HEAD

        if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean requestIntrusion(String agentNumber, IntrusionMode intrusionMode) {
        return this.requestIntrusion(agentNumber, intrusionMode, null);
    }

    @Override
    public boolean changeIntrusionMode(IntrusionMode newIntrusionMode, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("changeIntrusionMode() called with: newIntrusionMode={}, loginName={}", newIntrusionMode, loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        URI uriPut = URIBuilder.appendPath(uri, "intrusion");
        if (loginName != null) {
            uriPut = URIBuilder.appendQuery(uriPut, "loginName", loginName);
        }

        String json = gson.toJson(new ChangeIntrusionModeRequest(newIntrusionMode));

        if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.PUT(uriPut, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean changeIntrusionMode(IntrusionMode newIntrusionMode) {
        return this.changeIntrusionMode(newIntrusionMode, null);
    }

    @Override
    public boolean requestSupervisorHelp(String loginName) {
        return this.doAgentAction("supervisorHelp", loginName);
    }

    @Override
    public boolean requestSupervisorHelp() {
        return this.requestSupervisorHelp(null);
    }

    private boolean doCancelSupervisorHelpRequest(String otherNumber, String loginName) {

        URI uriDelete = URIBuilder.appendPath(uri, "intrusion");
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean rejectAgentHelpRequest(String agentNumber, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("rejectAgentHelpRequest() called with: agentNumber={}, loginName={}", agentNumber, loginName);
    	}

=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        return this.doCancelSupervisorHelpRequest(AssertUtil.requireNotEmpty(agentNumber, "agentNumber"), loginName);
    }

    @Override
    public boolean rejectAgentHelpRequest(String agentNumber) {
        return this.rejectAgentHelpRequest(agentNumber, null);
    }

    @Override
    public boolean cancelSupervisorHelpRequest(String supervisorNumber, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("cancelSupervisorHelpRequest() called with: supervisorNumber={}, loginName={}", supervisorNumber, loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
        return this.doCancelSupervisorHelpRequest(AssertUtil.requireNotEmpty(supervisorNumber, "supervisorNumber"),
                loginName);
    }

    @Override
    public boolean cancelSupervisorHelpRequest(String supervisorNumber) {
        return this.cancelSupervisorHelpRequest(supervisorNumber, null);
    }

    @Override
    public boolean requestSnaphot(String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("requestSnaphot() called with: loginName={}", loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        URI uriPost = URIBuilder.appendPath(uri, "state/snapshot");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.POST(uriPost);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);

    }

    @Override
    public boolean requestSnaphot() {
        return this.requestSnaphot(null);
    }

    @Override
    public List<WithdrawReason> getWithdrawReasons(String pgNumber, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("getWithdrawReasons() called with: pbNumber={}, loginName={}", pgNumber, loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        URI uriGet = URIBuilder.appendPath(uri, "withdrawReasons");
        uriGet = URIBuilder.appendQuery(uriGet, "pgNumber", AssertUtil.requireNotEmpty(pgNumber, "pgNumber"));

        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        WithdrawReasons reasons = getResult(response, WithdrawReasons.class);
        if (reasons == null) {
            return null;
        }
        else {
            return Collections.unmodifiableList(reasons.getReasons());
        }
    }

    @Override
    public List<WithdrawReason> getWithdrawReasons(String pgNumber) {
        return this.getWithdrawReasons(pgNumber, null);
    }

    @Override
    public boolean activateSkills(List<Integer> skills, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("activateSkills() called with: skills={}, loginName={}", skills, loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        URI uriPost = URIBuilder.appendPath(uri, "config/skills/activate");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new AgentSkillActivation(skills));

        if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean activateSkills(List<Integer> skills) {
        return this.activateSkills(skills, null);
    }

    @Override
    public boolean deactivateSkills(List<Integer> skills, String loginName) {
<<<<<<< HEAD
    	if (logger.isDebugEnabled()) {
    		logger.debug("deactivateSkills() called with: skills={}, loginName={}", skills, loginName);
    	}
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73

        URI uriPost = URIBuilder.appendPath(uri, "config/skills/deactivate");
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        String json = gson.toJson(new AgentSkillActivation(skills));

        if (logger.isDebugEnabled()) {
    		logger.debug("Request=: {}", json);
    	}

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deactivateSkills(List<Integer> skills) {
        return this.deactivateSkills(skills, null);
    }

 }
