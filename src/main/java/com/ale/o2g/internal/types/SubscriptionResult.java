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
package com.ale.o2g.internal.types;

/**
 * 
 *
 */
public class SubscriptionResult {

	private String subscriptionId;
	private String message;
	private String publicPollingUrl;
	private String privatePollingUrl;
	private String status;
	
	/**
	 * Retrieves whether this subscription result indicates the subscription has been accepted
	 * @return true if the subscription has been accepted; false otherwise
	 */
	public boolean isAccepted() {
		return "ACCEPTED".equals(status);
	}
	
	/**
	 * Retrieves the identifier of the accepted subscription
	 * @return the subscription identifier or null if the subscription has not been accepted
	 */
	public String getId() {
		return subscriptionId;
	}

	public String getPublicPollingUrl() {
		return publicPollingUrl;
	}

	public String getPrivatePollingUrl() {
		return privatePollingUrl;
	}

	public String getMessage() {
		return message;
	}
}
