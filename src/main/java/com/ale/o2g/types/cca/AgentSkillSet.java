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
import java.util.Map;
import java.util.Set;

/**
 * {@code AgentSkillSet} represents the collection of skills assigned to a CCD operator.
 * <p>
 * This class provides convenient access to individual skills by their identifier,
 * checks for existence, and exposes all available skills or their identifiers.
 */
public class AgentSkillSet {

    private Map<Integer, AgentSkill> skills;

    /**
     * Returns the skill with the specified number.
     *
     * @param number the skill number (identifier)
     * @return the {@link AgentSkill} with the given number, or {@code null} if no such skill exists
     */
    public AgentSkill get(int number) {
        return skills.get(number);
    }

    /**
     * Determines whether a skill with the specified number exists in this set.
     *
     * @param number the skill number to search for
     * @return {@code true} if the specified skill is present; {@code false} otherwise
     */
    public boolean contains(int number) {
        return skills.containsKey(number);
    }
        
    /**
     * Returns the set of skill numbers contained in this skill set.
     *
     * @return a set of skill identifiers
     */
    public Set<Integer> getSkillNumbers() {
        return skills.keySet();
    }
    
    /**
     * Returns all skills contained in this skill set.
     *
     * @return a collection of {@link AgentSkill} instances
     */
    public Collection<AgentSkill> getSkills() {
        return skills.values();
    }

    /**
     * Creates an {@code AgentSkillSet} with the given skills.
     * <p>
     * This constructor is intended for internal or deserialization use only.
     *
     * @param skills the map of skill numbers to {@link AgentSkill} instances
     */
    protected AgentSkillSet(Map<Integer, AgentSkill> skills) {
        this.skills = skills;
    }
    
}
