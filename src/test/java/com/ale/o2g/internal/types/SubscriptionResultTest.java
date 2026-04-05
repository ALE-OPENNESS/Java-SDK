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

package com.ale.o2g.internal.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.ale.o2g.test.AbstractJsonTest;

/**
 * Unit tests for {@link SubscriptionResult} JSON deserialization.
 */
@DisplayName("SubscriptionResult")
class SubscriptionResultTest extends AbstractJsonTest {

    // ── Full deserialization ──────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — all fields present, status ACCEPTED")
    void testDeserializationFull() {
        String json = """
                {
                    "subscriptionId": "sub-123456",
                    "message": "Subscription accepted",
                    "publicPollingUrl": "https://93.12.1.1/api/rest/1.0/subscriptions/sub-123456/polling",
                    "privatePollingUrl": "https://10.0.0.1/api/rest/1.0/subscriptions/sub-123456/polling",
                    "status": "ACCEPTED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertTrue(result.isAccepted());
        assertEquals("sub-123456", result.getId());
        assertEquals("Subscription accepted", result.getMessage());
        assertEquals("https://93.12.1.1/api/rest/1.0/subscriptions/sub-123456/polling",
                result.getPublicPollingUrl());
        assertEquals("https://10.0.0.1/api/rest/1.0/subscriptions/sub-123456/polling",
                result.getPrivatePollingUrl());
    }

    // ── isAccepted ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("isAccepted() — returns true when status is ACCEPTED")
    void testIsAcceptedTrue() {
        String json = """
                {
                    "subscriptionId": "sub-123456",
                    "status": "ACCEPTED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertTrue(result.isAccepted());
    }

    @Test
    @DisplayName("isAccepted() — returns false when status is REFUSED")
    void testIsAcceptedRefused() {
        String json = """
                {
                    "message": "Subscription refused — invalid filter",
                    "status": "REFUSED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertFalse(result.isAccepted());
    }

    @Test
    @DisplayName("isAccepted() — returns false when status is unknown value")
    void testIsAcceptedUnknownStatus() {
        String json = """
                {
                    "status": "PENDING"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertFalse(result.isAccepted());
    }

    @Test
    @DisplayName("isAccepted() — returns false when status is absent")
    void testIsAcceptedNoStatus() {
        String json = """
                {
                    "subscriptionId": "sub-123456"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertFalse(result.isAccepted());
    }

    @Test
    @DisplayName("isAccepted() — returns false when status is empty string")
    void testIsAcceptedEmptyStatus() {
        String json = """
                {
                    "status": ""
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertFalse(result.isAccepted());
    }

    @Test
    @DisplayName("isAccepted() — is case sensitive — lowercase accepted is not accepted")
    void testIsAcceptedCaseSensitive() {
        String json = """
                {
                    "status": "accepted"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertFalse(result.isAccepted());
    }

    // ── getId ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getId() — returns subscriptionId when accepted")
    void testGetId() {
        String json = """
                {
                    "subscriptionId": "sub-ABCDEF",
                    "status": "ACCEPTED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertEquals("sub-ABCDEF", result.getId());
    }

    @Test
    @DisplayName("getId() — returns null when subscriptionId is absent")
    void testGetIdNull() {
        String json = """
                {
                    "status": "REFUSED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertNull(result.getId());
    }

    // ── polling URLs ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("getPublicPollingUrl() — returns correct URL")
    void testGetPublicPollingUrl() {
        String json = """
                {
                    "publicPollingUrl": "https://93.12.1.1/api/rest/1.0/subscriptions/sub-123/polling",
                    "status": "ACCEPTED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertEquals("https://93.12.1.1/api/rest/1.0/subscriptions/sub-123/polling",
                result.getPublicPollingUrl());
    }

    @Test
    @DisplayName("getPrivatePollingUrl() — returns correct URL")
    void testGetPrivatePollingUrl() {
        String json = """
                {
                    "privatePollingUrl": "https://10.0.0.1/api/rest/1.0/subscriptions/sub-123/polling",
                    "status": "ACCEPTED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertEquals("https://10.0.0.1/api/rest/1.0/subscriptions/sub-123/polling",
                result.getPrivatePollingUrl());
    }

    @Test
    @DisplayName("getPublicPollingUrl() — returns null when absent")
    void testGetPublicPollingUrlNull() {
        String json = """
                {
                    "status": "ACCEPTED",
                    "subscriptionId": "sub-123"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertNull(result.getPublicPollingUrl());
    }

    @Test
    @DisplayName("getPrivatePollingUrl() — returns null when absent")
    void testGetPrivatePollingUrlNull() {
        String json = """
                {
                    "status": "ACCEPTED",
                    "subscriptionId": "sub-123"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertNull(result.getPrivatePollingUrl());
    }

    // ── getMessage ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getMessage() — returns message when present")
    void testGetMessage() {
        String json = """
                {
                    "message": "Subscription refused — invalid event package",
                    "status": "REFUSED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertEquals("Subscription refused — invalid event package", result.getMessage());
    }

    @Test
    @DisplayName("getMessage() — returns null when absent")
    void testGetMessageNull() {
        String json = """
                {
                    "status": "ACCEPTED",
                    "subscriptionId": "sub-123"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertNull(result.getMessage());
    }

    // ── Minimal deserialization ───────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — empty JSON returns defaults")
    void testDeserializationEmpty() {
        String json = "{}";

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertFalse(result.isAccepted());
        assertNull(result.getId());
        assertNull(result.getMessage());
        assertNull(result.getPublicPollingUrl());
        assertNull(result.getPrivatePollingUrl());
    }

    // ── Real-world scenarios ──────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — realistic accepted subscription response")
    void testDeserializationRealisticAccepted() {
        String json = """
                {
                    "subscriptionId": "88AB1234-sub-001",
                    "status": "ACCEPTED",
                    "publicPollingUrl": "https://93.12.1.1/api/rest/1.0/88AB1234-sub-001/polling",
                    "privatePollingUrl": "https://10.0.0.1/api/rest/1.0/88AB1234-sub-001/polling"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertTrue(result.isAccepted());
        assertNotNull(result.getId());
        assertNotNull(result.getPublicPollingUrl());
        assertNotNull(result.getPrivatePollingUrl());
        assertNull(result.getMessage());
    }

    @Test
    @DisplayName("deserialize — realistic refused subscription response")
    void testDeserializationRealisticRefused() {
        String json = """
                {
                    "message": "Subscription has been refused: invalid filter",
                    "status": "REFUSED"
                }
                """;

        SubscriptionResult result = gson.fromJson(json, SubscriptionResult.class);

        assertFalse(result.isAccepted());
        assertNull(result.getId());
        assertNotNull(result.getMessage());
        assertNull(result.getPublicPollingUrl());
        assertNull(result.getPrivatePollingUrl());
    }
}