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
package com.ale.o2g.types.users;

import java.util.Collection;
import java.util.Collections;

/**
 * {@code User} represents a subscriber in the OmniPCX Enterprise system.
 * <p>
 * A user can have one or multiple devices and optionally a voicemail mailbox.
 * This class provides access to the user's personal and account information,
 * devices, and system configuration.
 * </p>
 */
public final class User {

    private String companyPhone;
    private String firstName;
    private String lastName;
    private String loginName;
    private Voicemail voicemail;
    private Collection<Device> devices;
    private String nodeId;
    private String externalLogin;

    /**
     * Returns the user's company phone number.
     * <p>
     * For multi-device users, this is the phone number of the main device.
     * </p>
     *
     * @return the company phone number, or {@code null} if not set.
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     * Returns the user's first name.
     *
     * @return the first name, or {@code null} if not set.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return the last name, or {@code null} if not set.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the user's login name.
     *
     * @return the login name, or {@code null} if not set.
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Returns the user's voicemail information.
     *
     * @return the {@link Voicemail} object, or {@code null} if the user does not have voicemail.
     */
    public Voicemail getVoicemail() {
        return voicemail;
    }

    /**
     * Returns the collection of devices assigned to this user.
     *
     * @return a {@link Collection} of {@link Device} objects, or {@code null} if the user has no devices.
     */
    public Collection<Device> getDevices() {
        return (devices == null) ? Collections.emptyList() : Collections.unmodifiableCollection(devices);
    }

    /**
     * Returns the OmniPCX Enterprise node ID this user is configured on.
     *
     * @return the node ID as an integer, or {@code -1} if the node ID is not a valid number.
     */
    public int getNodeId() {
        try {
            return Integer.parseInt(nodeId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Returns the user's external login, if any.
     *
     * @return the external login, or {@code null} if the user does not have an external login.
     */
    public final String getExternalLogin() {
        return externalLogin;
    }

    /**
     * Protected default constructor for internal use and subclassing.
     */
    protected User() {
    }
}
