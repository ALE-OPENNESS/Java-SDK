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

/**
 * The {@code Page} class represents a paging mechanism for querying communication logs.
 * <p>
 * It defines a starting offset and a maximum number of records per page (length). 
 * The class provides methods to navigate forward and backward through pages.
 * 
 * <p>Example usage:
 * <pre>{@code 
 * Page page = new Page(0, 50); // first page, 50 records per page
 * page.next();                 // move to the next page
 * page.previous();             // move back to the previous page
 * int offset = page.getOffset();
 * int length = page.getLength();
 * }</pre>
 * 
 */
public class Page {
    private int offset;
    private int limit;

    /**
     * Constructs a new page starting at the specified offset with the given length.
     * 
     * @param offset the index of the first record in the page (zero-based)
     * @param length the maximum number of records in the page
     */
    public Page(int offset, int length) {
        this.offset = offset;
        this.limit = length;
    }

    /**
     * Moves to the next page by increasing the offset by the page length.
     */
    public void next() {
        offset += limit;
    }

    /**
     * Moves to the previous page by decreasing the offset by the page length.
     * Ensures the offset does not become negative.
     */
    public void previous() {
        offset -= limit;
        if (offset < 0) {
            offset = 0;
        }
    }

    /**
     * Returns the offset of the first record in the current page.
     * 
     * @return the page offset (zero-based)
     */
    public final int getOffset() {
        return offset;
    }

    /**
     * Returns the maximum number of records in the current page.
     * 
     * @return the page length
     */
    public final int getLength() {
        return limit;
    }
}
