/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author John Zhu
 */
public class AddClientScreenController implements Initializable {

    //private ResultSet rs;
    private ResultSet rs;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField emailAddress;

    @FXML
    private Button addClientButton;
    
    @FXML
    private Label infoLabel;
    
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
        //Input should not be empty
        if(firstName.getText().isEmpty()|| lastName.getText().isEmpty()
                ||phoneNumber.getText().isEmpty()||emailAddress.getText().isEmpty())
        {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Please fill all the information");
        }
        //Validating the input of name
        else if(!isValidName(firstName.getText())||!isValidName(lastName.getText()))
        {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Invalid Name");
        }
        //Validating the input of phone number
        else if(!isValidPhone(phoneNumber.getText()))
        {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Invalid phone number");
        }
        //Validating the input of email
        else if(!isValidEmail(emailAddress.getText()))
        {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Invalid email address");
        }
        //System.out.println(phoneNumber);
        //DataFormat datefrmat = new SimpleDataFormat("yyyy/MM/dd HH:mm:ss");
        //SQL command to insert client information to the database
        rs = DBConnection.getStatement().executeQuery("INSERT INTO Client (FirstName, LastName, Email, Phone) VALUES '"+firstName.getText()+","
                +lastName.getText()+","+phoneNumber.getText()+","+emailAddress.getText()+"'");
        //maybe need use try catch?
    } 
    
    public boolean isValidName(String username){      
       String regex="[A-Za-z\\s]+";      
        Pattern pat = Pattern.compile(regex); 
        return username.matches(regex);//returns true if input and regex matches otherwise false;
    }
    
    public boolean isValidPhone(String phoneNo) {
	//validate phone numbers of format "1234567890"
	if (phoneNo.matches("\\d{10}")) return true;
	//validating phone number with -, . or spaces
	else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
	//validating phone number with extension length from 3 to 5
	else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
	//validating phone number where area code is in braces ()
	else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
	//return false if nothing matches the input
	else return false;		
    }
   
    public boolean isValidEmail(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        return pat.matcher(email).matches(); //returns true if input and regex matches otherwise false;
    } 

}
