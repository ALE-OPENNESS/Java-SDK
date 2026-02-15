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
package com.ale.o2g.types.telephony.call.acd;

/**
 * {@code PilotTransferInfo} provides possible transfer information on a CCD
 * pilot.
 */
public class PilotTransferInfo {
    private boolean transferPossible;
    private PilotStatus pilotStatus;

    /**
     * Returns whether the transfer on this CCD pilot is possible.
     * 
     * @return {@code true} if the transfer is possible; {@code false} otherwise.
     */
    public final boolean isTransferPossible() {
        return transferPossible;
    }

    /**
     * Return the pilot state.
     * 
     * @return the pilot state
     */
    public final PilotStatus getPilotState() {
        return pilotStatus;
    }

    protected PilotTransferInfo() {
        
    }
}
