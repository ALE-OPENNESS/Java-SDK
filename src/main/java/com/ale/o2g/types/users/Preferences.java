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

/**
 * {@code Preferences} represents the preferred settings of a user.
 */
public class Preferences {

    private String guiLanguage;
    private String oxeLanguage;

    /**
     * Returns the preferred GUI language. This is the language the user prefers
     * when it uses an application with a graphical user interface.
     * 
     * @return the gui language in
     *         <a href="https://en.wikipedia.org/wiki/ISO_639-1">ISO 639-1
     *         format</a>.
     */
    public String getGuiLanguage() {
        return guiLanguage;
    }

    /**
     * Returns the prefered OXE language. This is the language the user prefers when
     * it uses his phone set.
     * 
     * @return the preferred OXE language.
     */
    public String getOxeLanguage() {
        return oxeLanguage;
    }

    protected Preferences() {
        
    }
}
