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
package com.ale.o2g.internal.events.management;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.events.management.OnPbxObjectInstanceCreatedEvent;
import com.ale.o2g.events.management.OnPbxObjectInstanceDeletedEvent;
import com.ale.o2g.events.management.OnPbxObjectInstanceModifiedEvent;
import com.ale.o2g.types.management.PbxObjectDefinition;

/**
 *
 */
public class OnInternalPbxObjectEvent extends O2GEvent {
    private String nodeId;
    private String objectName;
    private String objectId;
    private PbxObjectDefinition father;
    
    public static O2GEvent adaptCreated(O2GEvent ev) {
        if (ev instanceof OnInternalPbxObjectEvent) {
            
            OnInternalPbxObjectEvent org = (OnInternalPbxObjectEvent)ev;
            return new OnPbxObjectInstanceCreatedEvent(
                    org.getName(), 
                    Integer.parseInt(org.nodeId),
                    new PbxObjectDefinition(org.objectName, org.objectId) {},
                    org.father) {};
        }
        else {
            throw new Error("Invalid translator exception");
        }
    }

    public static O2GEvent adaptDeleted(O2GEvent ev) {
        if (ev instanceof OnInternalPbxObjectEvent) {
            
            OnInternalPbxObjectEvent org = (OnInternalPbxObjectEvent)ev;
            return new OnPbxObjectInstanceDeletedEvent(
                    org.getName(), 
                    Integer.parseInt(org.nodeId),
                    new PbxObjectDefinition(org.objectName, org.objectId) {},
                    org.father) {};
        }
        else {
            throw new Error("Invalid translator exception");
        }
    }    

    public static O2GEvent adaptModified(O2GEvent ev) {
        if (ev instanceof OnInternalPbxObjectEvent) {
            
            OnInternalPbxObjectEvent org = (OnInternalPbxObjectEvent)ev;
            return new OnPbxObjectInstanceModifiedEvent(
                    org.getName(), 
                    Integer.parseInt(org.nodeId),
                    new PbxObjectDefinition(org.objectName, org.objectId) {},
                    org.father) {};
        }
        else {
            throw new Error("Invalid translator exception");
        }
    }    
}
