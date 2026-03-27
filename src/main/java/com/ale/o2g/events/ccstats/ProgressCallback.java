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
 * Functional interface for receiving progress updates during an asynchronous or
 * long-running operation.
 * <p>
 * Implementations of this interface are typically provided as lambda expressions
 * or method references to receive updates about the current {@link ProgressStep}
 * and the completion percentage.
 * <p>
 * The progress callback may be invoked multiple times throughout the operation,
 * as progress advances through different steps or stages.
 *
 * <p><b>Example usage:</b></p>
 * <pre><code>
 * ProgressCallback callback = (step, percent) -&gt;
 *     System.out.println(step + " - " + percent + "% completed");
 * </code></pre>
 *
 * @see ProgressStep
 * @see com.ale.o2g.CallCenterStatisticsService
 */
@FunctionalInterface
public interface ProgressCallback {

    /**
     * Invoked to report the current progress or status of an operation.
     *
     * @param step the current logical step or phase of the operation
     * @param progress the completion percentage of the current step or the overall operation,
     *                 ranging from {@code 0} to {@code 100}
     */
    void onProgress(ProgressStep step, int progress);
}
