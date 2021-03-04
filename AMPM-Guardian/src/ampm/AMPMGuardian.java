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
 * @author John and Ben 
 */
public class AMPMGuardian extends Application {
    public static Scene loginScene;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        loginScene = scene;
        
        stage.show();
        
        /* TEST CODE */
//        System.out.println("Starting cards");
//        AlertCard test1 = new AlertCard();
//        System.out.println("Done with cards");
        /* END TEST */
        
//        PDFManager test = new PDFManager(null);
//        System.exit(0); //DELETE THIS AFTER TESTING
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
