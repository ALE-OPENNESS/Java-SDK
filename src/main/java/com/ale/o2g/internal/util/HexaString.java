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
package com.ale.o2g.internal.util;

/**
 *
 */
public class HexaString {


    public static byte[] toByteArray(String hexaString) {
        
        int len = hexaString.length();
        if ((len % 2) != 0) {
            throw new RuntimeException("Invalid hexadecimal string");
        }
     
        byte[] data = new byte[len / 2];
        for (int i=0; i<len; i += 2) {
            data[i/2] = (byte) ((Character.digit(hexaString.charAt(i), 16) << 4)
                                 + Character.digit(hexaString.charAt(i+1), 16));
        }

        return data;
    }
    
    public static String toHexaString(byte[] byteValue) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteValue) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16)).append(Character.forDigit((b & 0xF), 16));
        }
        return sb.toString();
    }
}
