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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@code PbxAttributeMap} represents a sequence of named attributes.
 */
public class PbxAttributeMap implements Iterable<PbxAttribute> {

    protected Map<String, PbxAttribute> attributes = new HashMap<String, PbxAttribute>();

    private PbxAttributeMap() {
    }

    /**
     * Returns the names of the attributes in this sequence.
     * 
     * @return a collection of attribute names.
     */
    public Collection<String> getNames() {
        return attributes.keySet();
    }

    /**
     * Returns the Attribute with the specified name.
     * 
     * <pre>
     * {@code 
     *     PbxAttributeMap skillSequence = ...
     *     PbxAttribute attr = skillSequence.getAttribute("Skill_nb");
     * }
     * </pre>
     * 
     * @param name The attribute name
     * @return A {@linkplain com.ale.o2g.types.management.PbxAttribute PbxAttribute}
     *         object that corresponds to the attribute with the given name or
     *         {@code null} if there is no attribute with this name.
     */
    public PbxAttribute getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Adds the specified attribute to this sequence. It's possible to chain the
     * {@code add} operation to create a complete sequence.
     * 
     * <pre>
     * {@code 
     *     attrList.add(PbxAttribute.create("Name", PbxAttributeMap.create()
     *                                                .add(PbxAttribute.create("Elem1", true))
     *                                                .add(PbxAttribute.create("Elem2", 23))));
     * 
     * }
     * </pre>
     * 
     * @param attr the attribute to add to this sequence
     * @return the sequence object.
     */
    public PbxAttributeMap add(PbxAttribute attr) {
        attributes.put(attr.getName(), attr);
//        sorted.add(attr);
        return this;
    }

    /**
     * Create a new empty PbxAttributeMap. Use this method to create a new sequence
     * of attribute. For exemple:
     * 
     * <pre>
     * {@code
     *     Sequence {
     *         Param1 := Integer,
     *         Param2 := Boolean
     *     }
     * }
     * </pre>
     * Can be create with the following operations
     * <pre>{@code
     * 
     *     PbxAttributeMap sequence = PbxAttributeMap.create()
     *                                   .add(PbxAttribute.Create("Param1", 1))
     *                                   .add(PbxAttribute.Create("Param2", true));
     * }
     * </pre>
     * 
     * @return the new created attribute map object.
     */
    public static PbxAttributeMap create() {
        return new PbxAttributeMap();
    }

    /**
     * Create a new PbxAttributeMap with the specified attributes.
     * Use this method to create a new sequence of attribute. For exemple:
     * 
     * <pre>
     * {@code
     *     Sequence {
     *         Param1 := Integer,
     *         Param2 := Boolean
     *     }
     * }
     * </pre>
     * Can be created with the following operations
     * <pre>{@code
     * 
     *     PbxAttributeMap sequence = 
     *         PbxAttributeMap.create(Arrays.asList(
     *                                   PbxAttribute.create("Param1", 1),
     *                                   PbxAttribute.Create("Param2", true)
     *                               ));
     * }
     * </pre>
     * 
     * 
     * @param attributes the collection of attributes
     * @return the new created attribute map object.
     */
    public static PbxAttributeMap create(List<PbxAttribute> attributes) {

        PbxAttributeMap map = new PbxAttributeMap();
        attributes.forEach(a -> map.attributes.put(a.getName(), a));
        return map;
    }

    protected PbxAttributeMap(PbxAttributeMap attrMap) {
        this.attributes = attrMap.attributes;
    }

    @Override
    public Iterator<PbxAttribute> iterator() {
        return attributes.values().iterator();
    }
}
