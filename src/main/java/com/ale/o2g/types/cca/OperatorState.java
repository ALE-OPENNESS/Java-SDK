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
 * {@code OperatorState} represents the current state of a CCD operator.
 * <p>
 * A CCD operator can have a static (main) state that reflects login status
 * and a dynamic (sub) state that reflects their current activity within the
 * call distribution system.
 * <p>
 * This class provides information about the operator's main and dynamic states,
 * the pro-ACD device they are logged on, the agent group (processing group) they
 * are associated with, and whether they are in a withdraw state.
 *
 */
public class OperatorState {

    /**
     * {@code OperatorMainState} represents the static login/logoff state of a CCD operator.
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public static enum OperatorMainState {

        /**
         * The O2G server is unable to determine the operator's main state.
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
     * {@code OperatorDynamicState} represents the dynamic state of a CCD operator.
     * <p>
     * Dynamic states indicate the operator's current activity or wrap-up phase.
     */
    @JsonEnumDeserializerFallback(value = "UNKNOWN")
    public static enum OperatorDynamicState {

        /** The operator is ready to receive calls. */
        READY,

        /** The operator is logged on but not entered into any agent group. */
        OUT_OF_PG,

        /** The operator is busy handling a call. */
        BUSY,

        /** The operator is entering a transaction code. */
        TRANSACTION_CODE_INPUT,

        /** The operator is in automatic wrap-up phase. */
        WRAPUP,

        /** The operator is in a pause state. */
        PAUSE,

        /** The operator has withdrawn from call distribution. */
        WITHDRAW,

        /** The operator is in wrap-up due to handling an instant message (IM). */
        WRAPUP_IM,

        /** The operator is in wrap-up due to handling an email. */
        WRAPUP_EMAIL,

        /** The operator is in wrap-up due to handling an email, but can still receive CCD calls. */
        WRAPUP_EMAIL_INTERRUPTIBLE,

        /** The operator is in wrap-up after an outbound call. */
        WRAPUP_OUTBOUND,

        /** The operator is in wrap-up after a web callback call. */
        WRAPUP_CALLBACK,
        
        /** The O2G server is unable to determine the operator's dynamic state. */
        UNKNOWN
    }

    private OperatorMainState mainState;
    private OperatorDynamicState subState;
    private String proAcdDeviceNumber;
    private String pgNumber;
    private Integer withdrawReason;
    private boolean withdraw;

    /**
     * Returns the static (main) state of the operator, indicating login or error status.
     *
     * @return the operator's main state
     */
    public final OperatorMainState getMainState() {
        return mainState;
    }

    /**
     * Returns the dynamic (sub) state of the operator, indicating their current activity.
     *
     * @return the operator's dynamic state, or {@code null} if the operator is logged off
     */
    public final OperatorDynamicState getSubState() {
        return subState;
    }

    /**
     * Returns the pro-ACD device number the operator is logged on.
     *
     * @return the pro-ACD extension number, or {@code null} if the operator is logged off
     */
    public final String getProAcdDeviceNumber() {
        return proAcdDeviceNumber;
    }

    /**
     * Returns the processing group (PG) the operator is logged in.
     *
     * @return the agent group number, or {@code null} if the operator is not entered in any agent group
     */
    public final String getPgNumber() {
        return pgNumber;
    }

    /**
     * Returns the withdraw reason index.
     * <p>
     * This value corresponds to the index of withdraw reasons defined for the agent's processing group.
     *
     * @return the withdraw reason index, or {@code null} if the operator is not in withdraw state
     */
    public final Integer getWithdrawReason() {
        return withdrawReason;
    }

    /**
     * Indicates whether the operator is currently in withdraw state.
     *
     * @return {@code true} if the operator is in withdraw state, {@code false} otherwise
     */
    public final boolean isWithdraw() {
        return withdraw;
    }

    protected OperatorState() {
    }
}
