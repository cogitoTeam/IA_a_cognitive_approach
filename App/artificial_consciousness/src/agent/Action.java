/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package agent;

import game.BoardMatrix;

public abstract class Action 
{
    /* NESTING */
    
    public static enum Type
    {
        MOVE,
        SKIP,
        RESTART,
        EXIT
    }
    
    public static class Option
    {
        // attributes
        private Action action;
        private BoardMatrix result;
        // creation
        public Option(Action _action, BoardMatrix _result)
        {
            action = _action;
            result = _result;
        }
        // query
        public Action getAction() { return action; }
        public BoardMatrix getResult() { return result; }
    }
    
    /* ATTRIBUTES */
    
    private final Type type;
    
    
    /* METHODS */
    
    // creation
    protected Action(Type _type)
    {
        type = _type;
    }
    
    // query
    public Type getType()
    {
        return type;
    }
    
    
    
    /* WE'LL PLACE A PIECE AT THIS POSITION */
    
    public static class Move extends Action
    {
        // attributes
        private final BoardMatrix.Position position;
        // creation
        public Move(BoardMatrix.Position _move) 
        { 
            super(Type.MOVE);
            position = _move; 
        }
        // query
        public BoardMatrix.Position getPosition() { return position; }
    }
    
    /* WE'LL SKIP OUR TURN */
    
    public static class Skip extends Action
    {
        public Skip() { super(Type.SKIP); }
    }
    
    
    /* WE'LL RESTART THE GAME */
    
    public static class Restart extends Action 
    { 
        public Restart() { super(Type.RESTART); }
    }
    
    /* WE'LL LEAVE THE GAME */
    
    public static class Exit extends Action 
    { 
        public Exit() { super(Type.EXIT); }
    }
}
