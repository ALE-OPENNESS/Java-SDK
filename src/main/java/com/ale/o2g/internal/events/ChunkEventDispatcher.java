/*
* Copyright 2022 ALE International
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.internal.SessionMonitoringHandler;
import com.ale.o2g.internal.util.AbstractQueuedThread;
import com.ale.o2g.internal.util.EventListenersMap;

/**
 *
 */
public class ChunkEventDispatcher extends AbstractQueuedThread<O2GEventDescriptor> {

    final static Logger logger = LoggerFactory.getLogger(ChunkEventDispatcher.class);
    private EventListenersMap listeners;
    private SessionMonitoringHandler sessionMonitoringHandler;

    public ChunkEventDispatcher(BlockingQueue<O2GEventDescriptor> queue, EventListenersMap listeners, SessionMonitoringHandler sessionMonitoringHandler) {
        super(queue, "ChunkEventDispatcher");
        this.listeners = listeners;
        this.sessionMonitoringHandler = sessionMonitoringHandler;
    }

    
    private Class<?> getEventClass(O2GEvent o2gEvent) {

        Class<?> eventClass = o2gEvent.getClass();
        if (eventClass.isAnonymousClass()) {

            return eventClass.getSuperclass();
        }
        else {
            return eventClass;
        }
    }

    private void dispatchHandler(List<EventListener> listenersList, O2GEvent o2gEvent, String methodName) {

        for (EventListener listener : listenersList) {

            Class<?> listenerClass = listener.getClass();
            Method invocationMethod;
            try {
                invocationMethod = listenerClass.getMethod(methodName, getEventClass(o2gEvent));
                try {
                    invocationMethod.setAccessible(true);
                    invocationMethod.invoke(listener, o2gEvent);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

                    String error = String.format("Invocation error method %s(%s) on interface %s", methodName,
                            o2gEvent.getClass().getName(), listenerClass.getName());
                    
                    logger.error("REFLEXION Error: {}", error);
                    throw new Error(error);
                }
                catch (Exception e) {
                    logger.error("Exception during event treatment", e);
                    sessionMonitoringHandler.getPolicy().eventTreatmentException(e);
                }
            }
            catch (NoSuchMethodException | SecurityException e) {

                String error = String.format("Unable to find method %s(%s) on interface %s", methodName,
                        o2gEvent.getClass().getName(), listenerClass.getName());

                logger.error("REFLEXION Error: {}", error);
                throw new Error(error);
            }
        }
    }

    
    @Override
    protected boolean run() throws InterruptedException {

        O2GEventDescriptor o2gEventDescriptor = get();
        Class<? extends EventListener> listenerClass = o2gEventDescriptor.listener();
        if (listenerClass != null) {

            List<EventListener> ll = listeners.getListeners(listenerClass);
            if ((ll != null) && !ll.isEmpty()) {
                dispatchHandler(ll, o2gEventDescriptor.event(), o2gEventDescriptor.methodName());
            }
        }
        
        return true;
    }
}
