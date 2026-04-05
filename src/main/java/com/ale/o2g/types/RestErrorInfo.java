/*
 * Copyright 2021 ALE International
 *
 * Licensed under the MIT License.
 */
package com.ale.o2g.types;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * Provides detailed information about an error raised during a service
 * invocation on the O2G REST API.
 *
 * <p>When an O2G service call fails, the server returns an HTTP error code
 * along with a {@code RestErrorInfo} object that describes the cause of the
 * failure. The following HTTP error codes are returned by most O2G services:
 *
 * <table>
 * <caption>REST Service error codes</caption>
 * <thead>
 * <tr><th>Code</th><th>Description</th></tr>
 * </thead>
 * <tbody>
 * <tr><td>400</td><td>Bad request — the request was malformed or contained invalid parameters</td></tr>
 * <tr><td>403</td><td>Forbidden — the caller does not have the required permissions</td></tr>
 * <tr><td>404</td><td>Not found — the requested resource does not exist</td></tr>
 * <tr><td>500</td><td>Internal server error — an unexpected error occurred on the server</td></tr>
 * <tr><td>503</td><td>Service unavailable — the service is temporarily unavailable</td></tr>
 * </tbody>
 * </table>
 *
 * <p>In addition to the HTTP error code, some services provide service-specific
 * error details through the following nested objects:
 * <ul>
 *   <li>{@link #getRouting()} — for errors raised by the {@link com.ale.o2g.RoutingService}</li>
 *   <li>{@link #getTelephony()} — for errors raised by the {@link com.ale.o2g.TelephonyService}</li>
 *   <li>{@link #getUserPreferences()} — for errors raised by the {@link com.ale.o2g.UsersService}</li>
 * </ul>
 *
 * @see com.ale.o2g.RoutingService
 * @see com.ale.o2g.TelephonyService
 * @see com.ale.o2g.UsersService
 */
public class RestErrorInfo {

    /**
     * Provides complementary error information for failures raised by the
     * {@link com.ale.o2g.RoutingService}.
     *
     * <p>When a routing service call fails, this object provides the specific
     * error type and cause to help diagnose and resolve the issue.
     *
     * @see RestErrorInfo#getRouting()
     */
    static class RoutingErrorInfo {

        /**
         * Represents the possible causes of a routing error.
         *
         * <p>The cause provides additional context to complement the
         * {@link RoutingErrorType}, helping to identify the exact reason the
         * routing operation was rejected.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum RoutingErrorCause {

            /**
             * The routing error cause could not be identified.
             */
            UNKNOWN,

            /**
             * The phone number does not comply with formatting rules.
             * The phone number must match the following regular expression:
             * {@code [+]?[0-9A-D*#\\(\\) ]{1,49}}.
             */
            BAD_PHONE_NUMBER_FORMAT,

            /**
             * The specified device number is not valid as a current device.
             * For example, the device is not in an acceptable state to be used.
             */
            INVALID_CURRENT_DEVICE,

            /**
             * The specified forward route is not valid. Possible reasons:
             * <ul>
             *   <li>The forwarded destination type is not one of {@code VOICEMAIL} or {@code NUMBER}.</li>
             *   <li>The forwarded destination is not acceptable.</li>
             *   <li>A loop in the forward chain has been detected.</li>
             * </ul>
             */
            INVALID_FORWARD_ROUTE,

            /**
             * The specified overflow route is not valid.
             * For example, the overflow destination is not acceptable.
             */
            INVALID_OVERFLOW_ROUTE,

            /**
             * A required parameter is {@code null} or empty.
             */
            NULL_OR_EMPTY_PARAMETER,

            /**
             * A required parameter is {@code null}.
             */
            NULL_PARAMETER,

            /**
             * The cancel overflow operation is not authorized for this user.
             */
            UNAUTHORIZED_CANCEL_OVERFLOW,

            /**
             * The destination type is set to {@code USER}, but the number does not
             * correspond to a known user.
             */
            UNAUTHORIZED_NOT_A_USER,

            /**
             * The overflow has been rejected because of barring rules configured
             * for this user.
             */
            UNAUTHORIZED_OVERFLOW,

            /**
             * The specified phone number is not authorized. Possible reasons:
             * <ul>
             *   <li>Barring rules have rejected the number.</li>
             *   <li>The destination cannot be a service number.</li>
             *   <li>The destination is a voicemail, but the user does not have the rights to use it.</li>
             * </ul>
             */
            UNAUTHORIZED_PHONE_NUMBER
        }

        /**
         * Represents the type of a routing error, grouping related failures
         * into categories.
         *
         * <p>Use the error type together with the {@link RoutingErrorCause} for
         * a complete picture of the routing failure.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum RoutingErrorType {

            /**
             * The routing error type could not be identified.
             */
            UNKNOWN,

            /**
             * The routing service rejected the request because of a bad or
             * missing parameter value.
             */
            BAD_PARAMETER_VALUE,

            /**
             * The routing service request was rejected because of a limitation
             * configured for the concerned user. Examples:
             * <ul>
             *   <li>Overflow on busy is not allowed (barring limitation).</li>
             *   <li>Cancel overflow is not allowed (barring limitation).</li>
             *   <li>Phone number to another destination is not authorized (dial plan limitation).</li>
             * </ul>
             */
            UNAUTHORIZED,

            /**
             * The requested operation is not supported by the routing service.
             */
            INVALID_OPERATION,

            /**
             * The provided phone number cannot be fully resolved in the current
             * dial plan. For example, a route destination containing a partially
             * matching number (e.g. {@code 3253} instead of {@code 32535}).
             */
            INCOMPLETE_PHONE_NUMBER,

            /**
             * The provided phone number cannot be resolved in the current dial
             * plan. For example, a route destination containing an unknown number.
             */
            UNKNOWN_PHONE_NUMBER
        }

        /** The routing error type. */
        public RoutingErrorType errorType;

        /** The routing error cause. */
        public RoutingErrorCause errorCause;

        /** An optional additional textual description of the error. */
        public String message;

        /**
         * Returns the routing error type, indicating the category of the failure.
         *
         * @return the error type; never {@code null}
         */
        public final RoutingErrorType getErrorType() {
            return errorType;
        }

        /**
         * Returns the routing error cause, providing additional context for the
         * failure.
         *
         * @return the error cause; never {@code null}
         */
        public final RoutingErrorCause getErrorCause() {
            return errorCause;
        }

        /**
         * Returns an additional textual description of the error provided by
         * the routing service, or {@code null} if no additional information is
         * available.
         *
         * @return the error message, or {@code null}
         */
        public final String getMessage() {
            return message;
        }
    }

    /**
     * Provides complementary error information for failures raised by the
     * {@link com.ale.o2g.TelephonyService}.
     *
     * <p>When a telephony service call fails, this object provides the specific
     * error type and cause to help diagnose and resolve the issue.
     *
     * @see RestErrorInfo#getTelephony()
     */
    static class TelephonyErrorInfo {

        /**
         * Represents the possible causes of a telephony error, providing
         * additional context when the error type is
         * {@link TelephonyErrorType#CALL_SERVER_ERROR}.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum TelephonyErrorCause {

            /**
             * The telephony error cause could not be identified.
             */
            UNKNOWN,

            /**
             * The call server rejected the request because the calling device
             * is not acceptable (e.g. not registered or in an invalid state).
             */
            INVALID_CALLING,

            /**
             * The destination number is not a valid or reachable number.
             */
            INVALID_DESTINATION,

            /**
             * The specified call reference does not exist or is no longer valid.
             */
            INVALID_CALL_ID,

            /**
             * The current state of the call does not permit the requested operation.
             * For example, attempting to hold a call that is already on hold.
             */
            INVALID_CONNECTION_STATE,

            /**
             * The device is out of service and cannot be used.
             */
            DEVICE_OUT_OF_SERVICE,

            /**
             * The specified device is not valid (e.g. unknown device identifier).
             */
            INVALID_DEVICE,

            /**
             * The current state of the device is incompatible with the request.
             * For example, the device is busy or not registered.
             */
            INVALID_DEVICE_STATE,

            /**
             * A data parameter attached to the request has an invalid value.
             */
            INVALID_DATA,

            /**
             * The destination is busy — all phone lines of the user are already
             * engaged.
             */
            RESOURCE_BUSY
        }

        /**
         * Represents the type of a telephony error, grouping related failures
         * into categories.
         *
         * <p>Use the error type together with the {@link TelephonyErrorCause}
         * for a complete picture of the telephony failure.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum TelephonyErrorType {

            /**
             * The telephony error type could not be identified. The O2G server
             * was unable to determine the root cause of the error.
             */
            UNKNOWN,

            /**
             * The specified call reference was not found. The call may have
             * already ended or the reference is invalid.
             */
            CALL_REFERENCE_NOT_FOUND,

            /**
             * The specified leg could not be found. For example, requesting an
             * online recording when there is no active leg on the call.
             */
            LEG_NOT_FOUND,

            /**
             * One or more parameters attached to the request are invalid or
             * missing.
             */
            BAD_PARAMETER_VALUE,

            /**
             * The telephony service cannot execute the request because the
             * current telephony state does not permit the operation.
             */
            INCOMPATIBLE_WITH_STATE,

            /**
             * The telephony service cannot execute the request because the
             * corresponding service is not available in this context.
             */
            SERVICE_NOT_PROVIDED,

            /**
             * The telephony service cannot execute the request because a
             * dependent service is unavailable. For example, redirecting to
             * voicemail is requested but the user has no voicemail configured.
             */
            SERVICE_UNAVAILABLE,

            /**
             * The telephony service is starting up and has not yet completed
             * its initialization.
             */
            INITIALIZATION,

            /**
             * The telephony service request was rejected. For example, the user
             * already has an active call and does not have an Advanced Telephony
             * license.
             */
            UNAUTHORIZED,

            /**
             * The telephony service request failed because of an error generated
             * by the call server. Additional details may be available via
             * {@link TelephonyErrorCause}.
             */
            CALL_SERVER_ERROR,

            /**
             * The telephony service operation timed out — no response was
             * received from the call server within the expected timeout
             * (default: 5 seconds).
             */
            REQUEST_TIMEOUT
        }

        /** The telephony error type. */
        public TelephonyErrorType errorType;

        /** The telephony error cause. */
        public TelephonyErrorCause errorCause;

        /** An optional additional textual description of the error. */
        public String message;

        /**
         * Returns the telephony error type, indicating the category of the
         * failure.
         *
         * @return the error type; never {@code null}
         */
        public final TelephonyErrorType getErrorType() {
            return errorType;
        }

        /**
         * Returns the telephony error cause, providing additional context for
         * the failure when the error type is
         * {@link TelephonyErrorType#CALL_SERVER_ERROR}.
         *
         * @return the error cause; never {@code null}
         */
        public final TelephonyErrorCause getErrorCause() {
            return errorCause;
        }

        /**
         * Returns an additional textual description of the error provided by
         * the telephony service, or {@code null} if no additional information
         * is available.
         *
         * @return the error message, or {@code null}
         */
        public final String getMessage() {
            return message;
        }
    }

    /**
     * Provides complementary error information for failures raised by the
     * {@link com.ale.o2g.UsersService} when setting user preferences.
     *
     * @see RestErrorInfo#getUserPreferences()
     */
    static class UserPreferencesErrorInfo {

        /**
         * Represents the possible error types for user preference operations.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public enum UserPreferencesErrorType {

            /**
             * An unexpected error occurred.
             */
            UNKNOWN,

            /**
             * The value provided for the preference is not acceptable.
             */
            WRONG_VALUE,

            /**
             * The phone number format provided for the preference is invalid.
             */
            WRONG_NUMBER_FORMAT
        }

        /**
         * Represents the user preference parameters that can cause an error.
         */
        public enum UserPreferenceParameter {

            /**
             * The user interface (GUI) language preference.
             */
            GUI_LANGUAGE
        }

        /** The user preferences error type. */
        public UserPreferencesErrorType userPreferencesErrorTypeDTO;

        /** The user preference parameter that caused the error. */
        public UserPreferenceParameter userPreferencesParameterDTO;

        /**
         * Returns the error type describing what went wrong when setting the
         * user preference.
         *
         * @return the user preferences error type; never {@code null}
         */
        public final UserPreferencesErrorType getUserPreferencesErrorType() {
            return userPreferencesErrorTypeDTO;
        }

        /**
         * Returns the specific user preference parameter that caused the error.
         *
         * @return the failing preference parameter; never {@code null}
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
     * Returns the HTTP status code as defined in
     * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">RFC 2616</a>.
     * For example: {@code "400 Bad Request"} or {@code "403 Forbidden"}.
     *
     * @return the HTTP status string, or {@code null} if not available
     */
    public final String getHttpStatus() {
        return httpStatus;
    }

    /**
     * Returns the O2G REST API error code that identifies the specific failure.
     * This code can be used to quickly categorize the error type without parsing
     * the help message.
     *
     * @return the API error code
     */
    public final int getCode() {
        return code;
    }

    /**
     * Returns a human-readable help message providing a more detailed description
     * of the error cause, associated with the {@link #getType() error type} and
     * {@link #getCode() error code}.
     *
     * @return the help message, or {@code null} if not available
     */
    public final String getHelpMessage() {
        return helpMessage;
    }

    /**
     * Returns the O2G REST API error type, which groups all underlying errors
     * into a finite set of categories for programmatic handling.
     *
     * @return the error type string, or {@code null} if not available
     */
    public final String getType() {
        return Type;
    }

    /**
     * Returns an internal message containing technical information to help
     * administrators or support teams identify the root cause of the problem.
     * This message is not intended for display to end users.
     *
     * @return the inner message, or {@code null} if not available
     */
    public final String getInnerMessage() {
        return innerMessage;
    }

    /**
     * Returns whether the error can potentially be resolved by modifying and
     * resubmitting the request — for example, by correcting an invalid parameter.
     *
     * <p>When this returns {@code false}, the error is not recoverable by
     * changing the request (for example, a permissions error or an internal
     * server error).
     *
     * @return {@code true} if the request can be retried with corrections;
     *         {@code false} otherwise
     */
    public final boolean isCanRetry() {
        return canRetry;
    }

    /**
     * Returns additional routing-specific error information when the error was
     * raised by the {@link com.ale.o2g.RoutingService}, or {@code null} if the
     * error was not raised by that service.
     *
     * @return the routing error info, or {@code null}
     */
    public final RoutingErrorInfo getRouting() {
        return routing;
    }

    /**
     * Returns additional telephony-specific error information when the error was
     * raised by the {@link com.ale.o2g.TelephonyService}, or {@code null} if the
     * error was not raised by that service.
     *
     * @return the telephony error info, or {@code null}
     */
    public final TelephonyErrorInfo getTelephony() {
        return telephony;
    }

    /**
     * Returns additional user-preferences-specific error information when the
     * error was raised by the {@link com.ale.o2g.UsersService}, or {@code null}
     * if the error was not raised by that service.
     *
     * @return the user preferences error info, or {@code null}
     */
    public final UserPreferencesErrorInfo getUserPreferences() {
        return userPreferences;
    }

    /**
     * Constructs a {@code RestErrorInfo} with the specified help message and
     * retry indication.
     *
     * @param helpMessage a human-readable description of the error
     * @param canRetry    {@code true} if the error can be resolved by modifying
     *                    the request; {@code false} otherwise
     */
    protected RestErrorInfo(String helpMessage, boolean canRetry) {
        this.helpMessage = helpMessage;
        this.canRetry = canRetry;
    }
}
