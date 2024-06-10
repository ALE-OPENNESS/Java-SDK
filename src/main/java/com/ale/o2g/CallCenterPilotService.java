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

/**
 * The {@code CallCenterPilotService} allows an administrator to monitor CCD
 * pilots.
 * <p>
 * Using this service requires having a <b>CONTACTCENTER_SERVICE</b> license in
 * CAPEX mode, or 40 api-tel-f subscription in OPEX mode (Purple On Demand).
 * 
 * @since 2.7
 */
public interface CallCenterPilotService extends IService {

    /**
     * Start the monitoring on the specified pilot. If a
     * {@code CallCenterPilotEventListener} has been configured. Notifications are
     * received on this listener on calls on the pilot.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #monitorStop(int, String)
     */
    boolean monitorStart(int nodeId, String pilotNumber);

    /**
     * Stop the monitoring on the specified pilot.
     * 
     * @param nodeId      the PCX Enterprise node id
     * @param pilotNumber the pilot number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #monitorStart(int, String)
     */    
    boolean monitorStop(int nodeId, String pilotNumber);
}
