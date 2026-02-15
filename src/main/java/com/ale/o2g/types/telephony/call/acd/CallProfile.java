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
package com.ale.o2g.types.telephony.call.acd;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.annotations.SerializedName;

/**
 * {@code CallProfile} represents an immutable collection of skills required to
 * handle a call in a contact center.
 * <p>
 * This class provides convenient access to individual skills by their
 * identifier, allows checking for existence, and exposes all available skills
 * or their identifiers. Skills are used by the <em>Advanced Call Routing</em>
 * strategy to influence how calls are distributed among agents.
 * </p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * // Create a call profile in one line using varargs
 * CallProfile profile = new CallProfile(
 *     new CallProfile.Skill(101, 3, true),
 *     new CallProfile.Skill(102, 2, false),
 *     new CallProfile.Skill(103, 1, false)
 * );
 *
 * // Access a skill
 * CallProfile.Skill s = profile.get(101);
 * System.out.println("Skill " + s.getNumber() + " mandatory? " + s.isMandatory());
 *
 * // Check if a skill exists
 * boolean hasSkill = profile.contains(102);
 *
 * // Iterate over all skills
 * for (CallProfile.Skill skill : profile.getSkills()) {
 *     System.out.println(skill.getNumber() + ": level " + skill.getLevel());
 * }
 * }</pre>
 */
public class CallProfile {

    /**
     * {@code Skill} represents a skill assigned to a CallProfile.
     * <p>
     * Each skill has a unique number (identifier), a proficiency level, and
     * a flag indicating whether it is mandatory for advanced call routing.
     */
    public static final class Skill {

        @SerializedName("skillNumber")
        private final int number;
        
        @SerializedName("expertEvalLevel")
        private final int level;
        
        @SerializedName("acrStatus")
        private final boolean mandatory;

        /**
         * Returns the unique identifier of this skill.
         *
         * @return the skill number (identifier)
         */
        public final int getNumber() {
            return number;
        }

        /**
         * Returns the proficiency level of this skill.
         * <p>
         * A higher level typically indicates greater expertise or higher priority
         * when routing calls.
         *
         * @return the skill level
         */
        public final int getLevel() {
            return level;
        }

        /**
         * Indicates whether this skill is mandatory when the call profile is
         * evaluated by advanced call routing.
         *
         * @return {@code true} if the skill is mandatory; {@code false} otherwise
         */
        public final boolean isMandatory() {
            return mandatory;
        }

        /**
         * Constructs a new skill instance.
         *
         * @param number the unique skill identifier
         * @param level the skill proficiency level
         * @param mandatory whether this skill is mandatory
         */
        public Skill(int number, int level, boolean mandatory) {
            this.number = number;
            this.level = level;
            this.mandatory = mandatory;
        }
    }

    private final Map<Integer, Skill> skills;

    /**
     * Creates an empty {@code CallProfile}.
     */
    public CallProfile() {
        this.skills = new HashMap<>();
    }

    /**
     * Creates a {@code CallProfile} from a collection of skills.
     *
     * @param skills collection of skills
     * @throws IllegalArgumentException if any skill in the collection is null
     */
    public CallProfile(Collection<Skill> skills) {
        Map<Integer, Skill> map = new HashMap<>();
        for (Skill skill : skills) {
            if (skill == null) {
                throw new IllegalArgumentException("Skill cannot be null");
            }
            map.put(skill.getNumber(), skill);
        }
        this.skills = Collections.unmodifiableMap(map);
    }
    
    /**
     * Convenience constructor to create a {@code CallProfile} from a varargs
     * array of skills.
     *
     * @param skills array of skills
     */
    public CallProfile(Skill... skills) {
        this(Arrays.asList(skills));
    }
    
    /**
     * Returns the skill with the specified number.
     *
     * @param number the skill number (identifier)
     * @return the {@link Skill} with the given number, or {@code null} if no
     *         such skill exists
     */
    public Skill get(int number) {
        return skills.get(number);
    }

    /**
     * Determines whether a skill with the specified number exists in this profile.
     *
     * @param number the skill number to search for
     * @return {@code true} if the skill is present; {@code false} otherwise
     */
    public boolean contains(int number) {
        return skills.containsKey(number);
    }

    /**
     * Returns an unmodifiable set of all skill numbers contained in this profile.
     *
     * @return a read-only set of skill identifiers
     */
    public Set<Integer> getSkillNumbers() {
        return Collections.unmodifiableSet(skills.keySet());
    }

    /**
     * Returns an unmodifiable collection of all skills contained in this profile.
     *
     * @return a read-only collection of {@link Skill} instances
     */
    public Collection<Skill> getSkills() {
        return Collections.unmodifiableCollection(skills.values());
    }
}
