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

package com.ale.o2g.types.maintenance;

import static com.ale.o2g.test.ExtendAssert.assertEqualsUtc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.maintenance.SystemStatus.Configuration;
import com.ale.o2g.types.maintenance.SystemStatus.SubscriberFilter;

/**
 * 
 */
public class SystemStatusTest extends AbstractJsonTest {

	@Test
	void testDeserializationFull() {
		// Prepare JSON with all fields
		String json = """
				      {
						  "logicalAddress": {
						    "fqdn": "roxe1.alcatel-lucent.com",
						    "ip": "10.2.20.12"
						  },
						  "startDate": "2025-12-19T16:26:23.940Z",
						  "ha": true,
						  "primary": "roxe1.alcatel-lucent.com",
						  "primaryVersion": "14.7.005.000",
						  "primaryServicesStatus": {
						    "services": [
						      {
						        "name": "apache",
						        "status": "STARTED"
						      },
						      {
						        "name": "flexlm",
						        "status": "STARTED"
						      }
						    ]
						  },
						  "pbxs": [
						    {
						      "name": "bsbice29",
						      "nodeId": 7,
						      "mainAddress": {
						        "fqdn": "oxe1.alcatel-lucent.com",
						        "ip": "10.2.20.13"
						      },
						      "secondaryAddress": {},
						      "version": "n4.513.9",
						      "connected": true,
						      "loaded": true,
						      "ctiLinkState": "CONNECTED_MAIN",
						      "secured": true,
						      "monitoredUserNumber": 3879,
						      "lmsConnectionStatus": false
						    }
						  ],
						  "license": {
						    "type": "FLEXLM",
						    "context": "",
						    "currentServer": "localhost",
						    "status": "NORMAL",
						    "statusMessage": "",
						    "lics": [
						      {
						        "name": "ROXE_HA",
						        "total": 1,
						        "currentUsed": 0,
						        "expiration": "31-dec-2026",
						        "application": true
						      }
						    ]
						  },
						  "systemResources": {
						    "fqdn": "roxe2.alcatel-lucent.com",
						    "ip": "111.13.1.114"
						  },
						  "configurationType": "FULL_SERVICES",
						  "subscriberFilter": "ALL"
						}
				      """;

		SystemStatus status = gson.fromJson(json, SystemStatus.class);

		assertEquals("roxe1.alcatel-lucent.com", status.getLogicalAddress().getFqdn());
		assertEqualsUtc("2025-12-19T16:26:23.940Z", status.getStartDate());
		assertTrue(status.isHa());
		assertEquals("roxe1.alcatel-lucent.com", status.getPrimary());
		assertEquals("14.7.005.000", status.getPrimaryVersion());
		assertEquals(2, status.getPrimaryServicesStatus().getServices().size());
		assertEquals(1, status.getPbxs().size());
		assertEquals("oxe1.alcatel-lucent.com", status.getPbxs().iterator().next().getMainAddress().getFqdn());
		assertEquals(7, status.getPbxs().iterator().next().getNodeId());
		assertEquals("NORMAL", status.getLicense().getStatus());
		assertEquals(Configuration.FULL_SERVICES, status.getConfigurationType());
		assertEquals(SubscriberFilter.ALL, status.getSubscriberFilter());
		assertEquals("roxe2.alcatel-lucent.com", status.getSystemResources().getFqdn());
	}

	@Test
	void testDeserializationMin() {
		// JSON with no fields
		String json = "{}";

		SystemStatus status = gson.fromJson(json, SystemStatus.class);

		assertNull(status.getLogicalAddress());
		assertNull(status.getStartDate());
		assertFalse(status.isHa());
		assertNull(status.getPrimary());
		assertNull(status.getPrimaryVersion());
		assertNull(status.getPrimaryServicesStatus());
		assertTrue(status.getPbxs().isEmpty());
		assertNull(status.getLicense());
		assertNull(status.getConfigurationType());
		assertNull(status.getSubscriberFilter());
	}
}