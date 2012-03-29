/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.BoardMatrix.Position;
import game.Game.Player;
import java.util.LinkedList;
import java.util.List;


public abstract class Rules
{
    /* ATTRIBUTES */
    
    /* METHODS */

    // query
    public BoardMatrix getResultingBoard(BoardMatrix parent, Player player, 
            Position move)
    {
        // perform the move on a copy of the parent
        BoardMatrix child = parent.copy();
        // provided of course that the move is legal
        if(isLegalMove(move, parent, player))
            performMove(move, child, player);

        // perform the move on a copy of the parent
        return child;
    }
    
    public List<BoardMatrix> getChildBoards(BoardMatrix parent, Player player)
    {
        // local variables
        List<BoardMatrix> children = new LinkedList<BoardMatrix>();
        List<Position> moves = getLegalMoves(parent, player);

        // collect possible children
        for(Position move : moves)
            children.add(getResultingBoard(parent, player, move));

        // return the result
        return children;
    }

    // query
    public List<Position> getLegalMoves(BoardMatrix board, Player player)
    {
        // local variables
        List<Position> positions = new LinkedList<Position>();
        Position p = new Position(0, 0);
        
        // collect legal position
        for(p.row = 0; p.row < board.getSize().n_rows; p.row++)
            for(p.col = 0; p.col < board.getSize().n_cols; p.col++)
                if(isLegalMove(p, board, player))
                    // be sure to add a copy, not the original !
                    positions.add(new Position(p.row, p.col));
        
        // return the positions
        return positions;
    }

    /* PUBLIC INTERFACE */
 
    // query
    public abstract Game.Player getFirstPlayer();
    
    public abstract boolean isDraw(BoardMatrix board);
    
    public abstract boolean hasWon(BoardMatrix board, Player player);
    
    public abstract int getValue(BoardMatrix board, Player player);

    public abstract boolean isLegalMove(Position p, BoardMatrix board, 
                                        Player player);
    
    public abstract BoardMatrix createBoard();
    
    // modification
    public abstract Game.State performMove(Position p, BoardMatrix board,
                                            Player player);

    public abstract void reset(BoardMatrix board);
    
    // override
    @Override
    public abstract String toString();

}
