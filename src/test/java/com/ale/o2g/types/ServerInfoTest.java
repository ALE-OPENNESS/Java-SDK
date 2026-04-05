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

package com.ale.o2g.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractJsonTest;
import com.ale.o2g.types.users.Device;
import com.ale.o2g.types.users.User;
import com.ale.o2g.types.users.Voicemail.Type;

/**
 * 
 */
public class ServerInfoTest extends AbstractJsonTest {

	@Test
	void testDeserializationFull() {
		// JSON with all fields
		String json = """
				{
				"productName": "O2G Solution",
				"productType": "O2G",
				"productVersion": {
					"major": "2.6",
					"minor": "000.000"
				},
				"haMode": true
		}
		""";

		ServerInfo info = gson.fromJson(json, ServerInfo.class);

		assertEquals("O2G Solution", info.getProductName());
		assertEquals("O2G", info.getProductType());
		assertEquals("2.6.000.000", info.getProductVersion());
		assertTrue(info.isHaMode());
	}
}