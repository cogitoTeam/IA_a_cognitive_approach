/*****************
 * @author william
 * @date 04-Apr-2012
 *****************/


package test;

import agent.Action;
import agent.Agent;
import agent.Percept;


public abstract class SwitchAgent extends Agent
{
    /* INTERFACE */
    
    protected abstract Action choicesReaction(Percept.Choices percept);
    protected abstract Action victoryReaction(Percept.Victory percept);
    protected abstract Action defeatReaction(Percept.Defeat percept);
    protected abstract Action drawReaction(Percept.Draw percept);
    
    /* IMPLEMENTATIONS */
    
    @Override
    protected Action perceptReaction(Percept percept)
    {
        switch(percept.getType())
        {
            case CHOICES:
                return choicesReaction((Percept.Choices)percept);
                
            case VICTORY:
                return victoryReaction((Percept.Victory)percept);
                
            case DEFEAT:
                return defeatReaction((Percept.Defeat)percept);
                
            case DRAW:
                return drawReaction((Percept.Draw)percept);
                
            default:
                return null;
        }
    }
}
