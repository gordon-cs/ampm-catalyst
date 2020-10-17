/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author John Zhu and Ben Abbett
 */
public class FXMLDocumentController implements Initializable {
    private ResultSet rs;
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {        
        // Test connection to database when the button is pressed.
        rs = AMPMGuardian.getStatement().executeQuery("SELECT * FROM hello");
        
        // Retrive result of query
        rs.next();
        String title = rs.getString("title");
        String body = rs.getString("body");

        // Set the new label's value
        label.setText(title + " " + body + "\n" + LocalDateTime.now().toString());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
