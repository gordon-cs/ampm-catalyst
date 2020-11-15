/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.sql.Timestamp;
//import java.text.SimpleDateFormat;

/** This class represents a single client in the system
 *
 * @author benab
 */
public class Client {
    
    // The private values for this object, each one represents a col in the DB
    private String clientID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Timestamp lastModified;
    private String cell;
    
    Client(String clientID, String firstName, String lastName, String email, String phone, Timestamp lastModified, String cell) {
       
        this.clientID = clientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.lastModified = lastModified;
        this.cell = cell;
        
        // Should throw an exception if any of the values are 'bad'
    }
    
    /**
     * Update this objects last modified field.
     * @param lastModified 
     */
    public void updateLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
 
    /**
     * Returns the full name of a client
     * @return 
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getID() {
        return clientID;
    }
    
    public Timestamp getLastModified() {
        return lastModified;
    }
    
    
    /**
     * Generates the sql string that can be used to insert this client
     * into the Client table
     * @return 
     */
    public String getSQLInsert() {
        String sqlInsert;
        
        if ("".equals(cell)) {
            sqlInsert = "INSERT INTO AMPM.Client (ClientID, FirstName, LastName, Email, Phone, Cell, LastModified) Values (" 
                + this.clientID + ",'" 
                + this.firstName + "', '"
                + this.lastName + "', '"
                + this.email + "', '" 
                + this.phone + "', NULL, '"
                + this.lastModified + "')";
        }
        else {
        sqlInsert = "INSERT INTO AMPM.Client (ClientID, FirstName, LastName, Email, Phone, Cell, LastModified) Values (" 
                + this.clientID + ",'" 
                + this.firstName + "', '"
                + this.lastName + "', '"
                + this.email + "', '" 
                + this.phone + "', '"
                + this.cell + "', '"
                + this.lastModified + "')";   
        }
        return sqlInsert;
    }
    
    /**
     * Generates the sql string that can be used to update this client
     * into the Client table
     * @return 
     */
    public String getSQLUpdate() {
        String sqlUpdate;
        
        sqlUpdate = "UPDATE AMPM.Client SET FirstName=\"" + this.firstName + "\", "
                    + "LastName=\"" + this.lastName + "\", "
                    + "Email=\"" + this.email + "\", "
                    + "Phone=\"" + this.phone + "\", "
                    + "LastModified=\"" + this.lastModified +"\", "
                    + "Cell=\"" + this.cell +"\" "
                    + "WHERE ClientID=" + this.clientID +"";
    
        return sqlUpdate;
    }
    
}
