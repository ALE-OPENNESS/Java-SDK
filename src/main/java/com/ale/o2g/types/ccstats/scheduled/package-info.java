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

/**
 * Provides types related to scheduled statistics reports in the Call Center Statistics Service.
 * <p>
 * This package contains classes and interfaces for defining and managing scheduled reports,
 * including their recurrence patterns, observation periods, and report metadata.
 * <p>
 * Key types include:
 * <ul>
 *   <li>{@link Recurrence} - defines how often a report is generated (daily, weekly, monthly).</li>
 *   <li>{@link ReportObservationPeriod} - defines the period of time over which statistics are collected.</li>
 *   <li>{@link ScheduledReport} - represents a scheduled report, including format, recipients, recurrence, and execution state.</li>
 * </ul>
 *
 * @since 2.7.4
 */
package com.ale.o2g.types.ccstats.scheduled;
