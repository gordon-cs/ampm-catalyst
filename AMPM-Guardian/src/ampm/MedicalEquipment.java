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

    MedicalEquipment(String clientID) {
        this.clientID = clientID;
        ;
    }

    // Update type of medical equipment
    public void updateType(String type) {
        this.type = type;
    }

    // Update who prescribed medical equipment
    public void updatePrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
    }

    // Update the use of medical equipment
    public void updateUsedFor(String usedFor) {
        this.usedFor = usedFor;
    }

    // Returns the date that medical equipemnt start used
    public void updateStartDate(Date startDate) {
        this.startDate = startDate;
    }

    // Update the notes of medical equipment
    public void updateNote(String notes) {
        this.notes = notes;
    }

    // Returns the type of medical equipment.
    public String getType() {
        return type;
    }

    // Returns who prescribed medical equipment
    public String getPrescribedBy() {
        return prescribedBy;
    }

    // Returns the medical equipment used for
    public String getUsedFor() {
        return usedFor;
    }

    // Returns the date that medical equipemnt start used
    public Date getStartDate() {
        return startDate;
    }

    //Returns the notes about medical equipment
    public String getNotes() {
        return notes;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Medical Equipment table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.MedicalEquipment (ClientID, EquipType, PrescribedBy, UsedFor, StartDate, Notes) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.prescribedBy + "','"
                + this.usedFor + "','"
                + this.startDate + "','"
                + this.notes + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.MedicalEquipment SET EquipType='" + this.type + "',"
                + "PrescribedBy='" + this.prescribedBy + "', "
                + "UsedFor='" + this.usedFor + "', "
                + "StartDate='" + this.usedFor + "', "
                + "Notes='" + this.startDate + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.MedicalEquipment WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }

}
