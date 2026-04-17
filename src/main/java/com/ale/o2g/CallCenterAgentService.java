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

import java.util.List;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.cca.IntrusionMode;
import com.ale.o2g.types.cca.OperatorConfiguration;
import com.ale.o2g.types.cca.OperatorState;
import com.ale.o2g.types.cca.WithdrawReason;

/**
 * The {@code CallCenterAgentService} provides access to Contact Center features
 * for CCD operators. A CCD operator can be either a CCD agent or a CCD supervisor.
 * <p>
 * Using this service requires a <b>CONTACTCENTER_AGENT</b> license.
 * <p>
 * This service exposes capabilities such as:
 * <ul>
 *   <li>Retrieving the agent configuration (type, Pro-ACD, processing groups, skills)</li>
 *   <li>Activating or deactivating skills</li>
 *   <li>Logging on or off a Pro-ACD set</li>
 *   <li>Requesting withdrawal reasons for a processing group</li>
 *   <li>Requesting withdrawal or returning to ready state</li>
 *   <li>Requesting wrap-up after a call</li>
 *   <li>Querying the agent state or requesting a snapshot event on agent state</li>
 *   <li>Enabling or disabling PBX multimedia features</li>
 *   <li>Requesting a pause</li>
 *   <li>Requesting help from a supervisor</li>
 *   <li>Cancelling (agent) or rejecting (supervisor) a help request</li>
 *   <li>(Supervisor only) requesting intrusion into an agent's call</li>
 *   <li>(Supervisor only) requesting permanent supervision of an agent</li>
 * </ul>
 */
public interface CallCenterAgentService extends IService {

    /**
     * Gets the operator configuration.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an administrator.
     *
     * @param loginName the operator login name
     * @return An {@link OperatorConfiguration} object that represents the operator configuration,
     *         or {@code null} in case of error.
     */
    OperatorConfiguration getOperatorConfiguration(String loginName);

    /**
     * Gets the specified agent or supervisor state.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the operator login name
     * @return An {@link OperatorState} object that represents the operator state,
     *         or {@code null} in case of error.
     */
    OperatorState getOperatorState(String loginName);

    /**
     * Gets the specified agent or supervisor state.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @return An {@link OperatorState} object that represents the operator state,
     *         or {@code null} in case of error.
     */
    OperatorState getOperatorState();

    /**
     * Logs on an agent or a supervisor.
     * <p>
     * For a supervisor, if the {@code pgNumber} is omitted, the supervisor is
     * logged on out of group.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param proAcdNumber the pro-ACD device number
     * @param pgNumber     the agent processing group number
     * @param headset      activate the headset mode
     * @param loginName    the CCD operator login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean logonOperator(String proAcdNumber, String pgNumber, boolean headset, String loginName);

    /**
     * Logs on an agent or a supervisor.
     * <p>
     * For a supervisor, if the {@code pgNumber} is omitted, the supervisor is
     * logged on out of group.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param proAcdNumber the pro-ACD device number
     * @param pgNumber     the agent processing group number
     * @param headset      activate the headset mode
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean logonOperator(String proAcdNumber, String pgNumber, boolean headset);

    /**
     * Logs off an agent or a supervisor.
     * <p>
     * This method does nothing and returns {@code true} if the agent or the
     * supervisor is already logged off.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the CCD operator login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean logoffOperator(String loginName);

    /**
     * Logs off an agent or a supervisor.
     * <p>
     * This method does nothing and returns {@code true} if the agent or the
     * supervisor is already logged off.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean logoffOperator();

    /**
     * Enters an agent group. Only for a supervisor.
     * <p>
     * This method is used by a supervisor to enter an agent group when in
     * pre-assigned state (logged on but not in an agent group).
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param pgNumber  the agent processing group number
     * @param loginName the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean enterAgentGroup(String pgNumber, String loginName);

    /**
     * Enters an agent group. Only for a supervisor.
     * <p>
     * This method is used by a supervisor to enter an agent group when in
     * pre-assigned state (logged on but not in an agent group).
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param pgNumber the agent processing group number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean enterAgentGroup(String pgNumber);

    /**
     * Exits from an agent group. Only for a supervisor.
     * <p>
     * This method is used by a supervisor to leave an agent group and go back to
     * pre-assigned state (logged on but not in an agent group).
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean exitAgentGroup(String loginName);

    /**
     * Exits from an agent group. Only for a supervisor.
     * <p>
     * This method is used by a supervisor to leave an agent group and go back to
     * pre-assigned state (logged on but not in an agent group).
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean exitAgentGroup();

    /**
     * Puts the specified agent in wrapup state.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setWrapup(String loginName);

    /**
     * Puts the specified agent in wrapup state.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setWrapup();

    /**
     * Puts the specified agent in ready state.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setReady(String loginName);

    /**
     * Puts the specified agent in ready state.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setReady();

    /**
     * Puts the specified agent in pause.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setPause(String loginName);

    /**
     * Puts the specified agent in pause.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setPause();

    /**
     * Withdraws an agent with the specified reason.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param reason    the withdraw reason
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setWithdraw(WithdrawReason reason, String loginName);

    /**
     * Withdraws an agent with the specified reason.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param reason the withdraw reason
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setWithdraw(WithdrawReason reason);

    /**
     * Requests a supervisor to listen to the specified agent (permanent listening).
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised for both the agent and the supervisor.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param agentNumber the extension number of the agent to listen to
     * @param loginName   the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.cca.CallCenterAgentEventListener CallCenterAgentEventListener
     * @see #cancelPermanentListening(String)
     */
    boolean requestPermanentListening(String agentNumber, String loginName);

    /**
     * Requests a supervisor to listen to the specified agent (permanent listening).
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised for both the agent and the supervisor.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param agentNumber the extension number of the agent to listen to
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.cca.CallCenterAgentEventListener CallCenterAgentEventListener
     * @see #cancelPermanentListening()
     */
    boolean requestPermanentListening(String agentNumber);

    /**
     * Cancels a permanent listening by a supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised for both the agent and the supervisor.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an administrator.
     *
     * @param loginName the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.cca.CallCenterAgentEventListener CallCenterAgentEventListener
     * @see #requestPermanentListening(String, String)
     */
    boolean cancelPermanentListening(String loginName);

    /**
     * Cancels a permanent listening by a supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised for both the agent and the supervisor.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.cca.CallCenterAgentEventListener CallCenterAgentEventListener
     * @see #requestPermanentListening(String)
     */
    boolean cancelPermanentListening();

    /**
     * Requests intrusion in a CCD call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param agentNumber   the extension number of the CCD agent who answers the CCD call
     * @param intrusionMode the intrusion mode
     * @param loginName     the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #changeIntrusionMode(IntrusionMode, String)
     */
    boolean requestIntrusion(String agentNumber, IntrusionMode intrusionMode, String loginName);

    /**
     * Requests intrusion in a CCD call.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param agentNumber   the extension number of the CCD agent who answers the CCD call
     * @param intrusionMode the intrusion mode
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #changeIntrusionMode(IntrusionMode)
     */
    boolean requestIntrusion(String agentNumber, IntrusionMode intrusionMode);

    /**
     * Changes the intrusion mode.
     * <p>
     * Calling this method allows changing the intrusion mode or cancelling an
     * intrusion. To cancel an intrusion, pass the current mode in the
     * {@code newIntrusionMode} parameter.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param newIntrusionMode the new intrusion mode
     * @param loginName        the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #requestIntrusion(String, IntrusionMode, String)
     */
    boolean changeIntrusionMode(IntrusionMode newIntrusionMode, String loginName);

    /**
     * Changes the intrusion mode.
     * <p>
     * Calling this method allows changing the intrusion mode or cancelling an
     * intrusion. To cancel an intrusion, pass the current mode in the
     * {@code newIntrusionMode} parameter.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param newIntrusionMode the new intrusion mode
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #requestIntrusion(String, IntrusionMode)
     */
    boolean changeIntrusionMode(IntrusionMode newIntrusionMode);

    /**
     * Requests help from the supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised for both the agent and the supervisor.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #cancelSupervisorHelpRequest(String, String)
     */
    boolean requestSupervisorHelp(String loginName);

    /**
     * Requests help from the supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised for both the agent and the supervisor.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #cancelSupervisorHelpRequest(String)
     */
    boolean requestSupervisorHelp();

    /**
     * Rejects a help request from an agent.
     * <p>
     * This method is invoked by a supervisor to reject a help request from an
     * agent. On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param agentNumber the extension number of the agent who has requested help
     * @param loginName   the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean rejectAgentHelpRequest(String agentNumber, String loginName);

    /**
     * Rejects a help request from an agent.
     * <p>
     * This method is invoked by a supervisor to reject a help request from an
     * agent. On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param agentNumber the extension number of the agent who has requested help
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean rejectAgentHelpRequest(String agentNumber);

    /**
     * Cancels a supervisor help request.
     * <p>
     * This method is invoked by an agent to cancel a help request. On success,
     * an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param supervisorNumber the extension number of the requested supervisor
     * @param loginName        the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #requestSupervisorHelp(String)
     */
    boolean cancelSupervisorHelpRequest(String supervisorNumber, String loginName);

    /**
     * Cancels a supervisor help request.
     * <p>
     * This method is invoked by an agent to cancel a help request. On success,
     * an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     *
     * @param supervisorNumber the extension number of the requested supervisor
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #requestSupervisorHelp()
     */
    boolean cancelSupervisorHelpRequest(String supervisorNumber);

    /**
     * Asks a snapshot event to receive an
     * {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent OnAgentStateChangedEvent}.
     * <p>
     * The {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent OnAgentStateChangedEvent}
     * event contains the operator {@link com.ale.o2g.types.cca.OperatorState OperatorState}
     * object. If a second request is asked while the previous one is still in progress, it
     * has no effect.
     * <p>
     * If an administrator invokes this method with {@code loginName=null}, the snapshot
     * event request is done for all the agents. The event processing can be long
     * depending on the number of users.
     *
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnaphot(String loginName);

    /**
     * Asks a snapshot event to receive an
     * {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent OnAgentStateChangedEvent}.
     * <p>
     * The {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent OnAgentStateChangedEvent}
     * event contains the operator {@link com.ale.o2g.types.cca.OperatorState OperatorState}
     * object. If a second request is asked while the previous one is still in progress, it
     * has no effect.
     * <p>
     * The snapshot event request is done for all the agents. The event processing
     * can be long depending on the number of users.
     *
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnaphot();

    /**
     * Activates the specified skills.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * This method does not validate skill numbers. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     *
     * @param skills    the list of skill numbers to activate
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deactivateSkills(List, String)
     */
    boolean activateSkills(List<Integer> skills, String loginName);

    /**
     * Activates the specified skills.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * This method does not validate skill numbers. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     *
     * @param skills the list of skill numbers to activate
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #deactivateSkills(List)
     */
    boolean activateSkills(List<Integer> skills);

    /**
     * Deactivates the specified skills.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * This method does not validate skill numbers. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     *
     * @param skills    the list of skill numbers to deactivate
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #activateSkills(List, String)
     */
    boolean deactivateSkills(List<Integer> skills, String loginName);

    /**
     * Deactivates the specified skills.
     * <p>
     * This method will fail and return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * This method does not validate skill numbers. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     *
     * @param skills the list of skill numbers to deactivate
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #activateSkills(List)
     */
    boolean deactivateSkills(List<Integer> skills);

    /**
     * Returns the list of withdraw reasons for the specified processing group.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param pgNumber  the agent processing group number
     * @param loginName the agent login name
     * @return A list of {@link WithdrawReason} objects, or {@code null} in case of error.
     * @see #setWithdraw(WithdrawReason, String)
     */
    List<WithdrawReason> getWithdrawReasons(String pgNumber, String loginName);

    /**
     * Returns the list of withdraw reasons for the specified processing group.
     * <p>
     * This method will fail and return {@code null} if it is invoked from a session
     * opened by an administrator.
     *
     * @param pgNumber the agent processing group number
     * @return A list of {@link WithdrawReason} objects, or {@code null} in case of error.
     * @see #setWithdraw(WithdrawReason)
     */
    List<WithdrawReason> getWithdrawReasons(String pgNumber);
}
