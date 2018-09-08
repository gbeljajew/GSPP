
package game.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * this class will contain all you game logic and graphic<br>
 * use it for smaller games
 * 
 * @author gbeljajew
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel
{
    private final MainFrame mainFrame;
    
    
    /** true = game running, false = paused. */
    private boolean running = false;
    /** true = game can be continued, false = after game over and/or before new game. */
    private boolean inGame = false;
    /** this field contains score of your game. */
    @SuppressWarnings( "FieldMayBeFinal" )
    private long score = 0;
    

    GamePanel(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        this.preload();
    }
    
    private void preload()
    {
        // TODO: this metod is for loading images and set some global variables.
    }
    
    public void init()
    {
        this.running = true;  // set false, if you want to start paused.
        this.inGame = true;
        
        //TODO: here comes initiation of your game
    }
    
    /**
     * this method will be started every time before redrawing.
     * game logic comes here.
     */
    public void update()
    {
    	// TODO: main logic of your game comes here.
    	
    	
    	
    	// this block controls if we have a situation, where we end a game session without saving score.
    	if( this.gameEndCondition() )
    	{
    		this.gameEnd();
    	}
    	
    	// this block controls if this game session has ended and we should save the score.
    	// and yes, wining a game is a game over too.
    	if( this.gameOverCondition() )
    	{
    		this.gameOver();
    	}
    }

    /**
     * this method is for drawing on your game screen
     * @param g1
     */
    @Override
    public void paint(Graphics g1) 
    {
        Graphics2D g = (Graphics2D)g1;
        
        //TODO: here comes all drawing
        
        g.drawString("SCORE: " + this.score, 10, 10);// drawing score
    }

    /**
     * call this method when the game session ends and you want to save score.
     * if you do not want to save score - use gameEnd() instead.
     */
    private void gameOver()
    {
    	long lastScore = this.score;
    	
    	//OPTIONAL: last calculations of score are made here (if needed)
    	
        this.pause();
        this.inGame = false;  	// with this you can not come back to this game session using "Continue" button in menu.
        
        this.mainFrame.gameOver(lastScore);
    }
    
    /**
     * call this method if you want to end game session without saving score.
     */
    private void gameEnd()
    {
        this.pause();
        this.inGame = false;
        this.mainFrame.goToMenu();
    }
    
    /**
     * call this method if you want to go to Menu without ending the game session.
     */
    @SuppressWarnings("unused")
	private void goToMenu()
    {
        this.pause();
    	this.mainFrame.goToMenu();
    }
    
    private boolean gameEndCondition()
    {
    	//TODO: here come condition for ending game session without saving score
    	return false;
    }
    
    private boolean gameOverCondition()
    {
    	// TODO: here come conditions for ending a game session and saving score
    	return false;
    }
    
    /**
     * method for pausing your game.
     * those methods both are private because there should be no need to pause/unpause your game from outside.
     */
    private void pause()
    {
        this.running = false;
    }
    
    /**
     * 
     */
    private void unpause()
    {
        this.running = true;
    }
    
    
    boolean isRunning()
    {
        return this.running;
    }

    boolean isInGame()
    {
        return this.inGame;
    }
    
    
    
    
    
}
