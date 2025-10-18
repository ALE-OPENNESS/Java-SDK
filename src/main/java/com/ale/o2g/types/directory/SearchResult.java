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
package com.ale.o2g.types.directory;

import java.util.Collection;
import java.util.Collections;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;
import com.ale.o2g.types.common.PartyInfo;

/**
 * {@code SearchResult} represents the outcome of a directory search performed
 * via {@link com.ale.o2g.DirectoryService}.
 * <p>
 * A search may return multiple batches of results, and each batch contains a
 * set of contacts along with a status code indicating the state of the search.
 */
public class SearchResult {

    /**
     * {@code Code} represents the status of a directory search. Each time
     * {@linkplain com.ale.o2g.DirectoryService#getResults() DirectoryService.getResults()}
     * is called, the returned code should be checked to determine the next action.
     */
    @JsonEnumDeserializerFallback(value = "TIMEOUT")
    public static enum Code {

        /**
         * Results are available for this batch. Further calls to
         * {@link com.ale.o2g.DirectoryService#getResults()} may retrieve more
         * results.
         */
        OK,

        /**
         * No results were received in this batch. Further calls to
         * {@link com.ale.o2g.DirectoryService#getResults()} may retrieve more
         * results.
         */
        NOK,

        /**
         * The search has completed and no further results will be returned.
         */
        FINISH,

        /**
         * The search ended due to a timeout.
         */
        TIMEOUT
    }

    /**
     * {@code Results} represents a single batch of contacts returned from a
     * directory search.
     */
    public static class Results {
        private Collection<PartyInfo> contacts;

        /**
         * Returns the contacts found in this batch.
         *
         * @return an unmodifiable collection of {@link PartyInfo} contacts
         */
        public final Collection<PartyInfo> getContacts() {
            return contacts;
        }

        protected Results() {
        }
    }

    private Code resultCode;
    private Collection<Results> resultElements;

    /**
     * Returns the status code of this search result.
     *
     * @return the {@link Code} of the search result, indicating whether more
     *         results are available or if the search is complete.
     */
    public final Code getResultCode() {
        return resultCode;
    }

    /**
     * Returns the list of result batches from the search.
     * <p>
     * Each element in the collection corresponds to a {@link Results} object,
     * which contains a collection of contacts.
     *
     * @return an unmodifiable collection of {@link Results} objects.
     */
    public final Collection<Results> getResultElements() {
        return Collections.unmodifiableCollection(resultElements);
    }

    protected SearchResult() {
    }
}
