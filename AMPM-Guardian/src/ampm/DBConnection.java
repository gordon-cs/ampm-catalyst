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
import java.util.Properties;

/**
 *
 * @author noniko
 */
public class DBConnection {

    private static Connection conn;
    private static DBConnection dbConnection = null;
    private static Statement stmt;
    private static Boolean offlineMode;

    /**
     * Creates a new instance of DBConnection
     */
    public DBConnection() {

    }
    // static method to create/get singleton instance of DBConnection class 

    public static DBConnection getInstance() {

        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }

        return dbConnection;
    }

    /**
     * Creates the DBConnection instance by logging in with a specified
     * user/pass combination.
     *
     * @param user String. The username to use for the database
     * @param pass String. The corresponding password
     */
    public Boolean init(String user, String pass) {
        // First, check to see if there is even an internet connection available
        if (!internetAvailable()) {
            // No connection, we should start the offline procedure
            initOffline(user, pass);
        }

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
        offlineMode = false;
        return success;
    }

    private Boolean initOffline(String username, String password) {
        // We need to ensure it isn't the first time that the system has been used
        //      if it is, the system can't be used (no downloaded data or way to do auth)

        // We need to make sure there is a saved username/pw hash and use it to verify
        // Assuming user/pass was correct, we 
        return false;
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
     * Get recent client in the database
     *
     * @return
     * @throws SQLException
     */
    public static ResultSet getClients() throws SQLException {
        if (offlineMode) {
            // if we are in offline mode, then we should probably 
        }
        return stmt.executeQuery("Select  FirstName, LastName, LastModified from Client ORDER BY LastModified DESC");
    }

    /**
     * Check database to see if that client is exist already
     *
     * @return
     * @throws SQLException
     */
    public static ResultSet checkClients(String firstName, String lastName, String emailAddress, String phoneNumber) throws SQLException {
        if (offlineMode) {
            // if we are in offline mode, then we should probably 
        }
        return stmt.executeQuery("SELECT * FROM Client WHERE FirstName = '" + firstName
                + "' AND LastName = '" + lastName
                + "' AND Email = '" + emailAddress
                + "' AND Phone = '" + phoneNumber + "'");
    }

    public static ResultSet executeStatement(String statement) throws SQLException {
        return stmt.executeQuery(statement);
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
            String emailAddress, String phoneNumber, String date, String cellPhoneNumber) throws SQLException {
        if (offlineMode) {
            // if we are in offline mode, then we should probably 
        }

        stmt.executeUpdate("INSERT INTO Client (FirstName, LastName, Email,"
                + " Phone, LastModified, Cell) VALUES ('" + firstName + "','" + lastName
                + "','" + emailAddress + "','" + phoneNumber + "','" + date + "','" + cellPhoneNumber + "')");

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
            System.out.println("Internet is not connected");
            return false;
        } catch (IOException e) {
            System.out.println("Internet is not connected");
            return false;
        }
    }

    public static void startOfflineMode() {
        offlineMode = true;
    }

}
