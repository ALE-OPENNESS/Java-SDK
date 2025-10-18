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
package com.ale.o2g.types.comlog;

import java.util.Date;
import java.util.EnumSet;

/**
 * {@code QueryFilter} represents a filter used to query communication log records.
 * <p>
 * This filter can be applied when retrieving records via
 * {@link com.ale.o2g.CommunicationLogService#getComRecords(QueryFilter, Page, boolean, String)
 * CommunicationLogService.getComRecords}.
 * <p>
 * The filter allows specifying a date range, call reference, participant role, remote party,
 * and additional options such as unanswered or unacknowledged calls.
 *
 */
public final class QueryFilter {

    /**
     * Builder for {@link QueryFilter}.
     * <p>
     * Instances are created via {@link QueryFilter#newBuilder()}.
     * The builder allows chaining setter methods to configure the filter.
     * Each call to {@link #build()} returns a new immutable {@code QueryFilter}.
     */
    public static interface Builder {

        /**
         * Sets the start date of the query.
         * <p>
         * Only records not older than this date will be returned.
         *
         * @param afterDate the start date
         * @return this builder
         * @see QueryFilter#getAfter()
         */
        Builder setAfterDate(Date afterDate);

        /**
         * Sets the end date of the query.
         * <p>
         * Only records not newer than this date will be returned.
         *
         * @param beforeDate the end date
         * @return this builder
         * @see QueryFilter#getBefore()
         */
        Builder setBeforeDate(Date beforeDate);

        /**
         * Sets the search options.
         *
         * @param options the options
         * @return this builder
         * @see QueryFilter#getOptions()
         */
        Builder setOptions(EnumSet<Option> options);

        /**
         * Adds a single search option.
         *
         * @param option the option
         * @return this builder
         * @see QueryFilter#getOptions()
         */
        Builder addOption(Option option);

        /**
         * Sets the call reference to filter on.
         *
         * @param callRef the call reference
         * @return this builder
         * @see QueryFilter#getCallRef()
         */
        Builder setCallRef(String callRef);

        /**
         * Sets the remote party identifier to filter on.
         *
         * @param remotePartyId the remote party ID
         * @return this builder
         * @see QueryFilter#getRemotePartyId()
         */
        Builder setRemotePartyId(String remotePartyId);

        /**
         * Sets the user's role in the communication to filter on.
         *
         * @param role the participant role
         * @return this builder
         * @see QueryFilter#getRole()
         */
        Builder setRole(Role role);

        /**
         * Builds and returns a new {@link QueryFilter} instance with the configured settings.
         *
         * @return a new {@code QueryFilter}
         */
        QueryFilter build();
    }

    private Date after;
    private Date before;
    private EnumSet<Option> options;
    private String callRef;
    private String remotePartyId;
    private Role role;

    /**
     * Returns the start date of the query filter.
     *
     * @return the start date
     */
    public final Date getAfter() {
        return after;
    }

    /**
     * Returns the end date of the query filter.
     *
     * @return the end date
     */
    public final Date getBefore() {
        return before;
    }

    /**
     * Returns the set of options applied to the query filter.
     *
     * @return the options
     */
    public final EnumSet<Option> getOptions() {
        return options;
    }

    /**
     * Returns the call reference used for filtering.
     *
     * @return the call reference
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Returns the remote party identifier used for filtering.
     *
     * @return the remote party ID
     */
    public final String getRemotePartyId() {
        return remotePartyId;
    }

    /**
     * Returns the participant role used for filtering.
     *
     * @return the role
     */
    public final Role getRole() {
        return role;
    }

    protected QueryFilter(Date after, Date before, EnumSet<Option> options, String callRef, String remotePartyId,
                          Role role) {
        this.after = after;
        this.before = before;
        this.options = options;
        this.callRef = callRef;
        this.remotePartyId = remotePartyId;
        this.role = role;
    }

    /**
     * Creates a new {@link Builder} instance for constructing a {@link QueryFilter}.
     *
     * @return a new builder
     */
    public Builder newBuilder() {
        return new Builder() {
            private Date after;
            private Date before;
            private EnumSet<Option> options = EnumSet.noneOf(Option.class);
            private String callRef;
            private String remotePartyId;
            private Role role;

            @Override
            public Builder setAfterDate(Date afterDate) {
                this.after = afterDate;
                return this;
            }

            @Override
            public Builder setBeforeDate(Date beforeDate) {
                this.before = beforeDate;
                return this;
            }

            @Override
            public Builder setOptions(EnumSet<Option> options) {
                this.options = options;
                return this;
            }

            @Override
            public Builder addOption(Option option) {
                this.options.add(option);
                return this;
            }

            @Override
            public Builder setCallRef(String callRef) {
                this.callRef = callRef;
                return this;
            }

            @Override
            public Builder setRemotePartyId(String remotePartyId) {
                this.remotePartyId = remotePartyId;
                return this;
            }

            @Override
            public Builder setRole(Role role) {
                this.role = role;
                return this;
            }

            @Override
            public QueryFilter build() {
                return new QueryFilter(after, before, options, callRef, remotePartyId, role);
            }
        };
    }
}
