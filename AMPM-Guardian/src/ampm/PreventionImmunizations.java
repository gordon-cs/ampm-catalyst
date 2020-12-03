/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.util.Date;

/**
 *
 * @author john.zhu
 */
public class PreventionImmunizations {
    // The private values for this object, each one represents a col in the DB

    private String clientID;
    private String type;
    private String name;
    private Date dateGiven;
    private String whereGiven;

    PreventionImmunizations(String clientID, String type, String name, Date dateGiven, String whereGiven) {
        this.clientID = clientID;
        this.type = type;
        this.name = name;
        this.dateGiven = dateGiven;
        this.whereGiven = whereGiven;
    }

    /**
     * Update type of medical equipment.
     *
     * @param type
     */
    public void updateType(String type) {
        this.type = type;
    }

    /**
     * Update who prescribed medical equipment.
     *
     * @param prescribedBy
     */
    public void updateName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of Alert
     *
     * @return
     */
    public void updateDateGiven(Date dateGiven) {
        this.dateGiven = dateGiven;
    }

    /**
     * Update the use of medical equipment.
     *
     * @param
     */
    public void updatewhereGiven(String whereGiven) {
        this.whereGiven = whereGiven;
    }

    /**
     * Returns the diagnosis of client
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the relation of relative
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of Alert
     *
     * @return
     */
    public Date getDateGiven() {
        return dateGiven;
    }

    /**
     * Returns the relation of relative
     *
     * @return
     */
    public String getWhereGiven() {
        return whereGiven;
    }

    /**
     * Generates the sql string that can be used to insert this client info into
     * the Providers table
     *
     * @return
     */
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Prevention (ClientID,) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.name + "'+'"
                + this.dateGiven + "'+'"
                + this.whereGiven + "')";
        return sqlInsert;
    }

    /**
     * Generates the sql string that can be used to update this provide info
     * into the Provide table
     *
     * @return
     */
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Prevention SET Type='" + this.type + "',"
                + "Name='" + this.name + "', "
                + "DateGiven='" + this.dateGiven + "', "
                + "whereGiven='" + this.whereGiven + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Prevention WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }
}
