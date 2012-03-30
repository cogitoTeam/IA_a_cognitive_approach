/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package test;

import frontier.Action;
import frontier.MyTurnPercept;
import frontier.Percept;
import main.Agent;


public class RandomAgent extends Agent
{

    @Override
    protected void think() 
    {
        // do nothing
    }

    @Override
    protected Action choose_reaction(Percept percept) 
    {
        /// FIXME
        MyTurnPercept p = (MyTurnPercept)percept;
        
        // choose random action from amongts options
        int rand_i = (int)(Math.random()*p.getOptions().size());
        System.out.println("playing option number " + rand_i);
        return p.getOptions().get(rand_i).getAction();
    }

    @Override
    protected void action_failed(Action action) 
    {
        // the will stop the execution: best avoided generally speaking
        state = State.ERROR;
    }

}
