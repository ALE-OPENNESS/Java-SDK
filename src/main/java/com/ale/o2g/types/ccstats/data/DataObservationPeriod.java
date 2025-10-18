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
package com.ale.o2g.types.ccstats.data;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the observation period over which statistical data is collected.
 * <p>
 * This enumeration is used to indicate whether the statistics cover a single day
 * or extend across multiple consecutive days. It is typically used when requesting
 * reports or querying data from the Call Center Statistics Service.
 *
 * @since 2.7.4
 */
public enum DataObservationPeriod {

    /**
     * Indicates that the observations are collected over a single calendar day.
     */
    @SerializedName("oneDay")
    ONE_DAY,

    /**
     * Indicates that the observations are collected over several consecutive days.
     */
    @SerializedName("onSeveralDays")
    ON_SEVERAL_DAYS
}
