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

/**
 * Provides types and utilities to access and represent CCD (Contact Center Distribution) 
 * agent services for <a href="https://www.al-enterprise.com/en/products/applications/omnitouch-contact-center">
 * OmniTouch Contact Center</a>.
 * <p>
 * This package contains classes and enums that represent the configuration, state, 
 * skills, and operational behavior of CCD agents and supervisors.
 * <p>
 * Key concepts include:
 * <ul>
 *     <li>{@link AgentGroups} - the set of agent groups an operator is attached to, including the preferred group.</li>
 *     <li>{@link AgentSkill} - a single skill of a CCD agent, including its level and active state.</li>
 *     <li>{@link AgentSkillSet} - a collection of skills for a CCD agent, allowing lookup and enumeration of skills.</li>
 *     <li>{@link OperatorConfiguration} - configuration of a CCD operator, including type (agent or supervisor), pro-ACD, groups, skills, and feature settings.</li>
 *     <li>{@link OperatorState} - the current state of a CCD operator, including main and dynamic states, logged-in pro-ACD, and withdraw information.</li>
 *     <li>{@link WithdrawReason} - represents a reason why an agent is in withdraw state, used for reporting and statistics.</li>
 *     <li>{@link IntrusionMode} - defines the possible intrusion modes a supervisor can use when intruding on a call.</li>
 * </ul>
 * <p>
 * These types are used in conjunction with CCD agent services to:
 * <ul>
 *     <li>Query and modify agent skills and group assignments</li>
 *     <li>Monitor operator states and transitions</li>
 *     <li>Manage agent features such as multi-line, headset, and help requests</li>
 *     <li>Handle withdraw reasons and supervisor intrusions</li>
 * </ul>
 * <p>
 * This package is part of the Contact Center API and provides a type-safe, structured 
 * way to represent agent-related data in the CCD system.
 *
 */
package com.ale.o2g.types.cca;
