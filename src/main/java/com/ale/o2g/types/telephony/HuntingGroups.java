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
package com.ale.o2g.types.telephony;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents the hunting group information for a user.
 * <p>
 * A hunting group is a set of extensions where calls are distributed. A user can be a member 
 * of at most one hunting group at a time.
 * </p>
 */
public class HuntingGroups {
    private Collection<String> hgList;
    private String currentHg;

    /**
     * Returns the list of all existing hunting groups in the system.
     * <p>
     * If no hunting groups are defined, an empty unmodifiable collection is returned.
     * </p>
     *
     * @return an unmodifiable {@link Collection} of hunting group extension numbers, never {@code null}
     */
    public final Collection<String> getHuntingGroups() {
        return (hgList == null) ? Collections.emptyList() : Collections.unmodifiableCollection(hgList);
    }

    /**
     * Returns the hunting group of which the user is currently a member.
     *
     * @return the extension number of the user's hunting group, or {@code null} if the user
     *         is not a member of any hunting group
     */
    public final String getCurrentHuntingGroup() {
        return currentHg;
    }

    /**
     * Protected constructor to prevent direct instantiation.
     * <p>
     * Instances are typically created internally by the system to represent
     * a user's hunting group configuration.
     * </p>
     */
    protected HuntingGroups() {
    }

}