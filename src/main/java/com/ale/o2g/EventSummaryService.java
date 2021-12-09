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

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.eventsummary.EventSummary;

/**
 * The Event summary service allows a user to get its new message
 * indicators(missed call, voice mails, callback request, fax).
 * Using this service requires having a <b>TELEPHONY_ADVANCED</b> license,
 */
public interface EventSummaryService extends IService {

	/**
	 * Retrieves main counters for the specified user.
	 * <p>
	 * If the session has been opened for a user, the {@code loginName} parameter is
	 * ignored, but it is mandatory if the session has been opened by an
	 * administrator.
	 * 
	 * @param loginName the user login name
	 * @return the {@link EventSummary EventSummary} in case of success; {@code null}
	 *         otherwise.
	 */
	EventSummary get(String loginName);

	/**
	 * Retrieves main counters of the user who has opened the session.
	 * <p>
	 * This method will fail and return {@code null} if it is invoked from a
	 * session opened by an administrator.
	 * 
	 * @return the {@link EventSummary EventSummary} in case of success; {@code null}
	 *         otherwise.
	 * @see #get(String)
	 */
	EventSummary get();
}
