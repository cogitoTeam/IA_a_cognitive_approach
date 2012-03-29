/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package main;

import frontier.Action;
import frontier.Frontier;
import frontier.Percept;


public abstract class Agent 
{
    /* NESTING */
    
    public static enum State
    {
        ASLEEP,
        NORMAL,
        ERROR
        // ... etc
    }
    
    
    /* ATTRIBUTES */
    
    private Frontier frontier;
    protected State state;
    
    

    /* INTERFACE */
    
    protected abstract void think();
    
    protected abstract Action choose_reaction(Percept percept);
    
    protected abstract void action_failed(Action action);
    
    
    
    /* METHODS */
    
    // creation
    protected Agent()
    {
        frontier = new Frontier();
        state = State.ASLEEP;
    }
    
    public void act()
    {
        // we can't act if state is abnormal
        if(state == State.ASLEEP)
        {
            awaken();
            return;
        }
        
        // check environment for new information
        Percept percept = frontier.newPercept();
        
        // if there's nothing new in sight, reflect on what we already know
        if(percept == null)
            think();
        // otherwise react to this new environment state
        else
            react(percept);
       
    }
    
    // query
    public State getState()
    {
        return state;
    }
    
    
    /* SUBROUTINES */
    
    private void awaken()
    {
        // always act upon waking up
        react(frontier.oldPercept());
        state = State.NORMAL;
    }
    
    private void react(Percept percept)
    {
        // choose a reaction to the stimulus
        Action action = choose_reaction(percept);
        
        // the agent receives feedback based on the success of their action
        if(!frontier.tryAction(action))
            action_failed(action);
    }
    
}
