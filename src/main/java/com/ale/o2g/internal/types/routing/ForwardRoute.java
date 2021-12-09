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
package com.ale.o2g.internal.types.routing;

import java.util.ArrayList;
import java.util.List;

import com.ale.o2g.types.routing.Forward;
import com.ale.o2g.types.routing.Forward.Condition;

/**
 *
 */
public class ForwardRoute {

	private String forwardType;
	private List<O2GDestination> destinations = new ArrayList<O2GDestination>();
	
	public Forward toForward() {

		Forward.Condition forwardCondition = getCondition();
		if (destinations.size() == 1) {
			
			O2GDestination destination = destinations.get(0);
			
			com.ale.o2g.types.routing.Destination forwardDestination = destination.toDestination();
            if (forwardDestination == com.ale.o2g.types.routing.Destination.NUMBER) {
            	return new Forward(forwardDestination, forwardCondition, destination.getNumber()) {};
            }
            else {
            	return new Forward(forwardDestination, forwardCondition, null) {};
            }
        }
		else {
        	return new Forward(null, forwardCondition, null) {};
		}
	}
	
	protected Forward.Condition getCondition() {
        if ("BUSY".equals(forwardType)) return Forward.Condition.BUSY;
        else if ("NO_ANSWER".equals(forwardType)) return Forward.Condition.NO_ANSWER;
        else if ("BUSY_NO_ANSWER".equals(forwardType)) return Forward.Condition.BUSY_NO_ANSWER;
        else return Forward.Condition.IMMEDIATE;
	}

	public static ForwardRoute createForwardOnVoiceMail(Condition condition) {
        ForwardRoute objForwardRoute = new ForwardRoute();
        objForwardRoute.destinations.add(O2GDestination.createVoiceMailDestination());
        objForwardRoute.forwardType = getForwardType(condition);

        return objForwardRoute;
	}

	public static ForwardRoute createForwardOnNumber(String number, Condition condition) {
        ForwardRoute objForwardRoute = new ForwardRoute();
        objForwardRoute.destinations.add(O2GDestination.createNumberDestination(number));
        objForwardRoute.forwardType = getForwardType(condition);

        return objForwardRoute;
	}
	
    private static String getForwardType(Forward.Condition condition) {
        switch (condition) {
            case BUSY: return "BUSY";
            case NO_ANSWER: return "NO_ANSWER";
            case BUSY_NO_ANSWER: return "BUSY_NO_ANSWER";
            default: return null;
        }
    }


}
