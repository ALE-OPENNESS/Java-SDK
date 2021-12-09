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
 * {@code OperatorConfiguration} class represents a CCD operator configuration.
 */
public class OperatorConfiguration {

    /**
     * {@code OperatorType} represents the CCD operator, either Agent or Supervisor.
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
     * Returns the type of CCD operator, agent or supervisor.
     * 
     * @return the operator type
     */
    public final Type getType() {
        return type;
    }

    /**
     * Returns the associated pro-acd station.
     * 
     * @return the proacd station extension number, or null if this operator as no
     *         associated pro-acd station.
     */
    public final String getProacd() {
        return proacd;
    }

    /**
     * Returns the agents groups the operator is attached in and the preferred agent
     * group if any.
     * 
     * @return the groups object that represents the agents groups the operator is
     *         attached in and the preferred agent group if any.
     */
    public final AgentGroups getGroups() {
        return groups;
    }

    /**
     * Returns the operator skills.
     * 
     * @return the skills
     */
    public final AgentSkillSet getSkills() {
        return skills;
    }

    /**
     * Returns whether the operator can choose his agent group.
     * 
     * @return {@code true} if the operator can choose his agent group;
     *         {@code false} otherwise.
     */
    public final boolean isSelfAssign() {
        return selfAssign;
    }

    /**
     * Returns whether the operator has the headset feature configured.
     * <p>
     * The headset feature allows a CCD operator to answer calls using a headset.
     * 
     * @return {@code true} if the operator has the headset feature configured;
     *         {@code false} otherwise.
     */
    public final boolean isHeadset() {
        return headset;
    }

    /**
     * Returns whether the operator can request help from a supervisor.
     * 
     * @return {@code true} if the operator can request help; {@code false}
     *         otherwise.
     */
    public final boolean isHelp() {
        return help;
    }

    /**
     * Returns whether the operator is multiline.
     * 
     * @return {@code true} if the operator is multiline; {@code false} otherwise.
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
