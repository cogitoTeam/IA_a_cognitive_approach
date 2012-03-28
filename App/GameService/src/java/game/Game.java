/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.ai.AI;
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


    /** CONSTANTS **/
    
    public static final Player AI_PLAYER = Player.BLACK;


    /** ATTRIBUTES **/

    private final int id;
    private final Rules rules;
    private final Board board;
    private final AI ai;
    private State state;
        
    /** METHODS **/
    
    // creation
    protected Game(int _id, Rules _rules, Board _board)
    {
        id = _id;
        rules = _rules;
        board = _board;
        ai = null;
        restart();
    }

    protected Game(int _id, Rules _rules, Board _board, AI _ai)
    {
        id = _id;
        rules = _rules;
        board = _board;
        ai = _ai;
        restart();
    }
    
    // main
    public boolean tryMove(Position move, Player player)
    {
        // requesting player must be the current player
        if(player != getCurrentPlayer())
            return false;
        
        // check if the move is legal for this player
        if(player != null && rules.isLegalMove(move, board, player))
        {
            // perform the move
            state = rules.performMove(move, board, player);

            // if playing against an AI, respond to the move
            if(ai != null && getCurrentPlayer() == AI_PLAYER)
            {
                Position ai_move = ai.chooseMove(rules, board, AI_PLAYER);
                state = rules.performMove(ai_move, board, AI_PLAYER);
            }

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

        // add information about the current player and their possible moves
        result += "<current_player colour=\"" + getCurrentPlayer()
                     + "\" n_options=\"" + moves.size() + "\">";
        for(Position p : moves)
            result += "<option " + p + "/>";
        result += "</current_player>";
        
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
