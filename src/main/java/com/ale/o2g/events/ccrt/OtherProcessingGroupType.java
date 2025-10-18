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
package com.ale.o2g.events.ccrt;

import com.google.gson.annotations.SerializedName;

/**
 * {@code OtherProcessingGroupType} represents the different types of processing groups 
 * (other than agent groups).
 * <p>
 * These types indicate the role or function of the processing group in the call center system.
 *
 * @see com.ale.o2g.events.ccrt.OnPGOtherRtiChangedEvent
 * @since 2.7.4
 */
public enum OtherProcessingGroupType {

    /**
     * Mutual aid processing group.
     */
    @SerializedName(value = "mutualAid")
    MUTUAL_AID,

    /**
     * Forward processing group.
     */
    @SerializedName(value = "forward")
    FORWARD,

    /**
     * Guide processing group.
     */
    @SerializedName(value = "guide")
    GUIDE,

    /**
     * Remote processing group.
     */
    @SerializedName(value = "remote")
    REMOTE,

    /**
     * Unknown or undefined type.
     */
    @SerializedName(value = "unknown")
    UNKNOWN
}
