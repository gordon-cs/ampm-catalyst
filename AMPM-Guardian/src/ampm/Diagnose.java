package ampm;

import java.util.Date;

/**
 * This one is for diagnoses in add client info screen
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

    // Update the description of this diagnose
    public void updateDescription(String type) {
        this.description = description;
    }

    // Update the diagnosed date
    public void updatedDiagnosedDate(Date diagnosedDate) {
        this.diagnosedDate = diagnosedDate;
    }

    // Update the doctor for this client
    public void updateByWho(String bywho) {
        this.byWho = bywho;
    }

    // Returns the description of this diagnose
    public String getDescrption() {
        return description;
    }

    // Returns the diagnosed date
    public Date getDiagnosedDate() {
        return diagnosedDate;
    }

    // Returns the doctor for this client
    public String getByWho() {
        return byWho;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Diagnose table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Diagnose (ClientID,) Values ('"
                + this.clientID + "','"
                + this.description + "'+'"
                + this.diagnosedDate + "'+'"
                + this.byWho + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Diagnose table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Diagnose SET Description='" + this.description + "',"
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