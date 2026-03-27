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
 * Represents a collection of rules associated with a CCD pilot.
 * <p>
 * Each {@link PilotRule} in this set is uniquely identified by its numeric identifier
 * (rule number). A {@code PilotRuleSet} provides methods to:
 * <ul>
 *     <li>Retrieve individual rules by number</li>
 *     <li>Check whether a rule exists</li>
 *     <li>Access all rules or all rule numbers</li>
 *     <li>Query the size or emptiness of the set</li>
 * </ul>
 * <p>
 * This class is typically used internally when creating {@link Pilot} objects,
 * providing a structured and immutable view of all rules governing call
 * distribution for the pilot.
 * <p>
 * The rule numbers serve as keys for fast lookup and ensure that each rule is
 * unique within the set.
 */
public class PilotRuleSet {

    private Map<Integer, PilotRule> rules;

    /**
     * Constructs a new {@code PilotRuleSet} with the specified rules.
     * <p>
     * Typically, this constructor is used internally by the system when
     * creating {@code Pilot} objects from deserialized data.
     *
     * @param rules a {@link Map} mapping rule numbers to {@link PilotRule} objects
     */
    protected PilotRuleSet(Map<Integer, PilotRule> rules) {
        this.rules = rules;
    }

    /**
     * Returns the rule with the specified number.
     *
     * @param number the unique identifier of the rule
     * @return the {@link PilotRule} with the given number, or {@code null} if no such rule exists
     */
    public PilotRule get(int number) {
        return rules.get(number);
    }

    /**
     * Checks whether a rule with the specified number exists in this set.
     *
     * @param number the rule number to check
     * @return {@code true} if the rule is present; {@code false} otherwise
     */
    public boolean contains(int number) {
        return rules.containsKey(number);
    }

    /**
     * Returns {@code true} if this rule set contains no rules.
     *
     * @return {@code true} if the set is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return rules.isEmpty();
    }

    /**
     * Returns the number of rules in this set.
     *
     * @return the size of the rule set
     */
    public int size() {
        return rules.size();
    }

    /**
     * Returns a {@link Set} of all rule numbers in this set.
     *
     * @return a set containing the unique numbers of all {@link PilotRule} objects
     */
    public Set<Integer> getRulesNumbers() {
        return rules.keySet();
    }

    /**
     * Returns a {@link Collection} of all rules in this set.
     *
     * @return a collection containing all {@link PilotRule} objects
     */
    public Collection<PilotRule> getRules() {
        return rules.values();
    }
}