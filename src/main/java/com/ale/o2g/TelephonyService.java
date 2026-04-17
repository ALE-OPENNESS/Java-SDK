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
package com.ale.o2g;

import java.util.Collection;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.telephony.Call;
import com.ale.o2g.types.telephony.Callback;
import com.ale.o2g.types.telephony.HuntingGroupStatus;
import com.ale.o2g.types.telephony.HuntingGroups;
import com.ale.o2g.types.telephony.MiniMessage;
import com.ale.o2g.types.telephony.RecordingAction;
import com.ale.o2g.types.telephony.TelephonicState;
import com.ale.o2g.types.telephony.call.CorrelatorData;
import com.ale.o2g.types.telephony.call.Leg;
import com.ale.o2g.types.telephony.call.Participant;
import com.ale.o2g.types.telephony.call.acd.PilotInfo;
import com.ale.o2g.types.telephony.call.acd.PilotTransferQueryParameters;
import com.ale.o2g.types.telephony.device.DeviceState;

/**
 * The {@code TelephonyService} allows a user to initiate calls and activate
 * any kind of OmniPCX Enterprise telephony services.
 * <p>
 * Using this service requires a <b>TELEPHONY_ADVANCED</b> license, except for
 * the three basic services {@link #basicMakeCall(String, String, boolean)},
 * {@link #basicAnswerCall(String)} and {@link #basicDropMe(String)}, which are
 * available without any license.
 */
public interface TelephonyService extends IService {

    /**
     * Initiates a basic call from the specified device to the specified called number.
     * <p>
     * This method does not require a license.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user's devices.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     *
     * @param deviceId   the device phone number used to place the call
     * @param callee     the called phone number
     * @param autoAnswer if {@code true}, the callee is called immediately; if {@code false},
     *                   the user's device is called first before placing the call to the callee
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean basicMakeCall(String deviceId, String callee, boolean autoAnswer);

    /**
     * Initiates a basic call from the specified device to the specified called number,
     * with automatic answer enabled.
     * <p>
     * This method does not require a license.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user's devices.
     *
     * @param deviceId the device phone number used to place the call
     * @param callee   the called phone number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #basicMakeCall(String, String, boolean)
     */
    boolean basicMakeCall(String deviceId, String callee);

    /**
     * Answers an incoming ringing call on the specified device.
     * <p>
     * This method does not require a license.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user's devices.
     *
     * @param deviceId the device phone number on which to answer
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean basicAnswerCall(String deviceId);

    /**
     * Exits from the current call for the specified user.
     * <p>
     * This method does not require a license.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the
     * call carries on without the user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name for whom the drop is done
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean basicDropMe(String loginName);

    /**
     * Exits from the current call for the user who opened the session.
     * <p>
     * This method does not require a license.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the
     * call carries on without the user.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #basicDropMe(String)
     */
    boolean basicDropMe();

    /**
     * Retrieves the calls currently in progress for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return The collection of active {@link Call} objects in case of success; {@code null}
     *         otherwise.
     */
    Collection<Call> getCalls(String loginName);

    /**
     * Retrieves the calls currently in progress for the user who opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return The collection of active {@link Call} objects in case of success; {@code null}
     *         otherwise.
     * @see #getCalls(String)
     */
    Collection<Call> getCalls();

    /**
     * Returns the call identified by the specified reference for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the unique call reference
     * @param loginName the login name
     * @return The {@link Call} in case of success; {@code null} if not found.
     */
    Call getCall(String callRef, String loginName);

    /**
     * Returns the call identified by the specified reference for the user who opened
     * the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the unique call reference
     * @return The {@link Call} in case of success; {@code null} if not found.
     * @see #getCall(String, String)
     */
    Call getCall(String callRef);

    /**
     * Attaches the specified correlator data to the specified call.
     * <p>
     * This is used by the application to provide application-related information
     * (limited to 32 bytes). In general, it is used to convey context from a
     * previously established call to the party of a second call.
     *
     * @param callRef        the call reference
     * @param deviceId       the device phone number for which the operation is invoked;
     *                       if the session is opened by a user, this must be one of the
     *                       user's devices
     * @param correlatorData the correlator data to attach
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean attachData(String callRef, String deviceId, CorrelatorData correlatorData);

    /**
     * Initiates a call from the specified device to the specified called number for
     * the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     *
     * @param deviceId   the device phone number from which the call is placed; if the
     *                   session is opened by a user, this must be one of the user's devices
     * @param callee     the called phone number
     * @param autoAnswer if {@code true}, the callee is called immediately; if {@code false},
     *                   the user's device is called first before placing the call to the callee
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, String loginName);

    /**
     * Initiates a call from the specified device to the specified called number for
     * the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     *
     * @param deviceId   the device phone number from which the call is placed; if the
     *                   session is opened by a user, this must be one of the user's devices
     * @param callee     the called phone number
     * @param autoAnswer if {@code true}, the callee is called immediately; if {@code false},
     *                   the user's device is called first before placing the call to the callee
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeCall(String, String, boolean, String)
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer);

    /**
     * Initiates a call from the specified device to the specified called number,
     * with extended options.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     * <p>
     * The {@code callingNumber} can be used to present a different calling number on
     * the public network in order to mask the real calling extension number.
     *
     * @param deviceId            the device phone number from which the call is placed; if the
     *                            session is opened by a user, this must be one of the user's devices
     * @param callee              the called phone number
     * @param autoAnswer          if {@code true}, the callee is called immediately; if {@code false},
     *                            the user's device is called first before placing the call to the callee
     * @param inhibitProgressTone {@code true} to inhibit the progress tone on the outbound call
     * @param correlatorData      correlator data to attach to the call
     * @param callingNumber       optional calling number to present to the public network, used to mask
     *                            the real calling extension number
     * @param loginName           the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeCall(String, String, boolean, String)
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
    		CorrelatorData correlatorData, String callingNumber, String loginName);

    /**
     * Initiates a call from the specified device to the specified called number,
     * with extended options.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     *
     * @param deviceId            the device phone number from which the call is placed; if the
     *                            session is opened by a user, this must be one of the user's devices
     * @param callee              the called phone number
     * @param autoAnswer          if {@code true}, the callee is called immediately; if {@code false},
     *                            the user's device is called first before placing the call to the callee
     * @param inhibitProgressTone {@code true} to inhibit the progress tone on the outbound call
     * @param associatedData      correlator data to attach to the call (as a string)
     * @param callingNumber       optional calling number to present to the public network, used to mask
     *                            the real calling extension number
     * @param loginName           the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @deprecated Use {@link #makeCall(String, String, boolean, boolean, CorrelatorData, String, String)} instead.
     */
	@Deprecated
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            String associatedData, String callingNumber, String loginName);

    /**
     * Initiates a call from the specified device to the specified called number,
     * with extended options, for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     * <p>
     * The {@code callingNumber} can be used to present a different calling number on
     * the public network in order to mask the real calling extension number.
     *
     * @param deviceId            the device phone number from which the call is placed; if the
     *                            session is opened by a user, this must be one of the user's devices
     * @param callee              the called phone number
     * @param autoAnswer          if {@code true}, the callee is called immediately; if {@code false},
     *                            the user's device is called first before placing the call to the callee
     * @param inhibitProgressTone {@code true} to inhibit the progress tone on the outbound call
     * @param correlatorData      correlator data to attach to the call
     * @param callingNumber       optional calling number to present to the public network, used to mask
     *                            the real calling extension number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeCall(String, String, boolean, boolean, CorrelatorData, String, String)
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
    		CorrelatorData correlatorData, String callingNumber);

    /**
     * Initiates a call from the specified device to the specified called number,
     * with extended options, for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     *
     * @param deviceId            the device phone number from which the call is placed; if the
     *                            session is opened by a user, this must be one of the user's devices
     * @param callee              the called phone number
     * @param autoAnswer          if {@code true}, the callee is called immediately; if {@code false},
     *                            the user's device is called first before placing the call to the callee
     * @param inhibitProgressTone {@code true} to inhibit the progress tone on the outbound call
     * @param associatedData      correlator data to attach to the call (as a string)
     * @param callingNumber       optional calling number to present to the public network, used to mask
     *                            the real calling extension number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @deprecated Use {@link #makeCall(String, String, boolean, boolean, CorrelatorData, String)} instead.
     */
	@Deprecated
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            String associatedData, String callingNumber);

    /**
     * Initiates a private call to the specified callee, identified by a PIN code.
     * <p>
     * A private call allows the user to flag a call as personal rather than
     * professional, enabling specific charging processing.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param deviceId   the device phone number from which the call is placed; if the
     *                   session is opened by a user, this must be one of the user's devices
     * @param callee     the called phone number
     * @param autoAnswer if {@code true}, the callee is called immediately; if {@code false},
     *                   the user's device is called first before placing the call to the callee
     * @param pin        the PIN code identifying the caller
     * @param secretCode the optional secret code used to confirm the PIN code
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makePrivateCall(String, String, boolean, String, String)
     */
    boolean makePrivateCall(String deviceId, String callee, boolean autoAnswer, String pin, String secretCode,
            String loginName);

    /**
     * Initiates a private call to the specified callee, identified by a PIN code,
     * for the user who opened the session.
     * <p>
     * A private call allows the user to flag a call as personal rather than
     * professional, enabling specific charging processing.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param deviceId   the device phone number from which the call is placed; if the
     *                   session is opened by a user, this must be one of the user's devices
     * @param callee     the called phone number
     * @param autoAnswer if {@code true}, the callee is called immediately; if {@code false},
     *                   the user's device is called first before placing the call to the callee
     * @param pin        the PIN code identifying the caller
     * @param secretCode the optional secret code used to confirm the PIN code
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makePrivateCall(String, String, boolean, String, String, String)
     */
    boolean makePrivateCall(String deviceId, String callee, boolean autoAnswer, String pin, String secretCode);

    /**
     * Initiates a business call to the specified callee, charged to the specified
     * cost center.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     *
     * @param deviceId     the device phone number from which the call is placed; if the
     *                     session is opened by a user, this must be one of the user's devices
     * @param callee       the called phone number
     * @param autoAnswer   if {@code true}, the callee is called immediately; if {@code false},
     *                     the user's device is called first before placing the call to the callee
     * @param businessCode the cost center code to charge the call to
     * @param loginName    the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeBusinessCall(String, String, boolean, String)
     */
    boolean makeBusinessCall(String deviceId, String callee, boolean autoAnswer, String businessCode, String loginName);

    /**
     * Initiates a business call to the specified callee, charged to the specified
     * cost center, for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * If {@code autoAnswer} is set to {@code false}, the user's device is called
     * first before placing the call to the callee; otherwise the callee is called
     * immediately.
     *
     * @param deviceId     the device phone number from which the call is placed; if the
     *                     session is opened by a user, this must be one of the user's devices
     * @param callee       the called phone number
     * @param autoAnswer   if {@code true}, the callee is called immediately; if {@code false},
     *                     the user's device is called first before placing the call to the callee
     * @param businessCode the cost center code to charge the call to
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeBusinessCall(String, String, boolean, String, String)
     */
    boolean makeBusinessCall(String deviceId, String callee, boolean autoAnswer, String businessCode);

    /**
     * Puts an active call on hold and retrieves a call that has been previously put
     * on hold.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user's devices.
     *
     * @param callRef  the call reference of the active call
     * @param deviceId the device phone number for which the operation is performed
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean alternate(String callRef, String deviceId);

    /**
     * Answers an incoming ringing call specified by its reference.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user's devices.
     * <p>
     * Answering a call will fail if the call state is not correct. The state can be
     * checked by listening to the telephony events, and more specifically by
     * checking the capabilities of the involved leg (answer capability on the leg).
     *
     * @param callRef  the call reference of the ringing call
     * @param deviceId the device phone number for which the operation is performed
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean answer(String callRef, String deviceId);

    /**
     * Transfers the active call to another party without keeping control of the call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef    the reference of the active call
     * @param transferTo the phone number to which the call is transferred
     * @param anonymous  if {@code true}, the call is transferred anonymously
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean blindTransfer(String callRef, String transferTo, boolean anonymous, String loginName);

    /**
     * Transfers the active call to another party without keeping control of the call,
     * for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef    the reference of the active call
     * @param transferTo the phone number to which the call is transferred
     * @param anonymous  if {@code true}, the call is transferred anonymously
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #blindTransfer(String, String, boolean, String)
     */
    boolean blindTransfer(String callRef, String transferTo, boolean anonymous);

    /**
     * Transfers the active call to another party without keeping control of the call,
     * and without being anonymous, for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef    the reference of the active call
     * @param transferTo the phone number to which the call is transferred
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #blindTransfer(String, String, boolean, String)
     */
    boolean blindTransfer(String callRef, String transferTo);

    /**
     * Requests a callback on the specified call for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the call reference
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean callback(String callRef, String loginName);

    /**
     * Requests a callback on the specified call for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #callback(String, String)
     */
    boolean callback(String callRef);

    /**
     * Returns the legs involved in the specified call for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the call reference
     * @param loginName the login name
     * @return The collection of {@link Leg} objects in case of success; {@code null}
     *         otherwise.
     */
    Collection<Leg> getLegs(String callRef, String loginName);

    /**
     * Returns the legs involved in the specified call for the user who opened the
     * session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the call reference
     * @return The collection of {@link Leg} objects in case of success; {@code null}
     *         otherwise.
     * @see #getLegs(String, String)
     */
    Collection<Leg> getLegs(String callRef);

    /**
     * Returns the leg specified by its identifier, involved in the specified call,
     * for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the call reference
     * @param legId     the leg identifier
     * @param loginName the login name
     * @return The {@link Leg} in case of success; {@code null} otherwise.
     */
    Leg getLeg(String callRef, String legId, String loginName);

    /**
     * Returns the leg specified by its identifier, involved in the specified call,
     * for the user who opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the call reference
     * @param legId   the leg identifier
     * @return The {@link Leg} in case of success; {@code null} otherwise.
     * @see #getLeg(String, String, String)
     */
    Leg getLeg(String callRef, String legId);

    /**
     * Exits from the specified call for the specified user.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the
     * call carries on without the user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the call reference
     * @param loginName the login name for whom the drop is done
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean dropme(String callRef, String loginName);

    /**
     * Exits from the specified call for the user who opened the session.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the
     * call carries on without the user.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #dropme(String, String)
     */
    boolean dropme(String callRef);

    /**
     * Puts the specified call on hold on the specified device for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the call reference
     * @param deviceId  the device phone number from which the call is put on hold
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean hold(String callRef, String deviceId, String loginName);

    /**
     * Puts the specified call on hold on the specified device for the user who
     * opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef  the call reference
     * @param deviceId the device phone number from which the call is put on hold
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #hold(String, String, String)
     */
    boolean hold(String callRef, String deviceId);

    /**
     * Creates a 3-party conference from the specified active call and the specified
     * held call for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef     the active call reference
     * @param heldCallRef the held call reference
     * @param loginName   the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean merge(String callRef, String heldCallRef, String loginName);

    /**
     * Creates a 3-party conference from the specified active call and the specified
     * held call for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef     the active call reference
     * @param heldCallRef the held call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #merge(String, String, String)
     */
    boolean merge(String callRef, String heldCallRef);

    /**
     * Redirects an outgoing ringing call to the voice mail of the called user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the ringing call reference
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean overflowToVoiceMail(String callRef, String loginName);

    /**
     * Redirects an outgoing ringing call to the voice mail of the called user,
     * for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the ringing call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #overflowToVoiceMail(String, String)
     */
    boolean overflowToVoiceMail(String callRef);

    /**
     * Returns a snapshot of the current telephonic state for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return The {@link TelephonicState} in case of success; {@code null} otherwise.
     */
    TelephonicState getState(String loginName);

    /**
     * Returns a snapshot of the current telephonic state for the user who opened the
     * session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return The {@link TelephonicState} in case of success; {@code null} otherwise.
     * @see #getState(String)
     */
    TelephonicState getState();

    /**
     * Parks the specified active call on a target device.
     * <p>
     * If {@code parkTo} is not provided, the call is parked on the current device.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the active call reference
     * @param parkTo    the target device extension number, or {@code null} to park
     *                  on the current device
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean park(String callRef, String parkTo, String loginName);

    /**
     * Parks the specified active call on a target device, for the user who opened
     * the session.
     * <p>
     * If {@code parkTo} is not provided, the call is parked on the current device.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the active call reference
     * @param parkTo  the target device extension number, or {@code null} to park
     *                on the current device
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #park(String, String, String)
     */
    boolean park(String callRef, String parkTo);

    /**
     * Parks the specified active call on the current device, for the user who opened
     * the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the active call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #park(String, String, String)
     */
    boolean park(String callRef);

    /**
     * Returns the participants of the specified call for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the call reference
     * @param loginName the login name
     * @return The collection of {@link Participant} objects in case of success;
     *         {@code null} otherwise.
     */
    Collection<Participant> getParticipants(String callRef, String loginName);

    /**
     * Returns the participants of the specified call for the user who opened the
     * session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the call reference
     * @return The collection of {@link Participant} objects in case of success;
     *         {@code null} otherwise.
     * @see #getParticipants(String, String)
     */
    Collection<Participant> getParticipants(String callRef);

    /**
     * Returns the specified participant in the specified call for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @param loginName     the login name
     * @return The {@link Participant} in case of success; {@code null} otherwise.
     */
    Participant getParticipant(String callRef, String participantId, String loginName);

    /**
     * Returns the specified participant in the specified call for the user who
     * opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @return The {@link Participant} in case of success; {@code null} otherwise.
     * @see #getParticipant(String, String, String)
     */
    Participant getParticipant(String callRef, String participantId);

    /**
     * Drops the specified participant from the specified call for the specified user.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the
     * call carries on without the participant.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @param loginName     the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean dropParticipant(String callRef, String participantId, String loginName);

    /**
     * Drops the specified participant from the specified call for the user who
     * opened the session.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the
     * call carries on without the participant.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #dropParticipant(String, String, String)
     */
    boolean dropParticipant(String callRef, String participantId);

    /**
     * Releases the specified call; all parties are disconnected.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the call reference
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean release(String callRef, String loginName);

    /**
     * Releases the specified call for the user who opened the session; all parties
     * are disconnected.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #release(String, String)
     */
    boolean release(String callRef);

    /**
     * Releases the current call (active or ringing) to retrieve a previously held
     * call, cancelling a consultation call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef        the held call reference
     * @param deviceId       the device phone number for which the operation is done
     * @param enquiryCallRef the reference of the enquiry call to cancel
     * @param loginName      the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean reconnect(String callRef, String deviceId, String enquiryCallRef, String loginName);

    /**
     * Releases the current call (active or ringing) to retrieve a previously held
     * call, cancelling a consultation call, for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef        the held call reference
     * @param deviceId       the device phone number for which the operation is done
     * @param enquiryCallRef the reference of the enquiry call to cancel
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #reconnect(String, String, String, String)
     */
    boolean reconnect(String callRef, String deviceId, String enquiryCallRef);

    /**
     * Starts, stops, pauses, or resumes the recording of the specified call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the reference of the call to record
     * @param action    the recording action
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean doRecordAction(String callRef, RecordingAction action, String loginName);

    /**
     * Starts, stops, pauses, or resumes the recording of the specified call,
     * for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef the reference of the call to record
     * @param action  the recording action
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #doRecordAction(String, RecordingAction, String)
     */
    boolean doRecordAction(String callRef, RecordingAction action);

    /**
     * Redirects an incoming ringing call to another number or to voice mail, instead
     * of answering it.
     * <p>
     * If {@code redirectTo} is equal to {@code "VOICEMAIL"}, the incoming ringing
     * call is redirected to the user's voice mail.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef    the incoming ringing call reference
     * @param redirectTo the phone number of the redirection, or {@code "VOICEMAIL"}
     * @param anonymous  if {@code true}, the call is redirected anonymously
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean redirect(String callRef, String redirectTo, boolean anonymous, String loginName);

    /**
     * Redirects an incoming ringing call to another number or to voice mail, instead
     * of answering it, for the user who opened the session.
     * <p>
     * If {@code redirectTo} is equal to {@code "VOICEMAIL"}, the incoming ringing
     * call is redirected to the user's voice mail.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef    the incoming ringing call reference
     * @param redirectTo the phone number of the redirection, or {@code "VOICEMAIL"}
     * @param anonymous  if {@code true}, the call is redirected anonymously
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #redirect(String, String, boolean, String)
     */
    boolean redirect(String callRef, String redirectTo, boolean anonymous);

    /**
     * Redirects an incoming ringing call to another number or to voice mail, without
     * being anonymous, for the user who opened the session.
     * <p>
     * If {@code redirectTo} is equal to {@code "VOICEMAIL"}, the incoming ringing
     * call is redirected to the user's voice mail.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef    the incoming ringing call reference
     * @param redirectTo the phone number of the redirection, or {@code "VOICEMAIL"}
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #redirect(String, String, boolean, String)
     */
    boolean redirect(String callRef, String redirectTo);

    /**
     * Retrieves a call that has been previously put on hold, for the user who
     * opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef  the held call reference
     * @param deviceId the device phone number for which the operation is done
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #retrieve(String, String, String)
     */
    boolean retrieve(String callRef, String deviceId);

    /**
     * Retrieves a call that has been previously put on hold.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef   the held call reference
     * @param deviceId  the device phone number for which the operation is done
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean retrieve(String callRef, String deviceId, String loginName);

    /**
     * Sends DTMF codes on the specified active call.
     *
     * @param callRef  the active call reference
     * @param deviceId the device phone number for which the operation is done
     * @param number   the DTMF codes to send
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean sendDtmf(String callRef, String deviceId, String number);

    /**
     * Sends the transaction code for the specified call on the specified device.
     * <p>
     * Used by a CCD agent to send the transaction code at the end of a call.
     * The value must comply with the OmniPCX Enterprise transaction code format
     * (numeric values only).
     *
     * @param callRef     the call reference
     * @param deviceId    the device phone number for which the operation is done
     * @param accountInfo the transaction code (numeric values only)
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean sendAccountInfo(String callRef, String deviceId, String accountInfo);

    /**
     * Transfers the specified active call to the specified held call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callRef     the active call reference
     * @param heldCallRef the held call reference
     * @param loginName   the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean transfer(String callRef, String heldCallRef, String loginName);

    /**
     * Transfers the specified active call to the specified held call, for the user
     * who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callRef     the active call reference
     * @param heldCallRef the held call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #transfer(String, String, String)
     */
    boolean transfer(String callRef, String heldCallRef);

    /**
     * Logs the specified user onto a desk sharing set.
     * <p>
     * The user must be configured as a desk sharing user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param dssDeviceNumber the desk sharing set phone number
     * @param loginName       the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deskSharingLogOff(String)
     */
    boolean deskSharingLogOn(String dssDeviceNumber, String loginName);

    /**
     * Logs the user who opened the session onto a desk sharing set.
     * <p>
     * The user must be configured as a desk sharing user.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param dssDeviceNumber the desk sharing set phone number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deskSharingLogOn(String, String)
     */
    boolean deskSharingLogOn(String dssDeviceNumber);

    /**
     * Logs the specified user off from their desk sharing set.
     * <p>
     * The user must be configured as a desk sharing user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deskSharingLogOn(String, String)
     */
    boolean deskSharingLogOff(String loginName);

    /**
     * Logs the user who opened the session off from their desk sharing set.
     * <p>
     * The user must be configured as a desk sharing user.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deskSharingLogOff(String)
     */
    boolean deskSharingLogOff();

    /**
     * Returns the operational state of all devices belonging to the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return The collection of {@link DeviceState} objects in case of success;
     *         {@code null} otherwise.
     */
    Collection<DeviceState> getDevicesState(String loginName);

    /**
     * Returns the operational state of all devices belonging to the user who opened
     * the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return The collection of {@link DeviceState} objects in case of success;
     *         {@code null} otherwise.
     * @see #getDevicesState(String)
     */
    Collection<DeviceState> getDevicesState();

    /**
     * Returns the operational state of the specified device of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param deviceId  the device phone number
     * @param loginName the login name
     * @return The {@link DeviceState} in case of success; {@code null} otherwise.
     * @see #getDevicesState(String)
     */
    DeviceState getDeviceState(String deviceId, String loginName);

    /**
     * Returns the operational state of the specified device of the user who opened
     * the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param deviceId the device phone number
     * @return The {@link DeviceState} in case of success; {@code null} otherwise.
     * @see #getDeviceState(String, String)
     */
    DeviceState getDeviceState(String deviceId);

    /**
     * Toggles interphony or hands-free mode on the specified device.
     * <ul>
     * <li>activates or deactivates the microphone if the device has an outgoing or
     * established call
     * <li>activates or deactivates the interphony if the device is idle
     * <li>has no effect if the device is ringing on an incoming call
     * </ul>
     * <p>
     * This operation is done in blind mode: no state event is raised on the push,
     * but when the device returns to idle after a call, the microphone comes back
     * to the active state.
     *
     * @param deviceId the device phone number for which the operation is done
     * @return {@code true} in case of success; {@code false} otherwise.
     * @since 2.6
     */
    boolean toggleInterphony(String deviceId);

    /**
     * Picks up an incoming call ringing on another user's device.
     *
     * @param deviceId         the device phone number from which the pickup is
     *                         performed; if the session is opened by a user, this
     *                         must be one of the user's devices
     * @param otherCallRef     the reference of the call to pick up on the remote user
     * @param otherPhoneNumber the phone number on which the call is ringing
     * @param autoAnswer       if {@code true}, the call is automatically answered
     *                         after pickup
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean pickUp(String deviceId, String otherCallRef, String otherPhoneNumber, boolean autoAnswer);

    /**
     * Picks up an incoming call ringing on another user's device, without automatic
     * answer.
     *
     * @param deviceId         the device phone number from which the pickup is
     *                         performed; if the session is opened by a user, this
     *                         must be one of the user's devices
     * @param otherCallRef     the reference of the call to pick up on the remote user
     * @param otherPhoneNumber the phone number on which the call is ringing
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #pickUp(String, String, String, boolean)
     */
    boolean pickUp(String deviceId, String otherCallRef, String otherPhoneNumber);

    /**
     * Unparks a previously parked call onto the specified device.
     *
     * @param heldCallRef the reference of the parked call
     * @param deviceId    the device from which the unpark request is made
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean unPark(String heldCallRef, String deviceId);

    /**
     * Intrudes into the active call of a busy user.
     * <p>
     * Intrusion requires that the current device is in releasing state while calling
     * a user who is engaged in a call, and that both the current device and the
     * engaged users have the intrusion capability configured.
     * <p>
     * Available from O2G 2.4.
     *
     * @param deviceId the device from which the intrusion is initiated
     * @return {@code true} in case of success; {@code false} otherwise.
     * @since O2G 2.4
     */
    boolean intrusion(String deviceId);

    /**
     * Returns the hunting group login status of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return The {@link HuntingGroupStatus} in case of success; {@code null}
     *         otherwise.
     */
    HuntingGroupStatus getHuntingGroupStatus(String loginName);

    /**
     * Returns the hunting group login status of the user who opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return The {@link HuntingGroupStatus} in case of success; {@code null}
     *         otherwise.
     * @see #getHuntingGroupStatus(String)
     */
    HuntingGroupStatus getHuntingGroupStatus();

    /**
     * Logs the specified user into their current hunting group.
     * <p>
     * The user must be configured as a member of a hunting group.
     * Has no effect and returns {@code true} if the user is already logged in.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #huntingGroupLogOn(String)
     * @see #huntingGroupLogOff(String)
     */
    boolean huntingGroupLogOn(String loginName);

    /**
     * Logs the user who opened the session into their current hunting group.
     * <p>
     * The user must be configured as a member of a hunting group.
     * Has no effect and returns {@code true} if the user is already logged in.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #huntingGroupLogOn(String)
     */
    boolean huntingGroupLogOn();

    /**
     * Logs the specified user off from their current hunting group.
     * <p>
     * The user must be configured as a member of a hunting group.
     * Has no effect and returns {@code true} if the user is already logged off.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #huntingGroupLogOn(String)
     */
    boolean huntingGroupLogOff(String loginName);

    /**
     * Logs the user who opened the session off from their current hunting group.
     * <p>
     * The user must be configured as a member of a hunting group.
     * Has no effect and returns {@code true} if the user is already logged off.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #huntingGroupLogOff(String)
     */
    boolean huntingGroupLogOff();

    /**
     * Adds the specified user as a member of an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user
     * already belongs to the group, nothing is done and {@code true} is returned.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param hgNumber  the hunting group number
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @deprecated Use {@link #addMeToHuntingGroup(String, String)} instead.
     */
	@Deprecated
    boolean addHuntingGroupMember(String hgNumber, String loginName);

    /**
     * Adds the user who opened the session as a member of an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user
     * already belongs to the group, nothing is done and {@code true} is returned.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param hgNumber the hunting group number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @deprecated Use {@link #addMeToHuntingGroup(String)} instead.
     */
	@Deprecated
    boolean addHuntingGroupMember(String hgNumber);

    /**
     * Removes the specified user from an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user does
     * not belong to the group, nothing is done and {@code true} is returned.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param hgNumber  the hunting group number
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @deprecated Use {@link #removeMeFromHuntingGroup(String, String)} instead.
     */
	@Deprecated
    boolean deleteHuntingGroupMember(String hgNumber, String loginName);

    /**
     * Removes the user who opened the session from an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user does
     * not belong to the group, nothing is done and {@code true} is returned.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param hgNumber the hunting group number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @deprecated Use {@link #removeMeFromHuntingGroup(String)} instead.
     */
	@Deprecated
    boolean deleteHuntingGroupMember(String hgNumber);

    
    /**
     * Adds the specified user as a member of an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user
     * already belongs to the group, nothing is done and {@code true} is returned.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param hgNumber  the hunting group number
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #removeMeFromHuntingGroup(String, String)
     */
    boolean addMeToHuntingGroup(String hgNumber, String loginName);

    /**
     * Adds the user who opened the session as a member of an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user
     * already belongs to the group, nothing is done and {@code true} is returned.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param hgNumber the hunting group number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #addMeToHuntingGroup(String, String)
     */
    boolean addMeToHuntingGroup(String hgNumber);

    /**
     * Removes the specified user from an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user does
     * not belong to the group, nothing is done and {@code true} is returned.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param hgNumber  the hunting group number
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #addMeToHuntingGroup(String, String)
     */
    boolean removeMeFromHuntingGroup(String hgNumber, String loginName);

    /**
     * Removes the user who opened the session from an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user does
     * not belong to the group, nothing is done and {@code true} is returned.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param hgNumber the hunting group number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #removeMeFromHuntingGroup(String, String)
     */
    boolean removeMeFromHuntingGroup(String hgNumber);

    /**
     * Returns the hunting groups available on the OmniPCX Enterprise node the
     * specified user belongs to.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return The {@link HuntingGroups} in case of success; {@code null} otherwise.
     */
    HuntingGroups queryHuntingGroups(String loginName);

    /**
     * Returns the hunting groups available on the OmniPCX Enterprise node the user
     * who opened the session belongs to.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return The {@link HuntingGroups} in case of success; {@code null} otherwise.
     * @see #queryHuntingGroups(String)
     */
    HuntingGroups queryHuntingGroups();

    /**
     * Returns the pending callback requests for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return The collection of {@link Callback} objects in case of success;
     *         {@code null} otherwise.
     */
    Collection<Callback> getCallbacks(String loginName);

    /**
     * Returns the pending callback requests for the user who opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return The collection of {@link Callback} objects in case of success;
     *         {@code null} otherwise.
     * @see #getCallbacks(String)
     */
    Collection<Callback> getCallbacks();

    /**
     * Deletes all pending callback requests for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteCallbacks(String loginName);

    /**
     * Deletes all pending callback requests for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deleteCallbacks(String)
     */
    boolean deleteCallbacks();

    /**
     * Deletes the specified callback request for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callbackId the callback identifier as returned by {@link #getCallbacks(String)}
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteCallback(String callbackId, String loginName);

    /**
     * Deletes the specified callback request for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callbackId the callback identifier as returned by {@link #getCallbacks(String)}
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deleteCallback(String, String)
     */
    boolean deleteCallback(String callbackId);

    /**
     * Returns the next unread mini message for the specified user.
     * <p>
     * Messages are consumed on read — once retrieved, a message is deleted from the
     * OXE and cannot be read again. Messages are returned in Last In First Out order.
     * Returns {@code null} when there are no more unread messages.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return The {@link MiniMessage} on success; {@code null} if there are no
     *         unread messages or on error.
     */
    MiniMessage getMiniMessage(String loginName);

    /**
     * Returns the next unread mini message for the user who opened the session.
     * <p>
     * Messages are consumed on read — once retrieved, a message is deleted from the
     * OXE and cannot be read again. Messages are returned in Last In First Out order.
     * Returns {@code null} when there are no more unread messages.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return The {@link MiniMessage} on success; {@code null} if there are no
     *         unread messages or on error.
     * @see #getMiniMessage(String)
     */
    MiniMessage getMiniMessage();

    /**
     * Sends a mini message to the specified recipient.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param recipient the phone number of the message recipient
     * @param message   the message text
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean sendMiniMessage(String recipient, String message, String loginName);

    /**
     * Sends a mini message to the specified recipient, for the user who opened the
     * session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param recipient the phone number of the message recipient
     * @param message   the message text
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #sendMiniMessage(String, String, String)
     */
    boolean sendMiniMessage(String recipient, String message);

    /**
     * Requests a callback from an idle device of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param callee    the phone number of the called party for which a callback is
     *                  requested
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestCallback(String callee, String loginName);

    /**
     * Requests a callback from an idle device, for the user who opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param callee the phone number of the called party for which a callback is
     *               requested
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #requestCallback(String, String)
     */
    boolean requestCallback(String callee);

    /**
     * Returns transfer possibilities for the specified CCD pilot.
     * <p>
     * The {@code pilotTransferQueryParam} defines optional filtering criteria such
     * as agent number, priority transfer, supervised transfer, or call profile.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param nodeId                  the OmniPCX Enterprise node ID
     * @param pilotNumber             the CCD pilot directory number
     * @param pilotTransferQueryParam optional query criteria to filter results by agent number,
     *                                priority transfer, supervised transfer, or call profile
     * @param loginName               the login name
     * @return The {@link PilotInfo} describing the pilot's queue state and transfer possibilities;
     *         {@code null} otherwise.
     * @since 2.7.4
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber, PilotTransferQueryParameters pilotTransferQueryParam,
            String loginName);

    /**
     * Returns transfer possibilities for the specified CCD pilot without any
     * transfer criteria.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param nodeId      the OmniPCX Enterprise node ID
     * @param pilotNumber the CCD pilot directory number
     * @param loginName   the login name
     * @return The {@link PilotInfo} describing the pilot's queue state and transfer possibilities;
     *         {@code null} otherwise.
     * @since 2.7
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber, String loginName);

    /**
     * Returns transfer possibilities for the specified CCD pilot, for the user who
     * opened the session.
     * <p>
     * The {@code pilotTransferQueryParam} defines optional filtering criteria such
     * as agent number, priority transfer, supervised transfer, or call profile.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param nodeId                  the OmniPCX Enterprise node ID
     * @param pilotNumber             the CCD pilot directory number
     * @param pilotTransferQueryParam optional query criteria to filter results by agent number,
     *                                priority transfer, supervised transfer, or call profile
     * @return The {@link PilotInfo} describing the pilot's queue state and transfer possibilities;
     *         {@code null} otherwise.
     * @since 2.7.4
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber, PilotTransferQueryParameters pilotTransferQueryParam);

    /**
     * Returns transfer possibilities for the specified CCD pilot without any
     * transfer criteria, for the user who opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param nodeId      the OmniPCX Enterprise node ID
     * @param pilotNumber the CCD pilot directory number
     * @return The {@link PilotInfo} describing the pilot's queue state and transfer possibilities;
     *         {@code null} otherwise.
     * @since 2.7
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber);

    /**
     * Requests a snapshot event to receive the current telephonic state via an
     * {@code OnTelephonyState} event.
     * <p>
     * The resulting event will contain the full {@link TelephonicState} including
     * active calls and device capabilities. If a second request is issued while the
     * first is still in progress, it has no effect.
     * <p>
     * If an administrator calls this with a {@code null} {@code loginName}, the
     * snapshot is requested for all users, which may take time depending on the
     * number of users.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnapshot(String loginName);

    /**
     * Requests a snapshot event to receive the current telephonic state via an
     * {@code OnTelephonyState} event, for all users (when invoked by an
     * administrator) or for the user who opened the session.
     * <p>
     * The resulting event will contain the full {@link TelephonicState} including
     * active calls and device capabilities. If a second request is issued while the
     * first is still in progress, it has no effect.
     * <p>
     * If an administrator calls this method, the snapshot is requested for all
     * users, which may take a long time depending on the number of users.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #requestSnapshot(String)
     */
    boolean requestSnapshot();
}
