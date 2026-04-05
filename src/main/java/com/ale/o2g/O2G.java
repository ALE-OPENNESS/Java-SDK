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

package com.ale.o2g;

import com.ale.o2g.internal.ServiceEndPointImpl;
import com.ale.o2g.types.Host;
import com.ale.o2g.types.O2GServers;

/**
 * {@code O2G} is the basic class to bootstrap the SDK and create a
 * {@linkplain ServiceEndPoint} object.
 *
 * <p>Use {@link #connect(O2GServers)} to create a {@link ServiceEndPoint}
 * configured for standalone, local HA, or geographic HA deployments.
 *
 * @see O2GServers
 * @see ServiceEndPoint
 */
public final class O2G {

    /**
     * The O2G Api version.
     */
    public static String ApiVersion = "1.0";

    /**
     * Attempts to establish a connection using the specified server configuration.
     *
     * <p>Supports three deployment topologies:
     * <ul>
     *   <li><b>Standalone:</b> single server, automatic retry on failure</li>
     *   <li><b>Local HA:</b> virtual IP shared by two nodes, same as standalone</li>
     *   <li><b>Geographic HA:</b> two distinct servers, automatic permanent
     *       failover to secondary on primary failure</li>
     * </ul>
     *
     * <p>The returned {@link ServiceEndPoint} drives the full session lifecycle
     * including initial connection retry, session recovery after failure, and
     * geographic failover.
     *
     * @param servers the O2G servers configuration, built with
     *                {@link O2GServers#newBuilder()}
     * @return the service endpoint
     * @see O2GServers
     */
    public static ServiceEndPoint connect(O2GServers servers) {
        return new ServiceEndPointImpl(servers, ApiVersion);
    }

    /**
     * Attempts to establish a connection on the specified host.
     *
     * @param host the host
     * @return the service endpoint
     * @deprecated Use {@link #connect(O2GServers)} instead.
     */
    @Deprecated
    public static ServiceEndPoint connect(Host host) {
        return connect(O2GServers.newBuilder().primaryHost(host).build());
    }

    protected O2G() {
    }
}