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
 * The {@code UserManagementService} allows an administrator to
 * create/delete/get the O2G users. O2G allows to create O2G users according to
 * different methods:
 * <ul>
 * <li>automatically when O2G starts, according to automatic user creation mode.
 * <li>through provisioning files.
 * <li>on demand through this REST service: the service allows to create one
 * user, a list of users or all the users.
 * </ul>
 * <p>
 * Using this service does not require any specific license.
 */
public interface UserManagementService extends IService {

    /**
     * Retrieves a list of users login from the connected OXEs.
     * <p>
     * if {@code nodeIds} is {@code null}, retrieves the login of users from all the
     * connected OmniPCX Enterprise nodes.
     * 
     * @param nodeIds      Specify a list of OXE nodes Id in which the query is
     *                     done.
     * @return The collection of users identified by their login.
     */
    Collection<String> getLogins(int[] nodeIds);

    /**
     * Retrieves a user login from one of its phone directory number.
     * @param deviceNumber a directory number of a device belonging to the user being searched for.
     * @return The login name for the user.
     */
    String getLogin(String deviceNumber);

    /**
     * Retrieves a user from its login.
     * 
     * @param loginName the user login name
     * @return a {@link com.ale.o2g.types.users.User User} object that represents
     *         the user, or {@code null} in case of error or if there is no user
     *         with the specified login.
     */
    User getUser(String loginName);

    /**
     * Create and monitor the specified list of O2G users on the specified OmniPCX Enterprise
     * node.
     * 
     * @param nodeId        the OXE node number.
     * @param deviceNumbers the list of device number to create.
     * @return The collection of {@see com.ale.o2g.types.users.User} in case of
     *         success; {@code null} otherwise.
     */
    Collection<User> createUsers(int nodeId, String[] deviceNumbers);

    /**
     * Create all O2G users configured on the specified OmniPCX Enterprise node.
     * 
     * @param nodeId the OXE node number.
     * @return The collection of {@see com.ale.o2g.types.users.User} in case of
     *         success; {@code null} otherwise.
     */
    Collection<User> createUsers(int nodeId);

    /**
     * Delete the specified O2G user.
     * 
     * @param loginName the O2G user's login name.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteUser(String loginName);
}
