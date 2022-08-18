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
 * {@code SearchResult} represents the result of a directory search.
 */
public class SearchResult {

    /**
     * {@code Code} represents the status of a directory search. Each time a call to
     * {@linkplain com.ale.o2g.DirectoryService#getResults()
     * DirectoryService.getResults} is done, the returned result code must be
     * tested.
     */
    @JsonEnumDeserializerFallback(value = "TIMEOUT")
    public static enum Code {

        /**
         * Responses are provided this time. Continue to invoking
         * {@linkplain com.ale.o2g.DirectoryService#getResults()
         * DirectoryService.getResults} periodically to get the next results.
         */
        OK,

        /**
         * No responses receive. Continue to invoking
         * {@linkplain com.ale.o2g.DirectoryService#getResults()
         * DirectoryService.getResults} to get more results.
         */
        NOK,

        /**
         * Search is finished. 
         */
        FINISH,

        /**
         * Search is ended for timeout reason.
         */
        TIMEOUT
    }

    /**
     * {@code Results} represents a set of result from a directory search.
     */
    public static class Results {
        private Collection<PartyInfo> contacts;

        /**
         * Returns the contacts found.
         * @return the contacts.
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
     * Gets the result code of this search result.
     * 
     * @return the {@link Code} of the search result.
     */
    public final Code getResultCode() {
        return resultCode;
    }

    /**
     * Returns the results.
     * @return the resultElements
     */
    public final Collection<Results> getResultElements() {
        return Collections.unmodifiableCollection(resultElements);
    }

    protected SearchResult() {
    }
}
