package ampm;

/**
 * This one is for alert in add client info screen
 * @author John Zhu
 */
public class Alert {
    // The private values for this object, each one represents a col in the DB
    private String clientID;
    private String type;
    private String detail;
    private String description;

    Alert(String clientID, String type, String detail, String description) {
        this.clientID = clientID;
        this.type = type;
        this.detail = detail;
        this.description = description;
    }

    // Update type for alert
    public void updateType(String type) {
        this.type = type;
    }

    // Update detail of alert
    public void updateDetail(String detail) {
        this.detail = detail;
    }

    // Update description of alert
    public void updateDescription(String description) {
        this.description = description;
    }

    // Returns the type of provide
    public String getType() {
        return type;
    }

    // Returns the detail of Alert
    public String getDetail() {
        return detail;
    }

    // Returns the description of Alert
    public String getDescription() {
        return description;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Providers table 
    public String getSQLInsert() {
        String sqlInsert;
        
        sqlInsert = "INSERT INTO AMPM.Provider (ClientID,) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.detail + "'+'"
                + this.description + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Client SET Type='" + this.type + "',"
                + "Detail='" + this.detail + "', "
                + "Description='" + this.description + "'"
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