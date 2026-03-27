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

package com.ale.o2g.types.users;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class SupportedLanguagesTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON with both collections
        String json = """
                {
                    "supportedLanguages": ["en", "fr", "de"],
                    "supportedGuiLanguages": ["en", "fr"]
                }
                """;

        SupportedLanguages langs = gson.fromJson(json, SupportedLanguages.class);

        Collection<String> expectedLanguages = Arrays.asList("en", "fr", "de");
        Collection<String> expectedGuiLanguages = Arrays.asList("en", "fr");

        assertNotNull(langs.getSupportedLanguages());
        assertEquals(expectedLanguages.size(), langs.getSupportedLanguages().size());
        assertTrue(langs.getSupportedLanguages().containsAll(expectedLanguages));

        assertNotNull(langs.getSupportedGuiLanguages());
        assertEquals(expectedGuiLanguages.size(), langs.getSupportedGuiLanguages().size());
        assertTrue(langs.getSupportedGuiLanguages().containsAll(expectedGuiLanguages));
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        SupportedLanguages langs = gson.fromJson(json, SupportedLanguages.class);

        assertNull(langs.getSupportedLanguages());
        assertNull(langs.getSupportedGuiLanguages());
    }

}