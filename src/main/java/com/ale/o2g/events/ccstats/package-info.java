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


/**
 * Provides classes and interfaces for reporting the progress of CCD statistics 
 * processing in asynchronous or long-running operations.
 * <p>
 * This package contains:
 * <ul>
 *   <li>{@link com.ale.o2g.events.ccstats.ProgressCallback ProgressCallback}: a functional interface 
 *       for receiving progress updates during statistics processing.</li>
 *   <li>{@link com.ale.o2g.events.ccstats.ProgressStep ProgressStep}: an enum representing the 
 *       different stages of the statistics lifecycle, such as data collection, 
 *       processing, and formatting.</li>
 * </ul>
 * <p>
 * These components are intended to be used together to provide real-time feedback 
 * about ongoing operations when generating statistical reports. Implementations 
 * of {@link com.ale.o2g.events.ccstats.ProgressCallback ProgressCallback} can be passed to asynchronous data retrieval methods 
 * (e.g., {@code getFileData(...)}) to track progress for UI updates, logging, or 
 * monitoring.
 * <p>
 * <b>Example usage:</b>
 * <pre><code>
 * ProgressCallback callback = (step, percent) -&gt; 
 *     System.out.println(step + " - " + percent + "% completed");
 * </code></pre>
 * 
 * @since 2.7.4
 */
package com.ale.o2g.events.ccstats;
