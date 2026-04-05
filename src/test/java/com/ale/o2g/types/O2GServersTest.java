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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link O2GServers}.
 */
@DisplayName("O2GServers")
class O2GServersTest {

    // ── Builder ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("newBuilder() returns a non-null builder")
    void newBuilder_returnsNonNullBuilder() {
        assertNotNull(O2GServers.newBuilder());
    }

    @Test
    @DisplayName("build() throws IllegalStateException when no primary host is set")
    void build_throwsWhenNoPrimaryHost() {
        assertThrows(IllegalStateException.class, () ->
            O2GServers.newBuilder().build()
        );
    }

    @Test
    @DisplayName("primaryHost() throws IllegalArgumentException when host is null")
    void primaryHost_throwsWhenNull() {
        assertThrows(IllegalArgumentException.class, () ->
            O2GServers.newBuilder().primaryHost(null)
        );
    }

    @Test
    @DisplayName("secondaryHost() throws IllegalArgumentException when host is null")
    void secondaryHost_throwsWhenNull() {
        assertThrows(IllegalArgumentException.class, () ->
            O2GServers.newBuilder().secondaryHost(null)
        );
    }

    @Test
    @DisplayName("builder is fluent — methods return the same builder instance")
    void builder_isFluent() {
        O2GServers.Builder builder = O2GServers.newBuilder();
        assertSame(builder, builder.primaryHost(new Host("10.0.0.1")));
        assertSame(builder, builder.secondaryHost(new Host("10.0.0.2")));
    }

    // ── Standalone configuration ──────────────────────────────────────────────

    @Test
    @DisplayName("standalone — primary private address is set correctly")
    void standalone_primaryPrivateAddress() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"))
            .build();

        assertNotNull(servers.getPrimaryHost());
        assertEquals("10.0.0.1", servers.getPrimaryHost().getPrivateAddress());
        assertNull(servers.getPrimaryHost().getPublicAddress());
    }

    @Test
    @DisplayName("standalone — primary with both private and public addresses")
    void standalone_primaryPrivateAndPublicAddress() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1", "93.12.1.1"))
            .build();

        assertEquals("10.0.0.1", servers.getPrimaryHost().getPrivateAddress());
        assertEquals("93.12.1.1", servers.getPrimaryHost().getPublicAddress());
    }

    @Test
    @DisplayName("standalone — hasSecondary() returns false")
    void standalone_hasSecondaryReturnsFalse() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"))
            .build();

        assertFalse(servers.hasSecondary());
    }

    @Test
    @DisplayName("standalone — getSecondaryHost() returns null")
    void standalone_getSecondaryHostReturnsNull() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"))
            .build();

        assertNull(servers.getSecondaryHost());
    }

    // ── Local HA configuration ────────────────────────────────────────────────

    @Test
    @DisplayName("local HA — virtual IP configured as primary only")
    void localHA_virtualIpAsPrimaryOnly() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("vip.example.com"))
            .build();

        assertEquals("vip.example.com", servers.getPrimaryHost().getPrivateAddress());
        assertFalse(servers.hasSecondary());
        assertNull(servers.getSecondaryHost());
    }

    // ── Geographic HA configuration ───────────────────────────────────────────

    @Test
    @DisplayName("geographic HA — primary and secondary hosts are set correctly")
    void geographicHA_primaryAndSecondaryHosts() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"))
            .secondaryHost(new Host("10.0.0.2"))
            .build();

        assertEquals("10.0.0.1", servers.getPrimaryHost().getPrivateAddress());
        assertEquals("10.0.0.2", servers.getSecondaryHost().getPrivateAddress());
    }

    @Test
    @DisplayName("geographic HA — hasSecondary() returns true")
    void geographicHA_hasSecondaryReturnsTrue() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"))
            .secondaryHost(new Host("10.0.0.2"))
            .build();

        assertTrue(servers.hasSecondary());
    }

    @Test
    @DisplayName("geographic HA — both hosts with public addresses")
    void geographicHA_bothHostsWithPublicAddresses() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1", "93.12.1.1"))
            .secondaryHost(new Host("10.0.0.2", "93.12.1.2"))
            .build();

        assertEquals("10.0.0.1", servers.getPrimaryHost().getPrivateAddress());
        assertEquals("93.12.1.1", servers.getPrimaryHost().getPublicAddress());
        assertEquals("10.0.0.2", servers.getSecondaryHost().getPrivateAddress());
        assertEquals("93.12.1.2", servers.getSecondaryHost().getPublicAddress());
    }

    @Test
    @DisplayName("geographic HA — primary and secondary are independent objects")
    void geographicHA_primaryAndSecondaryAreIndependent() {
        Host primary = new Host("10.0.0.1");
        Host secondary = new Host("10.0.0.2");

        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(primary)
            .secondaryHost(secondary)
            .build();

        assertNotSame(servers.getPrimaryHost(), servers.getSecondaryHost());
        assertSame(primary, servers.getPrimaryHost());
        assertSame(secondary, servers.getSecondaryHost());
    }

    // ── toString ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("toString() — standalone with private address only")
    void toString_standalonePrivateOnly() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"))
            .build();

        assertEquals("O2GServers[primary=10.0.0.1]", servers.toString());
    }

    @Test
    @DisplayName("toString() — standalone with private and public addresses")
    void toString_standalonePrivateAndPublic() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1", "93.12.1.1"))
            .build();

        assertEquals("O2GServers[primary=10.0.0.1 / 93.12.1.1]", servers.toString());
    }

    @Test
    @DisplayName("toString() — geographic HA with private addresses")
    void toString_geographicHAPrivateOnly() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"))
            .secondaryHost(new Host("10.0.0.2"))
            .build();

        assertEquals("O2GServers[primary=10.0.0.1, secondary=10.0.0.2]",
            servers.toString());
    }

    @Test
    @DisplayName("toString() — geographic HA with private and public addresses")
    void toString_geographicHAPrivateAndPublic() {
        O2GServers servers = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1", "93.12.1.1"))
            .secondaryHost(new Host("10.0.0.2", "93.12.1.2"))
            .build();

        assertEquals("O2GServers[primary=10.0.0.1 / 93.12.1.1, secondary=10.0.0.2 / 93.12.1.2]",
            servers.toString());
    }

    // ── Builder reuse ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("builder can be reused to build multiple independent instances")
    void builder_canBeReused() {
        O2GServers.Builder builder = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"));

        O2GServers first = builder.build();
        O2GServers second = builder.build();

        assertNotSame(first, second);
        assertEquals(first.getPrimaryHost().getPrivateAddress(),
            second.getPrimaryHost().getPrivateAddress());
    }

    @Test
    @DisplayName("builder produces independent instances — modifying builder does not affect built instance")
    void builder_producesIndependentInstances() {
        O2GServers.Builder builder = O2GServers.newBuilder()
            .primaryHost(new Host("10.0.0.1"));

        O2GServers first = builder.build();

        // Change builder state
        builder.primaryHost(new Host("10.0.0.99"));
        O2GServers second = builder.build();

        // First instance should be unaffected
        assertEquals("10.0.0.1", first.getPrimaryHost().getPrivateAddress());
        assertEquals("10.0.0.99", second.getPrimaryHost().getPrivateAddress());
    }
    
 // ── Host validation ───────────────────────────────────────────────────────

    @Test
    @DisplayName("Host — throws IllegalArgumentException when both addresses are null")
    void host_throwsWhenBothAddressesNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Host(null, null)
        );
    }

    @Test
    @DisplayName("Host — private address only is valid")
    void host_privateAddressOnlyIsValid() {
        assertDoesNotThrow(() -> new Host("10.0.0.1"));
    }

    @Test
    @DisplayName("Host — public address only is valid")
    void host_publicAddressOnlyIsValid() {
        assertDoesNotThrow(() -> new Host(null, "93.12.1.1"));
    }

    @Test
    @DisplayName("Host — both addresses is valid")
    void host_bothAddressesIsValid() {
        assertDoesNotThrow(() -> new Host("10.0.0.1", "93.12.1.1"));
    }    
}