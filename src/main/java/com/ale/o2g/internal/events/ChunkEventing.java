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

import com.ale.o2g.internal.util.EventListenersMap;

/**
 * 
 *
 */
public class ChunkEventing {

    final static Logger logger = LoggerFactory.getLogger(ChunkEventing.class);

    private Semaphore signalReady = new Semaphore(0);

    private ChunkEventListener chunkEventListener = null;
    private ChunkEventDispatcher chunkEventDispatcher = null;

    public ChunkEventing(URI chunkUri, EventListenersMap listeners) throws Exception {
        BlockingQueue<O2GEventDescriptor> eventQueue = new ArrayBlockingQueue<O2GEventDescriptor>(1000);

        chunkEventDispatcher = new ChunkEventDispatcher(eventQueue, listeners);
        chunkEventListener = new ChunkEventListener(eventQueue, chunkUri, signalReady);
    }

    public void start() throws InterruptedException {
        chunkEventDispatcher.start();
        chunkEventListener.start();

        signalReady.acquire();
    }

    public void stop() {
        chunkEventDispatcher.stop();
        chunkEventListener.stop();
    }

    public void awaitTermination() {
        chunkEventDispatcher.awaitTermination();
        chunkEventListener.awaitTermination();
    }
}
