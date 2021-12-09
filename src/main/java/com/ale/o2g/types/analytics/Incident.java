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
package com.ale.o2g.types.analytics;

import java.util.Date;

/**
 * {@code Incident} class represents an incident on an OmniPCX Enterprise node.
 */
public class Incident {

    private int id;
    private Date date;
    private int severity;
    private String description;
    private int nbOccurs;
    private int node;
    private boolean main;
    private String rack;
    private String board;
    private String equipement;
    private String termination;

    /**
     * Returns the incident identifier.
     * 
     * @return the id
     */
    public final int getId() {
        return id;
    }

    /**
     * Returns the date the incident has been raised.
     * 
     * @return the date
     */
    public final Date getDate() {
        return date;
    }

    /**
     * Returns the incident severity.
     * 
     * @return the severity
     */
    public final int getSeverity() {
        return severity;
    }

    /**
     * Returns the textual description of this incident.
     * 
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Returns the number of occurences of this incident.
     * 
     * @return the nbOccurs
     */
    public final int getNbOccurs() {
        return nbOccurs;
    }

    /**
     * Returns the OmniPCX Enterprise node on which this incident has been raised.
     * 
     * @return the OmniPCX Enterprise node
     */
    public final int getNode() {
        return node;
    }

    /**
     * Returns whether this incident has been raised on the main OmniPCX Enterprise
     * call server.
     * 
     * @return {@code true} if the incident has been raised on the main call server;
     *         {@code false} otherwise.
     */
    public final boolean isMain() {
        return main;
    }

    /**
     * Returns the rack related to this incident.
     * 
     * @return the rack
     */
    public final String getRack() {
        return rack;
    }

    /**
     * Returns the board related to this incident.
     * 
     * @return the board
     */
    public final String getBoard() {
        return board;
    }

    /**
     * Returns the equipement related to this incident.
     * 
     * @return the equipement
     */
    public final String getEquipement() {
        return equipement;
    }

    /**
     * Returns the termination related to this incident.
     * 
     * @return the termination
     */
    public final String getTermination() {
        return termination;
    }

    protected Incident(int id, Date date, int severity, String description, int nbOccurs, int node, boolean main,
            String rack, String board, String equipement, String termination) {
        this.id = id;
        this.date = date;
        this.severity = severity;
        this.description = description;
        this.nbOccurs = nbOccurs;
        this.node = node;
        this.main = main;
        this.rack = rack;
        this.board = board;
        this.equipement = equipement;
        this.termination = termination;
    }

}
