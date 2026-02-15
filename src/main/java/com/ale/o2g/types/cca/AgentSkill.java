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

/**
 * {@code AgentSkill} represents a skill assigned to a CCD operator.
 * <p>
 * Skills are used by the <em>Advanced Call Routing</em> strategy to influence
 * how calls are distributed among agents. Each skill has a unique identifier,
 * a proficiency level, and may belong to a specific domain.
 * </p>
 */
public final class AgentSkill {

    private int number;
    private int level;
    private boolean active;
    private Integer domain;
    private String name;
    private String abvName;

    /**
     * Returns the domain identifier this skill belongs to.
     *
     * @return the domain ID of the skill
     * @since 2.7.4
     */
    public final Integer getDomain() {
        return domain;
    }

    /**
     * Returns the full name of this skill.
     *
     * @return the skill name
     * @since 2.7.4
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the abbreviated name of this skill.
     *
     * @return the abbreviated skill name
     * @since 2.7.4
     */
    public final String getAbvName() {
        return abvName;
    }

    /**
     * Returns the unique identifier of this skill.
     *
     * @return the skill number (unique ID)
     */
    public final int getNumber() {
        return number;
    }

    /**
     * Returns the proficiency level of this skill.
     * <p>
     * A higher level typically indicates greater expertise or priority
     * when routing calls.
     * </p>
     *
     * @return the skill level
     */
    public final int getLevel() {
        return level;
    }

    /**
     * Indicates whether this skill is currently active.
     *
     * @return {@code true} if the skill is active; {@code false} otherwise
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * Protected constructor to allow subclassing or framework use.
     */
    protected AgentSkill() {
    }
}