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
import com.ale.o2g.types.users.Preferences;
import com.ale.o2g.types.users.SupportedLanguages;
import com.ale.o2g.types.users.User;

/**
 * The {@code UsersService} allows:
 * <ul>
 * <li>An administrator to retrieve the list of O2G users.</li>
 * <li>A user to get information on another user account.</li>
 * <li>A user to change their password or get parameters such as supported languages.</li>
 * </ul>
 * <p>
 * Using this service does not require any specific license.
 */
public interface UsersService extends IService {

    /**
     * Retrieves a list of user login names from the connected OmniPCX Enterprise nodes.
     * <p>
     * If {@code nodeIds} is {@code null}, retrieves the login names from all connected
     * OmniPCX Enterprise nodes. This method is generally used by an administrator. If
     * used by a user, {@code nodeIds} must be set to {@code null} and {@code onlyACD}
     * to {@code false}, in which case only the current user's login name is retrieved.
     *
     * @param nodeIds Specify a list of OXE node ids in which the query is done.
     *                This parameter is only valid for an administrator session.
     * @param onlyACD Allows to select only the ACD operators (agents or supervisors)
     *                during the query. This parameter is only valid for an administrator
     *                session.
     * @return The collection of user login names. If used by a user session, returns
     *         only the current user's login name.
     * @deprecated Use {@link #getLogins(int[], boolean)} instead.
     */
	@Deprecated
    Collection<String> getLogins(String[] nodeIds, boolean onlyACD);


    /**
     * Retrieves a list of user login names from the connected OmniPCX Enterprise nodes.
     * <p>
     * If {@code nodeIds} is {@code null}, retrieves the login names from all connected
     * OmniPCX Enterprise nodes. This method is generally used by an administrator. If
     * used by a user, {@code nodeIds} must be set to {@code null} and {@code onlyACD}
     * to {@code false}, in which case only the current user's login name is retrieved.
     *
     * @param nodeIds Specify a list of OXE node ids in which the query is done.
     *                This parameter is only valid for an administrator session.
     * @param onlyACD Allows to select only the ACD operators (agents or supervisors)
     *                during the query. This parameter is only valid for an administrator
     *                session.
     * @return The collection of user login names. If used by a user session, returns
     *         only the current user's login name.
     */
    Collection<String> getLogins(int[] nodeIds, boolean onlyACD);


    /**
     * Retrieves the information of a user identified by their login name.
     *
     * @param loginName the user login name
     * @return A {@link com.ale.o2g.types.users.User User} object that represents the
     *         user, or {@code null} in case of error or if there is no user with the
     *         specified login name.
     */
    User getByLoginName(String loginName);

    /**
     * Retrieves the information of a user identified by their company extension number.
     *
     * @param companyPhone the user company extension number
     * @return A {@link com.ale.o2g.types.users.User User} object that represents the
     *         user, or {@code null} in case of error or if there is no user with the
     *         specified company extension number.
     */
    User getByCompanyPhone(String companyPhone);

    /**
     * Returns the preferences of the specified user.
     *
     * @param loginName the user login name
     * @return A {@link com.ale.o2g.types.users.Preferences Preferences} object that
     *         represents the user's preferences, or {@code null} in case of error or
     *         if there is no user with the specified login name.
     */
    Preferences getPreferences(String loginName);

    /**
     * Returns the supported languages for the specified user.
     *
     * @param loginName the user login name
     * @return A {@link com.ale.o2g.types.users.SupportedLanguages SupportedLanguages}
     *         object that represents the user's supported languages, or {@code null}
     *         in case of error or if there is no user with the specified login name.
     */
    SupportedLanguages getSupportedLanguages(String loginName);

    /**
     * Changes the specified user's password.
     * <p>
     * This operation will fail if authentication is delegated to an external LDAP server.
     *
     * @param loginName   the user login name
     * @param oldPassword the current password
     * @param newPassword the new password
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean changePassword(String loginName, String oldPassword, String newPassword);
}
