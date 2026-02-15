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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@code AgentSkillSet} represents the collection of skillsByNumber assigned to
 * a CCD operator.
 * <p>
 * This class provides convenient access to individual skillsByNumber by their
 * identifier, checks for existence, and exposes all available skillsByNumber or
 * their identifiers.
 */
public class AgentSkillSet {

    private final Map<Integer, AgentSkill> skillsByNumber;
    private final Map<SkillKey, AgentSkill> skillsByDomainAndName;

    /**
     * Constructs an {@code AgentSkillSet} from a map of skills.
     * <p>
     * The map keys are skill numbers. Internally, a secondary index by (domain,
     * name) is also built for lookups by name.
     * </p>
     *
     * @param skills map of skill numbers to {@link AgentSkill} instances
     */
    protected AgentSkillSet(Map<Integer, AgentSkill> skills) {

        this.skillsByNumber = skills;

        this.skillsByDomainAndName = new HashMap<SkillKey, AgentSkill>();
        skills.values().forEach(skill -> {
            Integer domain = skill.getDomain(); // assume domain can be null
            String name = skill.getName(); // name can be null
            if (domain != null && name != null) {
                skillsByDomainAndName.put(new SkillKey(domain, name), skill);
            }
        });
    }

    /**
     * Returns the skill with the specified number.
     *
     * @param number the skill number (identifier)
     * @return the {@link AgentSkill} with the given number, or {@code null} if no
     *         such skill exists
     */
    public AgentSkill get(int number) {
        return skillsByNumber.get(number);
    }

    /**
     * Returns the skill with the specified name in the given domain.
     *
     * @param domain the domain ID
     * @param name   the skill name
     * @return the {@link AgentSkill} with the given name in the domain, or
     *         {@code null} if not found
     * @since 2.7.4
     */
    public AgentSkill get(int domain, String name) {
        return skillsByDomainAndName.get(new SkillKey(domain, name));
    }

    /**
     * Determines whether a skill with the specified number exists in this set.
     *
     * @param number the skill number to search for
     * @return {@code true} if the specified skill is present; {@code false}
     *         otherwise
     */
    public boolean contains(int number) {
        return skillsByNumber.containsKey(number);
    }

    /**
     * Determines whether a skill with the specified name exists in the given
     * domain.
     *
     * @param domain the domain ID
     * @param name   the skill name
     * @return {@code true} if the skill exists in the domain, {@code false}
     *         otherwise
     * @since 2.7.4
     */
    public boolean contains(int domain, String name) {
        return skillsByDomainAndName.containsKey(new SkillKey(domain, name));
    }

    /**
     * Returns the set of skill numbers contained in this skill set.
     *
     * @return a set of skill identifiers
     */
    public Set<Integer> getSkillNumbers() {
        return Collections.unmodifiableSet(skillsByNumber.keySet());
    }

    /**
     * Returns all skillsByNumber contained in this skill set.
     *
     * @return a collection of {@link AgentSkill} instances
     */
    public Collection<AgentSkill> getSkills() {
        return Collections.unmodifiableCollection(skillsByNumber.values());
    }

    /** Internal key for mapping skillsByNumber by (domain, name). */
    private static final class SkillKey {
        private final int domain;
        private final String name;

        private SkillKey(int domain, String name) {
            this.domain = domain;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof SkillKey other))
                return false;
            return domain == other.domain && name.equals(other.name);
        }

        @Override
        public int hashCode() {
            return 31 * domain + name.hashCode();
        }
    }
}
