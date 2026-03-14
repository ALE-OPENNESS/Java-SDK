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

package com.ale.o2g.types.cca;

import static com.ale.o2g.test.ExtendAssert.assertContainsStrict;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

/**
 * 
 */
class AgentSkillSetTest extends AbstractJsonTest {

	private AgentSkillSet skillSet;
	
	@Override
	protected void beforeEach() {
		Map<Integer, AgentSkill> mapSkill = new HashMap<Integer, AgentSkill>();
		
		mapSkill.put(1, gson.fromJson("{\"number\":1, \"level\":2, \"active\":true, \"domain\":1, \"name\": \"marketing\"}", AgentSkill.class));
		mapSkill.put(2, gson.fromJson("{\"number\":2, \"level\":3, \"active\":true, \"domain\":2, \"name\": \"marketing\"}", AgentSkill.class));
		mapSkill.put(3, gson.fromJson("{\"number\":3, \"level\":3, \"active\":true, \"domain\":1, \"name\": \"sales\"}", AgentSkill.class));
		
		skillSet = new AgentSkillSet(mapSkill) {};		
	}

	@Test
	void testGetSkillByNumber() {
				
		AgentSkill skill = skillSet.get(1);
		
		assertNotNull(skill);
		assertEquals(2, skill.getLevel());
		assertTrue(skill.isActive());
	}

	@Test
	void testGetSkillByWrongNumber() {				
		assertNull(skillSet.get(5));
	}

	@Test
	void testSize() {				
		assertEquals(3, skillSet.size());
	}	
	
	@Test
	void testGetSkillByDomainAndName() {				

		AgentSkill skill = skillSet.get(1, "marketing");
		
		assertNotNull(skill);
		assertEquals(2, skill.getLevel());
		assertTrue(skill.isActive());

		skill = skillSet.get(2, "marketing");
		
		assertNotNull(skill);
		assertEquals(3, skill.getLevel());
		assertTrue(skill.isActive());
	}
	
	@Test
	void testGetSkillContainsByNumber() {				
		assertTrue(skillSet.contains(1));
		assertFalse(skillSet.contains(5));
	}
	
	@Test
	void testGetSkillContainsByDomainAndName() {
		assertTrue(skillSet.contains(1, "marketing"));
		assertFalse(skillSet.contains(1, "support"));
		assertFalse(skillSet.contains(5, "marketing"));
	}

	@Test
	void testGetSkillNumbers() {
		assertContainsStrict(Arrays.asList(1, 2, 3), skillSet.getSkillNumbers());
	}

	@Test
	void testGetAgentSkills() {
		List<AgentSkill> skills = new ArrayList<AgentSkill>(skillSet.getSkills());
		assertEquals(3, skills.size());
	}

}
