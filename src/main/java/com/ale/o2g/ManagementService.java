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
package com.ale.o2g;

import java.util.Collection;

import com.ale.o2g.internal.services.IService;
import com.ale.o2g.types.management.Filter;
import com.ale.o2g.types.management.Model;
import com.ale.o2g.types.management.Pbx;
import com.ale.o2g.types.management.PbxAttribute;
import com.ale.o2g.types.management.PbxObject;

/**
 * {@code ManagementService} allows an administrator to manage an OmniPCX
 * Enterprise, that is to create, modify or delete any object or sub-object in the
 * OmniPCX Enterprise object model. Using this service requires having a
 * <b>MANAGEMENT</b> license.
 * <p>
 * <b>WARNING:</b> Using this service requires a good knowledge of the
 * OmniPCX Enterprise object model.
 * <p>
 * The service uses two kinds of resource: the object model resource and the
 * object instance resource.
 * <p><b><u>The object model</u></b>: The object model can be retrieved for the whole PBX
 * or for a particular object. It provides the detail of object attributes:
 * whether the attribute is mandatory or optional in the object creation, what
 * range of values is authorized, and what the possible enumeration values are.
 * <p><b><u>The object instance</u></b>: It is used to create, modify, retrieve or remove
 * any instances of any object, given the reference of this object. For the
 * creation or modification of an object, the body must be compliant with
 * the object model.
 * <p>
 * The list of sub-objects returned by a get instance of an object corresponds
 * to the relative path of the first instantiable objects in the hierarchy,
 * in order to be able by recursion to build the path to access any object
 * and sub-object.
 * <p>
 * When accessing an object which is a sub-object, the full path must be given:
 * {@code {object1Name}/{object1Id}/{object2Name}/{object2Id}/.../{objectxName}/{objectxId}}.
 */
public interface ManagementService extends IService {

    /**
     * Gets the list of OmniPCX Enterprise nodes connected on this O2G server.
     *
     * @return A collection of integers representing the node ids, or {@code null} in case of error.
     */
    Collection<Integer> getPbxs();

    /**
     * Gets the OmniPCX Enterprise specified by its node id.
     *
     * @param nodeId the OmniPCX Enterprise node id
     * @return A {@linkplain com.ale.o2g.types.management.Pbx Pbx} object representing the OmniPCX Enterprise node, or {@code null} in case of error.
     */
    Pbx getPbx(int nodeId);

    /**
     * Gets the description of the data model for the specified object on the
     * specified OmniPCX Enterprise node.
     * <p>
     * If {@code objectName} is {@code null}, the global object model of the OmniPCX
     * Enterprise node is returned.
     *
     * @param nodeId     the OmniPCX Enterprise node id
     * @param objectName the object name, or {@code null} to retrieve the global model
     * @return A {@linkplain com.ale.o2g.types.management.Model Model} object describing the requested object model, or {@code null} in case of error.
     */
    Model getObjectModel(int nodeId, String objectName);

    /**
     * Gets the node (root) object.
     *
     * @param nodeId the OmniPCX Enterprise node id
     * @return A {@linkplain com.ale.o2g.types.management.PbxObject PbxObject} representing the root node object, or {@code null} in case of error.
     */
    PbxObject getNodeObject(int nodeId);

    /**
     * Gets the object specified by its instance definition and its instance id.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @return A {@linkplain com.ale.o2g.types.management.PbxObject PbxObject} representing the requested object, or {@code null} in case of error.
     */
    PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId);

    /**
     * Gets the object specified by its instance definition and its instance id,
     * returning only the specified attributes.
     * <p>
     * The {@code attributes} value is a comma-separated list of attribute names,
     * for example: {@code "Station_Type,Directory_Number"}.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @param attributes               a comma-separated list of attribute names to retrieve
     * @return A {@linkplain com.ale.o2g.types.management.PbxObject PbxObject} representing the requested object, or {@code null} in case of error.
     */
    PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId, String attributes);

    /**
     * Gets the object specified by its instance definition and its instance id,
     * returning only the specified attributes.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @param attributes               the list of attributes to retrieve
     * @return A {@linkplain com.ale.o2g.types.management.PbxObject PbxObject} representing the requested object, or {@code null} in case of error.
     */
    PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId, Collection<PbxAttribute> attributes);
    
    /**
     * Queries the list of object instances that match the specified filter.
     * <pre>{@code
     *     AbstractFilter filter = AbstractFilter.create("StationType", AttributeFilter.Equals, "ANALOG");
     *     Collection<String> objectInstances = managementService.getObjectInstances(5, "Subscriber", filter);
     * }</pre>
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param filter                   a filter to query the instances
     * @return A collection of object instance ids matching the filter, or {@code null} in case of error.
     */
    Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition, Filter filter);

    /**
     * Queries the list of object instances that match the specified filter expression.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param filter                   a filter expression string to query the instances, or {@code null} to return all instances
     * @return A collection of object instance ids matching the filter, or {@code null} in case of error.
     */
    Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition, String filter);

    /**
     * Queries all instances of the specified object.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @return A collection of all object instance ids, or {@code null} in case of error.
     */
    Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition);
    
    /**
     * Changes one or several attribute values of the specified object.
     * <p>
     * If an update on the same object has been performed by another administrator since the last operation,
     * a conflict error occurs; a GET operation must be performed first to allow the update and prevent overwriting changes made by others.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @param attributes               the collection of attributes to change
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setObject(int nodeId, String objectInstanceDefinition, String objectId, Collection<PbxAttribute> attributes);

    /**
     * Deletes the specified instance of an object.
     * <p>
     * The <i>FORCED_DELETE</i> action is not available for all objects.
     * Check its availability in the {@linkplain com.ale.o2g.types.management.Model Model} corresponding to the object.
     * It can be used, for example, to delete a {@code Subscriber} that has voice mails in their mailbox.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @param forceDelete              if {@code true}, uses the FORCED_DELETE action to delete the object
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteObject(int nodeId, String objectInstanceDefinition, String objectId, boolean forceDelete);

    /**
     * Deletes the specified instance of an object.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteObject(int nodeId, String objectInstanceDefinition, String objectId);
    
    /**
     * Creates a new object with the specified collection of attributes.
     *
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param attributes               the collection of attributes to set at object creation
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean createObject(int nodeId, String objectInstanceDefinition, Collection<PbxAttribute> attributes);
}
