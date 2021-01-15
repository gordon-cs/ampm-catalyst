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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
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
    private TextField provideType;
    @FXML
    private TextField providerName;
    @FXML
    private TextField nurseName;
    @FXML
    private TextField nameOfPANP;
    @FXML
    private ListView providerList;

//Family History Tab
    @FXML
    private TextField familyDiagnosis;
    @FXML
    private TextField familyRealtionAge;
    @FXML
    private ListView relativeList;
    @FXML
    private ComboBox relativeSelectBox;

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
    private String clientID;
    DBConnection dbConnection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnection = new DBConnection();
    }

    private void basicInfoSetUp(String fname, String lname, String email, String phone) {
        firstName.setText(fname);
        lastName.setText(lname);
        phoneNumber.setText(email);
        emailAddress.setText(phone);
        accountNumber.setText(this.clientID);

    }

    private void diagnoseTabSetUp() throws SQLException {
        Diagnose diagnose = new Diagnose(this.clientID);
        ResultSet rs = dbConnection.executeStatement(diagnose.getSQLSelect());
        if (rs.next()) {
            diagnoseDescription.setText(rs.getString("Diagnosis"));
            diagnosisDoctor.setText(rs.getString("DiagnosedBy"));
        }

        Monitor monitor = new Monitor(this.clientID);
        ResultSet rs2 = dbConnection.executeStatement(monitor.getSQLSelect());
        if (rs2.next()) {
            monitorSpecific.setText(rs2.getString("SpecificName"));
        }
    }

    private void preventativeTabSetUp() throws SQLException {
        Preventive preventive = new Preventive(this.clientID);
        ResultSet rs = dbConnection.executeStatement(preventive.getSQLSelect());
        if (rs.next()) {
            preventiveType.setText(rs.getString("PreventiveType"));
            preventivePrescribedBy.setText(rs.getString("PrescribedBy"));
            preventiveSource.setText(rs.getString("Source"));
            preventiveFrequency.setText(rs.getString("Frequency"));
        }

        PreventionImmunizations preventionImmunizations = new PreventionImmunizations(this.clientID);
        ResultSet rs2 = dbConnection.executeStatement(preventionImmunizations.getSQLSelect());
        if (rs2.next()) {
            preventionImmunType.setText(rs2.getString("Type"));
            preventionImmunName.setText(rs2.getString("Name"));
            preventionImmunLocation.setText(rs2.getString("WhereGiven"));
        }
    }

    private void providersTabSetUp() throws SQLException {
        Provider provider = new Provider(this.clientID);
        ResultSet rs = dbConnection.executeStatement(provider.getSQLSelect());
        while (rs.next()) {
            providerList.getItems().addAll(rs.getString("Provider"));
        }
        /*
        if (rs.next()) {
            provideType.setText(rs.getString("Type"));
            providerName.setText(rs.getString("Provider"));
            nurseName.setText(rs.getString("Nurse"));
            nameOfPANP.setText(rs.getString("PANP"));
        }
         */
    }

    private void familyHistoryTabSetUp() throws SQLException {
        FamilyHistory familyHistory = new FamilyHistory(this.clientID);
        ResultSet rs = dbConnection.executeStatement(familyHistory.getSQLSelect());
        //Put all the family history info in the listview and showed relation in the listview
        while (rs.next()) {
            relativeSelectBox.getItems().addAll(rs.getString("Relation"));
        }
        rs.close();
        //Mouse Click event for each in the listview
        relativeSelectBox.setOnAction(e -> {
            relativeList.getItems().clear();
            //Clear the textfield when "new" item was clicked

            //Auto-fill the textfield based on selected item.
            try {
                String query = "SELECT * FROM AMPM.FamilyHistory WHERE ClientID ='" + this.clientID + "'AND Relation = '"
                        + (String) relativeSelectBox.getSelectionModel().getSelectedItem() + "'";
                System.out.println(query);
                ResultSet rs2 = dbConnection.executeStatement(query);
                while (rs2.next()) {
                    familyDiagnosis.setText(rs2.getString("Diagnoses"));
                    familyRealtionAge.setText(rs2.getString("Age"));
                }
                setUpDiagnosesList();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        //Select specific diagnosis for 
        relativeList.setOnMouseClicked(e -> {
            familyDiagnosis.setText((String) relativeList.getSelectionModel().getSelectedItem());

            if (relativeList.getSelectionModel().getSelectedItem().equals("New")) {
                familyDiagnosis.clear();
            }
        });
    }

    private void medicalEquipmentTabSetUp() throws SQLException {
        MedicalEquipment medicalEquipment = new MedicalEquipment(this.clientID);
        ResultSet rs = dbConnection.executeStatement(medicalEquipment.getSQLSelect());
        if (rs.next()) {
            medicalEquipType.setText(rs.getString("EquipType"));
            medicalEquipPrescribe.setText(rs.getString("PrescribedBy"));
            medicalEquipReason.setText(rs.getString("UsedFor"));
            medicalEquipNotes.setText(rs.getString("Notes"));
        }
    }

    private void alertsTabSetUp() throws SQLException {
        Alert alert = new Alert(this.clientID);
        ResultSet rs = dbConnection.executeStatement(alert.getSQLSelect());
        if (rs.next()) {
            alertsSpecific.setText(rs.getString("AlertSpecific"));
            altersDescrption.setText(rs.getString("AlertDescription"));
        }
    }

    private void medicationTabSetUp() throws SQLException {
        Medication medicaiton = new Medication(this.clientID);
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

    //Update client basic info
    @FXML
    private void saveBasicTab(MouseEvent event) throws SQLException, IOException {
        Client updateInfo = new Client(this.clientID, firstName.getText(), lastName.getText(),
                emailAddress.getText(), phoneNumber.getText(), new Timestamp(new Date().getTime()),
                null);
        System.out.println(updateInfo.getSQLUpdate());
        dbConnection.addInfo(updateInfo.getSQLUpdate());

    }

    //Insert or update diagnose info to database
    @FXML
    private void saveDiagnoseTab(MouseEvent event) throws SQLException, IOException {
        Diagnose diagnose = new Diagnose(this.clientID);
        ResultSet rs = dbConnection.executeStatement(diagnose.getSQLSelect());
        Diagnose diagnoseInfo = new Diagnose(this.clientID, diagnoseDescription.getText(), null, diagnosisDoctor.getText());
        if (rs.next()) {
            System.out.println(diagnoseInfo.getSQLUpdate());
            dbConnection.addInfo(diagnoseInfo.getSQLUpdate());
        } else {
            System.out.println(diagnoseInfo.getSQLInsert());
            dbConnection.addInfo(diagnoseInfo.getSQLInsert());
        }
        Monitor monitor = new Monitor(this.clientID);
        ResultSet rs2 = dbConnection.executeStatement(monitor.getSQLSelect());
        Monitor monitorInfo = new Monitor(this.clientID, diagnoseDescription.getText(), monitorSpecific.getText());
        if (rs2.next()) {
            System.out.println(monitorInfo.getSQLUpdate());
            dbConnection.addInfo(monitorInfo.getSQLUpdate());
        } else {
            System.out.println(monitorInfo.getSQLInsert());
            dbConnection.addInfo(monitorInfo.getSQLInsert());
        }

    }

    //Insert or update preventive info to database
    @FXML
    private void savePreventiveTab(MouseEvent event) throws SQLException, IOException {

        Preventive preventive = new Preventive(this.clientID);
        ResultSet rs = dbConnection.executeStatement(preventive.getSQLSelect());
        Preventive preventiveInfo = new Preventive(this.clientID, preventiveType.getText(), null, preventivePrescribedBy.getText(),
                null, preventiveSource.getText(), preventiveFrequency.getText());
        if (rs.next()) {
            System.out.println(preventiveInfo.getSQLUpdate());
            dbConnection.addInfo(preventiveInfo.getSQLUpdate());
        } else {
            System.out.println(preventiveInfo.getSQLInsert());
            dbConnection.addInfo(preventiveInfo.getSQLInsert());
        }
        PreventionImmunizations preventionImmunizations = new PreventionImmunizations(this.clientID);
        ResultSet rs2 = dbConnection.executeStatement(preventionImmunizations.getSQLSelect());
        PreventionImmunizations preventionImmunizationsInfo = new PreventionImmunizations(this.clientID, preventionImmunType.getText(),
                preventionImmunName.getText(), null, preventionImmunLocation.getText());
        if (rs2.next()) {
            System.out.println(preventionImmunizationsInfo.getSQLUpdate());
            dbConnection.addInfo(preventionImmunizationsInfo.getSQLUpdate());
        } else {
            System.out.println(preventionImmunizationsInfo.getSQLInsert());
            dbConnection.addInfo(preventionImmunizationsInfo.getSQLInsert());
        }

    }

    //Insert or update provider info to database
    @FXML
    private void saveProviderTab(MouseEvent event) throws SQLException, IOException {
        Provider provider = new Provider(this.clientID);
        ResultSet rs = dbConnection.executeStatement(provider.getSQLSelect());
        Provider providerInfo = new Provider(this.clientID, provideType.getText(), providerName.getText(),
                nurseName.getText(), nameOfPANP.getText());
        if (rs.next()) {
            System.out.println(providerInfo.getSQLUpdate());
            dbConnection.addInfo(providerInfo.getSQLUpdate());
        } else {
            System.out.println(providerInfo.getSQLInsert());
            dbConnection.addInfo(providerInfo.getSQLInsert());
        }
        rs.close();
    }

    //Insert or update family history of client info to database
    @FXML
    private void saveFamilyHistoryTab(MouseEvent event) throws SQLException, IOException {
        if (!this.relativeList.getSelectionModel().getSelectedItem().equals("New")) {
            //Update infomation for current selected relative
            FamilyHistory familyHistoryInfo = new FamilyHistory(this.clientID, familyDiagnosis.getText(),
                    (String) relativeSelectBox.getSelectionModel().getSelectedItem(), null, familyRealtionAge.getText());
            dbConnection.addInfo(familyHistoryInfo.getSQLUpdate());
        } else {
            //Insert infomation for new relative
            FamilyHistory familyHistoryInfo = new FamilyHistory(this.clientID, familyDiagnosis.getText(),
                    (String) relativeSelectBox.getSelectionModel().getSelectedItem(), null, familyRealtionAge.getText());
            dbConnection.addInfo(familyHistoryInfo.getSQLInsert());
        }
        setUpDiagnosesList();
    }

    //Insert or update family history of client info to database
    @FXML
    private void saveMedicalEquipTab(MouseEvent event) throws SQLException, IOException {
        MedicalEquipment medicalEquipment = new MedicalEquipment(this.clientID);
        ResultSet rs = dbConnection.executeStatement(medicalEquipment.getSQLSelect());
        MedicalEquipment medicalEquipmentInfo = new MedicalEquipment(this.clientID, medicalEquipType.getText(),
                medicalEquipPrescribe.getText(), medicalEquipReason.getText(), null, medicalEquipNotes.getText());
        if (rs.next()) {
            System.out.println(medicalEquipmentInfo.getSQLUpdate());
            dbConnection.addInfo(medicalEquipmentInfo.getSQLUpdate());
        } else {
            System.out.println(medicalEquipmentInfo.getSQLInsert());
            dbConnection.addInfo(medicalEquipmentInfo.getSQLInsert());
        }
    }    //Insert or update family history of client info to database

    @FXML
    private void saveAlertTab(MouseEvent event) throws SQLException, IOException {
        Alert alert = new Alert(this.clientID);
        ResultSet rs = dbConnection.executeStatement(alert.getSQLSelect());
        Alert alertInfo = new Alert(this.clientID, null,
                alertsSpecific.getText(), altersDescrption.getText());
        if (rs.next()) {
            System.out.println(alertInfo.getSQLUpdate());
            dbConnection.addInfo(alertInfo.getSQLUpdate());
        } else {
            System.out.println(alertInfo.getSQLInsert());
            dbConnection.addInfo(alertInfo.getSQLInsert());
        }
    }    //Insert or update family history of client info to database

    @FXML
    private void saveMedicalTab(MouseEvent event) throws SQLException, IOException {
        Medication medication = new Medication(this.clientID);
        ResultSet rs = dbConnection.executeStatement(medication.getSQLSelect());
        Medication medicationInfo = new Medication(this.clientID, medicationClass.getText(),
                medicationGenericName.getText(), medicationBrandName.getText(), medicationDose.getText(),
                medicationFrequency.getText(), null, medicationPrescribedBy.getText(),
                medicationUsedFor.getText(), null, medicationProvider.getText());
        if (rs.next()) {
            System.out.println(medicationInfo.getSQLUpdate());
            dbConnection.addInfo(medicationInfo.getSQLUpdate());
        } else {
            System.out.println(medicationInfo.getSQLInsert());
            dbConnection.addInfo(medicationInfo.getSQLInsert());
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
            this.clientID = rs.getString("ClientID");
            String email = rs.getString("Email");
            String phone = rs.getString("Phone");
            basicInfoSetUp(fname, lname, email, phone);
            diagnoseTabSetUp();
            preventativeTabSetUp();
            providersTabSetUp();
            familyHistoryTabSetUp();
            medicalEquipmentTabSetUp();
            alertsTabSetUp();
            medicationTabSetUp();
        }

    }

    public void setUpDiagnosesList() throws SQLException {
        String query = "SELECT * FROM AMPM.FamilyHistory WHERE ClientID ='" + this.clientID + "'AND Relation = '"
                + (String) relativeSelectBox.getSelectionModel().getSelectedItem() + "'";
        ResultSet rs = dbConnection.executeStatement(query);
        relativeList.getItems().clear();
        while (rs.next()) {
            relativeList.getItems().addAll(rs.getString("Diagnoses"));
        }
        relativeList.getItems().add("New");
    }

}
