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
import com.ale.o2g.types.comlog.ComRecord;
import com.ale.o2g.types.comlog.Page;
import com.ale.o2g.types.comlog.QueryFilter;
import com.ale.o2g.types.comlog.QueryResult;

/**
 * The {@code CommunicationLogService} service allows a user to retrieve his last
 * communication history records and to manage them. Using this service requires
 * having a <b>TELEPHONY_ADVANCED</b> license.
 */
public interface CommunicationLogService extends IService {

    /**
     * Gets the com records corresponding to the specified filter, using the
     * specified page, with a possible optimization.
     * <p>
     * If {@code optimized} is used and set to {@code true} the query returns the
     * full identity of a participant only the first time it occurs, when the same
     * participant appears in several records. When omitted this query returns the
     * records with no optimization.
     * <p>
     * {@code page} parameter allows to query the communication log by page. The
     * {@link QueryResult} result contains the same parameters and the total number
     * of records retrieved by the query.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param filter    the filter describing the query criterias
     * @param page      the page description
     * @param optimized {@code true} to activate optimization.
     * @param loginName the user login name
     * @return the results of this query in case of success; {@code null} otherwise.
     */
    QueryResult getComRecords(QueryFilter filter, Page page, boolean optimized, String loginName);

    /**
     * Gets the com records corresponding to the specified filter, using the
     * specified page, with a possible optimization.
     * <p>
     * If {@code optimized} is used and set to {@code true} the query returns the
     * full identity of a participant only the first time it occurs, when the same
     * participant appears in several records. When omitted this query returns the
     * records with no optimization.
     * <p>
     * {@code page} parameter allows to query the communication log by page. The
     * {@link QueryResult} result contains the same parameters and the total number
     * of records retrieved by the query.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param filter    the filter describing the query criterias
     * @param page      the page description
     * @param optimized {@code true} to activate optimization.
     * @return the results of this query in case of success; {@code null} otherwise.
     */
    QueryResult getComRecords(QueryFilter filter, Page page, boolean optimized);

    /**
     * Gets the com records corresponding to the specified filter, using the
     * specified page, without optimization.
     * <p>
     * {@code page} parameter allows to query the communication log by page. The
     * {@link QueryResult} result contains the same parameters and the total number
     * of records retrieved by the query.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param filter the filter describing the query criterias
     * @param page   the page description
     * @return the results of this query in case of success; {@code null} otherwise.
     */
    QueryResult getComRecords(QueryFilter filter, Page page);

    /**
     * Gets the com records corresponding to the specified filter.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param filter the filter describing the query criterias
     * @return the results of this query in case of success; {@code null} otherwise.
     */
    QueryResult getComRecords(QueryFilter filter);

    /**
     * Gets the specified com record.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param recordId  the com record identifier
     * @param loginName the user login name
     * @return the com record corresponding to the specified identifier, or
     *         {@code null} in case or error or if there is no com record with such
     *         identifier.
     */
    ComRecord getComRecord(long recordId, String loginName);

    /**
     * Gets the specified com record.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param recordId  the com record identifier
     * @return the com record corresponding to the specified identifier, or
     *         {@code null} in case or error or if there is no com record with such
     *         identifier.
     */
    ComRecord getComRecord(long recordId);

    /**
     * Deletes the specified com record.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param recordId  the com record identifier
     * @param loginName the user login name
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean deleteComRecord(long recordId, String loginName);

    /**
     * Deletes the specified com record.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param recordId  the com record identifier
     * @return the com record corresponding to the specified identifier, or
     *         {@code null} in case or error or if there is no com record with such
     *         identifier.
     */
    boolean deleteComRecord(long recordId);

    /**
     * Deletes the com records corresponding to the given filter.
     * <p>
     * When used, the {@code filter} parameter defines the search criteria for the
     * delete operation.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsDeletedEvent OnComRecordsDeletedEvent} event is raised
     * containing the list of the com records that have been deleted.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param filter    the filter describing the query criterias
     * @param loginName the user login name
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean deleteComRecords(QueryFilter filter, String loginName);

    /**
     * Deletes the com records corresponding to the given filter.
     * <p>
     * When used, the {@code filter} parameter defines the search criteria for the
     * delete operation.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsDeletedEvent OnComRecordsDeletedEvent} event is raised
     * containing the list of the com records that have been deleted.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param filter    the filter describing the query criterias
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean deleteComRecords(QueryFilter filter);

    /**
     * Deletes the specified list of com records.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsDeletedEvent OnComRecordsDeletedEvent} event is raised
     * containing the list of the com records that have been deleted.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param recordIds the list of com records
     * @param loginName the user login name
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean deleteComRecords(Collection<Long> recordIds, String loginName);

    /**
     * Deletes the specified list of com records.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsDeletedEvent OnComRecordsDeletedEvent} event is raised
     * containing the list of the com records that have been deleted.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param recordIds the list of com records
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean deleteComRecords(Collection<Long> recordIds);

    /**
     * Acknowledge the specified list of com records.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsAckEvent OnComRecordsAckEvent} event is raised
     * containing the list of the com records that have been acknowledged.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param recordIds the list of com records
     * @param loginName the user login name
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean acknowledgeComRecords(Collection<Long> recordIds, String loginName);

    /**
     * Acknowledge the specified list of com records.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsDeletedEvent OnComRecordsDeletedEvent} event is raised
     * containing the list of the com records that have been deleted.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param recordIds the list of com records
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean acknowledgeComRecords(Collection<Long> recordIds);

    /**
     * Acknowledge the specified com record.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsAckEvent OnComRecordsAckEvent} event is raised
     * containing the list of the com records that have been acknowledged.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param recordId  the com record identifier
     * @param loginName the user login name
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean acknowledgeComRecord(long recordId, String loginName);

    /**
     * Acknowledge the specified com record.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsAckEvent OnComRecordsAckEvent} event is raised
     * containing the list of the com records that have been acknowledged.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param recordId  the com record identifier
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean acknowledgeComRecord(long recordId);

    /**
     * Unacknowledge the specified list of com records.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsUnAckEvent OnComRecordsUnAckEvent} event is raised
     * containing the list of the com records that have been unacknowledged.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param recordIds the list of com records
     * @param loginName the user login name
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean unacknowledgeComRecords(Collection<Long> recordIds, String loginName);

    /**
     * Unacknowledge the specified list of com records.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsUnAckEvent OnComRecordsUnAckEvent} event is raised
     * containing the list of the com records that have been unacknowledged.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param recordIds the list of com records
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean unacknowledgeComRecords(Collection<Long> recordIds);

    /**
     * Unacknowledge the specified com record.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsUnAckEvent OnComRecordsUnAckEvent} event is raised
     * containing the list of the com records that have been unacknowledged.
     * <p>
     * If the session has been opened for a user, the {@code loginName} parameter is
     * ignored, but it is mandatory if the session has been opened by an
     * administrator.
     * 
     * @param recordId  the com record identifier
     * @param loginName the user login name
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean unacknowledgeComRecord(long recordId, String loginName);

    /**
     * Unacknowledge the specified com record.
     * <p>
     * An {@link com.ale.o2g.events.comlog.OnComRecordsUnAckEvent OnComRecordsUnAckEvent} event is raised
     * containing the list of the com records that have been unacknowledged.
     * <p>
     * This method with return {@code false} if it is invoked from a session opened
     * by an administrator.
     * 
     * @param recordId  the com record identifier
     * @return {@code true} in case or success; {@code true} otherwize.
     */
    boolean unacknowledgeComRecord(long recordId);
}
