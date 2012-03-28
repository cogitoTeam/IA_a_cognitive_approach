/*****************
 * @author wdyce
 * @date   Mar 27, 2012
 *****************/

package ai;

import java.util.List;
import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game.Player;
import game.Rules;

public class MiniMaxAI extends AI
{
    /* CONSTANTS */
    private static final Player PMIN = Player.BLACK;
    private static final Player PMAX = Player.WHITE;

    /* IMPLEMENTATIONS */

    @Override
    public Position chooseMove(Rules rules, BoardMatrix board, Player player)
    {
        List<BoardMatrix> children = rules.getChildBoards(board, player);

        return null;
    }

    /* SUBROUTINES */

    private int minimax(Rules rules, BoardMatrix board, Player player)
    {
        return 0;
        /*List<Position> options = rules.getLegalMoves(board, player);

        // Is leaf node ?
        if(options.isEmpty())
            return (rules.hasWon(board, PMAX)) ? 1 
                    : ((rules.hasWon(board, PMIN)) ? -1 : 0);


        // Is min node ?
        if(player == PMIN)
        {
            int beta = Integer.MAX_VALUE;
            for(Position option : options)
            {
                
            }
        }
        else // player == PMAX
        {
            int alpha = Integer.MIN_VALUE;
        }*/

    }

}