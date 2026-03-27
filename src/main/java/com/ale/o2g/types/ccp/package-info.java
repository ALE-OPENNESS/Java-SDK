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

/**
 * Provides data types related to pilot calls.
 * <p>
 * This package includes classes that represent detailed information about pilot calls, 
 *
 * </p>
 * 
 * <ul>
 *     <li>{@link com.ale.o2g.types.ccp.CallDataPilot} Represents the data associated with a pilot call,
 *         including the initial called party, call anonymity status, network rerouting information,
 *         and any correlator data.</li>
 * </ul>
 * 
 * <p>
 * This class is typically used in conjunction with events such as
 * {@link com.ale.o2g.events.ccp.OnPilotCallCreatedEvent} to provide complete context
 * about incoming and routed calls.
 * </p>
 *
 * @since 2.7
 */
package com.ale.o2g.types.ccp;