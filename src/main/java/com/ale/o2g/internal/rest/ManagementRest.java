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
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.ale.o2g.ManagementService;
import com.ale.o2g.internal.types.management.O2GObjectModel;
import com.ale.o2g.internal.types.management.O2GPbxAttribute;
import com.ale.o2g.internal.types.management.O2GPbxObject;
import com.ale.o2g.internal.util.AssertUtil;
import com.ale.o2g.internal.util.HttpUtil;
import com.ale.o2g.internal.util.URIBuilder;
import com.ale.o2g.types.management.Filter;
import com.ale.o2g.types.management.Model;
import com.ale.o2g.types.management.Pbx;
import com.ale.o2g.types.management.PbxAttribute;
import com.ale.o2g.types.management.PbxObject;

/**
 *
 */
public class ManagementRest extends AbstractRESTService implements ManagementService {

    static class PbxList {
        private Collection<String> nodeIds;

        public Collection<Integer> toIntNodesList() {
            if (nodeIds == null) {
                return null;
            }
            else {
                return nodeIds.stream().map(n -> Integer.parseInt(n)).toList();
            }
        }
    }
    
    static class O2GPbxObjectIds {
//        private String objectName;
        private Collection<String> objectIds;
    }

    
    static class O2GPbxAttributeList {
        public Collection<O2GPbxAttribute> attributes = new ArrayList<O2GPbxAttribute>();

        public O2GPbxAttributeList(Collection<PbxAttribute> attr) {
            attr.forEach(a -> attributes.addAll(O2GPbxAttribute.from(a)));
        }
    }
    
    
    
    
    public ManagementRest(HttpClient httpClient, URI uri) {
        super(httpClient, uri);
    }

    @Override
    public Collection<Integer> getPbxs() {
        HttpRequest request = HttpUtil.GET(uri);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        PbxList pbxs = getResult(response, PbxList.class);
        if (pbxs == null) {
            return null;
        }
        else {
            return pbxs.toIntNodesList();
        }
    }

    @Override
    public Pbx getPbx(int nodeId) {
        HttpRequest request = HttpUtil.GET(URIBuilder.appendPath(uri, String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId"))));
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return getResult(response, Pbx.class);
    }

    @Override
    public Model getObjectModel(int nodeId, String objectName) {
        URI uriGet = URIBuilder.appendPath(uri, String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), "model");
        if (objectName != null) {
            uriGet = URIBuilder.appendPath(uriGet, objectName);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());

        O2GObjectModel o2gObjectModel = getResult(response, O2GObjectModel.class);
        if (o2gObjectModel == null) {
            return null;
        }
        else {
            return O2GObjectModel.build(o2gObjectModel);
        }
    }

    @Override
    public PbxObject getNodeObject(int nodeId) {
        URI uriGet = URIBuilder.appendPath(uri, String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), "instances");
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GPbxObject o2GPbxObject = getResult(response, O2GPbxObject.class);
        if (o2GPbxObject == null) {
            return null;
        }
        else {
            return O2GPbxObject.build(o2GPbxObject);
        }        
    }

    @Override
    public PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId) {
        return this.getObject(nodeId, objectInstanceDefinition, objectId, (String)null);
    }

    @Override
    public PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId, String attributes) {

        URI uriGet = URIBuilder.appendPath(uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "instances",
                AssertUtil.requireNotEmpty(objectInstanceDefinition, "objectInstanceDefinition"),
                AssertUtil.requireNotEmpty(objectId, "objectId"));

        if ((attributes != null) && (attributes.length() > 0)) {
            uriGet = URIBuilder.appendQuery(uriGet, "attributes", attributes);
        }
        
        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GPbxObject o2GPbxObject = getResult(response, O2GPbxObject.class);
        if (o2GPbxObject == null) {
            return null;
        }
        else {
            return O2GPbxObject.build(o2GPbxObject);
        }        
    }

    @Override
    public PbxObject getObject(int nodeId, String objectInstanceDefinition, String objectId,
            Collection<PbxAttribute> attributes) {
        
        String attrString = String.join(",", AssertUtil.requireNotNull(attributes, "attributes").stream().map(a -> a.getName()).toList());
        return this.getObject(nodeId, objectInstanceDefinition, objectId, attrString);
    }

    @Override
    public Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition, Filter filter) {
        return this.getObjectInstances(nodeId, objectInstanceDefinition, filter.toString());
    }

    @Override
    public Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition, String filter) {

        URI uriGet = URIBuilder.appendPath(uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "instances",
                AssertUtil.requireNotEmpty(objectInstanceDefinition, "objectInstanceDefinition"));
        
        if (filter != null) {
            uriGet = URIBuilder.appendQuery(uriGet, "filter", filter);
        }

        HttpRequest request = HttpUtil.GET(uriGet);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        O2GPbxObjectIds objectIds = getResult(response, O2GPbxObjectIds.class);
        if (objectIds == null) {
            return null;
        }
        else {
            return objectIds.objectIds;
        }        
    }

    @Override
    public Collection<String> getObjectInstances(int nodeId, String objectInstanceDefinition) {
        return this.getObjectInstances(nodeId, objectInstanceDefinition, (String)null);
    }

    @Override
    public boolean setObject(int nodeId, String objectInstanceDefinition, String objectId,
            Collection<PbxAttribute> attributes) {

        URI uriPut = URIBuilder.appendPath(uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "instances",
                AssertUtil.requireNotEmpty(objectInstanceDefinition, "objectInstanceDefinition"),
                AssertUtil.requireNotEmpty(objectId, "objectId"));

        String json = gson.toJson(new O2GPbxAttributeList(attributes));

        HttpRequest request = HttpUtil.PUT(uriPut, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return isSucceeded(response);
    }

    @Override
    public boolean deleteObject(int nodeId, String objectInstanceDefinition, String objectId, boolean forceDelete) {
        URI uriDelete = URIBuilder.appendPath(uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "instances",
                AssertUtil.requireNotEmpty(objectInstanceDefinition, "objectInstanceDefinition"),
                AssertUtil.requireNotEmpty(objectId, "objectId"));
        
        if (forceDelete) {
            uriDelete = URIBuilder.appendQuery(uriDelete, "force");
        }
        
        HttpRequest request = HttpUtil.DELETE(uriDelete);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return isSucceeded(response);
    }

    @Override
    public boolean deleteObject(int nodeId, String objectInstanceDefinition, String objectId) {
        return this.deleteObject(nodeId, objectInstanceDefinition, objectId, false);
    }

    @Override
    public boolean createObject(int nodeId, String objectInstanceDefinition, Collection<PbxAttribute> attributes) {

        URI uriPost = URIBuilder.appendPath(uri, 
                String.valueOf(AssertUtil.requirePositive(nodeId, "nodeId")), 
                "instances",
                AssertUtil.requireNotEmpty(objectInstanceDefinition, "objectInstanceDefinition"));
        
        String json = gson.toJson(new O2GPbxAttributeList(attributes));

        HttpRequest request = HttpUtil.POST(uriPost, json);
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, BodyHandlers.ofString());
        
        return isSucceeded(response);
    }
}
