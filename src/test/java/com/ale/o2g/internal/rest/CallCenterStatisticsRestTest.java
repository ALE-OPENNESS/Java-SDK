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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.types.ccstats.ContextImpl;
import com.ale.o2g.internal.types.ccstats.RequesterImpl;
import com.ale.o2g.internal.types.ccstats.ScheduledReportImpl;
import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.ccstats.AgentAttributes;
import com.ale.o2g.types.ccstats.AgentByPilotAttributes;
import com.ale.o2g.types.ccstats.AgentFilter;
import com.ale.o2g.types.ccstats.Context;
import com.ale.o2g.types.ccstats.Filter;
import com.ale.o2g.types.ccstats.Format;
import com.ale.o2g.types.ccstats.Language;
import com.ale.o2g.types.ccstats.Requester;
import com.ale.o2g.types.ccstats.TimeInterval;
import com.ale.o2g.types.ccstats.data.StatisticsData;
import com.ale.o2g.types.ccstats.scheduled.Recurrence;
import com.ale.o2g.types.ccstats.scheduled.ReportObservationPeriod;
import com.ale.o2g.types.ccstats.scheduled.ScheduledReport;
import com.ale.o2g.types.common.DateRange;

class CallCenterStatisticsRestTest extends AbstractRestServiceTest<CallCenterStatisticsRest> {

	protected CallCenterStatisticsRestTest() {
		super(CallCenterStatisticsRest.class, "https://o2g/rest/api/callcenterstatistics");
	}

// --------------------------------------------------
// Requester
// --------------------------------------------------

	@Test
	void testCreateRequester() throws Exception {

		defineResponse(200, "");

		Requester requester = service.createRequester("super1", Language.EN, ZoneOffset.UTC,
				new String[] { "1001", "1002" });

		assertCalledWith(POST, "/scope",
				"{" + "\"supervisor\":{" + "\"identifier\":\"super1\"," + "\"language\":\"EN\"," + "\"timezone\":\"Z\""
						+ "}," + "\"agents\":[{\"number\":\"1001\"},{\"number\":\"1002\"}]" + "}");

		assertNotNull(requester);
		assertEquals("super1", requester.getId());

	}

	@Test
	void testDeleteRequester() throws Exception {

		defineResponse(200, "");

		RequesterImpl requester = new RequesterImpl("sup1", Language.EN, ZoneOffset.UTC);

		boolean result = service.deleteRequester(requester);

		assertCalledWith(DELETE, "/scope/sup1");
		assertTrue(result);
	}

	
// --------------------------------------------------
// Context
// --------------------------------------------------

	@Test
	void testCreateContext() throws Exception {

		defineResponse(200, "{ \"id\":\"ctx1\" }");

		RequesterImpl requester = new RequesterImpl("john doe", Language.EN, ZoneOffset.UTC);

		// filter creation
		AgentFilter filter = Filter.createAgentFilter();
		filter.addNumbers(new String[] { "2000", "2001", "2002" });
		filter.setAgentAttributes(AgentAttributes.acdWorkTDur, AgentAttributes.nbExtInNonAcdDirect);
		filter.setAgentByPilotAttributes(AgentByPilotAttributes.convADur);

		Context context = service.createContext(requester, "the label", "the description", filter);

		assertRequest().method(POST).uri("/scope/john+doe/ctx").jsonBody(json -> {
	        json.assertValue("$.supervisorId", "john doe");
	        json.assertValue("$.label", "the label");
	        json.assertValue("$.description", "the description");
	        json.assertArrayContains("$.filter.agentFilter.agentAttributes", List.of("acdWorkTDur", "nbExtInNonacdDirect"));
	        json.assertArrayContains("$.filter.agentFilter.pilotAttributes", List.of("convADur"));
	        json.assertArrayContains("$.filter.agentFilter.numbers", List.of("2000", "2001", "2002"));
		});

		assertNotNull(context);
		assertEquals("ctx1", context.getId());
	}

	@Test
	void testDeleteContext() throws Exception {

		defineResponse(200, "");

		ContextImpl context = new ContextImpl("ctx1", "sup1");

		boolean result = service.deleteContext(context);

		assertCalledWith(DELETE, "/scope/sup1/ctx/ctx1");
		assertTrue(result);
	}

	@Test
	void testGetRequester() throws Exception {

		defineResponse(200, "{ \"id\":\"sup1\" }");

		Requester requester = service.getRequester("sup1");

		assertCalledWith(GET, "/scope/sup1");
		assertNotNull(requester);
	}

	@Test
	void testDeleteContexts() throws Exception {

		defineResponse(200, "");

		RequesterImpl requester = new RequesterImpl("john doe", Language.EN, ZoneOffset.UTC);
		boolean result = service.deleteContexts(requester);

		assertCalledWith(DELETE, "/scope/john+doe/ctx");
		assertTrue(result);
	}
	
	@Test
	void testGetContexts() throws Exception {

		defineResponse(200, """
				{
					"contexts": [ {
						"ctxId": "abc-ctx",
						"supervisorId": "john doe",
						"label": "the Label",
						"description:": "the description",
						"isSheduled": true
					}]
				}
				""");

		RequesterImpl requester = new RequesterImpl("john doe", Language.EN, ZoneOffset.UTC);
		Collection<Context> contexts = service.getContexts(requester);

		assertCalledWith(GET, "/scope/john+doe/ctx");
		assertNotNull(contexts);
		assertEquals(1, contexts.size());
	}	

	@Test
	void testGetContext() throws Exception {

		defineResponse(200, """
				{

					"ctxId": "abc-ctx",
					"supervisorId": "john doe",
					"label": "the Label",
					"description:": "the description",
					"isSheduled": true
				}
				""");

		RequesterImpl requester = new RequesterImpl("john doe", Language.EN, ZoneOffset.UTC);
		Context context = service.getContext(requester, "abc-ctx");

		assertCalledWith(GET, "/scope/john+doe/ctx/abc-ctx");
		assertNotNull(context);
		assertEquals("the Label", context.getLabel());
	}	


	@Test
	void testUpdateContext() throws Exception {

		defineResponse(200, "");

		Context context = new ContextImpl("ctx1", "sup1");
		context.setDescription("A statistic report description");
		context.setLabel("the label");

		// filter creation
		AgentFilter filter = Filter.createAgentFilter();
		filter.addNumbers(new String[] { "2000", "2001", "2002" });
		filter.setAgentAttributes(AgentAttributes.acdWorkTDur, AgentAttributes.nbExtInNonAcdDirect);
		filter.setAgentByPilotAttributes(AgentByPilotAttributes.convADur);
		context.setFilter(filter);
		
		boolean result = service.updateContext(context);

		assertRequest().method(PUT).uri("/scope/sup1/ctx/ctx1").jsonBody(json -> {
			json.assertValue("$.supervisorId", "sup1");
	        json.assertValue("$.description", "A statistic report description");
	        json.assertValue("$.label", "the label");
	        json.assertArrayContains("$.filter.agentFilter.agentAttributes", List.of("acdWorkTDur", "nbExtInNonacdDirect"));
	        json.assertArrayContains("$.filter.agentFilter.pilotAttributes", List.of("convADur"));
	        json.assertArrayContains("$.filter.agentFilter.numbers", List.of("2000", "2001", "2002"));
		});

		assertTrue(result);
	}	
	
// --------------------------------------------------
// Data
// --------------------------------------------------

	@Test
	void testGetDataRange() throws Exception {

		defineResponse(200, "{ }");

		ContextImpl context = new ContextImpl("ctx1", "sup1");

		DateRange range = new DateRange(LocalDateTime.of(2026, 1, 1, 0, 0), LocalDateTime.of(2026, 1, 2, 0, 0));

		StatisticsData data = service.getData(context, range);

		assertCalledWith(GET,
				"/scope/sup1/ctx/ctx1/days/data?begindate=2026-01-01+00%3A00&enddate=2026-01-02+00%3A00&format=json");

		assertNotNull(data);
	}

	@Test
<<<<<<< HEAD
	void testGetDataRangeShortHeader() throws Exception {

		defineResponse(200, "{ }");

		ContextImpl context = new ContextImpl("ctx1", "sup1");
		context.setShortHeader(true);

		DateRange range = new DateRange(LocalDateTime.of(2026, 1, 1, 0, 0), LocalDateTime.of(2026, 1, 2, 0, 0));

		StatisticsData data = service.getData(context, range);

		assertCalledWith(GET,
				"/scope/sup1/ctx/ctx1/days/data?begindate=2026-01-01+00%3A00&enddate=2026-01-02+00%3A00&format=json&shortHeader");

		assertNotNull(data);
	}

	@Test
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
	void testGetDataOneDayWithSlot() throws Exception {

		defineResponse(200, "{ }");

		ContextImpl context = new ContextImpl("ctx1", "sup1");

		StatisticsData data = service.getData(context, LocalDate.of(2024, 1, 1), TimeInterval.HOUR);

		assertCalledWith(GET, "/scope/sup1/ctx/ctx1/oneday/data?date=2024-01-01+00%3A00&slotType=anHour&format=json");

		assertNotNull(data);
	}

<<<<<<< HEAD
	@Test
	void testGetDataOneDayWithSlotShortHeader() throws Exception {

		defineResponse(200, "{ }");

		ContextImpl context = new ContextImpl("ctx1", "sup1");
		context.setShortHeader(true);

		StatisticsData data = service.getData(context, LocalDate.of(2024, 1, 1), TimeInterval.HOUR);

		assertCalledWith(GET, "/scope/sup1/ctx/ctx1/oneday/data?date=2024-01-01+00%3A00&slotType=anHour&format=json&shortHeader");

		assertNotNull(data);
	}
	
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
// --------------------------------------------------
// Scheduled reports
// --------------------------------------------------

	@Test
	void testCreateScheduledReport() throws Exception {

		defineResponse(200, "{ \"id\":\"rep1\" }");

		ContextImpl context = new ContextImpl("ctx1", "sup1");

		ScheduledReport report = service.createScheduledReport(context, "Statistic report",
				"A statistic report description", ReportObservationPeriod.onCurrentMonth(), Recurrence.monthly(15),
				Format.EXCEL, new String[] { "john.doe@mycompany.com", "emily.brown@supervisor.com" });

		
		assertRequest().method(POST).uri("/scope/sup1/ctx/ctx1/schedule").jsonBody(json -> {
			json.assertValue("$.name", "Statistic report");
	        json.assertValue("$.description", "A statistic report description");
	        json.assertValue("$.obsPeriod.periodType", "currentMonth");
	        json.assertValue("$.frequency.periodicity", "monthly");
	        json.assertValue("$.frequency.dayInMonth", 15);
	        json.assertValue("$.fileType", "xls");

	        // array of recipients
	        json.assertArrayContains("$.recipients", List.of("john.doe@mycompany.com", "emily.brown@supervisor.com"));
		});

		assertNotNull(report);
		assertEquals("rep1", report.getId());
	}

	@Test
	void testDeleteScheduledReport() throws Exception {

		defineResponse(200, "");

		ContextImpl context = new ContextImpl("ctx1", "sup1");

		ScheduledReportImpl report = new ScheduledReportImpl(context, "report1", "description 1",
				ReportObservationPeriod.onLastDays(4), null, Format.CSV, new String[] { "john.doe@test.com" });

		boolean result = service.deleteScheduledReport(report);

		assertCalledWith(DELETE, "/scope/sup1/ctx/ctx1/schedule/report1");

		assertTrue(result);
	}

	@Test
	void testSetScheduledReportEnabled() throws Exception {

		defineResponse(200, "");

		ContextImpl context = new ContextImpl("ctx1", "sup1");

		ScheduledReportImpl report = new ScheduledReportImpl(context, "report1", "description 1",
				ReportObservationPeriod.fromDate(LocalDateTime.of(2026, 1, 1, 0, 0), 10), null, Format.CSV,
				new String[] { "john.doe@test.com" });

		boolean result = service.setScheduledReportEnabled(report, true);

		assertCalledWith(POST, "/scope/sup1/ctx/ctx1/schedule/report1/enable?enable=true");

		assertTrue(result);
	}
}