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
package com.ale.o2g.util;

import java.net.CookieManager;
import java.net.Socket;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Builder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;


public class HttpClientBuilder {

	private static HttpClientBuilder instance = null;

	public static HttpClientBuilder getInstance() {
		if (instance == null) {
			instance = new HttpClientBuilder();
		}

		return instance;
	}

	private TrustManager[] getPermissiveTrustManager() {
		return new TrustManager[] {
				new X509ExtendedTrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					}

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket)
                            throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine)
                            throws CertificateException {
                    }
				}
		};

	}

    public HttpClient build() throws Exception {
        return this.build(null);
    }

    public HttpClient build(ExecutorService executorService) throws Exception {

		Builder builder = HttpClient.newBuilder().cookieHandler(new CookieManager());

		if (executorService != null) {
		    builder.executor(executorService);
		}

		if (System.getProperty("o2g.disable.ssl") != null) {

			// Initialize SSL
			SSLContext sc = SSLContext.getInstance("TLSv1.2");

			sc.init(null, getPermissiveTrustManager(), new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            builder = builder.sslContext(sc);
		}

		return builder.build();
	}
}
