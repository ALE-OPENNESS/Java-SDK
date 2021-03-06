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

/**
 *
 */
public class O2GDestination {

	private String type;
	private String number;
	@SuppressWarnings("unused")
	private Boolean selected;
	
	
	private O2GDestination(String type, String number, Boolean selected) {
		this.type = type;
		this.number = number;
		this.selected = selected;
	}

	
	public static O2GDestination createMobileDestination(boolean active) {
		return new O2GDestination("MOBILE", null, active);
	}

	public static O2GDestination createVoiceMailDestination() {
		return new O2GDestination("VOICEMAIL", null, null);
	}

	public static O2GDestination createAssociateDestination() {
		return new O2GDestination("ASSOCIATE", null, null);
	}

	public static O2GDestination createNumberDestination(String number) {
		return new O2GDestination("NUMBER", number, null);
	}
	
	public final String getNumber() {
		return number;
	}
	
	public final String getType() {
		return type;
	}

	public boolean isSelected() {
		return (this.selected != null) ? this.selected : false;
	}

	public com.ale.o2g.types.routing.Destination toDestination() {
        if ("NUMBER".equals(type)) return com.ale.o2g.types.routing.Destination.NUMBER;
        else if ("VOICEMAIL".equals(type)) return com.ale.o2g.types.routing.Destination.VOICEMAIL;
//        else if ("ASSOCIATE".equals(type)) return com.ale.o2g.types.routing.Destination.ASSOCIATE;
        else return com.ale.o2g.types.routing.Destination.NONE;
    }

}
