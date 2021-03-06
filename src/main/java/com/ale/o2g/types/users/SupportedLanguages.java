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
package com.ale.o2g.types.users;

import java.util.Collection;

/**
 * {@code SupportedLanguages} represents the languages supported by a user.
 * 
 * @see Preferences Preference.
 */
public class SupportedLanguages {
    private Collection<String> supportedLanguages;
    private Collection<String> supportedGuiLanguages;
    
    protected SupportedLanguages() {
    }

    /**
     * Return the supported languages.
     * 
     * @return the collection of supported languages.
     */
    public Collection<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    /**
     * Return the supported GUI languages.
     * 
     * @return the collection of supported GUI languages.
     */
    public Collection<String> getSupportedGuiLanguages() {
        return supportedGuiLanguages;
    }

}
