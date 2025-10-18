/*
* Copyright 2024 ALE International
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
package com.ale.o2g.internal.types.ccm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ale.o2g.types.ccm.Pilot;
import com.ale.o2g.types.ccm.PilotRule;
import com.ale.o2g.types.ccm.PilotRuleSet;
import com.ale.o2g.types.common.ServiceState;

/**
 *
 */
public class O2GPilot {
    
    private static class PilotRules {
        private Collection<O2GPilotRule> ruleList;
    }
    
    private String number;
    private String name;
    private ServiceState state;
    private int waitingTime;
    private boolean saturation;
    private PilotRules rules;
    private boolean possibleTransfer;
    private boolean supervisedTransfer;
    
    public Pilot toPilot() {
        
        Map<Integer, PilotRule> mapRules = new HashMap<Integer, PilotRule>();
        if (rules.ruleList != null) {
            rules.ruleList.forEach(r -> mapRules.put(r.getNumber(), r.toPilotRule()));
        }

        return new Pilot(
                number,
                name,
                state,
                waitingTime,
                saturation,
                new PilotRuleSet(mapRules) {},
                possibleTransfer,
                supervisedTransfer) {};
    }
}
