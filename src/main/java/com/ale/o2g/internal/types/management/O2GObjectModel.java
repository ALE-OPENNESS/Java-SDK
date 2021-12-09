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

import com.ale.o2g.types.management.Model;
import com.ale.o2g.types.management.ModelAttribute;

/**
 *
 */
public class O2GObjectModel {

    private String objectName;
    private Collection<O2GAttributeModel> attributes;
    private Collection<O2GObjectModel> objects;
    private boolean hidden;
    private boolean create;
    private boolean delete;
    private boolean set;
    private boolean get;
    private Collection<String> otherActions;
    
    
    public static Model build(O2GObjectModel o2gObjectModel) {

        Map<String, Model> mapObjects = new HashMap<String, Model>();
        
        if (o2gObjectModel.objects != null) {
            
            for (O2GObjectModel o2gModel : o2gObjectModel.objects) {
                Model objModel = build(o2gModel);
                mapObjects.put(objModel.getName(), objModel);
            }
        }
        
        Map<String, ModelAttribute> mapAttributes = new HashMap<String, ModelAttribute>();
        if (o2gObjectModel.attributes != null) {
            
            o2gObjectModel.attributes.forEach(a -> mapAttributes.put(a.getName(), a.toAttributeModel(o2gObjectModel.objectName)));
        }
        
        return new Model(
                mapAttributes,
                mapObjects,
                o2gObjectModel.objectName,
                o2gObjectModel.hidden,
                o2gObjectModel.create, 
                o2gObjectModel.delete, 
                o2gObjectModel.set, 
                o2gObjectModel.get, 
                o2gObjectModel.otherActions) {};
    }
}
