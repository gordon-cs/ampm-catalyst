/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.sql.Connection;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author johnz
 */
public class AMPMGuardian extends Application {
    
    private static DBConnection db;
    private static Connection conn;
    private static Statement stmt;
    
    @Override
    public void start(Stage stage) throws Exception {
<<<<<<< Updated upstream
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
=======
        Parent root = FXMLLoader.load(getClass().getResource("AddClientScreen.fxml"));
>>>>>>> Stashed changes
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        // Create the DB connection
        db = new DBConnection();
        db.init();
        conn = db.getMyConnection();
        
        // Create the statement for running sql queries.
        stmt = conn.createStatement();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /* Getters for the class variables */
    
    public static DBConnection getDB() {
        return db;
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
    public static Statement getStatement() {
        return stmt;
    }
}
