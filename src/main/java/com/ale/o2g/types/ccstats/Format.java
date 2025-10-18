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
 * Defines the available output formats for generated statistical reports.
 * <p>
 * This enumeration specifies how the report data is represented when exported.
 * Supported formats include:
 * <ul>
 *   <li>{@link #CSV} - Comma-separated values format, suitable for text processing and import into spreadsheets.</li>
 *   <li>{@link #EXCEL} - Microsoft Excel-compatible format ({@code .xls}).</li>
 * </ul>
 * @since 2.7.4
 */
public enum Format {
     
    /** 
     * Comma-separated values format ({@code .csv}). 
     */
    @SerializedName("csv")
    CSV,

    /** 
     * Microsoft Excel format ({@code .xls}). 
     */
    @SerializedName("xls")
    EXCEL
}
