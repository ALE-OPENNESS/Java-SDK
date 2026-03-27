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
 * {@code ManagementService} allows an administrator to manage an OmniPcx
 * Enterprise, that is to create/modify/delete any object or sub-object in the
 * OmniPcx Enterprise object model. Using this service requires having a
 * <b>MANAGEMENT</b> license.
 * <p>
 * <b>WARNING:</b> Using this service requires to have a good knowledge of the
 * OmniPCX Enterprise object model.
 * <p>
 * The service uses two kinds of resource: the object model resource and the
 * object instance resource.
 * <p><b><u>The object model</u></b>: The object model can be retrieved for the whole Pbx
 * or for a particular object. It provides the detail of object attributes:
 * whether the attribute is mandatory/optional in the object creation, what
 * range of value is authorized, what are the possible enumeration value.
 * <p><b><u>The object instance</u></b>: It is used to create, modify, retrieve or remove
 * any instances of any object, giving the reference of this object. For the
 * creation or the modification of an object, the body must be compliant with
 * the object model.
 * <p>
 * The list of sub-objects which are returned by a get instance of an object
 * corresponds to the relative path of the first instanciable objects in the
 * hierarchy in order to be able by recursion to build the path to access to any
 * object and sub-object.
 * <p>
 * When access to an object which is a sub-object, the full path must be given :
 * {object1Name}/{object1Id}/{object2Name}/{object2Id}/..../{objectxName}/{objectxId}.
 */
public interface ManagementService extends IService {

    /**
     * Gets the list of OmniPCX Enterprise nodes connected on this O2G server.
     * 
     * @return a collection of integer that represents the node ids.
     */
    Collection<Integer> getPbxs();

    /**
     * Gets the OmniPCX Enterprise specified by its node id.
     * 
     * @param nodeId the PCX Enterprise node id
     * @return a {@linkplain com.ale.o2g.types.management.Pbx} object that
     *         represents the OmniPCX Enterprise node; Or {@code null} in case of
     *         error.
     */
    Pbx getPbx(int nodeId);

    /**
     * Get the description of the data model for the specified object on the
     * specified OmniPCX Enterprise node.
     * <p>
     * If {@code objectName} is {@code null}, the global object model of the OmniPCX
     * Enterprise node is returned.
     * 
     * @param nodeId     the OmniPCX Enterprise node id
     * @param objectName the object name
     * @return a {@linkplain com.ale.o2g.types.management.Model Model} object that
     *         represent the object model.
     */
    Model getObjectModel(int nodeId, String objectName);

    /**
     * Gets the node(root) object.
     * 
     * @param nodeId the OmniPCX Enterprise node id
     * @return a {@linkplain com.ale.o2g.types.management.PbxObject PbxObject}
     *         object that represent the node object.
     */
    PbxObject getNodeObject(int nodeId);

    /**
     * Get the object specified by its instance definition and its instance id.
     * 
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @return a {@linkplain com.ale.o2g.types.management.PbxObject PbxObject}
     *         object that represent the requested object.
     */
    PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId);

    /**
     * Get the object specified by its instance definition and its instance id.
     * 
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @param attributes               the optional object attributes to retrieve.
     * @return a {@linkplain com.ale.o2g.types.management.PbxObject PbxObject}
     *         object that represent the requested object.
     */
    PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId, String attributes);
    
    /**
     * Get the object specified by its instance definition and its instance id.
     * 
     * @param nodeId                   the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId                 the object instance id
     * @param attributes               the list of attributes that will be returned
     * @return a {@linkplain com.ale.o2g.types.management.PbxObject PbxObject}
     *         object that represent the requested object.
     */
    PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId, Collection<PbxAttribute> attributes);
    
    /**
     * Query the list of object instances that match the specified filter.
     * <pre>{@code 
     *     AbstractFilter filter = AbstractFilter.create("StationType", AttributeFilter.Equals, "ANALOG");
     *     Collection<String> objectInstances = pbxManagementService.getObjectInstances(5, "Subscriber", filter);
     * }</pre>
     * @param nodeId the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param filter a filter to query the instances
     * @return a collection of object instance matching the filter.
     */
    Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition, Filter filter);
    
    /**
     * Query the list of object instances.
     * @param nodeId the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param filter a filter to query the instances
     * @return a collection of object instance matching the filter.
     */
    Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition, String filter);
    
    /**
     * Query the list of object instances.
     * @param nodeId the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @return a collection of object instance.
     */
    Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition);
    
    /**
     * Change one or several attribute values of the specified object.
     * @param nodeId the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId the object instance id
     * @param attributes the collection of attributes to change
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean setObject(int nodeId, String objectInstanceDefinition, String objectId, Collection<PbxAttribute> attributes);

    /**
     * Delete the specified instance of object.
     * <p>
     * The "<i>FORCED_DELETE</i>" action is not available for all object. 
     * Check the availability in the {@linkplain com.ale.o2g.types.management.Model Model} corresponding to this object.
     * This option can be used, for exemple, to delete a {@code Subscriber} having voice mails in his mail box.
     * @param nodeId the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId the object instance id
     * @param forceDelete Use the "<i>FORCED_DELETE</i>" action to delete the object.
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteObject(int nodeId, String objectInstanceDefinition, String objectId, boolean forceDelete);

    /**
     * Delete the specified instance of object.
     * @param nodeId the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param objectId the object instance id
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean deleteObject(int nodeId, String objectInstanceDefinition, String objectId);
    
    /**
     * Create a new object with the specified collection of attributes
     * @param nodeId the OmniPCX Enterprise node id
     * @param objectInstanceDefinition the object instance definition
     * @param attributes the collection of attributes
     * @return {@code true} in case of success; {@code false} otherwise.
     */
    boolean createObject(int nodeId, String objectInstanceDefinition, Collection<PbxAttribute> attributes);
}
