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
 * {@code AcdData} represents the acd extension for an acd call.
 */
public final class AcdData {
    private Info callInfo;
    private QueueData queueData;
    private String pilotNumber;
    private String rsiNumber;
    private boolean supervisedTransfer;
    private PilotTransferInfo pilotTransferInfo;

    /**
     * Returns the information associated to this acd call.
     * 
     * @return the call info.
     */
    public final Info getCallInfo() {
        return callInfo;
    }

    /**
     * Returns the information about the queue that has distributed this call.
     * 
     * @return the queue data.
     */
    public final QueueData getQueueData() {
        return queueData;
    }

    /**
     * Returns the pilot who has distributed this call.
     * 
     * @return the pilot number.
     */
    public final String getPilotNumber() {
        return pilotNumber;
    }

    /**
     * Returns the RSI point that has distribuet this call.
     * 
     * @return the rsi number
     */
    public final String getRsiNumber() {
        return rsiNumber;
    }

    /**
     * Returns whether the transfer on the pilot was supervised.
     * 
     * @return {@code true} if the transfer was supervised.
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

    protected AcdData() {
    }

}
