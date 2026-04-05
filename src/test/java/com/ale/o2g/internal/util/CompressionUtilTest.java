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

/*
 * Copyright 2026 ALE International
 *
 * Licensed under the MIT License.
 */
package com.ale.o2g.internal.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CompressionUtil}.
 */
@DisplayName("CompressionUtil")
class CompressionUtilTest {

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static byte[] gzip(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gos = new GZIPOutputStream(baos)) {
            gos.write(data);
        }
        return baos.toByteArray();
    }

    private static byte[] zip(Map<String, byte[]> entries) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (Map.Entry<String, byte[]> entry : entries.entrySet()) {
                zos.putNextEntry(new ZipEntry(entry.getKey()));
                zos.write(entry.getValue());
                zos.closeEntry();
            }
        }
        return baos.toByteArray();
    }

    private static byte[] toBytes(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    private static String fromBytes(byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }

    // ── ungzip ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("ungzip() — decompresses simple string correctly")
    void ungzip_decompressesSimpleString() throws IOException {
        byte[] original = toBytes("Hello, O2G!");
        byte[] compressed = gzip(original);

        byte[] result = CompressionUtil.ungzip(compressed);

        assertArrayEquals(original, result);
        assertEquals("Hello, O2G!", fromBytes(result));
    }

    @Test
    @DisplayName("ungzip() — decompresses empty content")
    void ungzip_decompressesEmptyContent() throws IOException {
        byte[] original = new byte[0];
        byte[] compressed = gzip(original);

        byte[] result = CompressionUtil.ungzip(compressed);

        assertArrayEquals(original, result);
    }

    @Test
    @DisplayName("ungzip() — decompresses large content correctly")
    void ungzip_decompressesLargeContent() throws IOException {
        // Build content larger than the 1024-byte buffer
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            sb.append("Line ").append(i).append("\n");
        }
        byte[] original = toBytes(sb.toString());
        byte[] compressed = gzip(original);

        byte[] result = CompressionUtil.ungzip(compressed);

        assertArrayEquals(original, result);
    }

    @Test
    @DisplayName("ungzip() — decompresses binary content correctly")
    void ungzip_decompressesBinaryContent() throws IOException {
        byte[] original = new byte[256];
        for (int i = 0; i < 256; i++) {
            original[i] = (byte) i;
        }
        byte[] compressed = gzip(original);

        byte[] result = CompressionUtil.ungzip(compressed);

        assertArrayEquals(original, result);
    }

    @Test
    @DisplayName("ungzip() — throws IOException on invalid GZIP data")
    void ungzip_throwsOnInvalidData() {
        byte[] invalid = new byte[]{0x1F, (byte) 0x8B, 0x00, 0x01, 0x02};

        assertThrows(IOException.class, () ->
            CompressionUtil.ungzip(invalid)
        );
    }

    // ── unzip ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("unzip() — decompresses single entry correctly")
    void unzip_decompressesSingleEntry() throws IOException {
        byte[] content = toBytes("Hello from ZIP!");
        byte[] compressed = zip(Map.of("file.txt", content));

        Map<String, byte[]> result = CompressionUtil.unzip(compressed);

        assertEquals(1, result.size());
        assertTrue(result.containsKey("file.txt"));
        assertArrayEquals(content, result.get("file.txt"));
    }

    @Test
    @DisplayName("unzip() — decompresses multiple entries correctly")
    void unzip_decompressesMultipleEntries() throws IOException {
        byte[] content1 = toBytes("File one content");
        byte[] content2 = toBytes("File two content");
        byte[] content3 = toBytes("File three content");

        byte[] compressed = zip(Map.of(
            "file1.txt", content1,
            "file2.txt", content2,
            "file3.txt", content3
        ));

        Map<String, byte[]> result = CompressionUtil.unzip(compressed);

        assertEquals(3, result.size());
        assertArrayEquals(content1, result.get("file1.txt"));
        assertArrayEquals(content2, result.get("file2.txt"));
        assertArrayEquals(content3, result.get("file3.txt"));
    }

    @Test
    @DisplayName("unzip() — returns empty map for empty ZIP")
    void unzip_returnsEmptyMapForEmptyZip() throws IOException {
        byte[] compressed = zip(Map.of());

        Map<String, byte[]> result = CompressionUtil.unzip(compressed);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("unzip() — decompresses entry with empty content")
    void unzip_decompressesEntryWithEmptyContent() throws IOException {
        byte[] compressed = zip(Map.of("empty.txt", new byte[0]));

        Map<String, byte[]> result = CompressionUtil.unzip(compressed);

        assertTrue(result.containsKey("empty.txt"));
        assertArrayEquals(new byte[0], result.get("empty.txt"));
    }

    @Test
    @DisplayName("unzip() — decompresses large entry correctly")
    void unzip_decompressesLargeEntry() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            sb.append("Line ").append(i).append("\n");
        }
        byte[] original = toBytes(sb.toString());
        byte[] compressed = zip(Map.of("large.txt", original));

        Map<String, byte[]> result = CompressionUtil.unzip(compressed);

        assertArrayEquals(original, result.get("large.txt"));
    }

    @Test
    @DisplayName("unzip() — preserves entry file names exactly")
    void unzip_preservesEntryFileNames() throws IOException {
        byte[] compressed = zip(Map.of(
            "subdir/report.csv", toBytes("csv content"),
            "README.md", toBytes("readme content")
        ));

        Map<String, byte[]> result = CompressionUtil.unzip(compressed);

        assertTrue(result.containsKey("subdir/report.csv"));
        assertTrue(result.containsKey("README.md"));
    }

    @Test
    @DisplayName("decompress() — detects ZIP magic header even with invalid content")
    void decompress_zipWithInvalidContentReturnsEmptyMap() throws IOException {
        byte[] invalid = new byte[]{0x50, 0x4B, 0x00, 0x01, 0x02};

        Object result = CompressionUtil.decompress(invalid);

        assertInstanceOf(Map.class, result);
        assertTrue(((Map<?, ?>) result).isEmpty());
    }
    
    // ── decompress ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("decompress() — detects and decompresses GZIP correctly")
    void decompress_detectsGzip() throws IOException {
        byte[] original = toBytes("GZIP content");
        byte[] compressed = gzip(original);

        Object result = CompressionUtil.decompress(compressed);

        assertInstanceOf(byte[].class, result);
        assertArrayEquals(original, (byte[]) result);
    }

    @Test
    @DisplayName("decompress() — detects and decompresses ZIP correctly")
    void decompress_detectsZip() throws IOException {
        byte[] content = toBytes("ZIP content");
        byte[] compressed = zip(Map.of("file.txt", content));

        Object result = CompressionUtil.decompress(compressed);

        assertInstanceOf(Map.class, result);
        @SuppressWarnings("unchecked")
        Map<String, byte[]> map = (Map<String, byte[]>) result;
        assertArrayEquals(content, map.get("file.txt"));
    }

    @Test
    @DisplayName("decompress() — throws IOException when data is null")
    void decompress_throwsWhenNull() {
        assertThrows(IOException.class, () ->
            CompressionUtil.decompress(null)
        );
    }

    @Test
    @DisplayName("decompress() — throws IOException when data is empty")
    void decompress_throwsWhenEmpty() {
        assertThrows(IOException.class, () ->
            CompressionUtil.decompress(new byte[0])
        );
    }

    @Test
    @DisplayName("decompress() — throws IOException when data is one byte")
    void decompress_throwsWhenOneByte() {
        assertThrows(IOException.class, () ->
            CompressionUtil.decompress(new byte[]{0x1F})
        );
    }

    @Test
    @DisplayName("decompress() — throws IOException for unknown format")
    void decompress_throwsForUnknownFormat() {
        byte[] unknown = new byte[]{0x00, 0x01, 0x02, 0x03};

        assertThrows(IOException.class, () ->
            CompressionUtil.decompress(unknown)
        );
    }

    @Test
    @DisplayName("decompress() — throws IOException for plain text data")
    void decompress_throwsForPlainText() {
        byte[] plainText = toBytes("This is not compressed");

        assertThrows(IOException.class, () ->
            CompressionUtil.decompress(plainText)
        );
    }

    @Test
    @DisplayName("decompress() — GZIP result matches direct ungzip() result")
    void decompress_gzipResultMatchesUngzip() throws IOException {
        byte[] original = toBytes("Consistency check");
        byte[] compressed = gzip(original);

        byte[] fromDecompress = (byte[]) CompressionUtil.decompress(compressed);
        byte[] fromUngzip = CompressionUtil.ungzip(compressed);

        assertArrayEquals(fromUngzip, fromDecompress);
    }

    @Test
    @DisplayName("decompress() — ZIP result matches direct unzip() result")
    void decompress_zipResultMatchesUnzip() throws IOException {
        byte[] content = toBytes("Consistency check");
        byte[] compressed = zip(Map.of("check.txt", content));

        @SuppressWarnings("unchecked")
        Map<String, byte[]> fromDecompress = (Map<String, byte[]>) CompressionUtil.decompress(compressed);
        Map<String, byte[]> fromUnzip = CompressionUtil.unzip(compressed);

        assertEquals(fromUnzip.keySet(), fromDecompress.keySet());
        assertArrayEquals(fromUnzip.get("check.txt"), fromDecompress.get("check.txt"));
    }
}
