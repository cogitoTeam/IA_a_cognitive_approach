/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package main;

import test.MiniMaxAgent;



public class Main 
{
    public static void main(String[] args)
    {
        // create agent(s)
        Agent agent = new MiniMaxAgent();
        
        // main loop
        boolean stop = false;
        while(!stop)
        {
            // update the agent(s)
            agent.act();
            
            // stop if the agent breaks down or requests euthanasia 
            if(agent.getState() != Agent.State.NORMAL)
                stop = true;
        }
    }
}
