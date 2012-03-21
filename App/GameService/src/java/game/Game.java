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
    
    /** ATTRIBUTES **/
    
    private final Rules rules;
    private final Board board;
    private Player current_player;
    
    /** METHODS **/
    
    // creation
    public Game(Rules _rules, Board _board)
    {
        rules = _rules;
        board = _board;
    }
    
    // modification
    public void restart()
    {
        rules.reset(board);
        current_player = rules.getFirstPlayer();
    }
    
}
