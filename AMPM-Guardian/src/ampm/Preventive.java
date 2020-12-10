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

    Preventive(String clientID) {
        this.clientID = clientID;
    }

    // Update type of preventive
    public void updateType(String type) {
        this.type = type;
    }

    // Update the date done of preventive
    public void updateDateDone(Date dateDone) {
        this.dateDone = dateDone;
    }

    // Update who prescribed client this preventive
    public void updatePrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
    }

    // Update the next due date
    public void updateNextDueDate(Date nextDueDate) {
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
    public Date getDateDone() {
        return dateDone;
    }

    // Returns who prescribed client this preventive
    public String getPrescribedBy() {
        return prescribedBy;
    }

    // Returns the next due date
    public Date getNextDueDate() {
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
                + "PrescribedBy, Source, Frequency) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                //+ null + "','"
                + this.prescribedBy + "','"
                //+ null + "','"
                + this.source + "','"
                + this.frequency + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Preventive table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Preventive SET PreventiveType='" + this.type + "',"
                //+ "DateDone='" + "0000-00-00" + "', "
                + "PrescribedBy='" + this.prescribedBy + "', "
                //+ "NextDueDate='" + "0000-00-00" + "', "
                + "Source='" + this.source + "', "
                + "Frequency='" + this.frequency + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Preventive WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }
}
