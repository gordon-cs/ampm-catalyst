/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void init(String user, String password) {
        
        Properties connectionProps = new Properties();
        connectionProps.put("user", user); // Replace these when done testing
        connectionProps.put("password", password);
        
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
            System.out.println("Failed to get connection");
            e.printStackTrace();
        }
    }
    
    private static ResultSet getClients() throws SQLException {
        return stmt.executeQuery("Select  FirstName, LastName, LastModified from AMPM.Client ORDER BY LastModified DESC");
    } 
    
    public void addNewClients(String firstName, String lastName,
                              String emailAddress, String phoneNumber, 
                              Timestamp date, String cellPhoneNumber) throws SQLException {

        stmt.executeUpdate("INSERT INTO AMPM.Client (FirstName, LastName, Email,"
                + " Phone, LastModified, Cell) VALUES ('" + firstName + "','" + lastName
                + "','" + emailAddress + "','" + phoneNumber + "','" + date.toString() + "','" + cellPhoneNumber + "')");

    }
    
    public static void executeStatement(String statement) throws SQLException {
        stmt.executeUpdate(statement);
    }
    
    public ResultSet queryDB(String query) throws SQLException {
        return stmt.executeQuery(query);
    }
    
    public boolean isClientInDB(Client client) {
        return false;
    }
    
    public List<Client> getClientListFromDB() throws SQLException {
        // Get all the clients
        ResultSet rs = stmt.executeQuery("Select  * from AMPM.Client ORDER BY LastModified DESC");
        
        // Create the return list
        List<Client> clientList = new ArrayList<Client>();
        
        // Loop through offline results and create a full list of clients
        while (rs.next()) {
            String clientID = rs.getString("ClientID");
            String firstName = rs.getString("FirstName");
            String lastName = rs.getString("LastName");
            String email = rs.getString("Email");
            String phone = rs.getString("Phone");
            Timestamp lastModified = rs.getTimestamp("LastModified");
            String cell = rs.getString("Cell");
            
            // Create a new client and add it to the list
            Client client = new Client(clientID, firstName, lastName, email, phone, lastModified, cell);
            clientList.add(client);
        }
        
        return clientList;
    }
    
    public static void updateRemoteClient(Client client) {
        DBOnline dbOnline = new DBOnline();
        System.out.println(client.getSQLUpdate());
        try {
            dbOnline.executeStatement(client.getSQLUpdate());
        } catch (SQLException ex) {
            Logger.getLogger(DBOffline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateLocalClient(Client client) {
        try {
            executeStatement(client.getSQLUpdate());
        } catch (SQLException ex) {
            Logger.getLogger(DBOffline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** Should download any new data from the remote database and upload
     *  any data from the local database. Assumes the remote DB is accessible.
     * 
     * @throws SQLException 
     */
    public void sync() throws SQLException {
        DBOnline dbOnline = new DBOnline();
              
        List<Client> offlineClients = getClientListFromDB();
        List<Client> onlineClients = dbOnline.getClientListFromDB();
        
        System.out.println("Looking for recently modified clients in the two databases");
        for (Client onlineClient : onlineClients) {
            for (Client offlineClient : offlineClients) {
                if (onlineClient.getID().equals(offlineClient.getID())) {
                    Timestamp offlineLastModified = offlineClient.getLastModified();
                    Timestamp onlineLastModified = onlineClient.getLastModified();
                    if (!onlineLastModified.equals(offlineLastModified)) {
                        int diff = onlineLastModified.compareTo(offlineLastModified);
                        
                        System.out.println("the difference: " + onlineLastModified.toString() + ", " + offlineLastModified.toString());
                        // Most recent is online
                        if (onlineLastModified.after(offlineLastModified)) {
                          // Pull this client's changes to the local DB
                           System.out.println("Updating the local client");
                           updateLocalClient(onlineClient);
                        } 
                        // Most recent is offline  
                        else {
                           // Put the local client's changes on the remote server
                           System.out.println("updating the remote client");
                           updateRemoteClient(offlineClient);
                        }
                    }
                }
            }
        }
        
        
        System.out.println("Looking for clients not in the online database");
        // Check to see if there are new offline clients
        for (Client offlineClient : offlineClients) {
            // If this offline client is NOT in the online client list
            if (!isInClientList(offlineClient, onlineClients)) {
                System.out.println("Adding " + offlineClient.getFullName() + ", id: " + offlineClient.getID());
                System.out.println(offlineClient.getSQLInsert());

                dbOnline.executeStatement(offlineClient.getSQLInsert());
            }
        }
        
        System.out.println("Looking for clients not in the offline database");
        // Check to see if there are new online clients
        for (Client onlineClient : onlineClients) {
            // If this online client is NOT in the offline client list
            if (!isInClientList(onlineClient, offlineClients)) {
                System.out.println("Adding " + onlineClient.getFullName() + ", id: " + onlineClient.getID());
                System.out.println(onlineClient.getCell());
                System.out.println(onlineClient.getSQLInsert());

                executeStatement(onlineClient.getSQLInsert());
            }
        }
        
    }

    /** Check to see if a client's id is contained in the given client list
     * 
     * @return 
     */
    private Boolean isInClientList(Client searchClient, List<Client> clientList) {
        for (Client client : clientList) {
            if (searchClient.getID().equals(client.getID()))
                return true;
        }
        return false;
    }
}
