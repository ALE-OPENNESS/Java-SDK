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
package com.ale.o2g.types;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code RestErrorInfo} gives the detail information on an error raised during
 * a service invocation. Most of the O2G services return the following code in
 * case of error:
 * <table>
 * <caption>REST Service error code</caption>
 * <tr>
 * <th>Code</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>400</td>
 * <td>Bad request</td>
 * </tr>
 * <tr>
 * <td>403</td>
 * <td>Forbidden</td>
 * </tr>
 * <tr>
 * <td>404</td>
 * <td>Not found</td>
 * </tr>
 * <tr>
 * <td>500</td>
 * <td>Internal server error</td>
 * </tr>
 * <tr>
 * <td>503</td>
 * <td>Service unavailable</td>
 * </tr>
 * </table>
 */
public class RestErrorInfo {

    /**
     * {@code RoutingErrorInfo} class provides complementary information in case of
     * an error in the {@linkplain com.ale.o2g.RoutingService RoutingService}.
     */
    static class RoutingErrorInfo {

        /**
         * {@code RoutingErrorCause} represents the differents routing error causes.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum RoutingErrorCause {

            /**
             * Unknown routing error cause.
             */
            UNKNOWN,

            /**
             * The phone number does not comply with formating rules. For examples: The
             * phone number does not respect the following regular expression:
             * [+]?[0-9A-D*#\\(\\) ]{1,49}.
             */
            BAD_PHONE_NUMBER_FORMAT,

            /**
             * The given device number as current device is not valid. For examples: The
             * device is not in an acceptable state to be used.
             */
            INVALID_CURRENT_DEVICE,

            /**
             * The given forward route is not valid. For examples:
             * <ul>
             * <li>The forwarded destination type is not one of VOICEMAIL or NUMBER.</li>
             * <li>The forwarded destination is not acceptable.</li>
             * <li>A loop in the forward chain has been detected.</li>
             * </ul>
             */
            INVALID_FORWARD_ROUTE,

            /**
             * The given overflow route is not valid. For examples: The overflow destination
             * is not acceptable.
             */
            INVALID_OVERFLOW_ROUTE,

            /**
             * Parameter must not be null nor empty.
             */
            NULL_OR_EMPTY_PARAMETER,

            /**
             * Parameter must not be null.
             */
            NULL_PARAMETER,

            /**
             * Parameter must not be null.
             */
            UNAUTHORIZED_CANCEL_OVERFLOW,

            /**
             * The destination type is set to USER, but the number does not correspond to a
             * user.
             */
            UNAUTHORIZED_NOT_A_USER,

            /**
             * Overflow has been rejected because of barring rules.
             */
            UNAUTHORIZED_OVERFLOW,

            /**
             * The given phone number is unauthorized. For examples:
             * <ul>
             * <li>Barring as rejected the number.</li>
             * <li>The destination can not be a service number.</li>
             * <li>The destination is a voicemail, but user has no rights to use it.</li>
             * </ul>
             */
            UNAUTHORIZED_PHONE_NUMBER
        }

        /**
         * {@code RoutingErrorType} represents the different type of routing error.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum RoutingErrorType {

            /**
             * Unknown routing error.
             */
            UNKNOWN,

            /**
             * The routing service has rejected the request for a bad or missing parameter
             * reason.
             */
            BAD_PARAMETER_VALUE,

            /**
             * Routing service request has been rejected because of limitation configured on
             * the concerned user. For examples:
             * <ul>
             * <li>Overflow on busy is not allowed (barring limitation).</Li>
             * <li>Cancel overflow is not allowed: (barring limitation).</Li>
             * <li>Phone number to other destination is not authorized (dial plan
             * limitation).</Li>
             * </ul>
             */
            UNAUTHORIZED,

            /**
             * Requested operation is not supported by the routing service.
             */
            INVALID_OPERATION,

            /**
             * The provided phone number can not be fully resolved in the current dial plan.
             * For example: Setting route with a destination type Other, containing a
             * partially matching number (e.g. 3253 instead of 32535).
             */
            INCOMPLETE_PHONE_NUMBER,

            /**
             * The provided phone number can not be resolved in the current dial plan. For
             * example: Setting route with a destination type NUMBER, containing an unknown
             * number.
             */
            UNKNOWN_PHONE_NUMBER
        }

        public RoutingErrorType errorType;
        public RoutingErrorCause errorCause;

        public String message;

        /**
         * Returns the error type.
         * 
         * @return the error type.
         */
        public final RoutingErrorType getErrorType() {
            return errorType;
        }

        /**
         * Returns the error cause.
         * 
         * @return the error cause
         */
        public final RoutingErrorCause getErrorCause() {
            return errorCause;
        }

        /**
         * Returns an additional textual error information provided by the routing
         * service.
         * 
         * @return the message.
         */
        public final String getMessage() {
            return message;
        }

    }

    /**
     * {@code TelephonyErrorInfo} class provides complementary information in case
     * of an error in the {@linkplain com.ale.o2g.TelephonyService
     * TelephonyService}.
     */
    static class TelephonyErrorInfo {

        /**
         * {@code TelephonyErrorCause} represents the telephony error cause.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum TelephonyErrorCause {

            /**
             * Unknown telephony error cause.
             */
            UNKNOWN,

            /**
             * The call server can not process the request because the calling device is not
             * an acceptable one.
             */
            INVALID_CALLING,

            /**
             * Destination is not a correct number.
             */
            INVALID_DESTINATION,

            /**
             * The referenced call reference is not valid.
             */
            INVALID_CALL_ID,

            /**
             * The current state of the call does not permit the operation.
             */
            INVALID_CONNECTION_STATE,

            /**
             * The device is out of service.
             */
            DEVICE_OUT_OF_SERVICE,

            /**
             * The device is not in valid.
             */
            INVALID_DEVICE,

            /**
             * The device state is incompatible with the request.
             */
            INVALID_DEVICE_STATE,

            /**
             * The data parameter has invalid value.
             */
            INVALID_DATA,

            /**
             * The destination is busy. All the user phone lines are already engaged.
             */
            RESOURCE_BUSY
        }

        /**
         * {@code TelephonyErrorType} represents the telephony error types.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum TelephonyErrorType {
            /**
             * Unknown error, the O2G is unable to identify the root cause of the error.
             */
            UNKNOWN,

            /**
             * The specified call reference in invalid.
             */
            CALL_REFERENCE_NOT_FOUND,

            /**
             * The telephony service can not execute the request because the specified leg
             * can not be found. For exemple: request an online recording while there is no
             * active leg.
             */
            LEG_NOT_FOUND,

            /**
             * Some parameters attached to the request are not correct.
             */
            BAD_PARAMETER_VALUE,

            /**
             * The telephony service can not execute the request because the current
             * telephony state does not permit such operation.
             */
            INCOMPATIBLE_WITH_STATE,

            /**
             * The telephony service can not execute the request because the corresponding
             * service is not provided in this context.
             */
            SERVICE_NOT_PROVIDED,

            /**
             * The telephony service can not execute the request because it relies on
             * another service which is unavailable. For exemple: Redirecting to voicemail
             * is requested, but user has no voicemail.
             */
            SERVICE_UNAVAILABLE,

            /**
             * The telephony service is starting up and has not finished its initialization.
             */
            INITIALIZATION,

            /**
             * Telephony service request has been rejected. For exemple: The user has
             * already a call ongoing and he has no Advanced Telephony license.
             */
            UNAUTHORIZED,

            /**
             * Telephony service request failed because of an error generated by the call
             * server. More details can be provided by {@linkplain TelephonyErrorCause}.
             */
            CALL_SERVER_ERROR,

            /**
             * An operation invoked on the telephony service has exceeded the expected
             * response timeout (default 5 seconds).
             */
            REQUEST_TIMEOUT,
        }

        public TelephonyErrorType errorType;
        public TelephonyErrorCause errorCause;
        public String message;

        /**
         * Returns the error type.
         * 
         * @return the error type.
         */
        public final TelephonyErrorType getErrorType() {
            return errorType;
        }

        /**
         * Returns the error cause.
         * 
         * @return the error cause.
         */
        public final TelephonyErrorCause getErrorCause() {
            return errorCause;
        }

        /**
         * Returns an additional textual error information provided by the telephony
         * service.
         * 
         * @return the message
         */
        public final String getMessage() {
            return message;
        }
    }

    /**
     * {@code UserPreferencesErrorInfo} class provides complementary information in
     * case of an error in the {@linkplain com.ale.o2g.UsersService UsersService}.
     */
    static class UserPreferencesErrorInfo {

        /**
         * {@code UserPreferencesErrorType} represents the possible error types for the
         * user preferences.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum UserPreferencesErrorType {

            /**
             * Unexpected error.
             */
            UNKNOWN,

            /**
             * Value not expected.
             */
            WRONG_VALUE,

            /**
             * Wrong number format.
             */
            WRONG_NUMBER_FORMAT
        }

        /**
         * {@code UserPreferenceParameter} represents the possible preferences.
         */
        public enum UserPreferenceParameter {

            /**
             * User GUI language.
             */
            GUI_LANGUAGE
        }

        public UserPreferencesErrorType userPreferencesErrorTypeDTO;
        public UserPreferenceParameter userPreferencesParameterDTO;

        /**
         * Return the information on the error.
         * 
         * @return the userPreferencesErrorTypeDTO
         */
        public final UserPreferencesErrorType getUserPreferencesErrorType() {
            return userPreferencesErrorTypeDTO;
        }

        /**
         * Returns the parameter that cause the error.
         * 
         * @return the userPreferencesParameterDTO
         */
        public final UserPreferenceParameter getUserPreferencesParameter() {
            return userPreferencesParameterDTO;
        }

    }

    private String httpStatus;
    private int code;
    private String helpMessage;
    private String Type;
    private String innerMessage;
    private boolean canRetry;
    private RoutingErrorInfo routing;
    private TelephonyErrorInfo telephony;
    private UserPreferencesErrorInfo userPreferences;

    /**
     * Returns hhe Http status as define in
     * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">rfc2616</a>
     * 
     * @return the http Status.
     */
    public final String getHttpStatus() {
        return httpStatus;
    }

    /**
     * Returns the REST API error code. It allows to quickly identify the possible
     * error.
     * 
     * @return the code
     */
    public final int getCode() {
        return code;
    }

    /**
     * Returns a help message, associated with the {@linkplain #getType()} and
     * {@linkplain #getCode()}, providing a more detailed cause of the error.
     * 
     * @return the help message.
     */
    public final String getHelpMessage() {
        return helpMessage;
    }

    /**
     * Returns the REST API error type, which group all possible underlying errors
     * in a finite number of possibilities.
     * 
     * @return the type.
     */
    public final String getType() {
        return Type;
    }

    /**
     * Returns a message containing relevant information to help an administrator or
     * support team to find the cause of the problem.
     * 
     * @return the inner message.
     */
    public final String getInnerMessage() {
        return innerMessage;
    }

    /**
     * Returns whether the error can be solved by modifying the request.
     * 
     * @return {@code true} if the error can be fiwe; {@code false} otherwise.
     */
    public final boolean isCanRetry() {
        return canRetry;
    }

    /**
     * Return additional information when the error is raised by the
     * {@linkplain com.ale.o2g.RoutingService RoutingService} service.
     * 
     * @return the routing error info.
     */
    public final RoutingErrorInfo getRouting() {
        return routing;
    }

    /**
     * Return additional information when the error is raised by the
     * {@linkplain com.ale.o2g.TelephonyService TelephonyService} service.
     * 
     * @return the telephony error info.
     */
    public final TelephonyErrorInfo getTelephony() {
        return telephony;
    }

    /**
     * Return additional information when the error is raised by the
     * {@linkplain com.ale.o2g.UsersService UsersService} service.
     * 
     * @return the users error info.
     */
    public final UserPreferencesErrorInfo getUserPreferences() {
        return userPreferences;
    }

    protected RestErrorInfo(String helpMessage, boolean canRetry) {
        this.helpMessage = helpMessage;
        this.canRetry = canRetry;
    }
}
