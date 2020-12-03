/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

/**
 *
 * @author john.zhu
 */
public class FamilyHistory {
    // The private values for this object, each one represents a col in the DB

    private String clientID;
    private String diagnosis;
    private String relation;
    private Boolean exist;
    private String age;

    FamilyHistory(String clientID, String diagonoses, String relation, Boolean exist, String age) {
        this.clientID = clientID;
        this.diagnosis = diagnosis;
        this.relation = relation;
        this.exist = exist;
        this.age = age;
    }

    /**
     * Update diagnosis for client family history.
     *
     * @param diagnosis
     */
    public void updateDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Update relative of client with diagnosis.
     *
     * @param relation
     */
    public void updateRelative(String relation) {
        this.relation = relation;
    }

    /**
     * Update the existence of this relative.
     *
     * @param exist
     * @param age
     */
    public void updateExist(Boolean exist, String age) {
        this.exist = exist;
        this.age = age;
    }

    /**
     * Returns the diagnosis of client
     *
     * @return
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Returns the relation of relative
     *
     * @return
     */
    public String getRelative() {
        return relation;
    }

    /**
     * Returns the description of Alert
     *
     * @return
     */
    public Boolean getExist() {
        return exist;
    }

    /**
     * Generates the sql string that can be used to insert this client info into
     * the Providers table
     *
     * @return
     */
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.FamilyHistory (ClientID,) Values ('"
                + this.clientID + "','"
                + this.diagnosis + "','"
                + this.relation + "'+'"
                + this.exist + "'+'"
                + this.age + "')";
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

        sqlUpdate = "UPDATE AMPM.FamilyHistory SET Type='" + this.diagnosis + "',"
                + "Relation='" + this.relation + "', "
                + "Exist='" + this.exist + "', "
                + "Age='" + this.age + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Client WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }

}
