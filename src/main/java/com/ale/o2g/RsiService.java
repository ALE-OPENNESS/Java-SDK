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
 * {@code RsiService} provides access to th RSI (Routing Service Intelligence)
 * points features:
 * <ul>
 * <li>Makes route selection.</li>
 * <li>Makes digits collection.</li>
 * <li>Plays voice guides or tones.</li>
 * <li>Plays announcements (prompts and/or digits).</li>
 * </ul>
 * <p>
 * To be able to receive the RouteRequest from the OmniPCX Enterprise, the first
 * action is subscribe to rsi events and the second action is to enable the RSI
 * point.
 * <p>
 * Using this service requires having a <b>CONTACTCENTER_RSI</b> license.
 */
public interface RsiService extends IService {

    /**
     * Gets the configured Rsi points.
     * 
     * @return a collection of {@linkplain com.ale.o2g.types.rsi.RsiPoint RsiPoint}.
     */
    Collection<RsiPoint> getRsiPoints();

    /**
     * Enables the specified rsi point.
     * 
     * @param rsiNumber the rsi point extension number
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean enableRsiPoint(String rsiNumber);

    /**
     * Disables the specified rsi point.
     * 
     * @param rsiNumber the rsi point extension number.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean disableRsiPoint(String rsiNumber);

    /**
     * Starts a digits collection for the specified rsi, on the specified call.
     * 
     * @param rsiNumber          the rsi point extension number
     * @param callRef            the call reference
     * @param nbChars            the optionnal number of digits to collect. The
     *                           digit collection is stopped when this number is
     *                           reached
     * @param flushChar          the optional character used to stop the digit
     *                           collection when pressed.
     * @param timeout            optional timeout in second. Stop the digit
     *                           collection after this time elapses.
     * @param additionalCriteria extension criteria used to collect digits
     * @return the digits collection Crid. A unique identifier for the collection.
     * @see com.ale.o2g.events.rsi.OnDigitCollectedEvent OnDigitCollectedEvent
     * @see #stopCollectDigit(String, String) stopCollectDigit
     */
    String startCollectDigit(String rsiNumber, String callRef, Integer nbChars, Character flushChar, Integer timeout,
            AdditionalDigitCollectionCriteria additionalCriteria);

    /**
     * Stops the specified digits collection.
     * 
     * @param rsiNumber the rsi point extension number
     * @param callCrid  the digit collection identifier
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see #startCollectDigit(String, String, Integer, Character, Integer,
     *      AdditionalDigitCollectionCriteria) startCollectDigit
     */
    boolean stopCollectDigit(String rsiNumber, String callCrid);

    /**
     * Plays the specified tone on the specified call.
     * 
     * @param rsiNumber the rsi point extension number
     * @param callRef   the call reference
     * @param tone      the tone to play
     * @param duration  the duration the tone is played (in second)
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnToneGeneratedStartEvent
     *      OnToneGeneratedStartEvent.
     * @see #cancelTone(String, String) cancelTone.
     */
    boolean playTone(String rsiNumber, String callRef, Tones tone, int duration);

    /**
     * Cancels playing a tone on the specified call.
     * 
     * @param rsiNumber the rsi point extension number
     * @param callRef   the call reference
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnToneGeneratedStopEvent
     *      OnToneGeneratedStopEvent.
     * @see #playTone(String, String, Tones, int) playTone.
     */
    boolean cancelTone(String rsiNumber, String callRef);

    /**
     * Plays the specified voice guide on the specified call.
     * 
     * @param rsiNumber   the rsi point extension number
     * @param callRef     the call reference
     * @param guideNumber the voice guide number as defined in the OmniPcx
     *                    Enterprise
     * @param duration    an optional duration for the voice guide in second.
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnToneGeneratedStartEvent
     *      OnToneGeneratedStartEvent.
     */
    boolean playVoiceGuide(String rsiNumber, String callRef, int guideNumber, Integer duration);

    /**
     * Ends a route session.
     * 
     * @param rsiNumber the rsi point extension number
     * @param routeCrid the routing session unique identifier
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnRouteRequestEvent OnRouteRequestEvent.
     */
    boolean routeEnd(String rsiNumber, String routeCrid);

    /**
     * Selects a route for the specified route session. 
     * @param rsiNumber        the rsi point extension number
     * @param routeCrid        the routing session unique identifier
     * @param selectedRoute    the selected route number
     * @param callingLine      an optional calling line value that will be presented
     *                         to the selected route
     * @param associatedData   the optional associated data to attach to the call
     * @param routeToVoiceMail {@code true} iif the selected route is the voice
     *                         mail; {@code false} otherwise.
     * @return {@code true} in case of success; {@code false} otherwise.
     * @see com.ale.o2g.events.rsi.OnRouteRequestEvent OnRouteRequestEvent.
     */
    boolean routeSelect(String rsiNumber, String routeCrid, String selectedRoute, String callingLine,
            String associatedData, Boolean routeToVoiceMail);

    /**
     * Gets the list of existing route sessions for the specified rsi point.
     * 
     * @param rsiNumber the rsi point extension number
     * @return a collection of {@linkplain com.ale.o2g.types.rsi.RouteSession
     *         RouteSession}
     */
    Collection<RouteSession> getRouteSessions(String rsiNumber);

    /**
     * Return the specified route session.
     * 
     * @param rsiNumber the rsi point extension number
     * @param routeCrid the routing session unique identifier
     * @return a {@linkplain com.ale.o2g.types.rsi.RouteSession RouteSession} object
     *         or {@code null} in case of error or if there is no such route
     *         session.
     */
    RouteSession getRouteSession(String rsiNumber, String routeCrid);
}
