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
 * The {@code DirectoryService} is used to search contacts in the OmniPCX
 * Enterprise phone book. Using this service requires having a
 * <b>TELEPHONY_ADVANCED</b> license.
 * <p>
 * A directory search is a set of 2 or more sequential operations:
 * <ol>
 * <li>The first operation initiate the search with a set of criteria</li>
 * <li>The second and next operations retrieve results</li>
 * </ol>
 * <p>
 * Note: For each session (user or administrator), only 5 concurrent searches
 * are authorized. An unused search context is freed after 1 minute.
 * 
 * <p>search example:
 * <pre>{@code
 *   DirectoryService directoryService = session.getDirectoryService();
 *	
 *   directoryService.search(myCriteria);
 *   while (!finished) {
 *		
 *      SearchResult result = directoryService.getResults();
 *      if (result.getResultCode() == SearchResult.ResultCode.NOK) {
 *         // Wait the results
 *      }
 *      else if ((result.getResultCode() == SearchResult.ResultCode.FINISH) ||
 *               (result.getResultCode() == SearchResult.ResultCode.TIMEOUT)) {
 *          // Exit the loop
 *          finished = true;
 *      }
 *      else {
 *         // Process results
 *      }
 *   }
 *}</pre>
 */
public interface DirectoryService extends IService {

	/**
	 * Initiates a search for the specified user with the specified filter. The
	 * search will be limited to the specified number of results.
	 * <p>
	 * If the session has been opened for a user, the {@code loginName} parameter is
	 * ignored, but it is mandatory if the session has been opened by an
	 * administrator.
	 * 
	 * @param filter    the search filter
	 * @param limit     maximum number of results. The range of supported values is
	 *                  [1 .. 100]
	 * @param loginName the user login name
	 * @return {@code true} in case of success; {@code false} otherwise.
	 */
	boolean search(Criteria filter, int limit, String loginName);

	/**
	 * Initiates a search for the user who has opened the session with the specified
	 * filter. The search will be limited to the specified number of results.
	 * <p>
	 * This method will fail and return {@code false} if it is invoked from a
	 * session opened by an administrator.
	 * 
	 * @param filter the search filter
	 * @param limit  maximum number of results. The range of supported values is [1
	 *               .. 100]
	 * @return {@code true} in case of success; {@code false} otherwise.
	 * @see #search(Criteria, int, String)
	 */
	boolean search(Criteria filter, int limit);

	/**
	 * Initiates a search for the user who has opened the session with the specified
	 * filter. The search is limited to 100.
	 * <p>
	 * This method will fail and return {@code false} if it is invoked from a
	 * session opened by an administrator.
	 * 
	 * @param filter the search filter
	 * @return {@code true} in case of success; {@code false} otherwise.
	 * @see #search(Criteria, int, String)
	 */
	boolean search(Criteria filter);

	/**
	 * Cancel a search query for the specified user.
	 * <p>
	 * If the session has been opened for a user, the {@code loginName} parameter is
	 * ignored, but it is mandatory if the session has been opened by an
	 * administrator.
	 * 
	 * @param loginName the user login name
	 * @return {@code true} in case of success; {@code false} otherwise.
	 */
	boolean cancel(String loginName);

	/**
	 * Cancel a search query for the user who has opened the session.
	 * <p>
	 * This method will fail and return {@code false} if it is invoked from a
	 * session opened by an administrator.
	 * 
	 * @return {@code true} in case of success; {@code false} otherwise.
	 */
	boolean cancel();

	/**
	 * Gets the next available results for the current search.
	 * {@code #getResults(String) getResults} is generally called in a loop and for
	 * each iteration
	 * <ul>
	 * <li>if the status result code is {@code NOK}, the search is in progress but no result is
	 * available : it is recommended to wait before the next iteration (500ms for
	 * example)</li>
	 * <li>if the status result code is {@code OK}, results are available and can be processed</li>
	 * <li>if the status result code is {@code FINISH} or {@code TIMEOUT}, search is ended, exit from the
	 * loop</li>
	 * </ul>
	 * <p>
	 * If the session has been opened for a user, the {@code loginName} parameter is
	 * ignored, but it is mandatory if the session has been opened by an
	 * administrator.
	 * 
	 * @param loginName the user login name
	 * @return A {@link SearchResult SearchResult} object in case of success;
	 *         {@code null} otherwise.
	 */
	SearchResult getResults(String loginName);

	/**
	 * Gets the next available results for the current search.
	 * {@code #getResults(String) getResults} is generally called in a loop and for
	 * each iteration
	 * <ul>
	 * <li>if the status is {@code NOK}, the search is in progress but no result is
	 * available : it is recommended to wait before the next iteration (500ms for
	 * example)</li>
	 * <li>if the status result code is {@code OK}, you can process the results</li>
	 * <li>if the status result code is {@code FINISH} or {@code TIMEOUT}, exit the
	 * loop</li>
	 * </ul>
	 * <p>
	 * This method will fail and return {@code null} if it is invoked from a
	 * session opened by an administrator.
	 * 
	 * @return A {@link SearchResult SearchResult} object in case of success;
	 *         {@code null} otherwise.
	 */
	SearchResult getResults();
}

