/*****************
 * @author wdyce
 * @date   Mar 27, 2012
 *****************/

package game.ai;

import game.Board;
import game.Board.Position;
import game.Game.Player;
import game.Rules;
import java.util.List;

public class MiniMaxAI extends AI
{
    /** CONSTANTS **/
    private static final Player PMIN = Player.BLACK;
    private static final Player PMAX = Player.WHITE;

    /** IMPLEMENTATIONS **/

    @Override
    public Position chooseMove(Rules rules, Board board, Player player)
    {
        List<Board> children = rules.getChildBoards(board, player);

        return null;
    }

    /** SUBROUTINES **/

    private int minimax(Rules rules, Board board, Player player)
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
