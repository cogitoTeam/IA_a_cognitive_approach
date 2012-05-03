/*****************
 * @author william
 * @date 31-Mar-2012
 *****************/

package test;

import agent.Agent;

public class RandomIA
{
  /* MAIN */

  public static void main(String[] args) throws InterruptedException
  {
    boolean bootstrap = false;
    // create agent(s)
    Agent agent = new RandomAgent();

    if (bootstrap)
      agent.bootstrap();

    // main loop
    agent.run();

    System.out.println("Stopped");
  }
}
