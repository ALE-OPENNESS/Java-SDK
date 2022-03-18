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
package com.ale.o2g.types.telephony;

import java.util.Collection;

import com.ale.o2g.types.telephony.call.CallData;
import com.ale.o2g.types.telephony.call.Leg;
import com.ale.o2g.types.telephony.call.Participant;

/**
 * This class describe a call.
 * <p>
 * An outgoing call is created by invoking one of the {@code makeCall} methods
 * of {@link com.ale.o2g.TelephonyService TelephonyService}.
 * <p>
 * When the call has been created by the OmniPCX Enterprise, its state can be
 * monitored by analyzing event with a
 * {@link com.ale.o2g.events.telephony.TelephonyEventListener
 * TelephonyEventListener} added to the event subscription passed to the
 * session.
 */
public final class Call {

    /**
     * {@code Capabilities} represents the call capabilities.
     */
	public static class Capabilities {
		private boolean addDevice;
		private boolean addParticipant;
		private boolean intruded;
        private boolean intrusion;
		private boolean transfer;
		private boolean blindTransfer;
		private boolean merge;
		private boolean redirect;
		private boolean pickedUp;
		private boolean redirectToVoiceMail;
		private boolean redirectToDVA;
		private boolean overflowToVoiceMail;
		private boolean dropMe;
		private boolean terminate;
		private boolean reject;
		private boolean callBack;
		private boolean park;
		private boolean startRecord;
		private boolean stopRecord;
		private boolean pauseRecord;
		private boolean resumeRecord;
		private boolean dropParticipant;
		private boolean muteParticipant;
		private boolean holdParticipant;

		/**
		 * Returns whether a device can be added to this call.
		 * 
		 * @return {@code true} if a device can be added to this call; {@code false}
		 *         otherwise.
		 */
		public boolean canAddDevice() {
			return this.addDevice;
		}

		/**
		 * Returns whether a participant can be added to this call.
		 * 
		 * @return {@code true} if a participant can be added to this call;
		 *         {@code false} otherwise.
		 */
		public boolean canAddParticipant() {
			return this.addParticipant;
		}

        /**
         * Returns whether this call can be intruted.
         * @return {@code true} if the call can be intruted; {@code false} otherwise.
         */
        public final boolean canIntruded() {
            return intruded;
        }

        /**
         * Return whether it is possible to make intrusion on the user called through this call.
         * @return {@code true} if the intrusion is possible; {@code false} otherwise.
         */
        public final boolean canMakeIntrusion() {
            return intrusion;
        }

        /**
         * Returns whether this call can be transferred.
         * @return {@code true} if the call can be transferred; {@code false} otherwise.
         */
        public final boolean canTransfer() {
            return transfer;
        }

        /**
         * Returns whether this call can be blind transferred.
         * @return {@code true} if the call can be blind transferred; {@code false} otherwise.
         */
        public final boolean canBlindTransfer() {
            return blindTransfer;
        }

        /**
         * Returns whether this call can be merged.
         * @return {@code true} if the call can be merged; {@code false} otherwise.
         */
        public final boolean canMerge() {
            return merge;
        }

        /**
         * Returns whether this call can be redirected.
         * @return {@code true} if the call can be redirected; {@code false} otherwise.
         */
        public final boolean canRedirect() {
            return redirect;
        }

        /**
         * Returns whether this call can be picked up.
         * @return {@code true} if the call can be picked up; {@code false} otherwise.
         */
        public final boolean canPickedUp() {
            return pickedUp;
        }

        /**
         * Returns whether this call can be redirected on voice mail.
         * @return {@code true} if the call can be redirected on voice mail; {@code false} otherwise.
         */
        public final boolean canRedirectToVoiceMail() {
            return redirectToVoiceMail;
        }

        /**
         * Returns whether this call can be redirected on DVA.
         * @return {@code true} if the call can be redirected on DVA; {@code false} otherwise.
         */
        public final boolean canRedirectToDVA() {
            return redirectToDVA;
        }

        /**
         * Returns whether this call can overflow on voice mail.
         * @return {@code true} if the call can overflow on voice mail; {@code false} otherwise.
         */
        public final boolean canOverflowToVoiceMail() {
            return overflowToVoiceMail;
        }

        /**
         * Returns whether this call can be dropped.
         * @return {@code true} if the call can be dropped; {@code false} otherwise.
         */
        public final boolean canDropMe() {
            return dropMe;
        }

        /**
         * Returns whether this call can be terminated.
         * @return {@code true} if the call can be terminated; {@code false} otherwise.
         */
        public final boolean canTerminate() {
            return terminate;
        }

        /**
         * Returns whether this call can be rejected.
         * @return {@code true} if the call can be rejected; {@code false} otherwise.
         */
        public final boolean canReject() {
            return reject;
        }

        /**
         * Returns whether this call can be called back.
         * @return {@code true} if the call can be called back; {@code false} otherwise.
         */
        public final boolean canCallBack() {
            return callBack;
        }

        /**
         * Returns whether this call can be parked.
         * @return {@code true} if the call can be parked; {@code false} otherwise.
         */
        public final boolean canPark() {
            return park;
        }

        /**
         * Returns whether this call can be recorded.
         * @return {@code true} if the call can be recorded; {@code false} otherwise.
         */
        public final boolean canStartRecord() {
            return startRecord;
        }

        /**
         * Returns whether this call can stop recording.
         * @return {@code true} if the call can stop recording; {@code false} otherwise.
         */
        public final boolean canStopRecord() {
            return stopRecord;
        }

        /**
         * Returns whether this call can pause recording.
         * @return {@code true} if the call can pause recording; {@code false} otherwise.
         */
        public final boolean canPauseRecord() {
            return pauseRecord;
        }

        /**
         * Returns whether this call can resume recording.
         * @return {@code true} if the call can resume recording; {@code false} otherwise.
         */
        public final boolean canResumeRecord() {
            return resumeRecord;
        }

        /**
         * Returns whether drop participant can be invoked.
         * @return {@code true} if drop participant can be invoked; {@code false} otherwise.
         */
        public final boolean canDropParticipant() {
            return dropParticipant;
        }

        /**
         * Returns whether mute participant can be invoked.
         * @return {@code true} if mute participant can be invoked; {@code false} otherwise.
         */
        public final boolean canMuteParticipant() {
            return muteParticipant;
        }

        /**
         * Returns whether hold participant can be invoked.
         * @return {@code true} if hold participant can be invoked; {@code false} otherwise.
         */
        public final boolean canHoldParticipant() {
            return holdParticipant;
        }

        protected Capabilities() {
        }
		
		
	}

	private String callRef;
	private CallData callData;
	private Collection<Leg> legs;
	private Collection<Participant> participants;

	/**
	 * Returns the reference of this call.
	 * 
	 * @return the call reference
	 */
	public final String getCallRef() {
		return callRef;
	}

	/**
	 * Returns the data associated to this call.
	 * 
	 * @return the call data
	 */
	public final CallData getCallData() {
		return callData;
	}

    /**
     * Returns the legs associated to this call.
     * @return the legs
     */
    public final Collection<Leg> getLegs() {
        return legs;
    }

    /**
     * Returns the participants associated to this call.
     * @return the participants
     */
    public final Collection<Participant> getParticipants() {
        return participants;
    }

    protected Call() {
    }
	
}
