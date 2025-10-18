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

import com.ale.o2g.types.ccstats.AgentFilter;
import com.ale.o2g.types.ccstats.Context;
import com.ale.o2g.types.ccstats.Filter;
import com.ale.o2g.types.ccstats.PilotFilter;

/**
 */
public class ContextImpl implements Context {
    
    private static record StatsFilter(AgentFilterImpl agentFilter, PilotFilterImpl pilotFilter) {}
    
    private String ctxId;
    private String supervisorId;
    private String label;
    private String description;
    private boolean isScheduled;
    private StatsFilter filter;
    
    public ContextImpl(String ctxId, String supervisorId) {
        this.ctxId = ctxId;
        this.supervisorId = supervisorId;
    }

    @Override
    public Filter getFilter() {
        
        if (filter.agentFilter != null) {
            return filter.agentFilter;
        }
        else {
            return filter.pilotFilter;
        }
    }

    @Override
    public void setFilter(Filter filter) {
        if (filter instanceof AgentFilter) {
            this.filter = new StatsFilter((AgentFilterImpl)filter, null);
        }
        else if (filter instanceof PilotFilter) {
            this.filter = new StatsFilter(null, (PilotFilterImpl)filter);   
        }
        else {
            throw new IllegalArgumentException("Invalid filter type");
        }
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }


    @Override
    public String getId() {
        return this.ctxId;
    }


    @Override
    public String getLabel() {
        return this.label;
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public boolean isScheduled() {
        return this.isScheduled;
    }

    @Override
    public String getRequesterId() {
        return this.supervisorId;
    }
}
