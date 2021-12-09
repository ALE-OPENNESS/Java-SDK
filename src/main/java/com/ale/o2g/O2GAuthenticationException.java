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

/**
 * Signals that the attempts to open an O2G session has failed due to an authentication error.
 * @see com.ale.o2g.O2GException O2GException
 * @see com.ale.o2g.ServiceEndPoint#openSession(com.ale.o2g.types.Credential, String) ServiceEndPoint.openSession
 */
public class O2GAuthenticationException extends O2GException {

    @java.io.Serial
    private static final long serialVersionUID = -6427997564725850723L;
    
    /**
     * Constructs a O2GAuthenticationException. 
     */
    public O2GAuthenticationException() {
    }

    /**
     * Constructs an O2GAuthenticationException with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method). 
     */
    public O2GAuthenticationException(Throwable cause) {
        super(cause);
    }
}
