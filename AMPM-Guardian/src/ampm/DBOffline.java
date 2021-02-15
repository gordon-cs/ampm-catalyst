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
 * alongside the .jar). This database should always be accessible. 
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
    
    // Gets a list of clients (organized by LastModified) as a result set from
    // the offline database
    private static ResultSet getClients() throws SQLException {
        return stmt.executeQuery("Select  FirstName, LastName, LastModified from AMPM.Client ORDER BY LastModified DESC");
    } 
    
    // For the demo: this code smelled bad. I wasn't sure why we needed it. Turns out 
    // we dont. It is never actually used. 
    
//    public void addNewClients(Client client) throws SQLException {
//        
////        String firstName, String lastName,
////                              String emailAddress, String phoneNumber, 
////                              Timestamp date, String cellPhoneNumber
//
//        stmt.executeUpdate(client.getSQLInsert());
//        
//        /*stmt.executeUpdate("INSERT INTO AMPM.Client (FirstName, LastName, Email,"
//                + " Phone, LastModified, Cell) VALUES ('" + firstName + "','" + lastName
//                + "','" + emailAddress + "','" + phoneNumber + "','" + date.toString() + "','" + cellPhoneNumber + "')");*/
//
//    }
    
    public static void executeStatement(String statement) throws SQLException {
        stmt.executeUpdate(statement);
    }
    
    public ResultSet queryDB(String query) throws SQLException {
        return stmt.executeQuery(query);
    }
    
    // Returns a list of client objects from the Offline database. 
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
              
        // Retrieve the clients from both DBs
        List<Client> offlineClients = getClientListFromDB();
        List<Client> onlineClients = dbOnline.getClientListFromDB();
        
        System.out.println("Looking for recently modified clients in the two databases");
        for (Client onlineClient : onlineClients) {
            for (Client offlineClient : offlineClients) {
                // Check to see if a client in the online database is the same as one in
                // the offline one
                if (onlineClient.getID().equals(offlineClient.getID())) {
                    Timestamp offlineLastModified = offlineClient.getLastModified();
                    Timestamp onlineLastModified = onlineClient.getLastModified();
                    // Check to see if the timestamps are different
                    if (!onlineLastModified.equals(offlineLastModified)) {
                        
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
                // Put the offline client on the online database 
                dbOnline.executeStatement(offlineClient.getSQLInsert());
            }
        }
        
        System.out.println("Looking for clients not in the offline database");
        // Check to see if there are new online clients
        for (Client onlineClient : onlineClients) {
            // If this online client is NOT in the offline client list
            if (!isInClientList(onlineClient, offlineClients)) {
                System.out.println("Adding " + onlineClient.getFullName() + ", id: " + onlineClient.getID());
                // Put the online client on the offline db
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
