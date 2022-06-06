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

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.routing.DndState;
import com.ale.o2g.types.routing.Forward;
import com.ale.o2g.types.routing.Overflow;
import com.ale.o2g.types.routing.RoutingCapabilities;
import com.ale.o2g.types.routing.RoutingState;

/**
 * The Routing service allows a user to manage forward, overflow, DoNotDisturb
 * and activation of his remote extension device (if any). Using this service
 * requires having a <b>TELEPHONY_ADVANCED</b> license.
 * <h2>Forward:</h2>A forward can be activated on the voice mail or on any
 * number as far as this number is authorized by the OmniPCX Enterprise
 * numbering policy. Use one of the methods:
 * {@link #forwardOnNumber(String, com.ale.o2g.types.routing.Forward.Condition, String)
 * forwardOnNumber} or
 * {@link #forwardOnVoiceMail(com.ale.o2g.types.routing.Forward.Condition, String)
 * forwardOnVoiceMail} to activate a forward. <br>
 * A {@link Forward.Condition Condition} can be associated to the forward:
 * <table>
 * <caption>Forward conditions</caption>
 * <tr>
 * <td>IMMEDIATE
 * <td>
 * <td>Incoming calls are immediately forwarded on the target.</td>
 * <tr>
 * <tr>
 * <td>BUSY
 * <td>
 * <td>Incoming calls are forwarded on the target if the user is busy.</td>
 * <tr>
 * <tr>
 * <td>NO_ANSWER
 * <td>
 * <td>Incoming calls are forwarded on the target if the user does not answer
 * the call</td>
 * <tr>
 * <tr>
 * <td>BUSY_NO_ANSWER
 * <td>
 * <td>One of the two last conditions</td>
 * <tr>
 * </table>
 * <h2>Overflow:</h2>An overflow can be activated on the voice mail (if any).
 * Use method:
 * {@link #overflowOnVoiceMail(com.ale.o2g.types.routing.Overflow.Condition, String)
 * overflow} to activate an overflow. <br>
 * A {@link com.ale.o2g.types.routing.Overflow.Condition Condition} can be
 * associated to the overflow:
 * <table>
 * <caption>Overflow conditions</caption>
 * <tr>
 * <td>BUSY
 * <td>
 * <td>Incoming calls are redirected on the target if the user is busy.</td>
 * <tr>
 * <tr>
 * <td>NO_ANSWER
 * <td>
 * <td>Incoming calls are redirected on the target if the user does not answer
 * the call</td>
 * <tr>
 * <tr>
 * <td>BUSY_NO_ANSWER
 * <td>
 * <td>One of the two last conditions</td>
 * <tr>
 * </table>
 * <h2>Do Not Disturb:</h2> When the Do Not Disturb (DND) is activated, the user
 * does not receive any call. The DND is activated using method
 * {@link #activateDnd(String) activateDnd}.
 * <h2>Remote extension activation:</h2> When a remote extension is not
 * activated, it does not ring on incoming call. Use the method
 * {@link #setRemoteExtensionActivation(boolean, String)
 * setRemoteExtensionActivation} to activate the remote extension. <br>
 * <h2>Eventing:</h2> For each routing modification, a
 * {@link com.ale.o2g.events.routing.OnRoutingStateChangedEvent
 * OnRoutingStateChangedEvent} is raised.
 * <p>
 * If an application wants to perform some action based on routing state change,
 * it should implement {@link com.ale.o2g.events.routing.RoutingEventListener
 * RoutingEventListener} and register the new listener to receive events, by
 * calling the session's {@code listenEvent} method.
 */
public interface RoutingService extends IService {

    /**
     * Allows to know what the specified user is allowed to do.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return the {@link RoutingCapabilities RoutingCapabilities} in case of
     *         success; {@code null} otherwise.
     */
    RoutingCapabilities getCapabilities(String loginName);

    /**
     * Allows to know what the user who has opened the session is allowed to do.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return the {@link RoutingCapabilities RoutingCapabilities} in case of
     *         success; {@code null} otherwise.
     * @see #getCapabilities(String)
     */
    RoutingCapabilities getCapabilities();

    /**
     * Sets the activation state of the remote extension device for the specified
     * user.
     * <p>
     * When the remote extension is activated, it rings on incoming call on the user
     * company phone. When it is deactivated, it never rings, but it can be used to
     * place an outgoing call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param active    the remote extension device state
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setRemoteExtensionActivation(boolean active, String loginName);

    /**
     * Sets the activation state of the remote extension device for the user who has
     * opened the session.
     * <p>
     * When the remote extension is activated, it rings on incoming call on the user
     * company phone. When it is deactivated, it never rings, but it can be used to
     * place an outgoing call.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @param active the remote extension device state
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #setRemoteExtensionActivation(boolean, String)
     */
    boolean setRemoteExtensionActivation(boolean active);

    /**
     * Gets the Do Not Disturb state of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return the {@link DndState DndState} in case of success; {@code null}
     *         otherwise.
     */
    DndState getDndState(String loginName);

    /**
     * Gets the Do Not Disturb state of the user who has opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return the {@link DndState DndState} in case of success; {@code null}
     *         otherwise.
     * @see #getDndState(String)
     */
    DndState getDndState();

    /**
     * Activates the Do Not Disturb for the specified user.
     * <p>
     * This method does nothing and return {@code true} if the Do Not Disturb is
     * already activated.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean activateDnd(String loginName);

    /**
     * Activates the Do Not Disturb for the user who has opened the session.
     * <p>
     * This method does nothing and return {@code true} if the Do Not Disturb is
     * already activated.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #activateDnd(String)
     */
    boolean activateDnd();

    /**
     * Cancels the Do Not Disturb for the specified user.
     * <p>
     * This method does nothing and return {@code true} if the Do Not Disturb was
     * not activated.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean cancelDnd(String loginName);

    /**
     * Cancels the Do Not Disturb for the user who has opened the session.
     * <p>
     * This method does nothing and return {@code true} if the Do Not Disturb was
     * not activated.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #cancelDnd(String)
     */
    boolean cancelDnd();

    /**
     * Gets the forward state of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return the {@link Forward Forward} in case of success; {@code null}
     *         otherwise.
     */
    Forward getForward(String loginName);

    /**
     * Gets the forward state of the user who has opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @return the {@link Forward Forward} in case of success; {@code null}
     *         otherwise.
     * @see #getForward(String)
     */
    Forward getForward();

    /**
     * Cancels the forward for the specified user.
     * <p>
     * This method does nothing and return {@code true} if there is no forward
     * activated.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean cancelForward(String loginName);

    /**
     * Cancels the forward for the user who has opened the session.
     * <p>
     * This method does nothing and return {@code true} if there is no forward
     * activated.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #cancelForward(String)
     */
    boolean cancelForward();

    /**
     * Sets a forward on voice mail with the specified condition, for the specified
     * user.
     * <p>
     * This method will fail and return {@code false} if the user does not have a
     * voice mail. This can be check by using
     * {@link UsersService#getByLoginName(String)}.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param condition the forward condition
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean forwardOnVoiceMail(Forward.Condition condition, String loginName);

    /**
     * Sets a forward on voice mail with the specified condition, for the user who
     * has opened the session.
     * <p>
     * This method will fail and return {@code false} if the user does not have a
     * voice mail. This can be check by using
     * {@link UsersService#getByLoginName(String)}.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @param condition the forward condition
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #forwardOnVoiceMail(com.ale.o2g.types.routing.Forward.Condition, String)
     */
    boolean forwardOnVoiceMail(Forward.Condition condition);

    /**
     * Sets a forward on the specified number, with the specified condition, for the
     * specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param number    the phone number on which the forward is activated
     * @param condition the forward condition
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean forwardOnNumber(String number, Forward.Condition condition, String loginName);

    /**
     * Sets a forward on the specified number, with the specified condition, for the
     * user who has opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @param number    the phone number on which the forward is activated
     * @param condition the forward condition
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #forwardOnNumber(String, com.ale.o2g.types.routing.Forward.Condition,
     *      String)
     */
    boolean forwardOnNumber(String number, Forward.Condition condition);

    /**
     * Cancels the overflow for the specified user.
     * <p>
     * This method does nothing and return {@code true} if there is no overflow
     * activated.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean cancelOverflow(String loginName);

    /**
     * Cancels the overflow for the user who has opened the session.
     * <p>
     * This method does nothing and return {@code true} if there is no overflow
     * activated.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #cancelOverflow(String)
     */
    boolean cancelOverflow();

    /**
     * Gets the overflow state for the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return the {@link Overflow Overflow} in case of success; {@code null}
     *         otherwise.
     */
    Overflow getOverflow(String loginName);

    /**
     * Gets the overflow state for the user who has opened the session.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @return the {@link Overflow Overflow} in case of success; {@code null}
     *         otherwise.
     * @see #getOverflow(String)
     */
    Overflow getOverflow();

    /**
     * Activates an overflow on voice mail with the specified condition, for the
     * specified user.
     * <p>
     * This method will fail and return {@code false} if the user does not have a
     * voice mail. This can be check by using
     * {@link UsersService#getByLoginName(String)}.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param condition the overflow condition
     * @param loginName the user login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean overflowOnVoiceMail(Overflow.Condition condition, String loginName);

    /**
     * Activates an overflow on voice mail with the specified condition, for the user
     * who has opened the session.
     * <p>
     * This method will fail and return {@code false} if the user does not have a
     * voice mail. This can be check by using
     * {@link UsersService#getByLoginName(String)}.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a
     * session opened by an administrator.
     * 
     * @param condition the overflow condition
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #overflowOnVoiceMail(com.ale.o2g.types.routing.Overflow.Condition,
     *      String)
     */
    boolean overflowOnVoiceMail(Overflow.Condition condition);

    /*
     * Activation an overflow on associate with the specified condition, for the
     * specified user. <p> If the session has been opened for a user, the {@code
     * loginName} parameter is ignored, but it is mandatory if the session has been
     * opened by an administrator.
     * 
     * @param condition the overflow condition
     * 
     * @param loginName the user login name
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     */
//    boolean overflowOnAssociate(Overflow.Condition condition, String loginName);

    /*
     * Activation an overflow on associate with the specified conditionfor the user
     * who has opened the session. <p> This method will fail and return {@code
     * false} if it is invoked from a session opened by an administrator.
     * 
     * @param condition the overflow condition
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     * 
     * @see #overflowOnAssociate(com.ale.o2g.types.routing.Overflow.Condition,
     * String)
     */
//    boolean overflowOnAssociate(Overflow.Condition condition);

    /**
     * Gets the routing state of the specified user.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the user login name
     * @return the {@link RoutingState} in case of success; {@code null} otherwise.
     */
    RoutingState getRoutingState(String loginName);

    /**
     * Gets the routing state of the user who has opened the session.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return the {@link RoutingState} in case of success; {@code null} otherwise.
     * @see #getRoutingState(String)
     */
    RoutingState getRoutingState();

    /**
     * Asks a snapshot event on the specified user.
     * <p>
     * The event OnRoutingStateChanged will contain the DynamicState
     * (forward/overflow/dnd state). If a second request is asked since the previous
     * one is still in progress, it has no effect.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored.
     * 
     * @param loginName the login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnapshot(String loginName);

    /**
     * Ask a snapshot event.
     * <p>
     * The event OnRoutingStateChanged will contain the DynamicState
     * (forward/overflow/dnd state). If a second request is asked since the previous
     * one is still in progress, it has no effect.
     * <p>
     * If the session has been opened for an administrator, the snapshot event
     * request is done for all the users. This request is immediately acknowledged
     * but the processing may take a long time if the number of users is huge.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnapshot();
}
