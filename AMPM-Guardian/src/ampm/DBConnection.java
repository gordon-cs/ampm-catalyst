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
    
    private static Connection conn;
    private static DBConnection dbConnection = null;
    private static Statement stmt;
    
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
        
        // after the port info (3306), you need to put "/AMPM" to select
        // the database. 
        String dbURL = "jdbc:mysql://ampm-database.c9frur6iyppq.us-east-2.rds.amazonaws.com:3306/AMPM";
        
        try {
            // forname needs to be called once to establish the driver.
            Class.forName("com.mysql.cj.jdbc.Driver");
            
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
      
        if (conn != null){
            try {
                conn.close();
            }
            catch(Exception e){}     
        }
    }
    
    public static Statement getStatement() {
        return stmt;
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
    public static ResultSet getClients() throws SQLException {
        return stmt.executeQuery("Select  FirstName, LastName, LastModified from Client");
    } 
    
    public static Boolean isOnline() throws SQLException {
//        ResultSet rs = stmt.executeQuery("select * from Client limit 1");
//        System.out.println(rs);
        return conn.isValid(3);
    }
    
}
