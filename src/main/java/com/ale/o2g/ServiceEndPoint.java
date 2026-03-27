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
     * Assign a specific {@link SessionMonitoringPolicy SessionMonitoringPolicy} to
     * the {@link Session Session} opened by this {@code ServiceEndPoint}.
     * <p>
     * The {@link SessionMonitoringPolicy SessionMonitoringPolicy} allows to receive
     * notifications on abnormal situations and to react accordingly by providing a
     * defense Behavior.
     * <p>
     * A default session monitoring policy is provided. the default behaviors
     * are:<br>
     * <table>
     * <caption>Default behaviors</caption> <thead>
     * <tr>
     * <th>Abnormal situation</th>
     * <th>Behavior</th>
     * </tr>
     * </thead>
     * <tr>
     * <td>Exception in keep alive thread</td>
     * <td>The thread is aborted.</td>
     * </tr>
     * <tr>
     * <td>Lost of the chunk eventing thread</td>
     * <td>Try 5 second and retry.</td>
     * </tr>
     * </table>
     * 
     * 
     * @param sessionMonitoringPolicy the session monitoring policy
     */
    void setSessionMonitoringPolicy(SessionMonitoringPolicy sessionMonitoringPolicy);

    /**
     * Opens a supervised session on this O2G service endpoint for the specified credential,
     * application name, a supervised account.
     *
     * <p>The opened session provides access to services according to the licenses
     * associated with the administrator for whom the session is opened.</p>
     *
     * <p>When the session is successfully opened, it is automatically maintained
     * by a keep-alive mechanism running on a dedicated thread. The session can be
     * used like a normal user or administrator session. the session allows the supervisor 
     * to operate as the supervised user or administrator.</p>
     *
     * @param credential        the {@link Credential} associated with the user or
     *                          administrator for whom the session is opened
     * @param applicationName   an identifier for logging and statistical purposes
     * @param supervisedAccount {@link SupervisedAccount} representing
     *                          the user or administrator to supervise; can be
     *                          {@code null} if the session is not supervised
     *                          
     * @return the created {@link Session} object representing the opened session
     * @throws O2GAuthenticationException if authentication with the provided
     *                                    {@link Credential} fails
     */
    Session openSession(Credential credential, String applicationName, SupervisedAccount supervisedAccount) throws O2GAuthenticationException;

    /**
     * Open a session on this O2G service endpoint for the specified credential and
     * the specified applicationName.
     * <p>
     * The opened session provides access to services according to the licenses
     * associated to the user who open the session.
     * <p>
     * When the session is opened, it is automatically maintained by a keep alive
     * mechanism running on a dedicated thread.
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
