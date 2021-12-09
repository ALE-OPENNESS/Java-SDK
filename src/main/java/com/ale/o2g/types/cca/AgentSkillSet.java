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
 * {@code AgentSkillSet} class represents a set of skills for a CCD operator.
 */
public class AgentSkillSet {

    private Map<Integer, AgentSkill> skills;

    /**
     * Returns the skill with the specified number.
     * 
     * @param number The skill number
     * @return The agent skill with the specified number or {@code null} if there is
     *         no skill with such number.
     */
    public AgentSkill get(int number) {
        return skills.get(number);
    }

    /**
     * Determines whether the the specified skill exist in this skill set.
     * @param number the skill number to search
     * @return {@code true} if the specified skill is present; {@code false} otherwise. 
     */
    public boolean contains(int number) {
        return skills.containsKey(number);
    }
        
    /**
     * Return the set of skill numbers contained in this skills set.
     * @return A set object containing the skills number.
     */
    public Set<Integer> getSkillNumbers() {
        return skills.keySet();
    }
    
    /**
     * Returns the skills in this skill set.
     * @return A collection of AgentSkill.
     */
    public Collection<AgentSkill> getSkills() {
        return skills.values();
    }

    
    protected AgentSkillSet(Map<Integer, AgentSkill> skills) {
        this.skills = skills;
    }
    
}
