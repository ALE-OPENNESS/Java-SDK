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
package com.ale.o2g.types.cca;

import java.util.Collection;

import com.google.gson.annotations.SerializedName;

/**
 * {@code AgentGroups} represents the group configuration of a CCD operator.
 * <p>
 * It defines the list of processing groups the operator is attached to,
 * as well as the preferred group.
 */
public final class AgentGroups {

    private String preferred;

    @SerializedName(value = "processingGroups")
    private Collection<String> groups;

    /**
     * Returns the preferred processing group of the operator.
     *
     * @return the preferred group identifier (directory number), or {@code null}
     *         if none is defined
     */
    public final String getPreferred() {
        return preferred;
    }

    /**
     * Returns the collection of processing groups the operator is attached to.
     *
     * @return a collection of group identifiers (directory numbers);
     *         may be empty but never {@code null}
     */
    public final Collection<String> getGroups() {
        return groups;
    }

    protected AgentGroups() {
    }
    
}
