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
package com.ale.o2g.types.analytics;

import java.util.List;

/**
 * {@code ChargingResult} class represents the result of a query to the OmniPCX
 * Enterprise.
 */
public class ChargingResult {

    private List<Charging> chargings;
    private TimeRange range;
    private int chargingFileCount;
    private int totalTicketCount;
    private int valuableTicketCount;

    /**
     * Returns the list of charging elements.
     * @return the chargings
     */
    public final List<Charging> getChargings() {
        return chargings;
    }

    /**
     * Returns the range that has been specified to query this result.
     * @return the range
     */
    public final TimeRange getRange() {
        return range;
    }

    /**
     * Returns the number of analysed charging files in the OmniPCX Enterprise.
     * @return the chargingFileCount
     */
    public final int getChargingFileCount() {
        return chargingFileCount;
    }

    /**
     * Returns the total number of analysed tickets.
     * @return the totalTicketCount
     */
    public final int getTotalTicketCount() {
        return totalTicketCount;
    }

    /**
     * Returns the total number of valuable tickets; tickets with a non zero cost.
     * @return the valuableTicketCount
     */
    public final int getValuableTicketCount() {
        return valuableTicketCount;
    }

    protected ChargingResult(List<Charging> chargings, TimeRange range, int chargingFileCount, int totalTicketCount,
            int valuableTicketCount) {
        this.chargings = chargings;
        this.range = range;
        this.chargingFileCount = chargingFileCount;
        this.totalTicketCount = totalTicketCount;
        this.valuableTicketCount = valuableTicketCount;
    }
}
