/*
* Copyright 2026 ALE International
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

package com.ale.o2g.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * 
 */
public class ExtendAssert {
	// Dedicated formatter used in concrete class
	private static final DateTimeFormatter DATE_FORMATER = new DateTimeFormatterBuilder()
		    .appendPattern("yyyy-MM-dd")
		    .optionalStart()
		    .appendPattern("'T'HH:mm")
		    .optionalEnd()
		    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
		    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		    .toFormatter();	

	public static void assertEqualsUtc(String expectedUtc, Date actualDate) {
		Instant expected = Instant.parse(expectedUtc);
		Instant actual = actualDate.toInstant();
		assertEquals(expected, actual);
	}

	public static void assertEqualsUtc(String expectedUtc, LocalDate actualDate) {
		assertEqualsUtc(expectedUtc, actualDate.atStartOfDay());
	}

	public static void assertEqualsUtc(String expectedUtc, LocalDateTime actualDateTime) {
		    LocalDateTime expected = LocalDateTime.parse(expectedUtc, DATE_FORMATER);
		    assertEquals(expected, actualDateTime);
	}
	
	public static void assertContains(Collection<?> expected, Collection<?> actuals) {
		if (expected == null || actuals == null) {
			throw new AssertionError("Expected and actual collections must not be null");
		}

		for (Object item : expected) {
			if (!actuals.contains(item)) {
				throw new AssertionError("Expected item not found in actual collection: " + item);
			}
		}
	}

    public static <T, R> void assertContains(Collection<R> expected, Collection<T> actuals, Function<T, R> mapper) {
        if (expected == null || actuals == null) {
            throw new AssertionError("Expected and actual collections must not be null");
        }

        // Map actuals to comparable values
        Set<R> actualMapped = new HashSet<>();
        for (T item : actuals) {
            actualMapped.add(mapper.apply(item));
        }

        // Check each expected value exists
        for (R value : expected) {
            if (!actualMapped.contains(value)) {
                throw new AssertionError("Expected item not found in actual collection: " + value);
            }
        }
    }	
	
	public static void assertContainsStrict(Collection<?> expected, Collection<?> actuals) {
		if (expected == null || actuals == null) {
			throw new AssertionError("Expected and actual collections must not be null");
		}

		Set<?> expectedSet = new HashSet<>(expected);
		Set<?> actualSet = new HashSet<>(actuals);

		// Check for missing elements
		Set<Object> missing = new HashSet<>(expectedSet);
		missing.removeAll(actualSet);
		if (!missing.isEmpty()) {
			throw new AssertionError("Missing elements in actual collection: " + missing);
		}

		// Check for unexpected elements
		Set<Object> unexpected = new HashSet<>(actualSet);
		unexpected.removeAll(expectedSet);
		if (!unexpected.isEmpty()) {
			throw new AssertionError("Unexpected elements in actual collection: " + unexpected);
		}
	}

	public static <T, R> void assertContainsStrict(Collection<R> expected, Collection<T> actuals,Function<T, R> mapper) {
		if (expected == null || actuals == null) {
			throw new AssertionError("Expected and actual collections must not be null");
		}

		Set<R> expectedSet = new HashSet<>(expected);
		Set<R> actualSet = new HashSet<>();
		for (T item : actuals) {
			actualSet.add(mapper.apply(item));
		}

		// Check for missing elements
		Set<R> missing = new HashSet<>(expectedSet);
		missing.removeAll(actualSet);
		if (!missing.isEmpty()) {
			throw new AssertionError("Missing elements in actual collection: " + missing);
		}

		// Check for unexpected elements
		Set<R> unexpected = new HashSet<>(actualSet);
		unexpected.removeAll(expectedSet);
		if (!unexpected.isEmpty()) {
			throw new AssertionError("Unexpected elements in actual collection: " + unexpected);
		}
	}
	    
    public static void assertJsonEquals(String expectedJson, String actualJson) {
        // Remove all whitespace and compare
        assertEquals(
            expectedJson.replaceAll("\\s+", ""),
            actualJson.replaceAll("\\s+", ""),
            "JSON does not match!"
        );
    }
}
