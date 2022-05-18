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

/**
 * {@code User} class represents a user in the system. A user is an OmniPCX
 * Enterprise subscriber. He can have one or several devices, an a mail box.
 */
public final class User {

    private String companyPhone;
    private String firstName;
    private String lastName;
    private String loginName;
    private Voicemail voicemail;
    private Collection<Device> devices;
    private String nodeId;

    /**
     * Returns this user company phone. This company phone number is the phone
     * number of the main device when the user has a multi-device configuration.
     * 
     * @return the company phone number.
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     * Returns this user's first name.
     * 
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns this user's last name.
     * 
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns this user's login.
     * 
     * @return the login.
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Return this user's voice mail information.
     * 
     * @return the voice mail information or {@code null} if the user do not have a voice mail.
     */
    public Voicemail getVoicemail() {
        return voicemail;
    }

    /**
     * Returns the user's devices.
     * 
     * @return a collection of devices.
     */
    public Collection<Device> getDevices() {
        return devices;
    }

    /**
     * Return the OmniPCX Enterprise node this user is configured on.
     * 
     * @return the the OmniPCX Enterprise node number.
     */
    public int getNodeId() {
        try {
            return Integer.parseInt(nodeId);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    protected User() {
    }
    
}
