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
package com.ale.o2g.types.eventsummary;

/**
 * {@code EventSummary} represents a summary of event counters associated with a user.
 * <p>
 * This class allows a user to retrieve new message indicators such as missed calls,
 * voice mails, callback requests, faxes, and text messages.
 * <p>
 * Note: Some counters may return {@code null} if the server is unable to provide the
 * corresponding information.
 */
public class EventSummary {

    private Integer missedCallsNb;
    private Integer voiceMessagesNb;
    private Integer callBackRequestsNb;
    private Integer faxNb;
    private Integer newTextNb;
    private Integer oldTextNb;
    private boolean eventWaiting;

    /**
     * Returns the number of missed calls.
     * <p>
     * <b>Important:</b> This counter does not reflect the total missed calls managed
     * by the call server. It only counts unanswered and unacknowledged incoming calls
     * in the communication history. The counter decreases either when:
     * <ul>
     *   <li>The corresponding communication log records are explicitly acknowledged via the API</li>
     *   <li>The user receives a new answered call from the same caller</li>
     * </ul>
     * <p>
     * Each non-answered incoming call increments the counter, including successive attempts
     * from the same caller.
     * 
     * @return the number of missed calls, or {@code null} if unavailable
     */
    public final Integer getMissedCallsNb() {
        return missedCallsNb;
    }

    /**
     * Returns the number of new voice messages.
     * 
     * @return the number of new voice messages, or {@code null} if unavailable
     */
    public final Integer getVoiceMessagesNb() {
        return voiceMessagesNb;
    }

    /**
     * Returns the number of callback requests.
     * 
     * @return the number of callback requests, or {@code null} if unavailable
     */
    public final Integer getCallBackRequestsNb() {
        return callBackRequestsNb;
    }

    /**
     * Returns the number of new faxes.
     * 
     * @return the number of faxes, or {@code null} if unavailable
     */
    public final Integer getFaxNb() {
        return faxNb;
    }

    /**
     * Returns the number of new text messages.
     * 
     * @return the number of new text messages, or {@code null} if unavailable
     */
    public final Integer getNewTextNb() {
        return newTextNb;
    }

    /**
     * Returns the number of old text messages.
     * 
     * @return the number of old text messages, or {@code null} if unavailable
     */
    public final Integer getOldTextNb() {
        return oldTextNb;
    }

    /**
     * Indicates whether new events are waiting for the user.
     * <p>
     * This flag can be used to notify applications that new events require attention.
     * 
     * @return {@code true} if new events are waiting, {@code false} otherwise
     */
    public final boolean isEventWaiting() {
        return eventWaiting;
    }

    protected EventSummary() {
    }
}
