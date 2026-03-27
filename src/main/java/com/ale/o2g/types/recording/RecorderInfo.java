/*
* Copyright 2025 ALE International
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
package com.ale.o2g.types.recording;

import java.util.Collection;

/**
 *
 */
public class RecorderInfo {

    private String name;
    private String host;
    private String ipAddress;
    private boolean secured;
    private String siteId;
    private Collection<RecordedDevice> devices;
    private boolean connected;
    private String status;

    /**
     * Returns the unique name of the recorder.
     *
     * @return the recorder name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the host name of the recorder server.
     *
     * @return the host name
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the IP address of the recorder server.
     *
     * @return the IP address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Indicates whether the recorder is secured.
     *
     * @return {@code true} if the recorder uses security mechanisms; {@code false} otherwise
     */
    public boolean isSecured() {
        return secured;
    }

    /**
     * Returns the identifier of the site where the recorder is located.
     *
     * @return the site identifier
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * Returns the collection of devices managed by this recorder.
     *
     * @return a collection of {@link RecordedDevice} instances
     */
    public Collection<RecordedDevice> getDevices() {
        return devices;
    }

    /**
     * Indicates whether the recorder is currently connected.
     *
     * @return {@code true} if the recorder is connected; {@code false} otherwise
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Returns the current operational status of the recorder.
     *
     * @return the status string
     */
    public String getStatus() {
        return status;
    }
    
    protected RecorderInfo() {
        
    }
}
