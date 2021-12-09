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

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.ale.o2g.types.management.AttributeFormatException;

/**
 *
 */
public class ValueConverter {

    private static String assertUnique(List<String> values) {
        if ((values == null) || (values.size() != 1)) {
            throw new AttributeFormatException("Value is not an int : null or not unique");
        }

        return values.get(0);
    }

    public static <T> List<T> toListConverter(List<String> values, Class<T> objClass) {
        if (values == null) {
            throw new AttributeFormatException("Value is not a list : null");
        }

        if (objClass.equals(String.class)) {
            return values.stream().map(v -> objClass.cast(v)).toList();
        }
        else if (objClass.equals(int.class)) {
            return values.stream().map(v -> objClass.cast(Integer.parseInt(v))).toList();
        }
        else if (objClass.equals(boolean.class)) {

            return values.stream().map(v -> objClass.cast(toBooleanConverter(v))).toList();
        }
        else {
            throw new AttributeFormatException(String
                    .format("Unable to transform in a list of %s : No acceptable conversion", objClass.toString()));
        }
    }

    private static boolean toBooleanConverter(String value) {
        if (value == "true") {
            return true;
        }
        else if (value == "false") {
            return false;
        }
        else {
            throw new AttributeFormatException(String.format("Value [%s] is not a boolean", value));
        }
    }

    public static boolean toBooleanConverter(List<String> values) {
        return toBooleanConverter(assertUnique(values));
    }

    public static List<String> fromBooleanConverter(boolean value) {
        return fromStringConverter(value ? "true" : "false");
    }

    public static int toIntegerConverter(List<String> values) {

        try {
            return Integer.parseInt(assertUnique(values));
        }
        catch (NumberFormatException e) {
            throw new AttributeFormatException(e);
        }
    }

    public static List<String> fromIntegerConverter(int value) {
        return fromStringConverter(String.valueOf(value));
    }

    public static String toStringConverter(List<String> values) {
        return assertUnique(values);
    }

    public static List<String> fromStringConverter(String value) {
        return Arrays.asList(value);
    }

    public static <T> List<String> fromNative(T value, Function<T, List<String>> converter) {
        return converter.apply(value);
    }

    public static <T> T toNative(List<String> values, Function<List<String>, T> converter) {
        return converter.apply(values);
    }
}
