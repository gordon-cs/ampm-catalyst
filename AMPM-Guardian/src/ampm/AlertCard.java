package ampm;

import java.awt.Color;

/** This class inherits from the Card class. 
 *  Represents an alert card for a specific alert object.
 *
 * @author benab
 */
public class AlertCard extends Card {
    
    public AlertCard(/*Alert alert*/) {
        super(false, Color.RED , "Alert", 12, Color.WHITE);
        System.out.println("Its done?");
    }
    
}
