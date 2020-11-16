/*
 * MyDBConnection.java
 *
 * Created on 2005/01/16, 10:50
 * Copied (with slight modification) from https://netbeans.org/project_downloads/www/MyDBConnection.java
 */
package ampm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
    
/**
 *
 * @author noniko
 */
public class DBOnline {

    private static Connection conn;
    private static DBOnline dbConnection = null;
    private static Statement stmt;
  
    /**
     * Creates a new instance of DBConnection
     */
    public DBOnline() {

    }
    // static method to create/get singleton instance of DBOnline class 

    public static DBOnline getInstance() {

        if (dbConnection == null) {
            dbConnection = new DBOnline();
        }

        return dbConnection;
    }

    /**
     * Creates the DBOnline instance by logging in with a specified
 user/pass combination.
     *
     * @param user String. The username to use for the database
     * @param pass String. The corresponding password
     */
    public Boolean init(String user, String pass) {

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
        } catch (Exception e) {
            success = false;
            System.out.println("Failed to get connection");
            e.printStackTrace();
        }
        return success;
    }

    public void close(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
            }

        }
    }

    public void close(java.sql.Statement stmt) {

        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
            }
        }
    }

    public void destroy() {

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public static Statement getStatement() {
        return stmt;
    }

    public static Connection getConnection() {
        return conn;
    }
    
    /**
     * Get recent clients in the database
     * @return
     * @throws SQLException 
     */
    public ResultSet getClients() throws SQLException {
        return stmt.executeQuery("Select * from AMPM.Client ORDER BY LastModified DESC");
    }
    
    
    /**
     * Get a full list of the clients in the database. Stored in List<Client> object
     * @return
     * @throws SQLException 
     */
    public List<Client> getClientListFromDB() throws SQLException {
        // Get all the clients
        ResultSet rs = stmt.executeQuery("Select * from AMPM.Client ORDER BY LastModified DESC");
        
        // Create the return list
        List<Client> clientList = new ArrayList<Client>();
        
        // Loop through the online results and create a full list of clients
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

    /**
     * Check database to see if that client is exist already
     * @return
     * @throws SQLException 
     */
    public static ResultSet checkClients(String firstName, String lastName, String emailAddress, String phoneNumber) throws SQLException {
        return stmt.executeQuery("SELECT * FROM AMPM.Client WHERE FirstName = '" + firstName
                        + "' AND LastName = '" + lastName
                        + "' AND Email = '" + emailAddress
                        + "' AND Phone = '" + phoneNumber + "'");
    }
    
    /**
     * This method will add new client to database base on the 6 variables
     *
     * @param firstName
     * @param lastName
     * @param emailAddress
     * @param phoneNumber
     * @param date
     * @param cellPhoneNumber
     * @throws SQLException
     */
    public void addNewClients(String firstName, String lastName,
            String emailAddress, String phoneNumber, Timestamp date, String cellPhoneNumber) throws SQLException {

        stmt.executeUpdate("INSERT INTO AMPM.Client (FirstName, LastName, Email,"
                + " Phone, LastModified, Cell) VALUES ('" + firstName + "','" + lastName
                + "','" + emailAddress + "','" + phoneNumber + "','" + date.toString() + "','" + cellPhoneNumber + "')");

    }
    
    public void executeStatement(String statement) throws SQLException {
        stmt.executeUpdate(statement);
    }

    /**
     * This method can be called to determine if the DBConnection has been
     * invalidated. If we have a connection, it should return true, if we lost
     * the connection or the connection was interrupted, it will return false.
     *
     * @return true if there was no interruption, false if the connection was
     * lost
     * @throws SQLException
     */
    public static Boolean isOnline() throws SQLException {
        return conn.isValid(3);
    }

    /**
     * This code should be called to check if there is an internet connection
     * available.
     *
     * @return true if internet is available, false otherwise
     */
    private static Boolean internetAvailable() {
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
    

}
