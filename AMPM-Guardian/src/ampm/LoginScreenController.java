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
import javafx.scene.control.CheckBox;
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
    private Label loginLabel;
    @FXML
    private CheckBox showPassword;

    DBConnection dbConnection;
// For use with PasswordField
    public static final char BULLET = '\u2022';

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
    private void showPassword(ActionEvent event) {
        if (showPassword.isSelected()) {
            passField.setStyle("-fx-echo-char: -;");
        }
    }

    @FXML
    private void handleLoginClick(MouseEvent event) throws SQLException, IOException {
        System.out.println(passField.getText());
        Boolean success = dbConnection.init(userField.getText(), passField.getText());
        if (!success) {
            loginLabel.setStyle("-fx-text-fill:red");
            loginLabel.setText("Login failed: Username or Password is incorrect");
            System.out.println("Failure");
        } else {
            loginLabel.setStyle("-fx-text-fill:green");
            loginLabel.setText("Login Successful");
            System.out.println("Success");

            // Launch the homescreen stage
            Stage homeStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));

            Scene scene = new Scene(root);
            homeStage.setScene(scene);
            homeStage.show();

            // Close the current screen
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        }
    }
}
