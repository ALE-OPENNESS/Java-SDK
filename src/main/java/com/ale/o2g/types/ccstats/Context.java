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
package com.ale.o2g.types.ccstats;

/**
 * {@code Context} represents the configuration needed to retrieve a statistics report
 * from the Call Center Statistics Service.
 * <p>
 * A context defines the parameters and metadata for generating a report, including:
 * <ul>
 *   <li>The objects to include in the report (e.g., CCD agents, CCD pilots).</li>
 *   <li>The counters or metrics for each category of object.</li>
 *   <li>Metadata such as label, description, requester, and scheduling information.</li>
 * </ul>
 * <p>
 * Contexts are used to specify what data should be included in a report, how it should
 * be filtered, and how the report should be generated or scheduled.
 *
 * @since 2.7.4
 */
public interface Context {

    /**
     * Returns the unique identifier of this context.
     *
     * @return the context identifier
     */
    String getId();

    /**
     * Returns the identifier of the requester who owns this context.
     *
     * @return the requester identifier
     */
    String getRequesterId();

    /**
     * Returns the human-readable label of this context.
     *
     * @return the context label
     */
    String getLabel();

    /**
     * Returns the description of this context.
     *
     * @return the context description
     */
    String getDescription();

    /**
     * Indicates whether this context is associated with a scheduled report.
     *
     * @return {@code true} if the context is scheduled; {@code false} otherwise
     */
    boolean isScheduled();

    /**
     * Returns the filter associated with this context.
     * <p>
     * The filter specifies which objects (agents or pilots) and counters should
     * be included in the report.
     *
     * @return the associated filter
     */
    Filter getFilter();

    /**
     * Sets the filter associated with this context.
     *
     * @param filter the filter to set
     */
    void setFilter(Filter filter);

    /**
     * Sets the description of this context.
     *
     * @param description the description to set
     */
    void setDescription(String description);

    /**
     * Sets the label of this context.
     *
     * @param label the label to set
     */
    void setLabel(String label);
}
