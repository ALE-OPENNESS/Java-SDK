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
 */
public class Service {
    
	private String value;
	
	private Service(String value) { 
		this.value = value; 
	}


    public static Service get(String name)
    {
        return new Service(name.toLowerCase());
    }

    public static Service Authentication = new Service("authenticate");
    public static Service Sessions = new Service("sessions");
    public static Service O2G = new Service("O2G");
    public static Service Subscriptions = new Service("subscriptions");
    public static Service EventSummary = new Service("eventsummary");
    public static Service Telephony = new Service("telephony");
    public static Service Users = new Service("users");
    public static Service Routing = new Service("routing");
    public static Service Messaging = new Service("voicemail");
    public static Service Maintenance = new Service("system");
    public static Service Directory = new Service("directory");
    public static Service CommunicationLog = new Service("comlog");
    public static Service Analytics = new Service("analytics");
    public static Service CallCenterAgent = new Service("acdagent");
    public static Service CallCenterPilot = new Service("acdpilotmonitoring");
    public static Service CallCenterManagement = new Service("acdmanagement");
    public static Service Rsi = new Service("acdrsi");
    public static Service Management = new Service("pbxmanagement");
    public static Service UserManagement = new Service("usermanagement");
    public static Service CallCenterRealtime = new Service("acdrealtime");
    public static Service CallCenterStatistics = new Service("acd statistics");
//    public static Service Recording = new Service("oxr recording");


	@Override
	public int hashCode() {
		return value.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Service)) {
			return false;
		}
		Service other = (Service) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
}
