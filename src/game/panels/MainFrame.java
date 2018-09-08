package game.panels;

import game.GameConstants;
import game.GameStart;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author gbeljajew
 */
@SuppressWarnings( "serial" )
public class MainFrame extends JFrame
{

    private static final String GAME = "game";

    private static final String MENU = "menu";

    private final GamePanel gamePanel;
    private final MenuPanel menuPanel;
    private final CardLayout panelSwitcher;
    private final JPanel screenHolder;

    public MainFrame()
    {
        super( GameConstants.RESOURCE_BUNDLE.getString( "title" ) );
        // screens
        this.gamePanel = new GamePanel( this );
        this.menuPanel = new MenuPanel( this );

        this.panelSwitcher = new CardLayout();
        screenHolder = new JPanel( panelSwitcher );
        
        if( GameConstants.FULL_SCREEN_MODE )
        {
            this.createGuiFullScreen();
        }
        else
        {
            this.createGuiWindow();
        }

    }

    public void update()
    {
        if( this.gamePanel.isVisible() && this.gamePanel.isRunning() )
        {
            this.gamePanel.update();
        }
    }

    void newGame()
    {
        this.gamePanel.init();
        this.switchToGame();
    }

    void continueGame()
    {
        this.switchToGame();
    }

    private void switchToGame()
    {
        this.panelSwitcher.show( this.screenHolder, GAME );
    }

    private void switchToMenu()
    {
        this.menuPanel.setContinue( this.gamePanel.isInGame() );
        this.panelSwitcher.show( this.screenHolder, MENU );
    }

    void gameOver( long score )
    {
        this.switchToMenu();
        this.menuPanel.addScore( score );
    }

    void goToMenu()
    {
        this.switchToMenu();
    }

    private void createGuiFullScreen()
    {
        Image img = new ImageIcon(GameStart.class.getResource( GameConstants.ICON_IMAGE_PATH )).getImage();
        this.setIconImage(img);
        
        this.setUndecorated( true );
        this.setBounds( 0, 0, GameConstants.PANEL_WIDTH, GameConstants.PANEL_HEIGHT );

        this.add( screenHolder );
        screenHolder.setPreferredSize( new Dimension( GameConstants.PANEL_WIDTH, GameConstants.PANEL_HEIGHT ) );

        screenHolder.add( this.gamePanel, GAME );
        screenHolder.add( this.menuPanel, MENU );

        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable( false );
        this.switchToMenu();
    }

    private void createGuiWindow()
    {
        Image img = new ImageIcon(GameStart.class.getResource( GameConstants.ICON_IMAGE_PATH )).getImage();
        this.setIconImage(img);
        
        this.add( screenHolder );
        screenHolder.setPreferredSize( new Dimension( GameConstants.PANEL_WIDTH, GameConstants.PANEL_HEIGHT ) );

        screenHolder.add( this.gamePanel, GAME );
        screenHolder.add( this.menuPanel, MENU );

        this.pack();
        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable( false );
        this.switchToMenu();
    }
}
