/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.BoardMatrix.Position;
import game.ai.AI;
import java.util.List;


public abstract class Game 
{
    /* NESTING */
    
    public static enum Player
    {
        WHITE,
        BLACK
    }
    
    public static enum State
    {
        GAME_START,
        MOVE_SUCCESS,
        MOVE_FAILURE,
        NO_CHANGE,
        VICTORY,
        DRAW;
    }


    /* CONSTANTS */
    
    public static final Player AI_PLAYER = Player.BLACK;
    
    
    /* CLASS NAMESPACE FUNCTIONS */

    public static Player parsePlayer(String s)
    {
        if(s.equals("WHITE") || s.equals("white"))
            return Player.WHITE;
        
        else if(s.equals("BLACK") || s.equals("black"))
            return Player.BLACK;
        
        else
            return null;
    }
    

    /* ATTRIBUTES */

    private final int id;
    private final Rules rules;
    private final BoardMatrix board;
    private final AI ai;
    private State current_state;
    private Player current_player;
        
    /* METHODS */
    
    // creation
    protected Game(int _id, Rules _rules, BoardMatrix _board)
    {
        id = _id;
        rules = _rules;
        board = _board;
        ai = null;
        restart();
    }

    protected Game(int _id, Rules _rules, BoardMatrix _board, AI _ai)
    {
        id = _id;
        rules = _rules;
        board = _board;
        ai = _ai;
        restart();
    }
    
    // modification
    
    public void failMove()
    {
        current_state = State.NO_CHANGE;
    }
    
    public void tryMove(Position move, Player player)
    {
        // check that it's the right player playing
        if(player != current_player)
        {
            current_state = State.MOVE_FAILURE;
            // skip to the end
            return;
        }
        
        // if it's the right player's turn try to perform the move
        current_state = rules.performMove(move, board, player);
        
        // check if the move end the game
        if(current_state == State.MOVE_SUCCESS)
        {
            // switch the current player
            switchCurrentPlayer();

            // if playing against an AI, respond to the move
            if(ai != null && current_player == AI_PLAYER)
            {
                Position ai_move = ai.chooseMove(rules, board, AI_PLAYER);
                current_state = rules.performMove(ai_move, board, AI_PLAYER);
            }
        }
    }
    
    // query
    public State getState()
    {
        return current_state;
    }
    
    public Player getCurrentPlayer()
    {
        return current_player;
    }
    
    
    /* SUBROUTINES */
    
    private void switchCurrentPlayer()
    {
        current_player = (current_player == Player.WHITE) ? Player.BLACK 
                                                        : Player.WHITE;
    }
    
    /* OVERRIDES */
  
    @Override
    public String toString()
    {
        // local variables
        List<Position> moves = rules.getLegalMoves(board, getCurrentPlayer());
        String result = "<game id=\"" + id 
                    + "\" state=\"" + current_state 
                    + "\" rules=\"" + rules + "\">";
        
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
        current_player = rules.getFirstPlayer();
        current_state = State.GAME_START;
    }
    
}
