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
        Agent agent = new MiniMax();
        
        boolean stop = false;
        while(!stop)
        {
            System.out.println(agent.state);
        }
    }
}
