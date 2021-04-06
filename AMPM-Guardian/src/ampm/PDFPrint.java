/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author johnz
 */
public class PDFPrint {

    DBConnection dbConnection;

    PreparedStatement preparedStatement;

    //Print provider ioformation from the database for specific client to pdf file and save it
    public void printProviders(String clientID) throws SQLException {
        //Set counter for item and page, i is item and j is page
        int i = 1;
        int j = 1;
        //System.out.println(i + "" + j);

        Provider provider = new Provider(clientID);
        ResultSet rs = dbConnection.executeStatement(provider.getSQLSelect());
        try {
            System.out.println("here");
            //
            while (i < 10 && rs.next()) {
                System.out.println("Enter loop");

                PDDocument pDDocument = PDDocument.load(new File("./src/ProvidersTemp.pdf"));
                PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
                //Use while loop to put the data from database into pdf file
                do {
                    System.out.println(i + "" + j);
                    PDField field = pDAcroForm.getField("Provider_Type_" + i);
                    field.setValue(rs.getString("Type"));
                    System.out.println(rs.getString("Type"));

                    field = pDAcroForm.getField("Provider_" + i);
                    field.setValue(rs.getString("Provider"));
                    //System.out.println(rs.getString("Provider"));

                    field = pDAcroForm.getField("Nurse_" + i);
                    field.setValue(rs.getString("Nurse"));
                    //System.out.println(rs.getString("Nurse"));

                    field = pDAcroForm.getField("PANP_" + i);
                    field.setValue(rs.getString("PANP"));
                    //System.out.println(rs.getString("PANP"));

                    i++;
                    if (i == 11) {
                        break;
                    }
                } while (rs.next());
                pDDocument.save("./patient-cards/ProvidersCard/ProvidersCard-" + clientID + "-page" + j + ".pdf");
                pDDocument.close();
                if (i > 10) {
                    i = 1;
                    j++;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Print medication ioformation from the database for specific client to pdf file and save it
    public void printMedications(String clientID) throws SQLException {
        int i = 1;
        try {
            PDDocument pDDocument = PDDocument.load(new File("../src/MedicationsTemp.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            Medication medication = new Medication(clientID);
            ResultSet rs = dbConnection.executeStatement(medication.getSQLSelect());

            //Use while loop to put the data from database into pdf file
            while (rs.next()) {
                PDField field = pDAcroForm.getField("Class_" + i);
                field.setValue(rs.getString("Class"));
                field = pDAcroForm.getField("Generic_" + i);
                field.setValue(rs.getString("GenericName"));
                field = pDAcroForm.getField("Brand_" + i);
                field.setValue(rs.getString("BrandName"));
                field = pDAcroForm.getField("Dose_" + i);
                field.setValue(rs.getString("Dose"));
                field = pDAcroForm.getField("Freq_" + i);
                field.setValue(rs.getString("Frequency"));
                field = pDAcroForm.getField("Start Date_" + i);
                field.setValue(rs.getString("StartDate"));
                field = pDAcroForm.getField("Prescribed by_" + i);
                field.setValue(rs.getString("PrescribedBy"));
                field = pDAcroForm.getField("Used for_" + i);
                field.setValue(rs.getString("UsedFor"));
                field = pDAcroForm.getField("Stopped by_" + i);
                field.setValue(rs.getString("Provider"));
                field = pDAcroForm.getField("Stopped Date_" + i);
                field.setValue(rs.getString("DateStopped"));
                i++;
            }

            //field = pDAcroForm.getField("txt_2");
            //field.setValue("This is a second field printed by Java");
            pDDocument.save("../patient-cards/MedicationsCard/MedicationsCard-" + clientID + ".pdf");
            pDDocument.close();
            //Open file if that exist after save pdf file
            /*
            try {

                if ((new File("./src/MedicationCard/MedicationTemp-output-"+clientID+".pdf")).exists()) {

                    Process p = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler ./src/MedicationCard/MedicationTemp-output-"+clientID+".pdf");
                    p.waitFor();

                } else {

                    System.out.println("File is not exists");

                }

                System.out.println("Done");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Print medication ioformation from the database for specific client to pdf file and save it
    public void printDiagnoses(String clientID) throws SQLException {
        int i = 1;
        System.out.println("1");
        try {
            PDDocument pDDocument = PDDocument.load(new File("../src/DiagnosesTemp.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            Diagnose diagnose = new Diagnose(clientID);
            ResultSet rs = dbConnection.executeStatement(diagnose.getSQLSelect());
            System.out.println("2");

            //Use while loop to put the data from database into pdf file
            while (rs.next()) {
                PDField field = pDAcroForm.getField("Problem List_" + i);
                field.setValue(rs.getString("Diagnosis"));
                System.out.println(rs.getString("Diagnosis"));

                field = pDAcroForm.getField("Start Date_" + i);
                field.setValue(rs.getString("StartDate"));
                System.out.println(rs.getString("StartDate"));

                field = pDAcroForm.getField("Diagnosed by_" + i);
                field.setValue(rs.getString("DiagnosedBy"));
                System.out.println(rs.getString("DiagnosedBy"));

                i++;
                System.out.println("3");

            }
            pDDocument.save("../patient-cards/DiagnosesCard/DiagnosesCard-" + clientID + ".pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Print alert ioformation from the database for specific client to pdf file and save it
    public void printAlerts(String clientID) throws SQLException {
        int i = 1;
        System.out.println("1");
        try {
            PDDocument pDDocument = PDDocument.load(new File("../src/AlertsTemp.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            Alert alert = new Alert(clientID);
            ResultSet rs = dbConnection.executeStatement(alert.getSQLSelect());
            System.out.println("2");

            //Use while loop to put the data from database into pdf file
            while (rs.next()) {
                PDField field = pDAcroForm.getField("Alert_" + i);
                field.setValue(rs.getString("AlertType") + ": " + rs.getString("AlertSpecific"));
                System.out.println(rs.getString("AlertType") + ": " + rs.getString("AlertSpecific"));

                field = pDAcroForm.getField("Reaction_" + i);
                field.setValue(rs.getString("AlertDescription"));
                System.out.println(rs.getString("AlertDescription"));

                i++;
                System.out.println("3");

            }
            pDDocument.save("../patient-cards/AlertsCard/AlertsCard-" + clientID + ".pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
