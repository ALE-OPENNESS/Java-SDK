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
package com.ale.o2g.types.telephony.device;

/**
 * {@code Capabilities} represents the capability of a device.
 */
public class Capabilities {
    private String deviceId;
    private boolean makeCall;
    private boolean makeBusinessCall;
    private boolean makePrivateCall;
    private boolean unParkCall;

    /**
     * Returns the device identifier.
     * 
     * @return the device id.
     */
    public final String getDeviceId() {
        return deviceId;
    }

    /**
     * Returns whether this device can make a call.
     * 
     * @return {@code true} if the device can make a call; {@code false} otherwise.
     */
    public final boolean canMakeCall() {
        return makeCall;
    }

    /**
     * Returns whether this device can make a business call.
     * 
     * @return {@code true} if the device can make a business call; {@code false} otherwise.
     */
    public final boolean canMakeBusinessCall() {
        return makeBusinessCall;
    }


    /**
     * Returns whether this device can make a private call.
     * 
     * @return {@code true} if the device can make a private call; {@code false} otherwise.
     */
    public final boolean canMakePrivateCall() {
        return makePrivateCall;
    }


    /**
     * Returns whether this device can unpark a call.
     * 
     * @return {@code true} if the device can unpark call; {@code false} otherwise.
     */
    public final boolean canUnParkCall() {
        return unParkCall;
    }

}
