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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.events.cca.CallCenterAgentEventListener;
import com.ale.o2g.events.ccp.CallCenterPilotEventListener;
import com.ale.o2g.events.ccrt.CallCenterRealtimeEventListener;
import com.ale.o2g.events.comlog.CommunicationLogEventListener;
import com.ale.o2g.events.common.ChannelInformationEventListener;
import com.ale.o2g.events.maintenance.MaintenanceEventListener;
import com.ale.o2g.events.management.ManagementEventListener;
import com.ale.o2g.events.routing.RoutingEventListener;
import com.ale.o2g.events.rsi.RsiEventListener;
import com.ale.o2g.events.telephony.TelephonyEventListener;
import com.ale.o2g.events.users.UsersEventListener;
import com.ale.o2g.internal.events.EventRegistrar.EventTranslator;
import com.ale.o2g.internal.events.cca.OnInternalSkillChanged;
import com.ale.o2g.internal.events.ccstats.CallCenterStatisticsEventListener;
import com.ale.o2g.internal.events.maintenance.OnInternalNodeIdEvent;
import com.ale.o2g.internal.events.management.OnInternalPbxObjectEvent;
import com.ale.o2g.internal.events.routing.OnInternalRoutingStateChangedEvent;
import com.ale.o2g.internal.util.AnnotationExclusionStrategy;
import com.ale.o2g.internal.util.EnumAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 *
 */
public class EventBuilder {

	final static Logger logger = LoggerFactory.getLogger(EventBuilder.class);

	private static EventRegistrar eventRegistrar;

	static {
		eventRegistrar = new EventRegistrar();

		eventRegistrar.registerEventListener(ChannelInformationEventListener.class);

        eventRegistrar.registerEventListener(CommunicationLogEventListener.class);

        // register Routing handlers
        eventRegistrar.registerEventListener(RoutingEventListener.class);
        eventRegistrar.registerAdapter("OnRoutingStateChangedEvent", OnInternalRoutingStateChangedEvent::adapt, OnInternalRoutingStateChangedEvent.class);

        eventRegistrar.registerEventListener(ManagementEventListener.class);
        eventRegistrar.registerAdapter("OnPbxObjectInstanceCreatedEvent", OnInternalPbxObjectEvent::adaptCreated, OnInternalPbxObjectEvent.class);
        eventRegistrar.registerAdapter("OnPbxObjectInstanceDeletedEvent", OnInternalPbxObjectEvent::adaptDeleted, OnInternalPbxObjectEvent.class);
        eventRegistrar.registerAdapter("OnPbxObjectInstanceModifiedEvent", OnInternalPbxObjectEvent::adaptModified, OnInternalPbxObjectEvent.class);
        
        eventRegistrar.registerEventListener(MaintenanceEventListener.class);
        eventRegistrar.registerAdapter("OnCtiLinkDownEvent", OnInternalNodeIdEvent::adaptLinkDown, OnInternalNodeIdEvent.class);
        eventRegistrar.registerAdapter("OnCtiLinkUpEvent", OnInternalNodeIdEvent::adaptLinkUp, OnInternalNodeIdEvent.class);
        eventRegistrar.registerAdapter("OnPbxLoadedEvent", OnInternalNodeIdEvent::adaptPbxLoaded, OnInternalNodeIdEvent.class);

        eventRegistrar.registerEventListener(TelephonyEventListener.class);
		eventRegistrar.registerEventListener(UsersEventListener.class);
        eventRegistrar.registerEventListener(CallCenterAgentEventListener.class);
        eventRegistrar.registerAdapter("OnAgentSkillChangedEvent", OnInternalSkillChanged::adaptSkillChanged, OnInternalSkillChanged.class);
        
        eventRegistrar.registerEventListener(RsiEventListener.class);
        eventRegistrar.registerEventListener(CallCenterPilotEventListener.class);

        eventRegistrar.registerEventListener(CallCenterRealtimeEventListener.class);
        eventRegistrar.registerEventListener(CallCenterStatisticsEventListener.class);
	}

	protected static Gson gson = new GsonBuilder().
	        registerTypeAdapterFactory(new EnumAdapterFactory()).
	        setExclusionStrategies(new AnnotationExclusionStrategy()).create();

	@SuppressWarnings("unchecked")
	public static O2GEventDescriptor get(String evJson) {

		// retrieve the short event name
	    String eventName = String.format("%sEvent", gson.fromJson(evJson, O2GEvent.class).getName());
	    
		String qualifiedEventName = eventRegistrar.getQualifiedName(eventName);
		if (qualifiedEventName == null) {
		    return null;
		}
		
		Class<? extends O2GEvent> typeEvent = null;

		// We have the name of the event, check if there is a translator for this event
		EventTranslator translator = eventRegistrar.getAdapter(qualifiedEventName);
		if (translator != null) {
			typeEvent = translator.deserializableType();
		}
		else {
			// No translation,
			try {
				typeEvent = (Class<? extends O2GEvent>) Class.forName(qualifiedEventName);
			}
			catch (ClassNotFoundException e) {
				typeEvent = null;
			}
		}

		if (typeEvent == null) {
			return null;
		}

		// Now we can deserialize the object
		O2GEvent ev = gson.fromJson(evJson, typeEvent);
		if (ev == null) {
			return null;
		}

		// We have an object try to adapt it if necessary
		if (translator != null) {
			ev = translator.adapter().adapt(ev);
		}

		// Now search the object interface
		return new O2GEventDescriptor(ev, eventRegistrar.getInterface(qualifiedEventName), eventRegistrar.getMethodName(qualifiedEventName));
	}
}
