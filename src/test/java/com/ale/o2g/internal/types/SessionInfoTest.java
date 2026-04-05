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
 * Unit tests for {@link SessionInfo} JSON deserialization.
 */
@DisplayName("SessionInfo")
class SessionInfoTest extends AbstractJsonTest {

    // ── Full deserialization ──────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — all fields present")
    void testDeserializationFull() {
        String json = """
                {
                    "admin": true,
                    "timeToLive": 30,
                    "publicBaseUrl": "https://public.example.com/api/rest",
                    "privateBaseUrl": "https://10.0.0.1/api/rest",
                    "expirationDate": "2026-04-03T14:00:00Z",
                    "services": [
                        {
                            "serviceName": "telephony",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/telephony"
                        },
                        {
                            "serviceName": "routing",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/routing"
                        }
                    ]
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertTrue(info.isAdmin());
        assertEquals(30, info.getTimeToLive());
        assertEquals("https://public.example.com/api/rest", info.getPublicBaseUrl());
        assertEquals("https://10.0.0.1/api/rest", info.getPrivateBaseUrl());
        assertNotNull(info.getServices());
        assertEquals(2, info.getServices().size());
    }

    // ── Admin field ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — admin false")
    void testDeserializationAdminFalse() {
        String json = """
                {
                    "admin": false,
                    "timeToLive": 30,
                    "publicBaseUrl": "https://public.example.com/api/rest",
                    "privateBaseUrl": "https://10.0.0.1/api/rest"
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertFalse(info.isAdmin());
    }

    // ── timeToLive ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — timeToLive is set correctly")
    void testDeserializationTimeToLive() {
        String json = """
                {
                    "timeToLive": 1800
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertEquals(1800, info.getTimeToLive());
    }

    // ── URLs ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — public and private base URLs")
    void testDeserializationUrls() {
        String json = """
                {
                    "publicBaseUrl": "https://93.12.1.1/api/rest",
                    "privateBaseUrl": "https://10.0.0.1/api/rest"
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertEquals("https://93.12.1.1/api/rest", info.getPublicBaseUrl());
        assertEquals("https://10.0.0.1/api/rest", info.getPrivateBaseUrl());
    }

    // ── Services ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — service fields are set correctly")
    void testDeserializationServiceFields() {
        String json = """
                {
                    "services": [
                        {
                            "serviceName": "telephony",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/telephony"
                        }
                    ]
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertNotNull(info.getServices());
        assertEquals(1, info.getServices().size());

        SessionInfo.Service service = info.getServices().get(0);
        assertEquals("telephony", service.getServiceName());
        assertEquals("1.0", service.getServiceVersion());
        assertEquals("/telephony", service.getRelativeUrl());
    }

    @Test
    @DisplayName("deserialize — multiple services")
    void testDeserializationMultipleServices() {
        String json = """
                {
                    "services": [
                        {
                            "serviceName": "telephony",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/telephony"
                        },
                        {
                            "serviceName": "routing",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/routing"
                        },
                        {
                            "serviceName": "users",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/users"
                        }
                    ]
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertEquals(3, info.getServices().size());
        assertEquals("telephony", info.getServices().get(0).getServiceName());
        assertEquals("routing", info.getServices().get(1).getServiceName());
        assertEquals("users", info.getServices().get(2).getServiceName());
    }

    @Test
    @DisplayName("deserialize — empty services list")
    void testDeserializationEmptyServices() {
        String json = """
                {
                    "services": []
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertNotNull(info.getServices());
        assertTrue(info.getServices().isEmpty());
    }

    @Test
    @DisplayName("deserialize — services absent returns null")
    void testDeserializationServicesAbsent() {
        String json = """
                {
                    "admin": false,
                    "timeToLive": 30
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertNull(info.getServices());
    }

    // ── expirationDate ────────────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — expirationDate is ignored (unused field)")
    void testDeserializationExpirationDateIgnored() {
        String json = """
                {
                    "admin": false,
                    "timeToLive": 30,
                    "expirationDate": "2026-04-03T14:00:00Z"
                }
                """;

        // expirationDate is @SuppressWarnings("unused") — it is parsed but not exposed
        // Just verify that its presence does not cause any deserialization error
        assertDoesNotThrow(() -> gson.fromJson(json, SessionInfo.class));
    }

    // ── Minimal deserialization ───────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — empty JSON returns default values")
    void testDeserializationEmpty() {
        String json = "{}";

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertFalse(info.isAdmin());
        assertEquals(0, info.getTimeToLive());
        assertNull(info.getPublicBaseUrl());
        assertNull(info.getPrivateBaseUrl());
        assertNull(info.getServices());
    }

    // ── Service — minimal deserialization ─────────────────────────────────────

    @Test
    @DisplayName("deserialize — service with missing fields returns nulls")
    void testDeserializationServiceMinimal() {
        String json = """
                {
                    "services": [
                        {}
                    ]
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertEquals(1, info.getServices().size());

        SessionInfo.Service service = info.getServices().get(0);
        assertNull(service.getServiceName());
        assertNull(service.getServiceVersion());
        assertNull(service.getRelativeUrl());
    }

    // ── Real-world scenario ───────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — realistic O2G session response")
    void testDeserializationRealistic() {
        String json = """
                {
                    "admin": false,
                    "timeToLive": 1800,
                    "publicBaseUrl": "https://93.12.1.1/api/rest/1.0/88AB1234",
                    "privateBaseUrl": "https://10.0.0.1/api/rest/1.0/88AB1234",
                    "expirationDate": "2026-04-03T16:00:00Z",
                    "services": [
                        {
                            "serviceName": "telephony",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/telephony/oxe1000"
                        },
                        {
                            "serviceName": "routing",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/routing/oxe1000"
                        },
                        {
                            "serviceName": "voicemail",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/voicemail/oxe1000"
                        },
                        {
                            "serviceName": "eventsummary",
                            "serviceVersion": "1.0",
                            "relativeUrl": "/eventsummary/oxe1000"
                        }
                    ]
                }
                """;

        SessionInfo info = gson.fromJson(json, SessionInfo.class);

        assertFalse(info.isAdmin());
        assertEquals(1800, info.getTimeToLive());
        assertEquals("https://93.12.1.1/api/rest/1.0/88AB1234", info.getPublicBaseUrl());
        assertEquals("https://10.0.0.1/api/rest/1.0/88AB1234", info.getPrivateBaseUrl());
        assertEquals(4, info.getServices().size());

        // Verify service names are correct
        assertTrue(info.getServices().stream()
                .anyMatch(s -> "telephony".equals(s.getServiceName())));
        assertTrue(info.getServices().stream()
                .anyMatch(s -> "routing".equals(s.getServiceName())));
        assertTrue(info.getServices().stream()
                .anyMatch(s -> "voicemail".equals(s.getServiceName())));
        assertTrue(info.getServices().stream()
                .anyMatch(s -> "eventsummary".equals(s.getServiceName())));
    }
}