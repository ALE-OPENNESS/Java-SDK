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
package com.ale.o2g.internal.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An utility class to build URI
 *
 */
public class URIBuilder {

    /**
     * Append a query to the specified uri.
     * 
     * @param uri        the uri the query is added to.
     * @param queryName  the query parameter name.
     * @param queryValue The query parameter value
     * @return
     */
    public static URI appendQuery(URI uri, String queryName, String queryValue) {

        String uriValue = uri.toString();

        if (uriValue.indexOf('?') >= 0) {
            if (queryValue == null) {
                return URI.create(String.format("%s&%s", uriValue, queryName));
            }
            else {
                try {
                    return URI.create(String.format("%s&%s=%s", uriValue, queryName,
                            URLEncoder.encode(queryValue, StandardCharsets.UTF_8.toString())));
                }
                catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException(String.format("Unable to create a query string with value %s", queryValue), e);
                }
            }
        }
        else {
            if (queryValue == null) {
                return URI.create(String.format("%s?%s", uriValue, queryName));
            }
            else {
                try {
                    return URI.create(String.format("%s?%s=%s", uriValue, queryName,
                            URLEncoder.encode(queryValue, StandardCharsets.UTF_8.toString())));
                }
                catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException(String.format("Unable to create a query string with value %s", queryValue), e);
                }
            }
        }
    }

    public static URI appendQuery(URI uri, String queryName) {
        return appendQuery(uri, queryName, null);
    }

    public static URI appendPath(URI uri, String... paths) {

        String joined = Arrays.stream(paths)
                .map(String::trim)
                .map(s -> {
                    if (s.startsWith("/")) return s;
                    else return String.format("/%s", s);
                })
                .collect(Collectors.joining());
        
        String base = uri.toString();
        if (!base.endsWith("/") && !joined.startsWith("/")) {
            return URI.create(String.format("%s/%s", uri, joined));
        }
        else {
            return URI.create(String.format("%s%s", uri, joined));   
        }
    }
    
    public static URI getBaseUri(URI uri) {
        
        int port = uri.getPort();
        
        String baseStr = uri.getScheme() + "://" + uri.getHost() + (port != -1 ? ":" + port : "");
        return URI.create(baseStr);
    }
}
