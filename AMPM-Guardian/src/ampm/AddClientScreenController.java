/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.net.URL;
import java.sql.ResultSet;
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
 * @author John Zhu
 */
public class AddClientScreenController implements Initializable {

    private ResultSet rs;
     @FXML
    private TextField firstName;
    private TextField lastName;
    private TextField phoneNumber;
    private TextField emailAddress;

    @FXML
    private Button addClientButton;
    
    DBConnection dbConnection;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnection = new DBConnection();

    }    
     @FXML
    private void addNewClient(MouseEvent event) throws SQLException { 
        //SQL command to insert client information to the database
        rs = AMPMGuardian.getStatement().executeQuery("INSERT INTO Client VALUES '"+firstName.getText()+","+lastName.getText()+","+phoneNumber.getText()+","+emailAddress.getText()+"'");
    } 
}
