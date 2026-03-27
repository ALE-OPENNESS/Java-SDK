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
package com.ale.o2g.types.analytics;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * {@code Charging} represents a charging digest information.
 */
public class Charging {

    private String caller;
    private String name;
    private String called;
    private String initialDialedNumber;
    private int callNumber;
    private int chargingUnits;
    private float cost;
    private Date startDate;
    private int duration;
    private CallType callType;
    private int effectiveCallDuration;
    private int actingExtensionNumberNode;
    private Collection<TelFacility> internalFacilities;
    private Collection<TelFacility> externalFacilities;

    /**
     * Returns the caller phone number.
     * @return the caller
     */
    public final String getCaller() {
        return caller;
    }

    /**
     * Returns the caller name.
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the called phone number.
     * @return the called
     */
    public final String getCalled() {
        return called;
    }

    /**
     * Returns the initial dialed number.
     * <p>
     * This information is provided only if the query has been done with the {@code all} option.
     * @return the initialDialedNumber
     * @see com.ale.o2g.AnalyticsService#getChargings(int, DateRange, Integer, boolean) AnalyticsService.getChargings
     */
    public final String getInitialDialedNumber() {
        return initialDialedNumber;
    }

    /**
     * Returns the number of charged calls.
     * <p>
     * This information is provided only if the query has been done with the {@code all} option.
     * @return the callNumber
     * @see com.ale.o2g.AnalyticsService#getChargings(int, DateRange, Integer, boolean) AnalyticsService.getChargings
     */
    public final int getCallNumber() {
        return callNumber;
    }

    /**
     * Returns the number of charged units.
     * <p>
     * This information is provided only if the query has been done with the {@code all} option.
     * @return the chargingUnits
     * @see com.ale.o2g.AnalyticsService#getChargings(int, DateRange, Integer, boolean) AnalyticsService.getChargings
     */
    public final int getChargingUnits() {
        return chargingUnits;
    }

    /**
     * Returns the call charging cost.
     * <p>
     * This information is provided only if the query has been done with the {@code all} option.
     * @return the cost
     * @see com.ale.o2g.AnalyticsService#getChargings(int, DateRange, Integer, boolean) AnalyticsService.getChargings
     */
    public final float getCost() {
        return cost;
    }

    /**
     * Returns the call start date.
     * <p>
     * This information is provided only if the query has been done with the {@code all} option.
     * @return the startDate
     * @see com.ale.o2g.AnalyticsService#getChargings(int, DateRange, Integer, boolean) AnalyticsService.getChargings
     */
    public final Date getStartDate() {
        return startDate;
    }

    /**
     * Returns the call duration.
     * @return the duration
     */
    public final int getDuration() {
        return duration;
    }

    /**
     * Returns the call type.
     * <p>
     * This information is provided only if the query has been done with the {@code all} option.
     * @return the callType
     * @see com.ale.o2g.AnalyticsService#getChargings(int, DateRange, Integer, boolean) AnalyticsService.getChargings
     */
    public final CallType getCallType() {
        return callType;
    }

    /**
     * Returns the effective call duration.
     * @return the effectiveCallDuration
     */
    public final int getEffectiveCallDuration() {
        return effectiveCallDuration;
    }

    /**
     * Returns the acting extension node number.
     * @return the actingExtensionNumberNode
     */
    public final int getActingExtensionNumberNode() {
        return actingExtensionNumberNode;
    }

        
    /**
     * Return the internal facilities.
     * @return the internal facilities.
     */
    public final Collection<TelFacility> getInternalFacilities() {
        return internalFacilities;
    }

    /**
     * Return the external facilities.
     * @return the external facilities.
     */
    public final Collection<TelFacility> getExternalFacilities() {
        return externalFacilities;
    }

    protected Charging(String caller, String name, String called, String initialDialedNumber, int callNumber,
            int chargingUnits, float cost, Date startDate, int duration, CallType callType, int effectiveCallDuration,
            int actingExtensionNumberNode, List<TelFacility> internalFacilities, List<TelFacility> externalFacilities) {
        this.caller = caller;
        this.name = name;
        this.called = called;
        this.initialDialedNumber = initialDialedNumber;
        this.callNumber = callNumber;
        this.chargingUnits = chargingUnits;
        this.cost = cost;
        this.startDate = startDate;
        this.duration = duration;
        this.callType = callType;
        this.effectiveCallDuration = effectiveCallDuration;
        this.actingExtensionNumberNode = actingExtensionNumberNode;
        this.internalFacilities = internalFacilities;
        this.externalFacilities = externalFacilities;
    }
}
