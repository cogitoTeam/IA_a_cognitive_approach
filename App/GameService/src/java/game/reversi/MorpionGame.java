/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.reversi;

import game.Game;


public class MorpionGame extends Game
{
    /** METHODS **/
    
    public MorpionGame()
    {
        super(MorpionRules.getInstance(), new MorpionBoard());
    }
}
