package ampm;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * This class represents a single client in the system. The client is not
 * dependent of the database (doesn't matter if it's stored online or offline).
 *
 * @author benab
 */
public class Client {

    // The private values for this object, each one represents a col in the DB
    private String clientID;
    private String oldClientID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String lastModified;
    private String cell;
    private String DOB;

    Client(String firstName, String lastName, String email, String phone, String lastModified, String cell, String DOB) {
        this.clientID = DOB.replace("-", "") + firstName.toUpperCase().charAt(0) + lastName.toUpperCase().charAt(0);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.lastModified = lastModified;
        this.cell = cell;
        this.DOB = DOB;

    }

    Client(String clientID, String firstName, String lastName, String email, String phone, String lastModified, String cell, String DOB) {
        this.clientID = clientID;
        if (!clientID.equals(DOB.replace("-", "") + firstName.toUpperCase().charAt(0) + lastName.toUpperCase().charAt(0))) {
            this.oldClientID = clientID;
            this.clientID = DOB.replace("-", "") + firstName.toUpperCase().charAt(0) + lastName.toUpperCase().charAt(0);
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.lastModified = lastModified;
        this.cell = cell;
        this.DOB = DOB;
    }

    Client(String clientID, String firstName, String lastName) {
        this.clientID = clientID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    Client(String clientID) {
        this.clientID = clientID;
    }

    /**
     * Generates the sql string that can be used to insert this client into the
     * Client table
     *
     * @return
     */
    public String getSQLInsert() {
        String sqlInsert;

        if ("".equals(cell) || cell == null) {
            sqlInsert = "INSERT INTO AMPM.Client (ClientID, FirstName, LastName, Email, Phone, Cell, LastModified, DOB) Values ('"
                    + this.clientID + "','"
                    + this.firstName + "', '"
                    + this.lastName + "', '"
                    + this.email + "', '"
                    + this.phone + "', NULL, '"
                    + this.lastModified + "', '"
                    + this.DOB + "')";
        } else {
            sqlInsert = "INSERT INTO AMPM.Client (ClientID, FirstName, LastName, Email, Phone, Cell, LastModified, DOB) Values ('"
                    + this.clientID + "','"
                    + this.firstName + "', '"
                    + this.lastName + "', '"
                    + this.email + "', '"
                    + this.phone + "', '"
                    + this.cell + "', '"
                    + this.lastModified + "', '"
                    + this.DOB + "')";
        }
        return sqlInsert;
    }

    /**
     * Generates the sql string that can be used to update this client into the
     * Client table.
     *
     * @return The string that contains the SQL for updating client info.
     */
    public String getSQLUpdate() {
        String sqlUpdate;

        if ("".equalsIgnoreCase(cell) || cell == null) {
            sqlUpdate = "UPDATE AMPM.Client SET FirstName ='" + this.firstName + "', "
                    + "LastName ='" + this.lastName + "', "
                    + "Email ='" + this.email + "', "
                    + "Phone ='" + this.phone + "', "
                    + "LastModified ='" + this.lastModified + "', "
                    + "Cell = NULL ,"
                    + "DOB='" + this.DOB + "'"
                    + "WHERE ClientID ='" + this.clientID + "'";
        } else {
            sqlUpdate = "UPDATE AMPM.Client SET FirstName ='" + this.firstName + "', "
                    + "LastName ='" + this.lastName + "', "
                    + "Email ='" + this.email + "', "
                    + "Phone ='" + this.phone + "', "
                    + "LastModified ='" + this.lastModified + "', "
                    + "Cell ='" + this.cell + "', "
                    + "DOB='" + this.DOB + "'"
                    + "WHERE ClientID ='" + this.clientID + "'";
        }
        if (!this.oldClientID.isEmpty()) {
            sqlUpdate = "UPDATE AMPM.Client SET ClientID ='" + this.clientID + "', "
                    + "FirstName ='" + this.firstName + "', "
                    + "LastName ='" + this.lastName + "', "
                    + "Email ='" + this.email + "', "
                    + "Phone ='" + this.phone + "', "
                    + "LastModified ='" + this.lastModified + "', "
                    + "Cell = NULL ,"
                    + "DOB='" + this.DOB + "'"
                    + "WHERE ClientID ='" + this.oldClientID + "'";
        }
        return sqlUpdate;
    }

    /**
     * Generates the sql string that can be used to select this client from the
     * Client table. Useful for checking to see if it already exists in the
     * database
     *
     * @return The string that contains the SQL for selecting itself.
     */
    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Client WHERE FirstName ='" + this.firstName + "' AND "
                + "LastName ='" + this.lastName + "' AND "
                + "Email ='" + this.email + "' AND "
                + "Phone ='" + this.phone + "'";
        //System.out.println(sqlSelect);
        return sqlSelect;
    }

    public String getByName() {
        String sqlSelect = "SELECT * FROM AMPM.Client WHERE FirstName ='" + this.firstName + "' AND "
                + "LastName ='" + this.lastName + "'";
        //System.out.println(sqlSelect);
        return sqlSelect;
    }

    public String getByID() {
        String sqlSelect = "SELECT * FROM AMPM.Client WHERE ClientID ='" + this.clientID + "'";
        //System.out.println(sqlSelect);
        return sqlSelect;
    }

    /**
     * Getters + Setters *
     */
    /**
     * Update this objects last modified field. Timestamp is in the format:
     * yyyy-mm-dd hh:mm:ss
     *
     * @param lastModified
     */
    public void updateLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Returns the information of a client
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getID() {
        return clientID;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getCell() {
        return cell;
    }

    public String getDOB() {
        return DOB;
    }

}
