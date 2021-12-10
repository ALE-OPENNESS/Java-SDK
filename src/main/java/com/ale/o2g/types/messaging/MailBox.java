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

/**
 * {@code MailBox} class represents a mail box in a voice mail system. A voice mail is assigned to a user.
 */
public class MailBox {

    /**
     * {@code Capabilities} represents the capabilities of the voice mail.
     */
	public static class Capabilities {
	    private boolean listMessages;
        private boolean getMessages;
        private boolean getRecord;
        private boolean play;
        private boolean pause;
        private boolean hangup;
        private boolean record;
        private boolean resume;
        private boolean cancel;
        private boolean forward;
        private boolean callback;
        private boolean send;
        private boolean events;
        
        /**
         * Returns whether the voicemail server can return the list of messages.
         * @return {@code true} if the voice mail can return the list of message; {@code false} otherwise.
         */
        public final boolean canListMessages() {
            return listMessages;
        }
        
        /**
         * Returns wheather the voice messages can be downloaded.
         * @return {@code true} if the voice message can be downloaded; {@code false} otherwise.
         */
        public final boolean canGetMessages() {
            return getMessages;
        }
        
        /**
         * Returns whether the recorded message can be downloaded.
         * @return {@code true} if the recorded message can be downloaded; {@code false} otherwise.
         */
        public final boolean canGetRecord() {
            return getRecord;
        }
        
        /**
         * Returns whether the voicemail server can play voice messages.
         * @return {@code true} if voicemail server can play voice messages; {@code false} otherwise.
         */
        public final boolean canPlay() {
            return play;
        }
        
        /**
         * Returns whether a played voice message can be paused and resumes from the position it has been paused.
         * @return {@code true} if a voice message can be paused; {@code false} otherwise.
         */
        public final boolean canPause() {
            return pause;
        }
        
        /**
         * Returns whether the media session can be terminate.
         * @return {@code true} if the media session can be terminate; {@code false} otherwise.
         */
        public final boolean canHangup() {
            return hangup;
        }
        
        /**
         * Returns whether the voicemail server can record voice messages.
         * @return {@code true} if the voicemail server can record voice messages; {@code false} otherwise.
         */
        public final boolean canRecord() {
            return record;
        }
        
        /**
         * Returns wheter a voice message can be resumed to the position it has been paused.
         * @return {@code true} if a voice message can be resumed; {@code false} otherwise.
         */
        public final boolean canResume() {
            return resume;
        }
        
        /**
         * Returns whether the current recording can be cancelled.
         * @return {@code true} if the current recording can be cancelled; {@code false} otherwise.
         */
        public final boolean canCancel() {
            return cancel;
        }
        
        /**
         * Returns wheter the voicemail server can forward voice messages.
         * @return {@code true} if the voicemail server can forward voice messages; {@code false} otherwise.
         */
        public final boolean canForward() {
            return forward;
        }
        
        /**
         * Returns whether the voicemail server can call back the originator of the voice message.
         * @return {@code true} if the voicemail server can call back; {@code false} otherwise.
         */
        public final boolean canCallback() {
            return callback;
        }
        
        /**
         * Returns whether a voice message or a record can be sent to recipients.
         * @return {@code true} if a voice message or a record can be sent; {@code false} otherwise.
         */
        public final boolean canSend() {
            return send;
        }
        
        /**
         * Returns whether the voicemail server can send events in case of message deposit / removal.
         * @return {@code true} if the voicemail server can send events; {@code false} otherwise.
         */
        public final boolean canSendEvents() {
            return events;
        }

        protected Capabilities() {
        }
	}
	
	private String id;
	private String name;
	private Capabilities capabilities;
	
	
    /**
     * Returns this mail box identifier.
     * @return the id.
     */
    public final String getId() {
        return id;
    }
    
    
    /**
     * Returns this mail box name.
     * @return the name.
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Returns this mail box capabilities.
     * @return the capabilities.
     */
    public final Capabilities getCapabilities() {
        return capabilities;
    }


    protected MailBox() {
    }
}
