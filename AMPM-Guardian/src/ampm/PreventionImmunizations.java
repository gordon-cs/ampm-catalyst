package ampm;

import java.util.Date;

/**
 * This one is for prevention immunizations in add client info screen
 *
 * @author john.zhu
 */
public class PreventionImmunizations {
    // The private values for this object, each one represents a col in the DB

    private String clientID;
    private String type;
    private String name;
    private String dateGiven;
    private String whereGiven;

    PreventionImmunizations(String clientID, String type, String name, String dateGiven, String whereGiven) {
        this.clientID = clientID;
        this.type = type;
        this.name = name;
        this.dateGiven = dateGiven;
        this.whereGiven = whereGiven;
    }

    PreventionImmunizations(String clientID) {
        this.clientID = clientID;
    }

    // Update the type of preventive immunization
    public void updateType(String type) {
        this.type = type;
    }

    // Update the name of preventive immunization for 
    public void updateName(String name) {
        this.name = name;
    }

    // Update the date that immunizations given
    public void updateDateGiven(String dateGiven) {
        this.dateGiven = dateGiven;
    }

    // Update the place that immunizations given
    public void updatewhereGiven(String whereGiven) {
        this.whereGiven = whereGiven;
    }

    // Returns the type of preventive immunization
    public String getType() {
        return type;
    }

    // Returns the name of preventive immunization for 
    public String getName() {
        return name;
    }

    // Returns the date that immunizations given
    public String getDateGiven() {
        return dateGiven;
    }

    // Returns the place that immunizations given
    public String getWhereGiven() {
        return whereGiven;
    }

    // Generates the sql string that can be used to insert this client info into
    // the PreventionImmunizations table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.PreventionImmunizations (ClientID, Type, Name, DateGiven, whereGiven) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.name + "','"
                + this.whereGiven + "','"
                + this.whereGiven + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this client info
    // into the PreventionImmunizations table
    public String getSQLUpdate() {
        String sqlUpdate = "UPDATE AMPM.PreventionImmunizations SET Type='" + this.type + "',"
                + "Name='" + this.name + "', "
                + "DateGiven='" + this.dateGiven + "', "
                + "whereGiven='" + this.whereGiven + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLUpdateNewItem(String newItem) {
        String sqlUpdate = "UPDATE AMPM.PreventionImmunizations SET Type='" + this.type + "',"
                + "Name='" + newItem + "', "
                + "DateGiven='" + this.dateGiven + "', "
                + "whereGiven='" + this.whereGiven + "'"
                + "WHERE ClientID='" + this.clientID + "'"
                + "AND Name='" + this.name + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.PreventionImmunizations WHERE ClientID='"
                + this.clientID + "'";

        return sqlSelect;
    }
}
