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

package com.ale.o2g.internal.events;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.events.EventProcessor;
import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.events.common.OnChannelInformationEvent;

/**
 * 
 */
public class EventProcessorImpl implements EventProcessor {

	final static Logger logger = LoggerFactory.getLogger(EventProcessorImpl.class);

	private BlockingQueue<O2GEventDescriptor> queue;
	private Semaphore signalReady;

	public EventProcessorImpl(BlockingQueue<O2GEventDescriptor> queue, Semaphore signalReady) {
		this.queue = queue;
		this.signalReady = signalReady;
	}

	@Override
	public void process(String rawEvent) throws InterruptedException {

		O2GEventDescriptor eventDescriptor = EventBuilder.get(rawEvent);
		if (eventDescriptor == null) {
			
			// Unable to create an event descriptor from the event string, do nothing,
			// ignore the event
			logger.error("Unable to create Event from {event}", rawEvent);
		} 
		else {
			O2GEvent o2gEvent = eventDescriptor.event();

			if (o2gEvent instanceof OnChannelInformationEvent) {
				// Signal the channel has been established
				signalReady.release();
			}

			// Push event for dispatching
			queue.put(eventDescriptor);
		}
	}
}
