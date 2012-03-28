/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix.Position;


public abstract class Actuator 
{
    /* METHODS */
    
    // creation
    public Actuator(String s_url)
    {
        
    }
    
    // command
    boolean tryMove(Position p)
    {
        // return true if the move succeed, false otherwise
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
