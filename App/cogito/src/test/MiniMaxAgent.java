/*****************
 * @author wdyce
 * @date Mar 27, 2012
 *****************/

package test;

import agent.Action;
import agent.Percept.Choices;
import agent.Percept.GameEnd;
import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game;
import game.Game.Player;
import game.Rules;
import java.util.LinkedList;
import java.util.List;

public class MiniMaxAgent extends SwitchAgent
{
  /* MAIN */
  public static void main(String[] args) throws InterruptedException
  {
    (new MiniMaxAgent()).run();
  }  
    
  /* IMPLEMENTATIONS */

  @Override
  protected void think()
  {
    // minimax doesn't think
  }

  @Override
  public Action choicesReaction(Choices percept) 
  {
    // loca variables
    Player enemy = Game.otherPlayer(getPlayer());

    // we'll choose a random move from amongst the best we can find
    List<Action> best_moves = new LinkedList<Action>();
    float best_utility = Float.MIN_VALUE;

    for(Action.Option o : percept.getOptions())
    {
      // get the utility using evaluate (with or without alphabeta)
      float o_utility = evaluate(getRules(), o.getResult(), enemy, 0);

      // result is as good ? Add to list of possible moves
      if(o_utility >= best_utility)
      {
        // result is improvement ? Reset list of possible moves
        if(o_utility > best_utility)
        {
          best_utility = o_utility;
          best_moves.clear();
        }

        // needless to say we add to the list after clearing it!
        best_moves.add(o.getAction());
      }
    }

    // restart if no moves are possible
    if(best_moves.isEmpty())
      return new Action.Restart();
    // perform a random action from amongst the best
    else
      return best_moves.get((int)(Math.random()*best_moves.size()));
  }

  @Override
  protected Action gameEndReaction(GameEnd percept)
  {
    // restart the game
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
  
  /* OVERRIDEN */
  
  public int getMaxDepth()
  {
    // NB - complexity if O(b^depth) where b is the number of options per turn
    return 3; 
  }

  /* SUBROUTINES */

  protected Float value(Rules rules, BoardMatrix board, Player player, int depth)
  {
    // cut off with a heuritic value is search-depth is too high

    if(depth > getMaxDepth())
      return rules.estimateValue(board, player);


    // a zero-sum value is returned if the rules dictate that the game is over

    if(rules.hasWon(board, player))
      return 1.0f;
    else if(rules.hasWon(board, Game.otherPlayer(player)))
      return -1.0f;
    else if(rules.isDraw(board))
      return 0.0f;

    // null is returned if the game is not over

    else
      return null;
  }

  protected float evaluate(Rules rules, BoardMatrix board, Player current, 
                        int depth)
  {
      // Maximise my own gain, not the other player's
      Player me = getPlayer();

      // Check whether this is a leaf-node (victory, defeat, draw)
      Float value = value(rules, board, me, depth);
      if(value != null)
        return value;

      // Get the list of possible moves 
      List<Position> legal_moves = rules.getLegalMoves(board, current);
      if(legal_moves.isEmpty())
        return 0;

      // There should be possible moves to play...
      List<BoardMatrix> children =
              rules.getResultingBoards(board, current, legal_moves);

      // Is min mode ? Is max node ?
      return (current == me) 
              ? evaluate_max(rules, children, current, depth)
              : evaluate_min(rules, children, current, depth);
  }

  protected float evaluate_min(Rules rules, List<BoardMatrix> children,
                              Player current, int depth)
  {
      float beta = Float.MAX_VALUE;
      for(BoardMatrix child : children)
      {
        float beta_prime = evaluate(rules, child, Game.otherPlayer(current), 
                                  depth + 1);
        if(beta_prime < beta)
          beta = beta_prime;
      }
      return beta;
  }

  protected float evaluate_max(Rules rules, List<BoardMatrix> children,
                              Player current, int depth)
  {
      float alpha = Float.MIN_VALUE;
      for(BoardMatrix child : children)
      {
        float alpha_prime = evaluate(rules, child, Game.otherPlayer(current), 
                                    depth +1);
        if(alpha_prime > alpha)
          alpha = alpha_prime;
      }
      return alpha;
  }
}
