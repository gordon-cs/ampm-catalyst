/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.sql.Date;

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
    private int phone;
    private Date lastModified;
    private int cell;

    public Client(String clientID, String firstName, String lastName, String email, int phone, Date lastModified, int cell) throws Exception {
       
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
    public void updateLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
 
    /**
     * Returns the full name of a client
     * @return 
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    
    /**
     * Generates the sql string that can be used to insert this client
     * into the Client table
     * @return 
     */
    public String getSQLInsert() {
        // TODO: make sure that if, for example, there is no cell phone, we 
        // insert NULL into the sql statement.
        String string = "INSERT INTO Client (ClientID, FirstName, LastName, Email, Phone, Cell, LastModified) Values ('" 
                + this.clientID + "','" 
                + this.firstName + "', '"
                + this.lastName + "', '"
                + this.email + "', '" 
                + this.phone + "', '"
                + this.cell + "', '"
                + this.lastModified + "');";
        
    }
}
