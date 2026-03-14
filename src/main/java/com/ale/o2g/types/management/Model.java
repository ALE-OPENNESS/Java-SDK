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
 * {@code Model} represents a structured object model in the OmniPCX Enterprise system.
 * <p>
 * A {@code Model} defines the characteristics of an object, including its attributes, 
 * possible child objects, and allowed operations. Each attribute can specify whether it 
 * is mandatory or optional, its type, allowed values or enumerations, and other constraints.
 * </p>
 * <p>
 * This class also provides metadata about the object, such as whether it is hidden in the 
 * object model, and which operations (create, delete, set, get, or custom actions) are permitted.
 * </p>
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
     * @return the name of the object model
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns all attributes defined for this model.
     * <p>
     * The returned collection is unmodifiable to prevent external changes.
     * </p>
     *
     * @return an unmodifiable collection of {@link ModelAttribute} objects
     */
    public Collection<ModelAttribute> getAttributes() {
        return Collections.unmodifiableCollection(attributes.values());
    }

    /**
     * Returns the attribute with the specified name.
     *
     * @param attrName the name of the attribute
     * @return the {@link ModelAttribute} with the given name, or {@code null} if none exists
     */
    public ModelAttribute getAttribute(String attrName) {
        return attributes.get(attrName);
    }

    /**
     * Returns all child models of this object.
     * <p>
     * The returned collection is unmodifiable to prevent external changes.
     * </p>
     *
     * @return an unmodifiable collection of child {@link Model} objects
     */
    public Collection<Model> getChildModels() {
        return Collections.unmodifiableCollection(children.values());
    }

    /**
     * Returns the child model with the specified name.
     *
     * @param name the name of the child model
     * @return the child {@link Model} with the given name, or {@code null} if none exists
     */
    public Model getChildModel(String name) {
        return children.get(name);
    }

    /**
     * Indicates whether this object is hidden in the OmniPCX Enterprise object model.
     *
     * @return {@code true} if the object is hidden; {@code false} otherwise
     */
    public final boolean isHidden() {
        return hidden;
    }

    /**
     * Indicates whether this object can be created.
     *
     * @return {@code true} if creation is allowed; {@code false} otherwise
     */
    public final boolean canCreate() {
        return create;
    }

    /**
     * Indicates whether this object can be deleted.
     *
     * @return {@code true} if deletion is allowed; {@code false} otherwise
     */
    public final boolean canDelete() {
        return delete;
    }

    /**
     * Indicates whether this object can be modified.
     *
     * @return {@code true} if modification (set) is allowed; {@code false} otherwise
     */
    public final boolean canSet() {
        return set;
    }

    /**
     * Indicates whether this object can be retrieved.
     *
     * @return {@code true} if retrieval (get) is allowed; {@code false} otherwise
     */
    public final boolean canGet() {
        return get;
    }

    /**
     * Returns the collection of other custom actions allowed on this object.
     *
     * @return an unmodifiable collection of action names; empty if none
     */
    public final Collection<String> getOtherActions() {
        return (otherActions == null) ? Collections.emptyList() : Collections.unmodifiableCollection(otherActions);
    }

    /**
     * Protected constructor for initializing a {@code Model}.
     * <p>
     * Typically used internally by the framework when building object models.
     * </p>
     */
    protected Model(Map<String, ModelAttribute> attributes, Map<String, Model> children, String name,
                    boolean hidden, boolean create, boolean delete, boolean set, boolean get,
                    Collection<String> otherActions) {
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