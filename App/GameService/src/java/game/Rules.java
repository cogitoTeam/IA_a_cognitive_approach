/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.Board.Position;
import game.Game.Player;
import java.util.LinkedList;
import java.util.List;


public abstract class Rules 
{
    /** ATTRIBUTES **/
    
    /** IMPLEMENTED METHODS **/

    // query
    public List<Position> getLegalMoves(Board board, Player player)
    {
        // local variables
        List<Position> result = new LinkedList<Position>();
        Position p = new Position(0, 0);
        
        // collect legal position
        for(p.row = 0; p.row < board.get_n_rows(); p.row++)
            for(p.col = 0; p.col < board.get_n_cols(); p.col++)
                if(isLegalMove(p, board, player))
                    // be sure to add a copy, not the original !
                    result.add(new Position(p.row, p.col));
        
        // return the result
        return result;
    }

    /** PUBLIC INTERFACE **/
 
    // query
    public abstract Game.Player getFirstPlayer();
    
    public abstract boolean isDraw(Board board);
    
    public abstract boolean hasWon(Board board, Player player);

    public abstract boolean isLegalMove(Position p, Board board, Player player);
    
    // modification
    public abstract Game.State performMove(Position p, Board board,
                                            Player player);

    public abstract void reset(Board board);

}
