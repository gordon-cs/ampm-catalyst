/*
 * MyDBConnection.java
 *
 * Created on 2005/01/16, 10:50
 * Copied (with slight modification) from https://netbeans.org/project_downloads/www/MyDBConnection.java
 */

package ampm;

import java.sql.*;
import java.util.Properties;

/**
 *
 * @author noniko
 */
public class DBConnection {
    
    private Connection myConnection;
    private static DBConnection dbConnection = null;
    /** Creates a new instance of DBConnection */
    public DBConnection() {
        
    }
      // static method to create/get singleton instance of DBConnection class 
    public static DBConnection getInstance() {
      
        if (dbConnection == null) 
            dbConnection = new DBConnection(); 
  
        return dbConnection; 
    } 
    
    /**Creates the DBConnection instance by logging in with a specified user/pass
     * combination. 
     * 
     * @param user String. The username to use for the database
     * @param pass String. The corresponding password
     */
    public Boolean init(String user, String pass){
        Boolean success = true;
        
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", pass);
        
        // after the port info (3306), you need to put "/helloworld" to select
        // the database. 
        String dbURL = "jdbc:mysql://ampm-database.c9frur6iyppq.us-east-2.rds.amazonaws.com:3306/helloworld";
        
        try {
            // forname needs to be called once to establish the driver.
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // connect to the DB using the url and props
            myConnection = DriverManager.getConnection(dbURL, connectionProps);
        }
        catch(Exception e){
            success = false;
            System.out.println("Failed to get connection");
            e.printStackTrace();
        }
        return success; 
    }
    
    public void close(ResultSet rs){
        
        if(rs != null){
            try {
               rs.close();
            }
            catch(Exception e){}
        
        }
    }
    
    public void close(java.sql.Statement stmt){
        
        if(stmt != null){
            try {
               stmt.close();
            }
            catch(Exception e){}
        }
    }
     
  public void destroy(){
      
    if (myConnection != null){
        try {
            myConnection.close();
        }
        catch(Exception e){}     
    }
  }
  
}
