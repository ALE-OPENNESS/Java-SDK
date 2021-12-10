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

import java.util.Collection;

/**
 * {@code ModelAttribute} represents an attribute in an object model.
 */
public class ModelAttribute {

    /**
     * {@code Type} represents the possible type of an attribute.
     */
    public static enum Type {
        
        /**
         * An enumerated type.
         */
        Enumerated,
        
        /**
         * An octet string type.
         */
        OctetString,
        
        /**
         * A sequence of other attributes.
         */
        Sequence,
        
        /**
         * An integer type.
         */
        Integer,
        
        /**
         * A boolean type.
         */
        Boolean,
        
        /**
         * A set of other attributes.
         */
        Set,
        
        /**
         * A byte string type.
         */
        ByteString
    }
    
    private String name;
    private boolean mandatory;
    private Type type;
    private boolean multiValue;
    private Collection<String> allowedValues;
    private OctetStringLength octetStringLength;
    private String defaultValue;
    private boolean filtering;
    private String usedWhen;
    
    /**
     * Returns this attribute name.
     * @return the name
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Returns whether this attribute is andatory.
     * @return {@code true} if the attribute is mandatory; {@code false} otherwise.
     */
    public final boolean isMandatory() {
        return mandatory;
    }
    
    /**
     * Returns this attribute's type
     * @return the type
     */
    public final Type getType() {
        return type;
    }
    
    /**
     * Returns whether this attribute has multiple values. 
     * @return {@code true} if the attribute has multiple values; {@code false} otherwise.
     */
    public final boolean isMultiValue() {
        return multiValue;
    }
    
    /**
     * Return a collection of allowed values
     * @return the allowed values.
     */
    public final Collection<String> getAllowedValues() {
        return allowedValues;
    }
    
    /**
     * Returns the maximum length of the attribute value when the attribute is an byte string.
     * @return the length.
     */
    public final OctetStringLength getOctetStringLength() {
        return octetStringLength;
    }
    
    /**
     * Returns this attribute default value.
     * @return the defaultValue.
     */
    public final String getDefaultValue() {
        return defaultValue;
    }
    
    /**
     * Returns whether the attribute can be filtered.
     * @return {@code true} if the attribute can be filtered; {@code false} otherwise.
     */
    public final boolean isFiltering() {
        return filtering;
    }
    
    /**
     * Return in which context this attribute is used.
     * @return the context
     */
    public final String getUsedWhen() {
        return usedWhen;
    }

    
    protected ModelAttribute(String name, boolean mandatory, Type type, boolean multiValue,
            Collection<String> allowedValues, OctetStringLength octetStringLength, String defaultValue,
            boolean filtering, String usedWhen) {
        this.name = name;
        this.mandatory = mandatory;
        this.type = type;
        this.multiValue = multiValue;
        this.allowedValues = allowedValues;
        this.octetStringLength = octetStringLength;
        this.defaultValue = defaultValue;
        this.filtering = filtering;
        this.usedWhen = usedWhen;
    }
}
