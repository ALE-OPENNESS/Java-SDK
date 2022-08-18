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
 * {@code PartyInfo} represents a party involved in a call.
 */
public class PartyInfo {

    /**
     * {@code Type} represents a type of participant to a call. A participant is
     * identified by its {@code MainType} type, and by an optional {@code SubType}.
     * {@code SubType} can be "pbx", "public", "pre-off-hook", telephony-services",
     * "voicemail", "voice-homepage", "voice-it", "sip".
     */
    public static class Type {

        /**
         * {@code MainType} represents the main type a participant can be.
         */
        @JsonEnumDeserializerFallback(value = "UNKNOWN")
        public static enum MainType {

            /**
             * The participant is a user of the system.
             */
            USER,

            /**
             * The participant is a device of the system.
             */
            DEVICE,

            /**
             * The participant is a service of the system. For exemple the voice mail.
             */
            SERVICE,

            /**
             * The participant is not a user of the system.
             */
            EXTERNAL,

            /**
             * The participant type has not been identified.
             */
            UNKNOWN
        }

        private MainType main;
        private String subType;

        /**
         * Return the main type.
         * 
         * @return the main
         */
        public final MainType getMain() {
            return main;
        }

        /**
         * Returns the sub-type.
         * 
         * @return the sub type
         */
        public final String getSubType() {
            return subType;
        }

        protected Type() {
        }

        
    }

    /**
     * {@code Identifier} represents the information used to uniquely identify a
     * participant; either the login name or the phone number.
     */
    public static class Identifier {
        private String loginName;
        private String phoneNumber;
        
        /**
         * Returns the participant login name.
         * @return the login name
         */
        public final String getLoginName() {
            return loginName;
        }
        
        /**
         * Returns the participant phone number.
         * @return the phone number
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
     * Returns this participant's identifier; Either the login name or the phone number.
     * @return the id the identifier.
     */
    public final Identifier getId() {
        return id;
    }
    
    /**
     * Returns this participant's first name.
     * @return the first name.
     */
    public final String getFirstName() {
        return firstName;
    }
    
    /**
     * Returns this participant's last name.
     * @return the last name.
     */
    public final String getLastName() {
        return lastName;
    }
    
    /**
     * Return this participant's display name.
     * If {@code firstName} and {@code lastName} are filled, the {@code displayName} is {@code null}.
     * @return the display name.
     * 
     */
    public final String getDisplayName() {
        return displayName;
    }
    
    /**
     * Returns this participant's type.
     * @return the type.
     */
    public final Type getType() {
        return type;
    }

    protected PartyInfo() {
    }
}
