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

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code OperatorState} represents the state of a CCD operator.
 */
public class OperatorState {

    /**
     * {@code OperatorMainState} represents the login, logoff status of a CCD
     * operator.
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public static enum OperatorMainState {

        /**
         * The O2G server is unable to get the operator main state.
         */
        UNKNOWN,

        /**
         * The operator is logged on a pro-acd set.
         */
        LOG_ON,

        /**
         * The operator is logged off.
         */
        LOG_OFF,

        /**
         * Error status.
         */
        ERROR
    }

    /**
     * {@code OperatorDynamicState} represents the CCD operator dynamic state.
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public static enum OperatorDynamicState {

        /**
         * The operator is ready.
         */
        READY,

        /**
         * The operator is logged but out of an agent group.
         */
        OUT_OF_PG,

        /**
         * The operator is busy.
         */
        BUSY,

        /**
         * The operator is in the transaction code phase.
         */
        TRANSACTION_CODE_INPUT,

        /**
         * The operator is in the automatic wrapup phase.
         */
        WRAPUP,

        /**
         * The operator is in pause.
         */
        PAUSE,

        /**
         * The operator is in withdraw from the call distribution.
         */
        WITHDRAW,

        /**
         * The operator is in withdraw from the call distribution because he is treating
         * an IM.
         */
        WRAPUP_IM,

        /**
         * The operator is in withdraw from the call distribution because he is treating
         * an email.
         */
        WRAPUP_EMAIL,

        /**
         * The operator is in withdraw from the call distribution because he is treating
         * an email, nevertheless, a CCD call can be distributed on this operator.
         */
        WRAPUP_EMAIL_INTERRUPTIBLE,

        /**
         * The operator is in wrapup after an outbound call.
         */
        WRAPUP_OUTBOUND,

        /**
         * The operator is in wrapup after a callback call.
         */
        WRAPUP_CALLBACK,
        
        /**
         * The O2G server is unable to get the operator state.
         */
        UNKNOWN
    }

    private OperatorMainState mainState;
    private OperatorDynamicState subState;
    private String proAcdDeviceNumber;
    private String pgNumber;
    private Integer withdrawReason;
    private boolean withdraw;

    /**
     * Returns the operator static state.
     * @return the mainState
     */
    public final OperatorMainState getMainState() {
        return mainState;
    }

    /**
     * Returns the operator dynamic state.
     * @return the operator dynamic state or {@code null} if the operator is logged off.
     */
    public final OperatorDynamicState getSubState() {
        return subState;
    }

    /**
     * Returns the pro-acd this operator is logged on.
     * @return the pro-acd extension number or {@code null} if the operator is logged off.
     */
    public final String getProAcdDeviceNumber() {
        return proAcdDeviceNumber;
    }

    /**
     * Returns the agent group this operator is logged in.
     * @return the agent group number or {@code null} if the operator is not entered in an agent group.
     */
    public final String getPgNumber() {
        return pgNumber;
    }

    /**
     * Returns the withdraw reason.
     * <p>
     * An int value that represents the withdraw reason index in the withdraw reasons managed in an agent group, 
     * or a {@code null} value if the operator in not in withdraw state.
     * @return the withdrawReason
     */
    public final Integer getWithdrawReason() {
        return withdrawReason;
    }

    /**
     * Returns whether the operator is in withdraw state.
     * @return {@code true} if the operator is in withdraw state;
     *         {@code false} otherwise.
     */
    public final boolean isWithdraw() {
        return withdraw;
    }

    protected OperatorState() {
    }
}
