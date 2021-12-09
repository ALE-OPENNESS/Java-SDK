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

import com.ale.o2g.types.Credential;

/**
 * The {@code ServiceEndPoint} interface represents the reachable service
 * endpoint. The {@code ServiceEndPoint} is returned by the O2G singleton object
 * during the bootstrap sequence.
 * 
 * @see O2G
 * @see com.ale.o2g.types.Host Host
 */
public interface ServiceEndPoint {

	/**
	 * Open a session on this O2G service endpoint for the specified credential and
	 * the specified applicationName.
	 * 
	 * @param credential      the {@link Credential} associated to the user or
	 *                        administrator the session is open for.
	 * @param applicationName The applicationName is an identifier for logging and
	 *                        statistic purpose.
	 * @return The created {@link Session Session} object.
	 * @throws O2GAuthenticationException - When the authentication of the given
	 *                                    credential has failed.
	 */
	Session openSession(Credential credential, String applicationName) throws O2GAuthenticationException;
}