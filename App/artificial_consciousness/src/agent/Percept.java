/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package agent;

import game.BoardMatrix;
import java.util.LinkedList;
import java.util.List;

public abstract class Percept 
{
    /* NESTING */
    
    public static enum Type
    {
        OPPONENT_TURN,
        CHOICES,
        VICTORY,
        DEFEAT,
        DRAW
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
    public BoardMatrix getCurrentBoard() 
    { 
        return current_board; 
    }
    
    public Type getType()
    {
        return type;
    }
    
    /* IT'S NOT OUR TURN TO PLAY */
    
    public static class OpponentTurn extends Percept
    {
        // creation
        public OpponentTurn(BoardMatrix _current_board)
        {
            super(Type.OPPONENT_TURN, _current_board);
        }
    }
    
    
    /* IT'S OUR TURN TO PLAY */

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
    
    public static abstract class GameEnd extends Percept
    {
        // attributes
        private final int score;
        // creation
        protected GameEnd(Type _type, BoardMatrix _current_board, int _score) 
        { 
            super(_type, _current_board);
            score = _score; 
        }
        // query
        public int getScore() { return score; }
    }
    
    
    /* THE GAME HAS ENDED, WE WERE DEFEATED */
    
    public static class Defeat extends GameEnd 
    { 
        // creation
        public Defeat(BoardMatrix _current_board, int _score) 
        { 
            super(Type.DEFEAT, _current_board, _score); 
        }
    }
    
    
    /* THE GAME HAS ENDED, WE WERE VICTORIOUS */
    
    public static class Victory extends GameEnd
    { 
        // creation
        public Victory(BoardMatrix _current_board, int _score) 
        { 
            super(Type.VICTORY, _current_board, _score); 
        }
    }
    
    
    /* THE GAME HAS ENDED, IT WAS A DRAW */
    
    public static class Draw extends GameEnd
    { 
        // creation
        public Draw(BoardMatrix _current_board, int _score) 
        { 
            super(Type.DRAW, _current_board, _score); 
        }
    }
}
