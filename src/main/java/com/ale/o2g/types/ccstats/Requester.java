/*
* Copyright 2025 ALE International
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
package com.ale.o2g.types.ccstats;

import java.time.ZoneOffset;

/**
 * {@code Requester} represents an entity (typically a supervisor) that requests
 * statistics from the system. It encapsulates the requester's identifier,
 * preferred language, and time zone information.
 * <p>
 * Instances of this class are immutable once created.
 *
 * @since 2.7.4
 */
public interface Requester {

    /**
     * Returns the unique identifier of this requester.
     *
     * @return the requester identifier
     */
    String getId();

    /**
     * Returns the preferred language of this requester.
     *
     * @return the requester's language
     */
    Language getLanguage();

    /**
     * Returns the time zone of this requester as a {@link ZoneOffset}.
     *
     * @return the requester's time zone, or {@code null} if the stored value
     *         cannot be parsed into a valid {@code ZoneOffset}
     */
    ZoneOffset getTimezone();
}
