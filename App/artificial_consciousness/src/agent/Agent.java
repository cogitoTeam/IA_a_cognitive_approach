/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package agent;

import game.Game;
import game.Rules;


public abstract class Agent 
{
    /* NESTING */
    
    public static enum State
    {
        NORMAL,
        ERROR
        // ... etc
    }
    
    
    /* ATTRIBUTES */
    
    private Frontier frontier;
    private int sleep_time;
    protected State state;
    
    protected abstract void think();
    protected abstract Action perceptReaction(Percept percept);
    protected abstract void actionResult(boolean success, Action action);
    
    
    
    /* METHODS */
    
    // creation
    protected Agent()
    {
        frontier = new Frontier();
        sleep_time = 0;
        state = State.NORMAL;
    }
    
    // modification
    
    public void sleep(int n_seconds)
    {
        sleep_time = n_seconds;
    }
    
    public void act()
    {
        if(sleep_time > 0)
        {
            sleep_time--;
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
    
    // query
    protected Game.Player getPlayer()
    {
        return frontier.getPlayer();
    }
    
    protected Rules getRules()
    {
        return frontier.getRules();
    }
    
    private void react(Percept percept)
    {
        // choose a reaction to the stimulus
        Action action = perceptReaction(percept);
        // the agent receives feedback based on the success of their action
        actionResult(frontier.tryAction(action), action);
    }
    
}
