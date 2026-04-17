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
import com.ale.o2g.types.rsi.AdditionalDigitCollectionCriteria;
import com.ale.o2g.types.rsi.RouteSession;
import com.ale.o2g.types.rsi.RsiPoint;
import com.ale.o2g.types.rsi.Tones;

/**
 * {@code RsiService} provides access to the RSI (Routing Service Intelligence)
 * point features:
 * <ul>
 * <li>Makes route selection.</li>
 * <li>Makes digits collection.</li>
 * <li>Plays voice guides or tones.</li>
 * <li>Plays announcements (prompts and/or digits).</li>
 * </ul>
 * <p>
 * To be able to receive the route requests from the OmniPCX Enterprise, the
 * application must first subscribe to RSI events and then enable the RSI point.
 * <p>
 * Using this service requires having a <b>CONTACTCENTER_RSI</b> license.
 * @hidden
 */
public interface RsiService extends IService {

    /**
     * Gets the configured RSI points.
     *
     * @return A collection of {@linkplain com.ale.o2g.types.rsi.RsiPoint RsiPoint} representing all the declared RSI points.
     */
    Collection<RsiPoint> getRsiPoints();

    /**
     * Enables the specified RSI point.
     *
     * @param rsiNumber the RSI point extension number
     * @param backup    {@code true} to enable the RSI point in backup mode
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean enableRsiPoint(String rsiNumber, boolean backup);

    /**
     * Disables the specified RSI point.
     *
     * @param rsiNumber the RSI point extension number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean disableRsiPoint(String rsiNumber);

    /**
     * Starts a digits collection on the specified RSI point, for the specified call.
     *
     * @param rsiNumber          the RSI point extension number
     * @param callRef            the call reference
     * @param nbChars            the optional number of digits to collect; the digit collection stops when this number is reached
     * @param flushChar          the optional character that stops the digit collection when pressed
     * @param timeout            the optional timeout in seconds; the digit collection stops when this delay elapses
     * @param additionalCriteria extension criteria used to collect digits
     * @return A unique identifier (Crid) for this digit collection session.
     * @see com.ale.o2g.events.rsi.OnDigitCollectedEvent OnDigitCollectedEvent
     * @see #stopCollectDigit(String, String) stopCollectDigit
     */
    String startCollectDigit(String rsiNumber, String callRef, Integer nbChars, Character flushChar, Integer timeout,
            AdditionalDigitCollectionCriteria additionalCriteria);

    /**
     * Stops the specified digits collection on the specified RSI point.
     *
     * @param rsiNumber the RSI point extension number
     * @param callCrid  the digit collection identifier returned by {@link #startCollectDigit(String, String, Integer, Character, Integer, AdditionalDigitCollectionCriteria) startCollectDigit}
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #startCollectDigit(String, String, Integer, Character, Integer,
     *      AdditionalDigitCollectionCriteria) startCollectDigit
     */
    boolean stopCollectDigit(String rsiNumber, String callCrid);

    /**
     * Plays the specified tone on the specified call.
     *
     * @param rsiNumber the RSI point extension number
     * @param callRef   the call reference
     * @param tone      the tone to play
     * @param duration  the duration the tone is played, in seconds
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnToneGeneratedStartEvent OnToneGeneratedStartEvent
     * @see #cancelTone(String, String) cancelTone
     */
    boolean playTone(String rsiNumber, String callRef, Tones tone, int duration);

    /**
     * Cancels the tone currently playing on the specified call.
     *
     * @param rsiNumber the RSI point extension number
     * @param callRef   the call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnToneGeneratedStopEvent OnToneGeneratedStopEvent
     * @see #playTone(String, String, Tones, int) playTone
     */
    boolean cancelTone(String rsiNumber, String callRef);

    /**
     * Plays the specified voice guide on the specified call.
     *
     * @param rsiNumber   the RSI point extension number
     * @param callRef     the call reference
     * @param guideNumber the voice guide number as defined in the OmniPCX Enterprise
     * @param duration    an optional duration for the voice guide, in seconds
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnToneGeneratedStartEvent OnToneGeneratedStartEvent
     */
    boolean playVoiceGuide(String rsiNumber, String callRef, int guideNumber, Integer duration);

    /**
     * Ends a route session, indicating that no route will be selected.
     *
     * @param rsiNumber the RSI point extension number
     * @param routeCrid the routing session unique identifier
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnRouteRequestEvent OnRouteRequestEvent
     */
    boolean routeEnd(String rsiNumber, String routeCrid);

    /**
     * Selects a route as a response to a route request.
     * <p>
     * {@code callingLine} can be used to change the identity of the calling number presented to the called party.
     *
     * @param rsiNumber        the RSI point extension number
     * @param routeCrid        the routing session unique identifier
     * @param selectedRoute    the selected route number
     * @param callingLine      an optional calling line number that will be presented to the selected route
     * @param associatedData   optional correlator data to attach to the call
     * @param routeToVoiceMail {@code true} if the selected route is the voice mail; {@code false} otherwise
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnRouteRequestEvent OnRouteRequestEvent
     */
    boolean routeSelect(String rsiNumber, String routeCrid, String selectedRoute, String callingLine,
            String associatedData, Boolean routeToVoiceMail);

    /**
     * Gets the list of existing route sessions for the specified RSI point.
     *
     * @param rsiNumber the RSI point extension number
     * @return A collection of {@linkplain com.ale.o2g.types.rsi.RouteSession RouteSession} representing the route sessions in progress for this RSI point.
     */
    Collection<RouteSession> getRouteSessions(String rsiNumber);

    /**
     * Returns the specified route session.
     *
     * @param rsiNumber the RSI point extension number
     * @param routeCrid the routing session unique identifier
     * @return A {@linkplain com.ale.o2g.types.rsi.RouteSession RouteSession} object, or {@code null} in case of error or if there is no such route session.
     */
    RouteSession getRouteSession(String rsiNumber, String routeCrid);
}
