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
package com.ale.o2g.types.ccstats;

import com.google.gson.annotations.SerializedName;

/**
 * Represents fixed intervals of time in minutes.
 * <p>
 * Each constant corresponds to a specific duration:
 * <ul>
 *   <li>{@link #QUARTER_HOUR} - 15 minutes</li>
 *   <li>{@link #HALF_HOUR} - 30 minutes</li>
 *   <li>{@link #HOUR} - 60 minutes</li>
 * </ul>
 * @since 2.7.4
 */
public enum TimeInterval  {

    /** 15-minute interval */
    @SerializedName("aQuarterOfAnHour")
    QUARTER_HOUR,
     
    /** 30-minute interval */
    @SerializedName("halfAnHour")
    HALF_HOUR,
     
    /** 60-minute interval */
    @SerializedName("anHour")
    HOUR
}
