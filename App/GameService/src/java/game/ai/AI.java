/*****************
 * @author wdyce
 * @date   Mar 27, 2012
 *****************/

package game.ai;

import game.*;
import game.Board.Position;
import game.Game.Player;

public abstract class AI
{

    /* INTERFACE */

    public abstract Position chooseMove(Rules rules, Board board, Player player);

}
