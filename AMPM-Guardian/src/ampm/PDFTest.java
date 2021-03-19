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
public class PDFTest {

    DBConnection dbConnection;

    PreparedStatement preparedStatement;

    public static void main(String[] args) {
        /*
        try {
            PDDocument pDDocument = PDDocument.load(new File("C:/Users/johnz/OneDrive/Desktop/pdftest.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            PDField field = pDAcroForm.getField("text_1");
            field.setValue("This is a firddadst by Java");
            //field = pDAcroForm.getField("txt_2");
            //field.setValue("This is a second field printed by Java");
            pDDocument.save("C:/Users/johnz/OneDrive/Desktop/pdftest.pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    public void familyHistoryTest(String clientID, String diagnosis) throws SQLException {

        try {
            PDDocument pDDocument = PDDocument.load(new File("C:/Users/johnz/OneDrive/Desktop/FamilyHistoryTest.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            FamilyHistory familyHistoryInfo = new FamilyHistory(clientID);
            ResultSet rs = dbConnection.executeStatement(familyHistoryInfo.getSQLSelect(diagnosis));
            System.out.println("1");
            if (rs.next()) {
                PDField field = pDAcroForm.getField("relative1");
                field.setValue(rs.getString("Relation"));
                field = pDAcroForm.getField("age1");
                field.setValue(rs.getString("Age"));
                System.out.println("2");
            }
            if (rs.next()) {
                PDField field = pDAcroForm.getField("relative2");
                field.setValue(rs.getString("Relation"));
                field = pDAcroForm.getField("age2");
                field.setValue(rs.getString("Age"));
                System.out.println("3");

            }
            //field = pDAcroForm.getField("txt_2");
            //field.setValue("This is a second field printed by Java");
            pDDocument.save("C:/Users/johnz/OneDrive/Desktop/FamilyHistoryTest-output.pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMedication(String clientID) throws SQLException {
        int i = 1;
        try {
            PDDocument pDDocument = PDDocument.load(new File("C:/Users/johnz/OneDrive/Desktop/MedicationTemp.pdf"));
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
            pDDocument.save("C:/Users/johnz/OneDrive/Desktop/MedicationTemp-output.pdf");
            pDDocument.close();
            //Open file if that exist after save pdf file
            try {

                if ((new File("C:/Users/johnz/OneDrive/Desktop/MedicationTemp-output.pdf")).exists()) {

                    Process p = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler C:/Users/johnz/OneDrive/Desktop/MedicationTemp-output.pdf");
                    p.waitFor();

                } else {

                    System.out.println("File is not exists");

                }

                System.out.println("Done");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    catch (IOException e

    
        ) {
            e.printStackTrace();
    }
}
}
