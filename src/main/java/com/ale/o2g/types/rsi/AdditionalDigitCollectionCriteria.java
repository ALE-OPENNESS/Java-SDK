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
package com.ale.o2g.types.rsi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code AdditionalDigitCollectionCriteria} is used to define a digit
 * collection strategy.
 */
public class AdditionalDigitCollectionCriteria {

    private static enum Type {
        NONE, ABORT, IGNORE, BACKSPACE, TERM, RESET
    }

    private Map<Character, Type> mapDigits = new HashMap<Character, Type>();

    private Collection<Character> abortDigits = new ArrayList<Character>();
    private Collection<Character> ignoreDigits = new ArrayList<Character>();
    private Collection<Character> backspaceDigits = new ArrayList<Character>();
    private Collection<Character> termDigits = new ArrayList<Character>();
    private Collection<Character> resetDigits = new ArrayList<Character>();
    private int startTimeout;
    private int digitTimeout;

    /**
     * Constructs a new empty {@code AdditionalDigitCollectionCriteria}.
     */
    public AdditionalDigitCollectionCriteria() {
        clear();
    }

    /**
     * Sets the start timeout, the number of seconds waiting for DTMF inputs from
     * caller.
     * 
     * @param timeout the start timeout.
     */
    public void setStartTimeout(int timeout) {
        this.startTimeout = timeout;
    }

    /**
     * Sets the digit timeout, the number of seconds to wait between DTMF inputs.
     * 
     * @param timeout the digit timeout.
     */
    public void setDigitTimeout(int timeout) {
        this.digitTimeout = timeout;
    }

    /**
     * Returns the digits used to consider that digits collection is aborted.
     * 
     * @return a string containing the digits.
     */
    public String getAbortDigits() {
        return collectionToString(abortDigits);
    }

    /**
     * Returns the digits ignored during the collection.
     * 
     * @return a string containing the digits.
     */
    public String getIgnoreDigits() {
        return collectionToString(ignoreDigits);
    }

    /**
     * Returns the digits used to deleted the collected digit from the collection.
     * 
     * @return a string containing the digits.
     */
    public String getBackspaceDigits() {
        return collectionToString(backspaceDigits);
    }

    /**
     * Returns the digits used to validate the collection. If the flushChar ECMA3
     * standard parameter is present, the termDigits parameter is ignored by the
     * OmniPCX Enterprise.
     * 
     * @return a string containing the digits.
     */
    public String getTermDigits() {
        return collectionToString(termDigits);
    }

    /**
     * Returns the digits used to restart the collection.
     * 
     * @return a string containing the digits.
     */
    public String getResetDigits() {
        return collectionToString(resetDigits);
    }

    /**
     * Remove the specified digit from this digit collection criteria. If the
     * specifed parameter is not a valid digit, this method does nothing.
     * 
     * @param digit the digit.
     */
    public void removeDigit(char digit) {
        if (mapDigits.containsKey(digit)) {

            Type typeDigit = mapDigits.get(digit);
            switch (typeDigit) {
            case ABORT:
                abortDigits.remove(digit);
                break;

            case IGNORE:
                ignoreDigits.remove(digit);
                break;

            case BACKSPACE:
                backspaceDigits.remove(digit);
                break;

            case TERM:
                termDigits.remove(digit);
                break;

            case RESET:
                resetDigits.remove(digit);
                break;

            default:
                break;
            }
        }
    }

    private static String collectionToString(Collection<Character> col) {

        StringBuilder sb = new StringBuilder();
        for (Character c : col) {
            sb.append(c);
        }

        return sb.toString();
    }

    private void addDigit(char digit, Type type, Collection<Character> col, String strComment) {
        if (!mapDigits.containsKey(digit)) {
            throw new IllegalArgumentException(String.format("%c is not a valid digit", digit));
        }

        Type typeDigit = mapDigits.get(digit);
        if (typeDigit != Type.NONE) {
            throw new IllegalArgumentException(String.format("%c is already used as %s", digit, typeDigit.toString()));
        }

        if (col.size() >= 2) {
            throw new IllegalArgumentException(
                    String.format("Too many %s digits: %s", strComment, collectionToString(col)));
        }

        col.add(digit);
        mapDigits.put(digit, type);
    }

    /**
     * Add the specified digit in the list of abort digits.
     * 
     * @param digit the digit to add.
     * @throws IllegalArgumentException if the parameter is not a valid digit or if
     *                                  the digit is already used or if the
     *                                  collection is full.
     */
    public void addAbortDigit(char digit) {
        addDigit(digit, Type.ABORT, abortDigits, "abort");
    }

    /**
     * Add the specified digit in the list of backspace digits.
     * 
     * @param digit the digit to add.
     * @throws IllegalArgumentException if the parameter is not a valid digit or if
     *                                  the digit is already used or if the
     *                                  collection is full.
     */
    public void addBackspaceDigit(char digit) {
        addDigit(digit, Type.BACKSPACE, backspaceDigits, "backspace");
    }

    /**
     * Add the specified digit in the list of ignore digits.
     * 
     * @param digit the digit to add.
     * @throws IllegalArgumentException if the parameter is not a valid digit or if
     *                                  the digit is already used or if the
     *                                  collection is full.
     */
    public void addIgnoreDigit(char digit) {
        addDigit(digit, Type.IGNORE, ignoreDigits, "ignore");
    }

    /**
     * Add the specified digit in the list of term digits.
     * 
     * @param digit the digit to add.
     * @throws IllegalArgumentException if the parameter is not a valid digit or if
     *                                  the digit is already used or if the
     *                                  collection is full.
     */
    public void addTermDigit(char digit) {
        addDigit(digit, Type.TERM, termDigits, "term");
    }

    /**
     * Add the specified digit in the list of reset digits.
     * 
     * @param digit the digit to add.
     * @throws IllegalArgumentException if the parameter is not a valid digit or if
     *                                  the digit is already used or if the
     *                                  collection is full.
     */
    public void addResetDigit(char digit) {
        addDigit(digit, Type.RESET, resetDigits, "reset");
    }

    /**
     * Reset this digit collection criteria.
     */
    public void clear() {
        mapDigits.put('0', Type.NONE);
        mapDigits.put('1', Type.NONE);
        mapDigits.put('2', Type.NONE);
        mapDigits.put('3', Type.NONE);
        mapDigits.put('4', Type.NONE);
        mapDigits.put('5', Type.NONE);
        mapDigits.put('6', Type.NONE);
        mapDigits.put('7', Type.NONE);
        mapDigits.put('8', Type.NONE);
        mapDigits.put('9', Type.NONE);
        mapDigits.put('*', Type.NONE);
        mapDigits.put('#', Type.NONE);

        abortDigits.clear();
        backspaceDigits.clear();
        resetDigits.clear();
        termDigits.clear();
        ignoreDigits.clear();
    }

    /**
     * Returns the start timeout.
     * 
     * @return the start timeout.
     */
    public final int getStartTimeout() {
        return startTimeout;
    }

    /**
     * Return the digit timeout.
     * 
     * @return the digitTimeout.
     */
    public final int getDigitTimeout() {
        return digitTimeout;
    }

}
