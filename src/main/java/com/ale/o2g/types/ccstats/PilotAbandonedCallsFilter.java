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

import java.util.Set;

/**
 * Filter for selecting pilots in abandoned call statistics reports.
 * <p>
 * A {@code PilotAbandonedCallsFilter} specifies which pilots to include and which
 * abandoned call attributes to collect when generating statistics.
 * <p>
 * Instances should be created via {@link Filter#createPilotAbandonedCallsFilter}.
 *
 * @see Filter#createPilotAbandonedCallsFilter
 * @see PilotAbandonedCallsAttributes
 * @since 2.7.5
 */
public interface PilotAbandonedCallsFilter extends Filter {

    /**
     * Returns the set of pilot directory numbers associated with this filter.
     * <p>
     * These numbers identify the pilots whose statistics should be collected.
     *
     * @return a {@link Set} of pilot directory numbers; never {@code null},
     *         but may be empty if no numbers have been added
     */
    Set<String> getNumbers();

    /**
     * Adds one or more pilot directory numbers to this filter.
     * <p>
     * Once added, the corresponding pilots will be included in the scope
     * of the abandoned call statistics report.
     *
     * @param numbers the directory numbers to add; must not be {@code null}
     */
    void addNumbers(String[] numbers);

    /**
     * Returns the abandoned call attributes to collect for the pilots in this filter.
     *
     * @return the {@link Set} of {@link PilotAbandonedCallsAttributes} to include
     */
    Set<PilotAbandonedCallsAttributes> getPilotAbandonedCallsAttributes();

    /**
     * Sets the abandoned call attributes to collect for the pilots in this filter.
     * <p>
     * Replaces any previously configured attributes.
     *
     * @param attributes the {@link PilotAbandonedCallsAttributes} to include
     */
    void setPilotAbandonedCallsAttributes(PilotAbandonedCallsAttributes... attributes);
}
