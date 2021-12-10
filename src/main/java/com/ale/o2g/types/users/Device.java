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

/**
 * {@code Device} class represents a device of a user.
 */
public class Device {

    /**
     * The device type
     */
    public enum Type {

        /**
         * The device is a DECT. A wireless phone set used within the enterprise.
         */
        DECT,

        /**
         * The device is a deskphone. A phone set on an office's desk.
         */
        DESKPHONE,

        /**
         * The device is a mobile phone.
         */
        MOBILE,

        /**
         * The device is a softphone. A phone started from a computer with VOIP.
         */
        SOFTPHONE
    }

    private Type type;
    private String id;
    private String subType;

    /**
     * Returns this device type.
     * 
     * @return the device type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns this device Id.
     * 
     * @return the device id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns this device sub type.
     * 
     * @return the device sub type.
     */
    public String getSubType() {
        return subType;
    }

    protected Device() {
    }

}
