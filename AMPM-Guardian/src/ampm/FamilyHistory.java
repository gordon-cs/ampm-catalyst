package ampm;

/**
 * This one is for family history in add client info screen
 *
 * @author John Zhu
 */
public class FamilyHistory {

    // The private values for this object, each one represents a col in the DB
    private String listOrder;
    private String clientID;
    private String diagnosis;
    private String relation;
    private String exist;
    private String age;

    FamilyHistory(String clientID, String diagnosis, String relation, String exist, String age) {
        this.clientID = clientID;
        this.diagnosis = diagnosis;
        this.relation = relation;
        this.exist = exist;
        this.age = age;
    }

    FamilyHistory(String clientID) {
        this.clientID = clientID;
    }

    // Update diagnosis for client family history
    public void updateDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    // Update relative of client with diagnosis
    public void updateRelative(String relation) {
        this.relation = relation;
    }

    // Update the existence of this relative. if this relative is dead, then give age for this relative
    public void updateExist(String exist, String age) {
        this.exist = exist;
        this.age = age;
    }

    // Returns the diagnosis of client
    public String getDiagnosis() {
        return diagnosis;
    }

    // Returns the relation of relative
    public String getRelative() {
        return relation;
    }

    // Returns the description of Alert
    public String getExist() {
        return exist;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Providers table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.FamilyHistory (ClientID, Diagnoses, Relation, Age, Died) Values ( '"
                + this.clientID + "','"
                + this.diagnosis + "','"
                + this.relation + "','"
                + this.age + "','"
                + this.exist + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.FamilyHistory SET Diagnoses ='" + this.diagnosis + "',"
                + "Relation ='" + this.relation + "', "
                + "Age ='" + this.age + "', "
                + "Died ='" + this.exist + "',"
                + "WHERE ClientID ='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLUpdateNewDiagnoses(String newDiagnoses) {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.FamilyHistory SET Diagnoses ='" + newDiagnoses + "',"
                + "Relation ='" + this.relation + "', "
                + "Age ='" + this.age + "', "
                + "Died ='" + this.exist + "',"
                + "WHERE ClientID ='" + this.clientID + "'"
                + "AND Diagnoses ='" + this.diagnosis + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.FamilyHistory WHERE ClientID ='"
                + this.clientID + "'";

        return sqlSelect;
    }

    public String getSQLSelectByRelative(String relative) {
        String sqlSelect = "SELECT * FROM AMPM.FamilyHistory WHERE ClientID ='" + this.clientID
                + "'AND Relation = '"
                + relative + "'";

        return sqlSelect;
    }
}
