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
package com.ale.o2g.internal.events;

import com.ale.o2g.Subscription;
import com.ale.o2g.Subscription.Builder;
import com.ale.o2g.Subscription.Filter;
import com.ale.o2g.events.EventPackage;
import com.ale.o2g.events.comlog.CommunicationLogEventListener;
import com.ale.o2g.events.eventsummary.EventSummaryEventListener;
import com.ale.o2g.events.maintenance.MaintenanceEventListener;
import com.ale.o2g.events.management.ManagementEventListener;
import com.ale.o2g.events.routing.RoutingEventListener;
import com.ale.o2g.events.telephony.TelephonyEventListener;
import com.ale.o2g.events.users.UsersEventListener;
import com.ale.o2g.internal.util.EventListenersMap;

/**
  * 
  *
  */
public class SubscriptionBuilderImpl implements Subscription.Builder {

	private EventListenersMap listeners = new EventListenersMap();
	private Filter filter = new Filter();
	private String version = "1.0";
	private int timeout = 10;

	@Override
	public Builder addUsersEventListener(UsersEventListener listener) {
		filter.addPackages(EventPackage.USERS);
        filter.addPackages(EventPackage.USER);
		listeners.add(UsersEventListener.class, listener);
		return this;
	}

	@Override
	public Builder addEventSummaryEventListener(EventSummaryEventListener listener) {
		filter.addPackages(EventPackage.EVENT_SUMMARY);
		listeners.add(EventSummaryEventListener.class, listener);
		return this;
	}

	@Override
	public Builder addTelephonyEventListener(TelephonyEventListener listener) {
		filter.addPackages(EventPackage.TELEPHONY);
		listeners.add(TelephonyEventListener.class, listener);
		return this;
	}

	@Override
	public Builder addRoutingEventListener(RoutingEventListener listener) {
		filter.addPackages(EventPackage.ROUTING);
		listeners.add(RoutingEventListener.class, listener);
		return this;
	}

    @Override
    public Builder addCommunicationLogEventListener(CommunicationLogEventListener listener) {
        filter.addPackages(EventPackage.CALLLOG);
        listeners.add(CommunicationLogEventListener.class, listener);
        return this;
    }

    @Override
    public Builder addManagementEventListener(ManagementEventListener listener) {
        filter.addPackages(EventPackage.MANAGEMENT);
        listeners.add(ManagementEventListener.class, listener);
        return this;
    }

    @Override
    public Builder addMaintenanceEventListener(MaintenanceEventListener listener) {
        filter.addPackages(EventPackage.MAINTENANCE);
        listeners.add(MaintenanceEventListener.class, listener);
        return this;
    }

	@Override
	public Builder setVersion(String version) {
		this.version = version;
		return this;
	}

	@Override
	public Builder setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	@Override
	public Subscription build() {
		SubscriptionImpl subscription = new SubscriptionImpl();

		subscription.setFilter(filter);
		subscription.setListeners(listeners);
		subscription.setVersion(version);
		subscription.setTimeout(timeout);

		return subscription;
	}
}
