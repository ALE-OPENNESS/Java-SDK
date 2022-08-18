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
package com.ale.o2g.types.maintenance;

import com.ale.o2g.internal.util.JsonEnumDeserializerFallback;

/**
 * {@code PbxStatus} provides general information on an OmniPCX Enterprise node.
 * Its connection state, version, addresses...
 */
public class PbxStatus {

    /**
     * {@code CTILinkState} represents the differents possible states of the CTI
     * link between OmniPCX Enterprise and O2G server.
     */
    @JsonEnumDeserializerFallback(value = "DISCONNECTED")
    public static enum CTILinkState {

        /**
         * CTI Link is established with the main OmniPCX Enterprise call server.
         */
        CONNECTED_MAIN,

        /**
         * CTI Link is established with the standby OmniPCX Enterprise call server.
         */
        CONNECTED_SECONDARY,

        /**
         * CTI Link is not established.
         */
        DISCONNECTED
    }

    private String name;
    private int nodeId;
    private ServerAddress mainAddress;
    private ServerAddress secondaryAddress;
    private String version;
    private boolean connected;
    private boolean loaded;
    private CTILinkState ctiLinkState;
    private boolean secured;
    private int monitoredUserNumber;

    /**
     * Returns the name of the OmniPCX Enterprise.
     * 
     * @return the name the OXE node name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns this OmniPCX Enterprise node number.
     * 
     * @return the node number
     */
    public final int getNodeId() {
        return nodeId;
    }

    /**
     * Returns the OmniPCX Enterprise main address.
     * 
     * @return the main address
     */
    public final ServerAddress getMainAddress() {
        return mainAddress;
    }

    /**
     * Returns the OmniPCX Enterprise secondary address.
     * 
     * @return the secondary address or {@code null} if the OmniPCX Enterprise node
     *         is not duplicated.
     */
    public final ServerAddress getSecondaryAddress() {
        return secondaryAddress;
    }

    /**
     * Returns the OmniPCX Enterprise version.
     * 
     * @return the version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * Returns whether this O2G is connected to this OmniPCX Enterprise node.
     * 
     * @return {@code true} if connected; {@code false} otherwise.
     */
    public final boolean isConnected() {
        return connected;
    }

    /**
     * Returns whether the O2G has loaded all this OmniPCX Enterprise node's users.
     * 
     * @return {@code true} if the data are loaded; {@code false} otherwise.
     * @see com.ale.o2g.events.maintenance.OnPbxLoadedEvent OnPbxLoadedEvent.
     */
    public final boolean isLoaded() {
        return loaded;
    }

    /**
     * Returns the state of the CSTA link between the O2Gserver and this OmniPCX
     * Enterprise node.
     * 
     * @return the cti link state.
     */
    public final CTILinkState getCtiLinkState() {
        return ctiLinkState;
    }

    /**
     * Returns whether the OmniPCX Enterprise node is secured. If the OmniPCX
     * Enterprise node is secured, the connection with the O2G server is done using
     * SSH.
     * 
     * @return {@code true} if the OXE is secured; {@code false} otherwise.
     */
    public final boolean isSecured() {
        return secured;
    }

    /**
     * Returns the number of monitored users on this OmniPCX Enterprise node.
     * 
     * @return the the number of monitored users.
     */
    public final int getMonitoredUserNumber() {
        return monitoredUserNumber;
    }

    protected PbxStatus() {
        
    }
}
