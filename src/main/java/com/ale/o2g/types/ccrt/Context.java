/*
* Copyright 2025 ALE International
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
package com.ale.o2g.types.ccrt;

/**
 * The {@code Context} class represents a subscription to CCD real-time events
 * provided by the {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}.
 * <p>
 * A context defines the set of CCD objects to monitor, the attributes of
 * interest, the observation period, and the notification frequency for events.
 * It is associated with a {@link Filter} that specifies which agents, pilots,
 * queues, and processing groups are included in the real-time notifications.
 */
public class Context {

    private boolean active;
    private int obsPeriod;
    private int notifFrequency;
    private Filter filter;

    /**
     * Constructs a new {@code Context} with the specified observation period,
     * notification frequency, and filter.
     *
     * @param observationPeriod     the observation period in minutes.
     *                              Must be between 15 and 60 minutes.
     * @param notificationFrequency the frequency of real-time notifications in seconds.
     *                              Minimum value is 5 seconds.
     * @param filter                the {@link Filter} defining which CCD objects
     *                              and attributes are included in this context
     */
    public Context(int observationPeriod, int notificationFrequency, Filter filter) {
        this.active = false;
        this.obsPeriod = observationPeriod;
        this.notifFrequency = notificationFrequency;
        this.filter = filter;
    }

    /**
     * Returns whether this RTI context is currently active.
     *
     * @return {@code true} if the context is active; {@code false} otherwise
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * Returns the observation period for this context.
     * <p>
     * This defines the duration (in minutes) during which the context is active
     * and collects real-time events.
     *
     * @return the observation period in minutes
     */
    public final int getObservationPeriod() {
        return obsPeriod;
    }

    /**
     * Returns the notification frequency for this context.
     * <p>
     * This defines how often (in seconds) real-time notifications are sent
     * for changes in the monitored CCD objects.
     *
     * @return the notification frequency in seconds
     */
    public final int getNotificationFrequency() {
        return notifFrequency;
    }

    /**
     * Returns the {@link Filter} associated with this context.
     * <p>
     * The filter specifies which CCD objects and their attributes will be included
     * in real-time event notifications.
     *
     * @return the filter
     */
    public final Filter getFilter() {
        return filter;
    }

}
