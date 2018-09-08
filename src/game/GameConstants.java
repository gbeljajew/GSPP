
package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 * @author gbeljajew
 */
public class GameConstants 
{
    // OPTIONAL check all those constants and change, if needed.
    
    // a few constants needed for building up game gui.
    /** screen width. */
    public static final int PANEL_WIDTH;
    /** screen high. */
    public static final int PANEL_HEIGHT;
    /** true: this game starts in full screen, false: in a window. */
    public static final boolean FULL_SCREEN_MODE = false;
    
    /** time between updates. <br>
     * FPS = 1000 / TIMER_PERIOD */
    public static final int TIMER_PERIOD = 20;
    
    
    /** maximal number of scores that are saved */
    public static final int MAX_SCORE_ENTRIES = 20;
    
    /** Width of buttons in menu */
    public static final int BUTTON_WIDTH = GameConstants.PANEL_WIDTH / 4 - 5;
    /** Height of buttons in menu */
    public static final int BUTTON_HEIGHT = 20;
    
    /** Get your localized Strings from here */
    public static final ResourceBundle RESOURCE_BUNDLE = PropertyResourceBundle.getBundle("game.local.MyBundle");
    
    /** path to folder, where this game was started */
    public static final String WORK_PATH;
    
    /** Path to save file for score */
    public static final String SAVE_PATH;
    
    public static final String ICON_IMAGE_PATH = "/graphic/GameSeedIcon.png";
    
    private static final String SAVE_FILE_NAME = "/score.dat";
    
    
    
    static
    {
        // OPTIONAL if you want you can load those constants from config.
        
        if(FULL_SCREEN_MODE)
        {
            Dimension desctop = Toolkit.getDefaultToolkit().getScreenSize();
            PANEL_WIDTH = desctop.width;
            PANEL_HEIGHT = desctop.height;
        }
        else
        {
            PANEL_WIDTH = 800;
            PANEL_HEIGHT = 600;
        }
        
        URI location;
        String workPath = ".";
        try
        {
            location = GameConstants.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            
            Path path = Paths.get( location).getParent();
            
            workPath = path.toAbsolutePath().toString();
        }
        catch( URISyntaxException ex )
        {
            // i didn't used here method from game.util.Utils because i did not want to create circular dependances.
            JOptionPane.showMessageDialog( null, 
                    ex, 
                    RESOURCE_BUNDLE.getString( "error.generic_error"), 
                    JOptionPane.ERROR_MESSAGE);
            
        }
        
        WORK_PATH = workPath;
        
        SAVE_PATH = WORK_PATH + SAVE_FILE_NAME;
        
    }
    
    
    
}
