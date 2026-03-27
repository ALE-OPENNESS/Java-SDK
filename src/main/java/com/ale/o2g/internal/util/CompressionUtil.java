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
package com.ale.o2g.internal.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 */
public class CompressionUtil {

    /**
     * Detects compression type and decompresses accordingly.
     * 
     * @param data the compressed byte array
     * @return if GZIP: raw byte[]; if ZIP: Map of fileName -> fileBytes
     * @throws IOException if the format is unknown or decompression fails
     */
    public static Object decompress(byte[] data) throws IOException {
        if (data == null || data.length < 2) {
            throw new IOException("Data too short to determine format");
        }

        // GZIP magic header
        if ((data[0] == (byte) 0x1F) && (data[1] == (byte) 0x8B)) {
            return ungzip(data);
        }

        // ZIP magic header ("PK")
        if ((data[0] == (byte) 0x50) && (data[1] == (byte) 0x4B)) {
            return unzip(data);
        }

        throw new IOException("Unknown compression format");
    }

    /**
     * Decompress a GZIP byte array.
     */
    public static byte[] ungzip(byte[] compressed) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
                GZIPInputStream gis = new GZIPInputStream(bais);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        }
    }

    /**
     * Decompress a ZIP byte array into all its entries.
     * 
     * @return a Map where key = file name, value = file content as byte[]
     */
    public static Map<String, byte[]> unzip(byte[] zipBytes) throws IOException {
        Map<String, byte[]> result = new HashMap<>();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
                ZipInputStream zis = new ZipInputStream(bais)) {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            while ((entry = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                result.put(entry.getName(), baos.toByteArray());
                zis.closeEntry();
            }
        }

        return result;
    }
}
