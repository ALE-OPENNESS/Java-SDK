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
 * Provides detailed information about the server, including:
 * <ul>
 *   <li>Network addresses (logical and system resources)</li>
 *   <li>Software versions of primary and secondary nodes</li>
 *   <li>High-availability (HA) topology</li>
 *   <li>Connected OmniPCX Enterprise (OXE) nodes and their status</li>
 *   <li>License status</li>
 *   <li>Server configuration type and subscriber filter policy</li>
 * </ul>
 *
 * @see Configuration
 * @see SubscriberFilter
 * @see PbxStatus
 * @see LicenseStatus
 */
public class SystemStatus {

    /**
     * Defines the operational mode of an O2G server.
     * <p>
     * The configuration type determines which features and services are available.
     * In particular, only a server configured with {@link #FULL_SERVICES} monitors
     * devices on the OmniPCX Enterprise in real time.
     * </p>
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public enum Configuration {

        /**
         * The O2G server is configured for PBX management only.
         * <p>
         * In this mode, the server provides administrative and configuration
         * capabilities but does <strong>not</strong> monitor devices or telephony
         * events on the OmniPCX Enterprise.
         * </p>
         */
        PBX_MANAGEMENT,

        /**
         * The O2G server is configured with full services enabled.
         * <p>
         * In this mode, the server provides both management and real-time telephony
         * services, including device monitoring and event notification on the
         * OmniPCX Enterprise.
         * </p>
         */
        FULL_SERVICES,

        /**
         * The O2G server configuration could not be determined.
         * <p>
         * This value is used as a fallback when the server returns an unrecognized
         * configuration type.
         * </p>
         */
        UNKNOWN
    }

    /**
     * Defines the policy used by O2G to automatically load users from OmniPCX
     * Enterprise (OXE) subscribers.
     * <p>
     * This filter controls which OXE subscribers are automatically imported during
     * provisioning. Choosing the appropriate filter avoids unnecessary user creation
     * and allows fine-grained control over which subscribers are managed by O2G.
     * </p>
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public enum SubscriberFilter {

        /**
         * Only OXE subscribers with the <strong>A4980 attribute</strong> are
         * automatically loaded.
         * <p>
         * Use this filter when selective subscriber provisioning is required, limiting
         * the imported users to those explicitly flagged with the A4980 attribute on
         * the OmniPCX Enterprise.
         * </p>
         */
        A4980,

        /**
         * All OXE subscribers are automatically loaded.
         * <p>
         * Use this filter for full subscriber synchronization, where every subscriber
         * defined on the OmniPCX Enterprise is imported into O2G.
         * </p>
         */
        ALL,

        /**
         * No OXE subscribers are automatically loaded.
         * <p>
         * Users must be created manually or through an external provisioning process.
         * Use this filter when O2G user management is handled entirely outside of the
         * automatic OXE synchronization mechanism.
         * </p>
         */
        NONE,

        /**
         * The subscriber filter policy could not be determined.
         * <p>
         * This value is used as a fallback when the server returns an unrecognized
         * filter type.
         * </p>
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
    private ServerAddress systemResources;
    private Configuration configurationType;
    private String applicationId;
    private SubscriberFilter subscriberFilter;

    /**
     * Creates a new {@code SystemStatus} instance.
     * <p>
     * Instances of this class are typically created by the O2G SDK when
     * deserializing server responses, and are not intended to be constructed
     * directly by application code.
     * </p>
     */
    public SystemStatus() {
    }

    /**
     * Returns the logical address of this O2G server.
     * <p>
     * The logical address is the main entry point for client connections and
     * remains stable across HA failover events.
     * </p>
     *
     * @return the logical {@link ServerAddress} of this O2G server
     */
    public ServerAddress getLogicalAddress() {
        return logicalAddress;
    }

    /**
     * Returns the system resource address of this O2G server.
     * <p>
     * The system resource address provides access to internal server resources
     * and monitoring endpoints. This address may differ from the logical address
     * in HA deployments.
     * </p>
     *
     * @return the system resource {@link ServerAddress} of this O2G server
     * @since 2.7.5
     */
    public ServerAddress getSystemResources() {
        return systemResources;
    }

    /**
     * Returns the date and time at which this O2G server was started.
     *
     * @return the server start {@link Date}
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns whether this O2G server is deployed in high-availability (HA) mode.
     * <p>
     * When {@code true}, the server operates with a primary and a secondary node.
     * Use {@link #getPrimary()} and {@link #getSecondary()} to retrieve the
     * respective hostnames.
     * </p>
     *
     * @return {@code true} if the server is in HA mode; {@code false} otherwise
     */
    public boolean isHa() {
        return ha;
    }

    /**
     * Returns the fully qualified domain name (FQDN) of the primary server node.
     * <p>
     * This value is only meaningful when the server is deployed in HA mode
     * ({@link #isHa()} returns {@code true}).
     * </p>
     *
     * @return the FQDN of the primary server node, or {@code null} if not in HA mode
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * Returns the software version of the primary server node.
     * <p>
     * This value is only meaningful when the server is deployed in HA mode
     * ({@link #isHa()} returns {@code true}).
     * </p>
     *
     * @return the version string of the primary server node, or {@code null} if not in HA mode
     */
    public String getPrimaryVersion() {
        return primaryVersion;
    }

    /**
     * Returns the status of services running on the primary server node.
     * <p>
     * This value is only meaningful when the server is deployed in HA mode
     * ({@link #isHa()} returns {@code true}).
     * </p>
     *
     * @return the {@link SystemServices} status of the primary node, or {@code null} if not in HA mode
     */
    public SystemServices getPrimaryServicesStatus() {
        return primaryServicesStatus;
    }

    /**
     * Returns the fully qualified domain name (FQDN) of the secondary server node.
     * <p>
     * This value is only meaningful when the server is deployed in HA mode
     * ({@link #isHa()} returns {@code true}).
     * </p>
     *
     * @return the FQDN of the secondary server node, or {@code null} if not in HA mode
     */
    public String getSecondary() {
        return secondary;
    }

    /**
     * Returns the software version of the secondary server node.
     * <p>
     * This value is only meaningful when the server is deployed in HA mode
     * ({@link #isHa()} returns {@code true}).
     * </p>
     *
     * @return the version string of the secondary server node, or {@code null} if not in HA mode
     */
    public String getSecondaryVersion() {
        return secondaryVersion;
    }

    /**
     * Returns the status of services running on the secondary server node.
     * <p>
     * This value is only meaningful when the server is deployed in HA mode
     * ({@link #isHa()} returns {@code true}).
     * </p>
     *
     * @return the {@link SystemServices} status of the secondary node, or {@code null} if not in HA mode
     */
    public SystemServices getSecondaryServicesStatus() {
        return secondaryServicesStatus;
    }

    /**
     * Returns the OmniPCX Enterprise nodes currently connected to this O2G server.
     * <p>
     * Each entry in the returned collection represents a single OXE node and
     * its current connection state. The collection is unmodifiable.
     * </p>
     *
     * @return an unmodifiable {@link Collection} of {@link PbxStatus} objects;
     *         never {@code null}, but may be empty if no nodes are connected
     */
    public Collection<PbxStatus> getPbxs() {
        return (pbxs == null) ? Collections.emptyList() : Collections.unmodifiableCollection(pbxs);
    }

    /**
     * Returns the license status of this O2G server.
     * <p>
     * The license status indicates which licensed features are active and whether
     * the current license is valid.
     * </p>
     *
     * @return the {@link LicenseStatus} of this O2G server
     */
    public LicenseStatus getLicense() {
        return license;
    }

    /**
     * Returns the configuration type of this O2G server.
     * <p>
     * The configuration type determines the operational mode of the server.
     * See {@link Configuration} for possible values.
     * </p>
     *
     * @return the {@link Configuration} type of this O2G server
     * @see Configuration
     */
    public Configuration getConfigurationType() {
        return configurationType;
    }

    /**
     * Returns the application identifier of this O2G server instance.
     * <p>
     * The application ID uniquely identifies this O2G instance within a deployment
     * and may be used for logging, auditing, or multi-instance scenarios.
     * </p>
     *
     * @return the application ID string of this O2G instance
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Returns the subscriber filter policy applied by this O2G server.
     * <p>
     * The subscriber filter controls which OmniPCX Enterprise subscribers are
     * automatically imported into O2G during provisioning.
     * See {@link SubscriberFilter} for possible values.
     * </p>
     *
     * @return the {@link SubscriberFilter} applied by this O2G server
     * @see SubscriberFilter
     */
    public SubscriberFilter getSubscriberFilter() {
        return subscriberFilter;
    }
}