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

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.messaging.MailBox;
import com.ale.o2g.types.messaging.MailBoxInfo;
import com.ale.o2g.types.messaging.VoiceMessage;

/**
 * {@code MessagingService} service provides access to user's voice mail box.
 * It's possible using this service to connect to the voice mail box, retrieve
 * the information and the list of voice mails and manage the mail box. Using
 * this service requires having a <b>TELEPHONY_ADVANCED</b> license. <br>It's
 * possible to download the voice mail as a wav file and to delete an existing
 * messages.
 */
public interface MessagingService extends IService {

    /**
     * Get the specified user's mailboxes. This is the logical first step to access
     * further operation on voice mail feature.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return A collection of {@link MailBox MailBox} objects in case of success;
     *         {@code null} otherwise.
     */
    Collection<MailBox> getMailBoxes(String loginName);

    /**
     * Get the mailboxes of the user who has opened the session.. This is the
     * logical first step to access further operation on voice mail feature.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return A collection of {@link MailBox MailBox} objects in case of success;
     *         {@code null} otherwise.
     */
    Collection<MailBox> getMailBoxes();

    /**
     * Get the information on the specified mail box.
     * <p>
     * The {@code password} is optional. if not set, the user password is used to
     * connect on the voicemail. This is only possible if the OmniPCX Enterprise
     * administrator has managed the same pasword for the user and his mailbox.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param mailBoxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @param password  the mail box password
     * @param loginName the user login name
     * 
     * @return A {@link MailBoxInfo MailBoxInfo} object in case of success;
     *         {@code null} otherwise.
     */
    MailBoxInfo getMailBoxInfo(String mailBoxId, String password, String loginName);

    /**
     * Get the information on the specified mail box.
     * <p>
     * The {@code password} is optional. if not set, the user password is used to
     * connect on the voicemail. This is only possible if the OmniPCX Enterprise
     * administrator has managed the same pasword for the user and his mailbox.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param mailBoxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @param password  the mail box password
     * 
     * @return A {@link MailBoxInfo MailBoxInfo} object in case of success;
     *         {@code null} otherwise.
     */
    MailBoxInfo getMailBoxInfo(String mailBoxId, String password);

    /**
     * Get the list of voice messages in the specified mail box.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param mailboxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @param newOnly   filter only unread voicemail if set to {@code true}
     * @param offset    the offset from which to start retrieving the voicemail list
     * @param limit     the maximum number of items to return
     * @param loginName the user login name
     * @return A collection of {@link VoiceMessage VoiceMessage} objects in case of
     *         success; {@code null} otherwise.
     */
    Collection<VoiceMessage> getVoiceMessages(String mailboxId, boolean newOnly, Integer offset, Integer limit,
            String loginName);

    /**
     * Get the list of voice mails in the specified mail box for the user who has
     * opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param mailboxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @param newOnly   filter only unread voicemail if set to {@code true}
     * @param offset    the offset from which to start retrieving the voicemail list
     * @param limit     the maximum number of items to return
     * @return A collection of {@link VoiceMessage VoiceMessage} objects in case of
     *         success; {@code null} otherwise.
     */
    Collection<VoiceMessage> getVoiceMessages(String mailboxId, boolean newOnly, Integer offset, Integer limit);

    /**
     * Get the list of voice mails in the specified mail box for the user who has
     * opened the session. Voice mails are retrieved from the begining
     * ({@code offset = 0}) in a limit of 100.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param mailboxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @param newOnly   filter only unread voicemail if set to {@code true}
     * @return A list of {@link VoiceMessage VoiceMessage} objects in case of
     *         success; {@code null} otherwise.
     * @see #getVoiceMessages(String, boolean, Integer, Integer, String)
     */
    Collection<VoiceMessage> getVoiceMessages(String mailboxId, boolean newOnly);

    /**
     * Get the list of voice mails in the specified mail box for the user who has
     * opened the session. All voice mails (new and old) are retrieved from the
     * begining ({@code offset = 0}) in a limit of 100.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param mailboxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @return A collection of {@link VoiceMessage VoiceMessage} objects in case of
     *         success; {@code null} otherwise.
     * @see #getVoiceMessages(String, boolean, Integer, Integer)
     */
    Collection<VoiceMessage> getVoiceMessages(String mailboxId);

    /**
     * Deletes the specified list of voice messages.
     * <p>
     * If some of the specified voice message ids are not valid, they are ignored
     * and this method will succeed.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param mailboxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @param msgIds    the list of voice messages id to delete
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteVoiceMessages(String mailboxId, String[] msgIds, String loginName);

    /**
     * Deletes the specified list of voice messages.
     * <p>
     * If some of the specified voice message ids are not valid, they are ignored
     * and this method will succeed.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param mailboxId the mail box identifier given in a {@link MailBox MailBox}
     *                  object
     * @param msgIds    the list of voice messages id to delete
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteVoiceMessages(String mailboxId, String[] msgIds);

    /**
     * Deletes the specified voice message.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param mailboxId   the mail box identifier given in a {@link MailBox MailBox}
     *                    object
     * @param voicemailId the id of the voice message to delete
     * @param loginName   the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteVoiceMessage(String mailboxId, String voicemailId, String loginName);

    /**
     * Deletes the specified voice message.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param mailboxId   the mail box identifier given in a {@link MailBox MailBox}
     *                    object
     * @param voicemailId the id of the voice message to delete
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteVoiceMessage(String mailboxId, String voicemailId);

    /**
     * Download the specified voice message.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param mailboxId   the mail box identifier given in a {@link MailBox MailBox}
     *                    object
     * @param voicemailId the id of the voice message to download
     * @param wavPath     the path and name file
     * @param loginName   the user login name
     * @return the file downloaded.
     * @throws IOException in case of error while writing on file system.
     */
    Path downloadVoiceMessage(String mailboxId, String voicemailId, String wavPath, String loginName)
            throws IOException;

    /**
     * Download the specified voice message.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param mailboxId   the mail box identifier given in a {@link MailBox MailBox}
     *                    object
     * @param voicemailId the id of the voice message to download
     * @param wavPath     the path and name file
     * @return the file downloaded.
     * @throws IOException in case of error while writing on file system.
     */
    Path downloadVoiceMessage(String mailboxId, String voicemailId, String wavPath) throws IOException;
}
