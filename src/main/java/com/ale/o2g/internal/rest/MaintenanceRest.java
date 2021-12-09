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
package com.ale.o2g.internal.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.MaintenanceService;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.maintenance.License;
import com.ale.o2g.types.maintenance.PbxStatus;
import com.ale.o2g.types.maintenance.ServerAddress;
import com.ale.o2g.types.maintenance.SystemStatus;

/**
 *
 */
public class MaintenanceRest extends AbstractRESTService implements MaintenanceService {

    class LicenseStatus{
        public Collection<License> lics;
    }
    
    static class O2GSystemStatus {
        private ServerAddress logicalAddress;
        private Date startDate;
        private boolean ha;
        private String primary;
        private String primaryVersion;
        private String secondary;
        private String secondaryVersion;
        private Collection<PbxStatus> pbxs;
        private LicenseStatus license;
        private SystemStatus.Configuration configurationType;
    }
    
    
	public MaintenanceRest(HttpClient httpClient, URI uri) {
		super(httpClient, uri);
	}

	@Override
	public SystemStatus getSystemStatus() {

		HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, "status"));
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
		
		O2GSystemStatus systemStatus = getResult(response, O2GSystemStatus.class);
		if (systemStatus == null) {
		    return null;
		}
		else {
		    
		    Collection<License> lics = null;
		    if (systemStatus.license != null) {
		        lics = systemStatus.license.lics;
		    }
		    
		    return new SystemStatus(
		            systemStatus.logicalAddress,
		            systemStatus.startDate, 
		            systemStatus.ha,
		            systemStatus.primary,
		            systemStatus.primaryVersion,
		            systemStatus.secondary,
		            systemStatus.secondaryVersion,
		            systemStatus.pbxs,
		            lics,
		            systemStatus.configurationType) {};
		}
	}

}
