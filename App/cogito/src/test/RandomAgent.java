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
  /* MAIN */
  public static void main(String[] args) throws InterruptedException
  {
    (new RandomAgent()).run();
  }
    
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
    int rand_i = (int) (Math.random() * percept.getOptions().size());
    return percept.getOptions().get(rand_i).getAction();
  }

  @Override
  protected Action gameEndReaction(Percept.GameEnd percept)
  {
    // restart the game
    try
      {
        Thread.sleep(10000);
      }
    catch (InterruptedException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    return new Action.Restart();
  }

  @Override
  protected void actionResult(boolean success, Action action)
  {
    if (!success)
      {
        System.out.println(action + " failed!");
        sleep(2);
      }
  }

  /* (non-Javadoc)
   * 
   * @see agent.Agent#bootstrap() */
  @Override
  public void bootstrap()
  {
    // Random doesn't need to be boostraped
  }

}
