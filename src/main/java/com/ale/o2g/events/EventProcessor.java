/*
* Copyright 2026 ALE International
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

package com.ale.o2g.events;

/**
 * Forwards raw event payloads received on a webhook endpoint into the O2G SDK
 * event system.
 *
 * <p>When a subscription is created with a webhook URL, the SDK provides an
 * {@code EventProcessor} instance via {@link com.ale.o2g.events.WebHook#connectProcessor}.
 * The application is responsible for exposing the webhook HTTP endpoint and
 * invoking {@link #process(String)} for each incoming POST request body.</p>
 *
 * <p>The raw JSON body received by the webhook must be forwarded to the
 * processor unchanged. The SDK parses the event name and routes it to the
 * appropriate registered listeners.</p>
 *
 * <p>If no subscription is active when a webhook POST is received, the
 * application should return an HTTP {@code 404} response and not call this
 * method.</p>
 *
 * <pre>{@code
 * // Example using a webhook endpoint (framework-agnostic pseudocode)
 * post("/events", (request, response) -> {
 *     if (processor == null) {
 *         response.setStatus(404);
 *     } else {
 *         processor.process(request.getBody());
 *         response.setStatus(200);
 *     }
 * });
 * }</pre>
 *
 * @see com.ale.o2g.events.WebHook
 * @see com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription)
 */
public interface EventProcessor {

    /**
     * Forwards a raw event payload received on the webhook endpoint into the SDK.
     *
     * <p>The {@code rawEvent} parameter must be the unmodified JSON body of the
     * HTTP POST request sent by the O2G server to the webhook URL. The SDK
     * parses the event name and routes it to the appropriate registered
     * listeners.
     *
     * @param rawEvent the raw JSON event payload from the webhook HTTP POST
     *                 request body; must not be {@code null}
     * @throws InterruptedException if the calling thread is interrupted while
     *                              the event is being queued for dispatch
     */
    void process(String rawEvent) throws InterruptedException;
}