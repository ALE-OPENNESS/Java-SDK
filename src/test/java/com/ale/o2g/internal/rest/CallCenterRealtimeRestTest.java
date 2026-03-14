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

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.ccrt.AgentAttributes;
import com.ale.o2g.types.ccrt.Context;
import com.ale.o2g.types.ccrt.Filter;
import com.ale.o2g.types.ccrt.QueueAttributes;
import com.ale.o2g.types.ccrt.RtiObjectIdentifier;

class CallCenterRealtimeRestTest extends AbstractRestServiceTest<CallCenterRealtimeRest> {

	protected CallCenterRealtimeRestTest() {
		super(CallCenterRealtimeRest.class, "https://o2g/rest/api/callcenterrealtime");
	}

	// --------------------------------------------------
	// Context CRUD
	// --------------------------------------------------

	@Test
	void testSetContext() throws Exception {

		defineResponse(200, "");

		// Create a filter
		Filter filter = new Filter();

		filter.setAgentNumbers(new String[] { "60119", "60120" });
		filter.setAgentAttributes(AgentAttributes.PrivateCallsTotalDuration, AgentAttributes.LogonDate);

		filter.setQueueNumbers(new String[] { "1001", "1002" });
		filter.setQueueAttributes(QueueAttributes.ExpectedWaitingTime, QueueAttributes.NbOfWaitingCalls);

		Context context = new Context(30, 5, filter);

		boolean result = service.setContext(context);

		assertRequest().method(POST).uri("/ctx").jsonBody(json -> {
			
			json.assertValue("$.obsPeriod", 30);
	        json.assertValue("$.notifFrequency", 5);

	        // agentFilter arrays
	        json.assertArrayContains("$.filter.agentFilter.attributes", List.of("PrivateCallsTotalDuration", "LogonDate"));
	        json.assertArrayContains("$.filter.agentFilter.numbers", List.of("60119", "60120"));

	        // queueFilter arrays
	        json.assertArrayContains("$.filter.queueFilter.attributes", List.of("ExpectedWaitingTime", "NbOfWaitingCalls"));
	        json.assertArrayContains("$.filter.queueFilter.numbers", List.of("1001", "1002"));
		});

		assertTrue(result);
	}

	@Test
	void testGetContext() throws Exception {

		defineResponse(200, """
				{
					"obsPeriod": 20,
					"notifFrequency": 5
				}
				""");

		Context context = service.getContext();

		assertCalledWith(GET, "/ctx");
		assertNotNull(context);
		assertEquals(5, context.getNotificationFrequency());
	}

	@Test
	void testDeleteContext() throws Exception {

		defineResponse(200, "");

		boolean result = service.deleteContext();

		assertCalledWith(DELETE, "/ctx");
		assertTrue(result);
	}

	@Test
	void testGetAgents() throws Exception {

		defineResponse(200, """
				{
				  		"agents": [
				  			{ "number": "1200", "name": "ag1" },
				  			{ "number": "1201", "name": "ag2" },
				  			{ "number": "1202", "name": "ag3" }
				  		]
				 		}
				 		""");

		Collection<RtiObjectIdentifier> agents = service.getAgents();

		assertCalledWith(GET, "/agents");
		assertNotNull(agents);
		assertEquals(3, agents.size());
		assertEquals("1200", agents.iterator().next().getNumber());
	}

	@Test
	void testGetPilots() throws Exception {

		defineResponse(200, """
				{
				  		"pilots": [
				  			{ "number": "1200", "name": "pilot1" },
				  			{ "number": "1201", "name": "pilot1" }
				  		]
				 		}
				 		""");

		Collection<RtiObjectIdentifier> pilots = service.getPilots();

		assertCalledWith(GET, "/pilots");
		assertNotNull(pilots);
		assertEquals(2, pilots.size());
	}

	@Test
	void testGetQueues() throws Exception {

		defineResponse(200, """
				{
				  		"queues": [
				  			{ "number": "1200", "name": "queue1" },
				  			{ "number": "1201", "name": "queue2" }
				  		]
				 		}
				 		""");

		Collection<RtiObjectIdentifier> queues = service.getQueues();

		assertCalledWith(GET, "/queues");
		assertNotNull(queues);
		assertEquals(2, queues.size());
	}

	@Test
	void testGetAgentProcessingGroups() throws Exception {

		defineResponse(200, """
				{
				  		"pgAgents": [
				  			{ "number": "1200", "name": "AgGroup1" }
				  		]
				 		}
				 		""");

		Collection<RtiObjectIdentifier> pgAgents = service.getAgentProcessingGroups();

		assertCalledWith(GET, "/pgAgents");
		assertNotNull(pgAgents);
		assertEquals(1, pgAgents.size());
	}

	@Test
	void testGetOtherProcessingGroups() throws Exception {

		defineResponse(200, """
				{
				  		"pgOthers": [
				  			{ "number": "9200", "name": "OtherGroup" }
				  		]
				 		}
				 		""");

		Collection<RtiObjectIdentifier> pgOthers = service.getOtherProcessingGroups();

		assertCalledWith(GET, "/pgOthers");
		assertNotNull(pgOthers);
		assertEquals(1, pgOthers.size());
		assertEquals("OtherGroup", pgOthers.iterator().next().getName());
	}

	// --------------------------------------------------
	// Start snapshot
	// --------------------------------------------------

	@Test
	void testStart() throws Exception {

		defineResponse(200, "");

		boolean result = service.start();

		assertCalledWith(POST, "/snapshot");
		assertTrue(result);
	}
}