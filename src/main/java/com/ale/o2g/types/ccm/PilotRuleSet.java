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
 * {@code PilotRuleSet} represents a collection of rules for a CCD pilot.
 * <p>
 * Each rule in the set is uniquely identified by its number. This class provides
 * methods to access individual rules, check for their existence, and retrieve
 * the full set of rules or their identifiers.
 */
public class PilotRuleSet {

    private Map<Integer, PilotRule> rules;

    /**
     * Returns the rule with the specified number.
     * 
     * @param number the rule number
     * @return the {@link PilotRule} with the specified number, or {@code null} if
     *         no such rule exists
     */
    public PilotRule get(int number) {
        return rules.get(number);
    }

    /**
     * Determines whether the rule with the specified number exists in this set.
     * 
     * @param number the rule number to check
     * @return {@code true} if the rule is present; {@code false} otherwise
     */
    public boolean contains(int number) {
        return rules.containsKey(number);
    }
        
    /**
     * Returns the set of rule numbers contained in this rule set.
     * 
     * @return a {@link Set} of integers representing the rule numbers
     */
    public Set<Integer> getRulesNumbers() {
        return rules.keySet();
    }
    
    /**
     * Returns all rules in this set.
     * 
     * @return a {@link Collection} of {@link PilotRule} objects
     */
    public Collection<PilotRule> getRules() {
        return rules.values();
    }

    /**
     * Constructs a new {@code PilotRuleSet} with the specified rules.
     * 
     * @param rules a {@link Map} of rule numbers to {@link PilotRule} objects
     */
    protected PilotRuleSet(Map<Integer, PilotRule> rules) {
        this.rules = rules;
    }
    
}
