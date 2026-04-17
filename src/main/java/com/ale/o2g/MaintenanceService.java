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
package com.ale.o2g;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.maintenance.SystemStatus;

/**
 * The {@code MaintenanceService} allows retrieving information about the
 * system state, in particular information on the OmniPCX Enterprise nodes and
 * their connection state. Information about licenses is also provided per item:
 * total allocated licenses, number currently in use, and expiration date.
 * <p>
 * This service does not require any specific license on the O2G server.
 */
public interface MaintenanceService extends IService {

    /**
     * Retrieves information about the system state and the total number of each
     * license type available for the system.
     * <p>
     * This operation is restricted to an administrator session only.
     *
     * @return A {@link SystemStatus} object on success; {@code null} otherwise
     */
    SystemStatus getSystemStatus();
}
