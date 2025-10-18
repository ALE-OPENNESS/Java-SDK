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
package com.ale.o2g.types.comlog;

import com.ale.o2g.types.common.PartyInfo;

/**
 * {@code ComRecordParticipant} represents a participant referenced in a communication
 * record (com record).
 * <p>
 * <b>Record Content:</b>
 * <p>
 * <b>Simple call</b> (user A calls user B): the call record on each participant's side
 * will include both participants, though the order is not guaranteed between successive responses.
 * <table style="border: 1px solid black; border-collapse: collapse; padding: 4px;">
 * <caption>Simple call participants</caption>
 * <tr>
 * <th>Side</th>
 * <th>Participants</th>
 * </tr>
 * <tr>
 * <td>A side (caller)</td>
 * <td>
 * <pre>
 * Participant A (owner): role=Caller, answered status
 * Participant B: role and answered status not provided
 * </pre>
 * </td>
 * </tr>
 * <tr>
 * <td>B side (callee)</td>
 * <td>
 * <pre>
 * Participant A: role and answered status not provided
 * Participant B (owner): role=Callee, answered status
 * </pre>
 * </td>
 * </tr>
 * </table>
 * <p>
 * <b>Re-routed call</b> (user A -> B, rerouted to C due to overflow, redirection, or pickup):
 * <table style="border: 1px solid black; border-collapse: collapse; padding: 4px;">
 * <caption>Re-routed call participants</caption>
 * <tr>
 * <th>Side</th>
 * <th>Participants</th>
 * </tr>
 * <tr>
 * <td>A side</td>
 * <td>
 * <pre>
 * Participant A: role=Caller, answered status, initialCalled=B
 * Participant C
 * </pre>
 * </td>
 * </tr>
 * <tr>
 * <td>B side</td>
 * <td>
 * <pre>
 * Participant A: role and answered status not provided
 * Participant C: role=Callee, answered status, initialCalled=B
 * </pre>
 * </td>
 * </tr>
 * <tr>
 * <td>C side</td>
 * <td>
 * <pre>
 * Participant A: role and answered status not provided
 * Participant C (owner): role=Callee, answered status, initialCalled=B
 * </pre>
 * </td>
 * </tr>
 * </table>
 * <p>
 * <b>Multi-party calls:</b> Participants added using {@code addParticipant} will appear in records of
 * already connected users. Their answered status indicates whether they accepted or declined the call.
 * <p>
 * <b>Participant Identification:</b>
 * <ul>
 *   <li>In comlog notification events, the participant owner is identified by {@code loginName} only.
 *       Other participants include full identity ({@code loginName}, {@code phoneNumber}).</li>
 *   <li>In a {@linkplain QueryResult QueryResult}:
 *     <ul>
 *       <li>If no optimization is requested, all participants use full identity.</li>
 *       <li>If {@code optimized=true}, only the first occurrence of each participant uses full identity;
 *           subsequent occurrences use only phone numbers.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 */
public class ComRecordParticipant {

    private Role role;
    private Boolean answered;
    private PartyInfo identity;
    private boolean anonymous;
    private PartyInfo initialCalled;
    private Reason reason;

    /**
     * Returns this participant's role in the communication.
     * 
     * @return the role of the participant.
     */
    public final Role getRole() {
        return role;
    }

    /**
     * Returns whether this participant answered the call.
     * 
     * @return {@code true} if answered, {@code false} if not, or {@code null} if unknown.
     */
    public final Boolean getAnswered() {
        return answered;
    }

    /**
     * Returns the participant's identity.
     * 
     * @return the participant's {@link PartyInfo}.
     */
    public final PartyInfo getIdentity() {
        return identity;
    }

    /**
     * Indicates if the participant is anonymous.
     * 
     * @return {@code true} if anonymous; {@code false} otherwise.
     */
    public final boolean isAnonymous() {
        return anonymous;
    }

    /**
     * Returns the number that was initially called when this participant joined the call.
     * 
     * @return the initial called number, or {@code null} if not applicable.
     */
    public final PartyInfo getInitialCalled() {
        return initialCalled;
    }

    /**
     * Returns the reason for the call being established, rerouted, or terminated.
     * 
     * @return the call {@link Reason}.
     */
    public final Reason getReason() {
        return reason;
    }

    /** Protected default constructor for deserialization. */
    protected ComRecordParticipant() {
    }
}
