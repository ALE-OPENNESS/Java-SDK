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
package com.ale.o2g.internal.types.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ale.o2g.types.management.PbxAttribute;
import com.ale.o2g.types.management.PbxAttributeMap;

/**
 *
 */
public class O2GPbxAttribute {

    private String name;
    private List<String> value;
    private List<O2GPbxComplexAttribute> complexValue;
    
    
    public final String getName() {
        return name;
    }

    
    public final List<String> getValue() {
        return value;
    }


    protected O2GPbxAttribute(String name, List<String> value) {
        this.name = name;
        this.value = value;
    }

    public O2GPbxAttribute(String name, List<String> value, List<O2GPbxComplexAttribute> complexValue) {
        this(name, value);
        this.complexValue = complexValue;
    }


    public PbxAttribute toPbxAttribute() {

        List<PbxAttributeMap> attrSet = null;
        if (complexValue != null) {
            attrSet = complexValue.stream().map(cv -> cv.toPbxAttributeMap()).toList();
        }
        
        return new PbxAttribute(
                value,
                attrSet,
                name) {};
    }
    
       
    static class PbxAttributeMapExtended extends PbxAttributeMap {
        
        public PbxAttributeMapExtended(PbxAttributeMap attrMap) {
            super(attrMap);
        }
        
        public O2GPbxComplexAttribute toO2GPbxComplexAttribute(String attrName) {
            
            List<O2GPbxAttribute> o2gPbxAttributes = new ArrayList<O2GPbxAttribute>();
            
            for (PbxAttribute pbxAttribute : attributes.values()) {
                
                PbxAttributeExtended pbxAttributeExtended = new PbxAttributeExtended(pbxAttribute);
                o2gPbxAttributes.addAll(pbxAttributeExtended.toCollectionOfO2GPbxAttribute());
            }
            
            return new O2GPbxComplexAttribute(attrName, o2gPbxAttributes);
        }
    }
    

    static class PbxAttributeExtended extends PbxAttribute {
        
        public PbxAttributeExtended(PbxAttribute attribute) {
            super(attribute);
        }
                
        public O2GPbxAttribute toO2GPbxAttribute(String name) {
            return new O2GPbxAttribute(name, this.values);
        }
        
        public Collection<O2GPbxAttribute> toCollectionOfO2GPbxAttribute() {
            Collection<O2GPbxAttribute> listAttr = new ArrayList<O2GPbxAttribute>();
            
            if (sequenceMap != null) {
                for (String name : sequenceMap.getNames()) {
                    PbxAttributeExtended seqPbxAttr = new PbxAttributeExtended(sequenceMap.getAttribute(name));
                    listAttr.add(seqPbxAttr.toO2GPbxAttribute(String.format("%s.%s", this.getName(), name)));
                }
                                
            }
            else if (attributeMaps == null) {
                listAttr.add(new O2GPbxAttribute(name, values));
            }
            else {
                List<O2GPbxComplexAttribute> complexValue = new ArrayList<O2GPbxComplexAttribute>();
                
                for (PbxAttributeMap attrMap : attributeMaps) {
                    
                    PbxAttributeMapExtended pbxAttrMapExtended = new PbxAttributeMapExtended(attrMap);
                    complexValue.add(pbxAttrMapExtended.toO2GPbxComplexAttribute(name));
                }
                
                listAttr.add(new O2GPbxAttribute(name, values, complexValue));
            }
            
            return listAttr;
        }
    }
        
    public static Collection<O2GPbxAttribute> from(PbxAttribute attribute) {
         
        PbxAttributeExtended pbxAttr = new PbxAttributeExtended(attribute);
        return pbxAttr.toCollectionOfO2GPbxAttribute();
    }
}
