/*****************
 * @author wdyce
 * @date   Mar 27, 2012
 *****************/

package game.ai;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game.Player;
import game.Rules;

public abstract class AI
{

    /* INTERFACE */

    public abstract Position chooseMove(Rules rules, BoardMatrix board, Player player);

}
