/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package frontier;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import java.util.LinkedList;
import java.util.List;


public class Percept 
{
    /* NESTING */
    
    public static class Option
    {
        // attributes
        private Position move;
        private BoardMatrix result;
        // methods
        public Option(Position _move, BoardMatrix _result)
        {
            move = _move;
            result = _result;
        }
        public Position getMove() { return move; }
        public BoardMatrix getResult() { return result; }
    }
    
    /* ATTRIBUTES */
    
    private final BoardMatrix current_board;
    private final List<Option> options;
    private final int value;
    
    
    /* METHODS */
    
    // creation
    public Percept(BoardMatrix _current_board, int _value)
    {
        current_board = _current_board;
        value = _value;
        // NB: there may not be any options to relay (eg. if the game is over)
        options = new LinkedList<Option>();
    }
    
    // query
    public BoardMatrix getCurrentBoard()
    {
        return current_board;
    }
    
    public List<Option> getOptions()
    {
        return options;
    }
    
    public int getValue()
    {
        return value;
    }
    
    // modification
    public void addOption(Position move, BoardMatrix result)
    {
        options.add(new Option(move, result));
    }
    
}
