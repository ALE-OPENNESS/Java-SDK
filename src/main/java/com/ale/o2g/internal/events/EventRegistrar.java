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

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import com.ale.o2g.events.O2GEvent;

/**
 * 
 *
 */
public class EventRegistrar {

	@FunctionalInterface
	public interface EventAdapter {
		O2GEvent adapt(O2GEvent e);
	}

	static record EventTranslator(Class<? extends O2GEvent> deserializableType, EventAdapter adapter) {
	}

	private Map<String, Class<? extends EventListener>> eventInterfaces = new HashMap<String, Class<? extends EventListener>>();
	private Map<String, String> eventClasses = new HashMap<String, String>();
	private Map<String, String> eventMethods = new HashMap<String, String>();
	private Map<String, EventTranslator> adapters = new HashMap<String, EventTranslator>();

	public void registerEventListener(Class<? extends EventListener> interfaceType) {

		if (!interfaceType.isInterface()) {
			throw new Error(String.format("The specified type %s is not an O2G event handler", interfaceType));
		}

		for (Method method : interfaceType.getMethods()) {

			// Search the parameter
			Parameter[] parameters = method.getParameters();
			if (parameters.length == 1) {

				Class<?> eventType = parameters[0].getType();
				
				eventClasses.put(eventType.getSimpleName(), eventType.getName());
				eventInterfaces.put(eventType.getName(), interfaceType);
				eventMethods.put(eventType.getName(), method.getName());
			}
			else {
				// Warning !!
				throw new Error(String.format("The specified method %s in %s is not an O2G event handler",
						method.getName(), interfaceType));
			}
		}
	}
	
	
	public void registerAdapter(String eventName, EventAdapter adapter, Class<? extends O2GEvent> deserializeType) {
		
		String qualifiedName = getQualifiedName(eventName);
		if (qualifiedName == null) {
			// Warning !!
			throw new Error(String.format("The specified event %s is not registered as an event", eventName));
		}
		
		adapters.put(qualifiedName, new EventTranslator(deserializeType, adapter));
	}

	public Class<? extends EventListener> getInterface(String eventName) {

		if (eventInterfaces.containsKey(eventName)) {
			return eventInterfaces.get(eventName);
		}

		return null;
	}
	
	public EventTranslator getAdapter(String eventName) {

		if (adapters.containsKey(eventName)) {
			return adapters.get(eventName);
		}

		return null;
	}

	public String getQualifiedName(String eventName) {
		if (eventClasses.containsKey(eventName)) {
			return eventClasses.get(eventName);
		}

		return null;
	}

	public String getMethodName(String eventName) {
		if (eventMethods.containsKey(eventName)) {
			return eventMethods.get(eventName);
		}

		return null;
	}
}
