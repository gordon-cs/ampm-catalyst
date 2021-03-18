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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnz
 */
public class ComboBoxManageController implements Initializable {

    @FXML
    public TableView<ProviderBox> providerTable;
    @FXML
    public TableColumn<ProviderBox, String> columnType;
    @FXML
    public TableColumn<ProviderBox, String> columnProvider;
    DBConnection dbConnection;
    private ObservableList<ProviderBox> items;

    ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnection = new DBConnection();
        loadDataFromDatabase();
    }

    @FXML
    public void loadDataFromDatabase() {
        try {
            // Execute query and store result in a resultset
            items = FXCollections.observableArrayList();
            ResultSet rs = dbConnection.executeStatement("SELECT * FROM AMPM.ProviderList");
            while (rs.next()) {
                //get strings
                items.add(new ProviderBox(rs.getString("Type"), rs.getString("Provider")));
                providerTable.setItems(items);
                System.out.println(rs.getString("Type") + " " + rs.getString("Provider"));
            }

        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        //makes columns editable
        columnType.setCellValueFactory(new PropertyValueFactory<ProviderBox, String>("Type"));
        columnProvider.setCellValueFactory(new PropertyValueFactory<ProviderBox, String>("Provider"));

        //tableview.getColumns().addAll(columnType, columnProvider);
    }

}
