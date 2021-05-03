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
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

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
    Label clientName;
    @FXML
    BorderPane refreshPanel;

    private ResultSet rs;
    private ArrayList<Client> clients;
    private ObservableList<String> items;
    private String firstName;
    private String lastName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.print(DBConnection.getStatement());
        try {
            // should be able to do this from the DBConnection class
            setupListView();
        } catch (SQLException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        refreshPanel.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {

            public void handle(MouseEvent e) {
                try {
                    // TODO Auto-generated method stub
                    setupListView();
                } catch (SQLException ex) {
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
         */
        //tryRefresh();
    }

    public void setupListView() throws SQLException {
        rs = DBConnection.getClients();
        clients = new ArrayList<Client>();
        while (rs.next()) {
            clients.add(new Client(rs.getString("ClientID"), rs.getString("FirstName"), rs.getString("Lastname")));
        }
        items = FXCollections.observableArrayList();
        items.clear();
        clientListView.setItems(items);
        // Add the first 10 clients to the ListView
        if (clients.size() > 10) {
            clients.subList(0, 10).forEach(client -> {
                items.add(client.getFullName() + " (" + client.getID() + ")");
            });
        } else {
            clients.forEach(client -> {
                items.add(client.getFullName() + " (" + client.getID() + ")");
            });
        }
        if (!clientListView.getItems().isEmpty()) {
            clientListView.getSelectionModel().select(0);
            clientName.setText(clientListView.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    private void handleOnKeyTyped(KeyEvent event) throws SQLException {
        // If there's nothing in the textbox, just show the most recent clients
        String currSearch = clientSearchField.getText();
        System.out.println(currSearch);
        if (currSearch.length() == 0) {
            setupListView();
            return;
        }
        items = FXCollections.observableArrayList();
        items.clear();
        clients.forEach(client -> {
            if (client.getFullName().length() >= currSearch.length() || client.getID().length() >= currSearch.length()) {
                if (client.getFullName().contains(currSearch)) {
                    items.add(client.getFullName() + " (" + client.getID() + ")");
                } else if (client.getID().contains(currSearch)) {
                    items.add(client.getFullName() + " (" + client.getID() + ")");
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
        //refresh();
        Scene scene = new Scene(root);
        homeStage.setScene(scene);
        homeStage.show();

    }

    @FXML
    private void handleAddProviderButton(MouseEvent event) throws IOException {
        // Launch the new client stage
        Stage homeStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ComboBoxManage.fxml"));

        Scene scene = new Scene(root);
        homeStage.setScene(scene);
        homeStage.show();
    }

    @FXML
    private void handleNewClientInfoClicked(MouseEvent event) throws IOException, SQLException {
        if (!this.clientListView.getSelectionModel().getSelectedItem().isEmpty()) {
            // Launch the new client stage
            Stage homeStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddClientInfoScreen.fxml"));
            Parent root = loader.load();
            AddClientInfoScreenController ac = loader.getController();
            ac.setUp(clientListView.getSelectionModel().getSelectedItem());
            //refresh();
            Scene scene = new Scene(root);
            homeStage.setScene(scene);
            homeStage.show();

            //Close the current stage
            //Stage stage = (Stage) newClientButton.getScene().getWindow();
            //stage.close();  
        }

    }

    @FXML
    private void handleClientListAction(MouseEvent event) throws IOException {
        clientName.setText(clientListView.getSelectionModel().getSelectedItem());

    }

    /*
    @FXML
    private void handleTestButtonClicked(MouseEvent event) throws IOException, SQLException {
        if (DBConnection.isOnline()) {
            System.out.println("connected");
        } else {
            System.out.println("not connected");
        }
    }
     */
    //Use this method to refresh the client list
    @FXML
    public void refreshListview(MouseEvent event) throws SQLException {
        setupListView();
        //System.out.println(event.getX());
    }
    /*
    private void tryRefresh() {
        root1.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                try {
                    setupListView();
                } catch (SQLException ex) {
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
     */
}
