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
 * {@code Model} represents an object model. It provides the detail of object
 * attributes: whether the attribute is mandatory/optional in the object
 * creation, what range of value is authorized, what are the possible
 * enumeration value.
 */
public class Model {

    private Map<String, ModelAttribute> attributes;
    private Map<String, Model> children;

    private String name;
    private boolean hidden;
    private boolean create;
    private boolean delete;
    private boolean set;
    private boolean get;
    private Collection<String> otherActions;

    /**
     * Returns the name of this object model.
     * 
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the specified attribute.
     * 
     * @param attrName the attribute name.
     * @return The attribute or {@code null} if there is no attribute with this
     *         name.
     */
    public ModelAttribute getAttribute(String attrName) {
        return attributes.get(attrName);
    }

    /**
     * Returns the specified child model.
     * 
     * @param name the name of the child model.
     * @return the child model or {@code null} if there is no model with this name.
     */
    public Model getChildModel(String name) {
        return children.get(name);
    }

    /**
     * Returns whether this object is hidden in the OmniPCX Enterprise object model.
     * 
     * @return {@code true} if the object is hidden; {@code false} otherwise.
     */
    public final boolean isHidden() {
        return hidden;
    }

    /**
     * Returns whether this object can be created.
     * 
     * @return {@code true} if the object can be created; {@code false} otherwise.
     */
    public final boolean canCreate() {
        return create;
    }

    /**
     * Returns whether this object can be deleted.
     * 
     * @return {@code true} if the object can be deleted; {@code false} otherwise.
     */
    public final boolean canDelete() {
        return delete;
    }

    /**
     * Returns whether this object can be set.
     * 
     * @return {@code true} if the object can be set; {@code false} otherwise.
     */
    public final boolean canSet() {
        return set;
    }

    /**
     * Returns whether this object can be gotten.
     * 
     * @return {@code true} if the object can be gotten; {@code false} otherwise.
     */
    public final boolean canGet() {
        return get;
    }

    /**
     * Returns the collection of other possible actions.
     * 
     * @return the other actions.
     */
    public final Collection<String> getOtherActions() {
        return otherActions;
    }

    protected Model(Map<String, ModelAttribute> attributes, Map<String, Model> children, String name, boolean hidden,
            boolean create, boolean delete, boolean set, boolean get, Collection<String> otherActions) {
        this.attributes = attributes;
        this.children = children;
        this.name = name;
        this.hidden = hidden;
        this.create = create;
        this.delete = delete;
        this.set = set;
        this.get = get;
        this.otherActions = otherActions;
    }
}
