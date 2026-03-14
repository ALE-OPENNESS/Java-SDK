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
import java.util.Collections;

import com.ale.o2g.types.telephony.device.Capabilities;
import com.ale.o2g.types.telephony.user.UserState;

/**
 * Represents the telephonic state of a user.
 * <p>
 * This class provides information about the user's current call activity, device capabilities,
 * and overall telephonic status within the system.
 * </p>
 */
public class TelephonicState {
    private Collection<Call> calls;
    private Collection<Capabilities> deviceCapabilities;
    private UserState userState;

    /**
     * Returns the collection of active calls associated with the user.
     * <p>
     * If the user has no active calls, an empty unmodifiable collection is returned.
     * </p>
     *
     * @return an unmodifiable {@link Collection} of {@link Call} objects, never {@code null}
     */
    public final Collection<Call> getCalls() {
        return (calls == null) ? Collections.emptyList() : Collections.unmodifiableCollection(calls);
    }

    /**
     * Returns the collection of capabilities for the user's devices.
     * <p>
     * Each {@link Capabilities} object describes the actions the device can perform,
     * such as making a call or un-parking a call. If no device capabilities are available,
     * an empty unmodifiable collection is returned.
     * </p>
     *
     * @return an unmodifiable {@link Collection} of {@link Capabilities}, never {@code null}
     */
    public final Collection<Capabilities> getDeviceCapabilities() {
        return (deviceCapabilities == null) ? Collections.emptyList() : Collections.unmodifiableCollection(deviceCapabilities);
    }

    /**
     * Returns the overall telephonic state of the user.
     * <p>
     * If the user state has not been explicitly set, {@link UserState#UNKNOWN} is returned.
     * </p>
     *
     * @return the user's telephonic {@link UserState}, never {@code null}
     */
    public final UserState getUserState() {
        return (userState == null) ? UserState.UNKNOWN : userState;
    }

    /**
     * Protected constructor to prevent direct instantiation.
     * <p>
     * Instances are typically created internally by the system to reflect
     * the current telephonic state of a user.
     * </p>
     */
    protected TelephonicState() {
    }
}