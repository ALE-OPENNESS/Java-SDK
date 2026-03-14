/*
* Copyright 2026 ALE International
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

package com.ale.o2g.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ale.o2g.internal.util.HttpClientWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;

/**
 * Fully optimized base class for REST service tests.
 */
public abstract class AbstractRestServiceTest<T> {

    protected static final String GET = "GET";
    protected static final String POST = "POST";
    protected static final String DELETE = "DELETE";
    protected static final String PUT = "PUT";

    private static final SimpleDateFormat UTCDateFormatter;
    static {
        UTCDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        UTCDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    protected final Gson gson = new Gson();

    protected T service;
    protected String baseUri;

    private final Class<T> restClass;

    @Mock
    protected HttpClientWrapper httpClientMock;

    // cache captured requests per test
    private List<HttpRequest> capturedRequests;

    protected AbstractRestServiceTest(Class<T> restClass, String uri) {
        this.restClass = restClass;
        this.baseUri = uri;
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Constructor<T> constructor =
                restClass.getConstructor(HttpClientWrapper.class, URI.class);
        service = constructor.newInstance(httpClientMock, new URI(baseUri));

        capturedRequests = null;
    }

    protected <R> R makeFrom(String jsonDef, Class<R> objClass) {
        return gson.fromJson(jsonDef, objClass);
    }

    /* -------------------------------
     * Response mocking
     * ------------------------------- */

    protected HttpResponse<String> mockResponse(int statusCode, String body) {
        @SuppressWarnings("unchecked")
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(statusCode);
        when(response.body()).thenReturn(body);
        return response;
    }

    protected void defineResponse(int statusCode, String body) {
        defineResponses(List.of(mockResponse(statusCode, body)));
    }

    @SuppressWarnings("unchecked")
    protected void defineResponses(List<HttpResponse<String>> responses) {
        Iterator<HttpResponse<String>> iterator = responses.iterator();
        when(httpClientMock.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenAnswer(i -> CompletableFuture.completedFuture(iterator.next()));
    }

    /* -------------------------------
     * Backward compatible assertions
     * ------------------------------- */

    protected void assertCalledWith(int callIndex, String method, String uriToTest, String expectedBody) throws Exception {
        RequestAssert req = assertRequest(callIndex).method(method).uri(uriToTest);
        if (expectedBody != null) {
            req.body(expectedBody);
        }
    }

    protected void assertCalledWith(String method, String uriToTest, String expectedBody) throws Exception {
        assertCalledWith(0, method, uriToTest, expectedBody);
    }

    protected void assertCalledWith(String method, String uriToTest) throws Exception {
        assertCalledWith(0, method, uriToTest, null);
    }

    /* -------------------------------
     * Fluent API
     * ------------------------------- */

    protected RequestAssert assertRequest() throws Exception {
        return assertRequest(0);
    }

    protected RequestAssert assertRequest(int callIndex) throws Exception {
        List<HttpRequest> requests = capturedRequests();
        assertTrue(callIndex < requests.size(),
                "Requested call index " + callIndex + " but only " + requests.size() + " HTTP calls were made");
        return new RequestAssert(requests.get(callIndex));
    }

    @SuppressWarnings("unchecked")
    private List<HttpRequest> capturedRequests() {
        if (capturedRequests != null) return capturedRequests;

        ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClientMock, atLeastOnce()).sendAsync(captor.capture(), any(HttpResponse.BodyHandler.class));
        capturedRequests = captor.getAllValues();
        return capturedRequests;
    }

    /* -------------------------------
     * Fluent assertion helper
     * ------------------------------- */

    protected class RequestAssert {

        private final HttpRequest request;

        RequestAssert(HttpRequest request) {
            this.request = request;
        }

        public RequestAssert method(String expected) {
            assertEquals(expected, request.method(),
                    "Expected HTTP method " + expected + " but got " + request.method());
            return this;
        }

        public RequestAssert uri(String uriToTest) {
            assertUri(uriToTest, request.uri());
            return this;
        }

        public RequestAssert header(String name, String expectedValue) {
            String actual = request.headers()
                    .firstValue(name)
                    .orElseThrow(() -> new AssertionError("Header not present: " + name));
            assertEquals(expectedValue, actual, "Header mismatch for " + name);
            return this;
        }

        public RequestAssert headers(Consumer<HttpHeaders> assertions) {
            assertions.accept(request.headers());
            return this;
        }

        public RequestAssert body(String expectedBody) throws Exception {
            String actual = getRequestBody(request);
            JsonObject expectedJson = JsonParser.parseString(expectedBody).getAsJsonObject();
            JsonObject actualJson = JsonParser.parseString(actual).getAsJsonObject();
            assertEquals(expectedJson, actualJson,
                    "HTTP request body does not match expected JSON\nExpected: "
                    + expectedJson + "\nActual: " + actualJson);
            return this;
        }

        /* -------------------------------
         * jsonBody with JSONPath & array assertions
         * ------------------------------- */

        public RequestAssert jsonBody(Consumer<JsonBodyAssertions> assertions) throws Exception {
            String body = getRequestBody(request);
            assertions.accept(new JsonBodyAssertions(body));
            return this;
        }
    }

    protected static class JsonBodyAssertions {
        private final String body;
        private final JsonObject json;

        public JsonBodyAssertions(String body) {
            this.body = body;
            this.json = JsonParser.parseString(body).getAsJsonObject();
        }

        public void assertValue(String path, Object expected) {
            Object actual = JsonPath.read(body, path);
            assertEquals(expected, actual,
                    "JSONPath assertion failed for path: " + path +
                            "\nExpected: " + expected + "\nActual: " + actual);
        }

        public void assertArrayContains(String path, Collection<String> expectedValues) {
            List<String> actualValues = JsonPath.read(body, path);
            assertTrue(actualValues.containsAll(expectedValues),
                    "JSON array at path " + path + " does not contain all expected values.\n" +
                            "Expected: " + expectedValues + "\nActual: " + actualValues);
        }

        public JsonObject getJsonObject() {
            return json;
        }

        public String getBody() {
            return body;
        }
    }

    /* -------------------------------
     * Helpers
     * ------------------------------- */

    void assertUri(String uriToTest, URI capturedUri) {
        try {
            URI expected;
            if (uriToTest.startsWith("/") || uriToTest.startsWith("?")) {
                String fullUri = "/".equals(uriToTest) ? baseUri : baseUri + uriToTest;
                expected = new URI(fullUri.replace(" ", "%20"));
            } 
            else {
                expected = new URI(uriToTest.replace(" ", "%20"));
            }
            assertEquals(expected, capturedUri);
        } 
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected void assertUTCDateEquals(String utcFormat, Date date) {
        assertEquals(utcFormat, UTCDateFormatter.format(date));
    }

    private String getRequestBody(HttpRequest request) throws Exception {
        var publisher = request.bodyPublisher().orElse(null);
        if (publisher == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        publisher.subscribe(new Flow.Subscriber<>() {
            public void onSubscribe(Flow.Subscription subscription) { subscription.request(Long.MAX_VALUE); }
            public void onNext(ByteBuffer item) { byte[] bytes = new byte[item.remaining()]; item.get(bytes); baos.write(bytes, 0, bytes.length); }
            public void onError(Throwable throwable) { throw new RuntimeException(throwable); }
            public void onComplete() {}
        });
        return baos.toString(StandardCharsets.UTF_8);
    }
}
