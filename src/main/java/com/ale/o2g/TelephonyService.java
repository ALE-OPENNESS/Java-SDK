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
 * The {@code TelephonyService} allows a user to initiate a call and to activate
 * any kind of OmniPCX Enterprise telephony services.
 * <p>
 * Using this service requires having a <b>TELEPHONY_ADVANCED</b> license,
 * except for the 3 basic services
 * {@link #basicMakeCall(String, String, boolean) basicMakeCall},
 * {@link #basicAnswerCall(String) basicAnswerCall} and
 * {@link #basicDropMe(String) basicDropme} that are available without any
 * license.
 * 
 */
public interface TelephonyService extends IService {

    /**
     * Initiates a call from the specified device to the specified called number.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately
     * 
     * @param deviceId   the device phone number for which the call is made
     * @param callee     the called number
     * @param autoAnswer automatic answer on make call.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean basicMakeCall(String deviceId, String callee, boolean autoAnswer);

    /**
     * Initiates a call from the specified device to the specified called number,
     * with auto answer.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user.
     * 
     * @param deviceId the device phone number for which the call is made
     * @param callee   the called number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #basicMakeCall(String deviceId, String callee, boolean autoAnswer)
     */
    boolean basicMakeCall(String deviceId, String callee);

    /**
     * Answers to an incoming ringing call on the specified device.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user.
     * 
     * @param deviceId the device phone number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean basicAnswerCall(String deviceId);

    /**
     * Exits from the call for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * if the call is a single call, it is released; if it is a conference, the call
     * carries on without the user.
     * 
     * @param loginName the login name for whom the drop is done
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean basicDropMe(String loginName);

    /**
     * Exits from the call for the user who opened the session.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the call
     * carries on without the user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #basicDropMe(String loginName)
     */
    boolean basicDropMe();

    /**
     * Retrieves the calls in progress for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return the collection of calls in progress in case of success; {@code null}
     *         otherwise.
     */
    Collection<Call> getCalls(String loginName);

    /**
     * Retrieves the calls in progress for the user who opened the session.
     * <p>
     * This method will return {@code null} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return The collection of calls in progress in case of success; {@code null}
     *         otherwise.
     * @see #getCalls(String loginName)
     */
    Collection<Call> getCalls();

    /**
     * Returns the call specified by the call reference for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the call reference
     * @param loginName the login name
     * @return the call in case of success; {@code null} otherwise.
     */
    Call getCall(String callRef, String loginName);

    /**
     * Returns the call specified by the call reference for the user who opened the
     * session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param callRef the call reference
     * @return the call in case of success; {@code null} otherwise.
     */
    Call getCall(String callRef);

    /**
     * Attachs the specified correlator data to the specified call.
     * <p>
     * This is used by the application to provide application-related information
     * (limited to 32 bytes). In general, it is used to give information concerning
     * a previously established call to the party of a second call.
     * 
     * @param callRef        the call reference
     * @param deviceId       the device phone number for which the operation is
     *                       invoked. If the session is opened by a User, the device
     *                       phone number must be one of the user.
     * @param correlatorData the correlator data to add
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
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately
     * 
     * @param deviceId   the device phone number for which the call is made
     * @param callee     the called number
     * @param autoAnswer automatic answer on make call.
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, String loginName);

    /**
     * Initiates a call from the specified device to the specified called number for
     * the user who opened the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately
     * 
     * @param deviceId   the device phone number for which the call is made
     * @param callee     the called number
     * @param autoAnswer automatic answer on make call.
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeCall(String deviceId, String callee, boolean autoAnswer, String
     *      loginName)
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer);

    /**
     * Initiates a new call to another user (the callee), using the specified
     * deviceId and options.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately
     * 
     * @param deviceId            the device phone number for which the call is made
     * @param callee              the called number
     * @param autoAnswer          automatic answer on make call
     * @param inhibitProgressTone allows to inhibit the progress tone on the current
     *                            external call
     * @param associatedData      correlator data to add to the call
     * @param callingNumber       calling number to present to the public network
     * @param loginName           the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeCall(String deviceId, String callee, boolean autoAnswer, String
     *      loginName)
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            String associatedData, String callingNumber, String loginName);

    /**
     * Initiates a new call to another user (the callee), using the specified
     * deviceId and options.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately
     * 
     * @param deviceId            the device phone number for which the call is made
     * @param callee              the called number
     * @param autoAnswer          automatic answer on make call
     * @param inhibitProgressTone allows to inhibit the progress tone on the current
     *                            external call
     * @param associatedData      correlator data to add to the call
     * @param callingNumber       calling number to present to the public network
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makeCall(String, String, boolean, boolean, String, String, String)
     */
    boolean makeCall(String deviceId, String callee, boolean autoAnswer, boolean inhibitProgressTone,
            String associatedData, String callingNumber);

    /**
     * Initiates a new private call to another user (the callee), using a pin code
     * and an optional secret code.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately.
     * 
     * <p>
     * The private call is a service which allows a user to specify that the
     * external call made is personal and not professional. The charging for this
     * type of call can then be given specific processing. It requires the user
     * enters a PIN code (Personal Identification Number)
     * 
     * @param deviceId   the device phone number for which the call is made
     * @param callee     the called number
     * @param autoAnswer automatic answer on make call
     * @param pin        the PIN code to identify the caller
     * @param secretCode the optional secret code used to confirm the PIN code
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makePrivateCall(String, String, boolean, String, String)
     */
    boolean makePrivateCall(String deviceId, String callee, boolean autoAnswer, String pin, String secretCode,
            String loginName);

    /**
     * Initiates a new private call to another user (the callee), using a pin code
     * and an optional secret code.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately.
     * 
     * <p>
     * The private call is a service which allows a user to specify that the
     * external call made is personal and not professional. The charging for this
     * type of call can then be given specific processing. It requires the user
     * enters a PIN code (Personal Identification Number)
     * 
     * @param deviceId   the device phone number for which the call is made
     * @param callee     the called number
     * @param autoAnswer automatic answer on make call
     * @param pin        the PIN code to identify the caller
     * @param secretCode the optional secret code used to confirm the PIN code
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #makePrivateCall(String, String, boolean, String, String, String)
     */
    boolean makePrivateCall(String deviceId, String callee, boolean autoAnswer, String pin, String secretCode);

    /**
     * Initiates a new business call to another user (the callee), using the
     * specified business code.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately.
     * 
     * @param deviceId     the device phone number for which the call is made
     * @param callee       the called number
     * @param autoAnswer   automatic answer on make call
     * @param businessCode the cost center on which the call will be charged.
     * @param loginName    the login name
     * @return {@code true} in case of success; {@code false} otherwise. #see
     *         {@link #makeBusinessCall(String, String, boolean, String)}
     */
    boolean makeBusinessCall(String deviceId, String callee, boolean autoAnswer, String businessCode, String loginName);

    /**
     * Initiates a new business call to another user (the callee), using the
     * specified business code.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * <p>
     * If the automatic answer on make call {@code autoAnswer} parameter is set to
     * {@code false} the deviceId is called before launching the make call to
     * callee, else callee is called immediately.
     * 
     * @param deviceId     the device phone number for which the call is made
     * @param callee       the called number
     * @param autoAnswer   automatic answer on make call
     * @param businessCode the cost center on which the call will be charged.
     * @return {@code true} in case of success; {@code false} otherwise. #see
     *         {@link #makeBusinessCall(String, String, boolean, String)}
     */
    boolean makeBusinessCall(String deviceId, String callee, boolean autoAnswer, String businessCode);

    /**
     * Puts an active call on hold and retrieve a call that has been previously put
     * in hold.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user.
     * 
     * @param callRef  the call reference of the call on hold
     * @param deviceId the device phone number for which the operation is done
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean alternate(String callRef, String deviceId);

    /**
     * Answers to an incoming ringing call specified by it reference.
     * <p>
     * If the session is opened by a user, the device phone number must be one of
     * the user.
     * <p>
     * Answering a call will fail if the call state is not correct. The state can be
     * checked by listening to the telephony events, and more specifically by
     * checking the capabilities of the involved leg. (answer capability on the
     * leg).
     * 
     * @param callRef  the call reference of the call on hold
     * @param deviceId the device phone number for which the operation is done
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean answer(String callRef, String deviceId);

    /**
     * Transfers the active call to another user, without keeping control on this
     * call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef    the reference of the active call
     * @param transferTo the phone number to which the call is transfered
     * @param anonymous  anonymous transfer if this parameter is {@code true}, the
     *                   call will be transfered as anonymous
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean blindTransfer(String callRef, String transferTo, boolean anonymous, String loginName);

    /**
     * Transfers the active call to another user, without keeping control on this
     * call.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef    the reference of the active call
     * @param transferTo the phone number to which the call is transfered
     * @param anonymous  anonymous transfer if this parameter is {@code true}, the
     *                   call will be transfered as anonymous
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #blindTransfer(String callRef, String transferTo, boolean anonymous,
     *      String loginName)
     */
    boolean blindTransfer(String callRef, String transferTo, boolean anonymous);

    /**
     * Transfers the active call to another user, without keeping control on this
     * call, and without being anonymous.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef    the reference of the active call
     * @param transferTo the phone number to which the call is transfered
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #blindTransfer(String callRef, String transferTo, boolean anonymous,
     *      String loginName)
     */
    boolean blindTransfer(String callRef, String transferTo);

    /**
     * Requests a callback on the call specified by the call reference for the
     * specified user.
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
     * Requests a callback on the call specified by the call reference for the user
     * who opened the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #callback(String callRef, String loginName)
     */
    boolean callback(String callRef);

    /**
     * Returns the legs involved by the call specified by the call reference for the
     * specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the call reference
     * @param loginName the login name
     * @return the collection of legs.
     */
    Collection<Leg> getLegs(String callRef, String loginName);

    /**
     * Returns the legs involved by the call specified by the call reference for the
     * user who opened the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the call reference
     * @return the collection of legs.
     * @see #getLegs(String callRef, String loginName)
     */
    Collection<Leg> getLegs(String callRef);

    /**
     * Returns the leg specified by its id, involved by the call specified by the
     * call reference for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the call reference
     * @param legId     the leg identifier
     * @param loginName the login name
     * @return the leg
     */
    Leg getLeg(String callRef, String legId, String loginName);

    /**
     * Returns the leg specified by its id, involved by the call specified by the
     * call reference for the user who opened the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the call reference
     * @param legId   the leg identifier
     * @return the leg
     * @see #getLeg(String callRef, String legId, String loginName)
     */
    Leg getLeg(String callRef, String legId);

    /**
     * Exits from the call specified by its reference for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * if the call is a single call, it is released; if it is a conference, the call
     * carries on without the user.
     * 
     * @param callRef   the call reference
     * @param loginName the login name for whom the drop is done
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean dropme(String callRef, String loginName);

    /**
     * Exits from the call specified by its reference for the user who opened the
     * session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * <p>
     * if the call is a single call, it is released; if it is a conference, the call
     * carries on without the user.
     * 
     * @param callRef the call reference
     * @return {@code true} in case of success; {@code false} otherwise. #see
     *         dropme(String callRef, String loginName)
     */
    boolean dropme(String callRef);

    /**
     * Puts on hold the call specified by its reference, on the specified device,
     * for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the call reference
     * @param deviceId  the device phone number from which the call put on hold
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean hold(String callRef, String deviceId, String loginName);

    /**
     * Puts on hold the call specified by its reference, on the specified device,
     * for the user who opened the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef  the call reference
     * @param deviceId the device phone number for which the call is made
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #hold(String callRef, String deviceId, String loginName)
     */
    boolean hold(String callRef, String deviceId);

    /**
     * Makes a 3-party conference with a specified active call and a specified held
     * call for the specified user.
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
     * Makes a 3-party conference with a specified active call and a specified held
     * call , for the user who opened the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef     the active call reference
     * @param heldCallRef the held call reference
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #merge(String callRef, String heldCallRef, String loginName)
     */
    boolean merge(String callRef, String heldCallRef);

    /**
     * Redirects an outgoing ringing call specified by its reference to the voice
     * mail of the called user.
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
     * Redirects an outgoing ringing call specified by its reference to the voice
     * mail of the called user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the ringing call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #overflowToVoiceMail(String callRef, String loginName)
     */
    boolean overflowToVoiceMail(String callRef);

    /**
     * Gets the telephonic state and capabilities for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return The telephonic state in case of success; {@code null} otherwise.
     */
    TelephonicState getState(String loginName);

    /**
     * Gets the telephonic state and capabilities for the user who opened the
     * session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return The telephonic state in case of success; {@code null} otherwise.
     * @see #getState(String loginName)
     */
    TelephonicState getState();

    /**
     * Parks the specified active call to a target device. If the device is not
     * provided, the call will be parked on the current device.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the active call reference
     * @param parkTo    the target device
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean park(String callRef, String parkTo, String loginName);

    /**
     * Parks the specified active call to a target device. If the device is not
     * provided, the call will be parked on the current device.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the active call reference
     * @param parkTo  the target device
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #park(String callRef, String parkTo, String loginName)
     */
    boolean park(String callRef, String parkTo);

    /**
     * Parks the specified active call on the current device.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the active call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #park(String callRef, String parkTo, String loginName)
     */
    boolean park(String callRef);

    /**
     * Returns the list of participants in the specified call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the call reference
     * @param loginName the login name
     * @return the collection of participants in case of success; {@code null}
     *         otherwise.
     */
    Collection<Participant> getParticipants(String callRef, String loginName);

    /**
     * Returns the list of participants in a the specified call.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the call reference
     * @return the collection of participants in case of success; {@code null}
     *         otherwise.
     * @see #getParticipants(String callRef, String loginName)
     */
    Collection<Participant> getParticipants(String callRef);

    /**
     * Returns the specified participant in the specified call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @param loginName     the login name
     * @return the participant in case of success; {@code null} otherwise.
     */
    Participant getParticipant(String callRef, String participantId, String loginName);

    /**
     * Returns the specified participant in the specified call.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @return the participant in case of success; {@code null} otherwise.
     * @see #getParticipant(String callRef, String participantId, String loginName)
     */
    Participant getParticipant(String callRef, String participantId);

    /**
     * Drops the specified participant from the specified call for the specified
     * user.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the call
     * carries on without the participant.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @param loginName     the login name
     * @return the participant in case of success; {@code null} otherwise.
     */
    boolean dropParticipant(String callRef, String participantId, String loginName);

    /**
     * Drops the specified participant from the specified call for the user who
     * opened the session.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the call
     * carries on without the participant.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef       the call reference
     * @param participantId the participant identifier
     * @return the participant in case of success; {@code null} otherwise.
     * @see #dropParticipant(String callRef, String participantId, String loginName)
     */
    boolean dropParticipant(String callRef, String participantId);

    /**
     * Hangs on an active call, all the parties are released.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the call reference to hang on
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean release(String callRef, String loginName);

    /**
     * Hangs on an active call, all the parties are released.
     * <p>
     * If the call is a single call, it is released; if it is a conference, the call
     * carries on without the participant.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the call reference to hang on
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #release(String callRef, String loginName)
     */
    boolean release(String callRef);

    /**
     * Releases the current call (active or ringing) to retrieve a previously put in
     * hold call (cancel a consultation call).
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
     * Releases the current call (active or ringing) to retrieve a previously put in
     * hold call (cancel a consultation call).
     * <p>
     * If the call is a single call, it is released; if it is a conference, the call
     * carries on without the participant.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef        the held call reference
     * @param deviceId       the device phone number for which the operation is done
     * @param enquiryCallRef the reference of the enquiry call to cancel
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #reconnect(String callRef, String deviceId, String enquiryCallRef,
     *      String loginName)
     */
    boolean reconnect(String callRef, String deviceId, String enquiryCallRef);

    /**
     * Starts, stops, pauses or resumes the recording of a the specified call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef   the reference of the recorded call
     * @param action    the recording action
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean doRecordAction(String callRef, RecordingAction action, String loginName);

    /**
     * Starts, stops, pauses or resumes the recording of a the specified call.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef the reference of the recorded call
     * @param action  the recording action
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #doRecordAction(String callRef, RecordingAction action, String
     *      loginName)
     */
    boolean doRecordAction(String callRef, RecordingAction action);

    /**
     * Redirects an incoming ringing call to another user or number, instead of
     * responding to it. If {@code redirectTo} is equal to {@code VOICEMAIL},
     * redirect the incoming ringing call to the user voice mail.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callRef    the incoming ringing call reference
     * @param redirectTo Phone number of the redirection, or "VOICEMAIL"
     * @param anonymous  anonymous redirection if this parameter is {@code true},
     *                   the call will be redirected as anonymous
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean redirect(String callRef, String redirectTo, boolean anonymous, String loginName);

    /**
     * Redirects an incoming ringing call to another user or number, instead of
     * responding to it. If {@code redirectTo} is equal to {@code VOICEMAIL},
     * redirect the incoming ringing call to the user voice mail.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef    the incoming ringing call reference
     * @param redirectTo Phone number of the redirection, or "VOICEMAIL"
     * @param anonymous  anonymous redirection if this parameter is {@code true},
     *                   the call will be redirected as anonymous
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #redirect(String callRef, String redirectTo, boolean anonymous, String
     *      loginName)
     */
    boolean redirect(String callRef, String redirectTo, boolean anonymous);

    /**
     * Redirects an incoming ringing call to another user or number, instead of
     * responding to it. If {@code redirectTo} is equal to {@code VOICEMAIL},
     * redirect the incoming ringing call to the user voice mail. without being
     * anonymous.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef    the incoming ringing call reference
     * @param redirectTo Phone number of the redirection, or "VOICEMAIL"
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #redirect(String callRef, String redirectTo, boolean anonymous, String
     *      loginName)
     */
    boolean redirect(String callRef, String redirectTo);

    /**
     * Retrieves a call that has been previously put in hold.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef  the held call reference
     * @param deviceId the device phone number for which the operation is done
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean retrieve(String callRef, String deviceId);

    /**
     * Retrieves a call that has been previously put in hold.
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
     * Sends the account info for the specified call, on the specified device.
     * <p>
     * This operation is used by a CCD agent to send the transaction code at the end
     * of the call. The string value MUST complain with the transaction code
     * accepted by OXE (that is numerical value only)
     * 
     * @param callRef     the call reference
     * @param deviceId    the device phone number for which the operation is done
     * @param accountInfo the transaction code
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean sendAccountInfo(String callRef, String deviceId, String accountInfo);

    /**
     * Transfers a specified active call to a specified held call for the specified
     * user.
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
     * Transfers a specified active call to a specified held call for the specified
     * user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callRef     the active call reference
     * @param heldCallRef the held call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #transfer(String callRef, String heldCallRef, String loginName)
     */
    boolean transfer(String callRef, String heldCallRef);

    /**
     * Logs the specified user on a specified desk sharing set.
     * <p>
     * The user must be configured as a Desk sharing user.
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
     * Logs on the user who has open the session on a specified device desk sharing
     * set.
     * <p>
     * The user must be configured as a Desk sharing user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param dssDeviceNumber the desk sharing set phone number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deskSharingLogOn(String dssDeviceNumber, String loginName)
     */
    boolean deskSharingLogOn(String dssDeviceNumber);

    /**
     * Logs off the specified user from the desk sharing set.
     * <p>
     * The user must be configured as a Desk sharing user.
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
     * Logs off the user who has open the session from the desk sharing set.
     * <p>
     * The user must be configured as a Desk sharing user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deskSharingLogOff(String loginName)
     */
    boolean deskSharingLogOff();

    /**
     * Gets states of all devices of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return The collection of device state in case of success; {@code null}
     *         otherwise.
     */
    Collection<DeviceState> getDevicesState(String loginName);

    /**
     * Gets states of all devices of the user who has open the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return The collection of device state in case of success; {@code null}
     *         otherwise.
     * @see #getDevicesState(String loginName)
     */
    Collection<DeviceState> getDevicesState();

    /**
     * Gets state of the specified device of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param deviceId  the device phone number for which the operation is done
     * @param loginName the login name
     * @return The device state in case of success; {@code null} otherwise.
     */
    DeviceState getDeviceState(String deviceId, String loginName);

    /**
     * Gets state of the specified device of the user who has open the session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param deviceId the device phone number for which the operation is done
     * @return The device state in case of success; {@code null} otherwise.
     */
    DeviceState getDeviceState(String deviceId);

    /**
     * Activate or deactivate the interphony by simulating pressing the key on the
     * specified device.
     * <ul>
     * <li>it activates or deactivates the microphone if the device has an outgoing
     * or established call
     * <li>it activates or deactivates the interphony if the device is idle
     * <li>it has no effect if the device is ringing on incoming call
     * </ul>
     * <p>
     * This operation is done in blind mode: no state event is provided on the push
     * but when the device returns to idle after a call, the microphone comes back
     * in the active state.
     * 
     * @param deviceId the device phone number for which the operation is done
     * @return {@code true} in case of success; {@code false} otherwise.
     * @since 2.6
     */
    boolean toggleInterphony(String deviceId);

    /**
     * Picks up the specified incoming call for another user.
     * 
     * @param deviceId         the device phone number for which the operation is
     *                         done
     * @param otherCallRef     reference of the call to pickup (on the remote user)
     * @param otherPhoneNumber the phone number on which the call is ringing
     * @param autoAnswer       {@code true} to automatically answer the call after
     *                         the pickup.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean pickUp(String deviceId, String otherCallRef, String otherPhoneNumber, boolean autoAnswer);

    /**
     * Picks up the specified incoming call for another user, without auto answer.
     * 
     * @param deviceId         the device phone number for which the operation is
     *                         done
     * @param otherCallRef     Reference of the call to pickup (on the remote user)
     * @param otherPhoneNumber the phone number on which the call is to pickup
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #pickUp(String deviceId, String otherCallRef, String otherPhoneNumber,
     *      boolean autoAnswer)
     */
    boolean pickUp(String deviceId, String otherCallRef, String otherPhoneNumber);

    /**
     * UnParks a call from a target device.
     * 
     * @param heldCallRef Reference of the held call.
     * @param deviceId    the device from where the unpark request is requested.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean unPark(String heldCallRef, String deviceId);

    /**
     * Performs an intrusion in the active call of a called user.
     * <p>
     * No parameter is required to invoke the intrusion: it only depends on the
     * current capability intrusion of the current device. It is based on the fact
     * that the current device must be in releasing state while calling a user which
     * is in busy call with another user, the current device has the intrusion
     * capability and the 2 users engaged in the call have the capability to allow
     * intrusion.
     * </p>
     * 
     * @param deviceId the device from where the unpark request is requested.
     * @return {@code true} in case of success; {@code false} otherwise.
     * @since O2G 2.4
     */
    boolean intrusion(String deviceId);

    /**
     * Retrieves the specified user hunting group status.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return the hunting group status in case of success; {@code null} otherwise.
     */
    HuntingGroupStatus getHuntingGroupStatus(String loginName);

    /**
     * Retrieves the hunting group status of the user who has opened the session.
     * <p>
     * This method will return {@code null} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return the hunting group status in case of success; {@code null} otherwise.
     * @see #getHuntingGroupStatus(String loginName)
     */
    HuntingGroupStatus getHuntingGroupStatus();

    /**
     * Logs on the specified user in his current hunting group.
     * <p>
     * The user must be configured as member of a hunting group.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean huntingGroupLogOn(String loginName);

    /**
     * Logs on the user who has opened the session in his current hunting group.
     * <p>
     * The user must be configured as member of a hunting group.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #huntingGroupLogOn(String)
     */
    boolean huntingGroupLogOn();

    /**
     * Logs off the specified user from his current hunting group.
     * <p>
     * The user must be configured as member of a hunting group.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean huntingGroupLogOff(String loginName);

    /**
     * Logs off the user who has opened the session from his current hunting group.
     * <p>
     * The user must be configured as member of a hunting group.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #huntingGroupLogOff(String loginName)
     */
    boolean huntingGroupLogOff();

    /**
     * Sets the specified user as member of an existing hunting group.
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
     */
    boolean addHuntingGroupMember(String hgNumber, String loginName);

    /**
     * Sets the user who has opened the session as member of an existing hunting
     * group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user
     * already belongs to the group, nothing is done and {@code true} is returned.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param hgNumber the hunting group number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
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
     */
    boolean deleteHuntingGroupMember(String hgNumber, String loginName);

    /**
     * Removes the specified user from an existing hunting group.
     * <p>
     * The request will fail if the hunting group does not exist. If the user does
     * not belong to the group, nothing is done and {@code true} is returned.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param hgNumber the hunting group number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteHuntingGroupMember(String hgNumber);

    /**
     * Gets the list of hunting groups existing on the OXE node the specified user
     * belongs to.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return the hunting groups result in case of success; {@code null} otherwise.
     */
    HuntingGroups queryHuntingGroups(String loginName);

    /**
     * Gets the list of hunting groups existing on the OXE node the user who has
     * opened the session belongs to.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return the hunting groups result in case of success; {@code null} otherwise.
     * @see #getHuntingGroupStatus(String) getHuntingGroupStatus
     */
    HuntingGroups queryHuntingGroups();

    /**
     * Returns the list of callback requests for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return the collection of callback in case of success; {@code null}
     *         otherwise.
     */
    Collection<Callback> getCallbacks(String loginName);

    /**
     * Returns the list of callback requests for the specified user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return the collection of callback in case of success; {@code null}
     *         otherwise.
     * @see #getCallbacks(String loginName)
     */
    Collection<Callback> getCallbacks();

    /**
     * Deletes all callback requests for the specified user.
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
     * Deletes all callback requests for the specified user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deleteCallbacks(String loginName)
     */
    boolean deleteCallbacks();

    /**
     * Deletes the specified callback requests for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callbackId the callback identifier
     * @param loginName  the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteCallback(String callbackId, String loginName);

    /**
     * Deletes the specified callback requests for the specified user.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callbackId the callback identifier
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteCallback(String callbackId);

    /**
     * Returns the current new message for the specified user.
     * <p>
     * As soon as a message is read, it is erased from OXE and cannot be read again.
     * The messages are retrieved in Last In First Out mode.
     * <p>
     * This method will return {@code false} if all the messages have been
     * retrieved.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the login name
     * @return The mini message on success; {@code null} otherwise.
     */
    MiniMessage getMiniMessage(String loginName);

    /**
     * Returns the current new message for the user who has opened the session
     * <p>
     * As soon as a message is read, it is erased from OXE and cannot be read again.
     * The message are retrieved in Last In First Out mode.
     * <p>
     * This method will return {@code false} if all the messages have been
     * retrieved.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @return The mini message on success; {@code null} otherwise.
     * @see #getMiniMessage(String loginName)
     */
    MiniMessage getMiniMessage();

    /**
     * Sends the specified mini message to the specified recipient.
     * 
     * @param recipient the recipient of the mini message phone number
     * @param message   the mini message text
     *                  <p>
     *                  If the session has been opened for a user, the
     *                  {@code loginName} parameter is ignored, but it is mandatory
     *                  if the session has been opened by an administrator.
     * 
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean sendMiniMessage(String recipient, String message, String loginName);

    /**
     * Sends the specified mini message to the specified recipient.
     * 
     * @param recipient the recipient of the mini message phone number
     * @param message   the mini message text
     *                  <p>
     *                  This method will return {@code false} if it is invoked from
     *                  a session opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #sendMiniMessage(String recipient, String message, String loginName)
     */
    boolean sendMiniMessage(String recipient, String message);

    /**
     * Requests for call back from an idle device of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param callee    phone number of the called party for which a call back is
     *                  requested.
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestCallback(String callee, String loginName);

    /**
     * Requests for call back from an idle device of the user who has opened the
     * session.
     * <p>
     * This method will return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param callee phone number of the called party for which a call back is
     *               requested.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestCallback(String callee);

    /**
     * Queries the specified CCD pilot information using the given transfer
     * criteria.
     * <p>
     * The {@code pilotTransferQueryParam} defines the optional filtering criteria
     * such as agent number, priority transfer, supervised transfer, or call
     * profile.
     * </p>
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored. It is required only when the session has been opened by an
     * administrator.
     * </p>
     *
     * @param nodeId                  the PCX Enterprise node ID
     * @param pilotNumber             the pilot number to query
     * @param pilotTransferQueryParam the transfer criteria; must not be
     *                                {@code null}
     * @param loginName               the login name; required if session opened by
     *                                administrator
     * @return the {@link PilotInfo} for the CCD pilot on success, or {@code null}
     *         otherwise
     * @throws IllegalArgumentException if {@code pilotTransferQueryParam} is
     *                                  {@code null}
     * @since 2.7.4
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber, PilotTransferQueryParameters pilotTransferQueryParam,
            String loginName);

    /**
     * Queries the specified CCD pilot information without any transfer criteria.
     * <p>
     * This is a convenience overload for cases where no filtering is required. It
     * internally calls
     * {@link #getPilotInfo(int, String, PilotTransferQueryParameters, String)} with
     * an empty {@link PilotTransferQueryParameters} object.
     * </p>
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored. It is required only when the session has been opened by an
     * administrator.
     * </p>
     *
     * @param nodeId      the PCX Enterprise node ID
     * @param pilotNumber the pilot number to query
     * @param loginName   the login name; required if session opened by
     *                    administrator
     * @return the {@link PilotInfo} for the CCD pilot on success, or {@code null}
     *         otherwise
     * @since 2.7
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber, String loginName);

    /**
     * Queries the specified CCD pilot information using the given transfer
     * criteria.
     * <p>
     * The {@code pilotTransferQueryParam} defines the optional filtering criteria
     * such as agent number, priority transfer, supervised transfer, or call
     * profile.
     * </p>
     * <p>
     * This method will return {@code null} if it is invoked from a session opened
     * by an administrator.
     * </p>
     * 
     * @param nodeId                  the PCX Enterprise node id
     * @param pilotNumber             the pilot number
     * @param pilotTransferQueryParam the transfer criteria; must not be
     *                                {@code null}
     * @return The CCD pilot information on success; {@code null} otherwise.
     * @since 2.7.4
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber, PilotTransferQueryParameters pilotTransferQueryParam);


    /**
     * Queries the specified CCD pilot information without any transfer criteria.
     * <p>
     * This is a convenience overload for cases where no filtering is required. It
     * internally calls
     * {@link #getPilotInfo(int, String, PilotTransferQueryParameters)} with
     * an empty {@link PilotTransferQueryParameters} object.
     * </p>
     * <p>
     * This method will return {@code null} if it is invoked from a session opened
     * by an administrator.
     * </p>
     * 
     * @param nodeId                  the PCX Enterprise node id
     * @param pilotNumber             the pilot number
     * @return The CCD pilot information on success; {@code null} otherwise.
     * @since 2.7
     */
    PilotInfo getPilotInfo(int nodeId, String pilotNumber);

    /**
     * Asks a snapshot event on the specified user.
     * <p>
     * The event OnTelephonyState will contain the TelephonicState (calls[] and
     * deviceCapabilities[]). If a second request is asked since the previous one is
     * still in progress, it has no effect.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored.
     * 
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnapshot(String loginName);

    /**
     * Asks a snapshot event.
     * <p>
     * The event OnTelephonyState will contain the TelephonicState (calls[] and
     * deviceCapabilities[]). If a second request is asked since the previous one is
     * still in progress, it has no effect.
     * <p>
     * If the session has been opened for an administrator, the snapshot event
     * request is done for all the users. This request is immediately acknowledged
     * but the processing may take a long time if the number of users is huge.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnapshot();
}
