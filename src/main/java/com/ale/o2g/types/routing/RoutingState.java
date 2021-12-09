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
package com.ale.o2g.types.routing;

/**
 * {@code RoutingState} represente a user routing state. A routing state is
 * composed of four elements:
 * <table>
 * <tr>
 * <td>Remote extension activation</td>
 * <td>When the user is configured with a remote extension, he has the
 * possibility to activate or deactivate this remote extension. when the remote
 * extension is de-activated, call are not presented on the mobile device.</td>
 * </tr>
 * <tr>
 * <td>Forward</td>
 * <td>The user can configure a forward, on his voice mail if any or on any
 * other number (depending on the cOmniPCX Enterprise configuration).</td>
 * </tr>
 * <tr>
 * <td>Overflow</td>
 * <td>The user can configure an overflow on his asociate or on his voce mail.
 * If a forward is configured, it is considered prio the overflow.</td>
 * </tr>
 * <tr>
 * <td>Do Not Disturb</td>
 * <td>When Do Not Disturb (DND) is activated, call are not presented to the
 * user.</td>
 * </tr>
 * 
 * </table>
 */
public class RoutingState {
    private Boolean remoteExtensionActivated;
    private Forward forward;
    private Overflow overflow;
    private DndState dndState;

    protected RoutingState(Boolean remoteExtensionActivated, Forward forward, Overflow overflow, DndState dndState) {
        this.remoteExtensionActivated = remoteExtensionActivated;
        this.forward = forward;
        this.overflow = overflow;
        this.dndState = dndState;
    }

    /**
     * Returns whether the routing on remote extension is activated.
     * 
     * @return {@code true} if the routing on remote extension is activated;
     *         {@code false} otherwise.
     */
    public final Boolean isRemoteExtensionActivated() {
        return remoteExtensionActivated;
    }

    /**
     * Returns the forward.
     * 
     * @return the forward
     */
    public final Forward getForward() {
        return forward;
    }

    /**
     * Returns the overflow.
     * 
     * @return the overflow
     */
    public final Overflow getOverflow() {
        return overflow;
    }

    /**
     * Return the do not disturb state.
     * 
     * @return the dnd state.
     */
    public final DndState getDndState() {
        return dndState;
    }

}
