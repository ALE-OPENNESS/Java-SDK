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

import com.ale.o2g.Subscription;
import com.ale.o2g.events.telephony.TelephonyEventAdapter;
import com.ale.o2g.internal.types.SubscriptionResult;
import com.ale.o2g.test.AbstractRestServiceTest;

/**
 * 
 */
class SubscriptionsRestTest extends AbstractRestServiceTest<SubscriptionsRest> {


	protected SubscriptionsRestTest() {
		super(SubscriptionsRest.class, "https://o2g/rest/api/subscriptions");
	}

	
	@Test
	void testCreateSubsription() throws Exception {

		// Define the response
		defineResponse(200, "{"
				+ "\"subscriptionId\":\"c123g\","
				+ "\"status\":\"ACCEPTED\""
				+ "}");

		
		Subscription subscription = Subscription.newBuilder()
				.addTelephonyEventListener(new TelephonyEventAdapter( ) {}, new String[]{"1000", "1001"})
				.build();
		
		// Call the method
		SubscriptionResult result = service.create(subscription);

		// Verify Called URI
		assertCalledWith(POST, "/", "{"
				+ "\"version\":\"1.0\","
				+ "\"timeout\":10,"
				+ "\"filter\":{"
					+ "\"selectors\":["
						+ "{"
							+ "\"ids\":[\"1000\",\"1001\"],"
							+ "\"names\":[\"telephony\"]"
						+ "}"
					+ "]"
				+ "}"
			+ "}"
		);
		
		// Assert the result
		assertNotNull(result);
		assertTrue(result.isAccepted());
		assertEquals("c123g", result.getId());
	}

	
	@Test
	void testDeleteSubsription() throws Exception {

		// Define the response
		defineResponse(200, "");

		// Call the method
		boolean result = service.delete("123abced");

		// Verify Called URI
		assertCalledWith(DELETE, "/123abced");
		
		// Assert the result
		assertNotNull(result);
	}
	
}
