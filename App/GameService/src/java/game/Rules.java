/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.Board.Position;
import game.Game.Player;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Rules
{
    /** CONSTANTS **/
    private static final Logger logger = Logger.getLogger(Rules.class.getName());

    /** ATTRIBUTES **/
    
    /** METHODS **/

    // query
    public List<Board> getChildBoards(Board parent, Player player)
    {
        // local variables
        List<Board> children = new LinkedList<Board>();
        List<Position> moves = getLegalMoves(parent, player);

        // collect possible children
        for(Position move : moves)
        {
            try
            {
                // perform the move on a copy of the parent
                Board child = parent.copy();
                performMove(move, child, player);
                // save the child in the list
                children.add(child);
            }
            catch (Exception ex)
            {
                logger.log(Level.SEVERE, null, ex);
                return null;
            }
        }

        // return the result
        return children;
    }

    // query
    public List<Position> getLegalMoves(Board board, Player player)
    {
        // local variables
        List<Position> positions = new LinkedList<Position>();
        Position p = new Position(0, 0);
        
        // collect legal position
        for(p.row = 0; p.row < board.get_n_rows(); p.row++)
            for(p.col = 0; p.col < board.get_n_cols(); p.col++)
                if(isLegalMove(p, board, player))
                    // be sure to add a copy, not the original !
                    positions.add(new Position(p.row, p.col));
        
        // return the positions
        return positions;
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
