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

/**
 * Provides classes for querying and managing user communication logs (com records)
 * in conjunction with the {@link com.ale.o2g.CommunicationLogService}.
 * <p>
 * Communication records are generated at the O2G server level using telephony
 * call monitoring. As a result, the comlog database is maintained independently
 * of the OmniPcx Enterprise internal call log.
 * <p>
 * The main concepts and classes in this package include:
 * <ul>
 *   <li>{@link ComRecord} - represents a single communication record.</li>
 *   <li>{@link ComRecordParticipant} - represents a participant in a communication record.</li>
 *   <li>{@link Option} - defines filter options for communication log queries.</li>
 *   <li>{@link Page} - supports pagination when querying communication logs.</li>
 *   <li>{@link QueryFilter} - defines filters for querying communication records.</li>
 *   <li>{@link QueryResult} - represents the results of a communication log query, including paging information.</li>
 *   <li>{@link Reason} - enumerates the reasons for call establishment, termination, or rerouting.</li>
 *   <li>{@link Role} - defines the possible roles of participants in a communication.</li>
 * </ul>
 * <p>
 * This package is primarily used to retrieve, filter, and navigate communication
 * logs programmatically via the {@link com.ale.o2g.CommunicationLogService}, providing
 * detailed information about calls, participants, call outcomes, and durations.
 *
 */
package com.ale.o2g.types.comlog;
