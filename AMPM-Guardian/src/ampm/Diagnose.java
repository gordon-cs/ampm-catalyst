package ampm;

import java.util.Date;

/**
 * This one is for diagnosis information
 *
 * @author john.zhu
 */
public class Diagnose {

    // The private values for this object, each one represents a col in the DB
    private String clientID;
    private String description;
    private String diagnosedDate;
    private String byWho;

    Diagnose(String clientID, String description, String diagnosedDate, String byWho) {
        this.clientID = clientID;
        this.description = description;
        this.diagnosedDate = diagnosedDate;
        this.byWho = byWho;
    }

    Diagnose(String clientID) {
        this.clientID = clientID;
    }

    // Update the description of this diagnose
    public void updateDescription(String type) {
        this.description = description;
    }

    // Update the diagnosed date
    public void updatedDiagnosedDate(String diagnosedDate) {
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
    public String getDiagnosedDate() {
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

        sqlInsert = "INSERT INTO AMPM.Diagnoses (ClientID, Diagnosis, StartDate, DiagnosedBy) Values ('"
                + this.clientID + "','"
                + this.description + "','"
                + this.diagnosedDate + "','"
                + this.byWho + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Diagnose table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Diagnoses SET Diagnosis = '" + this.description + "',"
                + "StartDate = '" + this.diagnosedDate + "', "
                + "DiagnosedBy = '" + this.byWho + "'"
                + "WHERE ClientID = '" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLUpdateNewItem(String newItem) {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Diagnoses SET Diagnosis = '" + newItem + "',"
                + "StartDate = '" + this.diagnosedDate + "', "
                + "DiagnosedBy = '" + this.byWho + "'"
                + "WHERE ClientID = '" + this.clientID + "'"
                + "AND Diagnosis = '" + this.description + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Diagnoses WHERE ClientID = '"
                + this.clientID + "'";

        return sqlSelect;
    }

    public String getSQLSelectByDiagnosis(String diagnosis) {
        String sqlSelect = "SELECT * FROM AMPM.Diagnoses WHERE ClientID  = '" + this.clientID
                + "'AND Diagnosis = '" + diagnosis + "'";

        return sqlSelect;
    }
}
