package ampm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private final int cutWidth = 1002;
    private final int cutHeight = 552;
    private final int fullWidth = 1050;
    private final int fullHeight = 600;
    // The X and Y coordinates for the upper left corner of the 'safe zone',
    // also known as the dashed rectangle
    private int safeInitX = (fullWidth-cutWidth)/2;
    private int safeInitY = (fullHeight-cutHeight)/2;
    
    // Header height is .4 inches
    private final int headerHeight = 120;
    
    private final int COPYRIGHT_FONT_SIZE = 6;
    
    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f,
                                                      BasicStroke.CAP_BUTT,
                                                      BasicStroke.JOIN_MITER,
                                                      10.0f, dash1, 0.0f);
    
    public Card(Boolean twoSides, Color cardHeaderColor, String cardType, int headerFontSize, Color cardHeaderFontColor) {
        
        totalCards++;
        
        // TODO: Make sure this is an ok way to do the copyright.
        String currYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        this.copyrightString = "\u00a9 " + currYear + " AM/PM Catalyst, LLC";
        
        this.twoSides = twoSides;
        this.cardHeaderColor = cardHeaderColor;
        this.cardType = cardType;
        this.headerFontSize = headerFontSize;
        this.cardHeaderFontColor = cardHeaderFontColor;
        
        // Create the basic image
        BufferedImage buffImg = new BufferedImage(fullWidth, fullHeight, BufferedImage.TYPE_INT_ARGB);
        
        // This allows us to draw on the buffImg
	Graphics2D image = buffImg.createGraphics();
        
        // Fill the background
        image.setColor(Color.WHITE);
        image.fillRect(0, 0, fullWidth, fullHeight);

        // Create a rectangle for the header
        image.setColor(cardHeaderColor);
        image.fillRect(0, 0, fullWidth, headerHeight);
        
        // Draw dotted lines around border for cutting the card
        image.setColor(Color.GRAY);
        
        image.setStroke(dashed);
        image.drawRect(safeInitX, safeInitY, cutWidth, cutHeight);
        
        
        // Set the font and write the header
        image.setColor(cardHeaderFontColor);
        image.setFont(new Font("Arial", Font.PLAIN, 40));
        image.drawString(cardType, safeInitX, safeInitY+50);
        
        // Draw the copyright
        image.setFont(new Font("Arial", Font.PLAIN, COPYRIGHT_FONT_SIZE));
        image.drawString(copyrightString, 0, fullHeight);
        
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
    
    
    
}
