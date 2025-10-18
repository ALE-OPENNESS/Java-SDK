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
package com.ale.o2g.types.directory;

import java.util.Arrays;

/**
 * {@code Criteria} allows specifying a filter to apply when searching the
 * directory using {@link com.ale.o2g.DirectoryService}.
 * <p>
 * A simple criteria is a tuple of the form: {@code [Attribute, Operation, Value]}.
 * For example: {@code [LAST_NAME, BEGINS_WITH, "fr"]}.
 * <p>
 * A {@code Criteria} can also be a logical combination (AND/OR) of multiple other
 * {@code Criteria} instances.
 * 
 * <p><b>Attributes:</b>
 * <table>
 * <caption>Available attribute filters</caption>
 * <thead>
 * <tr><th>Value</th><th>Description</th></tr>
 * </thead>
 * <tbody>
 * <tr><td>LASTNAME</td><td>The last name of the user.</td></tr>
 * <tr><td>FIRSTNAME</td><td>The first name of the user.</td></tr>
 * <tr><td>LOGIN_NAME</td><td>The login name of the user.</td></tr>
 * <tr><td>PHONE_NUMBER</td><td>The phone number of the user.</td></tr>
 * </tbody>
 * </table>
 * 
 * <p><b>Operations:</b>
 * <table>
 * <caption>Available operation filters</caption>
 * <thead>
 * <tr><th>Value</th><th>Description</th></tr>
 * </thead>
 * <tbody>
 * <tr><td>EQUAL_IGNORE_CASE</td><td>Attribute is equal to the given value (case-insensitive).</td></tr>
 * <tr><td>BEGINS_WITH</td><td>Attribute starts with the given value.</td></tr>
 * <tr><td>CONTAINS</td><td>Attribute contains the given value.</td></tr>
 * <tr><td>ENDS_WITH</td><td>Attribute ends with the given value.</td></tr>
 * </tbody>
 * </table>
 * 
 * <p><b>Examples:</b>
 * <pre>{@code
 * // Search users whose last name begins with "b"
 * Criteria criteria = Criteria.create(
 *     Criteria.AttributeFilter.LASTNAME, 
 *     Criteria.OperationFilter.BEGINS_WITH, 
 *     "b"
 * );
 * 
 * // Search users whose last name begins with "b" AND first name contains "ja"
 * Criteria criteria = Criteria.AND(
 *     Criteria.create(
 *         Criteria.AttributeFilter.LASTNAME,
 *         Criteria.OperationFilter.BEGINS_WITH,
 *         "b"
 *     ),
 *     Criteria.create(
 *         Criteria.AttributeFilter.FIRSTNAME,
 *         Criteria.OperationFilter.CONTAINS,
 *         "ja"
 *     )
 * );
 * }</pre>
 */
public class Criteria {

    /**
     * {@code AttributeFilter} represents the attributes that can be used to filter
     * a directory search.
     */
    public static enum AttributeFilter {
        /** The last name of the user. */
        LASTNAME,
        /** The first name of the user. */
        FIRSTNAME,
        /** The phone number of the user. */
        PHONE_NUMBER,
        /** The login name of the user. */
        LOGIN_NAME
    }
    
    /**
     * {@code OperationFilter} represents the operation applied to an attribute when
     * filtering a directory search.
     */
    public static enum OperationFilter {
        /** The attribute is equal to the given value (case-insensitive). */
        EQUAL_IGNORE_CASE,
        /** The attribute starts with the given value. */
        BEGINS_WITH,
        /** The attribute contains the given value. */
        CONTAINS,
        /** The attribute ends with the given value. */
        ENDS_WITH
    }

    private String operation;
    private String field;
    @SuppressWarnings("unused")
    private Object operand;

    protected Criteria(String field, String operation, Object operand) {
        this.operation = operation;
        this.field = field;
        this.operand = operand;
    }

    private static String toField(AttributeFilter attr) {
        switch (attr) {
            case LASTNAME: return "lastName";
            case FIRSTNAME: return "firstName";
            case PHONE_NUMBER: return "id.phoneNumber";
            case LOGIN_NAME: 
            default: return "id.loginName";
        }
    }

    private static String toOperation(OperationFilter operation) {
        switch (operation) {
            case BEGINS_WITH: return "BEGIN_WITH";
            case EQUAL_IGNORE_CASE: return "EQUAL_IGNORE_CASE";
            case CONTAINS: return "CONTAIN";
            case ENDS_WITH: 
            default: return "END_WITH";
        }
    }

    /**
     * Creates a new {@code Criteria} with the specified attribute filter, operation, and value.
     *
     * @param field     the attribute to filter
     * @param operation the operation to apply
     * @param operand   the value to match
     * @return a new {@code Criteria} instance
     */
    public static Criteria create(AttributeFilter field, OperationFilter operation, String operand) {
        return new Criteria(toField(field), toOperation(operation), operand);
    }

    /**
     * Creates a {@code Criteria} that is the logical AND of multiple other criteria.
     *
     * @param criterias one or more {@code Criteria} objects
     * @return a new {@code Criteria} representing the AND combination
     */
    public static Criteria AND(Criteria... criterias) {
        return new Criteria(null, "AND", Arrays.asList(criterias));
    }

    /**
     * Creates a {@code Criteria} that is the logical OR of multiple other criteria.
     *
     * @param criterias one or more {@code Criteria} objects
     * @return a new {@code Criteria} representing the OR combination
     */
    public static Criteria OR(Criteria... criterias) {
        return new Criteria(null, "OR", Arrays.asList(criterias));
    }

    /** 
     * Returns this filter's operation.
     * 
     * @return the operation of the filter as a {@link String}
     */
    public final String getOperation() {
        return operation;
    }

    /** 
     * Returns this filter's field.
     * 
     * @return the field of the filter as a {@link String}
     */
    public final String getField() {
        return field;
    }
}
