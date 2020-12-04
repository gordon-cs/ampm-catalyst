package ampm;

/**
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

        sqlInsert = "INSERT INTO AMPM.Provider (ClientID,) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.providerName + "'+'"
                + this.nurseName + "'+'"
                + this.nameOfPANP + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Client SET Type='" + this.type + "',"
                + "ProvideName='" + this.providerName + "', "
                + "NurseName='" + this.nurseName + "', "
                + "NameOfPANP='" + this.nameOfPANP + "'"
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