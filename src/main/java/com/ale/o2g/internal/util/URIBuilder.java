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

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An utility class to build URI
 *
 */
public class URIBuilder {

	/**
	 * Append a query to the specified uri.
	 * @param uri the uri the query is added to.
	 * @param queryName the query parameter name.
	 * @param queryValue The query parameter value
	 * @return
	 */
	public static URI appendQuery(URI uri, String queryName, String queryValue) {
		
		String uriValue = uri.toString();
		
        if (uriValue.indexOf('?') >= 0)
        {
            if (queryValue == null)
            {
                return URI.create(String.format("%s&%s", uriValue, queryName));
            }
            else
            {
            	return URI.create(String.format("%s&%s=%s", uriValue, queryName, queryValue));
            }
        }
        else
        {
            if (queryValue == null)
            {
                return URI.create(String.format("%s?%s", uriValue, queryName));
            }
            else
            {
            	return URI.create(String.format("%s?%s=%s", uriValue, queryName, queryValue));
            }
        }
	}
	
	public static URI appendQuery(URI uri, String queryName) {
		return appendQuery(uri, queryName, null);
	}

	public static URI appendPath(URI uri, String... paths) {
		
		return URI.create(String.format("%s%s", 
				uri, 
				Arrays.asList(paths).stream()
			    	.map(s -> String.format("/%s", s.trim()))
			    	.collect(Collectors.joining())));
	}
}
