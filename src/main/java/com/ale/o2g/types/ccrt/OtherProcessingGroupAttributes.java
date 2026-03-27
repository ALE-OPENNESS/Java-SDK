/*
* Copyright 2025 ALE International
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
package com.ale.o2g.types.ccrt;

/**
 * {@code OtherProcessingGroupAttributes} represents the possible real-time attributes for a CCD processing group other than agent (forward, guide, ...).
 * <p>
 * The {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService} can report real-time events
 * for each of these attributes.
 */
public enum OtherProcessingGroupAttributes {

    /**
     * The service state of the processing group (e.g., Open, Blocked).
     */
    State,

    /**
     * The total number of ACD calls handled by the processing group.
     */
    NbOfACDCalls,
    
    /**
     * The number of incoming calls received by the processing group 
     * during the last minute.
     */
    IncomingTraffic,
    
    /**
     * Represents all attributes.
     */
    ALL
}
