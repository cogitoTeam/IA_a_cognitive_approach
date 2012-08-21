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
    System.out.println("MINIMAX");
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
    int best_utility = Integer.MIN_VALUE;

    for(Action.Option o : percept.getOptions())
    {
      // get the utility using evaluate (with or without alphabeta)
      int o_utility = evaluate(getRules(), o.getResult(), enemy, 0);

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


    // perform a random action from amongst the best
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
    return 12; 
  }

  /* SUBROUTINES */

  protected Integer value(Rules rules, BoardMatrix board, Player player, int depth)
  {
    // cut off with a heuritic value is search-depth is too high

    if(depth > getMaxDepth())
      return (int)((Integer.MAX_VALUE-1) * rules.estimateValue(board, player));


    // a zero-sum value is returned if the rules dictate that the game is over

    if(rules.hasWon(board, player))
      return Integer.MAX_VALUE-1;
    else if(rules.hasWon(board, Game.otherPlayer(player)))
      return Integer.MIN_VALUE+1;
    else if(rules.isDraw(board))
      return 0;

    // null is returned if the game is not over

    else
      return null;
  }

  protected int evaluate(Rules rules, BoardMatrix board, Player current, 
                        int depth)
  {
      // Maximise my own gain, not the other player's
      Player me = getPlayer();

      // Check whether this is a leaf-node (victory, defeat, draw)
      Integer value = value(rules, board, me, depth);
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

  protected int evaluate_min(Rules rules, List<BoardMatrix> children,
                              Player current, int depth)
  {
      int beta = Integer.MAX_VALUE;
      for(BoardMatrix child : children)
      {
        int beta_prime = evaluate(rules, child, Game.otherPlayer(current), 
                                  depth + 1);
        if(beta_prime < beta)
          beta = beta_prime;
      }
      return beta;
  }

  protected int evaluate_max(Rules rules, List<BoardMatrix> children,
                              Player current, int depth)
  {
      int alpha = Integer.MIN_VALUE;
      for(BoardMatrix child : children)
      {
        int alpha_prime = evaluate(rules, child, Game.otherPlayer(current), 
                                    depth +1);
        if(alpha_prime > alpha)
          alpha = alpha_prime;
      }
      return alpha;
  }
}
