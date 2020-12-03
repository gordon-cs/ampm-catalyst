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
public class MedicalEquipment {
    // The private values for this object, each one represents a col in the DB

    private String clientID;
    private String type;
    private String prescribedBy;
    private String usedFor;
    private Date startDate;
    private String notes;

    MedicalEquipment(String clientID, String type, String prescribedBy, String usedFor, Date startDate, String notes) {
        this.clientID = clientID;
        this.type = type;
        this.prescribedBy = prescribedBy;
        this.usedFor = usedFor;
        this.startDate = startDate;
        this.notes = notes;
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
    public void updatePrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
    }

    /**
     * Update the use of medical equipment.
     *
     * @param usedFor
     */
    public void updateUsedFor(String usedFor) {
        this.usedFor = usedFor;
    }

     /**
     * Returns the description of Alert
     *
     * @return
     */
    public void updateStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /**
     * Update the use of medical equipment.
     *
     * @param usedFor
     */
    public void updateNote(String notes) {
        this.notes = notes;
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
    public String getPrescribedBy() {
        return prescribedBy;
    }

    /**
     * Returns the description of Alert
     *
     * @return
     */
    public String getUsedFor() {
        return usedFor;
    }

    /**
     * Returns the description of Alert
     *
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns the relation of relative
     *
     * @return
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Generates the sql string that can be used to insert this client info into
     * the Providers table
     *
     * @return
     */
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.MedicalEquipment (ClientID,) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.prescribedBy + "'+'"
                + this.usedFor + "'+'"
                + this.startDate + "'+'"
                + this.notes + "')";
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

        sqlUpdate = "UPDATE AMPM.MedicalEquipment SET Type='" + this.type + "',"
                + "PrescribedBy='" + this.prescribedBy + "', "
                + "UsedFor='" + this.usedFor + "', "
                + "StartDate='" + this.usedFor + "', "
                + "Notes='" + this.startDate + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.MedicalEqui WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }

}
