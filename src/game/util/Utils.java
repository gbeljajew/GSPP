/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.util;

import static game.GameConstants.RESOURCE_BUNDLE;
import javax.swing.JOptionPane;

/**
 *
 * @author gbeljajew
 */
public class Utils
{
    /**
     * 
     * @param errorBundleKey    key for ResourceBundle for text for this error.
     * @param ex                Exception
     */
    public static void showErrorDialog(String errorBundleKey, Throwable ex )
    {
        
        String errorTitle = RESOURCE_BUNDLE.getString( errorBundleKey );
        
        if(errorTitle == null)
            errorTitle = RESOURCE_BUNDLE.getString( "error.generic_error" );
        
        JOptionPane.showMessageDialog( null, 
                    ex, 
                    errorTitle, 
                    JOptionPane.ERROR_MESSAGE );
    }
}
