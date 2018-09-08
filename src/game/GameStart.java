
package game;

import game.panels.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * there is no need to change this class<br>
 * it is used only for starting game
 * 
 * @author gbeljajew
 */
public class GameStart{

    public static void main(String[] args) 
    {
        final MainFrame frame = new MainFrame();
        
        Timer t = new Timer(GameConstants.TIMER_PERIOD, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.update();
                frame.repaint();
            }
        });
        
        
        frame.setVisible(true);
        
        t.start();
    }
    
}
