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
 * {@code CallCenterAgent} provides services for CCD operators. Using this
 * service requires having a <b>CONTACTCENTER_AGENT</b> license.
 */
public interface CallCenterAgentService extends IService {

    /**
     * Gets the operator configuration.
     * 
     * @param loginName the operator login name
     * @return an OperatorConfiguration object that represents the operator
     *         configuration.
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
     * @return an OperatorState object thats represents the operator state.
     */
    OperatorState getOperatorState(String loginName);

    /**
     * Gets the specified agent or supervisor state.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return an OperatorState object thats represents the operator state.
     */
    OperatorState getOperatorState();

    /**
     * Logon an agent or a supervisor.
     * <p>
     * For a Supervisor, if the {@code pgNumber} is omitted, the supervisor is
     * logged on out off group.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param proAcdNumber the pro-acd device number
     * @param pgNumber     the agent processing group number
     * @param headset      activate the headset mode
     * @param loginName    the ccd operator login name.
     * @return {@code true} in case of success; {@code false} otherwise
     */
    boolean logonOperator(String proAcdNumber, String pgNumber, boolean headset, String loginName);

    /**
     * Logon an agent or a supervisor.
     * <p>
     * For a Supervisor, if the {@code pgNumber} is omitted, the supervisor is
     * logged on out off group.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param proAcdNumber the pro-acd device number
     * @param pgNumber     the agent processing group number
     * @param headset      activate the headset mode
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean logonOperator(String proAcdNumber, String pgNumber, boolean headset);

    /**
     * Logoff an agent or a supervisor.
     * <p>
     * This method does nothing an returns {@code true} if the agent or the
     * supervisor is already logged off.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the ccd operator login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean logoffOperator(String loginName);

    /**
     * Logoff an agent or a supervisor.
     * <p>
     * This method does nothing an returns {@code true} if the agent or the
     * supervisor is already logged off.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean logoffOperator();

    /**
     * Enters in a agent group. Only for a supervisor.
     * <p>
     * This method is used by a supervisor to enter an agent group when it is in
     * pre-assigned state (logged but not in an agent group).
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
     * Enters in a agent group. Only for a supervisor.
     * <p>
     * This method is used by a supervisor to enter an agent group when it is in
     * pre-assigned state (logged but not in an agent group).
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param pgNumber the agent processing group number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean enterAgentGroup(String pgNumber);

    /**
     * Exits from an agent group. Only for a supervisor.
     * <p>
     * This method is used by a supervisor to leave an agent group an go back in
     * pre-assigned state (logged but not in an agent group).
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
     * This method is used by a supervisor to leave an agent group an go back in
     * pre-assigned state (logged but not in an agent group).
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean exitAgentGroup();

    /**
     * Puts the specified agent in wrapup.
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
     * Puts the specified agent in wrapup.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
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
     * This method will fail return {@code false} if it is invoked from a session
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
     * This method will fail return {@code false} if it is invoked from a session
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
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param reason the withdraw reason
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setWithdraw(WithdrawReason reason);

    /**
     * Requests to listen to the agent by a supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised. for both the agent and the
     * supervisor.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param agentNumber the listened agent number
     * @param loginName   the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.cca.CallCenterAgentEventListener
     *      CallCenterAgentEventListener
     */
    boolean requestPermanentListening(String agentNumber, String loginName);

    /**
     * Requests to listen to the agent by a supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised. for both the agent and the
     * supervisor.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param agentNumber the listened agent number
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.cca.CallCenterAgentEventListener
     *      CallCenterAgentEventListener
     */
    boolean requestPermanentListening(String agentNumber);

    /**
     * Requests intrusion in a ccd call.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param agentNumber   the extension number of the ccd agent who answers the
     *                      ccd call
     * @param intrusionMode the intrusion mode
     * @param loginName     the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestIntrusion(String agentNumber, IntrusionMode intrusionMode, String loginName);

    /**
     * Requests intrusion in a ccd call.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param agentNumber   the extension number of the ccd agent who answers the
     *                      ccd call
     * @param intrusionMode the intrusion mode
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestIntrusion(String agentNumber, IntrusionMode intrusionMode);

    /**
     * Changes the intrusion mode.
     * <p>
     * Calling this method allows to change the intrusion mode, or to cancel an
     * intrusion. To cancel an intrusion, the application must pass the current mode
     * in the {@code newIntrusionMode} parameter.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param newIntrusionMode the new intrusion mode
     * @param loginName        the supervisor login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean changeIntrusionMode(IntrusionMode newIntrusionMode, String loginName);

    /**
     * Changes the intrusion mode.
     * <p>
     * Calling this method allows to change the intrusion mode, or to cancel an
     * intrusion. To cancel an intrusion, the application must pass the current mode
     * in the {@code newIntrusionMode} parameter.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param newIntrusionMode the new intrusion mode
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean changeIntrusionMode(IntrusionMode newIntrusionMode);

    /**
     * Requests help of the supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised for both the agent and supervisor.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSupervisorHelp(String loginName);

    /**
     * Requests help of the supervisor.
     * <p>
     * On success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpRequestedEvent
     * OnSupervisorHelpRequestedEvent} is raised for both the agent and supervisor.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSupervisorHelp();

    /**
     * Rejects an help request from an agent.
     * <p>
     * This method is invoked by a supervisor when he reject an help request from an
     * agent. On success, an
     * {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
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
     * Rejects an help request from an agent.
     * <p>
     * This method is invoked by a supervisor when he reject an help request from an
     * agent. On success, an
     * {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param agentNumber the extension number of the agent who has requested help
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean rejectAgentHelpRequest(String agentNumber);

    /**
     * Cancels a supervisor help request.
     * <p>
     * This method is invoked by an agent when he want to cancel an help request. On
     * success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param supervisorNumber the requested supervisor extension number
     * @param loginName        the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean cancelSupervisorHelpRequest(String supervisorNumber, String loginName);

    /**
     * Cancels a supervisor help request.
     * <p>
     * This method is invoked by an agent when he want to cancel an help request. On
     * success, an {@link com.ale.o2g.events.cca.OnSupervisorHelpCancelledEvent
     * OnSupervisorHelpCancelledEvent} is raised.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param supervisorNumber the requested supervisor extension number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean cancelSupervisorHelpRequest(String supervisorNumber);

    /**
     * Asks a snapshot event to receive an
     * {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent
     * OnAgentStateChangedEvent}.
     * <p>
     * The {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent
     * OnAgentStateChangedEvent} event contain the operator
     * {@link com.ale.o2g.types.cca.OperatorState OperatorState} object. If a second
     * request is asked since the previous one is still in progress, it has no
     * effect.
     * <p>
     * If an administrator invokes this method with {@code loginName=null}, the
     * snapshot event request is done for all the agents. The event processing can
     * be long depending on the number of users.
     * 
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean requestSnaphot(String loginName);

    /**
     * Asks a snapshot event to receive an
     * {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent
     * OnAgentStateChangedEvent}.
     * <p>
     * The {@link com.ale.o2g.events.cca.OnAgentStateChangedEvent
     * OnAgentStateChangedEvent} event contain the operator
     * {@link com.ale.o2g.types.cca.OperatorState OperatorState} object. If a second
     * request is asked since the previous one is still in progress, it has no
     * effect.
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
     * This method doesn't control the skills number. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     * 
     * @param skills    the list of skills to activate.
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean activateSkills(List<Integer> skills, String loginName);

    /**
     * Activates the specified skills.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * This method doesn't control the skills number. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     * 
     * @param skills    the list of skills to activate.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean activateSkills(List<Integer> skills);


    /**
     * Deactivates the specified skills.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * <p>
     * This method doesn't control the skills number. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     * 
     * @param skills    the list of skills to activate.
     * @param loginName the agent login name
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deactivateSkills(List<Integer> skills, String loginName);

    /**
     * Deactivates the specified skills.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * <p>
     * This method doesn't control the skills number. If a skill number is invalid
     * (not assigned to the operator), it is ignored and the method returns
     * {@code true}.
     * 
     * @param skills    the list of skills to activate.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deactivateSkills(List<Integer> skills);

    /**
     * Returns the list of withdraw reason for the specified processing group.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param pgNumber  the agent processing group number
     * @param loginName the agent login name
     * @return A list of WithdrawReason or {@code null} in case of error.
     */
    List<WithdrawReason> getWithdrawReasons(String pgNumber, String loginName);

    /**
     * Returns the list of withdraw reason for the specified processing group.
     * <p>
     * This method will fail return {@code false} if it is invoked from a session
     * opened by an administrator.
     * 
     * @param pgNumber the agent processing group number
     * @return A list of WithdrawReason or {@code null} in case of error.
     */
    List<WithdrawReason> getWithdrawReasons(String pgNumber);
}
