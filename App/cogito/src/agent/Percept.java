/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/

package agent;

import game.BoardMatrix;
import game.Game.Player;
import java.util.LinkedList;
import java.util.List;

public abstract class Percept 
{
    /* NESTING */
    
    /**
     * The different types of Percept that the Agent can encounter, each related
     * to a specific Percept sub-class with specific attributes:
     * <li>WAITING_FOR_PLAYER when alone in the Game.</li>
     * <li>OPPONENT_TURN when it's not our turn.</li>
     * <li>CHOICES when it's our turn to make a choice.</li>
     * <li>GAME_END when the Game has ended (draw, victory or defeat).</li>
     */
    public static enum Type
    {
        WAITING_FOR_PLAYER,
        OPPONENT_TURN,
        CHOICES,
        GAME_END
    }
    
    
    /* ATTRIBUTES */
    
    private final Type type;
    private final BoardMatrix current_board;
    
    
    /* METHODS */
    
    // creation
    protected Percept(Type _type, BoardMatrix _current_board)
    {
        type = _type;
        current_board = _current_board;
    }
    
    // query
    
    /**
     * Check the perceived board state.
     * @return 
     * The board associated with the Percept: no matter the type we always
     * perceive the board in its current state.
     */
    public BoardMatrix getCurrentBoard() 
    { 
        return current_board; 
    }
    
    /**
     * Check this Percept's type so that we can cast it to the appropriate
     * sub-class.
     * @return 
     * The type Percept. Type enumeration case of this particular Percept: each
     * sub-class has their own special type.
     */
    public Type getType()
    {
        return type;
    }
    
    /* THE OTHER PLAYER HASN'T YET JOINED THE GAME */
    
    /**
     * Percept generated when the Agent is alone in a new Game and waiting for
     * another player to join before beginning to play: the server will not
     * accept move actions until both players have joined.
     */
    public static class WaitingForPlayer extends Percept
    {
        // creation
        public WaitingForPlayer(BoardMatrix _current_board)
        {
            super(Type.WAITING_FOR_PLAYER, _current_board);
        }
    }
    
    /* IT'S NOT OUR TURN TO PLAY */
    
    /**
     * Percept generated when the opponent has joined, but has not yet made a 
     * move: the server will not accept move actions from this Agent as it is
     * not its turn.
     */
    public static class OpponentTurn extends Percept
    {
        // creation
        public OpponentTurn(BoardMatrix _current_board)
        {
            super(Type.OPPONENT_TURN, _current_board);
        }
    }
    
    
    /* IT'S OUR TURN TO PLAY */

    /** 
     * Percept generated when its this Agents turn to make a decision: this
     * sub-class contains a set of Options which are Action-Result pairs. The
     * server will accept any Action chosen from amongst these Options.
     */
    public static class Choices extends Percept
    {
        // attributes
        private final List<Action.Option> options;
        // creation
        public Choices(BoardMatrix _current_board)
        {
            super(Type.CHOICES, _current_board);
            options = new LinkedList<Action.Option>();
        }
        // query
        public List<Action.Option> getOptions() { return options; }
    }
    
    
    /* THE GAME HAS ENDED */
    
    /** 
     * Percept generated when the Game has ended: this sub-class contains a this
     * Agent's score as well as the winner Player to be checked against the
     * Agent.getPlayer() to determine whether this Agent has won or not. Winner
     * is set to null if the game ended in a draw.
     */
    public static class GameEnd extends Percept
    {
        // attributes
        private final int score; //the score of the player who received this percept
        private final Player winner;
        // creation
        protected GameEnd(BoardMatrix _current_board, int _score, Player _winner) 
        { 
            super(Type.GAME_END, _current_board);
            score = _score; 
            winner = _winner;
        }
        // query
        public int getScore() { return score; }
        public Player getWinner() { return winner; }
    }
}
