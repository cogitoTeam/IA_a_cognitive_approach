/*****************
 * @author william
 * @date 30-Mar-2012
 *****************/


package frontier;

import game.BoardMatrix;
import java.util.LinkedList;
import java.util.List;


public class MyTurnPercept extends Percept
{
    /* NESTING */
    
    public static class Option
    {
        // attributes
        private Action action;
        private BoardMatrix result;
        // methods
        public Option(Action _action, BoardMatrix _result)
        {
            action = _action;
            result = _result;
        }
        public Action getAction() { return action; }
        public BoardMatrix getResult() { return result; }
    }
    
    /* ATTRIBUTES */
    
    private final BoardMatrix current_board;
    private final List<Option> options;
    
    
    /* METHODS */
    
    // creation
    public MyTurnPercept(BoardMatrix _current_board)
    {
        current_board = _current_board;
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

    // modification
    public void addOption(BoardMatrix.Position move, BoardMatrix result)
    {
        options.add(new Option(new MoveAction(move), result));
    }
}
