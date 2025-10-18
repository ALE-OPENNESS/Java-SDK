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



/**
 * {@code CallCenterRealtimeEventAdapter} is a convenience abstract class that implements
 * the {@link CallCenterRealtimeEventListener} interface with empty method bodies.
 * <p>
 * Subclasses can override only the methods they are interested in, without needing
 * to implement all methods of {@link CallCenterRealtimeEventListener}.
 * This is especially useful when handling a subset of call center real-time events.
 * <p>
 * Example usage:
 * <pre>{@code
 * public class MyRealtimeListener extends CallCenterRealtimeEventAdapter {
 *     {@literal @}Override
 *     public void onAgentRtiChanged(OnAcdStatsProgressEvent e) {
 *         // Handle agent updates
 *     }
 * }
 * }</pre>
 * 
 * @see CallCenterRealtimeEventListener
 * @since 2.7.4
 */
public abstract class CallCenterRealtimeEventAdapter implements CallCenterRealtimeEventListener {

    /**
     * {@inheritDoc} Default empty implementation.
     */
    @Override
    public void onAgentRtiChanged(OnAgentRtiChangedEvent e) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc} Default empty implementation.
     */
    @Override
    public void onPilotRtiChanged(OnPilotRtiChangedEvent e) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc} Default empty implementation.
     */
    @Override
    public void onQueueRtiChanged(OnQueueRtiChangedEvent e) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc} Default empty implementation.
     */
    @Override
    public void onPGOtherRtiChanged(OnPGOtherRtiChangedEvent e) {
        // Default empty implementation
    }

    
    /**
     * {@inheritDoc} Default empty implementation.
     */
    @Override
    public void onPGAgenRtiChanged(OnPGAgentRtiChangedEvent e) {
        // Default empty implementation
    }
}
