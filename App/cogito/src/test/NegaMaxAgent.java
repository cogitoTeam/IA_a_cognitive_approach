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

public class NegaMaxAgent extends MiniMaxAgent
{
  /* OVERRIDES */

  @Override
  protected float evaluate(Rules rules, BoardMatrix board, Player current, int depth)
  {
      return evaluate_neg(rules, board, current, depth);
  }
    
  /* SUBROUTINES */
  
  private float evaluate_neg(Rules rules, BoardMatrix board, Player current, int depth)
  {
    // maximise my own gain, not the other player's
    Player me = getPlayer();

    // Check whether this is a leaf-node (victory, defeat, draw)
    Float value = value(rules, board, me, depth);
    if(value != null)
      return (depth % 2 == 0) ? -value : value;

    // Get the list of possible moves 
    List<BoardMatrix.Position> legal_moves = rules.getLegalMoves(board, current);
    if(legal_moves.isEmpty())
      return 0;

    // There should be possible moves to play...
    List<BoardMatrix> children =
            rules.getResultingBoards(board, current, legal_moves);

    
    float alpha = Float.MIN_VALUE;
    for(BoardMatrix child : children)
    {
      float alpha_prime = -evaluate_neg(rules, child, Game.otherPlayer(current),
                                      depth + 1);
      alpha = Math.max(alpha, alpha_prime);
      
      // Alpha-beta pruning
      //if(alpha > beta)
      //  return alpha_prime;
    }
    
    return (depth % 2 == 0) ? -alpha : alpha;
  }
}