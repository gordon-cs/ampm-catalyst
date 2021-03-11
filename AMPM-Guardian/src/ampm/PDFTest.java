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

    public static void main(String[] args) {

        try {
            PDDocument pDDocument = PDDocument.load(new File("C:/Users/johnz/OneDrive/Desktop/pdftest.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            PDField field = pDAcroForm.getField("text_1");
            field.setValue("This is a first by Java");
            //field = pDAcroForm.getField("txt_2");
            //field.setValue("This is a second field printed by Java");
            pDDocument.save("C:/Users/johnz/OneDrive/Desktop/pdftest.pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        try {
            PDDocument pDDocument = PDDocument.load(new File("C:/Users/johnz/OneDrive/Desktop/pdftest.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            PDField field = pDAcroForm.getField("text_1");
            field.setValue("This is a by Java");
            //field = pDAcroForm.getField("txt_2");
            //field.setValue("This is a second field printed by Java");
            pDDocument.save("C:/Users/johnz/OneDrive/Desktop/pdftest.pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
