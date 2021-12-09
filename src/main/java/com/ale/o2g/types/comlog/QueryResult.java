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
 * {@code QueryResult} class represents the result of a communication log query.
 * The {@linkplain com.ale.o2g.CommunicationLogService CommunicationLogService}
 * service provides a paging mechanism that allows the application to query the
 * result by pages.
 */
public class QueryResult implements Iterable<ComRecord> {

    private Collection<ComRecord> records;
    private Page page;
    private Integer count;

    /**
     * Returns the number of record in this result.
     * 
     * @return the number of record.
     */
    public Integer size() {
        return count;
    }

    /**
     * Returns the page associated to this result.
     * 
     * @return the page.
     */
    public Page getPage() {
        return page;
    }

    @Override
    public Iterator<ComRecord> iterator() {
        return records.iterator();
    }

    protected QueryResult(Collection<ComRecord> records, Page page, Integer count) {
        this.records = records;
        this.page = page;
        this.count = count;
    }
}
