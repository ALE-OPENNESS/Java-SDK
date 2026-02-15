package com.ale.o2g.internal.telephony;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.internal.util.AnnotationExclusionStrategy;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.types.telephony.call.acd.CallProfile;
import com.ale.o2g.types.telephony.call.acd.PilotTransferQueryParameters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TelephonyRestTest {

    private static record ACRSkills(Collection<CallProfile.Skill> skills) {}
    private static record PilotQueryParam(String agentNumber, ACRSkills skills, Boolean priorityTransfer, Boolean supervisedTransfer) {}

    protected Gson gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    
    @Test
    void testPilotTransferQueryParametersJson() {
        
        PilotTransferQueryParameters ptqp = new PilotTransferQueryParameters()
                .setAgentNumber("23456")
                .setPriorityTransfer(true)
                .setCallProfile(new CallProfile(
                        new CallProfile.Skill(101, 3, true),
                        new CallProfile.Skill(102, 2, false),
                        new CallProfile.Skill(103, 1, false)))
                .setSupervisedTransfer(true);

        ACRSkills skills = null;
        if (ptqp.getCallProfile() != null) {
            skills = new ACRSkills(ptqp.getCallProfile().getSkills());
        }
        
        String json = gson.toJson(new PilotQueryParam(
                ptqp.getAgentNumber(), 
                skills, 
                ptqp.getPriorityTransfer(), 
                ptqp.getSupervisedTransfer()));
        
        assertEquals(json, "{"
                + "\"agentNumber\":\"23456\","
                + "\"skills\":{"
                    + "\"skills\":["
                        + "{\"skillNumber\":101,\"expertEvalLevel\":3,\"acrStatus\":true},"
                        + "{\"skillNumber\":102,\"expertEvalLevel\":2,\"acrStatus\":false},"
                        + "{\"skillNumber\":103,\"expertEvalLevel\":1,\"acrStatus\":false}"
                    + "]"
                + "},"
                + "\"priorityTransfer\":true,"
                + "\"supervisedTransfer\":true}"
                );
    }

}
