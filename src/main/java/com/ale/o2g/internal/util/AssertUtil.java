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
package com.ale.o2g.internal.util;

/**
 *
 */
public class AssertUtil {

	public static String requireNotEmpty(String value, String paramName) {
		if ((value == null) || value.trim().isEmpty()) {
			throw new NullPointerException(String.format("'%s' must be non null", paramName));
		}
		
		return value;
	}

	public static <T> T requireNotNull(T value, String paramName) {
		if (value == null) {
			throw new NullPointerException(String.format("'%s' must be non null", paramName));
		}
		
		return value;
	}

    public static int requirePositive(int value, String paramName) {
        
        if (value < 0)
        {
            throw new NullPointerException(String.format("'%s' must not be >= 0", paramName));
        }
        return value;
    }
}
