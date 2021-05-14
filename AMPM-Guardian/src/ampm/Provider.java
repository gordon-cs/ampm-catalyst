package ampm;

/**
 * This one is for provide information
 *
 * @author john.zhu
 */
public class Provider {
    // The private values for this object, each one represents a col in the DB

    private String clientID;
    private String type;
    private String providerName;
    private String nurseName;
    private String nameOfPANP;

    Provider(String clientID, String type, String providerName, String nurseName, String nameOfPANP) {
        this.clientID = clientID;
        this.type = type;
        this.providerName = providerName;
        this.nurseName = nurseName;
        this.nameOfPANP = nameOfPANP;
    }

    Provider(String clientID) {
        this.clientID = clientID;
    }

    // Update the type of provide
    public void updateType(String type) {
        this.type = type;
    }

    // Update the name of provider
    public void updateProviderName(String providerName) {
        this.providerName = providerName;
    }

    // Update the nurse name.
    public void updateNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    //Update the PA/NP name.
    public void updateNameOfPANP(String nameOfPANP) {
        this.nameOfPANP = nameOfPANP;
    }

    // Returns the type of provide
    public String getType() {
        return type;
    }

    // Returns the name of provider
    public String getProviderName() {
        return providerName;
    }

    // Returns the name of nurse
    public String getNurseName() {
        return nurseName;
    }

    // Returns the name of PA/NP
    public String getNameOfPANP() {
        return nameOfPANP;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Providers table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Provider (ClientID,Type, Provider, Nurse, PANP) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.providerName + "','"
                + this.nurseName + "','"
                + this.nameOfPANP + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Provider SET Type ='" + this.type + "',"
                + "Provider ='" + this.providerName + "', "
                + "Nurse ='" + this.nurseName + "', "
                + "PANP ='" + this.nameOfPANP + "'"
                + "WHERE ClientID ='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLEdit(String newProvider) {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Provider SET Type ='" + this.type + "',"
                + "Provider ='" + newProvider + "', "
                + "Nurse ='" + this.nurseName + "', "
                + "PANP ='" + this.nameOfPANP + "'"
                + "WHERE ClientID ='" + this.clientID + "'"
                + "AND Provider ='" + this.providerName + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Provider WHERE ClientID ='"
                + this.clientID + "'";

        return sqlSelect;
    }

    public String getSQLSelectByProvider(String provider) {
        String sqlSelect = "SELECT * FROM AMPM.Provider WHERE ClientID  ='" + this.clientID
                + "'AND Provider = '" + provider + "'";
        return sqlSelect;
    }

    public String getProviderType() {
        String sqlSelect = "SELECT distinct Type FROM AMPM.Provider "
                + "WHERE ClientID ='" + this.clientID + "'";
        return sqlSelect;
    }
}
