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

package com.ale.o2g.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.RestErrorInfo.RoutingErrorInfo.RoutingErrorCause;
import com.ale.o2g.types.RestErrorInfo.RoutingErrorInfo.RoutingErrorType;
import com.ale.o2g.types.RestErrorInfo.TelephonyErrorInfo.TelephonyErrorCause;
import com.ale.o2g.types.RestErrorInfo.TelephonyErrorInfo.TelephonyErrorType;
import com.ale.o2g.types.RestErrorInfo.UserPreferencesErrorInfo.UserPreferencesErrorType;
import com.ale.o2g.types.RestErrorInfo.UserPreferencesErrorInfo.UserPreferenceParameter;

/**
 * Unit tests for {@link RestErrorInfo} JSON deserialization.
 */
@DisplayName("RestErrorInfo")
class RestErrorInfoTest extends AbstractJsonTest {

    // ── Basic fields ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — all basic fields")
    void testDeserializationFull() {
        String json = """
                {
                    "httpStatus": "400 Bad Request",
                    "code": 400,
                    "helpMessage": "Bad parameter value",
                    "Type": "BAD_PARAMETER",
                    "innerMessage": "Internal detail for support",
                    "canRetry": true
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertEquals("400 Bad Request", info.getHttpStatus());
        assertEquals(400, info.getCode());
        assertEquals("Bad parameter value", info.getHelpMessage());
        assertEquals("BAD_PARAMETER", info.getType());
        assertEquals("Internal detail for support", info.getInnerMessage());
        assertTrue(info.isCanRetry());
    }

    @Test
    @DisplayName("deserialize — canRetry false")
    void testDeserializationCanRetryFalse() {
        String json = """
                {
                    "httpStatus": "403 Forbidden",
                    "code": 403,
                    "canRetry": false
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertEquals("403 Forbidden", info.getHttpStatus());
        assertFalse(info.isCanRetry());
    }

    @Test
    @DisplayName("deserialize — empty JSON returns null fields and false canRetry")
    void testDeserializationEmpty() {
        String json = "{}";

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNull(info.getHttpStatus());
        assertEquals(0, info.getCode());
        assertNull(info.getHelpMessage());
        assertNull(info.getType());
        assertNull(info.getInnerMessage());
        assertFalse(info.isCanRetry());
        assertNull(info.getRouting());
        assertNull(info.getTelephony());
        assertNull(info.getUserPreferences());
    }

    @Test
    @DisplayName("deserialize — no service-specific error info present")
    void testDeserializationNoServiceSpecificInfo() {
        String json = """
                {
                    "httpStatus": "500 Internal Server Error",
                    "code": 500,
                    "canRetry": false
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNull(info.getRouting());
        assertNull(info.getTelephony());
        assertNull(info.getUserPreferences());
    }

    // ── Routing error info ────────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — routing error info with all fields")
    void testDeserializationRoutingFull() {
        String json = """
                {
                    "httpStatus": "400 Bad Request",
                    "code": 400,
                    "canRetry": true,
                    "routing": {
                        "errorType": "BAD_PARAMETER_VALUE",
                        "errorCause": "BAD_PHONE_NUMBER_FORMAT",
                        "message": "Phone number format is invalid"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNotNull(info.getRouting());
        assertEquals(RoutingErrorType.BAD_PARAMETER_VALUE, info.getRouting().getErrorType());
        assertEquals(RoutingErrorCause.BAD_PHONE_NUMBER_FORMAT, info.getRouting().getErrorCause());
        assertEquals("Phone number format is invalid", info.getRouting().getMessage());
    }

    @Test
    @DisplayName("deserialize — routing error info — all RoutingErrorType values")
    void testDeserializationRoutingErrorTypes() {
        for (RoutingErrorType type : RoutingErrorType.values()) {
            String json = String.format("""
                    {
                        "routing": {
                            "errorType": "%s",
                            "errorCause": "UNKNOWN"
                        }
                    }
                    """, type.name());

            RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);
            assertEquals(type, info.getRouting().getErrorType(),
                    "Expected RoutingErrorType." + type.name());
        }
    }

    @Test
    @DisplayName("deserialize — routing error info — all RoutingErrorCause values")
    void testDeserializationRoutingErrorCauses() {
        for (RoutingErrorCause cause : RoutingErrorCause.values()) {
            String json = String.format("""
                    {
                        "routing": {
                            "errorType": "UNKNOWN",
                            "errorCause": "%s"
                        }
                    }
                    """, cause.name());

            RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);
            assertEquals(cause, info.getRouting().getErrorCause(),
                    "Expected RoutingErrorCause." + cause.name());
        }
    }

    @Test
    @DisplayName("deserialize — routing error type fallback to UNKNOWN for unrecognized value")
    void testDeserializationRoutingErrorTypeFallback() {
        String json = """
                {
                    "routing": {
                        "errorType": "SOME_FUTURE_TYPE",
                        "errorCause": "UNKNOWN"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertEquals(RoutingErrorType.UNKNOWN, info.getRouting().getErrorType());
    }

    @Test
    @DisplayName("deserialize — routing error cause fallback to UNKNOWN for unrecognized value")
    void testDeserializationRoutingErrorCauseFallback() {
        String json = """
                {
                    "routing": {
                        "errorType": "UNKNOWN",
                        "errorCause": "SOME_FUTURE_CAUSE"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertEquals(RoutingErrorCause.UNKNOWN, info.getRouting().getErrorCause());
    }

    @Test
    @DisplayName("deserialize — routing error info without optional message")
    void testDeserializationRoutingNoMessage() {
        String json = """
                {
                    "routing": {
                        "errorType": "UNAUTHORIZED",
                        "errorCause": "UNAUTHORIZED_PHONE_NUMBER"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNotNull(info.getRouting());
        assertEquals(RoutingErrorType.UNAUTHORIZED, info.getRouting().getErrorType());
        assertEquals(RoutingErrorCause.UNAUTHORIZED_PHONE_NUMBER, info.getRouting().getErrorCause());
        assertNull(info.getRouting().getMessage());
    }

    // ── Telephony error info ──────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — telephony error info with all fields")
    void testDeserializationTelephonyFull() {
        String json = """
                {
                    "httpStatus": "400 Bad Request",
                    "code": 400,
                    "canRetry": false,
                    "telephony": {
                        "errorType": "CALL_SERVER_ERROR",
                        "errorCause": "INVALID_CALLING",
                        "message": "The calling device is not valid"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNotNull(info.getTelephony());
        assertEquals(TelephonyErrorType.CALL_SERVER_ERROR, info.getTelephony().getErrorType());
        assertEquals(TelephonyErrorCause.INVALID_CALLING, info.getTelephony().getErrorCause());
        assertEquals("The calling device is not valid", info.getTelephony().getMessage());
    }

    @Test
    @DisplayName("deserialize — telephony error info — all TelephonyErrorType values")
    void testDeserializationTelephonyErrorTypes() {
        for (TelephonyErrorType type : TelephonyErrorType.values()) {
            String json = String.format("""
                    {
                        "telephony": {
                            "errorType": "%s",
                            "errorCause": "UNKNOWN"
                        }
                    }
                    """, type.name());

            RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);
            assertEquals(type, info.getTelephony().getErrorType(),
                    "Expected TelephonyErrorType." + type.name());
        }
    }

    @Test
    @DisplayName("deserialize — telephony error info — all TelephonyErrorCause values")
    void testDeserializationTelephonyErrorCauses() {
        for (TelephonyErrorCause cause : TelephonyErrorCause.values()) {
            String json = String.format("""
                    {
                        "telephony": {
                            "errorType": "UNKNOWN",
                            "errorCause": "%s"
                        }
                    }
                    """, cause.name());

            RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);
            assertEquals(cause, info.getTelephony().getErrorCause(),
                    "Expected TelephonyErrorCause." + cause.name());
        }
    }

    @Test
    @DisplayName("deserialize — telephony error type fallback to UNKNOWN for unrecognized value")
    void testDeserializationTelephonyErrorTypeFallback() {
        String json = """
                {
                    "telephony": {
                        "errorType": "SOME_FUTURE_TYPE",
                        "errorCause": "UNKNOWN"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertEquals(TelephonyErrorType.UNKNOWN, info.getTelephony().getErrorType());
    }

    @Test
    @DisplayName("deserialize — telephony error cause fallback to UNKNOWN for unrecognized value")
    void testDeserializationTelephonyErrorCauseFallback() {
        String json = """
                {
                    "telephony": {
                        "errorType": "UNKNOWN",
                        "errorCause": "SOME_FUTURE_CAUSE"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertEquals(TelephonyErrorCause.UNKNOWN, info.getTelephony().getErrorCause());
    }

    @Test
    @DisplayName("deserialize — telephony error info without optional message")
    void testDeserializationTelephonyNoMessage() {
        String json = """
                {
                    "telephony": {
                        "errorType": "INCOMPATIBLE_WITH_STATE",
                        "errorCause": "INVALID_CONNECTION_STATE"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNotNull(info.getTelephony());
        assertEquals(TelephonyErrorType.INCOMPATIBLE_WITH_STATE, info.getTelephony().getErrorType());
        assertEquals(TelephonyErrorCause.INVALID_CONNECTION_STATE, info.getTelephony().getErrorCause());
        assertNull(info.getTelephony().getMessage());
    }

    // ── UserPreferences error info ────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — user preferences error info with all fields")
    void testDeserializationUserPreferencesFull() {
        String json = """
                {
                    "httpStatus": "400 Bad Request",
                    "code": 400,
                    "canRetry": true,
                    "userPreferences": {
                        "userPreferencesErrorTypeDTO": "WRONG_VALUE",
                        "userPreferencesParameterDTO": "GUI_LANGUAGE"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNotNull(info.getUserPreferences());
        assertEquals(UserPreferencesErrorType.WRONG_VALUE,
                info.getUserPreferences().getUserPreferencesErrorType());
        assertEquals(UserPreferenceParameter.GUI_LANGUAGE,
                info.getUserPreferences().getUserPreferencesParameter());
    }

    @Test
    @DisplayName("deserialize — user preferences error info — all UserPreferencesErrorType values")
    void testDeserializationUserPreferencesErrorTypes() {
        for (UserPreferencesErrorType type : UserPreferencesErrorType.values()) {
            String json = String.format("""
                    {
                        "userPreferences": {
                            "userPreferencesErrorTypeDTO": "%s",
                            "userPreferencesParameterDTO": "GUI_LANGUAGE"
                        }
                    }
                    """, type.name());

            RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);
            assertEquals(type, info.getUserPreferences().getUserPreferencesErrorType(),
                    "Expected UserPreferencesErrorType." + type.name());
        }
    }

    @Test
    @DisplayName("deserialize — user preferences error type fallback to UNKNOWN for unrecognized value")
    void testDeserializationUserPreferencesErrorTypeFallback() {
        String json = """
                {
                    "userPreferences": {
                        "userPreferencesErrorTypeDTO": "SOME_FUTURE_TYPE",
                        "userPreferencesParameterDTO": "GUI_LANGUAGE"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertEquals(UserPreferencesErrorType.UNKNOWN,
                info.getUserPreferences().getUserPreferencesErrorType());
    }

    // ── Combined service error infos ──────────────────────────────────────────

    @Test
    @DisplayName("deserialize — routing info present but telephony and userPreferences absent")
    void testDeserializationOnlyRoutingPresent() {
        String json = """
                {
                    "routing": {
                        "errorType": "UNAUTHORIZED",
                        "errorCause": "UNAUTHORIZED_OVERFLOW"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNotNull(info.getRouting());
        assertNull(info.getTelephony());
        assertNull(info.getUserPreferences());
    }

    @Test
    @DisplayName("deserialize — telephony info present but routing and userPreferences absent")
    void testDeserializationOnlyTelephonyPresent() {
        String json = """
                {
                    "telephony": {
                        "errorType": "CALL_REFERENCE_NOT_FOUND",
                        "errorCause": "INVALID_CALL_ID"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNull(info.getRouting());
        assertNotNull(info.getTelephony());
        assertNull(info.getUserPreferences());
    }

    @Test
    @DisplayName("deserialize — userPreferences info present but routing and telephony absent")
    void testDeserializationOnlyUserPreferencesPresent() {
        String json = """
                {
                    "userPreferences": {
                        "userPreferencesErrorTypeDTO": "WRONG_NUMBER_FORMAT",
                        "userPreferencesParameterDTO": "GUI_LANGUAGE"
                    }
                }
                """;

        RestErrorInfo info = gson.fromJson(json, RestErrorInfo.class);

        assertNull(info.getRouting());
        assertNull(info.getTelephony());
        assertNotNull(info.getUserPreferences());
    }
}
