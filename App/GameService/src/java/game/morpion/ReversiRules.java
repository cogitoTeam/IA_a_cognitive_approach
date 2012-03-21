/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.morpion;

import game.Board;
import game.Game;
import game.Game.Player;
import game.Rules;
import java.util.List;


class ReversiRules extends Rules
{
    /** SINGLETON **/
    
    private static ReversiRules instance = null;
    public static ReversiRules getInstance()
    {
        if(instance == null)
            instance = new ReversiRules();
        return instance;
    }
    private ReversiRules() { }
    
    
    
    /** IMPLEMENT INTERFACE **/
       
    @Override
    public boolean isDraw(Board board) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasWon(Board board, Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Board.Position> getLegalMoves(Board board, Game.Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLegalMove(Board.Position p, Board board, Game.Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void performMove(Board.Position p, Board board, Game.Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
