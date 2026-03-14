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
import java.util.Collections;
import java.util.Date;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;


/**
 * Represents the full status of an O2G server and its connections.
 * <p>
 * Provides detailed information including server addresses, versions,
 * high-availability status, connected OmniPCX Enterprise nodes, license status,
 * and server configuration type.
 * </p>
 */
public class SystemStatus {

    /**
     * Represents possible O2G server configurations.
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public enum Configuration {
        /**
         * O2G Server is configured for management.
         * <p>
         * An O2G server in management mode does not monitor devices on the OmniPCX
         * Enterprise.
         * </p>
         */
        PBX_MANAGEMENT,

        /**
         * O2G Server is configured with full services enabled.
         */
        FULL_SERVICES,

        /**
         * Unknown O2G configuration.
         */
        UNKNOWN
    }

    
    /**
     * Defines how O2G automatically loads users from OXE subscribers.
     *
     * Controls which OXE subscribers are automatically imported or filtered
     * when provisioning users in the O2G system.
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public enum SubscriberFilter {
        /** 
         * Only OXE subscribers with the **A4980 attribute** are automatically loaded. 
         * Useful when selective subscriber provisioning is required.
         */
        A4980,

        /**
         * All OXE subscribers are automatically loaded.
         * Use this for full subscriber synchronization.
         */
        ALL,

        /**
         * No OXE subscribers are automatically loaded.
         * Users must be created manually or through another provisioning process.
         */
        NONE,
        
        /**
         * Unable to identify the filtering
         */
        UNKNOWN
    }
    
    
    private ServerAddress logicalAddress;
    private Date startDate;
    private boolean ha;
    private String primary;
    private String primaryVersion;
    private SystemServices primaryServicesStatus;
    private String secondary;
    private String secondaryVersion;
    private SystemServices secondaryServicesStatus;
    private Collection<PbxStatus> pbxs;
    private LicenseStatus license;
    private Configuration configurationType;
    private String applicationId;
    private SubscriberFilter subscriberFilter;

    /** @return the logical address of this O2G server */
    public ServerAddress getLogicalAddress() {
        return logicalAddress;
    }

    /** @return the start date of the server */
    public Date getStartDate() {
        return startDate;
    }

    /** @return true if the server is deployed in high availability (HA) mode */
    public boolean isHa() {
        return ha;
    }

    /** @return the FQDN of the primary server in HA mode */
    public String getPrimary() {
        return primary;
    }

    /** @return the version of the primary server in HA mode */
    public String getPrimaryVersion() {
        return primaryVersion;
    }

    /** @return the status of services running on the primary server */
    public SystemServices getPrimaryServicesStatus() {
        return primaryServicesStatus;
    }

    /** @return the FQDN of the secondary server in HA mode */
    public String getSecondary() {
        return secondary;
    }

    /** @return the version of the secondary server in HA mode */
    public String getSecondaryVersion() {
        return secondaryVersion;
    }

    /** @return the status of services running on the secondary server */
    public SystemServices getSecondaryServicesStatus() {
        return secondaryServicesStatus;
    }

    /** @return an unmodifiable collection of connected OmniPCX Enterprise nodes */
    public Collection<PbxStatus> getPbxs() {
        return (pbxs == null) ? Collections.emptyList() : Collections.unmodifiableCollection(pbxs);
    }

    /** @return the license status of this O2G server */
    public LicenseStatus getLicense() {
        return license;
    }

    /** @return the configuration type of the server */
    public Configuration getConfiguration() {
        return configurationType;
    }

    /** @return the application ID of this O2G instance */
    public String getApplicationId() {
        return applicationId;
    }


    /**
     * @return the configuration type of this O2G.
     */
    public Configuration getConfigurationType() {
        return configurationType;
    }

    /**
     * @return the subscriber'sfilter applied by this O2G
     */
    public SubscriberFilter getSubscriberFilter() {
        return subscriberFilter;
    }
    
    /*
    protected SystemStatus(ServerAddress logicalAddress, Date startDate, boolean ha, String primary,
            String primaryVersion, SystemServices primaryServicesStatus, String secondary, String secondaryVersion,
            SystemServices secondaryServicesStatus, Collection<PbxStatus> pbxs, LicenseStatus license,
            Configuration configurationType, String applicationId, SubscriberFilter subscriberFilter) {
        this.logicalAddress = logicalAddress;
        this.startDate = startDate;
        this.ha = ha;
        this.primary = primary;
        this.primaryVersion = primaryVersion;
        this.primaryServicesStatus = primaryServicesStatus;
        this.secondary = secondary;
        this.secondaryVersion = secondaryVersion;
        this.secondaryServicesStatus = secondaryServicesStatus;
        this.pbxs = pbxs;
        this.license = license;
        this.configurationType = configurationType;
        this.applicationId = applicationId;
        this.subscriberFilter = subscriberFilter;
    }
    */

}
