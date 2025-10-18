/*
* Copyright 2025 ALE International
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
package com.ale.o2g.types.ccstats.data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;

import com.ale.o2g.internal.util.FormatUtil;

/**
 * Base abstract class providing reflective access to statistics fields defined
 * in concrete subclasses, such as {@code PilotStatisticsRow},
 * {@code AgentStatisticsRow}, or {@code AgentByPilotStatisticsRow}.
 *
 * <p>
 * Each subclass contains fields corresponding to an associated enumeration
 * (for example, {@link com.ale.o2g.types.ccstats.PilotAttributes}).
 * This class allows retrieving the value of any such field dynamically
 * through its enum constant.
 * </p>
 *
 * <p>
 * The mapping between enum constants and fields is resolved lazily:
 * the first access to a specific attribute performs reflection to locate
 * the corresponding field and caches it for subsequent lookups.
 * This avoids unnecessary upfront reflection cost while ensuring efficient
 * repeated access.
 * </p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * PilotStatisticsRow row = ...;
 * Duration avgConversation = row.get(PilotAttributes.dconvADur).asDuration();
 * Integer openCalls = row.get(PilotAttributes.nbCallsOpen).asInteger();
 * }</pre>
 *
 * @param <E> the enumeration type representing the available statistics attributes
 * @since 2.7.4
 */
public abstract class StatisticsRow<E extends Enum<E>> {
    
    private final Map<E, Field> fieldCache = new EnumMap<>(getEnumClass());
    
    @SuppressWarnings("unchecked")
    private Class<E> getEnumClass() {
        
        Type type = getClass().getGenericSuperclass();
        
        if (type instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (actualType instanceof Class<?>) {
                return (Class<E>) actualType;
            }
        }
        
        throw new IllegalStateException("Cannot determine enum type for " + getClass().getName());
    }
    
    /**
     * Returns a {@link Value} wrapper for the specified attribute.
     * <p>
     * The first time this method is called for a given attribute, reflection is
     * used to locate the corresponding field in the subclass. The field reference
     * is then cached for subsequent fast access.
     * </p>
     *
     * @param attr the attribute whose value should be retrieved
     * @return a {@link Value} object representing the field's current value
     * @throws IllegalArgumentException if no matching field is found
     * @throws RuntimeException if the field cannot be accessed
     */
    public Value get(E attr) {
        Field field = fieldCache.get(attr);

        if (field == null) {
            
            try {
                field = this.getClass().getDeclaredField(attr.name());
                field.setAccessible(true);
                fieldCache.put(attr, field);
            } 
            catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("No field for attribute: " + attr.name(), e);
            }
        }

        try {
            return new Value(field.get(this));
        } 
        catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access field for " + attr.name(), e);
        }
    }
    
    /**
     * Wrapper for a value retrieved from a statistics field.
     * <p>
     * Provides type-safe conversions to common data types such as
     * {@link Integer}, {@link Double}, {@link Duration}, and {@link String}.
     * </p>
     */
    public static class Value {
        private final Object value;

        /**
         * Constructs a new {@code Value} wrapper for the specified raw value.
         *
         * @param value the underlying field value
         */
        public Value(Object value) {
            this.value = value;
        }

        /**
         * Returns the value as an {@link Integer}, performing conversion if possible.
         *
         * @return the integer value, or {@code null} if the underlying value is {@code null}
         * @throws IllegalStateException if the value cannot be converted to {@code Integer}
         */
        public Integer asInteger() {
            if (value == null) return null;
            if (value instanceof Integer) return (Integer) value;
            throw new IllegalStateException("Cannot convert to Integer: " + value);
        }

        /**
         * Returns the value as an {@link Double}, performing conversion if possible.
         *
         * @return the double value, or {@code null} if the underlying value is {@code null}
         * @throws IllegalStateException if the value cannot be converted to {@code Double}
         */
        public Double asDouble() {
            if (value == null) return null;
            if (value instanceof String s) {
                return FormatUtil.asDouble(s);
            }
            throw new IllegalStateException("Cannot convert to Duration: " + value);
        }
        
        /**
         * Returns the value as a {@link Duration}.
         * <p>
         * The following formats are supported:
         * <ul>
         *   <li>ISO-8601 duration strings (e.g., {@code PT5M30S})</li>
         *   <li>Time strings in {@code HH:mm:ss} format</li>
         * </ul>
         *
         * @return the duration, or {@code null} if the underlying value is {@code null}
         * @throws IllegalStateException if the value cannot be parsed as a {@code Duration}
         */
        public Duration asDuration() {
            if (value == null) return null;
            if (value instanceof Duration) return (Duration) value;
            if (value instanceof String s) {
                return FormatUtil.asDuration(s);
            }
            throw new IllegalStateException("Cannot convert to Duration: " + value);
        }

        /**
         * Returns the value as a {@link String}.
         *
         * @return the string representation, or {@code null} if the underlying value is {@code null}
         */
        public String asString() {
            return value == null ? null : value.toString();
        }
    }
    
    protected StatisticsRow() {
        
    }
}


