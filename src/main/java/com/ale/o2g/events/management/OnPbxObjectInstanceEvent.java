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
package com.ale.o2g.events.management;

import com.ale.o2g.events.O2GEvent;
import com.ale.o2g.types.management.PbxObjectDefinition;

/**
 *
 */
class OnPbxObjectInstanceEvent extends O2GEvent {

    private int nodeId;
    private PbxObjectDefinition object;
    private PbxObjectDefinition father;
    
    protected OnPbxObjectInstanceEvent(String eventName, int nodeId, PbxObjectDefinition object, PbxObjectDefinition father) {
        super(eventName);
        this.nodeId = nodeId;
        this.object = object;
        this.father = father;
    }

    /**
     * Returns the OmniPCX Enterprise node id that send this event.
     * 
     * @return the node id.
     */
    public final int getNodeId() {
        return nodeId;
    }

    /**
     * Returns the created object definition.
     * 
     * @return the object definition.
     */
    public final PbxObjectDefinition getObject() {
        return object;
    }

    /**
     * Returns the object father of the created object.
     * 
     * @return the father
     */
    public final PbxObjectDefinition getFather() {
        return father;
    }
}
