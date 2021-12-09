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
package com.ale.o2g.types.maintenance;

import java.util.Collection;
import java.util.Date;

/**
 * {@code SystemStatus} class provide a full status of the O2G server and its
 * connections.
 */
public class SystemStatus {

    /**
     * {@code Configuration} represents the possible O2G server configurations.
     */
    public enum Configuration {
        /**
         * O2G Server is configured for management. An O2G server configured for
         * management does not monitor devices on the OmniPCX Enterprise.
         */
        PBX_MANAGEMENT,

        /**
         * O2G Server is configured with full services.
         */
        FULL_SERVICES
    }

    private ServerAddress logicalAddress;
    private Date startDate;
    private boolean ha;
    private String primary;
    private String primaryVersion;
    private String secondary;
    private String secondaryVersion;
    private Collection<PbxStatus> pbxs;
    private Collection<License> lics;
    private Configuration configurationType;

    /**
     * Returns this O2G server logical address.
     * 
     * @return the logical address.
     */
    public final ServerAddress getLogicalAddress() {
        return logicalAddress;
    }

    /**
     * Returns the start date of the O2G server.
     * 
     * @return the start date
     */
    public final Date getStartDate() {
        return startDate;
    }

    /**
     * Returns whether this O2G is deployed in high availability mode.
     * 
     * @return {@code true} if O2G is in ha mode; {@code false} otehrwise.
     */
    public final boolean isHa() {
        return ha;
    }

    /**
     * Returns the FQDN of the currently active O2G server when it is configured in
     * HA mode.
     * 
     * @return the primary fqdn.
     */
    public final String getPrimary() {
        return primary;
    }

    /**
     * Returns the version of the current active O2G server when it is configured in
     * HA mode.
     * 
     * @return the primary version.
     */
    public final String getPrimaryVersion() {
        return primaryVersion;
    }

    /**
     * Return the FQDN of the backup O2G server when it is configured in HA mode.
     * @return the secondary O2G server.
     */
    public final String getSecondary() {
        return secondary;
    }

    /**
     * Returns the version of the backup O2G server when it is configured in HA mode.
     * @return the secondary version.
     */
    public final String getSecondaryVersion() {
        return secondaryVersion;
    }

    /**
     * Returns the collection of OmniPCX Enterprise nodes connected to this O2G server
     * @return the pbxs nodes
     */
    public final Collection<PbxStatus> getPbxs() {
        return pbxs;
    }

    /**
     * Returns the O2G server licenses.
     * @return the lics
     */
    public final Collection<License> getLics() {
        return lics;
    }

    /**
     * Return this O2G Server configuration
     * @return the configuration Type
     */
    public final Configuration getConfiguration() {
        return configurationType;
    }
    

    protected SystemStatus(ServerAddress logicalAddress, Date startDate, boolean ha, String primary,
            String primaryVersion, String secondary, String secondaryVersion, Collection<PbxStatus> pbxs,
            Collection<License> lics, Configuration configurationType) {
        this.logicalAddress = logicalAddress;
        this.startDate = startDate;
        this.ha = ha;
        this.primary = primary;
        this.primaryVersion = primaryVersion;
        this.secondary = secondary;
        this.secondaryVersion = secondaryVersion;
        this.pbxs = pbxs;
        this.lics = lics;
        this.configurationType = configurationType;
    }
}
