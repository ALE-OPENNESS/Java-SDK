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

import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.events.WebHook;
import com.ale.o2g.internal.SessionMonitoringHandler;
import com.ale.o2g.internal.util.EventListenersMap;

/**
 * 
 *
 */
public class EventSubscriptionManager {

    final static Logger logger = LoggerFactory.getLogger(EventSubscriptionManager.class);

    private Semaphore signalReady = null;

    private ChunkEventListener chunkEventListener = null;
    private EventDispatcher o2gEventDispatcher = null;

    public EventSubscriptionManager(URI chunkUri, EventListenersMap listeners, SessionMonitoringHandler sessionMonitoringHandler) throws Exception {

    	// Create the queue
    	BlockingQueue<O2GEventDescriptor> eventQueue = new ArrayBlockingQueue<O2GEventDescriptor>(1000);
    	
    	// Then the event processor
    	signalReady = new Semaphore(0);
    	EventProcessorImpl eventProcessor = new EventProcessorImpl(eventQueue, signalReady);

    	// Create the chunk listener associated to the processor
    	chunkEventListener = new ChunkEventListener(chunkUri, eventProcessor, sessionMonitoringHandler);        

    	// Create the dispatcher
        o2gEventDispatcher = new EventDispatcher(eventQueue, listeners, sessionMonitoringHandler);        
    }

    public EventSubscriptionManager(WebHook webHook, EventListenersMap listeners, SessionMonitoringHandler sessionMonitoringHandler) throws Exception {

    	// Create the queue
    	BlockingQueue<O2GEventDescriptor> eventQueue = new ArrayBlockingQueue<O2GEventDescriptor>(1000);
    	
    	// Connect the processor to the hook
    	webHook.connectProcessor(new EventProcessorImpl(eventQueue, null));

    	// Create the dispatcher
        o2gEventDispatcher = new EventDispatcher(eventQueue, listeners, sessionMonitoringHandler);        
    }
    
    public void start() throws InterruptedException {
    	o2gEventDispatcher.start();
    	
    	if (chunkEventListener != null) {
    		chunkEventListener.start();
    	}

        if (signalReady != null) {
        	signalReady.acquire();
        }
    }

    public void stop() {
    	o2gEventDispatcher.stop();
    	
    	if (chunkEventListener != null) {
    		chunkEventListener.stop();
    	}
    }

}
