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
 * {@code MailBoxInfo} provides information on the mail box the user is
 * connected on.
 * 
 * @see com.ale.o2g.MessagingService#getMailBoxInfo(String, String, String)
 *      MessagingService.getMailBoxInfo
 */
public class MailBoxInfo {

    private int totalVoiceMsg;
    private int newVoiceMsg;
    private int storageUsage;

    /**
     * Returns the number of voice messages in this mail box.
     * 
     * @return the total number of voice messages.
     */
    public final int getTotalVoiceMsg() {
        return totalVoiceMsg;
    }

    /**
     * Returns the new voice messages in this mail box.
     * 
     * @return the new voice messages.
     */
    public final int getNewVoiceMsg() {
        return newVoiceMsg;
    }

    /**
     * Returns the storage usage.
     * 
     * @return the storage usage.
     */
    public final int getStorageUsage() {
        return storageUsage;
    }

    protected MailBoxInfo() {
    }

}
