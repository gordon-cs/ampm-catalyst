/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.Serializable;
import java.sql.ResultSet;
import com.mockrunner.mock.jdbc.MockResultSet;
import java.sql.SQLException;

/** This class represents the offline database. It can be saved (serializable) and 
 * handles the authentication and syncing procedures.
 *
 * @author benab
 */
public class DBOffline implements Serializable {
    private static DBOffline dbOffline = null;
    
    // A resultset containing the entire clientTable
    private static ResultSet clientTable = null; 
    
    Boolean isSynced;

    /** Creates a new instance of DBOffline */
    public DBOffline() {
        
    }
      // static method to create/get singleton instance of DBOffline class 
    public static DBOffline getInstance() {
      
        if (dbOffline == null) 
            dbOffline = new DBOffline(); 
  
        return dbOffline; 
    } 
    
    public static void createMockClients() throws SQLException {
        MockResultSet rs = new MockResultSet("Client");
        
        // Add all the columns to the rs
        rs.addColumn("UserID");
        rs.addColumn("FirstName");
        rs.addColumn("LastName");
        rs.addColumn("Email");
        rs.addColumn("Phone");
        rs.addColumn("LastModified");
        rs.addColumn("Cell");
        
        ResultSet realRS = DBConnection.getClients();
        
        while (realRS.next()) {
            rs.next();
            
            // Save the real DB values into the fake DB
            rs.updateInt("UserID", realRS.getInt("UserID"));
            rs.updateString("FirstName", realRS.getString("FirstName"));
            rs.updateString("LastName", realRS.getString("LastName"));
            rs.updateString("Email", realRS.getString("Email"));
            rs.updateString("Phone", realRS.getString("Phone"));
            rs.updateDate("LastModified", realRS.getDate("LastModified"));
            rs.updateString("Cell", realRS.getString("Cell"));
        }

    }
}
