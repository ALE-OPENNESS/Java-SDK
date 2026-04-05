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
/*
 * Copyright 2021 ALE International
 *
 * Licensed under the MIT License.
 */
package com.ale.o2g.internal.events;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.SessionMonitoringPolicy.Behavior;
import com.ale.o2g.events.EventProcessor;
import com.ale.o2g.internal.SessionMonitoringHandler;
import com.ale.o2g.internal.util.AbstractLoopingThread;
import com.ale.o2g.internal.util.HttpClientBuilder;
import com.ale.o2g.internal.util.HttpClientWrapper;

/**
 *
 */
public class ChunkEventListener extends AbstractLoopingThread {

    final static Logger logger = LoggerFactory.getLogger(ChunkEventListener.class);

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private HttpClientWrapper httpClient;
    private URI uri;
    private SessionMonitoringHandler sessionMonitoringHandler;
    private boolean chunkEstablished = false;
    private InputStream currentEventStream = null;
    private EventProcessor eventProcessor;

    public ChunkEventListener(URI uri, EventProcessor eventProcessor,
            SessionMonitoringHandler sessionMonitoringHandler) throws Exception {
        super("ChunkEventListener");

        this.uri = uri;
        this.eventProcessor = eventProcessor;
        this.sessionMonitoringHandler = sessionMonitoringHandler;
        httpClient = HttpClientBuilder.getInstance().build(executorService);
    }

    private void readChunks(InputStream eventStream) throws InterruptedException {

        currentEventStream = eventStream;
        BufferedReader reader = new BufferedReader(new InputStreamReader(eventStream));

        while (true) {
            String sEvent = null;
            try {
                sEvent = reader.readLine();
            } 
            catch (IOException e) {
                logger.error("Event channel has been closed.");
                break;
            }

            if (sEvent == null) {
                logger.error("Reading null event");
                continue;
            }

            eventProcessor.process(sEvent);
        }

        currentEventStream = null;
    }

    @Override
    protected boolean run() throws InterruptedException {

        HttpResponse<InputStream> streamResponse = null;
        try {
            logger.debug("Start eventing channel on {}", uri);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(BodyPublishers.noBody())
                    .build();
            streamResponse = httpClient.send(request, BodyHandlers.ofInputStream());
        }
        catch (IOException e) {
            logger.error("Unable to open event channel. Maybe the O2G server is not reachable", e);

            Behavior behavior = sessionMonitoringHandler.getPolicy()
                    .getBehaviorOnChunkChannelFailure(sessionMonitoringHandler.getSession(), e);
            if (behavior.isRetry()) {
                TimeUnit timeUnit = behavior.getUnit();
                timeUnit.sleep(behavior.getPeriod());
            } 
            else if (behavior.isAbort()) {
                sessionMonitoringHandler.signalSessionLost("chunk-error-abort");
                return false;
            }
        }

        if (streamResponse != null) {

            if ((streamResponse.statusCode() >= 200) && (streamResponse.statusCode() <= 299)) {

                chunkEstablished = true;
                sessionMonitoringHandler.getPolicy()
                        .chunkChannelEstablished(sessionMonitoringHandler.getSession());

                logger.info("Event channel has been opened.");

                // Start reading chunks — on stream break, run() returns true
                // and loops back to reconnect immediately
                readChunks(streamResponse.body());

            } 
            else {
                // Fatal HTTP error
                if (!chunkEstablished) {
                    sessionMonitoringHandler.getPolicy()
                            .chunkChannelFatalError(sessionMonitoringHandler.getSession(),
                                    streamResponse.statusCode());
                }
                sessionMonitoringHandler.signalSessionLost("chunk-http-error");
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
