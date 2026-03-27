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
*/package com.ale.o2g.types.maintenance;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents the license status of the O2G server.
 * <p>
 * Provides detailed information about the current licensing setup, including
 * the license type, operational context, the active server, overall license status,
 * detailed status messages, and the list of individual licenses.
 * </p>
 */
public class LicenseStatus {

    /**
     * Defines the license control mode for the O2G system.
     * <p>
     * Determines how licenses are managed and provisioned, either via an external
     * FlexLM server (CAPEX mode) or the internal License Manager Server (OPEX mode).
     * </p>
     */
    public enum LicenseType {

        /**
         * License controlled via an external <b>FlexLM server</b> (CAPEX mode).
         * Typically used for on-premises deployments with traditional license management.
         */
        FLEXLM,

        /**
         * License controlled via the <b>License Manager Server</b> (OPEX mode, "Purple on Demand").
         * Typically used for subscription-based deployments.
         */
        LMS
    }

    private LicenseType type;
    private String context;
    private String currentServer;
    private String status;
    private String statusMessage;
    private Collection<License> lics;

    /**
     * Returns the license type for this server.
     *
     * @return the {@link LicenseType} of the server, or {@code null} if not set
     */
    public LicenseType getType() {
        return this.type;
    }

    /**
     * Returns the operational context for this license.
     *
     * @return a string describing the license context, e.g., "production", "test"
     */
    public String getContext() {
        return context;
    }

    /**
     * Returns the currently active license server.
     *
     * @return the hostname or identifier of the current license server
     */
    public String getCurrentServer() {
        return currentServer;
    }

    /**
     * Returns the overall license status of the O2G server.
     *
     * @return a string describing the current license status, e.g., "active", "expired"
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns a detailed status message providing additional information about
     * the license state or errors encountered.
     *
     * @return the detailed status message, or {@code null} if none
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Returns an unmodifiable collection of individual licenses associated with this server.
     *
     * @return a collection of {@link License} objects
     */
    public Collection<License> getLics() {
        return (lics == null) ? Collections.emptyList() : Collections.unmodifiableCollection(lics);
    }

	protected LicenseStatus() {   
    }
}