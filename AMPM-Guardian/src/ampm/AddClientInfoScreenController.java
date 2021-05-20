/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnz
 */
public class AddClientInfoScreenController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField firstName;

//Basic Info Tab
    @FXML
    private TextField clientName, clientName1, clientName2,
            clientName3, clientName4, clientName5, clientName6, clientName7;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField cellPhoneNumber;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private Label accountNumber;
    @FXML
    private TextField clientIdentifer;

//Diagnose Tab
    @FXML
    private ComboBox diagnoseDescription;
    @FXML
    private DatePicker diagosedDate;
    @FXML
    private ComboBox diagnosisType;
    @FXML
    private RadioButton diagonsisNone;
    @FXML
    private TextField monitorSpecific;
    @FXML
    private MenuButton addNewDiagnoseType;
    @FXML
    private ListView diagnoseList;
    @FXML
    private ListView monitorList;
    @FXML
    private TextArea printDiagnoseLabel;

//Preventive Tab
    @FXML
    private TextField preventiveType;
    @FXML
    private TextField preventiveDateDone;
    @FXML
    private TextField preventivePrescribedBy;
    @FXML
    private TextField preventiveNextDueDate;
    @FXML
    private TextField preventiveSource;
    @FXML
    private TextField preventiveFrequency;
    @FXML
    private ListView preventiveList;

//Prevention Immunizations Tab
    @FXML
    private TextField preventionImmunType;
    @FXML
    private TextField preventionImmunDateGiven;
    @FXML
    private TextField preventionImmunName;
    @FXML
    private TextField preventionImmunLocation;
    @FXML
    private ListView preventionImmunList;

//Providers Tab
    @FXML
    private TextField providerName;
    @FXML
    private TextField nurseName;
    @FXML
    private TextField nameOfPANP;
    @FXML
    private ListView providerList;
    @FXML
    private TextArea printProviderLabel;
    @FXML
    private ComboBox providerTypeBox;

//Family History Tab
    @FXML
    private TextField familyDiagnosis;
    @FXML
    private TextField familyRealtionAge;
    @FXML
    private ListView relativeList;
    @FXML
    private TextField familyRelative;
    @FXML
    private ListView diagnosesList;

//Medical Equipment Tab
    @FXML
    private TextField medicalEquipType;
    @FXML
    private TextField medicalEquipPrescribe;
    @FXML
    private TextField medicalEquipReason;
    @FXML
    private TextField medicalEquipStartDate;
    @FXML
    private ListView equipmentList;
    @FXML
    private TextArea medicalEquipNotes;

//Alerts Tab
    @FXML
    private ComboBox alertsType;
    @FXML
    private TextField alertsSpecific;
    @FXML
    private TextArea altersDescrption;
    @FXML
    private Button addNewAlert;
    @FXML
    private ListView alertList;
    @FXML
    private TextArea printAlertLabel;

//Medication Tab
    @FXML
    private ComboBox medicationClass;
    @FXML
    private ComboBox medicationGenericName;
    @FXML
    private ComboBox medicationBrandName;
    @FXML
    private TextField medicationDose;
    @FXML
    private TextField medicationFrequency;
    @FXML
    private DatePicker medicationStartDate;
    @FXML
    private ComboBox medicationPrescribedBy;
    @FXML
    private ComboBox medicationUsedFor;
    @FXML
    private DatePicker medicationStoppedDate;
    @FXML
    private ComboBox medicationProvider;
    @FXML
    private ListView medicationList;
    @FXML
    private TextArea printMedicationLabel;

    private String name;
    private String clientID;
    DBConnection dbConnection;
    PDFPrint pdfPrint;
    Alert alert = new Alert(AlertType.ERROR);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnection = new DBConnection();
        pdfPrint = new PDFPrint();
        Locale.setDefault(Locale.ENGLISH);
    }

    //Set up the TextField in BasicInfo tab and make TextField uneditable before click edit button
    private void basicInfoSetUp(String fname, String lname, String email, String phone, String cellPhone, String DOB) {
        firstName.setText(fname);
        lastName.setText(lname);
        phoneNumber.setText(phone);
        emailAddress.setText(email);
        cellPhoneNumber.setText(cellPhone);
        dateOfBirth.setValue(LocalDate.parse(DOB));
        clientIdentifer.setText(this.clientID);
        clientIdentifer.setStyle("-fx-text-fill:white; -fx-background-color: grey;");

        firstName.setEditable(false);
        lastName.setEditable(false);
        phoneNumber.setEditable(false);
        emailAddress.setEditable(false);
        cellPhoneNumber.setEditable(false);
        dateOfBirth.setEditable(false);
    }

    //Set up the TextField and ListView in Diagnose tab and make TextField uneditable before click edit button or new in the ListView
    private void diagnoseTabSetUp() throws SQLException {
        setUpDiagnoseList();
        setUpDiagnoseBox();
        Diagnose diagnose = new Diagnose(this.clientID);
        diagnoseList.setOnMouseClicked(e -> {
            try {
                setUpMonitorList();
                //Show the releated information when click one item in ListView
                ResultSet rs = dbConnection.executeStatement(diagnose.getSQLSelectByDiagnosis(
                        (String) diagnoseList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    diagnoseDescription.setValue(rs.getString("Diagnosis"));
                    if (!rs.getString("StartDate").equals("0000-00-00")) {
                        diagosedDate.setValue(LocalDate.parse(rs.getString("StartDate")));
                    } else {
                        diagosedDate.setValue(null);
                    }
                    diagnosisType.setValue(rs.getString("DiagnosedBy"));
                }
                //Make TextField editable
                //diagnoseDescription.setEditable(false);
                //diagnosisDoctor. (false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        );
        Monitor monitor = new Monitor(this.clientID);

        monitorList.setOnMouseClicked(e
                -> {
            //monitorSpecific.setEditable(true);

            try {
                //Show the releated information when click one item in ListView
                ResultSet rs = dbConnection.executeStatement(monitor.getSQLSelectByName(
                        (String) monitorList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    monitorSpecific.setText(rs.getString("SpecificName"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

    }

    //Set up the TextField and ListView in Preventive tab and make TextField uneditable before click edit button or new in the ListView
    private void preventativeTabSetUp() throws SQLException {
        setUpPreventiveList();
        Preventive preventive = new Preventive(this.clientID);
        preventiveList.setOnMouseClicked(e -> {
            try {
                //Show the releated information when click one item in ListView
                ResultSet rs = dbConnection.executeStatement(preventive.getSQLSelectByType(
                        (String) preventiveList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    preventiveType.setText(rs.getString("PreventiveType"));
                    preventiveDateDone.setText(rs.getString("DateDone"));
                    preventivePrescribedBy.setText(rs.getString("PrescribedBy"));
                    preventiveNextDueDate.setText(rs.getString("NextDueDate"));
                    preventiveSource.setText(rs.getString("Source"));
                    preventiveFrequency.setText(rs.getString("Frequency"));

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    //Set up the TextField and ListView in Prevention Immunizations tab and make TextField uneditable before click edit button or new in the ListView
    private void preventationImmunTabSetUp() throws SQLException {
        setUpPreventionImmunList();
        PreventionImmunizations preventionImmun = new PreventionImmunizations(this.clientID);
        preventionImmunList.setOnMouseClicked(e -> {
            /*
            if (preventionImmunList.getSelectionModel().getSelectedItem().equals("New")) {
                //Clear the TextField for create new item
                preventionImmunType.clear();
                preventionImmunDateGiven.clear();
                preventionImmunName.clear();
                preventionImmunLocation.clear();

                //Make TextField editable
                //preventionImmunType.setEditable(true);
                //preventionImmunDateGiven.setEditable(true);
                //preventionImmunName.setEditable(true);
                //preventionImmunLocation.setEditable(true);
            } else {
             */
            try {
                //Show the releated information when click one item in ListView
                ResultSet rs = dbConnection.executeStatement(preventionImmun.getSQLSelectByDiagnosisName(
                        (String) preventionImmunList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    preventionImmunType.setText(rs.getString("Type"));
                    preventionImmunName.setText(rs.getString("Name"));
                    preventionImmunDateGiven.setText(rs.getString("DateGiven"));
                    preventionImmunLocation.setText(rs.getString("WhereGiven"));
                }

                //Make TextField uneditable
                //preventionImmunType.setEditable(false);
                //preventionImmunDateGiven.setEditable(false);
                //preventionImmunName.setEditable(false);
                //preventionImmunLocation.setEditable(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    //Set up the TextField and ListView in Provider tab and make TextField uneditable before click edit button or new in the ListView
    private void providersTabSetUp() throws SQLException {
        setUpProviderList();
        setUpProviderBox();
        Provider provider = new Provider(this.clientID);
        //Mouse Click event for list view of provider     
        providerList.setOnMouseClicked(e -> {
            /*
            if (providerList.getSelectionModel().getSelectedItem().equals("New")) {
                provideType.clear();
                providerName.clear();
                nurseName.clear();
                nameOfPANP.clear();

                //provideType.setEditable(true);
                //providerName.setEditable(true);
                //nurseName.setEditable(true);
                //nameOfPANP.setEditable(true);
            } else {
             */
            try {
                String providerDetail = (String) providerList.getSelectionModel().getSelectedItem();
                ResultSet rs = dbConnection.executeStatement(provider.getSQLSelectByProvider(
                        providerDetail.substring(providerDetail.indexOf(":") + 2)));
                while (rs.next()) {
                    providerTypeBox.setValue((rs.getString("Type")));
                    providerName.setText(rs.getString("Provider"));
                    nurseName.setText(rs.getString("Nurse"));
                    nameOfPANP.setText(rs.getString("PANP"));
                }
                //provideType.setEditable(false);
                //providerName.setEditable(false);
                //nurseName.setEditable(false);
                //nameOfPANP.setEditable(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void familyHistoryTabSetUp() throws SQLException {
        //Put the relative in the ComboBox
        relativeList.getItems().addAll(
                "Mother",
                "Father",
                "Brother",
                "Sister",
                "Maternal Grandmother",
                "Maternal Grandfather",
                "Paternal Grandmother",
                "Paternal Grandfather",
                "Maternal Aunt",
                "Paternal Aunt",
                "Maternal Uncle",
                "Paternal Uncle"
        );
        diagnosesList.getItems().addAll("Stroke"
        );
        FamilyHistory family = new FamilyHistory(this.clientID);
        //Select event for relative combo box
        relativeList.setOnMouseClicked(e -> {
            //Auto-fill the textfield based on selected item.
            try {
                ResultSet rs = dbConnection.executeStatement(family.getSQLSelectByRelative(
                        (String) relativeList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    familyDiagnosis.setText(rs.getString("Diagnosis"));
                    familyRelative.setText((String) relativeList.getSelectionModel().getSelectedItem());
                    familyRelative.setEditable(false);
                    familyRealtionAge.setText(rs.getString("Age"));
                }
                //familyDiagnosis.setEditable(false);
                //familyRealtionAge.setEditable(false);
                //setUpDiagnosisList();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        //Mouse Click event for this ListView
        /*
        relativeList.setOnMouseClicked(e -> {
            familyDiagnosis.setText((String) relativeList.getSelectionModel().getSelectedItem());
            //familyDiagnosis.setEditable(false);
            if (relativeList.getSelectionModel().getSelectedItem().equals("New")) {
                familyDiagnosis.clear();
                //familyDiagnosis.setEditable(true);
            }
        });
         */
    }

    private void medicalEquipmentTabSetUp() throws SQLException {
        setUpMedicalEquipList();
        MedicalEquipment medicalEquip = new MedicalEquipment(this.clientID);
        equipmentList.setOnMouseClicked(e -> {
            try {
                ResultSet rs = dbConnection.executeStatement(medicalEquip.getSQLSelectByType(
                        (String) equipmentList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    medicalEquipType.setText(rs.getString("EquipType"));
                    medicalEquipPrescribe.setText(rs.getString("PrescribedBy"));
                    medicalEquipReason.setText(rs.getString("UsedFor"));
                    medicalEquipNotes.setText(rs.getString("Notes"));
                }
                //medicalEquipType.setEditable(false);
                //medicalEquipPrescribe.setEditable(false);
                //medicalEquipReason.setEditable(false);
                //medicalEquipNotes.setEditable(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void alertsTabSetUp() throws SQLException {
        setUpAlertList();
        setUpAlertTypeBox();
        Alerts alert = new Alerts(this.clientID);
        alertList.setOnMouseClicked(e -> {

            String alertDetail = (String) alertList.getSelectionModel().getSelectedItem();
            try {
                ResultSet rs = dbConnection.executeStatement(alert.getSQLSelectByType(
                        alertDetail.substring(alertDetail.indexOf(":") + 2)));
                while (rs.next()) {
                    alertsType.setValue(rs.getString("AlertType"));
                    alertsSpecific.setText(rs.getString("AlertSpecific"));
                    altersDescrption.setText(rs.getString("AlertDescription"));
                    altersDescrption.setWrapText(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
    }

    private void medicationTabSetUp() throws SQLException {
        setUpMedicationList();
        setUpGenericNameBox();
        setUpBrandNameBox();
        Medication medication = new Medication(this.clientID);
        medicationList.setOnMouseClicked(e -> {
            try {
                ResultSet rs = dbConnection.executeStatement(medication.getSQLSelectByGenericName(
                        (String) medicationList.getSelectionModel().getSelectedItem()));
                if (rs.next()) {
                    medicationClass.setValue(rs.getString("Class"));
                    medicationGenericName.setOnAction(null);
                    medicationGenericName.setValue(rs.getString("GenericName"));
                    medicationGenericName.setOnAction(e1 -> getMedicationInfoByGeneric());
                    medicationBrandName.setOnAction(null);
                    medicationBrandName.setValue(rs.getString("BrandName"));
                    medicationBrandName.setOnAction(e2 -> getMedicationInfoByBrand());
                    medicationDose.setText(rs.getString("Dose"));
                    medicationFrequency.setText(rs.getString("Frequency"));
                    if (!rs.getString("StartDate").equals("0000-00-00")) {
                        medicationStartDate.setValue(LocalDate.parse(rs.getString("StartDate")));
                    } else {
                        medicationStartDate.setValue(null);
                    }
                    medicationPrescribedBy.setValue(rs.getString("PrescribedBy"));
                    medicationUsedFor.setValue(rs.getString("UsedFor"));
                    if (!rs.getString("DateStopped").equals("0000-00-00")) {
                        medicationStoppedDate.setValue(LocalDate.parse(rs.getString("DateStopped")));
                    } else {
                        medicationStoppedDate.setValue(null);
                    }
                    medicationProvider.setValue(rs.getString("StoppedBy"));
                }
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        if (medicationList.getSelectionModel().isEmpty()) {
            medicationGenericName.setOnAction(e1 -> getMedicationInfoByGeneric());
            medicationBrandName.setOnAction(e2 -> getMedicationInfoByBrand());
        }

    }

    //Get other information by generic name 
    private void getMedicationInfoByGeneric() {
        medicationBrandName.setOnAction(null);
        getMedicationClassByGeneric();
        getMedicationBrandName();
    }

    //Get other information by brand name 
    private void getMedicationInfoByBrand() {
        medicationGenericName.setOnAction(null);
        getMedicationClassByBrand();
        getMedicationGenericName();
    }

    //Autofill the dropdown box by specific generic name
    private void getMedicationClassByGeneric() {
        try {
            setUpMedicationClassBoxByGeneric(medicationGenericName.getSelectionModel().getSelectedItem().toString());
            medicationClass.getSelectionModel().select(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Autofill the dropdown box by specific brand name
    private void getMedicationClassByBrand() {
        try {
            setUpMedicationClassBoxByBrand(medicationBrandName.getSelectionModel().getSelectedItem().toString());
            medicationClass.getSelectionModel().select(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Get all the brand name from database 
    private void getMedicationBrandName() {
        try {
            setUpBrandNameBox(medicationGenericName.getSelectionModel().getSelectedItem().toString());
            medicationBrandName.getSelectionModel().select(0);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //Get all the generic name from database 
    private void getMedicationGenericName() {
        try {
            setUpGenericNameBox(medicationBrandName.getSelectionModel().getSelectedItem().toString());
            medicationGenericName.getSelectionModel().select(0);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Update client basic info
    @FXML
    private void saveBasicTab(MouseEvent event) throws SQLException, IOException {

        Client updateInfo = new Client(this.clientID, firstName.getText(), lastName.getText(),
                emailAddress.getText(), phoneNumber.getText(), new Timestamp(new Date().getTime()).toString(),
                cellPhoneNumber.getText(), dateOfBirth.getValue().toString());
        dbConnection.addInfo(updateInfo.getSQLUpdate());
        firstName.setEditable(false);
        lastName.setEditable(false);
        phoneNumber.setEditable(false);
        emailAddress.setEditable(false);
        cellPhoneNumber.setEditable(false);
        dateOfBirth.setEditable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        Parent root = (Parent) loader.load();
        HomeScreenController refresh = loader.getController();
        refresh.setupListView();
    }

    @FXML
    //Make TextField in this tab editable
    private void editBasicTab(MouseEvent event) {
        firstName.setEditable(true);
        lastName.setEditable(true);
        phoneNumber.setEditable(true);
        emailAddress.setEditable(true);
        cellPhoneNumber.setEditable(true);
        dateOfBirth.setEditable(true);
    }

    //Insert or update diagnose info to database
    @FXML
    private void saveDiagnoseTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (diagnoseDescription.getValue() != null) {
            String diagDate = "0000-00-00";
            if (diagosedDate.getValue() != null) {
                diagDate = diagosedDate.getValue().toString();
            }
            if (diagnoseList.getSelectionModel().getSelectedIndex() > -1) {
                //Update infomation for current selected relative
                Diagnose diagnoseInfo = new Diagnose(this.clientID, (String) diagnoseList.getSelectionModel().getSelectedItem(),
                        diagDate, (diagnosisType.getValue() != null ? diagnosisType.getValue().toString() : ""));
                dbConnection.addInfo(diagnoseInfo.getSQLUpdateNewItem(diagnoseDescription.getValue().toString()));

            } else {
                //Insert infomation for new relative
                Diagnose diagnoseInfo = new Diagnose(this.clientID, diagnoseDescription.getValue().toString(),
                        diagDate, (diagnosisType.getValue() != null ? diagnosisType.getValue().toString() : ""));
                dbConnection.addInfo(diagnoseInfo.getSQLInsert());
            }

            //Reset the ListView (auto update ListView)
            Diagnose diagnoseInfo = new Diagnose(this.clientID);
            if (isEmpty(dbConnection.executeStatement(
                    diagnoseInfo.checkDianosesList((String) diagnoseDescription.getValue())))) {
                dbConnection.addInfo(
                        diagnoseInfo.insertDiagnosesToList(diagnoseDescription.getValue().toString()));
                setUpDiagnoseBox();
            }
            setUpDiagnoseList();
        } else {
            //Show the warning when save failed 
            alert.setTitle("Fail to save");
            alert.setHeaderText(null);
            alert.setContentText("You did not fill all the required information");
            alert.showAndWait();

        }
        //diagnoseDescription.setEditable(false);
        //diagnosisDoctor.setEditable(false);

        if (!monitorSpecific.getText().isEmpty()) {
            if (monitorList.getSelectionModel().getSelectedIndex() > -1) {
                //Update infomation for current selected relative
                Monitor monitorInfo = new Monitor(this.clientID, diagnoseDescription.getValue().toString(), (String) monitorList.getSelectionModel().getSelectedItem());
                dbConnection.addInfo(monitorInfo.getSQLEdit(monitorSpecific.getText()));
            } else {
                //Insert infomation for new relative
                Monitor monitorInfo = new Monitor(this.clientID, diagnoseDescription.getValue().toString(), monitorSpecific.getText());
                dbConnection.addInfo(monitorInfo.getSQLInsert());
            }
            setUpMonitorList();
        }

    }

    @FXML
    private void editDiagnoseTab(MouseEvent event) {

        diagnoseDescription.setValue(null);
        diagnosisType.setValue(null);
        diagosedDate.setValue(null);
        diagnoseList.getSelectionModel().clearSelection();
        monitorList.getItems().clear();
        monitorSpecific.clear();
    }

    @FXML
    private void printDiagnoseCard(MouseEvent event) throws SQLException {
        if (pdfPrint.printDiagnoses(this.clientID)) {
            printDiagnoseLabel.setStyle("-fx-text-fill:green");
            File f = new File("../patient-cards/DiagnosesCard");
            Date date = new Date();
            printDiagnoseLabel.setText("Diagnose information for this client was saved into the pdf file at " + formatter.format(date) + ". The location is \n " + f.getAbsolutePath());

        } else {
            printDiagnoseLabel.setStyle("-fx-text-fill:red");
            printDiagnoseLabel.setText("Failed to save the diagnoses information for this client");
        }

    }

    //Insert or update preventive info to database
    @FXML
    private void savePreventiveTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (!preventiveType.getText().isEmpty()) {
            if (preventiveList.getSelectionModel().getSelectedIndex() > -1) {
                //Update infomation for current selected relative
                Preventive preventiveInfo = new Preventive(this.clientID, (String) preventiveList.getSelectionModel().getSelectedItem(), preventiveDateDone.getText(),
                        preventivePrescribedBy.getText(), preventiveNextDueDate.getText(), preventiveSource.getText(), preventiveFrequency.getText());
                dbConnection.addInfo(preventiveInfo.getSQLUpdateNewType(preventiveType.getText()));
            } else {
                //Insert infomation for new relative
                Preventive preventiveInfo = new Preventive(this.clientID, preventiveType.getText(), preventiveDateDone.getText(), preventivePrescribedBy.getText(),
                        preventiveNextDueDate.getText(), preventiveSource.getText(), preventiveFrequency.getText());
                dbConnection.addInfo(preventiveInfo.getSQLInsert());
            }
            setUpPreventiveList();
        }
    }

    @FXML
    private void editPreventiveTab(MouseEvent event) {
        //preventiveType.setEditable(true);
        //preventiveDateDone.setEditable(true);
        //preventivePrescribedBy.setEditable(true);
        //preventiveNextDueDate.setEditable(true);
        //preventiveSource.setEditable(true);
        //preventiveFrequency.setEditable(true);

        preventiveType.clear();
        preventiveDateDone.clear();
        preventivePrescribedBy.clear();
        preventiveNextDueDate.clear();
        preventiveSource.clear();
        preventiveFrequency.clear();
        preventiveList.getSelectionModel().clearSelection();
    }

    //Insert or update preventive info to database
    @FXML
    private void savePreventionImmunTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (!preventionImmunName.getText().isEmpty()) {
            if (preventionImmunList.getSelectionModel().getSelectedIndex() > -1) {
                //Update infomation for current selected relative
                PreventionImmunizations preventionImmunizationsInfo = new PreventionImmunizations(this.clientID, preventionImmunType.getText(),
                        (String) preventionImmunList.getSelectionModel().getSelectedItem(), preventionImmunDateGiven.getText(), preventionImmunLocation.getText());
                dbConnection.addInfo(preventionImmunizationsInfo.getSQLUpdateNewItem(preventionImmunName.getText()));
            } else {
                //Insert infomation for new relative
                PreventionImmunizations preventionImmunizationsInfo = new PreventionImmunizations(this.clientID, preventionImmunType.getText(),
                        preventionImmunName.getText(), preventionImmunDateGiven.getText(), preventionImmunLocation.getText());
                dbConnection.addInfo(preventionImmunizationsInfo.getSQLInsert());
            }
            setUpPreventionImmunList();
        }
        //preventionImmunType.setEditable(false);
        //preventionImmunDateGiven.setEditable(false);
        //preventionImmunName.setEditable(false);
        //preventionImmunLocation.setEditable(false);
    }

    @FXML
    private void editPreventionImmunTab(MouseEvent event) {
        //preventionImmunType.setEditable(true);
        //preventionImmunDateGiven.setEditable(true);
        //preventionImmunName.setEditable(true);
        //preventionImmunLocation.setEditable(true);
        preventionImmunType.clear();
        preventionImmunDateGiven.clear();
        preventionImmunName.clear();
        preventionImmunLocation.clear();
        preventionImmunList.getSelectionModel().clearSelection();
    }

    //Insert or update provider info to database
    @FXML
    private void saveProviderTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (!providerName.getText().isEmpty() && providerName.getText() != null && providerTypeBox.getValue() != null) {
            if (providerList.getSelectionModel().getSelectedIndex() > -1) {
                //Update infomation for current selected relative
                String providerDetail = (String) providerList.getSelectionModel().getSelectedItem();
                Provider providerInfo = new Provider(this.clientID, providerTypeBox.getValue().toString(),
                        providerDetail.substring(providerDetail.indexOf(":") + 2), nurseName.getText(), nameOfPANP.getText());
                dbConnection.addInfo(providerInfo.getSQLEdit(providerName.getText()));
            } else {
                //Insert infomation for new relative
                Provider providerInfo = new Provider(this.clientID, providerTypeBox.getValue().toString(),
                        providerName.getText(), nurseName.getText(), nameOfPANP.getText());
                dbConnection.addInfo(providerInfo.getSQLInsert());
            }
            //Check if this input type exist in provider type database or not,
            //save if it do not exits
            ProviderType providerType = new ProviderType();
            if (isEmpty(dbConnection.executeStatement(
                    providerType.checkType(providerTypeBox.getValue().toString())))) {
                dbConnection.addInfo(
                        providerType.insertNewType(providerTypeBox.getValue().toString()));
                setUpProviderBox();
            }
            setUpProviderList();
        } else {
            //Show the warning when save failed 
            alert.setTitle("Fail to save");
            alert.setHeaderText(null);
            alert.setContentText("You did not fill all the required information");
            alert.showAndWait();
        }

    }

    @FXML
    private void editProviderTab(MouseEvent event
    ) {
        providerName.clear();
        nurseName.clear();
        nameOfPANP.clear();
        providerList.getSelectionModel().clearSelection();
        providerTypeBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void printProviderCard(MouseEvent event) throws SQLException {
        if (pdfPrint.printProviders(this.clientID)) {
            printProviderLabel.setStyle("-fx-text-fill:green");
            File f = new File("../patient-cards/ProvidersCard");
            Date date = new Date();
            printProviderLabel.setText("Provider information for this client was saved into the pdf file at " + formatter.format(date) + ". The location is \n " + f.getAbsolutePath());

        } else {
            printProviderLabel.setStyle("-fx-text-fill:red");
            printProviderLabel.setText("Failed to save the provider information for this client");
        }
    }

    //Insert or update family history of client info to database
    @FXML
    private void saveFamilyHistoryTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (!familyDiagnosis.getText().isEmpty()) {

            /*
        if (relativeSelectBox.getSelectionModel().getSelectedIndex() > -1) {
                //Update infomation for current selected relative
                FamilyHistory familyHistoryInfo = new FamilyHistory(this.clientID, familyDiagnosis.getText(),
                        (String) relativeSelectBox.getSelectionModel().getSelectedItem(), null, familyRealtionAge.getText());
                dbConnection.addInfo(familyHistoryInfo.getSQLUpdateNewDiagnosis(familyDiagnosis.getText()));
            } else {
                //Insert infomation for new relative
                FamilyHistory familyHistoryInfo = new FamilyHistory(this.clientID, familyDiagnosis.getText(),
                        (String) relativeSelectBox.getSelectionModel().getSelectedItem(), null, familyRealtionAge.getText());
                dbConnection.addInfo(familyHistoryInfo.getSQLInsert());
            }
             */
            //setUpDiagnosisList();
        }
        //familyDiagnosis.setEditable(false);
        //familyRealtionAge.setEditable(false);
    }

    @FXML
    private void editFamilyHistoryTab(MouseEvent event
    ) {
        //familyDiagnosis.setEditable(true);
        //familyRealtionAge.setEditable(true);
        familyDiagnosis.clear();
        familyRealtionAge.clear();
    }

    //Insert or update family history of client info to database
    @FXML
    private void saveMedicalEquipTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (!medicalEquipType.getText().isEmpty()) {

            if (equipmentList.getSelectionModel().getSelectedIndex() > -1) {
                MedicalEquipment medicalEquipmentInfo = new MedicalEquipment(this.clientID, (String) equipmentList.getSelectionModel().getSelectedItem(),
                        medicalEquipPrescribe.getText(), medicalEquipReason.getText(), null, medicalEquipNotes.getText());
                dbConnection.addInfo(medicalEquipmentInfo.getSQLUpdateEquipType(medicalEquipType.getText()));
            } else {
                MedicalEquipment medicalEquipmentInfo = new MedicalEquipment(this.clientID, medicalEquipType.getText(),
                        medicalEquipPrescribe.getText(), medicalEquipReason.getText(), null, medicalEquipNotes.getText());
                dbConnection.addInfo(medicalEquipmentInfo.getSQLInsert());
            }
            setUpMedicalEquipList();
        }

        //medicalEquipType.setEditable(false);
        //medicalEquipPrescribe.setEditable(false);
        //medicalEquipReason.setEditable(false);
        //medicalEquipNotes.setEditable(false);
    }

    @FXML
    private void editMedicalEquipTab(MouseEvent event
    ) {
        //medicalEquipType.setEditable(true);
        //medicalEquipPrescribe.setEditable(true);
        //medicalEquipReason.setEditable(true);     
        //medicalEquipNotes.setEditable(true);
        medicalEquipType.clear();
        medicalEquipPrescribe.clear();
        medicalEquipReason.clear();
        medicalEquipNotes.clear();
        equipmentList.getSelectionModel().clearSelection();
    }

    @FXML
    private void printMedicalCard(MouseEvent event) throws SQLException {
        if (pdfPrint.printMedications(this.clientID)) {
            printMedicationLabel.setStyle("-fx-text-fill:green");
            File f = new File("../patient-cards/MedicationsCard");
            Date date = new Date();
            printMedicationLabel.setText("Medication information for this client was saved into the pdf file at " + formatter.format(date) + ". The location is \n " + f.getAbsolutePath());
        } else {
            printMedicationLabel.setStyle("-fx-text-fill:red");
            printMedicationLabel.setText("Failed to save the medications information for this client");
        }
    }

    @FXML
    private void saveAlertTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (!alertsSpecific.getText().isEmpty() && !alertsSpecific.getText().isEmpty()) {
            if (alertList.getSelectionModel().getSelectedIndex() > -1) {
                String alertDetail = (String) alertList.getSelectionModel().getSelectedItem();
                Alerts alertInfo = new Alerts(this.clientID, alertsType.getValue().toString(),
                        alertDetail.substring(alertDetail.indexOf(":") + 2), altersDescrption.getText());
                dbConnection.addInfo(alertInfo.getSQLUpdateNewDetail(alertsSpecific.getText()));
            } else {
                Alerts alertInfo = new Alerts(this.clientID, alertsType.getValue().toString(),
                        alertsSpecific.getText(), altersDescrption.getText());
                dbConnection.addInfo(alertInfo.getSQLInsert());
            }
            AlertList alertList = new AlertList();
            //Check if this input type exist in provider type database or not,
            //save if it do not exits
            if (isEmpty(dbConnection.executeStatement(
                    alertList.checkType(alertsType.getValue().toString())))) {
                dbConnection.addInfo(
                        alertList.insertNewType(alertsType.getValue().toString()));
                setUpAlertTypeBox();
            }
            setUpAlertList();
        } else {
            //Show the warning when save failed 
            alert.setTitle("Fail to save");
            alert.setHeaderText(null);
            alert.setContentText("You did not fill all the required information");
            alert.showAndWait();
        }

        //alertsSpecific.setEditable(false);
        //altersDescrption.setEditable(false);
    }

    @FXML
    private void editAlertTab(MouseEvent event
    ) {
        //alertsSpecific.setEditable(true);
        //altersDescrption.setEditable(true);
        alertsType.getSelectionModel().clearSelection();
        alertsSpecific.clear();
        altersDescrption.clear();
        alertList.getSelectionModel().clearSelection();
    }

    @FXML
    private void printAlertsCard(MouseEvent event) throws SQLException {
        if (pdfPrint.printAlerts(this.clientID)) {
            printAlertLabel.setStyle("-fx-text-fill:green");
            File f = new File("../patient-cards/AlertsCard");
            Date date = new Date();
            printAlertLabel.setText("Alert information for this client was saved into the pdf file at " + formatter.format(date) + ". The location is \n " + f.getAbsolutePath());
        } else {
            printAlertLabel.setStyle("-fx-text-fill:red");
            printAlertLabel.setText("Failed to save the Alert information for this client");
        }
    }

    @FXML
    private void saveMedicalTab(MouseEvent event) throws SQLException, IOException {
        //Only save information when it is valid
        if (medicationGenericName.getValue() != null) {
            String startDate = "0000-00-00";
            String stoppedDate = "0000-00-00";
            if (medicationStartDate.getValue() != null) {
                startDate = medicationStartDate.getValue().toString();
            }
            if (medicationStoppedDate.getValue() != null) {
                stoppedDate = medicationStoppedDate.getValue().toString();
            }
            if (medicationList.getSelectionModel().getSelectedIndex() > -1) {
                Medication medicationInfo = new Medication(this.clientID, medicationClass.getValue().toString(),
                        (String) medicationList.getSelectionModel().getSelectedItem(),
                        medicationBrandName.getValue().toString(), medicationDose.getText(),
                        medicationFrequency.getText(), startDate,
                        (medicationPrescribedBy.getValue() != null ? medicationPrescribedBy.getValue().toString() : ""),
                        (medicationUsedFor.getValue() != null ? medicationProvider.getValue().toString() : ""), stoppedDate,
                        (medicationProvider.getValue() != null ? medicationProvider.getValue().toString() : ""));
                dbConnection.addInfo(medicationInfo.getSQLUpdateNewItem(medicationGenericName.getValue().toString()));
            } else {
                Medication medicationInfo = new Medication(this.clientID, medicationClass.getValue().toString(),
                        medicationGenericName.getValue().toString(), medicationBrandName.getValue().toString(),
                        medicationDose.getText(), medicationFrequency.getText(),
                        startDate, (medicationPrescribedBy.getValue() != null ? medicationPrescribedBy.getValue().toString() : ""),
                        (medicationUsedFor.getValue() != null ? medicationProvider.getValue().toString() : ""), stoppedDate,
                        (medicationProvider.getValue() != null ? medicationProvider.getValue().toString() : ""));
                dbConnection.addInfo(medicationInfo.getSQLInsert());
            }
            //Insert new medication if that is not in database
            MedicationList medication = new MedicationList(medicationClass.getValue() != null ? medicationClass.getValue().toString() : "",
                    medicationGenericName.getValue() != null ? medicationGenericName.getValue().toString() : "",
                    medicationBrandName.getValue() != null ? medicationBrandName.getValue().toString() : "");
            if (isEmpty(dbConnection.executeStatement(medication.checkMedication()))) {
                dbConnection.addInfo(medication.insertMedication());

                setUpGenericNameBox();
                setUpBrandNameBox();
            }
            setUpMedicationList();
        } else {
            //Show the warning when save failed 
            alert.setTitle("Fail to save");
            alert.setHeaderText(null);
            alert.setContentText("You did not fill all the required information");
            alert.showAndWait();
        }
    }

    @FXML
    private void editMedicalTab(MouseEvent event) throws SQLException {

        medicationClass.setValue(null);
        medicationGenericName.setOnAction(null);
        medicationGenericName.getSelectionModel().clearSelection();
        medicationBrandName.setOnAction(null);
        medicationBrandName.getSelectionModel().clearSelection();
        //Reset two comboBox for edit
        setUpGenericNameBox();
        setUpBrandNameBox();
        medicationGenericName.setOnAction(e1 -> getMedicationInfoByGeneric());
        medicationBrandName.setOnAction(e2 -> getMedicationInfoByBrand());
        medicationDose.clear();
        medicationFrequency.clear();
        medicationStartDate.setValue(null);
        medicationPrescribedBy.setValue(null);
        medicationUsedFor.setValue(null);
        medicationStoppedDate.setValue(null);
        medicationProvider.setValue(null);
        medicationList.getSelectionModel().clearSelection();
    }

    public void setUp(String nameID) throws SQLException {
        dbConnection = new DBConnection();

        //Get name that passed by home screen
        String name = nameID.split(" (?!.* )")[0];
        String id = nameID.split(" (?!.* )")[1].replaceAll("\\(|\\)", "");
        this.name = name;

        //Show client name in every tab
        clientName.setText(name);
        clientName.setStyle("-fx-text-fill:white; -fx-background-color: grey;");
        clientName1.setText(name);
        clientName1.setStyle("-fx-text-fill:white; -fx-background-color: grey;");
        clientName2.setText(name);
        clientName2.setStyle("-fx-text-fill:white; -fx-background-color: grey;");
        clientName3.setText(name);
        clientName3.setStyle("-fx-text-fill:white; -fx-background-color: grey;");
        clientName4.setText(name);
        clientName4.setStyle("-fx-text-fill:white; -fx-background-color: grey;");
        clientName5.setText(name);
        clientName5.setStyle("-fx-text-fill:white; -fx-background-color: grey;");
        clientName6.setText(name);
        clientName6.setStyle("-fx-text-fill:white; -fx-background-color: grey;");
        clientName7.setText(name);
        clientName7.setStyle("-fx-text-fill:white; -fx-background-color: grey;");

        //Seperate name to first name and last name
        //Set client with first name and last name 
        Client thisClient = new Client(id);
        ResultSet rs = dbConnection.executeStatement(thisClient.getByID());
        //Get clientID from result set
        if (rs.next()) {
            this.clientID = rs.getString("ClientID");
            String fname = rs.getString("Firstname");
            String lname = rs.getString("Lastname");
            String email = rs.getString("Email");
            String phone = rs.getString("Phone");
            String cell = rs.getString("Cell");
            String DOB = rs.getString("DOB");
            String identifer = rs.getString("ClientID");
            basicInfoSetUp(fname, lname, email, phone, cell, DOB);
            diagnoseTabSetUp();
            preventativeTabSetUp();
            preventationImmunTabSetUp();
            providersTabSetUp();
            familyHistoryTabSetUp();
            medicalEquipmentTabSetUp();
            alertsTabSetUp();
            medicationTabSetUp();
        }
    }

    //Set up the ListView for auto update (ListView will update after add or save a new item)
    /*
    public void setUpDiagnosisList() throws SQLException {
        FamilyHistory family = new FamilyHistory(this.clientID);
        ResultSet rs = dbConnection.executeStatement(family.getSQLSelectByRelative(
                (String) diagnoseList.getSelectionModel().getSelectedItem()));
        diagnoseList.getItems().clear();
        while (rs.next()) {
            diagnoseList.getItems().addAll(rs.getString("Diagnosis"));
        }
        rs.close();
    }
     */
    public void setUpProviderList() throws SQLException {
        Provider provider = new Provider(this.clientID);
        ResultSet rs = dbConnection.executeStatement(provider.getSQLSelect());
        providerList.getItems().clear();
        diagnosisType.getItems().clear();
        medicationProvider.getItems().clear();
        medicationPrescribedBy.getItems().clear();
        while (rs.next()) {
            providerList.getItems().addAll(rs.getString("Type") + ": " + rs.getString("Provider"));
            medicationProvider.getItems().addAll(rs.getString("Provider"));
            medicationPrescribedBy.getItems().addAll(rs.getString("Provider"));
        }
        rs.close();
        //Set up combo box for provider information
        ResultSet rs2 = dbConnection.executeStatement(provider.getProviderType());
        while (rs2.next()) {
            diagnosisType.getItems().addAll(rs2.getString("Type"));

        }
        new AutoCompleteBox(diagnosisType);
        new AutoCompleteBox(medicationProvider);
        new AutoCompleteBox(medicationPrescribedBy);
        rs2.close();
    }

    public void setUpMedicalEquipList() throws SQLException {
        MedicalEquipment medicalEquipment = new MedicalEquipment(this.clientID);
        ResultSet rs = dbConnection.executeStatement(medicalEquipment.getSQLSelect());
        equipmentList.getItems().clear();
        while (rs.next()) {
            equipmentList.getItems().addAll(rs.getString("EquipType"));
        }
        //rs.close();
    }

    public void setUpAlertList() throws SQLException {
        Alerts alert = new Alerts(this.clientID);
        ResultSet rs = dbConnection.executeStatement(alert.getSQLSelect());
        alertList.getItems().clear();
        while (rs.next()) {
            alertList.getItems().addAll(rs.getString("AlertType")
                    + ": " + rs.getString("AlertSpecific"));
        }
        rs.close();
    }

    public void setUpMedicationList() throws SQLException {
        Medication medication = new Medication(this.clientID);
        ResultSet rs = dbConnection.executeStatement(medication.getSQLSelect());
        medicationList.getItems().clear();
        while (rs.next()) {
            medicationList.getItems().addAll(rs.getString("GenericName"));
        }
        rs.close();
    }

    public void setUpPreventionImmunList() throws SQLException {
        PreventionImmunizations preventionImmunizations = new PreventionImmunizations(this.clientID);
        ResultSet rs = dbConnection.executeStatement(preventionImmunizations.getSQLSelect());
        preventionImmunList.getItems().clear();
        while (rs.next()) {
            preventionImmunList.getItems().addAll(rs.getString("Name"));
        }
        rs.close();
    }

    public void setUpPreventiveList() throws SQLException {
        Preventive preventive = new Preventive(this.clientID);
        ResultSet rs = dbConnection.executeStatement(preventive.getSQLSelect());
        preventiveList.getItems().clear();
        while (rs.next()) {
            preventiveList.getItems().addAll(rs.getString("PreventiveType"));
        }
        rs.close();
    }

    public void setUpDiagnoseList() throws SQLException {
        Diagnose diagnose = new Diagnose(this.clientID);
        ResultSet rs = dbConnection.executeStatement(diagnose.getSQLSelect());
        diagnoseList.getItems().clear();
        medicationUsedFor.getItems().clear();
        while (rs.next()) {
            diagnoseList.getItems().addAll(rs.getString("Diagnosis"));
            medicationUsedFor.getItems().addAll(rs.getString("Diagnosis"));
        }
        rs.close();
    }

    public void setUpDiagnoseBox() throws SQLException {
        Diagnose diagnose = new Diagnose(this.clientID);
        diagnoseDescription.getItems().clear();
        ResultSet rs = dbConnection.executeStatement(diagnose.getDiagnosesList());
        while (rs.next()) {
            diagnoseDescription.getItems().addAll(rs.getString("Diagnosis"));
        }
        new AutoCompleteBox(diagnoseDescription);
        rs.close();
    }

    public void setUpMonitorList() throws SQLException {
        Monitor monitor = new Monitor(this.clientID);
        ResultSet rs = dbConnection.executeStatement(monitor.getSQLSelectByReason(
                (String) diagnoseList.getSelectionModel().getSelectedItem()));
        monitorList.getItems().clear();
        while (rs.next()) {
            monitorList.getItems().addAll(rs.getString("SpecificName"));
        }
        rs.close();
    }

    public void setUpProviderBox() throws SQLException {
        ProviderType providerType = new ProviderType();
        providerTypeBox.getItems().clear();
        ResultSet rs = dbConnection.executeStatement(providerType.getProviderType());
        while (rs.next()) {
            providerTypeBox.getItems().addAll(rs.getString("Type"));
        }
        new AutoCompleteBox(providerTypeBox);
        rs.close();
    }

    public void setUpAlertTypeBox() throws SQLException {
        AlertList alertType = new AlertList();
        ResultSet rs = dbConnection.executeStatement(alertType.getProviderType());
        alertsType.getItems().clear();
        while (rs.next()) {
            alertsType.getItems().addAll(rs.getString("AlertType"));
        }
        rs.close();
        new AutoCompleteBox(alertsType);
    }

    public void setUpMedicationClassBoxByGeneric(String geneName) throws SQLException {
        MedicationList medicationList = new MedicationList();
        ResultSet rs = dbConnection.executeStatement(medicationList.getMedicationClassByGeneName(geneName));
        medicationClass.getItems().clear();
        while (rs.next()) {
            medicationClass.getItems().addAll(rs.getString("Class"));
        }
        rs.close();
        new AutoCompleteBox(medicationClass);
    }

    public void setUpMedicationClassBoxByBrand(String brandName) throws SQLException {
        MedicationList medicationList = new MedicationList();
        ResultSet rs = dbConnection.executeStatement(medicationList.getMedicationClassByBrandName(brandName));
        medicationClass.getItems().clear();
        while (rs.next()) {
            medicationClass.getItems().addAll(rs.getString("Class"));
        }
        rs.close();
        new AutoCompleteBox(medicationClass);
    }

    public void setUpGenericNameBox() throws SQLException {
        MedicationList medicationList = new MedicationList();
        ResultSet rs = dbConnection.executeStatement(medicationList.getGenericaName());
        medicationGenericName.getItems().clear();
        while (rs.next()) {
            medicationGenericName.getItems().addAll(rs.getString("GenericName"));
        }
        rs.close();
        new AutoCompleteBox(medicationGenericName);
    }

    public void setUpGenericNameBox(String brandName) throws SQLException {
        MedicationList medicationList = new MedicationList();
        ResultSet rs = dbConnection.executeStatement(medicationList.getGenericNameByBrandName(brandName));
        medicationGenericName.getItems().clear();
        while (rs.next()) {
            medicationGenericName.getItems().addAll(rs.getString("GenericName"));
        }
        rs.close();
        new AutoCompleteBox(medicationGenericName);
    }

    public void setUpBrandNameBox() throws SQLException {
        MedicationList medicationList = new MedicationList();
        ResultSet rs = dbConnection.executeStatement(medicationList.getBrandName());
        medicationBrandName.getItems().clear();
        while (rs.next()) {
            medicationBrandName.getItems().addAll(rs.getString("BrandName"));
        }
        rs.close();
        new AutoCompleteBox(medicationBrandName);
    }

    public void setUpBrandNameBox(String geneName) throws SQLException {
        MedicationList medicationList = new MedicationList();
        ResultSet rs = dbConnection.executeStatement(medicationList.getBrandNameByGenericName(geneName));
        medicationBrandName.getItems().clear();
        while (rs.next()) {
            medicationBrandName.getItems().addAll(rs.getString("BrandName"));
        }
        rs.close();
        new AutoCompleteBox(medicationBrandName);
    }

    // Check if rs is filled or not
    public static boolean isEmpty(ResultSet rs) {
        boolean isEmpty = true;
        try {
            while (rs.next()) {
                isEmpty = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }
}
