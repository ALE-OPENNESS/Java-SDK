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
package com.ale.o2g.internal.types.management;

import java.util.Collection;

import com.ale.o2g.types.management.AttributeFormatException;
import com.ale.o2g.types.management.ModelAttribute;
import com.ale.o2g.types.management.OctetStringLength;

/**
 *
 */
public class O2GAttributeModel {

    private String name;
    private boolean mandatory;
    private String typeValue;
    private boolean multiValue;
    private Collection<String> allowedValues;
    private String lengthValue;
    private String defaultValue;
    private boolean filtering;
    private String usedWhen;
    
    public final String getName() {
        return name;
    }

    // The LengthValue is either a simple name, or a
    // range in the form x..y
    private static OctetStringLength parseLengthValue(String value) {
        if (value == null) {
            return null;
        }

        String[] values = value.split("\\.\\.");
        if (values.length == 2) {
            return new OctetStringLength(
                    Integer.parseInt(values[0]),
                    Integer.parseInt(values[1])
                    ) {};
        }
        else if (values.length == 1) {
            return new OctetStringLength(
                    0,
                    Integer.parseInt(values[0])
                    ) {};
        }
        else {
            throw new AttributeFormatException(String.format("Unable to parse LengthValue: %s", value));
        }
    }
    
    
    // Transformation into a ModelAttribute
    public ModelAttribute toAttributeModel(String objectName) {
        
        return new ModelAttribute(
                name,
                mandatory,
                com.ale.o2g.types.management.ModelAttribute.Type.valueOf(typeValue),
                multiValue,
                allowedValues,
                parseLengthValue(lengthValue),
                defaultValue,
                filtering,
                usedWhen
                ) {};
    }
}
