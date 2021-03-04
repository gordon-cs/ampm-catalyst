package ampm;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
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

/** The PDFManager handles the creation of a client's full medical history book
 *
 * @author benab
 */
public class PDFManager {
    
    private PDDocument document = null;
    private Client client = null;
    private static PDType0Font arialFont = null;
    
    private float pageHeight;
    private float pageWidth;
    
    // Basic constant values needed for formatting the document
    public final float CARD_WIDTH_IN = 3.5f;
    public final float CARD_HEIGHT_IN = 2f;
    public final PDRectangle PAPER_TYPE = PDRectangle.A4;
    
    /** Creates a PDF object using Apache's PDFBox. Initially saves the client, creates
     *  the basic pdf (with 1 page), and loads the correct font.
     * 
     * @param client -- the client who's information is on the PDF
     * @throws IOException 
     */
    public PDFManager(Client client) throws IOException {
        
        // Create a new empty pdf
        document = new PDDocument();
        
        // Create a blank page
        PDPage page = new PDPage(PDRectangle.A4);
        // Save the dimensions of the page
        pageHeight = page.getMediaBox().getHeight();
        pageWidth = page.getMediaBox().getWidth();
               
        // Add the page to the doc
        document.addPage(page);
        
        // Add the basic dotted cutting lines to the page
        this.addPageLines(page);
        
//        //Instantiating the PDPageContentStream class
//        PDPageContentStream contentStream;
//        try {
//            contentStream = new PDPageContentStream(document, page);
//            
//            //Setting the non stroking color
//            contentStream.setNonStrokingColor(Color.DARK_GRAY);
//
//            //Drawing a rectangle 
//            contentStream.addRect(200, 650, 100, 100);
//            contentStream.addRect(10, 10, inToPt(3.5f), inToPt(2f));
//            
//
//            //Begin the Content stream 
//            contentStream.beginText();
//            
//            // Add arial as a font to use. Font saved in the resources folder
//            InputStream fontStream = this.getClass().getResourceAsStream("resources/ArialCE.ttf");
//            arialFont = PDType0Font.load(document, fontStream);
//            
//            //Setting the font to the Content stream  
//            contentStream.setFont(arialFont, 12);
//
//            //Setting the position for the line 
//            contentStream.newLineAtOffset(25, 500);
//
//            String text = "This is the sample document and we are adding content to it.";
//
//            //Adding text in the form of string 
//            contentStream.showText(text);      
//
//            //Ending the content stream
//            contentStream.endText();
//
//            //Drawing a rectangle
//            contentStream.fill();
//            
//            
//            float dash1[] = {10.0f};
//            contentStream.setLineDashPattern(dash1, 1.0f);
////            contentStream.setNonStrokingColor(Color.DARK_GRAY);
//            contentStream.setStrokingColor(Color.GRAY);
//            contentStream.drawLine(0, 0, pageWidth, pageHeight);
//            
//            // Insert a 
//            
//            
//            contentStream.close();
//        } catch (IOException ex) {
//            Logger.getLogger(PDFManager.class.getName()).log(Level.SEVERE, null, ex);
//        }

        closeDoc("C:/Downloads/test.pdf");
        
    }
    
    /** Adds a grid with cutting lines to the given page
     * 
     */
    private void addPageLines(PDPage page) throws IOException {
        // Create the content stream
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        
        // Set up the stroking for the pen
        float dash[] = {10.0f};
        contentStream.setLineDashPattern(dash, 1.0f);
        contentStream.setStrokingColor(Color.RED);
        
        // Find the middle of the page
        float halfX = pageWidth/2;
        float fifthY = pageHeight/5;
        
        // Find the lower left corner of the lower left card
//        initialCard
        
        // TEST LINE
        contentStream.drawLine(halfX, 0, halfX, pageHeight);
        for (int ii = 0; ii < 5; ii++) {
            contentStream.drawLine(0,ii*fifthY,pageWidth, ii*fifthY);
            System.out.println(ii*fifthY);
            contentStream.addRect(0, ii*fifthY, inToPt(CARD_WIDTH_IN), inToPt(CARD_HEIGHT_IN));
        }
        contentStream.fill();
        
        // Always close the content stream after use
        contentStream.close();
       
    }
    
    
    // Close the document, saving it to the given path
    private void closeDoc(String path) {
        try {
            document.save(path);
            document.close();
        } catch (IOException ex) {
            Logger.getLogger(PDFManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // Quick way to convert points (the measurement unit used by PDFBox) to inches
    private float ptToIn(float pt) {
        return pt/72;
    }
    
    // Quick way to convert inches to points 
    private float inToPt(float in) {
        return in*72;
    }
    
}
