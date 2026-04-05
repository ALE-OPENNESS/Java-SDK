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

import java.net.URI;

/**
 * Represents a Webhook configuration used for receiving O2G events.
 *
 * <p>When eventing is configured via a Webhook, the application must expose an HTTP
 * endpoint and provide its URI to the SDK through this interface.
 *
 * <p>
 * Once the subscription is successfully established, the SDK invokes
 * {@link #connectProcessor(EventProcessor)} to supply an {@link EventProcessor}
 * instance. The application is then responsible for forwarding incoming HTTP
 * request payloads to this processor.
 */
public interface WebHook {

    /**
     * Returns the URI of the Webhook endpoint exposed by the application.
     *
     * @return the Webhook endpoint URI
     */
    URI getURI();

    /**
     * Called by the SDK when the event channel is established.
     *
     * <p>This method provides the {@link EventProcessor} that must be used to
     * dispatch incoming Webhook events to the SDK.</p>
     *
     * @param processor the event processor to use for forwarding events
     */
    void connectProcessor(EventProcessor processor);
}
