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
package com.ale.o2g.types.management;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * {@code PbxObject} represents an instance of an object in the OmniPCX Enterprise
 * object model.
 * <p>
 * Each {@code PbxObject} is uniquely identified by its instance definition, which
 * includes its hierarchical path from the root object and a unique instance ID.
 * <p>
 * Examples:
 * <ul>
 *   <li>{@code "Subscriber"}: a Subscriber object.</li>
 *   <li>{@code "Application_Configuration/1/ACD2/1/ACD2_Operator/1/ACD2_Operator_data"}: 
 *       a CCD operator data object.</li>
 * </ul>
 * <p>
 * A {@code PbxObject} contains attributes, can expose sub-objects, and supports
 * operations such as modification and deletion depending on its metadata.
 */
public class PbxObject {

    private Map<String, PbxAttribute> attributes;

    private String objectName;
    private String id;
    private Collection<String> objectNames;
    private boolean delete;
    private boolean set;

    /**
     * Protected constructor for initializing a {@code PbxObject}.
     * <p>
     * Typically used internally when creating instances of objects in the PBX system.
     * </p>
     *
     * @param attributes  the attributes of the object
     * @param objectName  the name of the object type
     * @param id          the unique instance ID
     * @param objectNames the collection of sub-object names
     * @param delete      whether the object can be deleted
     * @param set         whether the object can be modified
     */
    protected PbxObject(Map<String, PbxAttribute> attributes, String objectName, String id,
                        Collection<String> objectNames, boolean delete, boolean set) {
        this.attributes = attributes;
        this.objectName = objectName;
        this.id = id;
        this.objectNames = objectNames;
        this.delete = delete;
        this.set = set;
    }

    /**
     * Returns the names of sub-objects contained within this object.
     *
     * @return an unmodifiable collection of sub-object names; empty if none exist
     */
    public final Collection<String> getObjectNames() {
        return (objectNames == null) ? Collections.emptyList() : Collections.unmodifiableCollection(objectNames);
    }

    /**
     * Indicates whether this object can be deleted.
     *
     * @return {@code true} if the object can be deleted; {@code false} otherwise
     */
    public final boolean canBeDeleted() {
        return delete;
    }

    /**
     * Indicates whether this object can be modified.
     *
     * @return {@code true} if the object can be modified; {@code false} otherwise
     */
    public final boolean canBeSet() {
        return set;
    }

    /**
     * Returns all attributes of this object.
     * <p>
     * The returned collection is unmodifiable to prevent external modification.
     * </p>
     *
     * @return an unmodifiable collection of {@link PbxAttribute} objects
     */
    public Collection<PbxAttribute> getAttributes() {
        return Collections.unmodifiableCollection(attributes.values());
    }

    /**
     * Returns the attribute with the specified name.
     *
     * @param attrName the name of the attribute
     * @return the {@link PbxAttribute} with the given name, or {@code null} if none exists
     */
    public PbxAttribute getAttribute(String attrName) {
        return attributes.get(attrName);
    }

    /**
     * Returns the name of this object type.
     *
     * @return the object name
     */
    public final String getName() {
        return objectName;
    }

    /**
     * Returns the unique instance ID of this object.
     *
     * @return the object instance ID
     */
    public final String getId() {
        return id;
    }
}