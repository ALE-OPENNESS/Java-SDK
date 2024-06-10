package com.ale.o2g.internal.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.events.cca.OnAgentSkillChangedEvent;
import com.ale.o2g.events.cca.OnAgentStateChangedEvent;
import com.ale.o2g.events.ccp.OnPilotCallCreatedEvent;
import com.ale.o2g.events.ccp.OnPilotCallQueuedEvent;
import com.ale.o2g.events.ccp.OnPilotCallRemovedEvent;
import com.ale.o2g.types.cca.AgentSkillSet;
import com.ale.o2g.types.cca.OperatorState.OperatorMainState;
import com.ale.o2g.types.telephony.call.Cause;

class EventBuilderTest {

    @Test
    void testOnPilotCallCreatedEvent() {
        
        String sEvent = "{\"eventName\":\"OnPilotCallCreated\", "
                + "\"caller\":\"0645234123\","
                + "\"callRef\":\"12345678\","
                + "\"cause\":\"ACD_ENTER_DISTRIBUTION\","
                + "\"pilot\":\"12340\""
                + "}";
        
        O2GEventDescriptor eventDescriptor = EventBuilder.get(sEvent);
        assertNotNull(eventDescriptor);
        
        O2GEvent ev = eventDescriptor.event();
        assertTrue(ev instanceof OnPilotCallCreatedEvent);
        
        OnPilotCallCreatedEvent e = (OnPilotCallCreatedEvent)ev;
        assertEquals(e.getCaller(), "0645234123");
        assertEquals(e.getCallRef(), "12345678");
        assertEquals(e.getPilot(), "12340");
        assertEquals(e.getCause(), Cause.ACD_ENTER_DISTRIBUTION);
        
        assertEquals(eventDescriptor.methodName(), "onPilotCallCreated");        
    }

    @Test
    void testOnPilotCallQueuedEvent() {
        
        String sEvent = "{\"eventName\":\"OnPilotCallQueued\", "
                + "\"caller\":\"0645234123\","
                + "\"callRef\":\"12345678\","
                + "\"cause\":\"DISTRIBUTED\","
                + "\"pilot\":\"12340\","
                + "\"queue\":\"13340\","
                + "\"numberOfQueued\":9"
                + "}";
        
        O2GEventDescriptor eventDescriptor = EventBuilder.get(sEvent);
        assertNotNull(eventDescriptor);
        
        O2GEvent ev = eventDescriptor.event();
        assertTrue(ev instanceof OnPilotCallQueuedEvent);
        
        OnPilotCallQueuedEvent e = (OnPilotCallQueuedEvent)ev;
        assertEquals(e.getCaller(), "0645234123");
        assertEquals(e.getCallRef(), "12345678");
        assertEquals(e.getPilot(), "12340");
        assertEquals(e.getQueue(), "13340");
        assertEquals(e.getCallNumber(), 9);
        assertEquals(e.getCause(), Cause.DISTRIBUTED);
        
        assertEquals(eventDescriptor.methodName(), "onPilotCallQueued");
        
    }
    

    @Test
    void testOnPilotCallRemovedEvent() {
        
        String sEvent = "{\"eventName\":\"OnPilotCallRemoved\", "
                + "\"newDestination\":\"10120\","
                + "\"callRef\":\"12345678\","
                + "\"cause\":\"DISTRIBUTED\","
                + "\"pilot\":\"12340\","
                + "\"releasingDevice\":\"13340\""
                + "}";
        
        O2GEventDescriptor eventDescriptor = EventBuilder.get(sEvent);
        assertNotNull(eventDescriptor);
        
        O2GEvent ev = eventDescriptor.event();
        assertTrue(ev instanceof OnPilotCallRemovedEvent);
        
        OnPilotCallRemovedEvent e = (OnPilotCallRemovedEvent)ev;
        assertEquals(e.getCallRef(), "12345678");
        assertEquals(e.getPilot(), "12340");
        assertEquals(e.getNewDestination(), "10120");
        assertEquals(e.getReleasingDevice(), "13340");
        assertEquals(e.getCause(), Cause.DISTRIBUTED);
        
        assertEquals(eventDescriptor.methodName(), "onPilotCallRemoved");
        
    }
    
    
    @Test
    void testOnAgentStateChangedEvent() {
        
        String sEvent = "{\"eventName\":\"OnAgentStateChanged\", "
                + "\"loginName\":\"oxe123\","
                + "\"state\":{"
                + "\"mainState\":\"LOG_ON\","
                + "\"subState\":\"READY\","
                + "\"proAcdDeviceNumber\":\"32000\","
                + "\"pgNumber\":\"42000\","
                + "\"withdrawReason\":0,"
                + "\"withdrawn\":true"
                + "}"
                + "}";
        
        O2GEventDescriptor eventDescriptor = EventBuilder.get(sEvent);
        assertNotNull(eventDescriptor);
        
        O2GEvent ev = eventDescriptor.event();
        assertEquals(ev.getClass().getName(), "com.ale.o2g.events.cca.OnAgentStateChangedEvent");
        
        OnAgentStateChangedEvent e = (OnAgentStateChangedEvent)ev;
        assertEquals(e.getLoginName(), "oxe123");
        assertEquals(e.getState().getMainState(), OperatorMainState.LOG_ON);
        
        assertEquals(eventDescriptor.methodName(), "onAgentStateChanged");
        
    }

    @Test
    void testOnAgentSkillChangedEvent() {
        
        String sEvent = "{\"eventName\": \"OnAgentSkillChanged\","
                + "                \"loginName\": \"oxe123\","
                + "                \"skills\":{"
                + "                    \"skills\": ["
                + "                        {"
                + "                            \"number\":10,"
                + "                            \"level\":3,"
                + "                            \"active\":true"
                + "                        },"
                + "                        {"
                + "                            \"number\":12,"
                + "                            \"level\":6,"
                + "                            \"active\":false"
                + "                        }"
                + "                    ]"
                + "                }"
                + "            }";
        

        O2GEventDescriptor eventDescriptor = EventBuilder.get(sEvent);
        assertNotNull(eventDescriptor);
        
        O2GEvent ev = eventDescriptor.event();
        assertTrue(ev instanceof OnAgentSkillChangedEvent);
        
        OnAgentSkillChangedEvent e = (OnAgentSkillChangedEvent)ev;
        
        assertEquals(eventDescriptor.methodName(), "onAgentSkillChanged");
        AgentSkillSet skills = e.getSkills();
        assertNotNull(skills);
        
        assertEquals(skills.get(10).getLevel(), 3);
        assertEquals(skills.get(10).isActive(), true);
        
        assertEquals(skills.get(12).getLevel(), 6);
        assertEquals(skills.get(12).isActive(), false);
    }
}
