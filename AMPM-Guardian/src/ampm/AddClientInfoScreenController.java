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
import javafx.scene.control.Label;
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
    private Label accountNumber;
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
    private TextField monitorSpecific;
    @FXML
    private MenuButton addNewDiagnoseType;

//Preventive Tab
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
    private TextField familyDiagnosis;
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

//Medication Tab
    @FXML
    private TextField medicationClass;
    @FXML
    private TextField medicationGenericName;
    @FXML
    private TextField medicationBrandName;
    @FXML
    private TextField medicationDose;
    @FXML
    private TextField medicationFrequency;
    @FXML
    private DatePicker medicationStartDate;
    @FXML
    private TextField medicationPrescribedBy;
    @FXML
    private TextField medicationUsedFor;
    @FXML
    private DatePicker medicationStopedDate;
    @FXML
    private TextField medicationProvider;

    private String name;
    DBConnection dbConnection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnection = new DBConnection();
    }

    private void basicInfoSetUp(String fname, String lname, String email, String phone, String clientID) {
        firstName.setText(fname);
        lastName.setText(lname);
        phoneNumber.setText(email);
        emailAddress.setText(phone);
        accountNumber.setText(clientID);

    }

    private void diagnoseTabSetUp(String clientID) throws SQLException {
        Diagnose diagnose = new Diagnose(clientID);
        ResultSet rs = dbConnection.executeStatement(diagnose.getSQLSelect());
        if (rs.next()) {
            diagnoseDescription.setText(rs.getString("Diagnosis"));
            diagnosisDoctor.setText(rs.getString("DiagnosedBy"));
        }

        Monitor monitor = new Monitor(clientID);
        ResultSet rs2 = dbConnection.executeStatement(monitor.getSQLSelect());
        if (rs2.next()) {
            monitorSpecific.setText(rs2.getString("Specific"));
        }
    }

    private void preventativeTabSetUp(String clientID) throws SQLException {
        Preventive preventive = new Preventive(clientID);
        ResultSet rs = dbConnection.executeStatement(preventive.getSQLSelect());
        if (rs.next()) {
            preventiveType.setText(rs.getString("PreventiveType"));
            preventivePrescribedBy.setText(rs.getString("PrescribedBy"));
            preventiveSource.setText(rs.getString("Source"));
            preventiveFrequency.setText(rs.getString("Frequency"));
        }

        PreventionImmunizations preventionImmunizations = new PreventionImmunizations(clientID);
        ResultSet rs2 = dbConnection.executeStatement(preventionImmunizations.getSQLSelect());
        if (rs2.next()) {
            preventionImmunType.setText(rs2.getString("Type"));
            preventionImmunName.setText(rs2.getString("Name"));
            preventionImmunLocation.setText(rs2.getString("WhereGiven"));
        }
    }

    private void providersTabSetUp(String clientID) throws SQLException {
        Provider provider = new Provider(clientID);
        ResultSet rs = dbConnection.executeStatement(provider.getSQLSelect());
        if (rs.next()) {
            provideType.setText(rs.getString("Type"));
            providerName.setText(rs.getString("Provider"));
            nurseName.setText(rs.getString("Nurse"));
            nameOfPANP.setText(rs.getString("PANP"));
        }
    }

    private void familyHistoryTabSetUp(String clientID) throws SQLException {
        FamilyHistory familyHistory = new FamilyHistory(clientID);
        ResultSet rs = dbConnection.executeStatement(familyHistory.getSQLSelect());
        if (rs.next()) {
            familyDiagnosis.setText(rs.getString("Diagnoses"));
            familyRelation.setText(rs.getString("Relation"));
            familyRealtionAge.setText(rs.getString("Age"));
        }
    }

    private void medicalEquipmentTabSetUp(String clientID) throws SQLException {
        MedicalEquipment medicalEquipment = new MedicalEquipment(clientID);
        ResultSet rs = dbConnection.executeStatement(medicalEquipment.getSQLSelect());
        if (rs.next()) {
            medicalEquipType.setText(rs.getString("EquipType"));
            medicalEquipPrescribe.setText(rs.getString("PrescribedBy"));
            medicalEquipReason.setText(rs.getString("UsedFor"));
            medicalEquipNotes.setText(rs.getString("Notes"));
        }
    }

    private void alertsTabSetUp(String clientID) throws SQLException {
        Alert alert = new Alert(clientID);
        ResultSet rs = dbConnection.executeStatement(alert.getSQLSelect());
        if (rs.next()) {
            alertsSpecific.setText(rs.getString("AlertSpecific"));
            altersDescrption.setText(rs.getString("AlertDescption"));
        }
    }

    private void medicationTabSetUp(String clientID) throws SQLException {
        Medication medicaiton = new Medication(clientID);
        ResultSet rs = dbConnection.executeStatement(medicaiton.getSQLSelect());
        if (rs.next()) {

            medicationClass.setText(rs.getString("Class"));
            medicationGenericName.setText(rs.getString("GenericName"));
            medicationBrandName.setText(rs.getString("BrandName"));
            medicationDose.setText(rs.getString("Dose"));
            medicationFrequency.setText(rs.getString("Frequency"));
            medicationPrescribedBy.setText(rs.getString("PrescribedBy"));
            medicationUsedFor.setText(rs.getString("UsedFor"));
            medicationProvider.setText(rs.getString("Provider"));
        }
    }

    public void setUp(String name) throws SQLException {
        dbConnection = new DBConnection();

        //Get name that passed by home screen
        this.name = name;

        //Seperate name to first name and last name
        String[] arr = name.split(" ");
        String fname = arr[0];
        String lname = arr[1];

        //Set client with first name and last name 
        Client thisClient = new Client(fname, lname);
        ResultSet rs = dbConnection.executeStatement(thisClient.getByName());

        //Get clientID from result set
        if (rs.next()) {
            String clientID = rs.getString("ClientID");
            String email = rs.getString("Email");
            String phone = rs.getString("Phone");
            basicInfoSetUp(fname, lname, email, phone, clientID);
            diagnoseTabSetUp(clientID);
            preventativeTabSetUp(clientID);
            providersTabSetUp(clientID);
            familyHistoryTabSetUp(clientID);
            medicalEquipmentTabSetUp(clientID);
            alertsTabSetUp(clientID);
            medicationTabSetUp(clientID);
        }
    }

}
