/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class LoginScreenController implements Initializable {
    
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button loginButton;
    @FXML
    private Label invalidLabel;
    
    DBConnection dbConnection;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BooleanBinding formIsFilled = passField.textProperty().isEmpty()
                            .or(userField.textProperty().isEmpty());
        loginButton.disableProperty().bind(formIsFilled);
        dbConnection = new DBConnection();
                
    }    
    
    @FXML
    private void handleLoginClick(MouseEvent event) throws SQLException, IOException {  
        System.out.println(passField.getText());
        Boolean success = dbConnection.init(userField.getText(), passField.getText());
        if (!success) {
            invalidLabel.setVisible(true);
            System.out.println("Failure");
        } else {
            invalidLabel.setVisible(false);
            System.out.println("Success");
            
            // Launch the homescreen stage
            Stage homeStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        
            Scene scene = new Scene(root);
            homeStage.setScene(scene);
            homeStage.show();
            
            // TODO: Need to find a way to close stages from a controller class.
            // this doesn't seem to be working right now.
            Stage loginStage = new Stage();
            loginStage.setScene(AMPMGuardian.loginScene);
            loginStage.hide();
        }
    } 
}