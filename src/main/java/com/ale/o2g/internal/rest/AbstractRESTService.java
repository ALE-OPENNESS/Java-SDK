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

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ale.o2g.O2GRuntimeException;
import com.ale.o2g.internal.util.AnnotationExclusionStrategy;
import com.ale.o2g.types.RestErrorInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 *
 */
public abstract class AbstractRESTService {

    /** The "attachment" disposition-type and separator. */
    static final String DISPOSITION_TYPE = "attachment;";

    /** The "filename" parameter. */
    static final Pattern FILENAME = Pattern.compile("filename\\s*=", CASE_INSENSITIVE);

    static final List<String> PROHIBITED = List.of(".", "..", "", "~" , "|");
    
    protected URI uri;
    protected HttpClient httpClient;
    protected Gson gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();

    protected static Optional<RestErrorInfo> lastError = Optional.empty();

    public AbstractRESTService(HttpClient httpClient, URI uri) {
        this.uri = uri;
        this.httpClient = httpClient;
    }
    
    protected boolean isSucceeded(int statusCode) {
        return (statusCode >= 200) && (statusCode <= 299);
    }

    protected <T> Optional<T> getOptionalResult(CompletableFuture<HttpResponse<String>> response, Class<T> objClass) {

        try {
            HttpResponse<String> httpResponse = response.get();

            if (isSucceeded(httpResponse.statusCode())) {
                lastError = Optional.empty();
                return Optional.of(gson.fromJson(httpResponse.body(), objClass));
            }
            else {

                try {
                    lastError = Optional.of(gson.fromJson(httpResponse.body(), RestErrorInfo.class));
                }
                catch (JsonSyntaxException e) {
                    lastError = Optional.empty();
                }
                return Optional.empty();
            }
        }
        catch (InterruptedException | ExecutionException e) {
            throw new O2GRuntimeException(e);
        }
    }
    
    
    protected String asString(CompletableFuture<HttpResponse<String>> response) {
        
        HttpResponse<String> httpResponse;
        try {
            httpResponse = response.get();

            if (isSucceeded(httpResponse.statusCode())) {
                lastError = Optional.empty();
                return httpResponse.body();
            }
            else {

                try {
                    lastError = Optional.of(gson.fromJson(httpResponse.body(), RestErrorInfo.class));
                }
                catch (JsonSyntaxException e) {
                    lastError = Optional.empty();
                }
                return null;
            }
        }
        catch (InterruptedException | ExecutionException e) {
            throw new O2GRuntimeException(e);
        }
    }
    

    protected byte[] asByteArray(CompletableFuture<HttpResponse<byte[]>> response) {

        HttpResponse<byte[]> httpResponse;
        try {
            httpResponse = response.get();

            if (isSucceeded(httpResponse.statusCode())) {
                lastError = Optional.empty();
                return httpResponse.body();
            }
            else {

                try {
                    String error = new String(httpResponse.body(), StandardCharsets.UTF_8);
                    lastError = Optional.of(gson.fromJson(error, RestErrorInfo.class));
                }
                catch (JsonSyntaxException e) {
                    lastError = Optional.empty();
                }
                return null;
            }
        }
        catch (InterruptedException | ExecutionException e) {
            throw new O2GRuntimeException(e);
        }
    }

    protected <T> T getResult(CompletableFuture<HttpResponse<String>> response, Class<T> objClass) {

        HttpResponse<String> httpResponse;
        try {
            httpResponse = response.get();

            if (isSucceeded(httpResponse.statusCode())) {
                lastError = Optional.empty();
                return gson.fromJson(httpResponse.body(), objClass);
            }
            else {

                try {
                    lastError = Optional.of(gson.fromJson(httpResponse.body(), RestErrorInfo.class));
                }
                catch (JsonSyntaxException e) {
                    lastError = Optional.empty();
                }
                return null;
            }
        }
        catch (InterruptedException | ExecutionException e) {
            throw new O2GRuntimeException(e);
        }
    }

    protected boolean isSucceeded(CompletableFuture<HttpResponse<String>> response) {
        try {
            HttpResponse<String> httpResponse = response.get();

            if (isSucceeded(httpResponse.statusCode())) {
                lastError = Optional.empty();
                return true;
            }
            else {

                try {
                    lastError = Optional.of(gson.fromJson(httpResponse.body(), RestErrorInfo.class));
                }
                catch (JsonSyntaxException e) {
                    lastError = Optional.empty();
                }
                return false;
            }
        }
        catch (Exception e) {
            throw new O2GRuntimeException(e);
        }
    }

    protected HttpResponse<String> getResponse(CompletableFuture<HttpResponse<String>> response) {
        try {
            HttpResponse<String> httpResponse = response.get();

            if (isSucceeded(httpResponse.statusCode())) {
                lastError = Optional.empty();
                return httpResponse;
            }
            else {

                try {
                    lastError = Optional.of(gson.fromJson(httpResponse.body(), RestErrorInfo.class));
                }
                catch (JsonSyntaxException e) {
                    lastError = Optional.empty();
                }
                return httpResponse;
            }
        }
        catch (Exception e) {
            throw new O2GRuntimeException(e);
        }
    }

    public final Optional<RestErrorInfo> getLastError() {
        return lastError;
    }

    protected Path downloadedFile(String wavPath, CompletableFuture<HttpResponse<Path>> response) {
        try {
            HttpResponse<Path> httpResponse = response.get();

            if (isSucceeded(httpResponse.statusCode())) {
                lastError = Optional.empty();

                // Get the downloaded file location
                Path fileDownloaded = httpResponse.body();
                if (wavPath != null) {
                    // Normally the file has been downloaded at the right location
                    return fileDownloaded;
                }
                else {
                    // Try to get the file in the Content-Disposition header. If there is no such
                    // header
                    // use the generated name, copy it under the download directory
                    Path downloadDirectory = Path.of(System.getProperty("user.home"), "Downloads");
                    Path destination;

                    String dispoFilename = getFilename(httpResponse.headers());
                    if (dispoFilename != null) {
                        destination = Path.of(downloadDirectory.toString(), dispoFilename);
                    }
                    else {
                        destination = Path.of(downloadDirectory.toString(), fileDownloaded.getFileName().toString());
                    }

                    return Files.copy(fileDownloaded, destination, StandardCopyOption.REPLACE_EXISTING);
                }

            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            throw new O2GRuntimeException(e);
        }
    }

    /**
     * getFilename from content-disposition header
     * @param headers
     * @return
     */
    protected String getFilename(HttpHeaders headers) {

        Optional<String> optDispoHeader = headers.firstValue("Content-Disposition");
        if (optDispoHeader.isPresent()) {

            String dispoHeader = optDispoHeader.get();
            
            if (!dispoHeader.regionMatches(true, // ignoreCase
                    0, DISPOSITION_TYPE, 0, DISPOSITION_TYPE.length())) {
                return null;
            }

            Matcher matcher = FILENAME.matcher(dispoHeader);
            if (!matcher.find()) {
                return null;
            }
            int n = matcher.end();

            int semi = dispoHeader.substring(n).indexOf(";");
            String filenameParam;
            if (semi < 0) {
                filenameParam = dispoHeader.substring(n);
            }
            else {
                filenameParam = dispoHeader.substring(n, n + semi);
            }

            // strip all but the last path segment
            int x = filenameParam.lastIndexOf("/");
            if (x != -1) {
                filenameParam = filenameParam.substring(x + 1);
            }
            x = filenameParam.lastIndexOf("\\");
            if (x != -1) {
                filenameParam = filenameParam.substring(x + 1);
            }

            filenameParam = filenameParam.trim();

            if (filenameParam.startsWith("\"")) { // quoted-string
                if (!filenameParam.endsWith("\"") || filenameParam.length() == 1) {
                    return null;
                }
                filenameParam = filenameParam.substring(1, filenameParam.length() - 1);
            }
            else { // token,
                if (filenameParam.contains(" ")) { // space disallowed
                    return null;
                }
            }

            if (PROHIBITED.contains(filenameParam)) {
                return null;
            }
            else {
                return filenameParam;
            }
        }
        else {
            return null;
        }
    }
}
