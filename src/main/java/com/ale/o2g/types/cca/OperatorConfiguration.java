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
 * {@code OperatorConfiguration} represents the configuration of a CCD operator.
 * <p>
 * A CCD operator can be an {@link Type#AGENT agent} or a {@link Type#SUPERVISOR
 * supervisor}, and this class provides access to the operator's type,
 * associated pro-ACD station, group memberships, skills, and feature settings
 * (such as headset usage, self-assignment, or multiline capability).
 */
public class OperatorConfiguration {

    /**
     * {@code Type} represents the CCD operator type, either agent or supervisor.
     */
    public static enum Type {

        /**
         * CCD Agent.
         */
        AGENT,

        /**
         * CCD Supervisor
         */
        SUPERVISOR
    }

    private Type type;
    private String proacd;
    private AgentGroups groups;
    private AgentSkillSet skills;
    private boolean selfAssign;
    private boolean headset;
    private boolean help;
    private boolean multiline;

    /**
     * Returns the type of CCD operator (agent or supervisor).
     * 
     * @return the operator type
     */
    public final Type getType() {
        return type;
    }

    /**
     * Returns the associated pro-ACD station.
     * 
     * @return the pro-ACD station extension number, or {@code null} if this
     *         operator has no associated pro-ACD station
     */
    public final String getProacd() {
        return proacd;
    }

    /**
     * Returns the agent groups the operator is attached to, along with the
     * preferred agent group if one is defined.
     * 
     * @return the {@link AgentGroups} object representing the operator's group
     *         memberships and preferred group
     */
    public final AgentGroups getGroups() {
        return groups;
    }

    /**
     * Returns the operator's skills.
     * 
     * @return the {@link AgentSkillSet} of the operator
     */
    public final AgentSkillSet getSkills() {
        return skills;
    }

    /**
     * Returns whether the operator can choose their own agent group.
     * 
     * @return {@code true} if the operator can self-assign a group;
     *         {@code false} otherwise
     * @deprecated Use {@link #isSelfAssignable()} instead.
     */
    @Deprecated
    public final boolean isSelfAssign() {
        return selfAssign;
    }

    /**
     * Returns whether the operator can choose their own agent group.
     * 
     * @return {@code true} if the operator can self-assign a group;
     *         {@code false} otherwise
     */
    public final boolean isSelfAssignable() {
        return selfAssign;
    }

    /**
     * Returns whether the operator has the headset feature enabled.
     * <p>
     * The headset feature allows a CCD operator to answer calls using a headset.
     * 
     * @return {@code true} if the operator has the headset feature configured;
     *         {@code false} otherwise
     * @deprecated Use {@link #isHeadsetEnabled()} instead.
     */
    @Deprecated
    public final boolean isHeadset() {
        return headset;
    }

    /**
     * Returns whether the operator has the headset feature enabled.
     * <p>
     * The headset feature allows a CCD operator to answer calls using a headset.
     * 
     * @return {@code true} if the operator has the headset feature configured;
     *         {@code false} otherwise
     */
    public final boolean isHeadsetEnabled() {
        return headset;
    }
    
    /**
     * Returns whether the operator can request help from a supervisor.
     * 
     * @return {@code true} if the operator can request help;
     *         {@code false} otherwise
     * @deprecated Use {@link #canRequestHelp()} instead.
     */
    @Deprecated
    public final boolean isHelp() {
        return help;
    }

    /**
     * Returns whether the operator can request help from a supervisor.
     * 
     * @return {@code true} if the operator can request help;
     *         {@code false} otherwise
     */
    public final boolean canRequestHelp() {
        return help;
    }

    /**
     * Returns whether the operator is configured as multiline.
     * 
     * @return {@code true} if the operator is multiline; {@code false} otherwise
     */
    public final boolean isMultiline() {
        return multiline;
    }

    protected OperatorConfiguration(Type type, String proacd, AgentGroups groups, AgentSkillSet skills,
            boolean selfAssign, boolean headset, boolean help, boolean multiline) {
        this.type = type;
        this.proacd = proacd;
        this.groups = groups;
        this.skills = skills;
        this.selfAssign = selfAssign;
        this.headset = headset;
        this.help = help;
        this.multiline = multiline;
    }

}
