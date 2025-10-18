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
package com.ale.o2g.types.common;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code PartyInfo} represents a participant involved in a call.
 * <p>
 * A participant can be a user, a device, a service, or an external entity.
 * Each participant can be identified by unique identifiers such as a login name or phone number.
 * Additional information like first name, last name, and display name may also be available.
 */
public class PartyInfo {

    /**
     * {@code Type} represents the type of a participant in a call.
     * <p>
     * Each participant has a {@code MainType} and may have an optional {@code SubType}.
     * The {@code SubType} provides more specific roles, for example:
     * "pbx", "public", "pre-off-hook", "telephony-services", "voicemail", "voice-homepage",
     * "voice-it", "sip", etc.
     */
    public static class Type {

        /**
         * {@code MainType} represents the primary category of a participant.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public static enum MainType {

            /** The participant is a user of the system. */
            USER,

            /** The participant is a device within the system. */
            DEVICE,

            /** The participant is a service, such as voicemail. */
            SERVICE,

            /** The participant is external to the system. */
            EXTERNAL,

            /** The participant type is unknown or could not be identified. */
            UNKNOWN
        }

        private MainType main;
        private String subType;

        /**
         * Returns the main type of the participant.
         *
         * @return the {@link MainType main type} of the participant, never {@code null}
         */
        public final MainType getMain() {
            return main;
        }

        /**
         * Returns the optional sub-type of the participant.
         *
         * @return the sub-type of the participant, or {@code null} if none is specified
         */
        public final String getSubType() {
            return subType;
        }

        protected Type() {
        }
    }

    /**
     * {@code Identifier} represents the unique identification information for a participant.
     * <p>
     * It may include a login name and/or a phone number.
     */
    public static class Identifier {
        private String loginName;
        private String phoneNumber;

        /**
         * Returns the login name of the participant.
         *
         * @return the login name, or {@code null} if not available
         */
        public final String getLoginName() {
            return loginName;
        }

        /**
         * Returns the phone number of the participant.
         *
         * @return the phone number, or {@code null} if not available
         */
        public final String getPhoneNumber() {
            return phoneNumber;
        }

        protected Identifier() {
        }
    }

    private Identifier id;
    private String firstName;
    private String lastName;
    private String displayName;
    private Type type;

    /**
     * Returns the participant's identifier, including login name and phone number.
     *
     * @return the {@link Identifier} object, never {@code null}
     */
    public final Identifier getId() {
        return id;
    }

    /**
     * Returns the participant's first name.
     *
     * @return the first name, or {@code null} if not provided
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Returns the participant's last name.
     *
     * @return the last name, or {@code null} if not provided
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * Returns the display name of the participant.
     * <p>
     * This may be {@code null} if both {@link #getFirstName()} and {@link #getLastName()} are provided.
     *
     * @return the display name, or {@code null} if not set
     */
    public final String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the type of the participant.
     *
     * @return the {@link Type type} of the participant, never {@code null}
     */
    public final Type getType() {
        return type;
    }

    protected PartyInfo() {
    }
}
