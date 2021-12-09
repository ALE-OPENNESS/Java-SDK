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
package com.ale.o2g.types.telephony.call;

/**
 * Describes a leg. A leg represents the user's device involved in a call for a
 * dedicated media.
 */
public class Leg {

    /**
     * {@code Capabilities} represents the capability of a log. The action that can
     * be carried out on the leg according to its state.
     */
    public static class Capabilities {

        private boolean answer;
        private boolean drop;
        private boolean hold;
        private boolean retrieve;
        private boolean reconnect;
        private boolean mute;
        private boolean unMute;
        private boolean sendDtmf;
        private boolean switchDevice;

        /**
         * Returns whether the leg can answer.
         * 
         * @return {@code true} if the leg can answer; {@code false} otherwise.
         */
        public final boolean canAnswer() {
            return answer;
        }

        /**
         * Returns whether the leg can be dropped.
         * 
         * @return {@code true} if the leg can be dropped; {@code false} otherwise.
         */
        public final boolean canDrop() {
            return drop;
        }

        /**
         * Returns whether the leg can be put on hold.
         * 
         * @return {@code true} if the leg can put on hold; {@code false} otherwise.
         */
        public final boolean canHold() {
            return hold;
        }

        /**
         * Returns whether the leg can be retrieved.
         * 
         * @return {@code true} if the leg can be retrieved; {@code false} otherwise.
         */
        public final boolean canRetrieve() {
            return retrieve;
        }

        /**
         * Returns whether the leg can be reconnected.
         * 
         * @return {@code true} if the leg can be reconnected; {@code false} otherwise.
         */
        public final boolean canReconnect() {
            return reconnect;
        }

        /**
         * Returns whether the leg can be muted.
         * 
         * @return {@code true} if the leg can be muted; {@code false} otherwise.
         */
        public final boolean canMute() {
            return mute;
        }

        /**
         * Returns whether the leg can be unmuted.
         * 
         * @return {@code true} if the leg can be unmuted; {@code false} otherwise.
         */
        public final boolean canUnMute() {
            return unMute;
        }

        /**
         * Returns whether the leg can send dtmf.
         * 
         * @return {@code true} if the leg can send dtmf; {@code false} otherwise.
         */
        public final boolean canSendDtmf() {
            return sendDtmf;
        }

        /**
         * Returns whether the leg can switch device.
         * 
         * @return {@code true} if the leg can switch device; {@code false} otherwise.
         */
        public final boolean canSwitchDevice() {
            return switchDevice;
        }

    }

    private String deviceId;
    private MediaState state;
    private boolean ringingRemote;
    private Capabilities capabilities;

    /**
     * Returns the phone number of the device associated to this leg.
     * 
     * @return the device phone number
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the media state.
     * 
     * @return the state
     */
    public final MediaState getState() {
        return state;
    }

    /**
     * Returns whether the remote party is ringing.
     * 
     * @return {@code true} if the remote is ringing; {@code false} otherwise.
     */
    public final boolean isRingingRemote() {
        return ringingRemote;
    }

    /**
     * Returns the leg capabilities
     * 
     * @return the capabilities
     */
    public final Capabilities getCapabilities() {
        return capabilities;
    }

}
