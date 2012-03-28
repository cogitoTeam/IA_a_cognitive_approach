/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix;


public class Option 
{
    /* ATTRIBUTES */
    
    private BoardMatrix.Position move;
    private BoardMatrix result;
    
    /* METHODS */
    
    // creation
    public Option(BoardMatrix.Position _move, BoardMatrix _result)
    {
        move = _move;
        result = _result;
    }
    
    
    // query
    public BoardMatrix.Position getMove()
    {
        return move;
    }
    
    public BoardMatrix getResult()
    {
        return result;
    }
}
