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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.O2GException;
import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.events.common.OnChannelInformationEvent;
import com.ale.o2g.internal.util.CancelableQueueTask;
import com.ale.o2g.util.HttpClientBuilder;

/**
 * 
 *
 */
public class ChunkEventListener extends CancelableQueueTask<O2GEventDescriptor> {

	final static Logger logger = LoggerFactory.getLogger(ChunkEventListener.class);

	private HttpClient httpClient;
	private URI uri;
	private Semaphore signalReady;

	public ChunkEventListener(BlockingQueue<O2GEventDescriptor> queue, URI uri, Semaphore signalReady)
			throws Exception {
		super(queue, ChunkEventListener.class.getSimpleName());

		this.uri = uri;
		this.signalReady = signalReady;

		httpClient = HttpClientBuilder.getInstance().build();
	}

	private void readChunks(InputStream eventStream) {
		logger.trace("Event channel is established.");

		BufferedReader reader = new BufferedReader(new InputStreamReader(eventStream));

		try {
			while (true) {
				String sEvent = reader.readLine();
				if (sEvent != null) {
					O2GEventDescriptor eventDescriptor = EventBuilder.get(sEvent);
					if (eventDescriptor == null) {
						logger.error("Unable to create Event from {event}", sEvent);
					}
					else {
						O2GEvent o2gEvent = eventDescriptor.event();

						if (o2gEvent instanceof OnChannelInformationEvent) {
							// Signal the channel has been established
							signalReady.release();
						}

						// Push event for dispatching
						add(eventDescriptor);
					}
				}
			}
		}
		catch (Exception e) {
			// Chunk is closed => exit and restart except if cancellation is requested
			logger.trace("Event channel has been closed.");
		}
	}

	@Override
	protected void cancelableRun() throws Exception {
		while (true) {
			logger.trace("Start eventing channel on {}", uri);

			HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(BodyPublishers.noBody()).build();

			HttpResponse<InputStream> streamResponse = httpClient.send(request, BodyHandlers.ofInputStream());
			boolean channelIsOpen = isSucceeded(streamResponse.statusCode());
			if (!channelIsOpen) {
				// We have a problem with the eventing
				throw new O2GException("Fail to open chunk event channel");
			}
			else {
				readChunks(streamResponse.body());
				if (isShutdown()) {
					break;
				}
			}
		}
	}

	protected boolean isSucceeded(int statusCode) {
		return (statusCode >= 200) && (statusCode <= 299);
	}
}
