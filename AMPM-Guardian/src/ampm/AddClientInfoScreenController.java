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
    private TextField diagosedDate;
    @FXML
    private TextField diagnosisDoctor;
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
    private TextField medicalEquipStartDate;
    @FXML
    private ListView equipmentList;
    @FXML
    private TextArea medicalEquipNotes;

//Alerts Tab
    @FXML
    private TextField alertsType;
    @FXML
    private TextField alertsSpecific;
    @FXML
    private TextArea altersDescrption;
    @FXML
    private Button addNewAlert;
    @FXML
    private ListView alertList;

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
    private TextField medicationStartDate;
    @FXML
    private TextField medicationPrescribedBy;
    @FXML
    private TextField medicationUsedFor;
    @FXML
    private TextField medicationStoppedDate;
    @FXML
    private TextField medicationProvider;
    @FXML
    private ListView medicationList;

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

    //Set up the TextField in BasicInfo tab and make TextField uneditable before click edit button
    private void basicInfoSetUp(String fname, String lname, String email, String phone) {
        firstName.setText(fname);
        lastName.setText(lname);
        phoneNumber.setText(phone);
        emailAddress.setText(email);
        //phoneNumber.setEditable(false);
        //firstName.setEditable(false);
        //lastName.setEditable(false);
        //emailAddress.setEditable(false);

    }

    //Set up the TextField and ListView in Diagnose tab and make TextField uneditable before click edit button or new in the ListView
    private void diagnoseTabSetUp() throws SQLException {
        setUpDiagnoseList();
        Diagnose diagnose = new Diagnose(this.clientID);
        diagnoseList.setOnMouseClicked(e -> {
            /*if (diagnoseList.getSelectionModel().getSelectedItem().equals("New")) {
                //Clear the TextField and monitor infomration for create new item
                diagnoseDescription.clear();
                diagnosisDoctor.clear();
                monitorList.getItems().clear();
                monitorSpecific.clear();

                //Make TextField editable
                //diagnoseDescription.setEditable(true);
                //diagnosisDoctor.setEditable(true);
            } else {
             */
            try {
                setUpMonitorList();
                //Show the releated information when click one item in ListView
                ResultSet rs = dbConnection.executeStatement(diagnose.getSQLSelectByDiagnosis(
                        (String) diagnoseList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    diagnoseDescription.setText(rs.getString("Diagnosis"));
                    diagnosisDoctor.setText(rs.getString("DiagnosedBy"));
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
            if (monitorList.getSelectionModel().getSelectedItem().equals("New")) {
                monitorSpecific.clear();
                //monitorSpecific.setEditable(true);

            } else {
                try {
                    //Show the releated information when click one item in ListView
                    ResultSet rs = dbConnection.executeStatement(monitor.getSQLSelectByName(
                            (String) monitorList.getSelectionModel().getSelectedItem()));
                    while (rs.next()) {
                        monitorSpecific.setText(rs.getString("SpecificName"));
                    }
                    monitorSpecific.setEditable(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        );

    }

    //Set up the TextField and ListView in Preventive tab and make TextField uneditable before click edit button or new in the ListView
    private void preventativeTabSetUp() throws SQLException {
        setUpPreventiveList();
        Preventive preventive = new Preventive(this.clientID);
        preventiveList.setOnMouseClicked(e -> {
            /*
            if (preventiveList.getSelectionModel().getSelectedItem().equals("New")) {
                //Clear the TextField for create new item
                preventiveType.clear();
                preventiveDateDone.clear();
                preventivePrescribedBy.clear();
                preventiveNextDueDate.clear();
                preventiveSource.clear();
                preventiveFrequency.clear();

                //Make TextField editable
                //preventiveType.setEditable(true);
                //preventiveDateDone.setEditable(true);
                //preventivePrescribedBy.setEditable(true);
                //preventiveNextDueDate.setEditable(true);
                //preventiveSource.setEditable(true);
                //preventiveFrequency.setEditable(true);
            } else {
             */
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
                //Make TextField uneditable
                //preventiveType.setEditable(false);
                //preventiveDateDone.setEditable(false);
                //preventivePrescribedBy.setEditable(false);
                //preventiveNextDueDate.setEditable(false);
                //preventiveSource.setEditable(false);
                //preventiveFrequency.setEditable(false);
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
                ResultSet rs = dbConnection.executeStatement(provider.getSQLSelectByProvider(
                        (String) providerList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    provideType.setText(rs.getString("Type"));
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
        relativeSelectBox.getItems().addAll(
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
        FamilyHistory family = new FamilyHistory(this.clientID);
        //Select event for relative combo box
        relativeSelectBox.setOnAction(e -> {
            relativeList.getItems().clear();
            //Auto-fill the textfield based on selected item.
            try {
                ResultSet rs = dbConnection.executeStatement(family.getSQLSelectByRelative(
                        (String) relativeSelectBox.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    familyDiagnosis.setText(rs.getString("Diagnoses"));
                    familyRealtionAge.setText(rs.getString("Age"));
                }
                //familyDiagnosis.setEditable(false);
                //familyRealtionAge.setEditable(false);
                setUpDiagnosesList();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        //Mouse Click event for this ListView
        relativeList.setOnMouseClicked(e -> {
            familyDiagnosis.setText((String) relativeList.getSelectionModel().getSelectedItem());
            //familyDiagnosis.setEditable(false);
            if (relativeList.getSelectionModel().getSelectedItem().equals("New")) {
                familyDiagnosis.clear();
                //familyDiagnosis.setEditable(true);
            }
        });
    }

    private void medicalEquipmentTabSetUp() throws SQLException {
        setUpMedicalEquipList();
        MedicalEquipment medicalEquip = new MedicalEquipment(this.clientID);
        equipmentList.setOnMouseClicked(e -> {
            /*
            if (equipmentList.getSelectionModel().getSelectedItem().equals("New")) {
                medicalEquipType.clear();
                medicalEquipPrescribe.clear();
                medicalEquipReason.clear();
                medicalEquipNotes.clear();
                //medicalEquipType.setEditable(true);
                //medicalEquipPrescribe.setEditable(true);
                //medicalEquipReason.setEditable(true);
                //medicalEquipNotes.setEditable(true);
            } else {
             */
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
        Alert alert = new Alert(this.clientID);
        alertList.setOnMouseClicked(e -> {

            String alertDetail = (String) alertList.getSelectionModel().getSelectedItem();
            try {
                ResultSet rs = dbConnection.executeStatement(alert.getSQLSelectByType(
                        alertDetail.substring(alertDetail.indexOf(":") + 1)));
                while (rs.next()) {
                    alertsType.setText(rs.getString("AlertSpecific"));
                    alertsSpecific.setText(rs.getString("AlertSpecific"));
                    altersDescrption.setText(rs.getString("AlertDescription"));
                }
                //alertsSpecific.setEditable(false);
                //altersDescrption.setEditable(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
    }

    private void medicationTabSetUp() throws SQLException {
        setUpMedicationList();
        Medication medication = new Medication(this.clientID);
        medicationList.setOnMouseClicked(e -> {
            /*
            if (medicationList.getSelectionModel().getSelectedItem().equals("New")) {
                medicationClass.clear();
                medicationGenericName.clear();
                medicationBrandName.clear();
                medicationDose.clear();
                medicationFrequency.clear();
                medicationStartDate.clear();
                medicationPrescribedBy.clear();
                medicationUsedFor.clear();
                medicationStoppedDate.clear();
                medicationProvider.clear();
                //medicationClass.setEditable(true);
                //medicationGenericName.setEditable(true);
                //medicationBrandName.setEditable(true);
                //medicationDose.setEditable(true);
                //medicationFrequency.setEditable(true);
                //medicationStartDate.setEditable(true);
                //medicationPrescribedBy.setEditable(true);
                //medicationUsedFor.setEditable(true);
                //medicationStoppedDate.setEditable(true);
                //medicationProvider.setEditable(true);
            } else {
             */
            try {
                ResultSet rs = dbConnection.executeStatement(medication.getSQLSelectByGenericName(
                        (String) medicationList.getSelectionModel().getSelectedItem()));
                while (rs.next()) {
                    medicationClass.setText(rs.getString("Class"));
                    medicationGenericName.setText(rs.getString("GenericName"));
                    medicationBrandName.setText(rs.getString("BrandName"));
                    medicationDose.setText(rs.getString("Dose"));
                    medicationFrequency.setText(rs.getString("Frequency"));
                    medicationStartDate.setText(rs.getString("StartDate"));
                    medicationPrescribedBy.setText(rs.getString("PrescribedBy"));
                    medicationUsedFor.setText(rs.getString("UsedFor"));
                    medicationStoppedDate.setText(rs.getString("DateStopped"));
                    medicationProvider.setText(rs.getString("Provider"));
                }
                //medicationClass.setEditable(false);
                //medicationGenericName.setEditable(false);
                //medicationBrandName.setEditable(false);
                //medicationDose.setEditable(false);
                //medicationFrequency.setEditable(false);
                //medicationStartDate.setEditable(false);
                //medicationPrescribedBy.setEditable(false);
                //medicationUsedFor.setEditable(false);
                //medicationStoppedDate.setEditable(false);
                //medicationProvider.setEditable(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        );
        Medication medicaiton = new Medication(this.clientID);
        ResultSet rs = dbConnection.executeStatement(medicaiton.getSQLSelect());

        if (rs.next()) {

        }
    }

    //Update client basic info
    @FXML
    private void saveBasicTab(MouseEvent event) throws SQLException, IOException {
        Client updateInfo = new Client(this.clientID, firstName.getText(), lastName.getText(),
                emailAddress.getText(), phoneNumber.getText(), new Timestamp(new Date().getTime()),
                null);
        dbConnection.addInfo(updateInfo.getSQLUpdate());

    }

    @FXML
    //Make TextField in this tab editable
    private void editBasicTab(MouseEvent event) {
        //firstName.setEditable(true);
        //lastName.setEditable(true);
        //phoneNumber.setEditable(true);
        //emailAddress.setEditable(true);
    }

    //Insert or update diagnose info to database
    @FXML
    private void saveDiagnoseTab(MouseEvent event) throws SQLException, IOException {

        if (diagnoseList.getSelectionModel().getSelectedIndex() > -1) {
            //Update infomation for current selected relative
            Diagnose diagnoseInfo = new Diagnose(this.clientID, (String) diagnoseList.getSelectionModel().getSelectedItem(), diagosedDate.getText(), diagnosisDoctor.getText());
            dbConnection.addInfo(diagnoseInfo.getSQLUpdateNewItem(diagnoseDescription.getText()));
        } else {
            //Insert infomation for new relative
            Diagnose diagnoseInfo = new Diagnose(this.clientID, diagnoseDescription.getText(), diagosedDate.getText(), diagnosisDoctor.getText());
            dbConnection.addInfo(diagnoseInfo.getSQLInsert());
        }
        //Reset the ListView (auto update ListView)
        setUpDiagnoseList();
        //diagnoseDescription.setEditable(false);
        //diagnosisDoctor.setEditable(false);

        if (monitorList.getSelectionModel().getSelectedIndex() > -1) {
            //Update infomation for current selected relative
            Monitor monitorInfo = new Monitor(this.clientID, diagnoseDescription.getText(), (String) monitorList.getSelectionModel().getSelectedItem());
            dbConnection.addInfo(monitorInfo.getSQLEdit(monitorSpecific.getText()));
        } else {
            //Insert infomation for new relative
            Monitor monitorInfo = new Monitor(this.clientID, diagnoseDescription.getText(), monitorSpecific.getText());
            dbConnection.addInfo(monitorInfo.getSQLInsert());
        }
        setUpMonitorList();
    }

    @FXML
    private void editDiagnoseTab(MouseEvent event) {
        //diagnoseDescription.setEditable(true);
        //diagnosisDoctor.setEditable(true);
        diagnoseDescription.clear();
        diagnosisDoctor.clear();
        diagnoseList.getSelectionModel().clearSelection();
        monitorList.getItems().clear();
        monitorSpecific.clear();
    }

    //Insert or update preventive info to database
    @FXML
    private void savePreventiveTab(MouseEvent event) throws SQLException, IOException {
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
        if (providerList.getSelectionModel().getSelectedIndex() > -1) {
            //Update infomation for current selected relative
            Provider providerInfo = new Provider(this.clientID, provideType.getText(),
                    (String) providerList.getSelectionModel().getSelectedItem(), nurseName.getText(), nameOfPANP.getText());
            dbConnection.addInfo(providerInfo.getSQLEdit(providerName.getText()));
        } else {
            //Insert infomation for new relative
            Provider providerInfo = new Provider(this.clientID, provideType.getText(),
                    providerName.getText(), nurseName.getText(), nameOfPANP.getText());
            dbConnection.addInfo(providerInfo.getSQLInsert());
        }
        setUpProviderList();
        //provideType.setEditable(false);
        //providerName.setEditable(false);
        //nurseName.setEditable(false);
        //nameOfPANP.setEditable(false);
    }

    @FXML
    private void editProviderTab(MouseEvent event) {
        //provideType.setEditable(true);
        //providerName.setEditable(true);
        //nurseName.setEditable(true);
        //nameOfPANP.setEditable(true);

        provideType.clear();
        providerName.clear();
        nurseName.clear();
        nameOfPANP.clear();
        providerList.getSelectionModel().clearSelection();
    }

    //Insert or update family history of client info to database
    @FXML
    private void saveFamilyHistoryTab(MouseEvent event) throws SQLException, IOException {
        if (relativeSelectBox.getSelectionModel().getSelectedIndex() > -1) {
            //Update infomation for current selected relative
            FamilyHistory familyHistoryInfo = new FamilyHistory(this.clientID, familyDiagnosis.getText(),
                    (String) relativeSelectBox.getSelectionModel().getSelectedItem(), null, familyRealtionAge.getText());
            dbConnection.addInfo(familyHistoryInfo.getSQLUpdateNewDiagnoses(familyDiagnosis.getText()));
        } else {
            //Insert infomation for new relative
            FamilyHistory familyHistoryInfo = new FamilyHistory(this.clientID, familyDiagnosis.getText(),
                    (String) relativeSelectBox.getSelectionModel().getSelectedItem(), null, familyRealtionAge.getText());
            dbConnection.addInfo(familyHistoryInfo.getSQLInsert());
        }
        setUpDiagnosesList();
        //familyDiagnosis.setEditable(false);
        //familyRealtionAge.setEditable(false);
    }

    @FXML
    private void editFamilyHistoryTab(MouseEvent event) {
        //familyDiagnosis.setEditable(true);
        //familyRealtionAge.setEditable(true);
        familyDiagnosis.clear();
        familyRealtionAge.clear();
    }

    //Insert or update family history of client info to database
    @FXML
    private void saveMedicalEquipTab(MouseEvent event) throws SQLException, IOException {
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
        //medicalEquipType.setEditable(false);
        //medicalEquipPrescribe.setEditable(false);
        //medicalEquipReason.setEditable(false);
        //medicalEquipNotes.setEditable(false);
    }

    @FXML
    private void editMedicalEquipTab(MouseEvent event) {
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
    private void saveAlertTab(MouseEvent event) throws SQLException, IOException {
        if (alertList.getSelectionModel().getSelectedIndex() > -1) {
            String alertDetail = (String) alertList.getSelectionModel().getSelectedItem();
            Alert alertInfo = new Alert(this.clientID, alertsType.getText(),
                    alertDetail.substring(alertDetail.indexOf(":") + 1), altersDescrption.getText());
            dbConnection.addInfo(alertInfo.getSQLUpdateNewDetail(alertsSpecific.getText()));
        } else {
            Alert alertInfo = new Alert(this.clientID, alertsType.getText(),
                    alertsSpecific.getText(), altersDescrption.getText());
            dbConnection.addInfo(alertInfo.getSQLInsert());
        }
        setUpAlertList();
        //alertsSpecific.setEditable(false);
        //altersDescrption.setEditable(false);
    }

    @FXML
    private void editAlertTab(MouseEvent event) {
        //alertsSpecific.setEditable(true);
        //altersDescrption.setEditable(true);
        alertsType.clear();
        alertsSpecific.clear();
        altersDescrption.clear();
        alertList.getSelectionModel().clearSelection();
    }

    @FXML
    private void saveMedicalTab(MouseEvent event) throws SQLException, IOException {
        if (medicationList.getSelectionModel().getSelectedIndex() > -1) {
            Medication medicationInfo = new Medication(this.clientID, medicationClass.getText(),
                    (String) medicationList.getSelectionModel().getSelectedItem(),
                    medicationBrandName.getText(), medicationDose.getText(),
                    medicationFrequency.getText(), medicationStartDate.getText(),
                    medicationPrescribedBy.getText(), medicationUsedFor.getText(),
                    medicationStoppedDate.getText(), medicationProvider.getText());
            dbConnection.addInfo(medicationInfo.getSQLUpdateNewItem(medicationGenericName.getText()));
        } else {
            Medication medicationInfo = new Medication(this.clientID, medicationClass.getText(),
                    medicationGenericName.getText(), medicationBrandName.getText(),
                    medicationDose.getText(), medicationFrequency.getText(),
                    medicationStartDate.getText(), medicationPrescribedBy.getText(),
                    medicationUsedFor.getText(), medicationStoppedDate.getText(),
                    medicationProvider.getText());
            dbConnection.addInfo(medicationInfo.getSQLInsert());
        }
        setUpMedicationList();
        //medicationClass.setEditable(false);
        //medicationGenericName.setEditable(false);
        // medicationBrandName.setEditable(false);
        // medicationDose.setEditable(false);
        // medicationFrequency.setEditable(false);
        //medicationStartDate.setEditable(false);
        // medicationPrescribedBy.setEditable(false);
        // medicationUsedFor.setEditable(false);
        //medicationStoppedDate.setEditable(false);
        //medicationProvider.setEditable(false);
    }

    @FXML
    private void editMedicalTab(MouseEvent event) {
        //medicationClass.setEditable(true);
        //medicationGenericName.setEditable(true);
        //medicationBrandName.setEditable(true);
        //medicationDose.setEditable(true);
        //medicationFrequency.setEditable(true);
        //medicationStartDate.setEditable(true);
        //medicationPrescribedBy.setEditable(true);
        //medicationUsedFor.setEditable(true);
        //medicationStoppedDate.setEditable(true);
        //medicationProvider.setEditable(true);

        medicationClass.clear();
        medicationGenericName.clear();
        medicationBrandName.clear();
        medicationDose.clear();
        medicationFrequency.clear();
        medicationStartDate.clear();
        medicationPrescribedBy.clear();
        medicationUsedFor.clear();
        medicationStoppedDate.clear();
        medicationProvider.clear();
        medicationList.getSelectionModel().clearSelection();
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
            preventationImmunTabSetUp();
            providersTabSetUp();
            familyHistoryTabSetUp();
            medicalEquipmentTabSetUp();
            alertsTabSetUp();
            medicationTabSetUp();
        }

    }

    //Set up the ListView for auto update (ListView will update after add or save a new item)
    public void setUpDiagnosesList() throws SQLException {
        FamilyHistory family = new FamilyHistory(this.clientID);
        ResultSet rs = dbConnection.executeStatement(family.getSQLSelectByRelative(
                (String) relativeSelectBox.getSelectionModel().getSelectedItem()));
        relativeList.getItems().clear();
        while (rs.next()) {
            relativeList.getItems().addAll(rs.getString("Diagnoses"));
        }
        rs.close();
    }

    public void setUpProviderList() throws SQLException {
        Provider provider = new Provider(this.clientID);
        ResultSet rs = dbConnection.executeStatement(provider.getSQLSelect());
        providerList.getItems().clear();
        while (rs.next()) {
            providerList.getItems().addAll(rs.getString("Provider"));
        }
        rs.close();
    }

    public void setUpMedicalEquipList() throws SQLException {
        MedicalEquipment medicalEquipment = new MedicalEquipment(this.clientID);
        ResultSet rs = dbConnection.executeStatement(medicalEquipment.getSQLSelect());
        equipmentList.getItems().clear();
        while (rs.next()) {
            equipmentList.getItems().addAll(rs.getString("EquipType"));
        }
        rs.close();
    }

    public void setUpAlertList() throws SQLException {
        Alert alert = new Alert(this.clientID);
        ResultSet rs = dbConnection.executeStatement(alert.getSQLSelect());
        alertList.getItems().clear();
        while (rs.next()) {
            alertList.getItems().addAll(rs.getString("AlertType")
                    + ":" + rs.getString("AlertSpecific"));
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
        while (rs.next()) {
            diagnoseList.getItems().addAll(rs.getString("Diagnosis"));
        }
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

}
