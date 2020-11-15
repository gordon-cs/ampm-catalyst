
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
import java.util.List;
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
import javafx.scene.control.Label;
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
    @FXML
    Button testButton;
    @FXML
    private Button syncButton;
    @FXML
    private Label welcomeLabel;
    
    private ResultSet rs;
    private List<Client> clients;
    private ObservableList<String> items;
    private DBManager dbManager;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            System.out.print(DBOnline.getStatement());
            
            dbManager = new DBManager();
            // should be able to do this from the DBOnline class
//            rs = DBOnline.getClients();
//            rs = DBOffline.getClients();
//            clients =setupListView(dbManager.getClients());
            clients = dbManager.getClients();
            setupListView(clients);
//            clients = new ArrayList<String>();
//            addResultToClients(rs);
//            setupListView();
            
            // Get the currently logged in user and update the label
//            welcomeLabel.setText("Welcome " + DBOnline.getCurrentUser()); UNCOMMENT WHEN DONE TESTING OFFLINE DB
        } catch (SQLException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    private void addResultToClients(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println("add result to client was called. shouldnt have been though");
//            clients.add(rs.getString("FirstName") + " " + rs.getString("LastName"));
        }
    }
    
    private void setupListView(List<Client> clientList) {
        items = FXCollections.observableArrayList();
        clientListView.setItems(items);
        
        // Add the first 10 clients to the listview
        if (clientList.size() > 10) {
            clientList.subList(0, 10).forEach(client -> {
                items.add(client.getFullName());
            });
        } else {
            clientList.forEach(client -> {
                items.add(client.getFullName());
            });
        }
    }
    
    @FXML
    private void handleOnKeyTyped(KeyEvent event) {
        // If there's nothing in the textbox, just show the most recent clients
        String currSearch = clientSearchField.getText();
        if (currSearch.length() == 0) {
            setupListView(clients);
            return;
        }
        
        items = FXCollections.observableArrayList();
        clients.forEach(client -> {
            if (client.getFullName().length() >= currSearch.length()) {
                if (client.getFullName().substring(0,currSearch.length()).equalsIgnoreCase(currSearch)) {
                    items.add(client.getFullName());
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

            // Close the current stage
            Stage stage = (Stage) newClientButton.getScene().getWindow();
            stage.close();  
            
    }
    
    @FXML
    private void handleTestButtonClicked(MouseEvent event) throws IOException, SQLException {
        if (DBOnline.isOnline()) {
            System.out.println("connected");
        } else {
            System.out.println("not connected");
        }
    }
    
    @FXML
    private void handleSyncButtonClicked(MouseEvent event) {
//        DBOffline.sync();
    }
    
}
