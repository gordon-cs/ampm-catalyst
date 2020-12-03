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
public class Diagnose {
    // The private values for this object, each one represents a col in the DB

    private String clientID;
    private String description;
    private Date diagnosedDate;
    private String byWho;

    Diagnose(String clientID, String description, Date diagnosedDate, String byWho) {
        this.clientID = clientID;
        this.description = description;
        this.diagnosedDate = diagnosedDate;
        this.byWho = byWho;
    }

    /**
     * Update type of medical equipment.
     *
     * @param type
     */
    public void updateDescription(String type) {
        this.description = description;
    }

    /**
     * Update who prescribed medical equipment.
     *
     * @param prescribedBy
     */
    public void updatedDiagnosedDate(Date diagnosedDate) {
        this.diagnosedDate = diagnosedDate;
    }

    /**
     * Update the use of medical equipment.
     *
     * @param
     */
    public void updateByWho(String bywho) {
        this.byWho = bywho;
    }

    /**
     * Returns the diagnosis of client
     *
     * @return
     */
    public String getDescrption() {
        return description;
    }

    /**
     * Returns the description of Alert
     *
     * @return
     */
    public Date getDiagnosedDate() {
        return diagnosedDate;
    }

    /**
     * Returns the relation of relative
     *
     * @return
     */
    public String getByWho() {
        return byWho;
    }

    /**
     * Generates the sql string that can be used to insert this client info into
     * the Providers table
     *
     * @return
     */
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Diagnose (ClientID,) Values ('"
                + this.clientID + "','"
                + this.description + "'+'"
                + this.diagnosedDate + "'+'"
                + this.byWho + "')";
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

        sqlUpdate = "UPDATE AMPM.Prevention SET Description='" + this.description + "',"
                + "DiagnosedDate='" + this.diagnosedDate + "', "
                + "ByWho='" + this.byWho + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Diagnose WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }
}
