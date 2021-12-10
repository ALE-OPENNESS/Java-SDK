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
package com.ale.o2g.types;

/**
 * {@code ServerInfo} provide information on the O2G Server.
 */
public final class ServerInfo {

    /**
     * {@code ProductVersion} represents the O2G product version.
     */
    public static class ProductVersion {

        private String major;
        private String minor;

        protected ProductVersion() {
        }

        public String toString() {
            return String.format("%s.%s", major, minor);
        }
    }
    
    protected ServerInfo() {
    }

    private String productName;
    private String productType;
    private ProductVersion productVersion;
    private boolean haMode;

    /**
     * Returns the product name.
     * @return the product name
     */
    public final String getProductName() {
        return productName;
    }

    /**
     * Returns the product type.
     * @return the product type. 
     */
    public final String getProductType() {
        return productType;
    }

    /**
     * Returns the O2G server version.
     * @return the version.
     */
    public final String getProductVersion() {
        return productVersion.toString();
    }

    /**
     * Returns wheather the O2G server is in HA mode.
     * 
     * @return {@code true} is the O2G server is in HA mode; {@code false}
     *         otherwise.
     */
    public final boolean isHaMode() {
        return haMode;
    }
}
