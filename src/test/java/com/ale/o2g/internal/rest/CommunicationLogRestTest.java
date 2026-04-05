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

	// ── getComRecords variants ────────────────────────────────────────────────────

	@Test
	void testGetComRecords_NoFilter() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":0,\"limit\":0,\"count\":0}");

	    QueryResult result = service.getComRecords(null);

	    assertCalledWith(GET, "/");
	    assertNotNull(result);
	    assertEquals(0, result.size());
	}

	@Test
	void testGetComRecords_WithFilterOnly() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":0,\"limit\":0,\"count\":0}");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setRole(Role.CALLER)
	            .build();

	    QueryResult result = service.getComRecords(filter);

	    assertCalledWith(GET, "?role=CALLER");
	    assertNotNull(result);
	}

	@Test
	void testGetComRecords_WithFilterAndPage() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":5,\"limit\":10,\"count\":0}");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setRole(Role.CALLER)
	            .build();
	    Page page = new Page(5, 10);

	    QueryResult result = service.getComRecords(filter, page);

	    assertCalledWith(GET, "?role=CALLER&offset=5&limit=10");
	    assertNotNull(result);
	    assertEquals(5, result.getPage().getOffset());
	    assertEquals(10, result.getPage().getLength());
	}

	@Test
	void testGetComRecords_WithFilterPageAndOptimized() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":0,\"limit\":10,\"count\":0}");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setRole(Role.CALLER)
	            .build();
	    Page page = new Page(0, 10);

	    QueryResult result = service.getComRecords(filter, page, true);

	    assertCalledWith(GET, "?role=CALLER&limit=10&optimized=true");
	    assertNotNull(result);
	}

	@Test
	void testGetComRecords_WithLoginName() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":0,\"limit\":0,\"count\":0}");

	    QueryResult result = service.getComRecords(null, null, false, "oxe1000");

	    assertCalledWith(GET, "?loginName=oxe1000");
	    assertNotNull(result);
	}

	@Test
	void testGetComRecords_WithUnansweredOption() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":0,\"limit\":0,\"count\":0}");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setOptions(EnumSet.of(Option.UNANSWERED))
	            .build();

	    service.getComRecords(filter);

	    assertCalledWith(GET, "?unanswered=true");
	}

	@Test
	void testGetComRecords_WithBothOptions() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":0,\"limit\":0,\"count\":0}");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setOptions(EnumSet.of(Option.UNACKNOWLEDGED, Option.UNANSWERED))
	            .build();

	    service.getComRecords(filter);

	    assertCalledWith(GET, "?unacknowledged=true&unanswered=true");
	}

	@Test
	void testGetComRecords_WithCallerRole() throws Exception {
	    defineResponse(200, "{\"records\":[],\"offset\":0,\"limit\":0,\"count\":0}");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setRole(Role.CALLER)
	            .build();

	    service.getComRecords(filter);

	    assertCalledWith(GET, "?role=CALLER");
	}

	@Test
	void testGetComRecords_ReturnsNullWhenServerReturnsError() throws Exception {
	    defineResponse(404, "");

	    QueryResult result = service.getComRecords(null);

	    assertNull(result);
	}

	@Test
	void testGetComRecords_WithRecords() throws Exception {
	    defineResponse(200, "{"
	            + "\"records\":["
	            + "{\"recordId\":1,\"comRef\":\"abc123\"},"
	            + "{\"recordId\":2,\"comRef\":\"def456\"}"
	            + "],"
	            + "\"offset\":0,"
	            + "\"limit\":10,"
	            + "\"count\":2"
	            + "}");

	    QueryResult result = service.getComRecords(null);

	    assertNotNull(result);
	    assertEquals(2, result.size());

	    // Verify records are iterable
	    int count = 0;
	    for (ComRecord record : result) {
	        assertNotNull(record);
	        count++;
	    }
	    assertEquals(2, count);
	}

	@Test
	void testGetComRecords_PageInfo() throws Exception {
	    defineResponse(200, "{"
	            + "\"records\":[],"
	            + "\"offset\":20,"
	            + "\"limit\":10,"
	            + "\"count\":50"
	            + "}");

	    Page page = new Page(20, 10);
	    QueryResult result = service.getComRecords(null, page);

	    assertNotNull(result);
	    assertEquals(50, result.size());
	    assertEquals(20, result.getPage().getOffset());
	    assertEquals(10, result.getPage().getLength());
	}

	// ── getComRecord ──────────────────────────────────────────────────────────────

	@Test
	void testGetComRecord_WithLoginName() throws Exception {
	    defineResponse(200, "{"
	            + "\"recordId\": 1023,"
	            + "\"comRef\": \"12345abcdef\""
	            + "}");

	    ComRecord record = service.getComRecord(1023L, "oxe1000");

	    assertCalledWith(GET, "/1023?loginName=oxe1000");
	    assertNotNull(record);
	    assertEquals("12345abcdef", record.getCallRef());
	}

	@Test
	void testGetComRecord_ReturnsNullWhenNotFound() throws Exception {
	    defineResponse(404, "");

	    ComRecord record = service.getComRecord(9999L);

	    assertCalledWith(GET, "/9999");
	    assertNull(record);
	}

	// ── deleteComRecord ───────────────────────────────────────────────────────────

	@Test
	void testDeleteComRecord_WithLoginName() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.deleteComRecord(5L, "oxe1000");

	    assertCalledWith(DELETE, "/5?loginName=oxe1000");
	    assertTrue(result);
	}

	@Test
	void testDeleteComRecord_ReturnsFalseOnError() throws Exception {
	    defineResponse(404, "");

	    boolean result = service.deleteComRecord(5L);

	    assertFalse(result);
	}

	// ── deleteComRecords by filter ────────────────────────────────────────────────

	@Test
	void testDeleteComRecords_ByFilter() throws Exception {
	    defineResponse(200, "");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setRole(Role.CALLEE)
	            .build();

	    boolean result = service.deleteComRecords(filter);

	    assertCalledWith(DELETE, "?role=CALLEE");
	    assertTrue(result);
	}

	@Test
	void testDeleteComRecords_ByFilterWithLoginName() throws Exception {
	    defineResponse(200, "");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setRole(Role.CALLER)
	            .setOptions(EnumSet.of(Option.UNANSWERED))
	            .build();

	    boolean result = service.deleteComRecords(filter, "oxe1000");

	    assertCalledWith(DELETE, "?loginName=oxe1000&unanswered=true&role=CALLER");
	    assertTrue(result);
	}

	@Test
	void testDeleteComRecords_ByFilterWithCallRef() throws Exception {
	    defineResponse(200, "");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setCallRef("ABC123")
	            .build();

	    boolean result = service.deleteComRecords(filter);

	    assertCalledWith(DELETE, "?comRef=ABC123");
	    assertTrue(result);
	}

	@Test
	void testDeleteComRecords_ByFilterWithRemotePartyId() throws Exception {
	    defineResponse(200, "");

	    QueryFilter filter = QueryFilter.newBuilder()
	            .setRemotePartyId("12000")
	            .build();

	    boolean result = service.deleteComRecords(filter);

	    assertCalledWith(DELETE, "?remotePartyId=12000");
	    assertTrue(result);
	}

	@Test
	void testDeleteComRecords_NullFilter() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.deleteComRecords((QueryFilter) null);

	    assertCalledWith(DELETE, "/");
	    assertTrue(result);
	}

	// ── deleteComRecords by ids ───────────────────────────────────────────────────

	@Test
	void testDeleteComRecords_ByIdsNoLoginName() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.deleteComRecords(Arrays.asList(1L, 2L, 3L));

	    assertCalledWith(DELETE, "?recordIdList=1%2C2%2C3");
	    assertTrue(result);
	}

	@Test
	void testDeleteComRecords_ByIdsSingleRecord() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.deleteComRecords(Arrays.asList(42L));

	    assertCalledWith(DELETE, "?recordIdList=42");
	    assertTrue(result);
	}

	// ── acknowledgeComRecords ─────────────────────────────────────────────────────

	@Test
	void testAcknowledgeComRecords_NoLoginName() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.acknowledgeComRecords(Arrays.asList(10L, 20L));

	    assertCalledWith(PUT, "?acknowledge=true", "{\"recordIds\":[10,20]}");
	    assertTrue(result);
	}

	@Test
	void testAcknowledgeComRecord_Single() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.acknowledgeComRecord(10L);

	    assertCalledWith(PUT, "?acknowledge=true", "{\"recordIds\":[10]}");
	    assertTrue(result);
	}

	@Test
	void testAcknowledgeComRecord_SingleWithLoginName() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.acknowledgeComRecord(10L, "oxe1000");

	    assertCalledWith(PUT, "?acknowledge=true&loginName=oxe1000", "{\"recordIds\":[10]}");
	    assertTrue(result);
	}

	// ── unacknowledgeComRecords ───────────────────────────────────────────────────

	@Test
	void testUnacknowledgeComRecords_WithLoginName() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.unacknowledgeComRecords(Arrays.asList(10L, 20L), "oxe1000");

	    assertCalledWith(PUT, "?acknowledge=false&loginName=oxe1000", "{\"recordIds\":[10,20]}");
	    assertTrue(result);
	}

	@Test
	void testUnacknowledgeComRecord_Single() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.unacknowledgeComRecord(10L);

	    assertCalledWith(PUT, "?acknowledge=false", "{\"recordIds\":[10]}");
	    assertTrue(result);
	}

	@Test
	void testUnacknowledgeComRecord_SingleWithLoginName() throws Exception {
	    defineResponse(200, "");

	    boolean result = service.unacknowledgeComRecord(10L, "oxe1000");

	    assertCalledWith(PUT, "?acknowledge=false&loginName=oxe1000", "{\"recordIds\":[10]}");
	    assertTrue(result);
	}
}