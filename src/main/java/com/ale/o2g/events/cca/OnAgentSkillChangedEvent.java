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
 * Event delivered when a CCD agent changes the activation status of one or more
 * skills.
 * <p>
 * This event contains the agent's login name and the updated set of skills. It
 * is typically received by classes implementing
 * {@link CallCenterAgentEventListener} or extending
 * {@link CallCenterAgentEventAdapter}.
 * <p>
 * Typical usage:
 * 
 * <pre>
 * <code>
 * {@literal @}Override
 * public void onAgentSkillChanged(OnAgentSkillChangedEvent e) {
 *     CallProfile updatedSkills = e.getSkills();
 *     // process skill changes
 * }
 * </code>
 * </pre>
 *
 * @see CallCenterAgentEventListener#onAgentSkillChanged(OnAgentSkillChangedEvent)
 * @see CallCenterAgentEventAdapter
 */
public class OnAgentSkillChangedEvent extends O2GEvent {

    private String loginName;
    private AgentSkillSet skills;

    /**
     * Returns the login name of the agent who changed their skills.
     * 
     * @return the agent's login name
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
     * Returns the updated set of skills for the agent.
     * 
     * @return the agent skills
     */
    public final AgentSkillSet getSkills() {
        return skills;
    }
}
