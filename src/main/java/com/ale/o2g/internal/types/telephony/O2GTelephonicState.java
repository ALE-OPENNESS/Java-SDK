/*
* Copyright 2026 ALE International
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

package com.ale.o2g.internal.types.telephony;

import java.util.Collection;

import com.ale.o2g.types.telephony.Call;
import com.ale.o2g.types.telephony.TelephonicState;
import com.ale.o2g.types.telephony.device.Capabilities;
import com.ale.o2g.types.telephony.device.DeviceState;
import com.ale.o2g.types.telephony.user.UserState;


class O2GDeviceStates {
	Collection<DeviceState> deviceStates;
}


/**
 * Internal class for deserialization
 */
public class O2GTelephonicState {
	
    private Collection<Call> calls;
    private Collection<Capabilities> deviceCapabilities;
    private UserState userState;
    private O2GDeviceStates deviceStates;
	
	
	public TelephonicState toTelephonicState() {
		
		Collection<DeviceState> dState = null;
		if (deviceStates != null) {
			dState = deviceStates.deviceStates;
		}
		
		return new TelephonicState(
				calls, 
				deviceCapabilities, 
				userState,
				dState) {};
	}
}
