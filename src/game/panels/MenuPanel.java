package game.panels;

import game.GameConstants;
import game.util.Utils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * @author gbeljajew
 */
@SuppressWarnings( "serial" )
public class MenuPanel extends JPanel
{

    private final MainFrame mainFrame;

    private final JButton startButton;
    private final JButton continueButton;
    private final JButton exitButton;
    private final JButton helpButton;

    private final EntryTableModel scoreList = new EntryTableModel();

    MenuPanel( MainFrame mainFrame )
    {
        this.mainFrame = mainFrame;
        this.startButton = new JButton( GameConstants.RESOURCE_BUNDLE.getString( "button.new_game" ) );
        this.continueButton = new JButton( GameConstants.RESOURCE_BUNDLE.getString( "button.continue" ) );
        this.helpButton = new JButton( GameConstants.RESOURCE_BUNDLE.getString( "button.help" ) );
        this.exitButton = new JButton( GameConstants.RESOURCE_BUNDLE.getString( "button.exit" ) );

        createGui();

    }

    private void createGui()
    {
        this.setLayout( new BorderLayout() );

        JLabel text = new JLabel( "Menu" );
        this.add( text, BorderLayout.NORTH );

        //----- Button Actions ------------------------------
        startButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                mainFrame.newGame();
            }
        } );

        continueButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                mainFrame.continueGame();
            }
        } );

        helpButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                JOptionPane.showMessageDialog( mainFrame, GameConstants.RESOURCE_BUNDLE.getString( "text.help" ) );
            }
        } );

        exitButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                System.exit( 0 );
            }
        } );
        
        //----- Button Panel ---------------------------------------------
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new GridLayout( 0, 4 ) );

        Dimension buttonSize = new Dimension( GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT );

        startButton.setMinimumSize( buttonSize );
        buttonPanel.add( startButton );

        // OPTIONAL comment this two linesout if your game does not have a way 
        // to get to menu while playing. except for gameower. 
        continueButton.setMinimumSize( buttonSize );
        buttonPanel.add( continueButton );

        helpButton.setMinimumSize( buttonSize );
        buttonPanel.add( helpButton );

        exitButton.setMinimumSize( buttonSize );
        buttonPanel.add( exitButton );

        this.add( buttonPanel, BorderLayout.SOUTH );

        //----- Score --------
        JTable table = new JTable( scoreList );
        table.getColumnModel().getColumn( 0 ).setHeaderValue( GameConstants.RESOURCE_BUNDLE.getString( "table.name" ) );
        table.getColumnModel().getColumn( 1 ).setHeaderValue( GameConstants.RESOURCE_BUNDLE.getString( "table.score" ) );
        table.getColumnModel().getColumn( 2 ).setHeaderValue( GameConstants.RESOURCE_BUNDLE.getString( "table.date" ) );

        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
        scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

        this.add( scrollPane, BorderLayout.CENTER );

    }

    void addScore( long score )
    {
        this.scoreList.addScore( score );
        // OPTIONAL: if you have multiple players, who will write scores 
        // to the same score file from multiple computers,
        // use addScoreForMultiplePlayers instead
        //this.scoreList.addScoreForMultiplePlayers( score );
        
        
        this.scoreList.fireTableDataChanged();
    }

    /**
     * enable or disable Continue button based on: game can be continued.
     *
     * @param running true if game was already started, but still no gameower.
     */
    void setContinue( boolean running )
    {
        this.continueButton.setEnabled( running );
    }

}

@SuppressWarnings( "serial" )
class EntryTableModel extends AbstractTableModel
{
    
    private final String pattern = "###,###";
    
    private final DecimalFormat formater;

    private static final Comparator<Entry> LOWER_IS_BETTER_COMPARATOR = new Comparator<Entry>()
    {

        @Override
        public int compare( Entry o1, Entry o2 )
        {

            if( o1.score > o2.score )
            {
                return 1;
            }

            if( o1.score < o2.score )
            {
                return -1;
            }

            if( o1.timeStamp > o2.timeStamp )
            {
                return 1;
            }

            if( o1.timeStamp < o2.timeStamp )
            {
                return -1;
            }

            return 0;

        }
    };

    private static final Comparator<Entry> HIGHER_IS_BETTER_COMPARATOR = new Comparator<Entry>()
    {

        @Override
        public int compare( Entry o1, Entry o2 )
        {
            if( o1.score > o2.score )
            {
                return -1;
            }

            if( o1.score < o2.score )
            {
                return 1;
            }

            if( o1.timeStamp > o2.timeStamp )
            {
                return 1;
            }

            if( o1.timeStamp < o2.timeStamp )
            {
                return -1;
            }

            return 0;
        }
    };

    List<Entry> scoreList = new ArrayList<>( GameConstants.MAX_SCORE_ENTRIES + 5 );
    private String lastNameEntry = ""; 

    public EntryTableModel()
    {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator( '\''); // OPTIONAL here you can change thousand separator for score
        
        this.formater = new DecimalFormat(pattern, symbols);
        this.load();
    }

    @Override
    public int getRowCount()
    {
        return scoreList.size();
    }

    @Override
    public int getColumnCount()
    {
        return 3;
    }

    @Override
    public Object getValueAt( int rowIndex, int columnIndex )
    {
        Entry entry = this.scoreList.get( rowIndex );

        if( columnIndex == 0 )
        {
            return entry.name;
        }

        if( columnIndex == 1 )
        {
            return formater.format( entry.score );
            //return new TimeConverter(entry.score);  // OPTIONAL: if your score is time use this one instead.
        }

        if( columnIndex == 2 )
        {
            return new Date( entry.timeStamp );
        }

        throw new ArrayIndexOutOfBoundsException( "wrong index: " + columnIndex );

    }

    /**
     * add a score to list.
     *
     * @param score
     */
    void addScore( long score )
    {
        long timeStamp = System.currentTimeMillis();

        String name = JOptionPane.showInputDialog( GameConstants.RESOURCE_BUNDLE.getString( "text.score.name" ), this.lastNameEntry );

        this.add( new Entry( name, score, timeStamp ) );
    }
    
    /**
     * this works the same as addScore. but it is aware that there may be 
     * mor than one player at the same time, who will 
     */
    void addScoreForMultiplePlayers( long score )
    {
        scoreList.clear();
        this.load();
        this.addScore( score );
    }

    private void add( Entry e )
    {
        this.scoreList.add( e );

        // OPTIONAL if your score better when smaller (Time, turns...) 
        // use LOWER_IS_BETTER_COMPARATOR instead of HIGHER_IS_BETTER_COMPARATOR.
        Collections.sort( scoreList, HIGHER_IS_BETTER_COMPARATOR );

        while( this.scoreList.size() > GameConstants.MAX_SCORE_ENTRIES )
        {
            scoreList.remove( this.scoreList.size() - 1 );
        }

        save();
    }

    private void save()
    {
        Path scorePath = Paths.get( GameConstants.SAVE_PATH );

        try
        {
            List<String> scores = new ArrayList<>( GameConstants.MAX_SCORE_ENTRIES + 5 ); // "optimisation" ;)

            for( Entry e : this.scoreList )
            {
                scores.add( e.toString() );
            }

            Files.write( scorePath, scores );
        }
        catch( IOException ex )
        {
            Utils.showErrorDialog( "error.save_failed", ex );
        }
    }
    
    

    private void load()
    {

        Path scorePath = Paths.get( GameConstants.SAVE_PATH );
        
        if( !Files.exists( scorePath ) )// there is no score file at the beginning
            return;

        try
        {
            List<String> scores = Files.readAllLines( scorePath );

            for( String s : scores )
            {
                this.add( new Entry( s ) );
            }
        }
        catch( IOException ex )
        {
            Utils.showErrorDialog( "error.save_failed", ex );
        }
        
    }

}

class Entry implements Comparable<Entry>
{

    final String name;
    final long score;
    final long timeStamp;

    public Entry( String name, long score )
    {
        this.name = name;
        this.score = score;
        this.timeStamp = new Date().getTime();

    }

    public Entry( String name, long score, long timeStamp )
    {
        this.name = name;
        this.score = score;
        this.timeStamp = timeStamp;
    }

    public Entry( String csv )
    {

        String[] values = csv.split( ";" );

        if( values.length != 3 )
        {
            throw new IllegalArgumentException( "score file was corrupted." );
        }

        this.name = values[ 0 ];						// name is first
        this.score = Long.parseLong( values[ 1 ] );		// score is second
        this.timeStamp = Long.parseLong( values[ 2 ] );	// time stamp is third.

    }

    @Override
    public int compareTo( Entry o )
    {
        if( o == null )
        {
            throw new NullPointerException();
        }

        if( this.score < o.score )
        {
            return 1;
        }

        if( this.score > o.score )
        {
            return -1;
        }

        if( this.timeStamp < o.timeStamp )
        {
            return 1;
        }

        return -1;

    }

    @Override
    public String toString()
    {	// this is needed for saving score as csv.
        return this.name + ";" + this.score + ";" + this.timeStamp;
    }

}

/**
 * use it to display score as time. make sure your score is time in milliseconds
 * between start and gameover. score = score + GameConstants.TIMER_PERIOD;
 *
 *
 */
class TimeConverter
{

    private final int hours;
    private final int minutes;
    private final int seconds;
    private final int milis;

    public TimeConverter( long time )
    {
        this.milis = ( int )time % 1000;
        time = time / 1000;

        this.seconds = ( int )time % 60;
        time = time / 60;

        this.minutes = ( int )time % 60;

        this.hours = ( int )time / 60;
    }

    @Override
    public String toString()
    {
        return String.format( "%02d:%02d.%02d.%03d", this.hours, this.minutes, this.seconds, this.milis );
    }

}
