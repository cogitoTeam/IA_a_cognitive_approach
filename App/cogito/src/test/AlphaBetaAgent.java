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
  /* OVERRIDES */

  @Override
  protected int evaluate(Rules rules, BoardMatrix board, Player current, 
                          int depth)
  {
    return evaluate_ab(rules, board, current, depth, 
            Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  
  /* SUBROUTINES */
  
  private int evaluate_ab(Rules rules, BoardMatrix board, Player current, 
                          int depth, int alpha, int beta)
  {
    // maximise my own gain, not the other player's
    Player me = getPlayer();

    // Check whether this is a leaf-node (victory, defeat, draw)
    Integer value = value(rules, board, me);
    if(value != null)
      return value;

    // Get the list of possible moves 
    List<BoardMatrix.Position> legal_moves = rules.getLegalMoves(board, current);
    if(legal_moves.isEmpty())
      return 0;

    // There should be possible moves to play...
    List<BoardMatrix> children =
            rules.getResultingBoards(board, current, legal_moves);

    // Is min mode ? Is max node ?
    return (current == me) 
            ? evaluate_max_ab(rules, children, current, depth, alpha, beta)
            : evaluate_min_ab(rules, children, current, depth, alpha, beta);
  }

  protected int evaluate_min_ab(Rules rules, List<BoardMatrix> children,
                                Player current, int depth, int alpha, int beta)
  {
      for(BoardMatrix child : children)
      {
        int beta_prime = evaluate_ab(rules, child, Game.otherPlayer(current), 
                                      depth +1, alpha, beta);
        beta = Math.min(beta, beta_prime);
        if(beta <= alpha)
            break;
      }
      return beta;
  }

  protected int evaluate_max_ab(Rules rules, List<BoardMatrix> children,
                              Player current, int depth, int alpha, int beta)
  {
      for(BoardMatrix child : children)
      {
        int alpha_prime = evaluate_ab(rules, child, Game.otherPlayer(current), 
                                      depth +1, alpha, beta);
        alpha = Math.max(alpha, alpha_prime);
        if(beta <= alpha)
            break;
      }
      return alpha;
  }
}
