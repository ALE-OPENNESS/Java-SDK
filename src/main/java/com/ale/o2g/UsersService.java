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
 * <li>A user to change its password or get some parameterq like supported
 * language.</li>
 * </ul>
 * <p>
 * Using this service does not require any specific license.
 */
public interface UsersService extends IService {

    /**
     * Retrieves a list of users login from the connected OXEs.
     * <p>
     * if {@code nodeIds} is {@code null}, retrieves the login of users from all the
     * connected OmniPCX Enterprise nodes. This method is generally used by an
     * administrator. if it is used by a user, {@code nodeIds} must be set to
     * {@code null} and {@code onlyACD} to false. In this case only the user login
     * is retrieved.
     * 
     * @param nodeIds Specify a list of OXE nodes Id in which the query is done.
     *                This parameter is only valid for an administrator session.
     * @param onlyACD Allows to select only the ACD operators (agents or
     *                supervisors) during the query. This parameter is only valid
     *                for an administrator session. .
     * @return The collection of users identified by their login. If getLogins is used by
     *         a user session, it return only the user's login.
     */
    Collection<String> getLogins(String[] nodeIds, boolean onlyACD);

    /**
     * Retrieves the information on a user from its login.
     * 
     * @param loginName the user login name
     * @return a {@link com.ale.o2g.types.users.User User} object that represents
     *         the user, or {@code null} in case of error or if there is no user
     *         with the specified login.
     */
    User getByLoginName(String loginName);

    /**
     * Retrieves the information on a user from its company extension umber.
     * 
     * @param companyPhone the user extension number
     * @return a {@link com.ale.o2g.types.users.User User} object that represents
     *         the user, or {@code null} in case of error or if there is no user
     *         with the specified comapny extension number.
     */
    User getByCompanyPhone(String companyPhone);

    /**
     * Returns the preference of the specified user.
     * 
     * @param loginName the user login name
     * @return a {@link com.ale.o2g.types.users.Preferences} object that represents
     *         the user's preferences or {@code null} in case of error or if there
     *         is no user with the specified login.
     */
    Preferences getPreferences(String loginName);

    /**
     * Returns the specified user supported languages.
     * 
     * @param loginName the user login name
     * @return a {@link com.ale.o2g.types.users.SupportedLanguages} object that
     *         represents the user's supported languages or {@code null} in case of
     *         error or if there is no user with the specified login.
     */
    SupportedLanguages getSupportedLanguages(String loginName);

    /**
     * Changes the specified user password.
     * 
     * @param loginName   the user login name
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean changePassword(String loginName, String oldPassword, String newPassword);
}
