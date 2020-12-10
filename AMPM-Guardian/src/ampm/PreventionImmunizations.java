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
    private Date dateGiven;
    private String whereGiven;

    PreventionImmunizations(String clientID, String type, String name, Date dateGiven, String whereGiven) {
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
    public void updateDateGiven(Date dateGiven) {
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
    public Date getDateGiven() {
        return dateGiven;
    }

    // Returns the place that immunizations given
    public String getWhereGiven() {
        return whereGiven;
    }

    // Generates the sql string that can be used to insert this client info into
    // the Providers table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.PreventionImmunizations (ClientID,) Values ('"
                + this.clientID + "','"
                + this.type + "','"
                + this.name + "'+'"
                + this.dateGiven + "'+'"
                + this.whereGiven + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.PreventionImmunizations SET Type='" + this.type + "',"
                + "Name='" + this.name + "', "
                + "DateGiven='" + this.dateGiven + "', "
                + "whereGiven='" + this.whereGiven + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.PreventionImmunizations WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }
}
