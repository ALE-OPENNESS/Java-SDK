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
package com.ale.o2g.events.ccstats;

/**
 * Represents the different steps in the processing of CCD statistics.
 * <p>
 * A {@code ProgressStep} indicates the current state of the data lifecycle,
 * from collection to processing and formatting, including possible error or 
 * cancellation states. It is typically used with {@link ProgressCallback} 
 * to report the progress of asynchronous or long-running operations.
 */
public enum ProgressStep {
    
    /**
     * Data is currently being collected by the AFE (Application Front End).
     */
    COLLECTING, 
    
    /**
     * Data collection has been successfully completed and the data has been processed.
     */
    PROCESSED,
    
    /**
     * Processed data has been successfully formatted for reporting or export.
     */
    FORMATED,
}
