package ampm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** The database manager class is in charge of setting up the databases on initialization. 
 *  It can be used to figures out if the remote database is accessible, and depending on the result will
 *  initialize the correct database(s). Anytime a class wants to do something to a 
 *  database, it should do it through this class.
 * 
 **/
public class DBManager {
    private static DBManager dbManager = null;
    private static DBOnline dbOnline;
    private static DBOffline dbOffline;
    private static Boolean isOnline;
    private static String currUser;
    
    /** Creates a new instance of the database manager*/
    public DBManager() {
        
    }
    
    // static method to create/get singleton instance of this class 
    public static DBManager getInstance() {
      
        if (dbManager == null) 
            dbManager = new DBManager(); 
  
        return dbManager; 
    } 
    
    /** This should initialize the manager class, setting up the applicable databases **/
    public static Boolean init(String user, String password) {
        Boolean success = true;
        isOnline = false;
        
        // Set up the offline database
        dbOffline = new DBOffline();
        dbOffline.init(user, password);
        
        // If there is internet available, we should setup the online database and
        // also make sure it has been synced.
        if (isInternetAvailable()) {
           // Login to the online DB
           dbOnline = new DBOnline();
           dbOnline.init(user, password);

           // Try to sync them up
            try {
                System.out.println("Starting sync...");
                dbOffline.sync();
                isOnline = true;
            } catch (SQLException ex) {
                System.out.println("dbOffline.sync() threw SQL Exception");
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                success = false;
            }
        }
        currUser = user;
        return success;
    }
    
    public List<Client> getClients() throws SQLException {
        if (isOnline) {
            return dbOnline.getClientListFromDB();
        } else {
            return dbOffline.getClientListFromDB();
        }
    }
    
    /**
     * This code should be called to check if there is an internet connection
     * available.
     *
     * @return true if internet is available, false otherwise
     */
    private static Boolean isInternetAvailable() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Internet is connected");
            return true;
        } catch (MalformedURLException e) {
            System.out.println("Internet is not connected, bad URL");
            return false;
        } catch (IOException e) {
            System.out.println("Internet is not connected, bad IO");
            return false;
        }
    }
    
    public String getCurrentUser() {
        return currUser;
    }
    
    /** Checks to see if the given client is in the database. Only need to check the
     *  local one. 
     * 
     * @param client
     * @return 
     */
    public Boolean isInDatabase(Client client) throws SQLException {
        ResultSet rs = dbOffline.queryDB(client.getSQLSelect());
        
        if ( rs.next()) {
            return true;
        }
        return false;
    }
    
    public void insertClient(Client client) throws SQLException {
        DBOffline.executeStatement(client.getSQLInsert());
        if (isOnline) {
            dbOnline.executeStatement(client.getSQLInsert());
        }
        
    }
}
