package ampm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This class represents an abstract version of a single card. 
 * Specific card types (an 'alert' card, for example) will inherit from this class. 
 * This class also allows methods to generate and print cards as an image file using java's Graphics2D library. 
 * @author benab
 */
public abstract class Card {
    
    // Does the card have two sides?
    private Boolean twoSides;
    private Color cardHeaderColor;
    // What type of card is it? preventative, alert, family history, etc...
    private String cardType;
    private String copyrightString;
    // The actual image
    private Graphics2D image;
    // Header font size/color is not the same throughout the card types
    private int headerFontSize;
    private Color cardHeaderFontColor;
    
    // Tracks the total number of card objects instantiated
    private static int totalCards = 0;
    
    
    
    // Each card should have approx 300ppi, so the size in pixels 
    // of the 3.5"x2" cards is 1050x600.
    private final int DOT_BORDER_WIDTH = /*1002*/412;
    private final int DOT_BORDER_HEIGHT = /*552*/230;
    private final int FULL_WIDTH = /*1050*/420;
    private final int FULL_HEIGHT = /*600*/238;
    // The X and Y coordinates for the upper left corner of the 'safe zone',
    // also known as the dashed rectangle
    private int dotBorderInitX = (FULL_WIDTH-DOT_BORDER_WIDTH)/2;
    private int dotBorderInitY = (FULL_HEIGHT-DOT_BORDER_HEIGHT)/2;
    
    // Header height is .4 inches
    private final int HEADER_HEIGHT = 48;
    
    private final int COPYRIGHT_FONT_SIZE = 10/*20*/;
    
    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f,
                                                      BasicStroke.CAP_BUTT,
                                                      BasicStroke.JOIN_MITER,
                                                      10.0f, dash1, 0.0f);
    
    public Card(Boolean twoSides, Color cardHeaderColor, String cardType, int headerFontSize, Color cardHeaderFontColor) {
        
        totalCards++;
        
        // TODO: Make sure this is an ok way to do the copyright.
        String currYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        this.copyrightString = "\u00a9 2019-" + currYear + " AM/PM Catalyst, LLC";
        
        this.twoSides = twoSides;
        this.cardHeaderColor = cardHeaderColor;
        this.cardType = cardType;
        this.headerFontSize = headerFontSize;
        this.cardHeaderFontColor = cardHeaderFontColor;
        
        // Create the basic image
        BufferedImage buffImg = new BufferedImage(FULL_WIDTH, FULL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        // This allows us to draw on the buffImg
	Graphics2D image = buffImg.createGraphics();
        
        // Fill the background
        image.setColor(Color.BLUE);
        image.fillRect(0, 0, FULL_WIDTH, FULL_HEIGHT);

        // Create a rectangle for the header
        image.setColor(cardHeaderColor);
        image.fillRect(dotBorderInitX+5, dotBorderInitY+5, DOT_BORDER_WIDTH-12, HEADER_HEIGHT);
        
        // Draw dotted lines around border for cutting the card
        image.setColor(Color.GRAY);
        
        image.setStroke(dashed);
        image.drawRect(dotBorderInitX, dotBorderInitY, DOT_BORDER_WIDTH, DOT_BORDER_HEIGHT);
        
        
        // Set the font and write the header
        image.setColor(cardHeaderFontColor);
        // TESTING RenderingHint
        image.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        image.setFont(new Font("Arial", Font.PLAIN, /*10*/20));
        image.drawString(cardType, dotBorderInitX, dotBorderInitY+50);
        
        // Draw the copyright
        image.setFont(new Font("Arial", Font.PLAIN, COPYRIGHT_FONT_SIZE));
        image.drawString(copyrightString, 100, 100);
        
        // Load the 'Guardian' logo image
        BufferedImage guardianLogo = null;
        try {
            guardianLogo = ImageIO.read(this.getClass().getResource("/resources/guardian_image.jpg"));
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("Issue loading guardian logo");
        }
//        image.drawImage(guardianLogo, 0, 0, null);
        
        
        // Set the 
        System.out.println(image.getFontMetrics());
        
        // Disposes of this graphics context and releases any system resources that it is using. 
        image.dispose();

        // TEST STUFF //
        // Save as PNG
        File file = new File("myimage" + String.valueOf(totalCards) + ".png");
        try {
            ImageIO.write(buffImg, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int getTotalCards() {
        return totalCards;
    }
    
//    private BufferedImage scale
}
