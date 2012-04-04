/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.BoardMatrix.Direction;
import game.BoardMatrix.Position;
import game.BoardMatrix.Size;
import game.Game.Player;
import java.util.Stack;


public class ReversiRules extends Rules
{
    /* CONSTANTS */
    private static final Size BOARD_SIZE = new Size(8, 8);
    
    /* SINGLETON */
    
    private static ReversiRules instance = null;
    public static ReversiRules getInstance()
    {
        if(instance == null)
            instance = new ReversiRules();
        return instance;
    }
    private ReversiRules() { }
    
    
    
    /* IMPLEMENT INTERFACE */
       
    
    // query
    
    @Override
    public Player getFirstPlayer() 
    {
        return Player.WHITE;
    }
    
    @Override
    public boolean canMove(BoardMatrix board, Player player)
    {
        return !getLegalMoves(board, player).isEmpty();
    }
    
    @Override
    public boolean isDraw(BoardMatrix board) 
    {
        return (!canMove(board, Player.WHITE) 
                && !canMove(board, Player.BLACK)
                && getValue(board, Player.WHITE) 
                    == getValue(board, Player.BLACK));
    }

    @Override
    public boolean hasWon(BoardMatrix board, Player player) 
    {
        return (!canMove(board, Player.WHITE) 
                && !canMove(board, Player.BLACK)
                && getValue(board, player) 
                        > getValue(board, Game.otherPlayer(player)));
    }
    
    
    @Override
    public int getValue(BoardMatrix board, Player player) 
    {
        return board.count_player_pieces(player);
    }

    @Override
    public boolean isLegalMove(BoardMatrix.Position p, BoardMatrix board, 
        Game.Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // modification
    @Override
    public void performMove(Position p, BoardMatrix board, Player player) 
    {   
        // place piece in selected position
        board.setCellOwner(p, player);
        
        /* Flip enemy pieces along the 8 directions */
        // right
        flipLine(p, new Direction(0, 1), board, player);
        // left
        flipLine(p, new Direction(0, -1), board, player);
        // down
        flipLine(p, new Direction(1, 0), board, player);
        // up
        flipLine(p, new Direction(-1, 0), board, player);
        // down-right
        flipLine(p, new Direction(1, 1), board, player);
        // up-right
        flipLine(p, new Direction(-1, 1), board, player);
        // down-left
        flipLine(p, new Direction(-1, 1), board, player);
        // down-right
        flipLine(p, new Direction(-1, -1), board, player);
    }

    @Override
    public void reset(BoardMatrix board)
    {
        // remove all pieces
        board.clear();
        
        // place 4 pieces, 2 white and 2 black, in the center
        for(Position p = new Position(3,3); p.row < 4; p.row++)
            for(p.col = 3; p.col < 4; p.col++)
                board.setCellOwner(p, ((p.row + p.col)%2 == 0) ? Player.WHITE 
                                                                : Player.BLACK);
    }
    
    @Override
    public String toString() 
    {
        return "reversi";
    }

    @Override
    public BoardMatrix createBoard() 
    {
        return new BoardMatrix(BOARD_SIZE);
    }

    
    /* SUBROUTINES */
    
    
    private void flipLine(Position start, Direction delta, BoardMatrix board, 
                            Player player)
    {
        // local variables
        Position iter = new Position(start.row, start.col);
        Player other = Game.otherPlayer(player);
        Stack<Position> potential = new Stack<Position>();
        
        // iterate along the line defined by the given direction
        for(iter.add(delta); iter.within(board.getSize()); iter.add(delta))
        {
            // enemies tokens will potentially be flipped, if surrounded
            if(board.getCellOwner(iter) == other)
                potential.push(new Position(iter.row, iter.col));
            else
            {
                // flip all the enemy tokens between two friendly tokens
                for(Position flipme : potential)
                    board.setCellOwner(flipme, player);
                // stop at the first friendly token
                break;
            }
        }
        // if we reached the end without encountering a friendly piece then
        // the 'potential' pieces are not flipped.
        potential.clear();
    }
}
