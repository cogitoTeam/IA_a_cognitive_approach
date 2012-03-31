/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package agent;


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
    
    protected abstract Action choices_reaction(Percept.Choices percept);
    protected abstract Action victory_reaction(Percept.Victory percept);
    protected abstract Action defeat_reaction(Percept.Defeat percept);
    protected abstract Action draw_reaction(Percept.Draw percept);
    
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
        Action action;
        // reaction vary depending on the type of stimulus
        switch(percept.getType())
        {
            case OPPONENT_TURN:
                think();
                return;
            case CHOICES:
                action = choices_reaction((Percept.Choices)percept);
                break;
            case VICTORY:
                action = victory_reaction((Percept.Victory)percept);
                break;    
            case DEFEAT:
                action = defeat_reaction((Percept.Defeat)percept);
                break;
            case DRAW:
                action = draw_reaction((Percept.Draw)percept);
                break;
            default:
                System.out.println("Unknown Percept type " + percept.getType());
                action = null;
                break;
                
        }
        
        // the agent receives feedback based on the success of their action
        if(!frontier.tryAction(action))
            action_failed(action);
    }
    
}