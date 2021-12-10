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
package com.ale.o2g.types.telephony;

import java.util.Date;

/**
 * {@code MiniMessage} class represents a mini message exchanged between two
 * users.
 */
public class MiniMessage {
    private String sender;
    private Date dateTime;
    private String message;

    /**
     * Return the sender of this message.
     * 
     * @return the sender.
     */
    public final String getSender() {
        return sender;
    }

    /**
     * Return date the mini message has been sent.
     * 
     * @return the date
     */
    public final Date getDate() {
        return dateTime;
    }

    /**
     * Return the text message.
     * 
     * @return the message.
     */
    public final String getMessage() {
        return message;
    }

    protected MiniMessage() {
    }

}
