/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.util;

import java.awt.Image;
import java.util.Arrays;

/**
 * it is an image container to make handling RPGM2K NPC sprites easy. 
 * @author gbeljajew
 */
public class RPGM2K_NPC_Sprite implements NPC_Sprite
{
    public static final int DIRECTION_SOUTH = 0;
    public static final int DIRECTION_WEST = 1;
    public static final int DIRECTION_EAST = 2;
    public static final int DIRECTION_NORTH = 3;
    
    private static final int[] STEP_PHASE={1,0,1,2};
    
    
    //-----------------------------------------------------
    
    private int direction = DIRECTION_SOUTH;
    
    private int step = 0;
    
    private final Image [][] sprites;
    
    //------------------------------------------------------

    /**
     * 
     * @param sprites an 3*4 Image matrix. 
     * @throws IllegalArgumentException if size of matrix is not 3*4
     * @throws NullPointerException if any Array or Image are null.
     */
    public RPGM2K_NPC_Sprite( Image[][] sprites )
    {
        validateSprites(sprites);
        
        // it is a little overprotective, but N00bz happens.
        this.sprites = new Image[sprites.length][];
        for(int i = 0; i < sprites.length; i++)
        {
            this.sprites[i] = Arrays.copyOf( sprites[i], sprites[i].length );
        }
        
    }

    /**
     * 
     * @param sprites
     * @return 
     */
    private static void validateSprites(Image[][] sprites)
    {
        
        if( sprites == null)
            throw new NullPointerException("we expect that all images are there.");
        
        if(sprites.length != 3)
            throw new IllegalArgumentException("we expect a standard 3*4 Image matrix.");
        
        for( Image[] sprite : sprites )
        {
            if( sprite == null )
                throw new NullPointerException("we expect that all images are there.");
            
            if( sprite.length != 4 )
            {
                throw new IllegalArgumentException("we expect a standard 3*4 Image matrix.");
            }
            
            for(Image im : sprite)
            {
                if(im == null)
                    throw new NullPointerException("we expect that all images are there.");
            }
        }
        
    }

    @Override
    public Image getSprite()
    {
        return sprites[STEP_PHASE[this.step]][this.direction];
    }

    @Override
    public NPC_Sprite step()
    {
        this.step = this.step ++ % STEP_PHASE.length;
        
        return this;
    }

    @Override
    public NPC_Sprite step0()
    {
        this.step = 0;
        
        return this;
    }

    @Override
    public NPC_Sprite turnLeft()
    {
        switch(this.direction)
        {
            case DIRECTION_EAST:
                this.direction = DIRECTION_NORTH;
                break;
                
            case DIRECTION_NORTH:
                this.direction = DIRECTION_WEST;
                break;
                
            case DIRECTION_SOUTH:
                this.direction = DIRECTION_EAST;
                break;
                
            case DIRECTION_WEST:
                this.direction = DIRECTION_SOUTH;
                break;
                
            default:
                throw new IllegalStateException("Unknown Direction: " + this.direction);
        }
        
        return this;
    }

    @Override
    public NPC_Sprite turnRight()
    {
        switch(this.direction)
        {
            case DIRECTION_EAST:
                this.direction = DIRECTION_SOUTH;
                break;
                
            case DIRECTION_NORTH:
                this.direction = DIRECTION_EAST;
                break;
                
            case DIRECTION_SOUTH:
                this.direction = DIRECTION_WEST;
                break;
                
            case DIRECTION_WEST:
                this.direction = DIRECTION_NORTH;
                break;
                
            default:
                throw new IllegalStateException("Unknown Direction: " + this.direction);
        }
        
        return this;
    }

    @Override
    public NPC_Sprite lookNorth()
    {
        this.direction = DIRECTION_NORTH;
        
        return this;
    }

    @Override
    public NPC_Sprite lookWest()
    {
        this.direction = DIRECTION_WEST;
        
        return this;
    }

    @Override
    public NPC_Sprite lookEast()
    {
        this.direction = DIRECTION_EAST;
        
        return this;
    }

    @Override
    public NPC_Sprite lookSouth()
    {
        this.direction = DIRECTION_SOUTH;
        
        return this;
    }
    
    
    
    
    
}
