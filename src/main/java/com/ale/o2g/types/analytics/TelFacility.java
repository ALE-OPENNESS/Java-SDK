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
package com.ale.o2g.types.analytics;

/**
 * {@code TelFacility} represents the telephonic facilities.
 */
public enum TelFacility {
    
    /**
     * Calling line Identification presentation.
     */
    CallingLineIdentificationPresentation,
    
    /**
     * Connected line identification presentation.
     */
    ConnectedLineIdentificationPresentation,
    
    /**
     * Calling line identification restriction. 
     */
    CallingLineIdentificationRestriction,
    
    /**
     * Connected line identification restriction.
     */
    ConnectedLineIdentificationRestriction,
    
    /**
     * Malicious call identification. 
     */
    MaliciousCallIdentification,
    
    /**
     * Unconditional call forwarding.
     */
    CallForwardingUnconditional,
    
    /**
     * Call forwarding on busy.
     */
    CallForwardingOnBusy,
    
    /**
     * Call forwarding on no reply.
     */
    CallForwardingOnNoReply,
    
    /**
     * Transfer. 
     */
    Transfer,
    
    /**
     * Advice of charge at setup. 
     */
    AdviceOfChargeAtSetup,
    
    /**
     * Advice of charge during call. 
     */
    AdviceOfChargeDuringCall,
    
    /**
     * Advice of charge at the end. 
     */
    AdviceOfChargeAtEnd,
    
    /**
     * Closed user group.
     */
    ClosedUserGroup,
    
    /**
     * Call waiting.
     */
    CallWaiting,
    
    /**
     * User to user signalling.
     */
    UserToUserSignalling,
    
    /**
     * User to user facility. 
     */
    UserToUserFacility,
    
    /**
     * Terminal portability.
     */
    TerminalPortability,
    
    /**
     * Interception.
     */
    Interception,
    
    /**
     * Booking.
     */
    Booking,
    
    /**
     * Camp on.
     */
    CampOn,
    
    /**
     * Conference. 
     */
    Conference,
    
    /**
     * Mini messaging.
     */
    MiniMessaging,
    
    /**
     * Sub addessing. 
     */
    Subaddressing,
    
    /**
     * Basic call.
     */
    BasicCall,
    
    /**
     * Operator facility.
     */
    OperatorFacility,
    
    /**
     * Substitution.
     */
    Substitution,
    
    /**
     * Priority incoming call.
     */
    PriorityIncomingCall,
    
    /**
     * Transit.
     */
    Transit,
    
    /**
     * Overflow private to public. 
     */
    PrivateOverflowToPublic,
    
    /**
     * Rerouting private to public.
     */
    ReroutingPublicToPrivate,
    
    /**
     * Fax server.
     */
    FaxServer,
    
    /**
     * Voice mail.
     */
    VoiceMail,
    
    /**
     * Central abbreviated numbering.
     */
    CentralAbbreviatedNumbering,
    
    /**
     * Individual abbreviated numbering.
     */
    IndividualAbbreviatedNumbering,
    
    /**
     * Integrated VPN service. 
     */
    IntegratedServiceVirtualPrivateNetwork,
    
    /**
     *  Dedicated OmniPCX Enterprise overflow on VPN service.
     */
    OverflowVirtualPrivateNetwork,
    
    /**
     * Dedicated OmniPCX Enterprise ARS service.
     */
    ARSService,
    
    /**
     * Dedicated OmniPCX Enterprise DISA service.
     */
    DISA,
    
    /**
     * None.
     */
    None
}
