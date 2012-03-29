/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package app;

import frontier.Action;
import frontier.Frontier;
import frontier.Percept;


public abstract class Agent 
{
    /* NESTING */
    
    public static enum State
    {
        NORMAL
    }
    
    
    /* ATTRIBUTES */
    
    private Frontier frontier;
    protected State state;
    
    
    
    /* METHODS */
    
    // creation
    protected Agent()
    {
        state = State.NORMAL;
    }
    
    // modification
    public void update()
    {
        // check the environment for new information
        Percept percept = frontier.getPercept();

        // if there's nothing new in sight, reflect on what we already know
        if(percept == null)
        {
            think();
            return;
        }
        
        // otherwise choose a reaction to the stimulus
        Action action = choose_reaction(percept);
        
        // the agent receives feedback based on the success of their action
        receive_feedback(frontier.tryAction(action));
    }
    
    // query
    public State getState()
    {
        return state;
    }
    
    /* INTERFACE */
    
    protected abstract void think();
    
    protected abstract Action choose_reaction(Percept percept);
    
    protected abstract void receive_feedback(boolean success);
}
