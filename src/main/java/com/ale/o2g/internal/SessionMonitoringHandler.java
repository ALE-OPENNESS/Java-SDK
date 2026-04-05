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

import com.ale.o2g.Session;
import com.ale.o2g.SessionMonitoringPolicy;

public class SessionMonitoringHandler {

    private Session session;
    private SessionMonitoringPolicy sessionMonitoringPolicy;
    private Runnable onSessionLostCallback;

    public SessionMonitoringHandler(
            SessionMonitoringPolicy sessionMonitoringPolicy,
            Session session,
            Runnable onSessionLostCallback) {
        this.session = session;
        this.sessionMonitoringPolicy = sessionMonitoringPolicy;
        this.onSessionLostCallback = onSessionLostCallback;
    }

    public SessionMonitoringPolicy getPolicy() {
        return this.sessionMonitoringPolicy;
    }

    public Session getSession() {
        return this.session;
    }

    /**
     * Called by KeepAlive or ChunkEventListener when the session is
     * definitively lost. Notifies the policy and triggers recovery.
     */
    public void signalSessionLost(String reason) {
        sessionMonitoringPolicy.onSessionLost(reason);
        if (onSessionLostCallback != null) {
            onSessionLostCallback.run();
        }
    }
}