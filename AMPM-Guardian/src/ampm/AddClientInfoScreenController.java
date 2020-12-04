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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnz
 */
public class AddClientInfoScreenController implements Initializable {

    @FXML
    private TabPane tabPane;
    //Basic Info Tab
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField accountNumber;
    @FXML
    private DatePicker dateOfBirth;
    //Diagnose Tab
    @FXML
    private TextField diagnoseDescription;
    @FXML
    private DatePicker diagosedDate;
    @FXML
    private TextField diagnosisDoctor;
    @FXML
    private RadioButton diagonsisNone;
    @FXML
    private RadioButton medicationGeneric;
    @FXML
    private RadioButton medicationSpecific;
    @FXML
    private MenuButton monitoringType;
    @FXML
    private MenuButton addNewDiagnoseType;
    //Preventative Tab
    @FXML
    private TextField preventiveType;
    @FXML
    private DatePicker preventiveDateDone;
    @FXML
    private TextField preventivePrescribedBy;
    @FXML
    private DatePicker preventiveNextDueDate;
    @FXML
    private TextField preventiveSource;
    @FXML
    private TextField preventiveFrequency;
    @FXML
    private TextField preventionImmunType;
    @FXML
    private DatePicker preventionImmunDateGiven;
    @FXML
    private TextField preventionImmunName;
    @FXML
    private TextField preventionImmunLocation;
    //Providers Tab
    @FXML
    private MenuButton provideType;
    @FXML
    private TextField providerName;
    @FXML
    private TextField nurseName;
    @FXML
    private TextField nameOfPANP;
    @FXML
    private Button addNewProvider;
    //Family History Tab
    @FXML
    private TextField familyDiagnos;
    @FXML
    private TextField familyRelation;
    @FXML
    private TextField familyRealtionAge;
    @FXML
    private Button addNewRelative;
    //Medical Equipment Tab
    @FXML
    private TextField medicalEquipType;
    @FXML
    private TextField medicalEquipPrescribe;
    @FXML
    private TextField medicalEquipReason;
    @FXML
    private DatePicker medicalEquipStartDate;
    @FXML
    private TextArea medicalEquipNotes;
    //Alerts Tab
    @FXML
    private MenuButton alertsType;
    @FXML
    private TextField alertsSpecific;
    @FXML
    private TextArea altersDescrption;
    @FXML
    private Button addNewAlert;

    private ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Set up MenuItem 

        //Set up if the information is filled 
        //Set up client info in basic info tab
        basicInfoSetUp();
        diagnoseTabSetUp();
        preventativeTabSetUp();
        providersTabSetUp();
        familyHistoryTabSetUp();
        medicalEquipmentTabSetUp();
        alertsTabSetUp();
    }

    private void basicInfoSetUp() {
        firstName.setText("John");
        lastName.setText("Zhu");
        phoneNumber.setText("test");
        emailAddress.setText("test");
        accountNumber.setText("");
    }

    private void diagnoseTabSetUp() {

    }

    private void preventativeTabSetUp() {

    }

    private void providersTabSetUp() {

    }
    
    private void familyHistoryTabSetUp(){
        
    }
    
    private void medicalEquipmentTabSetUp(){
        
    }
    
    private void alertsTabSetUp(){
        
    }
}