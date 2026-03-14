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

package com.ale.o2g.internal.rest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.directory.Criteria;
import com.ale.o2g.types.directory.SearchResult;

class DirectoryRestTest extends AbstractRestServiceTest<DirectoryRest> {

    protected DirectoryRestTest() {
        super(DirectoryRest.class, "https://o2g/rest/api/directory");
    }

    @Test
    void testSearchWithLimit() throws Exception {

        defineResponse(200, "");

        boolean result = service.search(Criteria.create(
       		 Criteria.AttributeFilter.LASTNAME, 
       		 Criteria.OperationFilter.BEGINS_WITH, 
       		 "b"), 10);

        assertCalledWith(POST,
                "/search",
                "{"
                    + "\"limit\":10,"
                    + "\"filter\":{"
                    	+ "\"operation\":\"BEGIN_WITH\","
                        + "\"field\":\"lastName\","
                        + "\"operand\":\"b\""
                    + "}"
                + "}"
        );

        assertTrue(result);
    }

    @Test
    void testSearchWithoutLimit() throws Exception {

        defineResponse(200, "");

        boolean result = service.search(Criteria.create(
          		 Criteria.AttributeFilter.LASTNAME, 
           		 Criteria.OperationFilter.EQUAL_IGNORE_CASE, 
           		 "doe"));

        assertCalledWith(POST,
                "/search",
                "{"
                    + "\"filter\":{"
                    	+ "\"operation\":\"EQUAL_IGNORE_CASE\","
                        + "\"field\":\"lastName\","
                        + "\"operand\":\"doe\""
                    + "}"
                + "}"
        );

        assertTrue(result);
    }

    @Test
    void testSearchWithLogin() throws Exception {

        defineResponse(200, "");

        boolean result = service.search(Criteria.create(
          		 Criteria.AttributeFilter.LASTNAME, 
           		 Criteria.OperationFilter.EQUAL_IGNORE_CASE, 
           		 "doe"), 5, "oxe1000");

        assertCalledWith(POST,
                "/search?loginName=oxe1000",
                "{"
                    + "\"limit\":5,"
                    + "\"filter\":{"
                    	+ "\"operation\":\"EQUAL_IGNORE_CASE\","
	                    + "\"field\":\"lastName\","
	                    + "\"operand\":\"doe\""
                    + "}"
                + "}"
        );

        assertTrue(result);
    }

    @Test
    void testCancel() throws Exception {

        defineResponse(200, "");

        boolean result = service.cancel();

        assertCalledWith(DELETE, "/search");
        assertTrue(result);
    }

    @Test
    void testCancelWithLogin() throws Exception {

        defineResponse(200, "");

        boolean result = service.cancel("oxe1000");

        assertCalledWith(DELETE, "/search?loginName=oxe1000");
        assertTrue(result);
    }

    @Test
    void testGetResults() throws Exception {

        defineResponse(200, "{"
        		+ "\"resultCode\": \"FINISH\","
        		+ "\"resultElements\": [{"
        			+ "\"contacts\":[{"
        			+ "\"id\":{"
        				+ "\"phoneNumber\": \"12000\""
        			+ "}"
        			+ "}]"
        		+ "}]"
        		+ "}");

        SearchResult result = service.getResults();

        assertCalledWith(GET, "/search");
        assertNotNull(result);
    }

    @Test
    void testGetResultsWithLogin() throws Exception {

        defineResponse(200, "{ \"results\": [] }");

        SearchResult result = service.getResults("oxe1000");

        assertCalledWith(GET, "/search?loginName=oxe1000");
        assertNotNull(result);
    }
}