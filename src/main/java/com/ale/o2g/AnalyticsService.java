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

/**
 * The {@code AnalyticService} service allows to retrieve OmniPCX entreprise
 * charging information and incidents. Using this service requires having a
 * <b>ANALYTICS</b> license. This service requires an administrative login.
 * <p>
 * O2G uses SSH to get the information from an OmniPCX Enterprise node. So
 * <b>SSH must be enabled</b> on the OmniPCX Enterprise node to use this
 * service.
 */
public interface AnalyticsService extends IService {

    /**
     * Returns a list of incidents from the specified OmniPCX Enterprise node.
     * 
     * @param nodeId the OmniPCX Enterprise node id
     * @return The list of incidents in progress in case of success; {@code null}
     *         otherwise.
     */
    Collection<Incident> getIncidents(int nodeId);

    /**
     * Returns a list of the N latest incidents from the specified OmniPCX
     * Enterprise node.
     * 
     * @param nodeId the OmniPCX Enterprise node id
     * @param last   the number of incidents to retrieve
     * @return The list of incidents in progress in case of success; {@code null}
     *         otherwise.
     */
    Collection<Incident> getIncidents(int nodeId, int last);

    /**
     * Get the list of charging file from the specified node.
     * 
     * @param nodeId the OmniPCX Enterprise node id
     * @return the list of {@link ChargingFile} that represents the loaded charging
     *         files.
     * @see #getChargings(int, List, Integer, boolean)
     */
    Collection<ChargingFile> getChargingFiles(int nodeId);

    /**
     * Get the list of charging file from the specified node, using the time range
     * filter.
     * 
     * @param nodeId the OmniPCX Enterprise node id
     * @param filter a time range filter
     * @return the list of {@link ChargingFile} that represents the loaded charging
     *         files.
     * @see #getChargings(int, List, Integer, boolean)
     */
    Collection<ChargingFile> getChargingFiles(int nodeId, TimeRange filter);

    /**
     * Query the charging information for the specified node, using the specified
     * options.
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
     * @param topResults allow to return only the 'top N' tickets
     * @param all        {@code true} to include tickets with a 0 cost
     * @return A {@link ChargingResult} object that represents the result of the
     *         query or {@code null} in case of error or if the specified filter
     *         does not return any result.
     */
    ChargingResult getChargings(int nodeId, TimeRange filter, Integer topResults, boolean all);

    /**
     * Query the charging information for the specified node, using the specified
     * options.
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
     */
    ChargingResult getChargings(int nodeId, TimeRange filter, boolean all);

    /**
     * Query the charging information for the specified node, using the specified
     * options.
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
     * Query the charging information for the specified node, using the specified
     * options.
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
