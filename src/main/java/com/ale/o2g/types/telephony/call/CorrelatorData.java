/*
* Copyright 2024 ALE International
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
package com.ale.o2g.types.telephony.call;

/**
 * This class describe a correlator data.
 * <p>
 * A correlator data is used by the application to provide application-related
 * information (limited to 32 bytes).
 * <p>
 * Example: user B has receives an external call. Before he makes an enquiry
 * call to user B, he associates data to this incoming call. If user A transfers
 * the call to user B, user B is notified of the transfer with an event
 * containing CallData with the associateData.
 */
public class CorrelatorData {

    private byte[] value;

    /**
     * Construct a new Correlator data with the specified string.
     * 
     * @param strValue the string value to attach to the call
     */
    public CorrelatorData(String strValue) {
        value = strValue.getBytes();
    }

    /**
     * Construct a new Correlator data with a bytes array value.
     * <p>
     * Byte "00" is not authorized in a correlator data.
     * 
     * @param bytes the byte array value
     */
    public CorrelatorData(byte[] bytes) {
        value = bytes;
    }

    /**
     * Return the byte array corresponding to this correlator data.
     * 
     * @return the byte array value
     */
    public byte[] asByteArray() {
        return this.value;
    }

    /**
     * Return the string corresponding to this correlator data.
     * 
     * @return the string value.
     */
    public String asString() {
        return new String(value);
    }
}
