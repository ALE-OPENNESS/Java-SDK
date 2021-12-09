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

import java.util.List;

import com.ale.o2g.O2GException;
import com.ale.o2g.types.ServerInfo;

/**
 *
 */
public final class RoxeRestApiDescriptor {

	public static class Version {
		
	    private String id;
	    private String status;
	    private String publicUrl;
	    private String internalUrl;
	    
		public String getId() {
			return id;
		}
		
		public String getStatus() {
			return status;
		}
		
		public String getPublicUrl() {
			return publicUrl;
		}
		
		public String getInternalUrl() {
			return internalUrl;
		}
	}

	
    private ServerInfo serverInfo;
    private List<Version> versions;

    /**
	 * @return the serverInfo
	 */
	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public Version getCurrent() throws O2GException {
        
    	for (Version version : versions) {
            if (version.getStatus() .equals("CURRENT")) {
                return version;
            }
        }

        throw new O2GException("Unable to retrieve current API version");
    }

    public Version get(String versionId) {
    	for (Version version : versions) {
            if (version.getId().equals(versionId)) {
                return version;
            }
        }

        return null;
    }
}
