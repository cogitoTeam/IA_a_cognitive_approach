/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/


package app;

import minimax.MiniMax;



public class Main 
{
    public static void main(String[] args)
    {
        // create agent(s)
        Agent agent = new MiniMax();
        
        // main loop
        boolean stop = false;
        while(!stop)
        {
            agent.update();
            
            // stop if the agent breaks down or requests euthanasia 
            if(agent.getState() != Agent.State.NORMAL)
                stop = true;
        }
    }
}
