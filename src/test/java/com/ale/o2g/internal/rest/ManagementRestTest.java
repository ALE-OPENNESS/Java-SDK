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

package com.ale.o2g.internal.rest;

import static com.ale.o2g.test.ExtendAssert.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ale.o2g.test.AbstractRestServiceTest;
import com.ale.o2g.types.management.Filter;
import com.ale.o2g.types.management.Filter.Operation;
import com.ale.o2g.types.management.Model;
import com.ale.o2g.types.management.Pbx;
import com.ale.o2g.types.management.PbxAttribute;
import com.ale.o2g.types.management.PbxObject;

class ManagementRestTest extends AbstractRestServiceTest<ManagementRest> {

    protected ManagementRestTest() {
        super(ManagementRest.class, "https://o2g/rest/api/management");
    }

    @Test
    void testGetPbxs() throws Exception {

        defineResponse(200, "{ \"nodeIds\": [\"1\",\"2\",\"3\"] }");

        Collection<Integer> result = service.getPbxs();

        assertCalledWith(GET, "/");
        assertNotNull(result);
        assertEquals(List.of(1,2,3), result);
    }

    @Test
    void testGetPbx() throws Exception {

        defineResponse(200, "{ \"nodeId\": 1 }");

        Pbx result = service.getPbx(1);

        assertCalledWith(GET, "/1");
        assertNotNull(result);
        assertEquals(1, result.getNodeId());
    }

    @Test
    void testGetObjectModel() throws Exception {

        defineResponse(200, "{ \"name\":\"Subscriber\" }");

        Model result = service.getObjectModel(1, "Subscriber");

        assertCalledWith(GET, "/1/model/Subscriber");
        assertNotNull(result);
    }

    @Test
    void testGetNodeObject() throws Exception {

        defineResponse(200, "{ "
        		+ "\"objectId\":\"35000\", "
        		+ "\"objectName\":\"Subscriber\" "
        	+ "}"
        );

        PbxObject result = service.getNodeObject(1);

        assertCalledWith(GET, "/1/instances");
        assertNotNull(result);
        assertEquals("Subscriber", result.getName());
        assertEquals("35000", result.getId());
    }

    @Test
    void testGetObjectWithAttributesString() throws Exception {

        defineResponse(200, "{ "
        		+ "\"objectId\":\"35000\", "
        		+ "\"objectName\":\"Subscriber\" "
        	+ "}"
        );

        PbxObject result = service.getObject(1, "Subscriber", "35000", "name, email");

        assertCalledWith(GET,"/1/instances/Subscriber/35000?attributes=name%2C+email");

        assertNotNull(result);
    }

    @Test
    void testGetObjectInstancesWithFilter() throws Exception {

        defineResponse(200, "{ \"objectIds\": [\"31000\", \"31001\"] }");

        Filter filter = Filter.create("Directory_Name", Operation.StartsWith, "f");
        
        Collection<String> result = service.getObjectInstances(1, "Subscriber", filter);

        assertCalledWith(GET, "/1/instances/Subscriber?filter=Directory_Name%3D%3Df*");

        assertNotNull(result);
        assertContains(List.of("31000","31001"), result);
    }

    @Test
    void testSetObject() throws Exception {

        defineResponse(200, "");

        Collection<PbxAttribute> attributes =
                List.of(PbxAttribute.create("Directory_Name", "John Doe"));

        boolean result = service.setObject(1, "Subscriber", "35000", attributes);

        assertCalledWith(PUT,
                "/1/instances/Subscriber/35000",
                "{"
                    + "\"attributes\":["
                        + "{"
                            + "\"name\":\"Directory_Name\","
                            + "\"value\":[\"John Doe\"]"
                        + "}"
                    + "]"
                + "}"
        );

        assertTrue(result);
    }

    @Test
    void testDeleteObjectWithForce() throws Exception {

        defineResponse(200, "");

        boolean result = service.deleteObject(1, "Subscriber", "35000", true);

        assertCalledWith(DELETE, "/1/instances/Subscriber/35000?force");
        assertTrue(result);
    }

    @Test
    void testDeleteObject() throws Exception {

        defineResponse(200, "");

        boolean result = service.deleteObject(1, "Subscriber", "35000");

        assertCalledWith(DELETE, "/1/instances/Subscriber/35000");

        assertTrue(result);
    }

    @Test
    void testCreateObject() throws Exception {

        defineResponse(200, "");

        Collection<PbxAttribute> attributes = List.of(
        		PbxAttribute.create("Directory_Name", "John Doe"),
        		PbxAttribute.create("Multi_Line", true));

        boolean result =
                service.createObject(1, "User", attributes);

        assertCalledWith(POST,
                "/1/instances/User",
                "{"
                    + "\"attributes\":["
                        + "{"
                            + "\"name\":\"Directory_Name\","
                            + "\"value\":[\"John Doe\"]"
                        + "},{"
                        + "\"name\":\"Multi_Line\","
                        + "\"value\":[\"true\"]"
                    + "}"
                    + "]"
                + "}"
        );

        assertTrue(result);
    }
}