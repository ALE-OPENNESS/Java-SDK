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
package com.ale.o2g.events.ccrt;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.common.ServiceState;

/**
 * Event delivered by {@link CallCenterRealtimeEventListener#onPGOtherRtiChanged(OnPGOtherRtiChangedEvent)
 * onPGOtherRtiChanged(OnPGOtherRtiChangedEvent)} whenever the attributes of one or more CCD 
 * processing groups change.
 * <p>
 * This event contains only the attributes that have changed since the previous notification.
 * It is sent periodically according to the {@link com.ale.o2g.types.ccrt.Context Context} configuration in 
 * {@link com.ale.o2g.CallCenterRealtimeService CallCenterRealtimeService}.
 * <p>
 * Typical usage:
 * <pre><code>
 * {@literal @}Override
 * public void onPGOtherRtiChanged(OnPGOtherRtiChangedEvent e) {
 *     int nbACDCalls = e.getNbOfACDCalls();
 *     // ...
 * }
 * </code></pre>
 * 
 * @see com.ale.o2g.CallCenterRealtimeService
 * @see com.ale.o2g.types.ccrt.Context
 * @see CallCenterRealtimeEventListener#onPGOtherRtiChanged(OnPGOtherRtiChangedEvent)
 * @since 2.7.4
 */
public class OnPGOtherRtiChangedEvent extends O2GEvent {

    private String name;
    private String number;
    private OtherProcessingGroupType type;
    private ServiceState state;
    private int nbOfACDCalls;
    private int incomingTraffic;
    private int afeKey;

    /** 
     * Returns the name of the processing group.
     * 
     * @return the processing group's name
     */
    public String getName() {
        return name;
    }

    /** 
     * Returns the directory number of the processing group.
     * 
     * @return the processing group's directory number
     */
    public String getNumber() {
        return number;
    }

    /** 
     * Returns the type of the processing group.
     * 
     * @return the {@link OtherProcessingGroupType} of the group
     */
    public OtherProcessingGroupType getType() {
        return type;
    }

    /** 
     * Returns the current service state of the processing group.
     * 
     * @return the {@link ServiceState} of the processing group
     */
    public ServiceState getState() {
        return state;
    }

    /** 
     * Returns the number of ACD calls in the processing group.
     * 
     * @return the number of ACD calls
     */
    public int getNbOfACDCalls() {
        return nbOfACDCalls;
    }

    /** 
     * Returns the number of incoming calls during the last minute.
     * 
     * @return the count of incoming calls in the last minute
     */
    public int getIncomingTraffic() {
        return incomingTraffic;
    }

    /** 
     * Returns the CCD key of this processing group.
     * 
     * @return the unique key identifier for the processing group
     */
    public final int getKey() {
        return afeKey;
    }

    protected OnPGOtherRtiChangedEvent() {
    }
}
