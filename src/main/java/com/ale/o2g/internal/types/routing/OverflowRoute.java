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

import com.ale.o2g.types.routing.Overflow;
import com.ale.o2g.types.routing.Overflow.Condition;

/**
 *
 */
public class OverflowRoute {
	private String overflowType;
	private List<O2GDestination> destinations = new ArrayList<O2GDestination>();
	
	public Overflow toOverflow() {

		if (destinations.size() == 1) {
			return new Overflow(destinations.get(0).toDestination(), getCondition()) {};
        }
		else {
        	return new Overflow(null, getCondition()) {};
		}
	}

	protected Overflow.Condition getCondition() {
        if ("BUSY".equals(overflowType)) return Overflow.Condition.BUSY;
        else if ("NO_ANSWER".equals(overflowType)) return Overflow.Condition.NO_ANSWER;
        else return Overflow.Condition.BUSY_NO_ANSWER;
	}

	public static OverflowRoute createOverflowOnVoiceMail(Condition condition) {
		OverflowRoute objForwardRoute = new OverflowRoute();
        objForwardRoute.destinations.add(O2GDestination.createVoiceMailDestination());
        objForwardRoute.overflowType = getOverflowType(condition);

        return objForwardRoute;
	}

	public static OverflowRoute createOverflowOnAssociate(Condition condition) {
		OverflowRoute objForwardRoute = new OverflowRoute();
        objForwardRoute.destinations.add(O2GDestination.createAssociateDestination());
        objForwardRoute.overflowType = getOverflowType(condition);

        return objForwardRoute;
	}

    private static String getOverflowType(Overflow.Condition condition) {
        switch (condition) {
            case BUSY: return "BUSY";
            case NO_ANSWER: return "NO_ANSWER";
            default: return "BUSY_NO_ANSWER";
        }
    }
}
