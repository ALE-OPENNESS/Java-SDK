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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ale.o2g.types.management.PbxAttribute;
import com.ale.o2g.types.management.PbxObject;

/**
 *
 */
public class O2GPbxObject {

    private String objectName;
    private String objectId;
    private Collection<O2GPbxAttribute> attributes;
    private Collection<String> objectNames;
//    private boolean create;
    private boolean delete;
    private boolean set;
//    private boolean get;
//    private Collection<String> otherActions;
    
    
    public static PbxObject build(O2GPbxObject o2gPbxObject) {
        
        Map<String, PbxAttribute> mapAttributes = new HashMap<String, PbxAttribute>();
        
        if (o2gPbxObject.attributes != null) {
            
            for (O2GPbxAttribute attr : o2gPbxObject.attributes) {
                
                String[] names = attr.getName().split("\\.");
                if (names.length == 1) {
                    mapAttributes.put(attr.getName(), attr.toPbxAttribute());
                }
                else {
                    if (!mapAttributes.containsKey(names[0])) {
                        mapAttributes.put(names[0], new PbxAttribute(names[0]) {});
                    }
                    
                    PbxAttribute parent = mapAttributes.get(names[0]);
                    parent.addSequenceAttribute(new PbxAttribute(attr.getValue(), names[1]) {});
                }
            }
        }
        
        return new PbxObject(
                mapAttributes,
                o2gPbxObject.objectName,
                o2gPbxObject.objectId,
                o2gPbxObject.objectNames,
                o2gPbxObject.delete,
                o2gPbxObject.set) {};
    }
}
