/*
* Copyright 2024 ALE International
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
 * {@code PilotInfo} represents the the pilot information.
 * @since 2.7
 */
public class PilotInfo {

    private String number;
    private int waitingTime;
    private boolean saturation;
    private boolean supervisedTransfer;
    private PilotTransferInfo pilotTransferInfo;
    

    /**
     * Returns the pilot number.
     * 
     * @return the pilot number.
     */
    public final String getPilotNumber() {
        return number;
    }
    
    /**
     * Returns the estimated waiting time in the queue.
     * 
     * @return the waiting time.
     */
    public final int getWaitingTime() {
        return waitingTime;
    }

    /**
     * Return whether this queue is saturated.
     * 
     * @return {@code true} if the queue is saturated; {@code false} otherwise.
     */
    public final boolean isSaturated() {
        return saturation;
    }

    /**
     * Returns whether the transfer on the pilot can be supervised.
     * 
     * @return {@code true} if the transfer can be supervised.
     */
    public final boolean isSupervisedTransfer() {
        return supervisedTransfer;
    }

    /**
     * Returns the information about the possible transfer on a pilot.
     * 
     * @return the information about the possible transfer or {@code null} if there
     *         is no transfer in progress.
     */
    public final PilotTransferInfo getPilotTransferInfo() {
        return pilotTransferInfo;
    }

    protected PilotInfo() {
    }

}
