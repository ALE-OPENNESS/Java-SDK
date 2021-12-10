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
import com.ale.o2g.internal.ServiceFactory;
import com.ale.o2g.types.Host;
import com.ale.o2g.types.ServerInfo;

/**
 * {@code O2G} is the basic class to bootstarp the SDK and create a
 * {@linkplain ServiceEndPoint} object.
 */
public final class O2G {

    /**
     * The O2G Api version
     */
    public static String ApiVersion = "1.0";

    /**
     * Attemps to establish a connection on the specified Host.
     * 
     * @param host the host
     * @return the connected endpoint
     * @throws O2GException when the service end point can not be reached.
     */
    public static ServiceEndPoint Connect(Host host) throws O2GException {

        ServiceFactory serviceFactory = new ServiceFactory(ApiVersion);
        ServerInfo serverInfo = serviceFactory.bootstrap(host);

        return new ServiceEndPointImpl(serviceFactory, serverInfo);
    }

    protected O2G() {
    }
}
