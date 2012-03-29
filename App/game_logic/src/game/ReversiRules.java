/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.BoardMatrix.Position;
import game.BoardMatrix.Size;
import game.Game.Player;


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
    public boolean isDraw(BoardMatrix board) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasWon(BoardMatrix board, Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    @Override
    public int getValue(BoardMatrix board, Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLegalMove(BoardMatrix.Position p, BoardMatrix board, Game.Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // modification
    @Override
    public Game.State performMove(BoardMatrix.Position p, BoardMatrix board, Game.Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
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

}
