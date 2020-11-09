/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/** This class handles the offline database (a local Apache Derby instance packaged
 * alongside the .jar).
 *
 * @author benab
 */
public class DBOffline implements Serializable {
    private static DBOffline dbOffline = null;
    private static Connection conn;
    private static Statement stmt;

    /** Creates a new instance of DBOffline */
    public DBOffline() {
        
    }
    
    // static method to create/get singleton instance of DBOffline class 
    public static DBOffline getInstance() {
      
        if (dbOffline == null) 
            dbOffline = new DBOffline(); 
  
        return dbOffline; 
    } 
    
    public static Boolean init(String user, String password) {
        Boolean success = true;
        
        Properties connectionProps = new Properties();
        connectionProps.put("user", "ampmadmin"); // Replace these when done testing
        connectionProps.put("password", "thepasswordispassword");
        
        // Setup the URL the db is located at
        String dbURL = "jdbc:derby://localhost:1527/ampm-database-local/";
        
        try {
            // forname needs to be called once to establish the driver.
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // connect to the DB using the url and props
            conn = DriverManager.getConnection(dbURL, connectionProps);
            stmt = conn.createStatement();
        }
        catch(Exception e){
            success = false;
            System.out.println("Failed to get connection");
            e.printStackTrace();
        }
        return success; 
    }
    
    public static ResultSet getClients() throws SQLException {
        return stmt.executeQuery("Select  FirstName, LastName, LastModified from AMPM.Client ORDER BY LastModified DESC");
    } 
    
    public void addNewClients(String firstName, String lastName,
            String emailAddress, String phoneNumber, String date, String cellPhoneNumber) throws SQLException {

        stmt.executeUpdate("INSERT INTO Client (FirstName, LastName, Email,"
                + " Phone, LastModified, Cell) VALUES ('" + firstName + "','" + lastName
                + "','" + emailAddress + "','" + phoneNumber + "','" + date + "','" + cellPhoneNumber + "')");

    }
    
    /** Should download any new data from the remote database and upload
     *  any data from the local database.
     * 
     * @throws SQLException 
     */
//    public static void sync() throws SQLException {
//        // Check if the remote DB is accessible
//        if (!DBConnection.isOnline()) {
//            throw new ConnectionException("No connection");
//        }
//        
//    }
    
}
