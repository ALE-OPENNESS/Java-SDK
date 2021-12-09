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

/**
 * This class represents an O2G host.
 * <p>
 * An O2G host can be reacheable from the local network, or from a wide area
 * network. In this case, enterprise access can be secured by a reverse proxy
 * border element.
 */
public final class Host {

	private String privateAddress;
	private String publicAddress;

	/**
	 * Construct a new host with an private and a public address.
	 * <p>The private address is either the IP address or the URI used to reach the O2G
	 * service end point on a local network.
	 * <p>The public address is either the IP address or the URI used to reach the O2G
	 * service end point through a reverse proxy.
	 * <p>It is possible to have only one address configured, but a {@link java.lang.IllegalArgumentException} is throws if both are {@code null}.
	 * 
	 * @param privateAddress the private address
	 * @param publicAddress  the public address
	 */
	public Host(String privateAddress, String publicAddress) {
		this.privateAddress = privateAddress;
		this.publicAddress = publicAddress;
		assertValid();
	}

	/**
	 * Construct a new host with a private address and not public address.
	 * <p>The private address is either the IP address or the URI used to reach the O2G
	 * service end point on a local network.
	 * <p>A {@link java.lang.IllegalArgumentException} is throws if the private address is {@code null}.
	 * 
	 * @param privateAddress the private address
	 */
	public Host(String privateAddress) {
		this(privateAddress, null);
		assertValid();
	}

	/**
	 * Returns the private address for this host.
	 * @return the private address or {@code null} if the private address is not specified
	 */
	public String getPrivateAddress() {
		return privateAddress;
	}

	/**
	 * Returns the public address for this host.
	 * @return the public address or {@code null} if the public address is not specified
	 */
	public String getPublicAddress() {
		return publicAddress;
	}

	private void assertValid() {
		if ((this.privateAddress == null) && (this.publicAddress == null)) {
			throw new IllegalArgumentException("Public and private address can't be both null.");
		}
	}
}
