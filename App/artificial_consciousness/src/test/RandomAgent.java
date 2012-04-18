/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package test;

import agent.Action;
import agent.Percept;
import agent.Percept.Choices;


public class RandomAgent extends SwitchAgent
{

    /* IMPLEMENTATIONS */
    
    @Override
    protected void think() 
    {
        // do nothing
    }

    @Override
    protected Action choicesReaction(Choices percept) 
    {
        // choose random action from amongst options
        int rand_i = (int)(Math.random()*percept.getOptions().size());
        return percept.getOptions().get(rand_i).getAction();
    }

    @Override
    protected Action gameEndReaction(Percept.GameEnd percept) 
    {
        // restart the game
        return new Action.Restart();
    }
    
    @Override
    protected void actionResult(boolean success, Action action) 
    {
        if(!success)
        {
            System.out.println(action + " failed!");
            sleep(2);
        }
    }

}
