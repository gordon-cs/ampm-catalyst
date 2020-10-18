/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private void handleLoginClick(MouseEvent event) throws SQLException {  
        System.out.println(passField.getText());
        Boolean success = dbConnection.init(userField.getText(), passField.getText());
        if (!success) {
            invalidLabel.setVisible(true);
            System.out.println("Failure");
        } else {
            invalidLabel.setVisible(false);
            System.out.println("Success");
        }
    } 
}