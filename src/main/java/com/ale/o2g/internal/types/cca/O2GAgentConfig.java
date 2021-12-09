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
package com.ale.o2g.internal.types.cca;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ale.o2g.types.cca.AgentGroups;
import com.ale.o2g.types.cca.AgentSkill;
import com.ale.o2g.types.cca.AgentSkillSet;
import com.ale.o2g.types.cca.OperatorConfiguration;

/**
 *
 */
public class O2GAgentConfig {

    static class O2GAgentSkills
    {
        private List<AgentSkill> skills;
    }
    
    private OperatorConfiguration.Type type;
    private String proacd;
    private AgentGroups processingGroups;
    private O2GAgentSkills skills;
    private boolean selfAssign;
    private boolean headset;
    private boolean help;
    private boolean multiline;

    public OperatorConfiguration toOperatorConfiguration() {
        // Transform the AgentSkills to a SkillSet
        Map<Integer, AgentSkill> mapSkills = new HashMap<Integer, AgentSkill>();
     
        if (skills.skills != null) {
            skills.skills.forEach(s -> mapSkills.put(s.getNumber(), s));
        }

        return new OperatorConfiguration(
                type,
                proacd,
                processingGroups,
                new AgentSkillSet(mapSkills) {},
                selfAssign,
                headset,
                help,
                multiline) {};
    }
}
