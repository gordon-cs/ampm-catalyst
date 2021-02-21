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
    private String dateDone;
    private String prescribedBy;
    private String nextDueDate;
    private String source;
    private String frequency;

    Preventive(String clientID, String type, String dateDone, String prescribedBy, String nextDueDate, String source, String frequency) {
        this.clientID = clientID;
        this.type = type;
        this.dateDone = dateDone;
        this.prescribedBy = prescribedBy;
        this.nextDueDate = nextDueDate;
        this.source = source;
        this.frequency = frequency;
    }

    Preventive(String clientID) {
        this.clientID = clientID;
    }

    // Update type of preventive
    public void updateType(String type) {
        this.type = type;
    }

    // Update the date done of preventive
    public void updateDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    // Update who prescribed client this preventive
    public void updatePrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
    }

    // Update the next due date
    public void updateNextDueDate(String nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    // Update the source
    public void updateSource(String source) {
        this.nextDueDate = nextDueDate;
    }

    // Update the frequency
    public void updateFrequency(String frequency) {
        this.frequency = frequency;
    }

    // Returns the type of preventive
    public String getType() {
        return type;
    }

    // Returns the date done of preventive
    public String getDateDone() {
        return dateDone;
    }

    // Returns who prescribed client this preventive
    public String getPrescribedBy() {
        return prescribedBy;
    }

    // Returns the next due date
    public String getNextDueDate() {
        return nextDueDate;
    }

    // Returns the source
    public String getSource() {
        return source;
    }

    // Returns the frequency
    public String getFrequency() {
        return frequency;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Preventive table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Preventive (ClientID, PreventiveType, "
                + "DateDone, PrescribedBy, NextDueDate, Source, Frequency) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.dateDone + "','"
                + this.prescribedBy + "','"
                + this.nextDueDate + "','"
                + this.source + "','"
                + this.frequency + "')";
        System.out.println(sqlInsert);
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Preventive table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Preventive SET PreventiveType='" + this.type + "',"
                + "DateDone='" + this.dateDone + "', "
                + "PrescribedBy='" + this.prescribedBy + "', "
                + "NextDueDate='" + this.nextDueDate + "', "
                + "Source='" + this.source + "', "
                + "Frequency='" + this.frequency + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLUpdateNewType(String newType) {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Preventive SET PreventiveType='" + newType + "',"
                + "DateDone='" + this.dateDone + "', "
                + "PrescribedBy='" + this.prescribedBy + "', "
                + "NextDueDate='" + this.nextDueDate + "', "
                + "Source='" + this.source + "', "
                + "Frequency='" + this.frequency + "'"
                + "WHERE ClientID='" + this.clientID + "'"
                + "AND PreventiveType ='" + this.type + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Preventive WHERE ClientID='"
                + this.clientID + "'";

        return sqlSelect;
    }

    public String getSQLSelectByType(String preventiveType) {
        String sqlSelect = "SELECT * FROM AMPM.Preventive WHERE ClientID ='" + this.clientID 
                + "'AND PreventiveType = '" + preventiveType + "'";
        
        return sqlSelect;
    }
}
