/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package agent;

import game.BoardMatrix;

public abstract class Action 
{
    /* NESTING */
    
    /**
     * The various types of Action that the Agent can perform, each 
     * corresponding to a specific sub-class of action which may or may not
     * contain additional parameters:
     * <li>MOVE for playing at a specific position.</li>
     * <li>SKIP to renounce this turn and pass the baton to the opponent.</li>
     * <li>RESTART to ask that the board be cleared and reset.</li>
     * <li>EXIT to notify the server that this Agent is disconnecting.</li>
     */
    public static enum Type
    {
        MOVE,
        SKIP,
        RESTART,
        EXIT
    }
    
    /**
     * An Action-result pair, where the result is the BoardMatrix engendered by
     * the Action. Generally speaking the Action is a Move, so the result allows
     * the Agent to evaluate the various moves based on their results and so to
     * choose the most attractive one.
     */
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
    /**
     * @return 
     * The Action's type, in other words its sub-class.
     */
    public Type getType()
    {
        return type;
    }
    
    @Override
    /**
     * Prints the Action's type, which corresponds to its sub-class.
     */
    public String toString()
    {
        return "Action (" + type + ")";
    }
    
    
    
    /* WE'LL PLACE A PIECE AT THIS POSITION */
    
    /**
     * Action sent to the server when the Agent wishes to play (generally 
     * placing a piece) at a specific position on the board: this sub-class 
     * contains a BoardMatrix.Position accessible via getPosition().
     */
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
        @Override
        public String toString()
        {
            return super.toString()+":"+position;
        }
    }
    
    /* WE'LL SKIP OUR TURN */
    
    /**
     * Action sent to the server when the Agent wishes to skip its turn: 
     * Depending on the Rules of the Game the server may or may not refuse this
     * Action.
     */
    public static class Skip extends Action
    {
        public Skip() { super(Type.SKIP); }
    }
    
    
    /* WE'LL RESTART THE GAME */
    
    /**
     * Action sent to the server when the Agent wishes to restart the Game:
     * the server always accepts this action, resetting the Board back to its
     * initial state.
     */
    public static class Restart extends Action 
    { 
        public Restart() { super(Type.RESTART); }
    }
    
    /* WE'LL LEAVE THE GAME */
    
    /**
     * Action sent to the server when the Agent wishes to leave the Game: the 
     * server always accepts this action, though it practice it is not used as
     * we don't manage disconnections.
     */
    public static class Exit extends Action 
    { 
        public Exit() { super(Type.EXIT); }
    }
}
