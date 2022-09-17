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
package com.ale.o2g.internal;


import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ale.o2g.SessionMonitoringPolicy.Behavior;
import com.ale.o2g.internal.services.ISessions;
import com.ale.o2g.internal.util.AbstractLoopingThread;

/**
 *
 */
public class KeepAlive extends AbstractLoopingThread {
    
    class Period {
        private TimeUnit unit;
        private long value;
        
        public Period(TimeUnit unit, long value) {
            this.unit = unit;
            this.value = value;
        }
        
        public void set(TimeUnit unit, long value) {
            this.unit = unit;
            this.value = value;
        }
        
        public void await() throws InterruptedException {
            this.unit.sleep(this.value);
        }
    }
    
    
    
    final static Logger logger = LoggerFactory.getLogger(KeepAlive.class);

    private Period period;
    private long keepAliveValue;
    private ISessions sessionService;
    private SessionMonitoringHandler sessionMonitoringHandler;

    public KeepAlive(int keepAliveValue, ISessions sessionService, SessionMonitoringHandler sessionMonitoringHandler) {
        super("Session KeepAlive");
        
        this.sessionService = sessionService;
        this.keepAliveValue = keepAliveValue;
        this.sessionMonitoringHandler = sessionMonitoringHandler;
        
        this.period = new Period(TimeUnit.SECONDS, this.keepAliveValue);
    }


    @Override
    protected boolean run() throws InterruptedException {
        
        this.period.await();
        logger.debug("do Keep Alive");

        try {
            logger.trace("Send Keep Alive");
            boolean result = sessionService.sendKeepAlive();
            if (result) {
                period.set(TimeUnit.SECONDS, this.keepAliveValue);
                sessionMonitoringHandler.getPolicy().sessionKeepAliveDone(sessionMonitoringHandler.getSession());
            }
            else {
                logger.error("Send Keep Alive return false!!");
                sessionMonitoringHandler.getPolicy().sessionKeepAliveFatalError(sessionMonitoringHandler.getSession());
                return false;
            }
        }
        catch (Exception e) {
            logger.error("Send Keep Alive FAILED!!");

            Behavior behavior = sessionMonitoringHandler.getPolicy().getBehaviorOnKeepAliveFailure(sessionMonitoringHandler.getSession(), e);
            if (behavior.isRetry()) {
                
                // Change the period and try another keep alive
                period.set(behavior.getUnit(), behavior.getPeriod());
            }
            else if (behavior.isAbort()) {
                // We abort the task on this situation
                return false;
            }
        }
        
        return true;
    }
}
