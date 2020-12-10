package ampm;

/**
 *
 * @author John Zhu
 */
public class Monitor {

    private String clientID;
    private String reason;
    private String specific;

    Monitor(String clientID, String reason, String specific) {
        this.clientID = clientID;
        this.reason = reason;
        this.specific = specific;

    }

    Monitor(String clientID) {
        this.clientID = clientID;
    }

    // Update the reason for monitor
    public void updateReason(String reason) {
        this.reason = reason;
    }

    // Update the specific type for monitor
    public void updateSpecific(String specific) {
        this.specific = specific;
    }

    // Returns the reason for monitor
    public String getReason() {
        return reason;
    }

    // Returns the specific type for monitor
    public String getSpecific() {
        return specific;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Providers table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Monitor (ClientID, For, Specific) Values ('"
                + this.clientID + "','"
                + this.reason + "','"
                + this.specific + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Monitor SET For='" + this.reason + "',"
                + "Specific='" + this.specific + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Monitor WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }
}
