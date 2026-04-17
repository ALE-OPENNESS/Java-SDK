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

import java.util.Collection;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.users.User;

/**
 * The {@code UserManagementService} allows an administrator to create, delete,
 * and retrieve O2G users. O2G allows users to be created according to different methods:
 * <ul>
 * <li>Automatically when O2G starts, according to the automatic user creation mode.</li>
 * <li>Through provisioning files.</li>
 * <li>On demand through this REST service, which allows creating one user, a list
 *     of users, or all users on a given OmniPCX Enterprise node.</li>
 * </ul>
 * <p>
 * Using this service does not require any specific license.
 */
public interface UserManagementService extends IService {

    /**
     * Retrieves a list of user login names from the connected OmniPCX Enterprise nodes.
     * <p>
     * If {@code nodeIds} is {@code null}, retrieves the login names from all connected
     * OmniPCX Enterprise nodes.
     *
     * @param nodeIds a list of OXE node ids to restrict the query to, or {@code null}
     *                to query all connected nodes.
     * @return The collection of user login names, or {@code null} in case of error.
     */
    Collection<String> getLogins(int[] nodeIds);

    /**
     * Retrieves the login name of a user identified by one of their device directory numbers.
     *
     * @param deviceNumber a directory number of a device belonging to the user being
     *                     searched for.
     * @return The login name of the user, or {@code null} in case of error or if no
     *         user owns a device with the specified directory number.
     */
    String getLogin(String deviceNumber);

    /**
     * Retrieves the information of a user identified by their login name.
     *
     * @param loginName the user login name
     * @return A {@link com.ale.o2g.types.users.User User} object that represents the
     *         user, or {@code null} in case of error or if there is no user with the
     *         specified login name.
     */
    User getUser(String loginName);

    /**
     * Creates and monitors the specified O2G users on the given OmniPCX Enterprise node.
     *
     * @param nodeId        the OXE node number.
     * @param deviceNumbers the list of device directory numbers identifying the users to create.
     * @return The collection of {@link com.ale.o2g.types.users.User User} objects
     *         representing the created users in case of success; {@code null} otherwise.
     */
    Collection<User> createUsers(int nodeId, String[] deviceNumbers);

    /**
     * Creates and monitors all O2G users configured on the given OmniPCX Enterprise node.
     *
     * @param nodeId the OXE node number.
     * @return The collection of {@link com.ale.o2g.types.users.User User} objects
     *         representing the created users in case of success; {@code null} otherwise.
     */
    Collection<User> createUsers(int nodeId);

    /**
     * Deletes the O2G user identified by their login name.
     *
     * @param loginName the login name of the O2G user to delete.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteUser(String loginName);
}
