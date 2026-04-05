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

/**
 * Represents the O2G server configuration, supporting three deployment topologies:
 *
 * <p><b>Case 1 — Standalone server:</b><br>
 * A single O2G server. On failure, the SDK automatically retries the same host
 * with exponential backoff until the server is reachable again.
 * <pre>{@code
 * O2GServers servers = O2GServers.Builder()
 *     .primaryHost(new Host("10.0.0.1"))
 *     .build();
 * }</pre>
 *
 * <p><b>Case 2 — Local HA (virtual IP):</b><br>
 * Two O2G server instances sharing the same virtual IP address or URL.
 * The failover is transparent at the network level — configure it exactly
 * like a standalone server. The SDK retries the same host; the virtual IP
 * routes to whichever node is active.
 * <pre>{@code
 * O2GServers servers = O2GServers.Builder()
 *     .primaryHost(new Host("vip.example.com"))
 *     .build();
 * }</pre>
 *
 * <p><b>Case 3 — Geographic HA (two distinct hosts):</b><br>
 * Two O2G server instances at different locations with distinct IP addresses.
 * On primary failure, the SDK switches immediately to the secondary and stays
 * there permanently — it does not switch back to the primary.
 * <pre>{@code
 * O2GServers servers = O2GServers.Builder()
 *     .primaryHost(new Host("10.0.0.1"))
 *     .secondaryHost(new Host("10.0.0.2"))
 *     .build();
 * }</pre>
 *
 * @see Host
 * @see com.ale.o2g.O2G
 */
public final class O2GServers {

    private final Host primaryHost;
    private final Host secondaryHost;

    private O2GServers(Host primaryHost, Host secondaryHost) {
        this.primaryHost = primaryHost;
        this.secondaryHost = secondaryHost;
    }

    /**
     * Returns the primary O2G server host.
     *
     * @return the primary host
     */
    public Host getPrimaryHost() {
        return primaryHost;
    }

    /**
     * Returns the secondary O2G server host, or {@code null} if geographic HA
     * is not configured.
     *
     * @return the secondary host, or {@code null}
     */
    public Host getSecondaryHost() {
        return secondaryHost;
    }

    /**
     * Returns whether geographic HA is configured, i.e. a secondary host
     * has been provided.
     *
     * @return {@code true} if a secondary host is configured; {@code false} otherwise
     */
    public boolean hasSecondary() {
        return secondaryHost != null;
    }

    /**
     * Returns a string representation of this server configuration.
     */
    @Override
    public String toString() {
        if (secondaryHost != null) {
            return String.format("O2GServers[primary=%s, secondary=%s]",
                    hostToString(primaryHost), hostToString(secondaryHost));
        }
        return String.format("O2GServers[primary=%s]", hostToString(primaryHost));
    }

    private static String hostToString(Host host) {
        if (host.getPrivateAddress() != null && host.getPublicAddress() != null) {
            return host.getPrivateAddress() + " / " + host.getPublicAddress();
        }
        return host.getPrivateAddress() != null ? host.getPrivateAddress() : host.getPublicAddress();
    }

    // ── Builder ───────────────────────────────────────────────────────────────

    /**
     * Returns a new {@link Builder} instance to construct an {@link O2GServers}.
     *
     * @return a new builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Fluent builder for {@link O2GServers}.
     *
     * <p>At least a primary host must be set before calling {@link #build()}.
     */
    public static final class Builder {

        private Host primaryHost;
        private Host secondaryHost;

        private Builder() {}

        /**
         * Sets the primary O2G server host.
         *
         * @param host the primary host; must not be {@code null}
         * @return this builder
         * @throws IllegalArgumentException if {@code host} is {@code null}
         */
        public Builder primaryHost(Host host) {
            if (host == null) {
                throw new IllegalArgumentException("Primary host must not be null.");
            }
            this.primaryHost = host;
            return this;
        }

        /**
         * Sets the secondary O2G server host, enabling geographic HA.
         *
         * <p>When configured, the SDK switches to this server permanently if the
         * primary becomes unreachable. Only use this for geographic HA deployments
         * where the two servers have distinct IP addresses or URLs. For local HA
         * with a shared virtual IP, use only {@link #primaryHost(Host)}.
         *
         * @param host the secondary host; must not be {@code null}
         * @return this builder
         * @throws IllegalArgumentException if {@code host} is {@code null}
         */
        public Builder secondaryHost(Host host) {
            if (host == null) {
                throw new IllegalArgumentException("Secondary host must not be null.");
            }
            this.secondaryHost = host;
            return this;
        }

        /**
         * Builds and returns a new {@link O2GServers} instance.
         *
         * @return a new {@link O2GServers}
         * @throws IllegalStateException if no primary host has been set
         */
        public O2GServers build() {
            if (primaryHost == null) {
                throw new IllegalStateException("A primary host must be provided.");
            }
            return new O2GServers(primaryHost, secondaryHost);
        }
    }
}