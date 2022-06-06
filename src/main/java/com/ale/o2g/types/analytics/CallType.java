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
package com.ale.o2g.types.analytics;

/**
 * {@code CallType} represents the possible call types in a charging ticket.
 */
public enum CallType {

    /**
     * A public network call.
     */
    PublicNetworkCall,

    /**
     * A public network call through a private network.
     */
    PublicNetworkCallThroughPrivateNetwork,

    /**
     * A private network call.
     */
    PrivateNetworkCall,

    /**
     * A local network call.
     */
    LocalNetworkCall,

    /**
     * An incoming call from the public network.
     */
    PublicNetworkIncomingCall,

    /**
     * An incoming call from the public network through the private network.
     */
    PublicNetworkIncomingCallThroughPrivateNetwork,

    /**
     * An outgoing call from private network to the public network.
     */
    PrivateNetworkOutgoingCallToPublicNetwork,

    /**
     * An outgoing call from private network to the private network.
     */
    PrivateNetworkOutgoingCallToPrivateNetwork,

    /**
     * An incoming call from the public network to the private network.
     */
    PublicNetworkIncomingCallToPrivateNetwork,

    /**
     * An incoming call from the private network to the private network.
     */
    PrivateNetworkIncomingCallToPrivateNetwork,

    /**
     * An outgoing call through to the private network.
     */
    PublicOrPrivateNetworkOutgoingCallThroughPrivateNetwork,

    /**
     * An incoming call through to the private network.
     */
    PublicOrPrivateNetworkIncomingCallThroughPrivateNetwork,

    /**
     * An incoming call from private network.
     */
    PrivateNetworkIncomingCall,

    /**
     * A local call.
     */
    LocalNode,

    /**
     * A call in transit on this node
     */
    LocalTransit,

    /**
     * Not specified call type.
     */
    Unspecified
}
