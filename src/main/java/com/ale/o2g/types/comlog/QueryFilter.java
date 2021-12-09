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
 * {@code QueryFilter} class represents a communication log query filter. It is
 * used to filter records in a
 * {@linkplain com.ale.o2g.CommunicationLogService#getComRecords(QueryFilter, Page, boolean, String)
 * CommunicationLogService.getComRecords} query.
 */
public final class QueryFilter {

    /**
     * A builder of {@linkplain QueryFilter}.
     *
     * <p>
     * Instances of {@code QueryFilter.Builder} are created by calling
     * {@link QueryFilter#newBuilder()}.
     *
     * <p>
     * The builder can be used to configure a query filter. Each methods modifies
     * the state of the builder and returns the same instance. The {@link #build()
     * build} method returns a new {@code QueryFilter} each time it is invoked.
     */
    public static interface Builder {

        /**
         * Sets the start date.
         * 
         * @param afterDate the start date
         * @return this builder.
         * @see QueryFilter#getAfter()
         */
        Builder setAfterDate(Date afterDate);

        /**
         * Sets the end date.
         * 
         * @param beforeDate the end date
         * @return beforeDatethis builder.
         * @see QueryFilter#getBefore()
         */
        Builder setBeforeDate(Date beforeDate);

        /**
         * Sets the options.
         * 
         * @param options the options
         * @return this builder.
         * @see QueryFilter#getOptions()
         */
        Builder setOptions(EnumSet<Option> options);


        /**
         * Adds the specified option.
         * 
         * @param option the option
         * @return this builder.
         * @see QueryFilter#getOptions()
         */
        Builder addOption(Option option);

        /**
         * Sets the call reference.
         * @param callRef the call reference
         * @return this builder.
         * @see QueryFilter#getCallRef()
         */
        Builder setCallRef(String callRef);


        /**
         * Sets the remote party id.
         * @param remotePartyId the remote party id
         * @return this builder.
         * @see QueryFilter#getRemotePartyId()
         */
        Builder setRemotePartyId(String remotePartyId);

        /**
         * Sets the role.
         * @param role the role
         * @return this builder.
         * @see QueryFilter#getRole()
         */
        Builder setRole(Role role);

        /**
         * Builds and returns a {@link QueryFilter}.
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
     * Returns the start date from which the query searches for matching the
     * records. When used, the query returns the records not older than this date.
     * When omitted the query searches for matching records starting from the
     * oldest.
     * 
     * @return the start date.
     */
    public final Date getAfter() {
        return after;
    }

    /**
     * Returns the end date from which the query stop searching the records. When
     * used the query returns the records older than this date. When omitted the
     * query searches for matching records until the newest.
     * 
     * @return the before date
     */
    public final Date getBefore() {
        return before;
    }

    /**
     * Returns the search options.
     * 
     * @return the options
     */
    public final EnumSet<Option> getOptions() {
        return options;
    }

    /**
     * Returns the call reference used in this query filter. When used, the query
     * will retrieve only the com record related to this call reference.
     * 
     * @return the call reference.
     */
    public final String getCallRef() {
        return callRef;
    }

    /**
     * Returns the remote party. When used, filters on the records in which the user
     * is engaged with this remote party.
     * 
     * @return the remote party identifier.
     */
    public final String getRemotePartyId() {
        return remotePartyId;
    }

    /**
     * Returns the user's role in the communication. Allows to filter on the user's
     * role in the communication.
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
     * Creates a {@code Builder} builder.
     *
     * @return a new QueryFilter builder.
     */
    public Builder newBuilder() {
        return new Builder() {
            private Date after;
            private Date before;
            private EnumSet<Option> options;
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
                return new QueryFilter(before, after, options, callRef, remotePartyId, role);
            }
        };
    }
}
