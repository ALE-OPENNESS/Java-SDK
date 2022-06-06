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

import java.util.List;

import com.ale.o2g.internal.types.management.ValueConverter;

/**
 * {@code PbxAttribute} class represents an attribute in a
 * {@linkplain com.ale.o2g.types.management.PbxObject PbxObject}. A PbxAttribute
 * can be of the following type:
 * <p><b><u>Integer</u></b>: An Integer value is equivalent to an {@code int} value.
 * <p><b><u>Boolean</u></b>: An Boolean value is equivalent to an {@code boolean} value.
 * <p><b><u>Enumerated</u></b>: An enumerated value can have a limited set of possible
 * values. {@code PbxAttribute} treats enumerated value as {@code String} value.
 * <p><b><u>OctetString, ByteString</u></b>: An OctetString or a ByteString are
 * equivalent to a {@code String} value.
 * <p><b><u>Sequence</u></b>: A Sequence is a structured data whose attribute member have
 * a specific name and type: For exemple
 * 
 * <pre>
 *     Skill := Sequence {
 *         Skill_Nb := Integer,
 *         Skill_Level := Integer,
 *         Skill_Activate := Boolean
 *     }
 * </pre>
 * 
 * <p><b><u>Set</u></b>: A Set value is a list of attributes of the same type. It can be
 * a list of simple value like:
 * 
 * <pre>
 *     SimpleSet := Set {
 *         Item := OctetString
 *     }
 * </pre>
 * 
 * or a list of sequences:
 * 
 * <pre>
 *     SkillSet := Set {
 *         Item := Sequence {
 *             Skill_Nb := Integer,
 *             Skill_Level := Integer,
 *             Skill_Activate := Boolean
 *         }
 *     }
 * </pre>
 * <p>
 * {@code PbxAttribute} provide conversion method to usual java types. The
 * conversion is controlled "at the best", but it can not check all the possible
 * error case. An Enumerated value and an OctetString value can both be
 * converted to a {@code String} value.
 * <p>
 * When a conversion error is detected, a
 * {@linkplain com.ale.o2g.types.management.AttributeFormatException AttributeFormatException}
 * exception is raised.
 */
public class PbxAttribute {

    protected List<String> values;
    protected List<PbxAttributeMap> attributeMaps;
    protected PbxAttributeMap sequenceMap;

    protected String name;

    
    private PbxAttribute() {
        
    }
    
    
    /**
     * Returns this attribute's name.
     * 
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Creates a new {@code PbxAttribute} of type sequence with the specified
     * strings.
     * 
     * @param attrName the attribute name
     * @param values   the list of string values to add in this attribute
     * @return the created attribute
     */
    public static PbxAttribute createSetOfStrings(String attrName, List<String> values) {

        PbxAttribute attribute = new PbxAttribute();
        attribute.name = attrName;
        attribute.values = values;
        
        return attribute;
    }

    /**
     * Creates a new {@code PbxAttribute} of type {@code String}.
     * <pre>{@code
     *     PbxAttribute simple = PbxAttribute.create("AttrName", "a string value");
     * }</pre>
     * 
     * @param attrName the attribute name
     * @param value the String value
     * @return the created attribute
     */
    public static PbxAttribute create(String attrName, String value) {

        PbxAttribute attribute = new PbxAttribute();
        attribute.name = attrName;
        attribute.values = ValueConverter.fromNative(value, ValueConverter::fromStringConverter);
        
        return attribute;
    }
    
    /**
     * Creates a new {@code PbxAttribute} of type {@code boolean}.
     * <pre>{@code
     *     PbxAttribute simple = PbxAttribute.create("AttrName", true);
     * }</pre>
     * 
     * @param attrName the attribute name
     * @param value the boolean value
     * @return the created attribute
     */
    public static PbxAttribute create(String attrName, boolean value) {

        PbxAttribute attribute = new PbxAttribute();
        attribute.name = attrName;
        attribute.values = ValueConverter.fromNative(value, ValueConverter::fromBooleanConverter);
        
        return attribute;
    }
    
    /**
     * Creates a new {@code PbxAttribute} of type {@code int}.
     * <pre>{@code
     *     PbxAttribute simple = PbxAttribute.create("AttrName", 12);
     * }</pre>
     * 
     * @param attrName the attribute name
     * @param value the int value
     * @return the created attribute
     */    
    public static PbxAttribute create(String attrName, int value) {
        

        PbxAttribute attribute = new PbxAttribute();
        attribute.name = attrName;
        attribute.values = ValueConverter.fromNative(value, ValueConverter::fromIntegerConverter);
        
        return attribute;
    }
    
    /**
     * Creates a new {@code PbxAttribute} with the specified sequence.
     * <pre>{@code
     *     PbxAttribute sequence = 
     *         PbxAttribute.create(
     *             "sequence", 
     *             PbxAttributeMap.create(Arrays.asList(
     *                 PbxAttribute.create("Param1", 1),
     *                 PbxAttribute.create("Param2", true)
     *             )
     *         );
     * }</pre>
     * @param attrName the attribute name
     * @param sequence the sequence value
     * @return the created attribute
     */
    public static PbxAttribute create(String attrName, PbxAttributeMap sequence) {
        
        PbxAttribute attribute = new PbxAttribute();
        attribute.name = attrName;
        attribute.sequenceMap = sequence;
        
        return attribute;
    }
    
    /**
     * Creates a new {@code PbxAttribute} with the specified list of sequences.
     * <pre>{@code
     *     PbxAttribute skillSet = PbxAttribute.createSetOfSequences("skillSet", Arrays.asList( 
     *         PbxAttributeMap.create(Arrays.asList(
     *             PbxAttribute.create("Skill_nb", 21),
     *             PbxAttribute.create("Skill_Level", 2),
     *             PbxAttribute.create("Skill_Activate", true)
     *         )),
     *         PbxAttributeMap.create(Arrays.asList(
     *             PbxAttribute.create("Skill_nb", 22),
     *             PbxAttribute.create("Skill_Level", 1),
     *             PbxAttribute.create("Skill_Activate", true)
     *         ))
     *     ));
     *         
     * }</pre>
     * 
     * @param attrName the attribute name
     * @param sequences the list of sequences
     * @return the created attribute
     */
    public static PbxAttribute createSetOfSequences(String attrName, List<PbxAttributeMap> sequences) {
        
        PbxAttribute attribute = new PbxAttribute();
        attribute.name = attrName;
        attribute.attributeMaps = sequences;
        
        return attribute;
    }
    
    /**
     * Returns the attributes set at the specified index.
     * <p>
     * This method is used when the attribute is a set of sequences. 
     * For exemple the SkillSet attribute in object ACD2_Operator_data:
     * <pre>{@code
     *     SkillSet := Set
     *         [
     *             Sequence
     *                 {
     *                     Skill_Level := Integer
     *                     Skill_Nb := Integer
     *                     Skill_Activate := Boolean
     *                 }
     *         ]
     * }</pre>
     * 
     * <pre>{@code
     *     PbxAttribute attr = pbxObject.getAttribute("SkillSet");
     *     int skillLevel = attr.getAt(0).getAttribute("Skill_Level").asInt();
     * 
     * }</pre>
     * 
     * 
     * @param index the attribute set index
     * @return a {@linkplain com.ale.o2g.types.management.PbxAttributeMap PbxAttributeMap} 
     * object that represents the attribute sets.
     * @throws AttributeFormatException in case of a conversion error.
     */
    public PbxAttributeMap getAt(int index) {
        
        if (attributeMaps == null) {
            throw new AttributeFormatException("This attribute is not a Set");
        }
        
        return attributeMaps.get(index);
    }
    
    /**
     * Returns this attribute value as a sequence of attributes.
     * @return A {@linkplain com.ale.o2g.types.management.PbxAttributeMap PbxAttributeMap} 
     * object that represents the attribute sets.
     */
    public PbxAttributeMap asAttributeMap() {
        return sequenceMap;
    }
    
    /**
     * Returns the value of this attribute as a list of {@code PbxAttributeMap}.
     * @return A list of {@linkplain com.ale.o2g.types.management.PbxAttributeMap PbxAttributeMap} 
     */
    public List<PbxAttributeMap> asListOfMaps() {
        return attributeMaps;
    }
    
    /**
     * Returns this attribute value as a boolean.
     * @return a boolean value.
     */
    public boolean asBool() {
        return ValueConverter.toNative(values, ValueConverter::toBooleanConverter);
    }
    
    /**
     * Sets the value of this attribute as a boolean.
     * @param value the boolean value
     */
    public void set(boolean value) {
        values = ValueConverter.fromNative(value, ValueConverter::fromBooleanConverter);
        sequenceMap = null;
        attributeMaps = null;
    }
    
    /**
     * Returns this attribute value as a boolean.
     * @return an int value.
     */
    public int asInt() {
        return ValueConverter.toNative(values, ValueConverter::toIntegerConverter);
    }
    
    /**
     * Sets the value of this attribute as an int.
     * @param value the int value
     */
    public void set(int value) {
        values = ValueConverter.fromNative(value, ValueConverter::fromIntegerConverter);
        sequenceMap = null;
        attributeMaps = null;
    }
    
    /**
     * Returns this attribute value as a string.
     * @return a string value.
     */
    public String asString() {
        return ValueConverter.toNative(values, ValueConverter::toStringConverter);
    }
    
    /**
     * Sets the value of this attribute as a string.
     * @param value the string value
     */
    public void set(String value) {
        values = ValueConverter.fromNative(value, ValueConverter::fromStringConverter);
        sequenceMap = null;
        attributeMaps = null;
    }
    
    /**
     * Returns this attribute value as a enum value.
     * @return a string value.
     */
    public String asEnum() {
        return ValueConverter.toNative(values, ValueConverter::toStringConverter);
    }

    /**
     * Add the specified attribute to this sequence.
     * @param attr the attribute.
     */
    public void addSequenceAttribute(PbxAttribute attr) {
     
        if (sequenceMap == null) {
            sequenceMap = PbxAttributeMap.create();
        }
        
        sequenceMap.add(attr);
    }
    

    protected PbxAttribute(String name) {
        this.name = name;
    }

    protected PbxAttribute(List<String> values, String name) {
        this(name);
        this.values = values;
    }
    
    protected PbxAttribute(List<String> values, List<PbxAttributeMap> attributeMaps, String name) {
        this(values, name);
        this.attributeMaps = attributeMaps;
    }

    protected PbxAttribute(PbxAttribute attr) {
        this.values = attr.values;
        this.attributeMaps = attr.attributeMaps;
        this.sequenceMap = attr.sequenceMap;
        this.name = attr.name;
    }

}
