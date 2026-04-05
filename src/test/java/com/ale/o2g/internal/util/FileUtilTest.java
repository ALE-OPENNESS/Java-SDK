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

package com.ale.o2g.internal.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link FileUtil}.
 */
@DisplayName("FileUtil")
class FileUtilTest {

    private static final Path DIRECTORY = Paths.get("/output");

    // ── withTimestamp ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("withTimestamp() — result is inside the specified directory")
    void withTimestamp_resultIsInsideDirectory() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "report.csv");

        assertEquals(DIRECTORY, result.getParent());
    }

    @Test
    @DisplayName("withTimestamp() — filename contains the original name")
    void withTimestamp_filenameContainsOriginalName() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "report.csv");
        String filename = result.getFileName().toString();

        assertTrue(filename.startsWith("report-"),
            "Filename should start with 'report-' but was: " + filename);
    }

    @Test
    @DisplayName("withTimestamp() — filename preserves the original extension")
    void withTimestamp_preservesExtension() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "report.csv");
        String filename = result.getFileName().toString();

        assertTrue(filename.endsWith(".csv"),
            "Filename should end with '.csv' but was: " + filename);
    }

    @Test
    @DisplayName("withTimestamp() — timestamp matches yyyyMMdd-HHmmss pattern")
    void withTimestamp_timestampMatchesPattern() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "report.csv");
        String filename = result.getFileName().toString();

        // Expected format: report-20260403-142530.csv
        // Extract the timestamp portion between first '-' and last '.'
        String withoutExtension = filename.substring(0, filename.lastIndexOf('.'));
        String timestamp = withoutExtension.substring(withoutExtension.indexOf('-') + 1);

        assertTrue(timestamp.matches("\\d{8}-\\d{6}"),
            "Timestamp should match yyyyMMdd-HHmmss but was: " + timestamp);
    }

    @Test
    @DisplayName("withTimestamp() — full filename matches expected pattern")
    void withTimestamp_fullFilenameMatchesPattern() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "report.csv");
        String filename = result.getFileName().toString();

        assertTrue(filename.matches("report-\\d{8}-\\d{6}\\.csv"),
            "Filename should match 'report-yyyyMMdd-HHmmss.csv' but was: " + filename);
    }

    @Test
    @DisplayName("withTimestamp() — file without extension")
    void withTimestamp_fileWithoutExtension() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "report");
        String filename = result.getFileName().toString();

        assertTrue(filename.startsWith("report-"),
            "Filename should start with 'report-' but was: " + filename);
        assertFalse(filename.contains("."),
            "Filename should not contain a dot but was: " + filename);
        assertTrue(filename.matches("report-\\d{8}-\\d{6}"),
            "Filename should match 'report-yyyyMMdd-HHmmss' but was: " + filename);
    }

    @Test
    @DisplayName("withTimestamp() — file with multiple dots preserves last extension only")
    void withTimestamp_fileWithMultipleDots() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "my.report.csv");
        String filename = result.getFileName().toString();

        assertTrue(filename.startsWith("my.report-"),
            "Filename should start with 'my.report-' but was: " + filename);
        assertTrue(filename.endsWith(".csv"),
            "Filename should end with '.csv' but was: " + filename);
        assertTrue(filename.matches("my\\.report-\\d{8}-\\d{6}\\.csv"),
            "Filename should match pattern but was: " + filename);
    }

    @Test
    @DisplayName("withTimestamp() — two calls produce valid results")
    void withTimestamp_twoCallsProduceValidResults() {
        Path result1 = FileUtil.withTimestamp(DIRECTORY, "report.csv");
        Path result2 = FileUtil.withTimestamp(DIRECTORY, "report.csv");

        String f1 = result1.getFileName().toString();
        String f2 = result2.getFileName().toString();

        assertTrue(f1.matches("report-\\d{8}-\\d{6}\\.csv"),
            "First result should match pattern but was: " + f1);
        assertTrue(f2.matches("report-\\d{8}-\\d{6}\\.csv"),
            "Second result should match pattern but was: " + f2);
    }

    @Test
    @DisplayName("withTimestamp() — nested directory path")
    void withTimestamp_nestedDirectory() {
        Path directory = Paths.get("/output/2026/reports");
        Path result = FileUtil.withTimestamp(directory, "report.csv");

        assertEquals(directory, result.getParent());
        assertTrue(result.getFileName().toString().matches("report-\\d{8}-\\d{6}\\.csv"));
    }

    @Test
    @DisplayName("withTimestamp() — different file names produce different prefixes")
    void withTimestamp_differentFileNamesProduceDifferentPrefixes() {
        Path result1 = FileUtil.withTimestamp(DIRECTORY, "report.csv");
        Path result2 = FileUtil.withTimestamp(DIRECTORY, "invoice.csv");

        String f1 = result1.getFileName().toString();
        String f2 = result2.getFileName().toString();

        assertTrue(f1.startsWith("report-"),
            "First filename should start with 'report-'");
        assertTrue(f2.startsWith("invoice-"),
            "Second filename should start with 'invoice-'");
    }

    @Test
    @DisplayName("withTimestamp() — different extensions are preserved independently")
    void withTimestamp_differentExtensionsPreserved() {
        Path csv  = FileUtil.withTimestamp(DIRECTORY, "data.csv");
        Path json = FileUtil.withTimestamp(DIRECTORY, "data.json");
        Path xml  = FileUtil.withTimestamp(DIRECTORY, "data.xml");

        assertTrue(csv.getFileName().toString().endsWith(".csv"));
        assertTrue(json.getFileName().toString().endsWith(".json"));
        assertTrue(xml.getFileName().toString().endsWith(".xml"));
    }

    @Test
    @DisplayName("withTimestamp() — result is not null")
    void withTimestamp_resultIsNotNull() {
        Path result = FileUtil.withTimestamp(DIRECTORY, "report.csv");

        assertNotNull(result);
    }
}