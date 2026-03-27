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
package com.ale.o2g.internal.types.ccstats;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.ale.o2g.types.ccstats.PilotAbandonedCallsAttributes;
import com.ale.o2g.types.ccstats.PilotAbandonedCallsFilter;

/**
 *
 */
public class PilotAbandonedCallsFilterImpl extends AbstractFilter implements PilotAbandonedCallsFilter {

    private Set<PilotAbandonedCallsAttributes> attributes = new HashSet<PilotAbandonedCallsAttributes>();
    
    @Override
    public final Set<PilotAbandonedCallsAttributes> getPilotAbandonedCallsAttributes() {
        return attributes;
    }    

    @Override
    public void setPilotAbandonedCallsAttributes(PilotAbandonedCallsAttributes... attributes) {
        Collections.addAll(this.attributes, attributes);
    }
}