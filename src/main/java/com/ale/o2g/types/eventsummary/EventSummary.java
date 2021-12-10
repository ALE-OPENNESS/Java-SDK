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
 * {@code EventSummary} represents event counters associated to the user. It
 * allows a user to get its new message indicators (missed call, voice mails,
 * callback request, fax).
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
     * <b>Caution</b>: This attribute doesn't reflect the missed call number managed
     * by the call server itself but is related to the unanswered and non
     * acknowledged incoming calls in the history call. Therefore, either only the
     * explicit acknowledgment of these history com records through the
     * communication log API service, or a new answered call with the same user will
     * decrease this counter. Moreover, the counter is incremented for each non
     * answered incoming call, including successive attempts from the same caller.
     * 
     * @return the number of missed call or {@code null} if the server is unable to
     *         provide that information.
     */
    public final Integer getMissedCallsNb() {
        return missedCallsNb;
    }

    /**
     * Returns the number of new voice messages.
     * <p>
     * This method returns {@code null} if the server is not able to provide that
     * information.
     * 
     * @return the number of new voice messages or {@code null} if the server is
     *         unable to provide that information.
     */
    public final Integer getVoiceMessagesNb() {
        return voiceMessagesNb;
    }

    /**
     * Returns the number of callback requests.
     * <p>
     * This method returns {@code null} if the server is not able to provide that
     * information.
     * 
     * @return the number of callback request or {@code null} if the server is
     *         unable to provide that information.
     */
    public final Integer getCallBackRequestsNb() {
        return callBackRequestsNb;
    }

    /**
     * Returns the number of new Faxes.
     * <p>
     * This method returns {@code null} if the server is not able to provide that
     * information.
     * 
     * @return the number of faxes or {@code null} if the server is unable to
     *         provide that information.
     */
    public final Integer getFaxNb() {
        return faxNb;
    }

    /**
     * Return the number of new text messages.
     * <p>
     * This method returns {@code null} if the server is not able to provide that
     * information.
     * 
     * @return the number of new text messages or {@code null} if the server is
     *         unable to provide that information.
     */
    public final Integer getNewTextNb() {
        return newTextNb;
    }

    /**
     * Returns the number of old text messages.
     * <p>
     * This method returns {@code null} if the server is not able to provide that
     * information.
     * 
     * @return the number of old text messages or {@code null} if the server is
     *         unable to provide that information.
     */
    public final int getOldTextNb() {
        return oldTextNb;
    }

    /**
     * Return whether a new events is waiting.
     * <p>
     * This flags can be used to notify the application that there are new events
     * waiting.
     * 
     * @return the eventWaiting
     */
    public final boolean isEventWaiting() {
        return eventWaiting;
    }

    protected EventSummary() {
    }

}
