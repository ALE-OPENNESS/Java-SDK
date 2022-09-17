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
package com.ale.o2g.internal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public abstract class AbstractLoopingThread {
    
    private static final int WAIT_JOIN = 2000;
    
    final static Logger logger = LoggerFactory.getLogger(AbstractLoopingThread.class);

    private Thread runningThread;
    
    public AbstractLoopingThread(String name) {
        
        runningThread = new Thread(() -> {

            try {
                logger.debug("Start {} thread", name);
                
                for (;;) {
                    if (run() == false) {
                        break;
                    }
                }
            }
            catch (InterruptedException e) {
                logger.debug("Thread {} has been interrupted", name);
            }
       
            onThreadTermination();
            
        }, name);
    }

    
    abstract protected boolean run() throws InterruptedException;
    
    protected void onThreadTermination() {
        logger.debug("Thread {} is now ended", Thread.currentThread().getName());
    }
    
    public void start() {
        this.runningThread.start();
    }

    public void stop() {
        this.runningThread.interrupt();
        try {
            this.runningThread.join(WAIT_JOIN);
        }
        catch (InterruptedException ignored) {
        }
    }
}
