/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.reversi;

import game.Board;
import game.Board.Position;
import game.Game.Player;
import game.Rules;
import java.util.List;


class MorpionRules extends Rules
{
    /** SINGLETON **/
    
    private static MorpionRules instance = null;
    public static MorpionRules getInstance()
    {
        if(instance == null)
            instance = new MorpionRules();
        return instance;
    }
    private MorpionRules() { }
    
    
    
    /** IMPLEMENT INTERFACE **/
    
    // query
    @Override
    public Player getFirstPlayer() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
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
    public List<Position> getLegalMoves(Board board, Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLegalMove(Position p, Board board, Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // modification
    @Override
    public void performMove(Position p, Board board, Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void reset(Board board) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}