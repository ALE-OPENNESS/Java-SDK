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
package com.ale.o2g.types.ccm;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * {@code PilotRuleSet} class represents a set of rule for a CCD pilot.
 */
public class PilotRuleSet {

    private Map<Integer, PilotRule> rules;

    /**
     * Returns the rule with the specified number.
     * 
     * @param number The rule number
     * @return The pilot rule with the specified number or {@code null} if there is
     *         no rule with such number.
     */
    public PilotRule get(int number) {
        return rules.get(number);
    }

    /**
     * Determines whether the the specified rule exist in this rule set.
     * @param number the rule number to search
     * @return {@code true} if the specified rule is present; {@code false} otherwise. 
     */
    public boolean contains(int number) {
        return rules.containsKey(number);
    }
        
    /**
     * Return the set of rules numbers contained in this rule set.
     * @return A set object containing the rule number.
     */
    public Set<Integer> getRulesNumbers() {
        return rules.keySet();
    }
    
    /**
     * Returns the rules in this rule set.
     * @return A collection of PilotRule.
     */
    public Collection<PilotRule> getSkills() {
        return rules.values();
    }

    
    protected PilotRuleSet(Map<Integer, PilotRule> skills) {
        this.rules = skills;
    }
    
}
