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
package com.ale.o2g.types.messaging;

import java.util.Date;

import com.ale.o2g.types.common.PartyInfo;

/**
 * {@code VoiceMessage} represents a message stored in a voice Mail box.
 */
public class VoiceMessage {

	private String voicemailId;
	private PartyInfo from;
	private int duration;
	private Date date;
	private boolean unread;
	private boolean highPriority;
	
	
    /**
     * Returns this message identifier.
     * @return the message identifier.
     */
    public final String getVoicemailId() {
        return voicemailId;
    }
    
    /**
     * Returns the party who has deposit this message.
     * @return the party info.
     */
    public final PartyInfo getFrom() {
        return from;
    }
    
    /**
     * Returns this message duration.
     * @return the duration in seconds.
     */
    public final int getDuration() {
        return duration;
    }
    
    /**
     * Returns the date this message has been deposit.
     * @return the date of the message.
     */
    public final Date getDate() {
        return date;
    }

    /**
     * Returns whether this message has been read.
     * @return {@code true} is the message has been read; {@code false} otherwise.
     */
    public final boolean isUnread() {
        return unread;
    }
    
    /**
     * Return whether this message has high priority.
     * @return {@code true} is the message has high priority; {@code false} otherwise.
     */
    public final boolean isHighPriority() {
        return highPriority;
    }

    protected VoiceMessage() {
    }
	
	
}
