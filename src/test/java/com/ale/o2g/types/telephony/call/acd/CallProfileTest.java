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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CallProfileTest {

    @Test
    void testSkillConstructorAndGetters() {
        CallProfile.Skill skill = new CallProfile.Skill(101, 5, true);

        assertEquals(101, skill.getNumber());
        assertEquals(5, skill.getLevel());
        assertTrue(skill.isMandatory());

        CallProfile.Skill skill2 = new CallProfile.Skill(102, 3, false);
        assertEquals(102, skill2.getNumber());
        assertEquals(3, skill2.getLevel());
        assertFalse(skill2.isMandatory());
    }

    @Test
    void testCallProfileVarargsConstructor() {
        CallProfile.Skill s1 = new CallProfile.Skill(1, 2, true);
        CallProfile.Skill s2 = new CallProfile.Skill(2, 4, false);

        CallProfile profile = new CallProfile(s1, s2);

        assertEquals(s1, profile.get(1));
        assertEquals(s2, profile.get(2));
        assertTrue(profile.contains(1));
        assertTrue(profile.contains(2));
        assertFalse(profile.contains(3));

        Collection<CallProfile.Skill> skills = profile.getSkills();
        assertEquals(2, skills.size());
        assertThrows(UnsupportedOperationException.class, () -> skills.add(s1));

        assertEquals(Set.of(1, 2), profile.getSkillNumbers());
        assertThrows(UnsupportedOperationException.class, () -> profile.getSkillNumbers().add(3));
    }

    @Test
    void testCallProfileCollectionConstructor() {
        CallProfile.Skill s1 = new CallProfile.Skill(10, 1, true);
        CallProfile.Skill s2 = new CallProfile.Skill(20, 2, false);

        CallProfile profile = new CallProfile(Arrays.asList(s1, s2));

        assertEquals(s1, profile.get(10));
        assertEquals(s2, profile.get(20));
        assertEquals(2, profile.getSkills().size());
    }

    @Test
    void testCallProfileEmptyConstructor() {
        CallProfile profile = new CallProfile();

        assertTrue(profile.getSkills().isEmpty());
        assertTrue(profile.getSkillNumbers().isEmpty());
        assertFalse(profile.contains(1));
        assertNull(profile.get(1));
    }

    @Test
    void testCollectionConstructorRejectsNull() {
        CallProfile.Skill s1 = new CallProfile.Skill(1, 2, true);
        assertThrows(IllegalArgumentException.class, () -> new CallProfile(Arrays.asList(s1, null)));
    }
}