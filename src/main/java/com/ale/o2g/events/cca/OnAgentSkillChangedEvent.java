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
package com.ale.o2g.events.cca;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.cca.AgentSkillSet;

/**
 * This event is raised when an agent change the activation of one or several
 * skills.
 */
public class OnAgentSkillChangedEvent extends O2GEvent {

    private String loginName;
    private AgentSkillSet skills;

    /**
     * Returns the operator login name.
     * 
     * @return the login name
     */
    public final String getLoginName() {
        return loginName;
    }

    protected OnAgentSkillChangedEvent(String eventName, String loginName, AgentSkillSet skills) {
        super(eventName);
        this.loginName = loginName;
        this.skills = skills;
    }

    /**
     * Return the agent skills.
     * 
     * @return the skills
     */
    public final AgentSkillSet getSkills() {
        return skills;
    }
}
