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
import com.ale.o2g.types.directory.Criteria;
import com.ale.o2g.types.directory.SearchResult;

/**
 * The {@code DirectoryService} allows searching for contacts in the OmniPCX
 * Enterprise phone book. Using this service requires a <b>TELEPHONY_ADVANCED</b> license.
 * <p>
 * A directory search involves a sequence of operations:
 * <ol>
 *   <li>Initiate the search with a set of criteria.</li>
 *   <li>Retrieve results using one or more subsequent operations.</li>
 * </ol>
 * <p>
 * <b>Note:</b> For each session (user or administrator), only 5 concurrent searches
 * are allowed. An unused search context is automatically freed after 1 minute.
 * <p>
 * <b>Example usage:</b>
 * <pre>{@code
 * DirectoryService directoryService = session.getDirectoryService();
 * boolean finished = false;
 * 
 * // Start the search
 * directoryService.search(myCriteria);
 * 
 * // Loop to retrieve results
 * while (!finished) {
 *     SearchResult result = directoryService.getResults();
 *     
 *     switch (result.getResultCode()) {
 *         case NOK:
 *             // Search in progress, no result available yet
 *             Thread.sleep(500); // wait before next iteration
 *             break;
 *         case OK:
 *             // Process available results
 *             process(result.getResultElements());
 *             break;
 *         case FINISH:
 *         case TIMEOUT:
 *             // Search ended
 *             finished = true;
 *             break;
 *     }
 * }
 * }</pre>
 */
public interface DirectoryService extends IService {

    /**
     * Initiates a search for the specified user with the given filter and result limit.
     * <p>
     * If the session is opened for a user, the {@code loginName} parameter is ignored.
     * It is mandatory only when the session is opened by an administrator.
     * 
     * @param filter    the search filter
     * @param limit     maximum number of results (1..100)
     * @param loginName the user login name
     * @return {@code true} if the search was successfully initiated, {@code false} otherwise
     */
    boolean search(Criteria filter, int limit, String loginName);

    /**
     * Initiates a search for the user who has opened the session with the specified
     * filter and result limit.
     * <p>
     * This method will return {@code false} if invoked from a session opened by an administrator.
     * 
     * @param filter the search filter
     * @param limit  maximum number of results (1..100)
     * @return {@code true} if the search was successfully initiated, {@code false} otherwise
     * @see #search(Criteria, int, String)
     */
    boolean search(Criteria filter, int limit);

    /**
     * Initiates a search for the user who has opened the session with the specified
     * filter. The search result limit defaults to 100.
     * <p>
     * This method will return {@code false} if invoked from a session opened by an administrator.
     * 
     * @param filter the search filter
     * @return {@code true} if the search was successfully initiated, {@code false} otherwise
     * @see #search(Criteria, int, String)
     */
    boolean search(Criteria filter);

    /**
     * Cancels the current search query for the specified user.
     * <p>
     * If the session is opened for a user, the {@code loginName} parameter is ignored.
     * It is mandatory only when the session is opened by an administrator.
     * 
     * @param loginName the user login name
     * @return {@code true} if the search was successfully cancelled, {@code false} otherwise
     */
    boolean cancel(String loginName);

    /**
     * Cancels the current search query for the user who has opened the session.
     * <p>
     * This method will return {@code false} if invoked from a session opened by an administrator.
     * 
     * @return {@code true} if the search was successfully cancelled, {@code false} otherwise
     */
    boolean cancel();

    /**
     * Retrieves the next available results for the current search for the specified user.
     * <p>
     * This method is generally called in a loop. Behavior for each result code:
     * <ul>
     *   <li>{@code NOK} - Search in progress, no results yet; wait before next iteration.</li>
     *   <li>{@code OK} - Results are available and can be processed.</li>
     *   <li>{@code FINISH} or {@code TIMEOUT} - Search has ended; exit the loop.</li>
     * </ul>
     * <p>
     * If the session is opened for a user, the {@code loginName} parameter is ignored.
     * It is mandatory only when the session is opened by an administrator.
     * 
     * @param loginName the user login name
     * @return a {@link SearchResult} object if successful; {@code null} otherwise
     */
    SearchResult getResults(String loginName);

    /**
     * Retrieves the next available results for the current search for the user
     * who has opened the session.
     * <p>
     * This method will return {@code null} if invoked from a session opened by an administrator.
     * <p>
     * Typical usage is in a loop checking {@link SearchResult#getResultCode()} as explained in the class-level Javadoc.
     * 
     * @return a {@link SearchResult} object if successful; {@code null} otherwise
     */
    SearchResult getResults();
}
