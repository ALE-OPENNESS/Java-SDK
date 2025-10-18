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
import com.ale.o2g.types.analytics.ChargingFile;
import com.ale.o2g.types.analytics.ChargingResult;
import com.ale.o2g.types.analytics.Incident;
import com.ale.o2g.types.analytics.TimeRange;
import com.ale.o2g.types.common.DateRange;

/**
 * The {@code AnalyticsService} provides access to OmniPCX Enterprise charging
 * information and incident reports.
 * <p>
 * Using this service requires an <b>ANALYTICS</b> license and an
 * administrative login. O2G uses SSH to collect the information from an
 * OmniPCX Enterprise node, so <b>SSH must be enabled</b> on the node.
 *
 */
public interface AnalyticsService extends IService {

    /**
     * Retrieves the list of incidents currently in progress on the specified
     * OmniPCX Enterprise node.
     *
     * @param nodeId the OmniPCX Enterprise node identifier
     * @return a collection of {@link Incident incidents} in progress,
     *         or {@code null} if the request fails
     */
    Collection<Incident> getIncidents(int nodeId);

    /**
     * Retrieves the {@code N} most recent incidents from the specified
     * OmniPCX Enterprise node.
     *
     * @param nodeId the OmniPCX Enterprise node identifier
     * @param last   the maximum number of incidents to retrieve
     * @return a collection of {@link Incident incidents}, or {@code null}
     *         if the request fails
     */
    Collection<Incident> getIncidents(int nodeId, int last);

    /**
     * Retrieves the list of charging files from the specified node.
     *
     * @param nodeId the OmniPCX Enterprise node identifier
     * @return a collection of {@link ChargingFile charging files} available
     *         on the node
     * @see #getChargings(int, Collection, Integer, boolean)
     */
    Collection<ChargingFile> getChargingFiles(int nodeId);

    /**
     * Retrieves the list of charging files from the specified node,
     * filtered by a time range.
     *
     * @param nodeId the OmniPCX Enterprise node identifier
     * @param filter a time range filter
     * @return a collection of {@link ChargingFile charging files} that match
     *         the filter
     * @see #getChargings(int, Collection, Integer, boolean)
     * @deprecated Use {@link #getChargingFiles(int, DateRange)} instead.
     */
    @Deprecated
    Collection<ChargingFile> getChargingFiles(int nodeId, TimeRange filter);
    
    /**
     * Retrieves the list of charging files from the specified node,
     * filtered by a date range.
     *
     * @param nodeId the OmniPCX Enterprise node identifier
     * @param filter a date range filter
     * @return a collection of {@link ChargingFile charging files} that match
     *         the filter
     * @see #getChargings(int, Collection, Integer, boolean)
     */
    Collection<ChargingFile> getChargingFiles(int nodeId, DateRange filter);
    
    /**
     * Queries the charging information for the specified node, using a time
     * range filter and the given options.
     * <p>
     * If {@code all} is {@code true}, all tickets are returned (including
     * zero-cost tickets and the called party). If {@code all} is {@code false},
     * only aggregated totals are returned for each user. In this case, the call
     * count reflects only calls with a non-zero charging cost.
     * <p>
     * Processing is limited to a maximum of 100 files for performance reasons.
     * If the filter selects more than 100 files, the method fails and returns
     * {@code null}. A smaller range must then be specified.
     *
     * @param nodeId     the OmniPCX Enterprise node identifier
     * @param filter     a time range filter
     * @param topResults the maximum number of top results to return (optional)
     * @param all        whether to include zero-cost tickets
     * @return a {@link ChargingResult} representing the query result,
     *         or {@code null} if the query fails or produces no results
     * @deprecated Use {@link #getChargings(int, DateRange, Integer, boolean)} instead.
     */
    @Deprecated
    ChargingResult getChargings(int nodeId, TimeRange filter, Integer topResults, boolean all);

    /**
     * Queries the charging information for the specified node, using a date
     * range filter and the given options.
     * <p>
     * If {@code all} is set to {@code true}, all the tickets are returned,
     * including the zero cost ticket, and with the called party; If {@code all} is
     * set to {@code false}, the total of charging info is returned for each user,
     * the call number giving the number of calls with non null charging cost.
     * <p>
     * The request processes charging files on the OmniPCX Enterprise. The
     * processing is limited to a maximum of 100 files for performance reason. If
     * the range filter is too large and the number of file to process is greater
     * than 100, the method fails and returns {@code null}. In this case, a smaller
     * range must be specified.
     * 
     * @param nodeId     the OmniPCX Enterprise node id
     * @param filter     a date range filter
     * @param topResults allow to return only the 'top N' tickets
     * @param all        {@code true} to include tickets with a 0 cost
     * @return A {@link ChargingResult} object that represents the result of the
     *         query or {@code null} in case of error or if the specified filter
     *         does not return any result.
     */
    ChargingResult getChargings(int nodeId, DateRange filter, Integer topResults, boolean all);

    /**
     * Queries the charging information for the specified node, using a time
     * range filter and the given options.
     * <p>
     * If {@code all} is set to {@code true}, all the tickets are returned,
     * including the zero cost ticket, and with the called party; If {@code all} is
     * set to {@code false}, the total of charging info is returned for each user,
     * the call number giving the number of calls with non null charging cost.
     * <p>
     * The request processes charging files on the OmniPCX Enterprise. The
     * processing is limited to a maximum of 100 files for performance reason. If
     * the range filter is too large and the number of file to process is greater
     * than 100, the method fails and returns {@code null}. In this case, a smaller
     * range must be specified.
     * 
     * @param nodeId     the OmniPCX Enterprise node id
     * @param filter     a time range filter
     * @param all        {@code true} to include tickets with a 0 cost
     * @return A {@link ChargingResult} object that represents the result of the
     *         query or {@code null} in case of error or if the specified filter
     *         does not return any result.
     * @deprecated Use {@link #getChargings(int, DateRange, boolean)} instead.
     */
    @Deprecated
    ChargingResult getChargings(int nodeId, TimeRange filter, boolean all);

    /**
     * Queries the charging information for the specified node, using a date
     * range filter and the given options.
     * <p>
     * If {@code all} is set to {@code true}, all the tickets are returned,
     * including the zero cost ticket, and with the called party; If {@code all} is
     * set to {@code false}, the total of charging info is returned for each user,
     * the call number giving the number of calls with non null charging cost.
     * <p>
     * The request processes charging files on the OmniPCX Enterprise. The
     * processing is limited to a maximum of 100 files for performance reason. If
     * the range filter is too large and the number of file to process is greater
     * than 100, the method fails and returns {@code null}. In this case, a smaller
     * range must be specified.
     * 
     * @param nodeId     the OmniPCX Enterprise node id
     * @param filter     a date range filter
     * @param all        {@code true} to include tickets with a 0 cost
     * @return A {@link ChargingResult} object that represents the result of the
     *         query or {@code null} in case of error or if the specified filter
     *         does not return any result.
     */
    ChargingResult getChargings(int nodeId, DateRange filter, boolean all);

    /**
     * Queries the charging information for the specified node, processing
     * the given charging files with the specified options.
     * <p>
     * If {@code all} is set to {@code true}, all the tickets are returned,
     * including the zero cost ticket, and with the called party; If {@code all} is
     * set to {@code false}, the total of charging info is returned for each user,
     * the call number giving the number of calls with non null charging cost.
     * <p>
     * The request processes charging files on the OmniPCX Enterprise. The
     * processing is limited to a maximum of 100 files for performance reason. If
     * the range filter is too large and the number of file to process is greater
     * than 100, the method fails and returns {@code null}.
     * 
     * @param nodeId     the OmniPCX Enterprise node id
     * @param files      the list of file to process
     * @param topResults allow to return only the 'top N' tickets
     * @param all        {@code true} to include tickets with a 0 cost
     * @return A {@link ChargingResult} object that represents the result of the
     *         query or {@code null} in case of error or if the specified filter
     *         does not return any result.
     */
    ChargingResult getChargings(int nodeId, Collection<ChargingFile> files, Integer topResults, boolean all);

    /**
     * Queries the charging information for the specified node, processing
     * the given charging files with the specified options.
     * <p>
     * If {@code all} is set to {@code true}, all the tickets are returned,
     * including the zero cost ticket, and with the called party; If {@code all} is
     * set to {@code false}, the total of charging info is returned for each user,
     * the call number giving the number of calls with non null charging cost.
     * <p>
     * The request processes charging files on the OmniPCX Enterprise. The
     * processing is limited to a maximum of 100 files for performance reason. If
     * the range filter is too large and the number of file to process is greater
     * than 100, the method fails and returns {@code null}.
     * 
     * @param nodeId     the OmniPCX Enterprise node id
     * @param files      the list of file to process
     * @param all        {@code true} to include tickets with a 0 cost
     * @return A {@link ChargingResult} object that represents the result of the
     *         query or {@code null} in case of error or if the specified filter
     *         does not return any result.
     */
    ChargingResult getChargings(int nodeId, Collection<ChargingFile> files, boolean all);
}
