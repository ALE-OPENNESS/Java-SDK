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
package com.ale.o2g.internal;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.Session;
import com.ale.o2g.SessionMonitoringPolicy;

/**
 *
 */
public class DefaultSessionMonitoringPolicy implements SessionMonitoringPolicy {

    final static Logger logger = LoggerFactory.getLogger(DefaultSessionMonitoringPolicy.class);

    @Override
    public void chunkChannelEstablished(Session session) {
        logger.info("Chunk channel has been established");
    }

    @Override
    public void chunkChannelFatalError(Session session, int error) {
        logger.error("Chunk channel is closed due to a fatal error: {}", error);
    }

    @Override
    public void sessionKeepAliveDone(Session session) {
        logger.info("Session KeepAlive done");
    }

    @Override
    public void sessionKeepAliveFatalError(Session session) {
        logger.info("Session KeepAlive has failed due to a fatal error");
    }

    @Override
    public Behavior getBehaviorOnChunkChannelFailure(Session session, Exception e) {
        logger.error("Chunk channel is disconnected due to an exception", e);
        logger.error("Try to restablish the chunk channel");
        return new RetryAfter(2, TimeUnit.SECONDS);
    }

    @Override
    public Behavior getBehaviorOnKeepAliveFailure(Session session, Exception e) {
        logger.error("Exception raised during keep alive", e);
        logger.error("ABORT KEEP ALIVE !!!!!!!!!!!!!");
        return new Abort();
    }

    @Override
    public void eventTreatmentException(Exception e) {
        logger.error("Exception raised during treatment of an event", e);
    }

}
