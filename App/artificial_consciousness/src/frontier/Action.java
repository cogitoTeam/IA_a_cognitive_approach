/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package frontier;

import game.BoardMatrix.Position;


public class Action 
{
    /* ATTRIBUTES */
    
    private final Position move;
    
    // ---- other parameters to be added as needed ----
    
    
    /* METHODS */
    
    public Action(Position _move)
    {
        move = _move;
    }
    
    public Position getMove()
    {
        return move;
    }
}
