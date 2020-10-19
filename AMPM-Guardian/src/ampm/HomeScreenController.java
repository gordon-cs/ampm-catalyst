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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HomeScreenController implements Initializable {
    @FXML
    TextField clientSearchField;
    @FXML
    ListView<String> clientListView;
    @FXML
    Button newClientButton;
    
    private ResultSet rs;
    private ArrayList<String> clients;
    private ObservableList<String> items;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            System.out.print(DBConnection.getStatement());
            
            rs = DBConnection.getStatement().executeQuery("Select  FirstName, LastName, LastModified from Client");
            clients = new ArrayList<String>();
            addResultToClients(rs);
            setupListView();
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    private void addResultToClients(ResultSet rs) throws SQLException {
        while (rs.next()) {
            clients.add(rs.getString("FirstName") + " " + rs.getString("LastName"));
        }
    }
    
    private void setupListView() {
        items = FXCollections.observableArrayList();
        clientListView.setItems(items);
        
        // Add the first 10 clients to the ListView
        if (clients.size() > 10) {
            clients.subList(0, 10).forEach(client -> {
                items.add(client);
            });
        } else {
            clients.forEach(client -> {
                items.add(client);
            });
        }
    }
    
    @FXML
    private void handleOnKeyTyped(KeyEvent event) {
        String currSearch = clientSearchField.getText();
        items = FXCollections.observableArrayList();
        
        clients.forEach(client -> {
            if (client.length() >= currSearch.length()) {
                if (client.substring(0,currSearch.length()).equalsIgnoreCase(currSearch)) {
                    items.add(client);
                }
            }
        });
        clientListView.setItems(items);
    }
    
    @FXML
    private void handleNewClientClicked(MouseEvent event) throws IOException {
            // Launch the new client stage
            Stage homeStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddClientScreen.fxml"));

            Scene scene = new Scene(root);
            homeStage.setScene(scene);
            homeStage.show();
            
    }
    
    
}
