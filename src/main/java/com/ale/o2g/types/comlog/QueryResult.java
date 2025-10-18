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

import java.util.Collection;
import java.util.Iterator;

/**
 * {@code QueryResult} represents the result of a communication log query.
 * <p>
 * The {@link com.ale.o2g.CommunicationLogService CommunicationLogService} provides
 * a paging mechanism, allowing applications to retrieve query results page by page.
 * <p>
 * Each instance contains the collection of communication records returned for
 * a specific query, along with the paging information and the total count of records.
 *
 * @see com.ale.o2g.CommunicationLogService#getComRecords(QueryFilter, Page, boolean, String)
 */
public class QueryResult implements Iterable<ComRecord> {

    private Collection<ComRecord> records;
    private Page page;
    private Integer count;

    /**
     * Returns the total number of records in this result.
     *
     * @return the number of records
     */
    public Integer size() {
        return count;
    }

    /**
     * Returns the page information associated with this result.
     * <p>
     * The {@link Page} object provides the offset and limit used to retrieve
     * this set of records and can be used to navigate through the pages.
     *
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * Returns an iterator over the communication records contained in this result.
     *
     * @return an {@link Iterator} over {@link ComRecord} instances
     */
    @Override
    public Iterator<ComRecord> iterator() {
        return records.iterator();
    }

    /**
     * Protected constructor for creating a {@code QueryResult}.
     * <p>
     * Instances are typically created by the {@link com.ale.o2g.CommunicationLogService}
     * and returned to the caller.
     *
     * @param records the collection of communication records
     * @param page the page information
     * @param count the total number of records
     */
    protected QueryResult(Collection<ComRecord> records, Page page, Integer count) {
        this.records = records;
        this.page = page;
        this.count = count;
    }
}
