/*****************
 * @author wdyce
 * @date   Apr 3, 2012
 *****************/


package test;

import game.BoardMatrix;
import game.Game;
import game.Game.Player;
import game.Rules;
import java.util.List;

public class AlphaBetaAgent extends MiniMaxAgent
{
  /* ATTRIBUTES */

  int alpha, beta;

  /* OVERRIDES */

  @Override
  protected int evaluate(Rules rules, BoardMatrix board, Player current, 
                          int depth)
  {
      alpha = Integer.MIN_VALUE;
      beta = Integer.MAX_VALUE;
      return super.evaluate(getRules(), board, current, depth);
  }

  @Override
  protected int evaluate_min(Rules rules, List<BoardMatrix> children,
                              Player current, int depth)
  {
      for(BoardMatrix child : children)
      {
          beta = Math.min(beta, evaluate(rules, child,
                  Game.otherPlayer(current), depth + 1));
          // beta cut-off
          if(beta <= alpha)
              break;
      }
      return alpha;
  }

  @Override
  protected int evaluate_max(Rules rules, List<BoardMatrix> children,
                              Player current, int depth)
  {
      for(BoardMatrix child : children)
      {
          alpha = Math.max(alpha, evaluate(rules, child,
                  Game.otherPlayer(current), depth + 1));
          // beta cut-off
          if(beta <= alpha)
              break;
      }
      return alpha;
  }
}
