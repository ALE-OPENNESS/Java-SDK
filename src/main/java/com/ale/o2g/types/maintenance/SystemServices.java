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

/**
 * Represents the status of all O2G system services.
 * Provides information about individual services, global IP address,
 * and DRBD status when the system is running in High Availability (HA) mode.
 */
public class SystemServices {

    /**
     * Represents a single system service running in the O2G environment.
     * Contains the service name, its current status, and running mode.
     */
    public static class SystemService {
        private String name;
        private String status;
        private String mode;

        protected SystemService() {
        }

        /**
         * Returns the name of this system service.
         *
         * @return the service name
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the current status of this system service.
         * For example, "running", "stopped", or "failed".
         *
         * @return the service status
         */
        public String getStatus() {
            return status;
        }

        /**
         * Returns the mode in which this service is running.
         * For example, "active", "passive", or "maintenance".
         *
         * @return the service mode
         */
        public String getMode() {
            return mode;
        }
    }

    private Collection<SystemService> services;
    private String globalIPAdress;
    private String drbd;

    /**
     * Returns an unmodifiable collection of all system services.
     * Each element provides details about a running service.
     *
     * @return a collection of {@link SystemService} objects
     */
    public Collection<SystemService> getServices() {
        return (services == null) ? Collections.emptyList() : Collections.unmodifiableCollection(services);
    }

    /**
     * Returns the status of the global IP address if the system is in HA mode.
     * Can be used to verify whether the HA IP is active and reachable.
     *
     * @return the global IP address status, or {@code null} if not applicable
     */
    public String getGlobalIPAdress() {
        return globalIPAdress;
    }

    /**
     * Returns the status of the DRBD service if the system is in HA mode.
     * DRBD (Distributed Replicated Block Device) ensures data replication between nodes.
     *
     * @return the DRBD service status, or {@code null} if not applicable
     */
    public String getDrbdStatus() {
        return drbd;
    }
<<<<<<< HEAD

	protected SystemServices() {   
    }
=======
>>>>>>> 668ec6157fe65d65bc91c1ca3bc1fc8e8d236d73
}