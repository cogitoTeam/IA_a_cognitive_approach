/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;


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
        DRAW
    }
    
    /** ATTRIBUTES **/
    
    private final Rules rules;
    private final Board board;
    private State state;
    
    /** METHODS **/
    
    // creation
    public Game(Rules _rules, Board _board)
    {
        rules = _rules;
        board = _board;
    }
    
    // main
    public boolean tryMove(Board.Position p)
    {
        // find the current player
        Player player = getCurrentPlayer();
        
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
    
    // modification
    public void restart()
    {
        rules.reset(board);
        state = (rules.getFirstPlayer() == Player.WHITE) ? 
                State.TURN_WHITE : State.TURN_BLACK;
    }
    
}
