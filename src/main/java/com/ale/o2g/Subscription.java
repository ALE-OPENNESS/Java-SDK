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
package com.ale.o2g;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ale.o2g.events.EventPackage;
import com.ale.o2g.events.cca.CallCenterAgentEventListener;
import com.ale.o2g.events.ccp.CallCenterPilotEventListener;
import com.ale.o2g.events.ccrt.CallCenterRealtimeEventListener;
import com.ale.o2g.events.comlog.CommunicationLogEventListener;
import com.ale.o2g.events.eventsummary.EventSummaryEventListener;
import com.ale.o2g.events.maintenance.MaintenanceEventListener;
import com.ale.o2g.events.management.ManagementEventListener;
import com.ale.o2g.events.routing.RoutingEventListener;
import com.ale.o2g.events.telephony.TelephonyEventListener;
import com.ale.o2g.events.users.UsersEventListener;
import com.ale.o2g.internal.events.SubscriptionBuilderImpl;

/**
 * A {@code Subscription} class represents an event subscription. It is used by
 * the application to request event notifications from the O2G server. An
 * application receives event on a http chunk connection established with teh
 * O2G server.
 *
 */
public abstract class Subscription {

    /**
     * {@code AbstractFilter} represents a filter used to identify the events the
     * application wants to receive.
     * 
     */
    public static class Filter {

        static record Selector(List<String> ids, List<String> names) {
        }

        private List<Selector> selectors = new ArrayList<Selector>();

        /**
         * Constructs a new AbstractFilter.
         */
        public Filter() {
        }

        /**
         * Add the specified event names to the filter.
         * @param ids the identifiers the events must contains.
         * @param names the event names the application want to subscribe on
         */
        public void add(List<String> ids, List<String> names) {
            selectors.add(new Selector(ids, names));
        }

        /**
         * Add the specified event names to the filter.
         * @param ids the identifiers the events must contains.
         * @param names the event names the application want to subscribe on
         */
        public void add(String[] ids, List<String> names) {
            this.add(Arrays.asList(ids), names);
        }

        /**
         * Add the specified event names to the filter.
         * @param ids the identifiers the events must contains.
         * @param names the event names the application want to subscribe on
         */
        public void add(List<String> ids, String[] names) {
            this.add(ids, Arrays.asList(names));
        }

        /**
         * Add the specified event names to the filter.
         * @param ids the identifiers the events must contains.
         * @param names the event names the application want to subscribe on
         */
        public void add(String[] ids, String[] names) {
            this.add(Arrays.asList(ids), Arrays.asList(names));
        }

        /**
         * Add the specified event name to the filter.
         * @param id the identifier the events must contains.
         * @param name the event name the application want to subscribe on
         */
        public void add(String id, String name) {
            this.add(new String[] {
                    id
            }, new String[] {
                    name
            });
        }

        /**
         * Add the specified event names to the filter.
         * @param names the event names the application want to subscribe on
         */
        public void add(String[] names) {
            this.add(Arrays.asList(names));
        }

        /**
         * Add the specified event names to the filter.
         * @param names the event names the application want to subscribe on
         */
        public void add(List<String> names) {
            this.add((List<String>) null, names);
        }

        /**
         * Add the specified event names to the filter.
         * @param name the event filter.
         */
        public void add(String name) {
            this.add(new String[] {
                    name
            });
        }

        /**
         * Add the specified event packages to this filter.
         * @param ids the identifiers the events must contains.
         * @param eventPackages a list of event packages the application want to subscribe on
         */
        public void addPackages(String[] ids, List<EventPackage> eventPackages) {
            this.addPackages(Arrays.asList(ids), eventPackages);
        }

        /**
         * Add the specified event packages to this filter.
         * @param ids the identifiers the events must contains.
         * @param eventPackage the event package the application want to subscribe on
         */
        public void addPackages(String[] ids, EventPackage eventPackage) {
            this.add(ids, new String[] {
                    eventPackage.toString()
            });
        }

        /**
         * Add the specified events packages to this filter.
         * @param ids A list of identifiers the events must contains.
         * @param eventPackages a list of event packages the application want to subscribe on
         */
        public void addPackages(List<String> ids, List<EventPackage> eventPackages) {
            this.add(ids, eventPackages.stream().map((e) -> e.toString()).collect(Collectors.toList()));
        }

        /**
         * Add the specified list of event package to this filter.
         * @param eventPackages a list of event packages the application want to subscribe on
         */
        public void addPackages(List<EventPackage> eventPackages) {
            this.add(eventPackages.stream().map((e) -> e.toString()).collect(Collectors.toList()));
        }

        /**
         * Add the specified event package to this filter.
         * @param eventPackage the event package the application want to subscribe on
         */
        public void addPackages(EventPackage eventPackage) {
            this.add(eventPackage.toString());
        }
    }

  
    protected Subscription() {}
    
    /**
     * Return this subscription event version.
     * 
     * @return the event version
     */
    public abstract String getVersion();

    /**
     * Return this subscription timeout.
     * 
     * @return the timeout in minutes
     */
    public abstract int getTimeout();

    /**
     * Return this subscription filter.
     * 
     * @return the subscription filter.
     */
    public abstract Filter getFilter();

//    public abstract String getWebHookUrl();

    /**
     * Creates a {@code Builder} builder.
     *
     * @return a new subscription builder
     */
    public static Builder newBuilder() {
        return new SubscriptionBuilderImpl();
    }

    /**
     * A builder of {@linkplain Subscription}.
     *
     * <p>
     * Instances of {@code Subscription.Builder} are created by calling
     * {@link Subscription#newBuilder()}.
     *
     * <p>
     * The builder can be used to configure an event subscription for the O2G
     * server. Each methods modifies the state of the builder and returns the same
     * instance. The {@link #build() build} method returns a new
     * {@code Subscription} each time it is invoked.
     */
    public static interface Builder {

        /**
         * Adds users service events to the subscription.
         * 
         * @param listener the event listener to receive the users events.
         * @return this builder
         */
        Builder addUsersEventListener(UsersEventListener listener);


        /**
         * Adds event summary events to the subscription.
         * 
         * @param listener the event listener to receive the event summary events.
         * @return this builder
         */
        Builder addEventSummaryEventListener(EventSummaryEventListener listener);

        /**
         * Adds event summary events to the subscription.
         * 
         * @param listener the event listener to receive the event summary events.
         * @param ids the ids to filter events on.
         * @return this builder
         */
        Builder addEventSummaryEventListener(EventSummaryEventListener listener, String[] ids);

        /**
         * Adds telephony events to the subscription.
         * 
         * @param listener the event listener to receive the telephony events.
         * @return this builder
         */
        Builder addTelephonyEventListener(TelephonyEventListener listener);

        /**
         * Adds telephony events to the subscription.
         * 
         * @param listener the event listener to receive the telephony events.
         * @param ids the ids to filter events on.
         * @return this builder
         */
        Builder addTelephonyEventListener(TelephonyEventListener listener, String[] ids);

        /**
         * Adds routing events to the subscription.
         * 
         * @param listener the event listener to receive the routing events.
         * @return this builder
         */
        Builder addRoutingEventListener(RoutingEventListener listener);

        /**
         * Adds routing events to the subscription.
         * 
         * @param listener the event listener to receive the routing events.
         * @param ids the ids to filter events on.
         * @return this builder
         */
        Builder addRoutingEventListener(RoutingEventListener listener, String[] ids);

        /**
         * Adds RSI events to the subscription.
         * 
         * @param listener the event listener to receive the RSI events.
         * @return this builder
         */
//        Builder addRsiEventListener(RsiEventListener listener);

        /**
         * Adds RSI events to the subscription.
         * 
         * @param listener the event listener to receive the RSI events.
         * @param ids the ids to filter events on.
         * @return this builder
         */
//        Builder addRsiEventListener(RsiEventListener listener, String[] ids);

        /**
         * Adds Call center agent events to the subscription.
         * 
         * @param listener the event listener to receive the Call center agent events.
         * @return this builder
         */
        Builder addCallCenterAgentEventListener(CallCenterAgentEventListener listener);

        /**
         * Adds Call center agent events to the subscription.
         * 
         * @param listener the event listener to receive the Call center agent events.
         * @param ids the ids to filter events on.
         * @return this builder
         */
        Builder addCallCenterAgentEventListener(CallCenterAgentEventListener listener, String[] ids);

        /**
         * Adds communication log events to the subscription.
         * 
         * @param listener the event listener to receive the communication log events.
         * @return this builder
         */
        Builder addCommunicationLogEventListener(CommunicationLogEventListener listener);

        /**
         * Adds communication log events to the subscription.
         * 
         * @param listener the event listener to receive the communication log events.
         * @param ids the ids to filter events on.
         * @return this builder
         */
        Builder addCommunicationLogEventListener(CommunicationLogEventListener listener, String[] ids);

        /**
         * Adds management events to the subscription.
         * 
         * @param listener the event listener to receive the management events.
         * @return this builder
         */
        Builder addManagementEventListener(ManagementEventListener listener);

        /**
         * Adds maintenance events to the subscription.
         * 
         * @param listener the event listener to receive the maintenance events.
         * @return this builder
         */
        Builder addMaintenanceEventListener(MaintenanceEventListener listener);
        
        /**
         * Adds Call center pilot events to the subscription.
         * 
         * @param listener the event listener to receive the Call center pilot events.
         * @return this builder
         */
        Builder addCallCenterPilotEventListener(CallCenterPilotEventListener listener);

        /**
         * Adds Call center pilot events to the subscription.
         * 
         * @param listener the event listener to receive the Call center pilot events.
         * @param ids the ids to filter events on.
         * @return this builder
         */
        Builder addCallCenterPilotEventListener(CallCenterPilotEventListener listener, String[] ids);

        /**
         * Adds Call center realtime events to the subscription.
         * 
         * @param listener the event listener to receive the Call center realtime events.
         * @return this builder
         */
        Builder addCallCenterRealtimeEventListener(CallCenterRealtimeEventListener listener);
               
        /**
         * Adds Call center statistics events to the subscription.
         * @return this builder
         */
        Builder addCallCenterStatisticsEventListener();
        
        
        /**
         * Specifies the required event version. by default version 1.0 is configured in
         * the builder.
         * 
         * @param version the events required version
         * @return this builder
         */
        Builder setVersion(String version);

        /**
         * Set the timeout which specifies that after that time the chunk may be closed
         * by the server.
         * <p>
         * By default, there is no timeout configured by the builder. in this case, the
         * chunk eventing channel is never closed by the server in case of no activity.
         * 
         * @param timeout the timeout value in minutes.
         * @return this builder
         */
        Builder setTimeout(int timeout);

        /**
         * Builds and returns a {@link Subscription}.
         *
         * @return a new {@code Subscription}
         */
        Subscription build();
    }
}
