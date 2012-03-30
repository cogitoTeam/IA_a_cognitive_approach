/*****************
 * @author william
 * @date 30-Mar-2012
 *****************/


package frontier;

import game.BoardMatrix;


public class MoveAction extends Action
{
    /* ATTRIBUTES */
    
    private final BoardMatrix.Position move;
    
    
    /* METHODS */
    
    public MoveAction(BoardMatrix.Position _move)
    {
        move = _move;
    }
    
    public BoardMatrix.Position getMove()
    {
        return move;
    }
}
