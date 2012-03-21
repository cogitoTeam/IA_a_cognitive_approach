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
    
    /** METHODS **/
    
    // creation
    public Game(Rules _rules, Board _board)
    {
        rules = _rules;
        board = _board;
    }
    
}
