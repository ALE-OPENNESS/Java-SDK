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
package com.ale.o2g.internal.events.cca;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.events.cca.OnAgentSkillChangedEvent;
import com.ale.o2g.types.cca.AgentSkill;
import com.ale.o2g.types.cca.AgentSkillSet;

/**
 *
 */
public class OnInternalSkillChanged extends O2GEvent {

    static class O2GAgentSkills
    {
        private List<AgentSkill> skills;
    }
    
    private String loginName;
    private O2GAgentSkills skills;
    
    public static O2GEvent adaptSkillChanged(O2GEvent ev) {
        if (ev instanceof OnInternalSkillChanged) {
            
            OnInternalSkillChanged org = (OnInternalSkillChanged)ev;
            
            // Transform the AgentSkills to a SkillSet
            Map<Integer, AgentSkill> mapSkills = new HashMap<Integer, AgentSkill>();
         
            if (org.skills.skills != null) {
                org.skills.skills.forEach(s -> mapSkills.put(s.getNumber(), s));
            }

            return new OnAgentSkillChangedEvent(
                    org.getName(),
                    org.loginName,
                    new AgentSkillSet(mapSkills) {}) {};
        }
        else {
            throw new Error("Invalid translator exception");
        }
    }
}
