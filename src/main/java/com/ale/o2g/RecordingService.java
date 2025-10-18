/*
* Copyright 2025 ALE International
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
import com.ale.o2g.types.recording.RecordedDevice;
import com.ale.o2g.types.recording.RecordingStartType;
import com.ale.o2g.types.recording.RecordingStatus;

/**
 * The ROD OXR Recording service allows starting, pausing, or resuming voice
 * recording of an OXE device on one or more OXR recorders.
 * <p>
 * All necessary permissions must be configured on the OXR side for the
 * dedicated user to perform start/pause/resume operations.
 * <p>
 * If a device is configured in multiple OXRs, the request is sent to all
 * relevant OXRs. A successful action on any one OXR is considered a success for
 * the overall request.
 * <p>
 * Modifications on an OXR may require a restart of the OXR service. To apply
 * such changes immediately, O2G services must also be restarted; otherwise, the
 * O2G server refreshes automatically after a scheduled period (4 hours). After
 * a new device is created on an OXR, the O2G server is refreshed automatically
 * for that device.
 * <p>
 * This service does not provide real-time notifications of changes in recording
 * state caused by call evolutions (e.g., hold, retrieve, transfer, conference,
 * intrusion). Applications may deduce the recording state using call
 * monitoring.
 * <p>
 * Only two telephony call states are relevant for recording actions:
 * <ul>
 * <li><b>HELD:</b> If a call was recording before hold, the recording state is
 * memorized and actions are disabled.</li>
 * <li><b>ACTIVE:</b>
 * <ul>
 * <li>New call without recording: "start" action possible.</li>
 * <li>Call from transfer/conference: recording stopped, new "start" action
 * possible.</li>
 * <li>Hold retrieve: recording resumes previous state.</li>
 * <li>Other cases: no change.</li>
 * </ul>
 * </li>
 * </ul>
 * <p>
 * While the API allows inferring recording state from telephony events, it is
 * recommended to query the device to get the updated recording state. Typical
 * event-to-state mappings:
 * <ul>
 * <li>ACTIVE -> HELD: recording stalled, no action possible</li>
 * <li>ACTIVE caused by TRANSFERRED/CONFERENCE: no recording in progress,
 * "start" possible</li>
 * <li>HELD -> ACTIVE (not TRANSFERRED/CONFERENCE): previous recording
 * resumes</li>
 * <li>ACTIVE -> ACTIVE (not TRANSFERRED/CONFERENCE): no change</li>
 * <li>Other -> ACTIVE (not TRANSFERRED/CONFERENCE): new "start" possible</li>
 * <li>New callref with state=ACTIVE: new "start" possible</li>
 * </ul>
 * 
 * @since 2.7.3
 */
public interface RecordingService extends IService {

    /**
     * Retrieves basic information about all recorded devices (administrator only).
     *
     * @return a list of recorded device identifiers, or {@code null} in case of
     *         error.
     */
    Collection<String> getRecordedDevices();

    /**
     * Retrieves detailed information about a specific recorded device.
     *
     * @param deviceId  the phone number of the device
     * @param loginName the user login name; ignored if the session is opened for a
     *                  user, but mandatory for an administrator session
     * @return a {@link RecordedDevice} representing the device info in case of
     *         success, or {@code null} in case of error
     */
    RecordedDevice getRecordedDeviceInfo(String deviceId, String loginName);

    /**
     * Starts recording on the specified device.
     * <p>
     * This method with return {@code null} if it is invoked from a session opened
     * by an administrator.
     *
     * @param deviceId  the phone number of the device
     * @param callRef   the reference of the call for which the recording is
     *                  requested
     * @param startType Indicate the record start mode
     * @return a {@link RecordedDevice} representing the device info in case of
     *         success, or {@code null} in case of error
     */
    RecordedDevice startRecording(String deviceId, String callRef, RecordingStartType startType);

    /**
     * Starts recording on the specified device.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param deviceId  the phone number of the device
     * @param callRef   the reference of the call for which the recording is
     *                  requested
     * @param startType Indicate the record start mode
     * @param loginName the user login name
     * @return a {@link RecordedDevice} representing the device info in case of
     *         success, or {@code null} in case of error
     */
    RecordedDevice startRecording(String deviceId, String callRef, RecordingStartType startType, String loginName);

    /**
     * Pauses recording on the specified device.
     * <p>
     * This method with return {@code null} if it is invoked from a session opened
     * by an administrator.
     *
     *
     * @param deviceId the phone number of the device
     * @param callRef  the reference of the call for which the recording pause is
     *                 requested
     * @return a {@link RecordedDevice} representing the device info in case of
     *         success, or {@code null} in case of error
     */
    RecordedDevice pauseRecording(String deviceId, String callRef);

    /**
     * Pauses recording on the specified device.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param deviceId  the phone number of the device
     * @param callRef   the reference of the call for which the recording pause is
     *                  requested
     * @param loginName the user login name
     * @return a {@link RecordedDevice} representing the device info in case of
     *         success, or {@code null} in case of error
     */
    RecordedDevice pauseRecording(String deviceId, String callRef, String loginName);

    /**
     * Resumes recording on the specified device.
     * <p>
     * This method with return {@code null} if it is invoked from a session opened
     * by an administrator.
     *
     * @param deviceId  the phone number of the device
     * @param callRef   the reference of the call for which the recording resume is
     *                  requested
     * @return a {@link RecordedDevice} representing the device info in case of
     *         success, or {@code null} in case of error
     */
    RecordedDevice resumeRecording(String deviceId, String callRef);

    /**
     * Resumes recording on the specified device.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     *
     * @param deviceId  the phone number of the device
     * @param callRef   the reference of the call for which the recording resume is
     *                  requested
     * @param loginName the user login name
     * @return a {@link RecordedDevice} representing the device info in case of
     *         success, or {@code null} in case of error
     */
    RecordedDevice resumeRecording(String deviceId, String callRef, String loginName);

    /**
     * Retrieves the status of the recording service.
     *
     * @return a {@link RecordingStatus} representing the current status of the
     *         recording service, or {@code null} in case of error or if no
     *         recording is configured
     */
    RecordingStatus getRecordingStatus();
}
