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

package com.ale.o2g;

import static com.ale.o2g.test.ExtendAssert.assertJsonEquals;

import org.junit.jupiter.api.Test;

import com.ale.o2g.events.cca.CallCenterAgentEventAdapter;
import com.ale.o2g.events.ccp.CallCenterPilotEventAdapter;
import com.ale.o2g.events.telephony.TelephonyEventAdapter;
import com.ale.o2g.events.users.UsersEventAdapter;
import com.ale.o2g.test.AbstractJsonTest;

public class SubscriptionTest extends AbstractJsonTest {

    @Test
    public void testUserSubscriptionAll() throws Exception {
        Subscription subscription = Subscription.newBuilder()
                .addUsersEventListener(new UsersEventAdapter() {})
                .setVersion("1.0")
                .setTimeout(5)
                .build();

        String json = gson.toJson(subscription);

        String expectedJson = """
                {
                  "version":"1.0",
                  "timeout":5,
                  "filter":{
                    "selectors":[
                      {"names":["userManagement"]},
                      {"names":["user"]}
                    ]
                  }
                }
                """;

        assertJsonEquals(expectedJson, json);
    }

    @Test
    public void testTelephonySubscriptionWithSpecificIds() throws Exception {
        Subscription subscription = Subscription.newBuilder()
                .addTelephonyEventListener(new TelephonyEventAdapter() {}, new String[]{"1001", "1002"})
                .setVersion("2.0")
                .setTimeout(15)
                .build();

        String json = gson.toJson(subscription);

        String expectedJson = """
                {
                  "version":"2.0",
                  "timeout":15,
                  "filter":{
                    "selectors":[
                      {"ids":["1001","1002"],"names":["telephony"]}
                    ]
                  }
                }
                """;

        assertJsonEquals(expectedJson, json);
    }

    @Test
    public void testPilotAndRoutingSubscriptionAll() throws Exception {
        Subscription subscription = Subscription.newBuilder()
                .addCallCenterPilotEventListener(new CallCenterPilotEventAdapter() {})
                .addRoutingEventListener(event -> {})
                .setVersion("1.5")
                .setTimeout(20)
                .build();

        String json = gson.toJson(subscription);

        String expectedJson = """
                {
                  "version":"1.5",
                  "timeout":20,
                  "filter":{
                    "selectors":[
                      {"names":["pilot"]},
                      {"names":["routingManagement"]}
                    ]
                  }
                }
                """;

        assertJsonEquals(expectedJson, json);
    }

    @Test
    public void testAgentSubscriptionWithWildcardId() throws Exception {
        Subscription subscription = Subscription.newBuilder()
                .addCallCenterAgentEventListener(new CallCenterAgentEventAdapter() {}, new String[]{"*"})
                .setVersion("1.0")
                .setTimeout(10)
                .build();

        String json = gson.toJson(subscription);

        String expectedJson = """
                {
                  "version":"1.0",
                  "timeout":10,
                  "filter":{
                    "selectors":[
                      {"ids":["*"],"names":["agent"]}
                    ]
                  }
                }
                """;

        assertJsonEquals(expectedJson, json);
    }

    @Test
    public void testMultiplePackagesAndIds() throws Exception {
        Subscription subscription = Subscription.newBuilder()
                .addTelephonyEventListener(new TelephonyEventAdapter() {}, new String[]{"2001"})
                .addCallCenterPilotEventListener(new CallCenterPilotEventAdapter() {}, new String[]{"*"})
                .addCallCenterAgentEventListener(new CallCenterAgentEventAdapter() {}, new String[]{"3001","3002"})
                .setVersion("3.0")
                .setTimeout(30)
                .build();

        String json = gson.toJson(subscription);

        String expectedJson = """
                {
                  "version":"3.0",
                  "timeout":30,
                  "filter":{
                    "selectors":[
                      {"ids":["2001"],"names":["telephony"]},
                      {"ids":["*"],"names":["pilot"]},
                      {"ids":["3001","3002"],"names":["agent"]}
                    ]
                  }
                }
                """;

        assertJsonEquals(expectedJson, json);
    }
}