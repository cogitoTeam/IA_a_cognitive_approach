/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.reversi;

import game.Game;


public class ReversiGame extends Game
{
    /** METHODS **/
    
    public ReversiGame()
    {
        super(ReversiRules.getInstance(), new ReversiBoard());
    }
}
