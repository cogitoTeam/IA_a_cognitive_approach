/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.Board.Position;
import game.Game.Player;
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

    @Override
    public void performMove(Position p, Board board, Player player) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
