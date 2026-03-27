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

import java.util.Arrays;
import java.util.EnumSet;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.comlog.ComRecord;
import com.ale.o2g.types.comlog.Option;
import com.ale.o2g.types.comlog.Page;
import com.ale.o2g.types.comlog.QueryFilter;
import com.ale.o2g.types.comlog.QueryResult;
import com.ale.o2g.types.comlog.Role;

class CommunicationLogRestTest extends AbstractRestServiceTest<CommunicationLogRest> {

	protected CommunicationLogRestTest() {
		super(CommunicationLogRest.class, "https://o2g/rest/api/communicationlog");
	}

	@Test
	void testGetComRecords_WithAllParameters() throws Exception {

		defineResponse(200, "{" + "\"records\":[]," + "\"offset\":0," + "\"limit\":10," + "\"count\":0" + "}");

		QueryFilter filter = QueryFilter.newBuilder()
				.setCallRef("ABC123")
				.setRemotePartyId("12000")
				.setRole(Role.CALLEE)
				.setOptions(EnumSet.of(Option.UNACKNOWLEDGED))
				.build();

		Page page = new Page(0, 10);
		QueryResult result = service.getComRecords(filter, page, true, "oxe1000");

		assertCalledWith(GET, "?loginName=oxe1000" + "&unacknowledged=true" + "&role=CALLEE" + "&comRef=ABC123"
				+ "&remotePartyId=12000" + "&limit=10" + "&optimized=true");

		assertNotNull(result);
	}

	@Test
	void testGetComRecord() throws Exception {

		defineResponse(200, "{"
				+ "\"recordId\": 1023,"
				+ "\"comRef\": \"12345abcdef\""
				+ "}");

		ComRecord record = service.getComRecord(1023L);

		assertCalledWith(GET, "/1023");
		assertNotNull(record);
		assertEquals("12345abcdef", record.getCallRef());
	}

	@Test
	void testDeleteComRecord() throws Exception {

		defineResponse(200, "");

		boolean result = service.deleteComRecord(5L);

		assertCalledWith(DELETE, "/5");
		assertTrue(result);
	}

	@Test
	void testDeleteComRecords_ByIds() throws Exception {

		defineResponse(200, "");

		boolean result = service.deleteComRecords(Arrays.asList(1L, 2L, 3L), "oxe1000");

		assertCalledWith(DELETE, "?recordIdList=1%2C2%2C3&loginName=oxe1000");

		assertTrue(result);
	}

	@Test
	void testAcknowledgeComRecords() throws Exception {

		defineResponse(200, "");

		boolean result = service.acknowledgeComRecords(Arrays.asList(10L, 20L), "oxe1000");

		assertCalledWith(PUT, "?acknowledge=true&loginName=oxe1000", "{" + "\"recordIds\":[10,20]" + "}");

		assertTrue(result);
	}

	@Test
	void testUnacknowledgeComRecords() throws Exception {

		defineResponse(200, "");

		boolean result = service.unacknowledgeComRecords(Arrays.asList(10L));

		assertCalledWith(PUT, "?acknowledge=false", "{" + "\"recordIds\":[10]" + "}");

		assertTrue(result);
	}
}