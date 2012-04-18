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
    
        
    /**
     * Create a board to play the game defined by these rules.
     * @return 
     * A brand-new and deep BoardMatrix with the correct size to play the game
     * defined by this rule-set.
     */
    public BoardMatrix createBoard()
    {
        return new BoardMatrix(getBoardSize());
    }
    
    /** 
    * Get the board resulting from a given player's move at a given position
    * of a given board.
    * @param parent
    * The board from which the result will be generated.
    * @param player
    * The player making the move which will modify the parent.
    * @position move
    * The position where the specified player will place their piece.
    * @return 
    * The board resulting from this set of rules when the specified player plays
    * at the specified position of the specified parent board.
    */
    public BoardMatrix getResultingBoard(BoardMatrix parent, Player player, 
            Position move)
    {
        // perform the move on a copy of the parent
        BoardMatrix child = parent.copy();
        // provided of course that the move is legal
        if(isLegalMove(move, parent, player))
            tryMove(move, child, player);

        // perform the move on a copy of the parent
        return child;
    }
    
    /**
     * Get the list of board resulting from all possible legal of a given player
     * on a given board.
     * @param parent
     * The board from which the result will be generated.
     * @param player
     * The player making the move which will modify the parent.
     * @return 
     * The list of boards resulting from each move this set of rules allows the 
     * given player to make on the given board.
     */
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

    /**
     * Get the list of moves this set of rules permits the specified player to
     * make.
     * @param board
     * The board under consideration for the given player.
     * @param player
     * The player whose legal moves we wish to collect for the given board.
     * @return 
     * A set of position where the specified player can play on the given board.
     */
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

    
    
    // modification
    
    /**
     * Attempt to play at a specified position on a specified board in the name
     * of a specified player.
     * @param p
     * The position where the specified player is attempting to move.
     * @param board
     * The board upon which all this action action takes place.
     * @param player
     * The player attempting to make a move
     * @return 
     * <li>Game.State.MOVE_FAILURE if by these rules the move is illegal. 
     * In this case and only this case the move is not performed!</li>
     * <li>Game.State.VICTORY if by these rules the move results in victory for 
     * the moving player.</li>
     * <li>Game.State.DRAW if by this rules the move results in a draw.</li>
     * <li>Game.State.MOVE_SUCCESS in all other circumstances.</li>
     */
    public Game.State tryMove(Position p, BoardMatrix board,
                                        Player player)
    {
        // check if the move is legal
        if(!isLegalMove(p, board, player))
            return Game.State.MOVE_FAILURE;
        
        // perform the move itself, if it is legal
        performMove(p, board, player);

        // check if success has occured
        if(hasWon(board, player))
            return Game.State.VICTORY;
        
        else if(hasWon(board, Game.otherPlayer(player)))
            return Game.State.DEFEAT;
        
        // check if draw has occured
        else if(isDraw(board))
            return Game.State.DRAW;
        
        // otherwise report next player to move (cycle between the two)
        else
            return Game.State.MOVE_SUCCESS;
    }
    
    
    /* PUBLIC INTERFACE */
 
    // query
    
    /**
     * Check the size of the board defined by these rules.
     * @return 
     * The size of the board this particular game is played on.
     */
    public abstract BoardMatrix.Size getBoardSize();
    
    /**
     * Check which player is supposed to play first by these rules.
     * @return 
     * The player who, by these rules, moves first.
     */
    public abstract Game.Player getFirstPlayer();
    
    
    
    /**
     * Check if a given player is able to make any move at all at this point, by
     * these rules.
     * @param board
     * The board upon which the move is considered.
     * @param player
     * The player considering the move.
     * @return 
     * True if and only if by the rule-set the specified player can make a move
     * on the specified board.
     */
    public abstract boolean canMove(BoardMatrix board, Player player);
    
    
    
    /**
     * Check if the game is a draw at this point, by these rules.
     * @param board
     * The board we are considering to determine if the game is a draw.
     * @return 
     * True if and only if by this rule-set the specified board is in a state of
     * stalemate.
     */
    public abstract boolean isDraw(BoardMatrix board);
    
    
    
    /**
     * Check if the specified player has won the game at this point, by these 
     * rules.
     * @param board
     * The board we are considering to determine if the game has ended.
     * @param player
     * The player to check for victory.
     * @return 
     * True if and only if by this rule-set the specified player has 
     * accomplished all victory conditions on the specified board.
     */
    public abstract boolean hasWon(BoardMatrix board, Player player);
    
    
    
    /**
     * Return the specified player's score at this point, by these rules.
     * @param board
     * The board in consideration.
     * @param player
     * The player whose score we wish to know or estimate.
     * @return 
     * The integer value of the specified board for the specified player, based
     * on this rule-set: can be a heuristic estimate or 0 for games with no
     * scoring system.
     */
    public abstract int getValue(BoardMatrix board, Player player);

    
    /**
     * Check the legality of a move for a given player on a given board.
     * @param p
     * The move in consideration.
     * @param board
     * The board upon which the move is considered.
     * @param player
     * The player considering the move.
     * @return 
     * True if by this rule-set the specified move is legal on the specified
     * board by the specified player.
     */
    public abstract boolean isLegalMove(Position p, BoardMatrix board, 
                                        Player player);
    
    
    // modification

    
    /**
     * Reset the specified board to the starting position implied by these 
     * rules.
     */
    public abstract void reset(BoardMatrix board);
    
    
    // override
    /**
     * @return 
     * The name of the game defined by these rules.
     */
    @Override
    public abstract String toString();
    
    /* PROTECTED INTERFACE */
    
    
    // modification
    
    /**
     * Alters the given board based on a given move by a given player: this 
     * method is protected to ensure that the legality of the move is tested
     * before it is called!
     * @param p
     * The position to play the move at.
     * @param board
     * The board upon which the move shall be played.
     * @param player 
     * The player playing the specified move
     */
    protected abstract void performMove(Position p, BoardMatrix board, 
            Player player);

}
