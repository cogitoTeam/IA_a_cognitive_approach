/*****************
 * @author wdyce
 * @date   Apr 3, 2012
 *****************/

package test;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game;
import game.Game.Player;
import game.Rules;
import java.util.List;

public class NegaMaxAgent extends MiniMaxAgent
{
    /* MAIN */
    public static void main(String[] args) throws InterruptedException
    {
      (new NegaMaxAgent()).run();
    }  
  
    /* OVERRIDES */

    @Override
    protected float evaluate(Rules rules, BoardMatrix board, Player current,
                           int depth)
    {
        // maximise my own gain, not the other player's
        Player me = getPlayer();

        // Is leaf node ?
        List<Position> legal_moves = rules.getLegalMoves(board, current);
        if(legal_moves.isEmpty())
            return ((current == me) ? 1 : -1) * value(rules, board, me, depth);

        // Otherwise there should be possible moves to play...
        List<BoardMatrix> children =
                rules.getResultingBoards(board, current, legal_moves);

        // Symetrical evaluation
        float alpha = Float.MIN_VALUE;
        for(BoardMatrix child : children)
            alpha = Math.max(alpha,
                -evaluate(rules, child, Game.otherPlayer(current), depth + 1));
        return alpha;
    }

}
