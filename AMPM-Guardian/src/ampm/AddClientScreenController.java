/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private ResultSet rs;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField cellPhoneNumber;
    @FXML
    private TextField emailAddress;

    @FXML
    private Button addClientButton;

    @FXML
    private Label infoLabel;

    DBConnection dbConnection;
    //Format for date
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    PreparedStatement preparedStatement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnection = new DBConnection();

    }

    /**
     *
     * @param event for onMouseClicked Get string for first name,last name,
     * phone number, email address,and cellphone number(optional) in TextField,
     * and check if there are valid. Insert client info to database if all the
     * data is valid.
     * @throws SQLException
     */
    @FXML
    private void addNewClient(MouseEvent event) throws SQLException, IOException {

        //Input should not be empty
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty()
                || phoneNumber.getText().isEmpty() || emailAddress.getText().isEmpty()) {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Please fill all the information");
        } //Validating the input of name
        else if (!isValidName(firstName.getText()) || !isValidName(lastName.getText())) {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Invalid Name");
        } //Validating the input of phone number
        else if (!isValidPhone(phoneNumber.getText())) {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Invalid home phone number");
        } //Validating the input of email
        else if (!isValidEmail(emailAddress.getText())) {
            infoLabel.setStyle("-fx-text-fill:red");
            infoLabel.setText("Invalid email address");
        } else if (!(cellPhoneNumber.getText().isEmpty())) {
            if (!isValidPhone(cellPhoneNumber.getText())) {
                infoLabel.setStyle("-fx-text-fill:red");
                infoLabel.setText("Invalid cell phone number");
            } else {
                String check = "SELECT * FROM Client WHERE FirstName = '" + firstName.getText()
                        + "' AND LastName = '" + lastName.getText()
                        + "' AND Email = '" + emailAddress.getText()
                        + "' AND Phone = '" + phoneNumber.getText() + "';";
                //Fortmat for date
                System.out.println(check);
                preparedStatement = dbConnection.getConnection().prepareStatement(check);
                rs = preparedStatement.executeQuery(check);
                if (!isFilled(rs)) {
                    Date date = new Date();
                    String sql = "INSERT INTO Client (FirstName, LastName, Email, "
                            + "Phone, LastModified,Cell) VALUES ('" + firstName.getText()
                            + "','" + lastName.getText() + "','" + emailAddress.getText()
                            + "','" + phoneNumber.getText() + "','" + formatter.format(date)
                            + "','" + cellPhoneNumber.getText() + "')";
                    try {
                        preparedStatement = dbConnection.getConnection().prepareStatement(sql);
                        preparedStatement.executeUpdate(sql);
                        infoLabel.setStyle("-fx-text-fill:green");
                        infoLabel.setText("A new client was inserted successfully!");
                    } catch (SQLException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        infoLabel.setText("A new client was insertion failed!");
                    }
                } else {
                    infoLabel.setStyle("-fx-text-fill:red");
                    infoLabel.setText("This client is in the database already.");
                }
            }
        } else {
            //Command to check if the client is already exist in databse
            String check = "SELECT * FROM Client WHERE FirstName = '" + firstName.getText()
                    + "' AND LastName = '" + lastName.getText()
                    + "' AND Email = '" + emailAddress.getText()
                    + "' AND Phone = '" + phoneNumber.getText() + "';";
            //Fortmat for date
            preparedStatement = dbConnection.getConnection().prepareStatement(check);
            rs = preparedStatement.executeQuery(check);
            if (!isFilled(rs)) {
                //SQL command to insert client information to the database
                Date date = new Date();
                String sql = "INSERT INTO Client (FirstName, LastName, Email, Phone, LastModified) VALUES ('" + firstName.getText() + "','"
                        + lastName.getText() + "','" + emailAddress.getText() + "','" + phoneNumber.getText() + "','" + formatter.format(date) + "')";
                try {
                    preparedStatement = dbConnection.getConnection().prepareStatement(sql);
                    preparedStatement.executeUpdate(sql);
                    infoLabel.setStyle("-fx-text-fill:green");
                    infoLabel.setText("A new client was inserted successfully!");
                    
                    finalizeInsert();
                 
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    infoLabel.setText("A new client was insertion failed!");
                }
            } else {
                infoLabel.setStyle("-fx-text-fill:red");
                infoLabel.setText("This client is in the database already.");
            }
        }
    }

    public boolean isValidName(String username) {
        String regex = "[A-Za-z\\s]+";
        Pattern pat = Pattern.compile(regex);
        return username.matches(regex);//returns true if input and regex matches otherwise false;
    }

    public boolean isValidPhone(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) {
            return true;
        } //validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        } //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        } //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        } //return false if nothing matches the input
        else {
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        //returns true if input and regex matches otherwise false;
        return pat.matcher(email).matches();
    }

    // Check if rs is filled or not
    public static boolean isFilled(ResultSet rs) {
        boolean isEmpty = true;
        try {
            while (rs.next()) {
                isEmpty = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return !isEmpty;
    }
    
    // Handle the logic after a successful insert. 
    private void finalizeInsert() throws IOException {
        // Close current screen
        Stage stage = (Stage) addClientButton.getScene().getWindow();
        stage.close();
        
        // Open home screen
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
}
