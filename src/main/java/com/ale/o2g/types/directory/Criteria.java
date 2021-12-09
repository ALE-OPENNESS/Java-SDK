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
 * {@code Criteria} allows to specifiy a filter to apply on a directory search.
 * <p>
 * A simple criteria is a tuple of the form: {@code [Attribute, Operation, Value]}. For example : {@code [LAST_NAME, BEGINS_WITH, "fr"]}.
 * <br>A {@code Criteria} can also be the logical OR or AND combination of the set of other {@code Criteria's}.
 * <p>
 * The acceptable values for the Attribute are:<br>
 * <table>
 * <thead>
 * <tr><th>Value</th><th>Description</th></tr>
 * </thead>
 * <tr><td>LAST_NAME</td><td>The last name.</td></tr>
 * <tr><td>FIRST_NAME</td><td>The first name.</td></tr>
 * <tr><td>LOGIN_NAME</td><td>The login name.</td></tr>
 * <tr><td>PHONE_NUMBER</td><td>The phone number.</td></tr>
 * </table>
 * <p>
 * The acceptable values for the Operands are<br>
 * <table>
 * <thead>
 * <tr><th>Value</th><th>Description</th></tr>
 * </thead>
 * <tr><td>BEGINS_WITH</td><td>The attribute must begin with the given value.</td></tr>
 * <tr><td>ENDS_WITH</td><td>The attribute must end with the given value.</td></tr>
 * <tr><td>CONTAINS</td><td>The attribute must contain with the given value.</td></tr>
 * <tr><td>EQUAL_IGNORE_CASE</td><td>The attribute is equal to the given value (case insensitive comparison).</td></tr>
 * </table>
 * <h1>exemples</h1>
 * <pre>{@code
 *     // Search users whom last name begins with "b"
 *     Criteria criteria = Criteria.create(
 *                               Criteria.AttributeFilter.LASTNAME, 
 *                               Criteria.OperationFilter.BEGINS_WITH, 
 *                               "b");
 *                               
 *     // Search users whom last name begins with "b" and first name contains "ja"
 *     Criteria criteria = Criteria.AND(
 *                               Criteria.create(
 *                                   Criteria.AttributeFilter.LASTNAME,
 *                                   Criteria.OperationFilter.BEGINS_WITH,
 *                                   "b"
 *                               ),
 *                               Criteria.create(
 *                                   Criteria.AttributeFilter.FIRSTNAME,
 *                                   Criteria.OperationFilter.CONTAINS,
 *                                   "ja"
 *                               )
 *                          );
 * }</pre>
 *
 */
public class Criteria {

    /**
     * {@code AttributeFilter} represents the attributes that can be used to filter a directory search.
     */
    public static enum AttributeFilter {

        /**
         * The last name.
         */
        LASTNAME,

        /**
         * The first name.
         */
        FIRSTNAME, 

        /**
         * The phone number.
         */
        PHONE_NUMBER, 

        /**
         * The login name.
         */
        LOGIN_NAME
    }
    
    /**
     * {@code OperationFilter} represents the operation that can be used to filter a directory search. 
     */
    public static enum OperationFilter {

        /**
         * The attribute and the given value are equals (case insensitive).
         */
        EQUAL_IGNORE_CASE, 

        /**
         * The attribute begins with the value.
         */
        BEGINS_WITH, 

        /**
         * The attribute contains the value.
         */
        CONTAINS, 

        /**
         * The attribute ends with the value.
         */
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
		if (attr == AttributeFilter.LASTNAME)
			return "lastName";
		else if (attr == AttributeFilter.FIRSTNAME)
			return "firstName";
		else if (attr == AttributeFilter.PHONE_NUMBER)
			return "id.phoneNumber";
		else
			return "id.loginName";
	}

	private static String toOperation(OperationFilter operation) {
		if (operation == OperationFilter.BEGINS_WITH)
			return "BEGIN_WITH";
		else if (operation == OperationFilter.EQUAL_IGNORE_CASE)
			return "EQUAL_IGNORE_CASE";
		else if (operation == OperationFilter.CONTAINS)
			return "CONTAIN";
		else
			return "END_WITH";
	}

	/**
	 * Create a new search Criteria with the specified attribute filter, operation
	 * filter and value.
	 * 
	 * @param field     the attribute filter
	 * @param operation the operation
	 * @param operand   the value associated to this critera
	 * @return the create {@code Criteria}.
	 */
	public static Criteria Create(AttributeFilter field, OperationFilter operation, String operand) {
		return new Criteria(toField(field), toOperation(operation), operand);
	}

	/**
	 * Create a search {@code Criteria} that is the AND combination of the given
	 * list of Criterias.
	 * 
	 * @param criterias a list of {@code Criteria} objects.
	 * @return the create {@code Criteria}.
	 */
	public static Criteria AND(Criteria... criterias) {
		return new Criteria(null, "AND", Arrays.asList(criterias));
	}

	/**
	 * Create a search {@code Criteria} that is the OR combination of the given list
	 * of Criterias.
	 * 
	 * @param criterias a list of {@code Criteria} objects.
	 * @return the create {@code Criteria}.
	 */
	public static Criteria OR(Criteria... criterias) {
		return new Criteria(null, "OR", Arrays.asList(criterias));
	}

    /**
     * Returns this filter operation.
     * @return the operation
     */
    public final String getOperation() {
        return operation;
    }

    /**
     * Returns this filter field.
     * @return the field
     */
    public final String getField() {
        return field;
    }	
}
