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
import java.util.Map;

/**
 * {@code PbxObject} represents an object of the OmniPCX Enterprise object
 * model.
 * <p>
 * A {@code PbxObject} object is referenced by it's object instance definition,
 * a hierarchical path from the root object, and a unique instance id.<br>
 * For exemple:
 * <ul>
 * <li>"Subscriber" : A Subscriber object.</li>
 * <li>"Application_Configuration/1/ACD2/1/ACD2_Operator/1/ACD2_Operator_data" :
 * A CCD operator data object.</li>
 * </ul>
 */
public class PbxObject {

    private Map<String, PbxAttribute> attributes;

    private String objectName;
    private String id;
    private Collection<String> objectNames;
    private boolean delete;
    private boolean set;

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
     * Returns the collection of sub-objects names.
     * @return the sub-objects.
     */
    public final Collection<String> getObjectNames() {
        return objectNames;
    }

    /**
     * Returns whether this object can be deleted.
     * @return {@code true} if the object can be deleted; {@code false} otherwise.
     */
    public final boolean canBeDeleted() {
        return delete;
    }

    /**
     * Returns whether this object can be modified.
     * @return {@code true} if the object can be modified; {@code false} otherwise.
     */
    public final boolean canBeSet() {
        return set;
    }


    /**
     * Returns the attribute with the specified name.
     * 
     * @param attrName the attribue name.
     * @return the attribute with the specified name or null if there is no
     *         attribute with this name.
     */
    public PbxAttribute getAttribute(String attrName) {
        return attributes.get(attrName);
    }

    /**
     * Returns this object name. 
     * @return the objectName the object name.
     */
    public final String getName() {
        return objectName;
    }

    /**
     * Returns this object instance id.
     * @return the id the object instance id.
     */
    public final String getId() {
        return id;
    }

}
