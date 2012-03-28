/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.reversi;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game;
import game.Game.Player;
import game.Rules;


class ReversiRules extends Rules
{
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

}
