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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.MessagingService;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.messaging.MailBox;
import com.ale.o2g.types.messaging.MailBoxInfo;
import com.ale.o2g.types.messaging.VoiceMessage;

/**
 *
 */
public class MessagingRest extends AbstractRESTService implements MessagingService {

    static class MailBoxes {
        private Collection<MailBox> mailboxes;
    }

    static class VoicemailsList {
        public Collection<VoiceMessage> voicemails;
    }

    static record ConnectRequest(String password) {}
    
    public MessagingRest(HttpClient httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public Collection<MailBox> getMailBoxes(String loginName) {

        URI uriGet = uri;
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        MailBoxes mailboxes = getResult(response, MailBoxes.class);
        if (mailboxes == null) {
            return null;
        }
        else {
            return mailboxes.mailboxes;
        }
    }

    @Override
    public Collection<MailBox> getMailBoxes() {
        return this.getMailBoxes(null);
    }

    @Override
    public MailBoxInfo getMailBoxInfo(String mailBoxId, String password, String loginName) {

        URI uriPost = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(mailBoxId, "mailBoxId"));
        if (loginName != null) {
            uriPost = URIBuilder.appendQuery(uriPost, "loginName", loginName);
        }

        HttpRequest request;
        if (password != null) {
            String json = gson.toJson(new ConnectRequest(password));
            request = HttpUtil.POST(uriPost, json);
        }
        else {
            uriPost = URIBuilder.appendQuery(uriPost, "withUserPwd");
            request = HttpUtil.POST(uriPost);
        }
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        return getResult(response, MailBoxInfo.class);
    }

    @Override
    public MailBoxInfo getMailBoxInfo(String mailBoxId, String password) {
        return this.getMailBoxInfo(mailBoxId, password, null);
    }

    @Override
    public Collection<VoiceMessage> getVoiceMessages(String mailboxId, boolean newOnly, Integer offset, Integer limit,
            String loginName) {

        URI uriGet = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(mailboxId, "mailboxId"), "voicemails");
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }
        
        if (offset != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "offset", String.valueOf(offset));
        }

        if (limit != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "offset", String.valueOf(limit));
        }

        if (newOnly) {
            uriGet = URIBuilder.appendQuery(uriGet, "newOnly", String.valueOf(newOnly));
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        VoicemailsList voicemails = getResult(response, VoicemailsList.class);
        if (voicemails == null) {
            return null;
        }
        else {
            return voicemails.voicemails;
        }
    }

    @Override
    public Collection<VoiceMessage> getVoiceMessages(String mailboxId, boolean newOnly, Integer offset, Integer limit) {
        return this.getVoiceMessages(mailboxId, newOnly, offset, limit, null);
    }

    @Override
    public Collection<VoiceMessage> getVoiceMessages(String mailboxId, boolean newOnly) {
        return this.getVoiceMessages(mailboxId, newOnly, null, null);
    }

    @Override
    public Collection<VoiceMessage> getVoiceMessages(String mailboxId) {
        return this.getVoiceMessages(mailboxId, false);
    }

    @Override
    public boolean deleteVoiceMessages(String mailboxId, String[] msgIds, String loginName) {

        URI uriDelete = URIBuilder.appendPath(uri, AssertUtil.requireNotEmpty(mailboxId, "mailboxId"), "voicemails");
        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }
        
        if (msgIds != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "msgIds", String.join(";", msgIds));
        }        

        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deleteVoiceMessages(String mailboxId, String[] msgIds) {
        return this.deleteVoiceMessages(mailboxId, msgIds, null);
    }

    @Override
    public boolean deleteVoiceMessage(String mailboxId, String voicemailId, String loginName) {

        URI uriDelete = URIBuilder.appendPath(
                uri, 
                AssertUtil.requireNotEmpty(mailboxId, "mailboxId"), 
                "voicemails",
                AssertUtil.requireNotEmpty(voicemailId, "voicemailId"));

        if (loginName != null) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "loginName", loginName);
        }
        
        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        return isSucceeded(response);
    }

    @Override
    public boolean deleteVoiceMessage(String mailboxId, String voicemailId) {
        return this.deleteVoiceMessage(mailboxId, voicemailId, null);
    }

    @Override
    public Path downloadVoiceMessage(String mailboxId, String voicemailId, String wavPath, String loginName) throws IOException {

        URI uriGet = URIBuilder.appendPath(
                uri, 
                AssertUtil.requireNotEmpty(mailboxId, "mailboxId"), 
                "voicemails",
                AssertUtil.requireNotEmpty(voicemailId, "voicemailId"));
        
        if (loginName != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "loginName", loginName);
        }
        
        HttpRequest request = HttpUtil.GET(uriGet);
        
        // Create a default temporary file
        Path downloadedFile;
        if (wavPath != null) {
            downloadedFile = Path.of(wavPath);
        }
        else {
            downloadedFile = File.createTempFile("vmsg", ".wav").toPath();
        }
        
        CompletableFuture<HttpResponse<Path>> response = 
                httpClient.sendAsync(request, BodyHandlers.ofFile(downloadedFile));
        return downloadedFile(wavPath, response);
    }

    @Override
    public Path downloadVoiceMessage(String mailboxId, String voicemailId, String wavPath) throws IOException {
        return this.downloadVoiceMessage(mailboxId, voicemailId, wavPath, null);
    }

}
