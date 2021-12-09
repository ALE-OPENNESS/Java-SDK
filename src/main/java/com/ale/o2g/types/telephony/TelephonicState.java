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
package com.ale.o2g.types.telephony;

import java.util.Collection;

import com.ale.o2g.types.telephony.device.Capabilities;
import com.ale.o2g.types.telephony.user.UserState;

/**
 * Represent the telephonic state of a user.
 */
public final class TelephonicState {
    private Collection<Call> calls;
    private Collection<Capabilities> deviceCapabilities;
    private UserState userState;

    /**
     * Returns the collection of active calls.
     * 
     * @return the collection of active calls
     */
    public final Collection<Call> getCalls() {
        return calls;
    }

    /**
     * Returns the collection of device capabilities.
     * 
     * @return the collection of device capabilities
     */
    public final Collection<Capabilities> getDeviceCapabilities() {
        return deviceCapabilities;
    }

    /**
     * Gets the user state.
     * 
     * @return the user state
     */
    public final UserState getUserState() {
        return (userState == null) ? UserState.UNKNOWN : userState;
    }

}
