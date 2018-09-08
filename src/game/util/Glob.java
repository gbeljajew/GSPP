package game.util;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.JOptionPane;

/**
 * this is an static class -> no instance needet.<br>
 * it contains some utility methods to load sprites from files.<br>
 *
 * constants are used for RPGM2k NPC Sprites
 *
 * @author gbeljajew
 */
public class Glob
{

    public static final int DIR_UP = 0;
    public static final int DIR_RIGHT = 1;
    public static final int DIR_DOWN = 2;
    public static final int DIR_LEFT = 3;
    
    private static int failCount = 0;

    /**
     * on RPGM2K NPC sprites contain 4 directions * 3 step phases. this array
     * gives order of images for smouth movement.
     */
    public static final int[] STEP =
    {
        1, 0, 1, 2
    };

    public static Dimension dim = new Dimension( 1024, 768 );

    /**
     *
     * transforms BufferedImage to Image
     *
     * @param bufferedImage
     * @return
     */
    public static Image toImage( BufferedImage bufferedImage )
    {
        if( bufferedImage == null )
        {
            throw new NullPointerException();
        }
        return Toolkit.getDefaultToolkit().createImage( bufferedImage.getSource() );
    }

    /**
     * loads a single Image from file
     *
     *
     * @param path make sure you use absolute path if your image is outside of jar file.
     * @return null if there are some problems
     */
    public static Image getImage( String path )
    {
        BufferedImage bi;

        bi = getBufferedImage( path );

        if( bi == null )
        {
            return null;
        }

        return toImage( bi );

    }

    /**
     * loads sprites from a file and puts it in an array as Image one row after
     * another
     *
     *
     * @param path make sure you use absolute path if your image is outside of jar file.
     * @param dx how wide is one sprite
     * @param dy how high is one sprite
     * @return an array of sprites
     */
    public static Image[] getTiles( String path, int dx, int dy )
    {
        BufferedImage bi;
        Image[] erg;

        bi = getBufferedImage( path );

        if( bi == null )
        {
            return null;
        }

        BufferedImage bis;
        int mx = bi.getWidth() / dx;
        int my = bi.getHeight() / dy;
        erg = new Image[ mx * my ];
        int ix, iy;
        for( iy = 0; iy < my; iy++ )
        {
            for( ix = 0; ix < mx; ix++ )
            {
                bis = bi.getSubimage( dx * ix, dy * iy, dx, dy );
                erg[ ix + iy * mx ] = toImage( bis );
            }
        }
        return erg;
    }

    /**
     * load RPGM2k NPC (or something like that) Sprites<br>
     *
     * you can use it to load other sprite maps if you wish.<br>
     *
     *
     * @param path make sure you use absolute path if your image is outside of jar file.
     * @param width width of sprite in pixel
     * @param high high of sprite in pixel
     * @return Image[step(0..2)][direction(0..3)] if you separated one NPC in
     * one file
     */
    public static Image[][] getHeroAnimation( String path, int width, int high )
    {

        BufferedImage bi;
        bi = getBufferedImage( path );

        if( bi == null )
        {
            return null;
        }

        BufferedImage b;

        Image[][] erg = new Image[ bi.getWidth() / width ][ bi.getHeight() / high ];

        for( int ix = 0; ix < erg.length; ix++ )
        {
            for( int iy = 0; iy < erg[ ix ].length; iy++ )
            {
                b = bi.getSubimage( ix * width, iy * high, width, high );
                erg[ ix ][ iy ] = toImage( b );
            }
        }
        return erg;
    }

    /**
     *
     * loads a BufferedImage from file<br>
     * use getImage() instead
     *
     * @param path make sure you use absolute path if your image is outside of jar file.
     *              Glob.getImage( "graphic/fly1.png" );
     * @return
     */
    public static BufferedImage getBufferedImage( String path )
    {
        BufferedImage bi;

        try
        {
            bi = ImageIO.read( Glob.class.getClassLoader().getResourceAsStream( path ) );
            return bi;
        }
        catch( IOException ex )
        {
            // Ignore. if this file is not in JAR then, maybe, it is outside. 
        }

        File f = new File( path );

        try
        {
            bi = ImageIO.read( f );
        }
        catch( IOException ex )
        {
            JOptionPane.showMessageDialog( null,
                    ex + "\n Failed to load " + f.getAbsolutePath(),
                    "Load Image failed.",
                    JOptionPane.ERROR_MESSAGE );
            
            failCount++;
            
            if( failCount > 5 ) // on the other hand it is PinA to click away too many error messages.
                throw new RuntimeException(new FileNotFoundException("too many files not found"));
            
            return null; // we do not expect this game to run well without this image, 
            // but we do not throw an exception here because there may be more than one image we fail to load.
            // so we give information about all images, that are missing.
        }

        return bi;
    }

}
