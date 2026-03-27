/*
* Copyright 2025 ALE International
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
package com.ale.o2g.types.ccrt;


/**
 * Represents a CCD object identifier for which real-time information is available.
 * <p>
 * Instances of this class provide details about a CCD object, such as agents, pilots,
 * or queues, that can be monitored using {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}.
 * <p>
 * Each object includes a directory number, name, first name, and a unique key.
 * This class is typically used to retrieve and reference objects for real-time events.
 * 
 * @see com.ale.o2g.CallCenterRealtimeService
 * @since 2.7.4
 */
public class RtiObjectIdentifier {
    
    private String number;
    private String name;
    private String firstName;
    private int key;
    
    /**
     * 
     */
    protected RtiObjectIdentifier() {
    }

    /**
     * Returns the directory number associated with this identifier.
     *
     * @return the directory number
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Returns the last name associated with this identifier.
     *
     * @return the last name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the first name associated with this identifier.
     *
     * @return the first name
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Returns the unique key associated with this identifier.
     *
     * @return the key
     */
    public final int getKey() {
        return key;
    }

}
