/*
* Copyright 2026 ALE International
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

public class PilotRuleSetTest extends AbstractJsonTest {

    @Test
    void testRuleSetBasicOperations() {
        // Create sample PilotRule objects
        PilotRule rule1 = new PilotRule(1, "Rule-One", true);
        PilotRule rule2 = new PilotRule(2, "Rule-Two", false);

        // Build a map of rules
        Map<Integer, PilotRule> ruleMap = new HashMap<>();
        ruleMap.put(rule1.getNumber(), rule1);
        ruleMap.put(rule2.getNumber(), rule2);

        // Create the PilotRuleSet
        PilotRuleSet ruleSet = new PilotRuleSet(ruleMap);

        // Test get(int number)
        assertEquals(rule1, ruleSet.get(1));
        assertEquals(rule2, ruleSet.get(2));
        assertNull(ruleSet.get(999)); // non-existing rule

        // Test contains(int number)
        assertTrue(ruleSet.contains(1));
        assertTrue(ruleSet.contains(2));
        assertFalse(ruleSet.contains(999));

        // Test size() and isEmpty()
        assertEquals(2, ruleSet.size());
        assertFalse(ruleSet.isEmpty());

        // Test getRulesNumbers()
        Set<Integer> ruleNumbers = ruleSet.getRulesNumbers();
        assertEquals(2, ruleNumbers.size());
        assertTrue(ruleNumbers.contains(1));
        assertTrue(ruleNumbers.contains(2));

        // Test getRules()
        Collection<PilotRule> rules = ruleSet.getRules();
        assertEquals(2, rules.size());
        assertTrue(rules.contains(rule1));
        assertTrue(rules.contains(rule2));
    }

    @Test
    void testEmptyRuleSet() {
        Map<Integer, PilotRule> emptyMap = new HashMap<>();
        PilotRuleSet emptySet = new PilotRuleSet(emptyMap);

        assertEquals(0, emptySet.size());
        assertTrue(emptySet.isEmpty());
        assertFalse(emptySet.contains(1));
        assertNull(emptySet.get(1));
        assertTrue(emptySet.getRulesNumbers().isEmpty());
        assertTrue(emptySet.getRules().isEmpty());
    }
}