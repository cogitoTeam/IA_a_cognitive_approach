/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix;
import game.BoardMatrix.Position;


public class Option 
{
    /* ATTRIBUTES */
    
    private Position move;
    private BoardMatrix result;
    
    /* METHODS */
    
    // creation
    public Option(Position _move, BoardMatrix _result)
    {
        move = _move;
        result = _result;
    }
      
    // query
    public Position getMove()
    {
        return move;
    }
    
    public BoardMatrix getResult()
    {
        return result;
    }
}
