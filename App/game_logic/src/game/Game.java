/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.BoardMatrix.Position;
import java.util.List;


public class Game 
{
    /* NESTING */
    
    /**
     * The two players playing the Game, black and white.
     */
    public static enum Player
    {
        WHITE,
        BLACK
    }
    
    /**
     * The various state the Game might be in, based on the most recent event
     * that has occurred:
     * <li>WAITING_FOR_PLAYER if the first player has just joined.</li>
     * <li>PLAYER_JOINED if the second player has just joined.</li>
     * <li>MOVE_SUCCESS if the most recent move order was successful.</li>
     * <li>MOVE_FAILURE if the most recent move order was illegal.</li>
     * <li>NO_CHANGE if the last access to the structure wasn't a move order.</li>
     * <li>VICTORY if the current player has just won the game.</li>
     * <li>DEFEAT if the current player has just won the game.</li>
     * <li>DRAW if the current player just drew the game.</li>
     */
    public static enum State
    {
        WAITING_FOR_PLAYER,
        PLAYER_JOINED,
        MOVE_SUCCESS,
        MOVE_FAILURE,
        NO_CHANGE,
        VICTORY,
        DEFEAT,
        DRAW;
    }
    
    
    /* CLASS NAMESPACE FUNCTIONS */
    
    /**
     * Returns the complementary player.
     * @param p
     * The player whose opponent we wish to know.
     * @return 
     * The specified players opponent, so BLACK for WHITE and WHITE for BLACK.
     * The complementary of both null and NEITHER player is NEITHER.
     */
    public static Player otherPlayer(Player p)
    {
        switch(p)
        {
            case WHITE: 
                return Player.BLACK;
            case BLACK:
                return Player.WHITE;
            default:
                return null;
        }
    }

    /**
     * Turn a String into a Game.Player enumeration case.
     * @param s
     * The String to parse.
     * @return 
     * The Game.Player corresponding to this String, or null if the String 
     * doesn't correspond to either player.
     */
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
    private State current_state;
    private Player current_player;
        
    /* METHODS */
    
    // creation
    /**
     * The standard constructor of the Game which initialises final attributes.
     * @param _id
     * An initialiser for the game unique identifier, used to find into inside
     * the GameManager.
     * @param _rules 
     * The rules that this game uses.
     */
    public Game(int _id, Rules _rules)
    {
        // save attributes
        id = _id;
        rules = _rules;
        // create the required structures
        board = _rules.createBoard();
        // start the game
        current_player = rules.getFirstPlayer();
        current_state = State.WAITING_FOR_PLAYER;
    }
    
    
    // modification
    
    
    /**
     * Reset the state of the Game to reflect that the most recent event is,
     * for instance, the joining of a new player.
     */
    public synchronized void setState(State new_state)
    {
        current_state = new_state;
    }
    
    /**
     * Attempt to make a move at a given position in the name of the specified
     * player: the rules are check and the Game's state is reset according to
     * the success or failure of the move.
     * @param move
     * The position of the move to be played.
     * @param player 
     * The player to play the move.
     */
    public synchronized void tryMove(Position move, Player player)
    {
        // check that it's the right player playing
        if(player != current_player)
        {
            current_state = State.MOVE_FAILURE;
            // skip to the end
            return;
        }
        
        // if it's the right player's turn try to perform the move
        current_state = rules.tryMove(move, board, player);
        
        // switch players if the move was a success
        if(current_state == State.MOVE_SUCCESS)
        {
            // switch the current player
            current_player = otherPlayer(current_player);
            
            // switch back again if this new player can't move
            if(!rules.canMove(board, current_player))
            {
                current_player = otherPlayer(current_player);
            
                // end in a draw if neither player can move
                if(!rules.canMove(board, current_player))
                    current_state = State.DRAW;
            }
        }
        
        // defeat state is neither used outside of this block: it's all relative
        else if(current_state == State.DEFEAT)
        {
            // one man's victory is another's defeat
            current_player = otherPlayer(current_player);
            current_state = State.VICTORY;
        }
    }
    
    /**
     * Reset the board, the current player and the game state back to their
     * original settings, based on the game rules.
     */
    public synchronized final void restart()
    {
        rules.reset(board);
        current_player = rules.getFirstPlayer();
        current_state = State.MOVE_SUCCESS;
    }
    
    
    // query
    
    /**
     * Read the Game's state, the last important event that occurred: useful
     * for judging the success or failure of a move.
     * @return
     * The current State of the Game.
     */
    public State getState()
    {
        return current_state;
    }
    
    /**
     * Check whose turn it is.
     * @return 
     * The next player to play.
     */
    public Player getCurrentPlayer()
    {
        return current_player;
    }
    
    /* OVERRIDES */
  
    /**
     * Return an XML 'game' tag containing all the information about the Game,
     * including:
     * <li>The unique identifier</li>
     * <li>The state</li>
     * <li>The rules in use</li>
     * <li>The board contents</li>
     * <li>Whose turn it is</li>
     * <li>A list of the current player options</li>
     * This is used for Representational State-Transfer (REST).
     * @return 
     */
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
}
