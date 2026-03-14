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
package com.ale.o2g;

/**
 * Represents a supervised account when an administrator opens a supervised session.
 *
 * <p><b>Supervised Session:</b></p>
 * <p>
 * A supervised session is opened by a supervisor (administrator) using their
 * credentials along with the identification of a target user or another
 * administrator (login name or phone number).
 * </p>
 * <p>
 * If the session is successfully opened, the supervisor's credentials will be
 * used in all subsequent requests to identify the session, the supervised user,
 * or the supervised administrator.
 * </p>
 * <p>
 * After opening, a supervised session can be used like a normal user session
 * or an administrator session. This allows a supervisor to operate as the
 * supervised user or administrator for services that rely on the session.
 * </p>
 */
public class SupervisedAccount {

    /**
     * Type of identifier used for the supervised account.
     */
    private static enum Type {
        LOGIN_NAME,
        PHONE_NUMBER
    }

    @SuppressWarnings("unused")
    private Type type;
    @SuppressWarnings("unused")
    private String id;

    /**
     * Constructs a new SupervisedAccount with the specified type and identifier.
     *
     * @param type the type of identifier (login name or phone number)
     * @param id   the identifier value
     */
    private SupervisedAccount(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    /**
     * Creates a new SupervisedAccount using a login name.
     *
     * @param loginName the login name of the supervised user or administrator
     * @return a new SupervisedAccount instance
     */
    public static SupervisedAccount withLoginName(String loginName) {
        return new SupervisedAccount(Type.LOGIN_NAME, loginName);
    }

    /**
     * Creates a new SupervisedAccount using a phone number.
     *
     * @param number the phone number of the supervised user or administrator
     * @return a new SupervisedAccount instance
     */
    public static SupervisedAccount withPhoneNumber(String number) {
        return new SupervisedAccount(Type.PHONE_NUMBER, number);
    }
}