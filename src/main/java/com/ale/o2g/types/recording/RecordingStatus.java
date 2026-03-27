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
package com.ale.o2g.types.recording;

import java.util.Collection;

/**
 * Represent the global status of recording service.
 */
public class RecordingStatus {

    private Collection<RecorderInfo> recorders;
    private Collection<RecordedDevice> devices;

    /**
     * Returns the configured recorders.
     * <p>
     * Can be {@code null} in case of an error or if no recorder is configured.
     *
     * @return a list of {@link RecorderInfo} objects representing the configured
     *         recorders, or {@code null}
     */
    public final Collection<RecorderInfo> getRecorders() {
        return recorders;
    }

    /**
     * Returns the recorded devices.
     * <p>
     * Can be {@code null} in case of an error or if no recorded device is configured.
     *
     * @return a list of {@link RecordedDevice} objects representing the configured
     *         recorded devices, or {@code null}
     */
    public final Collection<RecordedDevice> getDevices() {
        return devices;
    }

    protected RecordingStatus() {
        
    }
}
