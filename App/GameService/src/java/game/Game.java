/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.Board.Position;
import java.util.List;


public abstract class Game 
{
    /** NESTING **/
    
    public static enum Player
    {
        WHITE,
        BLACK
    }
    
    public static enum State
    {
        TURN_WHITE,
        TURN_BLACK,
        VICTORY_WHITE,
        VICTORY_BLACK,
        DRAW;
    }
    
    /** ATTRIBUTES **/
    private final int id;
    private final Rules rules;
    private final Board board;
    private State state;
    
    /** METHODS **/
    
    // creation
    protected Game(int _id, Rules _rules, Board _board)
    {
        id = _id;
        rules = _rules;
        board = _board;
        restart();
    }
    
    // main
    public boolean tryMove(Position p, Player player)
    {
        // requesting player must be the current player
        if(player != getCurrentPlayer())
            return false;
        
        // perform the move only if legal
        if(player != null && rules.isLegalMove(p, board, player))
        {
            state = rules.performMove(p, board, player);
            // signal success
            return true;
        }
        
        // signal failure
        return false;
    }
    
    // query
    public State getState()
    {
        return state;
    }
    
    public Player getCurrentPlayer()
    {
        switch(state)
        {
            case TURN_WHITE: 
                return Player.WHITE;
            case TURN_BLACK: 
                return Player.BLACK;
            default:
                return null;
        }
    }
  
    @Override
    public String toString()
    {
        // local variables
        List<Position> moves = rules.getLegalMoves(board, getCurrentPlayer());
        String result = "<game id=\"" + id + "\" state=\"" + state + "\">";
        
        // add the board state
        result += board;
        
        // add legal moves
        result += "<rules n_moves=\"" + moves.size() + "\">";
        for(Position p : moves)
            result += "<move " + p + "/>";
        result += "</rules>";
        
        // finished
        return result + "</game>";
    }
    
    // modification
    public void restart()
    {
        rules.reset(board);
        state = (rules.getFirstPlayer() == Player.WHITE) ? 
                State.TURN_WHITE : State.TURN_BLACK;
    }
    
}
