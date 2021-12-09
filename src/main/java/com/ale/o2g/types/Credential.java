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

import java.util.Objects;

/**
 * The credential class represents an O2G user or administrator credential.
 */
public final class Credential {

	private String login;
	private String password;
	
	/**
	 * Construct a new credential with the specified login and password.
	 * @param login ehe user or administrator login
	 * @param password the password
	 */
	public Credential(String login, String password) {
		
		this.login = Objects.requireNonNull(login, "Login can not be null");
		this.password = Objects.requireNonNull(password, "Password can not be null");;
	}

	/**
	 * Return this credential login.
	 * @return the login
	 */
	public final String getLogin() {
		return login;
	}

	/**
	 * Return this credential password.
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}
	
}
