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
package com.ale.o2g.types.management;

import java.util.Arrays;
import java.util.Collection;

import com.ale.o2g.internal.util.AssertUtil;

/**
 * {@code AbstractFilter} class represents a filter that can be used to query OmniPCX Enterprise Object instances.
 * <p>
 * The {@code AbstractFilter} class provides method to build logical combination of simple filters.
 * <pre>
 *   AbstractFilter complex = AbstractFilter.and(
 *                      AbstractFilter.or(
 *                        AbstractFilter.create("Station_Type", AttributeFilter.Equals, "ANALOG"),
 *                        AbstractFilter.create("Station_Type", AttributeFilter.Equals, "ALE-300")
 *                      ),
 *                      AbstractFilter.create("Directory_Name", OperationFilter.StartsWith, "f")
 *                    );
 * 
 * </pre>
 */
public class Filter {

    /**
     * {@code Operation} represents the possible operation to apply to an attribute to build a filter.
     */
    public static enum Operation {
        
        /**
         * The attribute is equal to the value.
         */
        Equals,
        
        /**
         * The attribute starts with the value.
         */
        StartsWith,
        
        /**
         * The attributes ends with the value.
         */
        EndsWith,
        
        /**
         * The attribute is not equals to the value.
         */
        NotEquals,
        
        /**
         * The attribute is greather than or equals to the value.
         */
        GreatherThanOrEquals,
        
        /**
         * The attribute is Less than or equals to the value.
         */
        LessThanOrEquals
    }
    
    
    private String value;
    
    
    private Filter(String value) {
        this.value = value;
    }
    
    
    /**
     * Create a new filter with the specified attribute, operation and value.
     * @param attribute the attribute
     * @param operation the operation
     * @param value the value to test
     * @return the created filter.
     */
    public static Filter create(PbxAttribute attribute, Operation operation, String value) {
        return create(attribute.getName(), operation, value);
    }
    
    /**
     * Create a new filter with the specified attribute name, operation and value.
     * @param attrName the attribute name
     * @param operation the operation
     * @param value the value to test
     * @return the created filter.
     */
    public static Filter create(String attrName, Operation operation, String value) {
        
        if (operation == Operation.Equals) {
            return new Filter(String.format("%s==%s", attrName, value));
        }
        else if (operation == Operation.NotEquals) {
            return new Filter(String.format("%s=!%s", attrName, value));
        }
        else if (operation == Operation.StartsWith) {
            return new Filter(String.format("%s==%s*", attrName, value));
        }
        else if (operation == Operation.EndsWith) {
            return new Filter(String.format("%s==*%s", attrName, value));
        }
        else if (operation == Operation.GreatherThanOrEquals) {
            return new Filter(String.format("%s=ge=%s", attrName, value));
        }
        else if (operation == Operation.LessThanOrEquals) {
            return new Filter(String.format("%s=le=%s", attrName, value));
        }
        else {
            throw new IllegalArgumentException(String.format("Unknown operation: %s", operation));
        }
    }
    
    private static Filter combine(String ope, Filter filter1, Filter filter2, Collection<Filter> otherFilters) {
        
        String result = String.format("%S %s %s",
                AssertUtil.requireNotNull(filter1, "filter1").value,
                ope,
                AssertUtil.requireNotNull(filter2, "filter2").value);

        StringBuilder sb = new StringBuilder(result);
        otherFilters.forEach(f -> sb.append(String.format(" %s %s", ope, f.value)));
        
        return new Filter(sb.toString());
    }
    
    /**
     * Combine a set of filter with a logical And. 
     * @param filter1 the first filter
     * @param filter2 the second filter
     * @param otherFilters other optional filters
     * @return the created filter.
     */
    public static Filter and(Filter filter1, Filter filter2, Filter... otherFilters) {
        return combine("and", filter1, filter2, Arrays.asList(otherFilters));
    }

    
    /**
     * Combine a set of filter with a logical Or. 
     * @param filter1 the first filter
     * @param filter2 the second filter
     * @param otherFilters other optional filters
     * @return the created filter.
     */
    public static Filter or(Filter filter1, Filter filter2, Filter... otherFilters) {
        return combine("or", filter1, filter2, Arrays.asList(otherFilters));
    }


    @Override
    public String toString() {
        return value;
    }
}
