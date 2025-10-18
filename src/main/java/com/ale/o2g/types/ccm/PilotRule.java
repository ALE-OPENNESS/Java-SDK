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
package com.ale.o2g.types.ccm;

/**
 * {@code PilotRule} represents a CCD pilot rule.
 * <p>
 * A pilot rule defines a specific configuration or behavior for a CCD pilot.
 * Each rule has a unique number, a name, and an active status indicating whether
 * it is currently applied.
 */
public class PilotRule {

    private int number;
    private String name;
    private boolean active;

    /**
     * Returns the unique identifier for this rule.
     * 
     * @return the rule number
     */
    public final int getNumber() {
        return number;
    }

    /**
     * Returns the name of this rule.
     * 
     * @return the rule name
     */
    public final String getName() {
        return name;
    }

    /**
     * Indicates whether this rule is currently active.
     * 
     * @return {@code true} if the rule is active, {@code false} otherwise
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * Constructs a new pilot rule with the specified number, name, and active status.
     * 
     * @param number the unique identifier of the rule
     * @param name the name of the rule
     * @param active {@code true} if the rule is active, {@code false} otherwise
     */
    protected PilotRule(int number, String name, boolean active) {
        this.number = number;
        this.name = name;
        this.active = active;
    }
    
}
