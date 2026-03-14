/*
* Copyright 2026 ALE International
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
package com.ale.o2g.types.ccp;

import com.ale.o2g.internal.util.HexaString;
import com.ale.o2g.types.telephony.TrunkIdentification;
import com.ale.o2g.types.telephony.call.CorrelatorData;

/**
 * {@code CallDataPilot} represents the detailed information associated with a
 * pilot call.
 * <p>
 * It provides access to the initial called party, call anonymity status, trunk
 * information for external calls, network rerouting status, and any associated
 * correlator data.
 * </p>
 */
public class CallDataPilot {
    private String initialCalled;
    private boolean anonymous;
    private String associatedData;
    private String hexaBinaryAssociatedData;
    private TrunkIdentification trunkIdentification;
    private boolean networkRerouted;

    /**
     * Returns the initial party that was called.
     *
     * @return the initial called party
     */
    public final String getInitialCalled() {
        return initialCalled;
    }

    /**
     * Indicates whether this call is anonymous.
     *
     * @return {@code true} if the call is anonymous; {@code false} otherwise
     */
    public final boolean isAnonymous() {
        return anonymous;
    }

    /**
     * Returns trunk information in case of an external call.
     *
     * @return the {@link TrunkIdentification} associated with the call, or
     *         {@code null} if not applicable
     */
    public final TrunkIdentification getTrunkIdentification() {
        return trunkIdentification;
    }

    /**
     * Indicates whether this call has been rerouted over the network.
     *
     * @return {@code true} if the call has been rerouted; {@code false} otherwise
     */
    public final boolean isNetworkRerouted() {
        return networkRerouted;
    }

    /**
     * Returns the correlator data associated with this call.
     * <p>
     * The data may come from either a standard string or a hexadecimal binary
     * representation. If no data is attached, this method returns {@code null}.
     * </p>
     *
     * @return the {@link CorrelatorData} associated with this call, or {@code null}
     *         if none is available
     */
    public final CorrelatorData getCorrelatorData() {
        if (associatedData != null) {
            return new CorrelatorData(associatedData);
        }
        else if (hexaBinaryAssociatedData != null) {
            return new CorrelatorData(HexaString.toByteArray(hexaBinaryAssociatedData));
        }
        else {
            return null;
        }
    }

    /**
     * Protected constructor to prevent direct instantiation.
     * <p>
     * Instances are typically created internally by the system when a pilot call
     * occurs.
     * </p>
     */
    protected CallDataPilot() {
    }
}
