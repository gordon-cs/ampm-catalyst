package ampm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private static Boolean isOnline;
    private static String currUser;
    private static Statement stmt;

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
       // Boolean success = true;        
        currUser = user;
        return true;
    }
    
    public List<Client> getClients() throws SQLException {
            return dbOnline.getClientListFromDB();
       
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
        ResultSet rs = stmt.executeQuery(client.getSQLSelect());
        
        if ( rs.next()) {
            return true;
        }
        return false;
    }
    
    public void insertClient(Client client) throws SQLException {
            dbOnline.executeStatement(client.getSQLInsert());
    }
}