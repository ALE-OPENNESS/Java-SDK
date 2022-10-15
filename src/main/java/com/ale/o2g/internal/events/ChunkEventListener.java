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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.SessionMonitoringPolicy.Behavior;
import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.events.common.OnChannelInformationEvent;
import com.ale.o2g.internal.SessionMonitoringHandler;
import com.ale.o2g.internal.util.AbstractQueuedThread;
import com.ale.o2g.util.HttpClientBuilder;

/**
 *
 */
public class ChunkEventListener extends AbstractQueuedThread<O2GEventDescriptor> {

    final static Logger logger = LoggerFactory.getLogger(ChunkEventListener.class);

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private HttpClient httpClient;
    private URI uri;
    private Semaphore signalReady;
    private SessionMonitoringHandler sessionMonitoringHandler;
    private boolean chunkEstablished = false;
    private InputStream currentEventStream = null;

    public ChunkEventListener(BlockingQueue<O2GEventDescriptor> queue, URI uri, Semaphore signalReady, SessionMonitoringHandler sessionMonitoringHandler)
            throws Exception {
        super(queue, "ChunkEventListener");

        this.uri = uri;
        this.signalReady = signalReady;
        this.sessionMonitoringHandler = sessionMonitoringHandler;
        httpClient = HttpClientBuilder.getInstance().build(executorService);
    }

    
    
    private void readChunks(InputStream eventStream) throws InterruptedException {
        
        currentEventStream = eventStream;
        BufferedReader reader = new BufferedReader(new InputStreamReader(eventStream));
        
        // Loop forever
        while (true) {
            
            // Read the event
            String sEvent = null;
            try {
                sEvent = reader.readLine();
            }
            catch (IOException e) {
                // The connexion has been broken, we have to exit from the reading loop
                logger.error("Event channel has been closed.");
                break;
            }
            
            if (sEvent == null) {
                // Not sure this can happen, but in case an event can't be read, just ignore
                logger.error("Reading null event");
                continue;
            }
            
            // Create the descriptor
            O2GEventDescriptor eventDescriptor = EventBuilder.get(sEvent);
            if (eventDescriptor == null) {
                // Unable to create an event descriptor from the event string, do nothing, ignore the event
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
        
        currentEventStream = null;
    }
    
    
    @Override
    protected boolean run() throws InterruptedException {
        
        HttpResponse<InputStream> streamResponse = null;
        try {
            logger.debug("Start eventing channel on {}", uri);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(BodyPublishers.noBody()).build();
            streamResponse = httpClient.send(request, BodyHandlers.ofInputStream());
        }
        catch (IOException e) {
            logger.error("Unable to open event channel. Maybe the O2G server is not reacheable", e);
            
            Behavior behavior = sessionMonitoringHandler.getPolicy().getBehaviorOnChunkChannelFailure(sessionMonitoringHandler.getSession(), e);
            if (behavior.isRetry()) {
                
                TimeUnit timeUnit = behavior.getUnit();
                timeUnit.sleep(behavior.getPeriod());
            }
            else if (behavior.isAbort()) {
                // We abort the task on this situation
                return false;
            }
        }
        
        if (streamResponse != null) {
            
            if ((streamResponse.statusCode() >= 200) && (streamResponse.statusCode() <= 299)) {
                
                chunkEstablished = true;
                sessionMonitoringHandler.getPolicy().chunkChannelEstablished(sessionMonitoringHandler.getSession());
                
                logger.info("Event channel has been opened.");
                
                // Start reading chunks
                readChunks(streamResponse.body());                
            }
            else {
                
                // We receive a negative HTTP response, 
                // If the chunk has never been established, this is abnormal, so signal the error an exit the thread
                // If the chunk has been already establish, do not report the error
                if (!chunkEstablished) {
                    sessionMonitoringHandler.getPolicy().chunkChannelFatalError(sessionMonitoringHandler.getSession(), streamResponse.statusCode());
                }

                // In all cases, exit the thread, this is an abnormal situation
                return false;
            }
        }
        
        return true;
    }



    @Override
    public void stop() {
        if (currentEventStream != null) {
            try {
                currentEventStream.close();
            }
            catch (IOException e) {
            }
        }
        
        super.stop();
    }



    @Override
    protected void onThreadTermination() {

        super.onThreadTermination();
        
        if (executorService != null) {
            executorService.shutdown();
        }

    }    
}
