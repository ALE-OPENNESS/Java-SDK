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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ale.o2g.O2GException;
import com.ale.o2g.test.AbstractJsonTest;

/**
 * Unit tests for {@link RoxeRestApiDescriptor} JSON deserialization.
 */
@DisplayName("RoxeRestApiDescriptor")
class RoxeRestApiDescriptorTest extends AbstractJsonTest {

    // ── Full deserialization ──────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — all fields present")
    void testDeserializationFull() throws O2GException {
        String json = """
                {
                    "serverInfo": {
                        "version": "2.7",
                        "productName": "OmniPCX Enterprise"
                    },
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertNotNull(descriptor.getServerInfo());
        assertNotNull(descriptor.getCurrent());
    }

    // ── Version deserialization ───────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — Version fields are set correctly")
    void testDeserializationVersionFields() throws O2GException {
        String json = """
                {
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        RoxeRestApiDescriptor.Version version = descriptor.getCurrent();
        assertEquals("1.0", version.getId());
        assertEquals("CURRENT", version.getStatus());
        assertEquals("https://93.12.1.1/api/rest/1.0", version.getPublicUrl());
        assertEquals("https://10.0.0.1/api/rest/1.0", version.getInternalUrl());
    }

    @Test
    @DisplayName("deserialize — multiple versions")
    void testDeserializationMultipleVersions() throws O2GException {
        String json = """
                {
                    "versions": [
                        {
                            "id": "0.9",
                            "status": "OLD",
                            "publicUrl": "https://93.12.1.1/api/rest/0.9",
                            "internalUrl": "https://10.0.0.1/api/rest/0.9"
                        },
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        RoxeRestApiDescriptor.Version current = descriptor.getCurrent();
        assertEquals("1.0", current.getId());
        assertEquals("CURRENT", current.getStatus());
    }

    // ── getCurrent ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getCurrent() — returns the version with CURRENT status")
    void testGetCurrent() throws O2GException {
        String json = """
                {
                    "versions": [
                        {
                            "id": "0.9",
                            "status": "OLD",
                            "publicUrl": "https://93.12.1.1/api/rest/0.9",
                            "internalUrl": "https://10.0.0.1/api/rest/0.9"
                        },
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        RoxeRestApiDescriptor.Version current = descriptor.getCurrent();

        assertNotNull(current);
        assertEquals("1.0", current.getId());
        assertEquals("CURRENT", current.getStatus());
    }

    @Test
    @DisplayName("getCurrent() — throws O2GException when no CURRENT version exists")
    void testGetCurrentThrowsWhenNoCurrent() {
        String json = """
                {
                    "versions": [
                        {
                            "id": "0.9",
                            "status": "OLD",
                            "publicUrl": "https://93.12.1.1/api/rest/0.9",
                            "internalUrl": "https://10.0.0.1/api/rest/0.9"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertThrows(O2GException.class, descriptor::getCurrent);
    }

    @Test
    @DisplayName("getCurrent() — throws O2GException when versions list is empty")
    void testGetCurrentThrowsWhenVersionsEmpty() {
        String json = """
                {
                    "versions": []
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertThrows(O2GException.class, descriptor::getCurrent);
    }

    @Test
    @DisplayName("getCurrent() — is case sensitive — 'current' is not CURRENT")
    void testGetCurrentCaseSensitive() {
        String json = """
                {
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "current",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertThrows(O2GException.class, descriptor::getCurrent);
    }

    @Test
    @DisplayName("getCurrent() — returns first CURRENT version when multiple exist")
    void testGetCurrentReturnsFirstMatch() throws O2GException {
        String json = """
                {
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        },
                        {
                            "id": "1.1",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.1",
                            "internalUrl": "https://10.0.0.1/api/rest/1.1"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        RoxeRestApiDescriptor.Version current = descriptor.getCurrent();
        assertEquals("1.0", current.getId());
    }

    // ── get(versionId) ────────────────────────────────────────────────────────

    @Test
    @DisplayName("get() — returns version matching the given id")
    void testGet() {
        String json = """
                {
                    "versions": [
                        {
                            "id": "0.9",
                            "status": "OLD",
                            "publicUrl": "https://93.12.1.1/api/rest/0.9",
                            "internalUrl": "https://10.0.0.1/api/rest/0.9"
                        },
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        RoxeRestApiDescriptor.Version version = descriptor.get("0.9");

        assertNotNull(version);
        assertEquals("0.9", version.getId());
        assertEquals("OLD", version.getStatus());
    }

    @Test
    @DisplayName("get() — returns null when version id is not found")
    void testGetNotFound() {
        String json = """
                {
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertNull(descriptor.get("2.0"));
    }

    @Test
    @DisplayName("get() — is case sensitive — '1.0' and '1.0' match but '1.0' and '1.0 ' do not")
    void testGetExactMatch() {
        String json = """
                {
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertNotNull(descriptor.get("1.0"));
        assertNull(descriptor.get("1.0 "));
        assertNull(descriptor.get("1.00"));
    }

    @Test
    @DisplayName("get() — returns current version by id")
    void testGetCurrentById() {
        String json = """
                {
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        RoxeRestApiDescriptor.Version version = descriptor.get("1.0");

        assertNotNull(version);
        assertEquals("1.0", version.getId());
        assertEquals("CURRENT", version.getStatus());
        assertEquals("https://93.12.1.1/api/rest/1.0", version.getPublicUrl());
        assertEquals("https://10.0.0.1/api/rest/1.0", version.getInternalUrl());
    }

    // ── getServerInfo ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("getServerInfo() — returns null when absent")
    void testGetServerInfoNull() {
        String json = """
                {
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertNull(descriptor.getServerInfo());
    }

    @Test
    @DisplayName("getServerInfo() — returns non-null when present")
    void testGetServerInfoPresent() {
        String json = """
                {
                    "serverInfo": {
                        "version": "2.7",
                        "productName": "OmniPCX Enterprise"
                    },
                    "versions": [
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertNotNull(descriptor.getServerInfo());
    }

    // ── Real-world scenario ───────────────────────────────────────────────────

    @Test
    @DisplayName("deserialize — realistic O2G bootstrap response")
    void testDeserializationRealistic() throws O2GException {
        String json = """
                {
                    "serverInfo": {
                        "version": "2.7.5",
                        "productName": "OmniPCX Enterprise"
                    },
                    "versions": [
                        {
                            "id": "0.9",
                            "status": "OLD",
                            "publicUrl": "https://93.12.1.1/api/rest/0.9",
                            "internalUrl": "https://10.0.0.1/api/rest/0.9"
                        },
                        {
                            "id": "1.0",
                            "status": "CURRENT",
                            "publicUrl": "https://93.12.1.1/api/rest/1.0",
                            "internalUrl": "https://10.0.0.1/api/rest/1.0"
                        }
                    ]
                }
                """;

        RoxeRestApiDescriptor descriptor = gson.fromJson(json, RoxeRestApiDescriptor.class);

        assertNotNull(descriptor.getServerInfo());

        // getCurrent returns 1.0
        RoxeRestApiDescriptor.Version current = descriptor.getCurrent();
        assertEquals("1.0", current.getId());
        assertEquals("https://93.12.1.1/api/rest/1.0", current.getPublicUrl());
        assertEquals("https://10.0.0.1/api/rest/1.0", current.getInternalUrl());

        // get by id works for both versions
        assertNotNull(descriptor.get("0.9"));
        assertNotNull(descriptor.get("1.0"));
        assertNull(descriptor.get("2.0"));
    }
}
