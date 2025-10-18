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
package com.ale.o2g.types.cca;

/**
 * /** {@code WithdrawReason} represents a reason why a CCD agent is in a
 * withdraw state.
 * <p>
 * Withdraw reasons are used by the CCD system for reporting and statistical
 * purposes, helping to understand why an agent is temporarily unavailable for
 * call distribution. Each reason has a unique index within the processing group
 * and a descriptive label.
 */
public class WithdrawReason {

    private int index;
    private String label;

    /**
     * Returns the index of this withdraw reason within the agent's processing group.
     *
     * @return the index of the withdraw reason
     */
    public final int getIndex() {
        return index;
    }

    /**
     * Returns the label describing this withdraw reason.
     *
     * @return the human-readable label of the withdraw reason
     */
    public final String getLabel() {
        return label;
    }

    protected WithdrawReason() {
    }

}
