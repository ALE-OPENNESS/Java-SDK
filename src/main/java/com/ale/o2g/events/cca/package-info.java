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
 * Provides the classes and interfaces for listening to <a href=
 * "https://www.al-enterprise.com/en/products/applications/omnitouch-contact-center">call
 * center agent</a> events in a CCD environment.
 * <p>
 * This package includes event classes that are delivered by the 
 * {@link com.ale.o2g.CallCenterAgentService CallCenterAgentService} whenever
 * changes occur related to CCD agents, such as:
 * <ul>
 *     <li>Agent state changes (login, logout, ready, busy, wrap-up, withdraw, etc.)</li>
 *     <li>Skill activation or deactivation</li>
 *     <li>Supervisor help requests and cancellations</li>
 * </ul>
 * <p>
 * To receive notifications about these events, applications implement the
 * {@link com.ale.o2g.events.cca.CallCenterAgentEventListener CallCenterAgentEventListener} interface, or extend
 * the {@link com.ale.o2g.events.cca.CallCenterAgentEventAdapter CallCenterAgentEventAdapter} and override only the
 * methods of interest. The listener object is then registered with an
 * {@link com.ale.o2g.Session Session} via
 * {@link com.ale.o2g.Session#listenEvents(com.ale.o2g.Subscription) Session.listenEvents}.
 * 
 * @see com.ale.o2g.CallCenterAgentService
 * @see com.ale.o2g.events.cca.CallCenterAgentEventListener
 * @see com.ale.o2g.events.cca.CallCenterAgentEventAdapter
 * @since 2.7.4
 */
package com.ale.o2g.events.cca;
