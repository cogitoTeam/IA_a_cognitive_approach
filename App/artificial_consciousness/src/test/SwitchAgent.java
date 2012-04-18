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

  protected abstract Action gameEndReaction(Percept.GameEnd percept);

  /* IMPLEMENTATIONS */

  @Override
  protected Action perceptReaction(Percept percept)
  {
    int score;
    switch (percept.getType())
      {
        case CHOICES:
          return choicesReaction((Percept.Choices) percept);

        case GAME_END:
          return gameEndReaction((Percept.GameEnd) percept);

        default:
          return null;
      }
  }
}
