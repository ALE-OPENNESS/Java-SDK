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
package com.ale.o2g;

import java.util.concurrent.TimeUnit;

/**
 * The SessionMonitoringPolicy allows to get notifications on abnormal
 * situations, and to customize the behavior to establish a defense mechanism.
 */
public interface SessionMonitoringPolicy {

    /**
     * Behavior class represents a defense mechanism that will be apply on an
     * abnormal situation. On an abnormal situation, this behavior allow to retry
     * the failed operation immediately, after a delay, or to abort.
     */
    public static class Behavior {

        public final static int RETRY = 0;
        public final static int ABORT = 1;

        private int action;
        private long period;
        private TimeUnit unit;

        protected Behavior(int action, long period, TimeUnit unit) {
            this.action = action;
            this.period = period;
            this.unit = unit;
        }

        /**
         * @return the action
         */
        public final int getAction() {
            return action;
        }

        /**
         * @return the period
         */
        public final long getPeriod() {
            return period;
        }

        /**
         * @return the unit
         */
        public final TimeUnit getUnit() {
            return unit;
        }

        public boolean isRetry() {
            return action == RETRY;
        }

        public boolean isAbort() {
            return action == ABORT;
        }
    }

    /**
     * A Behavior to retry the failed operation immediately.
     */
    public static class Retry extends Behavior {

        /**
         * Construct a new Retry behavior.
         */
        public Retry() {
            super(Behavior.RETRY, 0, TimeUnit.SECONDS);
        }
    }

    /**
     * A Behavior to retry the failed operation after a delay.
     */
    public static class RetryAfter extends Behavior {

        /**
         * Construct a RetryAfter behavior with teh specified period and time unit.
         * 
         * @param period the period
         * @param unit   the time unit
         */
        public RetryAfter(long period, TimeUnit unit) {
            super(Behavior.RETRY, period, unit);
        }
    }

    /**
     * A Behavior to abort the operation
     */
    public static class Abort extends Behavior {
        public Abort() {
            super(Behavior.ABORT, 0, TimeUnit.SECONDS);
        }
    }

    /**
     * This method is called by the chunk listening thread when an exception is
     * throwned. For exemple, on a network failure an IOException will be thrown.
     * 
     * <pre>
     * {@code
     *      // Retry to establish the chunk channel after 5 seconds
     *      Behavior getBehaviorOnChunkChannelFailure(Session session, Exception e) {
     *          return new RetryAfter(5, TimeUnit.SECONDS)
     *      }
     * }
     * </pre>
     * 
     * 
     * @param session the session object
     * @param e       the exception raised
     * @return the behavior in reaction to the failure. If an ABORT behavior is
     *         returned, the thread is ended.
     */
    Behavior getBehaviorOnChunkChannelFailure(Session session, Exception e);

    /**
     * Notify that the chunk channel has been successfully established.
     * 
     * @param session the session
     */
    void chunkChannelEstablished(Session session);

    /**
     * Fatal error when trying to establish the chunk channel. This error occurs on
     * an attempt to establish the HTTPS chunk and an HTTP error is returned.
     * 
     * @param session the session
     * @param error   the http error code received
     */
    void chunkChannelFatalError(Session session, int error);

    /**
     * Notify that a session keep alive has been successfuly sent.
     * 
     * @param session the session
     */
    void sessionKeepAliveDone(Session session);

    /**
     * Fatal error on keep alive. The keep alive message has receive a failure. this
     * occurs when the session has expired on O2G server. Typically if the network
     * connection has been lost during a duration greater than the keep alive
     * period.
     * 
     * @param session the session
     */
    void sessionKeepAliveFatalError(Session session);

    /**
     * This method is called by the keep alive thread when an exception is
     * throwned. For exemple, on a network failure an IOException will be thrown.
     * @param session the session
     * @param e the exception raised
     * @return the behavior in reaction to the failure. If an ABORT behavior is returned, the thread is ended.
     */
    Behavior getBehaviorOnKeepAliveFailure(Session session, Exception e);

    /**
     * Called when an exception has been thrown during the treatment of an event by the application
     * @param e
     */
    void eventTreatmentException(Exception e);
}
