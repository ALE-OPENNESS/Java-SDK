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
package com.ale.o2g.types.telephony;

import java.util.Collection;
import java.util.Collections;

/**
 * {@code TrunkIdentification} provides detailed information about an external call's 
 * network routing.
 * <p>
 * Specifically, it contains the network timeslot used for the call and the trunk 
 * equipment numbers (NEQTs) associated with the call. This information is relevant 
 * for calls routed through external trunks, helping to identify which timeslot and 
 * which physical or logical trunk equipment were involved in handling the call.
 * </p>
 */
public class TrunkIdentification {

    private Integer networkTimeslot;
    private Collection<Integer> trunkNeqt;
    
    /**
     * Returns the network timeslot assigned to this call.
     * <p>
     * If the timeslot is not available, {@code -1} is returned.
     * </p>
     *
     * @return the network timeslot, or {@code -1} if not assigned
     */
    public final int getNetworkTimeslot() {
        return (networkTimeslot == null) ? -1 : networkTimeslot;
    }

    /**
     * Returns the trunk equipment numbers (NEQTs) associated with this call.
     * <p>
     * The collection may contain one or two equipment numbers, representing the 
     * trunk(s) through which the call was routed. If no trunk information is available, 
     * an empty unmodifiable collection is returned.
     * </p>
     *
     * @return an unmodifiable {@link Collection} of trunk equipment numbers, never {@code null}
     */
    public final Collection<Integer> getTrunkNeqts() {
        return (trunkNeqt == null) ? Collections.emptyList() : Collections.unmodifiableCollection(trunkNeqt);
    }

    /**
     * Protected constructor to prevent direct instantiation.
     * <p>
     * Instances are typically created internally by the system when processing 
     * external calls.
     * </p>
     */
    protected TrunkIdentification() {
    }

}