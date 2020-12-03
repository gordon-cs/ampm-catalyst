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
public class Preventive {
    // The private values for this object, each one represents a col in the DB

    private String clientID;
    private String type;
    private Date dateDone;
    private String prescribedBy;
    private Date nextDueDate;
    private String source;
    private String frequency;

    Preventive(String clientID, String type, Date dateDone, String prescribedBy, Date nextDueDate, String source, String frequency) {
        this.clientID = clientID;
        this.type = type;
        this.dateDone = dateDone;
        this.prescribedBy = prescribedBy;
        this.nextDueDate = nextDueDate;
        this.source = source;
        this.frequency = frequency;
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
     * @param
     */
    public void updateDateDone(Date dateDone) {
        this.dateDone = dateDone;
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
    public void updateNextDueDate(Date nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    /**
     * Update the use of medical equipment.
     *
     * @param usedFor
     */
    public void updateSource(String source) {
        this.nextDueDate = nextDueDate;
    }

    /**
     * Update the use of medical equipment.
     *
     * @param usedFor
     */
    public void updateFrequency(String frequency) {
        this.frequency = frequency;
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
    public Date getDateDone() {
        return dateDone;
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
     * Returns the relation of relative
     *
     * @return
     */
    public Date getNextDueDate() {
        return nextDueDate;
    }

    /**
     * Returns the description of Alert
     *
     * @return
     */
    public String getSource() {
        return source;
    }

    /**
     * Returns the relation of relative
     *
     * @return
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Generates the sql string that can be used to insert this client info into
     * the Providers table
     *
     * @return
     */
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Preventive (ClientID,) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.dateDone + "','"
                + this.prescribedBy + "'+'"
                + this.nextDueDate + "'+'"
                + this.source + "'+'"
                + this.frequency + "')";
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
                + "DateDone='" + this.dateDone + "', "
                + "PrescribedBy='" + this.prescribedBy + "', "
                + "NextDueDate='" + this.nextDueDate + "', "
                + "Source='" + this.source + "', "
                + "Frequency='" + this.frequency + "'"
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
