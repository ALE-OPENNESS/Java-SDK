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

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;

class VoicemailTest extends AbstractJsonTest {

    @Test
    void testDeserializationFull() {
        // JSON with number and type
        String json = """
                {
                    "number": "VM1001",
                    "type": "VM_4635"
                }
                """;

        Voicemail vm = gson.fromJson(json, Voicemail.class);

        assertEquals("VM1001", vm.getNumber());
        assertEquals(Voicemail.Type.VM_4635, vm.getType());
    }

    @Test
    void testDeserializationUnknownType() {
        // JSON with invalid type should fallback to EXTERNAL
        String json = """
                {
                    "number": "VM9999",
                    "type": "VM_9999"
                }
                """;

        Voicemail vm = gson.fromJson(json, Voicemail.class);

        assertEquals("VM9999", vm.getNumber());
        assertEquals(Voicemail.Type.EXTERNAL, vm.getType());
    }

    @Test
    void testDeserializationMin() {
        // JSON with no fields
        String json = "{}";

        Voicemail vm = gson.fromJson(json, Voicemail.class);

        assertNull(vm.getNumber());
        assertNull(vm.getType());
    }

    @Test
    void testNullValues() {
        // JSON with explicit nulls
        String json = """
                {
                    "number": null,
                    "type": null
                }
                """;

        Voicemail vm = gson.fromJson(json, Voicemail.class);

        assertNull(vm.getNumber());
        assertNull(vm.getType());
    }
}