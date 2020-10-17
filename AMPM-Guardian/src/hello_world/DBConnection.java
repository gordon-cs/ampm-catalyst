/*
 * MyDBConnection.java
 *
 * Created on 2005/01/16, 10:50
 * Copied (with slight modification) from https://netbeans.org/project_downloads/www/MyDBConnection.java
 */

package hello_world;

import java.sql.*;
import java.util.Properties;

/**
 *
 * @author noniko
 */
public class DBConnection {
    
    private Connection myConnection;
    
    /** Creates a new instance of DBConnection */
    public DBConnection() {

    }
    
    public void init(){
        Properties connectionProps = new Properties();
        connectionProps.put("user", "ampmadmin");
        connectionProps.put("password", "thepasswordispassword");
        
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
            System.out.println("Failed to get connection");
            e.printStackTrace();
        }
    }
    
    public Connection getMyConnection(){
        return myConnection;
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
